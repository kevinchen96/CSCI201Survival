package GameStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{

	private static PrintWriter pw;
	private BufferedReader br;
	private String numPlayers;
	public static boolean connected;
	
	public Client(String hostName) {
		//try to connect to server
		try {
			Socket s = new Socket(hostName, 8000);
			connected = true;
			this.pw = new PrintWriter(s.getOutputStream());
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			this.start(); //start the thread (run method)
			
		} catch (Exception e) {
			numPlayers = "Error Connecting";
		}  
	}
	
	public void run(){
		while(true){
			try {
				while(true){
					String line = br.readLine();
					System.out.println(line);
					if(line.substring(0, 6).equals("JOINED")){
						numPlayers = line;
					}
				}
			} catch (IOException e) {
				System.out.println("Trouble reading line in Chat Client: " + e.getMessage());
			}
		}
	}

	public String getnumPlayers() {
		// TODO Auto-generated method stub
		return numPlayers;
	}

	
	public static void sendMessageToServer(String message){
		pw.println(message);
		pw.flush();
	}
	
}
