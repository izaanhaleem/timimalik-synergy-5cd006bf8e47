package com.hisdu.ses.Models.epiCenters;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Database.Location;

import java.util.List;

@Table(name = "EpiCenter")
public class EpiCenter extends Model {

    public EpiCenter(Integer centerId, String centerName) {
        this.centerId = centerId;
        this.centerName = centerName;
    }

    public EpiCenter() {
    }

    @Column(name = "CenterId")
    @SerializedName("CenterId")
    @Expose
    private Integer centerId;

    @Column(name = "CenterName")
    @SerializedName("CenterName")
    @Expose
    private String centerName;

    @Column(name = "PKCODE")
    @SerializedName("PKCODE")
    @Expose
    private String pKCODE;

    @Column(name = "Lattitude")
    @SerializedName("Lattitude")
    @Expose
    private Double lattitude;

    @Column(name = "Longitude")
    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getPKCODE() {
        return pKCODE;
    }

    public void setPKCODE(String pKCODE) {
        this.pKCODE = pKCODE;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static List<EpiCenter> getEpiCenters(String pkCode) {
        return new Select()
                .from(EpiCenter.class)
                .where("PKCODE = '"+ pkCode+"'")
                .execute();
    }

}
