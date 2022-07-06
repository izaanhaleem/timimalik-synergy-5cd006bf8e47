package com.hisdu.SESCluster.fragments.navigation;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.base.BaseFragment;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.fragments.support.SpinnerFragment;
import com.hisdu.SESCluster.objects.new_objcts.DistrictsObject;
import com.hisdu.SESCluster.objects.new_objcts.ResponseObject;
import com.hisdu.SESCluster.objects.support.SpinnerObject;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.SortBy;
import com.hisdu.SESCluster.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;



public class FragmentSelection extends BaseFragment implements BaseFragment.OnViewClickListener {
    Button btnSelectDay, btnNext,deviceInfo;
    int selectDay = -1, distrctId = -1, tehsilId = -1, ucId = -1, divisionId = -1,
            ucIdWithPos = -1, divisionalIdWithPos = -1, districtIdWithPos = -1, tehsilIdIWithPos;
    EditText etDistrict, etTehsilTown, etUc, etArea, etTeamNo, etProvince, etDivision;
    LinearLayout llMainLayout;
    ArrayList<SpinnerObject> ucInfoData = new ArrayList<>();
    ArrayList<SpinnerObject> divisionalInfoData = new ArrayList<>();
    ArrayList<SpinnerObject> districtInfoData = new ArrayList<>();
    ArrayList<SpinnerObject> tehsilInfoData = new ArrayList<>();
    //    UserMainObject userMainObject;
    ResponseObject responseObject;
   // String siaNo;

    public static FragmentSelection getInstance(Bundle bundle, String title, int icon) {
        FragmentSelection fragment = new FragmentSelection();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        btnSelectDay = v.findViewById(R.id.btnSelectDay);
        btnNext = v.findViewById(R.id.btnNext);
        etDistrict = v.findViewById(R.id.etDistrict);
        etTehsilTown = v.findViewById(R.id.etTehsilTown);
        etUc = v.findViewById(R.id.etUc);
        etArea = v.findViewById(R.id.etArea);
        etTeamNo = v.findViewById(R.id.etTeamNo);
        etProvince = v.findViewById(R.id.etProvince);
        etDivision = v.findViewById(R.id.etDivision);
        deviceInfo = v.findViewById(R.id.device_info);
      //  llMainLayout = v.findViewById(R.id.llMainLayout);
       // showSnackBar(llMainLayout, getResources().getString(R.string.please_set_your_date_and_time), Snackbar.LENGTH_INDEFINITE);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_selection_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        btnSelectDay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        etTehsilTown.setOnClickListener(this);
        etUc.setOnClickListener(this);
        etDivision.setOnClickListener(this);
        etProvince.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        deviceInfo.setOnClickListener(this);

    }

