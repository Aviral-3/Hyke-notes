package com.example.hykenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayAdapter arrayAdapter;
    ListView listView;

    SharedPreferences sharedPreferences;
   static ArrayList<String>notes= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.hykenotes", Context.MODE_PRIVATE);
        listView=findViewById(R.id.listview);
        HashSet<String>set=(HashSet<String>)sharedPreferences.getStringSet("notes", null);
       if (set==null){
           notes.add("New Notes");

       }else{
           notes=new ArrayList(set);
       }


        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    Intent intent=new Intent(getApplicationContext(),notepages.class);
                    intent.putExtra("notesid",i);
                    startActivity(intent);

            }
        });

     listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

         @Override
         public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
             final int itemToDelete=i;
             new AlertDialog.Builder(MainActivity.this)
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .setTitle("Are you sure?")
                     .setMessage("Do you want to delete this note?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int i) {
                             notes.remove(itemToDelete);
                             arrayAdapter.notifyDataSetChanged();
                             HashSet<String> set=new HashSet<>(MainActivity.notes);
                             sharedPreferences.edit().putStringSet("notes",set).apply();
                         }
                     })
                     .setNegativeButton("No",null)
                     .show();

             return true;
         }
     });


}

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
         super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.item1){
            Intent i = new Intent(getApplicationContext(),notepages.class);
            startActivity(i);
            return  true;
        }
        return false;


    }
}