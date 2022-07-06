package com.hisdu.SESCluster.base;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.communication.DoInBackground;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.database_1.DataSourceOperations;
import com.hisdu.SESCluster.fragments.support.SpinnerFragment;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;
import com.hisdu.SESCluster.objects.support.SpinnerObject;
import com.hisdu.SESCluster.utils.Dialogs;
import com.hisdu.SESCluster.utils.UIHelper;
import com.hisdu.SESCluster.widgets.CustomDialog;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public abstract class BaseFragment extends Fragment implements View.OnClickListener, DoInBackground.OnPostExecutionListener {


    /*Publically accessible property*/
    public String fragmentTitle;
//    public Calendar calendar;
    public int hours = 0, min = 0, sec = 0;
    public int fragmentIconId;
    public static boolean isSpanishAvailable = false;
    public static Location myLocation;
    protected
    OnViewClickListener onViewClickListener;
    protected static final int REQUEST_CAMERA = 0;
    protected static final int FROM_GALLERY = 1;

    /*Activity */
    public Activity mActivity;
    public static String area ="";
    public static String siaNo ="";
    protected Boolean mThisActivityIsGrayScale = null;
    protected boolean isGreyScale = false;


    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    public int getFragmentIconId() {
        return fragmentIconId;
    }

    public void setFragmentIconId(int fragmentIconId) {
        this.fragmentIconId = fragmentIconId;
    }

    protected LocalDatabaseManager mDbManager;
    protected DataSourceOperations mDbOperationOnDS;


    public BaseFragment() {
        // Required empty public constructor
    }

    public void setNavigationTitle(String title) {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.updateTitle(title);
        }
    }

    public Location getMyLocation() {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.getLocation();
            myLocation = activity.getLocation();
            return activity.getLocation();
        }
        return myLocation;
    }

    public String getIMEI() {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.getLocation();
            return activity.getIMEINO();
        }
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        calendar = Calendar.getInstance();
        initializationBundle(getArguments());


    }


    @Override
    public void onResume() {
        super.onResume();
        //animateSlidingTab(true);
        setFragmentTitle(fragmentTitle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mActivity = activity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResourceId(), container, false);
        mDbManager = LocalDatabaseManager.getInstance(getActivity());
//        mDbOperationOnDS = new DataSourceOperations(getActivity());
        initializeControls(v);
        initializationBundle(savedInstanceState);
        attachListeners();
        initializeData();
        myLocation = getMyLocation();
        hideKeyBoard(v);
        return v;
    }

    /**
     * Initialize activity level controls here.
     */
    protected abstract void initializeControls(View v);

    /**
     * @return The layout xml for this activity
     */
    protected abstract int getLayoutResourceId();


    /**
     * @return The arguments which have been passed to this fragment for initialization.
     */
    protected abstract void initializationBundle(Bundle bundle);

    /**
     * Attach listeners to your controls in this method. For example textview.setOnClickListener(this).
     */
    protected abstract void attachListeners();

    /**
     * Initialize activity level Methods calling here.
     */
    protected abstract void initializeData();

    @Override
    public void onClick(View view) {
    }


//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public void replaceFragment(Fragment fragment, boolean animated, boolean updownSlide, boolean addToBackStack, String tag) {
        ((MainActivity) getActivity()).replaceFragment(fragment, animated, updownSlide, addToBackStack, tag);
