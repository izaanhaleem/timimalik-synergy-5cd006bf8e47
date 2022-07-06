package com.hisdu.ses.ZeroDoseVaccination;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;


@Table(name = "ChildModel")
public class ChildDBData extends Model {

	@Column(name = "DivisionName")
	@SerializedName("DivisionName")
	private String divisionName;

	@Column(name = "ChildId")
	@SerializedName("ChildId")
	private Integer childId;

	@Column(name = "ChildName")
	@SerializedName("ChildName")
	private String childName;

	@Column(name = "Address")
	@SerializedName("Address")
	private String address;

	@Column(name = "TehsilName")
	@SerializedName("TehsilName")
	private String tehsilName;

	@Column(name = "TeamNo")
	@SerializedName("TeamNo")
	private Integer teamNo;


	@Column(name = "HouseNo")
	@SerializedName("HouseNo")
	private String houseNo;

	@Column(name = "EntryPersonDesignation")
	@SerializedName("EntryPersonDesignation")
	private String entryPersonDesignation;

	@Column(name = "DistrictName")
	@SerializedName("DistrictName")
	private String districtName;

	@Column(name = "EntryPersonName")
	@SerializedName("EntryPersonName")
	private String entryPersonName;


	@Column(name = "AgeType")
	@SerializedName("AgeType")
	private String ageType;


	@Column(name = "UCName")
	@SerializedName("UCName")
	private String uCName;

	@Column(name = "CampaignMonth")
	@SerializedName("CampaignMonth")
	private String campaignMonth;


	@Column(name = "Age")
	@SerializedName("Age")
	private String age;


	@Column(name = "FatherName")
	@SerializedName("FatherName")
	private String fatherName;


	@Column(name = "CampaignType")
	@SerializedName("CampaignType")
	private Integer campaignType;


	@Column(name = "Hrmp")
	@SerializedName("Hrmp")
	private String Hrmp;


	public String getHrmp() {
		return Hrmp;
	}

	public void setHrmp(String hrmp) {
		Hrmp = hrmp;
	}

	public void setDivisionName(String divisionName){
		this.divisionName = divisionName;
	}

	public String getDivisionName(){
		return divisionName;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getuCName() {
		return uCName;
	}

	public void setuCName(String uCName) {
		this.uCName = uCName;
	}

	public Integer getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(Integer campaignType) {
		this.campaignType = campaignType;
	}

	public void setChildName(String childName){
		this.childName = childName;
	}

	public String getChildName(){
		return childName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setTehsilName(String tehsilName){
		this.tehsilName = tehsilName;
	}

	public String getTehsilName(){
		return tehsilName;
	}


	public void setHouseNo(String houseNo){
		this.houseNo = houseNo;
	}

	public String getHouseNo(){
		return houseNo;
	}

	public void setEntryPersonDesignation(String entryPersonDesignation){
		this.entryPersonDesignation = entryPersonDesignation;
	}

	public String getEntryPersonDesignation(){
		return entryPersonDesignation;
	}

	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}

	public String getDistrictName(){
		return districtName;
	}

	public void setEntryPersonName(String entryPersonName){
		this.entryPersonName = entryPersonName;
	}

	public String getEntryPersonName(){
		return entryPersonName;
	}

	public void setAgeType(String ageType){
		this.ageType = ageType;
	}

	public String getAgeType(){
		return ageType;
	}

	public void setUCName(String uCName){
		this.uCName = uCName;
	}

	public String getUCName(){
		return uCName;
	}

	public void setCampaignMonth(String campaignMonth){
		this.campaignMonth = campaignMonth;
	}

	public String getCampaignMonth(){
		return campaignMonth;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setFatherName(String fatherName){
		this.fatherName = fatherName;
	}

	public String getFatherName(){
		return fatherName;
	}

}