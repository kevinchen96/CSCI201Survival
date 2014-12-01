package GameStates;

import game.GamePanel;
import items.TextItem;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import message.ChatMessage;
import message.Message;
import message.MonsterMessage;
import message.PlayerMessage;
import message.UsernameMessage;
import message.WhisperMessage;
import Map.TileMap;
import entity.Monster.Monster;
import entity.player.Player;

public class PlayState extends States{
	
	TileMap map;
	Image background;
	private static JFrame chat;
	private JTextArea chatArea;
	private JScrollPane jsp;
	private Player player;
	private Monster monster;
	private static List<TextItem> textItems;
	private Monster monster2;
	private PlayerMessage[] otherPlayers = {null, null, null, null}; 
	private int tag;
	private ArrayList<Monster> Monsters;
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		textItems = Collections.synchronizedList(new ArrayList<TextItem>());
		tag = manager.getTag();
		map = new TileMap(64); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/tiles.png");
		map.loadMap("src/resources/maps/our_map.map");
		map.setPosition(0,0);

		if(tag == 0){
			player = new Player(map);
			player.setPosition(100,100);
		}
		else if(tag == 1){
			player = new Player(map);
			player.setPosition(500, 100);
		}
		Monsters = new ArrayList<Monster>();
		
		monster = new Monster(map);
		monster2 = new Monster(map);
		monster.setPosition(200, 200);
		monster2.setPosition(350,150);
		Monsters.add(monster);
		Monsters.add(monster2);
	
