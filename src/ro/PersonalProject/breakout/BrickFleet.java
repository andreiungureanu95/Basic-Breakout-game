package ro.PersonalProject.breakout;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class BrickFleet implements IPaintable {

	private static final int BRICK_SPACING = 10;
	
	private List<Brick> bricks;
	private int score;
	
	public BrickFleet() {
		this.bricks = new ArrayList<Brick>();
		this.score = 0;
		this.layoutBricks();
	}
	
	private void layoutBricks() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 7; col++) {
				if (col == 0 || col == 6) {
					// Skip first & last column for spacing
					continue;
				}
				
				int x = (col * Brick.BRICK_WIDTH) + (BRICK_SPACING * col);
				int y = (row * Brick.BRICK_HEIGHT) + (BRICK_SPACING * row) + Paddle.OFFSET_TOP_PX;
				
				Brick brick = new Brick(x, y);
				this.bricks.add(brick);
			}
		}
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		for (Brick brick : this.bricks) {
			brick.paint(graphics);
		}
	}
	
	public void checkCollision(Ball ball) {
		for (Brick brick : this.bricks) {
			if (brick.hasCollision(ball)) {
				this.bricks.remove(brick);
				
				Side collisionSide = brick.getCollisionSide(ball);
				ball.changeDirectionForBricks(collisionSide);
				break;
			}
		}
	}
}
