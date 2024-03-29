package GameStates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import message.Message;
import message.StartingMessage;

public class GameStates {
	private static ArrayList<States> states;
	private Client user;
	private int tag;
	public static int currentState;
	public int MenuState = 0;
	public int LoadingState = 1;
	
	public GameStates(){
		states = new ArrayList<States>();
		currentState = MenuState;
		states.add(new MenuState(this));
		states.add(new LoadingState(this));
		states.add(new PlayState(this));
	}
	public void setState(int s){
		currentState = s;
		states.get(s).init();
	}
	public void update(){
		states.get(currentState).update();
	}
	public void draw(Graphics2D g){
		states.get(currentState).draw(g);
	}
	public void keyPressed(int k){
		states.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		states.get(currentState).keyReleased(k);
	}
	public void setClient(Client client){
		user = client;
	}
	public Client getClient(){
		return user;
	}
	
	public static void recieveMessage(Message message){
		states.get(currentState).interpretMessage(message);
	}
	public void setTag(int i){
		tag = i;
	}
	public int getTag(){
		return tag;
	}
}
