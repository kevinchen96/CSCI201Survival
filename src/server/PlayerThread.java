package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

public class PlayerThread extends Thread{

	int index;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	SurvivalServer server;
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
		try{
			if(message.getType().equals("CLOSE")){
				System.out.println("a player has left the game...");
				running = false;
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
