package com.hisdu.SESCluster.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.objects.support.SpinnerObject;

import java.util.ArrayList;

public class SpinnerMultiSelectOptionAdapter extends SelectableAdapter<SpinnerMultiSelectOptionAdapter.ViewHolder> {
    private ArrayList<SpinnerObject> mArrayList;
    private OnViewClickListener onViewClickListener;
    private Context context;

    public SpinnerMultiSelectOptionAdapter(Context context, ArrayList<SpinnerObject> mArrayList, OnViewClickListener onViewClickListener) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row_singleselect, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvRowName.setText(mArrayList.get(position).getTitle());
        if (isSelected(position)) {
            holder.tvRowName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checked, 0);
        } else {
            holder.tvRowName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClickListener.onViewClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRowName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRowName = (TextView) itemView.findViewById(R.id.tvRowName);
        }
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }


}
