package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.ses.Database.ZeroDoseMain;
import com.hisdu.ses.Models.appmodule.Content;
import com.hisdu.ses.ZeroDoseVaccination.ZeroDoseVacFragment;
import com.hisdu.ses.fragment.MasterRecordFragment;
import com.hisdu.ses.fragment.ZeroDoseChildListFragment;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == appModuleResponseList.size()) {
            holder.moduleName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        holder.itemView.setTag(appModuleResponseList.get(position));
        Content appModuleResponse = appModuleResponseList.get(position);
        holder.moduleName.setText(appModuleResponse.getAppModuleName());

        String usertype=new SharedPref(context).GetUserType();

        if(usertype!=null){
            if(usertype.equalsIgnoreCase("SIAUser")){
                int totalCount= ZeroDoseMain.getAllZeroDose(new SharedPref(context).GetserverID()).size();
                holder.count.setText(totalCount+"");
            }else {
                holder.count.setText(appModuleResponseList.get(position).getCount());
            }
        }else {
            holder.count.setText(appModuleResponseList.get(position).getCount());

        }
        holder.itemView.setOnClickListener(v -> { });

        if(appModuleResponseList.get(position).getAppModuleId() == 1)

        {
            holder.imageView.setImageResource(R.drawable.store_monitoring);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 2)

        {
            holder.imageView.setImageResource(R.drawable.site);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 3)

        {
            holder.imageView.setImageResource(R.drawable.outreach);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 4)

        {
            holder.imageView.setImageResource(R.drawable.hh);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 5)

        {
            holder.imageView.setImageResource(R.drawable.zero_dose);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 6)

        {
            holder.imageView.setImageResource(R.drawable.afp);
        }

        else if(appModuleResponseList.get(position).getAppModuleId() == 7)

        {
            holder.imageView.setImageResource(R.drawable.weak);
        }
        else if(appModuleResponseList.get(position).getAppModuleId() == 8)

        {
            holder.imageView.setImageResource(R.drawable.zero_dose);
        }
        else if(appModuleResponseList.get(position).getAppModuleId() == 9)

        {
            holder.imageView.setImageResource(R.drawable.zero_dose);
            holder.count.setVisibility(View.GONE);
        }


        holder.itemView.setOnTouchListener((v, event) ->

        {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                holder.main_bg.setBackgroundResource(R.drawable.bg_indicator);
//                    holder.moduleName.setTextColor(ContextCompat.getColor(context, R.color.white));
                return true;
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                holder. main_bg.setBackgroundResource(R.drawable.bg_indicator);
//                    holder.moduleName.setTextColor(ContextCompat.getColor(context, R.color.black));
                if (appModuleResponseList.get(position).getAppModuleId() == 4) {
                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MasterRecordFragment()).addToBackStack(null).commit();
                } else if (appModuleResponseList.get(position).getAppModuleId() == 5) {
                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MasterRecordFragment()).addToBackStack(null).commit();
                }
                else if(appModuleResponseList.get(position).getAppModuleId() == 8){
//                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
//                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ZeroDoseFormFragment()).addToBackStack(null).commit();
                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ZeroDoseChildListFragment()).addToBackStack(null).commit();

                }
                else if(appModuleResponseList.get(position).getAppModuleId() == 9){
                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ZeroDoseVacFragment()).addToBackStack(null).commit();

                }
                else {
                    AppController.appModuleID = appModuleResponseList.get(position).getAppModuleId();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MasterRecordFragment()).addToBackStack(null).commit();
                }
                // Do what you want
                return true;
            }
            if(event.getAction() == MotionEvent.ACTION_CANCEL){
                holder.main_bg.setBackgroundResource(R.drawable.bg_indicator);
//                    holder.moduleName.setTextColor(ContextCompat.getColor(context, R.color.black));
                // Do what you want
                return true;
            }
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return appModuleResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView moduleName,count;
        public ConstraintLayout main_bg;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.bt_module);
            main_bg    = itemView.findViewById(R.id.main_bg);
            imageView  = itemView.findViewById(R.id.imageView);
            count      = itemView.findViewById(R.id.count);

            itemView.setOnClickListener(view -> { });
        }
    }

}