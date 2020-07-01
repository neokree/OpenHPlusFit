package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SleepChartData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<SleepChartData> CREATOR = new Parcelable.Creator<SleepChartData>() {
        public SleepChartData createFromParcel(Parcel parcel) {
            return new SleepChartData(parcel);
        }

        public SleepChartData[] newArray(int i) {
            return new SleepChartData[i];
        }
    };
    private int dataCount;
    private int day;
    private List<SleepDetail> detailData;
    private int month;
    private int startHour;
    private int startMinute;
    private int status;
    private int year;

    public int describeContents() {
        return 0;
    }

    public SleepChartData() {
        this.detailData = new ArrayList();
    }

    protected SleepChartData(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.startHour = parcel.readInt();
        this.startMinute = parcel.readInt();
        this.dataCount = parcel.readInt();
        this.status = parcel.readInt();
    }

    public List<SleepDetail> getDetailData() {
        return this.detailData;
    }

    public void setDetailData(List<SleepDetail> list) {
        this.detailData = list;
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 2) {
            this.year = (bArr[0] & 255) | ((bArr[1] & 255) << 8);
        }
        if (bArr.length >= 3) {
            this.month = bArr[2];
        }
        if (bArr.length >= 4) {
            this.day = bArr[3];
        }
        if (bArr.length >= 5) {
            this.startHour = bArr[4];
        }
        if (bArr.length >= 6) {
            this.startMinute = bArr[5];
        }
        if (bArr.length >= 7) {
            this.dataCount = bArr[6];
        }
        if (bArr.length >= 8) {
            this.status = bArr[7];
        }
        if (bArr.length > 9) {
            int length = (bArr.length - 8) / 2;
            for (int i = 0; i < length; i++) {
                SleepDetail sleepDetail = new SleepDetail();
                int i2 = i * 2;
                byte b = (byte) (((bArr[i2 + 9] & 255) << 8) | (bArr[i2 + 8] & 255));
                int unused = sleepDetail.sleepType = b >> 12;
                int unused2 = sleepDetail.sleepTime = b & 255;
                this.detailData.add(sleepDetail);
            }
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("year", this.year);
            jSONObject.put("month", this.month);
            jSONObject.put("day", this.day);
            jSONObject.put("startHour", this.startHour);
            jSONObject.put("startMinute", this.startMinute);
            jSONObject.put(NotificationCompat.CATEGORY_STATUS, this.status);
            JSONArray jSONArray = new JSONArray();
            if (this.detailData != null && this.detailData.size() > 0) {
                for (int i = 0; i < this.detailData.size(); i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("sleepType", this.detailData.get(i).sleepType);
                    jSONObject2.put("sleepTime", this.detailData.get(i).sleepTime);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("detailData", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.startHour);
        parcel.writeInt(this.startMinute);
        parcel.writeInt(this.dataCount);
        parcel.writeInt(this.status);
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

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int i) {
        this.startHour = i;
    }

    public int getStartMinute() {
        return this.startMinute;
    }

    public void setStartMinute(int i) {
        this.startMinute = i;
    }

    public int getDataCount() {
        return this.dataCount;
    }

    public void setDataCount(int i) {
        this.dataCount = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public static class SleepDetail implements Parcelable {
        public static final Parcelable.Creator<SleepDetail> CREATOR = new Parcelable.Creator<SleepDetail>() {
            public SleepDetail createFromParcel(Parcel parcel) {
                return new SleepDetail(parcel);
            }

            public SleepDetail[] newArray(int i) {
                return new SleepDetail[i];
            }
        };
        /* access modifiers changed from: private */
        public int sleepTime;
        /* access modifiers changed from: private */
        public int sleepType;

        public int describeContents() {
            return 0;
        }

        public SleepDetail() {
        }

        protected SleepDetail(Parcel parcel) {
            this.sleepType = parcel.readInt();
            this.sleepTime = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.sleepType);
            parcel.writeInt(this.sleepTime);
        }

        public int getSleepType() {
            return this.sleepType;
        }

        public void setSleepType(int i) {
            this.sleepType = i;
        }

        public int getSleepTime() {
            return this.sleepTime;
        }

        public void setSleepTime(int i) {
            this.sleepTime = i;
        }
    }
}
