package com.hisdu.ses.Models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseDataLatest {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("UserRole")
    @Expose
    private String userRole;

    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("LocationCode")
    @Expose
    private String locationCode;
    @SerializedName("LocationName")
    @Expose
    private String locationName;
    @SerializedName("StoreTypeId")
    @Expose
    private String StoreTypeId;
    @SerializedName("ProvinceId")
    @Expose
    private String ProvinceId;


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getStoreTypeId() {
        return StoreTypeId;
    }

    public void setStoreTypeId(String storeTypeId) {
        StoreTypeId = storeTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}


