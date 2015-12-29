package ua.sinoptik.mediaplayer;


import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class MusicList extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,MusikPlay.onItemClickListener {
    MediaPlayer mediaPlayer;
    AudioManager am;
    Uri DataUri;
    int pos = 0;
    boolean play = false;
    AudioList audioList;
    Button playB;
    private SeekBar seekBar;
    boolean click = false;
    int sek;
    File[] trek;
    MusikFilter musikFilter;
    TextView duration;
    TextView size;
    Handler durationUp;
    static Context CONTEXT;
    FragmentTransaction fTrans;
    MusikPlay musikPlay;
    FileManegerM fileManegerM;
    boolean frags =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        musikPlay = new MusikPlay();
        fileManegerM = new FileManegerM();
        setContentView(R.layout.activity_music_list);
        fTrans = getFragmentManager().beginTransaction();
        CONTEXT = this;
        playB = (Button) findViewById(R.id.play);
        duration = (TextView) findViewById(R.id.duration);
        size = (TextView) findViewById(R.id.size);
        audioList = new AudioList();
        mediaPlayer = new MediaPlayer();
        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        musikFilter = new MusikFilter("хай");
        audioList = createListObj(trek);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekClik();
        showMusicFragment();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(frags){
                    if (FileManegerM.LISTMUSIK != null) {
                        trek = FileManegerM.LISTMUSIK.listFiles(musikFilter);
                    }
                    audioList = createListObj(trek);
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.linearLayout1, musikPlay);
                fTrans.commit();
                frags = false;
                }else{
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.linearLayout1, fileManegerM);
                    fTrans.commit();
                    frags =true;

                }
            }
        });


        durationUp = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
               duration.setText(time(mediaPlayer.getCurrentPosition()));
            };
        };
    }

    private void seekClik() {


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

                    sek = seekBar.getProgress();


                }

            }
        });


    }

    private void progresSeek() {
        if (!click) {
            durationUp.sendEmptyMessage(0);
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            new Thread(new Runnable() {

                public void run() {

                    progresSeek();
                }
            }).start();

        }
    }

    private void showMusicFragment() {

        fTrans.replace(R.id.linearLayout1, musikPlay);
        fTrans.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playMusic(AudioList audioList, int position) {
        if (audioList.isAllfile()) {
            DataUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioList.getId().get(position));
        } else {
            DataUri = Uri.fromFile(trek[new Integer(String.valueOf(audioList.getId().get(position)))]);

        }
        click = true;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaPlayer = new MediaPlayer();
        try {

            mediaPlayer.setDataSource(this, DataUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        playB.setBackgroundResource(R.drawable.ic_stop);
        play = false;
        click = false;
        progresSeek();

    }

    public void onClick(View view) {
        if (mediaPlayer == null)
            return;
        switch (view.getId()) {
            case R.id.play:
                if (play) {
                    mediaPlayer.start();
                    play = false;
                    playB.setBackgroundResource(R.drawable.ic_stop);
                } else {
                    mediaPlayer.pause();
                    play = true;
                    playB.setBackgroundResource(R.drawable.ic_action);
                }
                break;
//            case R.id.btnStop:
//                mediaPlayer.stop();
//                break;
            case R.id.back:
                pos -= 1;
                if (pos < 0) pos = 0;
                Log.d("log", "position" + pos);
                playMusic(audioList, pos);
                break;
            case R.id.forvard:
                pos += 1;
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
        ArrayList<String> duration = new ArrayList<>();
        ContentResolver contentResolver = MusicList.CONTEXT.getContentResolver();
        AudioList audioList = new AudioList();
        if (folder == null) {
            audioList.setAllfile(true);
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                // query failed, handle error.
            } else if (!cursor.moveToFirst()) {
                // no media on the device
            } else {

                int durationColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.DURATION);
                int idColumn = cursor
                        .getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                do {

                    int thisId = cursor.getInt(idColumn);
                    long thisduration = cursor.getLong(durationColumn);
                    duration.add(time(thisduration));
                    id.add(thisId);
                    audioList.setId(id);
                    audioList.setDuration(duration);


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

                    id.add(i);
                    duration.add(time(mediaP.getDuration()));
                    Log.d("seek", "id -" + mediaP.getDuration());





            }
            audioList.setDuration(duration);
            audioList.setId(id);
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
    public void itemClick(int position) {
        pos = position;
        size.setText(audioList.getDuration().get(pos));
        playMusic(audioList, pos);
    }
}