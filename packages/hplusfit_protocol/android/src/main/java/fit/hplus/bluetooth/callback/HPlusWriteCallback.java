package fit.hplus.bluetooth.callback;

import android.bluetooth.BluetoothDevice;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;

public abstract class HPlusWriteCallback implements ProfileDataCallback, DataSentCallback {
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
    }
}
