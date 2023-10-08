package utilz;


import Map.Map;
import entites.Player;
import main.Game;

public class Camera {

	Player player = null;
	private float ofSetX, ofSetY;

	public Camera(Player player) {
		this.player = player; 
	}

	public void update(Map map) {

		ofSetX = (player.getX() + player.getWidth() / 2) - Game.GAME_WIDTH / 2;
		ofSetY = (player.getY() + player.getHeight() / 2) - Game.GAME_HEIGHT / 2;
		
		CheckCloseToBorder(map);
	}
	
	private void CheckCloseToBorder(Map map) {
		
		int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
		int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
		
		int bottomBorder = (int) (0.8 * Game.GAME_HEIGHT);
		int upBorder = (int) (0.2 * Game.GAME_HEIGHT);
		
		int playerX = player.getHitbox().x;
		int playerY = player.getHitbox().y;
		
		int diffX = (int) (playerX - ofSetX);
		int diffY = (int) (playerY - ofSetY);
		
		int maxLvlOfSetX = map.getWidth() - Game.GAME_WIDTH;
		int maxLvlOfSetY = map.getHeight() - Game.GAME_HEIGHT;
		
		if(diffY > bottomBorder) {
			ofSetY += diffY - bottomBorder;
		} else if (diffY < upBorder)
			ofSetY += diffY - upBorder;
		
		
		if(diffX > rightBorder) {
			ofSetX += diffX - rightBorder;
		} else if (diffX < leftBorder)
			ofSetX += diffX - leftBorder;
		
		if (ofSetX > maxLvlOfSetX)
			ofSetX = maxLvlOfSetX;
		if (ofSetX < 0) {
			ofSetX = 0;
		}
		
		if (ofSetY > maxLvlOfSetY)
			ofSetY = maxLvlOfSetY;
		if (ofSetY < 0) {
			ofSetY = 0;
		}
		
	}

	public float getOfSetX() {
		return ofSetX;
	}

	public void setOfSetX(float ofSetX) {
		this.ofSetX = ofSetX;
	}

	public float getOfSetY() {
		return ofSetY;
	}

	public void setOfSetY(float ofSetY) {
		this.ofSetY = ofSetY;
	}


	
}
