package message;

public class StartMessage extends Message{
	private int numJoined;
	
	public StartMessage(int n){
		super("START");
		this.numJoined = n;
	}
	
	
	public String getMessage(){
		if(numJoined < 5){
			return "Joined - " + numJoined + " / 4"; 
		}else{
			return "START";
		}
	}
}
