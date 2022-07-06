package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.hisdu.ses.Models.indicators.SubIndicator;
import com.hisdu.ses.fragment.IndicatorsFragment;

import java.util.List;

public class IndicatorsChildAdapter extends RecyclerView.Adapter<IndicatorsChildAdapter.MyViewHolder> {

    private Context context;
    private List<SubIndicator> listItems;
    private IndicatorsChildAdapter.ChecklistAdapterChildListener listener;
    IndicatorsAdapter.MyViewHolder hol;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, header_text, textBox;
        RadioButton action_yes, action_no, action_na;
        LinearLayout linearlayout, header, anslayout, lvRow;
        EditText Answer, AnswerNumber, answerRemarks;
        RadioGroup actionGroup;
        RecyclerView rvSubIndicators;
        int mainPosition = 0;
        CheckBox multi_selection_check;
        public MyViewHolder(View view) {
            super(view);
            multi_selection_check = view.findViewById(R.id.multi_selection_check);

            name = view.findViewById(R.id.medicine_name);
            linearlayout = view.findViewById(R.id.LinearLayout);
            header_text = view.findViewById(R.id.header_text);
            header = view.findViewById(R.id.header);
            action_yes = view.findViewById(R.id.action_yes);
            action_no = view.findViewById(R.id.action_no);
            action_na = view.findViewById(R.id.action_na);
            Answer = view.findViewById(R.id.Answer);
            AnswerNumber = view.findViewById(R.id.AnswerNumber);
            actionGroup = view.findViewById(R.id.actionGroup);
            anslayout = view.findViewById(R.id.anslayout);
            textBox = view.findViewById(R.id.textBox);
            lvRow = view.findViewById(R.id.lv_row);
            answerRemarks = view.findViewById(R.id.answerRemarks);
            rvSubIndicators = view.findViewById(R.id.rvSubIndicators);

            Answer.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && Answer.isEnabled()) {
                    if (Answer.length() != 0) {
                        String abc = Answer.getText().toString();
                        listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc, "",hol.getAdapterPosition());
                    }
                }
            });

            AnswerNumber.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && AnswerNumber.isEnabled()) {
                    if (AnswerNumber.length() != 0) {
                        String abc = AnswerNumber.getText().toString();
                        listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc, "",hol.getAdapterPosition());
                    }
                }
            });

            answerRemarks.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && answerRemarks.isEnabled())

                {
                    if (answerRemarks.length() != 0)

                    {
                       // IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setComments( answerRemarks.getText().toString());
                        listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), listItems.get(getAdapterPosition()).getAnswer(), answerRemarks.getText().toString(),hol.getAdapterPosition());
                    }
                }
            });

            action_yes.setOnClickListener(v ->

            {
                IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setAnswer("Yes");
                // size = listItems.size();
                //listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "Yes", "",hol.getAdapterPosition());
                if (listItems.get(getAdapterPosition()).getShowRemarksInCase() == 1 && listItems.get(getAdapterPosition()).getRemarksShow())
                {
                    answerRemarks.setVisibility(View.VISIBLE);
                    if(listItems.get(getAdapterPosition()).getComments() != null)

                    {
                        answerRemarks.setText(listItems.get(getAdapterPosition()).getComments());
                    }

                    else { answerRemarks.setHint(listItems.get(getAdapterPosition()).getRemarksPlaceHolderText()); }
                }

                else
                {
                    answerRemarks.setVisibility(View.GONE);
                }
            });

            action_no.setOnClickListener(v -> {
                IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setAnswer("No");
                //listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "No", "",hol.getAdapterPosition());

                if (listItems.get(getAdapterPosition()).getShowRemarksInCase() == 2 && listItems.get(getAdapterPosition()).getRemarksShow())
                {
                    answerRemarks.setVisibility(View.VISIBLE);

                    if(listItems.get(getAdapterPosition()).getComments() != null)

                    {
                        answerRemarks.setText(listItems.get(getAdapterPosition()).getComments());
                    }

                    else { answerRemarks.setHint(listItems.get(getAdapterPosition()).getRemarksPlaceHolderText()); }

                }
                else { answerRemarks.setVisibility(View.GONE); }
            });

            action_na.setOnClickListener(v -> {
                IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setAnswer("N/A");
                //listener.onChecklistChildSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "N/A", "",hol.getAdapterPosition());
            });
            multi_selection_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                     if (isChecked) {
                                                                         IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setAnswer("Yes");

                                                                     } else {
                                                                         IndicatorsFragment.CF.listItems.get(hol.getAdapterPosition()).getSubIndicatorsValidation().get(getAdapterPosition()).setAnswer("No");

                                                                     }
                                                                 }
                                                             }
            );
        }
    }


    public IndicatorsChildAdapter(List<SubIndicator> listItems, String name, Context context, IndicatorsChildAdapter.ChecklistAdapterChildListener checklistAdapterListener,IndicatorsAdapter.MyViewHolder holder) {
        this.context = context;
        this.listItems = listItems;
        this.listener = checklistAdapterListener;
        this.hol = holder;
    }

    @Override
    public IndicatorsChildAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_display_layout, parent, false);
        return new IndicatorsChildAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(IndicatorsChildAdapter.MyViewHolder holder, final int position) {

        final SubIndicator li = listItems.get(position);

        if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("textbox")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.VISIBLE);
            holder.AnswerNumber.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.VISIBLE);


        }
        else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("MultiSelection")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.GONE);
            holder.multi_selection_check.setVisibility(View.VISIBLE);

        }
        else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("radiobutton")) {
            if (li.getNAShow()) {
                holder.action_na.setVisibility(View.VISIBLE);
            } else {
                holder.action_na.setVisibility(View.GONE);
            }
            holder.actionGroup.setVisibility(View.VISIBLE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.GONE);

        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("label")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.VISIBLE);
            holder.linearlayout.setVisibility(View.GONE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.GONE);

        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("number")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.VISIBLE);
            holder.linearlayout.setVisibility(View.GONE);
        }

        if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("label")) {
            holder.header.setVisibility(View.VISIBLE);
            holder.header_text.setText((li.getQuestion()));
        } else {
            holder.header.setVisibility(View.GONE);
        }

        holder.name.setText(li.getSrNo() + " - " + li.getQuestion());

        if(li.getRemarksPlaceHolderText() != null){ holder.answerRemarks.setHint(li.getRemarksPlaceHolderText()); }


        if(li.getAnswer() != null)

        {
            if(li.getFieldType().equals("Radio Button"))

            {
                if(li.getAnswer().equals("Yes"))

                {
                    if (li.getShowRemarksInCase() == 1 && li.getRemarksShow())
                    {
                        holder.answerRemarks.setVisibility(View.VISIBLE);
                        if(li.getComments() != null)

                        {
                            holder.answerRemarks.setText(li.getComments());
                        }

                        else { holder.answerRemarks.setHint(li.getRemarksPlaceHolderText()); }
                    }

                    else
                        {
                            holder.answerRemarks.setVisibility(View.GONE);
                        }

                    holder.action_yes.setChecked(true);
                }
                else if(li.getAnswer().equals("No"))

                {
                    if (li.getShowRemarksInCase() == 2 && li.getRemarksShow())
                    {
                        holder.answerRemarks.setVisibility(View.VISIBLE);

                        if(li.getComments() != null)

                        {
                            holder.answerRemarks.setText(li.getComments());
                        }

                        else { holder.answerRemarks.setHint(li.getRemarksPlaceHolderText()); }

                    }
                    else { holder.answerRemarks.setVisibility(View.GONE); }
                    holder.action_no.setChecked(true);
                }

                else if(li.getAnswer().equals("N/A"))

                { holder.action_na.setChecked(true); }

            }

            else if(li.getFieldType().equals("Textbox"))

            {
                holder.Answer.setText(li.getAnswer());
            }

            else if(li.getFieldType().equals("Number"))

            {
                holder.AnswerNumber.setText(li.getAnswer());
            }

        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public interface ChecklistAdapterChildListener {
        void onChecklistChildSelected(SubIndicator listItem, int position, String Answer, String answerRemarks,int parent);
    }
}