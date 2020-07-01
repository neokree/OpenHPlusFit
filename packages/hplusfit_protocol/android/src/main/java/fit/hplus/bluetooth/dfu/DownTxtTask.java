package fit.hplus.bluetooth.dfu;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import fit.hplus.bluetooth.bean.DeviceVersionBean;
import fit.hplus.bluetooth.util.DfuUtils;
import fit.hplus.bluetooth.util.PermissionUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownTxtTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = DownTxtTask.class.getName();
    private final String BASE_HPLUS = "HPlus";
    private final String BASE_TXT = ".txt";
    private final String BASE_URL = "http://120.77.159.61/hpluswatch/fw/";
    private final String BASE_XML = "version.xml";
    private final String BASE_ZIP = ".zip";
    private String functionNumber;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context mContext;
    /* access modifiers changed from: private */
    public DfuFileStatusCallback mDfuFileStatusCallback;
    private String productNumber;

    public DownTxtTask(Context context, String str, int i, DfuFileStatusCallback dfuFileStatusCallback) {
        this.mContext = context.getApplicationContext();
        this.mDfuFileStatusCallback = dfuFileStatusCallback;
        this.productNumber = str;
        this.functionNumber = String.format("%02d", new Object[]{Integer.valueOf(i)});
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

    /* access modifiers changed from: protected */
    public Boolean doInBackground(Void... voidArr) {
        return Boolean.valueOf(requestData());
    }

    private boolean requestData() {
        List<DeviceVersionBean> pullxml;
        String str = "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + "version.xml";
        String str2 = "http://120.77.159.61/hpluswatch/fw/" + this.productNumber + "/" + this.functionNumber + "/" + this.productNumber + "_" + this.functionNumber + ".txt";
        if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.INTERNET")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownTxtTask.this.mDfuFileStatusCallback.notInternetPermission();
                }
            });
            return false;
        } else if (!PermissionUtil.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            this.handler.post(new Runnable() {
                public void run() {
                    DownTxtTask.this.mDfuFileStatusCallback.notFileWritePermission();
                }
            });
            return false;
        } else {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                if (httpURLConnection.getResponseCode() == 200 && (pullxml = DfuUtils.pullxml(httpURLConnection.getInputStream())) != null && pullxml.size() > 0) {
                    DeviceVersionBean deviceVersionBean = pullxml.get(0);
                    String md5 = deviceVersionBean.getMd5();
                    final int ver = deviceVersionBean.getVer();
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str2).openConnection();
                    if (httpURLConnection2.getResponseCode() == 200) {
                        InputStream inputStream = httpURLConnection2.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        final String str3 = Environment.getExternalStorageDirectory() + File.separator + this.productNumber + "_" + this.functionNumber + ".zip";
                        DfuUtils.createFileWithByte(Base64.decode(DfuUtils.decrypt("HPlus27806616", byteArrayOutputStream.toByteArray()), 0), str3);
                        if (DfuUtils.md5(str3).equals(md5)) {
                            this.handler.post(new Runnable() {
                                public void run() {
                                    DownTxtTask.this.mDfuFileStatusCallback.onSuccessOTAFile(ver, str3);
                                }
                            });
                            return true;
                        }
                        this.handler.post(new Runnable() {
                            public void run() {
                                DownTxtTask.this.mDfuFileStatusCallback.onFailOTAFile("The file is error");
                            }
                        });
                        return false;
                    }
                }
            } catch (Exception e) {
                this.handler.post(new Runnable() {
                    public void run() {
                        DownTxtTask.this.mDfuFileStatusCallback.onFailOTAFile(e.getMessage());
                    }
                });
            }
            return false;
        }
    }
}
