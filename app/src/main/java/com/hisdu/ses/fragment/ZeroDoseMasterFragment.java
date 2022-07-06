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
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.GenericResponse;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Database.ZeroDoseMaster;
import com.hisdu.ses.Models.sidModel;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.ZeroAdapter;
import com.hisdu.ses.utils.ServerCalls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZeroDoseMasterFragment extends Fragment {

    Button familyPopup, submitButton;
    private ArrayList<ZeroDoseDetail> zerodataSet;
    private List<ZeroDoseDetail> zerolistItems;
    private RecyclerView zero_ListView;
    private RecyclerView.Adapter Radapter;
    RelativeLayout contactid;
    FragmentManager fragmentManager;
    public static ZeroDoseMasterFragment zeroDoseMasterFragment;
    List<sidModel> SidList;
    Spinner sid;
    LinearLayout SidLayout;
    String sidValue = null, CampaignMonthValue = null;
    ImageButton CampaignMonth;
    TextView dob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.zero_dose_master_fragment, container, false);

        setHasOptionsMenu(true);

        familyPopup = view.findViewById(R.id.familyPopup);
        zero_ListView = view.findViewById(R.id.family_ListView);
        submitButton = view.findViewById(R.id.submitButton);
        contactid = view.findViewById(R.id.contactid);
        SidLayout = view.findViewById(R.id.SidLayout);
        sid = view.findViewById(R.id.sid);
        CampaignMonth = view.findViewById(R.id.CampaignMonth);
        dob = view.findViewById(R.id.dob);

        zeroDoseMasterFragment = this;

        zero_ListView.setHasFixedSize(true);
        zero_ListView.setLayoutManager(new GridLayoutManager(MainActivity.main, 2));

        fragmentManager = getFragmentManager();

        zerodataSet = new ArrayList<>();
        zerolistItems = new ArrayList<>();

        GetSIDLookup();

        CampaignMonth.setOnClickListener(view1 -> {

            final Dialog dlg = new Dialog(MainActivity.main);
            LinearLayout DateOfBirthViewParent = new LinearLayout(MainActivity.main);
            DateOfBirthViewParent.setOrientation(LinearLayout.VERTICAL);

            final DatePicker datePicker = new DatePicker(MainActivity.main);
            datePicker.setMaxDate(new Date().getTime());
            Button setDateButton = new Button(MainActivity.main);
            setDateButton.setText("Set");
            setDateButton.setOnClickListener(view11 -> {

                try

                {
                    int month = datePicker.getMonth()+1;
                    int year  = datePicker.getYear();
                    int day   = datePicker.getDayOfMonth();

                    CampaignMonthValue = year + "-" + month + "-" + day;

                    dob.setText("Campaign Month : " +  month + "-" + year);

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
            Fragment fragment = new ZeroDoseValidationFragment();
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

        zerolistItems.add(AppController.getInstance().zeroDoseDetail);

        if (zerolistItems.size() > 0) {
            contactid.setVisibility(View.GONE);
        } else {
            contactid.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < zerolistItems.size(); i++) {

            String name = null, contact = null, dob = null;
            boolean vacc  = false;

            if (zerolistItems.get(i).getChildName() != null) {
                name = zerolistItems.get(i).getChildName();
            }

            if (zerolistItems.get(i).getFatherName() != null) {
                contact = zerolistItems.get(i).getFatherName();
            }

            if (zerolistItems.get(i).getFatherName() != null) {
                contact = zerolistItems.get(i).getFatherName();
            }

            if (zerolistItems.get(i).getDOB() != null) {
                dob = zerolistItems.get(i).getDOB();
            }

            if (zerolistItems.get(i).getIsVaccinated() != null) {
                vacc = zerolistItems.get(i).getIsVaccinated();
            }

            zerodataSet.add(new ZeroDoseDetail(name, contact,dob,vacc));
        }

        Radapter = new ZeroAdapter(zerodataSet, MainActivity.main,false);
        zero_ListView.setAdapter(Radapter);

    }

    void submit() {

        final ProgressDialog PD = new ProgressDialog(MainActivity.main);
        PD.setMessage("Saving Zero Dose Record, Please Wait...");
        PD.setCancelable(false);
        PD.show();


        long r = AppController.indicatorMasterDataSave.save();

        if(r != -1)

        {
            int masterID = ZeroDoseMaster.getAll().size() + 1;

            ZeroDoseMaster sfr = new ZeroDoseMaster();
            //sfr.setZeroDoseDetails(zerolistItems);
            sfr.setTotalZeroDoseRecorded(zerolistItems.size());
            sfr.setSiaId(Integer.parseInt(sidValue));
            sfr.setLocationCode(MasterRecordFragment.locationCode);
            sfr.setCampaignMonth(CampaignMonthValue);
            sfr.setCreatedOn(AppController.date);
            sfr.setZeroDoseMasterId(masterID);
            sfr.setMastID(AppController.MasterID);
            sfr.setSync("0");
            sfr.setCreatedBy(Integer.valueOf(new SharedPref(getActivity()).GetserverID()));

            long res = sfr.save();

            if(res != -1)

            {
                for (int i = 0 ; i < zerolistItems.size() ; i++)

                {
                    zerolistItems.get(i).setZeroDoseMasterId(masterID);
                    zerolistItems.get(i).save();
                }

                PD.dismiss();
                Toast.makeText(MainActivity.main, "Record Saved Successfully!", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).addToBackStack(null).commit();
            }

            else { Toast.makeText(MainActivity.main, "Error saving record, please retry.", Toast.LENGTH_SHORT).show(); }
        }

        else { Toast.makeText(MainActivity.main, "Error saving master record, please retry.", Toast.LENGTH_SHORT).show(); }

//        ServerCalls.getInstance().SaveZeroDose(sfr, new ServerCalls.OnGenericResult() {
//            @Override
//            public void onSuccess(final GenericResponse response) {
//                PD.dismiss();
//                submitButton.setEnabled(true);
//
//                if (!response.getError()) {
//                    Toast.makeText(MainActivity.main, "Record Saved Successfully!", Toast.LENGTH_SHORT).show();
//                    fragmentManager.popBackStack();
//                } else {
//                    Toast.makeText(MainActivity.main, response.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailed(int errorCode, String errorMessage) {
//                PD.dismiss();
//                submitButton.setEnabled(true);
//
//                Toast.makeText(MainActivity.main, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public boolean validate() {
        boolean valid = true;

        String mDob= dob.getText().toString().trim();
        if (zerolistItems.size() == 0) {
            Toast.makeText(getActivity(), "Please enter at-least a person", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (sidValue == null) {
            Toast.makeText(getContext(), "Please select sid", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if(mDob.isEmpty()){
            Toast.makeText(getContext(), "Please select Campaign Month", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }


    void GetSIDLookup() {


        SidList = sidModel.getAllSid();

        if (SidList.size() != 0) {

            String[] districtsArray = new String[SidList.size() + 1];
            districtsArray[0] = "Select Campaign";

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


}