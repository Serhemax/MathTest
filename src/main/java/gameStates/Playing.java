package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import Map.Door;
import Map.Map;
import audio.AudioPlayer;
import buttons.Button;
import entites.Numbers;
import entites.Platform;
import entites.Player;
import main.Game;
import utilz.Camera;
import utilz.LoadSave;

public class Playing extends State implements StateMethods {

	private Menu menu;
	private Button menuButton;

	public Playing(Game game) {
		super(game);

		menu = new Menu(game);
		initClasses();

	}

	private Map map;
	private Door door;

	private Player player;
	private Camera camera;

	private List<Platform> platforms;
	private Numbers numbers;

	private void initClasses() {

		map = new Map();

		door = new Door(map.getWidth() - 125 + 60, map.getHeight() - 270, 125, 300);

		player = new Player(game.getGamePanel(), 100, (Game.GAME_HEIGHT - 111), 70, 111);

		camera = new Camera(player);

		platforms = new ArrayList<Platform>();

		Platform.FillPlatforms(platforms, map);

		numbers = new Numbers(platforms);

		menuButton = new Button(Game.GAME_WIDTH - 64, Game.GAME_HEIGHT - 64, 64, 64, LoadSave.MENU_BUTTON);

	}

	@Override
	public void update() {
		if (!menu.isPaused()) {
			player.update(platforms, map, game.getAudioplayer());

			camera.update(map);

			numbers.update(game, door, map, player);
		}
		menuButton.update();
	}

	@Override
	public void draw(Graphics g) {

		if (!menu.isPaused()) {
			map.draw(g, camera.getOfSetX(), camera.getOfSetY());

			for (int i = 0; i < platforms.size(); i++)
				platforms.get(i).draw(g, camera.getOfSetX(), camera.getOfSetY(), this);

			player.draw(g, camera.getOfSetX(), camera.getOfSetY());

			map.drawSkifs(g, camera.getOfSetX(), camera.getOfSetY());

			for (int i = 0; i < platforms.size(); i++)
				platforms.get(i).drawCloudAndRocks(g, camera.getOfSetX(), camera.getOfSetY());

			numbers.draw(g, camera.getOfSetX(), camera.getOfSetY(), door);

			menuButton.draw(g);
		} else {
			game.getAudioplayer();
			game.getAudioplayer().playSong(AudioPlayer.BG_MENU);
			GameState.state = GameState.MENU;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuButton)) {
			menuButton.setMousePressed(true);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuButton))
			if (menuButton.isMousePressed()) {

				// go to menu

				menu.setPaused(true);
				game.getAudioplayer();
				game.getAudioplayer().playSong(AudioPlayer.BG_MENU);
				game.getAudioplayer().playEffect(AudioPlayer.BUTTON);
				GameState.state = GameState.MENU;

				Menu.notFirstTime();

			}
		menuButton.resetBools();

		// if click on duck, then duck gets scary
		if (player.getHitbox().contains(e.getX() + camera.getOfSetX(), e.getY() + camera.getOfSetY())) {
			game.getAudioplayer();
			game.getAudioplayer().playEffect(AudioPlayer.SCARY_DUCK);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void KeyPressed(KeyEvent e) {

		boolean firstQuack = true;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.left = true;

			break;
		case KeyEvent.VK_LEFT:
			player.left = true;

			break;
		case KeyEvent.VK_D:
			player.right = true;

			break;
		case KeyEvent.VK_RIGHT:
			player.right = true;

			break;

		case KeyEvent.VK_SPACE:
			player.jump = true;

			break;

		case KeyEvent.VK_ESCAPE:

			// same as menu button

			menu.setPaused(true);
			game.getAudioplayer().playSong(AudioPlayer.BG_MENU);
			game.getAudioplayer().playEffect(AudioPlayer.BUTTON);
			GameState.state = GameState.MENU;

			Menu.notFirstTime();

			break;

		}

		for (int i = 0; i < numbers.getNumbers().size(); i++) {
			if (player.getHitbox().intersects(numbers.getNumbers().get(i).getHitbox())) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_EQUALS: // plus

					numbers.sum += numbers.GetValue(i);

					changeOfNumbers(i, firstQuack);

					break;
				case KeyEvent.VK_MINUS:

					numbers.sum -= numbers.GetValue(i);

					changeOfNumbers(i, firstQuack);

					break;

				}
			}
		}

		for (int i = 0; i < platforms.size(); i++) {
			if (player.OnPlatform(platforms.get(i))) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_S:

					player.setY(platforms.get(i).getY() - player.getHeight() + 5);

					break;
				}
			}
		}

	}

	private void changeOfNumbers(int i, boolean firstQuack) {
		// to check when all numbers had been used
		numbers.sumAll -= numbers.GetValue(i);

		// duck quack when get number
		if (numbers.GetValue(i) != 0 && firstQuack) {
			game.getAudioplayer().playEffect(AudioPlayer.QUACK);
			firstQuack = false;
		} else
			firstQuack = true;

		// we can`t delete number, so we set value of number to zero and make it
		// transparent(in numbers update)
		numbers.setValueToZero(i);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_A:
			player.left = false;

			break;
		case KeyEvent.VK_D:
			player.right = false;

			break;

		case KeyEvent.VK_LEFT:
			player.left = false;

			break;
		case KeyEvent.VK_RIGHT:
			player.right = false;

		case KeyEvent.VK_SPACE:
			player.jump = false;

			break;

		case KeyEvent.VK_E:

			game.getAudioplayer().playEffect(AudioPlayer.QUACK);

			break;

		case KeyEvent.VK_R:
			// Restart the game
			resetAll();
			game.getAudioplayer().playSong(AudioPlayer.BG_GAME);
			;

			break;
		}

	}

	public Menu getMenu() {
		return menu;
	}

	public Player getPlayer() {
		return player;
	}

	public Numbers getNumbers() {
		return numbers;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void windowFocusLost() {
		player.resetBoolean();

	}

	public void resetAll() {
		initClasses();
		menu.setPaused(false);

		player.resetAll(map);
	}

}
