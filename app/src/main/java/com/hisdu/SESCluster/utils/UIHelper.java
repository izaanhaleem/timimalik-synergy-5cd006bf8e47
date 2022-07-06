package com.hisdu.SESCluster.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.content.FileProvider;

import com.hisdu.SESCluster.constants.Globals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * UI Helper Class developed to help for User Interface perspective. There are certain methods that are bunched
 * here for assistance of Developers.
 * Class is Developed  by
 *
 * @author PUTITOUT pvt ltd
 * @author Abid Rafiq
 * @author Ayaz Alvi
 * @author Usman Arshad kurd
 * @version 1.0
 */
public class UIHelper {
    // instance of UI Helper is for the Singleton behaviour of this class
    private static UIHelper instance;
    private Activity screen;
    private static WakeLock wakeLock;
    private DatePickerDialog.OnDateSetListener myDatePickerObject = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {/*		mYear = year-2000;
                                     mMonth = monthOfYear+1;
             						mDay = dayOfMonth;*/
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimePickerObject = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        /*                mHour = hourOfDay;
		                mMinute = minute;
		*/
        }
    };
    //

    /**
     * Constructer of UIHelper Class
     */
    private UIHelper() {
    }

    /**
     * Make the singleton class
     */
    public static UIHelper getInstance() {
        if (instance == null) {
            instance = new UIHelper();
        }
        return instance;
    }

    /**
     * Works when we need  Screen
     *
     * @return screen
     */

    public Activity getCurrentScreen() {
        return screen;
    }

    /**
     * Need to set Screen
     *
     * @param context
     */
    public void setCurrentScreen(Activity context) {
        screen = context;
    }


    /**
     * A Dialog box use to Represent user any particular message that is required<br>
     * This method Requires following parameter<br>
     * Title of the Message <br>
     * Message text that is Required for displaying According to User<br>
     * and Application Context
     *
     * @param title
     * @param msg
     * @param context
     */
    public void dialogBoxInUIthread(final String title, final String msg, Activity context) {
        //UIHelper.getInstance().getCurrentScreen().runOnUiThread(new Runnable() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(UIHelper.getInstance().getCurrentScreen());
                alertbox.setTitle(title);
                alertbox.setMessage(msg);
                alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertbox.show();
            }
        });
    }

    /**
     * getResizedBitmap method is used to Resized the Image according to custom width and height
     *
     * @param image
     * @param newHeight (new desired height)
     * @param newWidth  (new desired Width)
     * @return image (new resized image)
     */
    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    public String getDateStamp() {
        Date date = new Date();
        return Long.toString(date.getTime());
    }

    /**
     * This is a generalized method that is to use for formatting Date and Time
     *
     * @param unixDateStamp
     * @param dateFormat
     * @return new Date format
     */
    public String formattedDateTime(long unixDateStamp, SimpleDateFormat dateFormat) { //sample format is "yyyy-MM-dd HH:mm:ss" , "HH:mm:ss", "yyyy-MM-dd"
        Date date = new Date(unixDateStamp * 1000);
        return dateFormat.format(date);
    }

    /**
     * Read DateStemp form String and convert it in to Given Format
     *
     * @param dateStamp
     * @param formate
     * @return date format
     * @throws Parse Exception
     */
    public String dateReading(String dateStamp, String format) {
        Log.v("Date Stemp", dateStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateStamp);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Convert TimeString into String With given format.
     *
     * @param timeStemp the time stamp
     * @param format    the format
     * @return date format
     */
    public String timeReading(String timeStemp, String format) {
        Log.v("Time Stemp", timeStemp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(timeStemp);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Convert the Unix Date Stamp in Date<br>
     * SimpleDateFormat("dd MMMM, yyyy hh:mm a");<br>
     * SimpleDateForamt("EEE MMM d HH:mm:ss zzz yyyy");.<br>
     *
     * @param dateString the date string
     * @param format     the format
     * @return Date (converted date)
     */
    public Date stringToDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(convertedDate);
        return convertedDate;
    }

    /**
     * Hide the OnScreen keyboard.
     *
     * @param edittext field (name of the edit text field)
     * @param context  (Context of the Application)
     */
    public void hideSoftKeyBoard(EditText edittext, Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(edittext.getWindowToken(), 0);
    }

    /**
     * Show the OnScreen Hidden keyboard.
     *
     * @param edittext field (name of the Edit text Field
     * @param context  (Context of the Application)
     */
    public void showSoftKeyBoard(EditText edittext, Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(edittext, 0);
    }

    public double decimalPointFormat(double n) {
        double factor = 1e5; // = 1 * 10^5 = 100000.
        return Math.round(n * factor) / factor;
    }

    /**
     * Use to determine the distance from one location to another locations and return distance in meters.
     *
     * @param latitude_Loc1  (of 1st Location)
     * @param longitude_Loc1 (of 1st location)
     * @param latitude_Loc2  (of 2nd location)
     * @param longitude_Loc2 (of 2nd location)
     * @return distance between two points in meters
     */
    public double distBetween2Points(double latitude_Loc1, double longitude_Loc1, double latitude_Loc2, double longitude_Loc2) {

        double earthRadius = 3958.75;
        double dLat = Math.toRadians(latitude_Loc2 - latitude_Loc1);
        double dLng = Math.toRadians(longitude_Loc2 - longitude_Loc1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude_Loc1)) * Math.cos(Math.toRadians(latitude_Loc2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        return new Float(dist * meterConversion).floatValue();
    }

    /**
     * resize the text view according to the screen Density.
     *
     * @param textView (name of the Text view  field)
     * @param context  (context of the Application)
     */

    public void resizeTextView(TextView textView, Context context) {
        // TODO Auto-generated method stub
        Rect bounds = new Rect();
        Paint textPaint = textView.getPaint();
        textPaint.getTextBounds(textView.getText().toString(), 0, textView.getText().length(), bounds);
        int width = bounds.width();
        Log.v("current Text-Width", width + "");
        int tsize = (int) dpToPx(280, context);
        float fontSize = textView.getTextSize();
        while (width > tsize) {
            textView.setTextSize(fontSize -= 0.1f);
            textPaint.getTextBounds(textView.getText().toString(), 0, textView.getText().length(), bounds);
            width = bounds.width();
        }
    }

    /**
     * Opens the You tube intent without asking an option Open with Internet or Youtube.
     *
     * @param context of the Application
     * @param url     link of the you tube video
     * @return the you tube intent
     */
    public Intent getYouTubeIntent(Context context, String url) {
        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        final PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(videoIntent,
                0);
        for (int i = 0; i < activityList.size(); i++) {
            ResolveInfo app = activityList.get(i);
            if (app.activityInfo.name.contains("youtube")) {
                videoIntent.setClassName(app.activityInfo.packageName,
                        app.activityInfo.name);
                return videoIntent;
            }
        }
        return videoIntent;
    }

    /**
     * Email Validation.
     *
     * @param email (string for email for validation)
     * @return Email Address Pattern
     */

    public boolean emailAddressValidation(String email) {
        //?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\]
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-\\_]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    /**
     * To remove the Element from the Array.
     *
     * @param input    string (ArrayList <String>)
     * @param deleteMe (string that needs to be deleted)
     * @return result (resulted  string with removed element)
     */
    public ArrayList<String> removeElements(ArrayList<String> input, String deleteMe) {
        ArrayList<String> result = new ArrayList<String>();
        for (int c = 0; c < input.size(); c++) {
            if (!input.get(c).equals(deleteMe))
                result.add(input.get(c));
        }
        return result;
    }

    /**
     * To get the Width of the screen.
     *
     * @param context (context of the application)
     * @return the int
     */

    public static int screenWidth(Context context)


    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    /**
     * To get the Height of the screen.
     *
     * @param context (context of the application)
     * @return the int
     */
    public static int screenHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    /**
     * This Method is for Density-Independent-Pixels to Pixels conversion <br>
     * <b><h1>px</h1></b> <i><b>Pixels - corresponds to actual pixels on the screen.</b></i><br>
     * <b><h1>dp</h1></b> <i><b> Density-independent Pixels - an abstract unit that is based on the physical density
     * of the screen</b></i>.<br> These units are relative to a 160 dpi screen, so one dp is one pixel on
     * a 160 dpi screen. The ratio of dp-to-pixel will change with the screen density, but not
     * necessarily in direct proportion. Note: The compiler accepts both "dip" and "dp".
     *
     * @param floatValue the float value
     * @param context    (context of the application)
     * @return the float
     */


    public static float dpToPx(float floatValue, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        Log.v("DENSITY", Float.toString(density));
        return (float) (floatValue * density);
    }

    /**
     * This Method is for Pixels to Density-Independent-Pixels conversion <br>
     * <b>px</b> <i><b>Pixels - corresponds to actual pixels on the screen.</b></i><br>
     * <b>dp</b> <i><b> Density-independent Pixels - an abstract unit that is based on the physical density
     * of the screen</b></i>.<br> These units are relative to a 160 dpi screen, so one dp is one pixel on
     * a 160 dpi screen. The ratio of dp-to-pixel will change with the screen density, but not
     * necessarily in direct proportion. Note: The compiler accepts both "dip" and "dp".
     *
     * @param floatValue the float value
     * @param context    (context of the application)
     * @return float
     */
    public static float pxToDp(float floatValue, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (float) (floatValue / density);
    }

    /**
     * To find out whether Points lie inside the Rectangle our not Divide the Rectangle in two triangle
     * and then check whether Points lie in Rectangle A or in Rectangle B.
     * <p>
     * <b> Note :Alternatively Location can be Replaced with Point like Point pi1, Point p2 etc</b>
     *
     * @param currentLocation the current location
     * @param northEast       location (top right  location)
     * @param southEast       location (bottom right location)
     * @param northWest       location (top left location)
     * @param southWest       location (bottom left location)
     * @return true, if successful, Otherwise false
     * @see PointInTriangle(point, location1,location2,location3)
     */

    public static boolean locationWithinBox(Location currentLocation, Location northEast, Location southEast, Location northWest, Location southWest) {
        if (PointInTriangle(currentLocation, northEast, southEast, northWest) || PointInTriangle(currentLocation, southEast, northWest, southWest)) {
            return true;
        }
        return false;
    }

    /**
     * To check in Which Triangle does point Lies  either in Rectangle A or in Rectangle B.
     * <b>Note :Alternatively Location can be Replaced with Point like Point pi1, Point p2 etc</b>
     *
     * @param point  (point that has to be checked)
     * @param point1 (triangle point 1)
     * @param point2 (triangle point 2)
     * @param point3 (triangle point 3)
     * @return true, if successful,Otherwise false
     * @see sign(point ,v,v)<br>
     */

    private static boolean PointInTriangle(Location point, Location point1, Location point2, Location point3) {
        boolean b1, b2, b3;
        b1 = sign(point, point1, point2) < 0.0f;
        b2 = sign(point, point2, point3) < 0.0f;
        b3 = sign(point, point3, point1) < 0.0f;
        Log.v("B1-Calc", Float.toString(sign(point, point1, point2)));
        Log.v("B2-Calc", Float.toString(sign(point, point2, point3)));
        Log.v("B3-Calc", Float.toString(sign(point, point3, point1)));
        Log.v("B1", Boolean.toString(b1));
        Log.v("B2", Boolean.toString(b2));
        Log.v("B3", Boolean.toString(b3));
        return ((b1 == b2) && (b2 == b3));
    }

    /**
     * Sign.
     *
     * @param p1 the ponit1
     * @param p2 the point2
     * @param p3 the point3
     * @return the float
     */
    private static float sign(Location p1, Location p2, Location p3) {
        return (float) ((p1.getLongitude() - p3.getLongitude()) * (p2.getLatitude() - p3.getLatitude()) - (p2.getLongitude() - p3.getLongitude()) * (p1.getLatitude() - p3.getLatitude()));
    }

    /**
     * it makes height width is px (pixel) instead of fill parent and wrap content properties.
     *
     * @param list of element that is in specific group like Button Text field Edit text field
     */

    public static void makeHeightWidthStatic(ViewGroup list) {
        // TODO Auto-generated method stub
        for (int i = 0; i < list.getChildCount(); i++) {
            View view = list.getChildAt(i);
            if (view instanceof ViewGroup) {
                makeHeightWidthStatic((ViewGroup) view);
            } else if (view instanceof Button) {
                Button btn = (Button) view;
                btn.setMaxWidth(btn.getWidth());
                btn.setMaxHeight(btn.getHeight());
            } else if (view instanceof EditText) {
                EditText btn = (EditText) view;
                btn.setMaxWidth(btn.getWidth());
                btn.setMaxHeight(btn.getHeight());
            }
        }
    }

    /**
     * We can get the Screen type by using this method.
     * We have basically four different types of Densities<br>
     * LDPI<br>
     * MDPI<br>
     * HDPI<br>
     * XHDPI
     *
     * @param context (context of the Application)
     * @return the screen type
     */
    public static int getScreenType(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * To wake up screen when required.
     * <b><i>Note: use the permission</i></b>  <br><b> "<"uses-permission android:name="android.permission.WAKE_LOCK">"</b> <br><i><b>in manifest file.</i></b>
     *
     * @param context (context of the Application).<br>
     */
    public static void wakeUpTheScreen(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
    }

    /**
     * To release the wake up screen when required.<br>
     * <B><I>Note: use the permission</i> "<"uses-permission android:name="android.permission.WAKE_LOCK"/">"<i> in manifest file</i></b>
     *
     * @param context (context of the Application).<br>
     */

    public static void releaseWakeUpTheScreen(Context context) {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    /**
     * To unlock the screen when required.
     *
     * @param context (context of the Application)
     */
    public static void unlockTheScreen(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }


    /**
     * Distanceof point.
     *
     * @param currentLocation the current location
     * @param nw              the nw
     * @param sw              the sw
     * @return the double
     */
    public static double distanceofPoint(Location currentLocation, Location nw, Location sw) {
        double distance = distanceToSegment(nw, sw, currentLocation);
        return distance;
    }

    /**
     * Distance to segment.
     *
     * @param p1 the p1
     * @param p2 the p2
     * @param p3 the p3
     * @return the double
     */
    public static double distanceToSegment(Location p1, Location p2, Location p3) {

        double xDelta = p2.getLongitude() - p1.getLongitude();
        double yDelta = p2.getLatitude() - p1.getLatitude();

        if ((xDelta == 0) && (yDelta == 0)) {
            return 0.0;
        }

        double u = ((p3.getLongitude() - p1.getLongitude()) * xDelta + (p3.getLatitude() - p1.getLatitude()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        Location closestPoint;
        if (u < 0) {
            closestPoint = p1;
        } else if (u > 1) {
            closestPoint = p2;
        } else {
            closestPoint = new Location("");
            closestPoint.setLatitude(p1.getLatitude() + u * yDelta);
            closestPoint.setLongitude(p1.getLongitude() + u * xDelta);
        }
        //CLLocation *location1 = [[CLLocation alloc] initWithLatitude:closestPoint.latitude longitude:closestPoint.longitude];
        //CLLocation *location2 = [[CLLocation alloc] initWithLatitude:p3.latitude longitude:p3.longitude];
        return closestPoint.distanceTo(p3);
    }

    /**
     * This can be use to get info about the Services that are Application specific.
     * The number of services that are being used by application can returned using this method.
     * With in a specific class we can stop/run or any specific function suing particular Service(s).
     *
     * @param classname the classname
     * @param context   the context
     * @return the running service
     */
    public static boolean getRunningService(Class<Service> classname, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (classname.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void GetSpanAble() {
		
	 /*SpannableString spanSteps;
	SpannableString spanScan1;
	SpannableString spanScan3;
	  * 
	  *  spanSteps=new SpannableString("Step 1/3");
	  spanSteps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_green)), 5, 6, 0);
	  spanScan1=new SpannableString("Scan the QR code");
	  spanScan1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_green)), 0, 4, 0);
	  spanScan1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lightblue)), 9, 11, 0);
	  spanScan3=new SpannableString("or register manually");
	  
	  ClickableSpan myActivityLauncher = new ClickableSpan() {
	       public void onClick(View view) {
	         Intent intent=new Intent (getApplicationContext(),LoginController.class);
	         startActivity(intent);
	       }
	     };
	  spanScan3.setSpan(myActivityLauncher, 12, 20, 0);
	  //Scan_text_3.setMovementMethod(LinkMovementMethod.getInstance());
	  spanScan3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_green)), 12, 20, 0);
	  spanScan3.setSpan(new UnderlineSpan(), 12, 20, 0);
	  //steps.setText(spanSteps, BufferType.SPANNABLE);
	  //Scan_text_1.setText(spanScan1, BufferType.SPANNABLE);
	  //Scan_text_3.setText(spanScan3,BufferType.SPANNABLE);
*/
    }

    public void dialogBoxInUIthread(final String title, final String msg, final Activity context, final boolean completed) {
        context.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
                alertbox.setTitle(title);
                alertbox.setMessage(msg);
                alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (completed) {
                        } else {
                        }
                    }
                });
                alertbox.show();
            }
        });
    }

    /**
     * Check the application is it in Back ground
     *
     * @param context     the context
     * @param application Name the appName
     * @return the running Boolean
     */

    public Boolean isAppSentToBackground(final Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            // The first in the list of RunningTasks is always the foreground
            // task.
            RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity
                    .getPackageName();// get the top fore ground activity
            PackageManager pm = context.getPackageManager();
            PackageInfo foregroundAppPackageInfo = pm.getPackageInfo(
                    foregroundTaskPackageName, 0);

            String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo
                    .loadLabel(pm).toString();

            // Log.v(Constants.TAG, " foregroundTaskAppName "+foregroundTaskPackageName);
            //if (!foregroundTaskAppName.equals("University of Dammam")) {//context.getResources().getString(R.string.app_name)
            //if (!foregroundTaskAppName.equals(context.getResources().getString(R.string.))) {
            //    return true;
            // }
        } catch (Exception e) {
            Log.e(Globals.TAG, "" + e);
        }
        return false;
    }


    /**
     * Decodes image and scales it to reduce memory consumption
     *
     * @param File        stream to decode
     * @param application Name the appName
     * @return the running resized bitmap
     */
    private Bitmap ImageResizedbyFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            //use following for bitmap
            // BitmapFactory.decodeResource(res, resId, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * Decodes image and scales it to reduce memory consumption
     *
     * @param File stream to decode
     * @param
     * @return the running resized bitmap
     */
    private Bitmap ImageResizedbyResource(Resources res, int resId) {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        //use following for bitmap
        BitmapFactory.decodeResource(res, resId, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 70;

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        o2.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, o2);
    }

    /**
     * Decodes image and scales it to reduce memory consumption
     *
     * @param File stream to decode
     * @param
     * @return the running resized bitmap
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Decodes image and scales it to reduce memory consumption
     *
     * @param Resources
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return the running resized bitmap
     * how to call it decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100)
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, Bitmap bitmap,
                                                  int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, bitmap, options);
        Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // return BitmapFactory.decodeResource(res, bitmap, options);
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }

    /**
     * Compress image and scale to reduce memory consumption
     *
     * @param Resources
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return the running resized bitmap
     * how to call it decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100)
     */
    public Bitmap compressBitmap(Resources res, Bitmap bitmap,
                                 int reqWidth, int reqHeight) {
        Bitmap bm = UIHelper.getInstance().decodeSampledBitmapFromResource(res,
                bitmap, reqWidth, reqHeight);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return bm;
    }

    /**
     * Encode an image from byte array
     *
     * @param image bitmap want to convert in to Base64 String
     * @return encoded image screen.
     */
    public String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.v("LOOK", imageEncoded);
        return imageEncoded;
    }

    /**
     * decode an image from Base64 encoded String
     *
     * @param input keep the string Encoded with Base64
     * @return Bitmap return after decoding.
     */
    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * Get Intent for storing File on Media Storage
     *
     * @param
     * @return Intent return for Media Storage.
     */
    public Intent getCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    /**
     * Get Intent for storing File on Media Storage
     *
     * @param
     * @return Intent return for Media Storage.
     */
    public Intent getIntentToStoreFile(File filename, Context context) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext()
                    .getPackageName() + ".provider", new File(filename.toString())));
