package com.hisdu.ses.ZeroDoseVaccination;

import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveChildModel{

	@SerializedName("UpdatedBy")
	private int updatedBy;
	@SerializedName("CreatedBy")
	private int createdBy;
	@SerializedName("RegistrationChildId")
	private int registrationChildId;
	@SerializedName("MissedProfileId")
	private String missedProfileId;
	@SerializedName("VaccinationId")
	private int vaccinationId;
	@SerializedName("IsVaccinated")
	private boolean isVaccinated;
	@SerializedName("BackImageUrl")
	private String backImageUrl;
	@SerializedName("UpdatedOn")
	private String updatedOn;
	@SerializedName("FrontImageUrl")
	private String frontImageUrl;
	@SerializedName("CreatedOn")
	private String createdOn;

	@SerializedName("CardNo")
	private String CardNo;



	@SerializedName("EntryPersonDesignation")
	private String EntryPersonDesignation;


	@SerializedName("TeamContactNo")
	private String TeamContactNo;

	@SerializedName("EntryPersonName")
	private String EntryPersonName;

	public String getEntryPersonDesignation() {
		return EntryPersonDesignation;
	}

	public void setEntryPersonDesignation(String entryPersonDesignation) {
		EntryPersonDesignation = entryPersonDesignation;
	}

	public String getTeamContactNo() {
		return TeamContactNo;
	}

	public void setTeamContactNo(String teamContactNo) {
		TeamContactNo = teamContactNo;
	}

	public String getEntryPersonName() {
		return EntryPersonName;
	}

	public void setEntryPersonName(String entryPersonName) {
		EntryPersonName = entryPersonName;
	}

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy;
	}

	public int getUpdatedBy(){
		return updatedBy;
	}

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	public void setRegistrationChildId(int registrationChildId){
		this.registrationChildId = registrationChildId;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}

	public int getRegistrationChildId(){
		return registrationChildId;
	}

	public boolean isVaccinated() {
		return isVaccinated;
	}

	public void setVaccinated(boolean vaccinated) {
		isVaccinated = vaccinated;
	}

	public String getMissedProfileId() {
		return missedProfileId;
	}

	public void setMissedProfileId(String missedProfileId) {
		this.missedProfileId = missedProfileId;
	}

	public void setVaccinationId(int vaccinationId){
		this.vaccinationId = vaccinationId;
	}

	public int getVaccinationId(){
		return vaccinationId;
	}

	public void setIsVaccinated(boolean isVaccinated){
		this.isVaccinated = isVaccinated;
	}

	public boolean isIsVaccinated(){
		return isVaccinated;
	}

	public void setBackImageUrl(String backImageUrl){
		this.backImageUrl = backImageUrl;
	}

	public String getBackImageUrl(){
		return backImageUrl;
	}

	public void setUpdatedOn(String updatedOn){
		this.updatedOn = updatedOn;
	}

	public String getUpdatedOn(){
		return updatedOn;
	}

	public void setFrontImageUrl(String frontImageUrl){
		this.frontImageUrl = frontImageUrl;
	}

	public String getFrontImageUrl(){
		return frontImageUrl;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}
}
