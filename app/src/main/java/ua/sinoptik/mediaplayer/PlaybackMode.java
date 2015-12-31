package ua.sinoptik.mediaplayer;

/**
 * Created by Админ on 31.12.2015.
 */
public class PlaybackMode {
    public boolean isRandom() {
        return random;
    }

    boolean random = false;

    public boolean isLoop() {
        return loop;
    }

    boolean loop = false;


    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    public void setRandom(boolean random) {
        this.random = random;
    }
    public Integer Mode(){
        if(loop){
            return 1;
        }else{
            if(random){
                return 2;
            }
        }
    return 3;}
}
