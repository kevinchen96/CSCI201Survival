package items;

import java.awt.image.BufferedImage;

public class Armor extends Equippable{
	
	private int dp; //defence points
	
	public Armor(Sprite sprite, BufferedImage img, int slot, int dp){
		super(sprite, img, slot);
		this.dp = dp;
	}
	
	public int getDefencePoints(){
		return dp;
	}

	public String getItemType() {
		return "armor";
	}
}
