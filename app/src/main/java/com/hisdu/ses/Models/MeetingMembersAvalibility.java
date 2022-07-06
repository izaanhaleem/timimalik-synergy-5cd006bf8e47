package com.hisdu.ses.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeetingMembersAvalibility {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FKMeetingID")
    @Expose
    private Integer fKMeetingID;
    @SerializedName("FKMemberID")
    @Expose
    private Integer fKMemberID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ContectNo")
    @Expose
    private String contectNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFKMeetingID() {
        return fKMeetingID;
    }

    public void setFKMeetingID(Integer fKMeetingID) {
        this.fKMeetingID = fKMeetingID;
    }

    public Integer getFKMemberID() {
        return fKMemberID;
    }

    public void setFKMemberID(Integer fKMemberID) {
        this.fKMemberID = fKMemberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContectNo() {
        return contectNo;
    }

    public void setContectNo(String contectNo) {
        this.contectNo = contectNo;
    }
}
