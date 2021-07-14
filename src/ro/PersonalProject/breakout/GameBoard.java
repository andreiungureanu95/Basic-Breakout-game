package ro.PersonalProject.breakout;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameBoard implements IPaintable, ActionListener {

	private static final int GAME_STEP = 30;
	
	private Ball ball;
	private Paddle paddle;
	private BrickFleet brickFleet;
	
	private Timer gameTimer;
	
	public GameBoard() {
		this.ball = new Ball();
		this.paddle = new Paddle();
		this.brickFleet = new BrickFleet();
		
		PaddleMovedListener paddleMovedListener = this.ball.getPaddleMovedListener();
		this.paddle.setPaddleMovedListener(paddleMovedListener);
		
		this.gameTimer = new Timer(GAME_STEP, this);
		this.gameTimer.start();
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		this.ball.paint(graphics);
		this.paddle.paint(graphics);
		this.brickFleet.paint(graphics);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// THis gets called by GameTimer tick
		this.paddle.gameStep();
		this.ball.gameStep();
		
		this.checkCollisions();
		
		this.checkGameOver();
	}
	
	private void checkCollisions() {
		this.checkWallCollision();
		this.checkPaddleCollision();
		this.checkBricksCollision();
	}
	
	private void checkWallCollision() {
		int ballX = this.ball.getX();
		int ballY = this.ball.getY();
		
		// Left Wall
		if (ballX <= 8) {
			this.ball.changeDirectionForWalls(WallSide.LEFT);
			return;
		}
		
		// Top Wall 
		if (ballY <= 25) {
			this.ball.changeDirectionForWalls(WallSide.TOP);
			return;
		}
		
		// Right Wall
		if (ballX >= MainWindow.WIDTH - ball.getWidth()) {
			this.ball.changeDirectionForWalls(WallSide.RIGHT);
			return;
		}
	}
	
	private void checkPaddleCollision() {
		this.paddle.checkCollision(this.ball);
	}
	
	private void checkBricksCollision() {
		this.brickFleet.checkCollision(this.ball);
	}
	
	private void checkGameOver() {
		// Check ball Y is under Paddle Y
		int ballBottomY = this.ball.getY();
		
		if(ballBottomY > this.paddle.getY()) {
			this.gameTimer.stop();
			JOptionPane.showMessageDialog(null, "Game Over");
			this.resetGame();
		}
	}
	
	private void resetGame() {
		this.ball.reset();
		this.paddle.reset();
		this.brickFleet = new BrickFleet();
		
		PaddleMovedListener paddleMovedListener = this.ball.getPaddleMovedListener();
		this.paddle.setPaddleMovedListener(paddleMovedListener);
		
		this.gameTimer = new Timer(GAME_STEP, this);
		this.gameTimer.start();
	}
	
	public List<KeyListener> getKeyListeners() {
		List<KeyListener> listeners = new ArrayList<KeyListener>();
		
		listeners.add(ball.getKeyListener());
		listeners.add(paddle.getKeyListener());
		
		return listeners;
	}
}
