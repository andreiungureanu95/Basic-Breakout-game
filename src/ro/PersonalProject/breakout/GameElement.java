package ro.PersonalProject.breakout;

import java.awt.Color;

public abstract class GameElement implements IPaintable{
	
	private static final int STEP = 5;
	
	// Position
	private int x;
	private int y;
	
	// Dimension
	private int width;
	private int height;
	
	// Color
	private Color color = Color.GREEN;
	
	public GameElement(
		int x,
		int y,
		int width,
		int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void incrementX() {
		x += STEP;
	}
	
	public void decrementX() {
		x -= STEP;
	}
	
	public int getY() {
		return y;
	}
	
	public void incrementY() {
		y += STEP;
	}
	
	public void decrementY() {
		y -= STEP;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		if (color != null) {
			this.color = color;
		}
	}
}
