/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
public class Driver {
	public static void main(String[] args){
		Observer obsOne = new SomeObserver("OOne");
		Observer obsTwo = new SomeObserver("OTwo");
		Subject subOne = new SomeSubject("One");
		subOne.registerObserver(obsOne);
		subOne.registerObserver(obsTwo);
		subOne.updateAll();
		Subject subTwo = new SomeSubject("Two");
		subTwo.registerObserver(obsOne);
		subTwo.updateAll();
		subOne.deregisterObserver(obsTwo);
		subOne.updateAll();
	}
}
