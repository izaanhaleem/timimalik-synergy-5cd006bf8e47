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
import com.hisdu.SESCluster.objects.SixToTenWeeksObject;

import java.util.Date;



public class SixToTenWeeksFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    ToggleButton tbPenta1Vaccinated, tbPCV101Vaccinated,
            tbOPV1Vaccinated,
            tbRota1Vaccinated;
    EditText etRemarks;
    TextView tvTimelyVaccinated;
    LinearLayout llPenta, llPCV, llOPV, llRota;
    Button btnSubmit;
    String strPenta1Vaccinated = "false",
            strpcv101Vaccinated = "false",
            stropv1Vaccinated = "false",
            strrota1Vaccinated = "false";
    EditText etPenta1VaccinationDate, etPCV01VaccinationDate, etOpv1VaccinationDate, etRota1VaccinationDate;
    SixToTenWeeksObject sixToTenWeeksObject = new SixToTenWeeksObject();
    String strPenta1Date = "", strPCV1Date = "", strOPV1Date = "", strRota1Date = "";
    private static final int REQUEST_CODE_PENTA1 = 7;
    private static final int REQUEST_CODE_PCV1 = 8;
    private static final int REQUEST_CODE_OPV1 = 9;
    private static final int REQUEST_CODE_ROTA1 = 10;

    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;

    public static SixToTenWeeksFragment getInstance(Bundle bundle, String title, int icon) {
        SixToTenWeeksFragment fragment = new SixToTenWeeksFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        mbundle = bundle;
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        DateOfBirth = (Date) mbundle.getSerializable("DOB");
        tbPenta1Vaccinated = v.findViewById(R.id.tbPenta1Vaccinated);
        tbPCV101Vaccinated = v.findViewById(R.id.tbPCV101Vaccinated);
        tbOPV1Vaccinated = v.findViewById(R.id.tbOPV1Vaccinated);
        tbRota1Vaccinated = v.findViewById(R.id.tbRota1Vaccinated);
        etRemarks = v.findViewById(R.id.etRemarks);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        tvTimelyVaccinated = v.findViewById(R.id.tvTimelyVaccinated);
        llPenta = v.findViewById(R.id.llPenta);
        llPCV = v.findViewById(R.id.llPCV);
        llOPV = v.findViewById(R.id.llOPV);
        llRota = v.findViewById(R.id.llRota);
        etPenta1VaccinationDate = v.findViewById(R.id.etPenta1VaccinationDate);
        etPCV01VaccinationDate = v.findViewById(R.id.etPCV01VaccinationDate);
        etOpv1VaccinationDate = v.findViewById(R.id.etOpv1VaccinationDate);
        etRota1VaccinationDate = v.findViewById(R.id.etRota1VaccinationDate);
        SixToTenWeeksObject sixToTenWeeksObject1 = mbundle.getParcelable(Globals.Arguments.SIX_TO_TEN_WEEKS);
        if (sixToTenWeeksObject1 != null) {
            if (sixToTenWeeksObject1.getOpVOneVaccinated() != null && sixToTenWeeksObject1.getOpVOneVaccinated().length() > 0) {
                if (sixToTenWeeksObject1.getOpVOneVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbOPV1Vaccinated.setChecked(true);
                    if (sixToTenWeeksObject1.getOpVOneVaccinatedDate() != null && sixToTenWeeksObject1.getOpVOneVaccinatedDate().length() > 0) {
                        String date[] = sixToTenWeeksObject1.getOpVOneVaccinatedDate().split(" ");
                        etOpv1VaccinationDate.setText(date[0]);
                        etOpv1VaccinationDate.setVisibility(View.VISIBLE);
                        sixToTenWeeksObject.setOpVOneVaccinatedDate(sixToTenWeeksObject1.getOpVOneVaccinatedDate());
                    }
                } else
                    tbOPV1Vaccinated.setChecked(false);
                sixToTenWeeksObject.setOpVOneVaccinated(sixToTenWeeksObject1.getOpVOneVaccinated());
            }
            if (sixToTenWeeksObject1.getPcvTenOneVaccinated() != null && sixToTenWeeksObject1.getPcvTenOneVaccinated().length() > 0){
                if (sixToTenWeeksObject1.getPcvTenOneVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbPCV101Vaccinated.setChecked(true);
                    if (sixToTenWeeksObject1.getPcvTenOneVaccinatedDate() != null && sixToTenWeeksObject1.getPcvTenOneVaccinatedDate().length() > 0) {
                        String date[] = sixToTenWeeksObject1.getPcvTenOneVaccinatedDate().split(" ");
                        etPCV01VaccinationDate.setText(date[0]);
                        etPCV01VaccinationDate.setVisibility(View.VISIBLE);
                        sixToTenWeeksObject.setPcvTenOneVaccinatedDate(sixToTenWeeksObject1.getPcvTenOneVaccinatedDate());
                    }
                } else
                    tbPCV101Vaccinated.setChecked(false);
                sixToTenWeeksObject.setPcvTenOneVaccinated(sixToTenWeeksObject1.getPcvTenOneVaccinated());
            }
            if (sixToTenWeeksObject1.getPentaOneVaccinated() != null && sixToTenWeeksObject1.getPentaOneVaccinated().length() > 0) {
                if (sixToTenWeeksObject1.getPentaOneVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbPenta1Vaccinated.setChecked(true);
                    if (sixToTenWeeksObject1.getPentaOneVaccinatedDate() != null && sixToTenWeeksObject1.getPentaOneVaccinatedDate().length() > 0) {
                        etPenta1VaccinationDate.setVisibility(View.VISIBLE);
                        String[] date = sixToTenWeeksObject1.getPentaOneVaccinatedDate().split(" ");
                        etPenta1VaccinationDate.setText(date[0]);
                        sixToTenWeeksObject.setPentaOneVaccinatedDate(sixToTenWeeksObject1.getPentaOneVaccinatedDate());
                    }
                } else
                    tbPCV101Vaccinated.setChecked(false);
                sixToTenWeeksObject.setPentaOneVaccinated(sixToTenWeeksObject1.getPentaOneVaccinated());

            }
            if (sixToTenWeeksObject1.getRotaVaccineOneVaccinated() != null && sixToTenWeeksObject1.getRotaVaccineOneVaccinated().length() > 0) {
                if (sixToTenWeeksObject1.getRotaVaccineOneVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbRota1Vaccinated.setChecked(true);
                    if (sixToTenWeeksObject1.getRotaVaccineOneVaccinatedDate() != null &&
                            sixToTenWeeksObject1.getRotaVaccineOneVaccinatedDate().length() > 0) {
                        String date[] = sixToTenWeeksObject1.getRotaVaccineOneVaccinatedDate().split(" ");
                        etRota1VaccinationDate.setText(date[0]);
                        etRota1VaccinationDate.setVisibility(View.VISIBLE);
                        sixToTenWeeksObject.setRotaVaccineOneVaccinatedDate(sixToTenWeeksObject1.getRotaVaccineOneVaccinatedDate());
                    }
                } else tbRota1Vaccinated.setChecked(false);
                sixToTenWeeksObject.setRotaVaccineOneVaccinated(sixToTenWeeksObject1.getRotaVaccineOneVaccinated());
                etRemarks.setText(sixToTenWeeksObject1.getRemark());
            }
        } else {
            sixToTenWeeksObject.setPentaOneVaccinated(strPenta1Vaccinated);
            sixToTenWeeksObject.setPcvTenOneVaccinated(strpcv101Vaccinated);
            sixToTenWeeksObject.setOpVOneVaccinated(stropv1Vaccinated);
            sixToTenWeeksObject.setRotaVaccineOneVaccinated(strrota1Vaccinated);
            sixToTenWeeksObject.setPentaOneVaccinatedDate("");
            sixToTenWeeksObject.setPcvTenOneVaccinatedDate("");
            sixToTenWeeksObject.setOpVOneVaccinatedDate("");
            sixToTenWeeksObject.setRotaVaccineOneVaccinatedDate("");
            sixToTenWeeksObject.setRemark("");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_six_to_ten_week_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbPenta1Vaccinated.setOnCheckedChangeListener(this);
        tbPCV101Vaccinated.setOnCheckedChangeListener(this);
        tbOPV1Vaccinated.setOnCheckedChangeListener(this);
        tbRota1Vaccinated.setOnCheckedChangeListener(this);
        etPenta1VaccinationDate.setOnClickListener(this);
        etPCV01VaccinationDate.setOnClickListener(this);
        etOpv1VaccinationDate.setOnClickListener(this);
        etRota1VaccinationDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        dateCalculation();

    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.six_to_ten_weeks));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (isValid()) {

                    sixToTenWeeksObject.setRemark(etRemarks.getText().toString());
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.SIX_TO_TEN_WEEKS, sixToTenWeeksObject));
                    getActivity().onBackPressed();
                }
                break;
            case R.id.etPenta1VaccinationDate:
                showDatePickerDialog(REQUEST_CODE_PENTA1, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_PENTA1, this);
                break;
            case R.id.etPCV01VaccinationDate:
                showDatePickerDialog(REQUEST_CODE_PCV1, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_PCV1, this);
                break;
            case R.id.etOpv1VaccinationDate:
                showDatePickerDialog(REQUEST_CODE_OPV1, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_OPV1, this);
                break;
            case R.id.etRota1VaccinationDate:
                showDatePickerDialog(REQUEST_CODE_ROTA1, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_ROTA1, this);
                break;
            default:
                super.onClick(view);
                break;
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.tbPenta1Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){ etPenta1VaccinationDate.setVisibility(View.VISIBLE); }
                } else {
                    etPenta1VaccinationDate.setVisibility(View.GONE);
                }
                strPenta1Vaccinated = String.valueOf(b);
                sixToTenWeeksObject.setPentaOneVaccinated(strPenta1Vaccinated);
                break;
            case R.id.tbPCV101Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){etPCV01VaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etPCV01VaccinationDate.setVisibility(View.GONE);
                }
                strpcv101Vaccinated = String.valueOf(b);
                sixToTenWeeksObject.setPcvTenOneVaccinated(strpcv101Vaccinated);
                break;
            case R.id.tbOPV1Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){etOpv1VaccinationDate.setVisibility(View.VISIBLE);}
                } else {

                    etOpv1VaccinationDate.setVisibility(View.GONE);
                }
                stropv1Vaccinated = String.valueOf(b);
                sixToTenWeeksObject.setOpVOneVaccinated(stropv1Vaccinated);
                break;
            case R.id.tbRota1Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){etRota1VaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etRota1VaccinationDate.setVisibility(View.GONE);
                }
                strrota1Vaccinated = String.valueOf(b);
                sixToTenWeeksObject.setRotaVaccineOneVaccinated(strrota1Vaccinated);
                break;

        }
    }

    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_PENTA1:
                  /* /////Client requirement to set date of below fields when selected date from 1st field
                 //////// but below fields date can be changeable//////////////////*/
                etPenta1VaccinationDate.setText(dateString);
                strPenta1Date = dateOfVaccinate;
                etPCV01VaccinationDate.setText(dateString);
                strPCV1Date = dateOfVaccinate;
                etOpv1VaccinationDate.setText(dateString);
                strOPV1Date = dateOfVaccinate;
                etRota1VaccinationDate.setText(dateString);
                strRota1Date = dateOfVaccinate;
                break;
            case REQUEST_CODE_PCV1:
                etPCV01VaccinationDate.setText(dateString);
                strPCV1Date = dateOfVaccinate;
                break;
            case REQUEST_CODE_OPV1:
                etOpv1VaccinationDate.setText(dateString);
                strOPV1Date = dateOfVaccinate;
                break;
            case REQUEST_CODE_ROTA1:
                etRota1VaccinationDate.setText(dateString);
                strRota1Date = dateOfVaccinate;
                break;

        }
    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {

    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {

    }

    private boolean isValid() {
        int value = 0;
        if (strPenta1Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPenta1VaccinationDate.getText().length() > 0) {
                sixToTenWeeksObject.setPentaOneVaccinatedDate(strPenta1Date);
            } else {
                setError(etPenta1VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (strpcv101Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPCV01VaccinationDate.getText().length() > 0) {
                sixToTenWeeksObject.setPcvTenOneVaccinatedDate(strPCV1Date);
            } else {
                setError(etPCV01VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (stropv1Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etOpv1VaccinationDate.getText().length() > 0) {
                sixToTenWeeksObject.setOpVOneVaccinatedDate(strOPV1Date);
            } else {
                setError(etOpv1VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (strrota1Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etRota1VaccinationDate.getText().length() > 0) {
                sixToTenWeeksObject.setRotaVaccineOneVaccinatedDate(strRota1Date);
            } else {
                setError(etRota1VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }




        return value == 0;
    }
}