    @Override
    protected void initializeData() {
//        String response = Utils.getUserProfileInfo(getActivity());
//        responseObject = new Gson().fromJson(response, ResponseObject.class);
//        //Provincial type user
//        if (responseObject.getRoleID() != null &&
//                responseObject.getRoleID().length() > 0 &&
//                responseObject.getRoleID().equalsIgnoreCase(getString(R.string.provisional_type_user_id))) {
//            etProvince.setText(responseObject.getProvinceName());
//            etProvince.setEnabled(false);
//
//            divisionalInfoData = getDivisionalInfo(responseObject);
//            divisionalInfoData = sortData(divisionalInfoData);
//
//        }
//        //divisional type user
//        else if (responseObject.getRoleID() != null &&
//                responseObject.getRoleID().length() > 0 &&
//                responseObject.getRoleID().equalsIgnoreCase(getString(R.string.divisional_type_user_id))) {
//            etProvince.setEnabled(false);
//            etProvince.setText(responseObject.getProvinceName());
//            divisionalInfoData = getDivisionalInfo(responseObject);
//            divisionalInfoData = sortData(divisionalInfoData);
//
//        } // district type user
//        else if (responseObject.getRoleID() != null &&
//                responseObject.getRoleID().length() > 0 &&
//                responseObject.getRoleID().equalsIgnoreCase(getString(R.string.district_type_user_id))) {
//            etDivision.setEnabled(false);
//            etProvince.setText(responseObject.getProvinceName());
//            etDivision.setText(responseObject.getDivisions().get(0).getDivisionName());
//            int t =  responseObject.getDivisions().get(0).getDivisionID();
//            divisionId = responseObject.getDivisions().get(0).getDivisionID();
//            districtInfoData = getDistrictInfo(responseObject);
//            districtInfoData = sortData(districtInfoData);
//
//
//        } else {
//            Dialogs.showDialog(getString(R.string.please_assign_role_to_proceed), getString(R.string.app_name),
//                    getActivity(), true, false, getString(R.string.ok), "", true);
//        }
//        ucInfoData = getUCData(userMainObject);
//        etDistrict.setText(userMainObject.getUsers().get(0).getDistrictName());
//        etTehsilTown.setText(userMainObject.getUsers().get(0).getTownName());
        if (Utils.getSiaTeamNo(getActivity()) != null && Utils.getSiaTeamNo(getActivity()).length() > 0) {
            etTeamNo.setText(Utils.getSiaTeamNo(getActivity()));
//            etTeamNo.setEnabled(false);
//
        }
//        if (Utils.getUC(getActivity()) != null && Utils.getUC(getActivity()).length() > 0) {
//            etUc.setText(Utils.getUC(getActivity()));
//            etUc.setEnabled(false);
//        }
        if (Utils.getDivision(getActivity()) != null && Utils.getDivision(getActivity()).length() > 0) {
            etDivision.setText(Utils.getDivision(getActivity()));
            divisionId = Integer.parseInt(Utils.getDivisionID(getActivity()));
            etDivision.setEnabled(false);
        }
        if (Utils.getDistrict(getActivity()) != null && Utils.getDistrict(getActivity()).length() > 0) {
            etDistrict.setText(Utils.getDistrict(getActivity()));
            etDistrict.setEnabled(false);
            distrctId = Integer.parseInt(Utils.getDistrictID(getActivity()));
        }
        if (Utils.getTown(getActivity()) != null && Utils.getTown(getActivity()).length() > 0) {
            etTehsilTown.setText(Utils.getTown(getActivity()));
            etTehsilTown.setEnabled(false);
            tehsilId = Integer.parseInt(Utils.getTownID(getActivity()));
        }
        if (Utils.getUC(getActivity()) != null && Utils.getUC(getActivity()).length() > 0) {
            etUc.setText(Utils.getUC(getActivity()));
            etUc.setEnabled(false);
            ucId = Integer.parseInt(Utils.getUCID(getActivity()));
        }
        if (Utils.getArea(getActivity()) != null && Utils.getArea(getActivity()).length() > 0) {
            etArea.setText(Utils.getArea(getActivity()));
//            etArea.setEnabled(false);
        }
        if (Utils.getSelectDay(getActivity()) > -1) {
            selectDay = Utils.getSelectDay(getActivity());
//            btnSelectDay.setEnabled(false);
//            btnSelectDay.setAlpha(0.5f);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.demo_info));
        showToolbarSync();
        updateCount();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectDay:
                SpinnerFragment compliantTypeSpinnerFragment = new SpinnerFragment(getActivity(),
                        getResources().getString(R.string.select_day), "", getString(R.string.ok),
                        getString(R.string.cancel), true, getListData(Arrays.asList(getResources().getStringArray(R.array.select_days))), new SpinnerFragment.onSpinnerClick() {
                    @Override
                    public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                      //  selectDay = spinnerObject.getID();
                        selectDay = Integer.parseInt(spinnerObject.getTitle());
                    }

                    @Override
                    public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
                    }
                });
                compliantTypeSpinnerFragment.setSelectedItemPosition((selectDay-1));
                compliantTypeSpinnerFragment.show(getFragmentManager(), "");
//
                break;
            case R.id.btnNext:
                if (validateForm()) {
                    if (selectDay > -1) {
                        area = etArea.getText().toString();
                        siaNo = etTeamNo.getText().toString();
                        Utils.saveArea(etArea.getText().toString(), getActivity());
                        Utils.saveSiaTeamNo(etTeamNo.getText().toString(), getActivity());
                        Utils.saveSelectDay(selectDay, getActivity());
                        Utils.saveDivision(etDivision.getText().toString(), getActivity());
                        Utils.saveDivisionID(String.valueOf(divisionId), getActivity());
                        Utils.saveDistrict(etDistrict.getText().toString(), getActivity());
                        Utils.saveDistrictID(String.valueOf(distrctId), getActivity());
                        Utils.saveTown(etTehsilTown.getText().toString(), getActivity());
                        Utils.saveTownID(String.valueOf(tehsilId), getActivity());
                        Utils.saveUC(etUc.getText().toString(), getActivity());
                        Utils.saveUCID(String.valueOf(ucId), getActivity());
                        FragmentHome fragmentHome = FragmentHome.getInstance(new Bundle(), "", -1);
                        replaceFragment(fragmentHome, true, false, true, fragmentHome.getTag());
                    } else
                        Dialogs.showDialog(getString(R.string.please_select_days), getString(R.string.app_name),
                                getActivity(), true, false, getString(R.string.ok), "", false);
                }
                break;
            case R.id.etUc:
                SpinnerFragment ucFragment = new SpinnerFragment(getActivity(),
                        getResources().getString(R.string.select_uc), "", getString(R.string.ok),
                        getString(R.string.cancel), true, ucInfoData, new SpinnerFragment.onSpinnerClick() {
                    @Override
                    public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                        ucId = spinnerObject.getID();
                        ucIdWithPos = Integer.parseInt(spinnerObject.getTitleAr());
                        etUc.setText(spinnerObject.getTitle());
                        etUc.setError(null);

                    }

                    @Override
                    public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
                    }
                });
                ucFragment.setSelectedItemPosition(ucIdWithPos);
                ucFragment.show(getFragmentManager(), "");
                break;
            case R.id.etDivision:
                SpinnerFragment divisionalFragment = new SpinnerFragment(getActivity(),
                        getResources().getString(R.string.selet_division), "", getString(R.string.ok),
                        getString(R.string.cancel), true, divisionalInfoData, new SpinnerFragment.onSpinnerClick() {
                    @Override
                    public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                        divisionId = spinnerObject.getID();
                        divisionalIdWithPos = Integer.parseInt(spinnerObject.getTitleAr());
                        etDivision.setText(spinnerObject.getTitle());
                        districtInfoData = getDistrictInfoByDivision(divisionId);
                        districtInfoData = sortData(districtInfoData);
                        etDivision.setError(null);


                    }

                    @Override
                    public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
                    }
                });
                divisionalFragment.setSelectedItemPosition(divisionalIdWithPos);
                divisionalFragment.show(getFragmentManager(), "");
                break;
            case R.id.etDistrict:
                SpinnerFragment districtFragment = new SpinnerFragment(getActivity(),
                        getResources().getString(R.string.select_district), "", getString(R.string.ok),
                        getString(R.string.cancel), true, districtInfoData, new SpinnerFragment.onSpinnerClick() {
                    @Override
                    public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                        distrctId = spinnerObject.getID();
                        districtIdWithPos = Integer.parseInt(spinnerObject.getTitleAr());
                        etDistrict.setText(spinnerObject.getTitle());
                        tehsilInfoData = getTehsilInfoByDistrict(distrctId);
                        tehsilInfoData = sortData(tehsilInfoData);
                        etDistrict.setError(null);


                    }

                    @Override
                    public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
                    }
                });
                districtFragment.setSelectedItemPosition(districtIdWithPos);
                districtFragment.show(getFragmentManager(), "");
                break;
            case R.id.etTehsilTown:
                SpinnerFragment tehsilTownFragment = new SpinnerFragment(getActivity(),
                        getResources().getString(R.string.select_tehsil_town), "", getString(R.string.ok),
                        getString(R.string.cancel), true, tehsilInfoData, new SpinnerFragment.onSpinnerClick() {
                    @Override
                    public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                        tehsilId = spinnerObject.getID();
                        tehsilIdIWithPos = Integer.parseInt(spinnerObject.getTitleAr());
                        etTehsilTown.setText(spinnerObject.getTitle());
                        ucInfoData = getUCByTehsil(tehsilId);
                        ucInfoData = sortData(ucInfoData);
                        etTehsilTown.setError(null);
                    }

                    @Override
                    public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
                    }
                });
                tehsilTownFragment.setSelectedItemPosition(tehsilIdIWithPos);
                tehsilTownFragment.show(getFragmentManager(), "");
                break;

            case R.id.device_info:
