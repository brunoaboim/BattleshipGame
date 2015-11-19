package game.interfaces;



public interface Observable {

	void addObserver(Observer o);
	void notifyObservers();
	
}
