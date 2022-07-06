package com.hisdu.SESCluster.base;

import android.location.Location;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Usman Kurd on 9/6/2017.
 */

public abstract class ToolbarActivity extends BaseActivity {
    /*tag*/
    private static final String tag = "ToolbarActivity";

    /*Child accessible View Hooks*/
    protected Toolbar mToolbar;
    protected TextView tvPageTitle, tvCount;
    protected ImageView ivSync, ivSubmit,ivUpdate;
    protected ImageButton mMenuButton, ivBack;
    ArrayList<UnSentRecordsObject> unSentRecsObjects = new ArrayList<>();
    RelativeLayout rlTvCount;

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected void initializeControls() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar == null) {
            throw new IllegalArgumentException(tag + "| toolbar with layout id tool_bar is missing from the layout");
        }
        mToolbar.setTitle(getToolbarTitle());
        setSupportActionBar(mToolbar);
        tvPageTitle =  mToolbar.findViewById(R.id.tvPageTitle);
        mMenuButton =  mToolbar.findViewById(R.id.action_bar_menu_button);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMenuClicked();
            }
        });
        tvCount =  mToolbar.findViewById(R.id.tvCount);
        ivUpdate = mToolbar.findViewById(R.id.ivUpdate);
        ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMenuClicked();
            }
        });
        tvPageTitle.setText(getToolbarTitle());
    }

    @Override
    protected void attachListeners() {

    }

    @Override
    protected void initializeData() {

    }

    protected abstract int getToolbarTitle();

    protected void updateTitle(String title) {
        tvPageTitle.setText(title);
    }

    protected Location getLocation() {
        return myLocation;
    }

    protected String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Globals.WEB_DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(new Date());
    }

    protected String getIMEINO() {
        return DEVICE_IMEI;
    }

    protected abstract int getUserInfo();

//    private void btnMenuClicked() {
//        menuButtonPressed();
//    }

    protected void menuButtonPressed() {
        onBackPressed();
    }

    protected void btnSearchClicked() {

    }

    public void hideSync() {
        tvCount.setVisibility(View.INVISIBLE);
        ivUpdate.setVisibility(View.INVISIBLE);
    }

    public void showSync() {
        tvCount.setVisibility(View.VISIBLE);
        ivUpdate.setVisibility(View.VISIBLE);
    }
    protected void btnMenuClicked() {
    }
    public void updateCount() {
        unSentRecsObjects = mDbManager.getUnSentRecords();
        if (unSentRecsObjects.size() > 0) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(unSentRecsObjects.size()));
        } else
            tvCount.setVisibility(View.GONE);
    }

}
