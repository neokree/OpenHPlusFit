package fit.hplus.bluetooth.dial;

import android.content.Context;
import fit.hplus.bluetooth.HPlusBleManager;
import fit.hplus.bluetooth.bean.PushDialBean;
import fit.hplus.bluetooth.bean.PushDialLayoutBean;
import fit.hplus.bluetooth.bean.PushDialResBean;
import fit.hplus.bluetooth.util.HexUtil;
import java.util.ArrayList;
import java.util.List;

public class DialController implements IDialController, OnDialFileCallback, IDialFileCallback {
    private int functionNumber;
    private Context mContext;
    private DialFileCallback mDialFileCallback;
    private HPlusBleManager mHPlusBleManager;
    private List<PushDialBean> mList;
    private int productNumber;

    public DialController(HPlusBleManager hPlusBleManager, Context context) {
        this.mContext = context;
        this.mHPlusBleManager = hPlusBleManager;
    }

    public void getDialFile(int i, int i2, DialFileStatusCallback dialFileStatusCallback) {
        this.productNumber = i;
        this.functionNumber = i2;
        new DownUITask(this.mContext, HexUtil.encodeHexStr(new byte[]{(byte) (i >> 8), (byte) (i & 255)}, false), i2, dialFileStatusCallback, this).execute(new Void[0]);
    }

    public void pushDialWithIndex(int i, DialFileCallback dialFileCallback) {
        List<PushDialBean> list = this.mList;
        if (list != null) {
            if (i >= 0 && i <= list.size() - 1) {
                if (dialFileCallback != null) {
                    this.mDialFileCallback = dialFileCallback;
                }
                int i2 = this.productNumber;
                new DownDialTask(this.mContext, HexUtil.encodeHexStr(new byte[]{(byte) (i2 >> 8), (byte) (i2 & 255)}, false), this.functionNumber, this.mList.get(i), this).execute(new Void[0]);
            } else if (dialFileCallback != null) {
                dialFileCallback.getFailDialFile("The file does not exist");
            }
        } else if (dialFileCallback != null) {
            dialFileCallback.getFailDialFile("Please get the dialFile");
        }
    }

    public void pushDial(List<PushDialLayoutBean> list, List<PushDialResBean> list2, DialFileCallback dialFileCallback) {
        this.mDialFileCallback = dialFileCallback;
        onResData(list2);
        onLayoutData(list);
    }

    public void onDialFile(List<PushDialBean> list) {
        this.mList = list;
    }

    public void notInternetPermission() {
        DialFileCallback dialFileCallback = this.mDialFileCallback;
        if (dialFileCallback != null) {
            dialFileCallback.notInternetPermission();
        }
    }

    public void notFileWritePermission() {
        DialFileCallback dialFileCallback = this.mDialFileCallback;
        if (dialFileCallback != null) {
            dialFileCallback.notFileWritePermission();
        }
    }

    public void onFailDialFile(String str) {
        DialFileCallback dialFileCallback = this.mDialFileCallback;
        if (dialFileCallback != null) {
            dialFileCallback.getFailDialFile(str);
        }
    }

    public void onResData(List<PushDialResBean> list) {
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList(list.size());
            int i = 0;
            int i2 = 0;
            while (i < list.size()) {
                i++;
                List<byte[]> pack = list.get(i).pack(list.size(), i);
                arrayList.add(pack);
                i2 += pack.size();
            }
            this.mHPlusBleManager.sendDialResCommand(arrayList, i2, this.mDialFileCallback);
        }
    }

    public void onLayoutData(List<PushDialLayoutBean> list) {
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList(list.size() + 1);
            int i = 0;
            int i2 = 0;
            while (i < list.size()) {
                i++;
                arrayList.add(list.get(i).pack(list.size(), i));
                i2++;
            }
            byte[] bArr = new byte[20];
            bArr[0] = -14;
            bArr[1] = (byte) list.size();
            for (int i3 = 2; i3 < bArr.length; i3++) {
                bArr[i3] = -1;
            }
            arrayList.add(bArr);
            this.mHPlusBleManager.sendDialLayoutCommand(arrayList, i2 + 1, this.mDialFileCallback);
        }
    }
}
