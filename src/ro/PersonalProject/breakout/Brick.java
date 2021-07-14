package ro.PersonalProject.breakout;

import java.awt.Color;
import java.awt.Graphics2D;

public class Brick extends GameElement {

	public static final int BRICK_WIDTH = 100;
	public static final int BRICK_HEIGHT = 30;
	
	public Brick(int x, int y) {
		super(x, y, 
				BRICK_WIDTH, BRICK_HEIGHT);
		
		setColor(Color.GREEN);
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		graphics.setColor(getColor());
		
		graphics.fillRect(
				super.getX(), 
				super.getY(), 
				super.getWidth(), 
				super.getHeight());
	}
	
	public boolean hasCollision(Ball ball) {
		int ballX = ball.getX();
		int ballY = ball.getY();
		
		if (ballX > (this.getX() - Ball.BALL_SIZE) && ballX <= this.getX() + BRICK_WIDTH) {
			if (ballY >= (this.getY() - Ball.BALL_SIZE) && (ballY < (this.getY() + BRICK_HEIGHT))) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * No need to validate Y because Y is already validate in hasCollision
	 * @param ball
	 * @return
	 */
	public Side getCollisionSide(Ball ball) {
		int rightXOfBrick = this.getX() + BRICK_WIDTH + 8;
		int ballX = ball.getX() + 8;
		
		int delta = Math.abs(ballX - rightXOfBrick);
		
		if (delta < 3) {
			return Side.RIGHT;
		}
		
		int ballY = ball.getY() + ball.getHeight();
		int brickY = this.getY();
		
		delta = Math.abs(brickY - ballY);
		if (delta < 3) {
			return Side.TOP;
		}
		
		int rightXOfBall = ball.getX() + ball.getWidth();
		int brickX = this.getX();
		
		delta = Math.abs(brickX - rightXOfBall);
		if (delta < 3) {
			return Side.LEFT;
		}
		
		ballY = ball.getY();
		int bottomYOfBrick = this.getY() + this.getHeight();
		
		delta = Math.abs(bottomYOfBrick - ballY);
		if (delta < 3) {
			return Side.BOTTOM;
		}
		
		return null;
	}
}
