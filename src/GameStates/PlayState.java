package GameStates;

import game.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Map.TileMap;

public class PlayState extends States{
	
	TileMap map;
	Image background;
	
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		map = new TileMap(30); // parameter = square size of tiles (pixels)
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
		try {
			BufferedImage temp;
			temp = ImageIO.read(new File("src/resources/backgrounds/grassbg1.gif"));
			background = temp.getScaledInstance(GamePanel.gameWidth(), GamePanel.gameHeight(), Image.SCALE_SMOOTH);
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
		//clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.gameWidth(), GamePanel.gameHeight());
		
		//draw background
		g.drawImage(background, 0, 0, null );

		//draw map
		map.render(g);
	}

	@Override
	public void keyPressed(int k) {

		//movement 
		if(k == KeyEvent.VK_UP){
			System.out.println("up");
		}else if(k == KeyEvent.VK_RIGHT){
			System.out.println("right");
		}else if(k == KeyEvent.VK_DOWN){
			System.out.println("down");
		}else if(k == KeyEvent.VK_LEFT){
			System.out.println("left");
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
