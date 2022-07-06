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

@Table(name = "SaveInspectionVaccination")
public class SaveInspectionVaccination extends Model

{
    @Column(name="Serverid")
    @SerializedName("Id")
    @Expose
    public Integer serverid;

    @Column(name="VaccinatorName")
    @SerializedName("VaccinatorName")
    @Expose
    public String vaccinatorName;

    @Column(name="LHVName")
    @SerializedName("LHVName")
    @Expose
    public String lHVName;

    @Column(name="ASVName")
    @SerializedName("ASVName")
    @Expose
    public String aSVName;

    @Column(name="SessionType")
    @SerializedName("SessionType")
    @Expose
    public String sessionType;

    @Column(name="ImageBase641")
    @SerializedName("ImageBase641")
    @Expose
    public String imageBase641;

    @Column(name="ImageBase642")
    @SerializedName("ImageBase642")
    @Expose
    public String imageBase642;

    @Column(name="ImageBase643")
    @SerializedName("ImageBase643")
    @Expose
    public String imageBase643;

    @Column(name="ImageBase644")
    @SerializedName("ImageBase644")
    @Expose
    public String imageBase644;

    @Column(name="ImageBase645")
    @SerializedName("ImageBase645")
    @Expose
    public String imageBase645;

    @Column(name="Latitude")
    @SerializedName("Latitude")
    @Expose
    public Double latitude;

    @Column(name="Longitude")
    @SerializedName("Longitude")
    @Expose
    public Double longitude;

    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;

    @Column(name="CreationDate")
    @SerializedName("VisitDate")
    @Expose
    public String creationDate;

    @Column(name="CreationOnlyDate")
    @Expose
    public String creationOnlyDate;

    @Column(name="DistrictCode")
    @SerializedName("DistrictCode")
    @Expose
    public String districtCode;

    @Column(name="TehsilCode")
    @SerializedName("TehsilCode")
    @Expose
    public String tehsilCode;

    @Column(name="UC")
    @SerializedName("UC")
    @Expose
    public String uC;

    @Column(name="HfmisCode")
    @SerializedName("HfmisCode")
    @Expose
    public String hfmisCode;

    @Column(name="HFId")
    @SerializedName("HFId")
    @Expose
    public String hFId;

    @Column(name="sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    @Column(name="IMEI")
    @SerializedName("IMEI")
    @Expose
    public String IMEI;

    @SerializedName("VaccinationInsepectionDetails")
    @Expose
    public List<SaveCheckListVaccination> Checklist;

//    @SerializedName("ClusterInfo")
//    @Expose
//    public List<ClusterForm> ClusterForm;

    public String gethFId() {
        return hFId;
    }

    public void sethFId(String hFId) {
        this.hFId = hFId;
    }

    public List<SaveCheckListVaccination> getChecklist() {
        return Checklist;
    }

    public void setChecklist(List<SaveCheckListVaccination> checklist) {
        Checklist = checklist;
    }

//    public List<com.hisdu.epi.Database.ClusterForm> getClusterForm() {
//        return ClusterForm;
//    }
//
//    public void setClusterForm(List<com.hisdu.epi.Database.ClusterForm> clusterForm) {
//        ClusterForm = clusterForm;
//    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getuC() {
        return uC;
    }

    public void setuC(String uC) {
        this.uC = uC;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getTehsilCode() {
        return tehsilCode;
    }

    public void setTehsilCode(String tehsilCode) {
        this.tehsilCode = tehsilCode;
    }

    public String getHfmisCode() {
        return hfmisCode;
    }

    public void setHfmisCode(String hfmisCode) {
        this.hfmisCode = hfmisCode;
    }

    public Integer getServerid() {
        return serverid;
    }

    public void setServerid(Integer serverid) {
        this.serverid = serverid;
    }

    public String getVaccinatorName() {
        return vaccinatorName;
    }

    public void setVaccinatorName(String vaccinatorName) {
        this.vaccinatorName = vaccinatorName;
    }

    public String getlHVName() {
        return lHVName;
    }

    public void setlHVName(String lHVName) {
        this.lHVName = lHVName;
    }

    public String getaSVName() {
        return aSVName;
    }

    public void setaSVName(String aSVName) {
        this.aSVName = aSVName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getImageBase641() {
        return imageBase641;
    }

    public void setImageBase641(String imageBase641) {
        this.imageBase641 = imageBase641;
    }

    public String getImageBase642() {
        return imageBase642;
    }

    public void setImageBase642(String imageBase642) {
        this.imageBase642 = imageBase642;
    }

    public String getImageBase643() {
        return imageBase643;
    }

    public void setImageBase643(String imageBase643) {
        this.imageBase643 = imageBase643;
    }

    public String getImageBase644() {
        return imageBase644;
    }

    public void setImageBase644(String imageBase644) {
        this.imageBase644 = imageBase644;
    }

    public String getImageBase645() {
        return imageBase645;
    }

    public void setImageBase645(String imageBase645) {
        this.imageBase645 = imageBase645;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getCreationOnlyDate() {
        return creationOnlyDate;
    }

    public void setCreationOnlyDate(String creationOnlyDate) {
        this.creationOnlyDate = creationOnlyDate;
    }

    public static List<SaveInspectionVaccination> getAllInspection(String cb)

    {
        return new Select()
                .from(SaveInspectionVaccination.class)
                .where("sync = ?","0")
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<SaveInspectionVaccination> getAllRecord(String cb,String creationDate)

    {
        return new Select()
                .from(SaveInspectionVaccination.class)
                .where("CreatedBy = ?",cb)
                .where("CreationOnlyDate = ?",creationDate)
                .execute();
    }

    public static List<SaveInspectionVaccination> getAllRecord(String cb)

    {
        return new Select()
                .from(SaveInspectionVaccination.class)
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<SaveInspectionVaccination> getAllins()

    {
        return new Select()
                .from(SaveInspectionVaccination.class)
                .execute();
    }


    public static void UpdateData (String id)

    {
        new Update(SaveInspectionVaccination.class)
                .set("sync = 1")
                .where("Id = ?", id)
                .execute();
    }

    public static void UpdateflagData ()

    {
        new Update(SaveInspectionVaccination.class)
                .set("sync = 0")
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(SaveInspectionVaccination.class).where("Id = ?",id).execute();
    }

}
