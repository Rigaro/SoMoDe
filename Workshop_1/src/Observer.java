/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
public abstract class Observer {
	protected Subject subject;
	/**
	 * 
	 * @param subject
	 */
	abstract public void update(Subject subject);
}
