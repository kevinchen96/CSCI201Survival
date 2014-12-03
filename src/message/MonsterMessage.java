package message;


public class MonsterMessage extends Message {

	private int damage;
	private int index;
	private String killer;
	public MonsterMessage(int strength, int i, String killer) {
		super("MONSTER");
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
