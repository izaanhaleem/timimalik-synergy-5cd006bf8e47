package com.hisdu.ses.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.hisdu.ses.Models.login.LoginResponse;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.Models.provinces.GetProvinces;
import com.hisdu.ses.Models.storeTypes.StoreTypesResponse;
import com.hisdu.ses.SchedulesResponse;
import com.hisdu.ses.ZeroDoseVaccination.ChildModel;
import com.hisdu.ses.ZeroDoseVaccination.SaveChildModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class HttpClient {

    //public static String BASE_URL="http://172.16.25.62:31113/api/";
    //public static String BASE_URL="http://125.209.111.70:1012/api/";
    public static String BASE_URL="https://sesapi.pshealthpunjab.gov.pk/api/";
//    public static String BASE_URL="http://172.16.21.132:45455/api/";

//    public static String BASE_URL="http://116.58.20.67:1012/";

    public interface HttpService {
        @GET("https://hisduapps.pshealthpunjab.gov.pk/api/AppStatus/GetAppSettings?Name=Synergy%20Evaluation%20System")
        Call<HisduAppStatusResponse> getAppStatus();

        @GET("Location/GetAppVersion")
        Call<GenericResponse> getAppVersion();

        @FormUrlEncoded
        @POST("Token")
        Call<LoginResponse> Login(@Field("Username") String username, @Field("Password") String password, @Field("grant_type") String grantType);

        @GET("ContactFeedback/GetContactUs")
        Call<contactUsResponse> contactresoponse(@Header("Authorization") String autHeader);

        @POST("ContactFeedback/SaveFeedback")
        Call<feedbackResponse> feedback(@Body feedBackTable feedbackbody);

        @POST("ResourceMaterial/GetAllResourceMaterial")
        Call<appInfopagesize> getallResourse(@Body appInfoResourseRequest appinfoRequest);


        @GET("profile/getfolders")
        Call<folderResponse> getFolders();



        @GET("ResourceMaterial/GetResourceMaterialById")
        Call<appInfoLinkResponse> GetAppInfoLink(@Query("masterId") int sdf);

        @POST("UserAuthentication/UserLogin")
        Call<LoginResponseLatest> loginRequest(@Body LoginRequest body);

        @GET("Location/GetAllLocations")
        Call<LocationResponse> getLocations();

        @GET("Profile/GetSIDLookupAsync")
        Call<GenericResponse> GetSIDLookup(@Header("Authorization") String autHeader);


        @GET("Profile/GetCampaignSchedules")
        Call<SchedulesResponse> GetCampaignSchedules();


        @GET("ClusterMaster/GetClusterTypes")
        Call<GetClustersType> GetClusters(@Header("Authorization") String autHeader);

        @GET("Common/UCs/{code}")
        Call<List<UcGetModel>> getUC(@Query("code") String code);

        @GET("Common/EPICentersAll")
        Call<List<UcData>> getCenter();

        @GET("Common/CheckLists")
        Call<List<CheckList>> GetChecklist();

        @GET("Common/VaccinationCheckLists")
        Call<List<CheckListVaccination>> VaccinationCheckLists();

        @POST("Inspection/Save")
        Call<GenericResponse> SaveUnsyncRecord(@Header("Authorization") String autHeader, @Body SaveInspections record);

        @POST("ClusterMaster/SaveCluster")
        Call<GenericResponse> SaveUnsyncClusterRecord(@Header("Authorization") String autHeader, @Body ClusterModel clusterModel);

        @POST("Inspection/VaccinationSave")
        Call<GenericResponse> SaveUnsyncVaccinationRecord(@Header("Authorization") String autHeader, @Body SaveInspectionVaccination record);

        @POST("Common/EpiCenterLatLong")
        Call<GenericResponse> SaveGeoTagRecord(@Header("Authorization") String autHeader, @Body UcData record);

        @POST("ZeroDose/SaveZeroDose")
        Call<GenericResponse> SaveZeroDose(@Header("Authorization") String autHeader, @Body ZeroDoseMaster record);

        @POST("Indicator/Save")
        Call<IndicatorSaveResponse> SaveIndicators(@Header("Authorization") String autHeader, @Body IndicatorMasterDataSave record);

        @POST("Registration/Save")
        Call<IndicatorSaveResponse> SyncZeroDose(@Header("Authorization") String autHeader, @Body ZeroDoseMain record);


        @GET("Profile/GetAppModules")
        Call<AppModulesResponse> getAppModules(@Header("Authorization") String autHeader);


        @GET("Profile/GetRefusalReason")
        Call<RefusalReasonModel> getRefusalReason(@Header("Authorization") String autHeader);

        @GET("Profile/GetSIADesignations")
        Call<DesignationModel> getDesignation(@Header("Authorization") String autHeader);

        @GET("Profile/GetStoreTypes")
        Call<StoreTypesResponse> getStoreTypes(@Header("Authorization") String autHeader);

        @GET("Profile/GetAppVersion")
        Call<GetAppVersions> getAppVersions(@Header("Authorization") String autHeader);

        @GET("Profile/GetProvince")
        Call<GetProvinces> getProvinces(@Header("Authorization") String autHeader);

        @GET("Profile/GetEPICenters")
        Call<GetEpiCenters> getEpiCenters(@Header("Authorization") String autHeader);

        @GET("Indicator/GetIndicatorsAsync")
        Call<IndicatorsResponse> getIndicators(@Header("Authorization") String autHeader, @Query("AppModuleId") String appModuleId);

        @FormUrlEncoded
        @POST("Registration/GetChildDetail")
        Call<ChildModel> getVaccinationChild(@Header("Authorization") String autHeader,@Field("PageNumber") int PageNumber,@Field("size") int size,@Field("CampaignMonth") String CampaignMonth,@Field("CampaignType") String CampaignType);

        @FormUrlEncoded
        @POST("Registration/SaveChildVaccination")
        Call<feedbackResponse> saveChildVaccination(@Header("Authorization") String autHeader,
                                                    @Field("BackImageUrl") String BackImageUrl,
                                                    @Field("FrontImageUrl") String FrontImageUrl,
                                                    @Field("MissedProfileId") String MissedProfileId,
                                                    @Field("RegistrationChildId") String RegistrationChildId,
                                                    @Field("UpdatedBy") String UpdatedBy,
                                                    @Field("UpdatedOn") String UpdatedOn,
                                                    @Field("VaccinationId") String VaccinationId,
                                                    @Field("UpdatedBy") String CreatedBy,
                                                    @Field("UpdatedOn") String CreatedOn,
                                                    @Field("IsVaccinated") Boolean IsVaccinated,
                                                    @Field("Remarks") String Remarks,
                                                    @Field("TeamContactNo") String TeamContactNo,
                                                    @Field("EntryPersonName") String EntryPersonName,
                                                    @Field("EntryPersonDesignation") String EntryPersonDesignation,
                                                    @Field("CardNo") String CardNo,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude
                                                    );
    }

    public static HttpService getHttpService() {
        if (client == null) {
            OkHttpClient ok = new OkHttpClient.Builder()
                    .connectTimeout(7, TimeUnit.MINUTES)
                    .readTimeout(7, TimeUnit.MINUTES)
                    .build();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .setLenient()
                    .create();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.i("OKhttp Response",message));
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder() .connectTimeout(7, TimeUnit.MINUTES)
                    .readTimeout(7, TimeUnit.MINUTES);

            httpClient.addInterceptor(logging);

            httpClient.addInterceptor(logging);
            client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();

            return client.create(HttpService.class);
        } else {
            return client.create(HttpService.class);
        }
    }

    private static Retrofit client;
}
