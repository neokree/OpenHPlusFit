package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import fit.hplus.bluetooth.util.HexUtil;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealSingleHealthBean implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<RealSingleHealthBean> CREATOR = new Parcelable.Creator<RealSingleHealthBean>() {
        public RealSingleHealthBean createFromParcel(Parcel parcel) {
            return new RealSingleHealthBean(parcel);
        }

        public RealSingleHealthBean[] newArray(int i) {
            return new RealSingleHealthBean[i];
        }
    };
    private int DBP;
    private int SBP;
    private int bloodOxygen;
    private int day;
    private boolean hasBO;
    private boolean hasBR;
    private boolean hasHR;
    private boolean hasTEMP;
    private int heart;
    private int hour;
    private int minute;
    private int month;
    private int second;
    private float temperature;
    private int year;

    public int describeContents() {
        return 0;
    }

    public RealSingleHealthBean() {
    }

    protected RealSingleHealthBean(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        boolean z = true;
        this.hasHR = parcel.readByte() != 0;
        this.hasBR = parcel.readByte() != 0;
        this.hasBO = parcel.readByte() != 0;
        this.hasTEMP = parcel.readByte() == 0 ? false : z;
        this.heart = parcel.readInt();
        this.SBP = parcel.readInt();
        this.DBP = parcel.readInt();
        this.bloodOxygen = parcel.readInt();
        this.temperature = parcel.readFloat();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
        parcel.writeByte(this.hasHR ? (byte) 1 : 0);
        parcel.writeByte(this.hasBR ? (byte) 1 : 0);
        parcel.writeByte(this.hasBO ? (byte) 1 : 0);
        parcel.writeByte(this.hasTEMP ? (byte) 1 : 0);
        parcel.writeInt(this.heart);
        parcel.writeInt(this.SBP);
        parcel.writeInt(this.DBP);
        parcel.writeInt(this.bloodOxygen);
        parcel.writeFloat(this.temperature);
    }

    public void unPack(byte[] bArr) {
        this.year = (bArr[2] & 255) | (bArr[1] << 8);
        this.month = bArr[3] & 255;
        this.day = bArr[4] & 255;
        this.hour = bArr[5] & 255;
        this.minute = bArr[6] & 255;
        this.second = bArr[7] & 255;
        byte b = (byte) (bArr[8] & 255);
        if (b == 1) {
            this.hasHR = true;
        } else if (b == 2) {
            this.hasBR = true;
        } else if (b == 4) {
            this.hasBO = true;
        } else if (b == 8) {
            this.hasTEMP = true;
        }
        this.heart = bArr[9] & 255;
        this.SBP = bArr[10] & 255;
        this.DBP = bArr[11] & 255;
        this.bloodOxygen = bArr[12] & 255;
        this.temperature = Float.valueOf(HexUtil.setformat(1, ((float) (((bArr[16] & 255) << 8) | (bArr[15] & 255))) / 100.0f)).floatValue();
    }

    public static JSONArray unPackList(List<RealSingleHealthBean> list) {
        JSONArray jSONArray;

        jSONArray = new JSONArray();
        int i = 0;
        while (i < list.size()) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("year", list.get(i).getYear());
                jSONObject.put("month", list.get(i).getMonth());
                jSONObject.put("day", list.get(i).getDay());
                jSONObject.put("hour", list.get(i).getHour());
                jSONObject.put("minute", list.get(i).getMinute());
                jSONObject.put("second", list.get(i).getSecond());
                jSONObject.put("hasHR", list.get(i).isHasHR());
                jSONObject.put("hasBR", list.get(i).isHasBR());
                jSONObject.put("hasBO", list.get(i).isHasBO());
                jSONObject.put("hasTEMP", list.get(i).isHasTEMP());
                jSONObject.put("heart", list.get(i).getHeart());
                jSONObject.put("SBP", list.get(i).getSBP());
                jSONObject.put("DBP", list.get(i).getDBP());
                jSONObject.put("bloodOxygen", list.get(i).getBloodOxygen());
                jSONObject.put("temperature", (double) list.get(i).getTemperature());
                jSONArray.put(jSONObject);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return jSONArray;
            }
        }

        return jSONArray;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("hour", this.hour);
            jSONObject.put("minute", this.minute);
            jSONObject.put("second", this.second);
            jSONObject.put("hasHR", this.hasHR);
            jSONObject.put("hasBR", this.hasBR);
            jSONObject.put("hasBO", this.hasBO);
            jSONObject.put("hasTEMP", this.hasTEMP);
            jSONObject.put("heart", this.heart);
            jSONObject.put("SBP", this.SBP);
            jSONObject.put("DBP", this.DBP);
            jSONObject.put("bloodOxygen", this.bloodOxygen);
            jSONObject.put("temperature", (double) this.temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
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

    public int getHeart() {
        return this.heart;
    }

    public void setHeart(int i) {
        this.heart = i;
    }

    public int getSBP() {
        return this.SBP;
    }

    public void setSBP(int i) {
        this.SBP = i;
    }

    public int getDBP() {
        return this.DBP;
    }

    public void setDBP(int i) {
        this.DBP = i;
    }

    public boolean isHasHR() {
        return this.hasHR;
    }

    public void setHasHR(boolean z) {
        this.hasHR = z;
    }

    public boolean isHasBR() {
        return this.hasBR;
    }

    public void setHasBR(boolean z) {
        this.hasBR = z;
    }

    public boolean isHasBO() {
        return this.hasBO;
    }

    public void setHasBO(boolean z) {
        this.hasBO = z;
    }

    public boolean isHasTEMP() {
        return this.hasTEMP;
    }

    public void setHasTEMP(boolean z) {
        this.hasTEMP = z;
    }

    public int getBloodOxygen() {
        return this.bloodOxygen;
    }

    public void setBloodOxygen(int i) {
        this.bloodOxygen = i;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(float f) {
        this.temperature = f;
    }
}
