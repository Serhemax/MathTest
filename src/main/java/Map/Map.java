package Map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utilz.LoadSave;

public class Map {
	BufferedImage map, skifs;

	public Map() {

		getMapImage();
	}

	public void getMapImage() {

		map = LoadSave.GetSpriteAtlas(LoadSave.MAP);
		skifs = LoadSave.GetSpriteAtlas(LoadSave.SKIFS);

	}

	public void draw(Graphics g, float camX, float camY) {

		g.drawImage(map, (int) -camX, (int) -camY, map.getWidth(), map.getHeight(), null);
	}

	public void drawSkifs(Graphics g, float camX, float camY) {
		g.drawImage(skifs, (int) -camX, (int) -camY, map.getWidth(), map.getHeight(), null);
	}

	public int getWidth() {
		return map.getWidth();
	}

	public int getHeight() {
		return map.getHeight();
	}
}
