package entites;

import static entites.Collision.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import Map.Map;
import audio.AudioPlayer;
import main.GamePanel;
import utilz.LoadSave;

public class Player extends Entity {

	private float speed;
	public boolean left, right, jump, fall;

	private int startX, startY;

	private BufferedImage left1, left2, left3, left4, left5, right1, right2, right3, right4, right5;

	// JUMP AND GRAVITY
	private float airSpeed = 0f;
	private float gravity = 0.04f;
	private float jumpSpeed = -5.25f;

	private boolean onGround = false;

	public boolean onPlatform = false;
	///////////////////////////
	public boolean alreadyQuack;
	///////////////////////////

	public Player(GamePanel gamePanel, float x, float y, int width, int height) {
		super(x, y, width, height);
		setDefaultVariables();
		getPlayerImage();

		startX = (int) x;
		startY = (int) y;

		hitbox = new Rectangle((int) x, (int) y, width / 4 * 3, height);
	}

	public void setDefaultVariables() {
		speed = 2.5f;
		airSpeed = 0f;
		direction = "stay";
	}

	public void getPlayerImage() {

		stay = LoadSave.GetPlayerAtlas(LoadSave.STAY);

		left1 = LoadSave.GetPlayerAtlas(LoadSave.LEFT1);
		left2 = LoadSave.GetPlayerAtlas(LoadSave.LEFT2);
		left3 = LoadSave.GetPlayerAtlas(LoadSave.LEFT3);
		left4 = LoadSave.GetPlayerAtlas(LoadSave.LEFT4);
		left5 = LoadSave.GetPlayerAtlas(LoadSave.LEFT5);

		right1 = LoadSave.GetPlayerAtlas(LoadSave.RIGHT1);
		right2 = LoadSave.GetPlayerAtlas(LoadSave.RIGHT2);
		right3 = LoadSave.GetPlayerAtlas(LoadSave.RIGHT3);
		right4 = LoadSave.GetPlayerAtlas(LoadSave.RIGHT4);
		right5 = LoadSave.GetPlayerAtlas(LoadSave.RIGHT5);

	}

//UPDATE	

////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void update(List<Platform> platforms, Map map, AudioPlayer audioPlayer) {

		updatePlayerHitbox();

		updatePosition(platforms, map, audioPlayer);

		updateAnimation();

	}

	protected void updatePlayerHitbox() {
		hitbox.x = (int) x + width / 2 - 10;
		hitbox.y = (int) y;
	}

	private void updatePosition(List<Platform> platforms, Map map, AudioPlayer audioPlayer) {

		float xSpeed = 0;

		Quacks(x, audioPlayer);

		if (jump) {
			Jump();
		}

		if ((!right && !left) || (left && right))
			direction = "stay";

		if (left) {
			xSpeed -= speed;
			direction = "left";

		} else if (right) {
			xSpeed += speed;
			direction = "right";
		}

		if (onGround)
			if (IsPlayerOnGround(hitbox, map))
				onGround = false;

		if (!onGround) {

			if (CanMove(hitbox.x, hitbox.y + airSpeed + Collision.HEIGHT_TO_GROUND, hitbox.width, hitbox.height, map)) {
				y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed, map);
			} else {

				if (airSpeed > 0) { // fall
					y = GetPlayerYPosOnGround(hitbox, airSpeed, map);
					resetOnGround();
				}
				updateXPos(xSpeed, map);
			}
		} else {
			updateXPos(xSpeed, map);
		}

		for (int i = 0; i < platforms.size(); i++) {
			if (OnPlatform(platforms.get(i))) {
				y = platforms.get(i).y - hitbox.height;
				onPlatform = true;
				if (jump) {
					airSpeed = jumpSpeed;
				} else
					airSpeed = gravity;
			}
		}
	}

	private void Quacks(float x, AudioPlayer audioPlayer) {
		if (!audioPlayer.getGame().getPlaying().getMenu().isPaused()) {
			// if duck touch border
			if (!(x > 38 && x < 2194)) {
				if (!alreadyQuack)
					audioPlayer.playEffect(AudioPlayer.SCARY_DUCK);

				alreadyQuack = true;

			} else
				alreadyQuack = false;
		}

		// if duck jump
		if (airSpeed == jumpSpeed || (onGround == true && jump == true))
			audioPlayer.playEffect(AudioPlayer.QUACK);

	}

	private void Jump() {
		if (!onGround)
			return;
		onGround = false;
		airSpeed = jumpSpeed;
	}

	private void resetOnGround() {
		airSpeed = 0;
		onGround = true;
	}

	private void updateXPos(float xSpeed, Map map) {
		if (CanMove(hitbox.x + xSpeed, hitbox.y - Collision.HEIGHT_TO_GROUND, hitbox.width, hitbox.height, map)) {
			this.x += xSpeed;
		} else {
			direction = "stay";
			hitbox.x = (int) GetPlayerXPosNextToWall(hitbox, xSpeed, map);
		}
	}

	public boolean OnPlatform(Entity platform) {
		Line2D.Float lineOfPlatform = new Line2D.Float(platform.x, platform.y, platform.x + platform.width, platform.y);
		Rectangle2D.Float smallerHitbox = new Rectangle2D.Float(hitbox.x, hitbox.y + hitbox.height - 3, hitbox.width,
				10);

		if (smallerHitbox.intersectsLine(lineOfPlatform)) {
			if (airSpeed > 0) { // fall
				// if player stands on platform
				if (hitbox.y + height > platform.y - platform.height && platform.x + platform.width > hitbox.x
						&& platform.x - platform.width < hitbox.x) {
					return true;
				}
			}
		}

		return false;

	}

	private void updateAnimation() {
		spriteCounter++;
		if (spriteCounter > 23) {
			switch (spriteNum) {
			case 1:
				spriteNum = 2;
				break;
			case 2:
				spriteNum = 3;
				break;

			case 3:
				spriteNum = 4;
				break;

			case 4:
				spriteNum = 5;
				break;

			case 5:
				spriteNum = 1;
				break;

			}
			spriteCounter = 0;
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////

// DRAW	

//////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void draw(Graphics g, float camX, float camY) {
		BufferedImage image = null;
		image = drawAnimation(image);
		g.drawImage(image, (int) (x - camX), (int) (y - camY), null);

	}

	private BufferedImage drawAnimation(BufferedImage image) {
		switch (direction) {
		case "left":
			switch (spriteNum) {
			case 1:
				image = left1;
				break;

			case 2:
				image = left2;
				break;

			case 3:
				image = left3;
				break;

			case 4:
				image = left4;
				break;

			case 5:
				image = left5;
				break;
			}

			break;

		case "right":
			switch (spriteNum) {
			case 1:
				image = right1;
				break;

			case 2:
				image = right2;
				break;

			case 3:
				image = right3;
				break;

			case 4:
				image = right4;
				break;

			case 5:
				image = right5;
				break;
			}

			break;

		case "stay":
			image = stay;

			break;

		case "stay_spine":
			// image = stay_spine;

			break;
		}

		return image;
	}

	// GET, SET and RESTART

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	public float getGravity() {
		return gravity;
	}

	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	public void resetBoolean() {
		left = false;
		right = false;
		jump = false;
		fall = false;
	}

	public void resetAll(Map map) {
		resetBoolean();
		onGround = false;
		direction = "stay";

		x = (int) startX;
		y = (int) startY;

		if (!IsPlayerOnGround(hitbox, map)) {
			onGround = false;
		}

	}

}