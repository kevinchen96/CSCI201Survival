package message;


public class Monster2Message extends Message {

	private int damage;
	private int index;
	private String killer;
	public Monster2Message(int strength, int i, String killer) {
		super("MONSTER2");
		damage = strength;
		index = i;
		this.killer = killer;
		// TODO Auto-generated constructor stub
	}
	public int getDamage(){
		return damage;
	}
	public int getWhich() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public String getKiller(){
		return killer;
	}
}