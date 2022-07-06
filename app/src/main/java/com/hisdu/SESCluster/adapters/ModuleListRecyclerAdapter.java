package com.hisdu.SESCluster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.models.appmodule.Content;

import java.util.List;


public class ModuleListRecyclerAdapter extends RecyclerView.Adapter<ModuleListRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Content> appModuleResponseList;

    public ModuleListRecyclerAdapter(Context context, List appModuleResponseList) {
        this.context = context;
        this.appModuleResponseList = appModuleResponseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_modules_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==appModuleResponseList.size()){
            holder.moduleName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        holder.itemView.setTag(appModuleResponseList.get(position));
        Content appModuleResponse = appModuleResponseList.get(position);
        holder.moduleName.setText(appModuleResponse.getAppModuleName());
    }

    @Override
    public int getItemCount() {
        return appModuleResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView moduleName;

        public ViewHolder(View itemView) {
            super(itemView);

            moduleName = (TextView) itemView.findViewById(R.id.bt_module);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

}