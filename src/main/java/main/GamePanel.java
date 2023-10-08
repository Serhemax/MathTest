package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInput;
import inputs.MouthInput;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


public class GamePanel extends JPanel {
	
	private Game game;

	public GamePanel(Game game) {
		
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.setDoubleBuffered(true);
		this.addKeyListener(new KeyboardInput(this));
		this.addMouseListener(new MouthInput(this));
		this.setFocusable(true);
		this.game = game;
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.draw(g);
	}


	public Game getGame() {
		return game;
	}
	
	
}
