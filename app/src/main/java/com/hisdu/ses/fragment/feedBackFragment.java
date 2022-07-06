package com.hisdu.ses.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.Database.feedBackTable;
import com.hisdu.ses.LoginActivity;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.Models.feedback.feedbackResponse;
import com.hisdu.ses.Models.login.LoginRequest;
import com.hisdu.ses.Models.login.LoginResponseLatest;
import com.hisdu.ses.MyApplication;
import com.hisdu.ses.R;
import com.hisdu.ses.SharedPref;
import com.hisdu.ses.utils.ServerCalls;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;


public class feedBackFragment extends Fragment {



    public feedBackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        TextInputEditText feedbackmsg = (TextInputEditText)view.findViewById(R.id.feedback);
        Button submit = (Button)view.findViewById(R.id.submitFeed);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(feedbackmsg.getText().toString())) {
                    Toast.makeText(getContext(), "write feedBack...", Toast.LENGTH_SHORT).show();
                } else {

                    Date date = Calendar.getInstance().getTime();
                    String matdate = date.getDate() + "-" + date.getMonth() + "-" + date.getYear();

//                    int role = Integer.parseInt(new SharedPref(getContext()).GetKeyValue("UserRole"));
//                    Toast.makeText(getContext(), ""+role, Toast.LENGTH_SHORT).show();
                    feedBackTable table = new feedBackTable();
                    table.setDescription(feedbackmsg.getText().toString());
                    table.setCreatedBy(0);
                    table.setCreatedOn(matdate);
                    table.setActionTypeId(0);
                    table.setFeedBackId(0);
                    table.setUpdatedBy(0);
                    table.setUpdatedOn(new SharedPref(getContext()).GetKeyValue("UserRole"));
                    table.setSyncDateTime("2021-03-03T17:16:10.836Z");



//                    MyApplication.getHeader(userName.getText().toString(), userPassword.getText().toString());
                    ServerCalls.getInstance().feedBack(table, new ServerCalls.OnFeedBackResult(){


                        @Override
                        public void onSuccess(feedbackResponse feedbackRes) {

                            Toast.makeText(getContext(), "Submit Successfully", Toast.LENGTH_SHORT).show();
                             feedbackmsg.setText(null);
                        }

                        @Override
                        public void onFailed(int errorCode, String errorMessage) {
                            Toast.makeText(getContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


        return view;

    }
}