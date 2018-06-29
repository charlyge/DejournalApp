package com.example.android.dejournalapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by DELL PC on 6/25/2018.
 */
@Entity(tableName = "AddjournalEntry")
public class AddJournal {

    private String note;
    private Date date;
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    public AddJournal(String note, Date date){
    this.note=note;
    this.date=date;

    }
    public AddJournal(int id ,String note, Date date){
        this.note=note;
        this.date=date;
        this.id =id;

    }
    public void setNote(String note){
        this.note =note;

    }
    public void setDate(Date date){
        this.date =date;

    }
    public void setId(int id){
        this.id =id;

    }
    public String getNote(){
        return note;

    }
    public Date getDate(){

        return date;
    }
    public int getId(){
        return id;

    }

}
