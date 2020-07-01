package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PushDialLayoutBean implements Parcelable {
    public static final Parcelable.Creator<PushDialLayoutBean> CREATOR = new Parcelable.Creator<PushDialLayoutBean>() {
        public PushDialLayoutBean createFromParcel(Parcel parcel) {
            return new PushDialLayoutBean(parcel);
        }

        public PushDialLayoutBean[] newArray(int i) {
            return new PushDialLayoutBean[i];
        }
    };
    private int _1_type;
    private int _2_sysid;
    private int _3_event;
    private int _4_x;
    private int _5_y;
    private int _6_width;
    private int _7_height;
    private int _8_page;
    private int _9_dot_width;
    private int _a_neg_width;
    private int _b_code1;
    private int _c_code2;
    private String _d_file;

    public int describeContents() {
        return 0;
    }

    public PushDialLayoutBean() {
    }

    protected PushDialLayoutBean(Parcel parcel) {
        this._1_type = parcel.readInt();
        this._2_sysid = parcel.readInt();
        this._3_event = parcel.readInt();
        this._4_x = parcel.readInt();
        this._5_y = parcel.readInt();
        this._6_width = parcel.readInt();
        this._7_height = parcel.readInt();
        this._8_page = parcel.readInt();
        this._9_dot_width = parcel.readInt();
        this._a_neg_width = parcel.readInt();
        this._b_code1 = parcel.readInt();
        this._c_code2 = parcel.readInt();
        this._d_file = parcel.readString();
    }

    public byte[] pack(int i, int i2) {
        int i3 = this._4_x;
        int i4 = this._5_y;
        int i5 = this._6_width;
        int i6 = this._7_height;
        int i7 = this._8_page;
        return new byte[]{-15, (byte) (i & 255), (byte) (i2 & 255), (byte) (this._2_sysid & 255), (byte) (this._1_type & 255), (byte) (this._3_event & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255), (byte) ((i4 >> 8) & 255), (byte) (i4 & 255), (byte) ((i5 >> 8) & 255), (byte) (i5 & 255), (byte) ((i6 >> 8) & 255), (byte) (i6 & 255), (byte) ((i7 >> 8) & 255), (byte) (i7 & 255), (byte) (this._9_dot_width & 255), (byte) (this._a_neg_width & 255), (byte) (this._b_code1 & 255), (byte) (this._c_code2 & 255)};
    }

    public int get_1_type() {
        return this._1_type;
    }

    public void set_1_type(int i) {
        this._1_type = i;
    }

    public int get_2_sysid() {
        return this._2_sysid;
    }

    public void set_2_sysid(int i) {
        this._2_sysid = i;
    }

    public int get_3_event() {
        return this._3_event;
    }

    public void set_3_event(int i) {
        this._3_event = i;
    }

    public int get_4_x() {
        return this._4_x;
    }

    public void set_4_x(int i) {
        this._4_x = i;
    }

    public int get_5_y() {
        return this._5_y;
    }

    public void set_5_y(int i) {
        this._5_y = i;
    }

    public int get_6_width() {
        return this._6_width;
    }

    public void set_6_width(int i) {
        this._6_width = i;
    }

    public int get_7_height() {
        return this._7_height;
    }

    public void set_7_height(int i) {
        this._7_height = i;
    }

    public int get_8_page() {
        return this._8_page;
    }

    public void set_8_page(int i) {
        this._8_page = i;
    }

    public int get_9_dot_width() {
        return this._9_dot_width;
    }

    public void set_9_dot_width(int i) {
        this._9_dot_width = i;
    }

    public int get_a_neg_width() {
        return this._a_neg_width;
    }

    public void set_a_neg_width(int i) {
        this._a_neg_width = i;
    }

    public int get_b_code1() {
        return this._b_code1;
    }

    public void set_b_code1(int i) {
        this._b_code1 = i;
    }

    public int get_c_code2() {
        return this._c_code2;
    }

    public void set_c_code2(int i) {
        this._c_code2 = i;
    }

    public String get_d_file() {
        return this._d_file;
    }

    public void set_d_file(String str) {
        this._d_file = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._1_type);
        parcel.writeInt(this._2_sysid);
        parcel.writeInt(this._3_event);
        parcel.writeInt(this._4_x);
        parcel.writeInt(this._5_y);
        parcel.writeInt(this._6_width);
        parcel.writeInt(this._7_height);
        parcel.writeInt(this._8_page);
        parcel.writeInt(this._9_dot_width);
        parcel.writeInt(this._a_neg_width);
        parcel.writeInt(this._b_code1);
        parcel.writeInt(this._c_code2);
        parcel.writeString(this._d_file);
    }
}
