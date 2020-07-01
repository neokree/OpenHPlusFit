package fit.hplus.bluetooth.dfu;

import android.app.Activity;
import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuService extends DfuBaseService {
    /* access modifiers changed from: protected */
    public Class<? extends Activity> getNotificationTarget() {
        return DfuNotificationActivity.class;
    }
}
