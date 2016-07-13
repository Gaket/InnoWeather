package ru.innopolis.innoweather.data.entity.internals;


import com.google.gson.annotations.SerializedName;

public class Weather {
    private static final String TAG = "Weather";

    private int id;
    private String description;
    @SerializedName("main")
    private String shortInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Weather{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", shortInfo='").append(shortInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
