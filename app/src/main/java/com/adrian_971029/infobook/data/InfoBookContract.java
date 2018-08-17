package com.adrian_971029.infobook.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class InfoBookContract {

    public static final String AUTHORITY = "com.adrian_971029.infobook";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_INFOBOOK = "infobook";

    public static class InfoBookEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INFOBOOK).build();

        public static final String TABLE_NAME_VOLUME_INFO = "volume_info";
        public static final String COLUMN_VOLUME_INFO_ID = "volume_id";
        public static final String COLUMN_VOLUME_INFO_TITLE= "title";
        public static final String COLUMN_VOLUME_INFO_AUTHORS = "authors";
        public static final String COLUMN_VOLUME_INFO_PUBLISHER = "publisher";
        public static final String COLUMN_VOLUME_INFO_PUBLISHER_DATE = "publisher_date";
        public static final String COLUMN_VOLUME_INFO_LANGUAGE = "language";
        public static final String COLUMN_VOLUME_INFO_PAGE_COUNT = "page_count";
        public static final String COLUMN_VOLUME_INFO_CATEGORIES = "categories";
        public static final String COLUMN_VOLUME_INFO_DESCRIPTION = "description";
        public static final String COLUMN_VOLUME_INFO_SHARE_LINK = "share_link";
        public static final String COLUMN_VOLUME_INFO_AVERAGE_RATING = "average_rating";
        public static final String COLUMN_VOLUME_INFO_IMAGE_BOOK = "image_book";

    }

}
