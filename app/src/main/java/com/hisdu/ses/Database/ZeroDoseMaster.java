package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "ZeroDoseMaster")
public class ZeroDoseMaster extends Model {

    @Column(name="ZeroDoseMasterId")
    @SerializedName("ZeroDoseMasterId")
    @Expose
    private Integer zeroDoseMasterId;
    @Column(name="mastID")
    @SerializedName("mastID")
    @Expose
    private Integer mastID;
    @Column(name="CampaignMonth")
    @SerializedName("CampaignMonth")
    @Expose
    private String campaignMonth;
    @Column(name="SiaId")
    @SerializedName("SiaId")
    @Expose
    private Integer siaId;
    @Column(name="TotalZeroDoseRecorded")
    @SerializedName("TotalZeroDoseRecorded")
    @Expose
    private Integer totalZeroDoseRecorded;
    @Column(name="LocationCode")
    @SerializedName("LocationCode")
    @Expose
    private String locationCode;
    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @Column(name="CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("sync")
    @Column(name="sync")
    @Expose
    private String sync;


    private String CurrentAppVersion;

    @SerializedName("ZeroDoseDetails")
    @Expose
    private List<ZeroDoseDetail> zeroDoseDetails = null;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public Integer getMastID() {
        return mastID;
    }

    public void setMastID(Integer mastID) {
        this.mastID = mastID;
    }

    public Integer getZeroDoseMasterId() {
        return zeroDoseMasterId;
    }

    public void setZeroDoseMasterId(Integer zeroDoseMasterId) {
        this.zeroDoseMasterId = zeroDoseMasterId;
    }

    public String getCampaignMonth() {
        return campaignMonth;
    }

    public void setCampaignMonth(String campaignMonth) {
        this.campaignMonth = campaignMonth;
    }

    public Integer getSiaId() {
        return siaId;
    }

    public void setSiaId(Integer siaId) {
        this.siaId = siaId;
    }

    public Integer getTotalZeroDoseRecorded() {
        return totalZeroDoseRecorded;
    }

    public void setTotalZeroDoseRecorded(Integer totalZeroDoseRecorded) {
        this.totalZeroDoseRecorded = totalZeroDoseRecorded;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<ZeroDoseDetail> getZeroDoseDetails() {
        return zeroDoseDetails;
    }

    public String getCurrentAppVersion() {
        return CurrentAppVersion;
    }

    public void setCurrentAppVersion(String currentAppVersion) {
        this.CurrentAppVersion = currentAppVersion;
    }

    public void setZeroDoseDetails(List<ZeroDoseDetail> zeroDoseDetails) {
        this.zeroDoseDetails = zeroDoseDetails;
    }

    public static List<ZeroDoseMaster> getAll()

    {
        return new Select()
                .from(ZeroDoseMaster.class)
                .execute();
    }

    public static List<ZeroDoseMaster> getAllZeroDose(String cb,String mas)

    {
        return new Select()
                .from(ZeroDoseMaster.class)
                .where("CreatedBy = ?",cb)
                .where("mastID = ?",mas)
                .where("sync = ?","0")
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(ZeroDoseMaster.class)
                .set("sync = 1")
                .where("mastID = ?", id)
                .execute();
    }
}
