package fit.hplus.bluetooth;

import android.os.Build;
import android.text.TextUtils;
import com.amap.api.maps.AMap;
import fit.hplus.bluetooth.bean.AlarmClockBean;
import fit.hplus.bluetooth.bean.DeviceSettingBean;
import fit.hplus.bluetooth.bean.WeatherForecast;
import fit.hplus.bluetooth.util.HexUtil;
import it.neokree.hplusfitprotocol.Framer;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BleSend implements IBleSend {
    private HPlusBleManager mHPlusBleManager;

    public void setForgingGoalCommand(int i, int i2, int i3, int i4, int i5, int i6) {
        byte[] bArr = new byte[20];
        bArr[0] = 87;
        bArr[1] = (byte) ((i >> 8) & 255);
        bArr[2] = (byte) (i & 255);
        bArr[3] = (byte) ((i2 >> 8) & 255);
        bArr[4] = (byte) (i2 & 255);
        bArr[5] = (byte) ((i3 >> 8) & 255);
        bArr[6] = (byte) (i3 & 255);
        bArr[7] = (byte) ((i4 >> 8) & 255);
        bArr[8] = (byte) (i4 & 255);
        bArr[9] = (byte) i5;
        bArr[10] = (byte) ((i6 >> 8) & 255);
        bArr[11] = (byte) (i6 & 255);
    }

    public BleSend(HPlusBleManager hPlusBleManager) {
        this.mHPlusBleManager = hPlusBleManager;
    }

    public void sendDateCommand() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        this.mHPlusBleManager.sendDataWrite(new byte[]{8, (byte) (i >> 8), (byte) (i & 255), (byte) (instance.get(2) + 1), (byte) instance.get(5)});
    }

    public void sendTimeCommand() {
        Calendar instance = Calendar.getInstance();
        this.mHPlusBleManager.sendDataWrite(new byte[]{9, (byte) instance.get(11), (byte) instance.get(12), (byte) instance.get(13)});
    }

    public void sendWeekCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{42, (byte) i});
    }

    public void setHeightCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{4, (byte) i});
    }

    public void setWeightCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{5, (byte) i});
    }

    public void setAgeCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{44, (byte) i});
    }

    public void setSexCommand(boolean z) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{Framer.STDIN_FRAME_PREFIX, z ^ true ? (byte) 1 : 0});
    }

    public void setScreenSaveCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{11, (byte) i});
    }

    public void getSleepDataCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{25});
    }

    public void getStepDataCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{21});
    }

    public void getDeviceVersionCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{23});
    }

    public void sendNotificationTypeCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{Framer.STDOUT_FRAME_PREFIX, (byte) i});
    }

    public void sendCallRemindCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{6, -86});
    }

    public void sendCallRemindCommand(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                byte[] bytes = str.getBytes(Build.VERSION.SDK_INT >= 27 ? "UnicodeLittleUnmarked" : "Unicode");
                for (int i = 0; i < bytes.length; i += 2) {
                    byte b = bytes[i];
                    int i2 = i + 1;
                    bytes[i] = (byte) bytes[i2];
                    bytes[i2] = (byte) b;
                }
                if (bytes.length > 17) {
                    int length = bytes.length / 17;
                    int length2 = bytes.length % 17;
                    for (int i3 = 1; i3 <= length; i3++) {
                        byte[] bArr = new byte[20];
                        bArr[0] = 98;
                        bArr[1] = (byte) (length + 1);
                        bArr[2] = (byte) i3;
                        System.arraycopy(bytes, (i3 - 1) * 17, bArr, 3, 17);
                        this.mHPlusBleManager.sendDataWrite(bArr);
                    }
                    byte[] bArr2 = new byte[20];
                    bArr2[0] = 98;
                    byte b2 = (byte) (length + 1);
                    bArr2[1] = b2;
                    bArr2[2] = b2;
                    System.arraycopy(bytes, length * 17, bArr2, 3, length2);
                    this.mHPlusBleManager.sendDataWrite(bArr2);
                    return;
                }
                byte[] bArr3 = new byte[20];
                bArr3[0] = 98;
                bArr3[1] = 1;
                bArr3[2] = 1;
                System.arraycopy(bytes, 0, bArr3, 3, bytes.length);
                this.mHPlusBleManager.sendDataWrite(bArr3);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendSmsRemindCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{7, -86});
    }

    public void sendSmsRemindCommand(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                byte[] bytes = str.getBytes(Build.VERSION.SDK_INT >= 27 ? "UnicodeLittleUnmarked" : "Unicode");
                for (int i = 0; i < bytes.length; i += 2) {
                    byte b = bytes[i];
                    int i2 = i + 1;
                    bytes[i] = (byte) bytes[i2];
                    bytes[i2] = (byte) b;
                }
                if (bytes.length > 17) {
                    int length = bytes.length / 17;
                    int length2 = bytes.length % 17;
                    for (int i3 = 1; i3 <= length; i3++) {
                        byte[] bArr = new byte[20];
                        bArr[0] = 65;
                        bArr[1] = (byte) (length + 1);
                        bArr[2] = (byte) i3;
                        System.arraycopy(bytes, (i3 - 1) * 17, bArr, 3, 17);
                        this.mHPlusBleManager.sendDataWrite(bArr);
                    }
                    byte[] bArr2 = new byte[20];
                    bArr2[0] = 65;
                    byte b2 = (byte) (length + 1);
                    bArr2[1] = b2;
                    bArr2[2] = b2;
                    System.arraycopy(bytes, length * 17, bArr2, 3, length2);
                    this.mHPlusBleManager.sendDataWrite(bArr2);
                    return;
                }
                byte[] bArr3 = new byte[20];
                bArr3[0] = 65;
                bArr3[1] = 1;
                bArr3[2] = 1;
                System.arraycopy(bytes, 0, bArr3, 3, bytes.length);
                this.mHPlusBleManager.sendDataWrite(bArr3);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendHeartAllDay(boolean z) {
        byte[] bArr = new byte[2];
        bArr[0] = 53;
        bArr[1] = (byte) (z ? 10 : 255);
        this.mHPlusBleManager.sendDataWrite(bArr);
    }

    public void sendSedentaryCommand(boolean z, int i, int i2, int i3, int i4, int i5) {
        byte[] bArr = new byte[6];
        bArr[0] = 30;
        if (!z) {
            bArr[1] = 0;
        } else if (i == 45) {
            bArr[1] = 1;
        } else if (i == 60) {
            bArr[1] = 2;
        } else if (i == 120) {
            bArr[1] = 3;
        } else if (i == 180) {
            bArr[1] = 4;
        } else if (i == 240) {
            bArr[1] = 5;
        } else {
            bArr[1] = 1;
        }
        bArr[2] = (byte) i2;
        bArr[3] = (byte) i3;
        bArr[4] = (byte) i4;
        bArr[5] = (byte) i5;
        this.mHPlusBleManager.sendDataWrite(bArr);
    }

    public void clearHistoryData() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{-74, 90});
    }

    public void sendRestartDeviceCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{-91, 90});
    }

    public void sendShutdownDeviceCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{91, 90});
    }

    public void sendOpenRealHeartCommand(boolean z) {
        byte[] bArr = new byte[2];
        bArr[0] = Framer.STDERR_FRAME_PREFIX;
        bArr[1] = (byte) (z ? 11 : 22);
        this.mHPlusBleManager.sendDataWrite(bArr);
    }

    public void sendAllDayTenMinuteDataCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{82});
    }

    public void getTenMinuteDataCommand(int i, int i2, int i3, int i4) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{39, (byte) i, (byte) i2, (byte) i3, (byte) i4});
    }

    public void sendTurnWristScreenCommand(boolean z) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{58, z ? (byte) 1 : 0});
    }

    public void sendMetricCommand(boolean z) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{72, z ^ true ? (byte) 1 : 0});
    }

    public void sendTimeSystemCommand(boolean z) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{71, z ? (byte) 1 : 0});
    }

    public void setWeekCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{42, (byte) i});
    }

    public void setStepGoalCommand(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{38, (byte) ((i >> 8) & 255), (byte) (i & 255)});
    }

    public void sendLanguageCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{34, (byte) getLanguageMode()});
    }

    public void setSleepTime(int i, int i2, int i3, int i4) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{88, (byte) i, (byte) i2, (byte) i3, (byte) i4});
    }

    public void getSleepChart() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{89});
    }

    public void setPhoneType(boolean z) {
        byte[] bArr = new byte[2];
        bArr[0] = 79;
        bArr[1] = (byte) (z ? 90 : 80);
        this.mHPlusBleManager.sendDataWrite(bArr);
    }

    public void getForgingStatus(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{64, (byte) i});
    }

    public void getForgingData() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{77});
    }

    public void setBeat(int i) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{51, (byte) i});
    }

    public void setAlarmClock(List<AlarmClockBean> list) {
        String str;
        byte[] bArr = new byte[19];
        bArr[0] = 52;
        int size = list.size();
        if (list.size() > 6) {
            size = 6;
        }
        if (size > 0) {
            for (int i = 0; i < list.size(); i++) {
                AlarmClockBean alarmClockBean = list.get(i);
                if ((alarmClockBean.isOpenFriday() || alarmClockBean.isOpenMonday() || alarmClockBean.isOpenSaturday() || alarmClockBean.isOpenSunday() || alarmClockBean.isOpenThursday() || alarmClockBean.isOpenTuesday() || alarmClockBean.isOpenWednesday()) && alarmClockBean.isOpen()) {
                    char[] cArr = new char[8];
                    cArr[0] = '1';
                    if (alarmClockBean.isOpenSaturday()) {
                        cArr[1] = '1';
                    } else {
                        cArr[1] = '0';
                    }
                    if (alarmClockBean.isOpenFriday()) {
                        cArr[2] = '1';
                    } else {
                        cArr[2] = '0';
                    }
                    if (alarmClockBean.isOpenThursday()) {
                        cArr[3] = '1';
                    } else {
                        cArr[3] = '0';
                    }
                    if (alarmClockBean.isOpenWednesday()) {
                        cArr[4] = '1';
                    } else {
                        cArr[4] = '0';
                    }
                    if (alarmClockBean.isOpenTuesday()) {
                        cArr[5] = '1';
                    } else {
                        cArr[5] = '0';
                    }
                    if (alarmClockBean.isOpenMonday()) {
                        cArr[6] = '1';
                    } else {
                        cArr[6] = '0';
                    }
                    if (alarmClockBean.isOpenSunday()) {
                        cArr[7] = '1';
                    } else {
                        cArr[7] = '0';
                    }
                    str = String.valueOf(cArr);
                } else {
                    str = "00000000";
                }
                int i2 = i * 3;
                bArr[i2 + 1] = HexUtil.getFbyte(str);
                bArr[i2 + 2] = (byte) alarmClockBean.getHour();
                bArr[i2 + 3] = (byte) alarmClockBean.getMinute();
            }
        }
        this.mHPlusBleManager.sendDataWrite(bArr);
    }

    public void sendWeatherForecast(WeatherForecast weatherForecast) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{Framer.STDIN_REQUEST_FRAME_PREFIX, (byte) ((weatherForecast.getWeatherCode() >> 8) & 255), (byte) (weatherForecast.getWeatherCode() & 255), (byte) weatherForecast.getWindDirection(), (byte) weatherForecast.getWinPower(), (byte) ((weatherForecast.getWindSpeed() >> 8) & 255), (byte) (weatherForecast.getWindSpeed() & 255), (byte) weatherForecast.getCurrentTemperature(), (byte) weatherForecast.getMaxTemperature(), (byte) weatherForecast.getMinTemperature(), (byte) weatherForecast.getLifeIndex(), (byte) (weatherForecast.getPressure() & 255), (byte) ((weatherForecast.getPressure() >> 8) & 255), (byte) ((weatherForecast.getPressure() >> 16) & 255), (byte) ((weatherForecast.getPressure() >> 24) & 255), (byte) (weatherForecast.getAltitude() & 255), (byte) ((weatherForecast.getAltitude() >> 8) & 255), (byte) ((weatherForecast.getAltitude() >> 16) & 255), (byte) ((weatherForecast.getAltitude() >> 24) & 255), (byte) weatherForecast.getUltravioletLight()});
    }

    public void sendNotificationData(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                byte[] bytes = str.getBytes(Build.VERSION.SDK_INT >= 27 ? "UnicodeLittleUnmarked" : "Unicode");
                for (int i = 0; i < bytes.length; i += 2) {
                    byte b = bytes[i];
                    int i2 = i + 1;
                    bytes[i] = (byte) bytes[i2];
                    bytes[i2] = (byte) b;
                }
                if (bytes.length > 17) {
                    int length = bytes.length / 17;
                    int length2 = bytes.length % 17;
                    for (int i3 = 1; i3 <= length; i3++) {
                        byte[] bArr = new byte[20];
                        bArr[0] = 67;
                        bArr[1] = (byte) (length + 1);
                        bArr[2] = (byte) i3;
                        System.arraycopy(bytes, (i3 - 1) * 17, bArr, 3, 17);
                        this.mHPlusBleManager.sendDataWrite(bArr);
                    }
                    byte[] bArr2 = new byte[20];
                    bArr2[0] = 67;
                    byte b2 = (byte) (length + 1);
                    bArr2[1] = b2;
                    bArr2[2] = b2;
                    System.arraycopy(bytes, length * 17, bArr2, 3, length2);
                    this.mHPlusBleManager.sendDataWrite(bArr2);
                    return;
                }
                byte[] bArr3 = new byte[20];
                bArr3[0] = 67;
                bArr3[1] = 1;
                bArr3[2] = 1;
                System.arraycopy(bytes, 0, bArr3, 3, bytes.length);
                this.mHPlusBleManager.sendDataWrite(bArr3);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOpenRealHeartCommand(int i, int i2) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{93, (byte) i, (byte) i2});
    }

    public void setAlarmClock(int i, int i2) {
        this.mHPlusBleManager.sendDataWrite(new byte[]{12, (byte) i, (byte) i2});
    }

    public void sendAllDataCommand(DeviceSettingBean deviceSettingBean) {
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        bArr[0] = 80;
        bArr[1] = deviceSettingBean.isMale() ^ true ? (byte) 1 : 0;
        bArr[2] = (byte) deviceSettingBean.getAge();
        bArr[3] = (byte) deviceSettingBean.getHeight();
        bArr[4] = (byte) deviceSettingBean.getWeight();
        bArr[5] = (byte) (deviceSettingBean.getStepGoal() >> 24);
        bArr[6] = (byte) ((deviceSettingBean.getStepGoal() >> 16) & 255);
        bArr[7] = (byte) ((deviceSettingBean.getStepGoal() >> 8) & 255);
        bArr[8] = (byte) (deviceSettingBean.getStepGoal() & 255);
        bArr[9] = (byte) deviceSettingBean.getScreenTime();
        bArr[10] = (byte) getLanguageMode();
        bArr[12] = deviceSettingBean.isSmsSwitch() ? (byte) 1 : 0;
        bArr[13] = deviceSettingBean.isAllDayHeartSwitch() ? (byte) 1 : 0;
        bArr[14] = deviceSettingBean.isTurnWristScreenSwitch() ? (byte) 1 : 0;
        bArr[16] = (byte) deviceSettingBean.getAlarmClockStartHour();
        bArr[17] = (byte) deviceSettingBean.getAlarmClockStartMinute();
        bArr[18] = deviceSettingBean.isMetric() ^ true ? (byte) 1 : 0;
        bArr[19] = deviceSettingBean.isIs24Hour() ? (byte) 1 : 0;
        this.mHPlusBleManager.sendDataWrite(bArr);
        bArr2[0] = 81;
        bArr2[1] = deviceSettingBean.isSedentarySwitch() ? (byte) 1 : 0;
        bArr2[2] = (byte) deviceSettingBean.getSedentaryStartHour();
        bArr2[3] = (byte) deviceSettingBean.getSedentaryStartMinute();
        bArr2[4] = (byte) deviceSettingBean.getSedentaryEndHour();
        bArr2[5] = (byte) deviceSettingBean.getSedentaryEndMinute();
        bArr2[9] = (byte) ((deviceSettingBean.getYear() >> 8) & 255);
        bArr2[10] = (byte) (deviceSettingBean.getYear() & 255);
        bArr2[11] = (byte) deviceSettingBean.getMonth();
        bArr2[12] = (byte) deviceSettingBean.getDay();
        bArr2[13] = (byte) deviceSettingBean.getHour();
        bArr2[14] = (byte) deviceSettingBean.getMinute();
        bArr2[15] = (byte) deviceSettingBean.getSecond();
        this.mHPlusBleManager.sendDataWrite(bArr2);
    }

    public void getHRVCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{-7, 1});
    }

    public void getECGCommand() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{-7, 2});
    }

    public void sendUploadRawDataResponse(int i, int i2) {
        this.mHPlusBleManager.sendUIDataWrite(new byte[]{-8, (byte) i, (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
    }

    public void sendUploadRawDataServerStatus(int i) {
        this.mHPlusBleManager.sendUIDataWrite(new byte[]{-8, -1, 0, (byte) i});
    }

    public void sendDownloadRawData(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            byte[] hexStringToBytes = HexUtil.hexStringToBytes(list.get(i));
            if (hexStringToBytes != null) {
                this.mHPlusBleManager.sendUIData(hexStringToBytes);
            }
        }
    }

    public void sendDownloadRawDataServerStatus(int i) {
        this.mHPlusBleManager.sendUIDataWrite(new byte[]{-7, -1, 0, (byte) i});
    }

    public void exitCamera() {
        this.mHPlusBleManager.sendDataWrite(new byte[]{101, 48});
    }

    private int getLanguageMode() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (language.equals("zh")) {
            return (country.equalsIgnoreCase("TW") || country.equalsIgnoreCase("HK") || country.equalsIgnoreCase("MO")) ? 5 : 1;
        }
        if (language.equals(AMap.ENGLISH)) {
            return 2;
        }
        if (language.equals("fr")) {
            return 3;
        }
        if (language.equals("es")) {
            return 4;
        }
        if (language.equals("pl")) {
            return 6;
        }
        if (language.equals("ru")) {
            return 7;
        }
        if (language.equals("ja")) {
            return 8;
        }
        if (language.equals("ko")) {
            return 9;
        }
        if (language.equals("pt")) {
            return 10;
        }
        if (language.equals("de")) {
            return 11;
        }
        if (language.equals("it")) {
            return 12;
        }
        if (language.equals("cs")) {
            return 13;
        }
        if (language.equals("th")) {
            return 14;
        }
        if (language.equals("ar")) {
            return 15;
        }
        return 2;
    }
}
