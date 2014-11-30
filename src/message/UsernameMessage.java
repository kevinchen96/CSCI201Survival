package message;

public class UsernameMessage extends Message {
	String username;
	
	public UsernameMessage(String username){
		super("USERNAME");
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
}
