package com.hisdu.ses.Models.storeTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreTypesResponse {

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
private java.util.List<Store> stores = null;

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

public java.util.List<Store> getList() {
return stores;
}

public void setList(java.util.List<Store> list) {
this.stores = list;
}

}