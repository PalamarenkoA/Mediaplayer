package ua.sinoptik.mediaplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ua.sinoptik.mediaplayer.Objects.AudioList;
import ua.sinoptik.mediaplayer.R;

/**
 * Created by Админ on 27.12.2015.
 */
public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    AudioList audioList;
    int play;


    public BoxAdapter(Context context, AudioList audioList, int play) {
        ctx = context;
        this.audioList = audioList;
        this.play = play;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

  public void UpAudioList(AudioList audioList){
      this.audioList = audioList;
      this.notifyDataSetChanged();
  }
  public void Up(int pos){
      this.play = pos;
      this.notifyDataSetChanged();
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        ((TextView) view.findViewById(R.id.size)).setText(time(audioList.getDuration().get(position)));
        ((TextView) view.findViewById(R.id.name)).setText(audioList.getTitle().get(position));
        ((TextView) view.findViewById(R.id.artist)).setText(audioList.getArtist().get(position));
        ((TextView) view.findViewById(R.id.album)).setText(audioList.getAlbume().get(position));
        if(position == play){
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_action);}
        else{
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_note);
        }

        return view;
    }
    // пункт списка






}