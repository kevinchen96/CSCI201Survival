package message;


public class MonsterMessage extends Message {

	private int damage;
	private int index;
	public MonsterMessage(int strength, int i) {
		super("MONSTER");
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
