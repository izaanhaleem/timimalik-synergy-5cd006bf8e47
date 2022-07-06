package com.hisdu.ses.Models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Town {

@SerializedName("TownID")
@Expose
private String townID;
@SerializedName("TownName")
@Expose
private String townName;
@SerializedName("UCs")
@Expose
private List<UC> uCs = null;

public String getTownID() {
return townID;
}

public void setTownID(String townID) {
this.townID = townID;
}

public String getTownName() {
return townName;
}

public void setTownName(String townName) {
this.townName = townName;
}

public List<UC> getUCs() {
return uCs;
}

public void setUCs(List<UC> uCs) {
this.uCs = uCs;
}

}