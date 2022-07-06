package com.hisdu.SESCluster.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class District {

@SerializedName("id")
@Expose
private String id;
@SerializedName("district_name")
@Expose
private String districtName;
@SerializedName("Towns")
@Expose
private List<Town> towns = null;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDistrictName() {
return districtName;
}

public void setDistrictName(String districtName) {
this.districtName = districtName;
}

public List<Town> getTowns() {
return towns;
}

public void setTowns(List<Town> towns) {
this.towns = towns;
}

}


