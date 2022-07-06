package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "ClusterForm")
public class ClusterForm extends Model {

    @Column(name="mastId")
    @SerializedName("InspectionId")
    @Expose
    public Integer mastId;

    @Column(name="Server_id")
    @SerializedName("Id")
    @Expose
    public Integer server_id;

    @Column(name="CardNo")
    @SerializedName("CardNo")
    @Expose
    public String cardNo;

    @Column(name="ChildName")
    @SerializedName("ChildName")
    @Expose
    public String childName;

    @Column(name="FatherName")
    @SerializedName("FatherName")
    @Expose
    public String fatherName;

    @Column(name="ContactNo")
    @SerializedName("ContactNo")
    @Expose
    public String contactNo;

    @Column(name="DateOfBirth")
    @SerializedName("DateOfBirth")
    @Expose
    public String dateOfBirth;

    @Column(name="Age")
    @SerializedName("Age")
    @Expose
    public String age;

    @Column(name="BCG")
    @SerializedName("Bcg")
    @Expose
    public String bCG;

    @Column(name="HepBBD")
    @SerializedName("HepBBD")
    @Expose
    public String hep_B_BD;

    @Column(name="OPV0")
    @SerializedName("OPV0")
    @Expose
    public String oPV_0;

    @Column(name="PentaI")
    @SerializedName("PentaI")
    @Expose
    public String penta_I;

    @Column(name="PCV10I")
    @SerializedName("PCV10I")
    @Expose
    public String pCV_10_I;

    @Column(name="OPVI")
    @SerializedName("OPVI")
    @Expose
    public String oPV_I;

    @Column(name="RotaI")
    @SerializedName("RotaI")
    @Expose
    public String rota_I;

    @Column(name="PentaII")
    @SerializedName("PentaII")
    @Expose
    public String penta_II;

    @Column(name="PCV10II")
    @SerializedName("PCV10II")
    @Expose
    public String pCV_10_II;

    @Column(name="OPVII")
    @SerializedName("OPVII")
    @Expose
    public String oPV_II;

    @Column(name="RotaII")
    @SerializedName("RotaII")
    @Expose
    public String rota_II;

    @Column(name="PentaIII")
    @SerializedName("PentaIII")
    @Expose
    public String penta_III;

    @Column(name="PCV10III")
    @SerializedName("PCV10III")
    @Expose
    public String pCV_10_III;

    @Column(name="OPVIII")
    @SerializedName("OPVIII")
    @Expose
    public String oPV_III;

    @Column(name="IPV")
    @SerializedName("IPV")
    @Expose
    public String iPV;

    @Column(name="MeaslesI")
    @SerializedName("MeaslesI")
    @Expose
    public String measles_I;

    @Column(name="MeaslesII")
    @SerializedName("MeaslesII")
    @Expose
    public String measles_II;

