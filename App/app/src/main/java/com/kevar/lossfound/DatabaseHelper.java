package com.kevar.lossfound;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "DATA.DB";
    static final int DB_VERSION = 1;


    public static final String STUFF_TABLE_NAME = "stuff_data";
    public static final String USER_TABLE_NAME = "user_data";
    public static final String STUFF_CAT_TABLE_NAME = "stuff_cat_detail";
    public static final String STUFF_LOG_TABLE_NAME = "stuff_log";

    public static final String USER_ID = "u_id";
    public static final String CATEGORY_ID = "cat_id";
    public static final String STUFF_ID = "stf_id";
    public static final String LOG_ID = "log_id";

    private static final String CREATE_STUFF_TABLE = "create table " + STUFF_TABLE_NAME + "(" +
            "_"+ STUFF_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FOREIGN KEY("+ USER_ID +") REFERENCES " + USER_TABLE_NAME + "(_"+ USER_ID +"), " +
            "stf_name TEXT NOT NULL, " +
            "FOREIGN KEY("+ CATEGORY_ID +") REFERENCES (_"+ CATEGORY_ID +"), " +
            "stf_color TEXT NOT NULL, " +
            "stf_desc TEXT NOT NULL, " +
            "img_data BLOB NOT NULL, " +
            "stf_stats TEXT NOT NULL " +
            ");";

    private static final String CREATE_USER_TABLE = "create table " + USER_TABLE_NAME + "(" +
            "_"+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "u_fname TEXT NOT NULL, " +
            "u_lname TEXT NOT NULL, " +
            "u_email TEXT NOT NULL, " +
            "u_passkey TEXT NOT NULL, " +
            "u_cat TEXT NOT NULL " +
            ");";

    public static final String CREATE_STUFF_CAT_TABLE = "create table " + STUFF_CAT_TABLE_NAME + "(" +
            "_"+ CATEGORY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cat_nm TEXT NOT NULL" +
            ");";

    public static final String CREATE_STUFF_LOG_TABLE = "create table " + STUFF_LOG_TABLE_NAME + "(" +
            "_"+ LOG_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FOREIGN KEY("+ STUFF_ID +") REFERENCES (_"+ STUFF_ID +"), " +
            "ret_date DATE NOT NULL, " +
            "u_fname TEXT NOT NULL, " +
            "u_lname TEXT NOT NULL " +
            ");";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_STUFF_CAT_TABLE);
        sqLiteDatabase.execSQL(CREATE_STUFF_TABLE);
        sqLiteDatabase.execSQL(CREATE_STUFF_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUFF_CAT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUFF_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUFF_LOG_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
