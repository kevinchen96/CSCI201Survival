package message;

public class CloseMessage extends Message {
	
	private int index = -1;
	
	public CloseMessage() {
		super("CLOSE");
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}

}
