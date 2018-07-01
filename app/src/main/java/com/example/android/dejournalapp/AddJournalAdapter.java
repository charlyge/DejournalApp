package com.example.android.dejournalapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.dejournalapp.DatabaseFiles.AddJournal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL PC on 6/25/2018.
 */

public class AddJournalAdapter extends RecyclerView.Adapter<AddJournalAdapter.AddJournalViewHolder> {

     List<AddJournal> addJournalList = new ArrayList<>();
    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
     Context context;
    public AddJournalAdapter(Context context,List<AddJournal> addJournalList){
     this.context =context;
     this.addJournalList=addJournalList;

    }

    public AddJournalAdapter(Context context){
        this.context =context;

    }

    @NonNull
    @Override
    public AddJournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_item,parent,false);
        return new AddJournalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AddJournalViewHolder holder, int position) {
     final AddJournal addJournal= addJournalList.get(position);
     final int itemId = addJournal.getId();
     holder.noteTextView.setText(addJournal.getNote());
     holder.dateTextView.setText(dateFormat.format(addJournal.getDate()));


     holder.layout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             //Do Something when each layout is clicked
             String note = holder.noteTextView.getText().toString();
             Intent intent = new Intent(context,ViewFullNoteActivity.class);
             intent.putExtra(Intent.EXTRA_TEXT,note);
             intent.putExtra(AddTaskActivity.EXTRA_ID,itemId);
             context.startActivity(intent);
             //Toast.makeText(context,"hello position " + addJournal + "is clicked",Toast.LENGTH_LONG).show();
         }
     });
    }


    @Override
    public int getItemCount() {
        return addJournalList.size();
    }

    class AddJournalViewHolder extends RecyclerView.ViewHolder{
      TextView noteTextView;
      TextView dateTextView;
      View layout;
        public AddJournalViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            noteTextView = (TextView)itemView.findViewById(R.id.journal_note);
            dateTextView = (TextView)itemView.findViewById(R.id.date);
        }
    }
}
