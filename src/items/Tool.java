package items;

import java.awt.image.BufferedImage;

public class Tool extends Equippable{
	
	private int tp;
	private String toolType;
	
	public Tool(Sprite sprite, BufferedImage img, int slot, int tp, String toolType){
		super(sprite, img, slot);
		this.tp = tp;
		this.toolType = toolType;
	}
	
	//player's going to deal with sending messages to server
	
	public String getItemType() {
		return "tool";
	}
	
	public String getToolType(){
		return toolType;
	}

}
