package Map;

import game.GamePanel;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TileMap {
	private double x ,y; //position
	private int ymax, ymin, xmax, xmin; //bounds
	private int[][] map; //map
	private int tileSize, numRowsToDraw, numColsToDraw, width, height; //map stuff
	private BufferedImage tileSet; //tileSet
	private int numTilesAcross;
	Tile[][] tiles;
	
	TileMap(int tileSize){
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		
	}
	
	public void loadTiles(String s){
		try{
			tileSet = ImageIO.read(new File(s));
			numTilesAcross = tileSet.getWidth() / tileSize;
			tiles =  new Tile[2][numTilesAcross];
			BufferedImage subImage;
			for(int col = 0; col < numTilesAcross; col++){
				subImage = tileSet.getSubimage(col*tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImage, Tile.NORMAL);
				subImage = tileSet.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);

			}
		}catch(Exception e){
			
		}
	}
	
	public void loadMap(String s){
		
	}
	
	public void render(){
		
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		fixBounds();
	}
	
	private void fixBounds(){
		
	}
	
	//Getters
	public int getTileSize(){
		return tileSize;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getTypeOfTileAt(int row, int col){
		return tiles[row][col].getType();
	}
	
}
