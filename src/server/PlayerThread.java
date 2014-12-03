package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;
import message.UsernameMessage;

public class PlayerThread extends Thread{

	private int index;
	private String username;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private SurvivalServer server;
	private boolean running = true;
	
	public PlayerThread(Socket s, SurvivalServer ss, int index) {
		this.index = index;
		try{
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		}catch(Exception e){
			System.out.println("Error getting " + index + "'s streams: " + e.getMessage() );
		}
		this.server = ss;
	}
	
	public String getUsername(){
		return username;
	}

	public void run() {
		while(running){
			try {
				Message msg;
				try {
					msg = (Message) ois.readObject();
					msg.setIndex(index);
					server.interpretMessage(msg);
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found exception: " + e.getMessage()  );
				}
				
			} catch (IOException e) {
				System.out.println("ioexception reading message from " + index + "'s reader: " + e.getMessage());
			}
		}
	}
	
	public void send(Message message){
		if(message.getType().equals("MONSTER") || message.getType().equals("MONSTER2"))message.setIndex(index);
		try{
			if(message.getType().equals("CLOSE")){
				System.out.println("a player has left the game...");
				running = false;
			}else if(message.getType().equals("USERNAME")){
				this.username = ((UsernameMessage) message).getUsername();
				oos.writeObject(message);
				oos.flush();
			}else{
				oos.writeObject(message);
				oos.flush();
			}			
		}catch(Exception e){
			System.out.println("Trouble sending message of type '" + message.getType() + "': " + e.getMessage() );
		}
		
	}
	
	public void updateIndex(int index){
		this.index = index; 
	}
}
