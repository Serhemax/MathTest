package entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import Map.Map;
import gameStates.Playing;
import main.Game;
import main.GamePanel;
import utilz.LoadSave;

public class Platform extends Entity {

	private BufferedImage rock1, ground1, ground2, ground3, ground4, cloud;

	public final static int PLATFORM_WIDTH = 160;
	public final static int PLATFORM_HEIGHT = 30;

	private int index_ground;

	GamePanel gamePanel;

	public Platform(float x, float y, int width, int height, int index_ground) {
		super(x, y, width, height);

		this.index_ground = index_ground;

		getPlatformImage();

	}

	public void getPlatformImage() {

		platform = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM);

		ground1 = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_GROUND1);
		ground2 = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_GROUND2);
		ground3 = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_GROUND3);
		ground4 = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_GROUND4);
		rock1 = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_ROCK1);
		cloud = LoadSave.GetPlatformAtlas(LoadSave.PLATFORM_CLOUD);

	}

	/// function of random generated platforms

	public static void FillPlatforms(List<Platform> platforms, Map map) {

		/// this is if I want to change number of platforms

		int indexX = (int) Math.round(map.getWidth() / (Game.MAX_JUMP_IN_WIDTH + PLATFORM_WIDTH)) + 1; // 4
		int indexY = (int) Math.round(map.getHeight() / (Game.MAX_JUMP_IN_HEIGHT + PLATFORM_HEIGHT)); // 3

		for (int i = 0; i < indexY; i++) {
			for (int j = 0; j < indexX; j++) {
				
				

				/// generate random position of platform, where each platform will be in its own
				/// rectangle on map
				int platformX = (int) (((Math.random() * 200)) + (Game.MAX_JUMP_IN_WIDTH - 100))
						+ ((Game.MAX_JUMP_IN_WIDTH + 30) * j);

				int platformY = (int) ((int) (((Math.random() * 100)
						+ (Game.MAX_JUMP_IN_HEIGHT + PLATFORM_HEIGHT - 150))) + ((Game.MAX_JUMP_IN_HEIGHT - 70) * i));

				// if platform is to close to border, then

				if (!Collision.IsPlatformTouchBorder(platformX, platformY + Collision.HEIGHT_TO_GROUND + 150,
						PLATFORM_WIDTH, PLATFORM_HEIGHT, map)) {

					platformX = (int) ((Math.random() * 200) + (Game.MAX_JUMP_IN_WIDTH + PLATFORM_WIDTH))
							+ ((Game.MAX_JUMP_IN_WIDTH - 70) * j);

					platformY = (int) ((int) (((Math.random() * 100)
							+ (Game.MAX_JUMP_IN_HEIGHT + PLATFORM_HEIGHT - 150))) + ((Game.MAX_JUMP_IN_HEIGHT) * i));

				}

				platforms.add(new Platform((float) (platformX), (float) (platformY), PLATFORM_WIDTH, PLATFORM_HEIGHT,
						(int) (Math.random() * 4)));
				
			}
		}
	}

	// draw different type of platforms

	public void draw(Graphics g, float camX, float camY, Playing playing) {

		// draw ground platform if platform not in the sky and not very close to rocks;

		if ((y >= 400) && (x > 300 && x < 1900)) {

			switch (index_ground) {
			case 0:
				g.drawImage(ground1, (int) (x - camX - 10), (int) (y - camY - 60), PLATFORM_WIDTH + 20,
						PLATFORM_HEIGHT + 70, null);
				break;
			case 1:
				g.drawImage(ground2, (int) (x - camX - 10), (int) (y - camY - 25), PLATFORM_WIDTH + 20,
						PLATFORM_HEIGHT + 30, null);
				break;
			case 2:
				g.drawImage(ground3, (int) (x - camX - 10), (int) (y - camY - 30), PLATFORM_WIDTH + 20,
						PLATFORM_HEIGHT + 40, null);
				break;
			case 3:
				g.drawImage(ground4, (int) (x - camX - 10), (int) (y - camY - 40), PLATFORM_WIDTH + 20,
						PLATFORM_HEIGHT + 50, null);
				break;
			}

		}

	}

	// this function is separate because I want to draw cloud and rock platforms
	// over the player

	public void drawCloudAndRocks(Graphics g, float camX, float camY) {

		// if platform in the sky and not very close to rocks

		if ((y < 400) && (x > 300 && x < 1900))
			g.drawImage(cloud, (int) (x - camX - 10), (int) (y - camY - 40), PLATFORM_WIDTH + 40, PLATFORM_HEIGHT + 40,
					null);

		// if platform close to rocks - draw rock platform

		if (x <= 300 || x >= 1900) {

			g.drawImage(rock1, (int) (x - camX - 20), (int) (y - camY - 30), PLATFORM_WIDTH + 40, PLATFORM_HEIGHT + 40,
					null);
		}
	}

}
