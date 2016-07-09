package ru.innopolis.innoweather.data.entity;


import com.google.gson.annotations.SerializedName;

public class Main {
    private static final String TAG = "Main";

    private double temp;
    private double pressure;
    private double humidity;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double temp_max) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Main{");
        sb.append("temp=").append(temp);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append('}');
        return sb.toString();
    }
}
