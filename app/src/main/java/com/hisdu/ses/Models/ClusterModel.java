package com.hisdu.ses.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;

import java.util.ArrayList;

public class ClusterModel {

    @SerializedName("list")
    @Expose
    private UnSentRecordsObject unSentRecordsObjectsList;

    public UnSentRecordsObject getUnSentRecordsObjectsList() {
        return unSentRecordsObjectsList;
    }

    public void setUnSentRecordsObjectsList(UnSentRecordsObject unSentRecordsObjectsList) {
        this.unSentRecordsObjectsList = unSentRecordsObjectsList;
    }
}
