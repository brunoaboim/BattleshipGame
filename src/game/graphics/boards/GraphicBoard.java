package game.graphics.boards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;

import game.Game;
import game.board.Board;
import game.board.Position;
import game.enums.Status;
import game.interfaces.Observable;
import game.interfaces.Observer;

public abstract class GraphicBoard extends JComponent implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8226172036277242199L;
	private Board board;
	private ArrayList<Position> selectedPositions;
	
	public GraphicBoard(Board board) {
		this.board = board;
		this.board.addObserver(this);
		selectedPositions = new ArrayList<Position>();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Position selectedPosition = new Position((char) ('A' + (e.getY() / 25)), (e.getX() / 25) + 1);
				if(e.getSource() instanceof PlayerGraphicBoard) {
					selectedPositions.add(selectedPosition);
				} else if(e.getSource() instanceof EnemyGraphicBoard && Game.getUniqueInstance().isAlive()) {
					if(board.statusInPosition(selectedPosition).equals(Status.NOT_SHOOTED)) {
						if(selectedPositions.size() < Game.getUniqueInstance().numberOfPlayerShipsAlive()) {
							if(!selectedPositions.contains(selectedPosition)) {
								selectedPositions.add(selectedPosition);
							} else {
								selectedPositions.remove(selectedPosition);
							}
						} else {
							if(selectedPositions.contains(selectedPosition)) {
								selectedPositions.remove(selectedPosition);
							}
						}
						repaint();
					}
				}
				if(e.getSource() instanceof PlayerGraphicBoard) {
					PlayerGraphicBoard playerGraphicBoard = (PlayerGraphicBoard) e.getSource();
					playerGraphicBoard.notifyObservers();
				}
			}
		});
	}
	
	public Board getBoard() {
		return board;
	}
	
	protected ArrayList<Position> getSelectedPositions() {
		return selectedPositions;
	}
	
	protected void clearSelectedPositions() {
		selectedPositions.clear();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < 10; i++) {
			g.drawLine(0, 25*i, 250, 25*i);
		}
		for(int j = 0; j < 10; j++) {
			g.drawLine(25*j, 0, 25*j, 250);
		}
	}

	@Override
	public void update(Observable o) {
		repaint();
	}
	
}
