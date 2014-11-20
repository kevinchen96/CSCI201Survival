package message;

public class ChatMessage extends Message{

	String message;
	String from;
	
	public ChatMessage(String message) {
		super("CHAT");
		this.message = message;
		
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getMessage(){
		return message;
	}
	
	
}
