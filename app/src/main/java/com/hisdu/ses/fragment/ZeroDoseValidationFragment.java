package com.hisdu.ses.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.UcData;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.Models.provinces.Province;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.CustomSearchableSpinner;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ZeroDoseValidationFragment extends Fragment {

    String ChildNameValue = null, FatherNameValue = null, PhoneNoValue = null, AgeValue = null, AddressValue = null, VaccinatedValue = null, cardNoValue = null;
    EditText ChildName, FatherName, PhoneNo, Address, cardNo;
    LazyDatePicker lazyDatePicker;
    RadioButton VaccinatedYes, VaccinatedNo;
    Button proceed;
    FragmentManager fragmentManager;
    private StringBuilder dateString;
    private String currentDate;
    private int days = -1;
    Calendar myCalendar = Calendar.getInstance();
    Date DateOfBirth = new Date();
    LinearLayout cardlayout;
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    Date minDate = LazyDatePicker.stringToDate("01-01-2019", DATE_FORMAT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zero_dose_fragment, container, false);
        lazyDatePicker = view.findViewById(R.id.lazyDatePicker);

        proceed = view.findViewById(R.id.proceed);
        ChildName = view.findViewById(R.id.ChildName);
        FatherName = view.findViewById(R.id.FatherName);
        PhoneNo = view.findViewById(R.id.PhoneNo);
//        Age = view.findViewById(R.id.Age);
        Address = view.findViewById(R.id.Address);
        VaccinatedYes = view.findViewById(R.id.VaccinatedYes);
        VaccinatedNo = view.findViewById(R.id.VaccinatedNo);
        cardNo = view.findViewById(R.id.cardNo);
        cardlayout = view.findViewById(R.id.cardlayout);

        fragmentManager = getFragmentManager();

        lazyDatePicker.setMinDate(minDate);



        proceed.setOnClickListener(v ->

        {

            if (validate()) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You want to save Form!")
                        .setConfirmText("Yes,Go Save!")
                        .setCancelText("No")

                        .setCancelClickListener(sDialog -> sDialog.dismiss())
                        .setConfirmClickListener(sDialog -> {
                            submit();
                            sDialog.dismiss();
                        })
                        .show();
//                if (AppController.getInstance().hasinternetAccess) {
//
//                } else {
//                    proceed.setEnabled(true);
//                    Toast.makeText(MainActivity.main, "No Internet", Toast.LENGTH_SHORT).show();
//                }
            }
        });


        ChildName.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && ChildName.isEnabled()) {
                if (ChildName.length() != 0) {
                    ChildNameValue = ChildName.getText().toString();
                } else {
                    ChildNameValue = null;
                }
            }

        });

        FatherName.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && FatherName.isEnabled()) {
                if (FatherName.length() != 0) {
                    FatherNameValue = FatherName.getText().toString();
                } else {
                    FatherNameValue = null;
                }
            }

        });

        PhoneNo.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && PhoneNo.isEnabled()) {
                if (PhoneNo.length() != 0) {
                    PhoneNoValue = PhoneNo.getText().toString();
                } else {
                    PhoneNoValue = null;
                }
            }

        });

        lazyDatePicker.setOnDatePickListener(dateSelected -> {
            String finalDate = LazyDatePicker.dateToString(lazyDatePicker.getDate(), "dd-MM-yyyy");
            AgeValue = finalDate;
            try {
                DateValidator();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Address.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && Address.isEnabled()) {
                if (Address.length() != 0) {
                    AddressValue = Address.getText().toString();
                } else {
                    AddressValue = null;
                }
            }

        });

        cardNo.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && cardNo.isEnabled()) {
                if (cardNo.length() != 0) {
                    cardNoValue = cardNo.getText().toString();
                } else {
                    cardNoValue = null;
                }
            }

        });

        VaccinatedYes.setOnClickListener(v -> {
            VaccinatedValue = VaccinatedYes.getText().toString();
            cardlayout.setVisibility(View.VISIBLE);


        });

        VaccinatedNo.setOnClickListener(v -> {
//            VaccinatedValue = VaccinatedNo.getText().toString();
//            cardlayout.setVisibility(View.GONE);

        });





        return view;
    }

    void submit()

    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        ZeroDoseDetail zeroDoseDetail = new ZeroDoseDetail();

        zeroDoseDetail.setChildName(ChildNameValue);
        zeroDoseDetail.setFatherName(FatherNameValue);
        if (VaccinatedValue != null) { zeroDoseDetail.setCardNo(cardNoValue); }
        zeroDoseDetail.setDOB(AgeValue);
        zeroDoseDetail.setAddress(AddressValue);
        zeroDoseDetail.setSync("0");
        zeroDoseDetail.setCreatedBy(Integer.valueOf(new SharedPref(getActivity()).GetserverID()));
        zeroDoseDetail.setCreatedOn(currentDate);
        zeroDoseDetail.setPhoneNo(PhoneNoValue);
        zeroDoseDetail.setLatitude(AppController.getInstance().location.getLatitude());
        zeroDoseDetail.setLongitude(AppController.getInstance().location.getLongitude());
        if (VaccinatedValue.equals("Yes")) { zeroDoseDetail.setIsVaccinated(true); }
        else { zeroDoseDetail.setIsVaccinated(false); }
        AppController.getInstance().zeroDoseDetail = zeroDoseDetail;
        ZeroDoseMasterFragment.zeroDoseMasterFragment.UpdateLogList();
        MainActivity.main.onBackPressed();
    }


    public boolean validate() {
        boolean valid = true;

        if (ChildNameValue == null) {
//            Toast.makeText(getContext(), "Please enter child name!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please enter child name!")
                    .show();
            valid = false;
            return valid;
        }

        if (FatherNameValue == null) {
//            Toast.makeText(getContext(), "Please enter father name!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please enter father name!")
                    .show();
            valid = false;
            return valid;
        }

        if (PhoneNoValue == null) {
//            Toast.makeText(getContext(), "Please enter phone no!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please enter phone no!")
                    .show();
            valid = false;
            return valid;
        }

        if (AgeValue == null) {
//            Toast.makeText(getContext(), "Please enter age!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please enter age!")
                    .show();
            valid = false;
            return valid;
        }

        if (AddressValue == null) {
//            Toast.makeText(getContext(), "Please enter address!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please enter address!")
                    .show();
            valid = false;
            return valid;
        }

        if (VaccinatedValue == null) {
//            Toast.makeText(getContext(), "Please select vaccinated!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext())
                    .setTitleText("Please select vaccinated!")
                    .show();
            valid = false;
            return valid;
        } else {
            if (cardNoValue == null && VaccinatedValue.toLowerCase().equals("yes")) {
//                Toast.makeText(getContext(), "Please enter Card No!", Toast.LENGTH_LONG).show();
                new SweetAlertDialog(getContext())
                        .setTitleText("Please enter Card No!")
                        .show();
                valid = false;
                return valid;
            }
        }

        return valid;
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, mDateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            monthOfYear++;
            Calendar cal = Calendar.getInstance();
            cal.set(year, --monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat sdfShow = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(cal.getTime());
            String currentDateShow = sdfShow.format(cal.getTime());
            AgeValue = currentDate;
//            Age.setText(currentDateShow);

        }
    };

    void DateValidator() throws ParseException {

        Date result;
        Calendar now;
        now             = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        result   = df.parse(AgeValue);
        Date current  = now.getTime();

        DateTime t = new DateTime(result);
        AgeValue   = t.toString("dd-MM-yyyy");

        if (result.after(current))

        {
            Toast.makeText(MainActivity.main, "Please select valid date of birth", Toast.LENGTH_SHORT).show();
            lazyDatePicker.clear();//setDate(new Date());
            AgeValue = null;
        }
    }



}
