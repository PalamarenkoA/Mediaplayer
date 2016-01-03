package ua.sinoptik.mediaplayer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ua.sinoptik.mediaplayer.MusicList;
import ua.sinoptik.mediaplayer.R;


public class MusikPlay extends Fragment {
    final String LOG_TAG = "myLogs";
    public interface onItemClickListener {
        public void itemClick(int position);
    }
    public ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final onItemClickListener listener = (onItemClickListener) getActivity();

        View v = inflater.inflate(R.layout.fragment_musik_play, container, false);

        listView = (ListView) v.findViewById(R.id.listView2);
        listView.setAdapter(MusicList.boxAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.itemClick(position);
                listView.smoothScrollToPosition(position);


            }
        });

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "1 " + getActivity());

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Context context = activity;


    }


}
