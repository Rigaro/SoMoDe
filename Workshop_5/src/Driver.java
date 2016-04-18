import java.util.Date;

public class Driver {
	
	public static void main(String[] args){
		Date d = new Date();
		Customer c1 = new Customer("John");
		Bill b1 = new Bill(105f);
		Product p1 = new Product("Prod");
		Message m1 = new Message("PAY!",d,"or else!");
		
		
		// Show de pending state
		c1.update();
		System.out.println(c1.getState());
		
		// Approve with a product
		c1.approve(p1);
		c1.update();
		System.out.println(c1.getState());
		
		// Make inactive
		c1.removeProduct(p1);
		c1.update();
		System.out.println(c1.getState());
		
		// Reactivate
		c1.approve(p1);
		c1.update();
		System.out.println(c1.getState());
		
		// Complain
		c1.complain("Prod");
		c1.update();
		System.out.println(c1.getState());
		
		// Resolve the complaint
		c1.getComplaints().get(0).resolve();
		c1.update();
		System.out.println(c1.getState());
		
		// Add a Bill
		c1.addNewBill(b1);
		c1.update();
		System.out.println(c1.getState());
		
		// Miss the payment
		b1.missed();
		c1.update();
		System.out.println(c1.getState());
		c1.update();
		System.out.println(c1.getDefault());
		// Comply or not
		c1.comply();
		c1.update();
		System.out.println(c1.getState());
		System.out.println(c1.getDefault());
		// Send a message and reply or not
		c1.receiveMessage(m1);
		//c1.replyAll();
		c1.update();
		System.out.println(c1.getState());
		System.out.println(c1.getDefault());
		
		// Let it expire!
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		c1.update();
		System.out.println(c1.getState());
		System.out.println(c1.getDefault());
		
		
	}

}
