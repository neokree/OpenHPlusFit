package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import fit.hplus.bluetooth.util.HexUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import no.nordicsemi.android.ble.error.GattError;
import org.apache.commons.cli.HelpFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllDayTenMinuteThermometer implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<AllDayTenMinuteThermometer> CREATOR = new Parcelable.Creator<AllDayTenMinuteThermometer>() {
        public AllDayTenMinuteThermometer createFromParcel(Parcel parcel) {
            return new AllDayTenMinuteThermometer(parcel);
        }

        public AllDayTenMinuteThermometer[] newArray(int i) {
            return new AllDayTenMinuteThermometer[i];
        }
    };
    private int day;
    private List<AllDayTenMinuteDetail> mList;
    private int month;
    SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private int year;

    public int checkFenCount(int i) {
        if (i > 50) {
            return 5;
        }
        if (i <= 50 && i > 40) {
            return 4;
        }
        if (i <= 40 && i > 30) {
            return 3;
        }
        if (i > 30 || i <= 20) {
            return (i > 20 || i <= 10) ? 0 : 1;
        }
        return 2;
    }

    public int describeContents() {
        return 0;
    }

    public AllDayTenMinuteThermometer() {
    }

    protected AllDayTenMinuteThermometer(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.mList = parcel.createTypedArrayList(AllDayTenMinuteDetail.CREATOR);
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

    public List<AllDayTenMinuteDetail> getmList() {
        return this.mList;
    }

    public void setmList(List<AllDayTenMinuteDetail> list) {
        this.mList = list;
    }

    public int checkCount(int i, int i2) {
        if (i == 0) {
            return checkFenCount(i2);
        }
        if (i == 1) {
            return checkFenCount(i2) + 6;
        }
        if (i == 2) {
            return checkFenCount(i2) + 12;
        }
        if (i == 3) {
            return checkFenCount(i2) + 18;
        }
        if (i == 4) {
            return checkFenCount(i2) + 24;
        }
        if (i == 5) {
            return checkFenCount(i2) + 30;
        }
        if (i == 6) {
            return checkFenCount(i2) + 36;
        }
        if (i == 7) {
            return checkFenCount(i2) + 42;
        }
        if (i == 8) {
            return checkFenCount(i2) + 48;
        }
        if (i == 9) {
            return checkFenCount(i2) + 54;
        }
        if (i == 10) {
            return checkFenCount(i2) + 60;
        }
        if (i == 11) {
            return checkFenCount(i2) + 66;
        }
        if (i == 12) {
            return checkFenCount(i2) + 72;
        }
        if (i == 13) {
            return checkFenCount(i2) + 78;
        }
        if (i == 14) {
            return checkFenCount(i2) + 84;
        }
        if (i == 15) {
            return checkFenCount(i2) + 90;
        }
        if (i == 16) {
            return checkFenCount(i2) + 96;
        }
        if (i == 17) {
            return checkFenCount(i2) + 102;
        }
        if (i == 18) {
            return checkFenCount(i2) + 108;
        }
        if (i == 19) {
            return checkFenCount(i2) + 114;
        }
        if (i == 20) {
            return checkFenCount(i2) + 120;
        }
        if (i == 21) {
            return checkFenCount(i2) + 126;
        }
        if (i == 22) {
            return checkFenCount(i2) + GattError.GATT_BUSY;
        }
        if (i == 23) {
            return checkFenCount(i2) + GattError.GATT_MORE;
        }
        return 0;
    }

    public void unPack(byte[] bArr) {
        HexUtil.encodeHexStr(bArr);
        int i = 0;
        if (bArr.length >= 3) {
            this.year = (bArr[0] & 255) | (bArr[1] << 8);
        }
        this.month = bArr[2] & 255;
        this.day = bArr[3] & 255;
        this.year + HelpFormatter.DEFAULT_OPT_PREFIX + String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(this.month)}) + HelpFormatter.DEFAULT_OPT_PREFIX + String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(this.day)});
        String[] split = this.simpleDateFormatDay.format(new Date(System.currentTimeMillis())).split(" ")[1].split(":");
        int checkCount = checkCount(Integer.valueOf(split[0]).intValue(), Integer.valueOf(split[1]).intValue());
        int length = (((bArr.length - 4) - 13) - 36) / 4;
        this.mList = new ArrayList();
        int i2 = 0;
        while (i < length) {
            AllDayTenMinuteDetail allDayTenMinuteDetail = new AllDayTenMinuteDetail();
            if (i <= checkCount) {
                int i3 = i * 4;
                float unused = allDayTenMinuteDetail.shelltemper = Float.valueOf(HexUtil.setformat(1, ((float) ((bArr[(i3 + 17) + i2] & 255) | ((bArr[(i3 + 18) + i2] & 255) << 8))) / 100.0f)).floatValue();
                float unused2 = allDayTenMinuteDetail.temper = Float.valueOf(HexUtil.setformat(1, ((float) (((bArr[(i3 + 20) + i2] & 255) << 8) | (bArr[(i3 + 19) + i2] & 255))) / 100.0f)).floatValue();
            } else {
                float unused3 = allDayTenMinuteDetail.shelltemper = 0.0f;
                float unused4 = allDayTenMinuteDetail.temper = 0.0f;
            }
            i++;
            if (i % 4 == 0) {
                i2++;
            }
            this.mList.add(allDayTenMinuteDetail);
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            JSONArray jSONArray = new JSONArray();
            if (this.mList != null && this.mList.size() > 0) {
                for (int i = 0; i < this.mList.size(); i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("temper", (double) this.mList.get(i).temper);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("mList", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeTypedList(this.mList);
    }

    public static class AllDayTenMinuteDetail implements Parcelable {
        public static final Parcelable.Creator<AllDayTenMinuteDetail> CREATOR = new Parcelable.Creator<AllDayTenMinuteDetail>() {
            public AllDayTenMinuteDetail createFromParcel(Parcel parcel) {
                return new AllDayTenMinuteDetail(parcel);
            }

            public AllDayTenMinuteDetail[] newArray(int i) {
                return new AllDayTenMinuteDetail[i];
            }
        };
        /* access modifiers changed from: private */
        public float shelltemper = 0.0f;
        /* access modifiers changed from: private */
        public float temper = 0.0f;

        public int describeContents() {
            return 0;
        }

        public AllDayTenMinuteDetail() {
        }

        protected AllDayTenMinuteDetail(Parcel parcel) {
            this.shelltemper = parcel.readFloat();
            this.temper = parcel.readFloat();
        }

        public float getShelltemper() {
            return this.shelltemper;
        }

        public void setShelltemper(float f) {
            this.shelltemper = f;
        }

        public float getTemper() {
            return this.temper;
        }

        public void setTemper(float f) {
            this.temper = f;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(this.shelltemper);
            parcel.writeFloat(this.temper);
        }
    }
}
