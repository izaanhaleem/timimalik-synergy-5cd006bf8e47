package com.hisdu.SESCluster.constants;

import android.webkit.MimeTypeMap;

import com.hisdu.ses.utils.HttpClient;

import java.io.File;


/**
 * Created by Usman Kurd on 8/18/2015.
 */
public class Globals {
    public static String CNIC;
    public static int MENU_SELECTED_INDEX = 0;
    public static String TAG = "";
    public static String DOB_Format_3 = "dd MMM yyyy, HH:mm";
    public final static String CURRENT_TIME_FORMAT = "HHmm";
    public final static String REQUIRED_TIME_FORMAT = "hh:mm aa";
    public final static String LISTING_BOOLEAN = "listing_boolean";
    public static String APP_STORAGE_PATH = "/com.hisdu.DRS/images/";
    public static String TEMP_IMAGE_FILE = "/temp.jpg";
    public static File TEMP_IMG_PATH = new File("");
    public static final int IMAGE_HIGHT = (int) (487 / 2);
    public static final int IMAGE_WIDTH = (int) (559 / 2);
    public static String USER_NAME = "";
    public static String PASSWORD = "";
    public static String AppName = "";
    public static String DOB_FORMAT = "dd-MM-yyyy";
    public static String DOB_FORMAT_1 = "yyyy-MM-dd";
    public static String DOB_FORMAT_2 = "dd/MM/yyyy";
    public static String DOB_FORMAT_3 = "dd/MM/yyyy-hh:mm:ss a";
    public static String WEB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static String WEB_DATE_FORMAT1 = "yyyy-MM-dd hh:mm:ss a";//2017-09-08 12:07:00 AM/PM
    public static String WEB_DATE_FORMAT2 = "yyyy-MM-dd hh:mm:ss a";//2017-09-08 12:07:00
    public static String COMPLETE_DATE_FORMAT_1 = "dd MMM yyyy, EEEE hh:mm aa";
    public static String DATE_FORMAT = "yyyyMMddHHmmss";
    public static String DATE_FORMAT_1 = "MM/dd/yy HH:mm";
    public static String COMPLETE_DATE_FORMAT = "EEEE d MMMM yyyy";
    public static String MONTH_DATE_FORMAT = "dd MMM yyyy";
    public static String MONTH_DATE_FORMAT_1 = "MMMM yyyy";
    public static String TIME_DATE_FORMAT = "hh:mm aa";
    public static String TIME_FORMAT = "hh:mm:ss";
    public static String TIME_FORMAT_1 = "hh:mm";
    public static String MONTH_DATE_FORMAT_2 = "MMMM dd yyyy";
    public static String MOBILE_PATTERN = "[0-9][0-9]{9,14}";
    public static String imageUrl = "";
    public static String SIAs_No = "";
    public static String DAY = "";
    public static String AREA = "";
    public static String Count = "";
    public static String Location = "";
    public static int uc = 0;

    public static class Arguments {


        public static String CHILD_INFO = "Child_Info";
        public static String ZERO_TO_FOURTEEN_DAYS = "Zero_to_fourteen_days";
        public static final String SIX_TO_TEN_WEEKS = "six_to_fourteen_weeks";
        public static String TEN_TO_FOURTEEN_WEEKS = "ten_to_fourteen_weeks";
        public static String FIFTEEN_TO_SIXTEEN_MONTHS = "fifteen_to_sixteen_weeks";
        public static String FOURTEEN_TO_EIGHTEEN_WEEKS = "fourteen_to_eighteen_weeks";
        public static String NINE_TO_TEN_MONTHS = "nine_to_ten_months";
        public static String BOOSTER_DOSE = "BOOSTER_DOSE";

    }

