package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class PlayerThread extends Thread{

	String tag;
	PrintWriter playerWriter;
	BufferedReader playerReader;
	SurvivalServer server;
	private boolean running = true;
	
	public PlayerThread(Socket s, SurvivalServer ss, String tag) {
		this.tag = tag;
		try{
			playerWriter = new PrintWriter(s.getOutputStream());
			playerReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}catch(Exception e){
			System.out.println("Error getting " + tag + "'s streams: " + e.getMessage() );
		}
		this.server = ss;
	}
	

	public void run() {
		while(running){
			try {
				String message = tag + "-" + playerReader.readLine();
				server.interpretMessage(message);
			} catch (IOException e) {
				System.out.println("ioexception reading message from " + tag + "'s reader: " + e.getMessage());
			}
		}
	}
	
	public void send(String message){
		if(message.equals("CLOSE")){
			System.out.println("a player has left the game...");
			running = false;
		}
		playerWriter.println(message);
		playerWriter.flush();
	}
	
	public void updateTag(String tag){
		this.tag = tag; 
	}
}
