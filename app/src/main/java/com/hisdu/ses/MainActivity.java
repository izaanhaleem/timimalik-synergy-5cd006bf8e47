package com.hisdu.ses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.hisdu.ses.Database.SaveInspectionVaccination;
import com.hisdu.ses.fragment.DashboardFragment;
import com.hisdu.ses.fragment.FolderFragment;
import com.hisdu.ses.fragment.GeoTagFragment;
import com.hisdu.ses.fragment.LinkFragment;
import com.hisdu.ses.fragment.appInfoFragment;
import com.hisdu.ses.fragment.contactUsFragment;
import com.hisdu.ses.fragment.feedBackFragment;
import com.hisdu.ses.utils.MyPreferences;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.List;


public class MainActivity extends MerlinActivity implements Connectable, Disconnectable, Bindable, NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    int mPrevSelectedId;
    private NavigationView mNavigationView;
    private int mSelectedId;
    private Toolbar mToolbar;
    Double latitude, longitude;
    LocationManager locationManager;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    String UserRole = null;
    String createdBy = null;
    public static MainActivity main;
    public LinearLayout loader;
    TextView message,location_name,areaName;
    MyPreferences myPreferences;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        myPreferences = MyPreferences.getPreferences(this);

        main = MainActivity.this;

        mDrawerLayout = findViewById(R.id.drawer_layout);
        loader = findViewById(R.id.llProgressBar);
        message = loader.findViewById(R.id.pbText);
        fragmentManager = getSupportFragmentManager();

