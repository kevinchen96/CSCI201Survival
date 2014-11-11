package GameStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MenuState extends States {
	private int current = 0;
	private String[] types = {"Play", "Help", "Exit"};
	private BufferedImage background;
	public MenuState(GameStates gsm){
		manager = gsm;
		try{
			background = ImageIO.read(getClass().getResourceAsStream("src/test.jpg"));
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
			if(current == 2){
				current = 0;
			}
		}
	}

	public void choose() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
