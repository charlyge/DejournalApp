package com.example.android.dejournalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.dejournalapp.DatabaseFiles.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AddJournalAdapter addJournalAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(getApplication());
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        addJournalAdapter = new AddJournalAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addJournalAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        swipeToDelete();
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        setUpAppJournalViewModel();
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = item.getItemId();
        if (position == R.id.login_logout) {
           Intent intent = new Intent(MainActivity.this,LoginActivity.class);
           startActivity(intent);

        } else if (position == R.id.online_notes) {
     //will implement this functionality later
            Toast.makeText(this, "Online Notes", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpAppJournalViewModel() {

        MainAppJournalViewModel mainAppJournalViewModel = ViewModelProviders.of(this).get(MainAppJournalViewModel.class);
        mainAppJournalViewModel.getListLiveData().observe(this, new Observer<List<AddJournal>>() {
            @Override
            public void onChanged(@Nullable List<AddJournal> addJournals) {
                addJournalAdapter.addJournalList = addJournals;
                addJournalAdapter.notifyDataSetChanged();
            }
        });


    }


    public void swipeToDelete() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getAppExecutors().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<AddJournal> addJournals = addJournalAdapter.addJournalList;
                        appDatabase.addJournalDAO().delete(addJournals.get(position));
                    }
                });

            }
        }).attachToRecyclerView(recyclerView);
    }
}
