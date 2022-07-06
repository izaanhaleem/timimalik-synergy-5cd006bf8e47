package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "ZeroDoseMain")
public class ZeroDoseMain extends Model {

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

    @Column(name="phoneNumber")
    @SerializedName("TeamContactNo")
    @Expose
    private String phoneNumber;

    @Column(name="EntryPersonName")
    @SerializedName("EntryPersonName")
    @Expose
    private String EntryPersonName;

    @Column(name="Day")
    @SerializedName("Day")
    @Expose
    private String Day;

    @Column(name="TeamNo")
    @SerializedName("TeamNo")
    @Expose
    private String TeamNo;

    @Column(name="CampaignType")
    @SerializedName("CampaignType")
    @Expose
    private String CampaignType;
    @Column(name="EntryPersonDesignation")
    @SerializedName("EntryPersonDesignation")
    @Expose
    private String EntryPersonDesignation;
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
    @SerializedName("tblRegistrationChilds")
    @Expose
    private List<ZeroDoseChildModel> tblRegistrationChilds = null;


    @Column(name="CreatedUserLocationCode")
    @SerializedName("CreatedUserLocationCode")
    @Expose
    private String CreatedUserLocationCode;

    @SerializedName("CurrentAppVersion")
    @Expose
    private String CurrentAppVersion;

    public String getCreatedUserLocationCode() {
        return CreatedUserLocationCode;
    }

    public void setCreatedUserLocationCode(String createdUserLocationCode) {
        CreatedUserLocationCode = createdUserLocationCode;
    }

    public String getCurrentAppVersion() {
        return CurrentAppVersion;
    }

    public void setCurrentAppVersion(String currentAppVersion) {
        this.CurrentAppVersion = currentAppVersion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getEntryPersonName() {
        return EntryPersonName;
    }

    public void setEntryPersonName(String entryPersonName) {
        EntryPersonName = entryPersonName;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTeamNo() {
        return TeamNo;
    }

    public void setTeamNo(String teamNo) {
        TeamNo = teamNo;
    }

    public String getCampaignType() {
        return CampaignType;
    }

    public void setCampaignType(String campaignType) {
        CampaignType = campaignType;
    }

    public String getEntryPersonDesignation() {
        return EntryPersonDesignation;
    }

    public void setEntryPersonDesignation(String entryPersonDesignation) {
        EntryPersonDesignation = entryPersonDesignation;
    }

    public List<ZeroDoseChildModel> getTblRegistrationChilds() {
        return tblRegistrationChilds;
    }

    public void setTblRegistrationChilds(List<ZeroDoseChildModel> tblRegistrationChilds) {
        this.tblRegistrationChilds = tblRegistrationChilds;
    }

    public static List<ZeroDoseMain> getAll()

    {
        return new Select()
                .from(ZeroDoseMain.class)
                .execute();
    }

    public static List<ZeroDoseMain> getAllZeroDose(String cb)

    {
        return new Select()
                .from(ZeroDoseMain.class)
                .where("CreatedBy = ?",cb)
                .where("sync = ?","0")
                .execute();
    }

    public static List<ZeroDoseMain> getAllSync(String cb)

    {
        return new Select()
                .from(ZeroDoseMain.class)
                .where("CreatedBy = ?",cb)
                .where("sync = ?","1")
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(ZeroDoseMain.class)
                .set("sync = 1")
                .where("Id = ?", id)
                .execute();
    }
}
