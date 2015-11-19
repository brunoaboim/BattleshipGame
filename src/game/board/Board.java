package game.board;

import java.util.ArrayList;

import game.enums.Direction;
import game.enums.Status;
import game.exceptions.OccupiedPositionException;
import game.exceptions.OutOfBoardException;
import game.interfaces.Observable;
import game.interfaces.Observer;
import game.ships.Ship;

public class Board implements Observable {

	private Position[][] board;
	private Observer observer;
	
	public Board() {
		board = new Position[10][10];
		for(int l = 0; l < board.length; l++) {
			for(int n = 0; n < board[l].length; n++) {
				board[l][n] = new Position((char)('A'+l),n+1);
				board[l][n].setStatus(Status.NOT_SHOOTED);
			}
		}
	}
	
	private synchronized Position getPosition(char letter, int number) {
		return board[letter-'A'][number-1];
	}
	
	public synchronized Ship shipInPosition(Position position) {
		return getPosition(position.getLetter(), position.getNumber()).getShip();
	}
	
	public synchronized Status statusInPosition(Position position) {
		return getPosition(position.getLetter(), position.getNumber()).getStatus();
	}
	
	public synchronized int numberOfShipsAlive() {
		ArrayList<Ship> shipsAlive = new ArrayList<Ship>();
		for(Position[] positions : board) {
			for(Position position : positions)
				if(position.getShip() != null && position.getStatus().equals(Status.NOT_SHOOTED) && !shipsAlive.contains(position.getShip()))
					shipsAlive.add(position.getShip());
		}
		return shipsAlive.size();
	}
	
	public synchronized ArrayList<Position> shotsTaken() {
		ArrayList<Position> shotsTaken = new ArrayList<Position>();
		for(Position[] positions : board) {
			for(Position position : positions)
				if(position.getStatus().equals(Status.SHOOTED))
					shotsTaken.add(new Position(position.getLetter(),position.getNumber()));
		}
		return shotsTaken;
	}

	@Override
	public void addObserver(Observer o) {
		observer = o;
	}

	@Override
	public void notifyObservers() {
		observer.update(this);
	}
	
	public synchronized void insertBoat(Ship ship, Position initialPosition, Direction direction) throws OutOfBoardException, OccupiedPositionException {
		for(Position[] positions : board)
			for(Position position : positions)
				if(position.getShip() != null && position.getShip().getClass().equals(ship.getClass()))
					position.setShip(null);
		Position[] boatPositions = new Position[ship.getSize()];
		for(int i = 0; i < ship.getSize(); i++) {
			Position newPosition;
			try {
				if(direction.equals(Direction.HORIZONTAL)) {
					newPosition = getPosition((char)(initialPosition.getLetter()), initialPosition.getNumber()+i);
				} else {
					newPosition = getPosition((char)(initialPosition.getLetter()+i), initialPosition.getNumber());
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new OutOfBoardException();
			}
			boatPositions[i] = newPosition;
		}
		for(Position position : boatPositions) {
			if(!verifyInsertionPosition(position))
				throw new OccupiedPositionException();
		}
		for(Position position : boatPositions) {
			position.setShip(ship);
		}
		notifyObservers();
	}
	
	private synchronized boolean verifyInsertionPosition(Position position) {
		boolean validPosition = true;
		if(position.getShip() != null)
			return false;
		else {
			if(position.getLetter() != 'A') {
				validPosition &= getPosition((char)(position.getLetter()-1), position.getNumber()).getShip() == null;
			}
			if(position.getLetter() != 'J') {
				validPosition &= getPosition((char)(position.getLetter()+1), position.getNumber()).getShip() == null;
			}
			if(position.getNumber() != 1) {
				validPosition &= getPosition(position.getLetter(), position.getNumber()-1).getShip() == null;
			}
			if(position.getNumber() != 10) {
				validPosition &= getPosition(position.getLetter(), position.getNumber()+1).getShip() == null;
			}
			if(position.getLetter() != 'A' && position.getNumber() != 1) {
				validPosition &= getPosition((char)(position.getLetter()-1), position.getNumber()-1).getShip() == null;
			}
			if(position.getLetter() != 'J' && position.getNumber() != 1) {
				validPosition &= getPosition((char)(position.getLetter()+1), position.getNumber()-1).getShip() == null;
			}
			if(position.getLetter() != 'A' && position.getNumber() != 10) {
				validPosition &= getPosition((char)(position.getLetter()-1), position.getNumber()+1).getShip() == null;
			}
			if(position.getLetter() != 'J' && position.getNumber() != 10) {
				validPosition &= getPosition((char)(position.getLetter()+1), position.getNumber()+1).getShip() == null;
			}
		}
		return validPosition;
	}
	
	public synchronized void play(ArrayList<Position> attackPositions) {
		for(Position position : attackPositions) {
			getPosition(position.getLetter(), position.getNumber()).setStatus(Status.SHOOTED);
			verifyAttackPosition(position);
		}
		notifyObservers();
	}
	
	private synchronized void verifyAttackPosition(Position position) {
		Ship shipInPosition = shipInPosition(position);
		if(shipInPosition != null) {
			Position nextPosition = position;
			do {
				if(shipInPosition(nextPosition) != null && statusInPosition(nextPosition).equals(Status.NOT_SHOOTED))
					return;
				nextPosition = new Position((char)(nextPosition.getLetter()-1), nextPosition.getNumber());
			} while(Position.isValidPosition(nextPosition) && shipInPosition(nextPosition) != null);
			nextPosition = position;
			do {
				if(shipInPosition(nextPosition) != null && statusInPosition(nextPosition).equals(Status.NOT_SHOOTED))
					return;
				nextPosition = new Position((char)(nextPosition.getLetter()+1), nextPosition.getNumber());
			} while(Position.isValidPosition(nextPosition) && shipInPosition(nextPosition) != null);
			nextPosition = position;
			do {
				if(shipInPosition(nextPosition) != null && statusInPosition(nextPosition).equals(Status.NOT_SHOOTED))
					return;
				nextPosition = new Position(nextPosition.getLetter(), nextPosition.getNumber()-1);
			} while(Position.isValidPosition(nextPosition) && shipInPosition(nextPosition) != null);
			nextPosition = position;
			do {
				if(shipInPosition(nextPosition) != null && statusInPosition(nextPosition).equals(Status.NOT_SHOOTED))
					return;
				nextPosition = new Position(nextPosition.getLetter(), nextPosition.getNumber()+1);
			} while(Position.isValidPosition(nextPosition) && shipInPosition(nextPosition) != null);
			shipInPosition.setDeceased(true);
		}
	}
	
}
