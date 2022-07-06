package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDateSet;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.FifteenToSixteenMonthsObject;
import com.hisdu.SESCluster.utils.Dialogs;

import java.util.Date;



public class FifteenToSixteenMonthsFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    ToggleButton tbMeasles1Vaccinated;
    EditText etRemarks;
    Button btnSubmit;
    String measles1VaccinatedStr = "false";
    LinearLayout llMeasles;
    FifteenToSixteenMonthsObject fifteenToSixteenMonthsObject = new FifteenToSixteenMonthsObject();
    EditText etMeasles2VaccinationDate;
    private static final int REQUEST_CODE_MEASLES_2 = 16;
    String strMeaslesDate;
    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;

    public static FifteenToSixteenMonthsFragment getInstance(Bundle bundle, String title, int icon) {
        FifteenToSixteenMonthsFragment fragment = new FifteenToSixteenMonthsFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        mbundle = bundle;
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        DateOfBirth = (Date) mbundle.getSerializable("DOB");
        tbMeasles1Vaccinated = v.findViewById(R.id.tbMeasles1Vaccinated);
        etMeasles2VaccinationDate = v.findViewById(R.id.etMeasles2VaccinationDate);
        etRemarks = v.findViewById(R.id.etRemarks);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        llMeasles = v.findViewById(R.id.llMeasles);
        FifteenToSixteenMonthsObject fifteenToSixteenMonthsObject1 = mbundle.getParcelable(Globals.Arguments.FIFTEEN_TO_SIXTEEN_MONTHS);
        if (fifteenToSixteenMonthsObject1 != null) {

            if (fifteenToSixteenMonthsObject1.getMeaslesTwoVaccinated() != null &&
                    fifteenToSixteenMonthsObject1.getMeaslesTwoVaccinated().length() > 1) {
                etRemarks.setText(fifteenToSixteenMonthsObject1.getRemarks());

                if (fifteenToSixteenMonthsObject1.getMeaslesTwoVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbMeasles1Vaccinated.setChecked(true);
                    if (fifteenToSixteenMonthsObject1.getMeasles2DateOfVaccination() != null
                            && fifteenToSixteenMonthsObject1.getMeasles2DateOfVaccination().length() > 0) {
                        String[] date = fifteenToSixteenMonthsObject1.getMeasles2DateOfVaccination().split(" ");
                        etMeasles2VaccinationDate.setVisibility(View.VISIBLE);
                        etMeasles2VaccinationDate.setText(date[0]);
                        fifteenToSixteenMonthsObject.setMeasles2DateOfVaccination(fifteenToSixteenMonthsObject1.getMeasles2DateOfVaccination());
                    }
                } else
                    tbMeasles1Vaccinated.setChecked(false);
                fifteenToSixteenMonthsObject.setMeaslesTwoVaccinated(fifteenToSixteenMonthsObject1.getMeaslesTwoVaccinated());
            }

        } else {
            fifteenToSixteenMonthsObject.setMeaslesTwoVaccinated(measles1VaccinatedStr);

            fifteenToSixteenMonthsObject.setMeasles2DateOfVaccination("");
            fifteenToSixteenMonthsObject.setRemarks("");

        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_fifteen_to_sixteen_months_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbMeasles1Vaccinated.setOnCheckedChangeListener(this);
        btnSubmit.setOnClickListener(this);
        etMeasles2VaccinationDate.setOnClickListener(this);

    }

    @Override
    protected void initializeData() {
        dateCalculation();

    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.fifiteen_to_sisteen_months));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (measles1VaccinatedStr.equalsIgnoreCase(getResources().getString(R.string.true_)) && DateOfBirth != null) {
                    if (etMeasles2VaccinationDate.getText().length() > 0) {
                        fifteenToSixteenMonthsObject.setMeasles2DateOfVaccination(strMeaslesDate);
                        fifteenToSixteenMonthsObject.setRemarks(etRemarks.getText().toString());
                        getTargetFragment().onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK,
                                new Intent().putExtra(Globals.Arguments.FIFTEEN_TO_SIXTEEN_MONTHS, fifteenToSixteenMonthsObject));
                        getActivity().onBackPressed();
                    } else
                        Dialogs.showAlert(getActivity(), getString(R.string.enter_date_of_vaccination),
                                getString(R.string.ok), "", this, 1);

                } else {
                    fifteenToSixteenMonthsObject.setRemarks(etRemarks.getText().toString());
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.FIFTEEN_TO_SIXTEEN_MONTHS, fifteenToSixteenMonthsObject));
                    getActivity().onBackPressed();
                }
//                    Dialogs.showAlert(getActivity(), getString(R.string.enter_date_of_vaccination),
//                            getString(R.string.ok), "", this, 1);
//                    setError(etDateOfVaccination, getResources().getString(R.string.enter_date_of_vaccination));


                break;
            case R.id.etMeasles2VaccinationDate:
                showDatePickerDialog(REQUEST_CODE_MEASLES_2, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_MEASLES_2, this);
                break;
        }
        super.onClick(view);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        fifteenToSixteenMonthsObject.setMeaslesTwoVaccinated(measles1VaccinatedStr);
        switch (compoundButton.getId()) {
            case R.id.tbMeasles1Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){etMeasles2VaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etMeasles2VaccinationDate.setVisibility(View.GONE);
                }
                measles1VaccinatedStr = String.valueOf(b);
                fifteenToSixteenMonthsObject.setMeaslesTwoVaccinated(measles1VaccinatedStr);
                break;
        }
    }

    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_MEASLES_2:
                etMeasles2VaccinationDate.setText(dateString);
                strMeaslesDate = dateOfVaccinate;
                break;
        }
    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {

    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {

    }
}
