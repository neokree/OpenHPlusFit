package fit.hplus.bluetooth.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import fit.hplus.bluetooth.HPlusBleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

public class ScannerController implements IScannerController {
    public static final int LOW_SCAN = 2;
    public static final int SCAN_FAIL = 3;
    private BleLowScanCallback bleLowScanCallback;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                if (ScannerController.this.mList != null && ScannerController.this.mList.size() > 0) {
                    for (ScannerListener onScanner : ScannerController.this.mList) {
                        onScanner.onScanner(bluetoothDevice);
                    }
                }
            } else if (i == 3 && ScannerController.this.mList != null && ScannerController.this.mList.size() > 0) {
                for (ScannerListener onScanFail : ScannerController.this.mList) {
                    onScanFail.onScanFail();
                }
            }
        }
    };
    private BluetoothAdapter mBluetoothAdapter;
    /* access modifiers changed from: private */
    public List<ScannerListener> mList;

    public void init(BluetoothAdapter bluetoothAdapter) {
        this.mBluetoothAdapter = bluetoothAdapter;
        this.mList = new CopyOnWriteArrayList();
    }

    public void destroy() {
        this.handler.removeCallbacksAndMessages((Object) null);
        this.mList.clear();
        this.mList = null;
    }

    public void startScan() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            this.handler.sendEmptyMessage(3);
        } else if (!bluetoothAdapter.isEnabled()) {
            this.handler.sendEmptyMessage(3);
        } else {
            this.bleLowScanCallback = BleLowScanCallback.newInstance(this.handler);
            ScanSettings build = new ScanSettings.Builder().setScanMode(2).setReportDelay(300).setUseHardwareBatchingIfSupported(false).build();
            BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(HPlusBleManager.UART_SERVICE_UUID.toString())).build());
            scanner.startScan(arrayList, build, this.bleLowScanCallback);
        }
    }

    public void stopScan() {
        if (this.bleLowScanCallback != null) {
            BluetoothLeScannerCompat.getScanner().stopScan(this.bleLowScanCallback);
        }
    }

    public void addScanListener(ScannerListener scannerListener) {
        if (!this.mList.contains(scannerListener)) {
            this.mList.add(scannerListener);
        }
    }

    public void removeScanListener(ScannerListener scannerListener) {
        if (this.mList.contains(scannerListener)) {
            this.mList.remove(scannerListener);
        }
    }
}
