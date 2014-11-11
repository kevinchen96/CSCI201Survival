package items;

import java.awt.image.BufferedImage;

public abstract class Item {
	
	//change so that player does server messages
	//add location
	//tools only for functionality
	//separate weapons from tools
	
	private Sprite sprite;
	private BufferedImage img;
	
	public Item(Sprite sprite, BufferedImage img){
		this.sprite = sprite;
		this.img = img;
	}
	
	public static void get(String itemName){
		//parse xml
		
		String type = "";
		//find item type (Armor, tool, usable)
		
		//once item type is found separate into else ifs and get specifics
		if(type.equals("armor")){
			//Armor a = new Armor(); 
		}
		else if(type.equals("tool")){
			//Tool t = new Tool();
		}
		else if(type.equals("usable")){
			//Usable u = new Usable();
		}
		
	}
	
	public abstract String getItemType();
	
}
