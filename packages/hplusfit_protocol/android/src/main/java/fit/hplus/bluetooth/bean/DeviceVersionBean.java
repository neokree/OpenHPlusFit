package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceVersionBean implements Parcelable {
    public static final Parcelable.Creator<DeviceVersionBean> CREATOR = new Parcelable.Creator<DeviceVersionBean>() {
        public DeviceVersionBean createFromParcel(Parcel parcel) {
            return new DeviceVersionBean(parcel);
        }

        public DeviceVersionBean[] newArray(int i) {
            return new DeviceVersionBean[i];
        }
    };
    private String md5;
    private String model;
    private int object;
    private int subcode;
    private int ver;

    public int describeContents() {
        return 0;
    }

    public DeviceVersionBean() {
    }

    protected DeviceVersionBean(Parcel parcel) {
        this.model = parcel.readString();
        this.subcode = parcel.readInt();
        this.ver = parcel.readInt();
        this.object = parcel.readInt();
        this.md5 = parcel.readString();
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public int getSubcode() {
        return this.subcode;
    }

    public void setSubcode(int i) {
        this.subcode = i;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int i) {
        this.ver = i;
    }

    public int getObject() {
        return this.object;
    }

    public void setObject(int i) {
        this.object = i;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String str) {
        this.md5 = str;
    }

    public String toString() {
        return "DeviceVersionBean{model='" + this.model + '\'' + ", subcode=" + this.subcode + ", ver=" + this.ver + ", object=" + this.object + ", md5='" + this.md5 + '\'' + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.model);
        parcel.writeInt(this.subcode);
        parcel.writeInt(this.ver);
        parcel.writeInt(this.object);
        parcel.writeString(this.md5);
    }
}
