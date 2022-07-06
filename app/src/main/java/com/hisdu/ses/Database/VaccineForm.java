package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "VaccineForm")
public class VaccineForm extends Model {

    @Column(name="Server_id")
    @SerializedName("Id")
    @Expose
    public Integer server_id;

    @Column(name="mastId")
    @SerializedName("InspectionId")
    @Expose
    public Integer mastId;

    @Column(name="Vaccine")
    @SerializedName("Vaccine")
    @Expose
    public String vaccine;

    @Column(name="Value")
    @SerializedName("Value")
    @Expose
    public String value;

    @Column(name="sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public Integer getMastId() {
        return mastId;
    }

    public void setMastId(Integer mastId) {
        this.mastId = mastId;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public static List<CheckList> getAllIndicators(String type)

    {
        return new Select()
                .from(CheckList.class)
                .where("CheckListType = ?",type)
                .execute();
    }
}
