package ua.sinoptik.mediaplayer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import ua.sinoptik.mediaplayer.Adapter.FileAdapter;
import ua.sinoptik.mediaplayer.MusicList;
import ua.sinoptik.mediaplayer.R;


public class FileManegerM extends Fragment {
    public static File LISTMUSIK;
    private File currentDirectory = new File("/sdcard/");
    private FileAdapter arrayAdapter;
    private ArrayList<String> directoryList;
    private ListView listView;
    private ArrayList<String> fileList;
    private Button back;

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
        back = (Button) v.findViewById(R.id.button);

        listView = (ListView) v.findViewById(R.id.listView3);
        createListAndClicable(currentDirectory);

        return v;
    }

    private void createListAndClicable(final File file){
        if( file.isDirectory()) {
            fileList = new ArrayList();
            directoryList = new ArrayList();
            for (int i = 0; i < file.listFiles().length; i++) {
                directoryList.add(String.valueOf(file.listFiles()[i]));
                if(file.listFiles()[i].isFile()){
                    fileList.add(String.valueOf(file.listFiles()[i]));
                }
            }
            arrayAdapter = new FileAdapter(MusicList.CONTEXT,directoryList);
            listView.setAdapter(arrayAdapter);


        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(file.getPath().equals("/")) ){
                 createListAndClicable(file.getParentFile());
                }
            }
        });

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
