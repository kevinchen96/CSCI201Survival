package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SurvivalServer {
	
	List<PlayerThread> players; //a player's position in list is 1 minus it's id (first part of any message)
	
	public SurvivalServer(){
		players = Collections.synchronizedList(new ArrayList<PlayerThread>());
		int numPlayers = 0;
		
		try{
			URL whatismyip = new URL("http://myip.dnsomatic.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(
			                whatismyip.openStream()));
			String ip = in.readLine(); //you get the IP as a String
			System.out.println("IP address is: " + ip + "\n");
		}catch(Exception e){
			System.out.println("Couldn't retrieve ip address, please locate manually.\n");
		}
		
		try {
			System.out.println("Starting server...");
			ServerSocket ss = new ServerSocket(8000);
			System.out.println("Server running... waiting for players to join...");
			while(numPlayers < 4){
				numPlayers++;
				Socket s = ss.accept();
				System.out.println(numPlayers + " joined...");
				String tag = numPlayers + "-";
				PlayerThread pt = new PlayerThread(s, this, tag);
				players.add(pt);
				pt.start();
				sendAll("JOINED - " + numPlayers);
			}
			System.out.println("All players have joined.. game is starting..");
		} catch (IOException e) {
			System.out.println("Error connecting server. " + e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		new SurvivalServer();
	}
	
	public void interpretMessage(String msg){
		//parse through message and interpret what happens with it (i.e. who receives it / what )
	}
	
	public void sendAll(String message){
		for(int i = 0; i < players.size(); i++){
			players.get(i).send(message);
		}
	}
}
