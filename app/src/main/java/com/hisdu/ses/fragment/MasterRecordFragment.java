package com.hisdu.ses.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.fragments.navigation.FragmentHome;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.House;
import com.hisdu.ses.Database.HouseDetail;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.epiCenters.EpiCenter;
import com.hisdu.ses.Models.provinces.Province;
import com.hisdu.ses.Models.storeTypes.Store;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.recordAdapter;
import com.iceteck.silicompressorr.SiliCompressor;
import com.hisdu.ses.utils.CustomSearchableSpinner;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import static android.app.Activity.RESULT_OK;

public class MasterRecordFragment extends Fragment {

    CustomSearchableSpinner province, division, district, tehsil, uc, hf, checklist, storeType, epiCenterSpinner, selectDay,outreach_session_spinner;

    LinearLayout provinceLayout, districtLayout, tehsilLayout, UcLayout, hfLayout, monitorLayout, storeTypeLayout, divisionLayout, epiCentersLayout, clusterLayout;

    String userRole = null, provinceValue = null, districtValue = null, divisionValue = null, tehsilValue = null, storeValue = null, ucValue = null, hfValue = null, hfValueID = null, vaccinatorValue = null, LhvValue = null,
            AsvValue = null, ImageBase64 = null, checklistValue = null, createdBy = null, unionCouncellValue = null, IMEI = null, epiCenter = null, ImageBase641 = "", ImageBase642 = "",
            ImageBase643 = "", ImageBase644 = "", ImageBase645 = "", sessionsiteValue = null, Reason=null, Comments=null;
    public static String locationCode = null;

    EditText Lhv, Asv, vaccinator, otherEpiCenter,sessionsite,reason_outreach,reamrks_outreach;
    TextInputEditText storeName, siaTeamNo;
    TextInputLayout otherEpiLayout,reason_outreach_layout,remarks_outreach_layout;

    Button proceed;
    FragmentManager fragmentManager;
     boolean OutReachSessionConducted;
    ImageView ImagePreview;

    ImageButton AttachImage;
    String mCurrentPhotoPath, day = null, outreach_value= null;
    private Uri ImageUri;
    TextView registerText;
    View view;
    List<Location> DivisionList;
    List<Location> DistrictList;
    List<Location> TehsilList;
    List<Province> provinceList;
    List<UcData> healthFacilitiesList;
    List<Location> UcList;
    List<Store> StoreList;

    List<EpiCenter> epiCenterList;

    private static final int TYPE_IMAGE = 1;
    private static final int REQUEST_TAKE_CAMERA_PHOTO = 1;
    Uri capturedUri = null;
    SharedPref sharedPref;
    Integer LocationValue = 0;
    // class variables
    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    String type;
    String[] daysArray = new String[]{"Select Days", "1", "2", "3"};

    String[] outreachoptions = new String[]{"Outreach session conducted today in this UC?", "Yes", "No"};
    protected LocalDatabaseManager mDbManager;

    private RecyclerView RV;
    private recordAdapter mAdapter;
    TextView title,EPID,Count;
    LinearLayout recordLayout;

