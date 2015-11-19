package game.graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import game.Game;
import game.exceptions.NotEnoughBoatsException;
import game.exceptions.OccupiedPositionException;
import game.exceptions.OutOfBoardException;
import game.graphics.boards.EnemyGraphicBoard;
import game.graphics.boards.PlayerGraphicBoard;
import game.graphics.messages.Messages;
import game.interfaces.Observable;
import game.interfaces.Observer;

public class Frame extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 363766010783399507L;
	private JPanel contentPane;
	private GraphicSelector selector;
	
	public Frame() {
		setTitle("Battleship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		EnemyGraphicBoard gridEnemy = new EnemyGraphicBoard(Game.getUniqueInstance().getEnemyBoard());
		gridEnemy.setBorder(new LineBorder(new Color(0, 0, 0)));
		gridEnemy.setBounds(10, 10, 250, 250);
		contentPane.add(gridEnemy);
		
		PlayerGraphicBoard gridPlayer = new PlayerGraphicBoard(Game.getUniqueInstance().getPlayerBoard());
		gridPlayer.addObserver(this);
		gridPlayer.setBorder(new LineBorder(new Color(0, 0, 0)));
		gridPlayer.setBounds(10, 300, 250, 250);
		contentPane.add(gridPlayer);
		
		selector = new GraphicSelector();
		selector.setBorder(new LineBorder(new Color(0, 0, 0)));
		selector.setBounds(324, 10, 250, 250);
		contentPane.add(selector);
		
		JButton btnConfirm = new JButton("Confirm Ships Position");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = ((JButton)e.getSource());
				if(btn.getText().equals("Confirm Ships Position")) {
					try {
						if(Game.getUniqueInstance().numberOfPlayerShipsAlive() == 5) {
							btn.setText("Confirm Attack Coordinates");
							selector.shutdown();
							Messages.beginMessage(contentPane);
							Game.getUniqueInstance().start();
							Game.getUniqueInstance().initializeGameOverChecker(btnConfirm);
						} else {
							throw new NotEnoughBoatsException();
						}
					} catch (NotEnoughBoatsException ex) {
						Messages.notEnoughBoatsMessage(contentPane);
					}
				} else {
					Game.getUniqueInstance().play(Game.getUniqueInstance().getEnemyBoard(), gridEnemy.getSelectedPositions());
					gridEnemy.clearSelectedPositions();
					Game.getUniqueInstance().setPlayerTurn(false);
					synchronized(Game.getUniqueInstance()) {
						Game.getUniqueInstance().notify();
					}
				}
			}
		});
		btnConfirm.setBounds(324, 527, 250, 23);
		contentPane.add(btnConfirm);
	}
	
	public void init() {
		setVisible(true);
		Messages.welcomeMessage(contentPane);
	}

	@Override
	public void update(Observable o) {
		PlayerGraphicBoard playerGraphicBoard = (PlayerGraphicBoard) o;
		if(selector != null && selector.getSelectedShip() != null && selector.getSelectedDirection() != null) {
			try {
				Game.getUniqueInstance().insertBoat(
					Game.getUniqueInstance().getPlayerBoard(),
					selector.getSelectedShip(),
					playerGraphicBoard.getSelectedPosition(),
					selector.getSelectedDirection()
				);
			} catch (OutOfBoardException ex1) {
				Messages.outOfBoardMessage(contentPane);
			} catch (OccupiedPositionException ex2) {
				Messages.occupiedPositionMessage(contentPane);
			}
		}
		playerGraphicBoard.clearSelectedPosition();
	}
	
}
