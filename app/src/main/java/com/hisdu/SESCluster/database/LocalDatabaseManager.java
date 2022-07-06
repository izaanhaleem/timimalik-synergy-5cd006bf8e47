package com.hisdu.SESCluster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.objects.UnSentRecordsObject;
import com.hisdu.SESCluster.utils.Utils;
import com.hisdu.ses.AppController;
import com.hisdu.ses.MainActivity;
import com.hisdu.ses.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;


public class LocalDatabaseManager {

    private static final String TAG = "LocalDatabaseManager";
    private static final boolean DB_LOG = false;
    String dCount,dLocationCode;

    static LocalDatabaseManager transactionManager = null;
    // database manager
    private LocalDatabaseHelper databaseManager = null;
    // database
    private SQLiteDatabase database = null;

    public static LocalDatabaseManager getInstance(Context context) {
        if (transactionManager == null) {
            transactionManager = new LocalDatabaseManager(context);
        }
        return transactionManager;
    }

    private LocalDatabaseManager(Context context) {
        // get database manager from application context
        databaseManager = new LocalDatabaseHelper(context);
        // get writable database from database manager
        database = databaseManager.getWritableDatabase();

    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public static void destroy() {
        transactionManager = null;
    }



	/*public long insertUserObject(UserObject userobject){

		Log.d(TAG, "inserting userobject: " + userobject.toString());
		long returnValue = 0;
		if (database != null && database.isOpen()) {
			database.execSQL("DELETE FROM " + DatabaseConfig.UserTable.TABLE_NAME);
			ContentValues newPreference = new ContentValues();
			newPreference.put(DatabaseConfig.UserTable.COLUMN_ID, userobject.getId());
			newPreference.put(DatabaseConfig.UserTable.User_Name, userobject.getUser_name());
			newPreference.put(DatabaseConfig.UserTable.Password, userobject.getPassword());
			newPreference.put(DatabaseConfig.UserTable.Child_Name, userobject.getChild_name());
			newPreference.put(DatabaseConfig.UserTable.Father_Name, userobject.getFather_name());
			newPreference.put(DatabaseConfig.UserTable.Family_Name, userobject.getFamily_name());
			newPreference.put(DatabaseConfig.UserTable.Age, userobject.getAge());
			newPreference.put(DatabaseConfig.UserTable.Dob_Month, userobject.getDob_month());
			newPreference.put(DatabaseConfig.UserTable.Dob_Day, userobject.getDob_day());
			newPreference.put(DatabaseConfig.UserTable.Dob_Year, userobject.getDob_year());
			newPreference.put(DatabaseConfig.UserTable.Email, userobject.getEmail());
			newPreference.put(DatabaseConfig.UserTable.Mobile, userobject.getMobile());
			newPreference.put(DatabaseConfig.UserTable.Gender, userobject.getGender());
			newPreference.put(DatabaseConfig.UserTable.Emirates, userobject.getEmirates());
			newPreference.put(DatabaseConfig.UserTable.Nationality, userobject.getNationality());
			newPreference.put(DatabaseConfig.UserTable.Diagnosis, userobject.getDiagnosis());
			newPreference.put(DatabaseConfig.UserTable.Residence, userobject.getResidence());
			newPreference.put(DatabaseConfig.UserTable.Id, userobject.getId());
			newPreference.put(DatabaseConfig.UserTable.Services, userobject.getServices());
			newPreference.put(DatabaseConfig.UserTable.Does_Go_School, userobject.getDoes_go_school());

			returnValue = database.insert(DatabaseConfig.UserTable.TABLE_NAME, null, newPreference);
		}

		return returnValue;
	}

	
	public UserObject getUser()
	{
		UserObject user = null;
		String query = "SELECT * FROM " + DatabaseConfig.UserTable.TABLE_NAME;
		if (database != null && database.isOpen()) {
			Cursor cursor = database.rawQuery(query, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				//user = new UserObject(cursor);
			}
		}
		return user;
	}
	*/


    /************* Preferences **********/
    public String fetchPreference(String key) {
        String preferenceValue = null;
        String query = "SELECT * FROM " + DatabaseConfig.PreferenceTable.TABLE_NAME + " WHERE " + DatabaseConfig.PreferenceTable.COLUMN_KEY + " = '" + key + "';";
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                preferenceValue = cursor.getString(cursor.getColumnIndex(DatabaseConfig.PreferenceTable.COLUMN_VALUE));
            }
            log(TAG, preferenceValue);
            cursor.close();
        }
        return preferenceValue;
    }

    public long insertPreference(String key, String value) {
        long returnValue = 0;
        if (database != null && database.isOpen()) {
            ContentValues newPreference = new ContentValues();
            newPreference.put(DatabaseConfig.PreferenceTable.COLUMN_KEY, key);
            newPreference.put(DatabaseConfig.PreferenceTable.COLUMN_VALUE, value);
            returnValue = database.insert(DatabaseConfig.PreferenceTable.TABLE_NAME, null, newPreference);
        }
        return returnValue;
    }

    public boolean savePreferenceFlag(String key, boolean flag) {
        if (flag)
            return savePreference(key, "" + 1);
        else
            return savePreference(key, "" + 0);
    }

    public boolean getPreferenceFlag(String key) {
        String preferenceString = getPreference(key);
        log(TAG, "preferenceString:" + preferenceString);
        if (preferenceString == null || preferenceString.length() == 0 || preferenceString.equals("null"))
            return false;
        int preference = Integer.parseInt(preferenceString);
        if (preference == 1)
            return true;
        else
            return false;
    }

    public int getPreferenceInt(String key) {
        String preference = getPreference(key);
        if (preference == null || preference.equals("null") || preference.length() == 0)
            return -1;
        else
            return Integer.parseInt(preference);
    }

    public int getPreferenceInt(String key, int defaultInt) {
        String preference = getPreference(key);
        if (preference == null || preference.equals("null") || preference.length() == 0)
            return defaultInt;
        else
            return Integer.parseInt(preference);
    }

    public String getPreference(String key) {
        String preference = fetchPreference(key);

        return preference;
    }

    public String getPreference(String key, String defVal) {
        String preference = fetchPreference(key);
        if (preference == null || preference.length() == 0)
            return defVal;

        return preference;
    }

    public boolean savePreference(String key, String value) {
        Log.d(TAG, "key:" + key + " val:" + value);
        long status = replacePreference(key, value);

        if (status == -1)
            return false;
        else
            return true;
    }

    public int updatePreference(String key, String value) {
        int returnValue = 0;
        if (database != null && database.isOpen()) {
            String[] selectionArgs = {value, key};
            database.rawQuery(DatabaseConfig.PreferenceTable.UPDATE_PREFRENCE_VALUE_BY_KEY_QUERY, selectionArgs);

            log(TAG, "Upd-" + key + ":" + returnValue);
        }
        return returnValue;
    }

    public int insertOrUpdatePreference(String key, String value) {
        boolean isPreferenceKeyExist = isPreferenceKeyExist(key);
        if (isPreferenceKeyExist)
            return (int) replacePreference(key, value);
        else
            return (int) insertPreference(key, value);
    }

    public long replacePreference(String key, String value) {
        isPreferenceKeyExist(key);
        long returnValue = 0;
        if (database != null && database.isOpen()) {
            ContentValues newRecord = new ContentValues();
            newRecord.put(DatabaseConfig.PreferenceTable.COLUMN_KEY, key);
            newRecord.put(DatabaseConfig.PreferenceTable.COLUMN_VALUE, value);
            returnValue = database.replace(DatabaseConfig.PreferenceTable.TABLE_NAME, null, newRecord);
            log(TAG, "Upd:" + returnValue);
        }
        return returnValue;
    }

    private boolean isPreferenceKeyExist(String key) {
        boolean flag = false;
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConfig.PreferenceTable.TABLE_NAME + " WHERE " + DatabaseConfig.PreferenceTable.COLUMN_KEY + "= '" + key + "';", null);
            if (cursor != null && cursor.getCount() > 0)
                flag = true;
            else
                flag = false;
            cursor.close();
        }
        return flag;
    }

    public void clearPreferences() {
        if (database != null && database.isOpen()) {
            database.delete(DatabaseConfig.PreferenceTable.TABLE_NAME, null, null);
            database.execSQL("DROP TABLE IF EXISTS " + DatabaseConfig.PreferenceTable.TABLE_NAME);
            database.execSQL(DatabaseConfig.PreferenceTable.QUERY_CREATE_TABLE);
        }
    }

    public void clearPreferences(String column) {
        if (database != null && database.isOpen()) {
            String[] val = {column};
            database.delete(DatabaseConfig.PreferenceTable.TABLE_NAME, DatabaseConfig.PreferenceTable.COLUMN_KEY + " =? ", val);

        }
    }

    public ArrayList<String> fetchAllPrefrencesList() {
        ArrayList<String> preferences = new ArrayList<String>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery(DatabaseConfig.PreferenceTable.SELECT_ALL_PREFRENCES_QUERY, new String[]{});
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(DatabaseConfig.PreferenceTable.COLUMN_KEY));
                String value = cursor.getString(cursor.getColumnIndex(DatabaseConfig.PreferenceTable.COLUMN_VALUE));
                preferences.add(key + ":" + value);
                log(TAG, key + ":" + value);
            }
            cursor.close();
        }
        return preferences;
    }


    /************* Utils **********/
    boolean isEmptyTable(String tableName) {
        boolean flag = true;
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " ;", null);
            if (cursor == null || cursor.getCount() <= 0)
                flag = true;
            else
                flag = false;
            cursor.close();
        }
        log(TAG, "isEmpty=" + tableName + ":" + flag);
        return flag;
    }

    public int getRowCount(String tableName) {
        int count = -1;
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " ;", null);
            if (cursor != null && cursor.getCount() > 0)
                count = cursor.getCount();
            else
                count = 0;
            cursor.close();
        }
        return count;
    }

    /************* Utils // **********/

    public void clearTables() {

        if (database != null && database.isOpen()) {

//			database.execSQL("DELETE FROM " + DatabaseConfig.UserTable.TABLE_NAME);

            log(TAG, "Tables Cleared.");
        }

    }

    public void clearTable(String table) {

        if (database != null && database.isOpen()) {
            database.execSQL("DELETE FROM " + table);
            log(TAG, "Tables Cleared.");
        }

    }

    /************ User CRUD End *************/


    private void log(String tag, String string) {
        if (DB_LOG)
            Log.e(tag, "" + string);
    }


    public void DeleteUserOnLogout() {
        if (database != null && database.isOpen()) {
//			database.execSQL("DELETE FROM " + DatabaseConfig.UserTable.TABLE_NAME);
        }
    }


    public boolean isRecordAvailable(String cnic) {
        Cursor cursor = null;
        String sql = "SELECT * FROM " + DatabaseConfig.CNICTable.TABLE_NAME + " WHERE " + DatabaseConfig.CNICTable.CNIC_NO + " LIKE " + "'" + cnic + "'";
        cursor = database.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


    public void getBispInfo() {
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery(DatabaseConfig.BispInfoTable.QUERY_SELECT_ALL, new String[]{});
            while (cursor.moveToNext()) {
                Log.d("Info_1", cursor.getString(cursor.getColumnIndex(DatabaseConfig.BispInfoTable.HEAD_ADDRESS)));
            }
            cursor.close();
        }
    }

    public long insertRecordInClusterTable(int id, String url, JSONObject jsonObject, Context context) {
        long returnValue = 0;

        ContentValues clusterRecord = new ContentValues();
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.COLUMN_ID, id);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.URL, url);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.Count, Globals.Count);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.ModuleId, AppController.appModuleID);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.CreatedBy, new SharedPref(MainActivity.main).GetserverID());
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.LocationCode, Globals.Location);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.ClusterType, AppController.clusterType);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.Settlement, AppController.hrmp);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.Area, AppController.area);
        clusterRecord.put(DatabaseConfig.UnSentRecordTable.JSON_OBJECT, jsonObject.toString());
        returnValue = database.insert(DatabaseConfig.ClusterRecordTable.TABLE_NAME, null, clusterRecord);
        Log.d("cluster  record", returnValue + " : added successfully");
        Utils.saveClusterId(id + 1, context);
        return returnValue;
    }
    /* public int SaveUserRecordData(String url, String jsonData, int mode, Context context) throws JSONException {
        int result;
        synchronized (Globals.lock) {

            Calendar cal = Calendar.getInstance();
            ContentValues contentVal = new ContentValues();
            int id = Utils.getUnSyncId(context);
            contentVal.put("id", id);
//			contentVal.put("user_id", rec.getUser_id());
            contentVal.put("json_rec", jsonData);
            contentVal.put("url", url);
            //contentVal.put("image_id", rec.getImages_id());
//            contentVal.put("date_modify", Utils.formattedDateTime((cal.getTimeInMillis() / 1000)));
//            contentVal.put("date_created", Utils.formattedDateTime((cal.getTimeInMillis() / 1000)));
//			if(rec.getImages().size()>0){
//				if(!saveImageInDB(rec)){
//					deleteImage(rec.getId());
//					return -1;
//				}
//			}
//			if(mode == 0)
            Log.d(Globals.TAG + "Data_base", jsonData);
            result = (int) this.sqLiteDataBase.insert("Unsent_Rec", null, contentVal);
            Utils.saveUnSyncId(id + 1, context);
//			else{
//				String whereClause = "user_id like'"+rec.user_id+ "'";
//				result = (int)this.sqLiteDataBase.update("LatLongData", contentVal, whereClause, null);
        }
//		}
        return result;
    }*/
    public long saveRecordsOffline(String record, String url) {
        long returnValue = 0;
        if (database != null && database.isOpen()) {
            ContentValues workPlaceData = new ContentValues();
//            workPlaceData.put(DatabaseConfig.UnSentRecordTable.COLUMN_ID, id);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.JSON_OBJECT, record);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.URL, url);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.Count, Globals.Count);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.ModuleId, AppController.appModuleID);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.CreatedBy, new SharedPref(MainActivity.main).GetserverID());
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.LocationCode, Globals.Location);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.ClusterType, AppController.clusterType);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.Settlement, AppController.hrmp);
            workPlaceData.put(DatabaseConfig.UnSentRecordTable.Area, AppController.area);

            workPlaceData.put(DatabaseConfig.UnSentRecordTable.AlreadySync, "0");

            workPlaceData.put(DatabaseConfig.UnSentRecordTable.SyncStatus, "0");

            workPlaceData.put(DatabaseConfig.UnSentRecordTable.Sync, getUnSentRecords().size() + 1);
            returnValue = database.insert(DatabaseConfig.UnSentRecordTable.TABLE_NAME, null, workPlaceData);

