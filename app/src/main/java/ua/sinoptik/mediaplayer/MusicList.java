package ua.sinoptik.mediaplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.ArrayList;

public class MusicList extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
        MediaPlayer mediaPlayer;
        AudioManager am;
    Uri DATA_URI;
    int pos = 0;
    ListView listView;
    boolean play =false;
    AudioList audioList;
    Button playB;
    private SeekBar seekBar;
    private final Handler handler = new Handler();
    boolean click = false;
    int sek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mediaPlayer = new MediaPlayer();
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        listView = (ListView) findViewById(R.id.listMusik);
        audioList = createListObj();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seek();
        showMusicListAndClickable(audioList);
        playB = (Button) findViewById(R.id.play);


    }


    private void seek() {


        seekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekBar sb = (SeekBar) v;

                mediaPlayer.seekTo(sb.getProgress());
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.seekTo(seekBar.getProgress());
                    Log.d("seekkkkkk", "" + seekBar.getProgress());
                    sek = seekBar.getProgress();
                    Log.d("seekkkkkk", "" + mediaPlayer.getCurrentPosition());

                }

            }
        });


    }



    private void progresSeek(){
if(!click){

    seekBar.setProgress(mediaPlayer.getCurrentPosition());
    new Thread(new Runnable() {

        public void run() {

     progresSeek();
        }
    }).start();

}  else{

        }








    }
    private void showMusicListAndClickable(final AudioList audioList){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, audioList.getTitle());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                pos = position;

                playMusic(audioList, pos);

            }
        });

    }

    private void playMusic(AudioList audioList,int position){
        DATA_URI = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioList.getId().get(position));
        click = true;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, DATA_URI);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        playB.setBackgroundResource(R.drawable.ic_stop);
        play=false;
        click = false;
        progresSeek();

    }


















    public void onClick(View view) {
        if (mediaPlayer == null)
            return;
        switch (view.getId()) {
            case R.id.play:

                if(play){
                mediaPlayer.start();
                play=false;
                    playB.setBackgroundResource(R.drawable.ic_stop);
                }
                else{
                    mediaPlayer.pause();
                    play = true;
                    playB.setBackgroundResource(R.drawable.ic_action);
                }
                break;
//            case R.id.btnStop:
//                mediaPlayer.stop();
//                break;
            case R.id.back:
                pos -=1;
                if(pos<0)pos=0;
                Log.d("log", "position" + pos);
                playMusic(audioList, pos);
                break;
            case R.id.forvard:
                pos +=1;
                Log.d("log", "position" + pos);
                playMusic(audioList, pos);
                break;
//            case R.id.button3:
//                Log.d("log", "Playing " + mediaPlayer.isPlaying());
//                Log.d("log", "Time " + mediaPlayer.getCurrentPosition() + " / "
//                        + mediaPlayer.getDuration());
//                Log.d("log", "Looping " + mediaPlayer.isLooping());
//                Log.d("log",
//                        "Volume " + am.getStreamVolume(AudioManager.STREAM_MUSIC));
//                break;

        }
    }




























    private AudioList createListObj(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        AudioList audioList = new AudioList();
        ArrayList<Long> id = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
        } else {
            int titleColumn = cursor
                    .getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor
                    .getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {

                long thisId = cursor.getLong(idColumn);
                id.add(thisId);
                String thisTitle = cursor.getString(titleColumn);
                title.add(thisTitle);

                audioList.setId(id);
                audioList.setTitle(title);
            } while (cursor.moveToNext());
        }






        return audioList;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("log", "onPrepared");
        mp.start();
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

        Log.d("log", "onCompletion");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}