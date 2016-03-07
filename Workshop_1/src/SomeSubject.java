/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
public class SomeSubject extends Subject{
	String name;
	public SomeSubject(String name){
		this.name = name;
	}
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	public void deregisterObserver(Observer observer){
		observers.remove(observer);
	}
	@Override
	public String toString() {
		return this.name;
	}
	public void updateAll(){
		for (Observer observer : observers){
			observer.update(this);
		}
	}
}
