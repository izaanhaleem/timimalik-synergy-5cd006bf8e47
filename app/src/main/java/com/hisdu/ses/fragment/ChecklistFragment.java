package com.hisdu.ses.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.AppController;
import com.hisdu.ses.ChecklistAdapter;
import com.hisdu.ses.ChecklistVaccinationAdapter;
import com.hisdu.ses.Database.CheckList;
import com.hisdu.ses.Database.CheckListVaccination;
import com.hisdu.ses.Database.SaveCheckListVaccination;
import com.hisdu.ses.Database.SaveChecklist;
import com.hisdu.ses.Database.SaveInspections;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChecklistFragment extends Fragment implements ChecklistAdapter.ChecklistAdapterListener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer2Listener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer1Listener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer3Listener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer4Listener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer5Listener, ChecklistVaccinationAdapter.ChecklistAdapterAnswer6Listener {

    String remarksValue = null, Answer_value = null, createdBy, Username;
    String Answer_value1 = null, Answer_value2 = null, Answer_value3 = null, Answer_value4 = null, Answer_value5 = null, Answer_value6 = null;
    public Context context;
    private RecyclerView RV;
    private List<SaveChecklist> listItems;
    private List<SaveCheckListVaccination> listItemsVaccination;
    private ChecklistAdapter mAdapter;
    private ChecklistVaccinationAdapter mAdapterVaccination;
    EditText remarks;
    Button save, back;
    SaveChecklist abc;
    SaveCheckListVaccination saveCheckListVaccination;
    int position = 0;
    FragmentManager fragmentManager;
    String QuestionID, checklistType;
    List<CheckList> serverlistItems;
    List<CheckListVaccination> serverlistVaccinationItems;
    public static ChecklistFragment CF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        RV = view.findViewById(R.id.recyclerView);
        remarks = view.findViewById(R.id.remarks);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);
        CF = this;
        createdBy = new SharedPref(getActivity()).GetserverID();
        Username = new SharedPref(getActivity()).GetLoggedInUser();

        LoadIndicators();

        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(context));

        fragmentManager = getFragmentManager();

