package com.hisdu.ses.ZeroDoseVaccination;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.hisdu.SESCluster.interfaces.onChildSaveClick;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.AppController;
import com.hisdu.ses.Models.RefusalListItem;
import com.hisdu.ses.Models.RefusalReasonModel;
import com.hisdu.ses.Models.ZeroDose.Designation;
import com.hisdu.ses.Models.ZeroDose.DesignationModel;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.Models.feedback.feedbackResponse;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.ServerCalls;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChildDetailFragment extends Fragment {


    private TextView child_name, child_address, child_age, child_father, child_house_no, child_campaign_month,
            child_team_no, child_entry_person_name, child_uc_name,district,last_refusel_reason,vacinator_mobile,child_mobile,location_child;
    private Button submit;
//    private ImageView card_front_image;
    private static final int IMAGE_ONE = 101;
    private static final int IMAGE_TWO = 102;
    String base64Image1 = "";
    String base64Image2 = "";
    private ChildModelData child;
    onChildSaveClick onChildClick;
    Spinner isvaccinatedSpinner,not_vac_reason,designationSpinner;
    String refusel_reason_selected=null,SelectedDesignation = null;
    Boolean isChildVaccinated=null,notVacReason=null;
    EditText card_number;
    String[] optionArray = {"کیا بچے کو ویکسین لگائی گئی ہے*","ہاں","نہیں"};
    String[] not_vac_array = {"ویکسین نا لگنے کی وجوہات*","انکاری","موجود نہیں","بیمار ہے","تلاش نہیں ہو پایا","فوت ہو گیا"};

    List<String> designationArray =  new ArrayList<>(
            Arrays.asList("عہدہ منتخب کریں","Vaccinator","LHV","LHW","Others")
    );

    private LinearLayout vac_reason_layout,image_layout,SidLayout,last_refusel_layout;
    private ArrayList<RefusalListItem> refuselreasons = new ArrayList<>();
    public ChildDetailFragment(onChildSaveClick onChildClick) {
        this.onChildClick=onChildClick;
    }
    List<Designation> DesignationList=new ArrayList<Designation>();
    EditText person_name, teamNO,PhoneNo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.child_detail_layout, container, false);

        //TextViews
        child_name = view.findViewById(R.id.child_name);
        location_child = view.findViewById(R.id.location_child);

        child_address = view.findViewById(R.id.child_address);
        child_age = view.findViewById(R.id.child_age);
        child_father = view.findViewById(R.id.child_father);
        child_house_no = view.findViewById(R.id.child_house_no);
        child_campaign_month = view.findViewById(R.id.child_campaign_month);
        child_team_no = view.findViewById(R.id.child_team_no);
        child_entry_person_name = view.findViewById(R.id.child_entry_person_name);
        child_uc_name = view.findViewById(R.id.child_uc_name);
        SidLayout= view.findViewById(R.id.SidLayout);
        child_mobile= view.findViewById(R.id.child_mobile);
        person_name = view.findViewById(R.id.person_name);

        PhoneNo= view.findViewById(R.id.PhoneNo);
        //ImageView
//        card_front_image = view.findViewById(R.id.card_front_image);
        isvaccinatedSpinner = view.findViewById(R.id.is_vaccinated);
        vac_reason_layout= view.findViewById(R.id.vac_reason_layout);
        image_layout= view.findViewById(R.id.image_layout);
        district= view.findViewById(R.id.district);
        //Buttons
        vacinator_mobile = view.findViewById(R.id.vacinator_mobile);
//        card_one = view.findViewById(R.id.card_one);
        card_number = view.findViewById(R.id.card_number);
        designationSpinner = view.findViewById(R.id.designation);

//        card_two = view.findViewById(R.id.card_two);
        submit=view.findViewById(R.id.submit);
        not_vac_reason=view.findViewById(R.id.not_vac_reason);
        last_refusel_layout=view.findViewById(R.id.last_refusel_layout);
        last_refusel_reason=view.findViewById(R.id.last_refusel_reason);
        Bundle args = getArguments();
        String personJsonString = args.getString("child");
        child = Utils.getGsonParser().fromJson(personJsonString, ChildModelData.class);
        child_mobile.setText(child.getPhoneNo());

        location_child.setText(child.getHRMP());
        child_name.setText(child.getChildName());
        child_address.setText(child.getAddress());
        child_age.setText(child.getAge());
        child_father.setText(child.getFatherName());
        child_house_no.setText(child.getHouseNo());
        child_campaign_month.setText(child.getCampaignMonth());
        child_team_no.setText(child.getTeamNo().toString());
        child_entry_person_name.setText(child.getEntryPersonName());
        child_uc_name.setText(child.getUCName());
        district.setText(child.getDistrictName());
        if(child.getCheckedReason()!=null){
            last_refusel_reason.setText(child.getCheckedReason());
        }
        vacinator_mobile.setText(child.getTeamContactNo());


        if(child.getAlreadyChecked()!=null){
            if(child.getAlreadyChecked()){
                vac_reason_layout.setVisibility(View.GONE);
                image_layout.setVisibility(View.VISIBLE);
                SidLayout.setVisibility(View.GONE);
                last_refusel_layout.setVisibility(View.VISIBLE);
            }
        }

        GetDesignation();
