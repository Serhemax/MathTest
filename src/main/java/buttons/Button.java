package buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Button {

	public static final int PLAY = 0;
	public static final int EXIT = 1;
	public static final int RESTART = 2;
	public static final int RULES = 3;
	public static final int VOLUME = 4;
	public static final int MUSIC = 5;

	private int x, y, width, height;
	private int index;

	private Rectangle bounds;

	private BufferedImage img;
	private BufferedImage imgMove;

	protected boolean mouseOver, mousePressed;

	public Button(int x, int y, int width, int height, String nameButton) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		LoadImage(nameButton);

		initBounds();

	}

	private void initBounds() {
		bounds = new Rectangle(x, y, width, height);

	}

	private void LoadImage(String nameButton) {
		img = LoadSave.GetMenuAtlas(nameButton);
		imgMove = LoadSave.GetMenuMoveAtlas(nameButton);
	}

	public void update() {
		index = 0;
		if (mouseOver) {
			index = 0;
		}
		if (mousePressed) {
			index = 1;
		}
	}

	public void draw(Graphics g) {
		if (index == 0)
			g.drawImage(imgMove, (int) x, (int) y, width, height, null);
		if (index == 1)
			g.drawImage(img, (int) x, (int) y, width, height, null);
	}

	public void newImage(String nameButton) {
		LoadImage(nameButton);
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setShape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}
