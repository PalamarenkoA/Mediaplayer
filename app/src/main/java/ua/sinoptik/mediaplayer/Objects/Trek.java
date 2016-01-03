package ua.sinoptik.mediaplayer.Objects;

/**
 * Created by Админ on 02.01.2016.
 */
public class Trek {

   long duration;
   int id;
   String title;
   String artist;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbume() {
        return albume;
    }

    public void setAlbume(String albume) {
        this.albume = albume;
    }

    String albume;
}
