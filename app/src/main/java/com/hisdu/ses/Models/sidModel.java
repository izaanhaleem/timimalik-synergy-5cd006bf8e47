package com.hisdu.ses.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.Indicator;

import java.util.List;

@Table(name = "SIDTable")
public class sidModel extends Model {

    @Column(name = "masterId")
    @SerializedName("Id")
    @Expose
    private Integer siaId;

    @Column(name = "Name")
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getSiaId() {
        return siaId;
    }

    public void setSiaId(Integer siaId) {
        this.siaId = siaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<sidModel> getAllSid() {
        return new Select()
                .from(sidModel.class)
                .execute();
    }
}
