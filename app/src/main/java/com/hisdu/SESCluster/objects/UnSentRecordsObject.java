package com.hisdu.SESCluster.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnSentRecordsObject {

    @SerializedName("Data")
    @Expose
    private String Data;
    @SerializedName("id")
    @Expose
    private int id ;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("settlement")
    @Expose
    private String settlement;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("moduleid")
    @Expose
    private String moduleid;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("clustertype")
    @Expose
    private String clustertype;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("sync")
    @Expose
    private String sync;
    @SerializedName("syncStatus")
    @Expose
    private String syncStatus;
    @SerializedName("AlreadySync")
    @Expose
    private String AlreadySync="0";

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String districtName;
    private String ucName;
    private String EPID;

    public String getEPID() {
        return EPID;
    }

    public void setEPID(String EPID) {
        this.EPID = EPID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getUcName() {
        return ucName;
    }

    public void setUcName(String ucName) {
        this.ucName = ucName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getClustertype() {
        return clustertype;
    }

    public void setClustertype(String clustertype) {
        this.clustertype = clustertype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getAlreadySync() {
        return AlreadySync;
    }

    public void setAlreadySync(String alreadySync) {
        AlreadySync = alreadySync;
    }
}
