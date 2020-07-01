package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceSettingBean implements Parcelable {
    public static final Parcelable.Creator<DeviceSettingBean> CREATOR = new Parcelable.Creator<DeviceSettingBean>() {
        public DeviceSettingBean createFromParcel(Parcel parcel) {
            return new DeviceSettingBean(parcel);
        }

        public DeviceSettingBean[] newArray(int i) {
            return new DeviceSettingBean[i];
        }
    };
    private int age;
    private int alarmClockStartHour;
    private int alarmClockStartMinute;
    private boolean allDayHeartSwitch;
    private int day;
    private int height;
    private int hour;
    private boolean is24Hour;
    private boolean isMale;
    private boolean isMetric;
    private int minute;
    private int month;
    private int screenTime;
    private int second;
    private int sedentaryEndHour;
    private int sedentaryEndMinute;
    private int sedentaryStartHour;
    private int sedentaryStartMinute;
    private boolean sedentarySwitch;
    private boolean smsSwitch;
    private int stepGoal;
    private boolean turnWristScreenSwitch;
    private int weight;
    private int year;

    public int describeContents() {
        return 0;
    }

    public boolean isMale() {
        return this.isMale;
    }

    public void setMale(boolean z) {
        this.isMale = z;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int i) {
        this.weight = i;
    }

    public int getStepGoal() {
        return this.stepGoal;
    }

    public void setStepGoal(int i) {
        this.stepGoal = i;
    }

    public int getScreenTime() {
        return this.screenTime;
    }

    public void setScreenTime(int i) {
        this.screenTime = i;
    }

    public boolean isSmsSwitch() {
        return this.smsSwitch;
    }

    public void setSmsSwitch(boolean z) {
        this.smsSwitch = z;
    }

    public boolean isAllDayHeartSwitch() {
        return this.allDayHeartSwitch;
    }

    public void setAllDayHeartSwitch(boolean z) {
        this.allDayHeartSwitch = z;
    }

    public boolean isTurnWristScreenSwitch() {
        return this.turnWristScreenSwitch;
    }

    public void setTurnWristScreenSwitch(boolean z) {
        this.turnWristScreenSwitch = z;
    }

    public int getAlarmClockStartHour() {
        return this.alarmClockStartHour;
    }

    public void setAlarmClockStartHour(int i) {
        this.alarmClockStartHour = i;
    }

    public int getAlarmClockStartMinute() {
        return this.alarmClockStartMinute;
    }

    public void setAlarmClockStartMinute(int i) {
        this.alarmClockStartMinute = i;
    }

    public boolean isMetric() {
        return this.isMetric;
    }

    public void setMetric(boolean z) {
        this.isMetric = z;
    }

    public boolean isIs24Hour() {
        return this.is24Hour;
    }

    public void setIs24Hour(boolean z) {
        this.is24Hour = z;
    }

    public boolean isSedentarySwitch() {
        return this.sedentarySwitch;
    }

    public void setSedentarySwitch(boolean z) {
        this.sedentarySwitch = z;
    }

    public int getSedentaryStartHour() {
        return this.sedentaryStartHour;
    }

    public void setSedentaryStartHour(int i) {
        this.sedentaryStartHour = i;
    }

    public int getSedentaryStartMinute() {
        return this.sedentaryStartMinute;
    }

    public void setSedentaryStartMinute(int i) {
        this.sedentaryStartMinute = i;
    }

    public int getSedentaryEndHour() {
        return this.sedentaryEndHour;
    }

    public void setSedentaryEndHour(int i) {
        this.sedentaryEndHour = i;
    }

    public int getSedentaryEndMinute() {
        return this.sedentaryEndMinute;
    }

    public void setSedentaryEndMinute(int i) {
        this.sedentaryEndMinute = i;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int i) {
        this.year = i;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int i) {
        this.month = i;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int i) {
        this.day = i;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = i;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int i) {
        this.minute = i;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int i) {
        this.second = i;
    }

    protected DeviceSettingBean(Parcel parcel) {
        boolean z = true;
        this.isMale = parcel.readByte() != 0;
        this.age = parcel.readInt();
        this.height = parcel.readInt();
        this.weight = parcel.readInt();
        this.stepGoal = parcel.readInt();
        this.screenTime = parcel.readInt();
        this.smsSwitch = parcel.readByte() != 0;
        this.allDayHeartSwitch = parcel.readByte() != 0;
        this.turnWristScreenSwitch = parcel.readByte() != 0;
        this.alarmClockStartHour = parcel.readInt();
        this.alarmClockStartMinute = parcel.readInt();
        this.isMetric = parcel.readByte() != 0;
        this.is24Hour = parcel.readByte() != 0;
        this.sedentarySwitch = parcel.readByte() == 0 ? false : z;
        this.sedentaryStartHour = parcel.readInt();
        this.sedentaryStartMinute = parcel.readInt();
        this.sedentaryEndHour = parcel.readInt();
        this.sedentaryEndMinute = parcel.readInt();
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.isMale ? (byte) 1 : 0);
        parcel.writeInt(this.age);
        parcel.writeInt(this.height);
        parcel.writeInt(this.weight);
        parcel.writeInt(this.stepGoal);
        parcel.writeInt(this.screenTime);
        parcel.writeByte(this.smsSwitch ? (byte) 1 : 0);
        parcel.writeByte(this.allDayHeartSwitch ? (byte) 1 : 0);
        parcel.writeByte(this.turnWristScreenSwitch ? (byte) 1 : 0);
        parcel.writeInt(this.alarmClockStartHour);
        parcel.writeInt(this.alarmClockStartMinute);
        parcel.writeByte(this.isMetric ? (byte) 1 : 0);
        parcel.writeByte(this.is24Hour ? (byte) 1 : 0);
        parcel.writeByte(this.sedentarySwitch ? (byte) 1 : 0);
        parcel.writeInt(this.sedentaryStartHour);
        parcel.writeInt(this.sedentaryStartMinute);
        parcel.writeInt(this.sedentaryEndHour);
        parcel.writeInt(this.sedentaryEndMinute);
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
    }
}