    List<UnSentRecordsObject> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view==null){
        view = inflater.inflate(R.layout.master_info_fragment, container, false);

            reason_outreach= view.findViewById(R.id.reason_outreach);
            reamrks_outreach= view.findViewById(R.id.reamrks_outreach);
            outreach_session_spinner= view.findViewById(R.id.outreach_session_spinner);
            reason_outreach_layout= view.findViewById(R.id.reason_outreach_layout);
            remarks_outreach_layout= view.findViewById(R.id.remarks_outreach_layout);
        district = view.findViewById(R.id.sid);
        tehsil = view.findViewById(R.id.tehsil);
        uc = view.findViewById(R.id.uc);
        hf = view.findViewById(R.id.hf);
        proceed = view.findViewById(R.id.proceed);
        vaccinator = view.findViewById(R.id.vaccinator);
        Lhv = view.findViewById(R.id.Lhv);
        Asv = view.findViewById(R.id.Asv);
        checklist = view.findViewById(R.id.checklist);
        AttachImage = view.findViewById(R.id.AttachImage);
        ImagePreview = view.findViewById(R.id.ImagePreview);
        districtLayout = view.findViewById(R.id.SidLayout);
        tehsilLayout = view.findViewById(R.id.tehsilLayout);
        hfLayout = view.findViewById(R.id.hfLayout);
        monitorLayout = view.findViewById(R.id.monitorLayout);
        UcLayout = view.findViewById(R.id.UcLayout);
        registerText = view.findViewById(R.id.register_text);
        storeName = view.findViewById(R.id.storeName);
        storeTypeLayout = view.findViewById(R.id.StoreTypeLayout);
        storeType = view.findViewById(R.id.storeType);
        division = view.findViewById(R.id.division);
        divisionLayout = view.findViewById(R.id.divisionLayout);
        province = view.findViewById(R.id.provinces);
        provinceLayout = view.findViewById(R.id.provinceLayout);
        epiCenterSpinner = view.findViewById(R.id.epiCenter);
        epiCentersLayout = view.findViewById(R.id.EpiCenters);
        otherEpiCenter = view.findViewById(R.id.otherEpiCenter);
        otherEpiLayout = view.findViewById(R.id.otherEpiLayout);
        clusterLayout = view.findViewById(R.id.clusterLayoutMain);
        siaTeamNo = view.findViewById(R.id.siaTeamNo);
        selectDay = view.findViewById(R.id.selectDay);
        RV = view.findViewById(R.id.recyclerView);
        title = view.findViewById(R.id.title);
        recordLayout = view.findViewById(R.id.recordLayout);
        EPID = view.findViewById(R.id.EPID);
        Count = view.findViewById(R.id.Count);
            sessionsite = view.findViewById(R.id.sessionsite);
        sharedPref = new SharedPref(getActivity());

        mDbManager = LocalDatabaseManager.getInstance(MainActivity.main);

        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(MainActivity.main));

        if(AppController.appModuleID == 3)
        {
            sessionsite.setVisibility(View.VISIBLE);
            outreachSessionSpinner();
        }

        if(AppController.appModuleID == 4 || AppController.appModuleID == 7 || AppController.appModuleID == 6)

        {
            LoadRecords();
            if(AppController.appModuleID == 4 || AppController.appModuleID == 7)
            {
                EPID.setVisibility(View.GONE);
                Count.setText("children Sampled");
                title.setText("children Incomplete Cluster");
            }

            else

                {
                    Count.setText("House Sample");
                    title.setText("House Incomplete Cluster");
                }
        }

        vaccinator.setText(String.format("%s %s", new SharedPref(getActivity()).GetFirstName(), new SharedPref(getActivity()).GetLastName()));

        TelephonyManager telephonyManager = (TelephonyManager) MainActivity.main.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity.main, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            IMEI = telephonyManager.getDeviceId();
        }

        fragmentManager = getFragmentManager();
        createdBy = new SharedPref(getActivity()).GetserverID();
        userRole = new SharedPref(getActivity()).GetLoggedInRole().toLowerCase();
        LocationValue = new SharedPref(getActivity()).GetLocationID().length();

        if (AppController.appModuleID.toString().equals("1")) {
            getStoreTypes();
            clusterLayout.setVisibility(View.GONE);
        } else if (AppController.appModuleID.toString().equals("2")) {
            clusterLayout.setVisibility(View.GONE);
        } else if (AppController.appModuleID.toString().equals("4")) {
            clusterLayout.setVisibility(View.VISIBLE);
            getDays();
        }
        else {
            epiCentersLayout.setVisibility(View.GONE);
            clusterLayout.setVisibility(View.GONE);
        }

        locationType(new SharedPref(getActivity()).GetLocationID());

        if (!AppController.appModuleID.toString().equals("1")) {
            check("");
        }


        AttachImage.setOnClickListener(v -> {
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator")
                    || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                multiImagePicker();
            } else {
                dispatchTakePictureIntent();
            }
        });

        ImagePreview.setOnClickListener(v -> loadPhoto());

        proceed.setOnClickListener(v -> {

            if (validate()) { submit(); }
        });

        selectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    AppController.day = daysArray[position];
                    day = daysArray[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        storeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (storeType.getSelectedItemPosition() != 0) {
                    provinceValue = null;
                    divisionValue = null;
                    districtValue = null;
                    tehsilValue = null;
                    province.setSelection(0);
                    division.setSelection(0);
                    district.setSelection(0);
                    tehsil.setSelection(0);
                    storeValue = StoreList.get(i - 1).getStoreTypeId().toString();
                    if (storeValue.equals("1")) {
                        provinceLayout.setVisibility(View.GONE);
                        divisionLayout.setVisibility(View.GONE);
                        districtLayout.setVisibility(View.GONE);
                        tehsilLayout.setVisibility(View.GONE);

                    } else if (storeValue.equals("2")) {
                        provinceLayout.setVisibility(View.VISIBLE);
                        divisionLayout.setVisibility(View.GONE);
                        districtLayout.setVisibility(View.GONE);
                        tehsilLayout.setVisibility(View.GONE);
                    } else if (storeValue.equals("3")) {
                        provinceLayout.setVisibility(View.GONE);
                        divisionLayout.setVisibility(View.GONE);
                        districtLayout.setVisibility(View.VISIBLE);
                        tehsilLayout.setVisibility(View.GONE);
                    } else if (storeValue.equals("4")) {
                        provinceLayout.setVisibility(View.GONE);
                        divisionLayout.setVisibility(View.GONE);
                        districtLayout.setVisibility(View.GONE);
                        tehsilLayout.setVisibility(View.VISIBLE);
                        check("");
                    }
                    if (AppController.appModuleID.toString().equals("1")) {
                        check(storeValue);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (province.getSelectedItemPosition() != 0) {
                    provinceValue = provinceList.get(i - 1).getProvinceID().toString();
                    locationCode = provinceValue;
                    if (AppController.appModuleID == 1 && !storeValue.equals("1") && !storeValue.equals("2")) {
                        getDivisionByProvince(provinceList.get(i - 1).getProvinceID());
                    } else if (AppController.appModuleID != 1) {
                        getDivisionByProvince(provinceList.get(i - 1).getProvinceID());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (division.getSelectedItemPosition() != 0) {
                    divisionValue = DivisionList.get(i - 1).getServer_id();
                    locationCode = divisionValue;
                    LocationValue = DivisionList.get(i - 1).getServer_id().length();

                    if (AppController.appModuleID == 1 && !storeValue.equals("1") && !storeValue.equals("2")) {
                        getDistrict();

                    } else if (AppController.appModuleID != 1) {
                        getDistrict();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (district.getSelectedItemPosition() != 0) {
                    districtValue = DistrictList.get(i - 1).getServer_id();
                    locationCode = districtValue;
                    if (AppController.appModuleID == 1 && !storeValue.equals("1") && !storeValue.equals("2") && !storeValue.equals("3")) {
                        getTehsil();
                    } else if (AppController.appModuleID != 1) {
                        getTehsil();

                    }
                } else {
                    tehsilLayout.setVisibility(View.GONE);
                    tehsil.setSelection(0);
                    tehsilValue = null;

                    hfLayout.setVisibility(View.GONE);
                    hf.setSelection(0);
                    hfValue = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (tehsil.getSelectedItemPosition() != 0) {
                    tehsilValue = TehsilList.get(i - 1).getServer_id();
                    locationCode = tehsilValue;
                    if (AppController.appModuleID.toString().equals("1")) {

                    } else {
                        getUcs();

                    }
                } else {

                    hfLayout.setVisibility(View.GONE);
                    hf.setSelection(0);
                    hfValue = null;

                    UcLayout.setVisibility(View.GONE);
                    uc.setSelection(0);
                    unionCouncellValue = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        uc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (uc.getSelectedItemPosition() != 0) {
                    unionCouncellValue = uc.getSelectedItem().toString();
                    ucValue = UcList.get(i - 1).getServer_id();
                    locationCode = ucValue;
//                    getHealthFacilities();

                    if (AppController.appModuleID.toString().equals("2")) {
                        getEpiCenters(ucValue);
                    }
                } else {
                    unionCouncellValue = null;
                    hfLayout.setVisibility(View.GONE);
                    hf.setSelection(0);
                    hfValue = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        hf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (hf.getSelectedItemPosition() != 0) {
                    hfValue = healthFacilitiesList.get(i - 1).getServerID();
                } else {
                    hfValue = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        epiCenterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (epiCenterSpinner.getSelectedItemPosition() != 0) {
                    epiCenter = epiCenterList.get(i - 1).getCenterId().toString();
                    if (epiCenter.equals("0")) {
                        otherEpiLayout.setVisibility(View.VISIBLE);
                    } else {
                        otherEpiLayout.setVisibility(View.GONE);
                    }
                } else {
                    epiCenter = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        checklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (checklist.getSelectedItemPosition() != 0) {
                    checklistValue = checklist.getSelectedItem().toString();
                } else {
                    checklistValue = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        vaccinator.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && vaccinator.isEnabled()) {
                    if (vaccinator.length() != 0) {
                        vaccinatorValue = vaccinator.getText().toString();
                    } else {
                        vaccinatorValue = null;
                    }
                }

            }
        });

        Lhv.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && Lhv.isEnabled()) {
                    if (Lhv.length() != 0) {
                        LhvValue = Lhv.getText().toString();
                    } else {
                        LhvValue = null;
                    }
                }

            }
        });

        sessionsite.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && sessionsite.isEnabled()) {
                    if (sessionsite.length() != 0) {
                        sessionsiteValue = sessionsite.getText().toString();
                    } else {
                        sessionsiteValue = null;
                    }
                }

            }
        });

            reason_outreach.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && reason_outreach.isEnabled()) {
                        if (reason_outreach.length() != 0) {
                            Reason = reason_outreach.getText().toString();
                        } else {
                            Reason = null;
                        }
                    }

                }
            });

            reamrks_outreach.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && reamrks_outreach.isEnabled()) {
                        if (reamrks_outreach.length() != 0) {
                            Comments = reamrks_outreach.getText().toString();
                        } else {
                            Comments = null;
                        }
                    }

                }
            });

        Asv.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && Asv.isEnabled()) {
                    if (Asv.length() != 0) {
                        AsvValue = Asv.getText().toString();
                    } else {
                        AsvValue = null;
                    }
                }

            }
        });

    }
        return view;
    }


    public String locationType(String code) {

        if (code.isEmpty()) {
            type = "super";
        } else if (code.length() < 3) {
            provinceValue = new SharedPref(getActivity()).GetLocationID();
            type = "province";
        } else if (code.length() == 3) {
            divisionValue = new SharedPref(getActivity()).GetLocationID();
            type = "division";
        } else if (code.length() == 6) {
            districtValue = new SharedPref(getActivity()).GetLocationID();
            type = "district";
        } else if (code.length() == 9) {
            tehsilValue = new SharedPref(getActivity()).GetLocationID();
            type = "tehsil";
        } else {
            try {
                tehsilValue = new SharedPref(getActivity()).GetLocationID().substring(0, 9);
                unionCouncellValue = new SharedPref(getActivity()).GetUcName();
                type = "uc";
            } catch (NullPointerException e) {

            }
        }

        return type;
    }

    void submit() {

        if (AppController.appModuleID.toString().equals("4") || AppController.appModuleID.toString().equals("7") )

        {
            if(provinceValue != null){AppController.ProvinceID = provinceValue;}
            Utils.saveArea("test", getActivity());
            Utils.saveSiaTeamNo(siaTeamNo.getText().toString(), getActivity());
            //Utils.saveSelectDay(Integer.parseInt(day), getActivity());
            Utils.saveDivisionID(String.valueOf(divisionValue), getActivity());
            Utils.saveDistrictID(String.valueOf(districtValue), getActivity());
            Utils.saveTownID(String.valueOf(tehsilValue), getActivity());
            Utils.saveUCID(String.valueOf(ucValue), getActivity());
            FragmentHome fragmentHome = FragmentHome.getInstance(new Bundle(), "", -1);
            replaceFragment(fragmentHome, true, false, true, fragmentHome.getTag());
        }

        else

        {
            AppController.MasterID = IndicatorMasterDataSave.getAllins().size() + 1;
            IndicatorMasterDataSave cr = new IndicatorMasterDataSave();
            cr.setLocationCode(locationCode);
            cr.setAppModuleId(AppController.appModuleID);
            cr.setMastID(AppController.MasterID);
            cr.setSync("0");
            if(sessionsiteValue != null){cr.setSessionsiteValue(sessionsiteValue);}
            if(Reason != null){cr.setReason(Reason);}
            if(Comments != null){cr.setComments(Comments);}
            if (OutReachSessionConducted==true){cr.setIstrue(OutReachSessionConducted);}
            if (OutReachSessionConducted==false){cr.setIstrue(OutReachSessionConducted);}
            cr.setEPICenterId(epiCenter);
            cr.setCreatedBy(new SharedPref(getActivity()).GetserverID());
            cr.setEPICenterName(otherEpiCenter.getText().toString());
            cr.setLattitude(String.valueOf(AppController.location.getLatitude()));
            cr.setLongitude(String.valueOf(AppController.location.getLongitude()));
            cr.setProvinceId(provinceValue);
            cr.setStoreTypeId(storeValue);
            cr.setStoreName(storeName.getText().toString());
            cr.setSyncDateTime(AppController.date);
            AppController.indicatorMasterDataSave = cr;

            if (AppController.appModuleID.toString().equals("6"))

            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ChildFragment()).addToBackStack(null).commit();
            }

            else if (AppController.appModuleID.toString().equals("5"))

            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ZeroDoseMasterFragment()).addToBackStack(null).commit();
            }

            else

            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new IndicatorsFragment()).addToBackStack(null).commit();
            }
        }

    }


    public void replaceFragment(Fragment fragment, boolean animated, boolean updownSlide, boolean addToBackStack, String tag) {
        ((MainActivity) getActivity()).replaceFragment(fragment, animated, updownSlide, addToBackStack, tag);
    }

    void movetoNext() {

        if (checklistValue.equals("Cluster Form")) {
            AppController.checklistType = checklistValue;
            LinkFragment.LF.changeTab(2);
        } else {

            if (AppController.checklistType != null) {
                if (!AppController.checklistType.equals(checklistValue)) {
                    AppController.checklistType = checklistValue;
                    ChecklistFragment.CF.LoadIndicators();
                }
            } else {
                AppController.checklistType = checklistValue;
                ChecklistFragment.CF.LoadIndicators();
            }

            LinkFragment.LF.changeTab(1);
        }

        proceed.setEnabled(true);
    }

    public void check(String id) {


        if(!id.equals("1"))

        {
            if ((type.equals("super") || type.equals("province")))

            {
                getProvinces();
            }

            else if (type.equals("division"))

            {
                getDivision();
            }

            else if (type.equals("district"))

            {
                getDistrict();
            }

            else if (type.equals("tehsil"))

            {
                getTehsil();
            }

            else if (type.equals("uc"))

            {
                getUcs();
            }

            else

            {
                try {
                    tehsilValue = new SharedPref(getActivity()).GetLocationID().substring(0, 9);
                    unionCouncellValue = new SharedPref(getActivity()).GetUcName();
//                getHealthFacilities();
                } catch (NullPointerException e) {

                }
            }
        }
    }

    public boolean validate() {

        boolean valid = true;

        if (AppController.appModuleID.toString().equals("1"))

        {
            if (storeValue == null)

            {
//                Toast.makeText(getActivity(), "Please select Store", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select Store")
                        .show();
                valid = false;
                return valid;
            }

            else if(!storeValue.equals("1"))

            {
                if (provinceValue == null && (type.equals("super") || type.equals("province")) )

                {
//                    Toast.makeText(getActivity(), "Please select Province", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please select Province")
                            .show();
                    valid = false;
                    return valid;
                } else if (divisionValue == null && (type.equals("super") || type.equals("province") || type.equals("division")) && !storeValue.equals("1") && !storeValue.equals("2")) {
//                    Toast.makeText(getActivity(), "Please select Division", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please select Division")
                            .show();
                    valid = false;
                    return valid;
                } else if (districtValue == null && (type.equals("super") || type.equals("province") || type.equals("division") || type.equals("district")) && (storeValue.equals("3") || storeValue.equals("4"))) {
//                    Toast.makeText(getActivity(), "Please select District", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please select District")
                            .show();
                    valid = false;
                    return valid;
                } else if (tehsilValue == null && (type.equals("super") || type.equals("province") || type.equals("division") || type.equals("district") || type.equals("tehsil")) && storeValue.equals("4")) {
//                    Toast.makeText(getActivity(), "Please select Tehsil", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please select Tehsil")
                            .show();
                    valid = false;
                    return valid;
                }
            }

            if (storeName.getText().toString().isEmpty() )

            {
                new SweetAlertDialog(getContext())
                        .setTitleText("Please enter store name")
                        .show();
//                Toast.makeText(getActivity(), "Please enter store name", Toast.LENGTH_LONG).show();
                valid = false;
                return valid;
            }


        }

        else

        {

            if (provinceValue == null && (type.equals("super") || type.equals("province"))) {
//                Toast.makeText(getActivity(), "Please select Province", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select Province")
                        .show();
                valid = false;
                return valid;
            } else if (divisionValue == null && (type.equals("super") || type.equals("province") || type.equals("division"))) {
//                Toast.makeText(getActivity(), "Please select Division", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select Division")
                        .show();
                valid = false;
                return valid;
            } else if (districtValue == null && (type.equals("super") || type.equals("province") || type.equals("division") || type.equals("district"))) {
//                Toast.makeText(getActivity(), "Please select District", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select District")
                        .show();
                valid = false;
                return valid;
            } else if (tehsilValue == null && (type.equals("super") || type.equals("province") || type.equals("division") || type.equals("district") || type.equals("tehsil"))) {
//                Toast.makeText(getActivity(), "Please select Tehsil", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select Tehsil")
                        .show();
                valid = false;
                return valid;
            } else if (ucValue == null) {
//                Toast.makeText(getActivity(), "Please select UC", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please select UC")
                        .show();
                valid = false;
                return valid;
            } else if (AppController.appModuleID.toString().equals("2") && TextUtils.isEmpty(otherEpiCenter.getText().toString()) && (epiCenter == null || epiCenter.equals("0"))) {
//                Toast.makeText(getActivity(), "Please enter Epi center name", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please enter Epi center name")
                        .show();
                valid = false;
                return valid;
            }

//            else if (AppController.appModuleID.toString().equals("4") && day == null) {
//                Toast.makeText(getActivity(), "Please select day", Toast.LENGTH_LONG).show();
//                valid = false;
//            }
//            else if (AppController.appModuleID.toString().equals("4") && siaTeamNo.getText().toString().isEmpty()) {
////                Toast.makeText(getActivity(), "Please select SIAs Team No", Toast.LENGTH_LONG).show();
//                new SweetAlertDialog(getContext())
//                        .setTitleText("Please select SIAs Team No")
//                        .show();
//                valid = false;
//                return valid;
//            }

            if(AppController.appModuleID.toString().equals("3"))
            {
                if (sessionsiteValue == null) {
//                Toast.makeText(getActivity(), "Please select UC", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please enter session site")
                            .show();
                    valid = false;
                    return valid;
                }
                if(outreach_value==null){
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please Select Outreach session conducted today Or Not")
                            .show();
                    valid = false;
                    return valid;
                }else {

                     if(outreach_value.equalsIgnoreCase("No")){
                        if(reason_outreach.getText().toString().trim().isEmpty()){
                            new SweetAlertDialog(requireContext())
                                    .setTitleText("Please Enter Reason")
                                    .show();
                            valid = false;
                            return valid;
                        }
                        if(reamrks_outreach.getText().toString().trim().isEmpty()){
                            new SweetAlertDialog(requireContext())
                                    .setTitleText("Please Enter Remarks")
                                    .show();
                            valid = false;
                            return valid;
                        }
                    }
                }

            }
        }

        return valid;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_CAMERA_PHOTO && resultCode == RESULT_OK) {


            new ImageCompressionAsyncTask(MainActivity.main).execute(mCurrentPhotoPath,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

        }

        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;

                StringBuffer sb = new StringBuffer();
                sb.append(String.format("Totally %d images selected", mResults.size())).append("\n");


                registerText.setText(sb.toString());
                if (mResults.size() == 1) {
                    new Image1CompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                }
                if (mResults.size() == 2) {

                    new Image1CompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image2CompressionAsyncTask(MainActivity.main).execute(mResults.get(1),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                } else if (mResults.size() == 3) {

                    new Image1CompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image2CompressionAsyncTask(MainActivity.main).execute(mResults.get(1),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image3CompressionAsyncTask(MainActivity.main).execute(mResults.get(2),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                } else if (mResults.size() == 4) {

                    new Image1CompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image2CompressionAsyncTask(MainActivity.main).execute(mResults.get(1),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image3CompressionAsyncTask(MainActivity.main).execute(mResults.get(2),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image4CompressionAsyncTask(MainActivity.main).execute(mResults.get(3),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                } else if (mResults.size() == 5) {

                    new Image1CompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image2CompressionAsyncTask(MainActivity.main).execute(mResults.get(1),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image3CompressionAsyncTask(MainActivity.main).execute(mResults.get(2),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image4CompressionAsyncTask(MainActivity.main).execute(mResults.get(3),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                    new Image5CompressionAsyncTask(MainActivity.main).execute(mResults.get(4),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI/images");

                }

            }
        }

    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    private void loadPhoto() {
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageURI(ImageUri);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        builder.show();
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    void getStoreTypes()

    {

        StoreList = Store.getStores(new SharedPref(MainActivity.main).GetStoreType());

        if (StoreList.size() != 0) {

            String[] storeArray = new String[StoreList.size() + 1];
            storeArray[0] = "EPI Store Type";

            for (int i = 0; i < StoreList.size(); i++) {
                storeArray[i + 1] = StoreList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, storeArray);
            storeType.setAdapter(districtsAdapter);
            storeTypeLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Stores", Toast.LENGTH_SHORT).show();
        }
    }

    void getProvinces() {


        provinceList = Province.getProvinces();

        if (provinceList.size() != 0) {

            String[] provinceArray = new String[provinceList.size() + 1];
            provinceArray[0] = "Select Province";

            for (int i = 0; i < provinceList.size(); i++) {
                provinceArray[i + 1] = provinceList.get(i).getProvinceName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, provinceArray);
            province.setAdapter(districtsAdapter);
            provinceLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Provinces", Toast.LENGTH_SHORT).show();
        }

    }

    void getDivisionByProvince(int id) {

        DivisionList = Location.getDivisionByProvince(id);

        if (DivisionList.size() != 0) {

            String[] divisionArray = new String[DivisionList.size() + 1];
            divisionArray[0] = "Select Division";

            for (int i = 0; i < DivisionList.size(); i++) {
                divisionArray[i + 1] = DivisionList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionArray);
            division.setAdapter(districtsAdapter);
            divisionLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Divisions", Toast.LENGTH_SHORT).show();
        }

    }

    void getDivision() {

        String code = new SharedPref(MainActivity.main).GetLocationID();

        DivisionList = Location.getDivision(code);

        if (DivisionList.size() != 0) {
//035002_250  //R2ZWV6
            String[] divisionArray = new String[DivisionList.size() + 1];
            divisionArray[0] = "Select Division";

            for (int i = 0; i < DivisionList.size(); i++) {
                divisionArray[i + 1] = DivisionList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionArray);
            division.setAdapter(districtsAdapter);
            divisionLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Divisions", Toast.LENGTH_SHORT).show();
        }

    }

    void getDistrict() {

        if(divisionValue != null)

        {
            DistrictList = Location.getDistrict(divisionValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            DistrictList = Location.getDistrict(code);
        }


        if (DistrictList.size() != 0) {

            String[] districtsArray = new String[DistrictList.size() + 1];
            districtsArray[0] = "Select District";

            for (int i = 0; i < DistrictList.size(); i++) {
                districtsArray[i + 1] = DistrictList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            district.setAdapter(districtsAdapter);
            districtLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "No Districts found", Toast.LENGTH_SHORT).show();
        }

    }

    void getTehsil() {

        if(districtValue != null)

        {
            TehsilList = Location.getTehsil(districtValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            TehsilList = Location.getTehsil(code);
        }

        if (TehsilList.size() != 0) {
            String[] tehsilArray = new String[TehsilList.size() + 1];
            tehsilArray[0] = "Select Tehsil";

            for (int i = 0; i < TehsilList.size(); i++) {
                tehsilArray[i + 1] = TehsilList.get(i).getName();
            }
            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            tehsil.setAdapter(districtsAdapter);
            tehsilLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "No tehsil found against above selection", Toast.LENGTH_SHORT).show();
        }

    }

    void getUcs() {

        if(tehsilValue != null)

        {
            UcList = Location.getUC(tehsilValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            UcList = Location.getUC(code);
        }

        if (UcList.size() != 0) {
            String[] tehsilArray = new String[UcList.size() + 1];
            tehsilArray[0] = "Select UC";

            for (int i = 0; i < UcList.size(); i++) {

                tehsilArray[i + 1] = UcList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            uc.setAdapter(districtsAdapter);
            UcLayout.setVisibility(View.VISIBLE);


        } else {
            UcLayout.setVisibility(View.GONE);
            uc.setSelection(0);
            unionCouncellValue = null;
            Toast.makeText(getActivity(), "No UC found against above selection", Toast.LENGTH_SHORT).show();
        }
    }

    void getEpiCenters(String ucValue) {

        epiCenterList = EpiCenter.getEpiCenters(ucValue);
        epiCenterList.add(new EpiCenter(0, "Other"));

        if (epiCenterList.size() != 0) {
            String[] epiCentersArray = new String[epiCenterList.size() + 1];
            epiCentersArray[0] = "Select EPI Center";

            for (int i = 0; i < epiCenterList.size(); i++) {

                epiCentersArray[i + 1] = epiCenterList.get(i).getCenterName();
            }

            ArrayAdapter<String> epiCentersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, epiCentersArray);
            epiCenterSpinner.setAdapter(epiCentersAdapter);
            epiCentersLayout.setVisibility(View.VISIBLE);


        } else {
            epiCentersLayout.setVisibility(View.GONE);
            epiCenterSpinner.setSelection(0);
            epiCenter = null;
            Toast.makeText(getActivity(), "No Epi Centers Found", Toast.LENGTH_SHORT).show();
        }
    }

    void getDays() {
        ArrayAdapter<String> epiCentersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, daysArray);
        selectDay.setAdapter(epiCentersAdapter);
    }

    void outreachSessionSpinner() {

        ArrayAdapter<String> epiCentersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, outreachoptions);
        outreach_session_spinner.setAdapter(epiCentersAdapter);
        outreach_session_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    outreach_value = outreachoptions[position];
                    if(position==1){
                            OutReachSessionConducted=true;
                            reason_outreach_layout.setVisibility(View.GONE);
                            remarks_outreach_layout.setVisibility(View.GONE);

                    }else if(position==2){
                            OutReachSessionConducted=false;
                            reason_outreach_layout.setVisibility(View.VISIBLE);
                            remarks_outreach_layout.setVisibility(View.VISIBLE);
                    }
                }else {
                    outreach_value=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void multiImagePicker() {
        // start multiple photos selector
        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(MainActivity.main.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createMediaFile(TYPE_IMAGE);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("abc", "Error occurred while creating the file");

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // Get the content URI for the image file
                capturedUri = FileProvider.getUriForFile(MainActivity.main, "com.hisdu.epi.fileprovider", photoFile);

                Log.d("abc", "Log1: " + String.valueOf(capturedUri));

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_CAMERA_PHOTO);
            }
        }
    }

    private File createMediaFile(int type) throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = (type == TYPE_IMAGE) ? "JPEG_" + timeStamp + "_" : "VID_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(type == TYPE_IMAGE ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File file = File.createTempFile(
                fileName,  /* prefix */
                type == TYPE_IMAGE ? ".jpg" : ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Get the path of the file created
        mCurrentPhotoPath = file.getAbsolutePath();
        Log.d("abc", "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return file;
    }

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public ImageCompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);

            ImageUri = Uri.fromFile(imageFile);
            ImageBase64 = getFileToByte(imageFile.getPath());

            try {
                Bitmap bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(MainActivity.main.getContentResolver(), ImageUri);
                Glide.with(getActivity()).load(bitmap).centerCrop().into(ImagePreview);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class Image1CompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public Image1CompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {


            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);
            ImageBase641 = getFileToByte(imageFile.getPath()).replace("null,", "");

        }
    }


    class Image2CompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public Image2CompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {


            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);
            ImageBase642 = getFileToByte(imageFile.getPath()).replace("null,", "");

        }
    }

    class Image3CompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public Image3CompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {


            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);
            ImageBase643 = getFileToByte(imageFile.getPath()).replace("null,", "");

        }
    }

    class Image4CompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public Image4CompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {


            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);
            ImageBase644 = getFileToByte(imageFile.getPath()).replace("null,", "");

        }

    }


    class Image5CompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public Image5CompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {


            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            File imageFile = new File(s);
            ImageBase645 = getFileToByte(imageFile.getPath()).replace("null,", "");
        }
    }

    boolean isclus()

    {
        if(AppController.appModuleID.toString().equals("4") || AppController.appModuleID.toString().equals("7")){ return true;}
        else { return false; }
    }

    public void LoadRecords()

    {
        if(AppController.appModuleID == 6)

        {
            List<House> Total = House.getAllHouseRecord(new SharedPref(MainActivity.main).GetserverID());

            if(Total.size() > 0)

            {
                for(int i = 0 ; i < Total.size() ; i++)

                {
                    int count = 0;

                    List<House> houseDetails = new ArrayList<>();

                    if(Total.get(i).getCaseEPIDNo() != null)

                    {
                         houseDetails = House.getAllHouseEPID(Total.get(i).getCaseEPIDNo(),Total.get(i).getUcCode());
                    }

                    if(houseDetails.size() > 0)

                    {
                        for (int j = 0 ; j < houseDetails.size() ; j++)

                        {
                            count = count + HouseDetail.getHouseDstail(String.valueOf(houseDetails.get(j).getMastId())).size();
                        }

                        if(count < 30)

                        {

                            IndicatorMasterDataSave saveInspections = IndicatorMasterDataSave.getAllMAsterInspection(String.valueOf(Total.get(i).getMastId()));

                            Location dis = Location.getDistrictName(saveInspections.getLocationCode().substring(0, 6));
                            Location uc = Location.getUCName(saveInspections.getLocationCode());

                            UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                            unSentRecordsObject.setDistrictName(dis.getName());
                            unSentRecordsObject.setUcName(uc.getName());
                            unSentRecordsObject.setEPID(Total.get(i).getCaseEPIDNo());
                            unSentRecordsObject.setCount(count+"");

                            if(!containEpidData(Total.get(i).getCaseEPIDNo(),uc.getName()) && count>0)

                            {
                                list.add(unSentRecordsObject);
                            }
                        }
                    }

                }
            }

            if (list.size() > 0)

            {
                mAdapter = new recordAdapter(list, MainActivity.main);

                RV.setItemViewCacheSize(list.size());
                RV.setAdapter(mAdapter);
                recordLayout.setVisibility(View.VISIBLE);
                //mAdapter.notifyDataSetChanged();
            }

        }

        else


        {
            List<UnSentRecordsObject> ls = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID);

            for(int i = 0 ; i < ls.size() ; i++)

            {
                int count = 0;

                List<UnSentRecordsObject> locationlist = mDbManager.getUnSentRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + AppController.appModuleID,ls.get(i).getLocation());

                if(locationlist.size() > 0)

                {
                    for(int j = 0 ; j < locationlist.size() ; j++)

                    {
                        count = count + Integer.parseInt(locationlist.get(j).getCount());
                    }
                }

                if(AppController.appModuleID == 4)

                {
                    if(count < 10)

                    {
                        Location dis = Location.getDistrictName(ls.get(i).getLocation().substring(0, 6));
                        Location uc = Location.getUCName(ls.get(i).getLocation());

                        ls.get(i).setDistrictName(dis.getName());
                        ls.get(i).setUcName(uc.getName());
                        ls.get(i).setCount(count+"");
                        if(!containData(uc.getName()) && count>0)
                        {
                            list.add(ls.get(i));
                        }
                    }
                }

                else if(AppController.appModuleID == 7)

                {
                    if(count < 30)

                    {
                        Location dis = Location.getDistrictName(ls.get(i).getLocation().substring(0, 6));
                        Location uc = Location.getUCName(ls.get(i).getLocation());

                        ls.get(i).setDistrictName(dis.getName());
                        ls.get(i).setUcName(uc.getName());
                        ls.get(i).setCount(count+"");
                        if(!containData(uc.getName()) && count >0)
                        {
                            list.add(ls.get(i));
                        }
                    }
                }

            }

            if (list.size() > 0)

            {
                mAdapter = new recordAdapter(list, MainActivity.main);

                RV.setItemViewCacheSize(list.size());
                RV.setAdapter(mAdapter);
                recordLayout.setVisibility(View.VISIBLE);
                    //mAdapter.notifyDataSetChanged();
            }

                // else { Toast.makeText(getActivity(), "Error Loading records!", Toast.LENGTH_LONG).show(); }
        }

    }

    boolean containData(String name)

    {
        for(int i = 0 ; i < list.size() ; i++)

        {
            if(name.equals(list.get(i).getUcName()))

            {
                return true;
            }
        }

        return false;
    }

    boolean containEpidData(String name,String uc)

    {
        for(int i = 0 ; i < list.size() ; i++)

        {
            if(name.equals(list.get(i).getEPID()) && uc.equals(list.get(i).getUcName()))

            {
                return true;
            }
        }

        return false;
    }

}
