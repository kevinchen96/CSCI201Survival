package items;

import java.awt.image.BufferedImage;

public abstract class Usable extends Item{
	
	private int up;
	
	public Usable(Sprite sprite, BufferedImage img, int up){
		super(sprite, img);
		this.up = up;
	}
	
	public abstract void use();
	
}


