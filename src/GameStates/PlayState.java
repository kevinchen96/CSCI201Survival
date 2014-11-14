package GameStates;

import java.awt.Graphics2D;

<<<<<<< HEAD
public class PlayState extends States {

	public PlayState(GameStates gameStates) {
		// TODO Auto-generated constructor stub
		manager = gameStates;
=======
import Map.TileMap;

public class PlayState extends States{
	
	TileMap map = new TileMap(30);
	
	public PlayState(){
		
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

	@Override
	public void init() {
<<<<<<< HEAD
		// TODO Auto-generated method stub

=======
		map.loadMap("src/resources/maps/level1-1.map");
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

	@Override
	public void draw(Graphics2D g) {
<<<<<<< HEAD
		// TODO Auto-generated method stub

=======
		map.render(g);
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> a04cad74545a33c8e38fd85c1ccaa6f99bc4400a
	}

}