    public static class jsonKeys {
        public static final String DATE_OF_VACCINATION = "date_of_vaccination";
        public static final String REMARKS = "remarks";
        ///////////////////////////json keys for child///////////////
        public static final String CHILD_NAME = "CHILD_NAME";
        public static final String FATHER_NAME = "FATHER_NAME";
        public static final String AGE = "AGE";
        public static final String CARD_NO = "CARD_NO";
        public static final String CARD_AVAILABLE = "CARD_AVAILABLE";
        public static final String VACCINATED = "VACCINATED";
        public static final String CHILD_INFO = "child_info";
        ///////////////////////Json keys for zero to forty days//////
        public static final String OPV_VACCINATED = "opv_vaccinated";
        public static final String OPV_TIMELY_VACCINATED = "opv_timely_vaccinated";
        public static final String BCG_VACCINATED = "bcg_vaccinated";
        public static final String HEPB_VACCINATED = "hepb_vaccinated";
        public static final String BCG_TIMELY = "bcg_timely_vaccinated";
        public static final String ZERO_TO_FORTY_DAYS = "zero_to_forty_days";
        /////////////////////Josn keys for Six to Ten Weeks
        public static final String PENTA_ONE_VACCINATED = "penta_one_vaccinated";
        public static final String PENTA_ONE_TIMELY_VACCINATED = "penta_one_timely_vaccinated";
        public static final String PCV_TEN_ONE_VACCINATED = "pcv_ten_one_vaccinated";
        public static final String PCV_TEN_ONE_TIMELY_VACCINATED = "pcv_ten_one_timely_vaccinated";
        public static final String OPV_ONE_VACCINATED = "opv_one_vaccinated";
        public static final String OPV_ONE_TIMELY_VACCINATED = "opv_one_timely_vaccinated";
        public static final String ROTA_VACCINE_ONE_VACCINATED = "rota_vaccine_one_vaccinated";
        public static final String ROTA_VACCINE_ONE_TIMELY_VACCINATED = "rota_vaccine_one_timely_vaccinated";
        public static final String SIX_TO_TEN_WEEKS = "six_to_ten_weeks";
        /////////////////////////////Json object Ten to Fourteen Weeks ///////////////
        public static final String PENTA_VACCINATED = "penta_vaccinated";
        public static final String PENTA_TIMELY_VACCINATED = "penta_timely_vaccinated";
        public static final String PCV_TEN_VACCINATED = "pcv_ten_vaccinated";
        public static final String PCV_TEN_TIMELY_VACCINATED = "pcv_ten_timely_vaccinated";
        public static final String ROTA_VACCINATED = "rota_vaccinated";
        public static final String ROTA_TIMELY_VACCINATED = "rota_timely_vaccinated";
        public static final String TEN_TO_FOURTEEN_WEEKS = "ten_to_fourteen_weeks";
        ///////////////////////////Json Object for Foruteen to eighteen weeks ///////
        public static final String IPV_VACCINATED = "ipv_vaccinated";
        public static final String IPV_TIMELY_VACCINATED = "ipv_timely_vaccinated";
        public static final String FOURTEEN_TO_EIGHTEEN_WEEKS = "fourteen_to_eighteen_weeks";
        //////////////////////////Json Object for Nine to Ten Months////////////////////
        public static final String MEASLES_ONE_VACCINATED = "measles_one_vaccinated";
        public static final String ipv2_VACCINATED = "ipv2_vaccinated";
        public static final String tcv_VACCINATED = "tcv_vaccinated";
        public static final String MEASLES_ONE_TIMELY_VACCINATED = "measles_one_timely_vaccinated";
        public static final String NINE_TO_TEN_MONTHS = "nine_to_ten_months";
        ///////////////////////Json Object for Fifteen To Sixteen Months
        public static final String MEASLES_TWO_VACCINATED = "measles_two_vaccinated";
        public static final String MEASLES_TWO_TIMELY_VACCINATED = "measles_two_timely_vaccinated";
        public static final String FIFTEEN_TO_SIXTEEN_MONTHS = "fifteen_to_sixteen_months";
        public static final String Eighteen_Months = "eighteen_months";

        ///////////////////////Json Object for Fifteen To BoosterDose
        public static final String dpt_vaccinated = "dpt_vaccinated";
        public static final String remarks = "remarks";
        //////////////Json object House Info
        public static final String HOUSE_NO = "house_no";
        public static final String HouseNo = "HouseNo";
        public static final String Front = "Front";
        public static final String Back = "Back";
        public static final String Latitude = "Latitude";
        public static final String Longitude = "Longitude";
        public static final String ProvinceId = "ProvinceId";

