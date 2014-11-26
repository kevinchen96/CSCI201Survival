package items;

import java.awt.Graphics2D;
import java.util.List;

public class TextItem extends Thread {

	private String text;
	private int startX;
	private int startY;
	private List<TextItem> items;
	private boolean finished = false;
	private int count = 0;

	public TextItem(String text, int startX, int startY, List<TextItem> items) {
		this.text = text;
		this.items = items;
		this.startX = startX - (2 * text.length()) - (text.length()/2);
		this.startY = startY - 35;
		start();
	}

	public void run() {
		long start = System.nanoTime();
		long now = start;
		while(true){
			if(System.nanoTime() - now > 50000000){
				count++;
				now = System.nanoTime();
			}
			
			if(System.nanoTime() - start > 1300000000) break;
		}
		items.remove(this);
	}
	
	public void draw(Graphics2D g){
		g.drawString(text, startX, startY - (count*5));
	}
}
