package message;

import java.io.Serializable;

//messages READ by the SERVER but written by the client
public class Message implements Serializable {
	private String type = null;
	private int index = -1;
	
	public Message(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	
}
