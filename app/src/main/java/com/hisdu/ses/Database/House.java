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

@Table(name = "House")
public class House extends Model {

    @Column(name="MastId")
    @SerializedName("MastId")
    @Expose
    private Integer MastId;
    @Column(name="UcCode")
    @SerializedName("UcCode")
    @Expose
    private String ucCode;
    @Column(name="CaseEPIDNo")
    @SerializedName("CaseEPIDNo")
    @Expose
    private String CaseEPIDNo;
    @Column(name="HHClusterProfileId")
    @SerializedName("HHClusterProfileId")
    @Expose
    private String HHClusterProfileId;
    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;
    @Column(name="CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;
    @Column(name="sync")
    @SerializedName("sync")
    @Expose
    private String sync;
    @SerializedName("AFPHouseDetails")
    @Expose
    private List<HouseDetail> aFPHouseDetails = new ArrayList<>();

    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }

    public Integer getMastId() {
        return MastId;
    }

    public void setMastId(Integer mastId) {
        MastId = mastId;
    }

    public String getCaseEPIDNo() {
        return CaseEPIDNo;
    }

    public void setCaseEPIDNo(String caseEPIDNo) {
        CaseEPIDNo = caseEPIDNo;
    }

    public String getHHClusterProfileId() {
        return HHClusterProfileId;
    }

    public void setHHClusterProfileId(String HHClusterProfileId) {
        this.HHClusterProfileId = HHClusterProfileId;
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

    public List<HouseDetail> getaFPHouseDetails() {
        return aFPHouseDetails;
    }

    public void setaFPHouseDetails(List<HouseDetail> aFPHouseDetails) {
        this.aFPHouseDetails = aFPHouseDetails;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public static List<House> getAllHouse(String cb)

    {
        return new Select()
                .from(House.class)
                .where("sync = ?","0")
                .where("MastId = ?",cb)
                .execute();
    }

    public static List<House> getAllEPID(String afp)

    {
        return new Select()
                .from(House.class)
                .where("CaseEPIDNo = ?",afp)
                .execute();
    }

    public static List<House> getAllHouseEPID(String afp,String cb)

    {
        return new Select()
                .from(House.class)
                .where("CaseEPIDNo = ?",afp)
                .where("UcCode = ?",cb)
                .execute();
    }

    public static List<House> getAllHouseRecord(String cb)

    {
        return new Select()
                .from(House.class)
                .where("CreatedBy = ?",cb)
                .execute();
    }

    public static List<House> getAllHouse()

    {
        return new Select()
                .from(House.class)
                .execute();
    }


    public static void UpdateData (String id)

    {
        new Update(House.class)
                .set("sync = 1")
                .where("MastId = ?", id)
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(House.class).where("MastId = ?",id).execute();
    }

}
