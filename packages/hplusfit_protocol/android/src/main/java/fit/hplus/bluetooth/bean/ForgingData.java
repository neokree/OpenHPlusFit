package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgingData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<ForgingData> CREATOR = new Parcelable.Creator<ForgingData>() {
        public ForgingData createFromParcel(Parcel parcel) {
            return new ForgingData(parcel);
        }

        public ForgingData[] newArray(int i) {
            return new ForgingData[i];
        }
    };
    private int calorie;
    private int circleNumber;
    private int day;
    private int distance;
    private int forgingHour;
    private int forgingMinute;
    private int forgingSecond;
    private int hour;
    private int minute;
    private int month;
    private int second;
    private int step;
    private int type;
    private int week;
    private int year;

    public int describeContents() {
        return 0;
    }

    public ForgingData() {
    }

    protected ForgingData(Parcel parcel) {
        this.step = parcel.readInt();
        this.distance = parcel.readInt();
        this.calorie = parcel.readInt();
        this.circleNumber = parcel.readInt();
        this.type = parcel.readInt();
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        this.week = parcel.readInt();
        this.forgingHour = parcel.readInt();
        this.forgingMinute = parcel.readInt();
        this.forgingSecond = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 3) {
            this.step = (bArr[1] & 255) | ((bArr[2] << 8) & 65280);
        }
        if (bArr.length >= 5) {
            this.distance = (bArr[3] & 255) | ((bArr[4] << 8) & 65280);
        }
        if (bArr.length >= 7) {
            this.calorie = (bArr[5] & 255) | (65280 & (bArr[6] << 8));
        }
        if (bArr.length >= 8) {
            this.circleNumber = bArr[7] & 255;
        }
        if (bArr.length >= 9) {
            this.type = bArr[8] & 255;
        }
        if (bArr.length >= 11) {
            this.year = (bArr[9] & 255) | (bArr[10] << 8);
        }
        if (bArr.length >= 12) {
            this.month = bArr[11] & 255;
        }
        if (bArr.length >= 13) {
            this.day = bArr[12] & 255;
        }
        if (bArr.length >= 14) {
            this.hour = bArr[13] & 255;
        }
        if (bArr.length >= 15) {
            this.minute = bArr[14] & 255;
        }
        if (bArr.length >= 16) {
            this.second = bArr[15] & 255;
        }
        if (bArr.length >= 17) {
            this.week = bArr[16] & 255;
        }
        if (bArr.length >= 18) {
            this.forgingHour = bArr[17] & 255;
        }
        if (bArr.length >= 19) {
            this.forgingMinute = bArr[18] & 255;
        }
        if (bArr.length >= 20) {
            this.forgingSecond = bArr[19] & 255;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("step", this.step);
            jSONObject.put("distance", this.distance);
            jSONObject.put("calorie", this.calorie);
            jSONObject.put("circleNumber", this.circleNumber);
            jSONObject.put("type", this.type);
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("hour", this.hour);
            jSONObject.put("minute", this.minute);
            jSONObject.put("second", this.second);
            jSONObject.put("week", this.week);
            jSONObject.put("forgingHour", this.forgingHour);
            jSONObject.put("forgingMinute", this.forgingMinute);
            jSONObject.put("forgingSecond", this.forgingSecond);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
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

    public int getCircleNumber() {
        return this.circleNumber;
    }

    public void setCircleNumber(int i) {
        this.circleNumber = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
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

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int i) {
        this.week = i;
    }

    public int getForgingHour() {
        return this.forgingHour;
    }

    public void setForgingHour(int i) {
        this.forgingHour = i;
    }

    public int getForgingMinute() {
        return this.forgingMinute;
    }

    public void setForgingMinute(int i) {
        this.forgingMinute = i;
    }

    public int getForgingSecond() {
        return this.forgingSecond;
    }

    public void setForgingSecond(int i) {
        this.forgingSecond = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.step);
        parcel.writeInt(this.distance);
        parcel.writeInt(this.calorie);
        parcel.writeInt(this.circleNumber);
        parcel.writeInt(this.type);
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
        parcel.writeInt(this.week);
        parcel.writeInt(this.forgingHour);
        parcel.writeInt(this.forgingMinute);
        parcel.writeInt(this.forgingSecond);
    }
}
