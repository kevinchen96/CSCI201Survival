package message;

public class StartingMessage extends Message {
private int tag;
	
	public StartingMessage(int n){
		super("STARTING");
		tag = n;
	}
	
	
	public int getMessage(){
		return tag;
	}
}
