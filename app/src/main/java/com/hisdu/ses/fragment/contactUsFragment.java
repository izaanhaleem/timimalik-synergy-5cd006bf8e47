package com.hisdu.ses.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.hisdu.ses.Database.contactinfoTable;
import com.hisdu.ses.Models.contactUs.contactUsResponse;
import com.hisdu.ses.R;

import java.util.List;


public class contactUsFragment extends Fragment {


    public contactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        TextView primaryemail = (TextView)view.findViewById(R.id.primaryemail);
        TextView secondaryemail = (TextView)view.findViewById(R.id.secondaryemail);
        TextView primarycontact = (TextView)view.findViewById(R.id.primarycontact);
        TextView secondarycontact = (TextView)view.findViewById(R.id.secodarycontact);



        List<contactinfoTable> tables = contactinfoTable.getAllcontactInfo();

for(int i = 0;i<tables.size();i++) {
        primaryemail.setText(tables.get(i).primaryEmail);
        secondaryemail.setText(tables.get(i).secondaryEmail);
        primarycontact.setText(tables.get(i).primaryContactNo);
        secondarycontact.setText(tables.get(i).secondaryContactNo);

}
        return view;
    }
}