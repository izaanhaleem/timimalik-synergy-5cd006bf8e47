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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.Location;
import com.hisdu.ses.Database.ZeroDoseChildModel;
import com.hisdu.ses.Database.ZeroDoseDetail;
import com.hisdu.ses.MainActivity;
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

public class ZeroDoseAddChildFragment extends Fragment {

    String ChildNameValue = null, FatherNameValue = null, PhoneNoValue = null, AgeValue = null, AddressValue = null, VaccinatedValue = null, cardNoValue = null;
    EditText ChildName, FatherName, PhoneNo, Address, cardNo,house_no;
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
    CustomSearchableSpinner province, division, district, tehsil, uc;
    String  provinceValue = null, districtValue = null, divisionValue = null, tehsilValue = null,  ucValue = null,locationCode;
    LinearLayout provinceLayout, districtLayout, tehsilLayout, UcLayout,divisionLayout;
    List<Location> DivisionList;
    List<Location> DistrictList;
    List<Location> TehsilList;
    List<Province> provinceList;
    List<Location> UcList;

    Spinner ageinmonth,hr_mp_child;
    String ageinmonthtValue,hrmp;

    String[] mtextArray = {"عمر منتخب کریں*", "1 دن", "2 دن", "3 دن", "4 دن", "5 دن", "6 دن", "7 دن", "8 دن", "9 دن",
            "10 دن", "11 دن", "12 دن", "13 دن", "14 دن", "15 دن", "16 دن", "17 دن", "18 دن", "19 دن", "20 دن", "21 دن",
            "22 دن", "23 دن", "24 دن", "25 دن", "26 دن", "27 دن", "28 دن", "29 دن", "30 دن", "31 دن", "32 دن", "33 دن",
            "34 دن", "35 دن", "36 دن", "37 دن", "38 دن", "39 دن", "40 دن", "1.5 مہینہ", "2 مہینہ", "3 مہینہ", "4 مہینہ",
            "5 مہینہ", "6 مہینہ", "7 مہینہ", "8 مہینہ", "9 مہینہ", "10 مہینہ", "11 مہینہ", "12 مہینہ", "13 مہینہ", "14 مہینہ",
            "15 مہینہ", "16 مہینہ", "17 مہینہ", "18 مہینہ", "19 مہینہ", "20 مہینہ", "21 مہینہ", "22 مہینہ", "23 مہینہ"};
    String[] mtextArrayEnglish = {"Select Age*", "1 Day", "2 Day", "3 Day", "4 Day", "5 Day", "6 Day", "7 Day", "8 Day",
            "9 Day", "10 Day", "11 Day", "12 Day", "13 Day", "14 Day", "15 Day", "16 Day", "17 Day", "18 Day", "19 Day",
            "20 Day", "21 Day", "22 Day", "23 Day","24 Day", "25 Day", "26 Day", "27 Day", "28 Day", "29 Day", "30 Day",
            "31 Day", "32 Day", "33 Day", "34 Day", "35 Day", "36 Day", "37 Day", "38 Day", "39 Day", "40 Day", "1.5 Month",
            "2 Month", "3 Month", "4 Month", "5 Month", "6 Month", "7 Month", "8 Month", "9 Month", "10 Month", "11 Month",
            "12 Month", "13 Month", "14 Month", "15 Month", "16 Month", "17 Month", "18 Month", "19 Month", "20 Month",
            "21 Month", "22 Month", "23 Month"};
    String[] yes_noArray = {"بچہ کس آبادی سے تعلق رکھتا ہے؟", "HRMP", "Non-HRMP"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zerodose_add_child_fragment, container, false);
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
        house_no= view.findViewById(R.id.house_no);
        lazyDatePicker.setMinDate(minDate);

        division = view.findViewById(R.id.division);

        province = view.findViewById(R.id.provinces);
        district = view.findViewById(R.id.sid);
        tehsil = view.findViewById(R.id.tehsil);
        uc = view.findViewById(R.id.uc);
        districtLayout = view.findViewById(R.id.SidLayout);

