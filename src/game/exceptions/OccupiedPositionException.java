package game.exceptions;

public class OccupiedPositionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8269852038066980598L;

	public OccupiedPositionException() {
		super();
	}
	
	public OccupiedPositionException(String message) {
		super(message);
	}
	
}
