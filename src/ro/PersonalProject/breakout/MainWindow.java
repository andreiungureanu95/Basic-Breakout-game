package ro.PersonalProject.breakout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private GameBoard gameBoard;
	private Timer fpsTimer;

	public MainWindow() {
		super("Breakout 2021");

		this.gameBoard = new GameBoard();
		
		this.configure();
		
		this.fpsTimer = new Timer(1000 / 30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.this.repaint();
			}
		});
		this.fpsTimer.start();
	}

	private void configure() {
		super.setSize(WIDTH, HEIGHT);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		List<KeyListener> keyListeners = this.gameBoard.getKeyListeners();
		for (KeyListener keyListener : keyListeners) {
			super.addKeyListener(keyListener);
		}
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(
				WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		// paint using g2d ...

		super.paint(g2d);
		this.gameBoard.paint(g2d);

		Graphics2D g2dComponent = (Graphics2D) g;
		g2dComponent.drawImage(bufferedImage, null, 0, 0);
	}
}
