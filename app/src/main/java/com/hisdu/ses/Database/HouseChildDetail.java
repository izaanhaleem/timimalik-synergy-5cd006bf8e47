package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "HouseChildDetail")
public class HouseChildDetail extends Model {

    @Column(name="MastId")
    @SerializedName("MastId")
    @Expose
    public Integer MastId;
    @Column(name="Name")
    @SerializedName("Name")
    @Expose
    public String Name;
    @Column(name="AgeInMonths")
    @SerializedName("AgeInMonths")
    @Expose
    public String AgeInMonths;
    @Column(name="EpiCardAvailable")
    @SerializedName("EpiCardAvailable")
    @Expose
    public String EpiCardAvailable;
    @Column(name="RoutineOPVDoseProfileId")
    @SerializedName("RoutineOPVDoseProfileId")
    @Expose
    public String RoutineOPVDoseProfileId;
    @Column(name="ReceivedOPVLastTime")
    @SerializedName("ReceivedOPVLastTime")
    @Expose
    public String ReceivedOPVLastTime;
    @Column(name="OPVMissedReasonProfileId")
    @SerializedName("OPVMissedReasonProfileId")
    @Expose
    public String OPVMissedReasonProfileId;
    @Column(name="ReceivedOPVPriorLastTime")
    @SerializedName("ReceivedOPVPriorLastTime")
    @Expose
    public String ReceivedOPVPriorLastTime;
    @Column(name="PriorOPVMissedReasonProfileId")
    @SerializedName("PriorOPVMissedReasonProfileId")
    @Expose
    public String PriorOPVMissedReasonProfileId;
    @Column(name="IPVChild")
    @SerializedName("IPVChild")
    @Expose
    public String IPVChild;
    @Column(name="IPVChildTypeProfileId")
    @SerializedName("IPVChildTypeProfileId")
    @Expose
    public String IPVChildTypeProfileId;
    @Column(name="IPVMissedReason")
    @SerializedName("IPVMissedReason")
    @Expose
    public String IPVMissedReason;
    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public String CreatedBy;
    @Column(name="CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    public String CreatedOn;
    @Column(name="sync")
    @SerializedName("sync")
    @Expose
    public String sync;

    public HouseChildDetail(String Name, String AgeInMonths, String EpiCardAvailable, String RoutineOPVDoseProfileId,
                            String ReceivedOPVLastTime, String OPVMissedReasonProfileId, String ReceivedOPVPriorLastTime, String PriorOPVMissedReasonProfileId, String IPVChild,
                            String IPVChildTypeProfileId, String IPVMissedReason, String CreatedBy, String CreatedOn, String sync)

    {
        this.Name                      = Name;
        this.AgeInMonths               = AgeInMonths;
        this.EpiCardAvailable          = EpiCardAvailable;
        this.RoutineOPVDoseProfileId   = RoutineOPVDoseProfileId;
        this.ReceivedOPVLastTime       = ReceivedOPVLastTime;
        this.OPVMissedReasonProfileId  = OPVMissedReasonProfileId;
        this.ReceivedOPVPriorLastTime       = ReceivedOPVPriorLastTime;
        this.PriorOPVMissedReasonProfileId  = PriorOPVMissedReasonProfileId;
        this.IPVChild                  = IPVChild;
        this.IPVChildTypeProfileId     = IPVChildTypeProfileId;
        this.IPVMissedReason           = IPVMissedReason;
        this.CreatedBy                 = CreatedBy;
        this.CreatedOn                 = CreatedOn;
        this.sync                      = sync;
    }

    public HouseChildDetail(){}

    public String getPriorOPVMissedReasonProfileId() {
        return PriorOPVMissedReasonProfileId;
    }

    public void setPriorOPVMissedReasonProfileId(String priorOPVMissedReasonProfileId) {
        PriorOPVMissedReasonProfileId = priorOPVMissedReasonProfileId;
    }

    public Integer getMastId() {
        return MastId;
    }

    public void setMastId(Integer mastId) {
        MastId = mastId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAgeInMonths() {
        return AgeInMonths;
    }

    public void setAgeInMonths(String ageInMonths) {
        AgeInMonths = ageInMonths;
    }

    public String getEpiCardAvailable() {
        return EpiCardAvailable;
    }

    public void setEpiCardAvailable(String epiCardAvailable) {
        EpiCardAvailable = epiCardAvailable;
    }

    public String getRoutineOPVDoseProfileId() {
        return RoutineOPVDoseProfileId;
    }

    public void setRoutineOPVDoseProfileId(String routineOPVDoseProfileId) {
        RoutineOPVDoseProfileId = routineOPVDoseProfileId;
    }

    public String getReceivedOPVLastTime() {
        return ReceivedOPVLastTime;
    }

    public void setReceivedOPVLastTime(String receivedOPVLastTime) {
        ReceivedOPVLastTime = receivedOPVLastTime;
    }

    public String getOPVMissedReasonProfileId() {
        return OPVMissedReasonProfileId;
    }

    public void setOPVMissedReasonProfileId(String OPVMissedReasonProfileId) {
        this.OPVMissedReasonProfileId = OPVMissedReasonProfileId;
    }

    public String getReceivedOPVPriorLastTime() {
        return ReceivedOPVPriorLastTime;
    }

    public void setReceivedOPVPriorLastTime(String receivedOPVPriorLastTime) {
        ReceivedOPVPriorLastTime = receivedOPVPriorLastTime;
    }

    public String getIPVChild() {
        return IPVChild;
    }

    public void setIPVChild(String IPVChild) {
        this.IPVChild = IPVChild;
    }

    public String getIPVChildTypeProfileId() {
        return IPVChildTypeProfileId;
    }

    public void setIPVChildTypeProfileId(String IPVChildTypeProfileId) {
        this.IPVChildTypeProfileId = IPVChildTypeProfileId;
    }

    public String getIPVMissedReason() {
        return IPVMissedReason;
    }

    public void setIPVMissedReason(String IPVMissedReason) {
        this.IPVMissedReason = IPVMissedReason;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public static List<HouseChildDetail> getAllHouse(String cb)

    {
        return new Select()
                .from(HouseChildDetail.class)
                .where("sync = ?","0")
                .where("MastId = ?",cb)
                .execute();
    }

    public static List<HouseChildDetail> getAllHouseRecord(String cb)

    {
        return new Select()
                .from(HouseChildDetail.class)
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<HouseChildDetail> getAllHouse()

    {
        return new Select()
                .from(HouseChildDetail.class)
                .execute();
    }


    public static void UpdateData (String id)

    {
        new Update(HouseChildDetail.class)
                .set("sync = 1")
                .where("MastId = ?", id)
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(HouseChildDetail.class).where("MastId = ?",id).execute();
    }

}
