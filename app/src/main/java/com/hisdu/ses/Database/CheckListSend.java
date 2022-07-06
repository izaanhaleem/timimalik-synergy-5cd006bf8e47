package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.SubIndicator;

import java.util.ArrayList;
import java.util.List;

@Table(name = "IndicatorsSaveAnswersTable")
public class CheckListSend extends Model {

    @Column(name = "mastID")
    @SerializedName("mastID")
    @Expose
    private Integer mastID;

    @Column(name = "IndicatorId")
    @SerializedName("IndicatorId")
    @Expose
    private Integer IndicatorId;

    @Column(name = "AppModuleId")
    private Integer AppModuleId;

    @Column(name = "Answer")
    @SerializedName("Answer")
    @Expose
    private String answer;

    @Column(name = "Comments")
    @SerializedName("Comments")
    @Expose
    private String comments;

    @Column(name = "CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;

    @Column(name = "IsOptional")
    @SerializedName("IsOptional")
    @Expose
    private Boolean isOptional;

    @Column(name = "CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

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

    @Column(name = "FieldType")
    @SerializedName("FieldType")
    @Expose
    private String fieldType;

    @Column(name = "Question")
    @SerializedName("Question")
    @Expose
    private String question;

    @Column(name = "SrNo")
    @SerializedName("SrNo")
    @Expose
    private String SrNo;

    public Boolean getOptional() {
        return isOptional;
    }

    public void setOptional(Boolean optional) {
        isOptional = optional;
    }

    private List<SubIndicator> subIndicators           = new ArrayList<>();

    private List<SubIndicator> subIndicatorsValidation = new ArrayList<>();

    public List<SubIndicator> getSubIndicatorsValidation() {
        return subIndicatorsValidation;
    }

    public void setSubIndicatorsValidation(List<SubIndicator> subIndicatorsValidation) {
        this.subIndicatorsValidation.clear();
        this.subIndicatorsValidation.addAll(subIndicatorsValidation) ;
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

    public Integer getMastID() {
        return mastID;
    }

    public void setMastID(Integer mastID) {
        this.mastID = mastID;
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

    public Integer getShowInCase() {
        return showInCase;
    }

    public void setShowInCase(Integer showInCase) {
        this.showInCase = showInCase;
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

    @Column(name = "RemarksPlaceHolderText")
    @SerializedName("RemarksPlaceHolderText")
    @Expose
    private String remarksPlaceHolderText;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    @Column(name = "sync")
    public String sync;

    public List<SubIndicator> getSubIndicators() {
        return subIndicators;
    }

    public void setSubIndicators(List<SubIndicator> subIndicators) {
        this.subIndicators = subIndicators;
    }

    public Integer getIndicatorId() {
        return IndicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        IndicatorId = indicatorId;
    }

    public Integer getAppModuleId() {
        return AppModuleId;
    }

    public void setAppModuleId(Integer appModuleId) {
        AppModuleId = appModuleId;
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
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public static List<CheckListSend> getAllRecord(String cb) {
        return new Select()
                .from(CheckListSend.class)
                .where("CreatedBy = ?", cb)
                .execute();
    }

    public static List<CheckListSend> getAllChecklist(Integer cb,String userId,String sync) {
        return new Select()
                .from(CheckListSend.class)
                .where("mastID = ?", cb)
                .where("CreatedBy = ?", userId)
                .where("sync=  ?",sync)
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(CheckListSend.class)
                .set("sync = 1")
                .where("mastID = ?", id)
                .execute();
    }

}