//                String imei = SplashActivity.getIMEIInformation(getActivity());
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Device Information")
//                        .setMessage(imei)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
//                Log.d("test", imei);

                break;

            default:
                super.onClick(view);
                break;
        }

    }


    @Override
    public void onViewClick(String tag, int position) {
        if (tag.equalsIgnoreCase(getString(R.string.district)))
            distrctId = position;
        else if (tag.equalsIgnoreCase(getString(R.string.tehsil_town)))
            tehsilId = position;
        else if (tag.equalsIgnoreCase(getString(R.string.uc))) {
            ucId = position;
            Globals.uc = ucId;
        }

    }

    private boolean validateForm() {
        int valid = 0;
        if (TextUtils.isEmpty(etArea.getText().toString())) {
            valid = 1;
            setError(etArea, getString(R.string.please_enter_area));
        }
        if (TextUtils.isEmpty(etTeamNo.getText().toString())) {
            valid = 1;
            setError(etTeamNo, getString(R.string.please_enter_team_no));
        }
        if (TextUtils.isEmpty(etDivision.getText().toString())) {
            valid = 1;
            setError(etDivision, getString(R.string.please_enteR_division));
        }
        if (TextUtils.isEmpty(etDistrict.getText().toString())) {
            valid = 1;
            setError(etDistrict, getString(R.string.please_enter_district));
        }
        if (TextUtils.isEmpty(etTehsilTown.getText().toString())) {
            valid = 1;
            setError(etTehsilTown, getString(R.string.please_enter_town));
        }
        if (TextUtils.isEmpty(etUc.getText().toString())) {
            valid = 1;
            setError(etUc, getString(R.string.please_enter_team_no));
        }
        return valid == 0;

    }

    //    private ArrayList<SpinnerObject> getUCData(UserMainObject userMainObject) {
