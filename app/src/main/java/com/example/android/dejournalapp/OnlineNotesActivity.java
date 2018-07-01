package com.example.android.dejournalapp;

import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.dejournalapp.DatabaseFiles.AddJournal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class OnlineNotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AddJournalAdapter addJournalAdapter;
    ArrayList<AddJournal> onlineArrayList= new ArrayList<>();
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_notes);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_online_notes);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        addJournalAdapter = new AddJournalAdapter(this,onlineArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addJournalAdapter);
        populateWithOnlineNotes();
    }

    public void populateWithOnlineNotes(){
        FirebaseDatabase.getInstance().getReference().child("NOTES").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             // Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
             // while (iterator.hasNext()) {

              //    DataSnapshot Snapshot = iterator.next();
                //  onlineArrayList.add(Snapshot.getValue(AddJournal.class));
              }
         // }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    addJournalAdapter.notifyDataSetChanged();
    }
}
