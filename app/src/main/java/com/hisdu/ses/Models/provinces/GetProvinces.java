package com.hisdu.ses.Models.provinces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProvinces {

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
private java.util.List<Province> provinces = null;

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

public java.util.List<Province> getProvinces() {
return provinces;
}

public void setList(java.util.List<Province> list) {
this.provinces = list;
}

}

