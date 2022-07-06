package com.hisdu.ses.Models.appmodule;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "AppModulesTable")
public class Content extends Model {

    @Column(name = "AppModuleId")
    @SerializedName("AppModuleId")
    @Expose
    private Integer appModuleId;

    @Column(name = "AppModuleName")
    @SerializedName("AppModuleName")
    @Expose
    private String appModuleName;

    @Column(name = "IsActive")
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    @Column(name = "IsActive")
    @SerializedName("AppModuleTypes")
    @Expose
    private List<Object> appModuleTypes = null;

    @Column(name = "count")
    @SerializedName("count")
    @Expose
    private String  count;

    @Column(name = "image")
    @SerializedName("image")
    @Expose
    private Integer image;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }



    public Integer getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(Integer appModuleId) {
        this.appModuleId = appModuleId;
    }

    public String getAppModuleName() {
        return appModuleName;
    }

    public void setAppModuleName(String appModuleName) {
        this.appModuleName = appModuleName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Object> getAppModuleTypes() {
        return appModuleTypes;
    }

    public void setAppModuleTypes(List<Object> appModuleTypes) {
        this.appModuleTypes = appModuleTypes;
    }
    public static List<Content> getAll()

    {
        return new Select()
                .from(Content.class)
                .execute();
    }
}