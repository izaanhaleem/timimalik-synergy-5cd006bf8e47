package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.HouseChildDetail;

import java.util.List;

public class ChildLogAdapter extends RecyclerView.Adapter<ChildLogAdapter.ViewHolder> {

    private List<HouseChildDetail> listItems;
    private Context context;
    private final static int FADE_DURATION = 3000;

    public ChildLogAdapter(List<HouseChildDetail> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_c,parent,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       final HouseChildDetail li = listItems.get(position);

       holder.Name.setText(li.getName());
       holder.age.setText(""+li.getAgeInMonths());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        public TextView Name;
        public TextView age;
        public TextView doze;

        public ViewHolder(View itemView) {
            super(itemView);

            Name         = itemView.findViewById(R.id.Name);
            age          = itemView.findViewById(R.id.age);
            doze         = itemView.findViewById(R.id.status);

        }
    }

}
