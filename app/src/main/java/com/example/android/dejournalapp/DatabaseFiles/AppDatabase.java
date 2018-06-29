package com.example.android.dejournalapp.DatabaseFiles;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.android.dejournalapp.AddJournal;

/**
 * Created by DELL PC on 6/25/2018.
 */
@Database(entities = {AddJournal.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    private static String DATABASE_NAME = "JournalEnteries";
    private static Object LOCK = new Object();


    public static AppDatabase getInstance(Context context) {

        if (appDatabase == null) {
            synchronized (LOCK) {
                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME).build();

            }

        }
        return appDatabase;
    }

  public abstract AddJournalDAO addJournalDAO();
}
