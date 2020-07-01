package fit.hplus.bluetooth.scanner;

import android.bluetooth.BluetoothDevice;

public interface ScannerListener {
    void onScanFail();

    void onScanner(BluetoothDevice bluetoothDevice);
}
