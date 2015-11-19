package game.exceptions;

public class NotEnoughBoatsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -606776770497805494L;

	public NotEnoughBoatsException() {
		super();
	}
	
	public NotEnoughBoatsException(String message) {
		super(message);
	}
	
}
