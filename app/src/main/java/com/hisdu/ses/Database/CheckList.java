package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@Table(name = "CheckList")
public class CheckList extends Model {

    @Column(name="Server_id")
    @SerializedName("Id")
    @Expose
    public Integer server_id;

    @Column(name="Text")
    @SerializedName("SecondaryText")
    @Expose
    public String text;

    @Column(name="Type")
    @SerializedName("Type")
    @Expose
    public String type;

    @Column(name="CheckListTypeName")
    @SerializedName("CheckListTypeName")
    @Expose
    public String checkListTypeName;

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

    public String getCheckListType() {
        return checkListTypeName;
    }

    public void setCheckListType(String checkListType) {
        this.checkListTypeName = checkListType;
    }

    public static List<CheckList> getAll()

    {
        return new Select()
                .from(CheckList.class)
                .execute();
    }

    public static List<CheckList> getAllIndicators(String type)

    {
        return new Select()
                .from(CheckList.class)
                .where("CheckListTypeName = ?",type)
                .execute();
    }

    public static List<CheckList> getDualIndicators(String type, String type2)

    {
        return new Select()
                .from(CheckList.class)
                .where("CheckListTypeName" + " = '" + type + "' or " + "CheckListTypeName" + " = '" + type2 + "'")
                .execute();
    }

}
