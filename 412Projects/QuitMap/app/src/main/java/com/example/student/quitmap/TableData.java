package com.example.student.quitmap;

import android.provider.BaseColumns;

/**
 * Created by Alvin on 3/3/2016.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String DIARY_ID = "diary_id";
        public static final String LATLONG = "latlong";
        public static final String DIARY_CONTENT = "diary_content";
        public static final String CONTENT_TYPE = "content_type";
        public static final String DATABASE_NAME = "app_data";
        public static final String DIARY_ENTRY_TABLE_NAME = "diary_entries";
        public static final String TIME = "time";

    }

}
