package com.hisdu.ses;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.Models.sidModel;

import java.util.List;

@Table(name = "CampaignSchedules")
public class CampaignSchedules extends Model {

    @Column(name = "CampaignScheduleId")
    @SerializedName("CampaignScheduleId")
    @Expose
    private Integer CampaignScheduleId;


    @Column(name = "CampaignTypeId")
    @SerializedName("CampaignTypeId")
    @Expose
    private Integer CampaignTypeId;

    @Column(name = "CampaignMonth")
    @SerializedName("CampaignMonth")
    @Expose
    private String CampaignMonth;


    @Column(name = "CampaignMonthTitle")
    @SerializedName("CampaignMonthTitle")
    @Expose
    private String CampaignMonthTitle;

    @Column(name = "Campaign")
    @SerializedName("Campaign")
    @Expose
    private String Campaign;

    public String getCampaignMonthTitle() {
        return CampaignMonthTitle;
    }

    public void setCampaignMonthTitle(String campaignMonthTitle) {
        CampaignMonthTitle = campaignMonthTitle;
    }

    public Integer getCampaignScheduleId() {
        return CampaignScheduleId;
    }

    public void setCampaignScheduleId(Integer campaignScheduleId) {
        CampaignScheduleId = campaignScheduleId;
    }

    public Integer getCampaignTypeId() {
        return CampaignTypeId;
    }

    public void setCampaignTypeId(Integer campaignTypeId) {
        CampaignTypeId = campaignTypeId;
    }

    public String getCampaignMonth() {
        return CampaignMonth;
    }

    public void setCampaignMonth(String campaignMonth) {
        CampaignMonth = campaignMonth;
    }

    public String getCampaign() {
        return Campaign;
    }

    public void setCampaign(String campaign) {
        Campaign = campaign;
    }


    public static List<CampaignSchedules> getAllSchedules() {
        return new Select()
                .from(CampaignSchedules.class)
                .execute();
    }

    public static List<CampaignSchedules> getCampaignById(String code) {
        return new Select()
                .from(CampaignSchedules.class)
                .where("CampaignTypeId = ?", code).execute();
    }
}
