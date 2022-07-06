package com.hisdu.ses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.github.nikartm.support.StripedProcessButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.Models.ZeroDose.Designation;
import com.hisdu.ses.Models.ZeroDose.DesignationModel;
import com.hisdu.ses.Models.appVersion.AppVersion;
import com.hisdu.ses.Models.appmodule.AppModulesResponse;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.Models.login.LoginRequest;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.utils.MyPreferences;
import com.hisdu.ses.utils.ServerCalls;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText userName, userPassword;
    StripedProcessButton btnLogin;
    MyPreferences myPreferences;
    AppVersion appverion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();

    }

    void bindViews() {

        myPreferences = MyPreferences.getPreferences(this);
        userName = findViewById(R.id.input_email);
        userPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.start();
                if (TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(userPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Fill empty fields", Toast.LENGTH_SHORT).show();
                    btnLogin.stop();
                } else {
                    MyApplication.getHeader(userName.getText().toString(), userPassword.getText().toString());
                    ServerCalls.getInstance().LogIn(new LoginRequest(userName.getText().toString(), userPassword.getText().toString()), new ServerCalls.OnLoginResult() {
                        @Override
                        public void onLoggedIn(LoginResponseLatest loginresponse) {
                            String response =new Gson().toJson(loginresponse);
                            Utils.saveUserProfileInfo(response, getApplicationContext());
                            String locationCode="";
                            if (loginresponse.getData().getLocationCode()!=null){
                                locationCode=loginresponse.getData().getLocationCode();
                            }
                            new SharedPref(getApplicationContext()).SaveCredentials(loginresponse.getData().getToken(), loginresponse.getData().getUsername(), userPassword.getText().toString(), loginresponse.getData().getUserId().toString(), loginresponse.getData().getUserRole(), locationCode, loginresponse.getData().getName(), "",loginresponse.getData().getLocationName(),loginresponse.getData().getStoreTypeId(),loginresponse.getData().getProvinceId(),loginresponse.getData().getUserType(),loginresponse.getData().getLocationName());
                            getDesignation();
                            btnLogin.stop();
                        }

                        @Override
                        public void onLoginFailed() {

                        }

                        @Override
                        public void onRequestFailed(int errorCode, String errorMessage) {
                            btnLogin.stop();
                        }
                    });
                }
            }
        });
    }

    void getDesignation() {

//        status.setText("Preparing Modules....");

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetDesignationResponse(new ServerCalls.OnDesignationResult() {

                @Override
                public void onResult(DesignationModel designationModel) {
                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(Designation.class);
                    for (int index = 0; index < designationModel.getList().size(); index++) {

                        Designation content = new Designation();

                        if (designationModel.getList().get(index).getDesignationId() != null) {
                            content.setDesignationId(designationModel.getList().get(index).getDesignationId());
                        }

                        if (designationModel.getList().get(index).getDesignationName() != null) {
                            content.setDesignationName(designationModel.getList().get(index).getDesignationName());
                        }

                        content.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
//                    new SharedPref(getApplicationContext()).SaveModulesList(appverion.getAppModules());

                    getModules();
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



    void getModules() {

//        status.setText("Preparing Modules....");

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetAppModuleResponse(new ServerCalls.OnAppModuleResult() {
                @Override
                public void onResult(AppModulesResponse appModuleResponse) {

                    ActiveAndroid.beginTransaction();
                    AppController.clearTable(Content.class);
                    for (int index = 0; index < appModuleResponse.getData().size(); index++) {

                        Content content = new Content();

                        if (appModuleResponse.getData().get(index).getIsActive() != null) {
                            content.setIsActive(appModuleResponse.getData().get(index).getIsActive());
                        }

                        if (appModuleResponse.getData().get(index).getAppModuleId() != null) {
                            content.setAppModuleId(appModuleResponse.getData().get(index).getAppModuleId());
                        }

                        if (appModuleResponse.getData().get(index).getAppModuleName() != null) {
                            content.setAppModuleName(appModuleResponse.getData().get(index).getAppModuleName());
                        }

                        content.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
//                    new SharedPref(getApplicationContext()).SaveModulesList(appverion.getAppModules());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
}