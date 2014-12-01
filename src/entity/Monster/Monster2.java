package entity.Monster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Animation;
import Map.TileMap;

public class Monster2 extends entity.MapObject{
	private String name;
	private int speed;
	private int health;
	private int damage;
	private int behavior;
	private int xPosition;
	private int yPosition;
	private boolean idle, walking, slash, dead;
	private ArrayList<ArrayList<BufferedImage[]>> sprites;
	private final int[] numFrames = {1, 9, 6, 6};
	
	private Animation animation;
	public int IDLE = 0;
	public int WALKING = 1;
	public int SLASH = 2;
	public int DIE = 3;
	
	public int UP = 0; 
	public int LEFT = 1;
	public int DOWN = 2;
	public int RIGHT = 3;
	public Monster2(TileMap tm) {
		super(tm);
		/*
		this.name = name;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.behavior = behavior;
		this.xPosition = x;
		this.yPosition = y;
		dead = false;*/
		health = 300;
		BufferedImage spritesheet;
		try {
			//BufferedImage spritesheet = ImageIO.read(this.getClass().getResourceAsStream("char1.png"));
			spritesheet = ImageIO.read(new File("src/orc.png"));
			sprites = new ArrayList<ArrayList<BufferedImage[]>>();
			for (int i = 0; i < 4 ; i++) {
				ArrayList<BufferedImage[]> hi = new ArrayList<BufferedImage[]>();
				sprites.add(hi);
				for(int j = 0; j < 4; j++){
					BufferedImage[] bi = new BufferedImage[numFrames[j]];
					sprites.get(i).add(bi);
				}
			}
			for(int i = 0; i < 4; i++){
				for (int j = 0; j < numFrames[0]; j++) {
					(sprites.get(i).get(0))[j] = spritesheet.getSubimage(j*64, (i+8)*64, 64, 64);
				}
				for(int j = 0; j < numFrames[1]; j++){
					(sprites.get(i).get(1))[j] = spritesheet.getSubimage(j*64, (i+8)*64, 64, 64);
				}
				for(int j = 0; j < numFrames[2]; j++){
					(sprites.get(i).get(2))[j] = spritesheet.getSubimage(j*64, (i+12)*64, 64, 64);
				}
				for(int j = 0; j < numFrames[3]; j++){
					(sprites.get(i).get(3))[j] = spritesheet.getSubimage(j*64, 20*64, 64, 64);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites.get(2).get(IDLE));	
		animation.setDelay(50);
	}
	public int attack() {
		return damage;
	}
	
	public void attacked(int damageTaken) {
		if (dead) {
			return;
		}
		this.health = this.health - damageTaken;
		if (health < 0 || health == 0) {
			dead = true;
		}
		System.out.println("Monster's health: " + health);
	}
	
	public void move() {
		
		if (this.behavior == 1) {
			
		}
		else if (this.behavior == 2) {
			
		}
		else if (this.behavior == 3) {
			
		}
	}
	
	public void follow() {
		
	}
	
	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}

	public int getBehavior() {
		return behavior;
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public boolean isDead() {
		return dead;
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		g.drawImage(animation.getImage(), (int)(x + xmap - 32), (int)(y + ymap - 32), null);
	}

	
}
