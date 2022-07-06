package com.hisdu.ses.Models.ZeroDose;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "DesignationTable")
public class Designation extends Model {



    @Column(name = "DesignationId")
    @SerializedName("DesignationId")
    @Expose
    private int designationId;

    @Column(name = "IsActive")
    @SerializedName("IsActive")
    @Expose
    private boolean isActive;

    @Column(name = "DesignationName")
    @SerializedName("DesignationName")
    @Expose
    private String designationName;

    @Column(name = "IsSIADesignation")
    @SerializedName("IsSIADesignation")
    @Expose
    private boolean isSIADesignation;

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public boolean isSIADesignation() {
        return isSIADesignation;
    }

    public void setSIADesignation(boolean SIADesignation) {
        isSIADesignation = SIADesignation;
    }

    public static List<Designation> getAll()

    {
        return new Select()
                .from(Designation.class)
                .execute();
    }
}