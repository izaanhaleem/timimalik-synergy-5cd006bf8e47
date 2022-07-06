package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.SaveChecklist;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> {

    private Context context;
    private List<SaveChecklist> listItems;
    private ChecklistAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, header_text;
        RadioButton action_yes, action_no, action_na;
        LinearLayout linearlayout, header, anslayout, vaccinatorLayout;
        EditText Answer;
        RadioGroup actionGroup;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.medicine_name);
            linearlayout = view.findViewById(R.id.LinearLayout);
            header_text = view.findViewById(R.id.header_text);
            header = view.findViewById(R.id.header);
            action_yes = view.findViewById(R.id.action_yes);
            action_no = view.findViewById(R.id.action_no);
            action_na = view.findViewById(R.id.action_na);
            Answer = view.findViewById(R.id.Answer);
            actionGroup = view.findViewById(R.id.actionGroup);
            anslayout = view.findViewById(R.id.anslayout);

            Answer.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && Answer.isEnabled()) {
                    if (Answer.length() != 0) {
                        String abc = Answer.getText().toString();
                        listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                    } else {
                        listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "");
                    }
                }
            });

            action_yes.setOnClickListener(view1 -> {
                // send selected contact in callback
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "Yes");
            });

            action_no.setOnClickListener(view12 -> {
                // send selected contact in callback
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "No");
            });

            action_na.setOnClickListener(view13 -> {
                // send selected contact in callback
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "N/A");
            });
        }
    }

    public ChecklistAdapter(List<SaveChecklist> listItems, Context context, ChecklistAdapterListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ChecklistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_display_layout, parent, false);
        return new ChecklistAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChecklistAdapter.MyViewHolder holder, final int position) {

        final SaveChecklist li = listItems.get(position);

        if (li.getInputtype().equals("number")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.VISIBLE);
        } else if (li.getInputtype().equals("radio")) {
            holder.actionGroup.setVisibility(View.VISIBLE);
            holder.anslayout.setVisibility(View.GONE);
        } else if (li.getInputtype().equals("MultipleNumber")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
        }

        if (li.getCheckListTypeName() != null) {
            if (position == 0) {
                holder.header.setVisibility(View.VISIBLE);
                holder.header_text.setText((li.getCheckListTypeName()));
            } else {
                final SaveChecklist Prevli = listItems.get(position - 1);

                if (!li.getCheckListTypeName().equals(Prevli.getCheckListTypeName())) {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.header_text.setText((li.getCheckListTypeName()));
                } else {
                    holder.header.setVisibility(View.GONE);
                }
            }
        }

        holder.name.setText(li.getText());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public interface ChecklistAdapterListener {
        void onChecklistSelected(SaveChecklist listItem, int position, String Answer);
    }
}
