package ro.PersonalProject.breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends GameElement {
	
	private class PaddleKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// press and release
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Paddle.this.isLeft = true;
				Paddle.this.isKeyDown = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Paddle.this.isLeft = false;
				Paddle.this.isKeyDown = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			Paddle.this.isKeyDown = false;
		}
	}

	public static final int PADDLE_WIDTH = 100;
	public static final int PADDLE_HEIGHT = 20;
	
	// TODO Test this value
	public static final int OFFSET_TOP_PX = 48;
	
	private int stepX = 10;
	private boolean isKeyDown = false;
	private boolean isLeft = false;
	
	private PaddleKeyListener paddleKeyListener;
	private PaddleMovedListener paddleMovedListener;
	
	public Paddle() {
		super((MainWindow.WIDTH / 2) - (PADDLE_WIDTH / 2), 
				MainWindow.HEIGHT - PADDLE_HEIGHT - OFFSET_TOP_PX,
				PADDLE_WIDTH,
				PADDLE_HEIGHT);
		
		super.setColor(Color.GRAY);
		
		this.paddleKeyListener = new PaddleKeyListener();
	}
	
	public void reset() {
		setX((MainWindow.WIDTH / 2) - (PADDLE_WIDTH / 2));
		setY(MainWindow.HEIGHT - PADDLE_HEIGHT - OFFSET_TOP_PX);
		isKeyDown = false;
		isLeft = false;
	}
	
	public KeyListener getKeyListener() {
		return this.paddleKeyListener;
	}
	
	public void setStepX(int stepX) {
		this.stepX = stepX;
	}
	
	public void setPaddleMovedListener(
			PaddleMovedListener paddleMovedListener) {
		this.paddleMovedListener = paddleMovedListener;
	}
	
	/**
	 * Get's called on GameTimer Tick
	 */
	public void gameStep() {
		if (this.isKeyDown) {
			if (this.isLeft) {
				// Go Left
				int tempX = getX() - this.stepX;
				tempX = Math.max(8, tempX);
				super.setX(tempX);
				this.paddleMovedListener.paddleMoved(tempX);
			}else {
				// Go Right
				int tempX = getX() + this.stepX;
				tempX = Math.min(MainWindow.WIDTH - PADDLE_WIDTH - 8, tempX);
				super.setX(tempX);
				this.paddleMovedListener.paddleMoved(tempX);
			}
		}
	}
	
	/**
	 * Paddles cannot move vertically so we prevent 
	 * changing of Y coordinate
	 */
	@Override
	public void setY(int y) {
		// DO NOTHING 
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		graphics.setColor(super.getColor());
		
		graphics.fillRect(super.getX(), 
				super.getY(), 
				super.getWidth(), 
				super.getHeight());
	}
	
	public void checkCollision(Ball ball) {
		int ballBottomY = ball.getY() + ball.getHeight();
		int paddleY = this.getY();
		
		int delta = Math.abs(paddleY - ballBottomY);
		
		if (delta < 3) {
			int ballX = ball.getX();
			int paddleX = this.getX();
			
			if (ballX >= paddleX - ball.getWidth() 
					&& ballX < paddleX + this.getWidth()) {
				ball.changeDirectionForPaddle();
			}
		}
		
	}
}
