package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import fit.hplus.bluetooth.util.HexUtil;
import java.util.ArrayList;
import java.util.List;

public class PushDialResBean implements Parcelable {
    public static final Parcelable.Creator<PushDialResBean> CREATOR = new Parcelable.Creator<PushDialResBean>() {
        public PushDialResBean createFromParcel(Parcel parcel) {
            return new PushDialResBean(parcel);
        }

        public PushDialResBean[] newArray(int i) {
            return new PushDialResBean[i];
        }
    };
    private String _1_file;
    private int _2_page;
    private int _3_len;
    private String _4_data;
    private int _5_check;

    public int describeContents() {
        return 0;
    }

    public PushDialResBean() {
    }

    protected PushDialResBean(Parcel parcel) {
        this._1_file = parcel.readString();
        this._2_page = parcel.readInt();
        this._3_len = parcel.readInt();
        this._4_data = parcel.readString();
        this._5_check = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this._1_file);
        parcel.writeInt(this._2_page);
        parcel.writeInt(this._3_len);
        parcel.writeString(this._4_data);
        parcel.writeInt(this._5_check);
    }

    public List<byte[]> pack(int i, int i2) {
        int i3;
        String[] split = !TextUtils.isEmpty(this._4_data) ? this._4_data.split("\\,") : null;
        int i4 = this._3_len;
        int i5 = (i4 / 16) + (i4 % 16 > 0 ? 1 : 0) + 1;
        ArrayList arrayList = new ArrayList(i5 + 1);
        int i6 = 0;
        int i7 = 0;
        while (true) {
            if (i6 >= i5) {
                break;
            }
            byte[] bArr = new byte[20];
            if (i6 == 0) {
                bArr[0] = (byte) ((i5 >> 8) & 255);
                bArr[1] = (byte) (i5 & 255);
                int i8 = i6 + 1;
                bArr[2] = (byte) ((i8 >> 8) & 255);
                bArr[3] = (byte) (i8 & 255);
                int i9 = this._2_page;
                bArr[4] = (byte) ((i9 >> 8) & 255);
                bArr[5] = (byte) (i9 & 255);
                int i10 = this._3_len;
                bArr[6] = (byte) ((i10 >> 8) & 255);
                bArr[7] = (byte) (i10 & 255);
                bArr[8] = (byte) (i & 255);
                bArr[9] = (byte) (i2 & 255);
                int i11 = this._5_check;
                bArr[10] = (byte) ((i11 >> 8) & 255);
                bArr[11] = (byte) (i11 & 255);
            } else {
                bArr[0] = (byte) ((i5 >> 8) & 255);
                bArr[1] = (byte) (i5 & 255);
                int i12 = i6 + 1;
                bArr[2] = (byte) ((i12 >> 8) & 255);
                bArr[3] = (byte) (i12 & 255);
                for (int i13 = 0; i13 < 16; i13++) {
                    int i14 = i7 + i13;
                    if (i14 <= split.length - 1) {
                        bArr[i13 + 4] = HexUtil.hexStringToBytes(split[i14].replace("0x", ""))[0];
                    }
                }
                i7 += 16;
            }
            arrayList.add(bArr);
            i6++;
        }
        byte[] bArr2 = new byte[20];
        bArr2[0] = (byte) ((i5 >> 8) & 255);
        bArr2[1] = (byte) i5;
        for (i3 = 2; i3 < bArr2.length; i3++) {
            bArr2[i3] = -1;
        }
        arrayList.add(bArr2);
        return arrayList;
    }

    public String get_1_file() {
        return this._1_file;
    }

    public void set_1_file(String str) {
        this._1_file = str;
    }

    public int get_2_page() {
        return this._2_page;
    }

    public void set_2_page(int i) {
        this._2_page = i;
    }

    public int get_3_len() {
        return this._3_len;
    }

    public void set_3_len(int i) {
        this._3_len = i;
    }

    public String get_4_data() {
        return this._4_data;
    }

    public void set_4_data(String str) {
        this._4_data = str;
    }

    public int get_5_check() {
        return this._5_check;
    }

    public void set_5_check(int i) {
        this._5_check = i;
    }
}