		try {
			BufferedImage temp;
			temp = ImageIO.read(new File("src/resources/backgrounds/grassbg1.gif"));
			background = temp.getScaledInstance(GamePanel.gameWidth(), GamePanel.gameHeight(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Error with background in playstate: " + e.getMessage());
		}
		startChat();
		Client.sendMessageToServer(new UsernameMessage(JOptionPane.showInputDialog(new JFrame(), "Please pick a username.").replace(" ", "")));
		
	}

	private void startChat() {
		chat = new JFrame("Survival Chat");
		chat.setSize(GamePanel.gameWidth(), 150);
		chat.setResizable(false);
		chat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		chat.setLocation(GamePanel.gameWidth(), GamePanel.gameHeight());
		
		String intro = "You have joined the game!\nUse this window to chat with other players:\n ";
		intro += "- by default your message will be sent to everyone.\n";
		intro += "- To send a private message place recipient usernames inside '<>' at beginning of message \n";
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
						if(new Character(message.charAt(0)).equals('<') && message.contains(">")){
							String toParse = message.substring(message.indexOf('<') +1, message.indexOf('>'));
							message = message.substring(message.indexOf('>') + 1, message.length());
							String[] to = toParse.split(" ");
							Client.sendMessageToServer(new WhisperMessage(message, to, player.getUsername()));
							jtf.setText("");
						}
						else{
							Client.sendMessageToServer(new ChatMessage(message, player.getUsername()));
							jtf.setText("");
						}
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
		for(Monster m: Monsters){
			m.draw(g);
		}
		player.draw(g);
		
		for(PlayerMessage p : otherPlayers){
			if(p!=null && otherPlayerWithinScreen(p)){
				g.drawString(p.getUsername(), (int)(p.getX() + map.getX()) - p.getUsername().length() * 3, (int)(p.getY() + map.getY()) - 25);
				g.drawImage(player.getAnimation(p.getCurrentDirection(), p.getCurrentAction(), p.getCurrentFrame()), (int) (p.getX() + map.getX() - 32), (int) (p.getY() + map.getY() - 32), null);
			}
		}
		
		for(TextItem t : textItems){
			t.draw(g);
		}
		
		map.setPosition(GamePanel.gameWidth()/2 - player.getx(), GamePanel.gameHeight()/2 - player.gety());
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			player.setCurrDir(0);
			player.setWalking(true);
		}else if(k == KeyEvent.VK_RIGHT){
			player.setCurrDir(3);
			player.setWalking(true);
		}else if(k == KeyEvent.VK_DOWN){
			player.setCurrDir(2);
			player.setWalking(true);
		}else if(k == KeyEvent.VK_LEFT){
			player.setCurrDir(1);
			player.setWalking(true);
		}else if(k == KeyEvent.VK_A){
			player.setSlash(true);
			for(int i = 0; i < Monsters.size(); i++){
				if(otherMonsterWithinAttack(Monsters.get(i))){
					System.out.println(i);
					System.out.println("Attack to server");
					Client.sendMessageToServer(new MonsterMessage(player.getStrength(), i));
				}
			}
		}if(k == KeyEvent.VK_ENTER){
			chat.requestFocus();
		}		
		player.setIdle(false);
		Client.sendMessageToServer(new PlayerMessage(player.getCurrentDirection(), player.getCurrentAction(), player.getCurrentFrame(), player.getUsername(), player.getx(), player.gety()));
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
		}if(k == KeyEvent.VK_DOWN){
			player.setSlash(false);
		}
		player.setIdle(true);
	}
	

	public void interpretMessage(Message message) {
		if(message.getType().equals("CHAT")){
			ChatMessage msg = (ChatMessage)message;
			String m = msg.getMessage();
			chatArea.setText(chatArea.getText() + "\n" + msg.getFrom() + ": " + m);
			try {
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				jsp.scrollRectToVisible(chatArea.modelToView(chatArea.getDocument().getLength()));
			} catch (BadLocationException e) {
			}
		}else if(message.getType().equals("WHISPER")){
			WhisperMessage msg = (WhisperMessage)message;
			String m = msg.getMessage();
			chatArea.setText(chatArea.getText() + "\n" + "(Whispered From: " + msg.getFrom() +  ") " + m);
			try {
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				jsp.scrollRectToVisible(chatArea.modelToView(chatArea.getDocument().getLength()));
			} catch (BadLocationException e) {
			}
			
		}else if(message.getType().equals("USERNAME")){
			player.setUsername(((UsernameMessage)message).getUsername());
		}
		else if(message.getType().equals("PLAYER")){
			otherPlayers[message.getIndex()] = (PlayerMessage) message;
		}
		else if(message.getType().equals("MONSTER")){
			Monsters.get(((MonsterMessage) message).getWhich()).attacked(((MonsterMessage) message).getDamage());
			if(Monsters.get(((MonsterMessage) message).getWhich()).isDead()){
				System.out.println("Monster Died");
				Monsters.remove(((MonsterMessage) message).getWhich());
				System.out.println(((MonsterMessage) message).getWhich());
			}
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

	
	public static void createNewTextItem(String text, int x, int y){
		if(textItems != null){
			textItems.add(new TextItem(text, x, y, textItems));
		}
	}
	private boolean otherMonsterWithinAttack(Monster p){
		if(p.getx() <= player.getx() + 40 && p.getx() > player.getx()){
			if(player.getCurrentDirection() == 3){
				if(p.gety() <= player.gety()+20 && p.gety() >= player.gety()-20){
					return true;
				}
			}
		}
		if(p.getx() >= player.getx() - 40 && p.getx() < player.getx()){
			if(player.getCurrentDirection() == 1){
				if(p.gety() <= player.gety()+20 && p.gety() >= player.gety()-20){
					return true;
				}
			}
		}
		if(p.gety() <= player.gety() + 60 && p.gety() > player.gety()){
			if(player.getCurrentDirection() == 2){
				if(p.getx() <= player.getx() + 40 && p.getx() >= player.getx()-40){
					return true;
				}
			}
		}
		if(p.gety() >= player.gety() - 60 && p.gety() < player.gety()){
			if(player.getCurrentDirection() == 0){
				if(p.getx() <= player.getx() + 40 && p.getx() >= player.getx()-40){
					return true;
				}
			}
		}
		
		return false;
	}
}
