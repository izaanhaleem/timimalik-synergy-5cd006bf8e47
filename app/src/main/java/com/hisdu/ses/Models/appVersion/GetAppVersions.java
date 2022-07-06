package com.hisdu.ses.Models.appVersion;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppVersions {

@SerializedName("StatusCode")
@Expose
private Integer statusCode;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Error")
@Expose
private Boolean error;
@SerializedName("List")
@Expose
private AppVersion appVersion;

public Integer getStatusCode() {
return statusCode;
}

public void setStatusCode(Integer statusCode) {
this.statusCode = statusCode;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public AppVersion getAppVersions() {
return appVersion;
}

public void setList(AppVersion appVersion) {
this.appVersion = appVersion;
}

}

