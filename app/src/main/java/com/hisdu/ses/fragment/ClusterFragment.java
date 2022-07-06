package com.hisdu.ses.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.ses.AppController;
import com.hisdu.ses.Database.ClusterForm;
import com.hisdu.ses.Database.SaveChecklist;
import com.hisdu.ses.Database.SaveInspections;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.ZeroAdapter;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClusterFragment extends Fragment {

    String[] mArray = new String[0];

    Button familyPopup,submitButton,close,back;

    private ArrayList<ClusterForm> familydataSet;

    private List<ClusterForm> FamilylistItems;

    private RecyclerView family_ListView;

    private RecyclerView.Adapter Radapter;

    FragmentManager fragmentManager;

    EditText CardNumber,ChildName,FatherName,contact,Remarks;

    String   CardNumberValue = null, ChildNameValue = null, ageValue = null, FatherNameValue = null, contactValue = null, Username;

    public static ClusterFragment CLF;

    String   bcgValue = null,hepbbdValue = null,opvoValue = null,pentaIValue = null,pcv10IValue = null,opvIValue = null,rotaIValue = null,pentaIIValue = null,
             pcv10IIValue = null,opvIIValue = null,rotaIIValue = null,pentaIIIValue = null,pcv10IIIValue = null,opvIIIValue = null,ipvValue = null,measlesIValue = null,
             measlesIIValue = null,VaccineValue = null, createdBy = null, dobValue = null;

    Button   addFamily;

    RadioGroup typegroup;
    RadioButton unvaccinated,PartiallyVaccinated,UpToDateVaccinated;

    CheckBox   bcg,hepbbd,opvo,pentaI,pcv10I,opvI,rotaI,pentaII,pcv10II,opvII,rotaII,pentaIII,pcv10III,opvIII,ipv,measlesI,measlesII;

    TextView     dob;
    double       childAge;
    ImageButton  BirthDate;

    String BcgDateValue = null,HepBBDDateValue= null,OPV0DateValue= null,PentaIDateValue= null,PCV10IDateValue= null, OPVIDateValue= null,RotaIDateValue= null,PentaIIDateValue= null,PCV10IIDateValue= null,
           OPVIIDateValue= null, RotaIIDateValue= null,PentaIIIDateValue= null,PCV10IIIDateValue= null,OPVIIIDateValue= null, IPVDateValue= null,MeaslesIDateValue= null,MeaslesIIDateValue= null,RemarksValue = null;

    ImageButton BcgDate = null,HepBBDDate= null,OPV0Date= null,PentaIDate= null,PCV10IDate= null, OPVIDate= null,RotaIDate= null,PentaIIDate= null,PCV10IIDate= null,
            OPVIIDate= null, RotaIIDate= null,PentaIIIDate= null,PCV10IIIDate= null,OPVIIIDate= null, IPVDate= null,MeaslesIDate= null,MeaslesIIDate= null;

    String Dating = null;

    Integer  Day_int = null, Month_int = null, Year_int = null, Cyear = null;
    String   day = null, month = null, year = null;

    Calendar now;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cluster, container, false);

        familyPopup     = view.findViewById(R.id.familyPopup);
        family_ListView = view.findViewById(R.id.family_ListView);
        submitButton    = view.findViewById(R.id.submitButton);
        back            = view.findViewById(R.id.back);

        CLF = this;

        now     = Calendar.getInstance();
        Cyear   = now.get(Calendar.YEAR);

        back.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)

            {
                if(AppController.checklistType.equals("Cluster Form"))

                {
                    LinkFragment.LF.changeTab(0);
                }

                if(AppController.checklistType.equals("Outreach Session") || Username.toLowerCase().startsWith("asv"))

                {
                    LinkFragment.LF.changeTab(1);
                }

            }
        });

        family_ListView.setHasFixedSize(true);
        family_ListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fragmentManager = getFragmentManager();

        createdBy  = new SharedPref(getActivity()).GetserverID();
        Username   = new SharedPref(getActivity()).GetLoggedInUser();

        familydataSet   = new ArrayList<>();
        FamilylistItems = new ArrayList<>();

        familyPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.family_popup, null);

                close          = dialogView.findViewById(R.id.close);
                addFamily      = dialogView.findViewById(R.id.addFamily);
                bcg            = dialogView.findViewById(R.id.bcg);
                hepbbd         = dialogView.findViewById(R.id.hepbbd);
                opvo           = dialogView.findViewById(R.id.opvo);
                pentaI         = dialogView.findViewById(R.id.pentaI);
                pcv10I         = dialogView.findViewById(R.id.pcv10I);
                opvI           = dialogView.findViewById(R.id.opvI);
                rotaI          = dialogView.findViewById(R.id.rotaI);
                pentaII        = dialogView.findViewById(R.id.pentaII);
                pcv10II        = dialogView.findViewById(R.id.pcv10II);
                opvII          = dialogView.findViewById(R.id.opvII);
                rotaII         = dialogView.findViewById(R.id.rotaII);
                pentaIII       = dialogView.findViewById(R.id.pentaIII);
                pcv10III       = dialogView.findViewById(R.id.pcv10III);
                opvIII         = dialogView.findViewById(R.id.opvIII);
                ipv            = dialogView.findViewById(R.id.ipv);
                measlesI       = dialogView.findViewById(R.id.measlesI);
                measlesII      = dialogView.findViewById(R.id.measlesII);
                unvaccinated   = dialogView.findViewById(R.id.unvaccinated);
                PartiallyVaccinated  = dialogView.findViewById(R.id.PartiallyVaccinated);
                UpToDateVaccinated   = dialogView.findViewById(R.id.UpToDateVaccinated);
                CardNumber     = dialogView.findViewById(R.id.CardNumber);
                ChildName      = dialogView.findViewById(R.id.ChildName);
                FatherName     = dialogView.findViewById(R.id.FatherName);
                contact        = dialogView.findViewById(R.id.contact);
                typegroup      = dialogView.findViewById(R.id.typegroup);
                dob            = dialogView.findViewById(R.id.dob);
                BirthDate      = dialogView.findViewById(R.id.BirthDate);
                BcgDate        = dialogView.findViewById(R.id.BcgDate);
                HepBBDDate     = dialogView.findViewById(R.id.HepBBDDate);
                OPV0Date       = dialogView.findViewById(R.id.OPV0Date);
                PentaIDate     = dialogView.findViewById(R.id.PentaIDate);
                PCV10IDate     = dialogView.findViewById(R.id.PCV10IDate);
                OPVIDate       = dialogView.findViewById(R.id.OPVIDate);
                RotaIDate      = dialogView.findViewById(R.id.RotaIDate);
                PentaIIDate    = dialogView.findViewById(R.id.PentaIIDate);
                PCV10IIDate    = dialogView.findViewById(R.id.PCV10IIDate);
                OPVIIDate      = dialogView.findViewById(R.id.OPVIIDate);
                RotaIIDate     = dialogView.findViewById(R.id.RotaIIDate);
                PentaIIIDate   = dialogView.findViewById(R.id.PentaIIIDate);
                PCV10IIIDate   = dialogView.findViewById(R.id.PCV10IIIDate);
                OPVIIIDate     = dialogView.findViewById(R.id.OPVIIIDate);
                IPVDate        = dialogView.findViewById(R.id.IPVDate);
                MeaslesIDate   = dialogView.findViewById(R.id.MeaslesIDate);
                MeaslesIIDate  = dialogView.findViewById(R.id.MeaslesIIDate);
                Remarks        = dialogView.findViewById(R.id.Remarks);

                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(false);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                BirthDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dlg = new Dialog(MainActivity.main);
                        LinearLayout DateOfBirthViewParent = new LinearLayout(MainActivity.main);
                        DateOfBirthViewParent.setOrientation(LinearLayout.VERTICAL);

                        final DatePicker datePicker = new DatePicker(MainActivity.main);
                        datePicker.setMaxDate(new Date().getTime());
                        Button setDateButton = new Button(MainActivity.main);
                        setDateButton.setText("Set");
                        setDateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try

                                {
                                    int month = datePicker.getMonth()+1;
                                    int year  = datePicker.getYear();
                                    int day   = datePicker.getDayOfMonth();

                                    dobValue = year + "-" + month + "-" + day;

                                    childAge  = getAge();

                                    dob.setText("DOB : " +  day + "-" + month + "-" + year + " ( Age : " + childAge + " )");

                                    dlg.dismiss();

                                } catch (Exception ex) {
                                    Toast.makeText(MainActivity.main, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        DateOfBirthViewParent.addView(datePicker);
                        DateOfBirthViewParent.addView(setDateButton);
                        dlg.setContentView(DateOfBirthViewParent);
                        dlg.show();
                    }
                });


                bcg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                           if(isChecked) { bcgValue = "Yes"; BcgDate.setVisibility(View.VISIBLE); } else { bcgValue = null; BcgDate.setVisibility(View.GONE); BcgDateValue = null; }
                    }
                });

                BcgDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("BcgDate");

                    }
                });

                hepbbd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { hepbbdValue = "Yes"; HepBBDDate.setVisibility(View.VISIBLE); } else { hepbbdValue = null; HepBBDDate.setVisibility(View.GONE); HepBBDDateValue = null; }
                    }
                });

                HepBBDDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("HepBBDDate");

                    }
                });

                opvo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { opvoValue = "Yes"; OPV0Date.setVisibility(View.VISIBLE); } else { opvoValue = null; OPV0Date.setVisibility(View.GONE); OPV0DateValue = null; }
                    }
                });

                OPV0Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("OPV0Date");

                    }
                });

                pentaI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pentaIValue = "Yes"; PentaIDate.setVisibility(View.VISIBLE); } else { pentaIValue = null; PentaIDate.setVisibility(View.GONE); PentaIDateValue = null; }
                    }
                });

                PentaIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PentaIDate");

                    }
                });

                pcv10I.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pcv10IValue = "Yes"; PCV10IDate.setVisibility(View.VISIBLE); } else { pcv10IValue = null; PCV10IDate.setVisibility(View.GONE); PCV10IDateValue = null; }
                    }
                });

                PCV10IDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PCV10IDate");

                    }
                });

                opvI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { opvIValue = "Yes"; OPVIDate.setVisibility(View.VISIBLE); } else { opvIValue = null; OPVIDate.setVisibility(View.GONE); OPVIDateValue = null; }
                    }
                });

                OPVIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("OPVIDate");

                    }
                });

                rotaI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { rotaIValue = "Yes"; RotaIDate.setVisibility(View.VISIBLE); } else { rotaIValue = null; RotaIDate.setVisibility(View.GONE); RotaIDateValue = null; }
                    }
                });

                RotaIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("RotaIDate");

                    }
                });

                pentaII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pentaIIValue = "Yes"; PentaIIDate.setVisibility(View.VISIBLE); } else { pentaIIValue = null; PentaIIDate.setVisibility(View.GONE); PentaIIDateValue = null; }
                    }
                });

                PentaIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PentaIIDate");

                    }
                });

                pcv10II.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pcv10IIValue = "Yes"; PCV10IIDate.setVisibility(View.VISIBLE); } else { pcv10IIValue = null; PCV10IIDate.setVisibility(View.GONE); PCV10IIDateValue = null; }
                    }
                });

                PCV10IIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PCV10IIDate");

                    }
                });

                opvII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { opvIIValue = "Yes"; OPVIIDate.setVisibility(View.VISIBLE); } else { opvIIValue = null; OPVIIDate.setVisibility(View.GONE); OPVIIDateValue = null; }
                    }
                });

                OPVIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("OPVIIDate");

                    }
                });

                rotaII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { rotaIIValue = "Yes"; RotaIIDate.setVisibility(View.VISIBLE); } else { rotaIIValue = null; RotaIIDate.setVisibility(View.GONE); RotaIIDateValue = null; }
                    }
                });

                RotaIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("RotaIIDate");

                    }
                });

                pentaIII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pentaIIIValue = "Yes"; PentaIIIDate.setVisibility(View.VISIBLE); } else { pentaIIIValue = null; PentaIIIDate.setVisibility(View.GONE); PentaIIIDateValue = null; }
                    }
                });

                PentaIIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PentaIIIDate");

                    }
                });

                pcv10III.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { pcv10IIIValue = "Yes"; PCV10IIIDate.setVisibility(View.VISIBLE); } else { pcv10IIIValue = null; PCV10IIIDate.setVisibility(View.GONE); PCV10IIIDateValue = null; }
                    }
                });

                PCV10IIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("PCV10IIIDate");

                    }
                });

                opvIII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { opvIIIValue = "Yes"; OPVIIIDate.setVisibility(View.VISIBLE); } else { opvIIIValue = null; OPVIIIDate.setVisibility(View.GONE); OPVIIIDateValue = null; }
                    }
                });

                OPVIIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("OPVIIIDate");

                    }
                });

                ipv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { ipvValue = "Yes"; IPVDate.setVisibility(View.VISIBLE); } else { ipvValue = null; IPVDate.setVisibility(View.GONE); IPVDateValue = null; }
                    }
                });

                IPVDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("IPVDate");

                    }
                });

                measlesI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { measlesIValue = "Yes"; MeaslesIDate.setVisibility(View.VISIBLE); } else { measlesIValue = null; MeaslesIDate.setVisibility(View.GONE); MeaslesIDateValue = null; }
                    }
                });

                MeaslesIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("MeaslesIDate");

                    }
                });

                measlesII.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)

                    {
                        if(isChecked) { measlesIIValue = "Yes"; MeaslesIIDate.setVisibility(View.VISIBLE); } else { measlesIIValue = null; MeaslesIIDate.setVisibility(View.GONE); MeaslesIIDateValue = null; }
                    }
                });

                MeaslesIIDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVaccinationDate("MeaslesIIDate");

                    }
                });

                unvaccinated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        VaccineValue     = unvaccinated.getText().toString();
                    }
                });

                PartiallyVaccinated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        VaccineValue     = PartiallyVaccinated.getText().toString();
                    }
                });

                UpToDateVaccinated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        VaccineValue     = UpToDateVaccinated.getText().toString();
                    }
                });

                CardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener()

                {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus && CardNumber.isEnabled())

                        {
                            if(CardNumber.length() != 0) { CardNumberValue     = CardNumber.getText().toString(); }

                            else { CardNumberValue = null; }
                        }

                    }
                });

                ChildName.setOnFocusChangeListener(new View.OnFocusChangeListener()

                {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus && ChildName.isEnabled())

                        {
                            if(ChildName.length() != 0) { ChildNameValue     = ChildName.getText().toString(); }

                            else { ChildNameValue = null; }
                        }

                    }
                });


                Remarks.setOnFocusChangeListener(new View.OnFocusChangeListener()

                {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus && Remarks.isEnabled())

                        {
                            if(Remarks.length() != 0) { RemarksValue     = Remarks.getText().toString(); }

                            else { RemarksValue = null; }
                        }

                    }
                });

                FatherName.setOnFocusChangeListener(new View.OnFocusChangeListener()

                {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus && FatherName.isEnabled())

                        {
                            if(FatherName.length() != 0) { FatherNameValue     = FatherName.getText().toString(); }

                            else { FatherNameValue = null; }
                        }

                    }
                });

                contact.setOnFocusChangeListener(new View.OnFocusChangeListener()

                {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus && contact.isEnabled())

                        {
                            if(contact.length() != 0) { contactValue     = contact.getText().toString(); }

                            else { contactValue = null; }
                        }

                    }
                });

                addFamily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)

                    {
                        clearFocus();

                        if (validateFamilyData())

                        {
                            ClusterForm fm = new ClusterForm(AppController.MasterID,CardNumberValue,ChildNameValue,FatherNameValue,contactValue,dobValue,ageValue,bcgValue,hepbbdValue,
                                    opvoValue,pentaIValue,pcv10IValue,opvIValue,rotaIValue,pentaIIValue,pcv10IIValue,opvIIValue,rotaIIValue,pentaIIIValue,pcv10IIIValue,opvIIIValue,ipvValue,
                                    measlesIValue,measlesIIValue,createdBy,AppController.getInstance().date,VaccineValue,BcgDateValue,HepBBDDateValue,OPV0DateValue,PentaIDateValue,PCV10IDateValue,
                            OPVIDateValue,RotaIDateValue,PentaIIDateValue,PCV10IIDateValue,OPVIIDateValue,RotaIIDateValue,PentaIIIDateValue,PCV10IIIDateValue,OPVIIIDateValue,
                            IPVDateValue,MeaslesIDateValue,MeaslesIIDateValue,RemarksValue);
                            FamilylistItems.add(fm);
                            FamilyClearData();
                            FamilyUpdateLogList();
                            alertDialog.dismiss();

                        }

                        else { Toast.makeText(getActivity(),"Validation Failed", Toast.LENGTH_LONG).show();}
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FamilyClearData();
                        alertDialog.dismiss();

                    }
                });

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                clearFocus();
                submitButton.setEnabled(false);

                if(validate())

                {
                    submit();
                }

                else { Toast.makeText(getActivity(),"Validation Failed.", Toast.LENGTH_LONG).show(); submitButton.setEnabled(true);}
            }
        });

        return view;
    }


    private void FamilyUpdateLogList()

    {
        familydataSet.clear();

        for (int i = 0; i < FamilylistItems.size(); i++) {

            String name = null, contact = null, cnic = null;

            if (FamilylistItems.get(i).getChildName() != null)

            {
                name = FamilylistItems.get(i).getChildName();
            }

            if (FamilylistItems.get(i).getFatherName() != null)

            {
                contact = FamilylistItems.get(i).getFatherName();
            }

            if (FamilylistItems.get(i).getAge() != null) {
                cnic = FamilylistItems.get(i).getAge();
            }

            familydataSet.add(new ClusterForm(name, contact, cnic));
        }

//        Radapter = new ZeroAdapter(familydataSet, getActivity());
//        family_ListView.setAdapter(Radapter);

    }

    void submit()

    {

        final ProgressDialog PD = new ProgressDialog(getActivity());
        PD.setMessage("Saving Record, Please Wait...");
        PD.show();

        int a = 0;
        int b = 0;

        long res = AppController.saveInspections.save();

        if(res != -1)

        {
            if(AppController.checklistType.equals("Outreach Session") || Username.toLowerCase().startsWith("asv"))

            {
                int lol = AppController.saveChecklists.size();

                if(lol > 0)

                {
                    for (int i = 0; i < AppController.saveChecklists.size(); i++)

                    {
                        SaveChecklist si = new SaveChecklist();

                        si.setText(AppController.saveChecklists.get(i).getText());
                        si.setServer_id(AppController.saveChecklists.get(i).getServer_id());
                        si.setAnswer(AppController.saveChecklists.get(i).getAnswer());
                        si.setMastId(AppController.saveChecklists.get(i).getMastId());
                        si.setSync("0");
                        si.setRemarks(AppController.saveChecklists.get(i).getRemarks());
                        si.setCreatedBy(AppController.saveChecklists.get(i).getCreatedBy());
                        long r = si.save();

                        if(r != -1)

                        {
                            a++;
                        }

                        else

                        {
                            break;
                        }

                    }
                }

                else

                    {
                        Toast.makeText(getActivity(),"Checklist is empty!", Toast.LENGTH_LONG).show();
                    }

            }


            for (int i = 0 ; i < FamilylistItems.size() ; i++)

            {
                ClusterForm si = new ClusterForm();

                si.setCardNo(FamilylistItems.get(i).cardNo);
                si.setChildName(FamilylistItems.get(i).childName);
                si.setFatherName(FamilylistItems.get(i).fatherName);
                si.setAge(FamilylistItems.get(i).age);
                si.setDateOfBirth(FamilylistItems.get(i).dateOfBirth);
                si.setContactNo(FamilylistItems.get(i).contactNo);
                si.setSync("0");
                si.setMastId(AppController.getInstance().MasterID);
                si.setCreatedBy(createdBy);
                si.setVisitDateTime(AppController.getInstance().date);
                si.setbCG(FamilylistItems.get(i).bCG);
                si.setHep_B_BD(FamilylistItems.get(i).hep_B_BD);
                si.setoPV_0(FamilylistItems.get(i).oPV_0);
                si.setPenta_I(FamilylistItems.get(i).penta_I);
                si.setpCV_10_I(FamilylistItems.get(i).pCV_10_I);
                si.setoPV_I(FamilylistItems.get(i).oPV_I);
                si.setRota_I(FamilylistItems.get(i).rota_I);
                si.setPenta_II(FamilylistItems.get(i).penta_II);
                si.setpCV_10_II(FamilylistItems.get(i).pCV_10_II);
                si.setoPV_II(FamilylistItems.get(i).oPV_II);
                si.setRota_II(FamilylistItems.get(i).rota_II);
                si.setPenta_III(FamilylistItems.get(i).penta_III);
                si.setpCV_10_III(FamilylistItems.get(i).pCV_10_III);
                si.setoPV_III(FamilylistItems.get(i).oPV_III);
                si.setiPV(FamilylistItems.get(i).iPV);
                si.setMeasles_I(FamilylistItems.get(i).measles_I);
                si.setMeasles_II(FamilylistItems.get(i).measles_II);
                si.setVaccinationStatus(FamilylistItems.get(i).vaccinationStatus);
                si.setBcgDate(FamilylistItems.get(i).BcgDate);
                si.setHepBBDDate(FamilylistItems.get(i).HepBBDDate);
                si.setOPV0Date(FamilylistItems.get(i).OPV0Date);
                si.setPentaIDate(FamilylistItems.get(i).PentaIDate);
                si.setPCV10IDate(FamilylistItems.get(i).PCV10IDate);
                si.setOPVIDate(FamilylistItems.get(i).OPVIDate);
                si.setRotaIDate(FamilylistItems.get(i).RotaIDate);
                si.setPentaIIDate(FamilylistItems.get(i).PentaIIDate);
                si.setPCV10IIDate(FamilylistItems.get(i).PCV10IIDate);
                si.setOPVIIDate(FamilylistItems.get(i).OPVIIDate);
                si.setRotaIIDate(FamilylistItems.get(i).RotaIIDate);
                si.setPentaIIIDate(FamilylistItems.get(i).PentaIIIDate);
                si.setPCV10IIIDate(FamilylistItems.get(i).PCV10IIIDate);
                si.setOPVIIIDate(FamilylistItems.get(i).OPVIIIDate);
                si.setIPVDate(FamilylistItems.get(i).IPVDate);
                si.setMeaslesIDate(FamilylistItems.get(i).MeaslesIDate);
                si.setMeaslesIIDate(FamilylistItems.get(i).MeaslesIIDate);
                si.setRemarks(FamilylistItems.get(i).Remarks);

                long r = si.save();

                if(r != -1)

                {
                    b++;
                }

                else

                {
                    break;
                }

            }

            if(a == AppController.saveChecklists.size() && b == FamilylistItems.size())

            {
                PD.dismiss();
                moveNext();
            }

            else

            {
                PD.dismiss();
                SaveInspections.DeleteData(AppController.saveInspections.serverid);
                SaveChecklist.DeleteData(AppController.saveInspections.serverid);
                ClusterForm.DeleteData(AppController.saveInspections.serverid);
                Toast.makeText(getActivity(),"Failed!", Toast.LENGTH_LONG).show();
            }

        }

        else

            {
                PD.dismiss();Toast.makeText(getActivity(),"Failed!", Toast.LENGTH_LONG).show();
            }
    }

    void moveNext()

    {
        AppController.saveChecklists  = new ArrayList<>();
        AppController.saveInspections = null;
        AppController.checklistType   = null;
        AppController.MasterID        = null;
        AppController.location        = null;
        AppController.date            = null;
        Toast.makeText(getActivity(),"Record Save Successfully!", Toast.LENGTH_LONG).show();
        MainActivity.main.switchFragment(0);
    }

    public boolean validate()

    {
        boolean valid = true;

        if (FamilylistItems.size() < 10)

        {
            Toast.makeText(getActivity(),"Please enter 10 children data",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }


    public boolean validateFamilyData()

    {
        boolean valid = true;

        if (CardNumberValue == null)

        {
            Toast.makeText(getActivity(),"Please enter card number",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (ChildNameValue == null)

        {
            Toast.makeText(getActivity(),"Please enter child name",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (ageValue == null)

        {
            Toast.makeText(getActivity(),"Please enter age",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (FatherNameValue == null)

        {
            Toast.makeText(getActivity(),"Please enter father name",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (contactValue == null || !contactValue.startsWith("03"))

        {
            Toast.makeText(getActivity(),"Please enter valid contact",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (VaccineValue == null)

        {
            Toast.makeText(getActivity(),"Please select vaccine status",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (bcgValue != null)

        {
            if(BcgDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Bcg Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (hepbbdValue != null)

        {
            if(HepBBDDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select HepBBD Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pentaIValue != null)

        {
            if(PentaIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Penta I Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pcv10IValue != null)

        {
            if(PCV10IDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select PCV 10 I Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (opvIValue != null)

        {
            if(OPVIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select OPV I Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (rotaIValue != null)

        {
            if(RotaIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Rota I Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pentaIIValue != null)

        {
            if(PentaIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Penta II Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pcv10IIValue != null)

        {
            if(PCV10IIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select PCV 10 II Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (opvIIValue != null)

        {
            if(OPVIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select OPV II Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (rotaIIValue != null)

        {
            if(RotaIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Rota II Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pentaIIIValue != null)

        {
            if(PentaIIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Penta III Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (pcv10IIIValue != null)

        {
            if(PCV10IIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select PCV 10 III Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (opvIIIValue != null)

        {
            if(OPVIIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select OPV III Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (ipvValue != null)

        {
            if(IPVDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select IPV Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (measlesIValue != null)

        {
            if(MeaslesIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Measles I Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (measlesIIValue != null)

        {
            if(MeaslesIIDateValue == null)

            {
                Toast.makeText(getActivity(),"Please select Measles II Date",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        if (VaccineValue != null)

        {
            if(VaccineValue.equals("Un-Vaccinated") || VaccineValue.equals("Partially Vaccinated"))

            {
                if(RemarksValue == null)

                {
                    Toast.makeText(getActivity(),"Please enter remarks",Toast.LENGTH_SHORT).show();
                    valid = false;
                }
            }
        }

        return valid;

    }

    public void clearFocus()

    {
        if(CardNumber != null){ CardNumber.clearFocus(); }
        if(ChildName != null) { ChildName.clearFocus();  }
        if(FatherName != null){ FatherName.clearFocus(); }
        if(contact != null)   { contact.clearFocus();    }
        if(Remarks != null)   { Remarks.clearFocus();    }
    }

    void FamilyClearData()

    {
        CardNumberValue    = null;
        ChildNameValue     = null;
        ageValue           = null;
        dobValue           = null;
        FatherNameValue    = null;
        contactValue       = null;
        bcgValue           = null;
        hepbbdValue        = null;
        opvoValue          = null;
        pentaIValue        = null;
        pcv10IValue        = null;
        opvIValue          = null;
        rotaIValue         = null;
        pentaIIValue       = null;
        pcv10IIValue       = null;
        opvIIValue         = null;
        rotaIIValue        = null;
        pentaIIIValue      = null;
        pcv10IIIValue      = null;
        opvIIIValue        = null;
        ipvValue           = null;
        measlesIValue      = null;
        measlesIIValue     = null;
        VaccineValue       = null;
        BcgDateValue            = null;
        HepBBDDateValue         = null;
        OPV0DateValue           = null;
        PentaIDateValue         = null;
        PCV10IDateValue         = null;
        OPVIDateValue           = null;
        RotaIDateValue          = null;
        PentaIIDateValue        = null;
        PCV10IIDateValue        = null;
        OPVIIDateValue          = null;
        RotaIIDateValue         = null;
        PentaIIIDateValue       = null;
        PCV10IIIDateValue       = null;
        OPVIIIDateValue         = null;
        IPVDateValue            = null;
        MeaslesIDateValue       = null;
        MeaslesIIDateValue      = null;
        RemarksValue            = null;

    }

    private double getAge()

    {
        Date    result = null;
        ageValue       = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            result   = df.parse(dobValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date current  = now.getTime();

        DateTime t = new DateTime(result);

        if (!result.after(current))

        {
            long diff     = (current.getTime() - result.getTime());
            int days      = (int)(((diff/1000)/3600)/24);
            int year      = (int) days / 365;
            int month_qw  = (int) days % 365;
            int month     = (int) month_qw / 30;
            int day       = (int) month_qw % 30 ;

            ageValue      = year + "." + month;

        }

        return Double.parseDouble(ageValue);
    }


    public void setVaccinationDate(final String button)

    {

        final Dialog dialog = new Dialog(MainActivity.main);
        dialog.setContentView(R.layout.datepopup);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText day_text   = dialog.findViewById(R.id.day);
        final EditText month_text = dialog.findViewById(R.id.month);
        final EditText year_text  = dialog.findViewById(R.id.year);
        Button close              = dialog.findViewById(R.id.close);
        Button Set                = dialog.findViewById(R.id.Set);

        if(button.equals("BcgDate") && BcgDateValue != null)

        {
            String[] a  = BcgDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);

        }

        if(button.equals("HepBBDDate") && HepBBDDateValue != null)

        {

            String[] a  = HepBBDDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);

        }

        if(button.equals("OPV0Date") && OPV0DateValue != null)

        {
            String[] a  = OPV0DateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PentaIDate") && PentaIDateValue != null)

        {

            String[] a  = PentaIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PCV10IDate") && PCV10IDateValue != null)

        {
            String[] a  = PCV10IDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("OPVIDate") && OPVIDateValue != null)

        {
            String[] a  = OPVIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("RotaIDate") && RotaIDateValue != null)

        {
            String[] a  = RotaIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PentaIIDate") && PentaIIDateValue != null)

        {
            String[] a  = PentaIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PCV10IIDate") && PCV10IIDateValue != null)

        {
            String[] a  = PCV10IIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("OPVIIDate") && OPVIIDateValue != null)

        {
            String[] a  = OPVIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("RotaIIDate") && RotaIIDateValue != null)

        {
            String[] a  = RotaIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PentaIIIDate") && PentaIIIDateValue != null)

        {
            String[] a  = PentaIIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("PCV10IIIDate") && PCV10IIIDateValue != null)

        {
            String[] a  = PCV10IIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("OPVIIIDate") && OPVIIIDateValue != null)

        {
            String[] a  = OPVIIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("IPVDate") && IPVDateValue != null)

        {
            String[] a  = IPVDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("MeaslesIDate") && MeaslesIDateValue != null)

        {
            String[] a  = MeaslesIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        if(button.equals("MeaslesIIDate") && MeaslesIIDateValue != null)

        {
            String[] a  = MeaslesIIDateValue.split("-");

            day        = a[2];
            Day_int    = Integer.parseInt(day);

            month      = a[1];
            Month_int  = Integer.parseInt(month);

            year       = a[0];
            Year_int   = Integer.parseInt(year);

            day_text.setText(day);
            month_text.setText(month);
            year_text.setText(year);
        }

        dialog.show();

        day_text.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && day_text.isEnabled())

                {
                    if(day_text.length() != 0)

                    {
                        day        = day_text.getText().toString();
                        Day_int    = Integer.parseInt(day);

                        if(Day_int < 1 || Day_int > 31)

                        {
                            day_text.setText(null);
                            day_text.setError("Enter Valid Day");
                            day = null;
                        }

                        else

                        {
                            day_text.setError(null);
                            day  = Day_int.toString();
                            day_text.setText(day);
                        }

                    }

                    else

                    {
                        day = null;
                    }

                }

            }
        });

        month_text.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && month_text.isEnabled())

                {

                    if(month_text.length() != 0)

                    {
                        month        = month_text.getText().toString();
                        Month_int    = Integer.parseInt(month);

                        if(Month_int < 1 || Month_int > 12)

                        {
                            month_text.setText(null);
                            month_text.setError("Enter Valid Month");
                            month = null;
                        }

                        else

                        {
                            month_text.setError(null);
                            month = Month_int.toString();
                            month_text.setText(month);
                        }
                    }

                    else

                    {
                        month = null;
                    }

                }
            }
        });

        year_text.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus && year_text.isEnabled())

                {

                    if(year_text.length() != 0)

                    {
                        year          = year_text.getText().toString();
                        Year_int      = Integer.parseInt(year);

                        if(Year_int < Cyear - 5 || Year_int > Cyear)

                        {
                            year_text.setText(null);
                            year_text.setError("Enter Valid Year");
                            year = null;
                        }

                        else

                        {
                            year_text.setError(null);
                            year = Year_int.toString();
                            year_text.setText(year);
                        }
                    }

                    else

                    {
                        year = null;
                    }

                }

            }
        });

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day_text.clearFocus();
                month_text.clearFocus();
                year_text.clearFocus();

                if (day != null && month != null && year != null)

                {
                    try {
                        dialog.dismiss();
                        DobCalculator(button);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.main, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                else

                    {
                        Toast.makeText(MainActivity.main, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
    }

    void  DobCalculator (String button) throws java.text.ParseException

    {
        Date result;

        String DOB = year + "-" + month + "-" + day;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        result        = df.parse(DOB);

        Date current  = now.getTime();

        if (!result.after(current))

        {
            if(button.equals("BcgDate"))

            {
                BcgDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("HepBBDDate"))

            {
                HepBBDDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("OPV0Date"))

            {
                OPV0DateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PentaIDate"))

            {
                PentaIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PCV10IDate"))

            {
                PCV10IDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("OPVIDate"))

            {
                OPVIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("RotaIDate"))

            {
                RotaIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PentaIIDate"))

            {
                PentaIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PCV10IIDate"))

            {
                PCV10IIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("OPVIIDate"))

            {
                OPVIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("RotaIIDate"))

            {
                RotaIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PentaIIIDate"))

            {
                PentaIIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("PCV10IIIDate"))

            {
                PCV10IIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("OPVIIIDate"))

            {
                OPVIIIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("IPVDate"))

            {
                IPVDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("MeaslesIDate"))

            {
                MeaslesIDateValue = year + "-" + month + "-" + day;
            }

            if(button.equals("MeaslesIIDate"))

            {
                MeaslesIIDateValue = year + "-" + month + "-" + day;
            }

            Toast.makeText(MainActivity.main, "Date Added.", Toast.LENGTH_SHORT).show();
        }

        else

        {
            Toast.makeText(MainActivity.main, "Please enter Valid Date", Toast.LENGTH_SHORT).show();
        }
    }

}
