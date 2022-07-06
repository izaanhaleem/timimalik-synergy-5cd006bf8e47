package com.hisdu.ses.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hisdu.ses.Models.AppInfo.appInfoResourseRequest;
import com.hisdu.ses.Models.AppInfo.appInfopagesize;
import com.hisdu.ses.R;
import com.hisdu.ses.appInfoAdapter;
import com.hisdu.ses.utils.ServerCalls;

import java.util.List;


public class appInfoFragment extends Fragment {


    public appInfoFragment() {
        // Required empty public constructor
    }
    private List<appInfopagesize.Datum> appInfodata;
    private appInfoAdapter adapter;

    private int folderId=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.appInfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            folderId = bundle.getInt("folder_id", -1);
        }

        appInfoResourseRequest  data = new appInfoResourseRequest();
        data.setPageNumber(0);
        if(folderId!=-1){
            data.setFolderId(folderId);
        }

        data.setSize(1000);


        ServerCalls.getInstance().appinfo(data, new ServerCalls.OnAppInfoResult(){

            @Override
            public void onSuccess(List<appInfopagesize.Datum> appInfoRes) {

                adapter =  new appInfoAdapter(getContext(), appInfoRes);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                Toast.makeText(getContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();
            }
        });







        return view;
    }
}