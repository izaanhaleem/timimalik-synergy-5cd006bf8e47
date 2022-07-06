package com.hisdu.SESCluster.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;

/**
 * Created by evento on 3/11/2017.
 */

@SuppressLint("ValidFragment")
public class AlertDialogFragment extends DialogFragment implements View.OnClickListener {
    AlertDialog.Builder mDialogBuilder;
    TextView tvTile, tvMessage;
    Button tvOk/*, tvCancel*/;
    String title, message;
    boolean isRating;
    OnDialogButtonClickListener onDialogButtonClickListener;
    int requestCode;
    boolean closeActivity = true;
    boolean isSingleChoice = false;
    @SuppressLint("ValidFragment")
    public AlertDialogFragment(String title, String message, int requestCode, boolean isSingleChoice, OnDialogButtonClickListener onDialogButtonClickListener, String val) {
        this.title = title;
        this.message = message;
        this.onDialogButtonClickListener = onDialogButtonClickListener;
        this.requestCode = requestCode;
        this.closeActivity = false;
        this.isSingleChoice = isSingleChoice;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.alert_dialog_fragment, null);
        initializeControls(rootView);
        Dialog dialog = new Dialog(getActivity(), R.style.AppCompatAlertDialogStylePrimary);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(rootView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    private void initializeControls(View view) {

        tvTile = view.findViewById(R.id.tvTitle);
        tvMessage = view.findViewById(R.id.tvMessage);
        tvOk = view.findViewById(R.id.btnOk);
//        tvCancel = view.findViewById(R.id.tvCancel);
        tvTile.setText(title);
        tvMessage.setText(message);
        attachListener();
    }

    private void attachListener() {
        tvOk.setOnClickListener(this);
//        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       /* if (view == tvCancel) {
            onDialogButtonClickListener.onDialogNegativeButtonClick(requestCode);
            dismiss();
        } else*/ if (view == tvOk) {
            onDialogButtonClickListener.onDialogPositiveButtonClick(requestCode);
            dismiss();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() == null)
            return;
        else {
            Window window = getDialog().getWindow();
            window.setGravity(Gravity.CENTER);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setCancelable(true);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}

