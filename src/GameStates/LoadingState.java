package GameStates;

import game.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import message.CloseMessage;
import message.Message;
import message.StartMessage;

public class LoadingState extends States {
	private Image background;
	private String s = null;

	public LoadingState(GameStates gameStates) {
		// TODO Auto-generated constructor stub
		
		
		
		manager = gameStates;
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
		int coordX[] = {220, 420, 420, 220};
		int coordY[] = {100, 100, 200, 200};
		Polygon dialog = new Polygon(coordX, coordY, coordX.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(dialog);
		g.setColor(Color.GRAY);
		g.fillPolygon(dialog);
		g.setColor(Color.BLACK);
		g.drawString("Waiting...", 300, 140);
		String temp = s;
		if(temp == null){
			temp = "Error Connecting";
			g.drawString(temp, 275, 160);
			g.drawString("Return to Menu?", 277, 180);
			g.setColor(Color.WHITE);
			g.drawString("Ok", 310, 400);
		}
		else if(temp!=null){
			g.drawString(temp, 288, 160);
			g.drawString("Cancel", 330, 400);
		}		
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_ENTER){
			manager.setState(0);
			Client.sendMessageToServer(new CloseMessage());
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

	public void interpretMessage(Message message) {
		if(message.getType().equals("START")){
			StartMessage msg = (StartMessage) message;
			String tmp = msg.getMessage();
			if(tmp.equals("START")){
				manager.setState(2);
			}else{
				s = tmp;
				System.out.println("here");
				
			}
		}
		
	}

}
