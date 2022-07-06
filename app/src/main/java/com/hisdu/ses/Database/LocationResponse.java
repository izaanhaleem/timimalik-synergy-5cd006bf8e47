package com.hisdu.ses.Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResponse {

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
private java.util.List<Location> list = null;

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

public java.util.List<Location> getList() {
return list;
}

public void setList(java.util.List<Location> list) {
this.list = list;
}

}