        divisionLayout = view.findViewById(R.id.divisionLayout);
        provinceLayout = view.findViewById(R.id.provinceLayout);
        UcLayout = view.findViewById(R.id.UcLayout);
        tehsilLayout = view.findViewById(R.id.tehsilLayout);
        ageinmonth = view.findViewById(R.id.ageinmonth);
        hr_mp_child= view.findViewById(R.id.hr_mp_child);

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
//            VaccinatedValue = VaccinatedYes.getText().toString();
//            cardlayout.setVisibility(View.VISIBLE);
            provinceLayout.setVisibility(View.GONE);
            divisionLayout.setVisibility(View.GONE);
            districtLayout.setVisibility(View.GONE);
            tehsilLayout.setVisibility(View.GONE);
            UcLayout.setVisibility(View.GONE);

//            getUcs();
            getTehsil();

        });
        HrMpChild();
        VaccinatedNo.setOnClickListener(v -> {
//            VaccinatedValue = VaccinatedNo.getText().toString();
//            cardlayout.setVisibility(View.GONE);

//            divisionLayout.setVisibility(View.VISIBLE);
            divisionLayout.setVisibility(View.GONE);
            districtLayout.setVisibility(View.GONE);
            tehsilLayout.setVisibility(View.GONE);
            UcLayout.setVisibility(View.GONE);
            getProvinces();
//            getDivision();
        });


        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (province.getSelectedItemPosition() != 0) {
                    provinceValue = provinceList.get(i - 1).getProvinceID().toString();
                    locationCode= provinceList.get(i - 1).getProvinceID().toString();

                    getDivisionByProvince(provinceList.get(i - 1).getProvinceID());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (division.getSelectedItemPosition() != 0) {
                    divisionValue = DivisionList.get(i - 1).getServer_id();
                    locationCode= DivisionList.get(i - 1).getServer_id();
                    districtLayout.setVisibility(View.VISIBLE);
                    getDistrict();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (district.getSelectedItemPosition() != 0) {
                    districtValue = DistrictList.get(i - 1).getServer_id();
                    tehsilLayout.setVisibility(View.GONE);
                    locationCode= DistrictList.get(i - 1).getServer_id();
                    getTehsil();
                } else {
                    tehsilLayout.setVisibility(View.GONE);
                    tehsil.setSelection(0);
                    tehsilValue = null;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (tehsil.getSelectedItemPosition() != 0) {
                    tehsilValue = TehsilList.get(i - 1).getServer_id();
                    locationCode= TehsilList.get(i - 1).getServer_id();
                    getUcs();
                } else {
                    UcLayout.setVisibility(View.GONE);
                    uc.setSelection(0);
                    tehsilValue=null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        uc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (uc.getSelectedItemPosition() != 0) {
                    ucValue = UcList.get(i - 1).getServer_id();
                    locationCode=UcList.get(i - 1).getServer_id();
                }else {
                    ucValue=null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mtextArray);
        ageinmonth.setAdapter(mAdapter);
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

                    ageinmonthtValue =mtextArrayEnglish[i];
                }else {
                    ageinmonthtValue=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }

    void submit()

    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        ZeroDoseChildModel zeroDoseChildModel= new ZeroDoseChildModel();
        if(!house_no.getText().toString().isEmpty()){
            zeroDoseChildModel.setHouseNo(house_no.getText().toString());
        }
        zeroDoseChildModel.setHRMP(hrmp);
        zeroDoseChildModel.setAge(ageinmonthtValue);
        zeroDoseChildModel.setAgeType(ageinmonthtValue);
        zeroDoseChildModel.setChildName(ChildNameValue);
        zeroDoseChildModel.setFatherName(FatherNameValue);
        zeroDoseChildModel.setAddress(AddressValue);
        zeroDoseChildModel.setSync("0");
        zeroDoseChildModel.setCreatedBy(Integer.valueOf(new SharedPref(getActivity()).GetserverID()));
        zeroDoseChildModel.setCreatedOn(currentDate);
        zeroDoseChildModel.setPhoneNo(PhoneNoValue);
        zeroDoseChildModel.setLocationCode(locationCode);

        if(AppController.location!=null){
            zeroDoseChildModel.setLatitude(AppController.location.getLatitude());
            zeroDoseChildModel.setLongitude(AppController.location.getLongitude());
        }

        if(VaccinatedYes.isSelected()){
            zeroDoseChildModel.setWithinDistrict("Yes");
            zeroDoseChildModel.setUC(ucValue);
        }
        if(VaccinatedNo.isSelected()){
            zeroDoseChildModel.setWithinDistrict("No");


            zeroDoseChildModel.setDistrict(districtValue);
            zeroDoseChildModel.setDivision(divisionValue);
            zeroDoseChildModel.setTehsil(tehsilValue);
            zeroDoseChildModel.setUC(ucValue);
        }
        AppController.getInstance().zeroDoseChild = zeroDoseChildModel;
        ZeroDoseFormFragment.zeroDoseMasterFragment.UpdateLogList();
        MainActivity.main.onBackPressed();
    }


    public boolean validate() {
        boolean valid = true;

        if(hrmp==null){
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("بچہ کس آبادی سے تعلق رکھتا ہے؟")
                    .show();
            valid = false;
            return valid;
        }

        if (ChildNameValue == null) {
//            Toast.makeText(getContext(), "Please enter child name!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("براہ کرم بچے کا نام درج کریں!")
                    .show();
            valid = false;
            return valid;
        }

        if (FatherNameValue == null) {
//            Toast.makeText(getContext(), "Please enter father name!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("براہ کرم والد کا نام درج کریں!")
                    .show();
            valid = false;
            return valid;
        }
        if(house_no.getText().toString().isEmpty()){
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("براہ کرم  پولیو مکان نمبر منتخب کریں!")
                    .show();
            valid = false;
            return valid;

        }

        if (PhoneNoValue == null) {
//            Toast.makeText(getContext(), "Please enter phone no!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText( "براہ کرم فون نمبر درج کریں۔!")
                    .show();
            valid = false;
            return valid;
        }

        if (ageinmonthtValue == null) {
//            Toast.makeText(getContext(), "Please enter age!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("براہ کرم عمر درج کریں۔!")
                    .show();
            valid = false;
            return valid;
        }

        if (AddressValue == null) {
//            Toast.makeText(getContext(), "Please enter address!", Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("براہ کرم پتہ درج کریں")
                    .show();
            valid = false;
            return valid;
        }

       if(VaccinatedYes.isChecked()){
           if(ucValue ==null){
               new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("براہ کرم یونین کنسل منتخب کریں!")
                       .show();
               valid = false;
               return valid;
           }
       }

       if(VaccinatedNo.isChecked()){
           if(provinceValue ==null){
               new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("براہ کرم صوبہ منتخب کریں!")
                       .show();
               valid = false;
               return valid;
           }else if(provinceValue.equals("1")){
               if(divisionValue ==null){
                   new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("براہ کرم ڈویژن منتخب کریں!")
                           .show();
                   valid = false;
                   return valid;
               }
               if(districtValue ==null){
                   new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("براہ کرم ضلع منتخب کریں!")
                           .show();
                   valid = false;
                   return valid;
               }
           }
           if(DistrictList!=null){

//               if(tehsilValue ==null){
//                   new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
//                           .setTitleText("براہ کرم تحصیل منتخب کریں!")
//                           .show();
//                   valid = false;
//                   return valid;
//               }
//               if(ucValue ==null){
//                   new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
//                           .setTitleText("براہ کرم یونین کنسل منتخب کریں!")
//                           .show();
//                   valid = false;
//                   return valid;
//               }
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


    void HrMpChild(){
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, yes_noArray);
        hr_mp_child.setAdapter(mAdapter);
        hr_mp_child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (hr_mp_child.getSelectedItemPosition() != 0) {

                    hrmp=hr_mp_child.getSelectedItem().toString();
//                    if(i==1){
//                        hrmp="Yes";
//                    }else  if (i==2){
//                        hrmp="No";
//                    }else {
//                        hrmp=null;
//                    }
                }else {
                    hrmp=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
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
    void getProvinces() {


        provinceList = Province.getProvinces();

        if (provinceList.size() != 0) {

            String[] provinceArray = new String[provinceList.size() + 1];
            provinceArray[0] = "Select Province";

            for (int i = 0; i < provinceList.size(); i++) {
                provinceArray[i + 1] = provinceList.get(i).getProvinceName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, provinceArray);
            province.setAdapter(districtsAdapter);
            provinceLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Provinces", Toast.LENGTH_SHORT).show();
        }

    }

    void getDivisionByProvince(int id) {

        DivisionList = Location.getDivisionByProvince(id);

        if (DivisionList.size() != 0) {

            String[] divisionArray = new String[DivisionList.size() + 1];
            divisionArray[0] = "Select Division";

            for (int i = 0; i < DivisionList.size(); i++) {
                divisionArray[i + 1] = DivisionList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionArray);
            division.setAdapter(districtsAdapter);
            divisionLayout.setVisibility(View.VISIBLE);

        } else {
            divisionLayout.setVisibility(View.GONE);
            districtLayout.setVisibility(View.GONE);
            tehsilLayout.setVisibility(View.GONE);
            UcLayout.setVisibility(View.GONE);

            Toast.makeText(getActivity(), "Error Loading Divisions", Toast.LENGTH_SHORT).show();
        }

    }

    void  getDivision() {

        String code = new SharedPref(MainActivity.main).GetLocationID();

        DivisionList = Location.getAllDivision();

        if (DivisionList.size() != 0) {

            String[] divisionArray = new String[DivisionList.size() + 1];
            divisionArray[0] = "ڈویژن منتخب کریں";

            for (int i = 0; i < DivisionList.size(); i++) {
                divisionArray[i + 1] = DivisionList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionArray);
            division.setAdapter(districtsAdapter);
            divisionLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "Error Loading Divisions", Toast.LENGTH_SHORT).show();
        }

    }

    void getDistrict() {

        if(divisionValue != null)

        {
            DistrictList = Location.getDistrict(divisionValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            DistrictList = Location.getDistrict(code);
        }


        if (DistrictList.size() != 0) {

            String[] districtsArray = new String[DistrictList.size() + 1];
            districtsArray[0] = "ضلع منتخب کریں";

            for (int i = 0; i < DistrictList.size(); i++) {
                districtsArray[i + 1] = DistrictList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtsArray);
            district.setAdapter(districtsAdapter);
            districtLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "No Districts found", Toast.LENGTH_SHORT).show();
        }

    }

    void getTehsil() {

        if(districtValue != null)

        {
            TehsilList = Location.getTehsil(districtValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            TehsilList = Location.getTehsil(code.substring(0,6));
        }

        if (TehsilList.size() != 0) {
            String[] tehsilArray = new String[TehsilList.size() + 1];
            tehsilArray[0] = "تحصیل منتخب کریں";

            for (int i = 0; i < TehsilList.size(); i++) {
                tehsilArray[i + 1] = TehsilList.get(i).getName();
            }
            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            tehsil.setAdapter(districtsAdapter);
            tehsilLayout.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getActivity(), "No tehsil found against above selection", Toast.LENGTH_SHORT).show();
        }

    }

    void getUcs() {

        if(tehsilValue != null)

        {
            UcList = Location.getUC(tehsilValue);
        }

        else

        {
            String code = new SharedPref(MainActivity.main).GetLocationID();
            if(code.length()>6){
                UcList = Location.getUC(code.substring(0,6));
            }

        }

        if (UcList.size() != 0) {
            String[] tehsilArray = new String[UcList.size() + 1];
            tehsilArray[0] = " ای پی آئی یونین کونسل منتخب کریں";

            for (int i = 0; i < UcList.size(); i++) {

                tehsilArray[i + 1] = UcList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            uc.setAdapter(districtsAdapter);
            UcLayout.setVisibility(View.VISIBLE);


        } else {
            UcLayout.setVisibility(View.GONE);
            uc.setSelection(0);
            Toast.makeText(getActivity(), "No UC found against above selection", Toast.LENGTH_SHORT).show();
        }
    }

    void getUcsById(String code) {

        if(tehsilValue != null)

        {
            UcList = Location.getUC(tehsilValue);
        }

        else

        {
//            String code = new SharedPref(MainActivity.main).GetLocationID();
            UcList = Location.getUC(code);
        }

        if (UcList.size() != 0) {
            String[] tehsilArray = new String[UcList.size() + 1];
            tehsilArray[0] = "ای پی آئی یونین کونسل منتخب کریں";

            for (int i = 0; i < UcList.size(); i++) {

                tehsilArray[i + 1] = UcList.get(i).getName();
            }

            ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tehsilArray);
            uc.setAdapter(districtsAdapter);
            UcLayout.setVisibility(View.VISIBLE);


        } else {
            UcLayout.setVisibility(View.GONE);
            uc.setSelection(0);
            Toast.makeText(getActivity(), "No UC found against above selection", Toast.LENGTH_SHORT).show();
        }
    }
}
