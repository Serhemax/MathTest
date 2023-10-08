package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import buttons.Button;
import main.Game;
import utilz.LoadSave;

public class Rules extends State implements StateMethods {

	private Button back;

	private BufferedImage bg, text;

	public Rules(Game game) {
		super(game);

		GetRulesImage();

		back = new Button(64, 64 - 32, 64, 64, LoadSave.BACK);

	}

	private void GetRulesImage() {
		bg = LoadSave.GetMenuAtlas(LoadSave.RULES_BG);
		text = LoadSave.GetMenuAtlas(LoadSave.TEXT);
	}

	public void draw(Graphics g) {

		g.drawImage(bg, 0, 0, Game.GAME_WIDTH + 10, Game.GAME_HEIGHT + 10, null);
		g.drawImage(text, 210, 210, Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2, null);

		back.draw(g);

	}

	@Override
	public void update() {
		back.update();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, back)) {
			back.setMousePressed(true);

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, back)) {
			if (back.isMousePressed()) {
				game.getAudioplayer().playEffect(AudioPlayer.BUTTON);
				GameState.state = GameState.MENU;
			}
		}

		resetButton();
	}

	public void mouseMoved(MouseEvent e) {
		back.setMouseOver(false);

		if (isIn(e, back)) {
			back.setMouseOver(true);

		}
	}

	private void resetButton() {
		back.resetBools();
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.state = GameState.MENU;
			if (!Menu.IsFirstTime())
				Menu.notFirstTime();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
