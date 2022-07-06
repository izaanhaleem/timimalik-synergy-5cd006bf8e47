package com.hisdu.ses.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.ses.AppController;
import com.hisdu.ses.BuildConfig;
import com.hisdu.ses.Database.CheckListSend;
import com.hisdu.ses.Database.ClusterForm;
import com.hisdu.ses.Database.House;
import com.hisdu.ses.Database.HouseChildDetail;
import com.hisdu.ses.Database.HouseDetail;
import com.hisdu.ses.Database.IndicatorMasterDataSave;
import com.hisdu.ses.Database.SaveChecklist;
import com.hisdu.ses.Database.SaveInspections;
import com.hisdu.ses.Database.ZeroDoseChildModel;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Database.ZeroDoseMain;
import com.hisdu.ses.Database.ZeroDoseMaster;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.IndicatorSaveResponse;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.Models.indicators.CheckListImage;
import com.hisdu.ses.ModuleListRecyclerAdapter;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.ServerCalls;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    public List<IndicatorMasterDataSave> InsUnsyc;
    TextView syncCount, totalCount,location_name,appversion;
    String createdBy = null;
    Button  export, undo;
    ProgressDialog PD;
    Integer CurrentIndex = 0;
    FragmentManager fragmentManager;
    ImageView lol;
    LinearLayout epiLayout,syncButton;
    RecyclerView recyclerViewModules;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPref myPreferences;

    List<ZeroDoseMain> zeroDoseMain;
    List<Content> modulesList = new ArrayList<>();
    String[] mtextArray = {"Select Campaign", "Polio", "Dengue", "Meeting", "Training","COVID-19","Monitoring","Enquiry","Campaigns","Others"};
    protected LocalDatabaseManager mDbManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        PD = new ProgressDialog(getActivity());
        appversion= view.findViewById(R.id.appversion);
        syncButton = view.findViewById(R.id.syncButton);
        syncCount = view.findViewById(R.id.syncCount);
        export = view.findViewById(R.id.export);
        totalCount = view.findViewById(R.id.totalCount);
        undo = view.findViewById(R.id.undo);
        lol = view.findViewById(R.id.lol);
        epiLayout = view.findViewById(R.id.epi_dashboard);
        fragmentManager = MainActivity.main.getSupportFragmentManager();
        createdBy = new SharedPref(getActivity()).GetserverID();
        recyclerViewModules = view.findViewById(R.id.recyclerViewModules);
        recyclerViewModules.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerViewModules.setLayoutManager(layoutManager);
        myPreferences = new SharedPref(getActivity());
        modulesList.clear();
        mDbManager = LocalDatabaseManager.getInstance(MainActivity.main);


        appversion.setText(BuildConfig.VERSION_NAME);

        GetModules();


//        if (new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("Vaccinator")|| new SharedPref(getActivity()).GetLoggedInRole().equalsIgnoreCase("LHV")) {
//            epiLayout.setVisibility(View.VISIBLE);
//            coverageLayout.setVisibility(View.GONE);
//        } else {
//            epiLayout.setVisibility(View.VISIBLE);
//            coverageLayout.setVisibility(View.GONE);
//        }

        getTotal();
        updateEntry();

