package com.hisdu.ses;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.hisdu.SESCluster.models.clustersType.ClustersType;
import com.hisdu.SESCluster.models.clustersType.GetClustersType;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.Database.CheckList;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.LocationResponse;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.Models.GenericResponse;
import com.hisdu.ses.Models.ZeroDose.Designation;
import com.hisdu.ses.Models.ZeroDose.DesignationModel;
import com.hisdu.ses.Models.appVersion.AppVersion;
import com.hisdu.ses.Models.appVersion.GetAppVersions;
import com.hisdu.ses.Models.appmodule.AppModulesResponse;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.Models.contactUs.contactUsResponse;
import com.hisdu.ses.Database.contactinfoTable;
import com.hisdu.ses.Models.epiCenters.EpiCenter;
import com.hisdu.ses.Models.epiCenters.GetEpiCenters;
import com.hisdu.ses.Models.indicators.Indicator;
import com.hisdu.ses.Models.indicators.IndicatorsResponse;
import com.hisdu.ses.Models.indicators.SubIndicator;
import com.hisdu.ses.Models.login.LoginRequest;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.Models.provinces.GetProvinces;
import com.hisdu.ses.Models.provinces.Province;
import com.hisdu.ses.Models.sidModel;
import com.hisdu.ses.Models.storeTypes.Store;
import com.hisdu.ses.Models.storeTypes.StoreTypesResponse;
import com.hisdu.ses.utils.ServerCalls;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashActivity extends MerlinActivity implements Connectable, Disconnectable, Bindable {
    FrameLayout constraintLayout;
    private MerlinsBeard merlinsBeard;
    public SharedPref pref;
    boolean b = false;
    boolean acccess = false;

    public TextView status;

    AppVersion appverion;


    String CheckListEpiStoreMonitoring, CheckListEpiFixedSite, CheckListEpiOutReach, LocationVersion, StoreTypesVersion, AppModulesList, Provinces, EpiCenters, ZeroDoseVersion, ClusterTypes,ContactInfo,ResourseMaterial,CampaignSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        constraintLayout = findViewById(R.id.root_layout);
        status = findViewById(R.id.status);

        merlinsBeard = new MerlinsBeard.Builder().build(this);

        pref = new SharedPref(this);
        b = pref.CheckLoggedIn();

        new Handler(Looper.getMainLooper()).postDelayed(() -> abc(), 1000);
    }

    void abc() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
        String rationale = "Please provide permissions important to run this app...";
        Permissions.Options options = new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                startApp();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Warning!")
                        .setCancelable(false)
                        .setMessage("App require write, location & camera permission to run normally!")
                        .setPositiveButton("OK", (dialog, which) -> {

                            dialog.dismiss();
                            abc();

                        }).setNegativeButton("Deny", (dialog, which) -> {
                            dialog.dismiss();
                            finishAffinity();
                        }).show();
            }
        });
    }

    void startApp() {

        if (merlinsBeard.isConnectedToWifi() || merlinsBeard.isConnectedToMobileNetwork()) {
            merlinsBeard.hasInternetAccess(hasAccess -> {

                if (hasAccess) {

                    acccess = true;

                    ServerCalls.getInstance().GetAppVersions(new ServerCalls.OnAppVersions() {

                        @Override
                        public void onResult(final GetAppVersions appver) {

                            if (appver.getAppVersions().getAppVersion() != null) {
                                String version = BuildConfig.VERSION_NAME;
                                String latest = appver.getAppVersions().getAppVersion();
                                appverion = appver.getAppVersions();
                                AppController.appVersion = appver.getAppVersions();

                                if (version.equals(latest)) {
                                    DownloadUserData();
                                } else {
//                                    new AlertDialog.Builder(SplashActivity.this)
//                                            .setTitle("Update Available! "+appverion)
//                                            .setCancelable(true)
//                                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                                @Override
//                                                public void onCancel(DialogInterface dialog) {
//                                                    dialog.dismiss();
//                                                    DownloadUserData();
//                                                }
//                                            })
//                                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://hisduapps.pshealthpunjab.gov.pk/Upload/SES("+appverion+").apk")));
//                                                }
//                                            }).show();

                                    new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                            .setTitleText("Version Update ("+latest+")")

                                            .setContentText("Are you sure you want to update new Version?")
                                            .setConfirmText("Yes, Update")
                                            .showCancelButton(false)

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://hisduapps.pshealthpunjab.gov.pk/Upload/SES("+latest+").apk")));
                                                    sDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                }

                            } else {
                                new AlertDialog.Builder(SplashActivity.this)
                                        .setTitle("Something Went Wrong, Please try again later!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                finishAffinity();

                                            }
                                        }).show();
                            }

                        }

                        @Override
                        public void onFailed() {

                        }


                        @Override
                        public void onRequestFailed(int errorCode, String errorMessage) {
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setTitle(errorMessage)
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            finishAffinity();

                                        }
                                    }).show();
                        }
                    });
                } else {
                    acccess = false;
                    NoInternet();
                }
            });

        } else {
            acccess = false;
            NoInternet();
        }
    }


    void Next() {
        status.setText("");

        if (!b) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
            return;
        }

        ServerCalls.getInstance().LogIn(new LoginRequest(pref.GetLoggedInUser(), pref.GetLoggedInPassword()), new ServerCalls.OnLoginResult() {
            @Override
            public void onLoggedIn(LoginResponseLatest loginresponse) {
                String response = new Gson().toJson(loginresponse);
                Utils.saveUserProfileInfo(response, getApplicationContext());
                String locationCode = "";

                if (loginresponse.getData().getLocationCode() != null) {
                    locationCode = loginresponse.getData().getLocationCode();
                }

                new SharedPref(getApplicationContext()).SaveCredentials(loginresponse.getData().getToken(), loginresponse.getData().getUsername(), pref.GetLoggedInPassword(), loginresponse.getData().getUserId().toString(), loginresponse.getData().getUserRole(), locationCode, loginresponse.getData().getName(), "", loginresponse.getData().getLocationName(), loginresponse.getData().getStoreTypeId(), loginresponse.getData().getProvinceId(),loginresponse.getData().getUserType(),loginresponse.getData().getLocationName());
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onLoginFailed() {

            }

            @Override
            public void onRequestFailed(int errorCode, String errorMessage) {

            }
        });


    }

    void NoInternet() {

        Integer a = Location.getLocations().size();

        if (a != null) {
            if (a > 0) {
                if (new SharedPref(getApplicationContext()).CheckLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

            } else {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("No internet access, One time sync is required to run offline!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> {

                            dialog.dismiss();
                            finishAffinity();

                        }).show();
            }
        } else {
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("No internet access, One time sync is required to run offline!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, which) -> {

                        dialog.dismiss();
                        finishAffinity();

                    }).show();
        }

    }


    @Override
    protected Merlin createMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {

        AppController.getInstance().hasinternetAccess = true;
    }

    @Override
    public void onDisconnect() {

        AppController.getInstance().hasinternetAccess = false;
    }

    void DownloadUserData() {
        LocationVersion = (new SharedPref(getApplicationContext()).GetLocationVersion() != null) ? new SharedPref(getApplicationContext()).GetLocationVersion() : "0";
        CheckListEpiStoreMonitoring = (new SharedPref(getApplicationContext()).GetCheckListEpiStoreMonitoring() != null) ? new SharedPref(getApplicationContext()).GetCheckListEpiStoreMonitoring() : "0";
        CheckListEpiFixedSite = (new SharedPref(getApplicationContext()).GetCheckListFixSite() != null) ? new SharedPref(getApplicationContext()).GetCheckListFixSite() : "0";
        CheckListEpiOutReach = (new SharedPref(getApplicationContext()).GetCheckListOutReach() != null) ? new SharedPref(getApplicationContext()).GetCheckListOutReach() : "0";
        AppModulesList = (new SharedPref(getApplicationContext()).GetAppModulesList() != null) ? new SharedPref(getApplicationContext()).GetAppModulesList() : "0";
        StoreTypesVersion = (new SharedPref(getApplicationContext()).GetStoreTypes() != null) ? new SharedPref(getApplicationContext()).GetStoreTypes() : "0";
        CampaignSchedule = (new SharedPref(getApplicationContext()).GetCampaignSchedule() != null) ? new SharedPref(getApplicationContext()).GetCampaignSchedule() : "0";

        Provinces = (new SharedPref(getApplicationContext()).GetProvinces() != null) ? new SharedPref(getApplicationContext()).GetProvinces() : "0";
        EpiCenters = (new SharedPref(getApplicationContext()).GetEpiCenters() != null) ? new SharedPref(getApplicationContext()).GetEpiCenters() : "0";
        ZeroDoseVersion = (new SharedPref(getApplicationContext()).GetZeroDose() != null) ? new SharedPref(getApplicationContext()).GetZeroDose() : "0";
        ClusterTypes = (new SharedPref(getApplicationContext()).GetClusterTypes() != null) ? new SharedPref(getApplicationContext()).GetClusterTypes() : "0";


        ContactInfo = (new SharedPref(getApplicationContext()).GetContactInfo() != null) ? new SharedPref(getApplicationContext()).GetContactInfo() : "0";
//        ResourseMaterial = (new SharedPref(getApplicationContext()).GetClusterTypes() != null) ? new SharedPref(getApplicationContext()).GetClusterTypes() : "0";


        if (!CheckListEpiStoreMonitoring.equals(appverion.getEPIStoreMonitoring())) {
            getChecklist("1");
        } else if (!CheckListEpiFixedSite.equals(appverion.getEPIFixedSiteMonitoring())) {
            getChecklist("2");
        } else if (!CheckListEpiOutReach.equals(appverion.getEPIORSession())) {
            getChecklist("3");
        } else {
            if (!LocationVersion.equals(appverion.getLocations())) {
                getLocation();
            } else {
                if (!EpiCenters.equals(appverion.getEPICenters())) {
                    getEpiCenters();
                } else if (!ZeroDoseVersion.equals(appverion.getZeroDoseValidation())) {
                    GetSIDLookup();

                } else if (!ClusterTypes.equals(appverion.getClusters())) {
                    GetClusterTypes();
                } else if (!Provinces.equals(appverion.getProvince())) {
                    getProvinces();
                } else if (!StoreTypesVersion.equals(appverion.getStoreType())) {
                    getStores();
                }
                else if (!CampaignSchedule.equals(appverion.getCampaignSchedule())) {
                    GetSchedule();
                }
//                else if (!AppModulesList.equals(appverion.getAppModules())) {
//                    getModules();
//                }
                else if (!ContactInfo.equals(appverion.getContactUs())) {
                    getContactinfo();
                }
                else {
                    Next();
                }
            }
        }

    }


    void getContactinfo() {
        status.setText("Preparing ContactInfo....");
            ServerCalls.getInstance().GetcontactUs(new ServerCalls.OnContactUs() {
                @Override
                public void onResult(contactUsResponse contactResponse) {

                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(contactinfoTable.class);

                    contactinfoTable contactinfo = new contactinfoTable();


//                    contactinfo.setId(contactResponse.getData().getId());
                    contactinfo.setPrimaryEmail(contactResponse.getData().getPrimaryEmail());
                    contactinfo.setSecondaryEmail(contactResponse.getData().getSecondaryEmail());
                    contactinfo.setPrimaryContactNo(contactResponse.getData().getPrimaryContactNo());
                    contactinfo.setSecondaryContactNo(contactResponse.getData().getSecondaryContactNo());
//                    contactinfo.setTitle(contactResponse.getData().getTitle());
//                    contactinfo.setDescription(contactResponse.getData().getDescription());

                    contactinfo.save();

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();

                    new SharedPref(getApplicationContext()).SaveContactInfo(appverion.getContactUs());
                    DownloadUserData();

                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onRequestFailed(int errorCode, String errorMessage) {

                }
            });

    }

    void getStores() {

        status.setText("Preparing Stores....");

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetStoreTypes(new ServerCalls.OnStoreType() {
                @Override
                public void onResult(StoreTypesResponse appModuleResponse) {

                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(Store.class);

                    for (int index = 0; index < appModuleResponse.getList().size(); index++) {
                        Store content = new Store();

                        if (appModuleResponse.getList().get(index).getIsActive() != null) {
                            content.setIsActive(appModuleResponse.getList().get(index).getIsActive());
                        }

                        if (appModuleResponse.getList().get(index).getName() != null) {
                            content.setName(appModuleResponse.getList().get(index).getName());
                        }

                        if (appModuleResponse.getList().get(index).getStoreTypeId() != null) {
                            content.setStoreTypeId(appModuleResponse.getList().get(index).getStoreTypeId());
                        }

                        content.save();

                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    new SharedPref(getApplicationContext()).SaveStoreTypes(appverion.getStoreType());

                    DownloadUserData();


                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onRequestFailed(int errorCode, String errorMessage) {

                }
            });
        }
    }

    void getProvinces() {

        status.setText("Preparing Provinces....");

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetProvinces(new ServerCalls.OnProvince() {
                @Override
                public void onResult(GetProvinces provinces) {

                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(Province.class);

                    for (int index = 0; index < provinces.getProvinces().size(); index++) {
                        Province province = new Province();

                        if (provinces.getProvinces().get(index).getProvinceID() != null) {
                            province.setProvinceID(provinces.getProvinces().get(index).getProvinceID());
                        }

                        if (provinces.getProvinces().get(index).getProvinceName() != null) {
                            province.setProvinceName(provinces.getProvinces().get(index).getProvinceName());
                        }

                        province.save();

                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    new SharedPref(getApplicationContext()).SaveProvinces(appverion.getProvince());

                    DownloadUserData();


                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onRequestFailed(int errorCode, String errorMessage) {

                }
            });
        }
    }

    void getEpiCenters() {

        status.setText("Preparing Epi Centers....");

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetEpiCenters(new ServerCalls.OnEpiCenter() {
                @Override
                public void onSuccess(GetEpiCenters epiCenters) {

                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(EpiCenter.class);

                    for (int index = 0; index < epiCenters.getList().size(); index++) {
                        EpiCenter epiCenter = new EpiCenter();

                        if (epiCenters.getList().get(index).getCenterId() != null) {
                            epiCenter.setCenterId(epiCenters.getList().get(index).getCenterId());
                        }

                        if (epiCenters.getList().get(index).getCenterName() != null) {
                            epiCenter.setCenterName(epiCenters.getList().get(index).getCenterName());
                        }

                        if (epiCenters.getList().get(index).getLattitude() != null) {
                            epiCenter.setLattitude(epiCenters.getList().get(index).getLattitude());
                        }
                        if (epiCenters.getList().get(index).getLongitude() != null) {
                            epiCenter.setLongitude(epiCenters.getList().get(index).getLongitude());
                        }
                        if (epiCenters.getList().get(index).getPKCODE() != null) {
                            epiCenter.setPKCODE(epiCenters.getList().get(index).getPKCODE());
                        }

                        epiCenter.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    new SharedPref(getApplicationContext()).SaveEpiCenters(appverion.getEPICenters());

                    DownloadUserData();

                }

                @Override
                public void onFailed(int errorCode, String errorMessage) {

                }


            });
        }
    }


    void GetSIDLookup() {

        status.setText("Preparing SID....");

        ServerCalls.getInstance().GetSIDLookup(new ServerCalls.OnGenericResponseResult() {
            @Override
            public void onSuccess(GenericResponse response) {
                if (!response.getError()) {
                    if (response.getContent() != null && response.getContent().size() > 0) {

                        ActiveAndroid.beginTransaction();
                        AppController.clearTable(sidModel.class);

                        List<sidModel> SidList = response.getContent();

                        if (SidList.size() != 0) {

                            for (int i = 0; i < SidList.size(); i++) {
                                sidModel sidModel = new sidModel();

                                if (SidList.get(i).getName() != null) {
                                    sidModel.setName(SidList.get(i).getName());
                                }
                                if (SidList.get(i).getSiaId() != null) {
                                    sidModel.setSiaId(SidList.get(i).getSiaId());
                                }

                                sidModel.save();
                            }

                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();
                            new SharedPref(getApplicationContext()).SaveZeroDose(appverion.getZeroDoseValidation());

                            DownloadUserData();
                        } else {
                            Toast.makeText(SplashActivity.this, "Error Loading SIDs", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                Toast.makeText(SplashActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void GetSchedule() {

        status.setText("Preparing Campaign....");

        ServerCalls.getInstance().GetCampaignSchedule(new ServerCalls.OnScheduleResponseResult() {
            @Override
            public void onSuccess(SchedulesResponse response) {
                if (!response.getError()) {
                    if (response.getContent() != null && response.getContent().size() > 0) {

                        ActiveAndroid.beginTransaction();
                        AppController.clearTable(CampaignSchedules.class);

                        List<CampaignSchedules> schedulelist = response.getContent();

                        if (schedulelist.size() != 0) {

                            for (int i = 0; i < schedulelist.size(); i++) {
                                CampaignSchedules camapingModel = new CampaignSchedules();

                                if (schedulelist.get(i).getCampaignScheduleId() != null) {
                                    camapingModel.setCampaignScheduleId(schedulelist.get(i).getCampaignScheduleId());
                                }
                                if (schedulelist.get(i).getCampaignTypeId() != null) {
                                    camapingModel.setCampaignTypeId(schedulelist.get(i).getCampaignTypeId());
                                }
                                if (schedulelist.get(i).getCampaignMonth() != null) {
                                    camapingModel.setCampaignMonth(schedulelist.get(i).getCampaignMonth());
                                }
                                if (schedulelist.get(i).getCampaign() != null) {
                                    camapingModel.setCampaign(schedulelist.get(i).getCampaign());
                                }
                                if(schedulelist.get(i).getCampaignMonthTitle()!=null){
                                    camapingModel.setCampaignMonthTitle(schedulelist.get(i).getCampaignMonthTitle());

                                }

                                camapingModel.save();
                            }

                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();
                            new SharedPref(getApplicationContext()).SaveCampaignSchedule(appverion.getCampaignSchedule());

                            DownloadUserData();
                        } else {

//                            Toast.makeText(SplashActivity.this, "Loading SIDs", Toast.LENGTH_SHORT).show();
                            new SharedPref(getApplicationContext()).SaveCampaignSchedule(appverion.getCampaignSchedule());
                            DownloadUserData();
                        }
                    }else {
                        new SharedPref(getApplicationContext()).SaveCampaignSchedule(appverion.getCampaignSchedule());
                        DownloadUserData();
                    }
                }
            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                Toast.makeText(SplashActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void GetClusterTypes() {

        status.setText("Preparing Clusters....");

        ServerCalls.getInstance().GetClusterType(new ServerCalls.OnClusterTypes() {
            @Override
            public void onSuccess(GetClustersType response) {
                if (!response.getError()) {
                    if (response.getData() != null && response.getData().size() > 0) {

                        ActiveAndroid.beginTransaction();
                        AppController.clearTable(ClustersType.class);

                        List<ClustersType> SidList = response.getData();

                        if (SidList.size() != 0) {
                            for (int i = 0; i < SidList.size(); i++) {
                                ClustersType sidModel = new ClustersType();
                                if (SidList.get(i).getName() != null) {
                                    sidModel.setName(SidList.get(i).getName());
                                }
                                if (SidList.get(i).getClusterTypeId() != null) {
                                    sidModel.setClusterTypeId(SidList.get(i).getClusterTypeId());
                                }

                                sidModel.save();
                            }

                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();
                            new SharedPref(getApplicationContext()).SaveClusterType(appverion.getClusters());

                            DownloadUserData();
                        } else {
                            Toast.makeText(SplashActivity.this, "Error Loading SIDs", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                Toast.makeText(SplashActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getChecklist(String appModuleId) {

        status.setText("Preparing Checklist....");

        ServerCalls.getInstance().GetIndicators(appModuleId, new ServerCalls.OnIndicatorsResult() {
            @Override
            public void onResult(IndicatorsResponse appModuleResponse) {

                ActiveAndroid.beginTransaction();

                for (int a = 0; a < appModuleResponse.getData().size(); a++)

                {

                    Indicator main = new Indicator();

                    if (appModuleResponse.getData().get(a).getAppModuleTypeId() != null) {
                        main.setAppModuleTypeId(appModuleResponse.getData().get(a).getAppModuleId());

                    }
                    if (appModuleResponse.getData().get(a).getName() != null) {
                        main.setQuestion(appModuleResponse.getData().get(a).getName());

                    }

                    main.setIndicatorId(-1);
                    main.setNAShow(false);
                    main.setRemarksShow(false);
                    main.setShowRemarksInCase(0);
                    main.setRemarksMandatory(false);


                    if (appModuleResponse.getData().get(a).getFieldType() != null) {
                        main.setFieldType(appModuleResponse.getData().get(a).getFieldType());

                    }

                    if (appModuleResponse.getData().get(a).getIsActive() != null) {
                        main.setIsActive(appModuleResponse.getData().get(a).getIsActive());


                    }

                    if (appModuleResponse.getData().get(a).getSequenceNo() != null) {
                        main.setSequenceNo(appModuleResponse.getData().get(a).getSequenceNo());

                    }

                    if (appModuleResponse.getData().get(a).getSequenceNo() != null) {
                        main.setSequenceNo(appModuleResponse.getData().get(a).getSequenceNo());

                    }

                    main.save();

                    if (appModuleResponse.getData().get(a).getIndicators() != null || appModuleResponse.getData().get(0).getIndicators().size() > 0) {

                        for (int index = 0; index < appModuleResponse.getData().get(a).getIndicators().size(); index++) {
                            Indicator indicator = new Indicator();

                            if (appModuleResponse.getData().get(a).getAppModuleTypeId() != null) {
                                indicator.setAppModuleTypeId(appModuleResponse.getData().get(a).getAppModuleId());

                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getSrNo() != null) {
                                indicator.setSrNo(appModuleResponse.getData().get(a).getIndicators().get(index).getSrNo());

                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getFieldType() != null) {
                                indicator.setFieldType(appModuleResponse.getData().get(a).getIndicators().get(index).getFieldType());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getIndicatorId() != null) {
                                indicator.setIndicatorId(appModuleResponse.getData().get(a).getIndicators().get(index).getIndicatorId());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getIsActive() != null) {
                                indicator.setIsActive(appModuleResponse.getData().get(a).getIndicators().get(index).getIsActive());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getIsOptional() != null) {
                                indicator.setIsOptional(appModuleResponse.getData().get(a).getIndicators().get(index).getIsOptional());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getQuestion() != null) {
                                indicator.setQuestion(appModuleResponse.getData().get(a).getIndicators().get(index).getQuestion());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getSequenceNo() != null) {
                                indicator.setSequenceNo(appModuleResponse.getData().get(a).getIndicators().get(index).getSequenceNo());


                            }

                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getNAShow() != null) {
                                indicator.setNAShow(appModuleResponse.getData().get(a).getIndicators().get(index).getNAShow());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksMandatory() != null) {
                                indicator.setRemarksMandatory(appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksMandatory());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksShow() != null) {
                                indicator.setRemarksShow(appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksShow());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getShowRemarksInCase() != null) {
                                indicator.setShowRemarksInCase(appModuleResponse.getData().get(a).getIndicators().get(index).getShowRemarksInCase());


                            }
                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksPlaceHolderText() != null) {
                                indicator.setRemarksPlaceHolderText(appModuleResponse.getData().get(a).getIndicators().get(index).getRemarksPlaceHolderText());
                            }

                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getShowInCase() != null) {
                                indicator.setShowInCase(appModuleResponse.getData().get(a).getIndicators().get(index).getShowInCase());
                            }

                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getOptional() != null) {
                                indicator.setOptional(appModuleResponse.getData().get(a).getIndicators().get(index).getOptional());
                            }

                            indicator.save();


                            if (appModuleResponse.getData().get(a).getIndicators().get(index).getSubIndicators() != null) {
                                indicator.setSubIndicators(appModuleResponse.getData().get(a).getIndicators().get(index).getSubIndicators());

                                for (int index3 = 0; index3 < indicator.getSubIndicators().size(); index3++) {
                                    SubIndicator subIndicator = new SubIndicator();

                                    if (indicator.getSubIndicators().get(index3).getAppModuleTypeId() != null) {

                                        subIndicator.setAppModuleTypeId(indicator.getSubIndicators().get(index3).getAppModuleTypeId());
                                    }

                                    if (indicator.getSubIndicators().get(index3).getSrNo() != null) {
                                        subIndicator.setSrNo(indicator.getSubIndicators().get(index3).getSrNo() );

                                    }

                                    if (indicator.getSubIndicators().get(index3).getFieldType() != null) {

                                        subIndicator.setFieldType(indicator.getSubIndicators().get(index3).getFieldType());
                                    }

                                    if (indicator.getSubIndicators().get(index3).getIndicatorId() != null) {

                                        subIndicator.setIndicatorId(indicator.getSubIndicators().get(index3).getIndicatorId());

                                    }
                                    if (indicator.getSubIndicators().get(index3).getIndicatorParentId() != null) {

                                        subIndicator.setIndicatorParentId(indicator.getSubIndicators().get(index3).getIndicatorParentId());

                                    }
                                    if (indicator.getSubIndicators().get(index3).getIsActive() != null) {

                                        subIndicator.setIsActive(indicator.getSubIndicators().get(index3).getIsActive());

                                    }
                                    if (indicator.getSubIndicators().get(index3).getSequenceNo() != null) {

                                        subIndicator.setSequenceNo(indicator.getSubIndicators().get(index3).getSequenceNo());

                                    }
                                    if (indicator.getSubIndicators().get(index3).getQuestion() != null) {

                                        subIndicator.setQuestion(indicator.getSubIndicators().get(index3).getQuestion());

                                    }
                                    if (indicator.getSubIndicators().get(index3).getNAShow() != null) {
                                        subIndicator.setNAShow(indicator.getSubIndicators().get(index3).getNAShow());


                                    }
                                    if (indicator.getSubIndicators().get(index3).getRemarksMandatory() != null) {
                                        subIndicator.setRemarksMandatory(indicator.getSubIndicators().get(index3).getRemarksMandatory());


                                    }
                                    if (indicator.getSubIndicators().get(index3).getRemarksShow() != null) {
                                        subIndicator.setRemarksShow(indicator.getSubIndicators().get(index3).getRemarksShow());


                                    }
                                    if (indicator.getSubIndicators().get(index3).getShowRemarksInCase() != null) {
                                        subIndicator.setShowRemarksInCase(indicator.getSubIndicators().get(index3).getShowRemarksInCase());


                                    }
                                    if (indicator.getSubIndicators().get(index3).getRemarksPlaceHolderText() != null) {
                                        subIndicator.setRemarksPlaceHolderText(indicator.getSubIndicators().get(index3).getRemarksPlaceHolderText());
                                    }

                                    if (indicator.getSubIndicators().get(index3).getShowInCase() != null) {
                                        subIndicator.setShowInCase(indicator.getSubIndicators().get(index3).getShowInCase());
                                    }

                                    if (indicator.getSubIndicators().get(index3).getIsOptional() != null) {
                                        subIndicator.setIsOptional(indicator.getSubIndicators().get(index3).getIsOptional());
                                    }

                                    subIndicator.save();

                                }

                            }
                        }


                    } else {
                        Toast.makeText(SplashActivity.this, "Error Loading CheckList", Toast.LENGTH_SHORT).show();
                    }
                }

                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();

                if (appModuleId.equals("1")) {
                    new SharedPref(getApplicationContext()).SaveEpiStoreMonitoring(appverion.getEPIStoreMonitoring());
                } else if (appModuleId.equals("2")) {
                    new SharedPref(getApplicationContext()).SaveEpiFixSite(appverion.getEPIFixedSiteMonitoring());
                } else if (appModuleId.equals("3")) {
                    new SharedPref(getApplicationContext()).SaveEpiOutReach(appverion.getEPIORSession());
                }

                DownloadUserData();

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onRequestFailed(int errorCode, String errorMessage) {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Oh Crap!")
                        .setCancelable(false)
                        .setMessage("Failed to checkList, Retry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                getChecklist(appModuleId);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        }).show();
            }
        });


    }


    void getLocation() {
        status.setText("Preparing Locations....");

        ServerCalls.getInstance().GetLocationData("0", new ServerCalls.OnLocationResult() {
            @Override
            public void onSuccess(final LocationResponse location) {

                ActiveAndroid.beginTransaction();

                if (location.getList() != null || location.getList().size() > 0) {
                    AppController.clearTable(Location.class);

                    for (int i = 0; i < location.getList().size(); i++) {
                        Location UI = new Location();

                        if (location.getList().get(i).getServer_id() != null) {
                            UI.server_id = location.getList().get(i).getServer_id();
                        }

                        if (location.getList().get(i).getName() != null) {
                            UI.name = location.getList().get(i).getName();
                        }

                        if (location.getList().get(i).getType() != null) {
                            UI.type = location.getList().get(i).getType();
                        }
                        if (location.getList().get(i).getProvinceId() != null) {
                            UI.ProvinceId = location.getList().get(i).getProvinceId();
                        }

                        UI.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    new SharedPref(getApplicationContext()).Savelocation(appverion.getLocations());

                    DownloadUserData();

                } else {
                    Toast.makeText(SplashActivity.this, "Error Loading Location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {

                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Oh Crap!")
                        .setCancelable(false)
                        .setMessage("Failed to sync location, Retry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getLocation();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        }).show();
            }
        });

    }

    void getUcData() {
        status.setText("Preparing UCs....");

        ServerCalls.getInstance().GetCenterData(new ServerCalls.OnUcSaveResult() {
            @Override
            public void onSuccess(final List<UcData> ucdata) {

                ActiveAndroid.beginTransaction();

                if (ucdata != null || ucdata.size() > 0) {
                    AppController.clearTable(UcData.class);

                    for (int i = 0; i < ucdata.size(); i++) {
                        UcData UI = new UcData();

                        if (ucdata.get(i).getServerID() != null) {
                            UI.ServerID = ucdata.get(i).getServerID();
                        }

                        if (ucdata.get(i).getTehsilCode() != null) {
                            UI.TehsilCode = ucdata.get(i).getTehsilCode();
                        }

                        if (ucdata.get(i).getUCName() != null) {
                            UI.UCName = ucdata.get(i).getUCName();
                        }

                        if (ucdata.get(i).getName() != null) {
                            UI.Name = ucdata.get(i).getName();
                        }

                        if (ucdata.get(i).getLatitude() != null) {
                            UI.latitude = ucdata.get(i).getLatitude();
                        }

                        if (ucdata.get(i).getLongitude() != null) {
                            UI.Longitude = ucdata.get(i).getLongitude();
                        }

                        UI.save();

                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();

//                    pref.Saveuc(appverion.getVersionUc());

                    Next();

                } else {
                    Toast.makeText(SplashActivity.this, "Error Loading Ucs", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Oh Crap!")
                        .setCancelable(false)
                        .setMessage("Failed to sync UCs, Retry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getUcData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        }).show();
            }
        });

    }

}
