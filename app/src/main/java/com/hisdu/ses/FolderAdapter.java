package com.hisdu.ses;

import static com.activeandroid.Cache.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hisdu.PermissionUtils;
import com.hisdu.ses.Models.AppInfo.appInfoLinkResponse;
import com.hisdu.ses.Models.AppInfo.appInfopagesize;
import com.hisdu.ses.Models.AppInfo.folderResponse;
import com.hisdu.ses.fragment.ZeroDoseMasterFragment;
import com.hisdu.ses.fragment.appInfoFragment;
import com.hisdu.ses.utils.ServerCalls;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    private Activity context;
    private List<folderResponse.FolderModel> dataList;

    public FolderAdapter(Activity context, List<folderResponse.FolderModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public FolderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent,false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(this.dataList.get(position).getFolderName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new appInfoFragment();
                Bundle bundle= new Bundle();
                bundle.putInt("folder_id",dataList.get(position).getFolderId());
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myFragment).addToBackStack(null).commit();
            }
        });
    }
    //
//


    @Override
    public int getItemCount() {
        return this.dataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

                title = itemView.findViewById(R.id.folder_title);



        }
    }


}
