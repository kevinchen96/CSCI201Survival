package Map;

import game.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

public class TileMap {
	private double x ,y; //position
	private int ymax, ymin, xmax, xmin; //bounds
	private int[][] map; //map
	private int tileSize, numRows, numCols, numRowsToDraw, numColsToDraw, width, height; //map stuff
	private int colOffset, rowOffset;
	private BufferedImage tileSet; //tileSet
	private int numTilesAcross;
	private int actualTileSize;
	Tile[][] tiles;
	
	public TileMap(int tileSize){
		this.actualTileSize = tileSize;
		this.tileSize = tileSize * GamePanel.SCALE;
		numRowsToDraw = GamePanel.gameHeight() / tileSize + 2;
		numColsToDraw = GamePanel.gameWidth() / tileSize + 2;
		
	}
	
	public void loadTiles(String s){
		try{
			tileSet = ImageIO.read(new File(s));
			numTilesAcross = tileSet.getWidth() / actualTileSize;
			tiles =  new Tile[2][numTilesAcross];
			BufferedImage subImage;
			for(int col = 0; col < numTilesAcross; col++){
				subImage = tileSet.getSubimage(col*actualTileSize, 0, actualTileSize, actualTileSize);
				tiles[0][col] = new Tile(subImage, Tile.NORMAL);
				subImage = tileSet.getSubimage(col*actualTileSize, actualTileSize, actualTileSize, actualTileSize);
				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);

			}
		}catch(Exception e){
			
		}
	}
	
	public void loadMap(String s){
		try {
			BufferedReader mapReader = new BufferedReader(new FileReader(new File(s)));			
			
			numRows = Integer.parseInt(mapReader.readLine());
			numCols = Integer.parseInt(mapReader.readLine());
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.gameWidth() - width;
			System.out.println("this is xmin: " + xmin);
			xmax = 0;
			ymin = GamePanel.gameHeight() - height;
			ymax = 0;
		
			map = new int[numRows][numCols];
			for(int row = 0; row < numRows; row++){	
				String line = mapReader.readLine();
				String[] parts = line.split(" ");
				for(int col = 0; col < numCols; col++){
					map[row][col] = Integer.parseInt(parts[col]);
				}
			}
			System.out.println("should have loaded");
		} catch (Exception e) {
			System.out.println("<In TileMap loadMap()> Bad/Missing map file:  " + e.getMessage());
		}
	}
	
	public void render(Graphics2D g){
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++){
			if(row >= numRows) break;		
			for(int col = colOffset; col < colOffset + numColsToDraw; col++){
				if(col >= numCols) break;
				if(map[row][col] == 0) continue; //0 tile = null tile, doesn't get drawn
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(), (int)x+col*tileSize, (int)y+row*tileSize, null);
			}
		}
	}
	
	public void setPosition(int x, int y){
		this.x += (x - this.x);
		this.y += (y - this.y);
		fixBounds();
		colOffset = (int) -this.x / tileSize;  //which row / col to start drawing
		rowOffset = (int) -this.y / tileSize;
	
	}
	
	private void fixBounds(){
		if(x < xmin) x = xmin;
		if(x > xmax) x = xmax;
		if(y < ymin) y = ymin;
		if(y > ymax) y = ymax;
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
		int rc = map[row][col];
		int r = rc/ numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
}
