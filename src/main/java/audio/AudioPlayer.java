package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.Game;

public class AudioPlayer {

	public static int BG_MENU = 0;
	public static int BG_GAME = 1;

	public static int BUTTON = 0;
	public static int QUACK = 1;
	public static int SCARY_DUCK = 2;
	public static int DOOR = 3;
	public static int LOSE = 4;
	public static int WIN = 5;

	private Clip[] songs, effects;
	private int currentSongID;
	private float volumeSongs = 0.7f;
	private float volumeEffects = 0.8f;

	private boolean songMute, effectsMute;

	private Game game;

	public AudioPlayer(Game game) {
		loadSongs();
		loadEffects();

		setVolumeEffects();
		setVolumeSongs();

		playSong(BG_MENU);

		this.game = game;
	}

	private void loadSongs() {
		String[] songNames = { "bg_menu", "bg_play" };
		songs = new Clip[songNames.length];
		for (int i = 0; i < songs.length; i++) {
			songs[i] = getClip(songNames[i]);
		}
	}

	private void loadEffects() {
		String[] effectsnames = { "button_sound", "quack", "scary_quack", "doorSound", "game_over", "win" };
		effects = new Clip[effectsnames.length];
		for (int i = 0; i < effects.length; i++) {
			effects[i] = getClip(effectsnames[i]);
		}
	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");

		AudioInputStream audio;

		try {

			audio = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);

			return clip;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void muteSongs() {
		this.songMute = !songMute;
		for (Clip s : songs) {
			BooleanControl control = (BooleanControl) s.getControl(BooleanControl.Type.MUTE);

			control.setValue(songMute);
		}
	}

	public void stopSong() {
		if (songs[currentSongID].isActive())
			songs[currentSongID].stop();
	}

	public void muteEffects() {
		this.effectsMute = !effectsMute;
		for (Clip e : effects) {
			BooleanControl control = (BooleanControl) e.getControl(BooleanControl.Type.MUTE);

			control.setValue(effectsMute);
		}
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();

	}

	public void playSong(int song) {
		stopSong();

		currentSongID = song;

		songs[currentSongID].setMicrosecondPosition(0);
		songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
	}

	private void setVolumeSongs() {
		for (Clip s : songs) {
			FloatControl gainControl = (FloatControl) s.getControl(FloatControl.Type.MASTER_GAIN);
			float on = gainControl.getMaximum();
			float off = gainControl.getMinimum();
			float range = on - off;
			float gain = (range * volumeSongs) + off;
			gainControl.setValue(gain);
		}
	}

	private void setVolumeEffects() {
		for (Clip e : effects) {
			FloatControl gainControl = (FloatControl) e.getControl(FloatControl.Type.MASTER_GAIN);
			float on = gainControl.getMaximum();
			float off = gainControl.getMinimum();
			float range = on - off;
			float gain = (range * volumeEffects) + off;
			gainControl.setValue(gain);
		}
	}

	public Game getGame() {
		return game;
	}

}
