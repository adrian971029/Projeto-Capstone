package com.adrian_971029.infobook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InfoBookDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "infobook.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_VOLUME_INFO = "CREATE TABLE " +
            InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO + " (" +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID + " TEXT PRIMARY KEY, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_TITLE + " TEXT NOT NULL, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AUTHORS + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER_DATE + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_LANGUAGE + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PAGE_COUNT + " INTEGER, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_CATEGORIES + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_DESCRIPTION + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_SHARE_LINK + " TEXT, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AVERAGE_RATING + " REAL, " +
            InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_IMAGE_BOOK + " TEXT" +
            "); ";

    public InfoBookDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_VOLUME_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO);
    }

}
