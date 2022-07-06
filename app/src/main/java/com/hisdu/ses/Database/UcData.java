package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "UcData")
public class UcData extends Model {
    @Column(name = "ServerID")
    @SerializedName("Id")
    @Expose
    public String ServerID;

    @Column(name = "Name")
    @SerializedName("Name")
    @Expose
    public String Name;

    @Column(name = "TehsilCode")
    @SerializedName("TehsilCode")
    @Expose
    public String TehsilCode;

    @Column(name = "UCCode")
    @SerializedName("UCCode")
    @Expose
    public Integer UCCode;

    @Column(name = "UCName")
    @SerializedName("UCName")
    @Expose
    public String UCName;

    @Column(name = "Latitude")
    @SerializedName("Latitude")
    @Expose
    public Double latitude;

    @Column(name = "Longitude")
    @SerializedName("Longitude")
    @Expose
    public Double Longitude;

    public String getServerID() {
        return ServerID;
    }

    public void setServerID(String serverID) {
        ServerID = serverID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTehsilCode() {
        return TehsilCode;
    }

    public void setTehsilCode(String tehsilCode) {
        TehsilCode = tehsilCode;
    }

    public Integer getUCCode() {
        return UCCode;
    }

    public void setUCCode(Integer UCCode) {
        this.UCCode = UCCode;
    }

    public String getUCName() {
        return UCName;
    }

    public void setUCName(String UCName) {
        this.UCName = UCName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public static List<UcData> getUCData(String UcCode) {
        return new Select().
                from(UcData.class).
                where("UCCode", UcCode).
                where("Name IS NOT NULL").execute();
    }

    public static List<UcData> getUcs(String code) {
        return new Select()

                .from(UcData.class)
                .where("TehsilCode = ?", code)
                .groupBy("UCName")
                .execute();
    }

    public static List<UcData> getUcCenter(String code, String name) {
        return new Select()
                .from(UcData.class)
                .where("TehsilCode = ?", code)
                .where("UCName = ?", name)
                .where("Name IS NOT NULL").execute();
    }

}
