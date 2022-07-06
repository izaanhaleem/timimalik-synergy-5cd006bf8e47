package com.hisdu.SESCluster.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

@SerializedName("ResultStatus")
@Expose
private String resultStatus;
@SerializedName("ResultCode")
@Expose
private String resultCode;
@SerializedName("ResultMessage")
@Expose
private String resultMessage;
@SerializedName("UserID")
@Expose
private String userID;
@SerializedName("RoleID")
@Expose
private String roleID;
@SerializedName("UserTypeID")
@Expose
private String userTypeID;
@SerializedName("ProvinceID")
@Expose
private String provinceID;
@SerializedName("DivisionID")
@Expose
private String divisionID;
@SerializedName("DistrictID")
@Expose
private String districtID;
@SerializedName("TownID")
@Expose
private String townID;
@SerializedName("UCID")
@Expose
private String uCID;
@SerializedName("DepartmentID")
@Expose
private String departmentID;
@SerializedName("PhoneProvidedID")
@Expose
private String phoneProvidedID;
@SerializedName("ActualDesignationID")
@Expose
private String actualDesignationID;
@SerializedName("TeamDesignationID")
@Expose
private String teamDesignationID;
@SerializedName("Username")
@Expose
private String username;
@SerializedName("Password")
@Expose
private String password;
@SerializedName("Name")
@Expose
private String name;
@SerializedName("ContactNo")
@Expose
private String contactNo;
@SerializedName("CNIC")
@Expose
private String cNIC;
@SerializedName("IsActive")
@Expose
private String isActive;
@SerializedName("IMEI")
@Expose
private String iMEI;
@SerializedName("CreatedDate")
@Expose
private String createdDate;
@SerializedName("CreatedBy")
@Expose
private String createdBy;
@SerializedName("ModifiedDate")
@Expose
private String modifiedDate;
@SerializedName("ModifyBy")
@Expose
private String modifyBy;
@SerializedName("ProvinceID1")
@Expose
private String provinceID1;
@SerializedName("ProvinceName")
@Expose
private String provinceName;
@SerializedName("DivisionID1")
@Expose
private String divisionID1;
@SerializedName("DivisionName")
@Expose
private String divisionName;
@SerializedName("ProvinceID2")
@Expose
private String provinceID2;
@SerializedName("id")
@Expose
private String id;
@SerializedName("district_name")
@Expose
private String districtName;
@SerializedName("division_id")
@Expose
private String divisionId;
@SerializedName("TownID1")
@Expose
private String townID1;
@SerializedName("TownName")
@Expose
private String townName;
@SerializedName("DistrictID1")
@Expose
private String districtID1;
@SerializedName("Divisions")
@Expose
private List<Division> divisions = null;

public String getResultStatus() {
return resultStatus;
}

public void setResultStatus(String resultStatus) {
this.resultStatus = resultStatus;
}

public String getResultCode() {
return resultCode;
}

public void setResultCode(String resultCode) {
this.resultCode = resultCode;
}

public String getResultMessage() {
return resultMessage;
}

public void setResultMessage(String resultMessage) {
this.resultMessage = resultMessage;
}

public String getUserID() {
return userID;
}

public void setUserID(String userID) {
this.userID = userID;
}

public String getRoleID() {
return roleID;
}

public void setRoleID(String roleID) {
this.roleID = roleID;
}

public String getUserTypeID() {
return userTypeID;
}

public void setUserTypeID(String userTypeID) {
this.userTypeID = userTypeID;
}

public String getProvinceID() {
return provinceID;
}

public void setProvinceID(String provinceID) {
this.provinceID = provinceID;
}

public String getDivisionID() {
return divisionID;
}

public void setDivisionID(String divisionID) {
this.divisionID = divisionID;
}

public String getDistrictID() {
return districtID;
}

public void setDistrictID(String districtID) {
this.districtID = districtID;
}

public String getTownID() {
return townID;
}

public void setTownID(String townID) {
this.townID = townID;
}

public String getUCID() {
return uCID;
}

public void setUCID(String uCID) {
this.uCID = uCID;
}

public String getDepartmentID() {
return departmentID;
}

public void setDepartmentID(String departmentID) {
this.departmentID = departmentID;
}

public String getPhoneProvidedID() {
return phoneProvidedID;
}

public void setPhoneProvidedID(String phoneProvidedID) {
this.phoneProvidedID = phoneProvidedID;
}

public String getActualDesignationID() {
return actualDesignationID;
}

public void setActualDesignationID(String actualDesignationID) {
this.actualDesignationID = actualDesignationID;
}

public String getTeamDesignationID() {
return teamDesignationID;
}

public void setTeamDesignationID(String teamDesignationID) {
this.teamDesignationID = teamDesignationID;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getContactNo() {
return contactNo;
}

public void setContactNo(String contactNo) {
this.contactNo = contactNo;
}

public String getCNIC() {
return cNIC;
}

public void setCNIC(String cNIC) {
this.cNIC = cNIC;
}

public String getIsActive() {
return isActive;
}

public void setIsActive(String isActive) {
this.isActive = isActive;
}

public String getIMEI() {
return iMEI;
}

public void setIMEI(String iMEI) {
this.iMEI = iMEI;
}

public String getCreatedDate() {
return createdDate;
}

public void setCreatedDate(String createdDate) {
this.createdDate = createdDate;
}

public String getCreatedBy() {
return createdBy;
}

public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}

public String getModifiedDate() {
return modifiedDate;
}

public void setModifiedDate(String modifiedDate) {
this.modifiedDate = modifiedDate;
}

public String getModifyBy() {
return modifyBy;
}

public void setModifyBy(String modifyBy) {
this.modifyBy = modifyBy;
}

public String getProvinceID1() {
return provinceID1;
}

public void setProvinceID1(String provinceID1) {
this.provinceID1 = provinceID1;
}

public String getProvinceName() {
return provinceName;
}

public void setProvinceName(String provinceName) {
this.provinceName = provinceName;
}

public String getDivisionID1() {
return divisionID1;
}

public void setDivisionID1(String divisionID1) {
this.divisionID1 = divisionID1;
}

public String getDivisionName() {
return divisionName;
}

public void setDivisionName(String divisionName) {
this.divisionName = divisionName;
}

public String getProvinceID2() {
return provinceID2;
}

public void setProvinceID2(String provinceID2) {
this.provinceID2 = provinceID2;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDistrictName() {
return districtName;
}

public void setDistrictName(String districtName) {
this.districtName = districtName;
}

public String getDivisionId() {
return divisionId;
}

public void setDivisionId(String divisionId) {
this.divisionId = divisionId;
}

public String getTownID1() {
return townID1;
}

public void setTownID1(String townID1) {
this.townID1 = townID1;
}

public String getTownName() {
return townName;
}

public void setTownName(String townName) {
this.townName = townName;
}

public String getDistrictID1() {
return districtID1;
}

public void setDistrictID1(String districtID1) {
this.districtID1 = districtID1;
}

public List<Division> getDivisions() {
return divisions;
}

public void setDivisions(List<Division> divisions) {
this.divisions = divisions;
}

}