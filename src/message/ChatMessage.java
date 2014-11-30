package message;

public class ChatMessage extends Message{

	String message;
	String from;
	
	public ChatMessage(String message, String from) {
		super("CHAT");
		this.message = message;
		this.from = from;
		
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getMessage(){
		return message;
	}
	
	
}
