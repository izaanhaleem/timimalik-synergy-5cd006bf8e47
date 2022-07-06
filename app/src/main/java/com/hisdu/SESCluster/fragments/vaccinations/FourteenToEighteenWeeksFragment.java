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
import com.hisdu.SESCluster.objects.FourteenToEighteenWeeksObject;
import com.hisdu.SESCluster.utils.Dialogs;

import java.util.Calendar;
import java.util.Date;



public class FourteenToEighteenWeeksFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    ToggleButton tbPentaVaccinated,
            tbPCV10Vaccinated,
            tbOPVVaccinated,
            tbIPVVaccinated;
    EditText etRemarks;
    Button btnSubmit;
    TextView tvTimelyVaccinated;
    LinearLayout llPenta, llPCV, llOPV, llIPV;
    String pentaVaccinatedStr = "false",
            pcv10VaccinatedStr = "false",
            opvVaccinatedStr = "false",
            ipvVaccinatedStr = "false";
    FourteenToEighteenWeeksObject fourteenToEighteenWeeksObject = new FourteenToEighteenWeeksObject();
    Calendar myCalendar = Calendar.getInstance();
    private StringBuilder dateString1;
    private String currentDate1;
    protected String strDateOfVaccinateIPV;
    EditText etPentaVaccinationDate, etPCVVaccinationDate, etOPVVaccinationDate, etIPVVaccinationDate;
    String strPentaDate, strPCVDate, strOPVDate, strIPVDate;
    private static final int REQUEST_CODE_PENTA = 12;
    private static final int REQUEST_CODE_PCV = 13;
    private static final int REQUEST_CODE_OPV = 14;
    private static final int REQUEST_CODE_IPV = 15;

    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;

    public static FourteenToEighteenWeeksFragment getInstance(Bundle bundle, String title, int icon) {
        FourteenToEighteenWeeksFragment fragment = new FourteenToEighteenWeeksFragment();
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
        tbIPVVaccinated = v.findViewById(R.id.tbIPVVaccinated);
        etRemarks = v.findViewById(R.id.etRemarks);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        etPentaVaccinationDate = v.findViewById(R.id.etPentaVaccinationDate);
        etPCVVaccinationDate = v.findViewById(R.id.etPCVVaccinationDate);
        etOPVVaccinationDate = v.findViewById(R.id.etOPVVaccinationDate);
        etIPVVaccinationDate = v.findViewById(R.id.etIPVVaccinationDate);
        tvTimelyVaccinated = v.findViewById(R.id.tvTimelyVaccinated);

       /* fourteenToEighteenWeeksObject.setPentaVaccinated(pentaVaccinatedStr);
        fourteenToEighteenWeeksObject.setPcv10Vaccinated(pcv10VaccinatedStr);
        fourteenToEighteenWeeksObject.setOpvVaccinated(opvVaccinatedStr);
        fourteenToEighteenWeeksObject.setIpvVaccinated(ipvVaccinatedStr);*/
        fourteenToEighteenWeeksObject = mbundle.getParcelable(Globals.Arguments.FOURTEEN_TO_EIGHTEEN_WEEKS);
        if (fourteenToEighteenWeeksObject != null) {
            if (fourteenToEighteenWeeksObject.getIpvVaccinated() != null
                    && fourteenToEighteenWeeksObject.getIpvVaccinated().length() > 0) {
                if (fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    if (fourteenToEighteenWeeksObject.getIpvVaccinationDate() != null && fourteenToEighteenWeeksObject.getIpvVaccinationDate().length() > 0) {
                        String date[] = fourteenToEighteenWeeksObject.getIpvVaccinationDate().split(" ");
                        fourteenToEighteenWeeksObject.setIpvVaccinationDate(date[0]);
                        etIPVVaccinationDate.setVisibility(View.VISIBLE);
                        etIPVVaccinationDate.setText(fourteenToEighteenWeeksObject.getIpvVaccinationDate());
                     /*   fourteenToEighteenWeeksObject.setIpvVaccinationDate(fourteenToEighteenWeeksObject1.getIpvVaccinationDate());
                        fourteenToEighteenWeeksObject.setIpvVaccinated(fourteenToEighteenWeeksObject1.getIpvVaccinated());
*/
                    }
                    tbIPVVaccinated.setChecked(true);
                } else {
                    tbIPVVaccinated.setChecked(false);
                }
                fourteenToEighteenWeeksObject.setIpvVaccinated(fourteenToEighteenWeeksObject.getIpvVaccinated());
            }


            if (fourteenToEighteenWeeksObject.getOpvVaccinated() != null &&
                    fourteenToEighteenWeeksObject.getOpvVaccinated().length() > 0) {
                if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbOPVVaccinated.setChecked(true);
                    if (fourteenToEighteenWeeksObject.getOpvVaccinationDate() != null && fourteenToEighteenWeeksObject.getOpvVaccinationDate().length() > 0) {
                        String date[] = fourteenToEighteenWeeksObject.getOpvVaccinationDate().split(" ");
                        fourteenToEighteenWeeksObject.setOpvVaccinationDate(date[0]);
                        etOPVVaccinationDate.setVisibility(View.VISIBLE);
                        etOPVVaccinationDate.setText(fourteenToEighteenWeeksObject.getOpvVaccinationDate());
                      //  fourteenToEighteenWeeksObject.setOpvVaccinationDate(fourteenToEighteenWeeksObject1.getOpvVaccinationDate());
                    }
                } else {
                    tbOPVVaccinated.setChecked(false);

                }
              //  fourteenToEighteenWeeksObject.setIpvVaccinated(fourteenToEighteenWeeksObject1.getIpvVaccinated());
            }
            if (fourteenToEighteenWeeksObject.getPcv10Vaccinated() != null &&
                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().length() > 0) {
                if (fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbPCV10Vaccinated.setChecked(true);
                    if (fourteenToEighteenWeeksObject.getPcv10VaccinationDate() != null &&
                            fourteenToEighteenWeeksObject.getPcv10VaccinationDate().length() > 0) {
                        String date[] = fourteenToEighteenWeeksObject.getPcv10VaccinationDate().split(" ");
                        fourteenToEighteenWeeksObject.setPcv10VaccinationDate(date[0]);
                        etPCVVaccinationDate.setVisibility(View.VISIBLE);
                        etPCVVaccinationDate.setText(fourteenToEighteenWeeksObject.getPcv10VaccinationDate());
                     //   fourteenToEighteenWeeksObject.setPcv10VaccinationDate(fourteenToEighteenWeeksObject1.getPcv10VaccinationDate());
                    }
                } else {
                    tbPCV10Vaccinated.setChecked(false);

                }
              //  fourteenToEighteenWeeksObject.setPcv10Vaccinated(fourteenToEighteenWeeksObject1.getPcv10Vaccinated());
            }

            if (fourteenToEighteenWeeksObject.getPentaVaccinated() != null &&
                    fourteenToEighteenWeeksObject.getPentaVaccinated().length() > 0) {
                if (fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    if (fourteenToEighteenWeeksObject.getPentaVaccinationDate() != null &&
                            fourteenToEighteenWeeksObject.getPentaVaccinationDate().length() > 0) {
                        String date[] = fourteenToEighteenWeeksObject.getPentaVaccinationDate().split(" ");
                        fourteenToEighteenWeeksObject.setPentaVaccinationDate(date[0]);
                        etPentaVaccinationDate.setVisibility(View.VISIBLE);
                        etPentaVaccinationDate.setText(fourteenToEighteenWeeksObject.getPentaVaccinationDate());
                      //  fourteenToEighteenWeeksObject.setPentaVaccinationDate(fourteenToEighteenWeeksObject1.getPentaVaccinationDate());
                    }
                    tbPentaVaccinated.setChecked(true);
                } else {
                    tbPentaVaccinated.setChecked(false);

                  //  fourteenToEighteenWeeksObject.setPentaVaccinated(fourteenToEighteenWeeksObject1.getPentaVaccinated());
                }
            }
            etRemarks.setText(fourteenToEighteenWeeksObject.getRemarks());


        } else {
            fourteenToEighteenWeeksObject = new FourteenToEighteenWeeksObject();
            fourteenToEighteenWeeksObject.setPentaVaccinationDate("");
            fourteenToEighteenWeeksObject.setPcv10VaccinationDate("");
            fourteenToEighteenWeeksObject.setOpvVaccinationDate("");
            fourteenToEighteenWeeksObject.setIpvVaccinationDate("");
            fourteenToEighteenWeeksObject.setPentaVaccinated("");
            fourteenToEighteenWeeksObject.setPcv10Vaccinated("");
            fourteenToEighteenWeeksObject.setOpvVaccinated("");
            fourteenToEighteenWeeksObject.setIpvVaccinated("");
            fourteenToEighteenWeeksObject.setRemarks("");
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_forteen_to_eighteen_week_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbPentaVaccinated.setOnCheckedChangeListener(this);
        tbPCV10Vaccinated.setOnCheckedChangeListener(this);
        tbOPVVaccinated.setOnCheckedChangeListener(this);
        tbIPVVaccinated.setOnCheckedChangeListener(this);
        etPentaVaccinationDate.setOnClickListener(this);
        etPCVVaccinationDate.setOnClickListener(this);
        etOPVVaccinationDate.setOnClickListener(this);
        etIPVVaccinationDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void initializeData() {
        dateCalculation();


    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.forteen_to_eighteen_weeks));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (isValid()) {
                    fourteenToEighteenWeeksObject.setRemarks(etRemarks.getText().toString());

                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.FOURTEEN_TO_EIGHTEEN_WEEKS, fourteenToEighteenWeeksObject));
                    getActivity().onBackPressed();
                } else
                    Dialogs.showAlert(getActivity(), getString(R.string.enter_date_of_vaccination),
                            getString(R.string.ok), "", this, 1);
