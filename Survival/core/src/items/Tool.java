package items;

import java.awt.image.BufferedImage;

public abstract class Tool extends Equippable{
	
	protected int tp;
	protected int ap;
	
	public Tool(Sprite sprite, BufferedImage img, int slot, int tp){
		super(sprite, img, slot);
		this.tp = tp;
	}
	
	//player's going to deal with sending messages to server
	
	abstract String useTool();

}
