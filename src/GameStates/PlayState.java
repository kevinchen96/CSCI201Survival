package GameStates;

import game.GamePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import Map.TileMap;

public class PlayState extends States{
	
	TileMap map;
	Image background;
	private static JFrame chat;
	private JTextArea chatArea;
	private JScrollPane jsp;
	
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		map = new TileMap(30); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
		try {
			BufferedImage temp;
			temp = ImageIO.read(new File("src/resources/backgrounds/grassbg1.gif"));
			background = temp.getScaledInstance(GamePanel.gameWidth(), GamePanel.gameHeight(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Error with background in playstate: " + e.getMessage());
		}
		startChat();
	}

	private void startChat() {
		chat = new JFrame("Survival Chat");
		chat.setSize(GamePanel.gameWidth(), 150);
		chat.setResizable(false);
		chat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		chat.setLocation(GamePanel.gameWidth(), GamePanel.gameHeight());
		
		String intro = "You have joined the game!\nUse this window to chat with other players:\n ";
		intro += "- by default your message will be sent to everyone.\n";
		intro += "- other message methods still need to be implemented.\n";
		intro += "\nGood Luck!\n";
		
		chatArea = new JTextArea(intro);
		chatArea.setEditable(false);
		jsp = new JScrollPane(chatArea);
		
		final JTextField jtf = new JTextField();
		jtf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){					
					
					String message = jtf.getText();
					message = message.replace('-', '~');
					message = message.replace('"','\"');
					if(!message.equals("")){
						Client.sendMessageToServer("CHAT-" + "ALL-" + message);
						jtf.setText("");
					}
					
				}
			}
		});
		
		chat.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				jtf.requestFocus();
			}
		});
		
		chat.add(jsp, BorderLayout.CENTER);
		chat.add(jtf, BorderLayout.SOUTH);
		
		GamePanel.addChatWindow(chat);
	}
	
	public static void closeChat(){
		if(chat != null){
			chat.dispose();
			chat.setVisible(false);
		}
	}
	
	

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		//clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.gameWidth(), GamePanel.gameHeight());
		
		//draw background
		g.drawImage(background, 0, 0, null );

		//draw map
		map.render(g);
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			System.out.println("up");
		}else if(k == KeyEvent.VK_RIGHT){
			System.out.println("right");
		}else if(k == KeyEvent.VK_DOWN){
			System.out.println("down");
		}else if(k == KeyEvent.VK_LEFT){
			System.out.println("left");
		}else if(k == KeyEvent.VK_ENTER){
			chat.requestFocus();
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}
	

	public void interpretMessage(String message) {
		if(message.split("-")[0].equals("CHAT")){
			chatArea.setText(chatArea.getText() + "\n" + message.split("-")[1]);
			try {
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				jsp.scrollRectToVisible(chatArea.modelToView(chatArea.getDocument().getLength()));
			} catch (BadLocationException e) {
			}
		}
	}

}
