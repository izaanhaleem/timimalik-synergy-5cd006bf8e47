package com.hisdu.ses.utils;

import android.util.Log;

import com.hisdu.SESCluster.models.clustersType.GetClustersType;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.ses.Database.CheckList;
import com.hisdu.ses.Database.CheckListVaccination;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.LocationResponse;
import com.hisdu.ses.Database.SaveInspectionVaccination;
import com.hisdu.ses.Database.SaveInspections;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.Database.ZeroDoseMain;
import com.hisdu.ses.Database.feedBackTable;
import com.hisdu.ses.Models.AppInfo.appInfoLinkResponse;
import com.hisdu.ses.Models.AppInfo.folderResponse;
import com.hisdu.ses.Models.ClusterModel;
import com.hisdu.ses.Models.GenericResponse;
import com.hisdu.ses.Models.IndicatorSaveResponse;
import com.hisdu.ses.Models.AppInfo.appInfoResourseRequest;
import com.hisdu.ses.Models.AppInfo.appInfopagesize;
import com.hisdu.ses.Models.RefusalReasonModel;
import com.hisdu.ses.Models.UcGetModel;
import com.hisdu.ses.Database.ZeroDoseMaster;
import com.hisdu.ses.Models.ZeroDose.DesignationModel;
import com.hisdu.ses.Models.appVersion.GetAppVersions;
import com.hisdu.ses.Models.appmodule.AppModulesResponse;
import com.hisdu.ses.Models.contactUs.contactUsResponse;
import com.hisdu.ses.Models.epiCenters.GetEpiCenters;
import com.hisdu.ses.Models.feedback.feedbackResponse;
import com.hisdu.ses.Models.getAppDetailsResponse.HisduAppStatusResponse;
import com.hisdu.ses.Models.indicators.IndicatorsResponse;
import com.hisdu.ses.Models.login.LoginRequest;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.Models.provinces.GetProvinces;
import com.hisdu.ses.Models.storeTypes.StoreTypesResponse;
import com.hisdu.ses.MyApplication;
import com.hisdu.ses.SchedulesResponse;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.ZeroDoseVaccination.ChildModel;
import com.hisdu.ses.ZeroDoseVaccination.SaveChildModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerCalls {


    private ServerCalls() {
    }

    public static ServerCalls getInstance() {
        if (instance == null) instance = new ServerCalls();
        return instance;
    }


    private static ServerCalls instance = null;

    public void GetcontactUs(final OnContactUs callback) {
        HttpClient.getHttpService().contactresoponse(getAuthToken()).enqueue(new Callback<contactUsResponse>() {
            @Override
            public void onResponse(Call<contactUsResponse> call, Response<contactUsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<contactUsResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


    public void feedBack(feedBackTable feedback, final OnFeedBackResult callback) {
        HttpClient.getHttpService().feedback(feedback).enqueue(new Callback<feedbackResponse>() {
            @Override
            public void onResponse(Call<feedbackResponse> call, Response<feedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }

                else callback.onFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<feedbackResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }
        });
    }


    public void appinfo(appInfoResourseRequest infoRequest, final OnAppInfoResult callback) {
        HttpClient.getHttpService().getallResourse(infoRequest).enqueue(new Callback<appInfopagesize>() {
            @Override
            public void onResponse(Call<appInfopagesize> call, Response<appInfopagesize> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                }

                else callback.onFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<appInfopagesize> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }
        });
    }


    public void getFolders( final OnFolderResult callback) {
        HttpClient.getHttpService().getFolders().enqueue(new Callback<folderResponse>() {
            @Override
            public void onResponse(Call<folderResponse> call, Response<folderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                }

                else callback.onFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<folderResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void getAppInfoLink(int masterid, final OnappinfoLink callback) {
        HttpClient.getHttpService().GetAppInfoLink(masterid).enqueue(new Callback<appInfoLinkResponse>() {
            @Override
            public void onResponse(Call<appInfoLinkResponse> call, Response<appInfoLinkResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess((appInfoLinkResponse.Data) response.body().getData());
                }

                else callback.onFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<appInfoLinkResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }
        });
    }



    public void LogIn(LoginRequest loginRequest, final OnLoginResult callback) {
        HttpClient.getHttpService().loginRequest(loginRequest).enqueue(new Callback<LoginResponseLatest>() {
            @Override
            public void onResponse(Call<LoginResponseLatest> call, Response<LoginResponseLatest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoggedIn(response.body());
                } else if (response.code() < 500) callback.onLoginFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<LoginResponseLatest> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }

    public void GetAppModuleResponse(final OnAppModuleResult callback) {
        HttpClient.getHttpService().getAppModules(getAuthToken()).enqueue(new Callback<AppModulesResponse>() {
            @Override
            public void onResponse(Call<AppModulesResponse> call, Response<AppModulesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<AppModulesResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


    public void GetDesignationResponse(final OnDesignationResult callback) {
        HttpClient.getHttpService().getDesignation(getAuthToken()).enqueue(new Callback<DesignationModel>() {
            @Override
            public void onResponse(Call<DesignationModel> call, Response<DesignationModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<DesignationModel> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }

    public void GetRefusalReason(final OnRefualReasonResult callback) {
        HttpClient.getHttpService().getRefusalReason(getAuthToken()).enqueue(new Callback<RefusalReasonModel>() {
            @Override
            public void onResponse(Call<RefusalReasonModel> call, Response<RefusalReasonModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<RefusalReasonModel> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


    public void GetStoreTypes(final OnStoreType callback) {
        HttpClient.getHttpService().getStoreTypes(getAuthToken()).enqueue(new Callback<StoreTypesResponse>() {
            @Override
            public void onResponse(Call<StoreTypesResponse> call, Response<StoreTypesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<StoreTypesResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }

    public void GetProvinces(final OnProvince callback) {
        HttpClient.getHttpService().getProvinces(getAuthToken()).enqueue(new Callback<GetProvinces>() {
            @Override
            public void onResponse(Call<GetProvinces> call, Response<GetProvinces> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<GetProvinces> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }

    public void GetAppVersions(final OnAppVersions callback) {
        HttpClient.getHttpService().getAppVersions(getAuthToken()).enqueue(new Callback<GetAppVersions>() {
            @Override
            public void onResponse(Call<GetAppVersions> call, Response<GetAppVersions> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<GetAppVersions> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


    public void GetIndicators(String id, final OnIndicatorsResult callback) {
        HttpClient.getHttpService().getIndicators(getAuthToken(), id).enqueue(new Callback<IndicatorsResponse>() {
            @Override
            public void onResponse(Call<IndicatorsResponse> call, Response<IndicatorsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<IndicatorsResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


    public void GetVaccinationChild(final OnGetChildResult callback,int pagenumber, int size,String CampaignMonth,String CampaignType) {
        HttpClient.getHttpService().getVaccinationChild(getAuthToken(),pagenumber,size,CampaignMonth,CampaignType).enqueue(new Callback<ChildModel>() {
            @Override
            public void onResponse(Call<ChildModel> call, Response<ChildModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else if (response.code() < 500) callback.onFailed();

                else callback.onRequestFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<ChildModel> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }
        });
    }


//    public void LogIn(final String username, final String password, final OnLoginResult callback)
//
//    {
//        HttpClient.getHttpService().Login(username, password, "password").enqueue(new Callback<LoginResponse>()
//
//        {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
//
//            {
//                if(response.isSuccessful() )
//
//                {
//                    callback.onLoggedIn(response.body());
//                }
//
//                else if(response.code() < 500) callback.onLoginFailed();
//
//                else callback.onRequestFailed(response.code(), response.message());
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t)
//
//            {
//                callback.onRequestFailed(-1, t.getMessage());
//            }
//        });
//    }

    public void GetAppVersion(final OnGenericResult callback) {
        HttpClient.getHttpService().getAppVersion().enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }
        });
    }

    public void GetChecklistData(final OnChecklistResult callback) {
        HttpClient.getHttpService().GetChecklist().enqueue(new Callback<List<CheckList>>() {
            @Override
            public void onResponse(Call<List<CheckList>> call, Response<List<CheckList>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CheckList>> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetSIDLookup(final OnGenericResponseResult callback) {
        HttpClient.getHttpService().GetSIDLookup(getAuthToken()).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }


    //
    public void GetCampaignSchedule(final OnScheduleResponseResult callback) {
        HttpClient.getHttpService().GetCampaignSchedules().enqueue(new Callback<SchedulesResponse>() {
            @Override
            public void onResponse(Call<SchedulesResponse> call, Response<SchedulesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<SchedulesResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }


    public void GetClusterType(final OnClusterTypes callback) {
        HttpClient.getHttpService().GetClusters(getAuthToken()).enqueue(new Callback<GetClustersType>() {
            @Override
            public void onResponse(Call<GetClustersType> call, Response<GetClustersType> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GetClustersType> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetVaccinationCheckLists(final OnChecklistVaccinationResult callback) {
        HttpClient.getHttpService().VaccinationCheckLists().enqueue(new Callback<List<CheckListVaccination>>() {
            @Override
            public void onResponse(Call<List<CheckListVaccination>> call, Response<List<CheckListVaccination>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CheckListVaccination>> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetLocationData(String code, final OnLocationResult callback) {
        HttpClient.getHttpService().getLocations().enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetEpiCenters(final OnEpiCenter callback) {
        HttpClient.getHttpService().getEpiCenters(getAuthToken()).enqueue(new Callback<GetEpiCenters>() {
            @Override
            public void onResponse(Call<GetEpiCenters> call, Response<GetEpiCenters> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GetEpiCenters> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetUCData(String code, final OnUcResult callback) {
        HttpClient.getHttpService().getUC(code).enqueue(new Callback<List<UcGetModel>>() {
            @Override
            public void onResponse(Call<List<UcGetModel>> call, Response<List<UcGetModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UcGetModel>> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetCenterData(final OnUcSaveResult callback) {
        HttpClient.getHttpService().getCenter().enqueue(new Callback<List<UcData>>() {
            @Override
            public void onResponse(Call<List<UcData>> call, Response<List<UcData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UcData>> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void GetAppStatus(final OnAppStatus callback) {
        HttpClient.getHttpService().getAppStatus().enqueue(new Callback<HisduAppStatusResponse>() {
            @Override
            public void onResponse(Call<HisduAppStatusResponse> call, Response<HisduAppStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else {
                    callback.onRequestFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<HisduAppStatusResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveOfflineData(SaveInspections bul, final OnGenericResult callback) {
        HttpClient.getHttpService().SaveUnsyncRecord(getAuthToken(), bul).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveUnsyncClusterRecord(ClusterModel clusterModel, final OnGenericResult callback) {
        HttpClient.getHttpService().SaveUnsyncClusterRecord(getAuthToken(),  clusterModel).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response)
            {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveOfflineInspectionData(SaveInspectionVaccination bul, final OnGenericResult callback) {
        HttpClient.getHttpService().SaveUnsyncVaccinationRecord(getAuthToken(), bul).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveGeoTagData(UcData bul, final OnGenericResult callback) {
        HttpClient.getHttpService().SaveGeoTagRecord(getAuthToken(), bul).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveZeroDose(ZeroDoseMaster bul, final OnGenericResult callback) {
        HttpClient.getHttpService().SaveZeroDose(getAuthToken(), bul).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }

    public void SaveChildVaccination(
                                     String BackImageUrl,
                                     String FrontImageUrl,
                                     String MissedProfileId,
                                     String RegistrationChildId,
                                     String UpdatedBy,
                                     String UpdatedOn,
                                     String VaccinationId,
                                     String CreatedBy,
                                     String CreatedOn,
                                     Boolean IsVaccinated,
                                     String Remarks,
                                     String TeamContactNo,
                                     String EntryPersonName,
                                     String EntryPersonDesignation,
                                     String CardNo,
                                     String latitude,
                                     String longitude,
                                     final OnChildSaveResult callback
                                     ) {
        HttpClient.getHttpService().saveChildVaccination(getAuthToken(), BackImageUrl,FrontImageUrl,MissedProfileId,
                RegistrationChildId,
                UpdatedBy,
                UpdatedOn,
                VaccinationId,
                CreatedBy,
                CreatedOn,
                IsVaccinated,
                Remarks,
                TeamContactNo,
                EntryPersonName,
                EntryPersonDesignation,
                CardNo,
                latitude,
                longitude
                ).enqueue(new Callback<feedbackResponse>() {
            @Override
            public void onResponse(Call<feedbackResponse> call, Response<feedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<feedbackResponse> call, Throwable t) {
                callback.onFailed(-1, t.getMessage());
            }

        });
    }


    public void SaveIndicators(IndicatorMasterDataSave bul, final OnIndicatorsSave callback) {
        HttpClient.getHttpService().SaveIndicators(getAuthToken(), bul).enqueue(new Callback<IndicatorSaveResponse>() {
            @Override
            public void onResponse(Call<IndicatorSaveResponse> call, Response<IndicatorSaveResponse> response) {
                Log.d("test", bul.toString());
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else {
                    callback.onRequestFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<IndicatorSaveResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }

        });
    }

    public void SyncZeroDose(ZeroDoseMain bul, final OnIndicatorsSave callback) {
        HttpClient.getHttpService().SyncZeroDose(getAuthToken(), bul).enqueue(new Callback<IndicatorSaveResponse>() {
            @Override
            public void onResponse(Call<IndicatorSaveResponse> call, Response<IndicatorSaveResponse> response) {
                Log.d("test", bul.toString());
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else {
                    callback.onRequestFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<IndicatorSaveResponse> call, Throwable t) {
                callback.onRequestFailed(-1, t.getMessage());
            }

        });
    }

    public interface OnLoginResult {
        void onLoggedIn(LoginResponseLatest loginresponse);

        void onLoginFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnFeedBackResult {
        void onSuccess(feedbackResponse feedbackRes);


        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnAppInfoResult {
        void onSuccess(List<appInfopagesize.Datum> appInfoRes);


        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnFolderResult {
        void onSuccess(List<folderResponse.FolderModel> appInfoRes);


        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnappinfoLink {
        void onSuccess(appInfoLinkResponse.Data appInfoLink);


        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnGenericResult {
        void onSuccess(GenericResponse response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnChildSaveResult {
        void onSuccess(feedbackResponse response);

        void onFailed(int errorCode, String errorMessage);
    }
    public interface OnChecklistResult {
        void onSuccess(List<CheckList> response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnClusterTypes {
        void onSuccess(GetClustersType response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnGenericResponseResult {
        void onSuccess(GenericResponse response);

        void onFailed(int errorCode, String errorMessage);
    }


    public interface OnScheduleResponseResult {
        void onSuccess(SchedulesResponse response);

        void onFailed(int errorCode, String errorMessage);
    }


    public interface OnChecklistVaccinationResult {
        void onSuccess(List<CheckListVaccination> response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnLocationResult {
        void onSuccess(LocationResponse response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnEpiCenter {
        void onSuccess(GetEpiCenters response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnUcResult {
        void onSuccess(List<UcGetModel> response);

        void onFailed(int errorCode, String errorMessage);
    }

    public interface OnUcSaveResult {
        void onSuccess(List<UcData> response);

        void onFailed(int errorCode, String errorMessage);
    }

    String getAuthToken() {
        return new SharedPref(MyApplication.getAppContext()).GetToken();
    }

    public interface OnAppModuleResult {
        void onResult(AppModulesResponse appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnDesignationResult {
        void onResult(DesignationModel designationModel);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnRefualReasonResult {
        void onResult(RefusalReasonModel designationModel);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnStoreType {
        void onResult(StoreTypesResponse storeTypesResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnProvince {
        void onResult(GetProvinces getProvinces);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnAppVersions {
        void onResult(GetAppVersions appVersions);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnContactUs {
        void onResult(contactUsResponse contact);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnIndicatorsResult {
        void onResult(IndicatorsResponse appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnGetChildResult {
        void onResult(ChildModel appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnIndicatorsSave {
        void onResult(IndicatorSaveResponse appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnAppStatus {
        void onResult(HisduAppStatusResponse appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }
}
