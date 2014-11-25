package message;

public class PlayerMessage extends Message{
	
	private int currentDirection;
	private int currentAction;
	private int currentFrame;
	private int x;
	private int y;

	public PlayerMessage(int currentDirection, int currentAction, int currentFrame, int x, int y) {
		super("PLAYER");
		this.currentDirection = currentDirection;
		this.currentAction = currentAction;
		this.currentFrame = currentFrame;
		this.x = x;
		this.y = y;
	}
	
	public int getCurrentDirection(){
		return currentDirection;
	}
	
	public int getCurrentAction(){
		return currentAction;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
