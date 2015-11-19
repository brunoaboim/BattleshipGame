package game.ships;

import java.awt.Color;

public abstract class Ship {

	private final int size;
	private final Color color;
	private boolean deceased;
	
	public Ship(int size, Color color) {
		this.size = size;
		this.color = color;
	}
	
	public int getSize() {
		return size;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isDeceased() {
		return deceased;
	}
	
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
