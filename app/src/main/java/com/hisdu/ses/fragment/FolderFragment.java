package com.hisdu.ses.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.FolderAdapter;
import com.hisdu.ses.Models.AppInfo.appInfoResourseRequest;
import com.hisdu.ses.Models.AppInfo.appInfopagesize;
import com.hisdu.ses.Models.AppInfo.folderResponse;
import com.hisdu.ses.R;
import com.hisdu.ses.appInfoAdapter;
import com.hisdu.ses.utils.ServerCalls;

import java.util.List;


public class FolderFragment extends Fragment {


    public FolderFragment() {
        // Required empty public constructor
    }
    private List<appInfopagesize.Datum> appInfodata;
    private FolderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.folder_recycler);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);






        ServerCalls.getInstance().getFolders( new ServerCalls.OnFolderResult(){

            @Override
            public void onSuccess(List<folderResponse.FolderModel> appInfoRes) {

                adapter =  new FolderAdapter(getActivity(), appInfoRes);
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