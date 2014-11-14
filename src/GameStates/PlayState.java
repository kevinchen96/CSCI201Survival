package GameStates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Map.TileMap;

public class PlayState extends States{
	
	TileMap map;
	BufferedImage background;
	
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		map = new TileMap(30);
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
		try {
			background = ImageIO.read(new File("src/resources/backgrounds/grassbg1.gif"));
		} catch (IOException e) {
			System.out.println("Error with background in playstate: " + e.getMessage());
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(background, 0, 0, null );
		g.drawString("In PlayState", 277, 180);
		map.render(g);
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			
		}else if(k == KeyEvent.VK_RIGHT){
			
		}else if(k == KeyEvent.VK_DOWN){
			
		}else if(k == KeyEvent.VK_LEFT){
			
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
