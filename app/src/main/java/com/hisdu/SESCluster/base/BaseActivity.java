package com.hisdu.SESCluster.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.communication.DoInBackground;
import com.hisdu.SESCluster.communication.version.DoInBackgroundForAppVersion;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.database_1.DataSourceOperations;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.MyPermission;
import com.hisdu.SESCluster.utils.SystemBarTintManager;
import com.hisdu.SESCluster.utils.TransparentProgressDialog;
import com.hisdu.SESCluster.utils.UIHelper;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, DoInBackground.OnPostExecutionListener, DoInBackgroundForAppVersion.OnPostExecutionListener {
    public String STORAGE_PATH = "";
    public static TelephonyManager mTelephonyManager;
    public static String DEVICE_IMEI = "";
    public static Location myLocation = null;
    protected static Boolean isLocationSet = false;
    public static String cnicVal = "";
    protected LocalDatabaseManager mDbManager;
    TransparentProgressDialog mProgress;

    Calendar calendar;
    protected DataSourceOperations mDbOperationOnDS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        checkMyPermissions();

    }

    /**
     * @return The layout xml for this activity
     */
    protected abstract int getLayoutResourceId();

    /**
     * Initialize activity level controls here.
     */
    protected abstract void initializeControls();

    /**
     * Attach listeners to your controls in this method. For example textview.setOnClickListener(this).
     */
    protected abstract void attachListeners();

    protected abstract void initializeData();

    @Override
    public void onClick(View view) {

    }


    public void replaceFragment(Fragment fragment, boolean animated, boolean updownSlide, boolean addToBackStack, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animated) {
            if (updownSlide)
                fragmentTransaction.setCustomAnimations(R.anim.slidedown_in, R.anim.slideup_out, R.anim.slideup_in, R.anim.slidedown_out);
            else
                fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out, R.anim.right_in, R.anim.left_out);
        }
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
//        code commented will use later to add add fragment in activity
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public int getScreenWidth() {
        return findViewById(android.R.id.content).getWidth();
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColorLOLLIPOP();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.transparent));
        }

    }

    @TargetApi(21)
    private void setStatusBarColorLOLLIPOP() {
        Window window = getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.btn_normal));
