package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class SleepTotalData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<SleepTotalData> CREATOR = new Parcelable.Creator<SleepTotalData>() {
        public SleepTotalData createFromParcel(Parcel parcel) {
            return new SleepTotalData(parcel);
        }

        public SleepTotalData[] newArray(int i) {
            return new SleepTotalData[i];
        }
    };
    private int awakeCount;
    private int awakeTime;
    private int day;
    private int deepSleepTime;
    private int fallSleepTime;
    private int month;
    private int secondStageTime;
    private int shallowSleepTime;
    private int startSleepHour;
    private int startSleepMinute;
    private int year;

    public int describeContents() {
        return 0;
    }

    public SleepTotalData() {
    }

    protected SleepTotalData(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.fallSleepTime = parcel.readInt();
        this.secondStageTime = parcel.readInt();
        this.deepSleepTime = parcel.readInt();
        this.shallowSleepTime = parcel.readInt();
        this.awakeTime = parcel.readInt();
        this.awakeCount = parcel.readInt();
        this.startSleepHour = parcel.readInt();
        this.startSleepMinute = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 3) {
            this.year = (bArr[1] & 255) | ((bArr[2] & 255) << 8);
        }
        if (bArr.length >= 4) {
            this.month = bArr[3] & 255;
        }
        if (bArr.length >= 5) {
            this.day = bArr[4] & 255;
        }
        if (bArr.length >= 7) {
            this.fallSleepTime = (bArr[5] & 255) | ((bArr[6] & 255) << 8);
        }
        if (bArr.length >= 9) {
            this.secondStageTime = (bArr[7] & 255) | ((bArr[8] & 255) << 8);
        }
        if (bArr.length >= 11) {
            this.deepSleepTime = (bArr[9] & 255) | ((bArr[10] & 255) << 8);
        }
        if (bArr.length >= 13) {
            this.shallowSleepTime = (bArr[11] & 255) | ((bArr[12] & 255) << 8);
        }
        if (bArr.length >= 15) {
            this.awakeTime = (bArr[13] & 255) | ((bArr[14] & 255) << 8);
        }
        if (bArr.length >= 17) {
            this.awakeCount = (bArr[15] & 255) | ((bArr[16] & 255) << 8);
        }
        if (bArr.length >= 18) {
            this.startSleepHour = bArr[17] & 255;
        }
        if (bArr.length >= 19) {
            this.startSleepMinute = bArr[18] & 255;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("fallSleepTime", this.fallSleepTime);
            jSONObject.put("secondStageTime", this.secondStageTime);
            jSONObject.put("deepSleepTime", this.deepSleepTime);
            jSONObject.put("shallowSleepTime", this.shallowSleepTime);
            jSONObject.put("awakeTime", this.awakeTime);
            jSONObject.put("awakeCount", this.awakeCount);
            jSONObject.put("startSleepHour", this.startSleepHour);
            jSONObject.put("startSleepMinute", this.startSleepMinute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.fallSleepTime);
        parcel.writeInt(this.secondStageTime);
        parcel.writeInt(this.deepSleepTime);
        parcel.writeInt(this.shallowSleepTime);
        parcel.writeInt(this.awakeTime);
        parcel.writeInt(this.awakeCount);
        parcel.writeInt(this.startSleepHour);
        parcel.writeInt(this.startSleepMinute);
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

    public int getFallSleepTime() {
        return this.fallSleepTime;
    }

    public void setFallSleepTime(int i) {
        this.fallSleepTime = i;
    }

    public int getSecondStageTime() {
        return this.secondStageTime;
    }

    public void setSecondStageTime(int i) {
        this.secondStageTime = i;
    }

    public int getDeepSleepTime() {
        return this.deepSleepTime;
    }

    public void setDeepSleepTime(int i) {
        this.deepSleepTime = i;
    }

    public int getShallowSleepTime() {
        return this.shallowSleepTime;
    }

    public void setShallowSleepTime(int i) {
        this.shallowSleepTime = i;
    }

    public int getAwakeTime() {
        return this.awakeTime;
    }

    public void setAwakeTime(int i) {
        this.awakeTime = i;
    }

    public int getAwakeCount() {
        return this.awakeCount;
    }

    public void setAwakeCount(int i) {
        this.awakeCount = i;
    }

    public int getStartSleepHour() {
        return this.startSleepHour;
    }

    public void setStartSleepHour(int i) {
        this.startSleepHour = i;
    }

    public int getStartSleepMinute() {
        return this.startSleepMinute;
    }

    public void setStartSleepMinute(int i) {
        this.startSleepMinute = i;
    }
}
