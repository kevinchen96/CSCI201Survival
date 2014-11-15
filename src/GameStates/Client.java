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
	private String numPlayers, start;
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
					if(line.split("-")[0].equals("JOINED")){
						System.out.println("Hi");
						numPlayers = line;
					}
					else if(line.split("-")[0].equals("START")){
						start = line;
					}else{
						GameStates.recieveMessage(line);
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
	
	public String checkStart(){
		return start;
	}
	
	public static void sendMessageToServer(String message){
		if(connected){
			pw.println(message);
			pw.flush();
		}	
	}
	
}
