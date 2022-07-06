package com.hisdu.SESCluster.fragments.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.BoosterDoseObject;
import com.hisdu.SESCluster.objects.ZeroToFortyDaysObject;
import com.hisdu.ses.R;

import java.util.Date;


public class BoosterDoseFragment extends BaseVaccinationFragment implements CompoundButton.OnCheckedChangeListener, OnDialogButtonClickListener {
    Button btnSubmit;
    ToggleButton tbhepbVaccinated;
    EditText etRemarks;
    private StringBuilder dateString;
    BoosterDoseObject boosterDoseObject = new BoosterDoseObject();
    String zerodose = "false";

    private static Bundle mbundle = new Bundle();
    Date DateOfBirth = null;
    public static BoosterDoseFragment getInstance(Bundle bundle, String title, int icon) {
        BoosterDoseFragment fragment = new BoosterDoseFragment();
        fragment.setArguments(bundle);
        fragment.setFragmentTitle(title);
        fragment.setFragmentIconId(icon);
        mbundle = bundle;
        return fragment;
    }

    @Override
    protected void initializeControls(View v) {
        DateOfBirth = (Date) mbundle.getSerializable("DOB");

        btnSubmit = v.findViewById(R.id.btnSubmit);
        etRemarks = v.findViewById(R.id.etRemarks);
        tbhepbVaccinated = v.findViewById(R.id.tbhepbVaccinated);

        BoosterDoseObject zeroToFortyDaysObject1 = mbundle.getParcelable(Globals.Arguments.BOOSTER_DOSE);

        if (zeroToFortyDaysObject1 != null) {
            if (zeroToFortyDaysObject1.getBoosterVaccinated() != null && zeroToFortyDaysObject1.getBoosterVaccinated().length() > 0) {
                if (zeroToFortyDaysObject1.getBoosterVaccinated().equalsIgnoreCase(getString(R.string.true_))) {
                    tbhepbVaccinated.setChecked(true);
                } else
                    tbhepbVaccinated.setChecked(false);
            }

        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.boosterdose_fragment;
    }

    @Override
    protected void initializationBundle(Bundle bundle) {
    }

    @Override
    protected void attachListeners() {
        btnSubmit.setOnClickListener(this);
        tbhepbVaccinated.setOnCheckedChangeListener(this);
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
//                if (isValidate()) {
                    boosterDoseObject.setRemarks(etRemarks.getText().toString());
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            new Intent().putExtra(Globals.Arguments.BOOSTER_DOSE, boosterDoseObject));
                    getActivity().onBackPressed();


                break;

            default:
                super.onClick(view);
                break;
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {

            case R.id.tbhepbVaccinated:

                zerodose = String.valueOf(b);
                boosterDoseObject.setBoosterVaccinated(zerodose);
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

        if(zerodose.equalsIgnoreCase("true") ){

        }else {
            Toast.makeText(getContext(),"Please Select Vaccination",Toast.LENGTH_LONG).show();
            values= 1;
        }
        return values == 0;
    }

}
