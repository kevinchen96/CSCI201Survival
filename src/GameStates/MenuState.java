package GameStates;

import game.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuState extends States {
	private int current = 0;
	private String[] types = {"Play", "Help", "Exit"};
	private Image background;
	public MenuState(GameStates gsm){
		manager = gsm;
		try{
			BufferedImage temp = ImageIO.read(new File("src/resources/backgrounds/test.jpg"));
			background = temp.getScaledInstance(GamePanel.gameWidth(), GamePanel.gameHeight(), Image.SCALE_SMOOTH);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.RED);
		g.drawString("Survival", 80, 70);
		
		for(int i = 0; i < 3; i++){
			if(i == current){
				g.setColor(Color.WHITE);
			}
			else{
				g.setColor(Color.BLACK);
			}
			g.drawString(types[i], 145, 140 + 15*i);
		}
		
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_ENTER){
			choose();
		}
		else if(k == KeyEvent.VK_UP){
			current--;
			if(current == -1){
				current = 2;
			}
		}
		else if(k == KeyEvent.VK_DOWN){
			current++;
			if(current == 3){
				current = 0;
			}
		}
	}

	public void choose() {
		// TODO Auto-generated method stub
		if(current == 0){
			String ip = JOptionPane.showInputDialog("Enter IP Address");
			if(ip == null){
				manager.setState(0);
			}
			else{
				manager.setClient(new Client(ip));
				manager.setState(1);
			}
		}else if(current == 1){
			JDialog about = new JDialog(new JFrame(), "About", true);
			about.setSize(400, 400);
			about.setLocationRelativeTo(null);
			about.setVisible(true);
		}else{
			System.exit(0);
		}
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