//			Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
        else
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filename.toString())));

        return cameraIntent;
    }

    /**
     * Get storage Location
     *
     * @param appStorage storage folder name.
     * @param fileName   file name.
     * @return Intent return for Media Storage.
     */
    public File getAppStorageLocation(String appStorage, String fileName) {
        return new File(Environment.getExternalStorageDirectory() + appStorage + fileName);

    }

    /**
     * Rotate Bitmap with provided angle
     *
     * @param bitmap
     * @param angle  provide angle for rotation.
     * @return Bitmap return bitmap after rotation.
     */
    public Bitmap rotateImage(Bitmap bitmap, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return retVal;
    }

    public Bitmap rotateImageByfindingAngle(Bitmap bitmap, File filePath) {
        ExifInterface ei = null;
        Bitmap bm = null;
        try {
            ei = new ExifInterface(filePath.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bm = UIHelper.getInstance().rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                bm = UIHelper.getInstance().rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                bm = UIHelper.getInstance().rotateImage(bitmap, 270);
                break;
            default:
                bm = bitmap;
                break;
        }
        return bm;
    }

    public void myTouchImplementation(ListView listView) {
        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    /**
     * Rotate Bitmap with provided angle
     *
     * @param bitmap
     * @param angle  provide angle for rotation.
     * @return Bitmap return bitmap after rotation.
     */

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}