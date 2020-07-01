package fit.hplus.bluetooth.dfu;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import fit.hplus.bluetooth.HPlusBleManager;
import fit.hplus.bluetooth.util.HexUtil;
import fit.hplus.bluetooth.util.LogUtil;
import java.io.File;
import no.nordicsemi.android.dfu.DfuServiceInitiator;

public class DfuController implements IDfuController {
    private HPlusBleManager mBleProfileManager;
    private Context mContext;

    public DfuController(HPlusBleManager hPlusBleManager, Context context) {
        this.mContext = context;
        this.mBleProfileManager = hPlusBleManager;
    }

    public void getOTAFile(int i, int i2, DfuFileStatusCallback dfuFileStatusCallback) {
        new DownTxtTask(this.mContext, HexUtil.encodeHexStr(new byte[]{(byte) (i >> 8), (byte) (i & 255)}, false), i2, dfuFileStatusCallback).execute(new Void[0]);
    }

    public void startFirmwareUpgrade(String str) {
        BluetoothDevice bluetoothDevice = this.mBleProfileManager.getBluetoothDevice();
        if (bluetoothDevice == null || TextUtils.isEmpty(bluetoothDevice.getName())) {
            LogUtil.d("device is null");
            return;
        }
        boolean z = false;
        DfuServiceInitiator keepBond = new DfuServiceInitiator(bluetoothDevice.getAddress()).setDeviceName(bluetoothDevice.getName()).setForceDfu(false).setKeepBond(false);
        if (Build.VERSION.SDK_INT < 23) {
            z = true;
        }
        DfuServiceInitiator packetsReceiptNotificationsValue = keepBond.setPacketsReceiptNotificationsEnabled(z).setPacketsReceiptNotificationsValue(12);
        packetsReceiptNotificationsValue.setZip(Uri.fromFile(new File(str)), str);
        if (Build.VERSION.SDK_INT >= 26) {
            DfuServiceInitiator.createDfuNotificationChannel(this.mContext);
        }
        packetsReceiptNotificationsValue.start(this.mContext, DfuService.class);
    }
}
