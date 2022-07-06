package com.hisdu.ses.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.AppController;
import com.hisdu.ses.CampaignSchedules;
import com.hisdu.ses.Database.ZeroDoseChildModel;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Database.ZeroDoseMain;
import com.hisdu.ses.Database.ZeroDoseMaster;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.ZeroDose.Designation;
import com.hisdu.ses.Models.sidModel;
import com.hisdu.ses.R;
import com.hisdu.ses.SchedulesResponse;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.ZeroAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ZeroDoseFormFragment extends Fragment {

    Button familyPopup, submitButton;
    private ArrayList<ZeroDoseDetail> zerodataSet;
    private List<ZeroDoseChildModel> zerolistItems;
    private RecyclerView zero_ListView;
    private RecyclerView.Adapter Radapter;
    RelativeLayout contactid;
    FragmentManager fragmentManager;
    public static ZeroDoseFormFragment zeroDoseMasterFragment;
    List<sidModel> SidList;
    List<Designation> DesignationList=new ArrayList<Designation>();
    List<CampaignSchedules> campaignSchedules;

    Spinner sid, designationSpinner, compaign_month, working_days;
    LinearLayout SidLayout,layout_campaign_month;
    String sidValue = null, CampaignMonthValue = null, SelectedMonth = null, WorkingDays = null, WorkingDay = null, SelectedDesignation = null;
    ImageButton CampaignMonth;
    String locationCode;
    TextView dob;
    EditText person_name, teamNO,PhoneNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.zero_dose_form_fragment, container, false);

        setHasOptionsMenu(true);

        familyPopup = view.findViewById(R.id.familyPopup);
        zero_ListView = view.findViewById(R.id.family_ListView);
        submitButton = view.findViewById(R.id.submitButton);
        contactid = view.findViewById(R.id.contactid);
        SidLayout = view.findViewById(R.id.SidLayout);
        sid = view.findViewById(R.id.sid);
        working_days = view.findViewById(R.id.working_days);
        CampaignMonth = view.findViewById(R.id.CampaignMonth);
        dob = view.findViewById(R.id.dob);
        //ZeroDose
        designationSpinner = view.findViewById(R.id.designation);
        compaign_month = view.findViewById(R.id.compaing_month);
        person_name = view.findViewById(R.id.person_name);
        teamNO = view.findViewById(R.id.teamNO);
        PhoneNo= view.findViewById(R.id.PhoneNo);
        layout_campaign_month= view.findViewById(R.id.layout_campaign_month);
        zeroDoseMasterFragment = this;

        zero_ListView.setHasFixedSize(true);
        zero_ListView.setLayoutManager(new GridLayoutManager(MainActivity.main, 2));

        fragmentManager = getFragmentManager();

        zerodataSet = new ArrayList<>();
        zerolistItems = new ArrayList<>();

        GetSIDLookup();
        ComapignMonths();
        WorkingDays();
        GetDesignation();
        CampaignMonth.setOnClickListener(view1 -> {

            final Dialog dlg = new Dialog(MainActivity.main);
            LinearLayout DateOfBirthViewParent = new LinearLayout(MainActivity.main);
            DateOfBirthViewParent.setOrientation(LinearLayout.VERTICAL);

            final DatePicker datePicker = new DatePicker(MainActivity.main);
            datePicker.setMaxDate(new Date().getTime());
            Button setDateButton = new Button(MainActivity.main);
            setDateButton.setText("Set");
            setDateButton.setOnClickListener(view11 -> {

                try {
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    int day = datePicker.getDayOfMonth();

                    CampaignMonthValue = year + "-" + month + "-" + day;

                    dob.setText("Campaign Month : " + month + "-" + year);

                    dlg.dismiss();

                } catch (Exception ex) {
                    Toast.makeText(MainActivity.main, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            DateOfBirthViewParent.addView(datePicker);
            DateOfBirthViewParent.addView(setDateButton);
            dlg.setContentView(DateOfBirthViewParent);
            dlg.show();
        });

        familyPopup.setOnClickListener(v ->

        {
            Fragment fragment = new ZeroDoseAddChildFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isList", true);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.content_frame, fragment).addToBackStack("ZeroDoseValidationFragment").commit();
        });

        sid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sid.getSelectedItemPosition() != 0) {
                    sidValue = String.valueOf(SidList.get(i - 1).getSiaId());
                    ComapignMonths();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SelectedDesignation = String.valueOf(DesignationList.get(i - 1).getDesignationId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        submitButton.setOnClickListener(v -> {
            submitButton.setEnabled(false);

            if (validate()) {
                submit();
//                if (AppController.getInstance().hasinternetAccess) {
//
//                } else {
//                    submitButton.setEnabled(true);
//                    Toast.makeText(MainActivity.main, "No internet access", Toast.LENGTH_SHORT).show();
//                }
            } else {
                submitButton.setEnabled(true);
            }
        });


        return view;
    }

    public void UpdateLogList() {
        zerodataSet.clear();

        zerolistItems.add(AppController.getInstance().zeroDoseChild);

        if (zerolistItems.size() > 0) {
            contactid.setVisibility(View.GONE);
        } else {
            contactid.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < zerolistItems.size(); i++) {

            String name = null, contact = null, dob = null;
            boolean vacc = false;

            if (zerolistItems.get(i).getChildName() != null) {
                name = zerolistItems.get(i).getChildName();
            }

            if (zerolistItems.get(i).getFatherName() != null) {
                contact = zerolistItems.get(i).getFatherName();
            }

            if (zerolistItems.get(i).getFatherName() != null) {
                contact = zerolistItems.get(i).getFatherName();
            }

            if (zerolistItems.get(i).getAge() != null) {
                dob = zerolistItems.get(i).getAge();
            }


            zerodataSet.add(new ZeroDoseDetail(name, contact, dob, false));
        }

        Radapter = new ZeroAdapter(zerodataSet, MainActivity.main,true);
        zero_ListView.setAdapter(Radapter);

    }

    void submit() {

        final ProgressDialog PD = new ProgressDialog(MainActivity.main);
        PD.setMessage("Saving Zero Dose Record, Please Wait...");
        PD.setCancelable(false);
        PD.show();


        String urserlocationcdoe = new SharedPref(getContext()).GetLocationID();

        int masterID = ZeroDoseMain.getAll().size() + 1;

        ZeroDoseMain zeroDoseMain = new ZeroDoseMain();
        zeroDoseMain.setEntryPersonDesignation(SelectedDesignation);
        zeroDoseMain.setCampaignMonth(SelectedMonth);
        zeroDoseMain.setPhoneNumber(PhoneNo.getText().toString());
        zeroDoseMain.setDay(WorkingDay);
        zeroDoseMain.setEntryPersonName(person_name.getText().toString());
        zeroDoseMain.setTeamNo(teamNO.getText().toString());
        zeroDoseMain.setSync("0");
        zeroDoseMain.setCreatedBy(Integer.valueOf(new SharedPref(getActivity()).GetserverID()));
        zeroDoseMain.setMastID(AppController.MasterID);
        if(urserlocationcdoe!=null){
            zeroDoseMain.setCreatedUserLocationCode(urserlocationcdoe);
        }
        zeroDoseMain.setCreatedOn(AppController.date);
        zeroDoseMain.setLocationCode(MasterRecordFragment.locationCode);
        zeroDoseMain.setCampaignType(sidValue);
//            ZeroDoseMaster sfr = new ZeroDoseMaster();
//            //sfr.setZeroDoseDetails(zerolistItems);
//            sfr.setTotalZeroDoseRecorded(zerolistItems.size());
//            sfr.setSiaId(Integer.parseInt(sidValue));
//            sfr.setLocationCode(MasterRecordFragment.locationCode);
//            sfr.setCampaignMonth(CampaignMonthValue);
//            sfr.setCreatedOn(AppController.date);
//            sfr.setZeroDoseMasterId(masterID);
//            sfr.setMastID(AppController.MasterID);
//            sfr.setSync("0");
//            sfr.setCreatedBy(Integer.valueOf(new SharedPref(getActivity()).GetserverID()));

        long res = zeroDoseMain.save();

        if (res != -1) {
            for (int i = 0; i < zerolistItems.size(); i++) {
                zerolistItems.get(i).setZeroDoseMasterId(masterID);

                long childRes = zerolistItems.get(i).save();
                if(childRes == -1) { Toast.makeText(MainActivity.main, "Error saving child record.", Toast.LENGTH_SHORT).show(); return; }
            }

            PD.dismiss();
            Toast.makeText(MainActivity.main, "ریکارڈ کامیابی سے محفوظ ہو گیا!", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).addToBackStack(null).commit();
        } else {
            Toast.makeText(MainActivity.main, "Error saving record, please retry.", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean validate() {
        boolean valid = true;

        String mDob = dob.getText().toString().trim();

        if (teamNO.getText().toString().isEmpty()) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم ایس آئی اے ٹیم نمبر درج کریں۔n۔")
                    .show();
            valid = false;
            return valid;
        }
        if (zerolistItems.size() == 0) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم کم از کم ایک بچہ شامل کریں۔")
                    .show();
             valid = false;
            return valid;
        }

        if (sidValue == null) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("برائے مہربانی پولیو مہم کو منتخب کریں۔")
                    .show();
            valid = false;
            return valid;

        }

        if (SelectedDesignation == null) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("برائے مہربانی عہدہ منتخب کریں")
                    .show();
            valid = false;
            return valid;
        }

        if (WorkingDay == null) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم کام کا دن منتخب کریں")
                    .show();
            valid = false;
            return valid;
        }

        if (person_name == null) {

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم اندراج کرنے والے شخص کا نام منتخب کریں۔")
                    .show();
            valid = false;
            return valid;

        }




        if (SelectedMonth == null) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم مہینہ منتخب کریں۔")
                    .show();
            valid = false;
            return valid;

        }

        if(PhoneNo.getText().toString().isEmpty()){
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("فون نمبر درج کریں۔")
                    .show();
            valid = false;
            return valid;
        }

        return valid;
    }


    void GetSIDLookup() {


        SidList = sidModel.getAllSid();

        if (SidList.size() != 0) {

            String[] districtsArray = new String[SidList.size() + 1];
            districtsArray[0] = "مہم منتخب کریں *";

            for (int i = 0; i < SidList.size(); i++) {
                districtsArray[i + 1] = SidList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            sid.setAdapter(districtsAdapter);
            SidLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getContext(), "Error Loading SIDs", Toast.LENGTH_SHORT).show();
        }
    }

    void ComapignMonths() {
        if(sidValue!=null){
            campaignSchedules=CampaignSchedules.getCampaignById(sidValue);
        }


//        String[] monthUrdu = {"مہینہ منتخب کریں *", "جنوری", "فروری", "مارچ", "اپریل", "مئی", "جون", "جولائی", "اگست", "ستمبر", "اکتوبر", "نومبر", "دسمبر"};
//        String[] months = {"Please Select Month *", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, monthUrdu);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        compaign_month.setAdapter(aa);
//        compaign_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//                    String currentYear = sdf.format(Calendar.getInstance().getTime());
//                    SelectedMonth = months[position]+","+currentYear;
//                } else {
//                    SelectedMonth = null;
//                }
//            } // to close the onItemSelected
//
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        if (campaignSchedules != null) {

            if(campaignSchedules.size() >0){
                layout_campaign_month.setVisibility(View.VISIBLE);
            String[] districtsArray = new String[campaignSchedules.size() + 1];
            districtsArray[0] = "براہ کرم مہینہ منتخب کریں *";

            for (int i = 0; i < campaignSchedules.size(); i++) {
                districtsArray[i + 1] = campaignSchedules.get(i).getCampaignMonthTitle();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            compaign_month.setAdapter(districtsAdapter);
            compaign_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        SelectedMonth = campaignSchedules.get(position-1).getCampaignMonth();
                    } else {
                        SelectedMonth = null;
                    }
                } // to close the onItemSelected

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            }else {
                layout_campaign_month.setVisibility(View.GONE);
                SelectedMonth = null;
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("")
                        .setContentText("مہم کا کوئی مہینہ نہیں ملا!")
                        .show();
            }

        }
    }

    void WorkingDays() {
        String[] workingdays = {"کام کا دن منتخب کریں *", "1", "2", "3","4","5"};
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, workingdays);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        working_days.setAdapter(aa);
        working_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    WorkingDay = workingdays[position];
                } else {
                    WorkingDay = null;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void GetDesignation() {

        DesignationList.clear();
        DesignationList = Designation.getAll();

        if (DesignationList.size() != 0) {

            String[] districtsArray = new String[DesignationList.size() + 1];
            districtsArray[0] = "عہدہ منتخب کریں *";

            for (int i = 0; i < DesignationList.size(); i++) {
                districtsArray[i + 1] = DesignationList.get(i).getDesignationName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            designationSpinner.setAdapter(districtsAdapter);

        } else {
            Toast.makeText(getContext(), "Error Loading SIDs", Toast.LENGTH_SHORT).show();
        }
    }
}