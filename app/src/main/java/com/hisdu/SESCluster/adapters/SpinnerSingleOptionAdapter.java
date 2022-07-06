package com.hisdu.SESCluster.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.objects.support.SpinnerObject;

import java.util.ArrayList;


public class SpinnerSingleOptionAdapter extends RecyclerView.Adapter<SpinnerSingleOptionAdapter.SpinnerSingleViewHolder> {

    private ArrayList<SpinnerObject> arrayList;
    private OnViewClickListener onViewClickListener;
    private int selectedPosition = -1;
    private Context context;
    Drawable iconCheckedGrey;
    boolean isGrey = false;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void updateList(ArrayList<SpinnerObject> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public SpinnerSingleOptionAdapter(Context context, ArrayList<SpinnerObject> arrayList, OnViewClickListener onViewClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onViewClickListener = onViewClickListener;
//        Bitmap icon = Utils.toGrayscale(R.drawable.checked, context);
//        iconCheckedGrey = new BitmapDrawable(context.getResources(), icon);
//        isGrey = Utils.isGrayScale(context);
    }

    @Override
    public SpinnerSingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row_singleselect, parent, false);
        return new SpinnerSingleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SpinnerSingleViewHolder holder, int position) {
        holder.tvRowName.setText(arrayList.get(position).getTitle());

        if (position == selectedPosition) {
            holder.ivSelect.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.checked));
        } else {
            holder.ivSelect.setBackgroundDrawable(null);
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
        return arrayList.size();
    }

    public class SpinnerSingleViewHolder extends RecyclerView.ViewHolder {
        TextView tvRowName;
        ImageView ivSelect;

        public SpinnerSingleViewHolder(View itemView) {
            super(itemView);
            tvRowName = (TextView) itemView.findViewById(R.id.tvRowName);
            ivSelect = (ImageView) itemView.findViewById(R.id.ivSelect);
        }
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }

}
