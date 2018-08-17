package com.adrian_971029.infobook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.adrian_971029.infobook.model.ImageLinks;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderAccess {

    public static void inserirContentProvider(Context context, VolumeInfo volumeInfo) throws Exception {

        String jsonAuthor = new GsonBuilder().setPrettyPrinting().create().toJson(volumeInfo.getAuthors());
        String jsonCategories = new GsonBuilder().setPrettyPrinting().create().toJson(volumeInfo.getCategories());
        String jsonImages = new GsonBuilder().setPrettyPrinting().create().toJson(volumeInfo.getImageLinks());

        ContentValues values = new ContentValues();
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID,volumeInfo.getId());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_TITLE,volumeInfo.getTitle());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AUTHORS,jsonAuthor);
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER,volumeInfo.getPublisher());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER_DATE,volumeInfo.getPublishedDate());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_LANGUAGE,volumeInfo.getLanguage());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PAGE_COUNT,volumeInfo.getPageCount());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_CATEGORIES,jsonCategories);
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_DESCRIPTION,volumeInfo.getDescription());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_SHARE_LINK,volumeInfo.getInfoLink());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AVERAGE_RATING,volumeInfo.getAverageRating());
        values.put(InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_IMAGE_BOOK,jsonImages);

        context.getContentResolver().insert(InfoBookContract.InfoBookEntry.CONTENT_URI, values);

    }

    public static VolumeInfo buscar(Context context, String id) {

        InfoBookDbHelper infoBookDbHelper = new InfoBookDbHelper(context);
        final SQLiteDatabase db = infoBookDbHelper.getReadableDatabase();

        VolumeInfo volumeInfo;

        String[] columns = {InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_TITLE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AUTHORS,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER_DATE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_LANGUAGE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PAGE_COUNT,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_CATEGORIES,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_DESCRIPTION,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_SHARE_LINK,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AVERAGE_RATING,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_IMAGE_BOOK,
        };

        String where = InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID + "=?";
        String[] args = {id};

        Cursor cursor = db.query(InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO,columns,where,args,null,null,null);
        if (cursor.moveToFirst()) {

            String authors  = cursor.getString(cursor.getColumnIndex(columns[2]));
            String categories  = cursor.getString(cursor.getColumnIndex(columns[7]));
            String imageLink  = cursor.getString(cursor.getColumnIndex(columns[11]));

            ArrayList<String> listaAuthor = new Gson().fromJson(authors, new TypeToken<ArrayList<String>>() {}.getType());
            ArrayList<String> listaCategories = new Gson().fromJson(categories, new TypeToken<ArrayList<String>>() {}.getType());
            ImageLinks mImages = new Gson().fromJson(imageLink, new TypeToken<ImageLinks>() {}.getType());

            volumeInfo = new VolumeInfo();
            volumeInfo.setId(cursor.getLong(cursor.getColumnIndex(columns[0])));
            volumeInfo.setTitle(cursor.getString(cursor.getColumnIndex(columns[1])));
            volumeInfo.setAuthors(listaAuthor);
            volumeInfo.setPublisher(cursor.getString(cursor.getColumnIndex(columns[3])));
            volumeInfo.setPublishedDate(cursor.getString(cursor.getColumnIndex(columns[4])));
            volumeInfo.setLanguage(cursor.getString(cursor.getColumnIndex(columns[5])));
            volumeInfo.setPageCount(cursor.getInt(cursor.getColumnIndex(columns[6])));
            volumeInfo.setCategories(listaCategories);
            volumeInfo.setDescription(cursor.getString(cursor.getColumnIndex(columns[8])));
            volumeInfo.setInfoLink(cursor.getString(cursor.getColumnIndex(columns[9])));
            volumeInfo.setAverageRating(cursor.getDouble(cursor.getColumnIndex(columns[10])));
            volumeInfo.setImageLinks(mImages);

            return volumeInfo;

        }

        return null;
    }

    public static List<VolumeInfo> buscarTodos(Context context) {

        InfoBookDbHelper infoBookDbHelper = new InfoBookDbHelper(context);
        final SQLiteDatabase db = infoBookDbHelper.getReadableDatabase();

        List<VolumeInfo> listaVolumeInfo = new ArrayList<>();

        String[] columns = {InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_ID,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_TITLE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AUTHORS,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PUBLISHER_DATE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_LANGUAGE,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_PAGE_COUNT,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_CATEGORIES,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_DESCRIPTION,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_SHARE_LINK,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_AVERAGE_RATING,
                InfoBookContract.InfoBookEntry.COLUMN_VOLUME_INFO_IMAGE_BOOK,
        };

        Cursor cursor = db.query(InfoBookContract.InfoBookEntry.TABLE_NAME_VOLUME_INFO,columns,null,null,null,null,null);

        if (cursor.moveToFirst()) {

            do {

                String authors  = cursor.getString(cursor.getColumnIndex(columns[2]));
                String categories  = cursor.getString(cursor.getColumnIndex(columns[7]));
                String imageLink  = cursor.getString(cursor.getColumnIndex(columns[11]));

                ArrayList<String> listaAuthor = new Gson().fromJson(authors, new TypeToken<ArrayList<String>>() {}.getType());
                ArrayList<String> listaCategories = new Gson().fromJson(categories, new TypeToken<ArrayList<String>>() {}.getType());
                ImageLinks mImages = new Gson().fromJson(imageLink, new TypeToken<ImageLinks>() {}.getType());

                VolumeInfo volumeInfo = new VolumeInfo();
                volumeInfo.setId(cursor.getLong(cursor.getColumnIndex(columns[0])));
                volumeInfo.setTitle(cursor.getString(cursor.getColumnIndex(columns[1])));
                volumeInfo.setAuthors(listaAuthor);
                volumeInfo.setPublisher(cursor.getString(cursor.getColumnIndex(columns[3])));
                volumeInfo.setPublishedDate(cursor.getString(cursor.getColumnIndex(columns[4])));
                volumeInfo.setLanguage(cursor.getString(cursor.getColumnIndex(columns[5])));
                volumeInfo.setPageCount(cursor.getInt(cursor.getColumnIndex(columns[6])));
                volumeInfo.setCategories(listaCategories);
                volumeInfo.setDescription(cursor.getString(cursor.getColumnIndex(columns[8])));
                volumeInfo.setInfoLink(cursor.getString(cursor.getColumnIndex(columns[9])));
                volumeInfo.setAverageRating(cursor.getDouble(cursor.getColumnIndex(columns[10])));
                volumeInfo.setImageLinks(mImages);

                listaVolumeInfo.add(volumeInfo);

            }while (cursor.moveToNext());

            return listaVolumeInfo;

        }

        return null;
    }

    public static boolean deletar(VolumeInfo volumeInfo, String id, Context context) throws Exception {

        InfoBookDbHelper infoBookDbHelper = new InfoBookDbHelper(context);
        final SQLiteDatabase db = infoBookDbHelper.getReadableDatabase();

        String idString = id;
        Uri uri = InfoBookContract.InfoBookEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        return context.getContentResolver().delete(uri,null,null) > 0;

    }

}
