package com.example.android.dejournalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.dejournalapp.DatabaseFiles.AddJournal;
import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.provider.Settings.System.DATE_FORMAT;

public class AddTaskActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private EditText editText;
    public static String EXTRA_ID="extraid";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private DatabaseReference databaseReference;

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = (EditText)findViewById(R.id.edit_text_save_entry);
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
         currentUser = mAuth.getCurrentUser();

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
            final String note = editText.getText().toString();
            final Date date = new Date();

            final AddJournal addJournal = new AddJournal(note,date);
            AppExecutors.getAppExecutors().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(mTaskId==DEFAULT_TASK_ID){
                        //saving locally
                        appDatabase.addJournalDAO().insertEntry(addJournal);

                        //persisting to firebase database

                        createNoteInDb(currentUser.getUid(),note);
                    }
                    else {

                        addJournal.setId(mTaskId);
                        appDatabase.addJournalDAO().updateJournalEntry(addJournal);
                        updateNote(note);
                    }
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNote(String note) {
        Map<String,Object> update =  new HashMap<>();
        update.put("note",note);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("NOTES");
        databaseReference.child(currentUser.getUid()).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"update to Firebase Successful",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createNoteInDb(String uid, String note) {
    databaseReference = FirebaseDatabase.getInstance().getReference().child("NOTES");
    AddJournal addJournal = new AddJournal(note);
    databaseReference.child(uid).setValue(addJournal).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(getApplicationContext(),"online Sync sucessful",Toast.LENGTH_LONG).show();
        }
    });




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }


}
