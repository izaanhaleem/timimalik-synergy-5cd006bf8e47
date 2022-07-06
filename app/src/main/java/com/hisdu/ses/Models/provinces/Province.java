package com.hisdu.ses.Models.provinces;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.Indicator;

import java.util.List;

@Table(name = "Provinces")
public class Province extends Model {

    @Column(name = "Server_Id")
    @SerializedName("ProvinceID")
    @Expose
    private Integer provinceID;

    @Column(name = "ProvinceName")
    @SerializedName("ProvinceName")
    @Expose
    private String provinceName;

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public static List<Province> getProvinces() {
        return new Select()
                .from(Province.class)
                .execute();
    }

}