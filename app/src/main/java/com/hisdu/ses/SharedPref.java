package com.hisdu.ses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private final String SharedPrefileName = "hcaccount";


    private Context _context;

    private SharedPref instance;


    public SharedPref(Context context) {
        _context = context;
    }

    public void SaveKeyValue(String key, String value) {
        SharedPreferences prefs = _context.getSharedPreferences(SharedPrefileName, Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }


    public String GetKeyValue(String key) {
        SharedPreferences prefs = _context.getSharedPreferences(SharedPrefileName, Context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }


    public void SaveCredentials(String token, String username, String password, String serverID, String roleName, String Location, String first, String last, String ucName, String storeType, String provinceId,String userType,String LocationName) {
        SaveKeyValue("isLoggedIn", "true");
        SaveKeyValue("access_token", token);
        SaveKeyValue("userName", username);
        SaveKeyValue("password", password);
        SaveKeyValue("serverID", serverID);
        SaveKeyValue("UserRole", roleName);
        SaveKeyValue("LocationID", Location);
        SaveKeyValue("FirstName", first);
        SaveKeyValue("LastName", last);
        SaveKeyValue("UcName", ucName);
        SaveKeyValue("storeType", storeType);
        SaveKeyValue("provinceId", provinceId);
        SaveKeyValue("UserType", userType);
        SaveKeyValue("LocationName",LocationName);

    }

    public void Saveappversion(String appversion) {
        SaveKeyValue("appversion", appversion);
    }

    public void Savelocation(String location) {
        SaveKeyValue("location", location);
    }

    public void Savehf(String hf) {
        SaveKeyValue("hf", hf);
    }

    public void SaveEpiStoreMonitoring(String checkList) {
        SaveKeyValue("storeMonitoringCheckList", checkList);
    }

    public void SaveEpiFixSite(String checkList) {
        SaveKeyValue("storeEpiFixSiteCheckList", checkList);
    }

    public void SaveEpiOutReach(String checkList) {
        SaveKeyValue("storeEpiOutReachCheckList", checkList);
    }

    public void SaveStoreTypes(String checkList) {
        SaveKeyValue("storeTypes", checkList);
    }

    public void SaveCampaignSchedule(String checkList) {
        SaveKeyValue("CampaignSchedule", checkList);
    }

    public void SaveProvinces(String provinces) {
        SaveKeyValue("provinces", provinces);
    }

    public void SaveModulesList(String checkList) {
        SaveKeyValue("modulesList", checkList);
    }

    public void SaveEpiCenters(String epicenter) {
        SaveKeyValue("epicenters", epicenter);
    }

    public void SaveLocationId(String LocationID) {
        SaveKeyValue("LocationID", LocationID);
    }

    public void SaveZeroDose(String zeroDose) {
        SaveKeyValue("zeroDose", zeroDose);
    }


    public void Saveuc(String uc) {
        SaveKeyValue("uc", uc);

    }
    public void SaveClusterType(String clusterType) {
        SaveKeyValue("clusterType", clusterType);
    }
    public void SaveContactInfo(String contactinfo) {
        SaveKeyValue("contactInfo", contactinfo);
    }

    public boolean CheckLoggedIn() {
        String LoggedIn = GetKeyValue("isLoggedIn");
        return (LoggedIn != null && LoggedIn.equals("true"));
    }

    public String GetLoggedInUser() {
        return GetKeyValue("userName");
    }

    public String GetLocatioName() {
        return GetKeyValue("LocationName");
    }

    public String GetUcName() {
        return GetKeyValue("UcName");
    }

    public String GetLoggedInPassword() {
        return GetKeyValue("password");
    }

    public String GetserverID() {
        return GetKeyValue("serverID");
    }

    public String GetLoggedInRole() {
        return GetKeyValue("UserRole");
    }

    public String GetLoggedInfullName() {
        return GetKeyValue("HFName");
    }

    public String GetLocationID() {
        return GetKeyValue("LocationID");
    }

    public String GetUserType() {
        return GetKeyValue("UserType");
    }
    public String GetProvinceId() {
        return GetKeyValue("provinceId");
    }

    public String GetLocationVersion() {
        return GetKeyValue("location");
    }

    public String GethfVersion() {
        return GetKeyValue("hf");
    }

    public String GetUcVersion() {
        return GetKeyValue("uc");
    }

    public String GetCheckListEpiStoreMonitoring() {
        return GetKeyValue("storeMonitoringCheckList");
    }

    public String GetClusterTypes() {
        return GetKeyValue("clusterType");
    }
    public String GetContactInfo() {
        return GetKeyValue("contactInfo");
    }
    public String GetStoreType() {
        return GetKeyValue("storeType");
    }

    public String GetCheckListFixSite() {
        return GetKeyValue("storeEpiFixSiteCheckList");
    }

    public String GetCheckListOutReach() {
        return GetKeyValue("storeEpiOutReachCheckList");
    }

    public String GetAppModulesList() {
        return GetKeyValue("modulesList");
    }

    public String GetStoreTypes() {
        return GetKeyValue("storeTypes");
    }
    public String GetCampaignSchedule() {
        return GetKeyValue("CampaignSchedule");
    }
    public String GetDistrictName() {
        return GetKeyValue("DistrictName");
    }

    public String GetFirstName() {
        return GetKeyValue("FirstName");
    }

    public String GetLastName() {
        return GetKeyValue("LastName");
    }

    public String GetProvinces() {
        return GetKeyValue("provinces");
    }

    public String GetEpiCenters() {
        return GetKeyValue("epicenters");
    }

    public String GetZeroDose() {
        return GetKeyValue("zeroDose");
    }


    public String GetDesignationVersion() {
        return GetKeyValue("designation");
    }


    public void Logout() {
        SaveCredentials(null, null, null, null, null, null, null, null, null, null, null,null,null);
        SaveKeyValue("isLoggedIn", null);
    }


    public void LoggedIn(String str, String str2) {
        SaveKeyValue("isloggedin", str2);
        SaveKeyValue("username", str);
    }


    public String GetToken() {

        return GetKeyValue("access_token");
    }

}
