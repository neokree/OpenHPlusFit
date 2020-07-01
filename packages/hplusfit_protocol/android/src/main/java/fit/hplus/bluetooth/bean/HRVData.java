package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HRVData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<HRVData> CREATOR = new Parcelable.Creator<HRVData>() {
        public HRVData createFromParcel(Parcel parcel) {
            return new HRVData(parcel);
        }

        public HRVData[] newArray(int i) {
            return new HRVData[i];
        }
    };
    private int BPM;
    private int HFnrom;
    private int IBI;
    private List<Integer> IBIIntervals;
    private int LF;
    private int LFnorm;
    private int Pnn50;
    private int SDNN;
    private int SDSD;
    private int VLF;
    private int breath;
    private int day;
    private int hour;
    private int minute;
    private int month;
    private int pNN20;
    private int power;
    private int rMMSD;
    private int second;
    private int signal;
    private int year;

    public int describeContents() {
        return 0;
    }

    public HRVData() {
    }

    protected HRVData(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
        this.BPM = parcel.readInt();
        this.IBI = parcel.readInt();
        this.SDNN = parcel.readInt();
        this.rMMSD = parcel.readInt();
        this.pNN20 = parcel.readInt();
        this.Pnn50 = parcel.readInt();
        this.LFnorm = parcel.readInt();
        this.HFnrom = parcel.readInt();
        this.LF = parcel.readInt();
        this.breath = parcel.readInt();
        this.SDSD = parcel.readInt();
        this.signal = parcel.readInt();
        this.power = parcel.readInt();
        this.VLF = parcel.readInt();
    }

    public void unPack(byte[] bArr) {
        this.year = (bArr[0] & 255) | (bArr[1] << 8);
        this.month = bArr[2] & 255;
        this.day = bArr[3] & 255;
        this.hour = bArr[4] & 255;
        this.minute = bArr[5] & 255;
        this.second = bArr[6] & 255;
        this.BPM = bArr[7] & 255;
        this.IBI = ((bArr[8] << 8) & 65280) | (bArr[9] & 255);
        this.SDNN = ((bArr[10] << 8) & 65280) | (bArr[11] & 255);
        this.rMMSD = ((bArr[12] << 8) & 65280) | (bArr[13] & 255);
        this.pNN20 = bArr[14] & 255;
        this.Pnn50 = bArr[15] & 255;
        this.LFnorm = bArr[16] & 255;
        this.HFnrom = bArr[17] & 255;
        this.LF = ((bArr[18] << 8) & 65280) | (bArr[19] & 255);
        this.breath = ((bArr[20] << 8) & 65280) | (bArr[21] & 255);
        this.SDSD = ((bArr[22] << 8) & 65280) | (bArr[23] & 255);
        this.signal = bArr[24] & 255;
        this.power = ((bArr[25] << 8) & 65280) | (bArr[26] & 255);
        this.VLF = ((bArr[27] << 8) & 65280) | (bArr[28] & 255);
        int length = (bArr.length - 34) / 2;
        this.IBIIntervals = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int i3 = i2 + 34;
            if (i3 < bArr.length) {
                byte b = (byte) ((bArr[i2 + 35] & 255) | ((bArr[i3] << 8) & 65280));
                this.IBIIntervals.add(Integer.valueOf(b == 0 ? 0 : 60000 / b));
            }
        }
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

    public int getBPM() {
        return this.BPM;
    }

    public void setBPM(int i) {
        this.BPM = i;
    }

    public int getIBI() {
        return this.IBI;
    }

    public void setIBI(int i) {
        this.IBI = i;
    }

    public int getSDNN() {
        return this.SDNN;
    }

    public void setSDNN(int i) {
        this.SDNN = i;
    }

    public int getrMMSD() {
        return this.rMMSD;
    }

    public void setrMMSD(int i) {
        this.rMMSD = i;
    }

    public int getpNN20() {
        return this.pNN20;
    }

    public void setpNN20(int i) {
        this.pNN20 = i;
    }

    public int getPnn50() {
        return this.Pnn50;
    }

    public void setPnn50(int i) {
        this.Pnn50 = i;
    }

    public int getLFnorm() {
        return this.LFnorm;
    }

    public void setLFnorm(int i) {
        this.LFnorm = i;
    }

    public int getHFnrom() {
        return this.HFnrom;
    }

    public void setHFnrom(int i) {
        this.HFnrom = i;
    }

    public int getLF() {
        return this.LF;
    }

    public void setLF(int i) {
        this.LF = i;
    }

    public int getBreath() {
        return this.breath;
    }

    public void setBreath(int i) {
        this.breath = i;
    }

    public int getSDSD() {
        return this.SDSD;
    }

    public void setSDSD(int i) {
        this.SDSD = i;
    }

    public int getSignal() {
        return this.signal;
    }

    public void setSignal(int i) {
        this.signal = i;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int i) {
        this.power = i;
    }

    public int getVLF() {
        return this.VLF;
    }

    public void setVLF(int i) {
        this.VLF = i;
    }

    public List<Integer> getIBIIntervals() {
        return this.IBIIntervals;
    }

    public void setIBIIntervals(List<Integer> list) {
        this.IBIIntervals = list;
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
            jSONObject.put("BPM", this.BPM);
            jSONObject.put("IBI", this.IBI);
            jSONObject.put("SDNN", this.SDNN);
            jSONObject.put("rMMSD", this.rMMSD);
            jSONObject.put("pNN20", this.pNN20);
            jSONObject.put("Pnn50", this.Pnn50);
            jSONObject.put("LFnorm", this.LFnorm);
            jSONObject.put("HFnrom", this.HFnrom);
            jSONObject.put("LF", this.LF);
            jSONObject.put("breath", this.breath);
            jSONObject.put("SDSD", this.SDSD);
            jSONObject.put("signal", this.signal);
            jSONObject.put("power", this.power);
            jSONObject.put("VLF", this.VLF);
            JSONArray jSONArray = new JSONArray();
            if (this.IBIIntervals != null && this.IBIIntervals.size() > 0) {
                for (int i = 0; i < this.IBIIntervals.size(); i++) {
                    jSONArray.put(this.IBIIntervals.get(i));
                }
            }
            jSONObject.put("IBIIntervals", jSONArray);
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
        parcel.writeInt(this.BPM);
        parcel.writeInt(this.IBI);
        parcel.writeInt(this.SDNN);
        parcel.writeInt(this.rMMSD);
        parcel.writeInt(this.pNN20);
        parcel.writeInt(this.Pnn50);
        parcel.writeInt(this.LFnorm);
        parcel.writeInt(this.HFnrom);
        parcel.writeInt(this.LF);
        parcel.writeInt(this.breath);
        parcel.writeInt(this.SDSD);
        parcel.writeInt(this.signal);
        parcel.writeInt(this.power);
        parcel.writeInt(this.VLF);
    }
}
