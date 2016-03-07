/*
 * Ricardo Garcia Rosas 643952
 * Subject class
 * Observer pattern subject implementation for Workshop 1.
 */
import java.util.*;
public abstract class Subject {
	/*
	 * 
	 */
	protected List<Observer> observers = new ArrayList<Observer>();
	/**
	 * 
	 * @param observer
	 */
	abstract public void registerObserver(Observer observer);
	/**
	 * 
	 * @param observer
	 */
	abstract public void deregisterObserver(Observer observer);
	/**
	 * 
	 */
	abstract public void updateAll();
}
