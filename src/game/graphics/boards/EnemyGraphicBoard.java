package game.graphics.boards;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import game.board.Board;
import game.board.Position;
import game.enums.Status;

public class EnemyGraphicBoard extends GraphicBoard {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262137840669961149L;

	public EnemyGraphicBoard(Board board) {
		super(board);
	}

	@Override
	protected void paintComponent(Graphics g) {
		for(int l = 0; l < 10; l++) {
			for(int n = 0; n < 10; n++) {
				Position position = new Position((char)('A'+l), n+1);
				if(getBoard().statusInPosition(position).equals(Status.SHOOTED)) {
					if(getBoard().shipInPosition(position) != null) {
						if(getBoard().shipInPosition(position).isDeceased()) {
							g.setColor(getBoard().shipInPosition(position).getColor());
						} else {
							g.setColor(Color.RED);
						}
					} else {
						g.setColor(Color.CYAN);
					}
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(25*n, 25*l, 25, 25);
				g.setColor(Color.RED);
				if(getBoard().shipInPosition(position) != null && getBoard().shipInPosition(position).isDeceased()) {
					g.drawLine(25*n, 25*l, 25*n+25, 25*l+25);
					g.drawLine(25*n+25, 25*l, 25*n, 25*l+25);
				}
					
			}
		}
		for(Position position : getSelectedPositions()) {
			g.setColor(Color.BLACK);
			g.fillRect(25*(position.getNumber()-1), 25*(position.getLetter()-'A'), 25, 25);
		}
		super.paintComponent(g);
	}
	
	@Override
	public ArrayList<Position> getSelectedPositions() {
		return super.getSelectedPositions();
	}
	
	@Override
	public void clearSelectedPositions() {
		super.clearSelectedPositions();
	}
	
}
