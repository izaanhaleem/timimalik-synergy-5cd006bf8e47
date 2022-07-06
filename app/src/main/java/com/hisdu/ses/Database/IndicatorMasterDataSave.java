package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.CheckListImage;

import java.util.List;

@Table(name = "IndicatorsMasterTable")
public class IndicatorMasterDataSave extends Model {

    @Column(name = "mastID")
    @SerializedName("mastID")
    @Expose
    private Integer mastID;

    @Column(name = "AppModuleId")
    @SerializedName("AppModuleId")
    @Expose
    private Integer appModuleId;

    @Column(name = "StoreTypeId")
    @SerializedName("StoreTypeId")
    @Expose
    private String StoreTypeId;

    @Column(name = "StoreName")
    @SerializedName("StoreName")
    @Expose
    private String StoreName;

    @Column(name = "AreaName")
    @SerializedName("AreaName")
    @Expose
    private String areaName;

    @Column(name = "ProvinceId")
    @SerializedName("ProvinceId")
    @Expose
    private String ProvinceId;

    @Column(name = "LocationCode")
    @SerializedName("LocationCode")
    @Expose
    private String locationCode;

    @Column(name = "EPICenterId")
    @SerializedName("EPICenterId")
    @Expose
    private String EPICenterId;

    @Column(name = "EPICenterName")
    @SerializedName("EPICenterName")
    @Expose
    private String EPICenterName;

    @Column(name = "Latitude")
    @SerializedName("Latitude")
    @Expose
    private String Latitude;

    @Column(name = "Longitude")
    @SerializedName("Longitude")
    @Expose
    private String Longitude;

    @Column(name = "CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String SyncDateTime;

    @Column(name = "CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;

    @Column(name = "Settlement")
    @SerializedName("Settlement")
    @Expose
    private String settlement;

    @Column(name = "sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    @Column(name = "sessionsiteValue")
    @SerializedName("sessionsiteValue")
    @Expose
    public String sessionsiteValue;



    @Column(name = "Comments")
    @SerializedName("Comments")
    @Expose
    public String Comments;

    @Column(name = "Reason")
    @SerializedName("Reason")
    @Expose
    public String Reason;

    @Column(name = "OutReachSessionConducted")
    @SerializedName("OutReachSessionConducted")
    @Expose
    public Boolean istrue;

    @SerializedName("CheckListDetails")
    @Expose
    private List<CheckListSend> checkListDetails = null;

    @SerializedName("AFPMasters")
    @Expose
    private List<House> houses = null;

    @SerializedName("CheckListImages")
    @Expose
    private List<CheckListImage> checkListImageList = null;

    @SerializedName("zeroDose")
    @Expose
    private List<ZeroDoseMaster> zeroDose = null;

    public Boolean getIstrue() {
        return istrue;
    }

    public void setIstrue(Boolean istrue) {
        this.istrue = istrue;
    }

    public String getSessionsiteValue() {
        return sessionsiteValue;
    }
    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
    public void setSessionsiteValue(String sessionsiteValue) {
        this.sessionsiteValue = sessionsiteValue;
    }

    public List<ZeroDoseMaster> getZeroDose() {
        return zeroDose;
    }

    public void setZeroDose(List<ZeroDoseMaster> zeroDose) {
        this.zeroDose = zeroDose;
    }

    public List<CheckListImage> getCheckListImageList() {
        return checkListImageList;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setCheckListImageList(List<CheckListImage> checkListImageList) {
        this.checkListImageList = checkListImageList;
    }
    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Integer getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(Integer appModuleId) {
        this.appModuleId = appModuleId;
    }

    public String getStoreTypeId() {
        return StoreTypeId;
    }

    public void setStoreTypeId(String storeTypeId) {
        StoreTypeId = storeTypeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getEPICenterId() {
        return EPICenterId;
    }

    public void setEPICenterId(String EPICenterId) {
        this.EPICenterId = EPICenterId;
    }

    public String getEPICenterName() {
        return EPICenterName;
    }

    public void setEPICenterName(String EPICenterName) {
        this.EPICenterName = EPICenterName;
    }

    public String getLattitude() {
        return Latitude;
    }

    public void setLattitude(String lattitude) {
        Latitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getSyncDateTime() {
        return SyncDateTime;
    }

    public void setSyncDateTime(String syncDateTime) {
        SyncDateTime = syncDateTime;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public List<CheckListSend> getCheckListDetails() {
        return checkListDetails;
    }

    public void setCheckListDetails(List<CheckListSend> checkListDetails) {
        this.checkListDetails = checkListDetails;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public Integer getMastID() {
        return mastID;
    }

    public void setMastID(Integer mastID) {
        this.mastID = mastID;
    }

    public static List<IndicatorMasterDataSave> getAll(String cb, int appModuleId) {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .where("AppModuleId = ?", appModuleId)
                .where("CreatedBy = ?", cb)
                .where("sync = ?", 0)
                .execute();
    }

    public static List<IndicatorMasterDataSave> getAllInspection(String cb) {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .where("sync = ?", "1")
                .where("CreatedBy = ?", cb)
                .execute();
    }

    public static IndicatorMasterDataSave getAllMAsterInspection(String cb) {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .where("mastID = ?", cb)
                .executeSingle();
    }

    public static List<IndicatorMasterDataSave> getAllInspectionUnSynced(String cb) {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .where("sync = ?", "0")
                .where("CreatedBy = ?", cb)
                .execute();
    }

    public static List<IndicatorMasterDataSave> getAllins() {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .execute();
    }

    public static void DeleteData(Integer id) {
        new Delete().from(SaveChecklist.class).where("mastID = ?", id).execute();
    }

    public static List<IndicatorMasterDataSave> getAllRecord(String cb) {
        return new Select()
                .from(IndicatorMasterDataSave.class)
                .where("CreatedBy = ?", cb)
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(IndicatorMasterDataSave.class)
                .set("sync = 1")
                .where("mastID = ?", id)
                .execute();
    }
}