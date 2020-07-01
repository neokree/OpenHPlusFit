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

public class DownDialTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = DownDialTask.class.getName();
    private final String BASE_UI = "UI";
    private final String BASE_URL = "http://120.77.159.61/hpluswatch/fw/";
    /* access modifiers changed from: private */
    public IDialFileCallback dialFileCallback;
    private String functionNumber;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context mContext;
    private String productNumber;
    private PushDialBean pushDialBean;

    public DownDialTask(Context context, String str, int i, PushDialBean pushDialBean2, IDialFileCallback iDialFileCallback) {
        this.mContext = context.getApplicationContext();
        this.dialFileCallback = iDialFileCallback;
        this.productNumber = str;
        this.pushDialBean = pushDialBean2;
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
        String str = "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + "UI" + "/" + this.pushDialBean.getResFile();
        String str2 = "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + "UI" + "/" + this.pushDialBean.getLayoutFile();
        if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.INTERNET")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownDialTask.this.dialFileCallback.notInternetPermission();
                }
            });
            return false;
        } else if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownDialTask.this.dialFileCallback.notFileWritePermission();
                }
            });
            return false;
        } else {
            try {
                URL url = new URL(str);
                URL url2 = new URL(str2);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
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
                    DialFileUtil.analysisResJson(stringBuffer.toString());
                }
                if (httpURLConnection2.getResponseCode() == 200) {
                    InputStream inputStream2 = httpURLConnection2.getInputStream();
                    InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2);
                    BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);
                    StringBuffer stringBuffer2 = new StringBuffer();
                    while (true) {
                        String readLine2 = bufferedReader2.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        stringBuffer2.append(readLine2);
                    }
                    inputStream2.close();
                    bufferedReader2.close();
                    inputStreamReader2.close();
                    DialFileUtil.analysisLayoutJson(stringBuffer2.toString());
                }
            } catch (final Exception e) {
                this.handler.post(new Runnable() {
                    public void run() {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            DownDialTask.this.dialFileCallback.onFailDialFile(e.getMessage());
                        } else {
                            DownDialTask.this.dialFileCallback.onFailDialFile("Get dial file failed!");
                        }
                    }
                });
            }
            return false;
        }
    }
}
