package com.hisdu.SESCluster.database;


public class DatabaseConfig {

    // Database Properties
    static final String LOCAL_DATABASE_NAME = "SES.db";
    static final int DATABASE_VERSION = 1;

    public static class CNICTable {

        public static final String TABLE_NAME = "cnic";
        public static final String COLUMN_ID = "CID";
        public static final String CNIC_NO = "CNIC_NO";
        public static final String Id = "id";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + CNICTable.TABLE_NAME + " (" + CNICTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + CNICTable.CNIC_NO + " TEXT ,  "
                + CNICTable.Id + " TEXT  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + CNICTable.TABLE_NAME + " ORDER BY " + CNICTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class DemoInfoMother {
        static final String TABLE_NAME = "DemoInfoMother";
        static final String COLUMN_ID = "CID";
        static final String CNIC_NO = "cnicNo";
        static final String Id = "id";
        static final String REG_NO = "regNo";
        static final String MR_NO = "mrNo";
        static final String MOTHER_NAME = "motherName";
        static final String DOB = "dob";
        static final String AGE = "age";
        static final String UNION_COUNCIL = "unionCouncil";
        static final String ADDRESS = "address";
        static final String HEGIHT = "height";
        static final String BLOOD_GROUP = "bloodGroup";
        static final String REFERRED_BY = "referredBy";
        static final String APPROXIMATE = "approximate";
        static final String NOT_KNOWN = "notKnown";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DemoInfoMother.TABLE_NAME + " (" + DemoInfoMother.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + DemoInfoMother.CNIC_NO + " TEXT ,  "
                + DemoInfoMother.REG_NO + " TEXT ,  "
                + DemoInfoMother.MR_NO + " TEXT ,  "
                + DemoInfoMother.MOTHER_NAME + " TEXT ,  "
                + DemoInfoMother.DOB + " TEXT ,  "
                + DemoInfoMother.AGE + " TEXT ,  "
                + DemoInfoMother.UNION_COUNCIL + " TEXT ,  "
                + DemoInfoMother.ADDRESS + " TEXT ,  "
                + DemoInfoMother.HEGIHT + " TEXT ,  "
                + DemoInfoMother.BLOOD_GROUP + " TEXT ,  "
                + DemoInfoMother.REFERRED_BY + " TEXT ,  "
                + DemoInfoMother.APPROXIMATE + " TEXT ,  "
                + DemoInfoMother.NOT_KNOWN + " TEXT ,  "
                + DemoInfoMother.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + DemoInfoMother.TABLE_NAME + " ORDER BY " + DemoInfoMother.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class DemoInfoChild {
        static final String TABLE_NAME = "DemoInfoChild";
        static final String COLUMN_ID = "CID";
        static final String SEX = "sex";
        static final String MOTHER_NAME = "motherName";
        static final String Id = "id";
        static final String REG_NO = "regNo";
        static final String MR_NO = "mrNo";
        static final String DOB = "dob";
        static final String AGE = "age";
        static final String UNION_COUNCIL = "unionCouncil";
        static final String ADDRESS = "address";
        static final String REFERRED_BY = "referredBy";
        static final String APPROXIMATE = "approximate";
        static final String NOT_KNOWN = "notKnown";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DemoInfoChild.TABLE_NAME + " (" + DemoInfoMother.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + DemoInfoChild.SEX + " TEXT ,  "
                + DemoInfoChild.REG_NO + " TEXT ,  "
                + DemoInfoChild.MR_NO + " TEXT ,  "
                + DemoInfoChild.DOB + " TEXT ,  "
                + DemoInfoChild.AGE + " TEXT ,  "
                + DemoInfoChild.UNION_COUNCIL + " TEXT ,  "
                + DemoInfoChild.ADDRESS + " TEXT ,  "
                + DemoInfoChild.REFERRED_BY + " TEXT ,  "
                + DemoInfoChild.APPROXIMATE + " TEXT ,  "
                + DemoInfoChild.NOT_KNOWN + " TEXT ,  "
                + DemoInfoChild.MOTHER_NAME + " TEXT ,  "
                + DemoInfoChild.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + DemoInfoChild.TABLE_NAME + " ORDER BY " + DemoInfoChild.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PersonalInfoMother {
        //etGravida, etPara, etAbortion, etHusbandName, etHusbandCNIC, etPrimaryNo, etSecondaryNo
        static final String TABLE_NAME = "personalInfoMother";
        static final String COLUMN_ID = "CID";
        static final String GRAVIDA = "gravida";
        static final String PARA = "para";
        static final String ABORTION = "abortion";
        static final String HUSBAND_NAME = "husbandName";
        static final String HUSBAND_CNIC = "husbandCNIC";
        static final String PRIMARY_NO = "primaryNo";
        static final String SECONDARY_NO = "secondaryNo";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PersonalInfoMother.TABLE_NAME + " (" + PersonalInfoMother.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PersonalInfoMother.GRAVIDA + " TEXT ,  "
                + PersonalInfoMother.PARA + " TEXT ,  "
                + PersonalInfoMother.ABORTION + " TEXT ,  "
                + PersonalInfoMother.HUSBAND_NAME + " TEXT ,  "
                + PersonalInfoMother.HUSBAND_CNIC + " TEXT ,  "
                + PersonalInfoMother.PRIMARY_NO + " TEXT ,  "
                + PersonalInfoMother.SECONDARY_NO + " TEXT ,  "
                + PersonalInfoMother.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PersonalInfoMother.TABLE_NAME + " ORDER BY " + PersonalInfoMother.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PersonalInfoChild {
        static final String TABLE_NAME = "personalInfoChild";
        static final String COLUMN_ID = "CID";
        static final String BIRTH_WEIGHT = "birthWeight";
        static final String BIRTH_LENGTH = "birthLength";
        static final String BIRTH_CIR = "birthCir";
        static final String NO_OF_SIBS = "noOfSibs";
        static final String MOTHER_ALIVE = "motherAlive";
        static final String MOTHER_NAME = "motherName";
        static final String MOTHER_CNIC = "motherCNIC";
        static final String FATHER_NAME = "fatherName";
        static final String FATHER_CNIC = "fatherCNIC";
        static final String PRIMARY_MOBILE = "primaryMobile";
        static final String SECONDARY_MOBILE = "secondaryMobile";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PersonalInfoChild.TABLE_NAME + " (" + PersonalInfoChild.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PersonalInfoChild.BIRTH_WEIGHT + " TEXT ,  "
                + PersonalInfoChild.BIRTH_LENGTH + " TEXT ,  "
                + PersonalInfoChild.BIRTH_CIR + " TEXT ,  "
                + PersonalInfoChild.NO_OF_SIBS + " TEXT ,  "
                + PersonalInfoChild.MOTHER_ALIVE + " TEXT ,  "
                + PersonalInfoChild.MOTHER_NAME + " TEXT ,  "
                + PersonalInfoChild.MOTHER_CNIC + " TEXT ,  "
                + PersonalInfoChild.FATHER_NAME + " TEXT ,  "
                + PersonalInfoChild.FATHER_CNIC + " TEXT ,  "
                + PersonalInfoChild.PRIMARY_MOBILE + " TEXT ,  "
                + PersonalInfoChild.SECONDARY_MOBILE + " TEXT ,  "
                + PersonalInfoChild.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PersonalInfoChild.TABLE_NAME + " ORDER BY " + PersonalInfoChild.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PatientHistoryMother {
        //etPastObstetricHistory, etMedicalHistorySurgery, etLMP, etTrimester
        static final String TABLE_NAME = "patientHistoryMother";
        static final String COLUMN_ID = "CID";
        static final String PAST_OBSTETRIC_HISTORY = "pastObstetricHistory";
        static final String MEDICAL_HISTORY_SURGERY = "medicalHistorySurgery";
        static final String LMP = "lmp";
        static final String TRIMESTER = "trimester";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PatientHistoryMother.TABLE_NAME + " (" + PatientHistoryMother.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PatientHistoryMother.PAST_OBSTETRIC_HISTORY + " TEXT ,  "
                + PatientHistoryMother.MEDICAL_HISTORY_SURGERY + " TEXT ,  "
                + PatientHistoryMother.LMP + " TEXT ,  "
                + PatientHistoryMother.TRIMESTER + " TEXT ,  "
                + PatientHistoryMother.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PatientHistoryMother.TABLE_NAME + " ORDER BY " + PatientHistoryMother.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PatientHistoryChild {
        //etPastObstetricHistory, etMedicalHistorySurgery, etLMP, etTrimester
        static final String TABLE_NAME = "patientHistoryChild";
        static final String COLUMN_ID = "CID";
        static final String PLACE_OF_BIRTH = "placeOfBirth";
        static final String BREAST_FED_WITHIN_ONE_HOUR = "breastFedWithinOneHour";
        static final String EXCLUSIVE_BREAST_FEED = "exclusiveBreastFeed";
        static final String COMPLEMENTARY_BREAST_FEED = "complementaryBreastFeed";
        static final String COMPLICATION_AS_NEW_BORN = "complicationAsNewBorn";
        static final String ILLNESS_HISTORY = "illnessHistory";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PatientHistoryChild.TABLE_NAME + " (" + PatientHistoryChild.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PatientHistoryChild.PLACE_OF_BIRTH + " TEXT ,  "
                + PatientHistoryChild.BREAST_FED_WITHIN_ONE_HOUR + " TEXT ,  "
                + PatientHistoryChild.EXCLUSIVE_BREAST_FEED + " TEXT ,  "
                + PatientHistoryChild.COMPLEMENTARY_BREAST_FEED + " TEXT ,  "
                + PatientHistoryChild.COMPLICATION_AS_NEW_BORN + " TEXT ,  "
                + PatientHistoryChild.ILLNESS_HISTORY + " TEXT ,  "
                + PatientHistoryChild.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PatientHistoryChild.TABLE_NAME + " ORDER BY " + PatientHistoryChild.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PatientVitalsMother {
        //        etWeight, etMUAC, etHB, etPulse, etTemp, etGAge, etUSG, etFHR, etFundalHT, etBSR
        static final String TABLE_NAME = "PatientVitalsMother";
        static final String COLUMN_ID = "CID";
        static final String WEIGHT = "weight";
        static final String MUAC = "muac";
        static final String PULSE = "pulse";
        static final String HB = "hb";
        static final String TEMP = "temp";
        static final String AGE = "age";
        static final String USG = "usg";
        static final String FHR = "fhr";
        static final String BSR = "bsr";
        static final String FUNDAL_HT = "FundalHT";
        static final String BP = "bp";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PatientVitalsMother.TABLE_NAME + " (" + PatientVitalsMother.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PatientVitalsMother.WEIGHT + " TEXT ,  "
                + PatientVitalsMother.MUAC + " TEXT ,  "
                + PatientVitalsMother.HB + " TEXT ,  "
                + PatientVitalsMother.TEMP + " TEXT ,  "
                + PatientVitalsMother.AGE + " TEXT ,  "
                + PatientVitalsMother.USG + " TEXT ,  "
                + PatientVitalsMother.FHR + " TEXT ,  "
                + PatientVitalsMother.BSR + " TEXT ,  "
                + PatientVitalsMother.PULSE + " TEXT ,  "
                + PatientVitalsMother.FUNDAL_HT + " TEXT ,  "
                + PatientVitalsMother.BP + " TEXT ,  "
                + PatientVitalsMother.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PatientVitalsMother.TABLE_NAME + " ORDER BY " + PatientVitalsMother.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    static class PatientVitalsChild {
        //        etWeight, etMUAC, etHB, etPulse, etTemp, etGAge, etUSG, etFHR, etFundalHT, etBSR
        static final String TABLE_NAME = "PatientVitalsChild";
        static final String COLUMN_ID = "CID";
        static final String WEIGHT = "weight";
        static final String MUAC = "muac";
        static final String HB = "hb";
        static final String TEMP = "temp";
        static final String AGE = "age";
        static final String USG = "usg";
        static final String FHR = "fhr";
        static final String BSR = "bsr";
        static final String PULSE = "pulse";
        static final String FUNDAL_HT = "FundalHT";
        static final String BP = "bp";
        static final String Id = "id";

        // Queries
        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PatientVitalsChild.TABLE_NAME + " (" + PatientVitalsChild.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + PatientVitalsChild.WEIGHT + " TEXT ,  "
                + PatientVitalsChild.MUAC + " TEXT ,  "
                + PatientVitalsChild.HB + " TEXT ,  "
                + PatientVitalsChild.TEMP + " TEXT ,  "
                + PatientVitalsChild.AGE + " TEXT ,  "
                + PatientVitalsChild.USG + " TEXT ,  "
                + PatientVitalsChild.FHR + " TEXT ,  "
                + PatientVitalsChild.BSR + " TEXT ,  "
                + PatientVitalsChild.PULSE + " TEXT ,  "
                + PatientVitalsChild.FUNDAL_HT + " TEXT ,  "
                + PatientVitalsChild.BP + " TEXT ,  "
                + PatientVitalsChild.Id + " TEXT  "
                + " );";
        //
        static final String QUERY_SELECT_ALL = "SELECT * FROM " + PatientVitalsChild.TABLE_NAME + " ORDER BY " + PatientVitalsChild.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class UserDataTable {
        //        "user_id": "3",
//                "user_fullname": "Usman",
//                "facility_name": "ASTP Lahore",
//                "facility_id": "1",
//                "username": "usman",
//                "user_password": "hisdu"
        public static final String TABLE_NAME = "UserDataTable";
        public static final String COLUMN_ID = "CID";
        public static final String USER_FULLNAME = "user_fullname";
        public static final String FACILITY_ID = "facility_id";
        public static final String FACILITY_NAME = "facility_name";
        public static final String USERNAME = "username";
        public static final String USER_PASSWORD = "user_password";
        public static final String USER_ID = "id";

        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + UserDataTable.TABLE_NAME + " (" + UserDataTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + UserDataTable.USERNAME + " TEXT ,  "
                + UserDataTable.USER_FULLNAME + " TEXT ,  "
                + UserDataTable.FACILITY_ID + " TEXT ,  "
                + UserDataTable.FACILITY_NAME + " TEXT ,  "
                + UserDataTable.USER_PASSWORD + " TEXT ,  "
                + UserDataTable.USER_ID + " TEXT  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + UserDataTable.TABLE_NAME + " ORDER BY " + UserDataTable.USER_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }


    static class PreferenceTable {

        static final String TABLE_NAME = "preference";

        static final String COLUMN_KEY = "key";
        static final String COLUMN_VALUE = "value";

        static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + PreferenceTable.TABLE_NAME + " (" + PreferenceTable.COLUMN_KEY + " TEXT NOT NULL UNIQUE,  " + PreferenceTable.COLUMN_VALUE + " TEXT NOT NULL " + ");";

        static final String SELECT_ALL_PREFRENCES_QUERY = "SELECT * FROM " + PreferenceTable.TABLE_NAME + " ;";
        static final String SELECT_PREFRENCE_VALUE_BY_KEY_QUERY = "SELECT " + PreferenceTable.COLUMN_VALUE + " FROM " + PreferenceTable.TABLE_NAME + " WHERE " + PreferenceTable.COLUMN_KEY + " = ? ;";
        static final String REPLACE_PREFRENCE_VALUE_BY_KEY_QUERY = "REPLACE INTO preference VALUES( ? , ?)";
        static final String UPDATE_PREFRENCE_VALUE_BY_KEY_QUERY = "UPDATE preference SET value='?' WHERE key='?';";

    }

    public static class BispInfoTable {

        public static final String TABLE_NAME = "bisp_data";
        public static final String COLUMN_ID = "rowid";
        public static final String NAME = "col_2";
        public static final String SR_NO = "col_1";
        public static final String AGE = "col_4";
        public static final String HEAD_NAME = "col_5";
        public static final String HEAD_RELATION = "col_6";
        public static final String DISTRICT = "col_7";
        public static final String TEHSIL = "col_8";
        public static final String Col_1 = "col_9";
        public static final String VILLAGE_NAME = "col_10";
        public static final String HEAD_ADDRESS = "col_11";
        public static final String CNIC_NO = "col_3";
        public static final String Id = "id";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + BispInfoTable.TABLE_NAME + " (" + BispInfoTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + BispInfoTable.NAME + " TEXT ,  "
                + BispInfoTable.AGE + " TEXT ,  "
                + BispInfoTable.HEAD_NAME + " TEXT ,  "
                + BispInfoTable.SR_NO + " TEXT ,  "
                + BispInfoTable.HEAD_RELATION + " TEXT ,  "
                + BispInfoTable.DISTRICT + " TEXT ,  "
                + BispInfoTable.TEHSIL + " TEXT ,  "
                + BispInfoTable.Col_1 + " TEXT ,  "
                + BispInfoTable.VILLAGE_NAME + " TEXT ,  "
                + BispInfoTable.HEAD_ADDRESS + " TEXT ,  "
                + BispInfoTable.CNIC_NO + " TEXT ,  "
                + BispInfoTable.Id + " TEXT  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + BispInfoTable.TABLE_NAME + " ORDER BY " + BispInfoTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class VisitInfoTable {

        public static final String TABLE_NAME = "visit_info_table";
        public static final String COLUMN_ID = "rowid";
        public static final String MOTHER_NAME = "mother_name";
        public static final String CHILD_NAME = "child_name";
        public static final String DOB = "dob";
        public static final String RELATION = "relation";
        public static final String CNIC = "cnic";
        public static final String IS_BENEFICIARY = "is_beneficiary";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + VisitInfoTable.TABLE_NAME + " (" + VisitInfoTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + VisitInfoTable.MOTHER_NAME + " TEXT ,  "
                + VisitInfoTable.CHILD_NAME + " TEXT ,  "
                + VisitInfoTable.DOB + " TEXT ,  "
                + VisitInfoTable.RELATION + " TEXT ,  "
                + VisitInfoTable.CNIC + " TEXT ,  "
                + VisitInfoTable.IS_BENEFICIARY + " TEXT  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + VisitInfoTable.TABLE_NAME + " ORDER BY " + VisitInfoTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class DistrictTable {

        public static final String TABLE_NAME = "tbl_district";
        public static final String COLUMN_ID = "rowid";
        public static final String ID = "id";
        public static final String DISTRICT_NAME = "district_name";
        public static final String ACTIVE = "active";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DistrictTable.TABLE_NAME + " (" + DistrictTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + DistrictTable.DISTRICT_NAME + " TEXT ,  "
                + DistrictTable.ACTIVE + " TEXT ,  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + DistrictTable.TABLE_NAME + " ORDER BY " + DistrictTable.ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    //("id"  PRIMARY KEY  NOT NULL  UNIQUE , "town_name" , "district_id" , "active" )
    public static class TehsilTable {

        public static final String TABLE_NAME = "tbl_tehsil";
        public static final String ID = "id";
        public static final String TOWN_NAME = "town_name";
        public static final String DISTRICT_ID = "district_id";
        public static final String ACTIVE = "active";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TehsilTable.TABLE_NAME + " (" + TehsilTable.ID + " INTEGER PRIMARY KEY,  "
                + TehsilTable.TOWN_NAME + " TEXT ,  "
                + TehsilTable.DISTRICT_ID + " TEXT ,  "
                + TehsilTable.ACTIVE + " TEXT   "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + TehsilTable.TABLE_NAME + " ORDER BY " + TehsilTable.ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class UCTable {
        //("id"  PRIMARY KEY  NOT NULL  UNIQUE , "uc_name" , "town_id" , "active" )
        public static final String TABLE_NAME = "tbl_uc";
        public static final String ID = "id";
        public static final String UC_NAME = "uc_name";
        public static final String TOWN_ID = "town_id";
        public static final String ACTIVE = "active";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + UCTable.TABLE_NAME + " (" + UCTable.ID + " INTEGER PRIMARY KEY,  "
                + UCTable.UC_NAME + " TEXT ,  "
                + UCTable.TOWN_ID + " TEXT ,  "
                + UCTable.ACTIVE + " TEXT   "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + UCTable.TABLE_NAME + " ORDER BY " + UCTable.ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class Mother_Reg_Table {

        public static final String TABLE_NAME = "mother_reg_table";
        public static final String COLUMN_ID = "rowid";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String CNIC = "cnic";
        public static final String BLOOD_GROUP = "blood_group";
        public static final String DOB = "dob";
        public static final String AGE = "age";
        public static final String PROVINCE = "province";
        public static final String DISTRICT = "district";
        public static final String TEHSIL = "tehsil";
        public static final String ADDRESS = "address";
        public static final String UC = "uc";
        public static final String VILLAGE = "village";
        public static final String MARITAL_STATUS = "marital_status";
        public static final String RACE = "race";
        public static final String HUSBAND_NAME = "husband_name";
        public static final String HUSBAND_CNIC = "husband_cnic";
        public static final String PRIMARY_CONTACT = "primary_contact";
        public static final String SECONDARY_CONTACT = "secondary_contact";
        public static final String RELATION = "relation";

        public static final String Id = "id";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + Mother_Reg_Table.TABLE_NAME + " (" + BispInfoTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + Mother_Reg_Table.FIRST_NAME + " TEXT ,  "
                + Mother_Reg_Table.LAST_NAME + " TEXT ,  "
                + Mother_Reg_Table.CNIC + " TEXT ,  "
                + Mother_Reg_Table.BLOOD_GROUP + " TEXT ,  "
                + Mother_Reg_Table.DOB + " TEXT ,  "
                + Mother_Reg_Table.AGE + " TEXT ,  "
                + Mother_Reg_Table.TEHSIL + " TEXT ,  "
                + Mother_Reg_Table.PROVINCE + " TEXT ,  "
                + Mother_Reg_Table.DISTRICT + " TEXT ,  "
                + Mother_Reg_Table.ADDRESS + " TEXT ,  "
                + Mother_Reg_Table.VILLAGE + " TEXT ,  "
                + Mother_Reg_Table.UC + " TEXT ,  "
                + Mother_Reg_Table.MARITAL_STATUS + " TEXT ,  "
                + Mother_Reg_Table.RACE + " TEXT ,  "
                + Mother_Reg_Table.HUSBAND_NAME + " TEXT ,  "
                + Mother_Reg_Table.HUSBAND_CNIC + " TEXT ,  "
                + Mother_Reg_Table.PRIMARY_CONTACT + " TEXT ,  "
                + Mother_Reg_Table.SECONDARY_CONTACT + " TEXT ,  "
                + Mother_Reg_Table.RELATION + " TEXT ,  "
                + Mother_Reg_Table.Id + " TEXT  "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + BispInfoTable.TABLE_NAME + " ORDER BY " + BispInfoTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class UnSentRecordTable {

        public static final String TABLE_NAME = "un_sent_record_table";
        public static final String COLUMN_ID = "id";
        public static final String URL = "url";
        public static final String Count = "count";
        public static final String LocationCode = "location_code";
        public static final String CreatedBy = "created_by";
        public static final String ClusterType = "cluster_type";
        public static final String Settlement = "settlement";
        public static final String Area = "area";
        public static final String ModuleId = "module_id";
        public static final String Sync = "sync";
        public static final String JSON_OBJECT = "json_object";
        public static final String SyncStatus = "sync_status";
        public static final String AlreadySync = "AlreadySync";

        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + UnSentRecordTable.TABLE_NAME + " (" + UnSentRecordTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + UnSentRecordTable.URL + " TEXT ,  "
                + UnSentRecordTable.LocationCode + " TEXT ,  "
                + UnSentRecordTable.CreatedBy + " TEXT ,  "
                + UnSentRecordTable.Count + " TEXT ,  "
                + UnSentRecordTable.ModuleId + " TEXT ,  "
                + UnSentRecordTable.ClusterType + " TEXT ,  "
                + UnSentRecordTable.Settlement + " TEXT ,  "
                + UnSentRecordTable.Area + " TEXT ,  "
                + UnSentRecordTable.Sync + " TEXT ,  "
                + UnSentRecordTable.SyncStatus + " TEXT ,  "
                + UnSentRecordTable.JSON_OBJECT + " TEXT ,  "
                + UnSentRecordTable.AlreadySync + " TEXT   "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + UnSentRecordTable.TABLE_NAME + " ORDER BY " + UnSentRecordTable.COLUMN_ID + " ASC;";
          //static final String QUERY_SELECT_ALL_Keys = "SELECT * FROM " + UnSentRecordTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }

    public static class ClusterRecordTable {

        public static final String TABLE_NAME = "cluster_table";
        public static final String COLUMN_ID = "id";
        public static final String URL = "url";
        public static final String Count = "count";
        public static final String LocationCode = "location_code";
        public static final String CreatedBy = "created_by";
        public static final String ModuleId = "module_id";
        public static final String ClusterType = "cluster_type";
        public static final String Settlement = "settlement";
        public static final String Area = "area";
        public static final String JSON_OBJECT = "json_object";
        // Queries
        public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + ClusterRecordTable.TABLE_NAME + " (" + ClusterRecordTable.COLUMN_ID + " INTEGER PRIMARY KEY,  "
                + ClusterRecordTable.URL + " TEXT ,  "
                + ClusterRecordTable.LocationCode + " TEXT ,  "
                + ClusterRecordTable.CreatedBy + " TEXT ,  "
                + ClusterRecordTable.Count + " TEXT ,  "
                + ClusterRecordTable.ModuleId + " TEXT ,  "
                + ClusterRecordTable.ClusterType + " TEXT ,  "
                + ClusterRecordTable.Settlement + " TEXT ,  "
                + ClusterRecordTable.Area + " TEXT ,  "
                + ClusterRecordTable.JSON_OBJECT + " TEXT   "
                + " );";
        //
        public static final String QUERY_SELECT_ALL = "SELECT * FROM " + ClusterRecordTable.TABLE_NAME + " ORDER BY " + ClusterRecordTable.COLUMN_ID + " ASC;";
        public static final String QUERY_DELETE_ALL = "DELETE FROM " + ClusterRecordTable.TABLE_NAME;
        //  static final String QUERY_SELECT_USER_BY_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_ID + " = ? ORDER BY " + CacheTable.COLUMN_ID + " ASC;";
        //  static final String QUERY_SELECT_USER_BY_USER_ID = "SELECT * FROM " + CacheTable.TABLE_NAME + " WHERE " + CacheTable.COLUMN_USER_ID + " = ? ;";
    }
}