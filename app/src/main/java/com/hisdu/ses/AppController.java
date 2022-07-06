package com.hisdu.ses;

import android.location.Location;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.hisdu.ses.Database.CheckListDetail;
import com.hisdu.ses.Database.CheckListSend;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.SaveCheckListVaccination;
import com.hisdu.ses.Database.SaveChecklist;
import com.hisdu.ses.Database.SaveInspections;
import com.hisdu.ses.Database.ZeroDoseChildModel;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Models.appVersion.AppVersion;

import java.util.ArrayList;
import java.util.List;


public class AppController {

    public static Location location;
    public static String date;
    public static String dateOnly;
    public Boolean hasinternetAccess    = false;
    public Boolean isServiceRunning     = false;
    public static Integer MasterID      = null;
    public static String ProvinceID      = null;
    public static String  checklistType = null;
    public static String  checklistVaccinationType = null;
    public static IndicatorMasterDataSave indicatorSave    = null;
    public static SaveInspections saveInspections = null;
    public static IndicatorMasterDataSave indicatorMasterDataSave = null;
    public static CheckListDetail checkListDetail    = null;
    public static List<CheckListSend> saveIndicators = new ArrayList<>();
    public static List<SaveChecklist> saveChecklists = new ArrayList<>();
    public static List<SaveCheckListVaccination> saveChecklistsVaccination = new ArrayList<>();
    public static AppVersion appVersion;
    public static Integer appModuleID;
    public static String clusterType;
    public static String hrmp;
    public static String area;
    public static String siaTeamNo;
    public static String day;

    public ZeroDoseDetail zeroDoseDetail;
    public ZeroDoseChildModel zeroDoseChild;



    private AppController() {}
    private static AppController instance;


    public static AppController getInstance()

    {
        if(instance == null)
            instance = new AppController();

        return instance;
    }

    public static void clearTable(Class<? extends Model> type) {
        TableInfo tableInfo = Cache.getTableInfo(type);
        ActiveAndroid.execSQL(String.format("DELETE FROM %s;", tableInfo.getTableName()));
        ActiveAndroid.execSQL(
                String.format("DELETE FROM sqlite_sequence WHERE name='%s';", tableInfo.getTableName()));
    }

}
