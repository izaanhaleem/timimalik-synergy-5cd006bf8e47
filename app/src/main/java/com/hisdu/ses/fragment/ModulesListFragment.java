package com.hisdu.ses.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.ses.Models.appmodule.AppModulesResponse;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.ModuleListRecyclerAdapter;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.ServerCalls;
import java.util.ArrayList;
import java.util.List;

public class ModulesListFragment extends Fragment {

    RecyclerView recyclerViewModules;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPref myPreferences;
    List<Content> modulesList = new ArrayList<>();

    public ModulesListFragment() {
        // Required empty public constructor
    }

    public static ModulesListFragment newInstance(String param1, String param2) {
        ModulesListFragment fragment = new ModulesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modules_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewModules = view.findViewById(R.id.recyclerViewModules);
        recyclerViewModules.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewModules.setLayoutManager(layoutManager);
        myPreferences =new SharedPref(getActivity());
        modulesList.clear();

        ServerCalls.getInstance().GetAppModuleResponse(new ServerCalls.OnAppModuleResult() {
            @Override
            public void onResult(AppModulesResponse appModuleResponse) {

                for (int a = 0; a < appModuleResponse.getData().size(); a++)
                {

                    modulesList.add(appModuleResponse.getData().get(a));
                }
                mAdapter = new ModuleListRecyclerAdapter(getActivity(), modulesList);
                recyclerViewModules.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
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