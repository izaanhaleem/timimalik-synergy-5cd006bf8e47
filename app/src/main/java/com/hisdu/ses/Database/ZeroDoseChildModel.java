package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "ZeroDoseChild")
public class ZeroDoseChildModel extends Model {

    @Column(name="ZeroDoseDetailId")
    @SerializedName("ChildId")
    @Expose
    private Integer zeroDoseDetailId;
    @Column(name="ZeroDoseMasterId")
    @SerializedName("ZeroDoseMasterId")
    @Expose
    private Integer zeroDoseMasterId;

    @Column(name="HouseNo")
    @SerializedName("HouseNo")
    @Expose
    private String HouseNo;

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
    @Column(name="Age")
    @SerializedName("Age")
    @Expose
    private String Age;
    @Column(name="AgeType")
    @SerializedName("AgeType")
    @Expose
    private String AgeType;

    @Column(name="Address")
    @SerializedName("Address")
    @Expose
    private String address;

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

    @Column(name="Division")
    @SerializedName("Division")
    @Expose
    private String Division;

    @Column(name="District")
    @SerializedName("District")
    @Expose
    private String District;

    @Column(name="Tehsil")
    @SerializedName("Tehsil")
    @Expose
    private String Tehsil;

    @Column(name="UC")
    @SerializedName("UC")
    @Expose
    private String UC;


    @Column(name="HRMP")
    @SerializedName("HRMP")
    @Expose
    private String HRMP;


    @Column(name="WithinDistrict")
    @SerializedName("WithinDistrict")
    @Expose
    private String WithinDistrict;

    @Column(name="LocationCode")
    @SerializedName("LocationCode")
    @Expose
    private String LocationCode;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public ZeroDoseChildModel(String name, String fatherName, String dob, boolean vacc)

    {
        this.childName     = name;
        this.fatherName    = fatherName;

    }

    public ZeroDoseChildModel() { }

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

    public String getHRMP() {
        return HRMP;
    }

    public void setHRMP(String HRMP) {
        this.HRMP = HRMP;
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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }


    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getTehsil() {
        return Tehsil;
    }

    public void setTehsil(String tehsil) {
        Tehsil = tehsil;
    }

    public String getUC() {
        return UC;
    }

    public void setUC(String UC) {
        this.UC = UC;
    }

    public String getWithinDistrict() {
        return WithinDistrict;
    }

    public void setWithinDistrict(String withinDistrict) {
        WithinDistrict = withinDistrict;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getAgeType() {
        return AgeType;
    }

    public void setAgeType(String ageType) {
        AgeType = ageType;
    }

    public static List<ZeroDoseChildModel> getAll()

    {
        return new Select()
                .from(ZeroDoseChildModel.class)
                .execute();
    }

    public static List<ZeroDoseChildModel> getAllZeroDoseDetails(long id, String cb)

    {
        return new Select()
                .from(ZeroDoseChildModel.class)
                .where("CreatedBy = ?",cb)
                .where("ZeroDoseMasterId = ?",id)
                .where("sync = ?","0")
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(ZeroDoseChildModel.class)
                .set("sync = 1")
                .where("ZeroDoseMasterId = ?", id)
                .execute();
    }

}
