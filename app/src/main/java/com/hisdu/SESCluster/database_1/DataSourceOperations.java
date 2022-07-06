package com.hisdu.SESCluster.database_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.database.DatabaseConfig;

import org.json.JSONObject;

public class DataSourceOperations {
    // Database fields
    private SQLiteDatabase sqLiteDataBase;
    private DBHelper dbHelper;

    /* Default Constructor */
    public DataSourceOperations(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    /* Open Database For Writing */
    public void open() throws SQLException {
        synchronized (Globals.lock) {
            this.sqLiteDataBase = this.dbHelper.getWritableDatabase();
            //Log.v(Constants.TAG, "testing");
        }
    }

    /* Close the Database */
    public void close() {
        synchronized (Globals.lock) {
            this.dbHelper.close();
        }
    }

    public boolean deleteUnSaveRec(String id) {
        return this.sqLiteDataBase.delete("unsent_data", "FormDetails_ID" + "=" + id, null) >= 0;
    }

    public boolean deleteUnSaveRecByID(String id) {
        return this.sqLiteDataBase.delete("unsent_data", "id" + "=" + id, null) >= 0;
    }


    public boolean deleteCashedRec(String id) {
        return this.sqLiteDataBase.delete(DatabaseConfig.UnSentRecordTable.TABLE_NAME, DatabaseConfig.UnSentRecordTable.COLUMN_ID + "=" + id, null) >= 0;
    }

    public boolean deleteAllFromCasheData() {
        return this.sqLiteDataBase.delete("cashe_data", null, null) >= 0;
    }

    /* Count total records */
    public int getCountOfUnsent() {
        synchronized (Globals.lock) {
            int count = 0;
            Cursor cursor = this.sqLiteDataBase.rawQuery("SELECT * FROM un_sent_record_table", null);
            //select count(*) from unsent_data
            if (cursor != null && !cursor.isClosed()) {
                count = cursor.getCount();
                cursor.close();
            }
            return count;
        }
    }

    /* Count total records */
    public int getCountOfCasheData() {
        synchronized (Globals.lock) {
            int count = 0;
            Cursor cursor = this.sqLiteDataBase.rawQuery("SELECT * FROM cashe_data", null);
            //select count(*) from unsent_data
            if (cursor != null && !cursor.isClosed()) {
                count = cursor.getCount();
                cursor.close();
            }
            return count;
        }
    }






    /**
     * static final String TABLE_NAME = "UserDataTable";
     * static final String COLUMN_ID = "CID";
     * static final String USER_FULLNAME = "user_fullname";
     * static final String FACILITY_ID = "facility_id";
     * static final String FACILITY_NAME = "facility_name";
     * static final String USERNAME = "username";
     * static final String USER_PASSWORD = "user_password";
     * static final String USER_ID = "id";
     */





//    public ArrayList<MotherRegObject>getMotherData(String cnic){
//        Cursor cursor = null;
//        String sql = "SELECT " + "cnic" + " FROM " + "mother_reg_table"
//                + " WHERE " + "cnic" + " LIKE " + "'" + cnic + "'";
//        cursor = this.sqLiteDataBase.rawQuery(sql, null);
//    }





    public long insertRecordInUnsentTable(int id, String url, JSONObject jsonObject) {
        long returnValue = 0;
        ContentValues unsentRecord = new ContentValues();
        unsentRecord.put(DatabaseConfig.UnSentRecordTable.COLUMN_ID, id);
        unsentRecord.put(DatabaseConfig.UnSentRecordTable.URL, url);
        unsentRecord.put(DatabaseConfig.UnSentRecordTable.JSON_OBJECT, jsonObject.toString());
        returnValue = this.sqLiteDataBase.insert(DatabaseConfig.UnSentRecordTable.TABLE_NAME, null, unsentRecord);
        Log.d("Unsent  record", returnValue + " : added successfully");
        return returnValue;
    }


}
