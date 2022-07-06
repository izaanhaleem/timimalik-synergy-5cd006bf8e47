package com.hisdu.ses.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {

    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public static final String SHARED_PREFERENCES_NAME = "sesPrefs";
    public static final String USER_NAME = "userName";
    public static final String USER_PSWD = "userName";
    public static final String USER_TOKEN = "userToken";
    public static final String USER_ID = "userID";
    public static final String USER_ROLE = "userRole";

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getPreferences(Context context) {
        if (myPreferences == null) myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void setUserName(String userName){
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public void setToken(String token){
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String getUserName(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(USER_NAME, "Name not found");
    }



public void setUserPswd(String userName){
        editor.putString(USER_PSWD, userName);
        editor.apply();
    }

    public String getUserPswd(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(USER_PSWD, "Pswd not found");
    }


    public void setUserID(Integer userID){
        editor.putInt(USER_ID, userID);
        editor.apply();
    }

    public Integer getUserID(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getInt(USER_ID, 0);
    }

    public void setUserRole(String userRole){
        editor.putString(USER_ROLE, userRole);
        editor.apply();
    }

    public String getUserRole(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(USER_ROLE, "User Role not found");
    }



 public String getUserToken(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(USER_TOKEN, "Token not found");
    }


}