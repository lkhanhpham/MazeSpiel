package player;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Maze.MazeDisplay;
import team07.main.LeftPanel;

public class Player{
    private static Image player1;
    private static Image player2;
    // Pixelkoordinaten des Spielers
    private int x;
    private int y;
    // Zellkoordinaten des Spielers
    private int resizex;
    private int resizey;
	private MazeDisplay maze;
    
/**
 * the instance of this class is a player with predefined appearance 
 */
    
    public Player(){
    	maze = LeftPanel.getDp();
//        x = maze.getPointX();
//        y = maze.getPointY();
        URL path1 = this.getClass().getResource("/resources/ghost yellow.png");
        URL path2 = this.getClass().getResource("/resources/ghost red.png");
        try {
            player1 = ImageIO.read(path1); 
            player1 = player1.getScaledInstance(maze.getCellSize()/3*2, maze.getCellSize()/3*2, java.awt.Image.SCALE_REPLICATE);

            player2 = ImageIO.read(path2); 
            player2 = player2.getScaledInstance(maze.getCellSize()/3*2, maze.getCellSize()/3*2, java.awt.Image.SCALE_REPLICATE);
            } catch (IOException e) {
                }
    }
    
    public static Image getPlayer(String color){
        if (color == "red"){
            return player2;
        }
        return player1;
    }
   
	/** gets the current x-coordinate of Player (pixel-wise)
	 * 
	 * @return x
	 */
    public int getX(){
        return x;
    }
    /** gets the current y-coordinate of Player (pixel-wise)
     * 
     * @return y
     */
    public int getY(){
        return y;
    }
    /**
	 * @param x the x-coordinate to set
	 */
    public void setX(int x){
        this.x = x;
    }
    /**
  	 * @param y the y-coordinate to set
  	 */
    public void setY(int y){
        this.y = y;
    }
    public int getResizex() {
		return resizex;
	}

	public void setResizex(int resizex) {
		this.resizex = resizex;
	}

	public int getResizey() {
		return resizey;
	}

	public void setResizey(int resizey) {
		this.resizey = resizey;
	}
}
