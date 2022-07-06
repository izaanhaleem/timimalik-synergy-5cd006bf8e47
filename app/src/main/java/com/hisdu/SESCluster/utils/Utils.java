package com.hisdu.SESCluster.utils;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Location;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.constants.Globals;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author muhammad
 */
public class Utils {
    private static String mFirstTime = null;

    enum ClassMember {CONSTRUCTOR, FIELD, METHOD, CLASS, ALL}

    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
    public static void changeLanguage(String language, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("language", language);
        editor.commit();

        Resources res = ctx.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language.toLowerCase());
        res.updateConfiguration(conf, dm);

    }

    public static void changeColorMode(String color, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("color", color);
        editor.commit();


    }

    public static void addPreference(String key, String value, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static boolean isDeviceConnected(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    public static String getPreferenceByKey(String key, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString(key, null);


    }


    public static <T> T entityConvert(Class<T> entity, Cursor c) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        /*Create an entity object*/
        T a = entity.newInstance();

        for (String columnString : c.getColumnNames()) {
            Method m = null;
            /*hit property setters*/
            if (c.getType((c.getColumnIndex(columnString))) == Cursor.FIELD_TYPE_INTEGER) {
                m = entity.getDeclaredMethod("set" + columnString, new Class[]{Integer.class});
                m.invoke(a, new Object[]{c.getInt(c.getColumnIndex(columnString))});
            } else if (c.getType((c.getColumnIndex(columnString))) == Cursor.FIELD_TYPE_STRING) {
                m = entity.getDeclaredMethod("set" + columnString, new Class[]{String.class});
                m.invoke(a, new Object[]{c.getString(c.getColumnIndex(columnString))});
            }

        }


        return a;
    }


    public static void deleteDirectory(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    deleteDirectory(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static byte[] convertImageToBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    /**
     * Defaults to english
     */
    public static String getLanguage(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString("language", "en");
        // return "en";
//		return "en";

    }

    /**
     * Defaults to color
     */
    public static String getColorMode(Context ctx) {
        if (ctx == null)
            return "colorful";
        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString("color", "colorful");

    }


    public static void toggleLanguage(Context ctx) {
        if (Utils.getLanguage(ctx).equals("ar"))
            Utils.changeLanguage("en", ctx);
        else
            Utils.changeLanguage("ar", ctx);
    }

    public static void toggleColorMode(Context ctx) {
        if (Utils.getColorMode(ctx).equals("colorful"))
            Utils.changeColorMode("greyscale", ctx);
        else
            Utils.changeLanguage("colorful", ctx);
    }

    public static <T> String convertObjectToString(T o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }

    public static <T> Object convertStringToObject(Class<T> object, String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, object);
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static Date getDateFromString(String dateString, String strformat, String locale, String dateType, Date dateToday) {
        String format = strformat;
        if (format == null)
            format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, new Locale(locale));
        try {
            Log.d("Date", simpleDateFormat.parse(dateString) + "");
            Date date = simpleDateFormat.parse(dateString);
            if (dateToday.equals(date)) {
                if (dateType.equalsIgnoreCase("start_date"))
                    date.setTime(date.getTime() - (60 * 60 * 1000));
                else
                    date.setTime(date.getTime() + (60 * 60 * 1000));
            }
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromCalendar(Date date, String requiredFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredFormat, new Locale("en"));
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getDateFromString(String dateString, String strformat) {
        String format = strformat;
        if (format == null)
            format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromStringWithLocale(String dateString, String strformat) {
        String format = strformat;
        if (format == null)
            format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateStringFromString(String dateString, String strformat, String requiredFormat) {
        String format = strformat;
        if (format == null)
            format = Globals.WEB_DATE_FORMAT;
        String reqFormat = requiredFormat;
        if (requiredFormat == null)
            reqFormat = Globals.MONTH_DATE_FORMAT;

        Date d = getDateFromString(dateString, strformat);
        if (d != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(reqFormat);
            try {
                return simpleDateFormat.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getDate(String localTimeZoneDate, String timezone, String dateFormat) {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setCalendar(calendar);
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            Date value = formatter.parse(localTimeZoneDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            localTimeZoneDate = dateFormatter.format(value);

            //Log.d("OurDate", OurDate);
        } catch (Exception e) {
            localTimeZoneDate = "00-00-0000 00:00";
        }
        return localTimeZoneDate;
    }

    public static String convertLocalTimeToUTC(String timeZoneId, String p_localDateTime, String format) {

        String lv_dateFormateInUTC = "";// Will hold the final converted date
        Date lv_localDate = null;
        String lv_localTimeZone = "";
        SimpleDateFormat lv_formatter;
        SimpleDateFormat lv_parser;


        lv_localTimeZone = timeZoneId;

        // create a new Date object using the timezone of the specified city
        lv_parser = new SimpleDateFormat(format);
        lv_parser.setTimeZone(TimeZone.getTimeZone(lv_localTimeZone));
        try {
            lv_localDate = lv_parser.parse(p_localDateTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Set output format prints "2007/10/25  18:35:07 EDT(-0400)"

        lv_formatter = new SimpleDateFormat(format);
        lv_formatter.setTimeZone(TimeZone.getTimeZone(lv_localTimeZone));

        // Convert the date from the local timezone to UTC timezone
        lv_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        lv_dateFormateInUTC = lv_formatter.format(lv_localDate);

        return lv_dateFormateInUTC;
    }

    public static Date localToGMT(String date, String format) {
        SimpleDateFormat parseFormat = new SimpleDateFormat(format);
        Date parsedDate = new Date();
        try {
            parsedDate = parseFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.setTimeInMillis(Long.parseLong(date));
        SimpleDateFormat sdf = new SimpleDateFormat(Globals.DATE_FORMAT_1);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date finalDate = new Date(sdf.format(parsedDate));
        return finalDate;
    }

    public static String getDateStringFromString(String dateString, String strformat, String requiredFormat, String locale) {
        String format = strformat;
        if (format == null)
            format = Globals.WEB_DATE_FORMAT;
        String reqFormat = requiredFormat;
        if (requiredFormat == null)
            reqFormat = Globals.MONTH_DATE_FORMAT;

        Date d = getDateFromString(dateString, strformat, locale, "", new Date());
        if (d != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(reqFormat, new Locale(locale));
            try {
                return simpleDateFormat.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getTimeZonedDate(Date date, String format) {
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        return gmtFormat.format(date);
    }

    public static int compareWithoutTime(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
            return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
    }

    /*
     * Validation methods
     * */

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPassword(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() > 6;//&& target.toString().matches("(?=.*[\\d])");
    }

    public final static boolean isValidMobileNumber(CharSequence target) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(Globals.MOBILE_PATTERN);
        matcher = pattern.matcher(target);
        return matcher.matches();
    }

    public static float getDistance(double startLat, double startLng, double endLat, double endLng) {
        float[] results = new float[1];
        Location.distanceBetween(startLat, startLng, endLat, endLng, results);

        if (results.length > 0) {
            return results[0] / 1000;
        } else
            return 0;
    }

    public static String getDistanceInKMString(double startLat, double startLng, double endLat, double endLng) {
        float distance = getDistance(startLat, startLng, endLat, endLng);

        return String.format("%.1f km", distance);
    }

    public static float getDistanceInKM(double startLat, double startLng, double endLat, double endLng) {
        float distance = getDistance(startLat, startLng, endLat, endLng);
        return distance;
    }

    public static int getNumberOfDays(Date startDate, Date endDate) {

        Calendar sDate = toCalendar(startDate.getTime());
        Calendar eDate = toCalendar(endDate.getTime());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);

        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    private static Calendar toCalendar(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    public static Bitmap flipImage(Context context, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return flipImage(bitmap);
    }

    public static Bitmap flipImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        // x = x * -1
        matrix.preScale(-1.0f, 1.0f);
        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static class FontUtils {

        private static Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();

        public static Typeface getFonts(Context context, String name) {
            Typeface typeface = TYPEFACE.get(name);
            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"
                        + name);
                TYPEFACE.put(name, typeface);
            }
            return typeface;
        }

        public static void applyFonts(Context context, String fontName, TextView... textViews) {
            Typeface tf = getFonts(context, fontName);
            for (TextView tv : textViews) {
                tv.setTypeface(tf);
            }
        }

        public static void applyFonts(Context context, String fontName, final View v) {
            Typeface tf = getFonts(context, fontName);
            applyFonts(tf, v);
        }

        public static void applyFonts(Typeface fontToSet, final View v) {
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);
                        applyFonts(fontToSet, child);
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setTypeface(fontToSet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // ignore
            }
        }


        public static void applyFontSizes(float textSize, TextView... textViews) {
//            textSize = 1.3f;
            for (TextView tv : textViews) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * textSize);
            }
        }


        public static void applyFontSizes(final View v, float textSize) {
//            textSize = 1.3f;
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);
                        applyFontSizes(child, textSize);
                    }
                } else if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * textSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // ignore
            }
        }

    }

    public static void navigationToGeoPosition(Context context, double currentLat, double currentLong, double destLat, double destLong) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + currentLat + "," + currentLong + "&daddr=" + destLat + "," + destLong));

        context.startActivity(intent);
    }


    public static void animateCircularBackground(Context context, final View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.setDuration(500);

        anim.start();
    }

    public static void animateReverseCircularBackground(Context context, final View view, final int deltaY) {

        view.post(new Runnable() {
            @Override
            public void run() {


                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (int) ((view.getTop() + view.getBottom()) / 2.5f);

                // get the final radius for the clipping circle
                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                Animator anim =
                        null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                }

                // make the view visible and start the animation
                view.setVisibility(View.VISIBLE);
                anim.setDuration(500);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());

                anim.start();
            }
        });
    }

    public static String getStorageDir() {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/attendance";

        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        return path;
    }

    public static String getLocalPath(String path) {
        return getStorageDir() + "/" + getFileName(path);
    }

    public static String getFileName(String path) {
        int pos = path.lastIndexOf("/") + 1;
        return path.substring(pos);
    }

	/*public static void openLink(Context context, String url){
        if (!url.startsWith("http://") && !url.startsWith("https://"))
			url = "http://" + url;
		Intent webIntent = new Intent(context,WebViewActivity.class);
		webIntent.putExtra(Globals.Arguments.ARGS_URL,url);
		context.startActivity(webIntent);
	}*/


    public static void shareImage(Bitmap bitmap, String title, Context context) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("*/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "shareimage.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/shareimage.jpg"));
        share.putExtra(Intent.EXTRA_TEXT, title);
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }

    public static void shareText(String title, Context context) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, title);
        context.startActivity(Intent.createChooser(share, "Share Detail"));
    }

    public static void sharePdf(File file, Context context) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(Intent.createChooser(share, "Share Pdf"));
    }

    public static void launchApp(Context context, String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent == null) {
            launchIntent = new Intent(Intent.ACTION_VIEW);
            launchIntent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(launchIntent);
        } else {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        }

    }


    public static boolean newsFirstTime(Context ctx) {
        return !Utils.getNewsStatus(ctx).equalsIgnoreCase("false");

    }

    public static String getNewsStatus(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString("application_news", "false");

    }

    public static void changeNewsStatus(String notification, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("application_news", notification);
        editor.commit();
    }

    public static boolean newsEnFirstTime(Context ctx) {
        return !Utils.getEnNewsStatus(ctx).equalsIgnoreCase("false");

    }

    public static String getEnNewsStatus(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString("application_news", "false");

    }

    public static void changeEnNewsStatus(String notification, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("application_en_news", notification);
        editor.commit();
    }

    public static boolean publicationsFirstTime(Context ctx) {
        return !Utils.getPublicationsStatus(ctx).equalsIgnoreCase("false");

    }

    public static String getPublicationsStatus(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        return settings.getString("application_publications", "false");

    }

    public static void changePublicationsStatus(String notification, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("attendance", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("application_publications", notification);
        editor.commit();
    }


    public static void animateBounce(final View v, int delay) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(v, "scaleX", 0f, 1.2f).setDuration(150)
        );
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(v, "scaleY", 0f, 1.2f).setDuration(150)
        );

        AnimatorSet animatorSet1 = new AnimatorSet();

        animatorSet1.playTogether(
                ObjectAnimator.ofFloat(v, "scaleX", 1.2f, 1.0f).setDuration(150)
        );
        animatorSet1.playTogether(
                ObjectAnimator.ofFloat(v, "scaleY", 1.2f, 1f).setDuration(150)
        );

        AnimatorSet bounce = new AnimatorSet();
        bounce.playSequentially(animatorSet, animatorSet1);
        bounce.setStartDelay(delay);
        bounce.start();
    }

    public static String convertStringDateFormatFromOneToOther(String dateStr, String original, String required, String timeZone) {
        DateFormat originalFormat = new SimpleDateFormat(original);
        DateFormat targetFormat = new SimpleDateFormat(required);
        if (timeZone.equalsIgnoreCase("")) {
            targetFormat.setTimeZone(TimeZone.getDefault());
            originalFormat.setTimeZone(TimeZone.getDefault());
        } else {
            targetFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            originalFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }

//        Date date = new Date();
        try {
            Date date = originalFormat.parse(dateStr);
            dateStr = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        return dateStr;
    }

    public static String getMimeType(String url) {
        String type = null;
        url = url.replace(" ", "%20");
        String extension = fileExt(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String fileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    public static String getStringFromTime(Date time) {
        SimpleDateFormat outputFmt = new SimpleDateFormat(Globals.DATE_FORMAT);
        return outputFmt.format(time);
    }

    public static void expandWithAnimator(final View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, view.getMeasuredHeight(), view);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                view.setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mAnimator.start();
    }

    public static void collapseWithAnimator(final View view) {
        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, view);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private static ValueAnimator slideAnimator(int start, int end, final View view) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static float getTextSize(Context context) {

        SharedPreferences settings = context.getSharedPreferences("attendance", 0);
        return settings.getFloat("value", 1.0f);

    }

    public static Bitmap toGrayscale(int resource, Context ctx) {

        Bitmap bmpOriginal = BitmapFactory.decodeResource(ctx.getResources(),
                resource);
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


    public static int getGreyVersionForColor(int originalColor) {
        //Color color = Color. new Color(originalColor);
        float red = Color.red(originalColor);
        float green = Color.green(originalColor);
        float blue = Color.blue(originalColor);
        int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
        int greyColor = Color.argb((int) Color.alpha(originalColor), gray, gray, gray);
        return greyColor;
    }

    public static Bitmap toGrayscale(Context context, int resource) {

        Bitmap bmpOriginal = BitmapFactory.decodeResource(context.getResources(),
                resource);
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static int changeToGrayScale(Context ctx) {
        return ctx.getResources().getColor(R.color.color_gray);
    }


    public static void applyFontSize(Context context, TextView... textViews) {
        float textSize = Utils.getTextSize(context);
        FontUtils.applyFontSizes(textSize, textViews);
    }

    public static void storeUserAccountId(String response, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("hyper_reach", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_info", response);
        editor.commit();

    }

    public static String getUserInfo(Context context) {

        SharedPreferences settings = context.getSharedPreferences("hyper_reach", 0);
        return settings.getString("user_info", "");

    }

    public static void storeUserCred(String response, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("hyper_reach", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_cred", response);
        editor.commit();

    }

    public static String getUserCred(Context context) {

        SharedPreferences settings = context.getSharedPreferences("hyper_reach", 0);
        return settings.getString("user_cred", "");

    }

    public static void storeUserAccountDetail(String response, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("hyper_reach", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("account_detail", response);
        editor.commit();

    }

    public static String getUserAccountDetail(Context context) {

        SharedPreferences settings = context.getSharedPreferences("hyper_reach", 0);
        return settings.getString("account_detail", "");

    }

    public static Date getDateWithFormatFromString(String dateString, String strformat, String locale) {
        String format = strformat;
        if (format == null)
            format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, new Locale(locale));
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressLint("NewApi")
    public static void animateBounceFromBottom(final View v, long delay) {
        v.setTranslationY(1090);
        v.animate().translationY(-50).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(500).setStartDelay(delay)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.animate().translationY(0).setDuration(200)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setStartDelay(0)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    @SuppressLint("NewApi")
    public static void animateBounceFromLeft(final View v, int duration, long delay) {
        v.animate().translationX(20).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration).setStartDelay(delay)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.animate().translationX(0).setDuration(200)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setStartDelay(0)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    @SuppressLint("NewApi")
    public static void animateBounceFromRight(final View v, int duration, long delay) {
        v.animate().translationX(-20).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration).setStartDelay(delay)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.animate().translationX(0).setDuration(200)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setStartDelay(0)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    public static void animateWithAlpha(final View v, long duration, int delay, float alpha) {
        AnimatorSet animatorSet = new AnimatorSet();
        v.setAlpha(0f);
        animatorSet.playTogether(ObjectAnimator.ofFloat(v, View.ALPHA, 0, alpha));
        animatorSet.setDuration(duration).setStartDelay(delay);
        // animatorSet.setDuration(800);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.start();

    }

    @SuppressLint("NewApi")
    public static void animateBounceFromTop(final View v, int duration, long delay) {
        v.animate().translationY(30).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration).setStartDelay(delay)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.animate().translationY(0).setDuration(200)
                                .setInterpolator(new DecelerateInterpolator())
                                .setStartDelay(0)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d("attendance", "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            Log.d("attendance", "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


    public static void changeStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    public static String getMonthName(int month) {
        switch (month + 1) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }

        return "";
    }

    public static Bitmap getImageFromGallery(Context context, Uri selectedImageUri) throws IOException {
        // Get the Image from data
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        // Get the cursor
        Cursor cursor = context.getContentResolver().query(selectedImageUri,
                filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        // Set the Image in ImageView after decoding the String
        return rotateBitmap(BitmapFactory.decodeFile(filePath), filePath);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, String filePath) throws IOException {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
            bitmap = rotate(bitmap, 90);
        } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
            bitmap = rotate(bitmap, 270);
        } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
            bitmap = rotate(bitmap, 180);
        } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("1")) {
            bitmap = rotate(bitmap, 0);
        }
        return bitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {


        Matrix mtx = new Matrix();
        mtx.postRotate(degree);
//        mtx.setRotate(degree);

        return getResizedBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true), bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    }

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
//        resizedBitmap.recycle();
        return resizedBitmap;
    }


    public static class KeyBoardHandler {

        public static void hideSoftKeyboard(Activity activity) {
            if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }

        public static void showSoftKeyboard(Activity activity) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static boolean isJSONString(String str) {
        try {
            new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getStringFromTimeToDisplay(Date time, String format) {
        SimpleDateFormat outputFmt = new SimpleDateFormat(format);
//        outputFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return outputFmt.format(time);
    }

    public static boolean checkIfDateIsToday(int year, int month, int dayOfMonth, int startYear, int
            startMonth, int startDay, boolean endDate) {
        Calendar startDateCalender = Calendar.getInstance();
        Calendar endDateCalender = Calendar.getInstance();

        startDateCalender.set(startYear, startMonth, startDay);
        endDateCalender.set(year, month, dayOfMonth);
        if (endDate) {
            if (endDateCalender.before(startDateCalender)) {
                System.err.println("COLUMN_DATE specified [" + endDateCalender + "] is before today [" + startDateCalender + "]");
                return false;
            } else {
                System.err.println("COLUMN_DATE specified [" + endDateCalender + "] is NOT before today [" + startDateCalender + "]");
                return true;
            }
        } else {
            if (startDateCalender.before(endDateCalender) || startDateCalender.equals(endDateCalender)) {
                System.err.println("COLUMN_DATE specified [" + endDateCalender + "] is before today [" + startDateCalender + "]");
                return true;
            } else {
                System.err.println("COLUMN_DATE specified [" + endDateCalender + "] is NOT before today [" + startDateCalender + "]");
                return false;
            }
        }
    }


    public static String getStringSharedPreferences(Context context, String fileName, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }


    public static boolean saveToSharedPreferences(Context context, String fileName, String key, String data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, data);
        return editor.commit();
    }

    public static boolean isApplicationFirstTime(Context ctx) {
        return !Utils.getApplicationStatus(ctx) == (false);

    }

    public static boolean getApplicationStatus(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("hyper_reach", 0);
        return settings.getBoolean("application", false);

    }

    public static void changeApplicationStatus(boolean status, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("hyper_reach", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("application", status);
        editor.apply();

    }

    public static boolean isLoginFirstTime(Context ctx) {
        return !!Utils.getApplicationStatus(ctx);

    }

    public static boolean isLoggedIn(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("login", 0);
        return settings.getBoolean("application", false);

    }

    public static void changeLoginStatus(boolean status, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("application", status);
        editor.apply();

    }

    public static String getDateStringFromDate(Date date, String requiredFormat, String locale) {

        String reqFormat = requiredFormat;
        if (requiredFormat == null)
            reqFormat = Globals.MONTH_DATE_FORMAT;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(reqFormat, new Locale(locale));
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String timeConversion(String time, String currentFormat, String requiredFormat) {
//        String time = "530pm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentFormat, Locale.ENGLISH);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(requiredFormat, Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(time);

            time = dateFormat2.format(date);
            time = time.replace(".", "");
            Log.e("Time", time);
        } catch (ParseException e) {
        }

        return time;
    }

    public static boolean isDateBetweenRange(String strCurrentDate, String strNextDate, String strDateFormat, boolean isEnd) {
        Date currentDate = new Date();
        Date nextDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat, Locale.ENGLISH);
        try {
            currentDate = dateFormat.parse(strCurrentDate);
            nextDate = dateFormat.parse(strNextDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(currentDate);
        Calendar calNext = Calendar.getInstance();
        calNext.setTime(nextDate);
        if (!isEnd) {
            if (calCurrent.before(calNext) || calCurrent.equals(calNext))
                return true;
            else return false;
        } else {
            if (calCurrent.after(calNext) || calCurrent.equals(calNext))
                return false;
            else return true;
        }
    }

    public static String addDaysToString(String currentDate, int noOfDaysToAdd, String strDateFormat) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat, Locale.ENGLISH);
        try {
            date = dateFormat.parse(currentDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        return dateFormat.format(calendar.getTime());
    }

    public static long getTimeDifferenceInMinutes(Date startDate, Date endDate) {
        long diffInMilli = endDate.getTime() - startDate.getTime();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMilli);
        long sec = diffInSec % 60;
        diffInSec /= 60;
        return diffInSec % 60;// Time in minutes;
    }

    public static String feetsToMile(double distance, boolean isInMile) {
        if (!isInMile) {
            if (distance < 5280)
                return String.format(Locale.ENGLISH, "%.1f", distance) + " ft ";
            else if (distance == 5280)
                return 1.0 + " mi ";
//        distance = distance*0.00019;
//        String result = String.format("%.1f", distance);
            else return (String.format(Locale.ENGLISH, "%.1f", distance * 0.000189394)) + " mi ";
        } else return (String.format(Locale.ENGLISH, "%.1f", distance * 0.000189394)) + " mi ";
    }

    public static double getMiles(double distance) {
        return (distance * 5280);
    }

    public static double getMilesFromMeters(double distance) {
        return (distance * 1609.34);
    }

    public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText, double screenSize, Context context) {
        Resources resources = gContext.getResources();
//        float scale = resources.getDisplayMetrics().density;
        float density = context.getResources().getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (density >= 3.5)
            paint.setTextSize((int) (9 * density));
        else
            paint.setTextSize((int) (8 * density));
        paint.setShadowLayer(1f, 0f, 1f, Color.BLACK);

        Rect bounds = new Rect();

        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;
        if (density >= 3.5)
            canvas.drawText(gText, x, y + 50, paint);
        else if (density >= 3.0 && density < 3.5)
            canvas.drawText(gText, x, y + 42, paint);
        else if (density >= 2.0)
            canvas.drawText(gText, x, y + 28, paint);

        return bitmap;
    }

    public static double screenSizeInInches(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double wi = (double) width / dm.xdpi;
        double hi = (double) height / dm.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        return Math.sqrt(x + y);
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences("request_permission", 0);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences("request_permission", 0).getBoolean(permission, true);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static long bytesAvailable(File f) {
        StatFs stat = new StatFs(f.getPath());
        long bytesAvailable = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            bytesAvailable = (long) stat.getBlockSizeLong() * (long) stat.getAvailableBlocksLong();
        else
            bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        return bytesAvailable;
    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        secondsString = seconds < 10 ? "0" + seconds : "" + seconds;
        return finalTimerString + minutes + ":" + secondsString;
    }


    public static int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }

    ///////////////////Mother Profile Preferences///////////////
    public static void idPersonalInfoMother(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("personal_info_m", count);
        editor.apply();
    }

    public static int getIdPersonalInfoMother(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("personal_info_m", 0);
    }

    public static void idDemoInfoMother(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("demo_info_mother", count);
        editor.apply();
    }

    public static int getIDDemoInfoMother(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("demo_info_mother", 0);
    }

    public static void idPatientHistoryMother(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("patient_history_mother", count);
        editor.apply();
    }

    public static int getPatientHistoryMother(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("patient_history_mother", 0);
    }

    public static void idVitalsMother(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("vitals_mother", count);
        editor.apply();
    }

    public static int getVitalsMothers(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("vitals_mother", 0);
    }

    //////////////Child Profile Preferences///////////////////////

    public static void idPersonalInfoChild(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("personal_info_c", count);
        editor.apply();
    }

    public static int getIdPersonalInfoChild(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("personal_info_c", 0);
    }

    public static void idDemoInfoChild(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("demo_info_child", count);
        editor.apply();
    }

    public static int getIDDemoInfoChild(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("demo_info_child", 0);
    }

    public static void idPatientHistoryChild(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("patient_history_child", count);
        editor.apply();
    }

    public static int getPatientHistoryChild(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("patient_history_child", 0);
    }

    public static void idVitalsChild(int count, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("vitals_child", count);
        editor.apply();
    }

    public static int getVitalsChild(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("vitals_child", 0);
    }

    public static void saveUserId(int id, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("user_id", id);
        editor.apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("user_id", 0);
    }

    public static void saveFacilityId(int id, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("facility_id", id);
        editor.apply();
    }

    public static int getFacilityId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("facility_id", 0);
    }

    public static void saveInfo(String info, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_info", info);
        editor.apply();
    }

    public static String getInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("user_info", "");
    }

    public static boolean isFacilityDataFirstTIme(Context ctx) {
        return !Utils.getFacilityStatus(ctx) == false;

    }

    private static boolean getFacilityStatus(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("CMC", 0);
        return settings.getBoolean("facility", false);

    }

    public static void changeFacilityStatus(boolean status, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences("CMC", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("facility", status);
        editor.apply();
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void saveUnSentRecordId(int id, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("unsent_record_id", id);
        editor.apply();
    }

    public static int getUnSentRecordId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("unsent_record_id", 0);
    }

    public static void saveUserProfileInfo(String userProfileInfo, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userProfileInfo", userProfileInfo);
        editor.apply();
    }

    public static String getUserProfileInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("userProfileInfo", "");
    }

    public static void saveClusterOne(int clusterOne, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("cluster_one", clusterOne);
        editor.apply();
    }

    public static int getClusterOneInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("cluster_one", 0);
    }

    public static void saveClusterTwo(int clusterTwo, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("cluster_two", clusterTwo);
        editor.apply();
    }

    public static int getClusterTwoInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("cluster_two", 0);
    }

    public static void saveClusterThree(int clusterThree, Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("cluster_three", clusterThree);
        editor.apply();
    }

    public static int getClusterThreeInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("cluster_three", 0);
    }

    public static int getClusterId(Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("cluster", 0);

    }

    public static void saveClusterId(int messageID, Context ctx) {

        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("cluster", messageID);
        editor.apply();
    }

    public static void saveArea(String area, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("area", area);
        editor.apply();
    }

    public static String getArea(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("area", "");
    }

    public static void saveSiaTeamNo(String sia_team_no, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("sia_team_no", sia_team_no);
        editor.apply();
    }

    public static String getSiaTeamNo(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("sia_team_no", "");
    }

    public static void saveSelectDay(int day, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("select_day", day);
        editor.apply();
    }

    public static int getSelectDay(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getInt("select_day", -1);
    }

    public static void saveDistrict(String district, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("district", district);
        editor.apply();
    }public static String getDistrict(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("district", "");
    }
    public static void saveDistrictID(String districtID, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("districtID", districtID);
        editor.apply();
    }


    public static String getDistrictID(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("districtID", "");
    }
    public static void saveDivision(String division, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("division", division);
        editor.apply();
    }

    public static String getDivision(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("division", "");
    }
    public static void saveDivisionID(String divisionID, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("divisionID", divisionID);
        editor.apply();
    }
    public static String getDivisionID(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("divisionID", "");
    }

    public static void saveTown(String town, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("town", town);
        editor.apply();
    }

    public static String getTown(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("town", "");
    }
    public static void saveTownID(String townID, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("townID", townID);
        editor.apply();
    }public static String getTownID(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("townID", "");
    }

    public static void saveUC(String uc, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("uc", uc);
        editor.apply();
    }

    public static String getUC(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("uc", "");
    }


    public static void saveUCID(String uc_id, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("uc_id", uc_id);
        editor.apply();
    }

    public static String getUCID(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
        return settings.getString("uc_id", "");
    }

//    public static void saveBasicInfo(int uc_id, Context ctx) {
//        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("uc_id", uc_id);
//        editor.apply();
//    }
//
//    public static int getBasicInfo(Context ctx) {
//        SharedPreferences settings = ctx.getSharedPreferences(Globals.AppName, 0);
//        return settings.get("uc_id", 0);
//    }
}
