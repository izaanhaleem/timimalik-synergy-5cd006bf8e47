package com.hisdu.ses.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.AppController;
import com.hisdu.ses.ChildLogAdapter;
import com.hisdu.ses.FamilyLogAdapter;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Database.HouseDetail;
import com.hisdu.ses.Database.HouseChildDetail;
import com.hisdu.ses.Database.House;
import com.hisdu.ses.Models.Member;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.CustomSearchableSpinner;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChildFragment extends Fragment {

    String[] reasonArray = new String[]{"Select Reason", "EI Zero Dose", "WPV-1", "VDPV", "Urgent labelled form field", "NSC Criteria", "Inadequate","L20 B+ve"};
    String[] routineArray = new String[]{"Select no. of routine opv doses", "No OPV", "Birth dose", "1 dose", "2 doses", "3 doses"};
    String[] missedArray = new String[]{"Select reason for missed OPV doses", "No team", "Refusal", "Not Available", "Others"};

    CustomSearchableSpinner reason;

    Button familyPopup,submitButton,close;

    private ArrayList<HouseChildDetail> childdataSet;
    private ArrayList<HouseDetail> familydataSet;
    private List<HouseChildDetail> ChildlistItems;
    private List<HouseDetail> familylistItems;

    private RecyclerView family_ListView;
    private RecyclerView child_ListView;

    private RecyclerView.Adapter Radapter;

    EditText AFPCASEEPID;
    String  AFPCASEEPIDValue = null, reasonValue = null;

    TextView EPIDtext;

    ///////////////////////////////////

    EditText HeadName,yearschildren,address,caseNo;
    String   hhValue = null,HeadNameValue = null,yearschildrenValue = null,travelValue = null,AfpValue = null,addressValue = null,caseNoValue = null;
    Button   childPopup,addFamily;
    RadioGroup typegroup;
    LinearLayout addressLayout,caseLayout;
    RadioButton Local,NonLocal,travelYes,travelNo,AfpYes,AfpNo;

    // ///////////////////////////////

    EditText     cName,age,missedIPVreason;
    String       cNameValue = null,ageValue = null,missedIPVreasonValue = "",cardValue = null,OPVLastValue = null,OPVLastpriorValue = null,IPVValue = null,VaccinatedValue = null,routineValue = null,missedOPVLastreasonValue = "",missedOPVLastpriorreasonValue = "";
    RadioButton  cardyes,cardNo,OPVLastYes,OPVLastNo,IPVyes,IPVNo,VaccinatedYes,VaccinatedNo,OPVLastpriorYes,OPVLastpriorNo;
    RadioGroup   vaccinatedGroup;
    LinearLayout missedIPVreasonLayout;
    Button       addChild;
    CustomSearchableSpinner routine,missedOPVLastreason,missedOPVLastpriorreason;

    android.app.AlertDialog alertDialog;
    Boolean isShowing = false;

    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.child_fragment, container, false);

        familyPopup     = view.findViewById(R.id.familyPopup);
        family_ListView = view.findViewById(R.id.family_ListView);
        reason          = view.findViewById(R.id.reason);
        AFPCASEEPID     = view.findViewById(R.id.AFPCASEEPID);
        EPIDtext        = view.findViewById(R.id.EPIDtext);
        submitButton    = view.findViewById(R.id.submitButton);

        family_ListView.setHasFixedSize(true);
        family_ListView.setLayoutManager(new GridLayoutManager(MainActivity.main,3));

        ArrayAdapter<String> epiCentersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, reasonArray);
        reason.setAdapter(epiCentersAdapter);

        //fragmentManager = getFragmentManager();

        childdataSet    = new ArrayList<>();
        familydataSet   = new ArrayList<>();
        ChildlistItems  = new ArrayList<>();
        familylistItems = new ArrayList<>();

        familyPopup.setOnClickListener(v ->

        {
            final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());

            LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView           = inflater1.inflate(R.layout.house_popup, null);

                childPopup     = dialogView.findViewById(R.id.childPopup);
                close          = dialogView.findViewById(R.id.close);
                HeadName       = dialogView.findViewById(R.id.HeadName);
                yearschildren  = dialogView.findViewById(R.id.yearschildren);
                Local          = dialogView.findViewById(R.id.Local);
                NonLocal       = dialogView.findViewById(R.id.NonLocal);
                travelYes      = dialogView.findViewById(R.id.travelYes);
                travelNo       = dialogView.findViewById(R.id.travelNo);
                AfpYes         = dialogView.findViewById(R.id.AfpYes);
                child_ListView = dialogView.findViewById(R.id.child_ListView);
                addFamily      = dialogView.findViewById(R.id.addFamily);
                typegroup      = dialogView.findViewById(R.id.typegroup);
                AfpNo          = dialogView.findViewById(R.id.AfpNo);
                address        = dialogView.findViewById(R.id.address);
                caseNo         = dialogView.findViewById(R.id.caseNo);
                addressLayout  = dialogView.findViewById(R.id.addressLayout);
                caseLayout     = dialogView.findViewById(R.id.caseLayout);

                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(false);

                if(!isShowing)

                {
                    alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    isShowing   = true;
                }

                child_ListView.setHasFixedSize(true);
                child_ListView.setLayoutManager(new GridLayoutManager(MainActivity.main,3));

                childPopup.setOnClickListener(v19 ->

                {
                    yearschildren.clearFocus();
                    if(yearschildrenValue != null && ChildlistItems.size() < Integer.parseInt(yearschildrenValue))

                    {

                        final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(MainActivity.main,R.style.full_screen_dialog);
                        LayoutInflater inflater11 = MainActivity.main.getLayoutInflater();
                        final View dialogView1 = inflater11.inflate(R.layout.child_popup, null);

                        close            = dialogView1.findViewById(R.id.close);
                        cName            = dialogView1.findViewById(R.id.cName);
                        age              = dialogView1.findViewById(R.id.age);
                        routine          = dialogView1.findViewById(R.id.routine);
                        missedOPVLastreason     = dialogView1.findViewById(R.id.missedOPVLastreason);
                        cardyes          = dialogView1.findViewById(R.id.cardyes);
                        cardNo           = dialogView1.findViewById(R.id.cardNo);
                        OPVLastYes           = dialogView1.findViewById(R.id.OPVLastYes);
                        OPVLastNo            = dialogView1.findViewById(R.id.OPVLastNo);
                        OPVLastpriorYes            = dialogView1.findViewById(R.id.OPVLastpriorYes);
                        OPVLastpriorNo            = dialogView1.findViewById(R.id.OPVLastpriorNo);
                        IPVyes           = dialogView1.findViewById(R.id.IPVyes);
                        IPVNo            = dialogView1.findViewById(R.id.IPVNo);
                        VaccinatedYes    = dialogView1.findViewById(R.id.VaccinatedYes);
                        VaccinatedNo     = dialogView1.findViewById(R.id.VaccinatedNo);
                        missedIPVreason  = dialogView1.findViewById(R.id.missedIPVreason);
                        vaccinatedGroup  = dialogView1.findViewById(R.id.vaccinatedGroup);
                        missedOPVLastpriorreason  = dialogView1.findViewById(R.id.missedOPVLastpriorreason);
                        missedIPVreasonLayout  = dialogView1.findViewById(R.id.missedIPVreasonLayout);
                        addChild         = dialogView1.findViewById(R.id.addChild);

                        ArrayAdapter<String> routineAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, routineArray);
                        routine.setAdapter(routineAdapter);

                        ArrayAdapter<String> missedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, missedArray);
                        missedOPVLastreason.setAdapter(missedAdapter);

                        missedOPVLastpriorreason.setAdapter(missedAdapter);

                        dialogBuilder1.setView(dialogView1);
                        dialogBuilder1.setCancelable(false);
                        final AlertDialog alertDialog = dialogBuilder1.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();

                        cardyes.setOnClickListener(v17 -> cardValue = "Yes");
                        cardNo.setOnClickListener(v17 -> cardValue = "No");
                        OPVLastYes.setOnClickListener(v17 ->
                        {
                            OPVLastValue = "Yes";
                            missedOPVLastreason.setVisibility(View.GONE);
                            missedOPVLastreasonValue = "";
                        });
                        OPVLastNo.setOnClickListener(v17 ->
                        {
                            OPVLastValue = "No";
                            missedOPVLastreason.setVisibility(View.VISIBLE);
                        });
                        IPVyes.setOnClickListener(v17 ->
                        {
                            vaccinatedGroup.setVisibility(View.VISIBLE);
                            IPVValue = "Yes";
                        });
                        IPVNo.setOnClickListener(v17 ->
                        {
                            vaccinatedGroup.setVisibility(View.GONE);
                            missedIPVreasonLayout.setVisibility(View.GONE);
                            vaccinatedGroup.clearCheck();
                            VaccinatedValue = null;
                            IPVValue = "No";
                        });

                        OPVLastpriorYes.setOnClickListener(v17 ->
                        {
                            OPVLastpriorValue = "Yes";
                            missedOPVLastpriorreason.setVisibility(View.GONE);
                            missedOPVLastpriorreasonValue = "";
                        });
                        OPVLastpriorNo.setOnClickListener(v17 ->
                        {
                            OPVLastpriorValue = "No";
                            missedOPVLastpriorreason.setVisibility(View.VISIBLE);
                        });
                        VaccinatedYes.setOnClickListener(v17 ->
                        {
                            VaccinatedValue = "19";
                            missedIPVreasonLayout.setVisibility(View.GONE);
                            missedIPVreasonValue = null;
                        });
                        VaccinatedNo.setOnClickListener(v17 ->

                        {
                            VaccinatedValue = "20";
                            missedIPVreasonLayout.setVisibility(View.VISIBLE);
                        });

                        routine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (routine.getSelectedItemPosition() != 0) { routineValue = routine.getSelectedItem().toString(); }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) { }
                        });

                        missedOPVLastreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (missedOPVLastreason.getSelectedItemPosition() != 0) { missedOPVLastreasonValue = missedOPVLastreason.getSelectedItem().toString(); }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) { }
                        });

                        missedOPVLastpriorreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (missedOPVLastpriorreason.getSelectedItemPosition() != 0) { missedOPVLastpriorreasonValue = missedOPVLastpriorreason.getSelectedItem().toString(); }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) { }
                        });

                        missedIPVreason.setOnFocusChangeListener((v17, hasFocus) -> {

                            if (!hasFocus && missedIPVreason.isEnabled())

                            {
                                if(missedIPVreason.length() != 0) { missedIPVreasonValue     = missedIPVreason.getText().toString(); }

                                else { missedIPVreasonValue = ""; }
                            }

                        });

                        cName.setOnFocusChangeListener((v17, hasFocus) -> {

                            if (!hasFocus && cName.isEnabled())

                            {
                                if(cName.length() != 0) { cNameValue = cName.getText().toString(); }

                                else { cNameValue = null; }
                            }

                        });

                        age.setOnFocusChangeListener((v17, hasFocus) -> {

                            if (!hasFocus && age.isEnabled())

                            {
                                if(age.length() != 0)

                                {

                                    int a = Integer.parseInt(age.getText().toString());
                                    if(a > 0 && a < 60){ ageValue     = age.getText().toString(); }
                                    else
                                        {
                                            age.setText(null);
                                            Toast.makeText(MainActivity.main,"Please enter age in month not more than 59",Toast.LENGTH_LONG).show();
                                        }

                                }

                                else { ageValue = null; }
                            }

                        });

                        close.setOnClickListener(v16 -> {
                            childClearData();
                            isShowing = false;
                            alertDialog.dismiss();
                        });

                        addChild.setOnClickListener(v15 -> {
                            if (validateChildData())

                            {
                                HouseChildDetail fd = new HouseChildDetail(cNameValue, ageValue,cardValue,RoutineID(routineValue),OPVLastValue,MissedID(missedOPVLastreasonValue),OPVLastpriorValue,MissedID(missedOPVLastpriorreasonValue),IPVValue,VaccinatedValue,missedIPVreasonValue,new SharedPref(MainActivity.main).GetserverID(),AppController.date,"0");
                                ChildlistItems.add(fd);
                                childClearData();
                                ChildUpdateLogList();
                                alertDialog.dismiss();
                                isShowing = false;
                            }
                        });

                    }

                    else

                        {
                            Toast.makeText(MainActivity.main, "Please check no of children value", Toast.LENGTH_SHORT).show();
                        }
                });

                Local.setOnClickListener(v14 -> hhValue     = "7");
                NonLocal.setOnClickListener(v13 -> hhValue  = "9");

                travelYes.setOnClickListener(v13 ->

                {
                    travelValue  = travelYes.getText().toString();
                    addressLayout.setVisibility(View.VISIBLE);
                });
                travelNo.setOnClickListener(v13 ->

                {
                    travelValue  = travelNo.getText().toString();
                    addressLayout.setVisibility(View.GONE);
                    addressValue = null;
                });

                AfpYes.setOnClickListener(v13 ->
                {
                    AfpValue  = AfpYes.getText().toString();
                    caseLayout.setVisibility(View.VISIBLE);
                });
                AfpNo.setOnClickListener(v13 ->
                {
                    AfpValue   = AfpNo.getText().toString();
                    caseLayout.setVisibility(View.GONE);
                    caseNoValue = null;
                });

            HeadName.setOnFocusChangeListener((v13, hasFocus) -> {

                if (!hasFocus && HeadName.isEnabled())

                {
                    if(HeadName.length() != 0) { HeadNameValue     = HeadName.getText().toString(); }

                    else { HeadNameValue = null; }
                }

            });

            yearschildren.setOnFocusChangeListener((v13, hasFocus) -> {

                if (!hasFocus && yearschildren.isEnabled())

                {
                    if(yearschildren.length() != 0) { yearschildrenValue     = yearschildren.getText().toString(); }

                    else { yearschildrenValue = null; }
                }

            });

            address.setOnFocusChangeListener((v13, hasFocus) -> {

                if (!hasFocus && address.isEnabled())

                {
                    if(address.length() != 0) { addressValue     = address.getText().toString(); }

                    else { addressValue = null; }
                }

            });

            caseNo.setOnFocusChangeListener((v13, hasFocus) -> {

                if (!hasFocus && caseNo.isEnabled())

                {
                    if(caseNo.length() != 0) { caseNoValue     = caseNo.getText().toString(); }

                    else { caseNoValue = null; }
                }

            });

                addFamily.setOnClickListener(v12 ->

                {
                    if (validateFamilyData())

                    {
                        Double latitude= AppController.getInstance().location.getLatitude();
                        Double longitude= AppController.getInstance().location.getLongitude();

                        HouseDetail fm = new HouseDetail(hhValue,travelValue,addressValue,AfpValue,caseNoValue, HeadNameValue,yearschildrenValue,ChildlistItems,latitude ,longitude);
                        familylistItems.add(fm);
                        FamilyClearData();
                        FamilyUpdateLogList();
                        alertDialog.dismiss();
                        isShowing = false;

                    }
                });

                close.setOnClickListener(v1 -> {
                    FamilyClearData();
                    alertDialog.dismiss();
                    isShowing = false;

                });
        });


        AFPCASEEPID.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && AFPCASEEPID.isEnabled())

            {
                if(AFPCASEEPID.length() != 0)
                {
                    AFPCASEEPIDValue   = AFPCASEEPID.getText().toString();
                    int count = 0;
                    if(AppController.indicatorMasterDataSave.getLocationCode() != null)
                    {
                        List<House> houses = House.getAllHouseEPID(AFPCASEEPIDValue,AppController.indicatorMasterDataSave.getLocationCode());
                        if(houses.size() > 0)
                        {
                            for(int i = 0 ; i < houses.size() ; i++)
                            {
                                count = count + HouseDetail.getAllHouseMaster(houses.get(i).getMastId()).size();
                            }

                            reason.setSelection(Integer.parseInt(houses.get(0).getHHClusterProfileId()));
                            reasonValue = houses.get(0).getHHClusterProfileId();
                            reason.setEnabled(false);

                        }

                        else
                            {
                                reason.setSelection(0);
                                reasonValue = null;
                                reason.setEnabled(true);
                            }
                        EPIDtext.setText("AFP CASE EPID #: " + count);
                    }

                }
                else { AFPCASEEPIDValue = null; }
            }

        });

        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (reason.getSelectedItemPosition() != 0) {
                    reasonValue = reason.getSelectedItem().toString();
                }

                else

                    {
                        reasonValue = null;
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitButton.setOnClickListener(v ->

        {
            clearFocus();
            submitButton.setEnabled(false);

            if(validate())

            {
                submit();
//                if(AppController.getInstance().hasinternetAccess) { submit(); }
//
//                else {submitButton.setEnabled(true); Toast.makeText(MainActivity.main,"No internet access",Toast.LENGTH_SHORT).show();}
            }

            else {submitButton.setEnabled(true);}
        });

        return view;
    }

    private void ChildUpdateLogList()

    {
        childdataSet.clear();

        for (int i = 0; i < ChildlistItems.size(); i++) {

            String name = null;
            String age = null;

            if (ChildlistItems.get(i).getName() != null)

            {
                name = ChildlistItems.get(i).getName();
            }

            if (ChildlistItems.get(i).getAgeInMonths() != null)

            {
                age = ChildlistItems.get(i).getAgeInMonths();
            }

            childdataSet.add(new HouseChildDetail(name, age,"","","","","","","","","","","",""));
        }

        Radapter = new ChildLogAdapter(childdataSet, MainActivity.main);
        child_ListView.setAdapter(Radapter);

    }

    private void FamilyUpdateLogList()

    {
        familydataSet.clear();

        for (int i = 0; i < familylistItems.size(); i++) {

            String name = null, contact = null, cnic = null;

            if (familylistItems.get(i).getHeadOfHouse() != null)

            {
                name = familylistItems.get(i).getHeadOfHouse();
            }

            if (familylistItems.get(i).getNoOfChildren() != null)

            {
                contact = familylistItems.get(i).getNoOfChildren();
            }

            familydataSet.add(new HouseDetail(name, contact, cnic));
        }

        Radapter = new FamilyLogAdapter(familydataSet, MainActivity.main);
        family_ListView.setAdapter(Radapter);

    }

    void submit()

    {

        final ProgressDialog PD = new ProgressDialog(MainActivity.main);
        PD.setMessage("Saving Record, Please Wait...");
        PD.setCancelable(false);
        PD.show();

        long res = AppController.indicatorMasterDataSave.save();

        if(res != -1)

        {
            House h = new House();
            h.setMastId(AppController.indicatorMasterDataSave.getMastID());
            h.setCaseEPIDNo(AFPCASEEPIDValue);
            h.setUcCode(AppController.indicatorMasterDataSave.getLocationCode());
            h.setHHClusterProfileId(ReasonID(reasonValue));
            h.setCreatedBy(new SharedPref(MainActivity.main).GetserverID());
            h.setCreatedOn(AppController.date);
            h.setSync("0");

            long house = h.save();

            if(house != -1)

            {
                if(familylistItems != null && familylistItems.size() > 0)

                {
                    int housesize = 0;

                    for (int i = 0; i < familylistItems.size(); i++)

                    {
                        int mas =  HouseDetail.getAllHouse().size() + 1;

                        HouseDetail hd  =new HouseDetail();
                        hd.setMasterId(AppController.indicatorMasterDataSave.getMastID());
                        hd.setSubMasterId(mas);
                        hd.setAFPCaseNo(familylistItems.get(i).getAFPCaseNo());
                        hd.setAFPCaseSearch(familylistItems.get(i).getAFPCaseSearch());
                        hd.setHeadOfHouse(familylistItems.get(i).getHeadOfHouse());
                        hd.setHouseHoldProfileId(familylistItems.get(i).getHouseHoldProfileId());
                        hd.setNoOfChildren(familylistItems.get(i).getNoOfChildren());
                        hd.setTraveled(familylistItems.get(i).getTraveled());
                        hd.setTravelingAddress(familylistItems.get(i).getTravelingAddress());
                        hd.setLatitude(familylistItems.get(i).getLatitude());
                        hd.setLongitude(familylistItems.get(i).getLongitude());
                        hd.setSync("0");
                        hd.setCreatedBy(new SharedPref(MainActivity.main).GetserverID());
                        hd.setCreatedOn(AppController.date);
                        long famRes = hd.save();

                        if(famRes != -1)

                        {
                            housesize++;

                            if(familylistItems.get(i).getHouseChildDetails() != null && familylistItems.get(i).getHouseChildDetails().size() > 0)

                            {
                                for (int j = 0; j < familylistItems.get(i).getHouseChildDetails().size(); j++)

                                {
                                    HouseChildDetail hcd  =new HouseChildDetail();
                                    hcd.setMastId(mas);
                                    hcd.setAgeInMonths(familylistItems.get(i).getHouseChildDetails().get(j).getAgeInMonths());
                                    hcd.setName(familylistItems.get(i).getHouseChildDetails().get(j).getName());
                                    hcd.setEpiCardAvailable(familylistItems.get(i).getHouseChildDetails().get(j).getEpiCardAvailable());
                                    hcd.setIPVChild(familylistItems.get(i).getHouseChildDetails().get(j).getIPVChild());
                                    hcd.setIPVChildTypeProfileId(familylistItems.get(i).getHouseChildDetails().get(j).getIPVChildTypeProfileId());
                                    hcd.setIPVMissedReason(familylistItems.get(i).getHouseChildDetails().get(j).getIPVMissedReason());
                                    hcd.setReceivedOPVLastTime(familylistItems.get(i).getHouseChildDetails().get(j).getReceivedOPVLastTime());
                                    hcd.setReceivedOPVPriorLastTime(familylistItems.get(i).getHouseChildDetails().get(j).getReceivedOPVPriorLastTime());
                                    hcd.setOPVMissedReasonProfileId(familylistItems.get(i).getHouseChildDetails().get(j).getOPVMissedReasonProfileId());
                                    hcd.setPriorOPVMissedReasonProfileId(familylistItems.get(i).getHouseChildDetails().get(j).getPriorOPVMissedReasonProfileId());
                                    hcd.setRoutineOPVDoseProfileId(familylistItems.get(i).getHouseChildDetails().get(j).getRoutineOPVDoseProfileId());
                                    hcd.setSync("0");
                                    hcd.setCreatedBy(new SharedPref(MainActivity.main).GetserverID());
                                    hcd.setCreatedOn(AppController.date);
                                    long chiRes = hcd.save();
                                }

                            }
                        }
                    }

                    if(housesize == familylistItems.size())

                    {
                        PD.dismiss();
                        Toast.makeText(MainActivity.main, "Record Saved Successfully!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment()).addToBackStack(null).commit();
                    }

                    else

                        {
                            PD.dismiss();
                            Toast.makeText(MainActivity.main, "Error saving house data, please try again.", Toast.LENGTH_SHORT).show();
                        }
                }

            }

            else

            {
                PD.dismiss();
                Toast.makeText(MainActivity.main, "Error saving data, please try again.", Toast.LENGTH_SHORT).show();
            }
        }

        else

        {
            PD.dismiss();
            Toast.makeText(MainActivity.main, "Error saving master data, please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    String ReasonID(String Value)

    {
        if(Value.equals("EI Zero Dose")) { return "1"; }
        else if(Value.equals("WPV-1")) { return "2"; }
        else if(Value.equals("VDPV")) { return "3"; }
        else if(Value.equals("Urgent labelled form field")) { return "4"; }
        else if(Value.equals("NSC Criteria")) { return "5"; }
        else if(Value.equals("Inadequate")) { return "6"; }
        else if(Value.equals("L20 B+ve")) { return "37"; }
        return "";
    }

    String RoutineID(String Value)

    {
        if(Value.equals("No OPV")) { return "10"; }
        else if(Value.equals("Birth dose")) { return "11"; }
        else if(Value.equals("1 dose")) { return "12"; }
        else if(Value.equals("2 doses")) { return "13"; }
        else if(Value.equals("3 doses")) { return "14"; }
        return "";
    }

    String MissedID(String Value)

    {
        if(Value != null)

        {
            if(Value.equals("No team")) { return "15"; }
            else if(Value.equals("Refusal")) { return "16"; }
            else if(Value.equals("Not Available")) { return "17"; }
            else if(Value.equals("Others")) { return "18"; }
        }

        return "";
    }

    public boolean validate()

    {
        boolean valid = true;

        if (AFPCASEEPIDValue == null || AFPCASEEPIDValue.isEmpty()|| AFPCASEEPIDValue.equals("--/--/--/---"))

        {
            Toast.makeText(MainActivity.main,"Please enter AFP CASE EPID",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (reasonValue == null || reasonValue.isEmpty())

        {
            Toast.makeText(MainActivity.main,"Please select reason",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (familylistItems.size() == 0)

        {
            Toast.makeText(MainActivity.main,"Please enter at least one house",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if(AppController.location == null)

        {
            Toast.makeText(MainActivity.main,"Location is unavailable",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    public boolean validateChildData()

    {
        cName.clearFocus();
        age.clearFocus();
        missedIPVreason.clearFocus();


        if (cNameValue == null)

        {
            Toast.makeText(MainActivity.main,"Please enter child name",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ageValue == null)

        {
            Toast.makeText(MainActivity.main,"Please enter age",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cardValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select card available",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (routineValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select no. of routine opv doses",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (OPVLastValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select OPV during Last Campaign?",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(OPVLastValue.equals("No"))

        {
            if (missedOPVLastreasonValue == "")

            {
                Toast.makeText(MainActivity.main,"Please select missed reason",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (OPVLastpriorValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select OPV during Campaign prior to last Campaign?",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(OPVLastpriorValue.equals("No"))

        {
            if (missedOPVLastpriorreasonValue == "")

            {
                Toast.makeText(MainActivity.main,"Please select missed reason",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (IPVValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select child is eligible for IPV?",Toast.LENGTH_SHORT).show();
            return false;
        }

        else

        {
            if(IPVValue.equals("Yes"))

            {
                if (VaccinatedValue == null)

                {
                    Toast.makeText(MainActivity.main,"Please select vaccinated?",Toast.LENGTH_SHORT).show();
                    return false;
                }

                else if(VaccinatedValue.equals("20"))

                {
                    if (missedIPVreasonValue == null)

                    {
                        Toast.makeText(MainActivity.main,"Please enter missed reason",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }

        }

        return true;

    }

    public boolean validateFamilyData()

    {
        HeadName.clearFocus();yearschildren.clearFocus();address.clearFocus();caseNo.clearFocus();

        if (hhValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select house hold",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (travelValue == null)

        {
            Toast.makeText(MainActivity.main,"Please select travel history",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(travelValue.equals("Yes"))

        {
            if (addressValue == null)

            {
                Toast.makeText(MainActivity.main,"Please enter address",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (AfpValue == null)

        {
            Toast.makeText(MainActivity.main,"Please enter afp case search value",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (AfpValue.equals("Yes"))

        {
            if (caseNoValue == null)

            {
                Toast.makeText(MainActivity.main,"Please enter case no",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (HeadNameValue == null)

        {
            Toast.makeText(MainActivity.main,"Please enter name of head of house",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (yearschildrenValue == null)

        {
            Toast.makeText(MainActivity.main,"Please enter total less than 5 years children",Toast.LENGTH_SHORT).show();
            return false;
        }

        else

        {
            if(ChildlistItems.size() != Integer.parseInt(yearschildrenValue))

            {
                Toast.makeText(MainActivity.main,"Please enter all children details",Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        return true;

    }

    public void clearFocus()

    {
        AFPCASEEPID.clearFocus();
    }

    void childClearData()

    {
        cNameValue = null;
        ageValue = null;
        missedIPVreasonValue = null;
        cardValue = null;
        OPVLastValue = null;
        OPVLastpriorValue = null;
        IPVValue = null;
        VaccinatedValue = null;
        routineValue = null;
        missedOPVLastreasonValue = null;
        missedOPVLastpriorreasonValue = null;
    }

    void FamilyClearData()

    {
        hhValue            = null;
        HeadNameValue      = null;
        yearschildrenValue = null;
        travelValue        = null;
        AfpValue           = null;
        addressValue       = null;
        caseNoValue        = null;
        ChildlistItems.clear();
    }

    int getindex(String value)

    {
        for(int i = 0 ; i < reasonArray.length ; i++)

        {
            if(value.equals(reasonArray[i]))
            {
                return i;
            }
        }

        return 0;
    }
}
