package ua.sinoptik.mediaplayer;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class MusikPlay extends Fragment {
    final String LOG_TAG = "myLogs";
    public interface onItemClickListener {
        public void itemClick(int position);
    }

    ListView listView;
    File[] trek;
    AudioList audioList;
    BoxAdapter boxAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final onItemClickListener listener = (onItemClickListener) getActivity();

        View v = inflater.inflate(R.layout.fragment_musik_play, container, false);
        audioList = new AudioList();
        MusikFilter musikFilter = new MusikFilter("");
        if (FileManegerM.LISTMUSIK != null) {
            trek = FileManegerM.LISTMUSIK.listFiles(musikFilter);
        }
        audioList = createListObj(trek);

        boxAdapter = new BoxAdapter(v.getContext(), audioList,-1);
        boxAdapter.notifyDataSetChanged();

        listView = (ListView) v.findViewById(R.id.listView2);
        listView.setAdapter(boxAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.itemClick(position);
                boxAdapter.Up(position);


            }
        });

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "1 " + getActivity());

    }
    private String time(long milisek){
        String time;
        if(new Date(milisek).getMinutes()<10){
            if(new Date(milisek).getSeconds()<10){
                time ="0"+new Date(milisek).getMinutes()+":0"+new Date(milisek).getSeconds();
            }else{
                time ="0"+new Date(milisek).getMinutes()+":"+new Date(milisek).getSeconds();
            }
        }else{
            if(new Date(milisek).getSeconds()<10){
                time =new Date(milisek).getMinutes()+":0"+new Date(milisek).getSeconds();
            }else{
                time =new Date(milisek).getMinutes()+":"+new Date(milisek).getSeconds();
            }
        }
        return time;}
    private AudioList createListObj(File[] folder) {
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> artist = new ArrayList<>();
        ArrayList<String> album = new ArrayList<>();
        ArrayList<String> duration = new ArrayList<>();
        ContentResolver contentResolver = MusicList.CONTEXT.getContentResolver();
      AudioList  audioList = new AudioList();
        if (folder == null) {
            audioList.setAllfile(true);
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                // query failed, handle error.
            } else if (!cursor.moveToFirst()) {
                // no media on the device
            } else {
                int titleColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.TITLE);
                int durationColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int artistColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int idColumn = cursor
                        .getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                do {

                    int thisId = cursor.getInt(idColumn);
                    long thisduration = cursor.getLong(durationColumn);
                    String thisArtist = cursor.getString(artistColumn);
                    String thisAlbum = cursor.getString(albumColumn);
                    String thisTitle = cursor.getString(titleColumn);
                    duration.add(time(thisduration));



                    album.add(thisAlbum);
                    artist.add(thisArtist);
                    id.add(thisId);
                    title.add(thisTitle);



                    audioList.setId(id);
                    audioList.setDuration(duration);
                    audioList.setAlbume(album);
                    audioList.setArtist(artist);
                    audioList.setTitle(title);

                } while (cursor.moveToNext());
            }
            return audioList;
        } else {
            audioList.setAllfile(false);
            for (int i = 0; i < folder.length; i++) {
                MediaFile oMediaFile = new MP3File(folder[i]);
                MediaPlayer mediaP = new MediaPlayer();

                try {
                    mediaP.setDataSource(String.valueOf(folder[i]));
                    mediaP.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaP.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    title.add(folder[i].getName().substring(0, folder[i].getName().length()-4));
                    id.add(i);
                    duration.add(time(mediaP.getDuration()));
                    Log.d("seek", "id -" + mediaP.getDuration());
                    album.add(oMediaFile.getID3V2Tag().getAlbum());
                    artist.add(oMediaFile.getID3V2Tag().getArtist());

                } catch (ID3Exception e) {
                    e.printStackTrace();
                }


            }
            audioList.setDuration(duration);
            audioList.setAlbume(album);
            audioList.setArtist(artist);
            audioList.setTitle(title);
            audioList.setId(id);
        }
        return audioList;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Context context = activity;


    }


}
