package com.hisdu.SESCluster.models.appmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppModuleResponse {

@SerializedName("StatusCode")
@Expose
private Integer statusCode;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Error")
@Expose
private Boolean error;
@SerializedName("Content")
@Expose
private List<Content> content = null;

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

public List<Content> getContent() {
return content;
}

public void setContent(List<Content> content) {
this.content = content;
}

}


