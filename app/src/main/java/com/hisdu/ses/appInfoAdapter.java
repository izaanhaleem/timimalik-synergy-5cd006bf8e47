package com.hisdu.ses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hisdu.PermissionUtils;
import com.hisdu.ses.Models.AppInfo.appInfoLinkResponse;
import com.hisdu.ses.Models.AppInfo.appInfopagesize;
import com.hisdu.ses.utils.ServerCalls;

import java.util.ArrayList;
import java.util.List;

import static com.activeandroid.Cache.getContext;

public class appInfoAdapter extends RecyclerView.Adapter<appInfoAdapter.MyViewHolder> {

    private Context context;
    private List<appInfopagesize.Datum> dataList;

    public appInfoAdapter(Context context, List<appInfopagesize.Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public appInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appinforow, parent,false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull appInfoAdapter.MyViewHolder holder, int position) {

         holder.title.setText(this.dataList.get(position).getTitle());
         holder.description.setText(this.dataList.get(position).getDescription());

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                int resourseId =  dataList.get(position).getID();
//                 Toast.makeText(context, ""+resourseId, Toast.LENGTH_SHORT).show();


                 ServerCalls.getInstance().getAppInfoLink(resourseId, new ServerCalls.OnappinfoLink(){

                     @Override
                     public void onSuccess(appInfoLinkResponse.Data appInfoLink) {


                         for(int i = 0; i<appInfoLink.getResourceMaterialDeail().size() ;i++){

                             String filename = appInfoLink.getResourceMaterialDeail().get(i).getFileName();
                             String filepath  ="https://sesapi.pshealthpunjab.gov.pk"+appInfoLink.getResourceMaterialDeail().get(i).getFilePath();

                             PermissionUtils permissionUtils = new PermissionUtils();
                             Snackbar.make(v, " Download File", Snackbar.LENGTH_LONG)
                                     .setAction("Yes", new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
//                                             context.startActivity(new Intent(context, cart.class));

                                                 if (filepath != null) {
                                                     try {
                                                         context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(filepath)));
                                                     } catch (Exception e) {
                                                         e.getStackTrace();
                                                     }
                                                 }
                                         }
                                     }).show();


                         }

                     }

                     @Override
                     public void onFailed(int errorCode, String errorMessage) {
                         Toast.makeText(getContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();
                     }
                 });

             }
         });

    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

                title = itemView.findViewById(R.id.textViewTitle);
                description = itemView.findViewById(R.id.textViewDescription);



        }
    }


}
