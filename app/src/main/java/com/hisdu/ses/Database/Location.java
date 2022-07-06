package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "Location")
public class Location extends Model
{
    @Column(name="server_id")
    @SerializedName("Id")
    @Expose
    public String server_id;

    @Column(name="Name")
    @SerializedName("Name")
    @Expose
    public String name;

    @Column(name="Type")
    @SerializedName("Type")
    @Expose
    public String type;

    @Column(name="ProvinceId")
    @SerializedName("ProvinceId")
    @Expose
    public String ProvinceId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public static List<Location> getLocations()

    {
        return new Select()
                .from(Location.class)
                .execute();
    }

    public static List<Location> getDivision(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'Division'")
                .where("server_id LIKE '" + code + "%\'")
                .execute();
    }

    public static List<Location> getAllDivision()

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'Division'")
                .execute();
    }

    public static List<Location> getDivisionByProvince(int code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'Division'")
                .where("ProvinceId LIKE '" + code + "%\'")
                .execute();
    }

    public static List<Location> getDistrict(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'District'")
                .where("server_id LIKE '" + code + "%\'")
                .execute();
    }

    public static List<Location> getTehsil(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'Tehsil'")
                .where("server_id LIKE '" + code + "%\'")
                .execute();
    }

   public static List<Location> getUC(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'UnionCouncil'")
                .where("server_id LIKE '" + code + "%\'")
                .execute();
    }

    public static Location getDistrictName(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'District'")
                .where("server_id LIKE '" + code + "%\'")
                .executeSingle();
    }

    public static Location getUCName(String code)

    {
        return new Select()
                .from(Location.class)
                .where("Type = 'UnionCouncil'")
                .where("server_id = ?", code)
                .executeSingle();
    }

}