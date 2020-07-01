package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import fit.hplus.bluetooth.util.HexUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class RealSportData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<RealSportData> CREATOR = new Parcelable.Creator<RealSportData>() {
        public RealSportData createFromParcel(Parcel parcel) {
            return new RealSportData(parcel);
        }

        public RealSportData[] newArray(int i) {
            return new RealSportData[i];
        }
    };
    private int DBP;
    private int SBP;
    private int avtivetime;
    private int batteryValue;
    private int heart;
    private int realCalorie;
    private int realDistance;
    private int realStep;
    private float shelltemper = 0.0f;
    private int stillCalorie;
    private float temper = 0.0f;

    public int describeContents() {
        return 0;
    }

    public RealSportData() {
    }

    protected RealSportData(Parcel parcel) {
        this.realStep = parcel.readInt();
        this.realDistance = parcel.readInt();
        this.realCalorie = parcel.readInt();
        this.stillCalorie = parcel.readInt();
        this.batteryValue = parcel.readInt();
        this.heart = parcel.readInt();
        this.SBP = parcel.readInt();
        this.DBP = parcel.readInt();
        this.shelltemper = parcel.readFloat();
        this.temper = parcel.readFloat();
    }

    public void unPackNew(byte[] bArr) {
        if (bArr.length >= 3) {
            this.realStep = (bArr[1] & 255) | ((bArr[2] & 255) << 8);
        }
        if (bArr.length >= 5) {
            this.realDistance = (bArr[3] & 255) | (((bArr[4] & 255) << 8) * 10);
        }
        if (bArr.length >= 7) {
            this.realCalorie = (bArr[5] & 255) | ((bArr[6] & 255) << 8);
        }
        if (bArr.length >= 9) {
            this.stillCalorie = (bArr[7] & 255) | ((bArr[8] & 255) << 8);
        }
        if (bArr.length >= 10) {
            this.batteryValue = bArr[9] & 255;
        }
        if (bArr.length >= 11) {
            this.SBP = bArr[10] & 255;
        }
        if (bArr.length >= 12) {
            this.heart = bArr[11] & 255;
        }
        if (bArr.length >= 13) {
            this.DBP = bArr[12] & 255;
        }
        if (bArr.length >= 15) {
            this.avtivetime = (bArr[13] & 255) | ((bArr[14] & 255) << 8);
        }
        if (bArr.length >= 17) {
            this.shelltemper = Float.valueOf(HexUtil.setformat(1, ((float) ((bArr[15] & 255) | ((bArr[16] & 255) << 8))) / 100.0f)).floatValue();
        }
        if (bArr.length >= 19) {
            this.temper = Float.valueOf(HexUtil.setformat(1, ((float) (((bArr[18] & 255) << 8) | (bArr[17] & 255))) / 100.0f)).floatValue();
        }
    }

    public void unPack(byte[] bArr) {
        if (bArr.length >= 3) {
            this.realStep = (bArr[1] & 255) | ((bArr[2] & 255) << 8);
        }
        if (bArr.length >= 5) {
            this.realDistance = (bArr[3] & 255) | (((bArr[4] & 255) << 8) * 10);
        }
        if (bArr.length >= 7) {
            this.realCalorie = (bArr[5] & 255) | ((bArr[6] & 255) << 8);
        }
        if (bArr.length >= 9) {
            this.stillCalorie = (bArr[7] & 255) | ((bArr[8] & 255) << 8);
        }
        if (bArr.length >= 10) {
            this.batteryValue = bArr[9] & 255;
        }
        if (bArr.length >= 11) {
            this.SBP = bArr[10] & 255;
        }
        if (bArr.length >= 12) {
            this.heart = bArr[11] & 255;
        }
        if (bArr.length >= 13) {
            this.DBP = bArr[12] & 255;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("realStep", this.realStep);
            jSONObject.put("realDistance", this.realDistance);
            jSONObject.put("realCalorie", this.realCalorie);
            jSONObject.put("stillCalorie", this.stillCalorie);
            jSONObject.put("batteryValue", this.batteryValue);
            jSONObject.put("heart", this.heart);
            jSONObject.put("SBP", this.SBP);
            jSONObject.put("DBP", this.DBP);
            jSONObject.put("shelltemper", (double) this.shelltemper);
            jSONObject.put("temper", (double) this.temper);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public int getRealStep() {
        return this.realStep;
    }

    public void setRealStep(int i) {
        this.realStep = i;
    }

    public int getRealDistance() {
        return this.realDistance;
    }

    public void setRealDistance(int i) {
        this.realDistance = i;
    }

    public int getRealCalorie() {
        return this.realCalorie;
    }

    public void setRealCalorie(int i) {
        this.realCalorie = i;
    }

    public int getStillCalorie() {
        return this.stillCalorie;
    }

    public void setStillCalorie(int i) {
        this.stillCalorie = i;
    }

    public int getBatteryValue() {
        return this.batteryValue;
    }

    public void setBatteryValue(int i) {
        this.batteryValue = i;
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

    public int getAvtivetime() {
        return this.avtivetime;
    }

    public void setAvtivetime(int i) {
        this.avtivetime = i;
    }

    public float getShelltemper() {
        return this.shelltemper;
    }

    public void setShelltemper(float f) {
        this.shelltemper = f;
    }

    public float getTemper() {
        return this.temper;
    }

    public void setTemper(float f) {
        this.temper = f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.realStep);
        parcel.writeInt(this.realDistance);
        parcel.writeInt(this.realCalorie);
        parcel.writeInt(this.stillCalorie);
        parcel.writeInt(this.batteryValue);
        parcel.writeInt(this.heart);
        parcel.writeInt(this.SBP);
        parcel.writeInt(this.DBP);
        parcel.writeFloat(this.shelltemper);
        parcel.writeFloat(this.temper);
    }
}
