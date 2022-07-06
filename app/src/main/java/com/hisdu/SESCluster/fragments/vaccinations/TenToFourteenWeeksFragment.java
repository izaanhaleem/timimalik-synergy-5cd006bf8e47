package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDateSet;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.TenToFourteenWeeksObject;
import com.hisdu.ses.R;

import java.util.Date;



public class TenToFourteenWeeksFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    ToggleButton tbPentaVaccinated, tbPCV10Vaccinated,
            tbOPVVaccinated, tbRotaVaccinated;
    EditText etRemarks;
    Button btnSubmit;
    String strPentaVaccinated = "false", strPcv10Vaccinated = "false",
            strOpvVaccinated = "false",
            strRotaVaccinated = "false";
    TenToFourteenWeeksObject tenToFourteenWeeksObject = new TenToFourteenWeeksObject();
    TextView tvTimelyVaccinated;
    String strPentaVaccination, strPCV10Vaccination, strOPVVaccination, strRotaVaccination;
    EditText etPentaVaccinationDate, etPCV10VaccinationDate, etOPVVaccinationDate, etRotaVaccinationDate;
    private static final int REQUEST_CODE_PENTA = 3;
    private static final int REQUST_CODE_PCV10 = 4;
    private static final int REQUST_CODE_OPV = 5;
    private static final int REQUEST_CODE_ROTA = 6;

    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;

    public static TenToFourteenWeeksFragment getInstance(Bundle bundle, String title, int icon) {
        TenToFourteenWeeksFragment fragment = new TenToFourteenWeeksFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        mbundle = bundle;
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        DateOfBirth = (Date) mbundle.getSerializable("DOB");

        tbPentaVaccinated = v.findViewById(R.id.tbPentaVaccinated);
        tbPCV10Vaccinated = v.findViewById(R.id.tbPCV10Vaccinated);
        tbOPVVaccinated = v.findViewById(R.id.tbOPVVaccinated);
        tbRotaVaccinated = v.findViewById(R.id.tbRotaVaccinated);
        etRemarks = v.findViewById(R.id.etRemarks);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        etPentaVaccinationDate = v.findViewById(R.id.etPentaVaccinationDate);
        etPCV10VaccinationDate = v.findViewById(R.id.etPCV10VaccinationDate);
        etOPVVaccinationDate = v.findViewById(R.id.etOPVVaccinationDate);
        etRotaVaccinationDate = v.findViewById(R.id.etRotaVaccinationDate);
        tvTimelyVaccinated = v.findViewById(R.id.tvTimelyVaccinated);

        TenToFourteenWeeksObject tenToFourteenWeeksObject1 = mbundle.getParcelable(Globals.Arguments.TEN_TO_FOURTEEN_WEEKS);
        if (tenToFourteenWeeksObject1 != null) {
            if (tenToFourteenWeeksObject1.getOpvVaccinated() != null && tenToFourteenWeeksObject1.getOpvVaccinated().length() > 0) {
                if (tenToFourteenWeeksObject1.getOpvVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbOPVVaccinated.setChecked(true);
                    if (tenToFourteenWeeksObject1.getOpvDateOfVaccination() != null && tenToFourteenWeeksObject1.getOpvDateOfVaccination().length() > 0) {
                        String[] date = tenToFourteenWeeksObject1.getOpvDateOfVaccination().split(" ");
                        etOPVVaccinationDate.setText(date[0]);
                        etOPVVaccinationDate.setVisibility(View.VISIBLE);
                        tenToFourteenWeeksObject.setOpvDateOfVaccination(tenToFourteenWeeksObject1.getOpvDateOfVaccination());
                    }
                } else
                    tbOPVVaccinated.setChecked(false);
                tenToFourteenWeeksObject.setOpvVaccinated(tenToFourteenWeeksObject1.getOpvVaccinated());

            }
            if (tenToFourteenWeeksObject1.getPcv10Vaccinated() != null && tenToFourteenWeeksObject1.getPcv10Vaccinated().length() > 0) {
                if (tenToFourteenWeeksObject1.getPcv10Vaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbPCV10Vaccinated.setChecked(true);
                    if (tenToFourteenWeeksObject1.getPcv10DateOfVaccination() != null && tenToFourteenWeeksObject1.getPcv10DateOfVaccination().length() > 0) {
                        String date[] = tenToFourteenWeeksObject1.getPcv10DateOfVaccination().split(" ");
                        etPCV10VaccinationDate.setText(date[0]);
                        etPCV10VaccinationDate.setVisibility(View.VISIBLE);
                        tenToFourteenWeeksObject.setPcv10DateOfVaccination(tenToFourteenWeeksObject1.getPcv10DateOfVaccination());
                    }
                } else
                    tbPCV10Vaccinated.setChecked(false);
                tenToFourteenWeeksObject.setPcv10Vaccinated(tenToFourteenWeeksObject1.getPcv10Vaccinated());
            }
            if (tenToFourteenWeeksObject1.getPentaVaccinated() != null && tenToFourteenWeeksObject1.getPentaVaccinated().length() > 0) {
                if (tenToFourteenWeeksObject1.getPentaVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbPentaVaccinated.setChecked(true);
                    if (tenToFourteenWeeksObject1.getPentaDateOfVaccination() != null &&
                            tenToFourteenWeeksObject1.getPentaDateOfVaccination().length() > 0) {
                        String date[] = tenToFourteenWeeksObject1.getPentaDateOfVaccination().split(" ");
                        etPentaVaccinationDate.setVisibility(View.VISIBLE);
                        etPentaVaccinationDate.setText(date[0]);
                        tenToFourteenWeeksObject.setPentaDateOfVaccination(tenToFourteenWeeksObject1.getPentaDateOfVaccination());
                    }
                } else
                    tbPentaVaccinated.setChecked(false);
                tenToFourteenWeeksObject.setPentaVaccinated(tenToFourteenWeeksObject1.getPentaVaccinated());
            }
            if (tenToFourteenWeeksObject1.getRotaVaccinated() != null && tenToFourteenWeeksObject1.getRotaVaccinated().length() > 0) {
                if (tenToFourteenWeeksObject1.getRotaVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbRotaVaccinated.setChecked(true);
                    if (tenToFourteenWeeksObject1.getRotaDateOfVaccinated() != null &&
                            tenToFourteenWeeksObject1.getRotaDateOfVaccinated().length() > 0) {
                        String date[] = tenToFourteenWeeksObject1.getRotaDateOfVaccinated().split(" ");
                        etRotaVaccinationDate.setText(date[0]);
                        etRotaVaccinationDate.setVisibility(View.VISIBLE);
                        tenToFourteenWeeksObject.setRotaDateOfVaccinated(tenToFourteenWeeksObject1.getRotaDateOfVaccinated());
                    }
                } else
                    tbRotaVaccinated.setChecked(false);
                tenToFourteenWeeksObject.setRotaVaccinated(tenToFourteenWeeksObject1.getRotaVaccinated());
                etRemarks.setText(tenToFourteenWeeksObject1.getRemarks());
            }
        } else {
            tenToFourteenWeeksObject.setPentaVaccinated(strPentaVaccinated);
            tenToFourteenWeeksObject.setPcv10Vaccinated(strPcv10Vaccinated);
            tenToFourteenWeeksObject.setRotaVaccinated(strRotaVaccinated);
            tenToFourteenWeeksObject.setOpvVaccinated(strOpvVaccinated);
            tenToFourteenWeeksObject.setPentaDateOfVaccination("");
            tenToFourteenWeeksObject.setPcv10DateOfVaccination("");
            tenToFourteenWeeksObject.setOpvDateOfVaccination("");
            tenToFourteenWeeksObject.setRotaDateOfVaccinated("");
            tenToFourteenWeeksObject.setRemarks("");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_ten_to_forteen_week_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbPentaVaccinated.setOnCheckedChangeListener(this);
        tbPCV10Vaccinated.setOnCheckedChangeListener(this);
        tbOPVVaccinated.setOnCheckedChangeListener(this);
        tbRotaVaccinated.setOnCheckedChangeListener(this);
        etPentaVaccinationDate.setOnClickListener(this);
        etPCV10VaccinationDate.setOnClickListener(this);
        etOPVVaccinationDate.setOnClickListener(this);
        etRotaVaccinationDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        dateCalculation();

    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.ten_to_forteen_weeks));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:

                if (isValid()) {
                    tenToFourteenWeeksObject.setRemarks(etRemarks.getText().toString());

                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.TEN_TO_FOURTEEN_WEEKS, tenToFourteenWeeksObject));
                    getActivity().onBackPressed();
                }
                break;
            case R.id.etPentaVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_PENTA, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_PENTA, this);
                break;
            case R.id.etPCV10VaccinationDate:
                showDatePickerDialog(REQUST_CODE_PCV10, this, DateOfBirth);
