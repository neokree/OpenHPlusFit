package fit.hplus.bluetooth.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.internal.telephony.ITelephony;
import java.lang.reflect.Method;
import no.nordicsemi.android.log.LogContract;

public class PhoneUtils {
    static String TAG = "PhoneUtils";

    public static ITelephony getITelephony(TelephonyManager telephonyManager) throws Exception {
        Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("getITelephony", new Class[0]);
        declaredMethod.setAccessible(true);
        return (ITelephony) declaredMethod.invoke(telephonyManager, new Object[0]);
    }

    public static void autoAnswerPhone(Context context, TelephonyManager telephonyManager) {
        try {
            Log.i(TAG, "autoAnswerPhone");
            getITelephony(telephonyManager).answerRingingCall();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Log.e(TAG, "用于Android2.3及2.3以上的版本上");
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, 79));
                context.sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
                Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent2.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
                context.sendOrderedBroadcast(intent2, "android.permission.CALL_PRIVILEGED");
                Intent intent3 = new Intent("android.intent.action.HEADSET_PLUG");
                intent3.addFlags(1073741824);
                intent3.putExtra("state", 1);
                intent3.putExtra("microphone", 1);
                intent3.putExtra(LogContract.SessionColumns.NAME, "Headset");
                context.sendOrderedBroadcast(intent3, "android.permission.CALL_PRIVILEGED");
                Intent intent4 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent4.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, 79));
                context.sendOrderedBroadcast(intent4, "android.permission.CALL_PRIVILEGED");
                Intent intent5 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent5.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
                context.sendOrderedBroadcast(intent5, "android.permission.CALL_PRIVILEGED");
                Intent intent6 = new Intent("android.intent.action.HEADSET_PLUG");
                intent6.addFlags(1073741824);
                intent6.putExtra("state", 0);
                intent6.putExtra("microphone", 1);
                intent6.putExtra(LogContract.SessionColumns.NAME, "Headset");
                context.sendOrderedBroadcast(intent6, "android.permission.CALL_PRIVILEGED");
            } catch (Exception e2) {
                e2.printStackTrace();
                Intent intent7 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent7.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
                context.sendOrderedBroadcast(intent7, (String) null);
            }
        }
    }

    public static void endPhone(Context context, TelephonyManager telephonyManager) {
        try {
            Log.i(TAG, "endPhone");
            Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
            declaredMethod.setAccessible(true);
            ((ITelephony) declaredMethod.invoke(telephonyManager, (Object[]) null)).endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void myendcall(Context context) {
        if (Build.VERSION.SDK_INT < 28) {
            try {
                ITelephony.Stub.asInterface((IBinder) Class.forName("android.os.ServiceManager").getMethod("getService", new Class[]{String.class}).invoke((Object) null, new Object[]{"phone"})).endCall();
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "", e);
            } catch (ClassNotFoundException e2) {
                Log.d(TAG, "", e2);
            } catch (Exception unused) {
            }
        } else {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
            if (telecomManager != null) {
                telecomManager.endCall();
            }
        }
    }
}
