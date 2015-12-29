package ua.sinoptik.mediaplayer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


public class FileManegerM extends Fragment {
    private File currentDirectory = new File("/sdcard/");
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> directoryList;
    private ListView listView;
    private ArrayList<String> fileList;
    private File fileNew;
    static File LISTMUSIK;

   @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_file_maneger_m, container, false);
        listView = (ListView) v.findViewById(R.id.listView3);
        createListAndClicable(currentDirectory);
        return v;
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
            arrayAdapter = new ArrayAdapter(MusicList.CONTEXT, android.R.layout.simple_list_item_1, directoryList);
            listView.setAdapter(arrayAdapter);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                if (new File(directoryList.get(position)).isDirectory()) {
                    LISTMUSIK = new File(directoryList.get(position));
                    createListAndClicable(LISTMUSIK);

                }
            }

        });
    }



}
