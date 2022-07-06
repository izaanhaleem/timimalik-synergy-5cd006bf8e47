package com.hisdu.ses.Models.ZeroDose;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DesignationModel {

	@SerializedName("Message")
	@Expose
	private String message;

	@SerializedName("Error")
	@Expose
	private boolean error;

	@SerializedName("List")
	@Expose
	private List<DesignationData> list;

	@SerializedName("StatusCode")
	@Expose
	private int statusCode;

	public String getMessage(){
		return message;
	}

	public boolean isError(){
		return error;
	}

	public int getStatusCode(){

		return statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public List<DesignationData> getList() {
		return list;
	}

	public void setList(List<DesignationData> list) {
		this.list = list;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}