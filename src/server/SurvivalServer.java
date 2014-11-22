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

import message.Message;
import message.StartMessage;

public class SurvivalServer {
	
	List<PlayerThread> players; //a player's position in list is 1 minus it's id (first part of any message)
	private boolean playing = false;
	
	public SurvivalServer(){
		players = Collections.synchronizedList(new ArrayList<PlayerThread>());
		
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
			while(players.size() < 1){
				Socket s = ss.accept();
				PlayerThread pt = new PlayerThread(s, this, players.size());
				players.add(pt);
				System.out.println(players.size() + " joined...");
				
				pt.start();
				sendAll(new StartMessage(players.size()));
			}
			sendAll(new StartMessage(5));
			System.out.println("All players have joined.. game is starting..");
			playing = true;
		} catch (IOException e) {
			System.out.println("Error connecting server. " + e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		new SurvivalServer();
	}
	
	public void interpretMessage(Message msg){
			if(msg.getType().equals("CLOSE")){
				if(msg.getIndex() >= 0){
					players.get(msg.getIndex()).send(msg);
					players.remove(msg.getIndex());
					for(int i = 0; i < players.size(); i++){
						players.get(i).updateIndex(i);
					}
				}				
			}else{
				sendAll(msg);
			}
	}
	
	public void sendAll(Message message){
		for(int i = 0; i < players.size(); i++){
			players.get(i).send(message);
		}
	}
}
