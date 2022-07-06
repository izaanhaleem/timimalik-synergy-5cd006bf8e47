package com.hisdu.ses.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.GenericResponse;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.ServerCalls;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GeoTagFragment extends Fragment {

    Spinner      district,tehsil,uc,hf,center;

    LinearLayout districtLayout,tehsilLayout,UcLayout,hfLayout,centerLayout;

    String       districtValue = null, tehsilValue = null,ucValue = null,centerValue = null;

    List<Location> DistrictList = new ArrayList<>();

    List<Location> TehsilList = new ArrayList<>();

    List<UcData> UcsList = new ArrayList<>();

    List<UcData> CenterList = new ArrayList<>();

    Button proceed;

    ProgressDialog PD;

    Integer LocationValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.geo_tag_fragment, container, false);

        district        = view.findViewById(R.id.sid);
        tehsil          = view.findViewById(R.id.tehsil);
        uc              = view.findViewById(R.id.uc);
        hf              = view.findViewById(R.id.hf);
        districtLayout  = view.findViewById(R.id.SidLayout);
        tehsilLayout    = view.findViewById(R.id.tehsilLayout);
        UcLayout        = view.findViewById(R.id.UcLayout);
        centerLayout    = view.findViewById(R.id.centerLayout);
        center          = view.findViewById(R.id.center);
        proceed         = view.findViewById(R.id.proceed);

        PD = new ProgressDialog(getActivity());

        LocationValue = new SharedPref(getContext()).GetLocationID().length();

        if(LocationValue <= 3)

        {
            getDistrict();
        }

        else if (LocationValue == 6)

        {
            districtValue = new SharedPref(getContext()).GetLocationID();
            getTehsil();
        }

        else

        {
            tehsilValue = new SharedPref(getContext()).GetLocationID();
            getUCs();
        }

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)

            {
                if (district.getSelectedItemPosition() != 0)

                {
                    districtValue  = DistrictList.get(i-1).getServer_id();
                    getTehsil();
                }

                else

                {
                    tehsilLayout.setVisibility(View.GONE);
                    tehsil.setSelection(0);
                    tehsilValue = null;

                    UcLayout.setVisibility(View.GONE);
                    uc.setSelection(0);
                    ucValue = null;

                    centerLayout.setVisibility(View.GONE);
                    center.setSelection(0);
                    centerValue = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }});

        tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)

            {
                if (tehsil.getSelectedItemPosition() != 0)

                {
                    tehsilValue = TehsilList.get(i-1).getServer_id();
                    getUCs();
                }

                else

                {
                    UcLayout.setVisibility(View.GONE);
                    uc.setSelection(0);
                    ucValue = null;

                    centerLayout.setVisibility(View.GONE);
                    center.setSelection(0);
                    centerValue = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});

        uc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)

            {
                if (uc.getSelectedItemPosition() != 0)

                {
                    ucValue = UcsList.get(i-1).getUCName();
                    getCenters();
                }

                else

                {
                    ucValue = null;

                    centerLayout.setVisibility(View.GONE);
                    center.setSelection(0);
                    centerValue = null;
                }

            }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }});

        center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)

            {
                if (center.getSelectedItemPosition() != 0)

                {
                    if(CenterList.get(i-1).getLatitude() == null && CenterList.get(i-1).getLongitude() == null)

                    {
                        centerValue = CenterList.get(i-1).getServerID();
                    }

                    else

                        {
                            Toast.makeText(MainActivity.main, "Location Already Tagged!", Toast.LENGTH_SHORT).show();
                            center.setSelection(0);
                        }
                }

                else

                {
                    centerValue = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});



        proceed.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)

            {
                if(validate())

                {
                    if(AppController.getInstance().hasinternetAccess)

                    {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText("You want to save Form!")
                                .setConfirmText("Yes,Go Save!")
                                .setCancelText("No")

                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();

                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        proceed.setEnabled(false);
                                        Submit();
                                        sDialog.dismiss();
                                    }
                                })
                                .show();

                    }

                    else

                        {
                            Toast.makeText(MainActivity.main, "No Internet Access!", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        return view;
    }


    void Submit()

    {
        PD.setTitle("Saving Record, Please wait...");
        PD.setCancelable(false);
        PD.show();

        UcData si = new UcData();

        si.setServerID(centerValue);
        si.setLatitude(AppController.getInstance().location.getLatitude());
        si.setLongitude(AppController.getInstance().location.getLongitude());

        ServerCalls.getInstance().SaveGeoTagData(si, new ServerCalls.OnGenericResult() {
            @Override
            public void onSuccess(final GenericResponse response)

            {
                PD.dismiss();
                proceed.setEnabled(true);

                if (!response.getIsException())

                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setTitle("Center Tagged Successfully!");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id)

                                        {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                else

                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setTitle(response.getMessage());
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id)

                                        {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailed(int errorCode, String errorMessage)

            {
                PD.dismiss();
                proceed.setEnabled(true);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle(errorMessage);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id)

                                    {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public boolean validate()

    {
        boolean valid = true;

        if(LocationValue <= 3 && districtValue == null)

        {
            Toast.makeText(getContext(), "Please select district", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (tehsilValue == null)

        {
            Toast.makeText(getContext(), "Please select tehsil/town", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (ucValue == null)

        {
            Toast.makeText(getContext(), "Please enter UC", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (centerValue == null)

        {
            Toast.makeText(getContext(), "Please select center name", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (AppController.getInstance().location == null)

        {
            Toast.makeText(getContext(), "Location unavailable, please try again in couple of seconds!", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    void getDistrict()

    {

        String code = new SharedPref(MainActivity.main).GetLocationID();

        DistrictList = Location.getDistrict(code);

        if(DistrictList.size() != 0)

        {

            String[] districtsArray = new String[DistrictList.size()+1];
            districtsArray[0] = "Select District";

            for (int i = 0; i < DistrictList.size(); i++)

            {
                districtsArray[i+1] = DistrictList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            district.setAdapter(districtsAdapter);
            districtLayout.setVisibility(View.VISIBLE);

        }

        else { Toast.makeText(getContext(), "Error Loading Districts", Toast.LENGTH_SHORT).show(); }

    }

    void getTehsil()

    {

        TehsilList = Location.getTehsil(districtValue);

        if(TehsilList.size() != 0)

        {
            String[] tehsilArray = new String[TehsilList.size()+1];
            tehsilArray[0] = "Select Tehsil";

            for (int i = 0; i < TehsilList.size(); i++)

            {
                tehsilArray[i+1] = TehsilList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            tehsil.setAdapter(districtsAdapter);
            tehsilLayout.setVisibility(View.VISIBLE);

        }

        else {Toast.makeText(getContext(), "No tehsil found against above selection", Toast.LENGTH_SHORT).show();}

    }

    void getUCs()

    {
        UcsList.clear();

        UcsList = UcData.getUcs(tehsilValue);

        if(UcsList.size() != 0)

        {
            String[] tehsilArray = new String[UcsList.size()+1];
            tehsilArray[0] = "Select UC";

            for (int i = 0; i < UcsList.size(); i++)

            {
//                if(!ArrayUtils.contains(tehsilArray,UcsList.get(i).getUCName()))
//
//                {
                    tehsilArray[i+1] = UcsList.get(i).getUCName();
                //}
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            uc.setAdapter(districtsAdapter);
            UcLayout.setVisibility(View.VISIBLE);

        }

        else {Toast.makeText(getContext(), "No UC found against above selection", Toast.LENGTH_SHORT).show();}

    }

    void getCenters()

    {

        CenterList.clear();

        CenterList = UcData.getUcCenter(tehsilValue,ucValue);

        if(CenterList.size() != 0)

        {
            String[] tehsilArray = new String[CenterList.size()+1];
            tehsilArray[0] = "Select Center";

            for (int i = 0; i < CenterList.size(); i++)

            {
                tehsilArray[i+1] = CenterList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            center.setAdapter(districtsAdapter);
            centerLayout.setVisibility(View.VISIBLE);

        }

        else

        {
            centerLayout.setVisibility(View.GONE);
            center.setSelection(0);
            centerValue = null;

            Toast.makeText(getContext(), "No center found against above selection", Toast.LENGTH_SHORT).show();

        }

    }

}
