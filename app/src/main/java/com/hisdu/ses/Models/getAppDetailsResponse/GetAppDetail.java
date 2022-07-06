package com.hisdu.ses.Models.getAppDetailsResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppDetail {

@SerializedName("HisduAPP")
@Expose
private Object hisduAPP;
@SerializedName("Id")
@Expose
private Integer id;
@SerializedName("AppID")
@Expose
private Integer appID;
@SerializedName("Name")
@Expose
private String name;
@SerializedName("Value")
@Expose
private String value;
@SerializedName("CreateBy")
@Expose
private String createBy;
@SerializedName("CreatedDT")
@Expose
private String createdDT;

public Object getHisduAPP() {
return hisduAPP;
}

public void setHisduAPP(Object hisduAPP) {
this.hisduAPP = hisduAPP;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getAppID() {
return appID;
}

public void setAppID(Integer appID) {
this.appID = appID;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

public String getCreateBy() {
return createBy;
}

public void setCreateBy(String createBy) {
this.createBy = createBy;
}

public String getCreatedDT() {
return createdDT;
}

public void setCreatedDT(String createdDT) {
this.createdDT = createdDT;
}

}