//        card_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePicker.with(Objects.requireNonNull(ChildDetailFragment.this))
//                        .compress(1024)
//                        .cameraOnly()//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                        .start(IMAGE_ONE);
//
//
//            }
//        });

//        card_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePicker.with(Objects.requireNonNull(ChildDetailFragment.this))
//                        .compress(1024)
//                        .cameraOnly()//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                         .start(IMAGE_TWO);
//            }
//        });
        getRefuselReason();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidForm()){
                    submit();
                }
            }
        });

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, optionArray);
        isvaccinatedSpinner.setAdapter(mAdapter);
        isvaccinatedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isvaccinatedSpinner.getSelectedItemPosition() != 0) {
                    if(isvaccinatedSpinner.getSelectedItemPosition()==1){
                        isChildVaccinated=true;
                        vac_reason_layout.setVisibility(View.GONE);
                        image_layout.setVisibility(View.VISIBLE);
                    }else{
                        isChildVaccinated=false;
                        vac_reason_layout.setVisibility(View.VISIBLE);
                        image_layout.setVisibility(View.GONE);
                    }
                }else {
                    isChildVaccinated=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        return view;
    }

    public void submit(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());


        String userid= new SharedPref(getActivity()).GetserverID();
        SaveChildModel savechild = new SaveChildModel();
        savechild.setVaccinationId(child.getChildId());
        savechild.setRegistrationChildId(child.getChildId());
        savechild.setBackImageUrl(base64Image2);
        savechild.setCardNo(card_number.getText().toString());
        savechild.setFrontImageUrl(base64Image1);
        savechild.setMissedProfileId(refusel_reason_selected);
        savechild.setEntryPersonDesignation(SelectedDesignation);
        savechild.setEntryPersonName(person_name.getText().toString());
        savechild.setTeamContactNo(PhoneNo.getText().toString());
        if(!userid.isEmpty()){
            savechild.setCreatedBy(Integer.parseInt(userid));
            savechild.setUpdatedBy(Integer.parseInt(userid));
        }
        savechild.setCreatedOn(currentDate);
        savechild.setUpdatedOn(currentDate);
        if(isChildVaccinated!=null){
            savechild.setIsVaccinated(isChildVaccinated);
        }
        sendData(savechild);
    }

    private boolean isValidForm() {
        if(isChildVaccinated==null){
            if(card_number.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"برائے مہربانی کارڈ نمبر درج کریں۔",Toast.LENGTH_LONG).show();
                return false;
            }
        }else {
            if(isChildVaccinated ){
//           if(base64Image1.isEmpty()){
//               Toast.makeText(getContext(),"Please Take Card Picture",Toast.LENGTH_LONG).show();
//               return false;
//           }
                if(card_number.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"برائے مہربانی کارڈ نمبر درج کریں۔",Toast.LENGTH_LONG).show();
                    return false;
                }
            }else {
                if(refusel_reason_selected==null){
                    Toast.makeText(getContext(),"ویکسینیشن سے انکار کی وجہ منتخب کریں",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        if(SelectedDesignation==null){
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("برائے مہربانی عہدہ منتخب کریں")
                    .show();
            return false;
        }
        if(person_name.getText().toString().isEmpty()){
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("براہ کرم اندراج کرنے والے شخص کا نام منتخب کریں۔")
                    .show();
            return false;
        }


        if(PhoneNo.getText().toString().isEmpty()){
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentText("فون نمبر درج کریں۔")
                    .show();
            return false;
        }

        return true;
    }

    public void imagePicker() {
        ImagePicker.with(Objects.requireNonNull(getActivity()))
                .compress(1024)
                .cameraOnly()//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)
                .start(IMAGE_ONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            switch (requestCode) {
                case IMAGE_ONE:
//                    card_front_image.setImageURI(uri);
                    base64Image1 = getFileToByte(String.valueOf(new File(uri.getPath())));
                    break;
                case IMAGE_TWO:
//                    card_back_image.setImageURI(uri);
//                    base64Image2 = getFileToByte(String.valueOf(new File(uri.getPath())));
//                    getFileToByte(String.valueOf(new File(uri.getPath())));
                    break;
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
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

    void sendData(final SaveChildModel data) {
        ProgressDialog  PD = new ProgressDialog(getActivity());
        PD.setTitle("Syncing Record, Please wait...");
        PD.setCancelable(false);
        PD.show();

        if (!PD.isShowing()) {
            PD.show();
        }
        Gson gson = new Gson();
        String json = gson.toJson(data);
        Double latitude= AppController.getInstance().location.getLatitude();
        Double longitude= AppController.getInstance().location.getLongitude();

        ServerCalls.getInstance().SaveChildVaccination(
                data.getBackImageUrl(),
                data.getFrontImageUrl(),
                refusel_reason_selected,
                data.getRegistrationChildId()+"",
                data.getUpdatedBy()+"",
                data.getUpdatedOn(),
                data.getVaccinationId()+"",
                data.getCreatedBy()+"",
                data.getCreatedOn(),
                isChildVaccinated,
                "",
                data.getTeamContactNo(),
                data.getEntryPersonName(),
                data.getEntryPersonDesignation(),
                data.getCardNo(),
                latitude.toString(),
                longitude.toString()
                , new ServerCalls.OnChildSaveResult() {
            @Override
            public void onSuccess(feedbackResponse response) {
                PD.dismiss();
                if(response.getStatusCode()==201){
                    Toast.makeText(getContext(),"Record Submitted Successfully",Toast.LENGTH_LONG).show();
                    onChildClick.onChildSaved();
                    getActivity().onBackPressed();
                    getActivity().onBackPressed();
                }else {
                    Toast.makeText(getContext(),response.getMessage(),Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                PD.dismiss();
            }
        });

//        ServerCalls.getInstance().SaveOfflineData(SL, new ServerCalls.OnGenericResult() {
//            @Override
//            public void onSuccess(final GenericResponse response) {
//                syncButton.setEnabled(true);
//
////                if (!response.getIsException()) {
//                    if (SL.serverid != null) {
//                        SaveInspections.UpdateData(String.valueOf(SL.getServerid()));
//                        SaveChecklist.UpdateData(SL.getServerid());
//                        ClusterForm.UpdateData(SL.getServerid());
//                    }
//
//                    CurrentIndex = CurrentIndex + 1;
//
//                    SaveUnsyncData();
//                } else {
//                    PD.dismiss();
//                    syncButton.setEnabled(true);
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                    alertDialogBuilder.setTitle("Error!");
//                    alertDialogBuilder.setMessage(response.getMessage());
//                    alertDialogBuilder.setCancelable(false);
//                    alertDialogBuilder
//                            .setCancelable(false)
//                            .setPositiveButton("Retry",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            SaveUnsyncData();
//                                        }
//                                    })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                    updateEntry();
//                                }
//                            });
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                }
//            }
//
//            @Override
//            public void onFailed(int errorCode, String errorMessage) {
//                PD.dismiss();
//                syncButton.setEnabled(true);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                alertDialogBuilder.setTitle(errorMessage);
//                alertDialogBuilder.setCancelable(false);
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("Ok",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                        updateEntry();
//                                        CurrentIndex = 0;
//                                    }
//                                });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//            }
//        });
    }


    void getRefuselReason() {

        if (AppController.getInstance().hasinternetAccess) {
            ServerCalls.getInstance().GetRefusalReason(new ServerCalls.OnRefualReasonResult() {

                @Override
                public void onResult(RefusalReasonModel designationModel) {

                    refuselreasons.addAll(designationModel.list);
                    ArrayList<String> array=new ArrayList<String>();
                    for(int i=0;i<designationModel.list.size();i++){
                        array.add(designationModel.list.get(i).getRefusalReason());
                    }
                    array.add(0,"ویکسین نا لگنے کی وجوہات*");

                    ArrayAdapter<String> Adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, array);
                    not_vac_reason.setAdapter(Adapter);
                    not_vac_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (not_vac_reason.getSelectedItemPosition() != 0) {
                                refusel_reason_selected=refuselreasons.get(i-1).refusalId+"";
                            }else {
                                refusel_reason_selected=null;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onRequestFailed(int errorCode, String errorMessage) {

                }
            });
        }
    }
    void GetDesignation() {

//        DesignationList.clear();
//        DesignationList = Designation.getAll();




            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, designationArray);
            designationSpinner.setAdapter(districtsAdapter);


        designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SelectedDesignation = designationArray.get(i );

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
