package fit.hplus.bluetooth.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    public static final String PREF_NAME = "hPlus_device";
    public static final String PRE_ADDRESS = "pre_device_address";
    public static final String PRE_CLICK_DISCONNECT = "pre_disconnect";
    public static final String PRE_NAME = "pre_device_name";
    private static SPUtil mInstance;
    private Context mContext;
    private SharedPreferences mPreferences = this.mContext.getSharedPreferences(PREF_NAME, 0);

    private SPUtil(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SPUtil getInstance(Context context) {
        synchronized (SPUtil.class) {
            if (mInstance == null) {
                mInstance = new SPUtil(context);
            }
        }
        return mInstance;
    }

    public SPUtil save(String str, Object obj) {
        SharedPreferences.Editor edit = this.mPreferences.edit();
        if (obj instanceof String) {
            edit.putString(str, obj.toString());
        } else if (obj instanceof Boolean) {
            edit.putBoolean(str, Boolean.valueOf(obj.toString()).booleanValue());
        } else if (obj instanceof Long) {
            edit.putLong(str, Long.valueOf(obj.toString()).longValue());
        } else if (obj instanceof Float) {
            edit.putFloat(str, Float.valueOf(obj.toString()).floatValue());
        } else if (obj instanceof Integer) {
            edit.putInt(str, Integer.valueOf(obj.toString()).intValue());
        }
        edit.apply();
        return mInstance;
    }

    public Object get(String str, Object obj) {
        if (obj instanceof String) {
            return this.mPreferences.getString(str, (String) obj);
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(this.mPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(this.mPreferences.getLong(str, Long.parseLong(obj.toString())));
        }
        if (obj instanceof Float) {
            return Float.valueOf(this.mPreferences.getFloat(str, -1.0f));
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(this.mPreferences.getInt(str, Integer.parseInt(obj.toString())));
        }
        return null;
    }
}
