package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDateSet;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.ZeroToFortyDaysObject;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ZeroToFourteenDaysFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDateSet, OnDialogButtonClickListener {
    Button btnSubmit;
    ToggleButton tbOPVVaccinated, tbBCGVaccinated,tbhepbVaccinated;
    EditText etRemarks;
    private StringBuilder dateString;

    private ZeroToFortyDaysObject zeroToFortyDaysObject = new ZeroToFortyDaysObject();
    String opv = "false", bcg = "false", hepb = "false";
    EditText etOpv0VaccinationDate, etBCGVaccinationDate, ethepbVaccinationDate;
    private static final int OPV_REQUEST_CODE = 1;
    private static final int BCG_REQUEST_CODE = 2;
    private static final int Hepb_REQUEST_CODE = 3;
    private String strOPVDate, strBCGDate;
    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;
    LazyDatePicker vacinationDatePicker, BCGvacinationDatePicker, HepbvacinationDatePicker;
    public static ZeroToFourteenDaysFragment getInstance(Bundle bundle, String title, int icon) {
        ZeroToFourteenDaysFragment fragment = new ZeroToFourteenDaysFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        mbundle = bundle;
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {


        vacinationDatePicker=v.findViewById(R.id.vacinationDatePicker);
        BCGvacinationDatePicker=v.findViewById(R.id.BCGvacinationDatePicker);

        DateOfBirth = (Date) mbundle.getSerializable("DOB");
        btnSubmit = v.findViewById(R.id.btnSubmit);
        tbOPVVaccinated = v.findViewById(R.id.tbOPVVaccinated);
        tbBCGVaccinated = v.findViewById(R.id.tbBCGVaccinated);
        etRemarks = v.findViewById(R.id.etRemarks);
        etOpv0VaccinationDate = v.findViewById(R.id.etOpv0VaccinationDate);
        etBCGVaccinationDate = v.findViewById(R.id.etBCGVaccinationDate);
        ethepbVaccinationDate = v.findViewById(R.id.ethepbVaccinationDate);
        HepbvacinationDatePicker = v.findViewById(R.id.HepbvacinationDatePicker);
        tbhepbVaccinated = v.findViewById(R.id.tbhepbVaccinated);
        etOpv0VaccinationDate.setVisibility(View.GONE);
        etBCGVaccinationDate.setVisibility(View.GONE);
        BCGvacinationDatePicker.setVisibility(View.GONE);
        vacinationDatePicker.setVisibility(View.GONE);

        if(DateOfBirth!=null){
            vacinationDatePicker.setDate(DateOfBirth);
            BCGvacinationDatePicker.setDate(DateOfBirth);

        }
        ZeroToFortyDaysObject zeroToFortyDaysObject1 = mbundle.getParcelable(Globals.Arguments.ZERO_TO_FOURTEEN_DAYS);

        if (zeroToFortyDaysObject1 != null)

        {
            if (zeroToFortyDaysObject1.getBcgVaccinated() != null && zeroToFortyDaysObject1.getBcgVaccinated().length() > 0) {
                if (zeroToFortyDaysObject1.getBcgVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbBCGVaccinated.setChecked(true);
                    if (zeroToFortyDaysObject1.getBcgVaccinationDate() != null && zeroToFortyDaysObject1.getBcgVaccinationDate().length() > 0) {
                        String[] date = zeroToFortyDaysObject1.getBcgVaccinationDate().split(" ");
                        etBCGVaccinationDate.setVisibility(View.VISIBLE);
                        etBCGVaccinationDate.setText(date[0]);
                        zeroToFortyDaysObject.setBcgVaccinationDate(zeroToFortyDaysObject1.getBcgVaccinationDate());
                    }
                } else
                    tbBCGVaccinated.setChecked(false);
                zeroToFortyDaysObject.setBcgVaccinated(zeroToFortyDaysObject1.getBcgVaccinated());
            }
            if (zeroToFortyDaysObject1.getOpvVaccinated() != null && zeroToFortyDaysObject1.getOpvVaccinated().length() > 0) {
                if (zeroToFortyDaysObject1.getOpvVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbOPVVaccinated.setChecked(true);
                    if (zeroToFortyDaysObject1.getOpvVaccinationDate() != null && zeroToFortyDaysObject1.getOpvVaccinationDate().length() > 0) {
                        String[] date = zeroToFortyDaysObject1.getOpvVaccinationDate().split(" ");
                        etOpv0VaccinationDate.setText(date[0]);
                        etOpv0VaccinationDate.setVisibility(View.VISIBLE);
                        zeroToFortyDaysObject.setOpvVaccinationDate(zeroToFortyDaysObject1.getOpvVaccinationDate());
                    }
                } else
                    tbOPVVaccinated.setChecked(false);
                zeroToFortyDaysObject.setOpvVaccinated(zeroToFortyDaysObject1.getOpvVaccinated());
            }
            if (zeroToFortyDaysObject1.getHepbVaccinated() != null && zeroToFortyDaysObject1.getHepbVaccinated().length() > 0) {
                if (zeroToFortyDaysObject1.getHepbVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbhepbVaccinated.setChecked(true);
                    if (zeroToFortyDaysObject1.getHepVaccinationDate() != null && zeroToFortyDaysObject1.getHepVaccinationDate().length() > 0) {
                        String[] date = zeroToFortyDaysObject1.getHepVaccinationDate().split(" ");
                        ethepbVaccinationDate.setText(date[0]);
                        ethepbVaccinationDate.setVisibility(View.VISIBLE);
                        zeroToFortyDaysObject.setHepbVaccinated(zeroToFortyDaysObject1.getHepbVaccinated());
                    }
                } else
                    tbOPVVaccinated.setChecked(false);
                zeroToFortyDaysObject.setHepbVaccinated(zeroToFortyDaysObject1.getHepbVaccinated());
            }

            etRemarks.setText(zeroToFortyDaysObject1.getRemarks());
        } else {
            zeroToFortyDaysObject.setRemarks("");
            zeroToFortyDaysObject.setBcgVaccinationDate("");
            zeroToFortyDaysObject.setOpvVaccinated("");
            zeroToFortyDaysObject.setOpvVaccinated(opv);
            zeroToFortyDaysObject.setBcgVaccinated(bcg);
            zeroToFortyDaysObject.setHepbVaccinated(hepb);
        }


        vacinationDatePicker.setOnDateSelectedListener(new LazyDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Boolean dateSelected) {
                if(dateSelected){
                    String finalDate = LazyDatePicker.dateToString(vacinationDatePicker.getDate(), "dd-MM-yyyy");

                    int year = Integer.parseInt(String.valueOf(DateFormat.format("yyyy",   vacinationDatePicker.getDate())));
                    int monthOfYear = Integer.parseInt(String.valueOf(DateFormat.format("MM",   vacinationDatePicker.getDate())));
                    int dayOfMonth = Integer.parseInt(String.valueOf(DateFormat.format("dd",   vacinationDatePicker.getDate())));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String currentDate = sdf.format(Calendar.getInstance().getTime());

                    dateString = new StringBuilder();
                    dateString.append(String.format(Locale.ENGLISH, "%02d", year)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-").append(String.format(Locale.ENGLISH, "%02d", dayOfMonth));
                    Date currentDayDate = Utils.getDateFromString(currentDate, Globals.DOB_FORMAT_1);
                    Date selectedDate = Utils.getDateFromString(dateString.toString(), Globals.DOB_FORMAT_1);
                    dateString = new StringBuilder();
                    dateString.append(String.format(Locale.ENGLISH, "%02d", dayOfMonth)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-").append(String.format(Locale.ENGLISH, "%02d", year));
                    if ((selectedDate != null && selectedDate.equals(currentDayDate)) || (selectedDate != null && selectedDate.before(currentDayDate))) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, --monthOfYear, dayOfMonth);
                        DateOfBirth = selectedDate;
                        //resetSubData();
                        // etAge.setText(dateString.toString());

////                daysBetween(selectedDate, currentDayDate);
                        Calendar sDate = Calendar.getInstance();
                        sDate.setTime(selectedDate);
                        Calendar eDate = Calendar.getInstance();
                        eDate.setTime(currentDayDate);
                        DateTime strDate = new DateTime(sDate);
                        DateTime endDate = new DateTime(eDate);
                        Period period = new Period(strDate, endDate, PeriodType.yearMonthDayTime());
                        String yearVal = String.valueOf(period.getYears());
                        String MonthsVal = String.valueOf(period.getMonths());
                        String DaysVal = String.valueOf(period.getDays());


                        etOpv0VaccinationDate.setText(dateString);
                        strOPVDate = dateString.toString();
                        strBCGDate = dateString.toString();
                        etBCGVaccinationDate.setText(dateString);
                        //  ChangeButtonsColor();
//                etAge.setText(yearVal + " y " + MonthsVal + " m " + DaysVal + " d");
//                etAge.setError(null);
                    } else
                        Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name),
                                getActivity(), true, false, getString(R.string.ok), "", false);
                }

//                        Age.setText(finalDate);
            }
        });

        BCGvacinationDatePicker.setOnDateSelectedListener(new LazyDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Boolean dateSelected) {
                if(dateSelected){
                    String finalDate = LazyDatePicker.dateToString(BCGvacinationDatePicker.getDate(), "dd-MM-yyyy");

                    int year = Integer.parseInt(String.valueOf(DateFormat.format("yyyy",   BCGvacinationDatePicker.getDate())));
                    int monthOfYear = Integer.parseInt(String.valueOf(DateFormat.format("MM",   BCGvacinationDatePicker.getDate())));
                    int dayOfMonth = Integer.parseInt(String.valueOf(DateFormat.format("dd",   BCGvacinationDatePicker.getDate())));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String currentDate = sdf.format(Calendar.getInstance().getTime());
                    dateString = new StringBuilder();
                    dateString.append(String.format(Locale.ENGLISH, "%02d", year)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-").append(String.format(Locale.ENGLISH, "%02d", dayOfMonth));
                    Date currentDayDate = Utils.getDateFromString(currentDate, Globals.DOB_FORMAT_1);
                    Date selectedDate = Utils.getDateFromString(dateString.toString(), Globals.DOB_FORMAT_1);
                    dateString = new StringBuilder();
                    dateString.append(String.format(Locale.ENGLISH, "%02d", dayOfMonth)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-").append(String.format(Locale.ENGLISH, "%02d", year));
                    if ((selectedDate != null && selectedDate.equals(currentDayDate)) || (selectedDate != null && selectedDate.before(currentDayDate))) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, --monthOfYear, dayOfMonth);
                        DateOfBirth = selectedDate;
                        //resetSubData();
                        // etAge.setText(dateString.toString());

////                daysBetween(selectedDate, currentDayDate);
                        Calendar sDate = Calendar.getInstance();
                        sDate.setTime(selectedDate);
                        Calendar eDate = Calendar.getInstance();
                        eDate.setTime(currentDayDate);
                        DateTime strDate = new DateTime(sDate);
                        DateTime endDate = new DateTime(eDate);
                        Period period = new Period(strDate, endDate, PeriodType.yearMonthDayTime());
                        String yearVal = String.valueOf(period.getYears());
                        String MonthsVal = String.valueOf(period.getMonths());
                        String DaysVal = String.valueOf(period.getDays());


                        strBCGDate = dateString.toString();
                        etBCGVaccinationDate.setText(dateString);

                        //  ChangeButtonsColor();
//                etAge.setText(yearVal + " y " + MonthsVal + " m " + DaysVal + " d");
//                etAge.setError(null);
                    } else
                        Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name),
                                getActivity(), true, false, getString(R.string.ok), "", false);
                }

