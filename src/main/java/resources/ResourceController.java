package resources;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ResourceController {
    private static Properties languagePack;
    private MediaPlayer mediaplayer;

    private List<String> playlist = new ArrayList<>(Arrays.asList("/sounds/Dance With me.mp3",
            "/sounds/Sthlm Sunset.mp3",
            "/sounds/Rick_Astley_-_Never_Gonna_Give_You_Up.mp3"));
    private int currentIndex = 2;
    private double currentVolume = 0.1;

    private AudioClip buzzer;

    private boolean mute;
//        private int playListIndex = 0;


    /**
     * Instantiates a language pack
     * Available languages:
     * - LanguagePack_en_EN
     * - LanguagePack_nl_NL
     */
        public static void setLanguagePack(String taal) {
            Properties fallback = new Properties();
            fallback.put("key","default");
            ResourceController.languagePack = new Properties(fallback);

            URL res = ResourceController.class.getResource("/languages/" + taal + ".properties");
            if (res == null) {
                System.out.println("ERROR -- ResourceController.setLanguagePack() -- languagePack not found: " + taal);
                return;
            }
            URI uri;
            try {
                uri = res.toURI();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }

            try(InputStream is = Files.newInputStream(Paths.get(uri))) {
                languagePack.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static String getLanguagePack(String key) {
            return languagePack.getProperty(key);
        }

        /**
         * Play music.
         */
        public void playMusic() {
            mediaplayer = new MediaPlayer(new Media(getClass().getResource(playlist.get(currentIndex)).toExternalForm()));
            mediaplayer.setVolume(currentVolume);
            mediaplayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaplayer.stop();
                    currentIndex++;
                    if (currentIndex == playlist.size()) {
                        currentIndex = 0;
                    }
                    playMusic();
                }
            });

            mediaplayer.play();
        }


        /**
         * Play sound effect.
         *
         * @param effect the effect
         */
        public void playSoundEffect(String effect) {
            if (!mute) {
                buzzer = new AudioClip(getClass().getResource("/sounds/lazer.mp3").toExternalForm());
                buzzer.setVolume(currentVolume);
                buzzer.play();
            }
        }

        /**
         * Change volume.
         *
         * @param value the value
         */
        public void changeVolume(double value) {
            currentVolume = value/100;
            if (mediaplayer != null) {
                mediaplayer.setVolume(value);
            }
            if (buzzer != null) buzzer.setVolume(value);
        }


        /**
         * Gets current volume.
         *
         * @return the current volume
         */
        public double getCurrentVolume() {
            return currentVolume;
        }

        /**
         * Is paused boolean.
         *
         * @return the boolean
         */
        public boolean isPaused() {
            if (mediaplayer.getStatus() == MediaPlayer.Status.PAUSED) return true;
            return false;
        }

        public void handleMute(boolean muted) {
            mute = muted;
            if (mute) {
                pauseMusic();
            } else {
                unPauseMusic();
            }
        }

        /**
         * Pause music.
         */
        public void pauseMusic() {
            if (mediaplayer != null) {
                mediaplayer.pause();
                mediaplayer.setVolume(0);
            }

            if (buzzer != null) {
                buzzer.stop();
                buzzer.setVolume(0);
            }
        }

        /**
         * Un pause music.
         */
        public void unPauseMusic() {
            if (buzzer != null){
                buzzer.setVolume(currentVolume);
            }
            if ( mediaplayer != null && mediaplayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaplayer.setVolume(currentVolume);
                mediaplayer.play();
            }
        }

        /**
         * Next song.
         */
//        public void nextSong() {
//            mediaplayer.stop();
//            playListIndex++;
//            if (playListIndex == playList.size()) {
//                playListIndex = 0;
//            }
//            mediaplayer = new MediaPlayer(new Media(getClass().getResource(playList.get(playListIndex)).toExternalForm()));
//            mediaplayer.setVolume(currentVolume);
//            playMusic();
//        }

        /**
         * Gets mediaplayer.
         *
         * @return the mediaplayer
         */
        public MediaPlayer getMediaplayer() {
            return mediaplayer;
        }

        /**
         * Stop world music.
         */
        public void stopMusic() {
            mediaplayer.dispose();
        }

    public boolean isMute() {
        return mute;
    }
}

