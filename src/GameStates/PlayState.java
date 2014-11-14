package GameStates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Map.TileMap;

public class PlayState extends States{
	
	TileMap map;
	
	public PlayState(GameStates gameStates){
		manager = gameStates;
	}

	@Override
	public void init() {
		/*map = new TileMap(30);
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
		*/
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawString("In PlayState", 277, 180);
		//map.render(g);
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
