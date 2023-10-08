package entites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Map.Door;
import Map.Map;
import audio.AudioPlayer;
import gameStates.GameState;
import main.Game;
import utilz.Text;

public class Numbers {

	public int RAND_MAX_VALUE = 40;

	List<Platform> platforms;
	List<Text> numbers;
	private int[] value;
	private int[] indexSum;
	private int findValue;
	public int sum, sumAll;

	private boolean doorOpen = true;

	public Numbers(List<Platform> platforms) {
		this.platforms = platforms;
		numbers = new ArrayList<Text>();
		SetPositionToNumbers();
	}

	private void SetPositionToNumbers() {
		SetValueOfNumbers();
		SumAll();

		for (int i = 0; i < platforms.size(); i++) {
			numbers.add(new Text(platforms.get(i).getX() + Platform.PLATFORM_WIDTH / 3,
					platforms.get(i).getY() - Platform.PLATFORM_HEIGHT / 2, 58, 58));
		}

		numbers.add(new Text(0, 50, 58, 58));
		numbers.add(new Text(Game.GAME_WIDTH - 58 * 2, 50, 58, 58));

	}

	public void update(Game game, Door door, Map map, Player player) {

		// if player get right number || win mechanic
		if (GetWin()) {

			// play effect of open door
			if (doorOpen) {
				game.getAudioplayer();
				game.getAudioplayer().playEffect(AudioPlayer.DOOR);
				doorOpen = false;
			}

			// change close door to open
			door.setOpenDoor(map.getWidth() - 310 + 30, map.getHeight() - 270, 310, 300);

			// if player walk into open door
			if (door.getHitbox().intersects(player.getHitbox())) {

				// play win effect
				game.getAudioplayer().playEffect(AudioPlayer.WIN);

				// restart game
				game.getPlaying().resetAll();

				// for effect of open door can play again next time
				doorOpen = true;

				// restart game song
				game.getAudioplayer().playSong(game.getAudioplayer().BG_GAME);
			}
		}

		// if there left no numbers
		if (sumAll == 0 && !GetWin()) {

			game.getAudioplayer().playEffect(game.getAudioplayer().LOSE);
			game.getAudioplayer().playSong(game.getAudioplayer().BG_MENU);

			game.getPlaying().resetAll();
			GameState.state = GameState.MENU;
		}

	}

	// make sum of all the numbers, for lose game purpose
	private void SumAll() {
		for (int i = 0; i < value.length; i++)
			sumAll += value[i];

	}

	private void SetValueOfNumbers() {

		value = new int[platforms.size()];
		indexSum = new int[4];

		// this cycle generate random number for each platform
		// using an bubble sort with flag, for checking equality of values
		for (int i = 0; i < platforms.size(); i++) {

			boolean flag = false;
			while (flag == false) {
				flag = true;

				value[i] = (int) ((Math.random() * (RAND_MAX_VALUE)) + 1);

				for (int j = 0; j < i; j++) {
					if (value[i] == value[j]) {
						flag = false;
						break;
					}

				}
			}
		}

		// this cycle take 4 random index of massive of value numbers
		// same sorting method
		for (int i = 0; i < 4; i++) {
			boolean flag = false;
			while (flag == false) {
				flag = true;
				indexSum[i] = (int) (Math.random() * platforms.size());
				for (int j = 0; j < i; j++) {
					if (indexSum[i] == indexSum[j]) {
						flag = false;
						break;
					}

				}
			}
		}

		// generate number that we need to find from adding 4 random numbers
		// this way player always can find the number
		for (int i = 0; i < 4; i++) {
			findValue += value[indexSum[i]];
		}

	}

	// if our sum is equal to number that we need to find, then we win
	public boolean GetWin() {
		if (sum == findValue)
			return true;
		else
			return false;
	}

	public void draw(Graphics g, float camX, float camY, Door door) {
		

		for (int i = 0; i < numbers.size() - 2; i++) {

			if (value[i] == 0) {
				// set transparent color, we can`t just delete a number from a list, so we make
				// that we can`t see or add/substract this number
				g.setColor(new Color(0, 0, 0, 0));
			} else
				g.setColor(new Color(255, 255, 20));

			numbers.get(i).draw(g, camX, camY, Integer.toString(value[i]));
		}

		g.setColor(new Color(255, 238, 20));
		numbers.get(numbers.size() - 1).drawStatic(g, Integer.toString(sum));
		numbers.get(numbers.size() - 2).drawStatic(g, Integer.toString(findValue));
		g.setColor(new Color(255, 255, 20));

		if (GetWin())
			door.drawOpen(g, camX, camY);
		else
			door.drawClose(g, camX, camY);

	}

	public void setValueToZero(int index) {
		value[index] = 0;
	}

	public void resetAll() {
		SetValueOfNumbers();
		SetPositionToNumbers();
	}

	public List<Text> getNumbers() {
		return numbers;
	}

	public int GetValue(int index) {
		return value[index];
	}

	public int getFindValue() {
		return findValue;
	}

}
