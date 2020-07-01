package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleWorkoutData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<SingleWorkoutData> CREATOR = new Parcelable.Creator<SingleWorkoutData>() {
        public SingleWorkoutData createFromParcel(Parcel parcel) {
            return new SingleWorkoutData(parcel);
        }

        public SingleWorkoutData[] newArray(int i) {
            return new SingleWorkoutData[i];
        }
    };
    private int calorie;
    private List<SingleDetailData> data;
    private int dataNumber;
    private int day;
    private int distance;
    private int fallHeight;
    private int fallTime;
    private int hour;
    private int intervalTime;
    private int lastTime;
    private int maxHeight;
    private int maxTemperature;
    private int minHeight;
    private int minTemperature;
    private int minute;
    private int month;
    private int risingHeight;
    private int risingTime;
    private int second;
    private int sportType;
    private int step;
    private int year;

    public int describeContents() {
        return 0;
    }

    public SingleWorkoutData() {
    }

    protected SingleWorkoutData(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        this.sportType = parcel.readInt();
        this.distance = parcel.readInt();
        this.calorie = parcel.readInt();
        this.step = parcel.readInt();
        this.risingHeight = parcel.readInt();
        this.fallHeight = parcel.readInt();
        this.risingTime = parcel.readInt();
        this.fallTime = parcel.readInt();
        this.maxHeight = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.maxTemperature = parcel.readInt();
        this.minTemperature = parcel.readInt();
        this.intervalTime = parcel.readInt();
        this.dataNumber = parcel.readInt();
        this.lastTime = parcel.readInt();
        this.data = parcel.createTypedArrayList(SingleDetailData.CREATOR);
    }

    public void unPack(byte[] bArr) {
        this.year = (bArr[0] & 255) | (bArr[1] << 8);
        this.month = bArr[2];
        this.day = bArr[3];
        this.hour = bArr[4];
        this.minute = bArr[5];
        this.second = bArr[6];
        this.sportType = bArr[7];
        this.distance = (bArr[8] & 255) | ((bArr[9] & 255) << 8);
        this.calorie = (bArr[10] & 255) | ((bArr[11] & 255) << 8);
        this.step = (bArr[12] & 255) | ((bArr[13] & 255) << 8);
        this.risingHeight = (bArr[14] & 255) | ((bArr[15] & 255) << 8);
        this.fallHeight = (bArr[16] & 255) | ((bArr[17] & 255) << 8);
        this.risingTime = (bArr[18] & 255) | ((bArr[19] & 255) << 8);
        this.fallTime = (bArr[20] & 255) | ((bArr[21] & 255) << 8);
        this.maxHeight = (bArr[22] & 255) | ((bArr[23] & 255) << 8);
        this.minHeight = (bArr[24] & 255) | ((bArr[25] & 255) << 8);
        this.maxTemperature = bArr[26];
        this.minTemperature = bArr[27];
        this.intervalTime = (bArr[28] & 255) | ((bArr[29] & 255) << 8);
        this.dataNumber = bArr[30];
        this.lastTime = bArr[31];
        int length = (bArr.length - 32) / 6;
        this.data = new ArrayList();
        for (int i = 0; i < length; i++) {
            SingleDetailData singleDetailData = new SingleDetailData();
            int i2 = i * 6;
            int unused = singleDetailData.heart = bArr[i2 + 32] & 255;
            int unused2 = singleDetailData.calorie = bArr[i2 + 33] & 255;
            int unused3 = singleDetailData.step = (bArr[i2 + 34] & 255) | ((bArr[i2 + 35] & 255) << 8);
            int unused4 = singleDetailData.distance = ((bArr[i2 + 37] & 255) << 8) | (bArr[i2 + 36] & 255);
            this.data.add(singleDetailData);
        }
    }

    public List<SingleDetailData> getData() {
        return this.data;
    }

    public void setData(List<SingleDetailData> list) {
        this.data = list;
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
            jSONObject.put("sportType", this.sportType);
            jSONObject.put("distance", this.distance);
            jSONObject.put("calorie", this.calorie);
            jSONObject.put("step", this.step);
            jSONObject.put("risingHeight", this.risingHeight);
            jSONObject.put("fallHeight", this.fallHeight);
            jSONObject.put("risingTime", this.risingTime);
            jSONObject.put("fallTime", this.fallTime);
            jSONObject.put("maxHeight", this.maxHeight);
            jSONObject.put("minHeight", this.minHeight);
            jSONObject.put("maxTemperature", this.maxTemperature);
            jSONObject.put("minTemperature", this.minTemperature);
            jSONObject.put("intervalTime", this.intervalTime);
            jSONObject.put("lastTime", this.lastTime);
            JSONArray jSONArray = new JSONArray();
            if (this.data != null && this.data.size() > 0) {
                for (int i = 0; i < this.data.size(); i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("heart", this.data.get(i).heart);
                    jSONObject2.put("calorie", this.data.get(i).calorie);
                    jSONObject2.put("step", this.data.get(i).step);
                    jSONObject2.put("distance", this.data.get(i).distance);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("data", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
        parcel.writeInt(this.sportType);
        parcel.writeInt(this.distance);
        parcel.writeInt(this.calorie);
        parcel.writeInt(this.step);
        parcel.writeInt(this.risingHeight);
        parcel.writeInt(this.fallHeight);
        parcel.writeInt(this.risingTime);
        parcel.writeInt(this.fallTime);
        parcel.writeInt(this.maxHeight);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.maxTemperature);
        parcel.writeInt(this.minTemperature);
        parcel.writeInt(this.intervalTime);
        parcel.writeInt(this.dataNumber);
        parcel.writeInt(this.lastTime);
        parcel.writeTypedList(this.data);
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

    public int getSportType() {
        return this.sportType;
    }

    public void setSportType(int i) {
        this.sportType = i;
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

    public int getStep() {
        return this.step;
    }

    public void setStep(int i) {
        this.step = i;
    }

    public int getRisingHeight() {
        return this.risingHeight;
    }

    public void setRisingHeight(int i) {
        this.risingHeight = i;
    }

    public int getFallHeight() {
        return this.fallHeight;
    }

    public void setFallHeight(int i) {
        this.fallHeight = i;
    }

    public int getRisingTime() {
        return this.risingTime;
    }

    public void setRisingTime(int i) {
        this.risingTime = i;
    }

    public int getFallTime() {
        return this.fallTime;
    }

    public void setFallTime(int i) {
        this.fallTime = i;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(int i) {
        this.minHeight = i;
    }

    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    public void setMaxTemperature(int i) {
        this.maxTemperature = i;
    }

    public int getMinTemperature() {
        return this.minTemperature;
    }

    public void setMinTemperature(int i) {
        this.minTemperature = i;
    }

    public int getIntervalTime() {
        return this.intervalTime;
    }

    public void setIntervalTime(int i) {
        this.intervalTime = i;
    }

    public int getDataNumber() {
        return this.dataNumber;
    }

    public void setDataNumber(int i) {
        this.dataNumber = i;
    }

    public int getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(int i) {
        this.lastTime = i;
    }

    public static class SingleDetailData implements Parcelable {
        public static final Parcelable.Creator<SingleDetailData> CREATOR = new Parcelable.Creator<SingleDetailData>() {
            public SingleDetailData createFromParcel(Parcel parcel) {
                return new SingleDetailData(parcel);
            }

            public SingleDetailData[] newArray(int i) {
                return new SingleDetailData[i];
            }
        };
        /* access modifiers changed from: private */
        public int calorie;
        /* access modifiers changed from: private */
        public int distance;
        /* access modifiers changed from: private */
        public int heart;
        /* access modifiers changed from: private */
        public int step;

        public int describeContents() {
            return 0;
        }

        public SingleDetailData() {
        }

        protected SingleDetailData(Parcel parcel) {
            this.heart = parcel.readInt();
            this.calorie = parcel.readInt();
            this.step = parcel.readInt();
            this.distance = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.heart);
            parcel.writeInt(this.calorie);
            parcel.writeInt(this.step);
            parcel.writeInt(this.distance);
        }

        public int getHeart() {
            return this.heart;
        }

        public void setHeart(int i) {
            this.heart = i;
        }

        public int getCalorie() {
            return this.calorie;
        }

        public void setCalorie(int i) {
            this.calorie = i;
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

        public String toString() {
            return this.heart + "|" + this.calorie + "|" + this.step + "|" + this.distance;
        }
    }
}
