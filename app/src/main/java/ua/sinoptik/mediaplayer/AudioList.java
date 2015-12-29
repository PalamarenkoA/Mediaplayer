package ua.sinoptik.mediaplayer;

import java.util.ArrayList;

/**
 * Created by Админ on 24.12.2015.
 */
public class AudioList {


    public ArrayList<String> getDuration() {
        return duration;
    }

    public void setDuration(ArrayList<String> duration) {
        this.duration = duration;
    }

    ArrayList<String> duration;
    ArrayList<Integer> id;
    ArrayList<String> title;
    ArrayList<String> artist;
    ArrayList<String> albume;
    boolean allfile;
    public ArrayList<String> getArtist() {
        return artist;
    }

    public void setArtist(ArrayList<String> artist) {
        this.artist = artist;
    }

    public ArrayList<String> getAlbume() {
        return albume;
    }

    public void setAlbume(ArrayList<String> albume) {
        this.albume = albume;
    }




    public boolean isAllfile() {
        return allfile;
    }

    public void setAllfile(boolean allfile) {
        this.allfile = allfile;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public int size(){

return id.size();}

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }
}
