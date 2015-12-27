package ua.sinoptik.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class FileManager extends AppCompatActivity {
    private File currentDirectory = new File("/sdcard/");
    private ArrayAdapter arrayAdapter;
    private  ArrayList<String>  directoryList;
    private ListView listView;
    private ArrayList<String> fileList;
    final private Context CONTEXT = this;
    private File fileNew;
    static File LISTMUSIK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        createListAndClicable(currentDirectory);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CONTEXT,MusicList.class));

            }
        });
    }
    private void createListAndClicable(File file){
        if( file.isDirectory()) {
            fileList = new ArrayList();
            directoryList = new ArrayList();
            for (int i = 0; i < file.listFiles().length; i++) {
                directoryList.add(String.valueOf(file.listFiles()[i]));
                if(file.listFiles()[i].isFile()){
                    fileList.add(String.valueOf(file.listFiles()[i]));
                }
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, directoryList);
            listView.setAdapter(arrayAdapter);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

    if(new File(directoryList.get(position)).isDirectory()){
                LISTMUSIK = new File(fileList.get(position));
                createListAndClicable(LISTMUSIK);

            }
        }

    });
}}
