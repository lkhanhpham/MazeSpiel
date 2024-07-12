package Maze;

public class Cell {
	
	// walls 0 oben, walls 1 rechts, walls 2 unten, walls 3 links
	private byte[] walls = {1,1,1,1};
	
	private int x;
	private int y;
	private int dist;
	



	//Überprüft, ob alle Wände der Cell zu sind.
	public boolean checkWalls() {
		if (walls[0] == 1 && walls[1] == 1 && walls[2] == 1 && walls[3] == 1) {
			return true;
		} else {
			return false;
		}
	}

	public byte[] getWalls() {
		return walls;
	}

	public void setWalls(byte[] walls) {
		this.walls = walls;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	//Getter und Setter
	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

}