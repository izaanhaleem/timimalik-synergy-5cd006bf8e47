package com.hisdu.SESCluster.fragments.support;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.adapters.SpinnerMultiSelectOptionAdapter;
import com.hisdu.SESCluster.adapters.SpinnerSingleOptionAdapter;
import com.hisdu.SESCluster.objects.support.SpinnerObject;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class SpinnerFragment extends DialogFragment {

    private Context context;
    private String tag, title, positiveText, negativeText;
    private boolean singleSelection;
    private ArrayList<SpinnerObject> arrayList;
    private ArrayList<Integer> selectedArrayList = new ArrayList<>();
    private SpinnerMultiSelectOptionAdapter multiSelectOptionAdapter;
    private SpinnerSingleOptionAdapter singleOptionAdapter;
    private onSpinnerClick onSpinnerClick;
    private boolean isSelected = false;
    private int SELECTED_POSITION = -1;
    private int selectedItemPosition = -1;
    public boolean showSearch = false;
    private ArrayList<SpinnerObject> filteredList;

    public SpinnerFragment(Context context, String title, String tag, String positiveText, String negativeText,
                           boolean singleSelection, ArrayList<SpinnerObject> arrayList,
                           SpinnerFragment.onSpinnerClick onSpinnerClick) {
        this.context = context;
        this.tag = tag;
        this.title = title;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.singleSelection = singleSelection;
        this.arrayList = arrayList;
        this.onSpinnerClick = onSpinnerClick;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
        SELECTED_POSITION = selectedItemPosition;
    }

    public void setSelectedArrayList(ArrayList<Integer> selectedArrayList) {
        this.selectedArrayList = selectedArrayList;
    }

    public void setShowSearch(boolean showSearch) {
        this.showSearch = showSearch;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater factory = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = factory.inflate(R.layout.fragment_spinner, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final TextView tvSelectAll = (TextView) view.findViewById(R.id.tvSelectAll);
        final TextView tvUpdate = (TextView) view.findViewById(R.id.tvUpdate);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextInputLayout tilSearch = (TextInputLayout) view.findViewById(R.id.tilSearch);
        EditText etSearch = (EditText) view.findViewById(R.id.etSearch);
        tvTitle.setText(title);
        tvUpdate.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        if (arrayList != null) {
            if (singleSelection) {
                tvSelectAll.setVisibility(View.GONE);
                singleOptionAdapter = new SpinnerSingleOptionAdapter(context, arrayList, new SpinnerSingleOptionAdapter.OnViewClickListener() {

                    @Override
                    public void onViewClick(View view, int position) {
                        singleOptionAdapter.setSelectedPosition(position);
                        singleOptionAdapter.notifyDataSetChanged();
                        SELECTED_POSITION = position;
                        onSpinnerClick.onSingleClick(tag, arrayList.get(SELECTED_POSITION));
                        getDialog().dismiss();
                    }
                });
                recyclerView.setAdapter(singleOptionAdapter);
                singleOptionAdapter.setSelectedPosition(selectedItemPosition);
                if (showSearch) {
                    filteredList = new ArrayList<>();
                    tilSearch.setVisibility(View.VISIBLE);
                    etSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            synchronized (this) {
                                if (editable.toString().length() > 0) {
                                    filteredList.clear();
                                    for (SpinnerObject spinnerObject : arrayList) {
                                        if (spinnerObject.toString().toLowerCase().contains(editable.toString().toLowerCase())) {
                                            filteredList.add(spinnerObject);
                                        }
                                    }
                                    singleOptionAdapter.updateList(filteredList);
                                } else {
                                    singleOptionAdapter.updateList(arrayList);
                                }
                            }
                        }
                    });
                } else {
                    tilSearch.setVisibility(View.GONE);
                }

                tvUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SELECTED_POSITION == -1) {
                            Toast.makeText(context, context.getResources().getString(R.string.please_select_option), Toast.LENGTH_LONG).show();
                        } else {
                            if (showSearch)
                                if (filteredList.size() == 0)
                                    onSpinnerClick.onSingleClick(tag, arrayList.get(SELECTED_POSITION));
                                else
                                    onSpinnerClick.onSingleClick(tag, filteredList.get(SELECTED_POSITION));
                            else
                                onSpinnerClick.onSingleClick(tag, arrayList.get(SELECTED_POSITION));
                            getDialog().dismiss();
                        }
                    }
                });

            } else {
                tvSelectAll.setVisibility(View.VISIBLE);
                tilSearch.setVisibility(View.GONE);
                multiSelectOptionAdapter = new SpinnerMultiSelectOptionAdapter(context, arrayList, new SpinnerMultiSelectOptionAdapter.OnViewClickListener() {
                    @Override
                    public void onViewClick(View view, int position) {
                        multiSelectOptionAdapter.toggleSelection(position);
                    }
                });
                recyclerView.setAdapter(multiSelectOptionAdapter);
                if (selectedArrayList.size() > 0) {
                    multiSelectOptionAdapter.updateSelected(selectedArrayList);
                }
                tvSelectAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isSelected) {
                            isSelected = false;
                            tvSelectAll.setText(context.getResources().getString(R.string.lbl_select_all));
                            multiSelectOptionAdapter.clearSelection();
                        } else {
                            isSelected = true;
                            tvSelectAll.setText(context.getResources().getString(R.string.lbl_unselect_all));
                            for (int i = 0; i < arrayList.size(); i++) {
                                multiSelectOptionAdapter.setSelected(i);
                            }
                        }
                    }
                });
                tvUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Integer> integers = multiSelectOptionAdapter.getSelectedItems();
                        if (integers.size() > 0) {
                            Log.d("TAG", integers.toString());
                            ArrayList<SpinnerObject> sendBackList = new ArrayList<>();
                            for (int j = 0; j < integers.size(); j++) {
                                sendBackList.add(arrayList.get(integers.get(j)));
                            }
                            onSpinnerClick.onMultipleClick(tag, sendBackList);
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.please_select_option), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            recyclerView.setLayoutManager(manager);

            if (arrayList.size() > 7) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Point size = new Point();
                wm.getDefaultDisplay().getSize(size);
                int height = size.y;
                if (showSearch) {
                    layoutParams.height = (int) (height * 0.60);
                } else {
                    layoutParams.height = (int) (height * 0.70);
                }

            }
        }
        dialogBuilder.setView(view);

        return dialogBuilder.create();
    }

    public interface onSpinnerClick {
        void onSingleClick(String tag, SpinnerObject spinnerObject);

        void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList);
    }

}
