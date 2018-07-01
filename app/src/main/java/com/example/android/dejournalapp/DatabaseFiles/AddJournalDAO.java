package com.example.android.dejournalapp.DatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by DELL PC on 6/25/2018.
 */
@Dao
public interface AddJournalDAO {

    @Query("Select * from AddjournalEntry Order by id DESC")
    LiveData<List<AddJournal>>loadAllNotes();

    @Insert
    void insertEntry(AddJournal addJournal);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournalEntry(AddJournal addJournal);

    @Delete
    void delete(AddJournal addJournal);

    @Query("Select * from AddjournalEntry where id = :id")

   LiveData <AddJournal> loadTaskById(int id);

}
