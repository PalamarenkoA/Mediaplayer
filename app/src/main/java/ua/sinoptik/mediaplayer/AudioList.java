package ua.sinoptik.mediaplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Админ on 24.12.2015.
 */
public class AudioList {

    ArrayList<Long> id;
    ArrayList<String> title;
    ArrayList<String> artist;
    ArrayList<String> albume;
    boolean allfile;
    ArrayList<File> fileList;
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



    public ArrayList<File> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<File> fileList) {
        this.fileList = fileList;
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
if(allfile = true){
return id.size();}else{
    return fileList.size();

}}

    public ArrayList<Long> getId() {
        return id;
    }

    public void setId(ArrayList<Long> id) {
        this.id = id;
    }
}
