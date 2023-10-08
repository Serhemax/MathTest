package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import audio.AudioPlayer;
import buttons.Button;
import main.Game;
import utilz.LoadSave;

public class Menu extends State implements StateMethods {

	private BufferedImage menu;

	private List<Button> buttons;

	private static boolean firstTime = true;

	private boolean paused = true;

	private boolean mutedSong, mutedVolume;

	public Menu(Game game) {
		super(game);

		GetMenuImage();

		InnitButtons();
	}

	private void GetMenuImage() {
		menu = LoadSave.GetMenuAtlas(LoadSave.MENU);
	}

	private void InnitButtons() {
		buttons = new ArrayList<Button>();

		int sizeButton = (int) (64);

		int gameWidth = Game.GAME_WIDTH / 2;
		int gameHeight = Game.GAME_HEIGHT / 2;

		buttons.add(new Button(gameWidth - sizeButton * 2, gameHeight - sizeButton * 2, sizeButton * 4, sizeButton * 4,
				LoadSave.PLAY));
		String img = null;
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				img = LoadSave.EXIT;
				break;
			case 1:
				img = LoadSave.RESTART;
				break;
			case 2:
				img = LoadSave.RULES;
				break;
			}

			buttons.add(new Button(gameWidth + (int) (100 - i * 100) - sizeButton / 2,
					gameHeight + (int) (150) - sizeButton / 2, sizeButton, sizeButton, img));
		}

		buttons.add(new Button(gameWidth - (int) (50) - sizeButton / 2,
				gameHeight - (int) (150) - sizeButton / 2, sizeButton, sizeButton, LoadSave.VOLUME_ON));

		int musicButtonSize = (sizeButton - sizeButton / 8);

		buttons.add(new Button(gameWidth + (int) (50) - musicButtonSize / 2,
				gameHeight - (int) (150) - musicButtonSize / 2, musicButtonSize, musicButtonSize,
				LoadSave.MUSIC_ON));
	}

	@Override
	public void update() {
		for (Button b : buttons)
			b.update();

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(menu, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		for (Button b : buttons)
			b.draw(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// if mouse pressed but not released, then we only change an image to pressed
		// button
		for (Button b : buttons)
			if (isIn(e, b))
				b.setMousePressed(true);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (int i = 0; i < buttons.size(); i++) {
			if (isIn(e, buttons.get(i))) {
				if (buttons.get(i).isMousePressed())
					// if mouse released in button, then we change state of a game
					applyGameState(i);
				break;
			}
		}
		resetButtons();
	}

	public void applyGameState(int i) {
		game.getAudioplayer().playEffect(AudioPlayer.BUTTON);
		switch (i) {

		case Button.PLAY:

			paused = false;

			game.getPlaying().getPlayer().resetBoolean();

			GameState.state = GameState.PLAYING;

			game.getAudioplayer().playSong(game.getAudioplayer().BG_GAME);

			if (firstTime)
				firstTime = false;

			break;

		case Button.EXIT:
			GameState.state = GameState.QUIT;
			break;

		case Button.RESTART:
			if (firstTime)
				;
			else {
				GameState.state = GameState.RESTART;
				game.getAudioplayer().playSong(game.getAudioplayer().BG_GAME);
			}
			break;

		case Button.RULES:
			GameState.state = GameState.RULES;
			break;
		case Button.MUSIC:

			// change image when clicked and mute or unmute music

			mutedSong = !mutedSong;
			int sizeButton = (int) (64);

			int gameWidth = Game.GAME_WIDTH / 2;
			int gameHeight = Game.GAME_HEIGHT / 2;

			int musicButtonSize = (sizeButton - sizeButton / 8);

			if (mutedSong) {
				buttons.get(i).setShape(gameWidth + (int) (50) - musicButtonSize / 2,
						gameHeight - (int) (150) - sizeButton / 2, sizeButton, sizeButton);
				buttons.get(i).newImage(LoadSave.MUSIC_OFF);

			} else {
				buttons.get(i).setShape(gameWidth + (int) (50) - musicButtonSize / 2,
						gameHeight - (int) (150) - musicButtonSize / 2, musicButtonSize, musicButtonSize);
				buttons.get(i).newImage(LoadSave.MUSIC_ON);
			}

			game.getAudioplayer().muteSongs();
			break;
		case Button.VOLUME:

			// change image when clicked and mute or unmute effect

			mutedVolume = !mutedVolume;
			if (mutedVolume)
				buttons.get(i).newImage(LoadSave.VOLUME_OFF);
			else
				buttons.get(i).newImage(LoadSave.VOLUME_ON);

			game.getAudioplayer().muteEffects();

			break;

		}
	}

	public void mouseMoved(MouseEvent e) {

		for (Button b : buttons)
			b.setMouseOver(false);

		for (Button b : buttons)
			if (isIn(e, b))
				b.setMouseOver(true);

	}

	private void resetButtons() {
		for (Button b : buttons)
			b.resetBools();

	}

	@Override
	public void KeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// go to game

			paused = false;

			game.getPlaying().getPlayer().resetBoolean();

			GameState.state = GameState.PLAYING;

			game.getAudioplayer().playSong(game.getAudioplayer().BG_GAME);

			if (firstTime)
				firstTime = false;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public static void notFirstTime() {
		firstTime = false;
	}

	public static boolean IsFirstTime() {
		return firstTime;
	}

}