//                    setError(etDateOfVaccination, getResources().getString(R.string.enter_date_of_vaccination));
                break;
            case R.id.etPentaVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_PENTA, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_PENTA, this);
                break;
            case R.id.etPCVVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_PCV, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_PCV, this);
                break;

            case R.id.etOPVVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_OPV, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_OPV, this);
                break;
            case R.id.etIPVVaccinationDate:
                showDatePickerDialog(REQUEST_CODE_IPV, this, DateOfBirth);
//                showDatePickerDialog(REQUEST_CODE_IPV, this);
                break;

        }
        super.onClick(view);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.tbPentaVaccinated:
                if (b)
                    if(DateOfBirth != null ){etPentaVaccinationDate.setVisibility(View.VISIBLE);}
                else
                    etPentaVaccinationDate.setVisibility(View.GONE);

                pentaVaccinatedStr = String.valueOf(b);
                fourteenToEighteenWeeksObject.setPentaVaccinated(pentaVaccinatedStr);
                break;
            case R.id.tbPCV10Vaccinated:
                if (b)
                    if(DateOfBirth != null ){etPCVVaccinationDate.setVisibility(View.VISIBLE);}
                else
                    etPCVVaccinationDate.setVisibility(View.GONE);

                pcv10VaccinatedStr = String.valueOf(b);
                fourteenToEighteenWeeksObject.setPcv10Vaccinated(pcv10VaccinatedStr);
                break;
            case R.id.tbOPVVaccinated:
                if (b)
                    if(DateOfBirth != null ){etOPVVaccinationDate.setVisibility(View.VISIBLE);}
                else
                    etOPVVaccinationDate.setVisibility(View.GONE);

                opvVaccinatedStr = String.valueOf(b);
                fourteenToEighteenWeeksObject.setOpvVaccinated(opvVaccinatedStr);
                break;
            case R.id.tbIPVVaccinated:
                if (b)
                    if(DateOfBirth != null ){etIPVVaccinationDate.setVisibility(View.VISIBLE);}
                else
                    etIPVVaccinationDate.setVisibility(View.GONE);

                ipvVaccinatedStr = String.valueOf(b);
                fourteenToEighteenWeeksObject.setIpvVaccinated(ipvVaccinatedStr);
                break;
        }

    }


    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_PENTA:
                etPentaVaccinationDate.setText(dateString);
                strPentaDate = dateOfVaccinate;
                etPCVVaccinationDate.setText(dateString);
                strPCVDate = dateOfVaccinate;
                etOPVVaccinationDate.setText(dateString);
                strOPVDate = dateOfVaccinate;
                etIPVVaccinationDate.setText(dateString);
                strIPVDate = dateOfVaccinate;
                break;
            case REQUEST_CODE_PCV:
                etPCVVaccinationDate.setText(dateString);
                strPCVDate = dateOfVaccinate;
                break;
            case REQUEST_CODE_OPV:
                etOPVVaccinationDate.setText(dateString);
                strOPVDate = dateOfVaccinate;
                break;
            case REQUEST_CODE_IPV:
                etIPVVaccinationDate.setText(dateString);
                strIPVDate = dateOfVaccinate;
                break;

        }

    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {

    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {

    }

    public boolean isValid() {
        int value = 0;
        if (pentaVaccinatedStr.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPentaVaccinationDate.getText().length() > 0) {
                fourteenToEighteenWeeksObject.setPentaVaccinationDate(strPentaDate);
            } else {
                setError(etPentaVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (pcv10VaccinatedStr.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etPCVVaccinationDate.getText().length() > 0) {
                fourteenToEighteenWeeksObject.setPcv10VaccinationDate(strPCVDate);
            } else {
                setError(etPCVVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (opvVaccinatedStr.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etOPVVaccinationDate.getText().length() > 0) {
                fourteenToEighteenWeeksObject.setOpvVaccinationDate(strOPVDate);
            } else {
                setError(etOPVVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        if (ipvVaccinatedStr.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etIPVVaccinationDate.getText().length() > 0) {
                fourteenToEighteenWeeksObject.setIpvVaccinationDate(strIPVDate);
            } else {
                setError(etIPVVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                value = 1;
            }
        }
        return value == 0;

    }
}
