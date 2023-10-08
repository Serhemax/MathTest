package entites;

import java.awt.Rectangle;

import Map.Map;

public class Collision {

	public static int HEIGHT_TO_GROUND = 35;

	// check if player is in border
	public static boolean CanMove(float x, float y, int width, int height, Map map) {

		if (!IsBorder(x + width, y + height - HEIGHT_TO_GROUND, map))
			if (!IsBorder(x, y + height - HEIGHT_TO_GROUND, map))
				return true;
		return false;
	}

	private static boolean IsBorder(float x, float y, Map map) {

		int MaxWidth = map.getWidth() - 60;
		int MaxHeight = map.getHeight();

		if (x <= 60 || x >= MaxWidth - 1 || y >= MaxHeight - HEIGHT_TO_GROUND)
			return true;
		return false;
	}

	// check if platform is in border
	private static boolean IsBorderForPlatform(float x, float y, Map map) {

		if (y < 0 || IsBorder(x, y, map))
			return true;
		return false;
	}

	public static boolean IsPlatformTouchBorder(float x, float y, int width, int height, Map map) {
		if (!IsBorderForPlatform(x, y, map))
			if (!IsBorderForPlatform(x + width, y + height - HEIGHT_TO_GROUND, map))
				if (!IsBorderForPlatform(x + width, y, map))
					if (!IsBorderForPlatform(x, y + height - HEIGHT_TO_GROUND, map))
						return true;
		return false;
	}

	public static boolean IsPlayerOnGround(Rectangle hitbox, Map map) {
		if (!IsBorder(hitbox.x, hitbox.y + hitbox.height - HEIGHT_TO_GROUND, map))
			if (!IsBorder(hitbox.x + hitbox.width, hitbox.y + hitbox.height - 35, map))
				return false;

		return true;
	}

	public static float GetPlayerXPosNextToWall(Rectangle hitbox, float xSpeed, Map map) {

		int MaxWidth = map.getWidth() - 60;

		if (xSpeed > 0)
			// right
			return MaxWidth - hitbox.width - 2;
		else
		// left
		{
			return 60;
		}
	}

	public static float GetPlayerYPosOnGround(Rectangle hitbox, float airSpeed, Map map) {

		return map.getHeight() - hitbox.height - HEIGHT_TO_GROUND;

	}
}
