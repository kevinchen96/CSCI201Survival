package game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameStates.Client;
import GameStates.GameStates;

public class GamePanel extends JPanel  implements Runnable, KeyListener {
	private static String TITLE = new String("SUVIVAL");
	private static JFrame FRAME = new JFrame(TITLE);
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	private Graphics2D g;
	private BufferedImage image;
	private GameStates manager;
	
	public GamePanel(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		manager = new GameStates();
		
		FRAME.setSize(WIDTH, HEIGHT);
		FRAME.setResizable(false);
		FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		FRAME.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Exit??", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                	FRAME.setVisible(false);
                	if(Client.connected){
                		Client.sendMessageToServer("CLOSE");	
                	}
    				System.exit(0);
                }
				
			}
		});
		FRAME.setLocationRelativeTo(null);
		
		
		FRAME.add(this);
		
		FRAME.setVisible(true);
		this.requestFocusInWindow();
		addKeyListener(this);
		
		run();
	}


	public void run() {
		long lastTime = System.nanoTime(); 
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
				updates++;
			}
			render();
			drawToScreen();
			frames++;
			if((System.currentTimeMillis() - timer) > 1000){
				timer+= 1000;
				FRAME.setTitle(TITLE + "      ||      FPS: " + frames + "      ||      UPS: " + updates);
				frames = 0;
				updates = 0; 
			}
		}
	}
	
	//handles logic and server communication
	public void tick(){
		
	}
	
	//handles graphics 
	public void render(){
		manager.draw(g);
	}
	
	
	//refreshes screen
	public void drawToScreen(){
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		manager.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		manager.keyReleased(e.getKeyCode());
	}
	
	public static void main(String[] args){
		new GamePanel();
	}
}
