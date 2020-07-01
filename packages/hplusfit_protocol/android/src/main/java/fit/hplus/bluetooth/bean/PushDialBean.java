package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PushDialBean implements Parcelable {
    public static final Parcelable.Creator<PushDialBean> CREATOR = new Parcelable.Creator<PushDialBean>() {
        public PushDialBean createFromParcel(Parcel parcel) {
            return new PushDialBean(parcel);
        }

        public PushDialBean[] newArray(int i) {
            return new PushDialBean[i];
        }
    };
    private String drawableFile;
    private String layoutFile;
    private int num;
    private String resFile;

    public int describeContents() {
        return 0;
    }

    public PushDialBean() {
    }

    protected PushDialBean(Parcel parcel) {
        this.num = parcel.readInt();
        this.drawableFile = parcel.readString();
        this.resFile = parcel.readString();
        this.layoutFile = parcel.readString();
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public String getDrawableFile() {
        return this.drawableFile;
    }

    public void setDrawableFile(String str) {
        this.drawableFile = str;
    }

    public String getResFile() {
        return this.resFile;
    }

    public void setResFile(String str) {
        this.resFile = str;
    }

    public String getLayoutFile() {
        return this.layoutFile;
    }

    public void setLayoutFile(String str) {
        this.layoutFile = str;
    }

    public String toString() {
        return "PushDialBean{num=" + this.num + ", drawableFile='" + this.drawableFile + '\'' + ", resFile='" + this.resFile + '\'' + ", layoutFile='" + this.layoutFile + '\'' + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.num);
        parcel.writeString(this.drawableFile);
        parcel.writeString(this.resFile);
        parcel.writeString(this.layoutFile);
    }
}
