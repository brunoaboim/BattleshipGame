package game.exceptions;

public class OutOfBoardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8807792226167668042L;
	
	public OutOfBoardException() {
		super();
	}
	
	public OutOfBoardException(String message) {
		super(message);
	}

}
