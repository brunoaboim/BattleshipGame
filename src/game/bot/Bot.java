package game.bot;

import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.board.Board;
import game.board.Position;
import game.enums.Direction;
import game.enums.Status;
import game.exceptions.OccupiedPositionException;
import game.exceptions.OutOfBoardException;
import game.ships.AircraftCarrier;
import game.ships.Battleship;
import game.ships.Destroyer;
import game.ships.PatrolBoat;
import game.ships.Submarine;

public class Bot {

	private static Position selectRandomPosition(Board board) {
		return new Position((char) ('A' + new Random().nextInt(10)), new Random().nextInt(10) + 1);
	}
	
	private static Direction selectRandomDirection() {
		if(new Random().nextBoolean())
			return Direction.HORIZONTAL;
		else
			return Direction.VERTICAL;
	}
	
	public static void insertBoat() {
		insertBoat(0);
	}
	
	private static void insertBoat(int shipQueueNumber) {
		Board board = Game.getUniqueInstance().getEnemyBoard();
		try {
			switch(shipQueueNumber) {
				case(0):
					Game.getUniqueInstance().insertBoat(board, new AircraftCarrier(), selectRandomPosition(board), selectRandomDirection());
					shipQueueNumber++;
				case(1):
					Game.getUniqueInstance().insertBoat(board, new Battleship(), selectRandomPosition(board), selectRandomDirection());
					shipQueueNumber++;
				case(2):
					Game.getUniqueInstance().insertBoat(board, new Submarine(), selectRandomPosition(board), selectRandomDirection());
					shipQueueNumber++;
				case(3):
					Game.getUniqueInstance().insertBoat(board, new Destroyer(), selectRandomPosition(board), selectRandomDirection());
					shipQueueNumber++;
				case(4):
					Game.getUniqueInstance().insertBoat(board, new PatrolBoat(), selectRandomPosition(board), selectRandomDirection());
					shipQueueNumber++;
			}	
		} catch (OutOfBoardException ex1) {
			insertBoat(shipQueueNumber);
		} catch (OccupiedPositionException ex2) {
			insertBoat(shipQueueNumber);
		}
	}
	
	//TODO: Code review!!! Working as intended!!!
	private static ArrayList<Position> positionsNearNotDeceasedShips() {
		Board board = Game.getUniqueInstance().getPlayerBoard();
		ArrayList<Position> shotsTakenByPlayer = board.shotsTaken();
		ArrayList<Position> neightborPositions = new ArrayList<Position>();
		ArrayList<Position> positions = new ArrayList<Position>();
		for(Position position1 : shotsTakenByPlayer) {
			for(Position position2 : shotsTakenByPlayer) {
				if(
					!position1.equals(position2)  &&
					board.shipInPosition(position1) != null &&
					board.shipInPosition(position2) != null &&
					!board.shipInPosition(position1).isDeceased() &&
					!board.shipInPosition(position2).isDeceased()
				) {
					if(position1.getLetter() == position2.getLetter() && position1.getNumber() == position2.getNumber()-1) {
						Position west = new Position(position1.getLetter(), position1.getNumber()-1);
						Position east = new Position(position2.getLetter(), position2.getNumber()+1);
						if(Position.isValidPosition(west) && !positions.contains(west) && board.statusInPosition(west).equals(Status.NOT_SHOOTED))
							positions.add(west);
						if(Position.isValidPosition(east) && !positions.contains(east) && board.statusInPosition(east).equals(Status.NOT_SHOOTED))
							positions.add(east);
						if(!neightborPositions.contains(position1))
							neightborPositions.add(position1);
						if(!neightborPositions.contains(position2))
							neightborPositions.add(position2);
					}
					if(position1.getNumber() == position2.getNumber() && position1.getLetter() == position2.getLetter()-1) {
						Position north = new Position((char)(position1.getLetter()-1), position1.getNumber());
						Position south = new Position((char)(position2.getLetter()+1), position2.getNumber());
						if(Position.isValidPosition(north) && !positions.contains(north) && board.statusInPosition(north).equals(Status.NOT_SHOOTED))
							positions.add(north);
						if(Position.isValidPosition(south) && !positions.contains(south) && board.statusInPosition(south).equals(Status.NOT_SHOOTED))
							positions.add(south);
						if(!neightborPositions.contains(position1))
							neightborPositions.add(position1);
						if(!neightborPositions.contains(position2))
							neightborPositions.add(position2);
					}
				}
			}
		}
		shotsTakenByPlayer.removeAll(neightborPositions);
		if(positions.size() < Game.getUniqueInstance().numberOfEnemyShipsAlive()) {
			ArrayList<Position> singlePositions = new ArrayList<Position>();
			for(Position position : shotsTakenByPlayer) {
				if(board.shipInPosition(position) != null && !board.shipInPosition(position).isDeceased()) {
					Position[] nearPositions = new Position[4];
					nearPositions[0] = new Position((char)(position.getLetter()-1),position.getNumber());
					nearPositions[1] = new Position((char)(position.getLetter()+1),position.getNumber());
					nearPositions[2] = new Position(position.getLetter(),position.getNumber()-1);
					nearPositions[3] = new Position(position.getLetter(),position.getNumber()+1);
					for(Position nearPosition : nearPositions) {
						if(Position.isValidPosition(nearPosition) && !singlePositions.contains(nearPosition) && board.statusInPosition(nearPosition).equals(Status.NOT_SHOOTED)) {
							singlePositions.add(nearPosition);
						}
					}
				}
			}
			while(positions.size() < Game.getUniqueInstance().numberOfEnemyShipsAlive() && !singlePositions.isEmpty()) {
				positions.add(singlePositions.remove(new Random().nextInt(singlePositions.size())));
			}
		}
		return positions;
	}
	
	public static void play(int shots) {
		Board board = Game.getUniqueInstance().getPlayerBoard();
		Position position;
		ArrayList<Position> possibleTargets = positionsNearNotDeceasedShips();
		ArrayList<Position> attackPositions = new ArrayList<Position>();
		for(int i = 0; i < shots; i++) {
			if(possibleTargets.isEmpty()) {
				do {
					position = selectRandomPosition(board);
				} while(board.statusInPosition(position).equals(Status.SHOOTED) || attackPositions.contains(position));
			} else {
				position = possibleTargets.remove(new Random().nextInt(possibleTargets.size()));
			}
			attackPositions.add(position);
		}
		Game.getUniqueInstance().play(board, attackPositions);
	}
	
}