//        DownloadUserData();

        UserRole = myPreferences.getUserRole();
        replaceFragment(new DashboardFragment());

        mNavigationView = findViewById(R.id.navigation_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);


        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();







        if (!AppController.getInstance().isServiceRunning) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnable && !isNetworkEnable) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Location disabled");
                alertDialogBuilder
                        .setMessage("Please enable location in order to use this app!")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivityForResult(intent, 100);
                                    }
                                })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                finishAffinity();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else {
                Intent intent = new Intent(MainActivity.this, LocationService.class);
                startService(intent);
                AppController.getInstance().isServiceRunning = true;
            }

        }
    }

    public void switchFragment(int itemId) {
        mSelectedId = mNavigationView.getMenu().getItem(itemId).getItemId();
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
    }



    public void replaceFragment(Fragment navFragment) {

//        final View elevation = findViewById(R.id.elevation);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            AppController.indicatorSave = null;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();

//                if (elevation != null) {
//                    params.topMargin = navFragment instanceof DashboardFragment ? dp(0) : 0;
//
//                    Animation a = new Animation() {
//                        @Override
//                        protected void applyTransformation(float interpolatedTime, Transformation t) {
//                            elevation.setLayoutParams(params);
//                        }
//                    };
//                    a.setDuration(150);
//                    elevation.startAnimation(a);
//                }
            } catch (IllegalStateException ignored) {
            }
        }
    }

    public void replaceFragment(Fragment fragment, boolean animated, boolean updownSlide, boolean addToBackStack, String tag) {


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
        fragmentTransaction.replace(R.id.content_frame, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void navigate(final int itemId) {
//        final View elevation = findViewById(R.id.elevation);
        Fragment navFragment = null;
        switch (itemId) {
            case R.id.nav_1:
                mPrevSelectedId = itemId;
                navFragment = new DashboardFragment();
                break;

            case R.id.nav_2:
//                navFragment = new appInfoFragment();
                navFragment = new FolderFragment();

                break;
            case R.id.nav_3:
                navFragment = new contactUsFragment();
                break;
            case R.id.nav_5:
                navFragment = new feedBackFragment();
                break;


            case R.id.nav_4:
                logout();
                return;
        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            AppController.indicatorSave = null;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();

//                if (elevation != null) {
//                    params.topMargin = navFragment instanceof DashboardFragment ? dp(0) : 0;
//
//                    Animation a = new Animation() {
//                        @Override
//                        protected void applyTransformation(float interpolatedTime, Transformation t) {
//                            elevation.setLayoutParams(params);
//                        }
//                    };
//                    a.setDuration(150);
//                    elevation.startAnimation(a);
//                }
            } catch (IllegalStateException ignored) {
            }
        }
    }

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
//        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mDrawerLayout.closeDrawer(GravityCompat.START);
//        } else {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finishAffinity();
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
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
        registerReceiver(broadcastReceiver, new IntentFilter(LocationService.str_receiver));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 100) {
            // Make sure the request was successful
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.main);
        alertDialogBuilder.setTitle("Logout?");
        alertDialogBuilder
                .setMessage("Do you really want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new SharedPref(MainActivity.main).Logout();
                                Intent i = new Intent(MainActivity.main, LoginActivity.class);
                                startActivity(i);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void setSelection() {
        mNavigationView.setCheckedItem(R.id.nav_1);
    }


//    void DownloadUserData() {
//        LocationVersion = (new SharedPref(getApplicationContext()).GetLocationVersion() != null) ? new SharedPref(getApplicationContext()).GetLocationVersion() : "0";
//        ChecklistVersion = (new SharedPref(getApplicationContext()).GetCheckListVersion() != null) ? new SharedPref(getApplicationContext()).GetCheckListVersion() : "0";
//        UcVersion = (new SharedPref(getApplicationContext()).GetUcVersion() != null) ? new SharedPref(getApplicationContext()).GetUcVersion() : "0";
//
//        if (!ChecklistVersion.equals(AppController.appverion.getVersionCheckList())) {
//            loader.setVisibility(View.VISIBLE);
//            getChecklist();
//        } else {
//            loader.setVisibility(View.VISIBLE);
//            if (!LocationVersion.equals(AppController.appverion.getVersionLocation())) {
//                getLocation();
//            } else {
//                loader.setVisibility(View.VISIBLE);
//                if (!UcVersion.equals(AppController.appverion.getVersionUc())) {
//                    getUcData();
//                } else {
//                    loader.setVisibility(View.GONE);
//                }
//
//            }
//        }
//
//    }


//    void getVaccinationChecklist() {
//        message.setText("Preparing Vaccination Checklist...");
//        ServerCalls.getInstance().GetVaccinationCheckLists(new ServerCalls.OnChecklistResult() {
//            @Override
//            public void onSuccess(List<CheckList> checklist) {
//
//                ActiveAndroid.beginTransaction();
//
//                if (checklist != null || checklist.size() > 0) {
//                    AppController.clearTable(CheckList.class);
//
//                    for (int i = 0; i < checklist.size(); i++) {
//                        CheckList UI = new CheckList();
//
//                        if (checklist.get(i).getServer_id() != null) {
//                            UI.server_id = checklist.get(i).getServer_id();
//                        }
//
//                        if (checklist.get(i).getText() != null) {
//                            UI.text = checklist.get(i).getText();
//                        }
//
//                        if (checklist.get(i).getCheckListType() != null) {
//                            UI.checkListTypeName = checklist.get(i).getCheckListType();
//                        }
//
//                        if (checklist.get(i).getType() != null) {
//                            UI.type = checklist.get(i).getType();
//                        }
//
//                        UI.save();
//
//                    }
//
//                    ActiveAndroid.setTransactionSuccessful();
//                    ActiveAndroid.endTransaction();
//
//                    new SharedPref(getApplicationContext()).SavecheckList(AppController.appverion.getVersionCheckList());
//
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Error Loading CheckList", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailed(int errorCode, String message) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Oh Crap!")
//                        .setCancelable(false)
//                        .setMessage("Failed to checkList, Retry?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getChecklist();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finishAffinity();
//                            }
//                        }).show();
//                loader.setVisibility(View.GONE);
//
//            }
//        });
//
//    }
//
//    void getChecklist() {
//        message.setText("Preparing Checklist...");
//        ServerCalls.getInstance().GetChecklistData(new ServerCalls.OnChecklistResult() {
//            @Override
//            public void onSuccess(List<CheckList> checklist) {
//
//                ActiveAndroid.beginTransaction();
//
//                if (checklist != null || checklist.size() > 0) {
//                    AppController.clearTable(CheckList.class);
//
//                    for (int i = 0; i < checklist.size(); i++) {
//                        CheckList UI = new CheckList();
//
//                        if (checklist.get(i).getServer_id() != null) {
//                            UI.server_id = checklist.get(i).getServer_id();
//                        }
//
//                        if (checklist.get(i).getText() != null) {
//                            UI.text = checklist.get(i).getText();
//                        }
//
//                        if (checklist.get(i).getCheckListType() != null) {
//                            UI.checkListTypeName = checklist.get(i).getCheckListType();
//                        }
//
//                        if (checklist.get(i).getType() != null) {
//                            UI.type = checklist.get(i).getType();
//                        }
//
//                        UI.save();
//
//                    }
//
//                    ActiveAndroid.setTransactionSuccessful();
//                    ActiveAndroid.endTransaction();
//
//                    new SharedPref(getApplicationContext()).SavecheckList(AppController.appverion.getVersionCheckList());
//
//                    if (!LocationVersion.equals(AppController.appverion.getVersionLocation())) {
//                        getLocation();
//                    } else {
//
//                        if (!UcVersion.equals(AppController.appverion.getVersionUc())) {
//                            getUcData();
//                        } else {
//
//                        }
//                    }
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Error Loading CheckList", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailed(int errorCode, String message) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Oh Crap!")
//                        .setCancelable(false)
//                        .setMessage("Failed to checkList, Retry?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getChecklist();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finishAffinity();
//                            }
//                        }).show();
//                loader.setVisibility(View.GONE);
//
//            }
//        });
//
//    }
//
//
//    void getLocation() {
//        message.setText("Preparing Locations...");
//        ServerCalls.getInstance().GetLocationData("0", new ServerCalls.OnLocationResult() {
//            @Override
//            public void onSuccess(final List<Location> location) {
//
//                ActiveAndroid.beginTransaction();
//
//                if (location != null || location.size() > 0) {
//                    AppController.clearTable(Location.class);
//
//                    for (int i = 0; i < location.size(); i++) {
//                        Location UI = new Location();
//
//                        if (location.get(i).getServer_id() != null) {
//                            UI.server_id = location.get(i).getServer_id();
//                        }
//
//                        if (location.get(i).getName() != null) {
//                            UI.name = location.get(i).getName();
//                        }
//
//                        if (location.get(i).getType() != null) {
//                            UI.type = location.get(i).getType();
//                        }
//
//                        UI.save();
//                    }
//
//                    ActiveAndroid.setTransactionSuccessful();
//                    ActiveAndroid.endTransaction();
//
//                    new SharedPref(getApplicationContext()).Savelocation(AppController.appverion.getVersionLocation());
//
//                    if (!UcVersion.equals(AppController.appverion.getVersionUc())) {
//                        getUcData();
//                    } else {
//
//                    }
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Error Loading Location", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailed(int errorCode, String errorMessage) {
//
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Oh Crap!")
//                        .setCancelable(false)
//                        .setMessage("Failed to sync location, Retry?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getLocation();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finishAffinity();
//                            }
//                        }).show();
//                loader.setVisibility(View.GONE);
//
//            }
//        });
//
//    }
//
//    void getUcData() {
//        message.setText("Preparing Uc Data...");
//        ServerCalls.getInstance().GetCenterData(new ServerCalls.OnUcSaveResult() {
//            @Override
//            public void onSuccess(final List<UcData> ucdata) {
//
//                ActiveAndroid.beginTransaction();
//
//                if (ucdata != null || ucdata.size() > 0) {
//                    AppController.clearTable(UcData.class);
//
//                    for (int i = 0; i < ucdata.size(); i++) {
//                        UcData UI = new UcData();
//
//                        if (ucdata.get(i).getServerID() != null) {
//                            UI.ServerID = ucdata.get(i).getServerID();
//                        }
//
//                        if (ucdata.get(i).getTehsilCode() != null) {
//                            UI.TehsilCode = ucdata.get(i).getTehsilCode();
//                        }
//
//                        if (ucdata.get(i).getUCName() != null) {
//                            UI.UCName = ucdata.get(i).getUCName();
//                        }
//
//                        if (ucdata.get(i).getName() != null) {
//                            UI.Name = ucdata.get(i).getName();
//                        }
//
//                        if (ucdata.get(i).getLatitude() != null) {
//                            UI.latitude = ucdata.get(i).getLatitude();
//                        }
//
//                        if (ucdata.get(i).getLongitude() != null) {
//                            UI.Longitude = ucdata.get(i).getLongitude();
//                        }
//
//                        UI.save();
//
//                    }
//
//                    ActiveAndroid.setTransactionSuccessful();
//                    ActiveAndroid.endTransaction();
//
//                    new SharedPref(getApplicationContext()).Saveuc(AppController.appverion.getVersionUc());
//
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Error Loading Ucs", Toast.LENGTH_SHORT).show();
//                }
//                loader.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onFailed(int errorCode, String errorMessage) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Oh Crap!")
//                        .setCancelable(false)
//                        .setMessage("Failed to sync UCs, Retry?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getUcData();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finishAffinity();
//                            }
//                        }).show();
//                loader.setVisibility(View.GONE);
//
//            }
//        });
//
//    }

}