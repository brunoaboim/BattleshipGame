package game;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.board.Board;
import game.board.Position;
import game.bot.Bot;
import game.enums.Direction;
import game.exceptions.OccupiedPositionException;
import game.exceptions.OutOfBoardException;
import game.graphics.messages.Messages;
import game.ships.Ship;

public class Game extends Thread {
	
	private static class GameOverDetector extends Thread {
		
		private static final GameOverDetector uniqueInstance = new GameOverDetector();
		private JButton button;
		
		public static GameOverDetector getUniqueInstance() {
			return uniqueInstance;
		}
		
		public void setButton(JButton button) {
			this.button = button;
		}
		
		private void endGame() {
			if(Game.getUniqueInstance().numberOfEnemyShipsAlive() == 0) {
				button.setEnabled(false);
				button.setText("PLAYER WINS!");
				Messages.endVictoryMessage((JPanel)button.getParent());
			} else if(Game.getUniqueInstance().numberOfPlayerShipsAlive() == 0) {
				button.setEnabled(false);
				button.setText("ENEMY WINS!");
				Messages.endDefeatMessage((JPanel)button.getParent());
			}
		}
		
		@Override
		public void run() {
			try {
				synchronized(this) {
					if(Game.getUniqueInstance().numberOfPlayerShipsAlive() == 5) {
						wait();
					}
					while(!Game.getUniqueInstance().isGameOver()) {
						wait();
					}
					endGame();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Game uniqueInstance = new Game();
	
	private Board enemyBoard;
	private Board playerBoard;
	private boolean playerTurn;
	
	public Game() {
		enemyBoard = new Board();
		playerBoard = new Board();
		playerTurn = true;
	}
	
	public static Game getUniqueInstance() {
		return uniqueInstance;
	}
	
	public Board getEnemyBoard() {
		return enemyBoard;
	}
	
	public Board getPlayerBoard() {
		return playerBoard;
	}
	
	public boolean isPlayerTurn() {
		return playerTurn;
	}
	
	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	public int numberOfEnemyShipsAlive() {
		return enemyBoard.numberOfShipsAlive();
	}
	
	public int numberOfPlayerShipsAlive() {
		return playerBoard.numberOfShipsAlive();
	}
	
	public boolean isGameOver() {
		return numberOfEnemyShipsAlive() == 0 || numberOfPlayerShipsAlive() == 0;
	}
	
	public void insertBoat(Board board, Ship ship, Position initialPosition, Direction direction) throws OutOfBoardException, OccupiedPositionException {
		board.insertBoat(ship, initialPosition, direction);
	}
	
	@Override
	public void run() {
		Bot.insertBoat();
		while(!isGameOver()) {
			try {
				synchronized(this) {
					while(playerTurn)
						wait();
				}
				verifyGameOver();
				Bot.play(numberOfEnemyShipsAlive());
				verifyGameOver();
				setPlayerTurn(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void play(Board board, ArrayList<Position> attackPositions) {
		board.play(attackPositions);
	}
	
	public void initializeGameOverChecker(JButton button) {
		GameOverDetector.getUniqueInstance().setButton(button);
		GameOverDetector.getUniqueInstance().start();
	}
	
	private void verifyGameOver() {
		Thread thread;
		synchronized(thread = GameOverDetector.getUniqueInstance()) {
			thread.notify();
		}
	}
	
}
