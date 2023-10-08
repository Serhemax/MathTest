package entites;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	protected float x, y;
	protected int width, height;

	public BufferedImage stay, platform, map;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	protected Rectangle hitbox;
	
	
	

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		initHitBox();
	}
	
	

	private void initHitBox() {
		hitbox = new Rectangle((int) x, (int) y, width, height);

	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	protected void updateHitbox() {
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
