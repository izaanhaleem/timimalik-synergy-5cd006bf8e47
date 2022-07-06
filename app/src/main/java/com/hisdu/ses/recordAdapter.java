package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;

import java.util.List;

public class recordAdapter extends RecyclerView.Adapter<recordAdapter.MyViewHolder> {

    private Context context;
    private List<UnSentRecordsObject> listItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView District, UC,EPID,Count;

        public MyViewHolder(View view) {
            super(view);

            District = view.findViewById(R.id.District);
            UC = view.findViewById(R.id.UC);
            EPID = view.findViewById(R.id.EPID);
            Count = view.findViewById(R.id.Count);
        }
    }

    public recordAdapter(List<UnSentRecordsObject> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public recordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_display_layout, parent, false);
        return new recordAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(recordAdapter.MyViewHolder holder, final int position) {

        final UnSentRecordsObject li = listItems.get(position);

        holder.District.setText(li.getDistrictName());
        holder.UC.setText(li.getUcName());
        if(AppController.appModuleID == 4 || AppController.appModuleID == 7){holder.EPID.setVisibility(View.GONE);}
        else { holder.EPID.setText(li.getEPID()); }

        holder.Count.setText(li.getCount());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}
