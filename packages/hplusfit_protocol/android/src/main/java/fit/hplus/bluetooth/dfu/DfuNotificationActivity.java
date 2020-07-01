package fit.hplus.bluetooth.dfu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public abstract class DfuNotificationActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public abstract void onCreateActivity(Bundle bundle);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        onCreateActivity(bundle);
    }
}
