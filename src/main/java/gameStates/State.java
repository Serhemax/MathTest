package gameStates;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import buttons.Button;
import main.Game;

public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}

	public boolean isIn(MouseEvent e, Button button) {
		return button.getBounds().contains(e.getX(), e.getY());
	}

	public Game getGame() {
		return game;
	}

	public void setGameState(GameState state) {
		switch (state) {

		case MENU:
			game.getAudioplayer().playSong(AudioPlayer.BG_MENU);

		case PLAYING:
			game.getAudioplayer().playSong(AudioPlayer.BG_GAME);

		}
		GameState.state = state;
	}

}
