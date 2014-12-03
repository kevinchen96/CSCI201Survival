package GameStates;

import game.GamePanel;
import items.TextItem;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import server.PlayerThread;
import server.SurvivalServer;
import message.ChatMessage;
import message.Message;
import message.Monster2Message;
import message.MonsterMessage;
import message.PlayerMessage;
import message.RecoveryMessage;
import message.UsernameMessage;
import message.WhisperMessage;
import Map.TileMap;
import entity.Monster.Monster;
import entity.Monster.Monster2;
import entity.player.Player;

public class PlayState extends States{
	private ArrayList<Monster> skeletons;
	private ArrayList<Monster2> orcs;
	TileMap map;
	Image background;
	private static JFrame chat;
	private JTextArea chatArea;
	private JScrollPane jsp;
	private Player player;
	private Monster monster;
	private static List<TextItem> textItems;
	private Monster2 monster2;
	private PlayerMessage[] otherPlayers = {null, null, null, null}; 
	private int tag;
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
			player.setPosition(100, 100);
		}
		else if(tag == 1){
			player = new Player(map);
			player.setPosition(300, 100);
		}
		else if(tag == 2){
			player = new Player(map);
			player.setPosition(300, 300);
		}
		else if(tag == 3){
			player = new Player(map);
			player.setPosition(100, 300);
		}
		orcs = new ArrayList<Monster2>();
		skeletons = new ArrayList<Monster>();
		int[] x = {2591,2438,315,3494,563,920,3373,2424,2020,1162,698,206,1583,1610,3322,3606,3568,1211,2776,1932,395,1260,1350,2923,170,726,2039,2833,3657,3563,1891,3093,3133,1327,3256,2823,899,888,2113,1841,564,2911,2666,2535,1881,2362,348,178,658,336,604,1880,1621,3709,2538,1197,3509,1986,3229,758,1520,2128,1277,2089,2408,163,2043,2818,3411,2220,1721,2153,3596,2392,895,1231,1398,1541,629,2687,2020,3049,1870,3607,318,2519,2587,2804,1747,1658,1341,2158,3162,2390,2910,2595,1333,3022,683,1171,856,2452,691,3356,830,193,1877,3393,1312,901,1562,3679,232,2546,1037,1293,1437,1252,342,926,297,2257,2777,930,2034,1919,3186,3674,2018,3130,220,886,646,991,2106,1689,410,1213,1249,1931,1592,2781,332,2203,1896,2143,1527,1107,1100,369,3116,305,489,2577,1239,1409,2751,1959,3337,3403,3305,3550,1725,3274,1060,3661,1311,1513,1386,1563,3222,2242,1878,1004,726,2663,2670,282,2996,3617,3507,742,1213,1705,2669,2139,2552,1249,2020,1687,2758,180,1160,1089,227,1438,2997,1151,1727,1412};
		int[] y = {418,1763,2583,2589,375,1353,464,1917,1808,1303,1122,1836,1284,3355,2676,664,2274,1725,2918,334,1863,2074,2592,2158,1489,2022,3143,3657,3592,2337,185,2893,373,1190,1140,2096,2049,3517,1857,1117,315,2563,1251,1443,2041,2956,2920,2858,1016,1564,2047,3262,2383,3429,3493,1540,1690,2480,2751,3163,2802,3671,296,873,3489,272,1610,267,2084,3564,3669,1835,311,1351,1446,3033,2795,1583,3635,1462,996,2807,722,1398,454,1070,3649,917,657,2455,1239,3007,2389,679,1419,3674,1475,2592,194,3549,2582,1375,233,520,2875,2354,2685,3206,620,2402,2541,795,277,1140,2271,971,1975,1767,3573,2316,2031,1372,2961,1665,1179,1379,1631,2254,250,1379,992,3225,2617,2452,1291,1224,695,2339,1279,3129,945,3330,2092,1917,2931,584,2926,3098,2382,3304,2346,1942,860,372,3605,3524,243,895,2687,1983,2001,2820,3318,688,1787,1812,3268,2913,1041,1303,1053,2271,1876,3649,3121,917,334,3549,2022,2411,3084,1024,1478,436,2491,1748,2987,2223,374,1081,3393,2589,2165,1994,178,1288,2229,1772,2136,1122};
		System.out.println(x.length);
		System.out.println(y.length);
		for (int i = 0; i < 100; i++) {
			monster2 = new Monster2(map);
			monster2.setPosition(x[i], y[i]);
			orcs.add(monster2);
		}
		for (int i = 100; i < 200; i++) {
			monster = new Monster(map);
			monster.setPosition(x[i], y[i]);
			skeletons.add(monster);
		}
	
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
		//drawing orcs
		for(int i = 0; i < orcs.size(); i++){
			orcs.get(i).draw(g);
		}
		//drawing skeletons
		for(int i = 0; i < skeletons.size(); i++){
			skeletons.get(i).draw(g);
		}
			player.draw(g);
		
		for(PlayerMessage p : otherPlayers){
			if(p!=null && otherPlayerWithinScreen(p)){
				g.drawString(p.getUsername(), (int)(p.getX() + map.getX() -2) - p.getUsername().length() * 5, (int)(p.getY() + map.getY()) - 25);
				g.drawImage(player.getAnimation(p.getCurrentDirection(), p.getCurrentAction(), p.getCurrentFrame()), (int) (p.getX() + map.getX() - 32), (int) (p.getY() + map.getY() - 32), null);
			}
		}
		
		for(TextItem t : textItems){
			t.draw(g);
		}
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
			for(int i = 0; i < skeletons.size(); i++){
				if(otherMonsterWithinAttack(skeletons.get(i))){
					System.out.println(i);
					System.out.println("Attack to server");
					Client.sendMessageToServer(new MonsterMessage(player.getStrength(), i));
					player.currHealth = player.currHealth + 10;
					player.startAttacked();
				}
			}
			for(int i = 0; i < orcs.size(); i++){
				if(otherMonsterWithinAttack(orcs.get(i))){
					System.out.println(i);
					System.out.println("Attack to server");
					Client.sendMessageToServer(new Monster2Message(player.getStrength(), i));
					player.currHealth = player.currHealth + 30;
					player.startAttacked();
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
			skeletons.get(((MonsterMessage) message).getWhich()).attacked(((MonsterMessage) message).getDamage());
			if(skeletons.get(((MonsterMessage) message).getWhich()).isDead()){
				player.stopAttacked();
				//player.currHealth = player.currHealth + 100;
				System.out.println("Monster Died");
				skeletons.remove(((MonsterMessage) message).getWhich());
				System.out.println(((MonsterMessage) message).getWhich());
			}
		}
		else if (message.getType().equals("MONSTER2")) {
			orcs.get(((Monster2Message) message).getWhich()).attacked(((Monster2Message) message).getDamage());
			if(orcs.get(((Monster2Message) message).getWhich()).isDead()){
				player.stopAttacked();
				//player.currHealth = player.currHealth + 200;
				System.out.println("Monster Died");
				orcs.remove(((Monster2Message) message).getWhich());
				System.out.println(((Monster2Message) message).getWhich());
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
	private boolean otherMonsterWithinAttack(Monster2 p){
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
