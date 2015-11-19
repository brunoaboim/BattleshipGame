package game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import game.enums.Direction;
import game.ships.AircraftCarrier;
import game.ships.Battleship;
import game.ships.Destroyer;
import game.ships.PatrolBoat;
import game.ships.Ship;
import game.ships.Submarine;

public class GraphicSelector extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8882194811463438613L;
	private Ship selectedShip;
	private Direction selectedDirection;
	
	public GraphicSelector() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX() >= 10 && e.getX() <= (2*25 + 10) && e.getY() >= 10 && e.getY() <= (10 + 25))
					selectedShip = new PatrolBoat();
				else if(e.getX() >= 10 && e.getX() <= (3*25 + 10) && e.getY() >= 25 && e.getY() <= (50 + 25))
					selectedShip = new Destroyer();
				else if(e.getX() >= 10 && e.getX() <= (3*25 + 10) && e.getY() >= 25 && e.getY() <= (90 + 25))
					selectedShip = new Submarine();
				else if(e.getX() >= 10 && e.getX() <= (4*25 + 10) && e.getY() >= 25 && e.getY() <= (130 + 25))
					selectedShip = new Battleship();
				else if(e.getX() >= 10 && e.getX() <= (5*25 + 10) && e.getY() >= 25 && e.getY() <= (170 + 25))
					selectedShip = new AircraftCarrier();
				else if(e.getX() >= 10 && e.getX() <= (4*25 + 10) && e.getY() >= 25 && e.getY() <= (210 + 25))
					selectedDirection = Direction.HORIZONTAL;
				else if(e.getX() >= (40 + 4*25) && e.getX() <= (4*25 + (40 + 4*25)) && e.getY() >= 25 && e.getY() <= (210 + 25))
					selectedDirection = Direction.VERTICAL;
				else return;
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 250, 250);
		//PatrolBoat
		g.setColor(Color.BLUE);
		g.fillRect(10, 10, 2*25, 25);
		if(selectedShip != null && selectedShip.toString().equals("PatrolBoat"))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawRect(10, 10, 1*25, 25);
		g.drawRect(10, 10, 2*25, 25);
		g.drawString("Patrol Boat", 6*25, 27);
		//Destroyer
		g.setColor(Color.GREEN);
		g.fillRect(10, 50, 3*25, 25);
		if(selectedShip != null && selectedShip.toString().equals("Destroyer"))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawRect(10, 50, 1*25, 25);
		g.drawRect(10, 50, 2*25, 25);
		g.drawRect(10, 50, 3*25, 25);
		g.drawString("Destroyer", 6*25, 67);
		//Submarine
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(10, 90, 3*25, 25);
		if(selectedShip != null && selectedShip.toString().equals("Submarine"))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawRect(10, 90, 1*25, 25);
		g.drawRect(10, 90, 2*25, 25);
		g.drawRect(10, 90, 3*25, 25);
		g.drawString("Submarine", 6*25, 107);
		//Battleship
		g.setColor(Color.ORANGE);
		g.fillRect(10, 130, 4*25, 25);
		if(selectedShip != null && selectedShip.toString().equals("Battleship"))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawRect(10, 130, 1*25, 25);
		g.drawRect(10, 130, 2*25, 25);
		g.drawRect(10, 130, 3*25, 25);
		g.drawRect(10, 130, 4*25, 25);
		g.drawString("Battleship", 6*25, 147);
		//AircraftCarrier
		g.setColor(Color.YELLOW);
		g.fillRect(10, 170, 5*25, 25);
		if(selectedShip != null && selectedShip.toString().equals("AircraftCarrier"))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawRect(10, 170, 1*25, 25);
		g.drawRect(10, 170, 2*25, 25);
		g.drawRect(10, 170, 3*25, 25);
		g.drawRect(10, 170, 4*25, 25);
		g.drawRect(10, 170, 5*25, 25);
		g.drawString("Aircraft Carrier", 6*25, 187);
		//Horizontal
		if(selectedDirection != null && selectedDirection.equals(Direction.HORIZONTAL)) {
			g.setColor(Color.RED);
			g.fillRect(10, 210, 4*25, 25);
		}
		g.setColor(Color.BLACK);
		g.drawRect(10, 210, 4*25, 25);
		g.drawString("Horizontal", 33, 227);
		//Vertical
		if(selectedDirection != null && selectedDirection.equals(Direction.VERTICAL)) {
			g.setColor(Color.RED);
			g.fillRect(40 + 4*25, 210, 4*25, 25);
		}
		g.setColor(Color.BLACK);
		g.drawRect(40 + 4*25, 210, 4*25, 25);
		g.drawString("Vertical", 70 + 4*25, 227);
	}
	
	public Ship getSelectedShip() {
		return selectedShip;
	}
	
	public Direction getSelectedDirection() {
		return selectedDirection;
	}
	
	public void shutdown() {
		selectedShip = null;
		selectedDirection = null;
		removeMouseListener(getMouseListeners()[0]);
		repaint();
	}
	
}
