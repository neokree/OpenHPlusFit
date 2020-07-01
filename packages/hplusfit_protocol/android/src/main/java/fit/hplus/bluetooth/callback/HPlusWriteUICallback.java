package fit.hplus.bluetooth.callback;

import android.bluetooth.BluetoothDevice;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;

public abstract class HPlusWriteUICallback implements ProfileDataCallback, DataSentCallback {
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
    }

    public void onDataSent(BluetoothDevice bluetoothDevice, Data data) {
    }

    public void onInvalidDataReceived(BluetoothDevice bluetoothDevice, Data data) {
    }
}
