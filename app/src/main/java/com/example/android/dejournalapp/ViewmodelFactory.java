package com.example.android.dejournalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;

/**
 * Created by DELL PC on 6/26/2018.
 */

public class ViewmodelFactory extends ViewModelProvider.NewInstanceFactory{
    private final AppDatabase appDatabase;
    private final int Taskid;


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskActivityViewModel(Taskid,appDatabase);
    }

    public ViewmodelFactory(AppDatabase appDatabase, int Taskid){

       this.appDatabase=appDatabase;
       this.Taskid=Taskid;
    }
}
