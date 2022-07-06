package com.hisdu.SESCluster.objects.new_objcts;

import java.util.ArrayList;

public class DistrictsObject {
    private int id;
    private String district_name;
    private ArrayList<TownsObject> Towns = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public ArrayList<TownsObject> getTowns() {
        return Towns;
    }

    public void setTowns(ArrayList<TownsObject> towns) {
        Towns = towns;
    }
}
