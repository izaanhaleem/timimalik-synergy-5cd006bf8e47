package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.SubIndicator;

import java.util.List;

@Table(name = "IndicatorsAnswersTable")
public class CheckListDetail extends Model {

    @Column(name = "mastID")
    @SerializedName("IndicatorId")
    @Expose
    private Integer indicatorId;

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
    private Integer createdBy;

    @Column(name = "CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    @Column(name = "Question")
    @SerializedName("Question")
    @Expose
    private String Question;

    @Column(name = "SrNo")
    @SerializedName("SrNo")
    @Expose
    private String SrNo;

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

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public List<SubIndicator> getSubIndicators() {
        return subIndicators;
    }

    public void setSubIndicators(List<SubIndicator> subIndicators) {
        this.subIndicators = subIndicators;
    }

    @Column(name = "Type")
    @SerializedName("Type")
    @Expose
    private String Type;

    private List<SubIndicator> subIndicators = null;

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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

}

