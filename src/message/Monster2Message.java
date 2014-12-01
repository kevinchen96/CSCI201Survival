package message;


public class Monster2Message extends Message {

	private int damage;
	private int index;
	public Monster2Message(int strength, int i) {
		super("MONSTER2");
		damage = strength;
		index = i;
		// TODO Auto-generated constructor stub
	}
	public int getDamage(){
		return damage;
	}
	public int getWhich() {
		// TODO Auto-generated method stub
		return index;
	}
}