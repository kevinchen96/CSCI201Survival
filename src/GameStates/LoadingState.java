package GameStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoadingState extends States {
	private BufferedImage background;

	public LoadingState(GameStates gameStates) {
		// TODO Auto-generated constructor stub
		
		
		
		manager = gameStates;
		try{
			background = ImageIO.read(new File("src/test.jpg"));
			RescaleOp op = new RescaleOp(.3f, 0, null);
		    background = op.filter(background, null);
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
		int coordX[] = {300, 500, 500, 300};
		int coordY[] = {100, 100, 200, 200};
		Polygon dialog = new Polygon(coordX, coordY, coordX.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(dialog);
		g.setColor(Color.GRAY);
		g.fillPolygon(dialog);
		g.setColor(Color.BLACK);
		g.drawString("Waiting...", 380, 140);
		String temp = manager.getClient().getnumPlayers() + "/4";
		if(temp!=null){
			System.out.println(temp);
			g.drawString(temp, 368, 160);
		}
	
		
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
