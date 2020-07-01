package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmClockBean implements Parcelable {
    public static final Parcelable.Creator<AlarmClockBean> CREATOR = new Parcelable.Creator<AlarmClockBean>() {
        public AlarmClockBean createFromParcel(Parcel parcel) {
            return new AlarmClockBean(parcel);
        }

        public AlarmClockBean[] newArray(int i) {
            return new AlarmClockBean[i];
        }
    };
    private int hour;
    private int minute;
    private boolean open;
    private boolean openFriday;
    private boolean openMonday;
    private boolean openSaturday;
    private boolean openSunday;
    private boolean openThursday;
    private boolean openTuesday;
    private boolean openWednesday;

    public int describeContents() {
        return 0;
    }

    public AlarmClockBean() {
    }

    public AlarmClockBean(boolean z, int i, int i2, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        this.open = z;
        this.hour = i;
        this.minute = i2;
        this.openSaturday = z2;
        this.openSunday = z3;
        this.openFriday = z4;
        this.openThursday = z5;
        this.openWednesday = z6;
        this.openTuesday = z7;
        this.openMonday = z8;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean z) {
        this.open = z;
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

    public boolean isOpenSaturday() {
        return this.openSaturday;
    }

    public void setOpenSaturday(boolean z) {
        this.openSaturday = z;
    }

    public boolean isOpenSunday() {
        return this.openSunday;
    }

    public void setOpenSunday(boolean z) {
        this.openSunday = z;
    }

    public boolean isOpenFriday() {
        return this.openFriday;
    }

    public void setOpenFriday(boolean z) {
        this.openFriday = z;
    }

    public boolean isOpenThursday() {
        return this.openThursday;
    }

    public void setOpenThursday(boolean z) {
        this.openThursday = z;
    }

    public boolean isOpenWednesday() {
        return this.openWednesday;
    }

    public void setOpenWednesday(boolean z) {
        this.openWednesday = z;
    }

    public boolean isOpenTuesday() {
        return this.openTuesday;
    }

    public void setOpenTuesday(boolean z) {
        this.openTuesday = z;
    }

    public boolean isOpenMonday() {
        return this.openMonday;
    }

    public void setOpenMonday(boolean z) {
        this.openMonday = z;
    }

    protected AlarmClockBean(Parcel parcel) {
        boolean z = true;
        this.open = parcel.readByte() != 0;
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.openSaturday = parcel.readByte() != 0;
        this.openSunday = parcel.readByte() != 0;
        this.openFriday = parcel.readByte() != 0;
        this.openThursday = parcel.readByte() != 0;
        this.openWednesday = parcel.readByte() != 0;
        this.openTuesday = parcel.readByte() != 0;
        this.openMonday = parcel.readByte() == 0 ? false : z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.open ? (byte) 1 : 0);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeByte(this.openSaturday ? (byte) 1 : 0);
        parcel.writeByte(this.openSunday ? (byte) 1 : 0);
        parcel.writeByte(this.openFriday ? (byte) 1 : 0);
        parcel.writeByte(this.openThursday ? (byte) 1 : 0);
        parcel.writeByte(this.openWednesday ? (byte) 1 : 0);
        parcel.writeByte(this.openTuesday ? (byte) 1 : 0);
        parcel.writeByte(this.openMonday ? (byte) 1 : 0);
    }
}
