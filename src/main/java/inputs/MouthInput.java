package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameStates.GameState;
import main.GamePanel;

public class MouthInput implements MouseListener, MouseMotionListener {
	private GamePanel gamePanel;

	public MouthInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameState.state) {
		case MENU:
			gamePanel.getGame().getPlaying().getMenu().mousePressed(e);
			break;

		case PLAYING:
			gamePanel.getGame().getPlaying().mousePressed(e);
			break;
		case RULES:
			gamePanel.getGame().getRules().mousePressed(e);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameState.state) {
		case MENU:
			gamePanel.getGame().getPlaying().getMenu().mouseReleased(e);
			break;

		case PLAYING:
			gamePanel.getGame().getPlaying().mouseReleased(e);

			break;
		case RULES:
			gamePanel.getGame().getRules().mouseReleased(e);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameState.state) {
		case MENU:
			gamePanel.getGame().getPlaying().getMenu().mouseMoved(e);
			break;

		case PLAYING:
			gamePanel.getGame().getPlaying().mouseMoved(e);
			break;

		case RULES:
			gamePanel.getGame().getRules().mousePressed(e);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
