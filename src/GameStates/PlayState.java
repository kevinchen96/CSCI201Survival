package GameStates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

<<<<<<< HEAD
public class PlayState extends States {

	public PlayState(GameStates gameStates) {
		// TODO Auto-generated constructor stub
		manager = gameStates;
=======
import Map.TileMap;

public class PlayState extends States{
	
	TileMap map;
	
	public PlayState(){
		
>>>>>>> 48b927f906fec7368a0e3fcdcea992321e5bfa88
	}

	@Override
	public void init() {
<<<<<<< HEAD
		// TODO Auto-generated method stub

=======
		map = new TileMap(30);
		map.loadTiles("src/resources/tilesets/grasstileset.gif");
		map.loadMap("src/resources/maps/level1-1.map");
>>>>>>> 48b927f906fec7368a0e3fcdcea992321e5bfa88
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawString("In PlayState", 277, 180);
	}

	@Override
	public void keyPressed(int k) {
<<<<<<< HEAD
		// TODO Auto-generated method stub

=======
		//movement 
		if(k == KeyEvent.VK_UP){
			
		}else if(k == KeyEvent.VK_RIGHT){
			
		}else if(k == KeyEvent.VK_DOWN){
			
		}else if(k == KeyEvent.VK_LEFT){
			
		}
>>>>>>> 48b927f906fec7368a0e3fcdcea992321e5bfa88
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
