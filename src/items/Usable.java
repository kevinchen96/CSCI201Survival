package items;

import java.awt.image.BufferedImage;

import entity.player.Player;


public class Usable extends Item{
	
	private int up;
	private String useType;
	
	public Usable(Sprite sprite, BufferedImage img, int up, String useType){
		super(sprite, img);
		this.up = up;
	}
	
	public void use(Player p){
		if(useType.equals("health")){
			
		}
		else if(useType.equals("hunger")){
			
		}
		else if(useType.equals("thirst")){
			
		}
	}
	
	public String getItemType() {
		return "usable";
	}
	
}


