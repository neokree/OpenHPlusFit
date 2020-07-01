package fit.hplus.hplusfit.tool;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

public class SPUtils {
  public static final String DEVICE_ADDRESS = "device_address";
  public static final String DEVICE_BATTERY = "deviceBattery";
  public static final String DEVICE_INFO = "deviceInfo";
  public static final String DEVICE_NAME = "device_name";
  public static String FILL_NAME = "config";
  public static final String FIRMWARE_VERSION = "firmware_version";
  public static final String IS_BIND = "is_unbind";
  public static final String IS_DOWNLOAD_DATA = "is_download_data";
  public static final String IS_LOGIN = "is_login";
  public static final String LATITUDE = "latitude";
  public static final String LONGITUDE = "longitude";
  public static final String METRIC = "metric";
  public static final String NOTIFICATION_OPEN_APP = "notification_open_app";
  public static final String NOTIFICATION_SWITCH = "notification_switch_open";
  public static final String REAL_BLOOD_TIME = "real_blood_time";
  public static final String REAL_DATA = "real_data";
  public static final String REAL_DBP = "real_DBP";
  public static final String REAL_HEART = "real_heart";
  public static final String REAL_HEART_TIME = "real_heart_time";
  public static final String REAL_SBP = "real_SBP";
  public static final String REAL_STEP_INFO = "realStepInfo";
  public static final String REAL_THERMOMETER = "real_thermometer";
  public static final String REAL_THERMOMETER_TIME = "real_thermometer_time";
  public static final String REAL_TIME = "real_time";
  public static final String REQUEST_BIND = "request_bind";
  public static final String SERVER_APP_VERSION = "server_app_version";
  public static final String USER_NAME = "userName";
  public static final String USER_PASSWORD = "userPassword";
  public static final String USER_TOKEN = "userToken";
  public static final String WEATHER_ALTITUDE = "weather_altitude";
  public static final String WEATHER_DATE = "weather_date";
  public static final String WEATHER_INFO = "weather_info";
  public static final String WEATHER_TMEP = "weather_temp";

  public static void put(Context context, String str, Object obj) {
    SharedPreferences.Editor edit = context.getSharedPreferences(FILL_NAME, 0).edit();
    if (obj instanceof String) {
      edit.putString(str, (String) obj);
    } else if (obj instanceof Integer) {
      edit.putInt(str, ((Integer) obj).intValue());
    } else if (obj instanceof Boolean) {
      edit.putBoolean(str, ((Boolean) obj).booleanValue());
    } else if (obj instanceof Float) {
      edit.putFloat(str, ((Float) obj).floatValue());
    } else if (obj instanceof Long) {
      edit.putLong(str, ((Long) obj).longValue());
    }
    edit.apply();
  }

  public static void put(Context context, String str, String str2, Object obj) {
    SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
    if (obj instanceof String) {
      edit.putString(str2, (String) obj);
    } else if (obj instanceof Integer) {
      edit.putInt(str2, ((Integer) obj).intValue());
    } else if (obj instanceof Boolean) {
      edit.putBoolean(str2, ((Boolean) obj).booleanValue());
    } else if (obj instanceof Float) {
      edit.putFloat(str2, ((Float) obj).floatValue());
    } else if (obj instanceof Long) {
      edit.putLong(str2, ((Long) obj).longValue());
    }
    edit.apply();
  }

  public static Object get(Context context, String str, Object obj) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(FILL_NAME, 0);
    if (obj instanceof String) {
      return sharedPreferences.getString(str, (String) obj);
    }
    if (obj instanceof Integer) {
      return Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue()));
    }
    if (obj instanceof Boolean) {
      return Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
    }
    if (obj instanceof Float) {
      return Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue()));
    }
    if (obj instanceof Long) {
      return Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue()));
    }
    return null;
  }

  public static Object get(Context context, String str, String str2, Object obj) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
    if (obj instanceof String) {
      return sharedPreferences.getString(str2, (String) obj);
    }
    if (obj instanceof Integer) {
      return Integer.valueOf(sharedPreferences.getInt(str2, ((Integer) obj).intValue()));
    }
    if (obj instanceof Boolean) {
      return Boolean.valueOf(sharedPreferences.getBoolean(str2, ((Boolean) obj).booleanValue()));
    }
    if (obj instanceof Float) {
      return Float.valueOf(sharedPreferences.getFloat(str2, ((Float) obj).floatValue()));
    }
    if (obj instanceof Long) {
      return Long.valueOf(sharedPreferences.getLong(str2, ((Long) obj).longValue()));
    }
    return null;
  }

  public static Map<String, ?> getAll(Context context) {
    return context.getSharedPreferences(FILL_NAME, 0).getAll();
  }

  public static Map<String, ?> getAll(Context context, String str) {
    return context.getSharedPreferences(str, 0).getAll();
  }

  public static void remove(Context context, String str) {
    SharedPreferences.Editor edit = context.getSharedPreferences(FILL_NAME, 0).edit();
    edit.remove(str);
    edit.apply();
  }

  public static void remove(Context context, String str, String str2) {
    SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
    edit.remove(str2);
    edit.apply();
  }

  public static void clear(Context context) {
    SharedPreferences.Editor edit = context.getSharedPreferences(FILL_NAME, 0).edit();
    edit.clear();
    edit.apply();
  }

  public static void clear(Context context, String str) {
    SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
    edit.clear();
    edit.apply();
  }
}
