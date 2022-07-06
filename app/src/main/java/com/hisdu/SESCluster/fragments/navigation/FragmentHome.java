package com.hisdu.SESCluster.fragments.navigation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import com.hisdu.SESCluster.communication.NetworkUtil;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.models.clustersType.ClustersType;
import com.hisdu.SESCluster.objects.ChildObject;
import com.hisdu.SESCluster.objects.ResponseObject;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.ses.AppController;
import com.hisdu.ses.BuildConfig;
import com.hisdu.ses.Database.CheckListSend;
import com.hisdu.ses.Database.House;
import com.hisdu.ses.Database.HouseChildDetail;
import com.hisdu.ses.Database.      HouseDetail;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Database.ZeroDoseMaster;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.ClusterModel;
import com.hisdu.ses.Models.GenericResponse;
import com.hisdu.ses.Models.epiCenters.EpiCenter;
import com.hisdu.ses.Models.indicators.CheckListImage;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.base.BaseFragment;
import com.hisdu.SESCluster.fragments.AlertDialogFragment;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.fragment.DashboardFragment;
import com.hisdu.ses.fragment.MasterRecordFragment;
import com.hisdu.ses.recordAdapter;
import com.hisdu.ses.utils.CustomSearchableSpinner;
import com.hisdu.ses.utils.ServerCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FragmentHome extends BaseFragment implements OnDialogButtonClickListener {
    Button btnAddCluster1, btnAddCluster2, btnAddCluster3, btnSubmit;
    TextView tvClusterOne, tvClusterTwo, tvClusterThree, tvStatus1, tvStatus2, tvStatus3;
    LinearLayout clusterTypeLayout, clusterLayout;
    RelativeLayout cluster1, cluster2, cluster3;
    CustomSearchableSpinner clusterType;
    List<ClustersType> clustersTypeArrayList = new ArrayList<>();
    ProgressDialog PD;
    Integer CurrentIndex = 0;
    Integer syncCount=0;
    public List<UnSentRecordsObject> InsUnsyc;

    public static FragmentHome getInstance(Bundle bundle, String title, int icon) {
        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        btnAddCluster1 = v.findViewById(R.id.btnCluster1);
        btnAddCluster2 = v.findViewById(R.id.btnCluster2);
        btnAddCluster3 = v.findViewById(R.id.btnCluster3);
        tvClusterOne = v.findViewById(R.id.tvCluster1);
        tvClusterTwo = v.findViewById(R.id.tvCluster2);
        tvStatus1 = v.findViewById(R.id.tvStatus1);
        tvStatus2 = v.findViewById(R.id.tvStatus2);
        tvStatus3 = v.findViewById(R.id.tvStatus3);
        tvClusterThree = v.findViewById(R.id.tvCluster3);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        clusterTypeLayout = v.findViewById(R.id.clusterTypeLayout);
        clusterType = v.findViewById(R.id.clusterType);
        clusterLayout = v.findViewById(R.id.clustersLayout);
        cluster1 = v.findViewById(R.id.rvFirstCluster);
        cluster2 = v.findViewById(R.id.rvSecondCluster);
        cluster3 = v.findViewById(R.id.rvThirdCluster);
        PD = new ProgressDialog(getActivity());
        updateEntry();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        btnAddCluster1.setOnClickListener(this);
        btnAddCluster2.setOnClickListener(this);
        btnAddCluster3.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        /*btnAddCluster2.setEnabled(false);
        btnAddCluster2.setAlpha(0.5f);
        btnAddCluster3.setEnabled(false);
        btnAddCluster3.setAlpha(0.5f);
        btnAddCluster1.setEnabled(false);
        btnAddCluster1.setAlpha(0.5f);*/

        //int count =  mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID, MasterRecordFragment.locationCode);

        int clusterOne = 0;
        boolean clusterOneSync = true;
        int clusterTwo = 0;
        int clusterThree = 0;

        ArrayList<UnSentRecordsObject> clusterOneList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(), "" + AppController.appModuleID, MasterRecordFragment.locationCode, "1");

        if (clusterOneList != null && clusterOneList.size() > 0) {
            for (int i = 0; i < clusterOneList.size(); i++) {
                clusterOne = clusterOne + Integer.parseInt(clusterOneList.get(i).getCount());
                if (clusterOneList.get(i).getSyncStatus().equals("0")) {
                    clusterOneSync = false;
                }
            }
        }

        ArrayList<UnSentRecordsObject> clusterTwoList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(), "" + AppController.appModuleID, MasterRecordFragment.locationCode, "2");

        if (clusterTwoList != null && clusterTwoList.size() > 0) {
            for (int j = 0; j < clusterTwoList.size(); j++) {
                clusterTwo = clusterTwo + Integer.parseInt(clusterTwoList.get(j).getCount());
            }
        }

        ArrayList<UnSentRecordsObject> clusterThreeList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(), "" + AppController.appModuleID, MasterRecordFragment.locationCode, "3");

        if (clusterThreeList != null && clusterThreeList.size() > 0) {
            for (int k = 0; k < clusterThreeList.size(); k++) {
                clusterThree = clusterThree + Integer.parseInt(clusterThreeList.get(k).getCount());
            }
        }

        clusterType.setTitle("Select Cluster Type");
        boolean status = btnAddCluster1.isEnabled();
        clusterLayout.setVisibility(View.VISIBLE);
        if (AppController.appModuleID.toString().equals("4")) {


            btnAddCluster1.setText("Cluster");
            cluster1.setVisibility(View.VISIBLE);
            cluster2.setVisibility(View.GONE);
            cluster3.setVisibility(View.GONE);

        } else if (AppController.appModuleID.toString().equals("7")) {
            cluster1.setVisibility(View.VISIBLE);
            cluster2.setVisibility(View.VISIBLE);
            cluster3.setVisibility(View.VISIBLE);
        }
        if (AppController.appModuleID == 4) {
            tvClusterOne.setText("" + clusterOne);
            if (clusterOne < 10) {

                if (clusterOne == 10 && btnAddCluster1.isEnabled()) {
                    Utils.saveSiaTeamNo("", getActivity());
                    Utils.saveSelectDay(-1, getActivity());
                    Utils.saveArea("", getActivity());
                    tvStatus1.setText(getResources().getString(R.string.done));
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(true);
                    btnAddCluster2.setAlpha(1.0f);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                } else {
                    btnAddCluster1.setEnabled(true);
                    btnAddCluster1.setAlpha(1.0f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster2.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                    if (clusterOne > 0 && clusterOne < 10) {
                        tvStatus1.setText(getResources().getString(R.string.current));
                    } else {
                        tvStatus1.setText(getResources().getString(R.string.not_done));
                    }
                }
            }


            if (clusterOne == 10 ) {

                if(clusterOneSync){


                    tvClusterOne.setText("0");
                    btnAddCluster1.setEnabled(true);
                    btnAddCluster1.setAlpha(1f);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnSubmit.setEnabled(false);
                    btnSubmit.setAlpha(0.5f);
                }else {
                    tvClusterOne.setText("" + clusterOne);
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnSubmit.setEnabled(true);
                    btnSubmit.setAlpha(1.0f);
                }
            }
//            if (clusterOne == 10) {
//
//                btnAddCluster1.setEnabled(false);
//                btnAddCluster1.setAlpha(0.5f);
//                btnSubmit.setVisibility(View.VISIBLE);
//                btnSubmit.setEnabled(true);
//                btnSubmit.setAlpha(1.0f);
//            }

        } else {

            if (clusterOne < 10) {

                if (clusterOne == 10 && btnAddCluster1.isEnabled()) {
                    Utils.saveSiaTeamNo("", getActivity());
                    Utils.saveSelectDay(-1, getActivity());
                    Utils.saveArea("", getActivity());
                    tvStatus1.setText(getResources().getString(R.string.done));
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(true);
                    btnAddCluster2.setAlpha(1.0f);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                } else {
                    btnAddCluster1.setEnabled(true);
                    btnAddCluster1.setAlpha(1.0f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster2.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                    if (clusterOne > 0 && clusterOne < 10) {
                        tvStatus1.setText(getResources().getString(R.string.current));
                    } else {
                        tvStatus1.setText(getResources().getString(R.string.not_done));
                    }
                }
            } else if (clusterOne == 10) {
                tvClusterOne.setText("" + clusterOne);
                if (clusterOne == 10) {

                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
//                    btnSubmit.setVisibility(View.VISIBLE);
//                    btnSubmit.setEnabled(true);
//                    btnSubmit.setAlpha(1.0f);
                }
            }

            if (clusterOne == 10 && clusterTwo < 10) {

                if (clusterTwo == 10 && btnAddCluster2.isEnabled()) {
                    tvStatus2.setText(getResources().getString(R.string.done));
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster2.setAlpha(0.5f);
                    btnAddCluster3.setEnabled(true);
                    btnAddCluster3.setAlpha(1.0f);
                } else {
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(true);
                    btnAddCluster2.setAlpha(1.0f);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                    if (clusterTwo > 0 && clusterTwo < 10) {
                        tvStatus2.setText(getResources().getString(R.string.current));
                    } else {
                        tvStatus2.setText(getResources().getString(R.string.not_done));
                    }
                }
            }

            if (clusterTwo == 10 && clusterThree < 10) {

                if (clusterThree == 10 && btnAddCluster3.isEnabled()) {
                    tvStatus3.setText(getResources().getString(R.string.done));
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster2.setAlpha(0.5f);
                    btnAddCluster3.setEnabled(false);
                    btnAddCluster3.setAlpha(0.5f);
                } else {
                    btnAddCluster1.setEnabled(false);
                    btnAddCluster1.setAlpha(0.5f);
                    btnAddCluster2.setEnabled(false);
                    btnAddCluster2.setAlpha(0.5f);
                    btnAddCluster3.setEnabled(true);
                    btnAddCluster3.setAlpha(1.0f);
                    if (clusterThree > 0 && clusterThree < 10) {
                        tvStatus3.setText(getResources().getString(R.string.current));
                    } else {
                        tvStatus3.setText(getResources().getString(R.string.not_done));
                    }
                }
            }

            tvClusterOne.setText("" + clusterOne);
            tvClusterTwo.setText("" + clusterTwo);
            tvClusterThree.setText("" + clusterThree);

            if (clusterOne >= 10 && clusterTwo >= 10 && clusterThree >= 10) {
                btnAddCluster1.setEnabled(false);
                btnAddCluster1.setAlpha(0.5f);
                btnAddCluster2.setEnabled(false);
                btnAddCluster2.setAlpha(0.5f);
                btnAddCluster3.setEnabled(false);
                btnAddCluster3.setAlpha(0.5f);
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setEnabled(true);
                btnSubmit.setAlpha(1.0f);
            }

        }

        getClusterTypes();

    }

    void getClusterTypes() {

        clustersTypeArrayList = ClustersType.getClusterTypes();

        if (clustersTypeArrayList.size() != 0) {
            String[] epiCentersArray = new String[clustersTypeArrayList.size() + 1];
            epiCentersArray[0] = "Select Cluster Type";

            for (int i = 0; i < clustersTypeArrayList.size(); i++) {

                epiCentersArray[i + 1] = clustersTypeArrayList.get(i).getName();
            }

            ArrayAdapter<String> epiCentersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, epiCentersArray);
            clusterType.setAdapter(epiCentersAdapter);


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.home));
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        hideToolBarSync();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCluster1:
                HouseInfoFragment fragmentSelectionCluster1 = HouseInfoFragment.getInstance(new Bundle(), "", -1);
                String ttag = fragmentSelectionCluster1.getTag();
                AppController.clusterType = "1";
                replaceFragment(fragmentSelectionCluster1, true, false, true, fragmentSelectionCluster1.getTag());
                break;
            case R.id.btnCluster2:
                HouseInfoFragment fragmentSelectionCluster2 = HouseInfoFragment.getInstance(new Bundle(), "", -1);
                AppController.clusterType = "2";
                replaceFragment(fragmentSelectionCluster2, true, false, true, fragmentSelectionCluster2.getTag());
                break;
            case R.id.btnCluster3:
                HouseInfoFragment fragmentSelectionCluster3 = HouseInfoFragment.getInstance(new Bundle(), "", -1);
                AppController.clusterType = "3";
                replaceFragment(fragmentSelectionCluster3, true, false, true, fragmentSelectionCluster3.getTag());
                break;
            case R.id.btnSubmit:
                PD.setTitle("Syncing Record, Please wait...");
                PD.setMessage("Progress : " + "0" + "/" + InsUnsyc.size());
                PD.setCancelable(false);
                PD.show();
                SaveUnsyncData();
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {
        switch (requestCode) {
            case 1:
                AlertDialogFragment alertDialogFragment = new AlertDialogFragment(getResources().getString(R.string.app_name), getResources().getString(R.string.thank_you), 2, true, this, "");
                alertDialogFragment.show(getFragmentManager(), "Alert");
                break;
            case 2:
//                Utils.saveClusterOne(0, getActivity());
//                Utils.saveClusterTwo(0, getActivity());
//                Utils.saveClusterThree(0, getActivity());

                //mDbManager.deleteSentRec(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID, MasterRecordFragment.locationCode);
                Utils.saveDivision("", getActivity());
                Utils.saveSiaTeamNo("", getActivity());
                Utils.saveSelectDay(-1, getActivity());
                Utils.saveArea("", getActivity());
                Utils.saveDistrict("", getActivity());
                Utils.saveTown("", getActivity());
                Utils.saveUC("", getActivity());
                getActivity().onBackPressed();
                break;
        }

    }

    void SaveUnsyncData() {


        if (CurrentIndex < InsUnsyc.size()) {
            sendData(InsUnsyc.get(CurrentIndex), String.valueOf(CurrentIndex));
        } else {

            for(int i=0;i<InsUnsyc.size();i++){
                mDbManager.UpdateRecordsOffline(InsUnsyc.get(i).getSync());
            }

            lolMethod();



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Data Sync Successfully!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, id) ->
                    {
                        dialog.cancel();
                        dialog();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    void lolMethod() {
        PD.dismiss();
        updateEntry();
    }

    void updateEntry() {

        InsUnsyc = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(), "" + AppController.appModuleID, MasterRecordFragment.locationCode);
        CurrentIndex = 0;
    }

    void sendData(final UnSentRecordsObject indicatorMasterDataSave, final String pos) {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {

            PD.setMessage("Progress : " + CurrentIndex + "/" + InsUnsyc.size());

            if (!PD.isShowing()) {
                PD.show();
            }


            ClusterModel clusterModel = new ClusterModel();
            clusterModel.setUnSentRecordsObjectsList(indicatorMasterDataSave);

            String json = new Gson().toJson(clusterModel);

            ServerCalls.getInstance().SaveUnsyncClusterRecord(clusterModel, new ServerCalls.OnGenericResult() {
                @Override
                public void onSuccess(GenericResponse response) {
                    if (response.getStatus().equals("1")) {
//                        mDbManager.deleteRecord(indicatorMasterDataSave.getSync());
                        mDbManager.UpdateAlreadySync(indicatorMasterDataSave.getSync());
//                        mDbManager.UpdateRecordsOffline(InsUnsyc.get(CurrentIndex-1).getSync());
                        CurrentIndex = CurrentIndex + 1;

                        SaveUnsyncData();
                    } else {
                        lolMethod();
                        dialog2(response.getMessage());
                    }


//                    PD.dismiss();
//                    if(response.getStatus().equals("1")) { dialog(); }
//                    else { dialog2(response.getMessage()); }
                }

                @Override
                public void onFailed(int errorCode, String errorMessage) {
                    lolMethod();
                    dialog2(errorMessage);
                }
            });


        }
    }

    void dialog() {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment(getResources().getString(R.string.app_name), getResources().getString(R.string.thank_you), 2, true, this, "");
        alertDialogFragment.show(getFragmentManager(), "Alert");
    }

    void dialog2(String msg) {
        Dialogs.showPositiveAlert(getActivity(), msg, getString(R.string.ok), this, 1, getString(R.string.app_name));
    }

    @Override
    public void onPostExecution(String response, int requestCode) {
        /*{"Status":"0","Message":"Error in Network"}*/
        if (response != null) {
            if (response.length() > 0) {
                if (requestCode == Globals.RequestCodes.Record_Save) {
                    ResponseObject responseObject = new Gson().fromJson(response, ResponseObject.class);
                    if (responseObject != null) {
                        if (responseObject.getStatus() == 0) {
                            Dialogs.showAlert(getActivity(), getString(R.string.are_you_sure_you_want_to_submit_cluster_info), getString(R.string.ok), getString(R.string.cancel), this, 1);
                        } else {
                            Dialogs.showPositiveAlert(getActivity(), responseObject.getMessage(), getString(R.string.ok), this, 1, getString(R.string.app_name));
                        }
                    }
                }
            } else {
                btnSubmit.setEnabled(true);
            }
        } else {
            btnSubmit.setEnabled(true);
        }
    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {
    }

}
