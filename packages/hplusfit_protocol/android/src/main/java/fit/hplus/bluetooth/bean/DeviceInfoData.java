package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;
import fit.hplus.bluetooth.util.HexUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfoData implements JsonLizable, Parcelable {
    public static final Parcelable.Creator<DeviceInfoData> CREATOR = new Parcelable.Creator<DeviceInfoData>() {
        public DeviceInfoData createFromParcel(Parcel parcel) {
            return new DeviceInfoData(parcel);
        }

        public DeviceInfoData[] newArray(int i) {
            return new DeviceInfoData[i];
        }
    };
    private String address;
    private int fontLibrary;
    private int functionNumber;
    private boolean has3DHGravitySensor;
    private boolean hasAirPressureSensor;
    private boolean hasBlood;
    private boolean hasDetailData;
    private boolean hasDial;
    private boolean hasECG;
    private boolean hasGPS;
    private boolean hasGenerationBloodAlgorithm;
    private boolean hasHRV;
    private boolean hasHeartDetection;
    private boolean hasHeartRate;
    private boolean hasKX023GravitySensor;
    private boolean hasNewGPS;
    private boolean hasOxygen;
    private boolean hasSecondGenerationBloodAlgorithm;
    private boolean hasSingleCircleData;
    private boolean hasSmartCard;
    private boolean hasSwim;
    private boolean hasTFT;
    private boolean hasTenMinuteData;
    private boolean hasThermometer;
    private boolean hasWeChatPublicNumber;
    private boolean hasWeatherForecast;
    private int productNumber;
    private boolean usePhoneGps;
    private String version;

    public int describeContents() {
        return 0;
    }

    public DeviceInfoData() {
    }

    protected DeviceInfoData(Parcel parcel) {
        this.productNumber = parcel.readInt();
        this.fontLibrary = parcel.readInt();
        boolean z = true;
        this.hasBlood = parcel.readByte() != 0;
        this.hasGPS = parcel.readByte() != 0;
        this.hasSwim = parcel.readByte() != 0;
        this.hasSmartCard = parcel.readByte() != 0;
        this.hasWeChatPublicNumber = parcel.readByte() != 0;
        this.hasGenerationBloodAlgorithm = parcel.readByte() != 0;
        this.hasSecondGenerationBloodAlgorithm = parcel.readByte() != 0;
        this.hasAirPressureSensor = parcel.readByte() != 0;
        this.hasTFT = parcel.readByte() != 0;
        this.hasSingleCircleData = parcel.readByte() != 0;
        this.has3DHGravitySensor = parcel.readByte() != 0;
        this.hasKX023GravitySensor = parcel.readByte() != 0;
        this.usePhoneGps = parcel.readByte() != 0;
        this.hasDetailData = parcel.readByte() != 0;
        this.hasTenMinuteData = parcel.readByte() != 0;
        this.hasHeartDetection = parcel.readByte() != 0;
        this.hasDial = parcel.readByte() != 0;
        this.hasWeatherForecast = parcel.readByte() != 0;
        this.version = parcel.readString();
        this.address = parcel.readString();
        this.functionNumber = parcel.readInt();
        this.hasHeartRate = parcel.readByte() != 0;
        this.hasECG = parcel.readByte() != 0;
        this.hasNewGPS = parcel.readByte() != 0;
        this.hasHRV = parcel.readByte() != 0;
        this.hasOxygen = parcel.readByte() != 0;
        this.hasThermometer = parcel.readByte() == 0 ? false : z;
    }

    public void unPack(byte[] bArr) {
        char[] charArray;
        char[] charArray2;
        char[] charArray3;
        if (bArr.length >= 3) {
            this.productNumber = ((bArr[1] << 8) & 65280) | (bArr[2] & 255);
        }
        if (bArr.length >= 4 && (charArray3 = HexUtil.byteToBit(bArr[3]).toCharArray()) != null && charArray3.length > 0) {
            this.hasHeartRate = charArray3[6] == '1';
            this.hasOxygen = charArray3[5] == '1';
            this.hasThermometer = charArray3[3] == '1';
        }
        if (bArr.length >= 5) {
            this.hasBlood = bArr[4] == 1;
        }
        if (bArr.length >= 6) {
            this.hasGPS = bArr[5] == 1;
        }
        if (bArr.length >= 7 && (charArray2 = HexUtil.byteToBit(bArr[6]).toCharArray()) != null && charArray2.length > 0) {
            this.hasSwim = charArray2[7] == '1';
            this.hasSmartCard = charArray2[6] == '1';
            this.hasWeChatPublicNumber = charArray2[5] == '1';
            this.hasGenerationBloodAlgorithm = charArray2[4] == '1';
            this.hasSecondGenerationBloodAlgorithm = charArray2[3] == '1';
            this.hasAirPressureSensor = charArray2[2] == '1';
            this.hasTFT = charArray2[1] == '1';
            this.hasSingleCircleData = charArray2[0] == '1';
        }
        if (bArr.length >= 8) {
            this.has3DHGravitySensor = bArr[7] == 1;
        }
        this.hasKX023GravitySensor = bArr[7] == 1;
        if (bArr.length >= 9 && (charArray = HexUtil.byteToBit(bArr[8]).toCharArray()) != null && charArray.length > 0) {
            this.usePhoneGps = charArray[7] == '1';
            this.hasDetailData = charArray[6] == '1';
            this.hasTenMinuteData = charArray[5] == '1';
            this.hasHRV = charArray[4] == '1';
            this.hasDial = charArray[3] == '1';
            this.hasWeatherForecast = charArray[2] == '1';
            this.hasECG = charArray[1] == '1';
            this.hasNewGPS = charArray[0] == '1';
        }
        if (bArr.length >= 11) {
            this.version = (((bArr[10] & 255) * 100) + (bArr[9] & 255)) + "";
        }
        if (bArr.length >= 17) {
            this.address = HexUtil.encodeHexStr(new byte[]{bArr[16]}) + ":" + HexUtil.encodeHexStr(new byte[]{bArr[15]}) + ":" + HexUtil.encodeHexStr(new byte[]{bArr[14]}) + ":" + HexUtil.encodeHexStr(new byte[]{bArr[13]}) + ":" + HexUtil.encodeHexStr(new byte[]{bArr[12]}) + ":" + HexUtil.encodeHexStr(new byte[]{bArr[11]});
        }
        if (bArr.length >= 18) {
            this.functionNumber = bArr[17];
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("productNumber", this.productNumber);
            jSONObject.put("fontLibrary", this.fontLibrary);
            jSONObject.put("hasBlood", this.hasBlood);
            jSONObject.put("hasGPS", this.hasGPS);
            jSONObject.put("hasSwim", this.hasSwim);
            jSONObject.put("hasSmartCard", this.hasSmartCard);
            jSONObject.put("hasWeChatPublicNumber", this.hasWeChatPublicNumber);
            jSONObject.put("hasGenerationBloodAlgorithm", this.hasGenerationBloodAlgorithm);
            jSONObject.put("hasSecondGenerationBloodAlgorithm", this.hasSecondGenerationBloodAlgorithm);
            jSONObject.put("hasAirPressureSensor", this.hasAirPressureSensor);
            jSONObject.put("hasTFT", this.hasTFT);
            jSONObject.put("hasSingleCircleData", this.hasSingleCircleData);
            jSONObject.put("has3DHGravitySensor", this.has3DHGravitySensor);
            jSONObject.put("hasKX023GravitySensor", this.hasKX023GravitySensor);
            jSONObject.put("usePhoneGps", this.usePhoneGps);
            jSONObject.put("hasDetailData", this.hasDetailData);
            jSONObject.put("hasTenMinuteData", this.hasTenMinuteData);
            jSONObject.put("hasHeartDetection", this.hasHeartDetection);
            jSONObject.put("hasDial", this.hasDial);
            jSONObject.put("hasWeatherForecast", this.hasWeatherForecast);
            jSONObject.put("version", this.version);
            jSONObject.put("macAddress", this.address);
            jSONObject.put("functionNumber", this.functionNumber);
            jSONObject.put("hasHeartRate", this.hasHeartRate);
            jSONObject.put("hasECG", this.hasECG);
            jSONObject.put("hasNewGPS", this.hasNewGPS);
            jSONObject.put("hasHRV", this.hasHRV);
            jSONObject.put("hasOxygen", this.hasOxygen);
            jSONObject.put("hasThermometer", this.hasThermometer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public int getProductNumber() {
        return this.productNumber;
    }

    public void setProductNumber(int i) {
        this.productNumber = i;
    }

    public int getFontLibrary() {
        return this.fontLibrary;
    }

    public void setFontLibrary(int i) {
        this.fontLibrary = i;
    }

    public boolean isHasBlood() {
        return this.hasBlood;
    }

    public void setHasBlood(boolean z) {
        this.hasBlood = z;
    }

    public boolean isHasGPS() {
        return this.hasGPS;
    }

    public void setHasGPS(boolean z) {
        this.hasGPS = z;
    }

    public boolean isHasSwim() {
        return this.hasSwim;
    }

    public void setHasSwim(boolean z) {
        this.hasSwim = z;
    }

    public boolean isHasSmartCard() {
        return this.hasSmartCard;
    }

    public void setHasSmartCard(boolean z) {
        this.hasSmartCard = z;
    }

    public boolean isHasWeChatPublicNumber() {
        return this.hasWeChatPublicNumber;
    }

    public void setHasWeChatPublicNumber(boolean z) {
        this.hasWeChatPublicNumber = z;
    }

    public boolean isHasGenerationBloodAlgorithm() {
        return this.hasGenerationBloodAlgorithm;
    }

    public void setHasGenerationBloodAlgorithm(boolean z) {
        this.hasGenerationBloodAlgorithm = z;
    }

    public boolean isHasSecondGenerationBloodAlgorithm() {
        return this.hasSecondGenerationBloodAlgorithm;
    }

    public void setHasSecondGenerationBloodAlgorithm(boolean z) {
        this.hasSecondGenerationBloodAlgorithm = z;
    }

    public boolean isHasAirPressureSensor() {
        return this.hasAirPressureSensor;
    }

    public void setHasAirPressureSensor(boolean z) {
        this.hasAirPressureSensor = z;
    }

    public boolean isHasTFT() {
        return this.hasTFT;
    }

    public void setHasTFT(boolean z) {
        this.hasTFT = z;
    }

    public boolean isHasSingleCircleData() {
        return this.hasSingleCircleData;
    }

    public void setHasSingleCircleData(boolean z) {
        this.hasSingleCircleData = z;
    }

    public boolean isHas3DHGravitySensor() {
        return this.has3DHGravitySensor;
    }

    public void setHas3DHGravitySensor(boolean z) {
        this.has3DHGravitySensor = z;
    }

    public boolean isHasKX023GravitySensor() {
        return this.hasKX023GravitySensor;
    }

    public void setHasKX023GravitySensor(boolean z) {
        this.hasKX023GravitySensor = z;
    }

    public boolean isUsePhoneGps() {
        return this.usePhoneGps;
    }

    public void setUsePhoneGps(boolean z) {
        this.usePhoneGps = z;
    }

    public boolean isHasDetailData() {
        return this.hasDetailData;
    }

    public void setHasDetailData(boolean z) {
        this.hasDetailData = z;
    }

    public boolean isHasTenMinuteData() {
        return this.hasTenMinuteData;
    }

    public void setHasTenMinuteData(boolean z) {
        this.hasTenMinuteData = z;
    }

    public boolean isHasHeartDetection() {
        return this.hasHeartDetection;
    }

    public void setHasHeartDetection(boolean z) {
        this.hasHeartDetection = z;
    }

    public boolean isHasDial() {
        return this.hasDial;
    }

    public void setHasDial(boolean z) {
        this.hasDial = z;
    }

    public boolean isHasWeatherForecast() {
        return this.hasWeatherForecast;
    }

    public void setHasWeatherForecast(boolean z) {
        this.hasWeatherForecast = z;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public int getFunctionNumber() {
        return this.functionNumber;
    }

    public void setFunctionNumber(int i) {
        this.functionNumber = i;
    }

    public boolean isHasNewGPS() {
        return this.hasNewGPS;
    }

    public void setHasNewGPS(boolean z) {
        this.hasNewGPS = z;
    }

    public boolean isHasHeartRate() {
        return this.hasHeartRate;
    }

    public void setHasHeartRate(boolean z) {
        this.hasHeartRate = z;
    }

    public boolean isHasECG() {
        return this.hasECG;
    }

    public void setHasECG(boolean z) {
        this.hasECG = z;
    }

    public boolean isHasHRV() {
        return this.hasHRV;
    }

    public void setHasHRV(boolean z) {
        this.hasHRV = z;
    }

    public boolean isHasOxygen() {
        return this.hasOxygen;
    }

    public void setHasOxygen(boolean z) {
        this.hasOxygen = z;
    }

    public boolean isHasThermometer() {
        return this.hasThermometer;
    }

    public void setHasThermometer(boolean z) {
        this.hasThermometer = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.productNumber);
        parcel.writeInt(this.fontLibrary);
        parcel.writeByte(this.hasBlood ? (byte) 1 : 0);
        parcel.writeByte(this.hasGPS ? (byte) 1 : 0);
        parcel.writeByte(this.hasSwim ? (byte) 1 : 0);
        parcel.writeByte(this.hasSmartCard ? (byte) 1 : 0);
        parcel.writeByte(this.hasWeChatPublicNumber ? (byte) 1 : 0);
        parcel.writeByte(this.hasGenerationBloodAlgorithm ? (byte) 1 : 0);
        parcel.writeByte(this.hasSecondGenerationBloodAlgorithm ? (byte) 1 : 0);
        parcel.writeByte(this.hasAirPressureSensor ? (byte) 1 : 0);
        parcel.writeByte(this.hasTFT ? (byte) 1 : 0);
        parcel.writeByte(this.hasSingleCircleData ? (byte) 1 : 0);
        parcel.writeByte(this.has3DHGravitySensor ? (byte) 1 : 0);
        parcel.writeByte(this.hasKX023GravitySensor ? (byte) 1 : 0);
        parcel.writeByte(this.usePhoneGps ? (byte) 1 : 0);
        parcel.writeByte(this.hasDetailData ? (byte) 1 : 0);
        parcel.writeByte(this.hasTenMinuteData ? (byte) 1 : 0);
        parcel.writeByte(this.hasHeartDetection ? (byte) 1 : 0);
        parcel.writeByte(this.hasDial ? (byte) 1 : 0);
        parcel.writeByte(this.hasWeatherForecast ? (byte) 1 : 0);
        parcel.writeString(this.version);
        parcel.writeString(this.address);
        parcel.writeInt(this.functionNumber);
        parcel.writeByte(this.hasHeartRate ? (byte) 1 : 0);
        parcel.writeByte(this.hasNewGPS ? (byte) 1 : 0);
        parcel.writeByte(this.hasECG ? (byte) 1 : 0);
        parcel.writeByte(this.hasHRV ? (byte) 1 : 0);
        parcel.writeByte(this.hasOxygen ? (byte) 1 : 0);
        parcel.writeByte(this.hasThermometer ? (byte) 1 : 0);
    }
}
