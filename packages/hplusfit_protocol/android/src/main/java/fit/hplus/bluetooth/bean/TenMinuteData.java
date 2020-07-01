package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TenMinuteData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<TenMinuteData> CREATOR = new Parcelable.Creator<TenMinuteData>() {
        public TenMinuteData createFromParcel(Parcel parcel) {
            return new TenMinuteData(parcel);
        }

        public TenMinuteData[] newArray(int i) {
            return new TenMinuteData[i];
        }
    };
    private int DBP;
    private int SBP;
    private int heart;
    private int sequence;
    private int step;

    public int describeContents() {
        return 0;
    }

    public TenMinuteData() {
    }

    protected TenMinuteData(Parcel parcel) {
        this.heart = parcel.readInt();
        this.step = parcel.readInt();
        this.sequence = parcel.readInt();
        this.DBP = parcel.readInt();
        this.SBP = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 2) {
            this.heart = bArr[1] & 255;
        }
        if (bArr.length >= 4) {
            this.step = ((bArr[2] << 8) & 65280) | (bArr[3] & 255);
        }
        if (bArr.length >= 6) {
            this.sequence = ((bArr[4] << 8) & 65280) | (bArr[5] & 255);
        }
        if (bArr.length >= 7) {
            this.SBP = bArr[6] & 255;
        }
        if (bArr.length >= 8) {
            this.DBP = bArr[7] & 255;
        }
    }

    public static List<TenMinuteData> unPackList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        if (bArr.length >= 7) {
            int length = (bArr.length - 1) / 6;
            for (int i = 0; i < length; i++) {
                TenMinuteData tenMinuteData = new TenMinuteData();
                int i2 = i * 6;
                tenMinuteData.setHeart(bArr[i2 + 1] & 255);
                tenMinuteData.setStep(((bArr[i2 + 2] << 8) & 65280) | (bArr[i2 + 3] & 255));
                tenMinuteData.setSequence(bArr[i2 + 4] & 255);
                tenMinuteData.setSBP(bArr[i2 + 5] & 255);
                tenMinuteData.setDBP(bArr[i2 + 6] & 255);
                arrayList.add(tenMinuteData);
            }
        }
        return arrayList;
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

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int i) {
        this.sequence = i;
    }

    public int getDBP() {
        return this.DBP;
    }

    public void setDBP(int i) {
        this.DBP = i;
    }

    public int getSBP() {
        return this.SBP;
    }

    public void setSBP(int i) {
        this.SBP = i;
    }

    public JSONObject toJson() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("heart", this.heart);
            jSONObject.put("step", this.step);
            jSONObject.put("sequence", this.sequence);
            jSONObject.put("DBP", this.DBP);
            jSONObject.put("SBP", this.SBP);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jSONObject;
    }

    public static JSONArray toJsonList(List<TenMinuteData> list) {
        JSONArray jSONArray;
        try {
            jSONArray = new JSONArray();
            int i = 0;
            while (i < list.size()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("heart", list.get(i).getHeart());
                jSONObject.put("step", list.get(i).getStep());
                jSONObject.put("sequence", list.get(i).getSequence());
                jSONObject.put("DBP", list.get(i).getDBP());
                jSONObject.put("SBP", list.get(i).getSBP());
                jSONArray.put(jSONObject);
                i++;
            }
        } catch (JSONException e2) {
            jSONArray = null;
            e2.printStackTrace();
            return jSONArray;
        }
        return jSONArray;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.heart);
        parcel.writeInt(this.step);
        parcel.writeInt(this.sequence);
        parcel.writeInt(this.DBP);
        parcel.writeInt(this.SBP);
    }
}
