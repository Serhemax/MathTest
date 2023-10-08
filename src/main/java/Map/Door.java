package Map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entites.Entity;
import utilz.LoadSave;

public class Door extends Entity {

	BufferedImage door_open, door_close;

	public Door(float x, float y, int width, int height) {
		super(x, y, width, height);

		getDoorStateImage();

	}

	public void getDoorStateImage() {
		door_open = LoadSave.GetSpriteAtlas(LoadSave.DOOR_OPEN);
		door_close = LoadSave.GetSpriteAtlas(LoadSave.DOOR_CLOSE);
	}

	public void drawOpen(Graphics g, float camX, float camY) {

		g.drawImage(door_open, (int) (x - camX), (int) (y - camY), width, height, null);
	}

	public void drawClose(Graphics g, float camX, float camY) {

		g.drawImage(door_close, (int) (x - camX), (int) (y - camY), width, height, null);
	}

	public void setOpenDoor(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(x + 70, y + 100, width - 70, height - 100);
	}

}
