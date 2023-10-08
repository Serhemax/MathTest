package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import audio.AudioPlayer;
import gameStates.GameState;
import gameStates.Playing;
import gameStates.Rules;

public class Game implements Runnable {
	
	Dimension size
    = Toolkit.getDefaultToolkit().getScreenSize();

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;

	private AudioPlayer audioplayer;

	private int FPS = 120;
	private int UPS = 180;

	private Playing playing;
	private Rules rules;


	public final static int GAME_WIDTH = (int) (800);
	public final static int GAME_HEIGHT = (int) (800);

	public final static int MAX_JUMP_IN_WIDTH = 350;
	public final static int MAX_JUMP_IN_HEIGHT = 300;

	public Game() {
		gamePanel = new GamePanel(this);
		initClasses();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocusInWindow();
		startGameLoop();
	}

	private void initClasses() {

		audioplayer = new AudioPlayer(this);
		playing = new Playing(this);
		rules = new Rules(this);

	}

	public void update() {

		switch (GameState.state) {
		case MENU:
			playing.getMenu().update();
			break;

		case PLAYING:
			playing.update();
			break;
		case RULES:
			rules.update();
			break;
		default:
			break;

		}

	}

	public void draw(Graphics g) {

		switch (GameState.state) {
		case MENU:
			playing.getMenu().draw(g);
			break;

		case PLAYING:
			playing.draw(g);
			break;
		case QUIT:
			System.exit(0);
			break;
		case RESTART:
			playing.resetAll();
			GameState.state = GameState.PLAYING;
			break;
		case RULES:
			rules.draw(g);
			break;
		default:
			System.exit(0);
			break;

		}

	}

	public void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// GAME LOOP
	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;

//		long last = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		long lastTime = System.nanoTime();

//		int show_FPS = 0;
//		int show_UPS = 0;

		while (gameThread != null) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - lastTime) / timePerUpdate;
			deltaF += (currentTime - lastTime) / timePerFrame;
			lastTime = currentTime;

			if (deltaU >= 1) {

				// 1. UPDATE: update information of character posistion
				update();
				deltaU--;
				// show_UPS++;
			}
			if (deltaF >= 1) {

				// 2: DRAW: draw the screen with the update information
				gamePanel.repaint();

				deltaF--;
				// show_FPS++;
			}

//			if (System.currentTimeMillis() - last >= 1000) {
//				last = System.currentTimeMillis();
//				System.out.println("FPS: " + show_FPS + " || " + "UPS: " + show_UPS);
//				show_UPS = 0;
//				show_FPS = 0;
//			}
		}

	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Rules getRules() {
		return rules;
	}

	public AudioPlayer getAudioplayer() {
		return audioplayer;
	}

}
