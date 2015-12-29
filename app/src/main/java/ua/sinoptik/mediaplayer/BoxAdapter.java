package ua.sinoptik.mediaplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Админ on 27.12.2015.
 */
public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    AudioList audioList;
    int play;

    BoxAdapter(Context context, AudioList audioList,int play) {
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

  public void Up(int pos){
      this.play = pos;
      this.notifyDataSetChanged();
  }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        ((TextView) view.findViewById(R.id.size)).setText(String.valueOf(audioList.getDuration().get(position)));
        ((TextView) view.findViewById(R.id.name)).setText(audioList.getTitle().get(position));
        ((TextView) view.findViewById(R.id.artist)).setText(audioList.getArtist().get(position));
        ((TextView) view.findViewById(R.id.album)).setText(audioList.getAlbume().get(position));
        if(position == play){
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_action);}
        else{
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.ic_stop);
        }

        return view;
    }
    // пункт списка






}