//                        Age.setText(finalDate);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_zero_to_forteen_days_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {
    }

    @Override
    protected void attachListeners() {
        btnSubmit.setOnClickListener(this);
        tbOPVVaccinated.setOnCheckedChangeListener(this);
        tbBCGVaccinated.setOnCheckedChangeListener(this);
        tbhepbVaccinated.setOnCheckedChangeListener(this);
        etBCGVaccinationDate.setOnClickListener(this);
        etOpv0VaccinationDate.setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        dateCalculation();

    }

    @Override
    public void onResume() {
        super.onResume();
        setNavigationTitle(getString(R.string.zero_to_forty_weeks));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (isValidate()) {
                    zeroToFortyDaysObject.setRemarks(etRemarks.getText().toString());
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.ZERO_TO_FOURTEEN_DAYS, zeroToFortyDaysObject));
                    getActivity().onBackPressed();
                }

                break;
            case R.id.tbhepbVaccinated:

                showDatePickerDialog(Hepb_REQUEST_CODE, this, DateOfBirth);
                break;
            case R.id.etOpv0VaccinationDate:

                showDatePickerDialog(OPV_REQUEST_CODE, this, DateOfBirth);
                break;
            case R.id.etBCGVaccinationDate:
                showDatePickerDialog(BCG_REQUEST_CODE, this, DateOfBirth);
