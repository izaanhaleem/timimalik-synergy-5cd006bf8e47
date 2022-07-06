package com.hisdu.SESCluster.objects.new_objcts;

import java.util.ArrayList;

public class ProvinceObject {
    private int ProvinceID;
    private String ProvinceName;
    private ArrayList<DivisionObject> Divisions = new ArrayList<>();

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public ArrayList<DivisionObject> getDivisions() {
        return Divisions;
    }

    public void setDivisions(ArrayList<DivisionObject> divisions) {
        Divisions = divisions;
    }
}
