package com.hisdu.ses.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefusalReasonModel{

	@Expose
	@SerializedName("Message")
	public String message;

	@Expose
	@SerializedName("Error")
	public boolean error;

	@Expose
	@SerializedName("List")
	public List<RefusalListItem> list;

	@Expose
	@SerializedName("StatusCode")
	public int statusCode;

}