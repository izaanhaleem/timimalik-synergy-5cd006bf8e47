package com.hisdu.SESCluster.fragments.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.ses.AppController;
import com.hisdu.ses.BuildConfig;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.login.LoginResponse;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.adapters.AddChildAdapter;
import com.hisdu.SESCluster.base.BaseFragment;
import com.hisdu.SESCluster.communication.NetworkUtil;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.ChildObject;
import com.hisdu.SESCluster.objects.ResponseObject;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.TransparentProgressDialog;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.fragment.MasterRecordFragment;
import com.hisdu.ses.utils.CustomSearchableSpinner;
import com.hisdu.ses.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class HouseInfoFragment extends BaseFragment implements AddChildAdapter.OnViewClickListener, OnDialogButtonClickListener {

    Button btnAddChild, btnSubmit;
    RecyclerView rvAddChild;
    ArrayList<ChildObject> childInfoObjects = new ArrayList<>();
    EditText etHouseNo, etAgeMatchedVaccinatedChildren, etPartiallyVaccinatedChildren, etUnVaccinatedChildren;
    TextView clusterName, clusterCount;
    int CurrentClusterCount = 0;
    private TransparentProgressDialog mProgress;
    String[] setArray = new String[]{"Select Settlement", "HRMP", "Non-HRMP"};
    EditText areaname;
    int currentCluster=0;
    CustomSearchableSpinner Settlement;

    public static HouseInfoFragment getInstance(Bundle bundle, String title, int icon) {
        HouseInfoFragment fragment = new HouseInfoFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        btnAddChild = v.findViewById(R.id.btnAddChild);
        rvAddChild = v.findViewById(R.id.rvAddChild);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        areaname = v.findViewById(R.id.areaname);
        etHouseNo = v.findViewById(R.id.etHouseNo);
        Settlement = v.findViewById(R.id.Settlement);
        etAgeMatchedVaccinatedChildren = v.findViewById(R.id.etAgeMatchedVaccinatedChildren);
        etPartiallyVaccinatedChildren = v.findViewById(R.id.etPartiallyVaccinatedChildren);
        etUnVaccinatedChildren = v.findViewById(R.id.etUnVaccinatedChildren);
        clusterName = v.findViewById(R.id.clusterName);
        clusterCount = v.findViewById(R.id.tvClusterCount);
        rvAddChild.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAddChild.setNestedScrollingEnabled(true);

        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, setArray);
        Settlement.setAdapter(setAdapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_house_info_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        btnAddChild.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void initializeData()

    {

        int clusterOne   = 0;
        int clusterTwo   = 0;
        int clusterThree = 0;


                if(AppController.clusterType.equals("1"))
                {
                    ArrayList<UnSentRecordsObject> clusterOneList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID, MasterRecordFragment.locationCode,"1");

                    if(clusterOneList != null && clusterOneList.size() > 0) {

                        for (int i = 0; i < clusterOneList.size(); i++) {
                            if (clusterOneList.get(i).getSyncStatus().equals("0")) {
                                clusterOne = clusterOne + Integer.parseInt(clusterOneList.get(i).getCount());
                            }
                        }
                        if (clusterOne != 0) {
                            areaname.setText(clusterOneList.get(0).getArea());
                            if (clusterOneList.get(0).getSettlement().equals("HRMP")) {
                                Settlement.setSelection(1);
                            } else {
                                Settlement.setSelection(2);
                            }
                            areaname.setEnabled(false);
                            Settlement.setEnabled(false);


                        }
                    }
                    CurrentClusterCount = clusterOne;
                    clusterName.setText(getString(R.string.cluster_1));
                    clusterCount.setText(""+clusterOne);
                }




            if(AppController.clusterType.equals("2"))

            {
                ArrayList<UnSentRecordsObject> clusterTwoList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),""+ AppController.appModuleID, MasterRecordFragment.locationCode,"2");
                if(clusterTwoList != null && clusterTwoList.size() > 0) {
                    areaname.setText(clusterTwoList.get(0).getArea());
                    if (clusterTwoList.get(0).getSettlement().equals("HRMP")) {
                        Settlement.setSelection(1);
                    } else {
                        Settlement.setSelection(2);
                    }
                    areaname.setEnabled(false);
                    Settlement.setEnabled(false);


                }
                for(int j = 0 ; j < clusterTwoList.size() ; j++ ) { clusterTwo = clusterTwo + Integer.parseInt(clusterTwoList.get(j).getCount()); }
                CurrentClusterCount = clusterTwo;
                clusterName.setText(getString(R.string.cluster_2));
                clusterCount.setText(""+clusterTwo);
            }



            if(AppController.clusterType.equals("3"))

            {
                ArrayList<UnSentRecordsObject> clusterThreeList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID, MasterRecordFragment.locationCode,"3");

                if(clusterThreeList != null && clusterThreeList.size() > 0)

                {
                areaname.setText(clusterThreeList.get(0).getArea());
                if(clusterThreeList.get(0).getSettlement().equals("HRMP")) { Settlement.setSelection(1); }
                else {Settlement.setSelection(2);}
                areaname.setEnabled(false);
                Settlement.setEnabled(false);


                }
                for(int k = 0 ; k < clusterThreeList.size() ; k++ ) { clusterThree = clusterThree + Integer.parseInt(clusterThreeList.get(k).getCount()); }

                CurrentClusterCount = clusterThree;
                clusterName.setText(getString(R.string.cluster_3));
                clusterCount.setText(""+clusterThree);
            }


        if (clusterOne >= 0 && clusterOne < 10) {


        } else if (clusterTwo >= 0 && clusterTwo < 10) {

        } else if (clusterThree >= 0 && clusterThree < 10) {

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.house_info));
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if (childInfoObjects.size() > 0) {
                    btnSubmit.setVisibility(View.VISIBLE);
                } else
                    btnSubmit.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(r, 500);
        hideToolBarSync();
        AddChildAdapter addChildAdapter = new AddChildAdapter(getActivity(), childInfoObjects, this);
        rvAddChild.setAdapter(addChildAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddChild:


                if ((CurrentClusterCount ) < 10) {
                    if(isValidForm()){

                        AddChildFragment addChildFragment = AddChildFragment.getInstance(new Bundle(), "", -1);
                        addChildFragment.setTargetFragment(this, Globals.RequestCodes.Add_Child_RequestCode);
                        replaceFragment(addChildFragment, true, false, true, addChildFragment.getTag());
                    }


                } else {
                    btnAddChild.setEnabled(false);
                    btnAddChild.setAlpha(0.5f);
                    Dialogs.showDialog(getString(R.string.unalble_to_add_more_child_in_this_cluster), getString(R.string.app_name),
                            getActivity(), true, false, getString(R.string.ok), "", false);
                }
                break;
            case R.id.btnSubmit:
                if(isValidForm())
                {
                    btnSubmit.setEnabled(false);
                    btnSubmit.setEnabled(true);
                    String url = Globals.APIs.BASE_URL + Globals.APIs.save;
//                    long count = mDbManager.saveRecordsOffline(createJsonObject(childInfoObjects).toString(), url);
//                    if (count > -1) {
//                        Dialogs.showPositiveAlert(getActivity(), getResources().getString(R.string.record_inserted_successfully), getResources().getString(R.string.ok), this, 1, getResources().getString(R.string.app_name));
//                    }
                                    getFragmentManager().popBackStack();
                    break;
                }
            default:
                super.onClick(view);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Globals.RequestCodes.Add_Child_RequestCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                final ChildObject childObject = data.getParcelableExtra(Globals.Arguments.CHILD_INFO);
                final Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {


                        try {
                            ArrayList<ChildObject> childInfoObjectssave = new ArrayList<>();
                            childInfoObjectssave.add(childObject);
                            String url = Globals.APIs.BASE_URL + Globals.APIs.save;
                            long count = mDbManager.saveRecordsOffline(createJsonObject(childInfoObjectssave).toString(), url);
                            if (count > -1) {
                                Dialogs.showPositiveAlert(getActivity(), getResources().getString(R.string.record_inserted_successfully), getResources().getString(R.string.ok), HouseInfoFragment.this, 1, getResources().getString(R.string.app_name));
                            }

//                            ArrayList<UnSentRecordsObject> clusterOneList = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID, MasterRecordFragment.locationCode,"1");
//                            String clusterOneSize= clusterOneList.size()+"";
//                            CurrentClusterCount= clusterOneList.size();
                            callAdapter(childObject);

                            initializeData();

//                            clusterCount.setText(clusterOneSize);
                        }catch (Exception exception){

                        }

                    }
                };
                handler.postDelayed(r, 500);

            }
        }
    }

    @Override
    public void onViewClick(View view, int position) {

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
                            Dialogs.showPositiveAlert(getActivity(), responseObject.getMessage(), getString(R.string.ok), this, 2, getString(R.string.app_name));
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

    private void callAdapter(ChildObject childObject) {
        int size = childInfoObjects.size();
        if (CurrentClusterCount <=10) {
//            if ((CurrentClusterCount + childInfoObjects.size()) < 10) {


//                if (childInfoObjects.size() <= 10) {

            childInfoObjects.add(childObject);
            AddChildAdapter addChildAdapter = new AddChildAdapter(getActivity(), childInfoObjects, this);
            rvAddChild.setAdapter(addChildAdapter);


//                }
        }
             else {
                Dialogs.showDialog(getString(R.string.unalble_to_add_more_child_in_this_cluster), getString(R.string.app_name),
                        getActivity(), true, false, getString(R.string.ok), "", false);
                AddChildAdapter addChildAdapter = new AddChildAdapter(getActivity(), childInfoObjects, this);
                rvAddChild.setAdapter(addChildAdapter);
            }

    }

    private JSONObject createJsonObject(ArrayList<ChildObject> childInfoObjects) {
        Calendar calendar = Calendar.getInstance();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonHouseInfo = new JSONObject();

        for (ChildObject childObject : childInfoObjects) {
            try {
                JSONObject jsonChildInfo = new JSONObject();
                jsonChildInfo.put(Globals.jsonKeys.CHILD_NAME, childObject.getChildName());
                jsonChildInfo.put(Globals.jsonKeys.FATHER_NAME, childObject.getFatherName());
                jsonChildInfo.put(Globals.jsonKeys.AGE, childObject.getAge());
                jsonChildInfo.put(Globals.jsonKeys.REMARKS, childObject.getRemarks());
                jsonChildInfo.put(Globals.jsonKeys.VACCINATED, childObject.getVaccinated());
                jsonChildInfo.put(Globals.jsonKeys.CARD_AVAILABLE, childObject.getCardAvailable());
                jsonChildInfo.put(Globals.jsonKeys.CARD_NO, childObject.getCardNo());
                jsonChildInfo.put(Globals.jsonKeys.MOBILE, childObject.getMobileNo());
                jsonChildInfo.put(Globals.jsonKeys.HouseNo, childObject.getHouse_no());
                jsonChildInfo.put(Globals.jsonKeys.Front, childObject.getFront());
                jsonChildInfo.put(Globals.jsonKeys.Back, childObject.getBack());
                jsonChildInfo.put(Globals.jsonKeys.Latitude, childObject.getLatitude());
                jsonChildInfo.put(Globals.jsonKeys.Longitude, childObject.getLongitude());

                /////////////////////////////////////////////////////////////////////////////////////////
                jsonChildInfo.put(Globals.jsonKeys.ZERO_TO_FORTY_DAYS, createJsonObjectForZeroToFortyDays(childObject));
                jsonChildInfo.put(Globals.jsonKeys.SIX_TO_TEN_WEEKS, createJsonObjectSixToTenWeeks(childObject));
                jsonChildInfo.put(Globals.jsonKeys.TEN_TO_FOURTEEN_WEEKS, createJsonObjectTenToFourteenWeeks(childObject));
                jsonChildInfo.put(Globals.jsonKeys.FOURTEEN_TO_EIGHTEEN_WEEKS, createJsonObjectFourteenToEighteenWeeks(childObject));
                jsonChildInfo.put(Globals.jsonKeys.NINE_TO_TEN_MONTHS, createJsonObjectNineToTenMonths(childObject));
                jsonChildInfo.put(Globals.jsonKeys.FIFTEEN_TO_SIXTEEN_MONTHS, createJsonObjectFifteenToSixteenMonths(childObject));
                jsonChildInfo.put(Globals.jsonKeys.Eighteen_Months, createeighteen_months(childObject));


                jsonArray.put(jsonChildInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        try {

            AppController.area = areaname.getText().toString();
            AppController.hrmp = Settlement.getSelectedItem().toString();

            jsonHouseInfo.put(Globals.jsonKeys.INSPECTION_DATE, Utils.getDate(calendar.getTimeInMillis(), Globals.WEB_DATE_FORMAT1));
            jsonHouseInfo.put(Globals.jsonKeys.NO_OF_CHILDREN, childInfoObjects.size());
            jsonHouseInfo.put(Globals.jsonKeys.LocationCode, MasterRecordFragment.locationCode);
            Globals.Count = childInfoObjects.size() + "";
            Globals.Location = MasterRecordFragment.locationCode + "";
            jsonHouseInfo.put(Globals.jsonKeys.LAT, AppController.location.getLatitude());
            jsonHouseInfo.put(Globals.jsonKeys.LNG, AppController.location.getLongitude());
            jsonHouseInfo.put(Globals.jsonKeys.ProvinceId, AppController.ProvinceID);
            jsonHouseInfo.put(Globals.jsonKeys.Province, new SharedPref(getActivity()).GetProvinceId());
            jsonHouseInfo.put(Globals.jsonKeys.Settlement, Settlement.getSelectedItem().toString());
            jsonHouseInfo.put(Globals.jsonKeys.User_ID, new SharedPref(getActivity()).GetserverID());
            jsonHouseInfo.put(Globals.jsonKeys.AREA_NO, areaname.getText().toString());
            jsonHouseInfo.put(Globals.jsonKeys.SIA_No, Utils.getSiaTeamNo(getActivity()));
            jsonHouseInfo.put(Globals.jsonKeys.DAY, Utils.getSelectDay(getActivity()));
            jsonHouseInfo.put(Globals.jsonKeys.CREATED_DATE, Utils.getDate(calendar.getTimeInMillis(), Globals.WEB_DATE_FORMAT1));
            jsonHouseInfo.put(Globals.jsonKeys.MODIFIED_DATE, Utils.getDate(calendar.getTimeInMillis(), Globals.WEB_DATE_FORMAT1));
            jsonHouseInfo.put(Globals.jsonKeys.APP_VERSION, BuildConfig.VERSION_NAME);
            jsonHouseInfo.put(Globals.jsonKeys.CHILD_INFO, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonHouseInfo.toString();
        return jsonHouseInfo;

    }

    private JSONObject createJsonObjectForZeroToFortyDays(ChildObject childObject) {
        JSONObject jsonZeroToFourteenDays = new JSONObject();
        try {
            jsonZeroToFourteenDays.put(Globals.jsonKeys.OPV_VACCINATED, childObject.getZeroToFortyDaysObject().getOpvVaccinated());
            jsonZeroToFourteenDays.put(Globals.jsonKeys.BCG_VACCINATED, childObject.getZeroToFortyDaysObject().getBcgVaccinated());
            jsonZeroToFourteenDays.put(Globals.jsonKeys.HEPB_VACCINATED, childObject.getZeroToFortyDaysObject().getHepbVaccinated());
            jsonZeroToFourteenDays.put(Globals.jsonKeys.OPV_VACCINATION_DATE, childObject.getZeroToFortyDaysObject().getOpvVaccinationDate());
            jsonZeroToFourteenDays.put(Globals.jsonKeys.BCG_VACCINATION_DATE, childObject.getZeroToFortyDaysObject().getBcgVaccinationDate());
            jsonZeroToFourteenDays.put(Globals.jsonKeys.REMARKS, childObject.getZeroToFortyDaysObject().getRemarks());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonZeroToFourteenDays;
    }

    private JSONObject createJsonObjectSixToTenWeeks(ChildObject childObject) {
        JSONObject jsonSixToTenWeeks = new JSONObject();
        try {
            jsonSixToTenWeeks.put(Globals.jsonKeys.PENTA_ONE_VACCINATED, childObject.getSixToTenWeeksObject().getPentaOneVaccinated());
            jsonSixToTenWeeks.put(Globals.jsonKeys.PENTA_ONE_VACCINATION_DATE, childObject.getSixToTenWeeksObject().getPentaOneVaccinatedDate());
            jsonSixToTenWeeks.put(Globals.jsonKeys.PCV_TEN_ONE_VACCINATED, childObject.getSixToTenWeeksObject().getPcvTenOneVaccinated());
            jsonSixToTenWeeks.put(Globals.jsonKeys.PCV_TEN_ONE_VACCINATION_DATE, childObject.getSixToTenWeeksObject().getPcvTenOneVaccinatedDate());
            jsonSixToTenWeeks.put(Globals.jsonKeys.OPV_ONE_VACCINATED, childObject.getSixToTenWeeksObject().getOpVOneVaccinated());
            jsonSixToTenWeeks.put(Globals.jsonKeys.OPV_ONE_VACCINATION_DATE, childObject.getSixToTenWeeksObject().getOpVOneVaccinatedDate());
            jsonSixToTenWeeks.put(Globals.jsonKeys.ROTA_VACCINE_ONE_VACCINATED, childObject.getSixToTenWeeksObject().getRotaVaccineOneVaccinated());
            jsonSixToTenWeeks.put(Globals.jsonKeys.ROTA_ONE_VACCINATION_DATE, childObject.getSixToTenWeeksObject().getRotaVaccineOneVaccinatedDate());
            jsonSixToTenWeeks.put(Globals.jsonKeys.REMARKS, childObject.getSixToTenWeeksObject().getRemark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonSixToTenWeeks;
    }

    private JSONObject createJsonObjectTenToFourteenWeeks(ChildObject childObject) {
        JSONObject jsonObjectTenToFourteenWeeks = new JSONObject();
        try {
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.PENTA_VACCINATED, childObject.getTenToFourteenWeeksObject().getPentaVaccinated());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.PENTA_VACCINATION_DATE, childObject.getTenToFourteenWeeksObject().getPentaDateOfVaccination());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.PCV_TEN_VACCINATED, childObject.getTenToFourteenWeeksObject().getPcv10Vaccinated());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.PCV_TEN_VACCINATION_DATE, childObject.getTenToFourteenWeeksObject().getPcv10DateOfVaccination());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.OPV_VACCINATED, childObject.getTenToFourteenWeeksObject().getOpvVaccinated());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.OPV_DATE_VACCINATION_DATE, childObject.getTenToFourteenWeeksObject().getOpvDateOfVaccination());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.ROTA_VACCINATED, childObject.getTenToFourteenWeeksObject().getRotaVaccinated());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.ROTA_VACCINATION_DATE, childObject.getTenToFourteenWeeksObject().getRotaDateOfVaccinated());
            jsonObjectTenToFourteenWeeks.put(Globals.jsonKeys.REMARKS, childObject.getTenToFourteenWeeksObject().getRemarks());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjectTenToFourteenWeeks;
    }

    private JSONObject createJsonObjectFourteenToEighteenWeeks(ChildObject childObject) {
        JSONObject jsonFourteenToEighteenWeeks = new JSONObject();
        try {
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.PENTA_VACCINATED, childObject.getFourteenToEighteenWeeksObject().getPentaVaccinated());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.PENTA_VACCINATION_DATE, childObject.getFourteenToEighteenWeeksObject().getPentaVaccinationDate());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.PCV_TEN_VACCINATED, childObject.getFourteenToEighteenWeeksObject().getPcv10Vaccinated());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.PCV_TEN_VACCINATION_DATE, childObject.getFourteenToEighteenWeeksObject().getPcv10VaccinationDate());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.OPV_VACCINATED, childObject.getFourteenToEighteenWeeksObject().getOpvVaccinated());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.OPV_VACCINATION_DATE, childObject.getFourteenToEighteenWeeksObject().getOpvVaccinationDate());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.IPV_VACCINATED, childObject.getFourteenToEighteenWeeksObject().getIpvVaccinated());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.IPV_VACCINATION_DATE, childObject.getFourteenToEighteenWeeksObject().getIpvVaccinationDate());
            jsonFourteenToEighteenWeeks.put(Globals.jsonKeys.REMARKS, childObject.getFourteenToEighteenWeeksObject().getRemarks());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonFourteenToEighteenWeeks;
    }

    private JSONObject createJsonObjectNineToTenMonths(ChildObject childObject) {
        JSONObject jsonNineToTenMonths = new JSONObject();
        try {
            jsonNineToTenMonths.put(Globals.jsonKeys.MEASLES_ONE_VACCINATED, childObject.getNineToTenMonthsObjects().getMeaslesOneVaccinated());
            jsonNineToTenMonths.put(Globals.jsonKeys.ipv2_VACCINATED, childObject.getNineToTenMonthsObjects().getIPV2Vaccinated());
            jsonNineToTenMonths.put(Globals.jsonKeys.tcv_VACCINATED, childObject.getNineToTenMonthsObjects().getTCVVaccinated());
            jsonNineToTenMonths.put(Globals.jsonKeys.MEASLES_VACCINATION_DATE, childObject.getNineToTenMonthsObjects().getMeaslesDateOfVaccination());
            jsonNineToTenMonths.put(Globals.jsonKeys.REMARKS, childObject.getNineToTenMonthsObjects().getRemarks());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonNineToTenMonths;
    }

    private JSONObject createJsonObjectFifteenToSixteenMonths(ChildObject childObject) {
        JSONObject jsonFifteenToSixteenMonths = new JSONObject();
        try {
            jsonFifteenToSixteenMonths.put(Globals.jsonKeys.MEASLES_TWO_VACCINATED, childObject.getFifteenToSixteenMonthsObject().getMeaslesTwoVaccinated());
            jsonFifteenToSixteenMonths.put(Globals.jsonKeys.MEASLES_TWO_VACCINATION_DATE, childObject.getFifteenToSixteenMonthsObject().getMeasles2DateOfVaccination());
            jsonFifteenToSixteenMonths.put(Globals.jsonKeys.REMARKS, childObject.getFifteenToSixteenMonthsObject().getRemarks());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonFifteenToSixteenMonths;
    }

    private JSONObject createeighteen_months(ChildObject childObject) {
        JSONObject jsonFifteenToSixteenMonths = new JSONObject();
        try {
            jsonFifteenToSixteenMonths.put(Globals.jsonKeys.dpt_vaccinated, childObject.getBoosterDoseObject().getBoosterVaccinated());
            jsonFifteenToSixteenMonths.put(Globals.jsonKeys.remarks, childObject.getBoosterDoseObject().getRemarks());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonFifteenToSixteenMonths;
    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {
        switch (requestCode) {
            case 1:
                clusterCountUpdate();
//                btnSubmit.setEnabled(true);
//                getFragmentManager().popBackStack();
                break;
            case 2:
                btnSubmit.setEnabled(true);
                break;

        }

    }

    private void clusterCountUpdate() {
        int clusterOne = Utils.getClusterOneInfo(getActivity());
        int clusterTwo = Utils.getClusterTwoInfo(getActivity());
        int clusterThree = Utils.getClusterThreeInfo(getActivity());
        if (clusterOne < 10) {
            saveRecord();
            Utils.saveClusterOne(clusterOne + childInfoObjects.size(), getActivity());
        } else if (clusterTwo < 10) {
            saveRecord();
            Utils.saveClusterTwo(clusterTwo + childInfoObjects.size(), getActivity());
        } else if (clusterThree < 10) {
            saveRecord();
            Utils.saveClusterThree(clusterThree + childInfoObjects.size(), getActivity());
        }
    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {

    }

    private void saveRecord() {
        int id = Utils.getClusterId(getActivity());
        mDbManager.insertRecordInClusterTable(id, Globals.APIs.BASE_URL + Globals.APIs.save, createJsonObject(childInfoObjects), getActivity());
    }

    private void showDialog(Context context) {
        mProgress = new TransparentProgressDialog(context);
        mProgress.setCancelable(false);
        mProgress.setOnCancelListener(dialog -> {
            //cancel(false);
        });
        mProgress.show();

    }

    private boolean isValidForm() {
        int valid = 0;
        if (TextUtils.isEmpty(areaname.getText().toString())) {
            setError(areaname, getString(R.string.please_enter_house_no));
            valid = 1;
        }
        if (Settlement.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(),"Please select settlement",Toast.LENGTH_LONG).show();
            valid = 1;
        }
        return valid == 0;
    }

}

