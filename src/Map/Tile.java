package Map;

import game.GamePanel;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tile {
	private Image image;
	private int type;
	
	//tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	Tile(BufferedImage image, int type){
		int scalar = GamePanel.SCALE;	
		int w = image.getWidth();
		int h = image.getHeight();
		this.image = image.getScaledInstance(scalar*w, scalar*h, Image.SCALE_SMOOTH);
		
		this.type = type;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getType(){
		return type;
	}
}
