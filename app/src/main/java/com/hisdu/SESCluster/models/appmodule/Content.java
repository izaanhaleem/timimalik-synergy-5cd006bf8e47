package com.hisdu.SESCluster.models.appmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

@SerializedName("AppModuleId")
@Expose
private Integer appModuleId;
@SerializedName("AppModuleName")
@Expose
private String appModuleName;
@SerializedName("IsActive")
@Expose
private Boolean isActive;

public Integer getAppModuleId() {
return appModuleId;
}

public void setAppModuleId(Integer appModuleId) {
this.appModuleId = appModuleId;
}

public String getAppModuleName() {
return appModuleName;
}

public void setAppModuleName(String appModuleName) {
this.appModuleName = appModuleName;
}

public Boolean getIsActive() {
return isActive;
}

public void setIsActive(Boolean isActive) {
this.isActive = isActive;
}

}