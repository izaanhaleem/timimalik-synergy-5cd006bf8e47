package com.hisdu.ses.Models.appVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersion {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("App_Version")
    @Expose
    private String appVersion;
    @SerializedName("Locations")
    @Expose
    private String locations;
    @SerializedName("EPICenters")
    @Expose
    private String ePICenters;
    @SerializedName("AppModules")
    @Expose
    private String appModules;
    @SerializedName("EPIStoreMonitoring")
    @Expose
    private String ePIStoreMonitoring;
    @SerializedName("EPIFixedSiteMonitoring")
    @Expose
    private String ePIFixedSiteMonitoring;
    @SerializedName("EPIORSession")
    @Expose
    private String ePIORSession;
    @SerializedName("Clusters")
    @Expose
    private String clusters;
    @SerializedName("ZeroDoseValidation")
    @Expose
    private String zeroDoseValidation;
    @SerializedName("StoreType")
    @Expose
    private String StoreType;
    @SerializedName("Province")
    @Expose
    private String Province;

    @SerializedName("ContactUs")
    @Expose
    private String ContactUs;

    @SerializedName("ResourceMaterial")
    @Expose
    private String ResourceMaterial;

    @SerializedName("CampaignSchedule")
    @Expose
    private String CampaignSchedule;
    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getStoreType() {
        return StoreType;
    }

    public void setStoreType(String storeType) {
        StoreType = storeType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getEPICenters() {
        return ePICenters;
    }

    public void setEPICenters(String ePICenters) {
        this.ePICenters = ePICenters;
    }

    public String getAppModules() {
        return appModules;
    }

    public void setAppModules(String appModules) {
        this.appModules = appModules;
    }

    public String getEPIStoreMonitoring() {
        return ePIStoreMonitoring;
    }

    public void setEPIStoreMonitoring(String ePIStoreMonitoring) {
        this.ePIStoreMonitoring = ePIStoreMonitoring;
    }

    public String getEPIFixedSiteMonitoring() {
        return ePIFixedSiteMonitoring;
    }

    public void setEPIFixedSiteMonitoring(String ePIFixedSiteMonitoring) {
        this.ePIFixedSiteMonitoring = ePIFixedSiteMonitoring;
    }

    public String getEPIORSession() {
        return ePIORSession;
    }

    public void setEPIORSession(String ePIORSession) {
        this.ePIORSession = ePIORSession;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public String getZeroDoseValidation() {
        return zeroDoseValidation;
    }

    public void setZeroDoseValidation(String zeroDoseValidation) {
        this.zeroDoseValidation = zeroDoseValidation;
    }

    public String getContactUs() {
        return ContactUs;
    }

    public void setContactUs(String contactUs) {
        ContactUs = contactUs;
    }

    public String getResourceMaterial() {
        return ResourceMaterial;
    }

    public void setResourceMaterial(String resourceMaterial) {
        ResourceMaterial = resourceMaterial;
    }

    public String getCampaignSchedule() {
        return CampaignSchedule;
    }

    public void setCampaignSchedule(String campaignSchedule) {
        CampaignSchedule = campaignSchedule;
    }
}