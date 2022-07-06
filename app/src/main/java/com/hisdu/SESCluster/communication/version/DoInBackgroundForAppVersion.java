package com.hisdu.SESCluster.communication.version;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hisdu.ses.BuildConfig;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.communication.NetworkUtil;
import com.hisdu.SESCluster.utils.TransparentProgressDialog;

import java.util.concurrent.TimeoutException;


public class DoInBackgroundForAppVersion extends AsyncTask<Void, Void, String> {

    private static String ERROR_TIMEOUT_EXCEPTION = "100";
    private static String ERROR_API_EXCEPTION = "101";
    private static String ERROR_NETWORK = "102";
    private static String ERROR_INVALID_METHOD = "103";

    private TransparentProgressDialog mProgress;
    private Context context;
    private int requestCode;
    private boolean cancelable, showDialog = true, isLogin;
    private OnPostExecutionListener mListener;


    public DoInBackgroundForAppVersion(Context context, int requestCode,
                                       boolean cancelable, OnPostExecutionListener mListener) {
        this.context = context;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.mListener = mListener;
    }


    @Override
    protected String doInBackground(Void... params) {
        String url = "http://localhost/api/version/" + context.getPackageName() + "/" + BuildConfig.VERSION_NAME;
        if (NetworkUtil.isNetworkAvailable(context)) {
            try {
                return WebBinderForVersion.doGet(url);
            } catch (TimeoutException e) {
                e.printStackTrace();
                return ERROR_TIMEOUT_EXCEPTION;
            } catch (WebBinderForVersion.ApiException e) {
                e.printStackTrace();
                return ERROR_API_EXCEPTION;
            }
        } else {
            return ERROR_NETWORK;
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            mProgress = new TransparentProgressDialog(context);
            mProgress.setCancelable(cancelable);
            mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(false);
                }
            });
            mProgress.show();
        }


    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        showToast(context.getResources().getString(R.string.lbl_task_cancelled));
    }

    @Override
    protected void onCancelled(String result) {
        super.onCancelled(result);
        showToast(context.getResources().getString(R.string.lbl_task_cancelled));
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (mProgress != null && mProgress.isShowing()) mProgress.dismiss();

        if (result == null) {
            mListener.onPostExecution("", requestCode);
            return;
        }
        mListener.onPostExecution(result, requestCode);
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public interface OnPostExecutionListener {
        void onPostExecution(String response, int requestCode);
    }

    public interface OnPostExecutionListener1 {
        void onPostExecution(int responseCode, String response, int requestCode);
    }
}