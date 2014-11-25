package GameStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import message.PlayerMessage;
import Map.TileMap;
import entity.Monster.Monster;
import entity.player.Player;
import game.GamePanel;

public class PlayState extends States{
	
	TileMap map;
	Image background;
	private static JFrame chat;
	private JTextArea chatArea;
	private JScrollPane jsp;
	private Player player;

	private int tag;

	private Monster monster;
	private Monster monster2;

	private PlayerMessage[] otherPlayers = {null, null, null, null}; 

	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {

		map = new TileMap(30); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
		tag = manager.getTag();
		map.setPosition(0,0);
		if(tag == 0){
			player = new Player(map);
			player.setPosition(100,100);
		}
		else if(tag == 1){
			player = new Player(map);
			player.setPosition(500, 100);
		}

		map = new TileMap(64); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/tiles.png");
		map.loadMap("src/resources/maps/our_map.map");
		map.setPosition(0,0);
		player = new Player(map);
		monster = new Monster(map);
		monster2 = new Monster(map);
		monster.setPosition(200, 200);
		monster2.setPosition(350,150);
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
		player.draw(g);
		
		for(PlayerMessage p : otherPlayers){
			if(p!=null && otherPlayerWithinScreen(p)){
				g.drawImage(player.getAnimation(p.getCurrentDirection(), p.getCurrentAction()), (int) (p.getX() + map.getX() - 32), (int) (p.getY() + map.getY() - 32), null);
			}
		}

		monster.draw(g);
		monster2.draw(g);
		//draw health bar
		g.setColor(Color.ORANGE);
		g.fillRect(0, 400, 700, 50);
		g.setColor(Color.RED);
		Font font2 = new Font("Verdana", Font.BOLD, 18);
		g.setFont(font2);
		g.drawString("Health: " + player.currHealth + "/" + player.health, 10, 425);
		map.setPosition(GamePanel.gameWidth()/2 - player.getx(), GamePanel.gameHeight()/2 - player.gety());
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			player.setCurrDir(0);
			player.setWalking(true);
		}if(k == KeyEvent.VK_RIGHT){
			player.setCurrDir(3);
			player.setWalking(true);
		}if(k == KeyEvent.VK_DOWN){
			player.setCurrDir(2);
			player.setWalking(true);
		}if(k == KeyEvent.VK_LEFT){
			player.setCurrDir(1);
			player.setWalking(true);
		}if(k == KeyEvent.VK_A){
			player.setSlash(true);
		}if(k == KeyEvent.VK_ENTER){
			chat.requestFocus();
		}
		player.setIdle(false);
		Client.sendMessageToServer(new PlayerMessage(player.getCurrentDirection(), player.getCurrentDirection(), player.getx(), player.gety()));
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
		}else if(message.getType().equals("PLAYER")){
			otherPlayers[message.getIndex()] = (PlayerMessage) message;
		}
	}
	
	private boolean otherPlayerWithinScreen(PlayerMessage p){
		if(p.getX() <= player.getx() + GamePanel.gameWidth()/2 && p.getX() >= player.getx() - GamePanel.gameWidth()/2){
			if(p.getY() <= player.gety() + GamePanel.gameHeight()/2 && p.getY() >= player.gety() - GamePanel.gameHeight()/2){
				return true;
			}
		}
		
		return false;
	}

}
