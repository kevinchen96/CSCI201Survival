package entity.player;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Map.TileMap;
import entity.MapObject;
import entity.Animation;

public class Player extends MapObject{
	public int health;  //bars
	private int thirst;
	private int hunger;
	private int strength;
	private int defense;
	public int currHealth;
	private int currThirst;
	private int currHunger;
	private int healthRate, thirstRate, hungerRate; //rates
	private int expHealth, expThirst, expHunger; //amount of experience gained for each bar
	private int maxBag, maxEq; //max number of items bag and equipment can store
	private int expRatio; //ratio used to determine actual change in either rates or bars
	private int count; //timer?
	private int level; //level of character
	private ArrayList<String> Bag; //bag used to store items (change to type item when made)
	private ArrayList<String> Equipment; //Equipment such as weapons, armors, boots
	private ArrayList<String> Inventory;
	private double moveSpeed; //movement speed
	private boolean maxLevel; //true if level is at its max
	private boolean idle, walking, slash, dead;
	private ArrayList<ArrayList<BufferedImage[]>> sprites;
	private final int[] numFrames = {1, 9, 6, 6};
	private int range = 10;
	public int IDLE = 0;
	public int WALKING = 1;
	public int SLASH = 2;
	public int DIE = 3;
	private Animation animation;
	
	public int UP = 0; 
	public int LEFT = 1;
	public int DOWN = 2;
	public int RIGHT = 3;
	private Animation animation2;
	
