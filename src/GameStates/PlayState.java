package GameStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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

import message.ChatMessage;
import message.Message;
import Map.TileMap;
import entity.player.Player;
import game.GamePanel;

public class PlayState extends States{
	
	TileMap map;
	Image background;
	private static JFrame chat;
	private JTextArea chatArea;
	private JScrollPane jsp;
	private Player player;
	
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		map = new TileMap(64); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/tiles.png");
		map.loadMap("src/resources/maps/our_map.map");
		map.setPosition(0,0);
		player = new Player(map);
		player.setPosition(100,100);
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
						Client.sendMessageToServer(new ChatMessage(message));
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
		player.update();
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
		if(player == null) System.out.println("What");
		
		player.draw(g);
		
		map.setPosition(GamePanel.gameWidth()/2 - player.getx(), GamePanel.gameHeight()/2 - player.gety());
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			System.out.println("up");
			player.setCurrDir(0);
			player.setWalking(true);
		}if(k == KeyEvent.VK_RIGHT){
			System.out.println("right");
			player.setCurrDir(3);
			player.setWalking(true);
		}if(k == KeyEvent.VK_DOWN){
			System.out.println("down");
			player.setCurrDir(2);
			player.setWalking(true);
		}if(k == KeyEvent.VK_LEFT){
			System.out.println("left");
			player.setCurrDir(1);
			player.setWalking(true);
		}if(k == KeyEvent.VK_A){
			player.setSlash(true);
		}if(k == KeyEvent.VK_ENTER){
			chat.requestFocus();
		}
		player.setIdle(false);
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_UP){
			player.setWalking(false);	
		}if(k == KeyEvent.VK_RIGHT){
			player.setWalking(false);
		}if(k == KeyEvent.VK_DOWN){
			player.setWalking(false);
		}if(k == KeyEvent.VK_LEFT){
			player.setWalking(false);
		}
		player.setIdle(true);
	}
	

	public void interpretMessage(Message message) {
		if(message.getType().equals("CHAT")){
			ChatMessage msg = (ChatMessage)message;
			String m = msg.getMessage();
			chatArea.setText(chatArea.getText() + "\n" + m);
			try {
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				jsp.scrollRectToVisible(chatArea.modelToView(chatArea.getDocument().getLength()));
			} catch (BadLocationException e) {
			}
		}
	}

}
