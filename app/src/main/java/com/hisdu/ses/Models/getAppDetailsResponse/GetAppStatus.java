package com.hisdu.ses.Models.getAppDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppStatus {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("Name")
@Expose
private String name;
@SerializedName("Detail")
@Expose
private Object detail;
@SerializedName("APPAPK")
@Expose
private String aPPAPK;
@SerializedName("Image")
@Expose
private String image;
@SerializedName("Type")
@Expose
private String type;
@SerializedName("URL")
@Expose
private String uRL;
@SerializedName("AppVersion")
@Expose
private String appVersion;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("InsDT")
@Expose
private String insDT;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Object getDetail() {
return detail;
}

public void setDetail(Object detail) {
this.detail = detail;
}

public String getAPPAPK() {
return aPPAPK;
}

public void setAPPAPK(String aPPAPK) {
this.aPPAPK = aPPAPK;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getURL() {
return uRL;
}

public void setURL(String uRL) {
this.uRL = uRL;
}

public String getAppVersion() {
return appVersion;
}

public void setAppVersion(String appVersion) {
this.appVersion = appVersion;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getInsDT() {
return insDT;
}

public void setInsDT(String insDT) {
this.insDT = insDT;
}

}