package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerThread extends Thread{

	String tag;
	PrintWriter playerWriter;
	BufferedReader playerReader;
	SurvivalServer server;
	
	public PlayerThread(Socket s, SurvivalServer ss, String id) {
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
		while(true){
			try {
				String message = tag + "-" + playerReader.readLine();
			
			} catch (IOException e) {
				System.out.println("ioexception reading message from " + tag + "'s reader: " + e.getMessage());
			}
		}
	}
	
	public void send(String message){
		playerWriter.println(message);
		playerWriter.flush();
	}
}
