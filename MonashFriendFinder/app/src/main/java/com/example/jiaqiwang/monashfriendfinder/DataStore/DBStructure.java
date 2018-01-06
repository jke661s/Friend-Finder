package com.example.jiaqiwang.monashfriendfinder.DataStore;

import android.provider.BaseColumns;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class DBStructure {
    public static abstract class tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "sports";
        public static final String COLUMN_ID = "sportid";
        public static final String COLUMN_NAME = "sportname";
    }
}
