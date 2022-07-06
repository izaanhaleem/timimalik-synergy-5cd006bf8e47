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

@Table(name = "SaveIndicatorsForm")
public class SaveChecklist extends Model {

    @Column(name="mastId")
    @SerializedName("mastId")
    @Expose
    public Integer mastId;

    @Column(name="Server_id")
    @SerializedName("CheckListId")
    @Expose
    public Integer server_id;

    @Column(name="Text")
    @SerializedName("Text")
    @Expose
    public String text;

    @Column(name="Answer")
    @SerializedName("Value")
    @Expose
    public String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public static List<SaveChecklist> getChecklist(String type)

    {
        return new Select()
                .from(SaveChecklist.class)
                .where("CheckListTypeName = ?",type)
                .execute();
    }

    public static List<SaveChecklist> getAllChecklist(Integer cb)

    {
        return new Select()
                .from(SaveChecklist.class)
                .where("mastId = ?",cb)
                .execute();
    }

    public static void UpdateData (Integer id)

    {
        new Update(SaveChecklist.class)
                .set("sync = 1")
                .where("mastId = ?", id)
                .execute();
    }

    public static void UpdateflagData ()

    {
        new Update(SaveChecklist.class)
                .set("sync = 0")
                .execute();
    }

    public static void DeleteData (Integer id)

    {
        new Delete().from(SaveChecklist.class).where("mastId = ?",id).execute();
    }

}
