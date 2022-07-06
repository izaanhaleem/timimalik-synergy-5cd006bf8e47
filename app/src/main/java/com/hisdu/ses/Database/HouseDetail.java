package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Table(name = "HouseDetail")
public class HouseDetail extends Model {

    @Column(name="MastId")
    @SerializedName("MasterId")
    @Expose
    private Integer MasterId;
    @Column(name="subMastId")
    @SerializedName("subMasterId")
    @Expose
    private Integer subMasterId;
    @Column(name="HouseHoldProfileId")
    @SerializedName("HouseHoldProfileId")
    @Expose
    private String HouseHoldProfileId;
    @Column(name="Traveled")
    @SerializedName("Traveled")
    @Expose
    private String Traveled;
    @Column(name="TravelingAddress")
    @SerializedName("TravelingAddress")
    @Expose
    private String TravelingAddress;
    @Column(name="AFPCaseSearch")
    @SerializedName("AFPCaseSearch")
    @Expose
    private String AFPCaseSearch;
    @Column(name="AFPCaseNo")
    @SerializedName("AFPCaseNo")
    @Expose
    private String AFPCaseNo;
    @Column(name="HeadOfHouse")
    @SerializedName("HeadOfHouse")
    @Expose
    private String HeadOfHouse;
    @Column(name="NoOfChildren")
    @SerializedName("NoOfChildren")
    @Expose
    private String NoOfChildren;
    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;
    @Column(name="CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    public String CreatedOn;
    @Column(name="sync")
    @SerializedName("sync")
    @Expose
    public String sync;

    @Column(name="Latitude")
    @SerializedName("Latitude")
    @Expose
    public Double Latitude;

    @Column(name="Longitude")
    @SerializedName("Longitude")
    @Expose
    public Double Longitude;


    @SerializedName("AFPHouseChildDetails")
    @Expose
    private List<HouseChildDetail> houseChildDetails = new ArrayList<>();

    public List<HouseChildDetail> getHouseChildDetails() {
        return houseChildDetails;
    }

    public void setHouseChildDetails(List<HouseChildDetail> houseChildDetails) {
        this.houseChildDetails = houseChildDetails;
    }

    public Integer getSubMasterId() {
        return subMasterId;
    }

    public void setSubMasterId(Integer subMasterId) {
        this.subMasterId = subMasterId;
    }

    public Integer getMasterId() {
        return MasterId;
    }

    public void setMasterId(Integer masterId) {
        MasterId = masterId;
    }

    public String getHouseHoldProfileId() {
        return HouseHoldProfileId;
    }

    public void setHouseHoldProfileId(String houseHoldProfileId) {
        HouseHoldProfileId = houseHoldProfileId;
    }

    public String getTraveled() {
        return Traveled;
    }

    public void setTraveled(String traveled) {
        Traveled = traveled;
    }

    public String getTravelingAddress() {
        return TravelingAddress;
    }

    public void setTravelingAddress(String travelingAddress) {
        TravelingAddress = travelingAddress;
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

    public String getAFPCaseSearch() {
        return AFPCaseSearch;
    }

    public void setAFPCaseSearch(String AFPCaseSearch) {
        this.AFPCaseSearch = AFPCaseSearch;
    }

    public String getAFPCaseNo() {
        return AFPCaseNo;
    }

    public void setAFPCaseNo(String AFPCaseNo) {
        this.AFPCaseNo = AFPCaseNo;
    }

    public String getHeadOfHouse() {
        return HeadOfHouse;
    }

    public void setHeadOfHouse(String headOfHouse) {
        HeadOfHouse = headOfHouse;
    }

    public String getNoOfChildren() {
        return NoOfChildren;
    }

    public void setNoOfChildren(String noOfChildren) {
        NoOfChildren = noOfChildren;
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

    public HouseDetail(String HouseHoldProfileId, String Traveled, String TravelingAddress, String AFPCaseSearch, String AFPCaseNo, String HeadOfHouse, String NoOfChildren, List<HouseChildDetail> houseChildDetails,Double Lattitude,Double Longitude)

    {
        this.HouseHoldProfileId     = HouseHoldProfileId;
        this.Traveled               = Traveled;
        this.TravelingAddress       = TravelingAddress;
        this.AFPCaseSearch          = AFPCaseSearch;
        this.AFPCaseNo              = AFPCaseNo;
        this.HeadOfHouse            = HeadOfHouse;
        this.NoOfChildren           = NoOfChildren;
        this.houseChildDetails.clear();
        this.houseChildDetails.addAll(houseChildDetails);
        this.Latitude= Lattitude;
        this.Longitude= Longitude;
    }

    public HouseDetail(String HeadOfHouse, String NoOfChildren, String HouseHoldProfileId)

    {
        this.HeadOfHouse        = HeadOfHouse;
        this.NoOfChildren       = NoOfChildren;
        this.HouseHoldProfileId = HouseHoldProfileId;
    }

    public HouseDetail() { }

    public static List<HouseDetail> getAllHouse(String cb)

    {
        return new Select()
                .from(HouseDetail.class)
                .where("sync = ?","0")
                .where("MastId = ?",cb)
                .execute();
    }

    public static List<HouseDetail> getHouseDstail(String cb)

    {
        return new Select()
                .from(HouseDetail.class)
                .where("MastId = ?",cb)
                .execute();
    }

    public static List<HouseDetail> getAllHouseRecord(String cb)

    {
        return new Select()
                .from(HouseDetail.class)
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<HouseDetail> getAllHouse()

    {
        return new Select()
                .from(HouseDetail.class)
                .execute();
    }

    public static List<HouseDetail> getAllHouseMaster(Integer master)

    {
        return new Select()
                .from(HouseDetail.class)
                .where("MastId = ?",master)
                //.where("sync = ?","0")
                .execute();
    }

    public static List<HouseDetail> getAllHouseEPID(String afp,String cb)

    {
        return new Select()
                .from(HouseDetail.class)
                .where("AFPCaseNo = ?",afp)
                .where("CreatedBy = ?",cb)
                .execute();
    }


    public static void UpdateData (String id)

    {
        new Update(HouseDetail.class)
                .set("sync = 1")
                .where("MastId = ?", id)
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(HouseDetail.class).where("MastId = ?",id).execute();
    }


}
