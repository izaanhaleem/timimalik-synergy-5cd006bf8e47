package com.hisdu.SESCluster.objects;

import java.util.ArrayList;

public class UserMainObject extends ResponseObject {
    UserObject User;
    ArrayList<TownObject> Districts = new ArrayList<>();

    public UserObject getUser() {
        return User;
    }

    public void setUser(UserObject user) {
        User = user;
    }

    public ArrayList<TownObject> getDistricts() {
        return Districts;
    }

    public void setDistricts(ArrayList<TownObject> districts) {
        Districts = districts;
    }
}
