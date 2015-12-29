package ua.sinoptik.mediaplayer;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Админ on 27.12.2015.
 */
public class MusikFilter implements FileFilter {

    String ext;

    MusikFilter(String ext) {
        this.ext = ext;
    }

    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return false;
        }

       if( pathname.getName().regionMatches(pathname.getName().length()-3,"mp3",0,3)){
            return true;
        }

          return false;
    }


}
