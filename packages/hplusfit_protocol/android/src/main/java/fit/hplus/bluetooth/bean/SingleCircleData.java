package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import fit.hplus.hplusfit.tool.SPUtils;
import java.util.ArrayList;
import java.util.List;
import no.nordicsemi.android.log.LogContract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleCircleData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<SingleCircleData> CREATOR = new Parcelable.Creator<SingleCircleData>() {
        public SingleCircleData createFromParcel(Parcel parcel) {
            return new SingleCircleData(parcel);
        }

        public SingleCircleData[] newArray(int i) {
            return new SingleCircleData[i];
        }
    };
    private int day;
    private List<SingleCircleDetail> detail;
    private int hour;
    private int minute;
    private int month;
    private int number;
    private int second;
    private int year;

    public int describeContents() {
        return 0;
    }

    public SingleCircleData() {
        this.detail = new ArrayList();
    }

    protected SingleCircleData(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        this.number = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        this.year = (bArr[0] & 255) | (bArr[1] << 8);
        this.month = bArr[2];
        this.day = bArr[3];
        this.hour = bArr[4];
        this.minute = bArr[5];
        this.second = bArr[6];
        this.number = bArr[7];
        int length = (bArr.length - 8) / 24;
        for (int i = 0; i < length; i++) {
            SingleCircleDetail singleCircleDetail = new SingleCircleDetail();
            int i2 = i * 24;
            int unused = singleCircleDetail.singleHour = bArr[i2 + 8];
            int unused2 = singleCircleDetail.singleMinute = bArr[i2 + 9];
            int unused3 = singleCircleDetail.singleSecond = bArr[i2 + 10];
            int unused4 = singleCircleDetail.circle = bArr[i2 + 11];
            int unused5 = singleCircleDetail.step = (bArr[i2 + 12] & 255) | ((bArr[i2 + 13] & 255) << 8);
            int unused6 = singleCircleDetail.distance = (bArr[i2 + 14] & 255) | ((bArr[i2 + 15] & 255) << 8);
            int unused7 = singleCircleDetail.calorie = (bArr[i2 + 16] & 255) | ((bArr[i2 + 17] & 255) << 8);
            int unused8 = singleCircleDetail.avgHeart = bArr[i2 + 18] & 255;
            int unused9 = singleCircleDetail.maxHeart = bArr[i2 + 19] & 255;
            int unused10 = singleCircleDetail.lat = (bArr[i2 + 20] & 255) | ((bArr[i2 + 21] & 255) << 8) | ((bArr[i2 + 22] & 255) << 16) | ((bArr[i2 + 23] & 255) << 24);
            int unused11 = singleCircleDetail.lng = (bArr[i2 + 24] & 255) | ((bArr[i2 + 25] & 255) << 8) | ((bArr[i2 + 26] & 255) << 16) | ((bArr[i2 + 27] & 255) << 24);
            int unused12 = singleCircleDetail.risingHeight = (bArr[i2 + 28] & 255) | ((bArr[i2 + 29] & 255) << 8);
            int unused13 = singleCircleDetail.fallHeight = ((bArr[i2 + 31] & 255) << 8) | (bArr[i2 + 30] & 255);
            this.detail.add(singleCircleDetail);
        }
    }

    public List<SingleCircleDetail> getDetail() {
        return this.detail;
    }

    public void setDetail(List<SingleCircleDetail> list) {
        this.detail = list;
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
            jSONObject.put("number", this.number);
            JSONArray jSONArray = new JSONArray();
            if (this.detail != null && this.detail.size() > 0) {
                for (int i = 0; i < this.detail.size(); i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("singleHour", this.detail.get(i).singleHour);
                    jSONObject2.put("singleMinute", this.detail.get(i).singleMinute);
                    jSONObject2.put("singleSecond", this.detail.get(i).singleSecond);
                    jSONObject2.put("circle", this.detail.get(i).circle);
                    jSONObject2.put("step", this.detail.get(i).step);
                    jSONObject2.put("distance", this.detail.get(i).distance);
                    jSONObject2.put("calorie", this.detail.get(i).calorie);
                    jSONObject2.put("avgHeart", this.detail.get(i).avgHeart);
                    jSONObject2.put("maxHeart", this.detail.get(i).maxHeart);
                    jSONObject2.put(SPUtils.LATITUDE, this.detail.get(i).lat);
                    jSONObject2.put(SPUtils.LONGITUDE, this.detail.get(i).lng);
                    jSONObject2.put("risingHeight", this.detail.get(i).risingHeight);
                    jSONObject2.put("fallHeight", this.detail.get(i).fallHeight);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("detail", jSONArray);
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
        parcel.writeInt(this.number);
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

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public static class SingleCircleDetail implements Parcelable {
        public static final Parcelable.Creator<SingleCircleDetail> CREATOR = new Parcelable.Creator<SingleCircleDetail>() {
            public SingleCircleDetail createFromParcel(Parcel parcel) {
                return new SingleCircleDetail(parcel);
            }

            public SingleCircleDetail[] newArray(int i) {
                return new SingleCircleDetail[i];
            }
        };
        /* access modifiers changed from: private */
        public int avgHeart;
        /* access modifiers changed from: private */
        public int calorie;
        /* access modifiers changed from: private */
        public int circle;
        /* access modifiers changed from: private */
        public int distance;
        /* access modifiers changed from: private */
        public int fallHeight;
        /* access modifiers changed from: private */
        public int lat;
        /* access modifiers changed from: private */
        public int lng;
        /* access modifiers changed from: private */
        public int maxHeart;
        /* access modifiers changed from: private */
        public int risingHeight;
        /* access modifiers changed from: private */
        public int singleHour;
        /* access modifiers changed from: private */
        public int singleMinute;
        /* access modifiers changed from: private */
        public int singleSecond;
        /* access modifiers changed from: private */
        public int step;

        public int describeContents() {
            return 0;
        }

        public SingleCircleDetail() {
        }

        protected SingleCircleDetail(Parcel parcel) {
            this.singleHour = parcel.readInt();
            this.singleMinute = parcel.readInt();
            this.singleSecond = parcel.readInt();
            this.circle = parcel.readInt();
            this.step = parcel.readInt();
            this.distance = parcel.readInt();
            this.calorie = parcel.readInt();
            this.avgHeart = parcel.readInt();
            this.maxHeart = parcel.readInt();
            this.lat = parcel.readInt();
            this.lng = parcel.readInt();
            this.risingHeight = parcel.readInt();
            this.fallHeight = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.singleHour);
            parcel.writeInt(this.singleMinute);
            parcel.writeInt(this.singleSecond);
            parcel.writeInt(this.circle);
            parcel.writeInt(this.step);
            parcel.writeInt(this.distance);
            parcel.writeInt(this.calorie);
            parcel.writeInt(this.avgHeart);
            parcel.writeInt(this.maxHeart);
            parcel.writeInt(this.lat);
            parcel.writeInt(this.lng);
            parcel.writeInt(this.risingHeight);
            parcel.writeInt(this.fallHeight);
        }

        public int getSingleHour() {
            return this.singleHour;
        }

        public void setSingleHour(int i) {
            this.singleHour = i;
        }

        public int getSingleMinute() {
            return this.singleMinute;
        }

        public void setSingleMinute(int i) {
            this.singleMinute = i;
        }

        public int getSingleSecond() {
            return this.singleSecond;
        }

        public void setSingleSecond(int i) {
            this.singleSecond = i;
        }

        public int getCircle() {
            return this.circle;
        }

        public void setCircle(int i) {
            this.circle = i;
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

        public int getAvgHeart() {
            return this.avgHeart;
        }

        public void setAvgHeart(int i) {
            this.avgHeart = i;
        }

        public int getMaxHeart() {
            return this.maxHeart;
        }

        public void setMaxHeart(int i) {
            this.maxHeart = i;
        }

        public int getLat() {
            return this.lat;
        }

        public void setLat(int i) {
            this.lat = i;
        }

        public int getLng() {
            return this.lng;
        }

        public void setLng(int i) {
            this.lng = i;
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

        public String toString() {
            return this.circle + "|" + this.step + "|" + this.distance + "|" + this.calorie + "|" + this.avgHeart + "|" + this.maxHeart + "|" + this.lat + "|" + this.lng + "|" + this.risingHeight + "|" + this.fallHeight;
        }
    }
}
