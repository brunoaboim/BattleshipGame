package game.graphics.messages;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Messages {

	public static void notEnoughBoatsMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "Insert all the ships before start the game!", "Battleship", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void outOfBoardMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "You must insert the ships in valid positions only!", "Battleship", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void occupiedPositionMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "You must insert the ships in not occupied positions only!", "Battleship", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void welcomeMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "Welcome to Battleship, Commander!", "Battleship", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void beginMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "Good Luck, Commander!", "Battleship", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void endVictoryMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "Victory is ours, Commander!", "Battleship", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void endDefeatMessage(JPanel panel) {
		JOptionPane.showMessageDialog(panel, "You are a disgrace, Commander!", "Battleship", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
