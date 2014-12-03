package GameStates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

public class Client extends Thread{

	private static ObjectOutputStream oos;
	private ObjectInputStream ois;
	public static boolean connected;
	
	public Client(String hostName) {
		//try to connect to server
		try {
			Socket s = new Socket(hostName, 8000);
			connected = true;
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.ois = new ObjectInputStream(s.getInputStream());
			
			this.start(); //start the thread (run method)
			
		} catch (Exception e) {
			System.out.println("error in Client: " + e.getMessage());
		}  
	}
	
	public void run(){
		while(true){
			try {
				while(true){
					Message msg = (Message) ois.readObject();
					GameStates.recieveMessage(msg);
				}
			} catch (Exception e) {
				
			}
		}
	}

	
	public static void sendMessageToServer(Message message){
		if(connected){
			try {
				oos.writeObject(message);
				oos.flush();
			} catch (Exception e) {
				System.out.println("Trouble sending message to server: " + e.getMessage());
			}
			
		}	
	}
	
}
