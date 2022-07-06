package com.hisdu.ses.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefusalListItem {

	@Expose
	@SerializedName("RefusalId")
	public int refusalId;

	@Expose
	@SerializedName("IsActive")
	public boolean isActive;

	@Expose
	@SerializedName("RefusalReason")
	public String refusalReason;

	public int getRefusalId(){
		return refusalId;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public String getRefusalReason(){
		return refusalReason;
	}
}