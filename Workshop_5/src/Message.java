import java.util.Date;

public class Message {
	private String title;
	private Date date;
	private String content;
	private boolean replied;
	
	public Message(String title, Date date, String content){
		this.title = title;
		this.date = date;
		this.content = content;
		this.replied = false;
	}
	
	public void reply(){
		this.replied = true;
	}
	
	public boolean hasReplied(){
		return replied;
	}
	public Date getDate(){
		return this.date;
	}
}
