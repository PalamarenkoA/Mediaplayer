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
        return true;
    }


}
