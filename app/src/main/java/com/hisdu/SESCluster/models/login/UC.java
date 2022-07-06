package com.hisdu.SESCluster.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UC {

@SerializedName("UCID")
@Expose
private String uCID;
@SerializedName("UCName")
@Expose
private String uCName;

public String getUCID() {
return uCID;
}

public void setUCID(String uCID) {
this.uCID = uCID;
}

public String getUCName() {
return uCName;
}

public void setUCName(String uCName) {
this.uCName = uCName;
}

}