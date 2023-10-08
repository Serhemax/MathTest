package utilz;

import java.awt.Font;
import java.awt.Graphics;
import java.io.InputStream;
import entites.Entity;

public class Text extends Entity {

	private InputStream is;
	public static Font comicCAT = null;

	public Text(float x, float y, int width, int height) {
		super(x, y, width, height);
		getTextFont();
	}

	private void getTextFont() {
		try {
			is = ClassLoader.getSystemClassLoader().getResourceAsStream("Comic_CAT.ttf");

			comicCAT = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics g, float camX, float camY, String number) {

		g.setFont(comicCAT.deriveFont(Font.PLAIN, 58));
		g.drawString(number, (int) (x - camX), (int) (y - camY));

	}

	public void drawStatic(Graphics g, String number) {

		g.setFont(comicCAT.deriveFont(Font.PLAIN, 58));
		g.drawString(number, (int) (x), (int) (y));

	}
}
