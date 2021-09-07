package Splendor.data.soundEffect;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * This enumerate contains clips of the sound effect in the game
 * @author Yida Chen, Jacky Lin
 */
public enum SoundEffect {
    /** Coin clip represents sound clip of the coin being collected */
    COIN_CLIP("/coin_drop.wav"),
    /** Card flip clip represents sound clip of the card being flip */
    CARD_FLIP_CLIP("/card_flip.wav"),
    /** Card deal clip represents Sound clip of the card being dealt */
    CARD_DEAL_CLIP("/dealing_card.wav");

    /** the clip*/
    private Clip clip;

    /**
     * the constructor of the enumerator initialize the sound file
     * @param path the path to the sound file
     * @author Jacky Lin
     */
    SoundEffect(String path) { this.clip = getSoundClip(path); }

    /**
     * Get the sound clip from the audio file
     * @param path The path of the audio file
     * @return The sound clip of the audio file
     * @author Yida Chen
     */
    private Clip getSoundClip(String path) {
        try {
            InputStream soundFile = new FileInputStream(getClass().getResource("/Splendor/data/soundEffect").getPath() + path);
            InputStream bufferedIn = new BufferedInputStream(soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audio);
            return soundClip; }
        catch (UnsupportedAudioFileException e) { System.out.println("Unsupported audio file."); }
        catch (IOException e) { System.out.println("Exception when read in audio file."); }
        catch (LineUnavailableException e) { System.out.println("Unavailable line."); }
        catch (Exception e) { System.out.println("Unexpected exception when read in audio file."); }
        return null;
    }

    /**
     * The getter method of the clip of certain sound effect
     * @return Clips object represents the sound effect
     */
    public Clip getClip() { return clip; }
}
