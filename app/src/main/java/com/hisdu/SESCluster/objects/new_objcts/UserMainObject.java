package com.hisdu.SESCluster.objects.new_objcts;

public class UserMainObject extends ResponseObject {
    private UserSubObject Users;

    public UserSubObject getUsers() {
        return Users;
    }

    public void setUsers(UserSubObject users) {
        Users = users;
    }
}
