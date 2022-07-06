package com.hisdu.ses.Database;

import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;
import com.hisdu.SESCluster.models.clustersType.ClustersType;
import com.hisdu.ses.CampaignSchedules;
import com.hisdu.ses.Models.ZeroDose.Designation;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.Models.epiCenters.EpiCenter;
import com.hisdu.ses.Models.indicators.CheckListImage;
import com.hisdu.ses.Models.indicators.Indicator;
import com.hisdu.ses.Models.indicators.SubIndicator;
import com.hisdu.ses.Models.provinces.Province;
import com.hisdu.ses.Models.storeTypes.Store;
import com.hisdu.ses.Models.sidModel;

public class DatabaseContentProvider extends ContentProvider {

    @Override
    protected Configuration getConfiguration() {
        Configuration.Builder builder = new Configuration.Builder(getContext());
        builder.addModelClass(SaveInspections.class);
        builder.addModelClass(SaveInspectionVaccination.class);
        builder.addModelClass(CheckList.class);
        builder.addModelClass(CheckListVaccination.class);
        builder.addModelClass(SaveChecklist.class);
        builder.addModelClass(SaveCheckListVaccination.class);
        builder.addModelClass(Location.class);
        builder.addModelClass(ClusterForm.class);
        builder.addModelClass(UcData.class);
        builder.addModelClass(IndicatorMasterDataSave.class);
        builder.addModelClass(CheckListDetail.class);
        builder.addModelClass(CheckListSend.class);
        builder.addModelClass(Content.class);
        builder.addModelClass(Indicator.class);
        builder.addModelClass(SubIndicator.class);
        builder.addModelClass(Store.class);
        builder.addModelClass(Province.class);
        builder.addModelClass(EpiCenter.class);
        builder.addModelClass(sidModel.class);
        builder.addModelClass(ClustersType.class);
        builder.addModelClass(CheckListImage.class);
        builder.addModelClass(House.class);
        builder.addModelClass(HouseDetail.class);
        builder.addModelClass(HouseChildDetail.class);
        builder.addModelClass(contactinfoTable.class);
        builder.addModelClass(feedBackTable.class);
        builder.addModelClass(ZeroDoseDetail.class);
        builder.addModelClass(ZeroDoseMaster.class);
        builder.addModelClass(Designation.class);
        builder.addModelClass(ZeroDoseChildModel.class);
        builder.addModelClass(ZeroDoseMain.class);
        builder.addModelClass(CampaignSchedules.class);


        return builder.create();
    }
}