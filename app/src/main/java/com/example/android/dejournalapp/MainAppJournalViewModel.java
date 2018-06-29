package com.example.android.dejournalapp;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;

import java.util.List;

/**
 * Created by DELL PC on 6/26/2018.
 */

public class MainAppJournalViewModel extends AndroidViewModel {
  private LiveData<List<AddJournal>> listLiveData;

    public MainAppJournalViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase =AppDatabase.getInstance(this.getApplication());
       listLiveData= appDatabase.addJournalDAO().loadAllNotes();
    }
    public LiveData<List<AddJournal>> getListLiveData(){

        return listLiveData;
    }
}
