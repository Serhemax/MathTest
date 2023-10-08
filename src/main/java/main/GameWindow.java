  package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import utilz.LoadSave;

public class GameWindow {

	private JFrame window;

	public GameWindow(GamePanel gamePanel) {

		window = new JFrame();
		
		setIcon();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("MathTest");

		window.add(gamePanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		window.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().getPlaying().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void setIcon() {
		BufferedImage image = LoadSave.GetPlayerAtlas(LoadSave.STAY);
		window.setIconImage(new ImageIcon(image).getImage());
	}
}