//        lol.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v)
//
//            {
//                if(undo.getVisibility() == View.GONE)
//
//                {
//                    undo.setVisibility(View.VISIBLE);
//                }
//
//                else
//
//                {
//                    undo.setVisibility(View.GONE);
//                }
//
//                return false;
//            }
//        });
        try {
            location_name =  view.findViewById(R.id.area);
            String locationName = new SharedPref(requireContext()).GetLocatioName();
            if(locationName!=null){
                location_name.setText(locationName);
            }
        }catch (Exception e){

            Log.d("Exception",e.getMessage());
        }
        export.setOnClickListener(v -> exportDB());

        zeroDoseMain = ZeroDoseMain.getAllZeroDose(new SharedPref(getActivity()).GetserverID());

        syncButton.setOnClickListener(v -> {

            PD.setTitle("Syncing Record, Please wait...");
            PD.setMessage("Progress : " + "0" + "/" + InsUnsyc.size());
            PD.setCancelable(false);
            PD.show();
            SaveUnsyncData();


        });

        undo.setOnClickListener(v -> {

            SaveInspections.UpdateflagData();
            SaveChecklist.UpdateflagData();
            ClusterForm.UpdateflagData();

            Toast.makeText(MainActivity.main, "undo Successfully!", Toast.LENGTH_SHORT).show();

            fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).commit();

        });

        return view;
    }

    void getTotal()
    {
        List<IndicatorMasterDataSave> TotaRecord = IndicatorMasterDataSave.getAllInspection(createdBy);
        List<ZeroDoseMain> zero=  ZeroDoseMain.getAllSync(new SharedPref(getActivity()).GetserverID());
//        List<ZeroDoseChildModel> zeroDoseDetails = ZeroDoseChildModel.getAllZeroDoseDetails(zeroDoseMain.get(i).getId(),new SharedPref(getActivity()).GetserverID());
//
        int total = TotaRecord.size()+zero.size();
        totalCount.setText("" + total);
    }

    void GetModules() {
        modulesList.addAll(Content.getAll());

        for(int i =0;i<modulesList.size();i++)

        {
            if(modulesList.get(i).getAppModuleId() == 4 || modulesList.get(i).getAppModuleId() == 7)

            {
                List<UnSentRecordsObject> ls = mDbManager.getUnSyncRecordsWithKeys(new SharedPref(MainActivity.main).GetserverID(),"" + modulesList.get(i).getAppModuleId());
                modulesList.get(i).setCount("" + ls.size());
            }

            else

                {
                    List<IndicatorMasterDataSave> Total = IndicatorMasterDataSave.getAll(createdBy,modulesList.get(i).getAppModuleId());
                    modulesList.get(i).setCount("" + Total.size());
                }

        }

        mAdapter = new ModuleListRecyclerAdapter(getActivity(), modulesList);
        recyclerViewModules.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    void SaveUnsyncData() {
        if (CurrentIndex < InsUnsyc.size()+zeroDoseMain.size())

        {
            if(InsUnsyc.size()>0){
                List<CheckListSend> SCL = CheckListSend.getAllChecklist(InsUnsyc.get(CurrentIndex).getMastID(), new SharedPref(getActivity()).GetserverID(),"0");
                List<CheckListImage> checkListImages = CheckListImage.getAllImages(InsUnsyc.get(CurrentIndex).getMastID().toString());
                List<House> hhForm = House.getAllHouse(InsUnsyc.get(CurrentIndex).getMastID().toString());
                List<ZeroDoseMaster> zero = ZeroDoseMaster.getAllZeroDose(new SharedPref(getActivity()).GetserverID(),InsUnsyc.get(CurrentIndex).getMastID().toString());

                if(hhForm.size() > 0)

                {
                    List<HouseDetail> hhDetailForm = HouseDetail.getAllHouse(InsUnsyc.get(CurrentIndex).getMastID().toString());

                    if(hhDetailForm != null && hhDetailForm.size() > 0)

                    {
                        for(int i = 0; i < hhDetailForm.size(); i++)

                        {
                            List<HouseChildDetail> houseChildDetails = HouseChildDetail.getAllHouse(hhDetailForm.get(i).getSubMasterId().toString());
                            hhDetailForm.get(i).getHouseChildDetails().addAll(houseChildDetails);
                        }
                    }

                    hhForm.get(0).getaFPHouseDetails().addAll(hhDetailForm);
                }

                if(zero != null && zero.size() > 0)

                {
                    String versionName = BuildConfig.VERSION_NAME;
                    for(int i = 0; i < zero.size(); i++)

                    {
                        zero.get(i).setCurrentAppVersion(versionName);
                        List<ZeroDoseDetail> zeroDoseDetails = ZeroDoseDetail.getAllZeroDoseDetails(zero.get(i).getZeroDoseMasterId(),new SharedPref(getActivity()).GetserverID());
                        if(zeroDoseDetails != null && zeroDoseDetails.size() > 0)
                        {
                            zero.get(i).setZeroDoseDetails(zeroDoseDetails);
                        }
                    }
                    InsUnsyc.get(CurrentIndex).setZeroDose(zero);
                }

                InsUnsyc.get(CurrentIndex).setCheckListDetails(SCL);
                InsUnsyc.get(CurrentIndex).setCheckListImageList(checkListImages);
                InsUnsyc.get(CurrentIndex).setHouses(hhForm);
                sendData(InsUnsyc.get(CurrentIndex), String.valueOf(CurrentIndex));
            }


            if(zeroDoseMain != null && zeroDoseMain.size() > 0)

            {
                String versionName = BuildConfig.VERSION_NAME;

                for(int i = 0; i < zeroDoseMain.size(); i++)
                {

                    List<ZeroDoseChildModel> zeroDoseDetails = ZeroDoseChildModel.getAllZeroDoseDetails(zeroDoseMain.get(i).getId(),new SharedPref(getActivity()).GetserverID());
                    if(zeroDoseDetails != null && zeroDoseDetails.size() > 0)
                    {
                        zeroDoseMain.get(i).setTblRegistrationChilds(zeroDoseDetails);
                        zeroDoseMain.get(i).setCurrentAppVersion(versionName);
                    }
                    syncZeroDose(zeroDoseMain.get(i));
                }
                Log.d("Result Added","");

            }


        }

        else

        {

            lolMethod();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Data Sync Successfully!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, id) ->
                    {
                        dialog.cancel();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).commit();
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    void lolMethod() {
        PD.dismiss();
        updateEntry();
    }

    void updateEntry() {

        InsUnsyc = IndicatorMasterDataSave.getAllInspectionUnSynced(createdBy);
        zeroDoseMain = ZeroDoseMain.getAllZeroDose(new SharedPref(getActivity()).GetserverID());
        int count= InsUnsyc.size()+zeroDoseMain.size();
        if(count>0){
            syncCount.setText(count+"");
        }

        CurrentIndex = 0;

        if (InsUnsyc.size() > 0 || zeroDoseMain.size()>0  && AppController.getInstance().hasinternetAccess) {
            syncButton.setVisibility(View.VISIBLE);
        } else {
            syncButton.setVisibility(View.GONE);
        }

    }


    void sendData(final IndicatorMasterDataSave indicatorMasterDataSave, final String pos) {

        PD.setMessage("Progress : " + CurrentIndex + "/" + InsUnsyc.size());

        if (!PD.isShowing()) {
            PD.show();
        }

        ServerCalls.getInstance().SaveIndicators(indicatorMasterDataSave, new ServerCalls.OnIndicatorsSave() {
            @Override
            public void onResult(IndicatorSaveResponse appModuleResponse)

            {
                PD.dismiss();

                if(!appModuleResponse.getError())

                {
                    CheckListSend.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    IndicatorMasterDataSave.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    CheckListImage.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    House.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    HouseDetail.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    ZeroDoseMaster.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
                    CurrentIndex = CurrentIndex + 1;
                    SaveUnsyncData();
                }

                else

                {
                    Toast.makeText(getActivity(), appModuleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed() {
                PD.dismiss();
            }

            @Override
            public void onRequestFailed(int errorCode, String errorMessage) {
                PD.dismiss();
                syncButton.setEnabled(true);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle(errorMessage);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                (dialog, id) -> {
                                    dialog.cancel();
                                    updateEntry();
                                    CurrentIndex = 0;
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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


    void syncZeroDose(final ZeroDoseMain indicatorMasterDataSave) {


        PD.setMessage("Progress : " + CurrentIndex + "/" + InsUnsyc.size());

        if (!PD.isShowing()) {
            PD.show();
        }

        ServerCalls.getInstance().SyncZeroDose(indicatorMasterDataSave, new ServerCalls.OnIndicatorsSave() {
            @Override
            public void onResult(IndicatorSaveResponse appModuleResponse)

            {
                PD.dismiss();

                if(!appModuleResponse.getError())

                {
                    ZeroDoseMain.UpdateData(String.valueOf(indicatorMasterDataSave.getId()));
                    CurrentIndex = CurrentIndex + 1;
                    SaveUnsyncData();

//                    Toast.makeText(getActivity(), appModuleResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    if (indicatorMasterDataSave.getAppModuleId() != null)
//                    {
//                        CheckListSend.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
//                        IndicatorMasterDataSave.UpdateData(String.valueOf(indicatorMasterDataSave.getMastID()));
//                        InsUnsyc.clear();
//                        //getTotal();
//                    }

                }

                else

                {
                    Toast.makeText(getActivity(), appModuleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed() {
                PD.dismiss();
            }

            @Override
            public void onRequestFailed(int errorCode, String errorMessage) {
                PD.dismiss();
                syncButton.setEnabled(true);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle(errorMessage);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                (dialog, id) -> {
                                    dialog.cancel();
                                    updateEntry();
                                    CurrentIndex = 0;
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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


//    void sendDataVaccination(final SaveInspectionVaccination SL, final String pos) {
//        PD.setMessage("Progress : " + CurrentIndex + "/" + InsUnsycInspection.size());
//
//        if (!PD.isShowing()) {
//            PD.show();
//        }
//
//        ServerCalls.getInstance().SaveOfflineInspectionData(SL, new ServerCalls.OnGenericResult() {
//            @Override
//            public void onSuccess(final GenericResponse response) {
//                syncButton.setEnabled(true);
//
//                if (!response.getIsException()) {
//                    if (SL.serverid != null) {
//                        SaveInspectionVaccination.UpdateData(String.valueOf(SL.getServerid()));
//                        SaveCheckListVaccination.UpdateData(SL.getServerid());
//
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
//    }

    private void exportDB() {
        try {

            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI");

            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                File dbFile = new File(MainActivity.main.getDatabasePath("epi.db").getAbsolutePath());
                FileInputStream fis = new FileInputStream(dbFile);

                String outFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EPI" + File.separator + "epi_" + new SharedPref(getActivity()).GetserverID() + ".db";

                OutputStream output = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                fis.close();

                File File = new File(outFileName);

                shareFile(File);
            } else {
                Toast.makeText(MainActivity.main, "Failed to create directory", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            Log.e("dbBackup:", e.getMessage());
        }
    }

    private void shareFile(File file) {

        Uri u = FileProvider.getUriForFile(MainActivity.main, "com.hisdu.epi.fileprovider", file);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, u);
        shareIntent.setType("text/*");
        startActivity(Intent.createChooser(shareIntent, "Share File"));

    }

}
