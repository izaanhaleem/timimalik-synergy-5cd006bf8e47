package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "ZeroDoseDetail")
public class ZeroDoseDetail extends Model {

    @Column(name="ZeroDoseDetailId")
    @SerializedName("ZeroDoseDetailId")
    @Expose
    private Integer zeroDoseDetailId;
    @Column(name="ZeroDoseMasterId")
    @SerializedName("ZeroDoseMasterId")
    @Expose
    private Integer zeroDoseMasterId;
    @Column(name="ChildName")
    @SerializedName("ChildName")
    @Expose
    private String childName;
    @Column(name="FatherName")
    @SerializedName("FatherName")
    @Expose
    private String fatherName;
    @Column(name="PhoneNo")
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @Column(name="DOB")
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @Column(name="Address")
    @SerializedName("Address")
    @Expose
    private String address;
    @Column(name="IsVaccinated")
    @SerializedName("IsVaccinated")
    @Expose
    private Boolean isVaccinated;
    @Column(name="CardNo")
    @SerializedName("CardNo")
    @Expose
    private String cardNo;
    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @Column(name="CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @Column(name="sync")
    @SerializedName("sync")
    @Expose
    private String sync;

    @Column(name="Latitude")
    @SerializedName("Latitude")
    @Expose
    private Double Latitude;

    @Column(name="Longitude")
    @SerializedName("Longitude")
    @Expose
    private Double Longitude;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public ZeroDoseDetail(String name, String fatherName,String dob,boolean vacc)

    {
        this.childName     = name;
        this.fatherName    = fatherName;
        this.dOB    = dob;
        this.isVaccinated    = vacc;
    }

    public ZeroDoseDetail() { }

    public Integer getZeroDoseDetailId() {
        return zeroDoseDetailId;
    }

    public void setZeroDoseDetailId(Integer zeroDoseDetailId) {
        this.zeroDoseDetailId = zeroDoseDetailId;
    }

    public Integer getZeroDoseMasterId() {
        return zeroDoseMasterId;
    }

    public void setZeroDoseMasterId(Integer zeroDoseMasterId) {
        this.zeroDoseMasterId = zeroDoseMasterId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsVaccinated() {
        return isVaccinated;
    }

    public void setIsVaccinated(Boolean isVaccinated) {
        this.isVaccinated = isVaccinated;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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


    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public Boolean getVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public static List<ZeroDoseDetail> getAll()

    {
        return new Select()
                .from(ZeroDoseDetail.class)
                .execute();
    }

    public static List<ZeroDoseDetail> getAllZeroDoseDetails(int id, String cb)

    {
        return new Select()
                .from(ZeroDoseDetail.class)
                .where("CreatedBy = ?",cb)
                .where("ZeroDoseMasterId = ?",id)
                .where("sync = ?","0")
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(ZeroDoseDetail.class)
                .set("sync = 1")
                .where("ZeroDoseMasterId = ?", id)
                .execute();
    }

}
