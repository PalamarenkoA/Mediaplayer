package ua.sinoptik.mediaplayer;

import java.util.ArrayList;

/**
 * Created by Админ on 24.12.2015.
 */
public class AudioList {

    ArrayList<Long> id;
    ArrayList<String> title;

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

public int size(){

return id.size();}

    public ArrayList<Long> getId() {
        return id;
    }

    public void setId(ArrayList<Long> id) {
        this.id = id;
    }
}
