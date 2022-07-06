package com.hisdu.ses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.Database.SaveCheckListVaccination;

import java.util.List;

public class ChecklistVaccinationAdapter extends RecyclerView.Adapter<ChecklistVaccinationAdapter.MyViewHolder> {

    private Context context;
    private List<SaveCheckListVaccination> listItems;
    private ChecklistAdapterAnswer1Listener listener1;
    private ChecklistAdapterAnswer2Listener listener2;
    private ChecklistAdapterAnswer3Listener listener3;
    private ChecklistAdapterAnswer4Listener listener4;
    private ChecklistAdapterAnswer5Listener listener5;
    private ChecklistAdapterAnswer6Listener listener6;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, header_text;
        LinearLayout linearlayout, header, vaccinatorLayout, TTMarried, TTUnamarried;
        RadioGroup actionGroup;
        EditText ansDueMale, ansDueFemale, ansDueMarried, ansDueUnMarried, ansDefaulterMale, ansDefaulterFemale, ansDefaulterMarried, ansDefaulterUnMarried, ansZeroDosageMale, ansZeroDosageFemale, ansZeroDosageMarried, ansZeroDosageUnMarried;


        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.medicine_name);
            linearlayout = view.findViewById(R.id.LinearLayout);
            header_text = view.findViewById(R.id.header_text);
            header = view.findViewById(R.id.header);
            actionGroup = view.findViewById(R.id.actionGroup);
            vaccinatorLayout = view.findViewById(R.id.vaccinatorLayout);
            TTMarried = view.findViewById(R.id.marriedTT);
            TTUnamarried = view.findViewById(R.id.unmarriedTT);
            ansDueMale = view.findViewById(R.id.answer_due);
            ansDueFemale = view.findViewById(R.id.ans_due_female);
            ansDueMarried = view.findViewById(R.id.ans_due_married);
            ansDueUnMarried = view.findViewById(R.id.ans_due_unmarried);
            ansDefaulterMale = view.findViewById(R.id.answer_defaulter);
            ansDefaulterFemale = view.findViewById(R.id.ans_defaulter_female);
            ansDefaulterMarried = view.findViewById(R.id.ans_defaulter_married);
            ansDefaulterUnMarried = view.findViewById(R.id.ans_defaulter_unmarried);
            ansZeroDosageMale = view.findViewById(R.id.ans_zero_dosage);
            ansZeroDosageFemale = view.findViewById(R.id.ans_zero_dosage_female);
            ansZeroDosageMarried = view.findViewById(R.id.ans_zero_dosage_married);
            ansZeroDosageUnMarried = view.findViewById(R.id.ans_zero_dosage_unmarried);

            ansDueMale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDueMale.isEnabled()) {
                        if (ansDueMale.length() != 0) {
                            String abc = ansDueMale.getText().toString();
                            listener1.onChecklistVaccination1Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener1.onChecklistVaccination1Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDefaulterMale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDefaulterMale.isEnabled()) {
                        if (ansDefaulterMale.length() != 0) {
                            String abc = ansDefaulterMale.getText().toString();
                            listener2.onChecklistVaccination2Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener2.onChecklistVaccination2Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansZeroDosageMale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansZeroDosageMale.isEnabled()) {
                        if (ansZeroDosageMale.length() != 0) {
                            String abc = ansZeroDosageMale.getText().toString();
                            listener3.onChecklistVaccination3Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener3.onChecklistVaccination3Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDueFemale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDueFemale.isEnabled()) {
                        if (ansDueFemale.length() != 0) {
                            String abc = ansDueFemale.getText().toString();
                            listener4.onChecklistVaccination4Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener4.onChecklistVaccination4Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDefaulterFemale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDefaulterFemale.isEnabled()) {
                        if (ansDefaulterFemale.length() != 0) {
                            String abc = ansDefaulterFemale.getText().toString();
                            listener5.onChecklistVaccination5Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener5.onChecklistVaccination5Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansZeroDosageFemale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansZeroDosageFemale.isEnabled()) {
                        if (ansZeroDosageFemale.length() != 0) {
                            String abc = ansZeroDosageFemale.getText().toString();
                            listener6.onChecklistVaccination6Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener6.onChecklistVaccination6Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDueMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDueMarried.isEnabled()) {
                        if (ansDueMarried.length() != 0) {
                            String abc = ansDueMarried.getText().toString();
                            listener1.onChecklistVaccination1Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener1.onChecklistVaccination1Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDefaulterMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDefaulterMarried.isEnabled()) {
                        if (ansDefaulterMarried.length() != 0) {
                            String abc = ansDefaulterMarried.getText().toString();
                            listener2.onChecklistVaccination2Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener2.onChecklistVaccination2Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansZeroDosageMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansZeroDosageMarried.isEnabled()) {
                        if (ansZeroDosageMarried.length() != 0) {
                            String abc = ansZeroDosageMarried.getText().toString();
                            listener3.onChecklistVaccination3Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener3.onChecklistVaccination3Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDueUnMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDueUnMarried.isEnabled()) {
                        if (ansDueUnMarried.length() != 0) {
                            String abc = ansDueUnMarried.getText().toString();
                            listener4.onChecklistVaccination4Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener4.onChecklistVaccination4Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansDefaulterUnMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansDefaulterUnMarried.isEnabled()) {
                        if (ansDefaulterUnMarried.length() != 0) {
                            String abc = ansDefaulterUnMarried.getText().toString();
                            listener5.onChecklistVaccination5Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener5.onChecklistVaccination5Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

            ansZeroDosageUnMarried.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus && ansZeroDosageUnMarried.isEnabled()) {
                        if (ansZeroDosageUnMarried.length() != 0) {
                            String abc = ansZeroDosageUnMarried.getText().toString();
                            listener6.onChecklistVaccination6Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), abc);
                        } else {
                            listener6.onChecklistVaccination6Selected(listItems.get(getAdapterPosition()), getAdapterPosition(), null);
                        }
                    }
                }
            });

        }
    }

    public ChecklistVaccinationAdapter(List<SaveCheckListVaccination> listItems, Context context, ChecklistAdapterAnswer1Listener listener1, ChecklistAdapterAnswer2Listener listener2, ChecklistAdapterAnswer3Listener listener3, ChecklistAdapterAnswer4Listener listener4, ChecklistAdapterAnswer5Listener listener5, ChecklistAdapterAnswer6Listener listener6) {
        this.listItems = listItems;
        this.context = context;
        this.listener1 = listener1;
        this.listener2 = listener2;
        this.listener3 = listener3;
        this.listener4 = listener4;
        this.listener5 = listener5;
        this.listener6 = listener6;
    }

    @Override
    public ChecklistVaccinationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_vaccination_item_layout, parent, false);
        return new ChecklistVaccinationAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ChecklistVaccinationAdapter.MyViewHolder holder, final int position) {

        final SaveCheckListVaccination li = listItems.get(position);

        if (!li.isActive) {
            holder.ansZeroDosageMale.setVisibility(View.GONE);
            holder.ansZeroDosageFemale.setVisibility(View.GONE);
            holder.ansZeroDosageMarried.setVisibility(View.GONE);
            holder.ansZeroDosageUnMarried.setVisibility(View.GONE);
        } else {
            holder.ansZeroDosageMale.setVisibility(View.VISIBLE);
            holder.ansZeroDosageFemale.setVisibility(View.VISIBLE);
            holder.ansZeroDosageMarried.setVisibility(View.VISIBLE);
            holder.ansZeroDosageUnMarried.setVisibility(View.VISIBLE);
        }

        if (li.getInputtype().equals("MultipleNumber")) {
            holder.vaccinatorLayout.setVisibility(View.VISIBLE);
            holder.TTMarried.setVisibility(View.GONE);
            holder.TTUnamarried.setVisibility(View.GONE);
        } else if (li.getInputtype().equals("TTM")) {
            holder.vaccinatorLayout.setVisibility(View.GONE);
            holder.TTMarried.setVisibility(View.VISIBLE);
            holder.TTUnamarried.setVisibility(View.VISIBLE);
        }
//        else if (li.getInputtype().equals("TTU")) {
//            holder.vaccinatorLayout.setVisibility(View.GONE);
//            holder.TTMarried.setVisibility(View.GONE);
//            holder.TTUnamarried.setVisibility(View.VISIBLE);
//        }

        if (li.getCheckListTypeName() != null) {
            if (position == 0) {
                holder.header.setVisibility(View.VISIBLE);
                holder.header_text.setText((li.getCheckListTypeName()));
            } else {
                final SaveCheckListVaccination Prevli = listItems.get(position - 1);

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

    public interface ChecklistAdapterAnswer1Listener {
        void onChecklistVaccination1Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }

    public interface ChecklistAdapterAnswer2Listener {
        void onChecklistVaccination2Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }

    public interface ChecklistAdapterAnswer3Listener {
        void onChecklistVaccination3Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }

    public interface ChecklistAdapterAnswer4Listener {
        void onChecklistVaccination4Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }

    public interface ChecklistAdapterAnswer5Listener {
        void onChecklistVaccination5Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }

    public interface ChecklistAdapterAnswer6Listener {
        void onChecklistVaccination6Selected(SaveCheckListVaccination listItem, int position, String Answer);
    }
}