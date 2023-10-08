package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameStates.GameState;
import main.GamePanel;

public class KeyboardInput implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameState.state) {
		case MENU:
			gamePanel.getGame().getPlaying().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;

		case RULES:
			gamePanel.getGame().getRules().keyReleased(e);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameState.state) {
		case MENU:
			gamePanel.getGame().getPlaying().getMenu().KeyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().KeyPressed(e);
			break;

		case RULES:
			gamePanel.getGame().getRules().KeyPressed(e);
			break;

		default:
			break;
		}
	}
}
