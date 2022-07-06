package com.hisdu.ses.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Database.SaveChecklist;

import java.util.List;

public class SaveChecklistRequest {

    @SerializedName("QList")
    @Expose
    private List<SaveChecklist> qList = null;
    @SerializedName("Remarks")
    @Expose
    private String remarks;

    public List<SaveChecklist> getQList() {
        return qList;
    }

    public void setQList(List<SaveChecklist> qList) {
        this.qList = qList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
