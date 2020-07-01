package fit.hplus.bluetooth.scanner;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import java.util.List;
import java.util.UUID;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class BleLowScanCallback extends ScanCallback {
    private static BleLowScanCallback scanCallback;
    private static final UUID uuid = UUID.fromString("14701820-620a-3973-7c78-9cfff0876abd");
    private Handler mHandler;

    public BleLowScanCallback(Handler handler) {
        this.mHandler = handler;
    }

    public static BleLowScanCallback newInstance(Handler handler) {
        if (scanCallback == null) {
            synchronized (BleLowScanCallback.class) {
                if (scanCallback == null) {
                    scanCallback = new BleLowScanCallback(handler);
                }
            }
        }
        return scanCallback;
    }

    public void onScanResult(int i, ScanResult scanResult) {
        super.onScanResult(i, scanResult);
        if (!TextUtils.isEmpty(scanResult.getDevice().getName()) && !TextUtils.isEmpty(scanResult.getDevice().getAddress())) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = scanResult.getDevice();
            this.mHandler.sendMessage(obtain);
        }
    }

    public void onBatchScanResults(List<ScanResult> list) {
        super.onBatchScanResults(list);
        for (ScanResult next : list) {
            if (!TextUtils.isEmpty(next.getDevice().getName()) && !TextUtils.isEmpty(next.getDevice().getAddress())) {
                Message obtain = Message.obtain();
                obtain.what = 2;
                obtain.obj = next.getDevice();
                this.mHandler.sendMessage(obtain);
            }
        }
    }

    public void onScanFailed(int i) {
        super.onScanFailed(i);
    }
}
