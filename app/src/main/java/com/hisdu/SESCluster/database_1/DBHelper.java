package com.hisdu.SESCluster.database_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.hisdu.SESCluster.constants.Globals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASENAME = "SES.db";
    //  STORAGE_PATH = Environment.getExternalStorageDirectory() + "/com.hisdu.SES/images/";
    private static String DB_PATH = Environment.getExternalStorageDirectory() + "/com.hisdu.SES/databases/";
    private Context context;

    /* constructor */
    public DBHelper(Context context) {
        //super(context, DATABASENAME, null, DATABASE_VERSION);
        super(context, DB_PATH + DATABASENAME, null, DATABASE_VERSION);
        this.context = context;
        boolean dbExist = checkDataBase(DB_PATH, DATABASENAME);
        SQLiteDatabase db_Read = null;
        if (dbExist) {
            db_Read = this.getReadableDatabase();
            db_Read.close();
        } else {
            try {
                copyDataBase(context);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public static boolean checkDataBase(String dbPath, String dbName) {
        File dbFile = new File(dbPath + dbName);
        return dbFile.exists();
    }

    /**
     * @param myContext
     * @throws IOException
     */
    private void copyDataBase(Context myContext) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASENAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASENAME;
        File dbDir = new File(DB_PATH);
        if (!dbDir.exists()) {
            boolean directoryCreated = dbDir.mkdirs();
            Log.v(Globals.TAG, "Directory Created =" + directoryCreated);

        }
        // Open the empty db as the output stream
        File dbFile = new File(outFileName);
        if (!dbFile.exists()) {
            boolean fileCreated = dbFile.createNewFile();
            Log.v(Globals.TAG, "File created =" + fileCreated);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Create Category Table */
		/*db.execSQL("CREATE TABLE IF NOT EXISTS TableName (" + 
				"Id INTEGER PRIMARY KEY," +
				"DataSource VARCHAR ," +
				"InitialCatege VARCHAR, " +				
				"UserId VARCHAR, " +
				"Password VARCHAR, " +
				"IsLive VARCHAR, " +
				"AddedBy VARCHAR, " +
				"ChangedBy VARCHAR, " +
				"DateChanged VARCHAR, " +
				"CompanyId VARCHAR, " +
				"TS VARCHAR, " +				
				"DisplayName VARCHAR, " +
				"SQLLogin VARCHAR, " +
				"SQLPWD VARCHAR)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //	db.execSQL("DROP TABLE IF EXISTS Gateways");
        onCreate(db);
    }
}
