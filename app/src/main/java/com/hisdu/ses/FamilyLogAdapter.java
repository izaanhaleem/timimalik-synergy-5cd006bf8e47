package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.HouseDetail;

import java.util.List;

public class FamilyLogAdapter extends RecyclerView.Adapter<FamilyLogAdapter.ViewHolder> {

    private List<HouseDetail> listItems;
    private Context context;

    public FamilyLogAdapter(List<HouseDetail> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_item,parent,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       final HouseDetail li = listItems.get(position);

       holder.Name.setText(li.getHeadOfHouse());
       holder.contact.setText(li.getNoOfChildren());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        public TextView Name;
        public TextView contact;
        public TextView cnic;

        public ViewHolder(View itemView) {
            super(itemView);

            Name         = itemView.findViewById(R.id.Name);
            contact      = itemView.findViewById(R.id.Contact);

        }
    }

}
