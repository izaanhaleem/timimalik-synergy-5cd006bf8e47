package com.hisdu.ses;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.CheckList;
import com.hisdu.ses.Database.CheckListDetail;
import com.hisdu.ses.Database.CheckListSend;
import com.hisdu.ses.Models.indicators.Indicator;
import com.hisdu.ses.Models.indicators.IndicatorsData;
import com.hisdu.ses.Models.indicators.SubIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IndicatorsAdapter extends RecyclerView.Adapter<IndicatorsAdapter.MyViewHolder> implements IndicatorsChildAdapter.ChecklistAdapterChildListener {

    private Context context;
    private List<CheckListSend> listItems;
    private IndicatorsAdapter.ChecklistAdapterListener listener;
    private List<SubIndicator> listItemsIndicators = new ArrayList<>();

    @Override
    public void onChecklistChildSelected(SubIndicator listItem, int position, String Answer, String answerRemarks, int parent) {
        listener.onChecklistChildSelected(listItems.get(position), parent, position, Answer, answerRemarks);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, header_text, textBox;
        RadioButton action_yes, action_no, action_na;
        LinearLayout linearlayout, header, anslayout, lvRow;
        EditText Answer, AnswerNumber, answerRemarks;
        RadioGroup actionGroup;
        RecyclerView rvSubIndicators;
        CheckBox multi_selection_check;
        int mainPosition = 0;

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
                        listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc, "");
                    }
                }
            });

            AnswerNumber.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && AnswerNumber.isEnabled()) {
                    if (AnswerNumber.length() != 0) {
                        String abc = AnswerNumber.getText().toString();
                        listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc, "");
                    }
                }
            });

            answerRemarks.setOnFocusChangeListener((v, hasFocus) -> {

                if (!hasFocus && answerRemarks.isEnabled()) {
                    if (answerRemarks.length() != 0) {
                        listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), listItems.get(getAdapterPosition()).getAnswer(), answerRemarks.getText().toString());
                    }
                }
            });

            action_yes.setOnClickListener(v -> {
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "Yes", "");
            });

            action_no.setOnClickListener(v -> {
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "No", "");
            });

            action_na.setOnClickListener(v -> {
                listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "N/A", "");
            });

            multi_selection_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                     if (isChecked) {
                                                                         listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "Yes", "");

                                                                     } else {
                                                                         listener.onChecklistSelected(listItems.get(getAdapterPosition()), getAdapterPosition(), "No", "");

                                                                     }
                                                                 }
                                                             }
            );
        }
    }

    public IndicatorsAdapter(List<CheckListSend> listItems, String name, Context context, IndicatorsAdapter.ChecklistAdapterListener checklistAdapterListener) {
        this.context = context;
        this.listItems = listItems;
        this.listener = checklistAdapterListener;
    }

    @Override
    public IndicatorsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_display_layout, parent, false);
        return new IndicatorsAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(IndicatorsAdapter.MyViewHolder holder, final int position) {

        final CheckListSend li = listItems.get(position);

        if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("textbox")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.VISIBLE);
            holder.AnswerNumber.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.multi_selection_check.setVisibility(View.GONE);

        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("MultiSelection")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.GONE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.GONE);
            holder.multi_selection_check.setVisibility(View.VISIBLE);

        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("radiobutton")) {
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
            holder.multi_selection_check.setVisibility(View.GONE);


        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("label")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.GONE);
            holder.textBox.setVisibility(View.VISIBLE);
            holder.linearlayout.setVisibility(View.GONE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.GONE);
            holder.multi_selection_check.setVisibility(View.GONE);


        } else if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("number")) {
            holder.actionGroup.setVisibility(View.GONE);
            holder.anslayout.setVisibility(View.VISIBLE);
            holder.Answer.setVisibility(View.GONE);
            holder.AnswerNumber.setVisibility(View.VISIBLE);
            holder.textBox.setVisibility(View.GONE);
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.multi_selection_check.setVisibility(View.GONE);

        }

        if (li.getFieldType().toLowerCase().replaceAll("\\s+", "").equalsIgnoreCase("label")) {
            holder.header.setVisibility(View.VISIBLE);
            holder.multi_selection_check.setVisibility(View.GONE);

            holder.header_text.setText((li.getQuestion()));
        } else {
            holder.header.setVisibility(View.GONE);
        }

        String remarkshint = li.getRemarksPlaceHolderText();

        SpannableStringBuilder builder = new SpannableStringBuilder();


        holder.name.setText(li.getSrNo() + " - " + li.getQuestion());

        if (li.getRemarksPlaceHolderText() != null ) {

            builder.append(remarkshint);
            int start = builder.length() - 1;
            int end = builder.length();
            builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if(li.getRemarksPlaceHolderText().contains("*")){holder.answerRemarks.setHint(builder);}
            else { holder.answerRemarks.setHint(remarkshint); }
        }


        if (li.getAnswer() != null) {

            if (li.getFieldType().equals("Radio Button")) {
                if (li.getAnswer().equals("Yes")) {
                    if (li.getShowRemarksInCase() == 1 && li.getRemarksShow()) {
                        holder.answerRemarks.setVisibility(View.VISIBLE);
                        if (li.getComments() != null) {
                            holder.answerRemarks.setText(li.getComments());
                        } else {
                            builder.append(remarkshint);
                            int start = builder.length();
                            int end = builder.length();

                            builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            holder.answerRemarks.setHint(builder);
                        }
                    } else {
                        holder.answerRemarks.setVisibility(View.GONE);
                    }

                    holder.action_yes.setChecked(true);
                } else if (li.getAnswer().equals("No")) {
                    if (li.getShowRemarksInCase() == 2 && li.getRemarksShow()) {
                        holder.answerRemarks.setVisibility(View.VISIBLE);

                        if (li.getComments() != null) {
                            holder.answerRemarks.setText(li.getComments());
                        } else {
                            builder.append(remarkshint);
                            int start = builder.length();
                            int end = builder.length();

                            builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            holder.answerRemarks.setHint(builder);
                        }

                    } else {
                        holder.answerRemarks.setVisibility(View.GONE);
                    }
                    holder.action_no.setChecked(true);
                } else if (li.getAnswer().equals("N/A")) {
                    holder.action_na.setChecked(true);
                }

            } else if (li.getFieldType().equals("Textbox")) {
                holder.Answer.setText(li.getAnswer());
            } else if (li.getFieldType().equals("Number")) {
                holder.AnswerNumber.setText(li.getAnswer());
            }

        }

        if (li.getShowInCase() != null && li.getSubIndicators() != null && li.getSubIndicators().size() > 0) {
            listItemsIndicators.clear();

            if (li.getShowInCase() == 4) {
                for (int i = 0; i < li.getSubIndicators().size(); i++) {
                    SetList(li.getSubIndicators().get(i));
                }
            } else {

                if (li.getAnswer() != null) {
                    for (int i = 0; i < li.getSubIndicators().size(); i++) {
                        if (li.getSubIndicators().get(i).getShowInCase() != null) {
                            if (li.getAnswer().equals(something(li.getSubIndicators().get(i).getShowInCase()))) {
                                SetList(li.getSubIndicators().get(i));
                            }
                        }
                    }
                }
            }

            if (listItemsIndicators.size() > 0) {
                holder.rvSubIndicators.setLayoutManager(new LinearLayoutManager(context));
                holder.rvSubIndicators.setItemViewCacheSize(listItemsIndicators.size());
                listener.onUpdateSubchildSelected(listItemsIndicators, position);
                IndicatorsChildAdapter mAdapter = new IndicatorsChildAdapter(listItemsIndicators, "", context, this, holder);
                holder.rvSubIndicators.setAdapter(mAdapter);
                holder.rvSubIndicators.setVisibility(View.VISIBLE);
            } else {
                holder.rvSubIndicators.setVisibility(View.GONE);
            }

        } else {
            holder.rvSubIndicators.setVisibility(View.GONE);
        }

    }

    void SetList(SubIndicator subIndicator) {
        SubIndicator si = new SubIndicator();
        si.setQuestion(subIndicator.getQuestion());
        si.setIndicatorId(subIndicator.getIndicatorId());
        si.setFieldType(subIndicator.getFieldType());
        si.setSrNo(subIndicator.getSrNo());
        si.setNAShow(subIndicator.getNAShow());
        si.setIsOptional(subIndicator.getIsOptional());
        si.setRemarksMandatory(subIndicator.getRemarksMandatory());
        si.setRemarksPlaceHolderText(subIndicator.getRemarksPlaceHolderText());
        si.setShowRemarksInCase(subIndicator.getShowRemarksInCase());
        si.setRemarksShow(subIndicator.getRemarksShow());
        si.setShowInCase(subIndicator.getShowInCase());
        listItemsIndicators.add(si);
    }

    String something(int num) {
        String type = "";

        if (num == 1) {
            type = "Yes";
        } else if (num == 2) {
            type = "No";
        } else if (num == 3) {
            type = "N/A";
        }

        return type;

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public interface ChecklistAdapterListener {
        void onChecklistSelected(CheckListSend listItem, int position, String Answer, String answerRemarks);

        void onChecklistChildSelected(CheckListSend listItem, int parent, int child, String Answer, String answerRemarks);

        void onUpdateSubchildSelected(List<SubIndicator> listItem, int pos);
    }
}