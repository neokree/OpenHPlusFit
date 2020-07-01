package fit.hplus.bluetooth.callback;

import android.bluetooth.BluetoothDevice;
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;

public abstract class HPlusReadUICallback implements ProfileDataCallback {
    public /* synthetic */ void onInvalidDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        ProfileDataCallback.CC.$default$onInvalidDataReceived(this, bluetoothDevice, data);
    }
}
