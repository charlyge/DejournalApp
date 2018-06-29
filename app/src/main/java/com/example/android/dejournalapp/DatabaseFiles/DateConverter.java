package com.example.android.dejournalapp.DatabaseFiles;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by DELL PC on 6/25/2018.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
