package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.hisdu.SESCluster.base.BaseFragment;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDateSet;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.R;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class BaseVaccinationFragment extends BaseFragment {

    Calendar myCalendar = Calendar.getInstance();
    private StringBuilder dateString;
    private String currentDate;
    protected String strDateOfVaccinate;

    //    protected EditText etDateOfVaccination;
    OnDateSet mOnDateSet;

    EditText mEditText;
    int mRequestCode;
    Date mDateOfBirth = new Date();

    public static BaseVaccinationFragment getInstance(Bundle bundle, String title, int icon) {
        BaseVaccinationFragment fragment = new BaseVaccinationFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {

    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {

    }

    @Override
    protected void initializeData() {


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    public void showDatePickerDialog(int requestCode, OnDateSet onDateSet) {
        mRequestCode = requestCode;
        mOnDateSet = onDateSet;


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, mDateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showDatePickerDialog(int requestCode, OnDateSet onDateSet, Date dob) {
        mRequestCode = requestCode;
        mOnDateSet = onDateSet;
        mDateOfBirth = dob;

        String day          = (String) DateFormat.format("dd",   mDateOfBirth); // 20
        String monthNumber  = (String) DateFormat.format("MM",   mDateOfBirth); // 06
        String year         = (String) DateFormat.format("yyyy", mDateOfBirth); // 2013

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, mDateSetListener1, Integer.parseInt(year), Integer.parseInt(monthNumber) - 1, Integer.parseInt(day));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            monthOfYear++;
            dateString = new StringBuilder();
            dateString.append(String.format(Locale.ENGLISH, "%02d", year)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-")
                    .append(String.format(Locale.ENGLISH, "%02d", dayOfMonth));
            Date currentDayDate = Utils.getDateFromString(currentDate, Globals.DOB_FORMAT_1);
            Date selectedDate = Utils.getDateFromString(dateString.toString(), Globals.DOB_FORMAT_1);
            dateString = new StringBuilder();
            dateString.append(String.format(Locale.ENGLISH, "%02d", dayOfMonth)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-")
                    .append(String.format(Locale.ENGLISH, "%02d", year));
            if ((selectedDate != null && selectedDate.equals(currentDayDate)) || (selectedDate != null && selectedDate.before(currentDayDate))) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, --monthOfYear, dayOfMonth);

                strDateOfVaccinate = Utils.getDate(cal.getTimeInMillis(), Globals.WEB_DATE_FORMAT1);
//                etDateOfVaccination.setText(Utils.getDate(cal.getTimeInMillis(), Globals.DOB_FORMAT_1));
//                etDateOfVaccination.setError(null);
                mOnDateSet.onDateSet(strDateOfVaccinate, Utils.getDate(cal.getTimeInMillis(), Globals.DOB_FORMAT_1), mRequestCode);
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
//                etAge.setText(yearVal + " y " + MonthsVal + " m " + DaysVal + " d");
//                etAge.setError(null);
            } else
                Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name),
                        getActivity(), true, false, getString(R.string.ok), "", false);

        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            monthOfYear++;
            dateString = new StringBuilder();
            dateString.append(String.format(Locale.ENGLISH, "%02d", year)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-")
                    .append(String.format(Locale.ENGLISH, "%02d", dayOfMonth));
            Date currentDayDate = Utils.getDateFromString(currentDate, Globals.DOB_FORMAT_1);
            Date selectedDate = Utils.getDateFromString(dateString.toString(), Globals.DOB_FORMAT_1);
            dateString = new StringBuilder();
            dateString.append(String.format(Locale.ENGLISH, "%02d", dayOfMonth)).append("-").append(String.format(Locale.ENGLISH, "%02d", monthOfYear)).append("-")
                    .append(String.format(Locale.ENGLISH, "%02d", year));
//            if (mDateOfBirth != null) {
//                if ((selectedDate != null && selectedDate.equals(mDateOfBirth))||(selectedDate != null && selectedDate.after(mDateOfBirth))) {
                    if ((selectedDate != null && selectedDate.equals(currentDayDate)) || (selectedDate != null && selectedDate.before(currentDayDate))) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, --monthOfYear, dayOfMonth);

                        strDateOfVaccinate = Utils.getDate(cal.getTimeInMillis(), Globals.WEB_DATE_FORMAT1);
//                etDateOfVaccination.setText(Utils.getDate(cal.getTimeInMillis(), Globals.DOB_FORMAT_1));
//                etDateOfVaccination.setError(null);
                        mOnDateSet.onDateSet(strDateOfVaccinate, Utils.getDate(cal.getTimeInMillis(), Globals.DOB_FORMAT_1), mRequestCode);
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
//                etAge.setText(yearVal + " y " + MonthsVal + " m " + DaysVal + " d");
//                etAge.setError(null);
                    } else
                        Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name),
                                getActivity(), true, false, getString(R.string.ok), "", false);
                }
//                else
//                    Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow1), getString(R.string.app_name),
//                            getActivity(), true, false, getString(R.string.ok), "", false);


    };

    protected void dateCalculation() {
        Calendar calendarDate = Calendar.getInstance();
        int month = calendarDate.get(Calendar.MONTH);
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        currentDate = calendarDate.get(Calendar.YEAR) + "-" +
                String.format(Locale.ENGLISH, "%02d", month + 1) + "-" +
                String.format(Locale.ENGLISH, "%02d", day);
    }

}
