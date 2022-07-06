package com.hisdu.SESCluster.objects.new_objcts;

import java.util.ArrayList;

public class DivisionObject {
    private int DivisionID;
    private String DivisionName;
    private ArrayList<DistrictsObject> Districts = new ArrayList<>();

    public int getDivisionID() {
        return DivisionID;
    }

    public void setDivisionID(int divisionID) {
        DivisionID = divisionID;
    }

    public String getDivisionName() {
        return DivisionName;
    }

    public void setDivisionName(String divisionName) {
        DivisionName = divisionName;
    }

    public ArrayList<DistrictsObject> getDistricts() {
        return Districts;
    }

    public void setDistricts(ArrayList<DistrictsObject> districts) {
        Districts = districts;
    }
}
