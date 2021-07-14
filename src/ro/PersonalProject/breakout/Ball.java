package ro.PersonalProject.breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ball extends GameElement {

	public static final int BALL_SIZE = 30;
	
	private class PaddleMovedHandler implements PaddleMovedListener {
		@Override
		public void paddleMoved(int movedX) {
			if (!Ball.this.isStarted) {
				Ball.this.setX(movedX + (Paddle.PADDLE_WIDTH / 2) - (BALL_SIZE /2));
			}
		}
	}
	
	private class BallKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				Ball.this.isStarted = !Ball.this.isStarted;
			}
		}
		
	}
	
	private boolean isStarted = false;
	private BallDirection direction = BallDirection.NONE;
	
	private BallKeyListener ballKeyListener;
	private PaddleMovedHandler paddleMovedHandler;
	
	public Ball() {
		super(
				(MainWindow.WIDTH / 2) - (BALL_SIZE / 2) , 
				MainWindow.HEIGHT - Paddle.PADDLE_HEIGHT - Paddle.OFFSET_TOP_PX - BALL_SIZE, 
				BALL_SIZE, 
				BALL_SIZE);
	
		super.setColor(Color.RED);
		this.ballKeyListener = new BallKeyListener();
		this.paddleMovedHandler = new PaddleMovedHandler();
		this.direction = BallDirection.UPPER_RIGHT;
	}
	
	public void reset() {
		setX((MainWindow.WIDTH / 2) - (BALL_SIZE / 2));
		setY(MainWindow.HEIGHT - Paddle.PADDLE_HEIGHT - Paddle.OFFSET_TOP_PX - BALL_SIZE);
		this.direction = BallDirection.UPPER_LEFT;
		this.isStarted = false;
	}
	
	public KeyListener getKeyListener() {
		return this.ballKeyListener;
	}
	
	public PaddleMovedListener getPaddleMovedListener() {
		return this.paddleMovedHandler;
	}
	
	public void gameStep() {
		if (this.isStarted) {
			// MOVE ME!
			switch(this.direction) {
				case UPPER_RIGHT: {
					incrementX();
					decrementY();
				}; break;
				case UPPER_LEFT: {
					decrementX();
					decrementY();
				}; break;
				case DOWN_LEFT: {
					decrementX();
					incrementY();
				}; break;
				case DOWN_RIGHT: {
					incrementX();
					incrementY();
				}; break;
				case DOWN: {
					incrementY();
				}; break;
				case UP: {
					decrementY();
				}; break;
			default:
				break;
			}
		}
	}
	
	public void changeDirectionForBricks(Side side) {
		switch(this.direction) {
			case DOWN: this.direction = BallDirection.UPPER_RIGHT; break;
			case UP: this.direction = BallDirection.DOWN_LEFT;break;
			case DOWN_LEFT: {
				if (side == Side.TOP) {
					this.direction = BallDirection.UPPER_LEFT;
				}else if (side == Side.RIGHT) {
					this.direction = BallDirection.DOWN_RIGHT;
				}
			}; break;
			case DOWN_RIGHT: this.direction = BallDirection.UPPER_RIGHT;break;
			case UPPER_LEFT: this.direction = BallDirection.DOWN_LEFT;break;
			case UPPER_RIGHT: this.direction = BallDirection.DOWN_RIGHT;break;
			case NONE: ;
			default:;
		}
	}
	
	public void changeDirectionForWalls(WallSide wallSide) { 
		switch(this.direction) {
			case UP: this.direction = BallDirection.DOWN_RIGHT; break;
			case DOWN_RIGHT: this.direction = BallDirection.DOWN_LEFT; break;
			case DOWN_LEFT: this.direction = BallDirection.DOWN_RIGHT;break;
			case UPPER_LEFT: {
				if (wallSide == WallSide.LEFT) {
					this.direction = BallDirection.UPPER_RIGHT;
				} else if (wallSide == WallSide.TOP) {
					this.direction = BallDirection.DOWN_LEFT;
				}
			}break;
			case UPPER_RIGHT: {
				if (wallSide == WallSide.RIGHT) {
					this.direction = BallDirection.UPPER_LEFT;
				}else if (wallSide == WallSide.TOP) {
					this.direction = BallDirection.DOWN_RIGHT;
				}
			}break;
			default: ;
		}
	}
	
	public void changeDirectionForPaddle() {
		switch(this.direction) {
			case DOWN: this.direction = BallDirection.UPPER_RIGHT; break;
			case DOWN_LEFT: this.direction = BallDirection.UPPER_LEFT; break;
			case DOWN_RIGHT: this.direction = BallDirection.UPPER_RIGHT; break;
			default: ;
		}
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		graphics.setColor(this.getColor());
		
		graphics.fillOval(
				super.getX(), 
				super.getY(), 
				super.getWidth(), 
				super.getHeight());
	}
}
