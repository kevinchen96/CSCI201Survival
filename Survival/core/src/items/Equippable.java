package items;

import java.awt.image.BufferedImage;

public abstract class Equippable extends Item{
	
	private int slot;
	
	public Equippable(Sprite sprite, BufferedImage img, int slot){
		super(sprite, img);
		this.slot = slot;
	}
	
	public int getSlot(){
		return slot;
	}
	
}
