package GameStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
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
			background = ImageIO.read(new File("src/resources/backgrounds/test.jpg"));
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
		int coordX[] = {220, 420, 420, 220};
		int coordY[] = {100, 100, 200, 200};
		Polygon dialog = new Polygon(coordX, coordY, coordX.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(dialog);
		g.setColor(Color.GRAY);
		g.fillPolygon(dialog);
		g.setColor(Color.BLACK);
		g.drawString("Waiting...", 300, 140);
		String temp = manager.getClient().getnumPlayers();
		if(temp == null){
			//System.out.println("Hi");
		}
		else if(temp.equals("Error Connecting")){
			temp = "Error Connecting";
			g.drawString(temp, 275, 160);
			g.drawString("Return to Menu?", 277, 180);
			g.setColor(Color.WHITE);
			g.drawString("Ok", 310, 400);
		}
		else if(temp!=null){
			temp += "/4";
			g.drawString(temp, 288, 160);
			g.drawString("Cancel", 330, 400);
		}
		String checkStart = manager.getClient().checkStart();
		if(checkStart!=null){
			manager.setState(2);
		}
		
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_ENTER){
			manager.setState(0);
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
