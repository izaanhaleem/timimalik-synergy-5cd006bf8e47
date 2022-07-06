package com.hisdu.ses.ZeroDoseVaccination;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.activeandroid.ActiveAndroid;
import com.hisdu.SESCluster.interfaces.onChildSaveClick;
import com.hisdu.SESCluster.models.clustersType.ClustersType;
import com.hisdu.ses.AppController;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.sidModel;
import com.hisdu.ses.R;
import com.hisdu.ses.utils.PaginationScrollListener;
import com.hisdu.ses.utils.ServerCalls;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ZeroDoseVacFragment extends Fragment implements onChildSaveClick {


    private List<ChildModelData> childList;

    private RecyclerView childlist_recycler;
    Spinner compaign_month,sid,compaing_year;
    Button submitButton;
    String SelectedMonth = null,SelectedYear=null,sidValue = null;
    List<sidModel> SidList;
    private RecyclerView.Adapter Radapter;
    private Boolean isLoading = true;
    private int currentPage = 0;
    private int TOTAL_PAGES = 0;
    private Boolean isLastPage = true;
    private ChildListAdapter paginationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zero_dose_vac_fragment, container, false);

        childlist_recycler = view.findViewById(R.id.childlist_recycler);
        compaign_month= view.findViewById(R.id.compaing_month);
        sid= view.findViewById(R.id.sid);
        compaing_year= view.findViewById(R.id.compaing_year);
        submitButton= view.findViewById(R.id.submitButton);
        childList = new ArrayList<>();

        GetSIDLookup();
        ComapignYear();
        ComapignMonths();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    loadChild();
                }

            }
        });

        return view;
    }

    public boolean validate() {
        boolean valid = true;

        if (sidValue==null) {
            Toast.makeText(getActivity(), " پولیو موہیم کو منتخب کریں", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }

        if (SelectedYear==null) {
            Toast.makeText(getActivity(), " مہم کا سال", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }
        if (SelectedMonth==null) {
            Toast.makeText(getActivity(), " مہم کا مہینہ", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }
        return valid;
    }
    public void loadChild() {
        childList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        paginationAdapter = new ChildListAdapter(getContext(), childList,this,true);
        childlist_recycler.setLayoutManager(linearLayoutManager);
        childlist_recycler.setAdapter(paginationAdapter);

        childlist_recycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                loadFirstPage(true);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        loadFirstPage(false);
    }


    private void loadNextPage() {

//        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
//            @Override
//            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
//                paginationAdapter.removeLoadingFooter();
//                isLoading = false;
//
//                List<Movie> results = response.body();
//                paginationAdapter.addAll(results);
//
//                if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<Movie>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });


    }


    private void loadFirstPage(Boolean isnextpage) {
        final ProgressDialog PD = new ProgressDialog(MainActivity.main);
        PD.setMessage("Please Wait...");
        PD.setCancelable(false);
        PD.show();
        ServerCalls.getInstance().GetVaccinationChild(new ServerCalls.OnGetChildResult() {
            @Override
            public void onResult(ChildModel appModuleResponse) {

//                if(isnextpage){
//                    paginationAdapter.removeLoadingFooter();
//                    if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                    else isLastPage = false;
//                }else {
//                    if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                    else isLastPage = true;
//                }

                paginationAdapter.addAll(appModuleResponse.getData());

                if(appModuleResponse.getData().size()>0){
                    childlist_recycler.setVisibility(View.VISIBLE);
                    try {
                        if (appModuleResponse.getData() != null && appModuleResponse.getData().size() > 0) {
                            ActiveAndroid.beginTransaction();
                            AppController.clearTable(ClustersType.class);

                            List<ChildModelData> childList = appModuleResponse.getData();

                            if (childList.size() != 0) {
                                for (int i = 0; i < childList.size(); i++) {
                                    ChildDBData childDb = new ChildDBData();

                                    if (childList.get(i).getChildId() != null) {
                                        childDb.setChildId(childList.get(i).getChildId());
                                    }
                                    if (childList.get(i).getChildName() != null) {
                                        childDb.setChildName(childList.get(i).getChildName());
                                    }
                                    if (childList.get(i).getAddress() != null) {
                                        childDb.setAddress(childList.get(i).getAddress());
                                    }
                                    if (childList.get(i).getAgeType() != null) {
                                        childDb.setAgeType(childList.get(i).getAgeType());
                                    }
                                    if (childList.get(i).getAge() != null) {
                                        childDb.setAge(childList.get(i).getAge());
                                    }
                                    if (childList.get(i).getFatherName() != null) {
                                        childDb.setFatherName(childList.get(i).getFatherName());
                                    }
                                    if (childList.get(i).getHouseNo() != null) {
                                        childDb.setHouseNo(childList.get(i).getHouseNo());
                                    }
                                    if (childList.get(i).getCampaignMonth() != null) {
                                        childDb.setCampaignMonth(childList.get(i).getCampaignMonth());
                                    }
                                    if (childList.get(i).getCampaignType() != null) {
                                        childDb.setCampaignType(childList.get(i).getCampaignType());
                                    }
                                    if (childList.get(i).getTeamNo() != null) {
                                        childDb.setTeamNo(childList.get(i).getTeamNo());
                                    }
                                    if (childList.get(i).getEntryPersonName() != null) {
                                        childDb.setEntryPersonName(childList.get(i).getEntryPersonName());
                                    }
                                    if (childList.get(i).getEntryPersonDesignation() != null) {
                                        childDb.setEntryPersonDesignation(childList.get(i).getEntryPersonDesignation());
                                    }
                                    if (childList.get(i).getDivisionName() != null) {
                                        childDb.setDivisionName(childList.get(i).getDivisionName());
                                    }
                                    if (childList.get(i).getDistrictName() != null) {
                                        childDb.setDistrictName(childList.get(i).getDistrictName());
                                    }
                                    if (childList.get(i).getTehsilName() != null) {
                                        childDb.setTehsilName(childList.get(i).getTehsilName());
                                    }
                                    if (childList.get(i).getUCName() != null) {
                                        childDb.setUCName(childList.get(i).getUCName());
                                    }
                                    if (childList.get(i).getHRMP() != null) {
                                        childDb.setHrmp(childList.get(i).getHRMP());
                                    }
                                    childDb.save();
                                }

                                ActiveAndroid.setTransactionSuccessful();
                                ActiveAndroid.endTransaction();
//                            new SharedPref(getApplicationContext()).SaveClusterType(appverion.getClusters());
//
//                            DownloadUserData();


                            } else {
                                Toast.makeText(getContext(), "Error Loading SIDs", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception exception) {

                    }
                }else {
                    try {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("")
                                .setContentText("کوئی بچہ نہیں ملا")
                                .show();
                    }catch (Exception e){

                    }

                }

                PD.dismiss();

            }

            @Override
            public void onFailed() {
                PD.dismiss();
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestFailed(int errorCode, String errorMessage) {
                PD.dismiss();
            }
        }, currentPage, 5000,SelectedMonth+","+SelectedYear,sidValue);
//        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
//            @Override
//            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
//                List<Movie> results = response.body();
//                progressBar.setVisibility(View.GONE);
//                paginationAdapter.addAll(results);
//
//                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<Movie>> call, Throwable t) {
//
//            }
//
//        });
    }


    String ReasonID(String Value) {
        if (Value.equals("EI Zero Dose")) {
            return "1";
        } else if (Value.equals("WPV-1")) {
            return "2";
        } else if (Value.equals("VDPV")) {
            return "3";
        } else if (Value.equals("Urgent labelled form field")) {
            return "4";
        } else if (Value.equals("NSC Criteria")) {
            return "5";
        } else if (Value.equals("Inadequate")) {
            return "6";
        }
        return "";
    }


    @Override
    public void onChildSaved() {
        loadChild();
    }


    void ComapignMonths() {
        String[] monthUrdu = {"مہینہ منتخب کریں *", "جنوری", "فروری", "مارچ", "اپریل", "مئی", "جون", "جولائی", "اگست", "ستمبر", "اکتوبر", "نومبر", "دسمبر"};
        String[] months = {"Please Select Month *", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, monthUrdu);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compaign_month.setAdapter(aa);
        compaign_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SelectedMonth = months[position];
                } else {
                    SelectedMonth = null;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        } else {
            Toast.makeText(getContext(), "Error Loading SIDs", Toast.LENGTH_SHORT).show();
        }

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
    }

    void ComapignYear() {
        String[] yearlist = {"سال منتخب کریں *", "2022","2021","2020",};
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, yearlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compaing_year.setAdapter(aa);
        compaing_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SelectedYear = yearlist[position];
                } else {
                    SelectedYear = null;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
