package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.ZeroDoseDetail;

import java.util.List;

public class ZeroAdapter extends RecyclerView.Adapter<ZeroAdapter.ViewHolder> {

    private List<ZeroDoseDetail> listItems;
    private Context context;
    private final static int FADE_DURATION = 3000;
    private Boolean isUrdu=false;

    public ZeroAdapter(List<ZeroDoseDetail> listItems, Context context,Boolean isUrdu) {
        this.listItems = listItems;
        this.context = context;
        this.isUrdu=isUrdu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       final ZeroDoseDetail li = listItems.get(position);

       if(isUrdu){
           holder.name_title.setText("بچے کا نام");
           holder.father_name_title.setText("بچے کے والد کا نام");
           holder.dob_title.setText("بچے کی عمر");
           holder.layout_dob.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
           holder.layout_name.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
           holder.layout_father_name.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);

       }
       holder.Name.setText(li.getChildName());
       holder.contact.setText(li.getFatherName());
       holder.cnic.setText(li.getDOB());
       if(li.getIsVaccinated() != null && li.getIsVaccinated())
       {
           holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.button_gradiant_green));
       }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        public TextView Name,name_title,father_name_title,dob_title;
        public TextView contact;
        public TextView cnic;
        public LinearLayout linearLayout,layout_dob,layout_name,layout_father_name;

        public ViewHolder(View itemView) {
            super(itemView);
            name_title= itemView.findViewById(R.id.name_title);
            father_name_title= itemView.findViewById(R.id.father_name_title);
            dob_title= itemView.findViewById(R.id.dob_title);

            Name         = itemView.findViewById(R.id.Name);
            contact      = itemView.findViewById(R.id.Contact);
            cnic         = itemView.findViewById(R.id.CNIC);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            layout_dob = itemView.findViewById(R.id.layout_dob);
            layout_name = itemView.findViewById(R.id.layout_name);
            layout_father_name = itemView.findViewById(R.id.layout_father_name);

        }
    }

}
