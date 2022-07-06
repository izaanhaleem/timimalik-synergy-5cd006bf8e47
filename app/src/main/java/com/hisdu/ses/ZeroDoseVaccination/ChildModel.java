package com.hisdu.ses.ZeroDoseVaccination;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildModel {

	@Expose
	@SerializedName("PageCount")
	private int pageCount;

	@Expose
	@SerializedName("Message")
	private String message;

	@Expose
	@SerializedName("PageNumber")
	private int pageNumber;

	@Expose
	@SerializedName("TotalVaccinated")
	private int TotalVaccinated;

	@Expose
	@SerializedName("Size")
	private int size;

	@Expose
	@SerializedName("TotalRecords")
	private int totalRecords;

	@Expose
	@SerializedName("Error")
	private boolean error;

	@Expose
	@SerializedName("Data")
	private List<ChildModelData> data;

	@Expose
	@SerializedName("StatusCode")
	private int statusCode;

	public int getTotalVaccinated() {
		return TotalVaccinated;
	}

	public void setTotalVaccinated(int totalVaccinated) {
		TotalVaccinated = totalVaccinated;
	}

	public void setPageCount(int pageCount){
		this.pageCount = pageCount;
	}

	public int getPageCount(){
		return pageCount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setTotalRecords(int totalRecords){
		this.totalRecords = totalRecords;
	}

	public int getTotalRecords(){
		return totalRecords;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setData(List<ChildModelData> data){
		this.data = data;
	}

	public List<ChildModelData> getData(){
		return data;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}
}