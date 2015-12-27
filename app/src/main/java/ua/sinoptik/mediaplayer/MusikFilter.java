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
        String extension = getExtension(pathname);
        return extension.equals(ext);
    }

    private String getExtension(File pathname) {
        String filename = pathname.getPath();
        int i = filename.lastIndexOf('.');
        if ( i>0 && i<filename.length()-1 ) {
            return filename.substring(i+1).toLowerCase();
        }
        return "";
    }

}
