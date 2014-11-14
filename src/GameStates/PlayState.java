package GameStates;

import java.awt.Graphics2D;

import Map.TileMap;

public class PlayState extends States{
	
	TileMap map = new TileMap(30);
	
	public PlayState(){
		
	}

	@Override
	public void init() {
		map.loadMap("src/resources/maps/level1-1.map");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		map.render(g);
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
