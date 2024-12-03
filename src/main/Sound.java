package main;

import tiles.Tile;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    int currentSong = 0;

    public Sound() {
        soundURL[0] = setup("theme");
        soundURL[1] = setup("playerHurt");
        soundURL[2] = setup("pickupItem");
        soundURL[3] = setup("inGame");
    }

    public void setFile(int i) {

        if (currentSong == i && clip != null && clip.isActive()) {
            return;
        }

        setSong(i);

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {

        }
    }

    public void setSong(int i) {
        currentSong = i;
    }

    public int getCurrentSong() {
        return currentSong;
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public URL setup(String path) {
        return getClass().getResource("/sound/" + path + ".wav");
    }
}
