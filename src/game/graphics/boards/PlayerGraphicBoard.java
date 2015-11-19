package game.graphics.boards;

import java.awt.Color;
import java.awt.Graphics;

import game.board.Board;
import game.board.Position;
import game.enums.Status;
import game.interfaces.Observable;
import game.interfaces.Observer;
import game.ships.Ship;

public class PlayerGraphicBoard extends GraphicBoard implements Observable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3110565622182244413L;
	private Observer observer;

	public PlayerGraphicBoard(Board board) {
		super(board);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for(int l = 0; l < 10; l++) {
			for(int n = 0; n < 10; n++) {
				Position position = new Position((char)('A'+l), n+1);
				Ship shipInPosition = getBoard().shipInPosition(position);
				Status statusInPosition = getBoard().statusInPosition(position);
				if(shipInPosition != null) {
					g.setColor(shipInPosition.getColor());
				} else {
					g.setColor(Color.CYAN);
				}
				g.fillRect(25*n, 25*l, 25, 25);
				if(statusInPosition.equals(Status.SHOOTED)) {
					g.setColor(Color.RED);
					g.fillOval(25*n + 5, 25*l + 5, 15, 15);
				}
			}
		}
		super.paintComponent(g);
	}
	
	public Position getSelectedPosition() {
		return super.getSelectedPositions().get(0);
	}
	
	public void clearSelectedPosition() {
		super.clearSelectedPositions();
	}

	@Override
	public void addObserver(Observer o) {
		observer = o;
	}

	@Override
	public void notifyObservers() {
		observer.update(this);
	}
	
}
