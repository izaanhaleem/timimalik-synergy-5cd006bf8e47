package com.hisdu.SESCluster.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Network Class is made to Unified the Common Methods of Daily programming Practice.<br>
 * <b><i>Note:    add the permission in manifest file </i>"<"uses-permission android:name="android.permission.INTERNET"/">"</b>
 * @author  Designed and Developed by Matrix pvt Ltd
 * @author  Abid Rafiq
 * 
 *
 */
public class MyPermission {
	private static MyPermission instance;
	
	
	 
 	/** The params. */
	// for Permission 
	public static final String[] LOCATION_READ_CONTACTS_PERMS={
	    Manifest.permission.ACCESS_FINE_LOCATION,
	    Manifest.permission.READ_CONTACTS
	  };
	public static final String[] CAMERA_PERMS={
	    Manifest.permission.CAMERA
	  };
	public static final String[] CONTACTS_PERMS={
	      Manifest.permission.READ_CONTACTS
	  };
	public static final String[] LOCATION_PERMS={
	      Manifest.permission.ACCESS_FINE_LOCATION
	  };
	public static final String[] READ_PHONE_STATE_PERMS={
	      Manifest.permission.READ_PHONE_STATE
	  };
	public static final String[] WRITE_EXTERNAL_STORAGE_PERMS={
		    Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	
	
	//11-30 13:18:32.871: E/AndroidRuntime(11210): Caused by: java.lang.SecurityException: "gps" location provider requires ACCESS_FINE_LOCATION permission.

	  
	public static final int INITIAL_REQUEST=1;
	public static final int CAMERA_REQUEST=2;
	public static final int CONTACTS_REQUEST=3;
	public static final int LOCATION_REQUEST=4;
	public static final int WRITE_EXTERNAL_STORAGE_REQUEST=5;
	public static final int READ_PHONE_STATE_REQUEST=6;
	
	
 	
 	private MyPermission() {
	}
	/** Make the singleton class */
	public static MyPermission getInstance() {
		if (instance == null) {
			instance = new MyPermission();
		}
		return instance;
	}
 	
 	
/**
 * check the Permission.
 *
 * @param context  (Context of the Application)
 * @return Boolean ()
 */ 	
	public boolean canAccessLocation(Context context) {
		return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION,context));
	}
	    
	public boolean canWriteExternalStorage(Context context){
		return(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,context));
	}
	public boolean canAccessCamera(Context context){
        return(hasPermission(Manifest.permission.CAMERA,context));
    }

	public boolean canAccessContacts(Context context){
        return(hasPermission(Manifest.permission.READ_CONTACTS,context));
    }
	
	public boolean canReadIMEI(Context context){
        return(hasPermission(Manifest.permission.READ_PHONE_STATE,context));
    }

      @TargetApi(Build.VERSION_CODES.M)
	private boolean hasPermission(String perm, Context context){
    	  return(PackageManager.PERMISSION_GRANTED==context.checkSelfPermission(perm));
    }
    
    public boolean isMarshMallow(){
    	return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }
}