//        ArrayList<SpinnerObject> ucInfo = new ArrayList<>();
//        for (int i = 0; i < userMainObject.getDistricts().size(); i++) {
//            SpinnerObject spinnerObject = new SpinnerObject();
//            spinnerObject.setID(userMainObject.getDistricts().get(i).getUCID());
//            spinnerObject.setTitle(userMainObject.getDistricts().get(i).getUCName());
//            spinnerObject.setTitleAr(Integer.toString(i));
//            ucInfo.add(spinnerObject);
//        }
//        return ucInfo;
//    }

    private ArrayList<SpinnerObject> getDivisionalInfo(ResponseObject responseObject) {
        ArrayList<SpinnerObject> divisionalInfo = new ArrayList<>();
        for (int i = 0; i < responseObject.getDivisions().size(); i++) {
            SpinnerObject spinnerObject = new SpinnerObject();
            spinnerObject.setID(responseObject.getDivisions().get(i).getDivisionID());
            spinnerObject.setTitle(responseObject.getDivisions().get(i).getDivisionName());
            spinnerObject.setTitleAr(Integer.toString(i));
            divisionalInfo.add(spinnerObject);

        }
        return divisionalInfo;
    }

    private ArrayList<SpinnerObject> getDistrictInfo(ResponseObject responseObject) {
        ArrayList<SpinnerObject> divisionalInfo = new ArrayList<>();
        ArrayList<DistrictsObject> districtsObjects = new ArrayList<>();
        districtsObjects = responseObject.getDivisions().get(0).getDistricts();
        for (int i = 0; i < districtsObjects.size(); i++) {
            SpinnerObject spinnerObject = new SpinnerObject();
            spinnerObject.setID(districtsObjects.get(i).getId());
            spinnerObject.setTitle(districtsObjects.get(i).getDistrict_name());
            spinnerObject.setTitleAr(Integer.toString(i));
            divisionalInfo.add(spinnerObject);
        }
        return divisionalInfo;
    }

    //
