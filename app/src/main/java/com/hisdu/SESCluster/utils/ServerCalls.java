package com.hisdu.SESCluster.utils;
import com.hisdu.ses.Models.appmodule.AppModulesResponse;
import com.hisdu.ses.Models.login.LoginRequest;
import com.hisdu.ses.Models.login.LoginResponse;
import com.hisdu.ses.utils.HttpClient;

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



    public void GetAppModuleResponse(String header, final OnAppModuleResult callback) {
        HttpClient.getHttpService().getAppModules(header).enqueue(new Callback<AppModulesResponse>() {
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




    public interface OnLoginResult {
        void onLoggedIn(LoginResponse loginresponse);

        void onLoginFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }

    public interface OnAppModuleResult {
        void onResult(AppModulesResponse appModuleResponse);

        void onFailed();

        void onRequestFailed(int errorCode, String errorMessage);
    }
}
