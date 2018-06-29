package com.example.android.dejournalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;

/**
 * Created by DELL PC on 6/26/2018.
 */

class AddTaskActivityViewModel extends ViewModel {

    private LiveData<AddJournal> addJournalLiveData;

    public AddTaskActivityViewModel(int taskid, AppDatabase appDatabase) {
      addJournalLiveData=appDatabase.addJournalDAO().loadTaskById(taskid);
    }
public LiveData<AddJournal>getAddJournalLiveData(){
        return addJournalLiveData;

}
}