        public static final String AGE_MATCHED_VACCINATED_CHILDREN = "age_matched_vaccinated_children";
        public static final String PARTIALLY_VACCINATED_CHILDREN = "partially_vaccinated_children";
        public static final String TEHSIL = "TEHSIL";
        public static final String UC = "UC";
        public static final String LocationCode = "LocationCode";
        public static final String Division = "DIVISION";
        public static final String Province = "PROVINCE";
        public static final String User_ID = "User_ID";
        public static final String Settlement = "settlement";
        public static final String AREA_NO = "AREA_NO";
        public static final String CREATED_DATE = "CREATED_DATE";
        public static final String MODIFIED_DATE = "MODIFIED_DATE";
        public static final String MOBILE = "MOBILE";
        public static final String NO_OF_CHILDREN = "NO_OF_CHILDREN";
        public static final String CLUSTER_NO = "CLUSTER_NO";
        public static final String INSPECTION_DATE = "INSPECTION_DATE";
        public static final String DATE_OF_VACCINATION_IPV = "DATE_OF_VACCINATION_IPV";
        public static final String OPV_VACCINATION_DATE = "OPV_VACCINATION_DATE";
        public static final String BCG_VACCINATION_DATE = "BCG_VACCINATION_DATE";
        public static final String PENTA_ONE_VACCINATION_DATE = "PENTA_ONE_VACCINATION_DATE";
        public static final String PCV_TEN_ONE_VACCINATION_DATE = "PCV_TEN_ONE_VACCINATION_DATE";
        public static final String OPV_ONE_VACCINATION_DATE = "OPV_ONE_VACCINATION_DATE";
        public static final String ROTA_ONE_VACCINATION_DATE = "ROTA_ONE_VACCINATION_DATE";
        public static final String PENTA_VACCINATION_DATE = "PENTA_VACCINATION_DATE";
        public static final String PCV_TEN_VACCINATION_DATE = "PCV_TEN_VACCINATION_DATE";
        public static final String OPV_DATE_VACCINATION_DATE = "OPV_DATE_VACCINATION_DATE";
        public static final String ROTA_VACCINATION_DATE = "ROTA_VACCINATION_DATE";
        public static final String IPV_VACCINATION_DATE = "IPV_VACCINATION_DATE";
        public static final String MEASLES_VACCINATION_DATE = "MEASLES_VACCINATION_DATE";
        public static final String MEASLES_TWO_VACCINATION_DATE = "MEASLES_TWO_VACCINATION_DATE";

        public static String UN_VACCINATED_CHILDREN = "un_vaccinated_children";
        public static String LAT = "lat";
        public static String LNG = "lng";
        public static String DISTRICT = "district";
        public static String SIA_No = "sia_no";
        public static String DAY = "day";
        public static String APP_VERSION = "AppVersion";

    }

    public static class PrefConstants {
        public static String APP_PREFS = "attendance_system_prefs";
    }

    public static class Locale {
        public static String arLocale = "ar";
        public static String enLocale = "en";
    }

    public static class RequestCodes {

        public static int Add_Child_RequestCode = 10;
        public static int Zero_To_40_days = 11;
        public static int login_request = 101;
        public static int Record_Save = 201;
        public static int Six_to_Ten_Weeks = 12;
        public static int Ten_to_Fourteen_Weeks = 13;
        public static int Fourteen_to_Eighteen_Weeks = 14;
        public static int Nine_to_Ten_months = 15;
        public static int Fifteen_to_Sixteen_Months = 16;
        public static int BOOSTER_DOSE = 17;

        public static int Version_code = 501;
    }

    public static class Params {
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

    private static class StatusType {
    }


    public static class ControlFlags {
        public static boolean IS_GREY_SCALE = false;
    }


    public static class APIs {
        //       public static String BASE_URL = "http://localhost/api/user/";
//       public static String BASE_URL = "http://172.16.5.15:8055/api/user/";
        public static String BASE_URL = HttpClient.BASE_URL;
        public static String login = "UserLogin";
        public static String save = "ClusterMaster/SaveCluster";

        public static class ParamKeys {

        }

        public static class ParamValues {
        }

        public static class RequestCode {
        }

    }

    public static LockingObject lock = new LockingObject();

    public static class LockingObject {
    }


}