//        ((NavigationDrawerActivity) getActivity()).updateIndex();


    }

    protected int getScreenHeight() {
        return ((BaseActivity) getActivity()).getScreenHeight();
    }

    protected int getScreenWidth() {
        return ((BaseActivity) getActivity()).getScreenWidth();
    }


    public void callGetMethod(String url, int requestCode) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url, DoInBackground.MethodType.GET, new JSONObject(), "", false, this);
        doInBackground.execute();
    }

    public void callPostMethod(String url, int requestCode) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url, DoInBackground.MethodType.POST, new JSONObject(), "", false, this);
        doInBackground.execute();
    }

    public void callGetMethod(String url, int requestCode, boolean isLoader) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url,
                DoInBackground.MethodType.GET, new JSONObject(), "", false, this, isLoader);
        doInBackground.execute();
    }

    public void callGetMethod(String url, int requestCode, String userName, String password) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url,
                DoInBackground.MethodType.GET, new JSONObject(), "", false, true, this, userName, password);
        doInBackground.execute();
    }

    protected void callPostMethod(String url, int requestCode, JSONObject jsonObject) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url, DoInBackground.MethodType.POST,
                jsonObject, "", false, this);
        doInBackground.execute();
    }

    protected void callPostMethodWithoutLoader(String url, int requestCode, JSONObject jsonObject) {
        DoInBackground doInBackground = new DoInBackground(getActivity(), requestCode, url, DoInBackground.MethodType.POST,
                jsonObject, "", false, this, false);
        doInBackground.execute();
    }

    @Override
    public void onPostExecution(String response, int requestCode) {

    }

    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    public void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }


    public void takePictures(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Intent cameraIntent = UIHelper.getInstance().getIntentToStoreFile(Globals.TEMP_IMG_PATH, getActivity());
        startActivityForResult(cameraIntent, tag);
    }

    protected String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Globals.WEB_DATE_FORMAT1, Locale.ENGLISH);
        Log.d("System Date in Unix", Long.toString(new Date().getTime()));
        return Long.toString(new Date().getTime());
    }

    protected String getDateFromLocation() {
        String locationDate = "";
        if (myLocation != null) {
            long time = myLocation.getTime();
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat(Globals.WEB_DATE_FORMAT1, Locale.ENGLISH);
            locationDate = sdf.format(date);
        }
        Log.d("Location date in Unix", locationDate);
        return locationDate;
    }

    protected void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
    }

    protected boolean isCNICEmpty(EditText editText) {
        String strCNIC = editText.getText().toString();
        String[] CNICArray = strCNIC.split("-");

        if (CNICArray[0].contains(" ") || CNICArray[1].contains(" ") || CNICArray[2].contains(" ")) {
            return true;
        } else
            return false;
    }

    protected boolean isMobileEmpty(EditText editText) {
        String strMobile = editText.getText().toString();
        String[] mobileArray = strMobile.split("-");

        if (mobileArray[0].contains(" ") || mobileArray[1].contains(" ")) {
            return true;
        } else
            return false;
    }

    protected void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    protected void showMessage(String message, boolean shouldCloseOnOk) {
        Dialogs.showDialog(message, getString(R.string.app_name),
                getActivity(), true, false, getString(R.string.ok), "", shouldCloseOnOk);
    }

    protected ArrayList<SpinnerObject> getListData(List<String> data) {
        ArrayList<SpinnerObject> arrayListData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            SpinnerObject spinnerObject = new SpinnerObject();
            spinnerObject.setTitle(data.get(i));
            spinnerObject.setID(i);
            arrayListData.add(spinnerObject);
        }
        return arrayListData;
    }

    protected void loadSpinner(final String title, ArrayList<SpinnerObject> data, final EditText editText, int id, final OnViewClickListener onViewClickListener) {
        final int[] localId = {id};
        this.onViewClickListener = onViewClickListener;
        SpinnerFragment spinnerFragment = new SpinnerFragment(getActivity(), title, "", getString(R.string.ok),
                getString(R.string.cancel), true, data, new SpinnerFragment.onSpinnerClick() {
            @Override
            public void onSingleClick(String tag, SpinnerObject spinnerObject) {
                editText.setText(spinnerObject.getTitle());
                localId[0] = spinnerObject.getID();
                onViewClickListener.onViewClick(title, localId[0]);
                editText.setError(null);
            }

            @Override
            public void onMultipleClick(String tag, ArrayList<SpinnerObject> objectList) {
            }
        });
        spinnerFragment.setSelectedItemPosition(localId[0]);
        spinnerFragment.show(getFragmentManager(), "");
    }

    public interface OnViewClickListener {
        void onViewClick(String tag, int position);
    }

    protected void showImagePikerDialog() {
        showImagePickerAlert(getActivity(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.select_file)),
                        FROM_GALLERY);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }

    protected void showImagePickerAlert(Context context, DialogInterface.OnClickListener fromGallerydialogListner, DialogInterface.OnClickListener fromCameradialogListner) {
        new android.app.AlertDialog.Builder(context).setMessage(getString(R.string.choosePicture)).setCancelable(true)
                .setPositiveButton(getString(R.string.chooseFromGallery), fromGallerydialogListner)
                .setNegativeButton(getString(R.string.takeFromCamera), fromCameradialogListner).show();
    }
    protected Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    protected void loadCustomDialog(String message) {
        if (getActivity() instanceof NavigationDrawerActivity) {
            CustomDialog cancelDialog = new CustomDialog(getResources().getString
                    (R.string.app_name), message, getResources().getString(R.string.yes),
                    getResources().getString(R.string.no), 1, new OnDialogButtonClickListener() {
                @Override
                public void onDialogPositiveButtonClick(int requestCode) {
                }

                @Override
                public void onDialogNegativeButtonClick(int requestCode) {

                }
            }, true);
            cancelDialog.show(getFragmentManager(), "Alert");
        }
    } protected void loadCustomDialog(String message, OnDialogButtonClickListener onDialogButtonClickListener, int responseCode) {
        if (getActivity() instanceof NavigationDrawerActivity) {
            CustomDialog cancelDialog = new CustomDialog(getResources().getString
                    (R.string.app_name), message, getResources().getString(R.string.yes),
                    getResources().getString(R.string.no), responseCode, onDialogButtonClickListener, true);
            cancelDialog.show(getFragmentManager(), "Alert");
        }
    }
    public void hideToolBarSync() {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.hideSync();
        }
    }

    public void showToolbarSync() {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.showSync();
        }
    }
    public void updateCount() {
        if (getActivity() instanceof ToolbarActivity) {
            ToolbarActivity activity = (ToolbarActivity) getActivity();
            activity.updateCount();
        }
    }
    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    public static boolean isTimeZoneAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }
    }
    protected void showSnackBar(View view, final String message, int duration) {
        // Create snackbar
        final Snackbar snackbar = Snackbar.make(view, message, duration);

        // Set an action on it, and a handler
        snackbar.setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equalsIgnoreCase(getResources().getString(R.string.please_set_your_date_and_time)))
                    startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