	public Player(TileMap tm){
		super(tm);
		health = 768;
		currHealth = health;
		thirst = 700;
		currThirst = thirst;
		hunger = 600;
		currHunger = hunger;
		strength = 50;
		moveSpeed = 1;
		Bag = new ArrayList<String>();
		Equipment = new ArrayList<String>();
		//inventory?
		level = 0;
		maxLevel = false;
		dead = false;
		BufferedImage spritesheet;
		animation2 = new Animation();
		try {
			//BufferedImage spritesheet = ImageIO.read(this.getClass().getResourceAsStream("char1.png"));
			spritesheet = ImageIO.read(new File("src/char1.png"));
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
					(sprites.get(i).get(2))[j] = spritesheet.getSubimage(((j*3)+1)*64, ((i*3)+22)*64, 64, 64);
				}
				for(int j = 0; j < numFrames[3]; j++){
					(sprites.get(i).get(3))[j] = spritesheet.getSubimage(j*64, 20*64, 64, 64);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		currentAction = IDLE;
		currentDirection = 3;
		animation.setFrames(sprites.get(currentDirection).get(IDLE));	
		animation.setDelay(50);
	}
	
	public int getRange(){
		return range;
	}
	public BufferedImage getAnimation(int currentDirection, int currentAction){
		animation2.setFrames(sprites.get(currentDirection).get(IDLE));
		return animation2.getImage();
	}
	
	public void getNextPosition(){
		if(walking){
			if(currentDirection == LEFT){
				dx = -1*moveSpeed;
				dy = 0;
			}
			else if(currentDirection == DOWN){
				dy = moveSpeed;
				dx = 0;
			}
			else if(currentDirection == RIGHT){
				dx = moveSpeed;
				dy = 0;
			}
			else if(currentDirection == UP){
				dy = -1*moveSpeed;
				dx = 0;
			}
		}
		else{
			dx = 0;
			dy = 0;
		}
	}
	
	public void update(){
		//if (!notOnScreen()) 
		if (((!(xtemp==16)) || currentDirection==RIGHT || currentDirection==DOWN || currentDirection==UP) &&
			((!(ytemp==20)) || currentDirection==LEFT || currentDirection==DOWN || currentDirection==RIGHT) &&
			((!(xtemp==3823)) || currentDirection==LEFT || currentDirection==DOWN || currentDirection==UP) &&
			((!(ytemp==3785)) || currentDirection==LEFT || currentDirection==RIGHT || currentDirection==UP)){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(idle){
			if(currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(currentDirection).get(IDLE));
				animation.setDelay(50);
			}
		}
		else if(walking){
			if(currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(currentDirection).get(WALKING));
				animation.setDelay(50);
			}
		}
		else if(slash){
			if(currentAction != SLASH){
				currentAction = SLASH;
				animation.setFrames(sprites.get(currentDirection).get(SLASH));
				animation.setDelay(50);
			}
		}
		else if(dead){
			if(currentAction != DIE){
				currentAction = DIE;
				animation.setFrames(sprites.get(currentDirection).get(DIE));
				animation.setDelay(50);
			}
		}
		
		animation.update();
		}
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		g.drawImage(animation.getImage(), (int)(x + xmap - 32), (int)(y + ymap - 32), null);
	}
	public void updateHealthC(int x){
		currHealth+=x;
	}
	public void updateThirstC(int x){
		currThirst+=x;
	}
	public void updateHungerC(int x){
		currHunger+=x;
	}

	
	// updating bars
	public void updateHealth(int x){
		health+=x;
	}
	public void updateThirst(int x){
		thirst+=x;
	}
	public void updateHunger(int x){
		hunger+=x;
	}
	public void updateStrength(int x){
		strength+=x;
	}
	public void updateDefense(int x){
		defense+=x;
	}
	
	//updating experience
	public void updateHealthXP(int x){
		expHealth+=x;
	}
	public void updateThirstXP(int x){
		expThirst+=x;
	}
	public void updateHungerXP(int x){
		expHunger=x;
	}
	
	//updating level
	public void updateLevel(){
		level++;
		if(level == 10){
			maxLevel = true;
		}
		else{
			updateHealth(expHealth*expRatio);
			updateThirst(expThirst*expRatio);
			updateHunger(expHunger*expRatio);
			updateHealthC(expHealth*expRatio);
			updateThirstC(expThirst*expRatio);
			updateHungerC(expHunger*expRatio);
			
		}
		updateHealthXP(-1*(expHealth));
		updateThirstXP(-1*(expThirst));
		updateHungerXP(-1*(expHunger));
		
	}
	
	
	//updating items in Bag
	public void updateAddBag(String item){
		if(Bag.size()!=maxBag){
			Bag.add(item);
		}
	}
	public void updateUseBag(String item){
		for(int i = 0; i < Bag.size(); i++){
			if(Bag.get(i).equals(item)){
				Bag.remove(i);
			}
		}
		//if(item.equals("    ") update whatever bar needs to be updated...need list of resources
	}
	public void updateStoreBag(String item){
		for(int i = 0; i < Bag.size(); i++){
			if(Bag.get(i).equals(item)){
				Bag.remove(i);
			}
		}
		updateAddInv(item);
	}
	
	public void updateAddInv(String item) {
		// TODO Auto-generated method stub
		Inventory.add(item);
	}

	//updating items in Equipment
	public void updateAddEq(String item){
		if(Equipment.size()!=maxEq){
			Equipment.add(item);
		}
		//if(item.equals("    ") update whatever bar needs to be updated...need list of equipment
		
	}
	
	//get levels
	public int getHealth(){
		return health;
	}
	public int getThirst(){
		return thirst;
	}
	public int getHunger(){
		return hunger;
	}
	public int getStrength(){
		return strength;
	}
	public int getDefense(){
		return defense;
	}
	public int getLevel(){
		return level;
	}
	public int getHealthC(){
		return currHealth;
	}
	public int getThirstC(){
		return currThirst;
	}
	public int getHungerC(){
		return currHunger;
	}
	
	//get rates
	public int gethealthRate(){
		return healthRate;
	}
	public int getthirstRate(){
		return thirstRate;
	}
	public int gethungerRate(){
		return hungerRate;
	}
	
	//get experience
	public int gethealthXP(){
		return expHealth;
	}
	public int gethungerXP(){
		return expHunger;
	}
	public int getthirsthXP(){
		return expThirst;
	}
	
	public void setIdle(boolean b){
		idle = b;
	}
	public void setWalking(boolean b){
		walking = b;
	}
	public void setSlash(boolean b){
		slash = b;
	}
	public void setDead(boolean b){
		dead = b;
	}
	public boolean getDead(){
		return dead;
	}
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public int getCurrentAction(){
		return currentAction;
	}
	
}
