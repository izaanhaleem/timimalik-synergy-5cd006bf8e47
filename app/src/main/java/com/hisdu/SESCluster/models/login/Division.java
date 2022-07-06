package com.hisdu.SESCluster.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Division {

@SerializedName("DivisionID")
@Expose
private String divisionID;
@SerializedName("DivisionName")
@Expose
private String divisionName;
@SerializedName("Districts")
@Expose
private List<District> districts = null;

public String getDivisionID() {
return divisionID;
}

public void setDivisionID(String divisionID) {
this.divisionID = divisionID;
}

public String getDivisionName() {
return divisionName;
}

public void setDivisionName(String divisionName) {
this.divisionName = divisionName;
}

public List<District> getDistricts() {
return districts;
}

public void setDistricts(List<District> districts) {
this.districts = districts;
}

}