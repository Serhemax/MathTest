package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

	public static final String MAP = "Map";
	public static final String SKIFS = "skifs";

	public static final String PLATFORM = "Platform";
	public static final String PLATFORM_GROUND1 = "ground1";
	public static final String PLATFORM_GROUND2 = "ground2";
	public static final String PLATFORM_GROUND3 = "ground3";
	public static final String PLATFORM_GROUND4 = "ground4";
	public static final String PLATFORM_ROCK1 = "rock1";
	public static final String PLATFORM_CLOUD = "cloud";

	public static final String DOOR_OPEN = "door/open_door";
	public static final String DOOR_CLOSE = "door/close_door";

	public static final String MENU = "menu";
	public static final String MENU_BUTTON = "menu_button";
	public static final String EXIT = "exit";
	public static final String PLAY = "play";
	public static final String RESTART = "restart";
	public static final String RULES = "rules";

	public static final String RULES_BG = "rules_bg";
	public static final String TEXT = "text";
	public static final String BACK = "back";

	public static final String VOLUME_ON = "volume_on";
	public static final String VOLUME_OFF = "volume_off";
	public static final String MUSIC_ON = "music_on";
	public static final String MUSIC_OFF = "music_off";

	public static final String STAY = "stay";
	public static final String LEFT1 = "left1";
	public static final String LEFT2 = "left2";
	public static final String LEFT3 = "left3";
	public static final String LEFT4 = "left4";
	public static final String LEFT5 = "left5";
	public static final String RIGHT1 = "right1";
	public static final String RIGHT2 = "right2";
	public static final String RIGHT3 = "right3";
	public static final String RIGHT4 = "right4";
	public static final String RIGHT5 = "right5";
	
	public static final String UPJS = "Upjs";

	/// this are all function to quick get image, but from different platforms

	public static BufferedImage GetSpriteAtlas(String filename) {
		InputStream is = LoadSave.class.getResourceAsStream("/" + filename + ".png");

		return SetImage(is);
	}

	public static BufferedImage GetPlatformAtlas(String filename) {

		InputStream is = LoadSave.class.getResourceAsStream("/Platforms/" + filename + ".png");
		return SetImage(is);
	}

	public static BufferedImage GetPlayerAtlas(String filename) {

		InputStream is = LoadSave.class.getResourceAsStream("/player/" + filename + ".png");
		return SetImage(is);
	}

	public static BufferedImage GetMenuAtlas(String filename) {

		InputStream is = LoadSave.class.getResourceAsStream("/Menu/" + filename + ".png");
		return SetImage(is);
	}

	public static BufferedImage GetMenuMoveAtlas(String filename) {

		InputStream is = LoadSave.class.getResourceAsStream("/Menu/" + filename + "_move.png");

		return SetImage(is);
	}

	private static BufferedImage SetImage(InputStream is) {
		BufferedImage img = null;
		try {

			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

}
