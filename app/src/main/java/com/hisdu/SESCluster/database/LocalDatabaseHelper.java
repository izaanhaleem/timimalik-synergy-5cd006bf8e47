package com.hisdu.SESCluster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "LocalDatabaseHelper";
    private static final String ENCODING_SETTING = "PRAGMA encoding ='windows-1256'";
    String databasePath =  Environment.getExternalStorageDirectory() +"/com.hisdu.SES/databases/";

    LocalDatabaseHelper(Context context) {
        // configure database for respective context
        super(context, DatabaseConfig.LOCAL_DATABASE_NAME, null, DatabaseConfig.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        // create database query

        db.execSQL(DatabaseConfig.ClusterRecordTable.QUERY_CREATE_TABLE);
        db.execSQL(DatabaseConfig.UnSentRecordTable.QUERY_CREATE_TABLE);
        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;
        if (dbExist){
            db_Read = this.getReadableDatabase();
            db_Read.close();
        }else{
            try {
                copyDataBase(context);
            } catch (IOException e){
                e.getMessage();
            }
        }
//        databasePath = context.getDatabasePath(DatabaseConfig.LOCAL_DATABASE_NAME).getPath();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade()");
        if (newVersion > oldVersion) {

            Log.d(LocalDatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseConfig.ClusterRecordTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseConfig.UnSentRecordTable.TABLE_NAME);
//			db.execSQL(DatabaseConfig.PreferenceTable.QUERY_CREATE_TABLE);
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            db.execSQL(ENCODING_SETTING);
        }
    }
    private Context context;

    /* constructor */


    private boolean checkDataBase() {
        File dbFile = new File(databasePath + DatabaseConfig.LOCAL_DATABASE_NAME);
        return dbFile.exists();
    }

    /**
     * @param myContext
     * @throws IOException
     */
    private void copyDataBase(Context myContext) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DatabaseConfig.LOCAL_DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = databasePath + DatabaseConfig.LOCAL_DATABASE_NAME;
        File dbDir = new File(databasePath);
        if(!dbDir.exists()){
            boolean directoryCreated =dbDir.mkdirs();
            Log.v("", "Directory Created ="+directoryCreated);

        }
        // Open the empty db as the output stream
        File dbFile = new File(outFileName);
        if(!dbFile.exists()) {
            boolean fileCreated = dbFile.createNewFile();
            Log.v("", "File created ="+ fileCreated);
        }
        OutputStream myOutput = new FileOutputStream(dbFile);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

}
