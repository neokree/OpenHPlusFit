package fit.hplus.bluetooth;

import fit.hplus.bluetooth.bean.AlarmClockBean;
import fit.hplus.bluetooth.bean.DeviceSettingBean;
import fit.hplus.bluetooth.bean.WeatherForecast;
import java.util.List;

public interface IBleSend {
    void clearHistoryData();

    void exitCamera();

    void getDeviceVersionCommand();

    void getECGCommand();

    void getForgingData();

    void getForgingStatus(int i);

    void getHRVCommand();

    void getSleepChart();

    void getSleepDataCommand();

    void getStepDataCommand();

    void getTenMinuteDataCommand(int i, int i2, int i3, int i4);

    void sendAllDataCommand(DeviceSettingBean deviceSettingBean);

    void sendAllDayTenMinuteDataCommand();

    void sendCallRemindCommand();

    void sendCallRemindCommand(String str);

    void sendDateCommand();

    void sendDownloadRawData(List<String> list);

    void sendDownloadRawDataServerStatus(int i);

    void sendHeartAllDay(boolean z);

    void sendLanguageCommand();

    void sendMetricCommand(boolean z);

    void sendNotificationData(String str);

    void sendNotificationTypeCommand(int i);

    void sendOpenRealHeartCommand(int i, int i2);

    void sendOpenRealHeartCommand(boolean z);

    void sendRestartDeviceCommand();

    void sendSedentaryCommand(boolean z, int i, int i2, int i3, int i4, int i5);

    void sendShutdownDeviceCommand();

    void sendSmsRemindCommand();

    void sendSmsRemindCommand(String str);

    void sendTimeCommand();

    void sendTimeSystemCommand(boolean z);

    void sendTurnWristScreenCommand(boolean z);

    void sendUploadRawDataResponse(int i, int i2);

    void sendUploadRawDataServerStatus(int i);

    void sendWeatherForecast(WeatherForecast weatherForecast);

    void sendWeekCommand(int i);

    void setAgeCommand(int i);

    void setAlarmClock(int i, int i2);

    void setAlarmClock(List<AlarmClockBean> list);

    void setBeat(int i);

    void setForgingGoalCommand(int i, int i2, int i3, int i4, int i5, int i6);

    void setHeightCommand(int i);

    void setPhoneType(boolean z);

    void setScreenSaveCommand(int i);

    void setSexCommand(boolean z);

    void setSleepTime(int i, int i2, int i3, int i4);

    void setStepGoalCommand(int i);

    void setWeekCommand(int i);

    void setWeightCommand(int i);
}
