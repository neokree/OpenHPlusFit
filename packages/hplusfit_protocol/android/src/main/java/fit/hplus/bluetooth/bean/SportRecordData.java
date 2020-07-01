package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class SportRecordData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<SportRecordData> CREATOR = new Parcelable.Creator<SportRecordData>() {
        public SportRecordData createFromParcel(Parcel parcel) {
            return new SportRecordData(parcel);
        }

        public SportRecordData[] newArray(int i) {
            return new SportRecordData[i];
        }
    };
    private int calorie;
    private int day;
    private int distance;
    private int maxHeart;
    private int minHeart;
    private int month;
    private int sportTime;
    private int step;
    private int stillCalorie;
    private int year;

    public int describeContents() {
        return 0;
    }

    public SportRecordData() {
    }

    protected SportRecordData(Parcel parcel) {
        this.step = parcel.readInt();
        this.distance = parcel.readInt();
        this.calorie = parcel.readInt();
        this.stillCalorie = parcel.readInt();
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.sportTime = parcel.readInt();
        this.maxHeart = parcel.readInt();
        this.minHeart = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 3) {
            this.step = (bArr[1] & 255) | ((bArr[2] & 255) << 8);
        }
        if (bArr.length >= 5) {
            this.distance = (bArr[3] & 255) | ((bArr[4] & 255) << 8);
        }
        if (bArr.length >= 7) {
            this.calorie = (bArr[5] & 255) | ((bArr[6] & 255) << 8);
        }
        if (bArr.length >= 9) {
            this.stillCalorie = (bArr[7] & 255) | ((bArr[8] & 255) << 8);
        }
        if (bArr.length >= 11) {
            this.year = (bArr[9] & 255) | ((bArr[10] & 255) << 8);
        }
        if (bArr.length >= 12) {
            this.month = bArr[11] & 255;
        }
        if (bArr.length >= 13) {
            this.day = bArr[12] & 255;
        }
        if (bArr.length >= 15) {
            this.sportTime = (bArr[13] & 255) | ((bArr[14] & 255) << 8);
        }
        if (bArr.length >= 16) {
            this.maxHeart = bArr[15] & 255;
        }
        if (bArr.length >= 17) {
            this.minHeart = bArr[16] & 255;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("step", this.step);
            jSONObject.put("distance", this.distance);
            jSONObject.put("calorie", this.calorie);
            jSONObject.put("stillCalorie", this.stillCalorie);
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("sportTime", this.sportTime);
            int i = 0;
            jSONObject.put("maxHeart", this.maxHeart == 255 ? 0 : this.maxHeart);
            if (this.minHeart != 255) {
                i = this.minHeart;
            }
            jSONObject.put("minHeart", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.step);
        parcel.writeInt(this.distance);
        parcel.writeInt(this.calorie);
        parcel.writeInt(this.stillCalorie);
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.sportTime);
        parcel.writeInt(this.maxHeart);
        parcel.writeInt(this.minHeart);
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int i) {
        this.step = i;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int i) {
        this.distance = i;
    }

    public int getCalorie() {
        return this.calorie;
    }

    public void setCalorie(int i) {
        this.calorie = i;
    }

    public int getStillCalorie() {
        return this.stillCalorie;
    }

    public void setStillCalorie(int i) {
        this.stillCalorie = i;
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

    public int getSportTime() {
        return this.sportTime;
    }

    public void setSportTime(int i) {
        this.sportTime = i;
    }

    public int getMaxHeart() {
        return this.maxHeart;
    }

    public void setMaxHeart(int i) {
        this.maxHeart = i;
    }

    public int getMinHeart() {
        return this.minHeart;
    }

    public void setMinHeart(int i) {
        this.minHeart = i;
    }
}
