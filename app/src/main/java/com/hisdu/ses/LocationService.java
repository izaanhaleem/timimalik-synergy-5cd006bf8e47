package com.hisdu.ses;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class LocationService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 10000;
    public static String str_receiver = "epi.service.receiver";
    Intent intent;

    public LocationService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        intent = new Intent(str_receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation()

    {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable     = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable)

        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.main);
            alertDialogBuilder.setTitle("Location disabled");
            alertDialogBuilder
                    .setMessage("Please enable location in order to use this app!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        else

        {

            if(AppController.getInstance().hasinternetAccess)

            {
                if (isNetworkEnable)

                {

                    //Toast.makeText(getApplicationContext(),"getting network location",Toast.LENGTH_LONG).show();

                    location = null;
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the User grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

//                    try {
//                        locationManager.removeTestProvider(LocationManager.NETWORK_PROVIDER);
//                    } catch (IllegalArgumentException error) {
//                        Log.d("TAG","Got exception in removing test  provider");
//                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);

                    if (locationManager != null)

                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {

                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            LocationManager locMan = (LocationManager) MainActivity.main.getSystemService(MainActivity.main.LOCATION_SERVICE);
                            long networkTS = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getTime();

                            fn_update(location,networkTS);
                        }
                    }

                }

                else

                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.main);
                        alertDialogBuilder.setTitle("Location disabled");
                        alertDialogBuilder
                                .setMessage("Please enable Location!")
                                .setCancelable(false)
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

            }

            else

                {

                    if (isGPSEnable)

                    {
                        //Toast.makeText(getApplicationContext(),"getting gps",Toast.LENGTH_LONG).show();

                        location = null;

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the User grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

//                        try {
//                            locationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
//                        } catch (IllegalArgumentException error) {
//                            Log.d("TAG","Got exception in removing test  provider");
//                        }

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

                        if (locationManager !=null )

                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location!=null){
                                Log.e("latitude",location.getLatitude()+"");
                                Log.e("longitude",location.getLongitude()+"");
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                fn_update(location,location.getTime());
                            }
                        }

                        else

                        {
                            Toast.makeText(getApplicationContext(),"Failed to get gps location", Toast.LENGTH_LONG).show();
                        }
                    }

                    else

                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.main);
                        alertDialogBuilder.setTitle("GPS disabled");
                        alertDialogBuilder
                                .setMessage("Please enable GPS!")
                                .setCancelable(false)
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }
        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run()
        {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });
        }
    }

    private void fn_update(Location location, Long d)

    {

        if(location.isFromMockProvider())

        {
            Toast.makeText(MainActivity.main,"Warning, Please avoid tempering the location",Toast.LENGTH_SHORT).show();
        }
        else {

            AppController.location = location;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date dat = new Date(d);
            String date = df.format(dat);
            AppController.date = date;

            Log.d("LocationServiceLat",location.getLatitude()+"");
            Log.d("LocationServiceLong",location.getLongitude()+"");

            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dat1 = new Date(d);
            String date1 = df1.format(dat1);
            AppController.dateOnly = date1;
            //Toast.makeText(MainActivity.main,location.getLatitude()+""+location.getLongitude()+"",Toast.LENGTH_SHORT).show();
            intent.putExtra("latutide",location.getLatitude()+"");
            intent.putExtra("longitude",location.getLongitude()+"");
            sendBroadcast(intent);
        }
    }


}
