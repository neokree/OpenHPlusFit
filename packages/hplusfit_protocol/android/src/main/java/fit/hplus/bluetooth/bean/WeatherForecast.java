package fit.hplus.bluetooth.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherForecast implements Parcelable {
    public static final Parcelable.Creator<WeatherForecast> CREATOR = new Parcelable.Creator<WeatherForecast>() {
        public WeatherForecast createFromParcel(Parcel parcel) {
            return new WeatherForecast(parcel);
        }

        public WeatherForecast[] newArray(int i) {
            return new WeatherForecast[i];
        }
    };
    private int altitude;
    private int currentTemperature;
    private int lifeIndex;
    private int maxTemperature;
    private int minTemperature;
    private int pressure;
    private int ultravioletLight;
    private int weatherCode;
    private int winPower;
    private int windDirection;
    private int windSpeed;

    public int describeContents() {
        return 0;
    }

    public WeatherForecast() {
    }

    public int getWeatherCode() {
        return this.weatherCode;
    }

    public void setWeatherCode(int i) {
        this.weatherCode = i;
    }

    public int getWindDirection() {
        return this.windDirection;
    }

    public void setWindDirection(int i) {
        this.windDirection = i;
    }

    public int getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(int i) {
        this.windSpeed = i;
    }

    public int getCurrentTemperature() {
        return this.currentTemperature;
    }

    public void setCurrentTemperature(int i) {
        this.currentTemperature = i;
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

    public int getLifeIndex() {
        return this.lifeIndex;
    }

    public void setLifeIndex(int i) {
        this.lifeIndex = i;
    }

    public int getPressure() {
        return this.pressure;
    }

    public void setPressure(int i) {
        this.pressure = i;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public void setAltitude(int i) {
        this.altitude = i;
    }

    public int getUltravioletLight() {
        return this.ultravioletLight;
    }

    public void setUltravioletLight(int i) {
        this.ultravioletLight = i;
    }

    public int getWinPower() {
        return this.winPower;
    }

    public void setWinPower(int i) {
        this.winPower = i;
    }

    protected WeatherForecast(Parcel parcel) {
        this.weatherCode = parcel.readInt();
        this.windDirection = parcel.readInt();
        this.winPower = parcel.readInt();
        this.windSpeed = parcel.readInt();
        this.currentTemperature = parcel.readInt();
        this.maxTemperature = parcel.readInt();
        this.minTemperature = parcel.readInt();
        this.lifeIndex = parcel.readInt();
        this.pressure = parcel.readInt();
        this.altitude = parcel.readInt();
        this.ultravioletLight = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.weatherCode);
        parcel.writeInt(this.windDirection);
        parcel.writeInt(this.winPower);
        parcel.writeInt(this.windSpeed);
        parcel.writeInt(this.currentTemperature);
        parcel.writeInt(this.maxTemperature);
        parcel.writeInt(this.minTemperature);
        parcel.writeInt(this.lifeIndex);
        parcel.writeInt(this.pressure);
        parcel.writeInt(this.altitude);
        parcel.writeInt(this.ultravioletLight);
    }
}
