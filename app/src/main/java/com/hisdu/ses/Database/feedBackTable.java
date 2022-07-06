package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "feedBackTable")
public class feedBackTable extends Model {

    @Column(name = "FeedBackId")
    @SerializedName("FeedBackId")
    @Expose
    public Integer feedBackId;

    @Column(name = "Description")
    @SerializedName("Description")
    @Expose
    public String description;

    @Column(name = "SyncDateTime")
    @SerializedName("SyncDateTime")
    @Expose
    public String syncDateTime;

    @Column(name = "CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    public Integer createdBy;

    @Column(name = "CreatedOn")
    @SerializedName("CreatedOn")
    @Expose
    public String createdOn;

    @Column(name = "UpdatedBy")
    @SerializedName("UpdatedBy")
    @Expose
    public Integer updatedBy;

    @Column(name = "UpdatedOn")
    @SerializedName("UpdatedOn")
    @Expose
    public String updatedOn;

    @Column(name = "ActionTypeId")
    @SerializedName("ActionTypeId")
    @Expose
    public Integer actionTypeId;

    public Integer getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Integer feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyncDateTime() {
        return syncDateTime;
    }

    public void setSyncDateTime(String syncDateTime) {
        this.syncDateTime = syncDateTime;
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }


    public static List<feedBackTable> getAllcontactInfo() {
        return new Select()
                .from(feedBackTable.class)
                .execute();
    }
}
