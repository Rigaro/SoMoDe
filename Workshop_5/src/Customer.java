import java.util.ArrayList;

public class Customer {

	private String name;
	public static enum State {
		PENDING,ACTIVE,INACTIVE,PRIORITY,DEFAULT,SUSPENDED,CLOSED;
		public static enum Default {
			NONE,SECOND_CHANCE,ADMINISTRATION,SUSPENSION,COLLECTIONS,HEALTHY,BAD};
	};
	private State state;
	private State.Default defaulted;
	private ArrayList<Bill> bills;
	private ArrayList<Product> products;
	private ArrayList<Message> messages;
	private ArrayList<Complaint> complaints;
	private int missed;
	private int daysSinceNotice;
	private boolean complied;
	
	public Customer(String name){
		this.name = name;
		this.state = State.PENDING;
		this.defaulted = State.Default.NONE;
		this.bills = new ArrayList<Bill>();
		this.products = new ArrayList<Product>();
		this.messages = new ArrayList<Message>();
		this.complaints = new ArrayList<Complaint>();
		this.missed = 0;
		this.daysSinceNotice = 0;
		this.complied = false;
	}
	
	public void addNewBill(Bill bill){
		this.bills.add(bill);
	}
	
	// ONLY WHEN TIME DEPENDENT.
	// In this case the states change with each event.
	public void update(){
		this.updateMissed();
		// States
		switch(this.state){
		case PENDING:
			break;
		case ACTIVE:
			if(this.hasActiveComplaints()){
				this.state = State.PRIORITY;
			}
			if(this.products.isEmpty()&&!this.hasDue()){
				this.state = State.INACTIVE;
			}
			if(this.missed>0){
				this.state = State.DEFAULT;
			}
			break;
		case INACTIVE:
			if(!this.products.isEmpty()){
				this.state = State.ACTIVE;
			}
			break;
		case PRIORITY:
			if(!this.hasActiveComplaints()){
				this.state = State.ACTIVE;
			}
			break;
		case DEFAULT:
			switch(this.defaulted){
			case NONE:
				// Check the customer's history
				if(this.missed>2){
					this.defaulted = State.Default.ADMINISTRATION;
					break;
				}
				else{
					this.defaulted = State.Default.SECOND_CHANCE;
					break;
				}
			case SECOND_CHANCE:
				if(!this.hasReplied()){
					this.defaulted = State.Default.SUSPENSION;
				}
				if(!this.hasDue()){
					this.state = State.ACTIVE;
				}
				if(!this.complied){
					this.defaulted = State.Default.ADMINISTRATION;
				}
				break;
			case ADMINISTRATION:
				if(!this.hasDue()){
					this.state = State.ACTIVE;
				}
				else if(!this.hasReplied()){
					this.defaulted = State.Default.SUSPENSION;
				}
				else {
					this.defaulted = State.Default.HEALTHY;
				}
				break;
			case SUSPENSION:
				if(this.daysSinceNotice>=10){
					this.defaulted = State.Default.COLLECTIONS;
				}
				else if(!this.hasDue()){
					this.state = State.ACTIVE;
				}
				else
					this.daysSinceNotice++;
				break;
			case COLLECTIONS:
				break;
			case HEALTHY:
				if(!this.hasDue()){
					this.state = State.ACTIVE;
				}
				else if(this.hasMissed()){
					this.defaulted = State.Default.BAD;
				}
				break;
			case BAD:
				if(!this.hasDue()){
					this.state = State.ACTIVE;
				}
				break;
			}
			break;
		case SUSPENDED:
			if(this.daysSinceNotice>=30){
				this.state = State.CLOSED;
			}
			break;
		case CLOSED:
			break;
		}
	}
	
	private boolean hasDue(){
		// Check all bills for one that is Due.
		for(Bill bill : this.bills){
			if(bill.getStatus().equals(Bill.Status.DUE)||bill.getStatus().equals(Bill.Status.MISSED)){
				return true;
			}
		}
		return false;
	}
	
	// Checks for missed
	private void updateMissed(){
		// Check all bills for one that is Missed.
		for(Bill bill : this.bills){
			if(bill.getStatus().equals(Bill.Status.MISSED)){
				this.missed++;
			}
		}
	}

	// Checks for missed
	private boolean hasMissed(){
		// Check all bills for one that is Missed.
		for(Bill bill : this.bills){
			if(bill.getStatus().equals(Bill.Status.MISSED)){
				return true;
			}
		}
		return false;
	}
	
	// Checks if there are any active complaints.
	private boolean hasActiveComplaints(){
		for(Complaint complaint : this.complaints){
			if(complaint.getState().equals(Complaint.State.ACTIVE)){
				return true;
			}
		}
		return false;		
	}
	
	private boolean hasReplied(){
		for(Message message : this.messages){
			if(!message.hasReplied()){
				return false;
			}
		}
		return true;		
	}
	
	// Approve a product for the account.
	public void approve(Product product){
		this.products.add(product);
		this.state = State.ACTIVE;
	}
	
	// Remove products
	public void removeProduct(Product product){
		this.products.remove(product);
	}
	
	// Complain
	
	public void complain(String productName) throws Exception{
		this.complaints.add(new Complaint(productName));
		// Gotta check that the state is ACTIVE first.
		if(this.state.equals(State.ACTIVE))
			this.state = State.PRIORITY;
		else
			throw new Exception();
	}
	
	public void resolveComplaint(Complaint complaint) throws Exception{
		if(this.complaints.contains(complaint)){
			int index = this.complaints.indexOf(complaint);
			this.complaints.get(index).resolve();
			if(this.state.equals(State.PRIORITY))
				this.state = State.ACTIVE;
			else
				throw new Exception();
		}
	}
	
	// Pays all bills
	public void payAllDue(){
		for(Bill bill : this.bills){
			if(bill.getStatus().equals(Bill.Status.DUE)){
				bill.pay();
			}
		}
	}
	
	public void receiveMessage(Message msg){
		this.messages.add(msg);
	}
	
	// Replies to all messages.
	public void replyAll(){
		for(Message message : this.messages){
			message.reply();
		}
		this.daysSinceNotice = 0;
	}
	
	public void comply(){
		this.complied = true;
	}
	
	public State getState(){
		return this.state;
	}
	public State.Default getDefault(){
		return this.defaulted;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Complaint> getComplaints(){
		return this.complaints;
	}
}
