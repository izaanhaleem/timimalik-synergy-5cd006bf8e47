package com.hisdu.SESCluster.widgets;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;


/**
 * Created by evento on 3/11/2017.
 */

@SuppressLint("ValidFragment")
public class CustomDialog extends DialogFragment implements View.OnClickListener {
    AlertDialog.Builder mDialogBuilder;
    TextView tvTile, tvMessage;
    Button tvOk, tvCancel;
    String title, message, positiveText, negativeText;
    OnDialogButtonClickListener onDialogButtonClickListener;
    int requestCode;
    boolean isCancel = false;
    ImageView ivClose;


    @SuppressLint("ValidFragment")
    public CustomDialog(String title, String message, String positiveText, String negativeText, int requestCode,
                        OnDialogButtonClickListener onDialogButtonClickListener, boolean isCancel) {
        this.title = title;
        this.message = message;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.onDialogButtonClickListener = onDialogButtonClickListener;
        this.requestCode = requestCode;
        this.isCancel = isCancel;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.custom_dialog_fragment, null);
        initializeControls(rootView);
        Dialog dialog = new Dialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(rootView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    private void initializeControls(View view) {

        tvTile = view.findViewById(R.id.tvTitle);
        tvMessage = view.findViewById(R.id.tvMessage);
        tvOk = view.findViewById(R.id.tvOk);
        tvCancel = view.findViewById(R.id.tvCancel);
        ivClose = view.findViewById(R.id.ivClose);

        tvTile.setText(title);
        tvMessage.setText(message);
        tvOk.setText(positiveText);
        tvCancel.setText(negativeText);
        attachListener();
    }

    private void attachListener() {
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvCancel) {
            onDialogButtonClickListener.onDialogNegativeButtonClick(requestCode);
            dismiss();
        } else if (view == tvOk) {
            onDialogButtonClickListener.onDialogPositiveButtonClick(requestCode);
            dismiss();
        } else if (view == ivClose) {
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
            getDialog().setCancelable(false);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}

