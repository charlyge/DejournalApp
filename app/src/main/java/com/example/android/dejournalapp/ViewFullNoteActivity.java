package com.example.android.dejournalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewFullNoteActivity extends AppCompatActivity {

    private int DEFAULTID=344;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_note);
        TextView textView = (TextView)findViewById(R.id.textView);
         intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText(text);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_resource_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = item.getItemId();
        if(position==R.id.edit_menu){

            if (intent.hasExtra(AddTaskActivity.EXTRA_ID)){

                //gets the unique id from AddjournalAdapter class
                int newid = intent.getIntExtra(AddTaskActivity.EXTRA_ID,DEFAULTID);
                Intent intent1 = new Intent(ViewFullNoteActivity.this,AddTaskActivity.class);
                intent1.putExtra(AddTaskActivity.EXTRA_ID,newid);
                startActivity(intent1);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
