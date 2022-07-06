package com.hisdu.SESCluster.fragments.navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;
import com.hisdu.SESCluster.fragments.support.SpinnerFragment;
import com.hisdu.SESCluster.fragments.vaccinations.BoosterDoseFragment;
import com.hisdu.SESCluster.objects.BoosterDoseObject;
import com.hisdu.SESCluster.objects.support.SpinnerObject;
import com.hisdu.ses.AppController;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.base.BaseFragment;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.fragments.vaccinations.FifteenToSixteenMonthsFragment;
import com.hisdu.SESCluster.fragments.vaccinations.FourteenToEighteenWeeksFragment;
import com.hisdu.SESCluster.fragments.vaccinations.NineToTenMonthsFragment;
import com.hisdu.SESCluster.fragments.vaccinations.SixToTenWeeksFragment;
import com.hisdu.SESCluster.fragments.vaccinations.TenToFourteenWeeksFragment;
import com.hisdu.SESCluster.fragments.vaccinations.ZeroToFourteenDaysFragment;
import com.hisdu.SESCluster.objects.ChildObject;
import com.hisdu.SESCluster.objects.FifteenToSixteenMonthsObject;
import com.hisdu.SESCluster.objects.FourteenToEighteenWeeksObject;
import com.hisdu.SESCluster.objects.NineToTenMonthsObjects;
import com.hisdu.SESCluster.objects.SixToTenWeeksObject;
import com.hisdu.SESCluster.objects.TenToFourteenWeeksObject;
import com.hisdu.SESCluster.objects.ZeroToFortyDaysObject;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.SharedPref;
import com.iceteck.silicompressorr.SiliCompressor;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class AddChildFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    ToggleButton tbVaccinated, tbCardAvailable;
    TextInputLayout tilRemarks, tilCardNo;
    EditText etRemarks, etCardNo, etAge, etName, etFatherName, etMobile, etHouseNo;
    Spinner ageinmonth;
    LinearLayout llCardAvailable, llButton;
    Calendar myCalendar = Calendar.getInstance();
    AppCompatButton btnZero2fortyWeeks, btnSixToTenWeeks, btnTenToFourteenWeeks, btnFourteenToEighteenWeeks, btnNineToTenMonths,
            btnFifteenToSixteenMonths, btnSubmit, dp_booster;
    private StringBuilder dateString;
    private String currentDate;
    private int days = -1;
    ZeroToFortyDaysObject zeroToFortyDaysObject;
    SixToTenWeeksObject sixToTenWeeksObject;
    TenToFourteenWeeksObject tenToFourteenWeeksObject;
    FourteenToEighteenWeeksObject fourteenToEighteenWeeksObject;
    NineToTenMonthsObjects nineToTenMonthsObjects;
    FifteenToSixteenMonthsObject fifteenToSixteenMonthsObject;
    BoosterDoseObject boosterDoseObject;
    ChildObject childObject = new ChildObject();
    private String cardAvailable = "false", vaccinated = "false";
    Date DateOfBirth = null;//new Date();
    String[] mtextArray = {"Select age in months", "<1", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String ageinmonthtValue;
    LazyDatePicker lazyDatePicker;

    LinearLayout AttachImages;

    View view;
    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    TextView imagesCount;
    List<String> indicatorsMasterSaveImages = new ArrayList<>();


    public static AddChildFragment getInstance(Bundle bundle, String title, int icon) {
        AddChildFragment fragment = new AddChildFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        view=v;
        tbVaccinated = v.findViewById(R.id.tbVaccinated);
        tbCardAvailable = v.findViewById(R.id.tbCardAvailable);
        tilRemarks = v.findViewById(R.id.tilRemarks);
        tilCardNo = v.findViewById(R.id.tilCardNo);
        etRemarks = v.findViewById(R.id.etRemarks);
        etCardNo = v.findViewById(R.id.etCardNo);
        etAge = v.findViewById(R.id.etAge);
        etName = v.findViewById(R.id.etName);
        etFatherName = v.findViewById(R.id.etFatherName);
        etMobile = v.findViewById(R.id.etMobile);
        llCardAvailable = v.findViewById(R.id.llCardAvailable);
        btnZero2fortyWeeks = v.findViewById(R.id.btnZero2fortyWeeks);
        btnSixToTenWeeks = v.findViewById(R.id.btnSixToTenWeeks);
        btnTenToFourteenWeeks = v.findViewById(R.id.btnTenToFourteenWeeks);
        btnFourteenToEighteenWeeks = v.findViewById(R.id.btnFourteenToEighteenWeeks);
        btnNineToTenMonths = v.findViewById(R.id.btnNineToTenMonths);
        btnFifteenToSixteenMonths = v.findViewById(R.id.btnFifteenToSixteenMonths);
        llButton = v.findViewById(R.id.llButton);
        etHouseNo = v.findViewById(R.id.etHouseNo);
        ageinmonth = v.findViewById(R.id.ageinmonth);
        lazyDatePicker = v.findViewById(R.id.lazyDatePicker);
        AttachImages = v.findViewById(R.id.AttachImages);
        imagesCount = v.findViewById(R.id.imagesCount);
        dp_booster = v.findViewById(R.id.dp_booster);
        AttachImages.setOnClickListener(v1 -> multiImagePicker());

        if(mResults!=null){
            if(imagesCount!=null){
                imagesCount.setText(mResults.size()+"");
            }
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mtextArray);
        ageinmonth.setAdapter(mAdapter);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        if (days > -1)
            enableDisableButtons(days);

        ageinmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (ageinmonth.getSelectedItemPosition() != 0) {
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.MONTH, -i);
//                    Date date = calendar.getTime();
//                    DateOfBirth= date;
//                    SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
//                    String dateOutput = format.format(date);

                    ageinmonthtValue = ageinmonth.getSelectedItem().toString();

                    int days = 0;

                    if (ageinmonth.getSelectedItemPosition() == 1) {
                        days = 29;
                    } else {
                        days = Integer.parseInt(ageinmonthtValue) * 30;
                    }

//                    resetSubData();
                    enableDisableButtons(days);
                } else {
                    ageinmonthtValue = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        if (DateOfBirth != null) {
            lazyDatePicker.setDate(DateOfBirth);
        }

        lazyDatePicker.setOnDateSelectedListener(dateSelected ->

        {
            if (dateSelected) {
                String finalDate = LazyDatePicker.dateToString(lazyDatePicker.getDate(), "dd-MM-yyyy");

                int year = Integer.parseInt(String.valueOf(DateFormat.format("yyyy", lazyDatePicker.getDate())));
                int monthOfYear = Integer.parseInt(String.valueOf(DateFormat.format("MM", lazyDatePicker.getDate())));
                int dayOfMonth = Integer.parseInt(String.valueOf(DateFormat.format("dd", lazyDatePicker.getDate())));

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
                    days = getDaysDifference(selectedDate, currentDayDate);
                    etAge.setText(dateString.toString());
                    enableDisableButtons(days);
                } else
                    Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name), getActivity(), true, false, getString(R.string.ok), "", false);
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add_child_layout;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {

    }

    @Override
    protected void attachListeners() {
        tbVaccinated.setOnCheckedChangeListener(this);
        tbCardAvailable.setOnCheckedChangeListener(this);
        btnZero2fortyWeeks.setOnClickListener(this);
        btnSixToTenWeeks.setOnClickListener(this);
        btnTenToFourteenWeeks.setOnClickListener(this);
        btnFourteenToEighteenWeeks.setOnClickListener(this);
        btnNineToTenMonths.setOnClickListener(this);
        btnFifteenToSixteenMonths.setOnClickListener(this);
        etAge.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        dp_booster.setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        dateCalculation();
    }

    @Override
    public void onResume() {
        super.onResume();
        ChangeButtonsColor();
        setNavigationTitle(getString(R.string.add_child));
        hideToolBarSync();

    }

    public void setVaccinatedStatus(ChildObject rec) {
        rec.setVaccinated("false");

        if (btnZero2fortyWeeks.isEnabled()) {
            if (rec.getZeroToFortyDaysObject() != null) {
                if ((rec.getZeroToFortyDaysObject().getOpvVaccinated() != null && rec.getZeroToFortyDaysObject().getOpvVaccinated().equals("true")) || (rec.getZeroToFortyDaysObject().getBcgVaccinated() != null && rec.getZeroToFortyDaysObject().getBcgVaccinated().equals("true")) || (rec.getZeroToFortyDaysObject().getHepbVaccinated() != null && rec.getZeroToFortyDaysObject().getHepbVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            ZeroToFortyDaysObject object = new ZeroToFortyDaysObject();
            childObject.setZeroToFortyDaysObject(object);
        }


        if (btnSixToTenWeeks.isEnabled()) {
            if (rec.getSixToTenWeeksObject() != null) {
                if ((rec.getSixToTenWeeksObject().getOpVOneVaccinated() != null && rec.getSixToTenWeeksObject().getOpVOneVaccinated().equals("true")) ||
                        (rec.getSixToTenWeeksObject().getPcvTenOneVaccinated() != null && rec.getSixToTenWeeksObject().getPcvTenOneVaccinated().equals("true")) ||
                        (rec.getSixToTenWeeksObject().getPentaOneVaccinated() != null && rec.getSixToTenWeeksObject().getPentaOneVaccinated().equals("true")) ||
                        (rec.getSixToTenWeeksObject().getRotaVaccineOneVaccinated() != null && rec.getSixToTenWeeksObject().getRotaVaccineOneVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            SixToTenWeeksObject object = new SixToTenWeeksObject();

            childObject.setSixToTenWeeksObject(object);
        }

        if (btnTenToFourteenWeeks.isEnabled()) {
            if (rec.getTenToFourteenWeeksObject() != null) {
                if ((rec.getTenToFourteenWeeksObject().getOpvVaccinated() != null && rec.getTenToFourteenWeeksObject().getOpvVaccinated().equals("true")) ||
                        (rec.getTenToFourteenWeeksObject().getPcv10Vaccinated() != null && rec.getTenToFourteenWeeksObject().getPcv10Vaccinated().equals("true")) ||
                        (rec.getTenToFourteenWeeksObject().getPentaVaccinated() != null && rec.getTenToFourteenWeeksObject().getPentaVaccinated().equals("true")) ||
                        (rec.getTenToFourteenWeeksObject().getRotaVaccinated() != null && rec.getTenToFourteenWeeksObject().getRotaVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {

            TenToFourteenWeeksObject object = new TenToFourteenWeeksObject();

            childObject.setTenToFourteenWeeksObject(object);

        }


        if (btnFourteenToEighteenWeeks.isEnabled()) {
            if (rec.getFourteenToEighteenWeeksObject() != null) {
                if ((rec.getFourteenToEighteenWeeksObject().getIpvVaccinated() != null && rec.getFourteenToEighteenWeeksObject().getIpvVaccinated().equals("true")) ||
                        (rec.getFourteenToEighteenWeeksObject().getOpvVaccinated() != null && rec.getFourteenToEighteenWeeksObject().getOpvVaccinated().equals("true")) ||
                        (rec.getFourteenToEighteenWeeksObject().getPcv10Vaccinated() != null && rec.getFourteenToEighteenWeeksObject().getPcv10Vaccinated().equals("true")) ||
                        (rec.getFourteenToEighteenWeeksObject().getPentaVaccinated() != null && rec.getFourteenToEighteenWeeksObject().getPentaVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            FourteenToEighteenWeeksObject object = new FourteenToEighteenWeeksObject();

            childObject.setFourteenToEighteenWeeksObject(object);

        }

        if (btnNineToTenMonths.isEnabled()) {
            if (rec.getNineToTenMonthsObjects() != null) {
                if ((rec.getNineToTenMonthsObjects().getMeaslesOneVaccinated() != null && rec.getNineToTenMonthsObjects().getMeaslesOneVaccinated().equals("true")) ||
                        (rec.getNineToTenMonthsObjects().getIPV2Vaccinated() != null && rec.getNineToTenMonthsObjects().getIPV2Vaccinated().equals("true")) ||
                        (rec.getNineToTenMonthsObjects().getTCVVaccinated() != null && rec.getNineToTenMonthsObjects().getTCVVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            NineToTenMonthsObjects object = new NineToTenMonthsObjects();
            childObject.setNineToTenMonthsObjects(object);

        }

        if (btnFifteenToSixteenMonths.isEnabled()) {
            if (rec.getFifteenToSixteenMonthsObject() != null) {
                if ((rec.getFifteenToSixteenMonthsObject().getMeaslesTwoVaccinated() != null && rec.getFifteenToSixteenMonthsObject().getMeaslesTwoVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            FifteenToSixteenMonthsObject object = new FifteenToSixteenMonthsObject();

            childObject.setFifteenToSixteenMonthsObject(object);

        }


        if (dp_booster.isEnabled()) {
            if (rec.getBoosterDoseObject() != null) {
                if ((rec.getBoosterDoseObject().getBoosterVaccinated() != null && rec.getBoosterDoseObject().getBoosterVaccinated().equals("true"))) {
                    rec.setVaccinated("true");
                }
            }
        } else {
            BoosterDoseObject object = new BoosterDoseObject();

            childObject.setBoosterDoseObject(object);

        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnZero2fortyWeeks:
                Bundle bundle = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle.putSerializable("DOB", DateOfBirth);
//                }
                if (zeroToFortyDaysObject != null)
                    if (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().length() > 0 ||
                            zeroToFortyDaysObject.getOpvVaccinated() != null && zeroToFortyDaysObject.getOpvVaccinated().length() > 0)
                        bundle.putParcelable(Globals.Arguments.ZERO_TO_FOURTEEN_DAYS, zeroToFortyDaysObject);
                ZeroToFourteenDaysFragment zeroToFourteenDaysFragment = ZeroToFourteenDaysFragment.getInstance(bundle, "", -1);
                zeroToFourteenDaysFragment.setTargetFragment(this, Globals.RequestCodes.Zero_To_40_days);
                replaceFragment(zeroToFourteenDaysFragment, true, false, true, zeroToFourteenDaysFragment.getTag());
                break;
            case R.id.btnSixToTenWeeks:
                Bundle bundle1 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle1.putSerializable("DOB", DateOfBirth);
//                }
                if (sixToTenWeeksObject != null)
                    if (sixToTenWeeksObject.getOpVOneVaccinated() != null && sixToTenWeeksObject.getOpVOneVaccinated().length() > 0 ||
                            sixToTenWeeksObject.getPcvTenOneVaccinated() != null && sixToTenWeeksObject.getPcvTenOneVaccinated().length() > 0 ||
                            sixToTenWeeksObject.getPentaOneVaccinated() != null && sixToTenWeeksObject.getPentaOneVaccinated().length() > 0 ||
                            sixToTenWeeksObject.getRotaVaccineOneVaccinated() != null && sixToTenWeeksObject.getRotaVaccineOneVaccinated().length() > 0)
                        bundle1.putParcelable(Globals.Arguments.SIX_TO_TEN_WEEKS, sixToTenWeeksObject);
                SixToTenWeeksFragment sixToTenWeeksFragment = SixToTenWeeksFragment.getInstance(bundle1, "", -1);
                sixToTenWeeksFragment.setTargetFragment(this, Globals.RequestCodes.Six_to_Ten_Weeks);
                replaceFragment(sixToTenWeeksFragment, true, false, true, sixToTenWeeksFragment.getTag());
                break;
            case R.id.btnTenToFourteenWeeks:
                Bundle bundle2 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle2.putSerializable("DOB", DateOfBirth);
//                }
                if (tenToFourteenWeeksObject != null)
                    if (tenToFourteenWeeksObject.getOpvVaccinated() != null && tenToFourteenWeeksObject.getOpvVaccinated().length() > 0 ||
                            tenToFourteenWeeksObject.getPcv10Vaccinated() != null && tenToFourteenWeeksObject.getPcv10Vaccinated().length() > 0 ||
                            tenToFourteenWeeksObject.getPentaVaccinated() != null && tenToFourteenWeeksObject.getPentaVaccinated().length() > 0 ||
                            tenToFourteenWeeksObject.getRotaVaccinated() != null && tenToFourteenWeeksObject.getRotaVaccinated().length() > 0)
                        bundle2.putParcelable(Globals.Arguments.TEN_TO_FOURTEEN_WEEKS, tenToFourteenWeeksObject);
                TenToFourteenWeeksFragment tenToFourteenWeeksFragment = TenToFourteenWeeksFragment.getInstance(bundle2, "", -1);
                tenToFourteenWeeksFragment.setTargetFragment(this, Globals.RequestCodes.Ten_to_Fourteen_Weeks);
                replaceFragment(tenToFourteenWeeksFragment, true, false, true, tenToFourteenWeeksFragment.getTag());
                break;
            case R.id.btnFourteenToEighteenWeeks:
                Bundle bundle3 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle3.putSerializable("DOB", DateOfBirth);
//                }
                if (fourteenToEighteenWeeksObject != null)
                    if (fourteenToEighteenWeeksObject.getIpvVaccinated() != null && fourteenToEighteenWeeksObject.getIpvVaccinated().length() > 0 ||
                            fourteenToEighteenWeeksObject.getOpvVaccinated() != null && fourteenToEighteenWeeksObject.getOpvVaccinated().length() > 0 ||
                            fourteenToEighteenWeeksObject.getPcv10Vaccinated() != null && fourteenToEighteenWeeksObject.getPcv10Vaccinated().length() > 0 ||
                            fourteenToEighteenWeeksObject.getPentaVaccinated() != null && fourteenToEighteenWeeksObject.getPentaVaccinated().length() > 0)
                        bundle3.putParcelable(Globals.Arguments.FOURTEEN_TO_EIGHTEEN_WEEKS, fourteenToEighteenWeeksObject);
                FourteenToEighteenWeeksFragment fourteenToEighteenWeeksFragment = FourteenToEighteenWeeksFragment.getInstance(bundle3, "", -1);
                fourteenToEighteenWeeksFragment.setTargetFragment(this, Globals.RequestCodes.Fourteen_to_Eighteen_Weeks);
                replaceFragment(fourteenToEighteenWeeksFragment, true, false, true, fourteenToEighteenWeeksFragment.getTag());
                break;
            case R.id.btnNineToTenMonths:
                Bundle bundle4 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle4.putSerializable("DOB", DateOfBirth);
//                }
                if (nineToTenMonthsObjects != null)
                    if (nineToTenMonthsObjects.getMeaslesOneVaccinated() != null || nineToTenMonthsObjects.getMeaslesOneVaccinated().length() > 0)
                        bundle4.putParcelable(Globals.Arguments.NINE_TO_TEN_MONTHS, nineToTenMonthsObjects);
                NineToTenMonthsFragment nineToTenMonthsFragment = NineToTenMonthsFragment.getInstance(bundle4, "", -1);
                nineToTenMonthsFragment.setTargetFragment(this, Globals.RequestCodes.Nine_to_Ten_months);
                replaceFragment(nineToTenMonthsFragment, true, false, true, nineToTenMonthsFragment.getTag());
                break;
            case R.id.btnFifteenToSixteenMonths:
                Bundle bundle5 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle5.putSerializable("DOB", DateOfBirth);
//                }
                if (fifteenToSixteenMonthsObject != null)
                    if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated() != null && fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().length() > 0)
                        bundle5.putParcelable(Globals.Arguments.FIFTEEN_TO_SIXTEEN_MONTHS, fifteenToSixteenMonthsObject);
                FifteenToSixteenMonthsFragment fifteenToSixteenMonthsFragment = FifteenToSixteenMonthsFragment.getInstance(bundle5, "", -1);
                fifteenToSixteenMonthsFragment.setTargetFragment(this, Globals.RequestCodes.Fifteen_to_Sixteen_Months);
                replaceFragment(fifteenToSixteenMonthsFragment, true, false, true, fifteenToSixteenMonthsFragment.getTag());
                break;
            case R.id.dp_booster:

                Bundle bundle6 = new Bundle();
//                if(cardAvailable.equals("true"))
//                {
                bundle6.putSerializable("DOB", DateOfBirth);
//                }
                if (boosterDoseObject != null)
                    if (boosterDoseObject.getBoosterVaccinated() != null && boosterDoseObject.getBoosterVaccinated().length() > 0)
                        bundle6.putParcelable(Globals.Arguments.BOOSTER_DOSE, boosterDoseObject);
                BoosterDoseFragment boosterDoseFragment = BoosterDoseFragment.getInstance(bundle6, "", -1);
                boosterDoseFragment.setTargetFragment(this, Globals.RequestCodes.BOOSTER_DOSE);
                replaceFragment(boosterDoseFragment, true, false, true, boosterDoseFragment.getTag());

                break;
            case R.id.etAge:
                showDatePickerDialog();
                break;
            case R.id.btnSubmit:
                if (isValidForm()) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("You want to save Form!")
                            .setConfirmText("Yes,Go Save!")
                            .setCancelText("No")

                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        String currentDate = sdf.format(Calendar.getInstance().getTime());
//                                    if(tbCardAvailable.isChecked()){
//                                        childObject.setAge(etAge.getText().toString());
//                                    }
//                                    else {
//                                        childObject.setAge(ageinmonthtValue);
//                                    }
                                        childObject.setAge(ageinmonthtValue);
                                        childObject.setFatherName(etFatherName.getText().toString());
                                        childObject.setHouse_no(etHouseNo.getText().toString());
                                        childObject.setChildName(etName.getText().toString());
                                        childObject.setRemarks(etRemarks.getText().toString());
                                        childObject.setCardNo(etCardNo.getText().toString());
                                        childObject.setCardAvailable(cardAvailable);
                                        childObject.setMobileNo(etMobile.getText().toString());
                                        childObject.setCreatedBy(new SharedPref(getActivity()).GetLoggedInUser());
                                        childObject.setCreatedOn(currentDate);


                                        childObject.setLatitude(AppController.getInstance().location.getLatitude());
                                        childObject.setLongitude(AppController.getInstance().location.getLongitude());

                                        if (tbCardAvailable.isChecked() && indicatorsMasterSaveImages.size() == 2) {
                                            childObject.setFront(indicatorsMasterSaveImages.get(0));
                                            childObject.setBack(indicatorsMasterSaveImages.get(1));
                                        }
                                        childObject.setUc(Utils.getUC(getActivity()));
                                        setVaccinatedStatus(childObject);
                                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra(Globals.Arguments.CHILD_INFO, childObject));
                                        getFragmentManager().popBackStack();
                                        sDialog.dismiss();
                                    } catch (Exception e) {

                                    }

                                }
                            })
                            .show();


//                    getActivity().onBackPressed();
                }
                break;
            default:
                super.onClick(view);
                break;
        }
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
                days = getDaysDifference(selectedDate, currentDayDate);
                etAge.setText(dateString.toString());
//                resetSubData();
                enableDisableButtons(days);
                //  ChangeButtonsColor();
//                etAge.setText(yearVal + " y " + MonthsVal + " m " + DaysVal + " d");
//                etAge.setError(null);
            } else
                Dialogs.showDialog(getResources().getString(R.string.dob_les_than_tomorrow), getString(R.string.app_name),
                        getActivity(), true, false, getString(R.string.ok), "", false);

        }
    };


    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;

        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.tbVaccinated:
                if (b) {

//                    llCardAvailable.setVisibility(View.VISIBLE);
                    tilRemarks.setVisibility(View.GONE);
                    tilRemarks.setVisibility(View.GONE);
                    etRemarks.setText("");
                    vaccinated = "true";
                    llButton.setVisibility(View.VISIBLE);
                } else {
//                    llCardAvailable.setVisibility(View.GONE);
                    tilCardNo.setVisibility(View.GONE);
                    tbCardAvailable.setChecked(false);
                    etCardNo.setText("");
                    tilRemarks.setVisibility(View.VISIBLE);
                    vaccinated = "false";
                    llButton.setVisibility(View.GONE);
                }
                break;
            case R.id.tbCardAvailable:
                if (b) {
                    cardAvailable = "true";
                    AttachImages.setVisibility(View.VISIBLE);
                    lazyDatePicker.setVisibility(View.GONE);
                    ageinmonth.setVisibility(View.VISIBLE);
                    etAge.setVisibility(View.VISIBLE);
                    tilCardNo.setVisibility(View.VISIBLE);

                } else {
                    lazyDatePicker.setVisibility(View.GONE);
                    AttachImages.setVisibility(View.GONE);
                    tilCardNo.setVisibility(View.GONE);
                    ageinmonth.setVisibility(View.VISIBLE);
                    etAge.setVisibility(View.GONE);
                    etCardNo.setText("");
                    cardAvailable = "false";
                }
                break;
        }
    }

    private void dateCalculation() {
        Calendar calendarDate = Calendar.getInstance();
        int month = calendarDate.get(Calendar.MONTH);
//        calYear = calendarDate.get(Calendar.YEAR);
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        currentDate = calendarDate.get(Calendar.YEAR) + "-" +
                String.format(Locale.ENGLISH, "%02d", month + 1) + "-" +
                String.format(Locale.ENGLISH, "%02d", day)
        ;
    }

    private void enableDisableButtons(int days) {
        if (days >= 0 && days <= 41) {

            zerotofourtyone();

        } else if (days >= 42 && days <= 69) {

            forty_two_to_sixty_nine();

        } else if (days >= 70 && days <= 97) {
            seventy_to_ninety_seven();
        } else if (days >= 98 && days <= 269) {

            ninety_eight_to_two_six_nine();
        } else if (days >= 270 && days <= 449) {
            two_seventy_to_four_four_nine();
        } else if (days >= 450 && days <= 730) {
            four_fifty_to_seven_thirty();
            if (days >= 540) {
                dp_booster.setEnabled(true);
                dp_booster.setAlpha(1.0f);
            }
        } else {
            btnZero2fortyWeeks.setEnabled(false);
            btnZero2fortyWeeks.setAlpha(0.5f);
            btnSixToTenWeeks.setEnabled(false);
            btnSixToTenWeeks.setAlpha(0.5f);
            btnTenToFourteenWeeks.setEnabled(false);
            btnTenToFourteenWeeks.setAlpha(0.5f);
            btnFourteenToEighteenWeeks.setEnabled(false);
            btnFourteenToEighteenWeeks.setAlpha(0.5f);
            btnNineToTenMonths.setEnabled(false);
            btnNineToTenMonths.setAlpha(0.5f);
            btnFifteenToSixteenMonths.setEnabled(false);
            btnFifteenToSixteenMonths.setAlpha(0.5f);
            dp_booster.setEnabled(false);
            dp_booster.setAlpha(0.5f);
        }
        ChangeButtonsColor();
    }

    public void zerotofourtyone() {
        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(false);
        btnSixToTenWeeks.setAlpha(0.5f);
        btnTenToFourteenWeeks.setEnabled(false);
        btnTenToFourteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnNineToTenMonths.setEnabled(false);
        btnNineToTenMonths.setAlpha(0.5f);
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);

        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    public void forty_two_to_sixty_nine() {


        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(true);
        btnSixToTenWeeks.setAlpha(1.0f);
        btnTenToFourteenWeeks.setEnabled(false);
        btnTenToFourteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnNineToTenMonths.setEnabled(false);
        btnNineToTenMonths.setAlpha(0.5f);
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);
        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    public void seventy_to_ninety_seven() {
        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(true);
        btnSixToTenWeeks.setAlpha(1.0f);
        btnTenToFourteenWeeks.setEnabled(true);
        btnTenToFourteenWeeks.setAlpha(1.0f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnNineToTenMonths.setEnabled(false);
        btnNineToTenMonths.setAlpha(0.5f);
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);
        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    public void ninety_eight_to_two_six_nine() {
        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(true);
        btnSixToTenWeeks.setAlpha(1.0f);
        btnTenToFourteenWeeks.setEnabled(true);
        btnTenToFourteenWeeks.setAlpha(1.0f);
        btnFourteenToEighteenWeeks.setEnabled(true);
        btnFourteenToEighteenWeeks.setAlpha(1.0f);
        btnNineToTenMonths.setEnabled(false);
        btnNineToTenMonths.setAlpha(0.5f);
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);
        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    public void two_seventy_to_four_four_nine() {
        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(true);
        btnSixToTenWeeks.setAlpha(1.0f);
        btnTenToFourteenWeeks.setEnabled(true);
        btnTenToFourteenWeeks.setAlpha(1.0f);
        btnFourteenToEighteenWeeks.setEnabled(true);
        btnFourteenToEighteenWeeks.setAlpha(1.0f);
        btnNineToTenMonths.setEnabled(true);
        btnNineToTenMonths.setAlpha(1.0f);
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);
        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    public void four_fifty_to_seven_thirty() {
        btnZero2fortyWeeks.setEnabled(true);
        btnZero2fortyWeeks.setAlpha(1.0f);
        btnSixToTenWeeks.setEnabled(true);
        btnSixToTenWeeks.setAlpha(1.0f);
        btnTenToFourteenWeeks.setEnabled(true);
        btnTenToFourteenWeeks.setAlpha(1.0f);
        btnFourteenToEighteenWeeks.setEnabled(true);
        btnFourteenToEighteenWeeks.setAlpha(1.0f);
        btnNineToTenMonths.setEnabled(true);
        btnNineToTenMonths.setAlpha(1.0f);
        btnFifteenToSixteenMonths.setEnabled(true);
        btnFifteenToSixteenMonths.setAlpha(1.0f);
        dp_booster.setEnabled(false);
        dp_booster.setAlpha(0.5f);
    }

    private void resetSubData() {
       /* zeroToFortyDaysObject = new ZeroToFortyDaysObject();
        sixToTenWeeksObject = new SixToTenWeeksObject();
        tenToFourteenWeeksObject= new TenToFourteenWeeksObject();
        fifteenToSixteenMonthsObject= new FifteenToSixteenMonthsObject();
        nineToTenMonthsObjects= new NineToTenMonthsObjects();
        fourteenToEighteenWeeksObject=  new FourteenToEighteenWeeksObject();*/
        zeroToFortyDaysObject = null;
        sixToTenWeeksObject = null;
        tenToFourteenWeeksObject = null;
        fifteenToSixteenMonthsObject = null;
        nineToTenMonthsObjects = null;
        fourteenToEighteenWeeksObject = null;
        btnZero2fortyWeeks.setEnabled(false);
        btnZero2fortyWeeks.setAlpha(0.5f);
        btnZero2fortyWeeks.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
        btnSixToTenWeeks.setEnabled(false);
        btnSixToTenWeeks.setAlpha(0.5f);
        btnSixToTenWeeks.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
        btnTenToFourteenWeeks.setEnabled(false);
        btnTenToFourteenWeeks.setAlpha(0.5f);
        btnTenToFourteenWeeks.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
        btnFourteenToEighteenWeeks.setEnabled(false);
        btnFourteenToEighteenWeeks.setAlpha(0.5f);
        btnFourteenToEighteenWeeks.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
        btnNineToTenMonths.setEnabled(false);
        btnNineToTenMonths.setAlpha(0.5f);
        btnNineToTenMonths.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
        btnFifteenToSixteenMonths.setEnabled(false);
        btnFifteenToSixteenMonths.setAlpha(0.5f);
        btnFifteenToSixteenMonths.setBackgroundColor(getActivity().getResources().getColor(R.color.chart_value_2));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void ChangeButtonsColor() {
        if (zeroToFortyDaysObject != null) {
            if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") &&
                    zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true") &&
                    zeroToFortyDaysObject.getHepbVaccinated().equalsIgnoreCase("true")) {
                btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));

            } else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") ||
                    zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true") ||
                    zeroToFortyDaysObject.getHepbVaccinated().equalsIgnoreCase("true")) {
                btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
            } else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("false") &&
                    zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("false") &&
                    zeroToFortyDaysObject.getHepbVaccinated().equalsIgnoreCase("false")) {
                btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
            }
        }
        if (sixToTenWeeksObject != null) {
            if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") &&
                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") &&
                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") &&
                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
            } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") ||
                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") ||
                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") ||
                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
            } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("false") &&
                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("false") &&
                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("false") &&
                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("false")) {
                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
            }
        }
        if (tenToFourteenWeeksObject != null) {
            if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
            } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
            } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("false")) {

                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
            }
        }
        if (fourteenToEighteenWeeksObject != null) {
            if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
            } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
            } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("false")) {

                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
            }
        }
        if (nineToTenMonthsObjects != null) {
            if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("true") && nineToTenMonthsObjects.getIPV2Vaccinated().equalsIgnoreCase("true") && nineToTenMonthsObjects.getTCVVaccinated().equalsIgnoreCase("true")) {
                btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
            } else if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("true") || nineToTenMonthsObjects.getIPV2Vaccinated().equalsIgnoreCase("true") || nineToTenMonthsObjects.getTCVVaccinated().equalsIgnoreCase("true")) {
                btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
            } else if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("false") && nineToTenMonthsObjects.getIPV2Vaccinated().equalsIgnoreCase("false") && nineToTenMonthsObjects.getTCVVaccinated().equalsIgnoreCase("false")) {
                btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
            }
        }
        if (fifteenToSixteenMonthsObject != null) {
            if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated() != null && fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().length() > 0)
                if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("true")) {
                    btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                } else if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("false")) {
                    btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                }
        }
        if (boosterDoseObject != null) {
            if (boosterDoseObject.getBoosterVaccinated() != null) {
                if (boosterDoseObject.getBoosterVaccinated().equalsIgnoreCase("true")) {
                    dp_booster.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                } else if (boosterDoseObject.getBoosterVaccinated().equalsIgnoreCase("true")) {
                    dp_booster.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));

                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Globals.RequestCodes.Zero_To_40_days) {
                if (data != null) {
                    zeroToFortyDaysObject = data.getParcelableExtra(Globals.Arguments.ZERO_TO_FOURTEEN_DAYS);
                    childObject.setZeroToFortyDaysObject(zeroToFortyDaysObject);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run()
//
//                        {
//                            if(zeroToFortyDaysObject != null)
//
//                            {
//                                if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") && (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true")))
//
//                                {
//                                    btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                                }
//
//                                else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") || (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true")))
//
//                                {
//                                    btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
//                                }
//
//                                else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("false") &&  (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("false")))
//
//                                {
//                                    btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                                }
//                            }
//                        }
//                    };
//                    handler.postDelayed(r, 500);

                    if (zeroToFortyDaysObject != null) {
                        if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") && (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true"))) {
                            btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                        } else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("true") || (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("true"))) {
                            btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
                        } else if (zeroToFortyDaysObject.getOpvVaccinated().equalsIgnoreCase("false") && (zeroToFortyDaysObject.getBcgVaccinated() != null && zeroToFortyDaysObject.getBcgVaccinated().equalsIgnoreCase("false"))) {
                            btnZero2fortyWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                        }
                    }


                }
            } else if (requestCode == Globals.RequestCodes.Six_to_Ten_Weeks) {
                if (data != null) {
                    sixToTenWeeksObject = data.getParcelableExtra(Globals.Arguments.SIX_TO_TEN_WEEKS);
                    childObject.setSixToTenWeeksObject(sixToTenWeeksObject);
//                    final Handler handler1 = new Handler();
//                    final Runnable r1 = new Runnable() {
//                        public void run() {
//                            if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") &&
//                                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") &&
//                                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") &&
//                                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
//                                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") ||
//                                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") ||
//                                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") ||
//                                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
//                                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
//                            } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("false") &&
//                                    sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("false") &&
//                                    sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("false") &&
//                                    sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("false")) {
//                                btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//                        }
//                    };
//                    handler1.postDelayed(r1, 500);
                    if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") &&
                            sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") &&
                            sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") &&
                            sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
                        btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                    } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("true") ||
                            sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("true") ||
                            sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("true") ||
                            sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("true")) {
                        btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
                    } else if (sixToTenWeeksObject.getOpVOneVaccinated().equalsIgnoreCase("false") &&
                            sixToTenWeeksObject.getPcvTenOneVaccinated().equalsIgnoreCase("false") &&
                            sixToTenWeeksObject.getPentaOneVaccinated().equalsIgnoreCase("false") &&
                            sixToTenWeeksObject.getRotaVaccineOneVaccinated().equalsIgnoreCase("false")) {
                        btnSixToTenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                    }
                }
            } else if (requestCode == Globals.RequestCodes.Ten_to_Fourteen_Weeks) {
                if (data != null) {
                    tenToFourteenWeeksObject = data.getParcelableExtra(Globals.Arguments.TEN_TO_FOURTEEN_WEEKS);
                    childObject.setTenToFourteenWeeksObject(tenToFourteenWeeksObject);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
//                                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
//                                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
//                                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
//                                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
//                                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
//                                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
//                                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
//                                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
//                            } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
//                                    tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
//                                    tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
//                                    tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("false")) {
//
//                                btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//                        }
//                    };
//                    handler.postDelayed(r, 500);
                    if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
                            tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
                            tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
                            tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
                        btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                    } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
                            tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
                            tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
                            tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("true")) {
                        btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
                    } else if (tenToFourteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
                            tenToFourteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
                            tenToFourteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
                            tenToFourteenWeeksObject.getRotaVaccinated().equalsIgnoreCase("false")) {

                        btnTenToFourteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                    }
                }
            } else if (requestCode == Globals.RequestCodes.Fourteen_to_Eighteen_Weeks) {
                if (data != null) {
                    fourteenToEighteenWeeksObject = data.getParcelableExtra(Globals.Arguments.FOURTEEN_TO_EIGHTEEN_WEEKS);
                    childObject.setFourteenToEighteenWeeksObject(fourteenToEighteenWeeksObject);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
//                                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
//                                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
//                                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
//                                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
//                                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
//                                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
//                                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
//                                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
//                            } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
//                                    fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
//                                    fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
//                                    fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("false")) {
//
//                                btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//
//                        }
//                    };
//                    handler.postDelayed(r, 500);
                    if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") &&
                            fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") &&
                            fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") &&
                            fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
                        btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                    } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("true") ||
                            fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("true") ||
                            fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("true") ||
                            fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("true")) {
                        btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_3)));
                    } else if (fourteenToEighteenWeeksObject.getOpvVaccinated().equalsIgnoreCase("false") &&
                            fourteenToEighteenWeeksObject.getPentaVaccinated().equalsIgnoreCase("false") &&
                            fourteenToEighteenWeeksObject.getPcv10Vaccinated().equalsIgnoreCase("false") &&
                            fourteenToEighteenWeeksObject.getIpvVaccinated().equalsIgnoreCase("false")) {

                        btnFourteenToEighteenWeeks.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                    }
                }
            } else if (requestCode == Globals.RequestCodes.Nine_to_Ten_months) {
                if (data != null) {
                    nineToTenMonthsObjects = data.getParcelableExtra(Globals.Arguments.NINE_TO_TEN_MONTHS);
                    childObject.setNineToTenMonthsObjects(nineToTenMonthsObjects);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("true")) {
//                                btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("false")) {
//                                btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//                        }
//                    };
//                    handler.postDelayed(r, 500);
                    if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("true")) {
                        btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                    } else if (nineToTenMonthsObjects.getMeaslesOneVaccinated().equalsIgnoreCase("false")) {
                        btnNineToTenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                    }
                }
            } else if (requestCode == Globals.RequestCodes.Fifteen_to_Sixteen_Months) {
                if (data != null) {
                    fifteenToSixteenMonthsObject = data.getParcelableExtra(Globals.Arguments.FIFTEEN_TO_SIXTEEN_MONTHS);
                    childObject.setFifteenToSixteenMonthsObject(fifteenToSixteenMonthsObject);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("true")) {
//                                btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("false")) {
//                                btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//                        }
//                    };
//                    handler.postDelayed(r, 500);
                    if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("true")) {
                        btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                    } else if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("false")) {
                        btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                    }
                }
            } else if (requestCode == Globals.RequestCodes.BOOSTER_DOSE) {
                if (data != null) {
                    boosterDoseObject = data.getParcelableExtra(Globals.Arguments.BOOSTER_DOSE);
                    childObject.setBoosterDoseObject(boosterDoseObject);
//                    final Handler handler = new Handler();
//                    final Runnable r = new Runnable() {
//                        public void run() {
//                            if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("true")) {
//                                btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
//                            } else if (fifteenToSixteenMonthsObject.getMeaslesTwoVaccinated().equalsIgnoreCase("false")) {
//                                btnFifteenToSixteenMonths.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
//                            }
//                        }
//                    };
//                    handler.postDelayed(r, 500);
                    if (boosterDoseObject.getBoosterVaccinated() != null) {
                        if (boosterDoseObject.getBoosterVaccinated().equalsIgnoreCase("true")) {
                            dp_booster.setBackground(new ColorDrawable(getResources().getColor(R.color.green_color)));
                        } else if (boosterDoseObject.getBoosterVaccinated().equalsIgnoreCase("false")) {
                            dp_booster.setBackground(new ColorDrawable(getResources().getColor(R.color.chart_value_2)));
                        }
                    }

                }
            } else if (requestCode == REQUEST_CODE) {

                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                imagesCount.setText(mResults.size() + "");

                if (mResults.size() > 0) {
                    indicatorsMasterSaveImages.clear();
                }

                if (mResults.size() >= 1) {
                    new ImageCompressionAsyncTask(MainActivity.main).execute(mResults.get(0),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ses/images");

                }
                if (mResults.size() >= 2) {

                    new ImageCompressionAsyncTask(MainActivity.main).execute(mResults.get(1),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ses/images");

                }

            }
//            setCount();
        }
    }
    public void setCount(){
        String count= mResults.size()+"";
        imagesCount = view.findViewById(R.id.imagesCount);
        imagesCount.setText(count);

    }

    private boolean isValidForm() {
        int valid = 0;
        String lent_ = etMobile.getText().toString();
        String lent__ = lent_.replace(" ", "");
        int len = lent__.length();
        int len2 = etMobile.getText().toString().replace(" ", "").length();
        if (TextUtils.isEmpty(etName.getText().toString())) {
            setError(etName, getString(R.string.plese_enter_name));
            Dialogs.showDialog(getString(R.string.plese_enter_name), getString(R.string.app_name),
                    getActivity(), true, false, getString(R.string.ok), "", false);
            valid = 1;
        }
        if (TextUtils.isEmpty(etFatherName.getText().toString())) {
            setError(etFatherName, getString(R.string.please_enter_father_name));
            Dialogs.showDialog(getString(R.string.please_enter_father_name), getString(R.string.app_name),
                    getActivity(), true, false, getString(R.string.ok), "", false);
            valid = 1;
        }
        if (TextUtils.isEmpty(etHouseNo.getText().toString())) {
            setError(etHouseNo, "Please enter house no");
            Dialogs.showDialog("Please enter house no", getString(R.string.app_name),
                    getActivity(), true, false, getString(R.string.ok), "", false);
            valid = 1;
        }

//        if(tbCardAvailable.isChecked())
//
//        {
//            if (TextUtils.isEmpty(etAge.getText().toString())) {
//                setError(etAge, getString(R.string.please_enter_age));
//                Dialogs.showDialog(getString(R.string.please_enter_age), getString(R.string.app_name),
//                        getActivity(), true, false, getString(R.string.ok), "", false);
//                valid = 1;
//            }
//        }
//
//        else

        if (ageinmonthtValue == null) {
            Toast.makeText(getContext(), "please select age in month", Toast.LENGTH_SHORT).show();
            valid = 1;
        }

        if (etMobile.getText().toString().replace(" ", "").length() != 12) {
            setError(etMobile, getString(R.string.please_enter_Phone_no));
            Dialogs.showDialog(getString(R.string.please_enter_Phone_no), getString(R.string.app_name),
                    getActivity(), true, false, getString(R.string.ok), "", false);
            valid = 1;
        }
        if (tbCardAvailable.isChecked()) {
            if (TextUtils.isEmpty(etCardNo.getText().toString())) {
                setError(etCardNo, getString(R.string.card_no));
                Dialogs.showDialog(getString(R.string.card_no), getString(R.string.app_name),
                        getActivity(), true, false, getString(R.string.ok), "", false);
                valid = 1;
            }
        }

        if (tbCardAvailable.isChecked()) {
            if (indicatorsMasterSaveImages.size() < 1) {
                Dialogs.showDialog(getString(R.string.image), getString(R.string.app_name), getActivity(), true, false, getString(R.string.ok), "", false);
                valid = 1;
            }
        }

        if (tbVaccinated.isChecked()) {

            if (zeroToFortyDaysObject == null && sixToTenWeeksObject == null && tenToFourteenWeeksObject == null && fourteenToEighteenWeeksObject == null && nineToTenMonthsObjects == null && fifteenToSixteenMonthsObject == null && boosterDoseObject == null) {
                Dialogs.showDialog(getString(R.string.val), getString(R.string.app_name), getActivity(), true, false, getString(R.string.ok), "", false);
                valid = 1;
            }
        }

        return valid == 0;
    }

    private void multiImagePicker() {
        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 10000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        startActivityForResult(intent, REQUEST_CODE);
    }

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public ImageCompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);
            if (imageFile.getPath() != null) {
                String ImageBase = getFileToByte(imageFile.getPath());
                indicatorsMasterSaveImages.add(ImageBase);
            }

        }
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }
}
