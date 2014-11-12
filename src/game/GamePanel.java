package game;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GameStates.GameStates;

public class GamePanel extends JPanel  implements Runnable, KeyListener {
	private static String TITLE = new String("Survival");
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
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setLocationRelativeTo(null);
		
		
		FRAME.add(this);
		
		FRAME.setVisible(true);
		this.requestFocusInWindow();
		addKeyListener(this);
		
		run();
	}


	public void run() {
		long start = System.nanoTime(); 
		double elapsed;
		
		while(true){
			tick();
			
			elapsed = (double)(System.nanoTime() - start) / 1000000.0;
			if(elapsed >= 60){
				FRAME.setTitle(TITLE + "       ||       FPS: " + (int)elapsed);
				render();
				drawToScreen();
				start = System.nanoTime();
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
