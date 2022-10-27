package com.example.hykenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class notepages extends AppCompatActivity {
    int notesid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepages);
        EditText editT=findViewById(R.id.edit1);
        Intent intent=getIntent();
        notesid= intent.getIntExtra("notesid",-1);
        if(notesid!=-1){
            editT.setText(MainActivity.notes.get(notesid));
        }
        else{
             MainActivity.notes.add("");
            notesid=MainActivity.notes.size() -1;
        }
        editT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(notesid,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.hykenotes", Context.MODE_PRIVATE);
                HashSet<String>set=new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}