package fit.hplus.bluetooth.callback;

import android.bluetooth.BluetoothDevice;

public interface HPlusFitBluetoothListener {
    void connectFail(BluetoothDevice bluetoothDevice);

    void onBonded(BluetoothDevice bluetoothDevice);

    void onBondingFailed(BluetoothDevice bluetoothDevice);

    void onConnectSuccess(BluetoothDevice bluetoothDevice);

    void onConnecting(BluetoothDevice bluetoothDevice);

    void onDataUIWrite(String str);

    void onDataWrite(String str);

    void onDeviceNoSupport(BluetoothDevice bluetoothDevice);

    void onDeviceReady(BluetoothDevice bluetoothDevice);

    void onDisConnect(BluetoothDevice bluetoothDevice);

    void onDisConnecting(BluetoothDevice bluetoothDevice);

    void onServicesDiscovered(BluetoothDevice bluetoothDevice);
}