//            }
        }
        Log.d(" record", returnValue + " : added successfully");
        return returnValue;
    }
    public long UpdateRecordsOffline(String id) {
        long returnValue = 0;
        if (database != null && database.isOpen()) {
            ContentValues workPlaceData = new ContentValues();

            workPlaceData.put(DatabaseConfig.UnSentRecordTable.SyncStatus, "1");
            returnValue = database.update(DatabaseConfig.UnSentRecordTable.TABLE_NAME, workPlaceData, "sync = ?", new String[]{id});
        }
        Log.d(" record", returnValue + " : added successfully");
        return returnValue;
    }

    public long UpdateAlreadySync(String id) {
        long returnValue = 0;
        if (database != null && database.isOpen()) {
            ContentValues workPlaceData = new ContentValues();

            workPlaceData.put(DatabaseConfig.UnSentRecordTable.AlreadySync, "1");
            returnValue = database.update(DatabaseConfig.UnSentRecordTable.TABLE_NAME, workPlaceData, "sync = ?", new String[]{id});
        }
        Log.d(" record", returnValue + " : added successfully");
        return returnValue;
    }

    public ArrayList<UnSentRecordsObject> getUnSentRecords() {
        ArrayList<UnSentRecordsObject> workplaceRecords = new ArrayList<>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery(DatabaseConfig.UnSentRecordTable.QUERY_SELECT_ALL, new String[]{});
            while (cursor.moveToNext()) {
                UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                unSentRecordsObject.setData(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.JSON_OBJECT)));
                unSentRecordsObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.COLUMN_ID))));
                unSentRecordsObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.URL)));
                workplaceRecords.add(unSentRecordsObject);
            }
            cursor.close();
        }
        return workplaceRecords;
    }

    public ArrayList<UnSentRecordsObject> getUnSentRecordsWithKeys(String cb, String module, String location, String clustertype) {
        ArrayList<UnSentRecordsObject> workplaceRecords = new ArrayList<>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConfig.UnSentRecordTable.TABLE_NAME + " WHERE " + DatabaseConfig.UnSentRecordTable.CreatedBy + " = '" + cb + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ModuleId + " = '" + module + "'" + " AND " + DatabaseConfig.UnSentRecordTable.LocationCode + " = '" + location + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ClusterType + " = '" + clustertype + "'"+ " AND " + DatabaseConfig.UnSentRecordTable.SyncStatus + " = '" + "0" + "'", new String[]{});

            while (cursor.moveToNext()) {
                UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                unSentRecordsObject.setData(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.JSON_OBJECT)));
                unSentRecordsObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.COLUMN_ID))));
                unSentRecordsObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.URL)));
                unSentRecordsObject.setCount(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Count)));
                unSentRecordsObject.setLocation(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.LocationCode)));
                unSentRecordsObject.setClustertype(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ClusterType)));
                unSentRecordsObject.setCreatedby(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.CreatedBy)));
                unSentRecordsObject.setModuleid(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ModuleId)));
                unSentRecordsObject.setArea(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Area)));
                unSentRecordsObject.setSettlement(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Settlement)));
                unSentRecordsObject.setSync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Sync)));
                unSentRecordsObject.setSyncStatus(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.SyncStatus)));
                unSentRecordsObject.setAlreadySync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.AlreadySync)));

                workplaceRecords.add(unSentRecordsObject);
            }
            cursor.close();
        }
        return workplaceRecords;
    }

    public ArrayList<UnSentRecordsObject> getUnSentRecordsWithKeys(String cb, String module, String location) {
        ArrayList<UnSentRecordsObject> workplaceRecords = new ArrayList<>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConfig.UnSentRecordTable.TABLE_NAME + " WHERE " + DatabaseConfig.UnSentRecordTable.CreatedBy + " = '" + cb + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ModuleId + " = '" + module + "'" + " AND " + DatabaseConfig.UnSentRecordTable.LocationCode + " = '" + location + "'"+ " AND " + DatabaseConfig.UnSentRecordTable.SyncStatus + " = '" + 0 + "'", new String[]{});
            while (cursor.moveToNext()) {
                UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                unSentRecordsObject.setData(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.JSON_OBJECT)));
                unSentRecordsObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.COLUMN_ID))));
                unSentRecordsObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.URL)));
                unSentRecordsObject.setCount(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Count)));
                unSentRecordsObject.setLocation(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.LocationCode)));
                unSentRecordsObject.setClustertype(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ClusterType)));
                unSentRecordsObject.setCreatedby(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.CreatedBy)));
                unSentRecordsObject.setModuleid(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ModuleId)));
                unSentRecordsObject.setArea(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Area)));
                unSentRecordsObject.setSettlement(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Settlement)));
                unSentRecordsObject.setSync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Sync)));
                unSentRecordsObject.setSyncStatus(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.SyncStatus)));
                unSentRecordsObject.setAlreadySync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.AlreadySync)));

                workplaceRecords.add(unSentRecordsObject);
            }
            cursor.close();
        }
        return workplaceRecords;
    }

    public ArrayList<UnSentRecordsObject> getUnSentRecordsWithKeys(String cb, String module) {
        ArrayList<UnSentRecordsObject> workplaceRecords = new ArrayList<>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConfig.UnSentRecordTable.TABLE_NAME + " WHERE " + DatabaseConfig.UnSentRecordTable.CreatedBy + " = '" + cb + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ModuleId + " = '" + module + "'", new String[]{});
            while (cursor.moveToNext()) {
                UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                unSentRecordsObject.setData(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.JSON_OBJECT)));
                unSentRecordsObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.COLUMN_ID))));
                unSentRecordsObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.URL)));
                unSentRecordsObject.setCount(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Count)));
                unSentRecordsObject.setLocation(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.LocationCode)));
                unSentRecordsObject.setClustertype(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ClusterType)));
                unSentRecordsObject.setCreatedby(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.CreatedBy)));
                unSentRecordsObject.setModuleid(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ModuleId)));
                unSentRecordsObject.setArea(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Area)));
                unSentRecordsObject.setSettlement(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Settlement)));
                unSentRecordsObject.setAlreadySync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.AlreadySync)));

                workplaceRecords.add(unSentRecordsObject);
            }
            cursor.close();
        }
        return workplaceRecords;
    }
    public ArrayList<UnSentRecordsObject> getUnSyncRecordsWithKeys(String cb, String module) {
        ArrayList<UnSentRecordsObject> workplaceRecords = new ArrayList<>();
        if (database != null && database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConfig.UnSentRecordTable.TABLE_NAME + " WHERE " + DatabaseConfig.UnSentRecordTable.CreatedBy + " = '" + cb + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ModuleId + " = '" + module + "'"+ " AND " + DatabaseConfig.UnSentRecordTable.SyncStatus + " = '" + "0" + "'", new String[]{});
            while (cursor.moveToNext()) {
                UnSentRecordsObject unSentRecordsObject = new UnSentRecordsObject();
                unSentRecordsObject.setData(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.JSON_OBJECT)));
                unSentRecordsObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.COLUMN_ID))));
                unSentRecordsObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.URL)));
                unSentRecordsObject.setCount(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Count)));
                unSentRecordsObject.setLocation(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.LocationCode)));
                unSentRecordsObject.setClustertype(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ClusterType)));
                unSentRecordsObject.setCreatedby(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.CreatedBy)));
                unSentRecordsObject.setModuleid(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.ModuleId)));
                unSentRecordsObject.setArea(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Area)));
                unSentRecordsObject.setSettlement(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.Settlement)));
                unSentRecordsObject.setAlreadySync(cursor.getString(cursor.getColumnIndex(DatabaseConfig.UnSentRecordTable.AlreadySync)));

                workplaceRecords.add(unSentRecordsObject);
            }
            cursor.close();
        }
        return workplaceRecords;
    }

    public boolean deleteUnSentRec(String id)
    {
        return database.delete(DatabaseConfig.UnSentRecordTable.TABLE_NAME,
                DatabaseConfig.UnSentRecordTable.COLUMN_ID + "=" + id, null) >= 0;
    }

    public boolean deleteSentRec(String cb, String module, String location)
    {
        return database.delete(DatabaseConfig.UnSentRecordTable.TABLE_NAME, DatabaseConfig.UnSentRecordTable.CreatedBy + " = '" + cb + "'" + " AND " + DatabaseConfig.UnSentRecordTable.ModuleId + " = '" + module + "'" + " AND " + DatabaseConfig.UnSentRecordTable.LocationCode + " = '" + location  + "'", null) >= 0;
    }

    public boolean deleteRecord(String sync)
    {
        return database.delete(DatabaseConfig.UnSentRecordTable.TABLE_NAME, DatabaseConfig.UnSentRecordTable.Sync + "=?", new String[]{sync}) > 0;
    }

}
