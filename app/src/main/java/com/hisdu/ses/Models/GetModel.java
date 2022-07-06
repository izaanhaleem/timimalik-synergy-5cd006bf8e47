package com.hisdu.ses.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetModel {

    @SerializedName("TehsilCode")
    @Expose
    private String tehsilCode;
    @SerializedName("UCCode")
    @Expose
    private String uCCode;

    public String getTehsilCode() {
        return tehsilCode;
    }

    public void setTehsilCode(String tehsilCode) {
        this.tehsilCode = tehsilCode;
    }

    public String getUCCode() {
        return uCCode;
    }

    public void setUCCode(String uCCode) {
        this.uCCode = uCCode;
    }
}