//                showDatePickerDialog(BCG_REQUEST_CODE, this);
//                showDatePickerDialog();
                break;

            default:
                super.onClick(view);
                break;
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tbOPVVaccinated:
                if (b)

                {
                    if(DateOfBirth != null ){
                        etOpv0VaccinationDate.setVisibility(View.VISIBLE);

                        vacinationDatePicker.setVisibility(View.VISIBLE);
                    }

                } else {
                    etOpv0VaccinationDate.setVisibility(View.GONE);
                    vacinationDatePicker.setVisibility(View.GONE);
                }
                opv = String.valueOf(b);
                zeroToFortyDaysObject.setOpvVaccinated(opv);
                break;
            case R.id.tbBCGVaccinated:
                if (b) {
                    if(DateOfBirth != null ){
                        BCGvacinationDatePicker.setVisibility(View.VISIBLE);
                        etBCGVaccinationDate.setVisibility(View.VISIBLE);
                    }


                } else {
                    BCGvacinationDatePicker.setVisibility(View.GONE);
                    etBCGVaccinationDate.setVisibility(View.GONE);
                }
                bcg = String.valueOf(b);
                zeroToFortyDaysObject.setBcgVaccinated(bcg);
                break;
            case R.id.tbhepbVaccinated:
                if (b) {
                    if(DateOfBirth != null ){
                        HepbvacinationDatePicker.setVisibility(View.VISIBLE);
                        ethepbVaccinationDate.setVisibility(View.VISIBLE);
                    }


                } else {
                    HepbvacinationDatePicker.setVisibility(View.GONE);
                    ethepbVaccinationDate.setVisibility(View.GONE);
                }
                hepb = String.valueOf(b);
                zeroToFortyDaysObject.setHepbVaccinated(hepb);
                break;
        }
    }


    @Override
    public void onDateSet(String dateOfVaccinate, String dateString, int requestCode) {
        switch (requestCode) {
            case OPV_REQUEST_CODE:
                  /* /////Client requirement to set date of below fields when selected date from 1st field
                 //////// but below fields date can be changeable//////////////////*/
                etOpv0VaccinationDate.setText(dateString);
                strOPVDate = dateOfVaccinate;
                strBCGDate = dateOfVaccinate;
                etBCGVaccinationDate.setText(dateString);
                break;
            case BCG_REQUEST_CODE:
                strBCGDate = dateOfVaccinate;
                etBCGVaccinationDate.setText(dateString);
                break;
        }
    }

    @Override
    public void onDialogPositiveButtonClick(int requestCode) {

    }

    @Override
    public void onDialogNegativeButtonClick(int requestCode) {

    }

    private boolean isValidate() {
        int values = 0;
        if (opv.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etOpv0VaccinationDate.getText().length() > 0) {
                zeroToFortyDaysObject.setOpvVaccinationDate(strOPVDate);

            } else {
                setError(etOpv0VaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
                values = 1;
            }
        }
        if (bcg.equalsIgnoreCase(getString(R.string.true_)) && DateOfBirth != null) {
            if (etBCGVaccinationDate.getText().length() > 0 ) {
                zeroToFortyDaysObject.setBcgVaccinationDate(strBCGDate);
            } else {
                values = 1;
                setError(etBCGVaccinationDate, getResources().getString(R.string.enter_date_of_vaccination));
            }

        }
        return values == 0;
    }

}