//                showDatePickerDialog(REQUST_CODE_PCV10, this);
                break;
            case R.id.etOPVVaccinationDate:
                showDatePickerDialog(REQUST_CODE_OPV, this, DateOfBirth);
//                showDatePickerDialog(REQUST_CODE_OPV, this);
                break;
            case R.id.etRotaVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_ROTA, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_ROTA, this);
                break;
        }
        super.onClick(view);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tbPentaVaccinated:
                if (b) {
                    if(DateOfBirth != null ){etPentaVaccinationDate.setVisibility(View.VISIBLE);}

                } else {
                    etPentaVaccinationDate.setVisibility(View.GONE);
                }
                strPentaVaccinated = String.valueOf(b);
                tenToFourteenWeeksObject.setPentaVaccinated(strPentaVaccinated);
                break;
            case R.id.tbPCV10Vaccinated:
                if (b) {
                    if(DateOfBirth != null ){etPCV10VaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etPCV10VaccinationDate.setVisibility(View.GONE);
                }
                strPcv10Vaccinated = String.valueOf(b);
                tenToFourteenWeeksObject.setPcv10Vaccinated(strPcv10Vaccinated);
                break;
            case R.id.tbOPVVaccinated:
                if (b) {
                    if(DateOfBirth != null ){etOPVVaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etOPVVaccinationDate.setVisibility(View.GONE);
                }
                strOpvVaccinated = String.valueOf(b);
                tenToFourteenWeeksObject.setOpvVaccinated(strOpvVaccinated);
                break;
            case R.id.tbRotaVaccinated:
                if (b) {
                    if(DateOfBirth != null ){etRotaVaccinationDate.setVisibility(View.VISIBLE);}
                } else {
                    etRotaVaccinationDate.setVisibility(View.GONE);

                }
                strRotaVaccinated = String.valueOf(b);
                tenToFourteenWeeksObject.setRotaVaccinated(strRotaVaccinated);
                break;
        }
    }


    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_PENTA:
                /* /////Client requirement to set date of below fields when selected date from 1st field
                 //////// but below fields date can be changeable//////////////////*/

                etPentaVaccinationDate.setText(dateString);
                strPentaVaccination = dateOfVaccinate;
                etPCV10VaccinationDate.setText(dateString);
                strPCV10Vaccination = dateOfVaccinate;
                etOPVVaccinationDate.setText(dateString);
                strOPVVaccination = dateOfVaccinate;
                etRotaVaccinationDate.setText(dateString);
                strRotaVaccination = dateOfVaccinate;
                break;
            case REQUST_CODE_PCV10:
                etPCV10VaccinationDate.setText(dateString);
                strPCV10Vaccination = dateOfVaccinate;
                break;
            case REQUST_CODE_OPV:
                etOPVVaccinationDate.setText(dateString);
                strOPVVaccination = dateOfVaccinate;
                break;
            case REQUEST_CODE_ROTA:
                etRotaVaccinationDate.setText(dateString);
                strRotaVaccination = dateOfVaccinate;
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
        if (strPentaVaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPentaVaccinationDate.getText().length() > 0) {
                tenToFourteenWeeksObject.setPentaDateOfVaccination(strPentaVaccination);
            } else {
                setError(etPentaVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (strPcv10Vaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPCV10VaccinationDate.getText().length() > 0) {
                tenToFourteenWeeksObject.setPcv10DateOfVaccination(strPCV10Vaccination);
            } else {
                setError(etPCV10VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (strOpvVaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etOPVVaccinationDate.getText().length() > 0) {
                tenToFourteenWeeksObject.setOpvDateOfVaccination(strOPVVaccination);
            } else {
                setError(etOPVVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (strRotaVaccinated.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etRotaVaccinationDate.getText().length() > 0) {
                tenToFourteenWeeksObject.setRotaDateOfVaccinated(strRotaVaccination);
            } else {
                setError(etRotaVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        return value == 0;

    }
}
