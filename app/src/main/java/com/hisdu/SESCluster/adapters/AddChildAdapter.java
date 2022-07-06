package com.hisdu.SESCluster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.objects.ChildObject;

import java.util.ArrayList;


/**
 * Created by Usman Kurd on 9/20/2016.
 */

public class AddChildAdapter extends RecyclerView.Adapter<AddChildAdapter.ViewHolder> {
    private OnViewClickListener onViewClickListener;
    private Context context;
    private ArrayList<ChildObject> item;
    View view1;
    ViewHolder viewHolder1;

    public AddChildAdapter(Context context1, ArrayList<ChildObject> item, OnViewClickListener onViewClickListener) {
        this.context = context1;
        this.item = item;
        this.onViewClickListener = onViewClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.add_child_item_layout, parent, false);
        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ChildObject childObject = item.get(position);
        holder.tvChildName.setText(childObject.getChildName());
        holder.tvSerial.setText(String.valueOf(position + 1));
        if (childObject.getVaccinated().equalsIgnoreCase(context.getString(R.string.false_))) {
            holder.ivVaccinated.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.not_vaccinated));
        } else if (childObject.getVaccinated().equalsIgnoreCase(context.getString(R.string.true_))) {
            holder.ivVaccinated.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.vaccinated));
        }
        holder.tvVaccinated.setText(childObject.getVaccinated());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClickListener.onViewClick(view, holder.getAdapterPosition());
            }
        });
        String url = "";
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChildName, tvSerial,tvVaccinated;
        ImageView ivVaccinated;
        ViewHolder(View v) {
            super(v);
            tvChildName = v.findViewById(R.id.tvChildName);
            tvSerial = v.findViewById(R.id.tvSerial);
            ivVaccinated = v.findViewById(R.id.ivVaccinated);
            tvVaccinated = v.findViewById(R.id.tvVaccinated);


        }
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }


}
