package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDateSet;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.NineToTenMonthsObjects;
import com.hisdu.SESCluster.utils.Dialogs;

import java.util.Date;



public class NineToTenMonthsFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    ToggleButton tbMeasles1Vaccinated,tbIPV2Vaccinated,tbTCVVaccinated;
    EditText etRemarks;
    Button btnSubmit;
    TextView tvTimelyVaccinated;
    LinearLayout llMeasles;
    String measles1Vaccinated = "false",ipv2Vaccinated = "false",tcvVaccinated = "false";
    NineToTenMonthsObjects nineToTenMonthsObjects = new NineToTenMonthsObjects();
    EditText etMeasles1DateOfVaccination;
    private static final int REQUEST_CODE_MEASLES = 11;
    String strMeaslesDate;
    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;


    public static NineToTenMonthsFragment getInstance(Bundle bundle, String title, int icon) {
        NineToTenMonthsFragment fragment = new NineToTenMonthsFragment();
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
        tbIPV2Vaccinated = v.findViewById(R.id.tbIPV2Vaccinated);
        tbTCVVaccinated = v.findViewById(R.id.tbTCVVaccinated);
        etRemarks = v.findViewById(R.id.etRemarks);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        etMeasles1DateOfVaccination = v.findViewById(R.id.etMeasles1DateOfVaccination);
        tvTimelyVaccinated = v.findViewById(R.id.tvTimelyVaccinated);
        NineToTenMonthsObjects nineToTenMonthsObjects1 = mbundle.getParcelable(Globals.Arguments.NINE_TO_TEN_MONTHS);
        if (nineToTenMonthsObjects1 != null)

        {
            if (nineToTenMonthsObjects1.getMeaslesOneVaccinated() != null && nineToTenMonthsObjects1.getMeaslesOneVaccinated().length() > 0)

            {
                if (nineToTenMonthsObjects1.getMeaslesOneVaccinated().equalsIgnoreCase(getString(R.string.true_)))

                {
                    measles1Vaccinated = nineToTenMonthsObjects1.getMeaslesOneVaccinated();
                    tbMeasles1Vaccinated.setChecked(true);

                    if (nineToTenMonthsObjects1.getMeaslesDateOfVaccination() != null && nineToTenMonthsObjects1.getMeaslesDateOfVaccination().length() > 0)

                    {
                        String[] date = nineToTenMonthsObjects1.getMeaslesDateOfVaccination().split(" ");
                        etMeasles1DateOfVaccination.setText(date[0]);
                        etMeasles1DateOfVaccination.setVisibility(View.VISIBLE);
                        nineToTenMonthsObjects.setMeaslesDateOfVaccination(nineToTenMonthsObjects1.getMeaslesDateOfVaccination());
                    }
                }

                else

                {
                    tbMeasles1Vaccinated.setChecked(false);
                    etRemarks.setText(nineToTenMonthsObjects1.getRemarks());
                }

                nineToTenMonthsObjects.setMeaslesOneVaccinated(nineToTenMonthsObjects1.getMeaslesOneVaccinated());

            }

            else

            {
                tbMeasles1Vaccinated.setChecked(false);
                nineToTenMonthsObjects.setMeaslesOneVaccinated(nineToTenMonthsObjects1.getMeaslesOneVaccinated());
                etRemarks.setText(nineToTenMonthsObjects1.getRemarks());
            }

            if (nineToTenMonthsObjects1.getIPV2Vaccinated() != null && nineToTenMonthsObjects1.getIPV2Vaccinated().length() > 0)

            {
                if (nineToTenMonthsObjects1.getIPV2Vaccinated().equalsIgnoreCase(getString(R.string.true_)))

                {
                    ipv2Vaccinated = nineToTenMonthsObjects1.getIPV2Vaccinated();
                    tbIPV2Vaccinated.setChecked(true);
                }

                else

                {
                    tbIPV2Vaccinated.setChecked(false);
                }

                nineToTenMonthsObjects.setIPV2Vaccinated(nineToTenMonthsObjects1.getIPV2Vaccinated());
            }

            else

            {
                tbIPV2Vaccinated.setChecked(false);
                nineToTenMonthsObjects.setIPV2Vaccinated(nineToTenMonthsObjects1.getIPV2Vaccinated());
                etRemarks.setText(nineToTenMonthsObjects1.getRemarks());
            }

            if (nineToTenMonthsObjects1.getTCVVaccinated() != null && nineToTenMonthsObjects1.getTCVVaccinated().length() > 0)

            {
                if (nineToTenMonthsObjects1.getTCVVaccinated().equalsIgnoreCase(getString(R.string.true_)))

                {
                    tcvVaccinated = nineToTenMonthsObjects1.getTCVVaccinated();
                    tbTCVVaccinated.setChecked(true);
                }

                else

                {
                    tbTCVVaccinated.setChecked(false);
                }

                nineToTenMonthsObjects.setTCVVaccinated(nineToTenMonthsObjects1.getTCVVaccinated());
            }

            else

            {
                tbTCVVaccinated.setChecked(false);
                nineToTenMonthsObjects.setTCVVaccinated(nineToTenMonthsObjects1.getTCVVaccinated());
                etRemarks.setText(nineToTenMonthsObjects1.getRemarks());
            }

        } else {
            nineToTenMonthsObjects.setMeaslesOneVaccinated(measles1Vaccinated);
            nineToTenMonthsObjects.setIPV2Vaccinated(ipv2Vaccinated);
            nineToTenMonthsObjects.setTCVVaccinated(tcvVaccinated);
            nineToTenMonthsObjects.setMeaslesDateOfVaccination("");
            nineToTenMonthsObjects.setRemarks("");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_nine_to_ten_months_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbMeasles1Vaccinated.setOnCheckedChangeListener(this);
        tbIPV2Vaccinated.setOnCheckedChangeListener(this);
        tbTCVVaccinated.setOnCheckedChangeListener(this);
        etMeasles1DateOfVaccination.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void initializeData() {
        dateCalculation();

    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.nine_to_ten_months));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etMeasles1DateOfVaccination:
                showDatePickerDialog(REQUEST_CODE_MEASLES, this, DateOfBirth);
                break;
            case R.id.btnSubmit:

                if (measles1Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
                    if (etMeasles1DateOfVaccination.getText().length() > 0) {
                        nineToTenMonthsObjects.setRemarks(etRemarks.getText().toString());
                        nineToTenMonthsObjects.setMeaslesDateOfVaccination(strMeaslesDate);
                        getTargetFragment().onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK,
                                new Intent().putExtra(Globals.Arguments.NINE_TO_TEN_MONTHS, nineToTenMonthsObjects));
                        getActivity().onBackPressed();
                    } else
                        Dialogs.showAlert(getActivity(), getString(R.string.enter_date_of_vaccination), getString(R.string.ok), "", this, 1);
                }

                else

                {
                    nineToTenMonthsObjects.setRemarks(etRemarks.getText().toString());
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.NINE_TO_TEN_MONTHS, nineToTenMonthsObjects));
                    getActivity().onBackPressed();
                }
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        nineToTenMonthsObjects.setMeaslesOneVaccinated(measles1Vaccinated);
//        nineToTenMonthsObjects.setIPV2Vaccinated(ipv2Vaccinated);
//        nineToTenMonthsObjects.setTCVVaccinated(tcvVaccinated);
        switch (compoundButton.getId()) {
            case R.id.tbMeasles1Vaccinated:
                if (b) { if(DateOfBirth != null ){etMeasles1DateOfVaccination.setVisibility(View.VISIBLE);} }
                else { etMeasles1DateOfVaccination.setVisibility(View.GONE); }
                measles1Vaccinated = String.valueOf(b);
                nineToTenMonthsObjects.setMeaslesOneVaccinated(measles1Vaccinated);
                break;
            case R.id.tbIPV2Vaccinated:
                ipv2Vaccinated = String.valueOf(b);
                nineToTenMonthsObjects.setIPV2Vaccinated(ipv2Vaccinated);
                break;
            case R.id.tbTCVVaccinated:
                tcvVaccinated = String.valueOf(b);
                nineToTenMonthsObjects.setTCVVaccinated(tcvVaccinated);
                break;
        }

    }

    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_MEASLES:
                etMeasles1DateOfVaccination.setText(dateString);
                strMeaslesDate = dateOfVaccinate;
                break;
        }

    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) { }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) { }
}
