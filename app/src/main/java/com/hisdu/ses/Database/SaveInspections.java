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

@Table(name = "SaveInspections")
public class SaveInspections extends Model

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

    @Column(name="ImageBase64")
    @SerializedName("ImageBase64")
    @Expose
    public String imageBase64;

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

    @SerializedName("InsepectionDetails")
    @Expose
    public List<SaveChecklist> Checklist;

    @SerializedName("ClusterInfo")
    @Expose
    public List<ClusterForm> ClusterForm;

    public String gethFId() {
        return hFId;
    }

    public void sethFId(String hFId) {
        this.hFId = hFId;
    }

    public List<SaveChecklist> getChecklist() {
        return Checklist;
    }

    public void setChecklist(List<SaveChecklist> checklist) {
        Checklist = checklist;
    }

    public List<com.hisdu.ses.Database.ClusterForm> getClusterForm() {
        return ClusterForm;
    }

    public void setClusterForm(List<com.hisdu.ses.Database.ClusterForm> clusterForm) {
        ClusterForm = clusterForm;
    }

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

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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

    public static List<SaveInspections> getAllInspection(String cb)

    {
        return new Select()
                .from(SaveInspections.class)
                .where("sync = ?","0")
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static SaveInspections getAllMasterInspection(String master)

    {
        return new Select()
                .from(SaveInspections.class)
                .where("Id = ?",master)
                .executeSingle();
    }

    public static List<SaveInspections> getAllRecord(String cb)

    {
        return new Select()
                .from(SaveInspections.class)
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<SaveInspections> getAllins()

    {
        return new Select()
                .from(SaveInspections.class)
                .execute();
    }


    public static void UpdateData (String id)

    {
        new Update(SaveInspections.class)
                .set("sync = 1")
                .where("Id = ?", id)
                .execute();
    }

    public static void UpdateflagData ()

    {
        new Update(SaveInspections.class)
                .set("sync = 0")
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(SaveInspections.class).where("Id = ?",id).execute();
    }

}
