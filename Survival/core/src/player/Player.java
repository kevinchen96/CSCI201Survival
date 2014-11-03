import java.util.ArrayList;


public class Player {
	private int health, thirst, hunger, strength, defense;  //bars
	private int healthRate, thirstRate, hungerRate; //rates
	private int expHealth, expThirst, expHunger; //amount of experience gained for each bar
	private int maxBag, maxEq; //max number of items bag and equipment can store
	private int expRatio; //ratio used to determine actual change in either rates or bars
	private int count; //timer?
	private int level; //level of character
	private ArrayList<String> Bag; //bag used to store items (change to type item when made)
	private ArrayList<String> Equipment; //Equipment such as weapons, armors, boots
	private ArrayList<String> Inventory;
	private int moveSpeed; //movement speed
	private int positionX; //position in the x-coordinates
	private int positionY; //position in the y-coordinates
	private boolean maxLevel; //true if level is at its max
	
	
	public Player(){
		health = 768;
		thirst = 700;
		hunger = 600;
		strength = 50;
		Bag = new ArrayList<String>();
		Equipment = new ArrayList<String>();
		//inventory?
		level = 0;
		maxLevel = false;
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
		}
		updateHealthXP(-1*(expHealth));
		updateThirstXP(-1*(expThirst));
		updateHungerXP(-1*(expHunger));
		
	}
	
	//updating position
	public void updatePosition(int dx, int dy){
		positionX+=dx;
		positionY+=dy;
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
	public int getLevel(){
		return level;
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
	
	//get position
	public int getPositionX(){
		return positionX;
	}
	public int getPositionY(){
		return positionY;
	}
	
}
