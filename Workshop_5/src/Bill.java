
public class Bill {

	private float amount;
	public static enum Status {PAID,DUE,MISSED};
	private Status status;
	
	public Bill(float amount){
		this.amount = amount;
		this.status = Status.DUE;
	}
	
	public void pay(){
		this.status = Status.PAID;
	}
	public void missed(){
		this.status = Status.MISSED;
	}
	public Status getStatus(){
		return this.status;
	}
	public float getAmout(){
		return this.amount;
	}
}
