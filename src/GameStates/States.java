package GameStates;

import java.awt.Graphics2D;

public abstract class States {
	public GameStates manager;
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	abstract public void interpretMessage(String message);
	
}
