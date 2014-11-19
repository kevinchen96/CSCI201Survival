package Entity.Monster;

public class Monster {
	private String name;
	private int speed;
	private int health;
	private int damage;
	private int behavior;
	private int xPosition;
	private int yPosition;
	private boolean dead;
	
	public Monster(String name, int speed, int health, int damage, int behavior, int x, int y) {
		this.name = name;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.behavior = behavior;
		this.xPosition = x;
		this.yPosition = y;
		dead = false;
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


	
}
