package com.hisdu.SESCluster.communication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hisdu.ses.R;
import com.hisdu.SESCluster.utils.TransparentProgressDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;


public class DoInBackground extends AsyncTask<Void, Void, String> {

    public enum MethodType {
        GET(0), POST(1), PUT(2), POST_FORM_ENCODED(3), SOAP(4);

        private int numVal;

        MethodType(int numVal) {
            this.numVal = numVal;
        }

        @Override
        public String toString() {
            return "MethodType{" +
                    "numVal=" + numVal +
                    '}';
        }
    }

    private static String ERROR_TIMEOUT_EXCEPTION = "100";
    private static String ERROR_API_EXCEPTION = "101";
    private static String ERROR_NETWORK = "102";
    private static String ERROR_INVALID_METHOD = "103";

    private TransparentProgressDialog mProgress;
    private Context context;
    private int requestCode;
    private String url;
    private MethodType methodType;
    private JSONObject paramJsonObjects;
    private ArrayList<RequestObject> mHeaders = null;
    private ArrayList<RequestObject> mParams = null;
    private String message;
    private boolean cancelable, showDialog = true, isLogin;
    private OnPostExecutionListener mListener;
    private OnPostExecutionListener1 mListener1;
    private boolean isCache = false;
//    private LocalDatabaseManager mDBManger;

    //Soap
    private String soapAction = "http://tempuri.org/FahrenheitToCelsius";
    private String nameSpace = "http://tempuri.org/";
    private String methodName = "FahrenheitToCelsius";
    private String body;
    private String userName, password;
    private int responseCode;

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, JSONObject paramJsonObjects,
                          String message, boolean cancelable, OnPostExecutionListener mListener) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.paramJsonObjects = paramJsonObjects;
        this.mListener = mListener;
        this.mHeaders = null;
    }

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, JSONObject paramJsonObjects,
                          String message, boolean cancelable, boolean isLogin, OnPostExecutionListener mListener,
                          String userName, String password) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.paramJsonObjects = paramJsonObjects;
        this.mListener = mListener;
        this.mHeaders = null;
        this.isLogin = isLogin;
        this.userName = userName;
        this.password = password;
    }

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, JSONObject paramJsonObjects,
                          String message, boolean cancelable, OnPostExecutionListener mListener, boolean showDialog) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.paramJsonObjects = paramJsonObjects;
        this.mListener = mListener;
        this.mHeaders = null;
        this.showDialog = showDialog;
    }

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, JSONObject paramJsonObjects,
                          String message, boolean cancelable, OnPostExecutionListener mListener, boolean showDialog, boolean isCache) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.paramJsonObjects = paramJsonObjects;
        this.mListener = mListener;
        this.mHeaders = null;
        this.showDialog = showDialog;
        this.isCache = isCache;
//        mDBManger = LocalDatabaseManager.getInstance(context);
    }

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, ArrayList<RequestObject> headers, ArrayList<RequestObject> paramObjects,
                          String message, boolean cancelable, OnPostExecutionListener mListener) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.mParams = paramObjects;
        this.mListener = mListener;
        this.mHeaders = headers;
    }

    public DoInBackground(Context context, int requestCode, String url, MethodType methodType, String method, String namespace,
                          String xml, String message, boolean cancelable, OnPostExecutionListener mListener) {
        this.context = context;
        this.methodType = methodType;
        this.url = url;
        this.message = message;
        this.cancelable = cancelable;
        this.requestCode = requestCode;
        this.methodName = method;
        this.nameSpace = namespace;
        this.body = xml;
        this.mListener = mListener;
    }


    @Override
    protected String doInBackground(Void... params) {
        if (NetworkUtil.isNetworkAvailable(context)) {
            try {
                if (methodType == MethodType.GET && isLogin)
                    return WebBinder.doGet(url, mHeaders, userName, password);
                else if (methodType == MethodType.GET && !isLogin)
                    return WebBinder.doGet(url, mHeaders,userName, password);
                else if (methodType == MethodType.POST && isLogin)
                    return WebBinder.doPost(paramJsonObjects, url, userName, password);
                else if (methodType == MethodType.POST && !isLogin)
                    return WebBinder.doPost(paramJsonObjects, url);
                else if (methodType == MethodType.POST_FORM_ENCODED)
                    return WebBinder.doPostFormEncoded(mParams, url, mHeaders);
                else if (methodType == MethodType.SOAP)
                    return WebBinder.doSoapRequest(url, methodName, body, nameSpace);
                else if (methodType == MethodType.PUT)
                    return WebBinder.doPut(paramJsonObjects, url);
                else
                    return ERROR_INVALID_METHOD;
            } catch (TimeoutException e) {
                e.printStackTrace();
                return ERROR_TIMEOUT_EXCEPTION;
            } catch (WebBinder.ApiException e) {
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
//            showToast(context.getString(R.string.err_went_wrong));
            mListener.onPostExecution("", requestCode);
            return;
        }

        if (result.equals(ERROR_TIMEOUT_EXCEPTION)) {
            showToast(context.getString(R.string.toast_time_out));
            mListener.onPostExecution("", requestCode);
        } else if (result.equals(ERROR_API_EXCEPTION)) {
            Log.d("TAG", "ERROR ACTIVITY");
            mListener.onPostExecution("", requestCode);
        } else if (result.equals(ERROR_NETWORK)) {
            if (isCache) {
//                result = mDBManger.fetchPreference(String.valueOf(url.hashCode()));
                mListener.onPostExecution(result, requestCode);
            } else {
//                showToast(context.getString(R.string.toast_network_error));
                mListener.onPostExecution("", requestCode);
            }
        } else if (result.equals(ERROR_INVALID_METHOD)) {
            showToast("Select valid method type");
            mListener.onPostExecution("", requestCode);
        } else if (mListener != null) {
//            if (isCache)
//                mDBManger.savePreference(String.valueOf(url.hashCode()), result);
            mListener.onPostExecution(result, requestCode);
        }

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