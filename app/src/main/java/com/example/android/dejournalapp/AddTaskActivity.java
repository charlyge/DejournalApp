package com.example.android.dejournalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private EditText editText;
    private Button button;
    public static String EXTRA_ID="extraid";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = (EditText)findViewById(R.id.edit_text_save_entry);
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }
        popuplateEditTextWithIntentData();
        }



    private void popuplateEditTextWithIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ID)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_ID,DEFAULT_TASK_ID);

                     ViewmodelFactory viewmodelFactory = new ViewmodelFactory(appDatabase,mTaskId);
                     final AddTaskActivityViewModel addTaskActivityViewModel = ViewModelProviders.of(this,viewmodelFactory)
                             .get(AddTaskActivityViewModel.class);
                        addTaskActivityViewModel.getAddJournalLiveData().observe(this, new Observer<AddJournal>() {
                            @Override
                            public void onChanged(@Nullable AddJournal addJournal) {
                               addTaskActivityViewModel.getAddJournalLiveData().removeObserver(this);
                                if (addJournal != null) {
                                    editText.setText(addJournal.getNote());
                                }
                            }
                        });

                    }

            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addjournal_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = item.getItemId();
        if(position==R.id.save_menu){

            //save Entry
            String note = editText.getText().toString();
            Date date = new Date();

            final AddJournal addJournal = new AddJournal(note,date);
            AppExecutors.getAppExecutors().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(mTaskId==DEFAULT_TASK_ID){
                        appDatabase.addJournalDAO().insertEntry(addJournal);


                    }
                    else {

                        addJournal.setId(mTaskId);
                        appDatabase.addJournalDAO().updateJournalEntry(addJournal);
                    }
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }


}
