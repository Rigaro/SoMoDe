
public class Complaint {
	
	private String productName;
	public static enum State {RESOLVED,ACTIVE};
	private State state;
	
	public Complaint(String productName){
		this.productName = productName;
		this.state = State.ACTIVE;
	}
	
	public void resolve(){
		this.state = State.RESOLVED;
	}
	
	public State getState(){
		return this.state;
	}

}
