package com.hisdu.ses.Models.indicators;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Database.CheckList;

import java.util.List;

@Table(name = "Indicators")
public class Indicator extends Model {

    @Column(name = "Server_id")
    @SerializedName("IndicatorId")
    @Expose
    private Integer indicatorId;

    @Column(name = "AppModuleTypeId")
    @SerializedName("AppModuleTypeId")
    @Expose
    private Integer appModuleTypeId;

    @Column(name = "IndicatorParentId")
    @SerializedName("IndicatorParentId")
    @Expose
    private Integer indicatorParentId;

    @Column(name = "SrNo")
    @SerializedName("SrNo")
    @Expose
    private String SrNo;

    @Column(name = "Question")
    @SerializedName("Question")
    @Expose
    private String question;

    @Column(name = "FieldType")
    @SerializedName("FieldType")
    @Expose
    private String fieldType;

    @Column(name = "SequenceNo")
    @SerializedName("SequenceNo")
    @Expose
    private Integer sequenceNo;

    @Column(name = "IsOptional")
    @SerializedName("IsOptional")
    @Expose
    private Boolean isOptional;

    @Column(name = "IsActive")
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    @Column(name = "ShowInCase")
    @SerializedName("ShowInCase")
    @Expose
    private Integer showInCase;

    @Column(name = "IsNAShow")
    @SerializedName("IsNAShow")
    @Expose
    private Boolean isNAShow;

    @Column(name = "IsRemarksMandatory")
    @SerializedName("IsRemarksMandatory")
    @Expose
    private Boolean isRemarksMandatory;

    @Column(name = "IsRemarksShow")
    @SerializedName("IsRemarksShow")
    @Expose
    private Boolean isRemarksShow;

    @Column(name = "ShowRemarksInCase")
    @SerializedName("ShowRemarksInCase")
    @Expose
    private Integer showRemarksInCase;

    @Column(name = "RemarksPlaceHolderText")
    @SerializedName("RemarksPlaceHolderText")
    @Expose
    private String remarksPlaceHolderText;

    @Column(name = "mastID")
    @SerializedName("mastID")
    @Expose
    private Integer mastID;

    @Column(name = "AppModuleId")
    @SerializedName("AppModuleId")
    @Expose
    private Integer appModuleId;

    @Column(name = "Answer")
    @SerializedName("Answer")
    @Expose
    private String answer;

    @Column(name = "Comments")
    @SerializedName("Comments")
    @Expose
    private String comments;

    @Column(name = "CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;

    @Column(name = "CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;

    @Column(name = "sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    public Boolean getOptional() {
        return isOptional;
    }

    public void setOptional(Boolean optional) {
        isOptional = optional;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    @SerializedName("SubIndicators")
    @Expose
    private List<SubIndicator> subIndicators = null;

    public Integer getMastID() {
        return mastID;
    }

    public void setMastID(Integer mastID) {
        this.mastID = mastID;
    }

    public Integer getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(Integer appModuleId) {
        this.appModuleId = appModuleId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public Integer getShowInCase() {
        return showInCase;
    }

    public void setShowInCase(Integer showInCase) {
        this.showInCase = showInCase;
    }

    public Boolean getNAShow() {
        return isNAShow;
    }

    public void setNAShow(Boolean NAShow) {
        isNAShow = NAShow;
    }

    public Boolean getRemarksMandatory() {
        return isRemarksMandatory;
    }

    public void setRemarksMandatory(Boolean remarksMandatory) {
        isRemarksMandatory = remarksMandatory;
    }

    public Boolean getRemarksShow() {
        return isRemarksShow;
    }

    public void setRemarksShow(Boolean remarksShow) {
        isRemarksShow = remarksShow;
    }

    public Integer getShowRemarksInCase() {
        return showRemarksInCase;
    }

    public void setShowRemarksInCase(Integer showRemarksInCase) {
        this.showRemarksInCase = showRemarksInCase;
    }

    public String getRemarksPlaceHolderText() {
        return remarksPlaceHolderText;
    }

    public void setRemarksPlaceHolderText(String remarksPlaceHolderText) {
        this.remarksPlaceHolderText = remarksPlaceHolderText;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Integer getAppModuleTypeId() {
        return appModuleTypeId;
    }

    public void setAppModuleTypeId(Integer appModuleTypeId) {
        this.appModuleTypeId = appModuleTypeId;
    }

    public Integer getIndicatorParentId() {
        return indicatorParentId;
    }

    public void setIndicatorParentId(Integer indicatorParentId) {
        this.indicatorParentId = indicatorParentId;
    }

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<SubIndicator> getSubIndicators() {
        return subIndicators;
    }

    public void setSubIndicators(List<SubIndicator> subIndicators) {
        this.subIndicators = subIndicators;
    }

    public static List<Indicator> getAllIndicators(String type) {
        return new Select()
                .from(Indicator.class)
                .where("AppModuleTypeId = ?", type)
                .execute();
    }

}

