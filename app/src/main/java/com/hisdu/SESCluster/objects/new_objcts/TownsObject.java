package com.hisdu.SESCluster.objects.new_objcts;

import java.util.ArrayList;

public class TownsObject {
    private int TownID;
    private String TownName;
    private ArrayList<UCsObject> UCs = new ArrayList<>();

    public int getTownID() {
        return TownID;
    }

    public void setTownID(int townID) {
        TownID = townID;
    }

    public String getTownName() {
        return TownName;
    }

    public void setTownName(String townName) {
        TownName = townName;
    }

    public ArrayList<UCsObject> getUCs() {
        return UCs;
    }

    public void setUCs(ArrayList<UCsObject> UCs) {
        this.UCs = UCs;
    }
}
