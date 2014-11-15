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
				String tag = "PLAYER-" + Integer.toString(players.size() + 1);
				PlayerThread pt = new PlayerThread(s, this, tag);
				players.add(pt);
				System.out.println(players.size() + " joined...");
				
				pt.start();
				sendAll("JOINED-" + players.size());
			}
			System.out.println("All players have joined.. game is starting..");
			sendAll("START-");
			playing = true;
		} catch (IOException e) {
			System.out.println("Error connecting server. " + e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		new SurvivalServer();
	}
	
	public void interpretMessage(String msg){
		//parse through message and interpret what happens with it (i.e. who receives it / what )
		
		/*check if closed ------- we should eventually move this code to another 
		function or class to make it more readable when we have like a million cases   */
		String[] parts = msg.split("-");
		System.out.println(msg);
		if(parts[0].equals("PLAYER")){
			if(parts[2].equals("CLOSE") || parts[2].equals("null")){
				int index = (Integer.parseInt(parts[1]) -1);
				players.get(index).send("CLOSE");
				players.remove(index);
				if(playing){
					//notify other players that a player has left -------------------- still needs to be implemented
					//note that this can't be as simple as just updating people's tags because it would message with 
					//people's stats and stuff.
					//should either do the work to transition over all stats, or just end the game.
				}else{
					sendAll("JOINED-" + players.size());
					//update tags
					for(int i = 0; i < players.size(); i++){
						players.get(i).updateTag("PLAYER-" + Integer.toString(i+1));
					}
				}
				
			}
			else if(parts[2].equals("CHAT")){
				if(parts[3].equals("ALL")){
					sendAll("CHAT-" + parts[4]);
				}
			}
		}
	}
	
	public void sendAll(String message){
		for(int i = 0; i < players.size(); i++){
			players.get(i).send(message);
		}
	}
}
