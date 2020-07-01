package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import no.nordicsemi.android.log.LogContract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<MapData> CREATOR = new Parcelable.Creator<MapData>() {
        public MapData createFromParcel(Parcel parcel) {
            return new MapData(parcel);
        }

        public MapData[] newArray(int i) {
            return new MapData[i];
        }
    };
    private int day;
    private int hour;
    private int index;
    private List<Double> mLat;
    private List<Double> mLng;
    private int minute;
    private int month;
    private int number;
    private int second;
    private int year;

    public int describeContents() {
        return 0;
    }

    protected MapData(Parcel parcel) {
        this.number = parcel.readInt();
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        this.index = parcel.readInt();
    }

    public void unPacket(byte[] bArr) {
        if (bArr.length >= 2) {
            this.number = bArr[1];
        }
        if (bArr.length >= 6) {
            this.year = (bArr[4] & 255) | ((bArr[5] & 255) << 8);
        }
        if (bArr.length >= 7) {
            this.month = bArr[6];
        }
        if (bArr.length >= 8) {
            this.day = bArr[7];
        }
        if (bArr.length >= 9) {
            this.hour = bArr[8];
        }
        if (bArr.length >= 10) {
            this.minute = bArr[9];
        }
        if (bArr.length >= 11) {
            this.second = bArr[10];
        }
        if (bArr.length >= 12) {
            this.index = bArr[11] & 255;
        }
        byte b = (byte) ((bArr[12] & 255) | ((bArr[13] & 255) << 8) | ((bArr[14] & 255) << 16) | ((bArr[15] & 255) << 24));
        if (bArr.length >= 16) {
            List<Double> list = this.mLng;
            double d = (double) b;
            Double.isNaN(d);
            list.add(Double.valueOf((d * 1.0d) / 1000000.0d));
        }
        byte b2 = (byte) ((bArr[16] & 255) | ((bArr[17] & 255) << 8) | ((bArr[18] & 255) << 16) | ((bArr[19] & 255) << 24));
        if (bArr.length >= 20) {
            List<Double> list2 = this.mLat;
            double d2 = (double) b2;
            Double.isNaN(d2);
            list2.add(Double.valueOf((d2 * 1.0d) / 1000000.0d));
        }
    }

    public void unPacketLatLng(byte[] bArr) {
        if (bArr.length >= 2) {
            this.number = bArr[1];
        }
        if (bArr.length >= 12) {
            byte b = (byte) ((bArr[4] & 255) | ((bArr[5] & 255) << 8) | ((bArr[6] & 255) << 16) | ((bArr[7] & 255) << 24));
            List<Double> list = this.mLng;
            double d = (double) b;
            Double.isNaN(d);
            list.add(Double.valueOf((d * 1.0d) / 1000000.0d));
            byte b2 = (byte) ((bArr[8] & 255) | ((bArr[9] & 255) << 8) | ((bArr[10] & 255) << 16) | ((bArr[11] & 255) << 24));
            List<Double> list2 = this.mLat;
            double d2 = (double) b2;
            Double.isNaN(d2);
            list2.add(Double.valueOf((d2 * 1.0d) / 1000000.0d));
        }
        if (bArr.length >= 20) {
            byte b3 = (byte) ((bArr[12] & 255) | ((bArr[13] & 255) << 8) | ((bArr[14] & 255) << 16) | ((bArr[15] & 255) << 24));
            List<Double> list3 = this.mLng;
            double d3 = (double) b3;
            Double.isNaN(d3);
            list3.add(Double.valueOf((d3 * 1.0d) / 1000000.0d));
            byte b4 = (byte) (((bArr[19] & 255) << 24) | (bArr[16] & 255) | ((bArr[17] & 255) << 8) | ((bArr[18] & 255) << 16));
            List<Double> list4 = this.mLat;
            double d4 = (double) b4;
            Double.isNaN(d4);
            list4.add(Double.valueOf((d4 * 1.0d) / 1000000.0d));
        }
    }

    public MapData() {
        this.mLat = new ArrayList();
        this.mLng = new ArrayList();
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
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

    public List<Double> getmLat() {
        return this.mLat;
    }

    public void setmLat(List<Double> list) {
        this.mLat = list;
    }

    public List<Double> getmLng() {
        return this.mLng;
    }

    public void setmLng(List<Double> list) {
        this.mLng = list;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public JSONObject toJson() {
        JSONObject jSONObject;
        JSONException e;

        jSONObject = new JSONObject();
        try {
            jSONObject.put("number", this.number);
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("hour", this.hour);
            jSONObject.put("minute", this.minute);
            jSONObject.put("second", this.second);
            jSONObject.put("index", this.index);
            JSONArray jSONArray = new JSONArray();
            if (this.mLat != null && this.mLat.size() > 0) {
                for (int i = 0; i < this.mLat.size(); i++) {
                    jSONArray.put(this.mLat.get(i));
                }
            }
            JSONArray jSONArray2 = new JSONArray();
            if (this.mLng != null && this.mLng.size() > 0) {
                for (int i2 = 0; i2 < this.mLng.size(); i2++) {
                    jSONArray2.put(this.mLng.get(i2));
                }
            }
            jSONObject.put("mLat", jSONArray2);
            jSONObject.put("mLng", jSONArray);
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return jSONObject;
        }

        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.number);
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
        parcel.writeInt(this.index);
    }
}
