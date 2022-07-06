package com.hisdu.SESCluster.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.base.ToolbarActivity;
import com.hisdu.SESCluster.communication.NetworkUtil;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.SESCluster.utils.Dialogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Usman Kurd on 10/20/2017.
 */

public class MainActivity extends ToolbarActivity {
    ArrayList<UnSentRecordsObject> unSentRec = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initializeControls() {
        super.initializeControls();

    }

    @Override
    protected void attachListeners() {

    }

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getToolbarTitle() {
        return R.string.app_name;
    }

    @Override
    protected int getUserInfo() {
        return 0;
    }

    private void pushUnSentRecords() {
        unSentRec = mDbManager.getUnSentRecords();
        if (unSentRec.size() > 0) {
            if (NetworkUtil.isNetworkAvailable(this)) {
                for (UnSentRecordsObject rec : unSentRec) {
                    try {
                        JSONObject jsonObject = new JSONObject(rec.getData());
                        callPostMethod(rec.getUrl(), rec.getId(), jsonObject, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else
                Dialogs.showDialog(getResources().getString(R.string.no_internet_connect), getString(R.string.app_name),
                        this, true, false, getString(R.string.ok), "", false);
        }
    }

    @Override
    public void onPostExecution(String response, int requestCode) {
        if (response != null && response.length() > 0) {
            for (UnSentRecordsObject unSentRecordObject : unSentRec) {
                if (requestCode == unSentRecordObject.getId()) {
                    boolean recordDeleted = mDbManager.deleteUnSentRec(String.valueOf(unSentRecordObject.getId()));
                    unSentRec = mDbManager.getUnSentRecords();
                    if (unSentRec.size() > 0)
                        tvCount.setText(String.valueOf(unSentRec.size()));
                    else
                        tvCount.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void btnMenuClicked() {
        pushUnSentRecords();
    }
}
