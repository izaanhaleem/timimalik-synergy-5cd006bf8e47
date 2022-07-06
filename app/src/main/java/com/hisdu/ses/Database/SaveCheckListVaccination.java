package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@Table(name = "SaveIndicatorsFormVaccination")
public class SaveCheckListVaccination extends Model {

    @Column(name="mastId")
    @SerializedName("mastId")
    @Expose
    public Integer mastId;

    @Column(name="Server_id")
    @SerializedName("VaccinationCheckListId")
    @Expose
    public Integer server_id;

    @Column(name="Text")
    @SerializedName("Text")
    @Expose
    public String text;

    @Column(name="Answer1")
    @SerializedName("ValueDue")
    @Expose
    public String answer1;

    @Column(name="Answer2")
    @SerializedName("ValueDefaulter")
    @Expose
    public String answer2;

    @Column(name="Answer3")
    @SerializedName("ValueZeroDose")
    @Expose
    public String answer3;

    @Column(name="Answer4")
    @SerializedName("FValueDue")
    @Expose
    public String answer4;

    @Column(name="Answer5")
    @SerializedName("FValueDefaulter")
    @Expose
    public String answer5;

    @Column(name="Answer6")
    @SerializedName("FValueZeroDose")
    @Expose
    public String answer6;

    @Column(name="PersonType")
    @SerializedName("PersonType")
    @Expose
    public String PersonType;

    @Column(name="Type")
    @SerializedName("Type")
    @Expose
    public String type;

    @Column(name="CheckListTypeName")
    @SerializedName("CheckListTypeName")
    @Expose
    public String checkListTypeName;

    @Column(name="Remarks")
    @SerializedName("Remarks")
    @Expose
    public String remarks;

    @Column(name="Header")
    @SerializedName("Header")
    @Expose
    public String header;

    @Column(name="sync")
    @SerializedName("Sync")
    @Expose
    public String sync;

    @Column(name="CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;

    @Column(name="InputType")
    @SerializedName("InputType")
    @Expose
    public String inputtype;

    public boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getPersonType() {
        return PersonType;
    }

    public void setPersonType(String personType) {
        PersonType = personType;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckListTypeName() {
        return checkListTypeName;
    }

    public void setCheckListTypeName(String checkListTypeName) {
        this.checkListTypeName = checkListTypeName;
    }
    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public Integer getMastId() {
        return mastId;
    }

    public void setMastId(Integer mastId) {
        this.mastId = mastId;
    }

    public static List<SaveCheckListVaccination> getChecklist(String type)

    {
        return new Select()
                .from(SaveCheckListVaccination.class)
                .where("CheckListTypeName = ?",type)
                .execute();
    }

    public static List<SaveCheckListVaccination> getAllChecklist(Integer cb)

    {
        return new Select()
                .from(SaveCheckListVaccination.class)
                .where("mastId = ?",cb)
                .execute();
    }

    public static void UpdateData (Integer id)

    {
        new Update(SaveCheckListVaccination.class)
                .set("sync = 1")
                .where("mastId = ?", id)
                .execute();
    }

    public static void UpdateflagData ()

    {
        new Update(SaveCheckListVaccination.class)
                .set("sync = 0")
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(SaveCheckListVaccination.class).where("mastId = ?",id).execute();
    }

}
