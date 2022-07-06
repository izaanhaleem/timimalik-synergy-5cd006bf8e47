package com.hisdu.SESCluster.base;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.fragments.support.NavigationDrawerFragment;
import com.hisdu.SESCluster.interfaces.NavigationCallbacks;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.utils.Utils;


public abstract class NavigationDrawerActivity extends ToolbarActivity implements NavigationCallbacks,

        DialogInterface.OnDismissListener, OnDialogButtonClickListener {
    /*tag*/
    private static final String tag = "ToolbarDrawerActivity";

    protected NavigationDrawerFragment mNavigationDrawerFragment;
    //    private TextView mTxtName, mTxtDepartment;
    protected LocalDatabaseManager mDbManager;
    //    protected CircleImageView userImage;
    protected Button btnMenuArabic, btnMenuEnglish;
    protected boolean isCampaignCancelled = false;
    String lang;
    int dialogCLickResponse = -1;
    protected OnBackPressedListener onBackPressedListener;

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected void initializeControls() {
        super.initializeControls();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.initDrawer(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), null);
//        mDbManager = LocalDatabaseManager.getInstance(this);
        btnMenuArabic = (Button) findViewById(R.id.btnMenuArabic);
        btnMenuEnglish = (Button) findViewById(R.id.btnMenuEnglish);
        lang = Utils.getLanguage(getBaseContext());
    }

    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    @Override
    protected int getUserInfo() {
        return 0;
    }

    @Override
    protected void attachListeners() {

    }

    @Override
    protected void menuButtonPressed() {
        toggleDrawer();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onItemSelected(int selectedIdx) {
//        if (mNavigationDrawerFragment.menuItems.size() == 3) {
//            switch (selectedIdx) {
//                case 0:
//                    requestPermissionDialog(getResources().getString(R.string.cancel), getResources().getString(R.string.cancel_campagin_message));
//                    break;
//                case 1:
//                    Bundle bundle1 = new Bundle();
//                    SwitchAccountFragment switchAccountFragment = SwitchAccountFragment.getInstance(bundle1, getString(R.string.switch_account), -1);
//                    replaceFragment(switchAccountFragment, false, false, false, switchAccountFragment.getTag());
//                    break;
//                case 2:
//
//                    logoutDialog();
//                    break;
//            }
//        } else if (mNavigationDrawerFragment.menuItems.size() == 2) {
//            switch (selectedIdx) {
//                case 0:
//                    Bundle bundle1 = new Bundle();
//                    SwitchAccountFragment switchAccountFragment = SwitchAccountFragment.getInstance(bundle1, getString(R.string.switch_account), -1);
//                    replaceFragment(switchAccountFragment, false, false, false, switchAccountFragment.getTag());
//                    break;
//                case 1:
//                    logoutDialog();
//                    break;
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        /* Close navigation drawer, if open */
        if (mToolbar != null && mToolbar.getVisibility() == View.GONE || mToolbar != null && mToolbar.getVisibility() == View.INVISIBLE)
            mToolbar.setVisibility(View.VISIBLE);
//        if (mNavigationDrawerFragment != null && mNavigationDrawerFragment.isDrawerOpen())
//            mNavigationDrawerFragment.closeDrawer();
//        super.onBackPressed();
        else {
            if (onBackPressedListener != null) {
                onBackPressedListener.onBackPressed();
            } else {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    public void toggleDrawer() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
//        if (!(fragment instanceof FragmentHome)) {
//            mNavigationDrawerFragment.updateNavigationAdapterToCreateCampaign();
//            Bundle bundle = new Bundle();
//            FragmentHome homeFragment = FragmentHome.getInstance(bundle, getString(R.string.app_name), -1);
//            replaceFragment(homeFragment, false, false, false, "");
//        } else {
//            mNavigationDrawerFragment.updateNavigationAdapter();
//        }
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            mNavigationDrawerFragment.openDrawer();
    }

    public void updateIndex() {
        mNavigationDrawerFragment.updateSelectedIndex();
    }


    @Override
    public void onLanguageSelected(boolean isChecked) {
        String lang = Utils.getLanguage(this);
        String newLang = "en";
        if (isChecked) {
            newLang = "en";
        } else {
            newLang = "ar";
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

//    @Override
//    public void onDialogPositiveButtonClick(int requestCode) {
//        if (mToolbar.getVisibility() == View.GONE || mToolbar.getVisibility() == View.INVISIBLE)
//            mToolbar.setVisibility(View.VISIBLE);
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
////        FragmentHome homeFragment = FragmentHome.getInstance(new Bundle(), getString(R.string.app_name), -1);
////        replaceFragment(homeFragment, false, false, false, "");
////        FragmentManager fragmentManager = this.getSupportFragmentManager();
////        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////        isCampaignCancelled = true;
//
//    }
//
//    @Override
//    public void onDialogNegativeButtonClick(int requestCode) {
//
//    }
//
//    private void requestPermissionDialog(String title, String message) {
//        AlertDialogFragment alertDialogFragment = new AlertDialogFragment(title, message, false, true, -1, this);
//        alertDialogFragment.show(getSupportFragmentManager(), "Alert");
//    }

    public interface OnBackPressedListener {
        public void onBackPressed();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void onPositiveClick() {
        dialogCLickResponse = 1;
    }

    public void onNegativeClick() {
        dialogCLickResponse = 2;
    }
//    private void logoutDialog(){
//        CancelMapDialog cancelMapDialog = new CancelMapDialog(getResources().getString(R.string.log_out),
//                getResources().getString(R.string.are_you_want_to_log_out),getResources().getString(R.string.yes),getResources().getString(R.string.no), 1, new OnDialogButtonClickListener() {
//            @Override
//            public void onDialogPositiveButtonClick(int requestCode) {
//                Intent intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                Utils.changeLoginStatus(false, NavigationDrawerActivity.this);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onDialogNegativeButtonClick(int requestCode) {
//
//            }
//        });
//        cancelMapDialog.show(getSupportFragmentManager(), "Alert");
//    }
}
