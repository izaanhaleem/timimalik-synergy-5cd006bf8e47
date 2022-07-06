package com.hisdu.ses.Models.ZeroDose;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DesignationData {

	@SerializedName("DesignationId")
	@Expose
	private int designationId;

	@SerializedName("IsActive")
	@Expose
	private boolean isActive;

	@SerializedName("DesignationName")
	@Expose
	private String designationName;

	@SerializedName("IsSIADesignation")
	@Expose
	private boolean isSIADesignation;

	public Integer getDesignationId(){
		return designationId;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public String getDesignationName(){
		return designationName;
	}

	public boolean isIsSIADesignation(){
		return isSIADesignation;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public boolean isSIADesignation() {
		return isSIADesignation;
	}

	public void setSIADesignation(boolean SIADesignation) {
		isSIADesignation = SIADesignation;
	}
}