//        window.setNavigationBarColor(Color.TRANSPARENT);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void callPostMethod(String url, int requestCode, JSONObject jsonObject) {
        DoInBackground doInBackground = new DoInBackground(this, requestCode, url, DoInBackground.MethodType.POST, jsonObject, "", false, this);
        doInBackground.execute();
    }

    public void callPostMethod(String url, int requestCode, JSONObject jsonObject, boolean showProgress) {
        DoInBackground doInBackground = new DoInBackground(this, requestCode, url, DoInBackground.MethodType.POST,
                jsonObject, "", false, this, showProgress);
        doInBackground.execute();
    }

    public void callGetMethod(String url, int requestCode) {
        DoInBackground doInBackground = new DoInBackground(this, requestCode, url, DoInBackground.MethodType.GET, new JSONObject(),
                "", false, this, true);
        doInBackground.execute();
    }

    public void callGetMethod(int requestCode) {
        DoInBackgroundForAppVersion doInBackground = new DoInBackgroundForAppVersion(this, requestCode,
                 false, this);
        doInBackground.execute();
    }

    @Override
    public void onPostExecution(String response, int requestCode) {

    }

    public void getCellulerNetworkLoc() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManagerNetwork = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location l) {
                // Called when a new location is found by the network location provider.
                if (l != null) {
                    Log.v(Globals.TAG, "Location Set By Celluler Network with Lat = " + l.getLatitude() + " Longitude = " + l.getLongitude());
                    isLocationSet = !isLocationSet;
                    myLocation = l;
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManagerNetwork.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
    }

    public void getGPSLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManagerNetwork = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                if (location != null && (!isLocationSet)) {
                    Log.v(Globals.TAG, "Location Set in GPS Listner with Lat = " + location.getLatitude() + " Longitude = " + location.getLongitude());
                    isLocationSet = !isLocationSet;
                    myLocation = location;
                    //subTitle2.setText("Lat = "+myLocation.getLatitude()+" Longitude = "+myLocation.getLongitude());
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManagerNetwork.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerNetwork);
    }

    protected String getDateFromLocation() {
        String locationDate = "";
        if (myLocation != null) {
            long time = myLocation.getTime();
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat(Globals.WEB_DATE_FORMAT, Locale.ENGLISH);
            locationDate = sdf.format(date);
        }
        return locationDate;
    }

    protected String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Globals.WEB_DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(new Date());
    }

    public void toggleHideyBar() {
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(Globals.TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(Globals.TAG, "Turning immersive mode mode on.");
        }

        // Immersive mode: Backward compatible to KitKat (API 19).
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // This sample uses the "sticky" form of immersive mode, which will let the user swipe
        // the bars back in again, but will automatically make them disappear a few seconds later.
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void takePictures(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Intent cameraIntent = UIHelper.getInstance().getIntentToStoreFile(Globals.TEMP_IMG_PATH, this);
        startActivityForResult(cameraIntent, tag);
    }

    protected void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
    }

    protected boolean isCNICEmpty(EditText editText) {
        String strCNIC = editText.getText().toString();
        String[] CNICArray = strCNIC.split("-");

        if (CNICArray[0].contains(" ") || CNICArray[1].contains(" ") || CNICArray[2].contains(" ")) {
            return true;
        } else
            return false;
    }

    protected void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    protected void showMessage(String message, boolean shouldCloseOnOk) {
        Dialogs.showDialog(message, getString(R.string.app_name),
                this, true, false, getString(R.string.ok), "", shouldCloseOnOk);
    }

    protected void showDialog() {

        mProgress = new TransparentProgressDialog(this);
        mProgress.setCancelable(false);
        mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//             cancel(false);
            }
        });
        mProgress.show();
    }

    protected void hideDialog() {
        if (mProgress != null && mProgress.isShowing()) mProgress.dismiss();
    }

    @TargetApi(23)
    private void checkMyPermissions() {
        if (MyPermission.getInstance().isMarshMallow()) {
            if (!MyPermission.getInstance().canReadIMEI(this)) {
                requestPermissions(MyPermission.READ_PHONE_STATE_PERMS, MyPermission.READ_PHONE_STATE_REQUEST);
            } else if (!MyPermission.getInstance().canAccessCamera(this)) {
                requestPermissions(MyPermission.CAMERA_PERMS, MyPermission.CAMERA_REQUEST);
            } else if (!MyPermission.getInstance().canWriteExternalStorage(this)) {
                requestPermissions(MyPermission.WRITE_EXTERNAL_STORAGE_PERMS, MyPermission.WRITE_EXTERNAL_STORAGE_REQUEST);
            } else if (!MyPermission.getInstance().canAccessLocation(this)) {
                requestPermissions(MyPermission.LOCATION_PERMS, MyPermission.LOCATION_REQUEST);
            } else {
                createDir();
                mDbOperationOnDS = new DataSourceOperations(this);
                mDbManager = LocalDatabaseManager.getInstance(this);
                setContentView(getLayoutResourceId());
                calendar = Calendar.getInstance();
                initializeControls();
                attachListeners();
                initializeData();
            }

        } else {
            createDir();
            mDbOperationOnDS = new DataSourceOperations(this);
            mDbManager = LocalDatabaseManager.getInstance(this);
            setContentView(getLayoutResourceId());
            calendar = Calendar.getInstance();
            initializeControls();
            attachListeners();
            initializeData();
        }
    }


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MyPermission.CAMERA_REQUEST:
                // doCameraThing();
                checkMyPermissions();
                break;
            case MyPermission.READ_PHONE_STATE_REQUEST:
                checkMyPermissions();
                break;
            case MyPermission.LOCATION_REQUEST:
                checkMyPermissions();
                break;
            case MyPermission.WRITE_EXTERNAL_STORAGE_REQUEST:
                checkMyPermissions();
                break;
        }
    }

    private void createDir() {
        STORAGE_PATH = Environment.getExternalStorageDirectory() + getPackageName();
        Globals.TEMP_IMG_PATH = UIHelper.getInstance().getAppStorageLocation(Globals.APP_STORAGE_PATH, Globals.TEMP_IMAGE_FILE);
        File dbDir = new File(STORAGE_PATH);
        if (!dbDir.exists()) {
            boolean directoryCreated = dbDir.mkdirs();
//            Log.v(Constants.TAG, "Directory Created ="+directoryCreated);
        }
        // Open the empty db as the output stream
        File dbFile = new File(Globals.TEMP_IMG_PATH.toString());
        if (!dbFile.exists()) {
            boolean fileCreated;
            try {
                fileCreated = dbFile.createNewFile();
                Log.v(Globals.TAG, "File created =" + fileCreated);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
