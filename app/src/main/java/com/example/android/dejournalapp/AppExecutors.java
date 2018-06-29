package com.example.android.dejournalapp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by DELL PC on 6/26/2018.
 */

public class AppExecutors {
    private static final Object LOCK = new Object();
    private static AppExecutors appExecutors;
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainthread;


    private AppExecutors( Executor diskIO,Executor networkIO,Executor mainthread){
     this.diskIO=diskIO;
     this.mainthread=mainthread;
     this.networkIO=networkIO;

    }
    public static AppExecutors getAppExecutors(){
   if(appExecutors==null){
  synchronized (LOCK){
appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());

  }

   }
  return appExecutors;
    }
    public Executor getDiskIO(){
        return diskIO;

    }

    public Executor getNetworkIO(){
        return networkIO;

    }
    public Executor getMainthread(){
        return mainthread;

    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable runnable) {
     mainThreadHandler.post(runnable);
        }
    }
}
