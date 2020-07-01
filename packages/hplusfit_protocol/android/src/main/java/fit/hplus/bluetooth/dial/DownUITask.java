package fit.hplus.bluetooth.dial;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import fit.hplus.bluetooth.bean.PushDialBean;
import fit.hplus.bluetooth.util.DialFileUtil;
import fit.hplus.bluetooth.util.PermissionUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DownUITask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = DownUITask.class.getName();
    private final String BASE_INFO = "info";
    private final String BASE_JSON = ".json";
    private final String BASE_UI = "UI";
    private final String BASE_URL = "http://120.77.159.61/hpluswatch/fw/";
    /* access modifiers changed from: private */
    public OnDialFileCallback dialFileCallback;
    private String functionNumber;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context mContext;
    /* access modifiers changed from: private */
    public DialFileStatusCallback mUIFileStatusCallback;
    private String productNumber;

    public DownUITask(Context context, String str, int i, DialFileStatusCallback dialFileStatusCallback, OnDialFileCallback onDialFileCallback) {
        this.mContext = context.getApplicationContext();
        this.mUIFileStatusCallback = dialFileStatusCallback;
        this.dialFileCallback = onDialFileCallback;
        this.productNumber = str;
        this.functionNumber = String.format("%02d", new Object[]{Integer.valueOf(i)});
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground(Void... voidArr) {
        return requestData();
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        this.handler.removeCallbacksAndMessages((Object) null);
    }

    private Boolean requestData() {
        String str = "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + "UI" + "/" + this.productNumber + "_" + this.functionNumber + "_" + "info" + ".json";
        if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.INTERNET")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownUITask.this.mUIFileStatusCallback.notInternetPermission();
                }
            });
            return false;
        } else if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownUITask.this.mUIFileStatusCallback.notFileWritePermission();
                }
            });
            return false;
        } else {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuffer.append(readLine);
                    }
                    inputStream.close();
                    bufferedReader.close();
                    inputStreamReader.close();
                    final List<PushDialBean> analysisDialJson = DialFileUtil.analysisDialJson(stringBuffer.toString());
                    if (analysisDialJson == null || analysisDialJson.size() <= 0) {
                        this.handler.post(new Runnable() {
                            public void run() {
                                DownUITask.this.mUIFileStatusCallback.onFailDialFile("The device has not dial!");
                            }
                        });
                    } else {
                        final JSONArray jSONArray = new JSONArray();
                        for (int i = 0; i < analysisDialJson.size(); i++) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("dialIndex", i);
                            jSONObject.put("imaUrl", "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + "UI" + "/" + analysisDialJson.get(i).getDrawableFile());
                            jSONArray.put(jSONObject);
                        }
                        this.handler.post(new Runnable() {
                            public void run() {
                                DownUITask.this.mUIFileStatusCallback.onSuccessDialFile(jSONArray.toString());
                                DownUITask.this.dialFileCallback.onDialFile(analysisDialJson);
                            }
                        });
                    }
                }
            } catch (final Exception e) {
                this.handler.post(new Runnable() {
                    public void run() {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            DownUITask.this.mUIFileStatusCallback.onFailDialFile(e.getMessage());
                        } else {
                            DownUITask.this.mUIFileStatusCallback.onFailDialFile("Get dial file failed!");
                        }
                    }
                });
            }
            return false;
        }
    }
}
