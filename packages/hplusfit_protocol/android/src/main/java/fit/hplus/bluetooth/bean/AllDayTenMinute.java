package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllDayTenMinute implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<AllDayTenMinute> CREATOR = new Parcelable.Creator<AllDayTenMinute>() {
        public AllDayTenMinute createFromParcel(Parcel parcel) {
            return new AllDayTenMinute(parcel);
        }

        public AllDayTenMinute[] newArray(int i) {
            return new AllDayTenMinute[i];
        }
    };
    private int day;
    private List<AllDayTenMinuteDetail> mList;
    private int month;
    private int year;

    public int describeContents() {
        return 0;
    }

    public AllDayTenMinute() {
    }

    protected AllDayTenMinute(Parcel parcel) {
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

    public void unPack(byte[] bArr) {
        if (bArr.length >= 3) {
            this.year = (bArr[0] & 255) | (bArr[1] << 8);
        }
        this.month = bArr[2] & 255;
        this.day = bArr[3] & 255;
        int length = (bArr.length - 8) / 6;
        this.mList = new ArrayList();
        for (int i = 0; i < length; i++) {
            AllDayTenMinuteDetail allDayTenMinuteDetail = new AllDayTenMinuteDetail();
            int i2 = i * 6;
            int unused = allDayTenMinuteDetail.activityTime = bArr[i2 + 8] & 255;
            int unused2 = allDayTenMinuteDetail.SBP = bArr[i2 + 9] & 255;
            int unused3 = allDayTenMinuteDetail.heart = bArr[i2 + 10] & 255;
            int unused4 = allDayTenMinuteDetail.DBP = bArr[i2 + 11] & 255;
            int unused5 = allDayTenMinuteDetail.step = ((bArr[i2 + 13] & 255) << 8) | (bArr[i2 + 12] & 255);
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
                    jSONObject2.put("activityTime", this.mList.get(i).activityTime);
                    jSONObject2.put("SBP", this.mList.get(i).SBP);
                    jSONObject2.put("heart", this.mList.get(i).heart);
                    jSONObject2.put("DBP", this.mList.get(i).DBP);
                    jSONObject2.put("step", this.mList.get(i).step);
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
        public int DBP;
        /* access modifiers changed from: private */
        public int SBP;
        /* access modifiers changed from: private */
        public int activityTime;
        /* access modifiers changed from: private */
        public int heart;
        /* access modifiers changed from: private */
        public int step;

        public int describeContents() {
            return 0;
        }

        public AllDayTenMinuteDetail() {
        }

        protected AllDayTenMinuteDetail(Parcel parcel) {
            this.activityTime = parcel.readInt();
            this.heart = parcel.readInt();
            this.step = parcel.readInt();
        }

        public int getActivityTime() {
            return this.activityTime;
        }

        public void setActivityTime(int i) {
            this.activityTime = i;
        }

        public int getHeart() {
            return this.heart;
        }

        public void setHeart(int i) {
            this.heart = i;
        }

        public int getStep() {
            return this.step;
        }

        public void setStep(int i) {
            this.step = i;
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

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.activityTime);
            parcel.writeInt(this.SBP);
            parcel.writeInt(this.heart);
            parcel.writeInt(this.DBP);
            parcel.writeInt(this.step);
        }
    }
}
