package com.adrian_971029.infobook.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adrian_971029.infobook.R;

public class InfoBookContentProvider extends ContentProvider {

    private InfoBookDbHelper infoBookDbHelper;

    private static final int VOLUME_INFO          = 500;
    private static final int VOLUME_INFO_WITH_ID  = 501;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        String with_id = "/#";
        String path_with_id = InfoBookContract.PATH_INFOBOOK + with_id;

        uriMatcher.addURI(InfoBookContract.AUTHORITY, InfoBookContract.PATH_INFOBOOK, VOLUME_INFO);
        uriMatcher.addURI(InfoBookContract.AUTHORITY, path_with_id, VOLUME_INFO_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        infoBookDbHelper = new InfoBookDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = infoBookDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;


        switch (match) {
            case VOLUME_INFO:
                cursor = db.query(InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = infoBookDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case VOLUME_INFO:
                long idVolumeInfo = db.insert(InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO, null, contentValues);
                if (idVolumeInfo > 0) {
                    returnUri = ContentUris.withAppendedId(InfoBookContract.InfoBookEntry.CONTENT_URI, idVolumeInfo);
                } else {
                    throw new android.database.SQLException(getContext().getString(R.string.error_inserir) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = infoBookDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);


        int volumeDeleted;

        switch (match) {
            case VOLUME_INFO_WITH_ID:
                String idVolume = uri.getPathSegments().get(1);
                volumeDeleted = db.delete(InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO, InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID + "=?", new String[]{idVolume});
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        if (volumeDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return volumeDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
