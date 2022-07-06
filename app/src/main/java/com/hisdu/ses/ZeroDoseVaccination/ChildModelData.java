package com.hisdu.ses.ZeroDoseVaccination;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChildModelData {

	@Expose
	@SerializedName("DivisionName")
	private String divisionName;

	@Expose
	@SerializedName("ChildId")
	private Integer childId;

	@Expose
	@SerializedName("ChildName")
	private String childName;

	@Expose
	@SerializedName("Address")
	private String address;

	@Expose
	@SerializedName("TehsilName")
	private String tehsilName;

	@Expose
	@SerializedName("TeamNo")
	private Integer teamNo;

	@Expose
	@SerializedName("HouseNo")
	private String houseNo;

	@Expose
	@SerializedName("EntryPersonDesignation")
	private String entryPersonDesignation;

	@Expose
	@SerializedName("DistrictName")
	private String districtName;

	@Expose
	@SerializedName("EntryPersonName")
	private String entryPersonName;

	@Expose
	@SerializedName("AgeType")
	private String ageType;

	@Expose
	@SerializedName("UCName")
	private String uCName;

	@Expose
	@SerializedName("CampaignMonth")
	private String campaignMonth;

	@Expose
	@SerializedName("Age")
	private String age;

	@Expose
	@SerializedName("FatherName")
	private String fatherName;
	@Expose
	@SerializedName("isVaccinated")
	private Boolean isVaccinated =null;
	@Expose
	@SerializedName("CampaignType")
	private Integer campaignType;

	@Expose
	@SerializedName("AlreadyChecked")
	private Boolean AlreadyChecked;

	@Expose
	@SerializedName("CheckedReason")
	private String CheckedReason;

	@Expose
	@SerializedName("TeamContactNo")
	private String TeamContactNo;

	@Expose
	@SerializedName("PhoneNo")
	private String PhoneNo;


	@Expose
	@SerializedName("HRMP")
	private String HRMP;


	public String getHRMP() {
		return HRMP;
	}

	public void setHRMP(String HRMP) {
		this.HRMP = HRMP;
	}

	public String getTeamContactNo() {
		return TeamContactNo;
	}

	public void setTeamContactNo(String teamContactNo) {
		TeamContactNo = teamContactNo;
	}

	public String getPhoneNo() {
		return PhoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}

	public String getCheckedReason() {
		return CheckedReason;
	}

	public void setCheckedReason(String checkedReason) {
		CheckedReason = checkedReason;
	}

	public Boolean getVaccinated() {
		return isVaccinated;
	}

	public void setVaccinated(Boolean vaccinated) {
		isVaccinated = vaccinated;
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

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(Integer campaignType) {
		this.campaignType = campaignType;
	}

	public String getuCName() {
		return uCName;
	}

	public void setuCName(String uCName) {
		this.uCName = uCName;
	}

	public Boolean getAlreadyChecked() {
		return AlreadyChecked;
	}

	public void setAlreadyChecked(Boolean alreadyChecked) {
		AlreadyChecked = alreadyChecked;
	}
}