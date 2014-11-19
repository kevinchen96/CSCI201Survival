package Entity;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] frames;
	private int currentF;
	private long startTime;
	private long delay;
	private boolean playedOnce;
	
	public Animation(){
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentF = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) {
		delay = d;
	}
	public void setFrame(int i) {
		currentF = i;
	}
	public void update(){
		long now = (System.nanoTime()-startTime)/1000000;
		if(now > delay){
			currentF++;
			startTime = System.nanoTime();
		}
		if(currentF == frames.length){
			currentF = 0;
			playedOnce = true;
		}
	}
	public int getFrame(){
		return currentF;
	}
	public BufferedImage getImage(){ 
		return frames[currentF];
	}
	public boolean playedOnce(){
		return playedOnce;
	}
}