    @Column(name="sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;

    @Column(name="VisitDateTime")
    @SerializedName("VisitDateTime")
    @Expose
    public String visitDateTime;

    @Column(name="VaccinationStatus")
    @SerializedName("VaccinationStatus")
    @Expose
    public String vaccinationStatus;

    @Column(name="BcgDate")
    @SerializedName("BcgDate")
    @Expose
    public String BcgDate;

    @Column(name="HepBBDDate")
    @SerializedName("HepBBDDate")
    @Expose
    public String HepBBDDate;

    @Column(name="OPV0Date")
    @SerializedName("OPV0Date")
    @Expose
    public String OPV0Date;

    @Column(name="PentaIDate")
    @SerializedName("PentaIDate")
    @Expose
    public String PentaIDate;

    @Column(name="PCV10IDate")
    @SerializedName("PCV10IDate")
    @Expose
    public String PCV10IDate;

    @Column(name="OPVIDate")
    @SerializedName("OPVIDate")
    @Expose
    public String OPVIDate;

    @Column(name="RotaIDate")
    @SerializedName("RotaIDate")
    @Expose
    public String RotaIDate;

    @Column(name="PentaIIDate")
    @SerializedName("PentaIIDate")
    @Expose
    public String PentaIIDate;

    @Column(name="PCV10IIDate")
    @SerializedName("PCV10IIDate")
    @Expose
    public String PCV10IIDate;

    @Column(name="OPVIIDate")
    @SerializedName("OPVIIDate")
    @Expose
    public String OPVIIDate;

    @Column(name="RotaIIDate")
    @SerializedName("RotaIIDate")
    @Expose
    public String RotaIIDate;

    @Column(name="PentaIIIDate")
    @SerializedName("PentaIIIDate")
    @Expose
    public String PentaIIIDate;

    @Column(name="PCV10IIIDate")
    @SerializedName("PCV10IIIDate")
    @Expose
    public String PCV10IIIDate;

    @Column(name="OPVIIIDate")
    @SerializedName("OPVIIIDate")
    @Expose
    public String OPVIIIDate;

    @Column(name="IPVDate")
    @SerializedName("IPVDate")
    @Expose
    public String IPVDate;

    @Column(name="MeaslesIDate")
    @SerializedName("MeaslesIDate")
    @Expose
    public String MeaslesIDate;

    @Column(name="MeaslesIIDate")
    @SerializedName("MeaslesIIDate")
    @Expose
    public String MeaslesIIDate;

    @Column(name="Remarks")
    @SerializedName("Remarks")
    @Expose
    public String Remarks;


    public ClusterForm (Integer mastId, String cardNo, String childName, String fatherName, String contactNo, String dob,String age, String bCG, String hep_B_BD, String oPV_0, String penta_I,
                        String pCV_10_I, String oPV_I, String rota_I, String penta_II, String pCV_10_II, String oPV_II, String rota_II, String penta_III, String pCV_10_III,
                        String oPV_III, String iPV, String measles_I, String measles_II, String createdBy, String visitDateTime, String vaccinationStatus
            , String BcgDate
            , String HepBBDDate
            , String OPV0Date
            , String PentaIDate
            , String PCV10IDate
            , String OPVIDate
            , String RotaIDate
            , String PentaIIDate
            , String PCV10IIDate
            , String OPVIIDate
            , String RotaIIDate
            , String PentaIIIDate
            , String PCV10IIIDate
            , String OPVIIIDate
            , String IPVDate
            , String MeaslesIDate
            , String MeaslesIIDate
            , String Remarks
    )

    {
        this.mastId              = mastId;
        this.cardNo              = cardNo;
        this.childName           = childName;
        this.fatherName          = fatherName;
        this.contactNo           = contactNo;
        this.age                 = age;
        this.dateOfBirth         = dob;
        this.bCG                 = bCG;
        this.hep_B_BD            = hep_B_BD;
        this.oPV_0               = oPV_0;
        this.penta_I             = penta_I;
        this.pCV_10_I            = pCV_10_I;
        this.oPV_I               = oPV_I;
        this.rota_I              = rota_I;
        this.penta_II            = penta_II;
        this.pCV_10_II           = pCV_10_II;
        this.oPV_II              = oPV_II;
        this.rota_II             = rota_II;
        this.penta_III           = penta_III;
        this.pCV_10_III          = pCV_10_III;
        this.oPV_III             = oPV_III;
        this.iPV                 = iPV;
        this.measles_I           = measles_I;
        this.measles_II          = measles_II;
        this.createdBy           = createdBy;
        this.visitDateTime       = visitDateTime;
        this.vaccinationStatus   = vaccinationStatus;
        this.BcgDate             = BcgDate;
        this.HepBBDDate          = HepBBDDate;
        this.OPV0Date            = OPV0Date;
        this.PentaIDate          = PentaIDate;
        this.PCV10IDate          = PCV10IDate;
        this.OPVIDate            = OPVIDate;
        this.RotaIDate           = RotaIDate;
        this.PentaIIDate         = PentaIIDate;
        this.PCV10IIDate         = PCV10IIDate;
        this.OPVIIDate           = OPVIIDate;
        this.RotaIIDate          = RotaIIDate;
        this.PentaIIIDate        = PentaIIIDate;
        this.PCV10IIIDate        = PCV10IIIDate;
        this.OPVIIIDate          = OPVIIIDate;
        this.IPVDate             = IPVDate;
        this.MeaslesIDate        = MeaslesIDate;
        this.MeaslesIIDate       = MeaslesIIDate;
        this.Remarks             = Remarks;
    }

    public ClusterForm (String childName, String fatherName, String age)

    {
        this.childName    = childName;
        this.fatherName   = fatherName;
        this.age          = age;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public ClusterForm () { }

    public String getbCG() {
        return bCG;
    }

    public void setbCG(String bCG) {
        this.bCG = bCG;
    }

    public String getHep_B_BD() {
        return hep_B_BD;
    }

    public void setHep_B_BD(String hep_B_BD) {
        this.hep_B_BD = hep_B_BD;
    }

    public String getoPV_0() {
        return oPV_0;
    }

    public void setoPV_0(String oPV_0) {
        this.oPV_0 = oPV_0;
    }

    public String getPenta_I() {
        return penta_I;
    }

    public void setPenta_I(String penta_I) {
        this.penta_I = penta_I;
    }

    public String getpCV_10_I() {
        return pCV_10_I;
    }

    public void setpCV_10_I(String pCV_10_I) {
        this.pCV_10_I = pCV_10_I;
    }

    public String getoPV_I() {
        return oPV_I;
    }

    public void setoPV_I(String oPV_I) {
        this.oPV_I = oPV_I;
    }

    public String getRota_I() {
        return rota_I;
    }

    public void setRota_I(String rota_I) {
        this.rota_I = rota_I;
    }

    public String getPenta_II() {
        return penta_II;
    }

    public void setPenta_II(String penta_II) {
        this.penta_II = penta_II;
    }

    public String getpCV_10_II() {
        return pCV_10_II;
    }

    public void setpCV_10_II(String pCV_10_II) {
        this.pCV_10_II = pCV_10_II;
    }

    public String getoPV_II() {
        return oPV_II;
    }

    public void setoPV_II(String oPV_II) {
        this.oPV_II = oPV_II;
    }

    public String getRota_II() {
        return rota_II;
    }

    public void setRota_II(String rota_II) {
        this.rota_II = rota_II;
    }

    public String getPenta_III() {
        return penta_III;
    }

    public void setPenta_III(String penta_III) {
        this.penta_III = penta_III;
    }

    public String getpCV_10_III() {
        return pCV_10_III;
    }

    public void setpCV_10_III(String pCV_10_III) {
        this.pCV_10_III = pCV_10_III;
    }

    public String getoPV_III() {
        return oPV_III;
    }

    public void setoPV_III(String oPV_III) {
        this.oPV_III = oPV_III;
    }

    public String getiPV() {
        return iPV;
    }

    public void setiPV(String iPV) {
        this.iPV = iPV;
    }

    public String getMeasles_I() {
        return measles_I;
    }

    public void setMeasles_I(String measles_I) {
        this.measles_I = measles_I;
    }

    public String getMeasles_II() {
        return measles_II;
    }

    public void setMeasles_II(String measles_II) {
        this.measles_II = measles_II;
    }

    public Integer getMastId() {
        return mastId;
    }

    public void setMastId(Integer mastId) {
        this.mastId = mastId;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(String visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getBcgDate() {
        return BcgDate;
    }

    public void setBcgDate(String bcgDate) {
        BcgDate = bcgDate;
    }

    public String getHepBBDDate() {
        return HepBBDDate;
    }

    public void setHepBBDDate(String hepBBDDate) {
        HepBBDDate = hepBBDDate;
    }

    public String getOPV0Date() {
        return OPV0Date;
    }

    public void setOPV0Date(String OPV0Date) {
        this.OPV0Date = OPV0Date;
    }

    public String getPentaIDate() {
        return PentaIDate;
    }

    public void setPentaIDate(String pentaIDate) {
        PentaIDate = pentaIDate;
    }

    public String getPCV10IDate() {
        return PCV10IDate;
    }

    public void setPCV10IDate(String PCV10IDate) {
        this.PCV10IDate = PCV10IDate;
    }

    public String getOPVIDate() {
        return OPVIDate;
    }

    public void setOPVIDate(String OPVIDate) {
        this.OPVIDate = OPVIDate;
    }

    public String getRotaIDate() {
        return RotaIDate;
    }

    public void setRotaIDate(String rotaIDate) {
        RotaIDate = rotaIDate;
    }

    public String getPentaIIDate() {
        return PentaIIDate;
    }

    public void setPentaIIDate(String pentaIIDate) {
        PentaIIDate = pentaIIDate;
    }

    public String getPCV10IIDate() {
        return PCV10IIDate;
    }

    public void setPCV10IIDate(String PCV10IIDate) {
        this.PCV10IIDate = PCV10IIDate;
    }

    public String getOPVIIDate() {
        return OPVIIDate;
    }

    public void setOPVIIDate(String OPVIIDate) {
        this.OPVIIDate = OPVIIDate;
    }

    public String getRotaIIDate() {
        return RotaIIDate;
    }

    public void setRotaIIDate(String rotaIIDate) {
        RotaIIDate = rotaIIDate;
    }

    public String getPentaIIIDate() {
        return PentaIIIDate;
    }

    public void setPentaIIIDate(String pentaIIIDate) {
        PentaIIIDate = pentaIIIDate;
    }

    public String getPCV10IIIDate() {
        return PCV10IIIDate;
    }

    public void setPCV10IIIDate(String PCV10IIIDate) {
        this.PCV10IIIDate = PCV10IIIDate;
    }

    public String getOPVIIIDate() {
        return OPVIIIDate;
    }

    public void setOPVIIIDate(String OPVIIIDate) {
        this.OPVIIIDate = OPVIIIDate;
    }

    public String getIPVDate() {
        return IPVDate;
    }

    public void setIPVDate(String IPVDate) {
        this.IPVDate = IPVDate;
    }

    public String getMeaslesIDate() {
        return MeaslesIDate;
    }

    public void setMeaslesIDate(String measlesIDate) {
        MeaslesIDate = measlesIDate;
    }

    public String getMeaslesIIDate() {
        return MeaslesIIDate;
    }

    public void setMeaslesIIDate(String measlesIIDate) {
        MeaslesIIDate = measlesIIDate;
    }

    public static List<ClusterForm> getChecklist(String type)

    {
        return new Select()
                .from(ClusterForm.class)
                .where("CheckListTypeName = ?",type)
                .execute();
    }

    public static List<ClusterForm> getAllCluster(Integer cb)

    {
        return new Select()
                .from(ClusterForm.class)
                .where("mastId = ?",cb)
                .execute();
    }

    public static void UpdateData (Integer id)

    {
        new Update(ClusterForm.class)
                .set("sync = 1")
                .where("mastId = ?", id)
                .execute();
    }

    public static void UpdateflagData ()

    {
        new Update(ClusterForm.class)
                .set("sync = 0")
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Update(ClusterForm.class)
                .set("sync = 1")
                .where("mastId = ?", id)
                .execute();
    }
}