//        save.setOnClickListener(v -> Submit());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Submit();
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
//        back.setOnClickListener(v ->
//
//                LinkFragment.LF.changeTab(0)
//
//
//        );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                               sDialog.dismiss();

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                LinkFragment.LF.changeTab(0);
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
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

        return view;
    }

    public void LoadIndicators() {
        listItems = new ArrayList<>();
        listItemsVaccination = new ArrayList<>();
        serverlistItems = new ArrayList<>();
        serverlistVaccinationItems = new ArrayList<>();

        checklistType = AppController.checklistType;

        if (checklistType.equals("Fixed Center")) {
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                serverlistVaccinationItems.addAll(CheckListVaccination.getDualIndicators(checklistType, "Fixed Session"));
            } else {
                serverlistItems.addAll(CheckList.getDualIndicators(checklistType, "Fixed Session"));
            }
        } else {
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                serverlistVaccinationItems.addAll(CheckListVaccination.getAllIndicators(checklistType));
            } else {
                serverlistItems.addAll(CheckList.getAllIndicators(checklistType));
            }
        }

        if (serverlistItems.size() > 0 || serverlistVaccinationItems.size() > 0) {
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                for (int i = 0; i < serverlistVaccinationItems.size(); i++) {

                    SaveCheckListVaccination si = new SaveCheckListVaccination();

                    si.setServer_id(serverlistVaccinationItems.get(i).server_id);
                    si.setText(serverlistVaccinationItems.get(i).text);
                    si.setInputtype(serverlistVaccinationItems.get(i).type);
                    si.setCheckListTypeName(serverlistVaccinationItems.get(i).checkListTypeName);
                    si.setIsActive(serverlistVaccinationItems.get(i).getIsActiveInput());

                    listItemsVaccination.add(si);
                }
            } else {
                for (int i = 0; i < serverlistItems.size(); i++) {

                    SaveChecklist si = new SaveChecklist();

                    si.setServer_id(serverlistItems.get(i).server_id);
                    si.setText(serverlistItems.get(i).text);
                    si.setInputtype(serverlistItems.get(i).type);
                    si.setCheckListTypeName(serverlistItems.get(i).checkListTypeName);

                    listItems.add(si);
                }

            }
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                AppController.saveChecklistsVaccination = listItemsVaccination;

            } else {
                AppController.saveChecklists = listItems;

            }
            if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                mAdapterVaccination = new ChecklistVaccinationAdapter(listItemsVaccination, MainActivity.main, this, this, this, this, this, this);
                RV.setItemViewCacheSize(listItemsVaccination.size());
                RV.setItemViewCacheSize(listItemsVaccination.size());
                RV.setAdapter(mAdapterVaccination);
            } else {
                mAdapter = new ChecklistAdapter(listItems, MainActivity.main, this);
                RV.setItemViewCacheSize(listItems.size());
                RV.setAdapter(mAdapter);
            }

            //mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Error Loading checklist!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onChecklistSelected(SaveChecklist listItem, int pos, String Answer) {

        abc = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value = Answer;

        listItems.get(position).setAnswer(Answer_value);
        listItems.get(position).setMastId(AppController.MasterID);
        listItems.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItems.get(position).setCreatedBy(createdBy);
        listItems.get(position).setRemarks(remarksValue);
    }


    void Submit() {

        if (validate()) {

            if ((AppController.checklistType.equals("Outreach Session") && !(new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) && !(Username.toLowerCase().startsWith("ceo") || Username.toLowerCase().startsWith("dhops"))) || Username.toLowerCase().startsWith("asv")) {
                AppController.saveChecklists = listItems;
                LinkFragment.LF.changeTab(2);

            } else {
                final ProgressDialog PD = new ProgressDialog(getActivity());
                PD.setMessage("Saving Record, Please Wait...");
                PD.show();

                int a = 0;

                if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
                    long res = AppController.saveInspections.save();

                    if (res != -1) {
                        for (int i = 0; i < listItemsVaccination.size(); i++) {
                            SaveCheckListVaccination si = new SaveCheckListVaccination();

                            si.setText(listItemsVaccination.get(i).getText());
                            si.setServer_id(listItemsVaccination.get(i).getServer_id());
                            si.setAnswer1(listItemsVaccination.get(i).getAnswer1());
                            si.setAnswer2(listItemsVaccination.get(i).getAnswer2());
                            si.setAnswer3(listItemsVaccination.get(i).getAnswer3());
                            si.setAnswer4(listItemsVaccination.get(i).getAnswer4());
                            si.setAnswer5(listItemsVaccination.get(i).getAnswer5());
                            si.setAnswer6(listItemsVaccination.get(i).getAnswer6());
                            si.setMastId(listItemsVaccination.get(i).getMastId());
                            si.setSync("0");
                            si.setRemarks(listItemsVaccination.get(i).getRemarks());
                            si.setCreatedBy(listItemsVaccination.get(i).getCreatedBy());

                            long r = si.save();

                            if (r != -1) {
                                a++;
                            } else {
                                break;
                            }
                        }

                        if (a == listItemsVaccination.size()) {
                            PD.dismiss();
                            moveNext();
                        } else {
                            PD.dismiss();
                            SaveInspections.DeleteData(AppController.saveInspections.serverid);
                            SaveChecklist.DeleteData(AppController.saveInspections.serverid);
                            Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        PD.dismiss();
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                    }
                } else {

                    long res = AppController.saveInspections.save();

                    if (res != -1) {
                        for (int i = 0; i < listItems.size(); i++) {
                            SaveChecklist si = new SaveChecklist();

                            si.setText(listItems.get(i).getText());
                            si.setServer_id(listItems.get(i).getServer_id());
                            si.setAnswer(listItems.get(i).getAnswer());
                            si.setMastId(listItems.get(i).getMastId());
                            si.setSync("0");
                            si.setRemarks(listItems.get(i).getRemarks());
                            si.setCreatedBy(listItems.get(i).getCreatedBy());

                            long r = si.save();

                            if (r != -1) {
                                a++;
                            } else {
                                break;
                            }
                        }

                        if (a == listItems.size()) {
                            PD.dismiss();
                            moveNext();
                        } else {
                            PD.dismiss();
                            SaveInspections.DeleteData(AppController.saveInspections.serverid);
                            SaveChecklist.DeleteData(AppController.saveInspections.serverid);
                            Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        PD.dismiss();
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

    }

    void moveNext() {

        Toast.makeText(getActivity(), "Record Save Successfully!", Toast.LENGTH_LONG).show();

        if ((AppController.checklistType.equals("Outreach Session") && !(new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) && !(Username.toLowerCase().startsWith("ceo") || Username.toLowerCase().startsWith("dhops"))) || Username.toLowerCase().startsWith("asv")) {
            LinkFragment.LF.changeTab(2);

        } else {
            AppController.saveChecklists = new ArrayList<>();
            AppController.saveInspections = null;
            AppController.checklistType = null;
            AppController.saveChecklistsVaccination = new ArrayList<>();
            AppController.MasterID = null;
            AppController.location = null;
            AppController.date = null;
            AppController.dateOnly = null;
            MainActivity.main.switchFragment(0);
        }
    }

    public boolean validate() {
        boolean valid = true;

        remarks.clearFocus();

        if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator") || new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
            if (listItemsVaccination == null) {
                valid = false;
                Toast.makeText(getActivity(), "Error getting checklist! please reload.", Toast.LENGTH_LONG).show();
            } else if (listItemsVaccination.size() == 0) {

                valid = false;
                Toast.makeText(getActivity(), "Error getting checklist! please reload.", Toast.LENGTH_LONG).show();

            } else {
                for (int i = 0; i < listItemsVaccination.size(); i++) {

                    if (listItemsVaccination.get(i).getIsActive()) {
                        if (listItemsVaccination.get(i).getAnswer1() == null || listItemsVaccination.get(i).getAnswer2() == null || listItemsVaccination.get(i).getAnswer3() == null
                                || listItemsVaccination.get(i).getAnswer4() == null || listItemsVaccination.get(i).getAnswer5() == null || listItemsVaccination.get(i).getAnswer6() == null) {
                            valid = false;
                            Toast.makeText(getActivity(), "Please submit complete indicators", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else {
                        if (listItemsVaccination.get(i).getAnswer1() == null || listItemsVaccination.get(i).getAnswer2() == null
                                || listItemsVaccination.get(i).getAnswer4() == null || listItemsVaccination.get(i).getAnswer5() == null) {
                            valid = false;
                            Toast.makeText(getActivity(), "Please submit complete indicators", Toast.LENGTH_LONG).show();
                            break;
                        }

                    }
                }
            }
        } else {
            if (listItems == null) {
                valid = false;
                Toast.makeText(getActivity(), "Error getting checklist! please reload.", Toast.LENGTH_LONG).show();
            } else if (listItems.size() == 0) {

                valid = false;
                Toast.makeText(getActivity(), "Error getting checklist! please reload.", Toast.LENGTH_LONG).show();

            } else {
                for (int i = 0; i < listItems.size(); i++) {
                    if (listItems.get(i).getAnswer() == null) {
                        valid = false;
                        Toast.makeText(getActivity(), "Please submit complete indicators", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        }

        if (remarksValue == null) {
            Toast.makeText(getActivity(), "Please enter remarks", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }


    @Override
    public void onChecklistVaccination1Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value1 = Answer;

        listItemsVaccination.get(position).setAnswer1(Answer_value1);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }

    @Override
    public void onChecklistVaccination2Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value2 = Answer;

        listItemsVaccination.get(position).setAnswer2(Answer_value2);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }

    @Override
    public void onChecklistVaccination3Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value3 = Answer;

        listItemsVaccination.get(position).setAnswer3(Answer_value3);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }

    @Override
    public void onChecklistVaccination4Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value4 = Answer;

        listItemsVaccination.get(position).setAnswer4(Answer_value4);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }

    @Override
    public void onChecklistVaccination5Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value5 = Answer;

        listItemsVaccination.get(position).setAnswer5(Answer_value5);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }

    @Override
    public void onChecklistVaccination6Selected(SaveCheckListVaccination listItem,
                                                int pos, String Answer) {
        saveCheckListVaccination = listItem;
        QuestionID = listItem.getServer_id().toString();
        position = pos;
        Answer_value6 = Answer;

        listItemsVaccination.get(position).setAnswer6(Answer_value6);
        listItemsVaccination.get(position).setMastId(AppController.MasterID);
        listItemsVaccination.get(position).setServer_id(Integer.parseInt(QuestionID));
        listItemsVaccination.get(position).setCreatedBy(createdBy);
        listItemsVaccination.get(position).setRemarks(remarksValue);
    }
}