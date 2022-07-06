package com.hisdu.ses.Models.indicators;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Database.IndicatorMasterDataSave;

import java.util.List;

@Table(name = "images")
public class CheckListImage extends Model {

    public CheckListImage() {
    }

    @SerializedName("masID")
    @Column(name = "MasterID")
    private Integer MasterID;

    @Column(name = "ImageUrl")
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;

    @Column(name = "sync")
    private String sync;

    public Integer getMasterID() {
        return MasterID;
    }

    public void setMasterID(Integer masterID) {
        MasterID = masterID;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public static List<CheckListImage> getAllImages(String AppModuleTypeId) {
        return new Select()
                .from(CheckListImage.class)
                .where("MasterID = ?", AppModuleTypeId)
                .where("sync = ?", 0)
                .execute();
    }

    public static void UpdateData(String id) {
        new Update(CheckListImage.class)
                .set("sync = 1")
                .where("MasterID = ?", id)
                .execute();
    }

}