////    private ArrayList<SpinnerObject> getDistrictInfo(UserMainObject userMainObject) {
////        ArrayList<SpinnerObject> divisionalInfo = new ArrayList<>();
////        for (int i = 0; i < userMainObject.getUsers().getProvince().getDivisions().size(); i++) {
////            SpinnerObject spinnerObject = new SpinnerObject();
////            spinnerObject.setID(userMainObject.getUsers().getProvince().getDivisions().get(i).getDivisionID());
////            spinnerObject.setTitle(userMainObject.getUsers().getProvince().getDivisions().get(i).getDivisionName());
////            spinnerObject.setTitleAr(Integer.toString(i));
////            divisionalInfo.add(spinnerObject);
////
////        }
////        return divisionalInfo;
////    }
//
    private ArrayList<SpinnerObject> getDistrictInfoByDivision(int id) {
        ArrayList<SpinnerObject> districtInfo = new ArrayList<>();

        for (int i = 0; i < responseObject.getDivisions().size(); i++) {
//            for (int j = i; j < userMainObject.getUsers().getProvince().getDivisions().get(i).getDistricts().size(); j++)
            if (responseObject.getDivisions().get(i).getDivisionID() == id) {
                for (int j = 0; j < responseObject.getDivisions().get(i).getDistricts().size(); j++) {
                    SpinnerObject spinnerObject = new SpinnerObject();
                    spinnerObject.setID(responseObject.getDivisions().get(i).getDistricts().get(j).getId());
                    spinnerObject.setTitle(responseObject.getDivisions().get(i).getDistricts().get(j).getDistrict_name());
                    spinnerObject.setTitleAr(Integer.toString(j));
                    districtInfo.add(spinnerObject);
                }
            }

        }
        return districtInfo;
    }

    //
    private ArrayList<SpinnerObject> getTehsilInfoByDistrict(int id) {
        ArrayList<SpinnerObject> districtInfo = new ArrayList<>();
        for (int i = 0; i < responseObject.getDivisions().size(); i++) {
            for (int j = 0; j < responseObject.getDivisions().get(i).getDistricts().size(); j++) {
                Log.d("Id_district", responseObject.getDivisions().get(i).getDistricts().get(j).getId() + "");
                if (responseObject.getDivisions().get(i).getDistricts().get(j).getId() == id) {
                    for (int k = 0; k < responseObject.getDivisions().get(i).getDistricts().get(j).getTowns().size(); k++) {
                        SpinnerObject spinnerObject = new SpinnerObject();
                        spinnerObject.setID(responseObject.getDivisions().get(i).getDistricts().get(j).getTowns().get(k).getTownID());
                        spinnerObject.setTitle(responseObject.getDivisions().get(i).getDistricts().get(j).getTowns().get(k).getTownName());
                        spinnerObject.setTitleAr(Integer.toString(k));
                        districtInfo.add(spinnerObject);
                    }
                }
            }

        }
        return districtInfo;
    }

    //
    private ArrayList<SpinnerObject> getUCByTehsil(int id) {
        ArrayList<SpinnerObject> districtInfo = new ArrayList<>();
        for (int i = 0; i < responseObject.getDivisions().size(); i++) {
            for (int j = 0; j < responseObject.getDivisions().get(i).getDistricts().size(); j++) {
                for (int k = 0; k < responseObject.getDivisions().get(i).getDistricts().get(j).
                        getTowns().size(); k++) {
                    if (responseObject.getDivisions().get(i).getDistricts().get(j)
                            .getTowns().get(k).getTownID() == id) {
                        for (int l = 0; l < responseObject.getDivisions().get(i).
                                getDistricts().get(j).getTowns().get(k).getUCs().size(); l++) {
                            SpinnerObject spinnerObject = new SpinnerObject();
                            spinnerObject.setID(responseObject.getDivisions().get(i).getDistricts().get(j).getTowns().get(k).getUCs().get(l).getUCID());
                            spinnerObject.setTitle(responseObject.getDivisions().get(i).getDistricts().get(j).getTowns().get(k).getUCs().get(l).getUCName());
                            spinnerObject.setTitleAr(Integer.toString(l));
                            districtInfo.add(spinnerObject);
                        }
                    }
                }

            }
        }
        return districtInfo;

    }

    private ArrayList<SpinnerObject> sortData(ArrayList<SpinnerObject> info) {
        SortBy comparator = new SortBy();
        Collections.sort(info, comparator);
        return info;
    }

}
