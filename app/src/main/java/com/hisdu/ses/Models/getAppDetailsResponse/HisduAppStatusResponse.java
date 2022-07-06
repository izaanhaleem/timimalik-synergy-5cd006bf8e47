package com.hisdu.ses.Models.getAppDetailsResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HisduAppStatusResponse {

@SerializedName("getAppStatus")
@Expose
private GetAppStatus getAppStatus;
@SerializedName("getAppDetail")
@Expose
private List<GetAppDetail> getAppDetail = null;
@SerializedName("Err")
@Expose
private String err;

public GetAppStatus getGetAppStatus() {
return getAppStatus;
}

public void setGetAppStatus(GetAppStatus getAppStatus) {
this.getAppStatus = getAppStatus;
}

public List<GetAppDetail> getGetAppDetail() {
return getAppDetail;
}

public void setGetAppDetail(List<GetAppDetail> getAppDetail) {
this.getAppDetail = getAppDetail;
}

public String getErr() {
return err;
}

public void setErr(String err) {
this.err = err;
}

}