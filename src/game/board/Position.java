package game.board;

import game.enums.Status;
import game.ships.Ship;

public class Position {
	
	private final char letter;
	private final int number;
	private Ship ship;
	private Status status;
	
	public Position(char letter, int number) {
		this.letter = letter;
		this.number = number;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Position) {
			Position p = (Position) o;
			return letter == p.getLetter() && number == p.getNumber();
		} else return false;
	}
	
	@Override
	public String toString() {
		return "Position(" + letter + number + ")";
	}
	
	public static boolean isValidPosition(Position position) {
		return position.getLetter() >= 'A' && position.getLetter() <= 'J' &&
				position.getNumber() >= 1 && position.getNumber() <= 10;
	}

}
