package com.hisdu.ses.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeandroid.ActiveAndroid;
import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.CheckListDetail;
import com.hisdu.ses.Database.CheckListSend;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.SaveCheckListVaccination;
import com.hisdu.ses.IndicatorsAdapter;
import com.hisdu.ses.IndicatorsChildAdapter;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.indicators.CheckListImage;
import com.hisdu.ses.Models.indicators.Indicator;
import com.hisdu.ses.Models.indicators.IndicatorsData;
import com.hisdu.ses.Models.indicators.IndicatorsResponse;
import com.hisdu.ses.Models.indicators.SubIndicator;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.MyPreferences;
import com.hisdu.ses.utils.ServerCalls;
import com.iceteck.silicompressorr.SiliCompressor;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class IndicatorsFragment extends Fragment implements IndicatorsAdapter.ChecklistAdapterListener {


    String remarksValue = null, Answer_value = null, createdBy, Username;
    public Context context;
    private RecyclerView RV;
    private List<Indicator> serverlistItems =  new ArrayList<>();
    public List<CheckListSend> listItems =  new ArrayList<>();
    private IndicatorsAdapter mAdapter;
    EditText remarks;
    Button save, back;
    int position = 0;
    FragmentManager fragmentManager;
    String QuestionID, checklistType;
    MyPreferences myPreferences;
    public static IndicatorsFragment CF;
    IndicatorMasterDataSave indicatorMasterDataSave;
    LinearLayout attachImages;
    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    TextView imagesCount;
    List<String> indicatorsMasterSaveImages = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        myPreferences = MyPreferences.getPreferences(getActivity());
        RV = view.findViewById(R.id.recyclerView);
        remarks = view.findViewById(R.id.remarks);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);
        CF = this;
        indicatorMasterDataSave = AppController.indicatorMasterDataSave;
        createdBy = new SharedPref(getActivity()).GetserverID();
        Username = new SharedPref(getActivity()).GetLoggedInUser();

        RV.setDrawingCacheEnabled(true);
        RV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(context));
        RV.getRecycledViewPool().setMaxRecycledViews(listItems.size(), 0);

        attachImages = view.findViewById(R.id.AttachImages);
        imagesCount = view.findViewById(R.id.imagesCount);

        fragmentManager = getFragmentManager();

        LoadIndicators();

        save.setOnClickListener(v -> {
            Submit();


        });

        back.setOnClickListener(v -> {


            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Form Data will be lost if you go back!")
                    .setConfirmText("Yes,Go Back!")
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
                            if (fragmentManager.getBackStackEntryCount() > 0) {
                                fragmentManager.popBackStack();
                            }
                            sDialog.dismiss();
                        }
                    })
                    .show();

        });

        remarks.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && remarks.isEnabled()) {
                if (remarks.length() != 0) {
                    remarksValue = remarks.getText().toString();
                } else {
                    remarksValue = null;
                }

            }
        });

        attachImages.setOnClickListener(v -> multiImagePicker());


        return view;
    }


    private void multiImagePicker() {
        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void LoadIndicators()

    {
        indicatorsMasterSaveImages.clear();
        serverlistItems.clear();
        serverlistItems = Indicator.getAllIndicators(AppController.appModuleID.toString());

        if(serverlistItems.size() > 0)

        {
            for (int i = 0; i < serverlistItems.size(); i++)

            {
                CheckListSend checkListSend = new CheckListSend();

                List<SubIndicator> subIndicatorList = SubIndicator.getAllSubIndicators(serverlistItems.get(i).getIndicatorId().toString());

                checkListSend.setCreatedBy(new SharedPref(getActivity()).GetserverID());
                checkListSend.setQuestion(serverlistItems.get(i).getQuestion());
                checkListSend.setIndicatorId(serverlistItems.get(i).getIndicatorId());
                checkListSend.setAppModuleId(AppController.appModuleID);
                checkListSend.setCreatedOn(AppController.date);
                checkListSend.setSubIndicators(subIndicatorList);
                checkListSend.setSync("0");
                checkListSend.setSrNo(serverlistItems.get(i).getSrNo());
                checkListSend.setOptional(serverlistItems.get(i).getOptional());
                checkListSend.setFieldType(serverlistItems.get(i).getFieldType());
                checkListSend.setNAShow(serverlistItems.get(i).getNAShow());
                checkListSend.setRemarksMandatory(serverlistItems.get(i).getRemarksMandatory());
                checkListSend.setRemarksPlaceHolderText(serverlistItems.get(i).getRemarksPlaceHolderText());
                checkListSend.setRemarksShow(serverlistItems.get(i).getRemarksShow());
                checkListSend.setShowRemarksInCase(serverlistItems.get(i).getShowRemarksInCase());
                checkListSend.setShowInCase(serverlistItems.get(i).getShowInCase());

                listItems.add(checkListSend);

            }

            AppController.saveIndicators = listItems;

            mAdapter = new IndicatorsAdapter(listItems, "", getActivity(), this);
            RV.setItemViewCacheSize(listItems.size());
            RV.setAdapter(mAdapter);
        }

        else

        {
            Toast.makeText(getContext(), "Failed to load indicators", Toast.LENGTH_SHORT).show();
        }

        checklistType = AppController.checklistType;

    }

    @Override
    public void onChecklistSelected(CheckListSend listItem, int pos, String Answer, String AnswerRemarks) {
        QuestionID = listItem.getIndicatorId().toString();
        position = pos;
        Answer_value = Answer;
        listItems.get(position).setAnswer(Answer_value);
        listItems.get(position).setCreatedBy(new SharedPref(getActivity()).GetserverID());
        listItems.get(position).setIndicatorId(listItem.getIndicatorId());
        listItems.get(position).setComments(AnswerRemarks);
        listItems.get(position).setCreatedOn(AppController.date);
        listItems.get(position).setMastID(AppController.MasterID);
        update(pos);
    }

    @Override
    public void onChecklistChildSelected(CheckListSend listItem,int ParentPos, int ChildPos, String Answer, String answerRemarks)

    {
        listItems.get(ParentPos).getSubIndicatorsValidation().get(ChildPos).setAnswer(Answer);
        listItems.get(ParentPos).getSubIndicatorsValidation().get(ChildPos).setComments(answerRemarks);
    }

    @Override
    public void onUpdateSubchildSelected(List<SubIndicator> listItem, int pos)

    {
        listItems.get(pos).setSubIndicatorsValidation(listItem);
//        update(pos);
    }

    void update(int pos)

    {
        if(!RV.isComputingLayout()){mAdapter.notifyItemChanged(pos);}
        else {RV.post(() -> mAdapter.notifyItemChanged(pos));}
    }

    void Submit() {

        if (validate()) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("You want to save Form!")
                    .setConfirmText("Yes,Go Save!")
                    .setCancelText("No")

                    .setCancelClickListener(sDialog -> sDialog.dismiss())
                    .setConfirmClickListener(sDialog -> {
                        final ProgressDialog PD = new ProgressDialog(getActivity());
                        PD.setMessage("Saving Record, Please Wait...");
                        PD.show();
                        int a = 0;
                        long res = AppController.indicatorMasterDataSave.save();

                        if (res != -1)

                        {
                            for (int i = 0; i < listItems.size(); i++) {

                                CheckListSend si = new CheckListSend();
                                si.setCreatedBy(listItems.get(i).getCreatedBy());
                                si.setComments(listItems.get(i).getComments());
                                si.setAnswer(listItems.get(i).getAnswer());
                                si.setCreatedOn(listItems.get(i).getCreatedOn());
                                si.setIndicatorId(listItems.get(i).getIndicatorId());
                                si.setAppModuleId(listItems.get(i).getAppModuleId());
                                si.setMastID(listItems.get(i).getMastID());
                                si.setSync("0");

                                long r = si.save();

                                if(listItems.get(i).getAnswer() != null && listItems.get(i).getSubIndicatorsValidation() != null && listItems.get(i).getSubIndicatorsValidation().size() > 0)

                                {
                                    for (int j = 0; j < listItems.get(i).getSubIndicatorsValidation().size(); j++) {

                                        if(listItems.get(i).getAnswer().equals(something(listItems.get(i).getSubIndicatorsValidation().get(j).getShowInCase())))

                                        {
                                            CheckListSend child = new CheckListSend();
                                            child.setCreatedBy(listItems.get(i).getCreatedBy());
                                            child.setComments(listItems.get(i).getSubIndicatorsValidation().get(j).getComments());
                                            child.setAnswer(listItems.get(i).getSubIndicatorsValidation().get(j).getAnswer());
                                            child.setCreatedOn(listItems.get(i).getCreatedOn());
                                            child.setIndicatorId(listItems.get(i).getSubIndicatorsValidation().get(j).getIndicatorId());
                                            child.setAppModuleId(listItems.get(i).getAppModuleId());
                                            child.setMastID(listItems.get(i).getMastID());
                                            child.setSync("0");
                                            child.save();
                                        }

                                    }
                                }

                                if (r != -1) { a++; }
                                else { break; }

                            }

                            for (int b = 0; b < indicatorsMasterSaveImages.size(); b++) {
                                CheckListImage checkListImage = new CheckListImage();
                                checkListImage.setMasterID(AppController.MasterID);
                                checkListImage.setImageUrl(indicatorsMasterSaveImages.get(b));
                                checkListImage.setSync("0");
                                checkListImage.save();
                            }

                            if (a == listItems.size()) {
                                PD.dismiss();
                                moveNext();
                            } else {
                                PD.dismiss();
                                IndicatorMasterDataSave.DeleteData(AppController.indicatorSave.getMastID());
                                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                            }

                        }

                        else

                        {
                            PD.dismiss();
                            Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                        }
                        sDialog.dismiss();
                    })
                    .show();


            //indicatorMasterDataSave.setCheckListDetails(checkListSendList);
        }

    }

    void moveNext() {

        Toast.makeText(getActivity(), "Record Save Successfully!", Toast.LENGTH_LONG).show();
        AppController.saveIndicators = new ArrayList<>();
        AppController.MasterID = null;
        AppController.location = null;
        AppController.date = null;
        AppController.dateOnly = null;
        fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).commit();


    }


    public boolean validate() {
        boolean valid = true;

        remarks.clearFocus();

        if (listItems == null || listItems.size() == 0)

        {
            valid = false;
//            Toast.makeText(getActivity(), "Error getting checklist! please reload.", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("rror getting checklist! please reload.!")
                    .show();
            return valid;
        }

        else

        {
            outer:
            for (int i = 0; i < listItems.size(); i++)

            {
                if (listItems.get(i).getAnswer() == null && (listItems.get(i).getOptional() != null && !listItems.get(i).getOptional()) && !listItems.get(i).getFieldType().equals("Label")) {
                    valid = false;
//                    Toast.makeText(getActivity(), "Please select "+ listItems.get(i).getQuestion(), Toast.LENGTH_LONG).show();

                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please select "+ listItems.get(i).getQuestion())
                            .show();
                    return valid;

                }

                if(listItems.get(i).getShowRemarksInCase() != null)

                {
                    if(listItems.get(i).getRemarksMandatory() && listItems.get(i).getComments().equals(""))

                    {
                        if(listItems.get(i).getShowRemarksInCase() == 1 && listItems.get(i).getAnswer().equals("Yes"))

                        {
                            valid = false;
                            Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Please enter "+ listItems.get(i).getRemarksPlaceHolderText())
                                    .show();
                            return valid;
                        }

                        else if(listItems.get(i).getShowRemarksInCase() == 2 && listItems.get(i).getAnswer().equals("No"))

                        {
                            valid = false;
                            Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Please enter "+ listItems.get(i).getRemarksPlaceHolderText())
                                    .show();
                            return valid;
                        }

                        else if(listItems.get(i).getShowRemarksInCase() == 3 && listItems.get(i).getAnswer().equals("N/A"))

                        {
                            valid = false;
                            Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Please enter "+ listItems.get(i).getRemarksPlaceHolderText())
                                    .show();
                            return valid;
                        }
                    }

                }

                if(listItems.get(i).getSubIndicatorsValidation() != null && listItems.get(i).getSubIndicatorsValidation().size() > 0)

                {
                    for (int j = 0; j < listItems.get(i).getSubIndicatorsValidation().size(); j++)


                        if(listItems.get(i).getAnswer() != null)

                        {
                            Integer a  = listItems.get(i).getSubIndicatorsValidation().get(j).getShowInCase();

                            if(listItems.get(i).getAnswer().equals(something(a)))
                            {
                                if(listItems.get(i).getSubIndicatorsValidation().get(j).getIsOptional() || listItems.get(i).getSubIndicatorsValidation().get(j).getIsOptional() ==null){

                                }else {
                                    if (listItems.get(i).getSubIndicatorsValidation().get(j).getAnswer() == null) {
                                        valid = false;
                                        Toast.makeText(getActivity(), "Please select "+ listItems.get(i).getSubIndicatorsValidation().get(j).getQuestion(), Toast.LENGTH_LONG).show();
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Please select "+ listItems.get(i).getSubIndicatorsValidation().get(j).getQuestion())
                                                .show();
                                        return valid;
//                                            break outer;
                                    }
                                }

                            }


//                            else if(listItems.get(i).getSubIndicators().get(j).getRemarksMandatory() && listItems.get(i).getSubIndicators().get(j).getComments().equals(""))
                            else if(listItems.get(i).getSubIndicators().get(j).getRemarksMandatory() )

                            {
                                if(listItems.get(i).getSubIndicators().get(j).getShowInCase() != null && listItems.get(i).getSubIndicators().get(j).getAnswer()!=null)

                                {
                                    if(listItems.get(i).getSubIndicators().get(j).getShowInCase() == 1 && listItems.get(i).getSubIndicators().get(j).getAnswer().equals("Yes"))

                                    {
                                        valid = false;
                                        Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText())
                                                .show();
                                        return valid;
                                    }

                                    else if(listItems.get(i).getSubIndicators().get(j).getShowInCase() == 2 && listItems.get(i).getSubIndicators().get(j).getAnswer().equals("No"))

                                    {
                                        valid = false;
                                        Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText())
                                                .show();
                                        return valid;
                                    }

                                    else if(listItems.get(i).getSubIndicators().get(j).getShowInCase() == 3 && listItems.get(i).getSubIndicators().get(j).getAnswer().equals("N/A"))

                                    {
                                        valid = false;
                                        Toast.makeText(getActivity(), "Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText(), Toast.LENGTH_LONG).show();
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Please enter "+ listItems.get(i).getSubIndicators().get(j).getRemarksPlaceHolderText())
                                                .show();
                                        return valid;
                                    }
                                }

                            }
                        }

                }
            }
        }

        if (remarksValue == null) {
//            Toast.makeText(getActivity(), "Please enter remarks", Toast.LENGTH_LONG).show();
            valid = false;
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Please enter remarks")
                    .show();
            return valid;
        }

        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                imagesCount.setText(mResults.size() + "");

                if (mResults.size() != 0) {
                    new ImageCompressionAsyncTask(MainActivity.main).execute(mResults.get(mResults.size()-1), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/irmnch/images");
                }

            }
        }
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
            indicatorsMasterSaveImages.add("data:image/jpg;base64,"+ getFileToByte(imageFile.getPath()).replace("null,", ""));

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


    String something(int num)

    {
        String type = "";

        if(num == 1)

        {
            type =   "Yes";
        }

        else if(num == 2)

        {
            type =  "No";
        }

        else if(num == 3)

        {
            type =  "N/A";
        }

        return type;

    }

}