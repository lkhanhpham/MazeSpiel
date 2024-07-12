package Maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import team07.main.LeftPanel;

/**ShortestPath findet den/die kürzesten Weg/e in einem Maze auf Basis des Lee Algorithmus (Breitensuche) 
 *@author Team 7
 *
 */
public class ShortestPath {
	private static final int[] row = { -1, 0, 0, 1 };
	private static final int[] col = { 0, -1, 1, 0 };
	private int dis;
	private int M, N;
	private int[][] min_dist;
	private ArrayList<Integer> koordinatenX;
	private ArrayList<Integer> koordinatenY;
	private ArrayList<Integer> distance;
	private ArrayList<ArrayList<Integer>> koor, koor2, koor3, koor4;

	/** Konstruktor, der für alle Teile des Mazes ein Liste mit Koordinaten für den kurzesten Weg berechnet
	 * @param maze für das der kürzeste Weg berechnet werden soll
	 * @param s Startzelle 
	 * @param e Zielzelle
	 */
	public ShortestPath(Maze maze, Cell s, Cell e) {
		M = maze.getCells().length;
		N = maze.getCells()[0].length;
		min_dist = new int[M][N];
		koor = null;
		koor = findShortestPath(maze, s, e, M, N, koordinatenX, koordinatenY, distance, koor, min_dist);
		koor2 = findShortestPath(maze, s, e, M, N, koordinatenX, koordinatenY, distance, koor, min_dist);
		koor3 = findShortestPath(maze, s, e, M, N, koordinatenX, koordinatenY, distance, koor, min_dist);
		koor4 = findShortestPath(maze, s, e, M, N, koordinatenX, koordinatenY, distance, koor, min_dist);

		for (int j = 0; j < koor.get(1).size(); j++) {
			koor2.get(0).set(j, (koor2.get(0).get(j)) + (-(koor2.get(0).get(j) * 2) + 1));
			koor3.get(1).set(j, (koor3.get(1).get(j)) + (-(koor3.get(1).get(j) * 2) + 1));
			koor4.get(0).set(j, (koor4.get(0).get(j)) + (-(koor4.get(0).get(j) * 2) + 1));
			koor4.get(1).set(j, (koor4.get(1).get(j)) + (-(koor4.get(1).get(j) * 2) + 1));
		}

	}

	/**findShortestPath berechnet ein zweidimensionales Array mit allen Distancen vom Start zum Ziel 
	 * @param maze aus dem die Zellen entnommen werden
	 * @param s Startzelle
	 * @param e Zielzelle
	 * @param M Größe des Maze
	 * @param N Größe des Maze
	 * @param koordinatenX bekommt die X Koordinaten aller Zellen die auf dem kürzesten Weg liegen
	 * @param koordinatenY bekommt die Y Koordinaten aller Zellen die auf dem kürzesten Weg liegen
	 * @param distance bekommt die Distanzen zum Startpunkt aller Zellen die auf dem kürzesten Weg liegen
	 * @param koor Liste, die koordinatenX, koordinatenY und distance vereint
	 * @param min_dist Array das die brecheneten Distanzen aller erreichten Zellen zugewiesen
	 * @return Liste mit den Listen koordinatenX, koordinatenY und distance 
	 */
	private static ArrayList<ArrayList<Integer>> findShortestPath(Maze maze, Cell s, Cell e, int M, int N,
			ArrayList<Integer> koordinatenX, ArrayList<Integer> koordinatenY, ArrayList<Integer> distance,
			ArrayList<ArrayList<Integer>> koor, int[][] min_dist) {
		int i = s.getX();
		int j = s.getY();
		int x = e.getX();
		int y = e.getY();

		if (maze == null || maze.getCells().length == 0) {
			return null;
		}

		boolean visited[][] = new boolean[M][N];

		Queue<Cell> q = new ArrayDeque<>();

		visited[i][j] = true;
		q.add(maze.getCells()[i][j]);
		maze.getCells()[i][j].setDist(0);

		for (int k = 0; k < min_dist.length; k++) {
			for (int k2 = 0; k2 < min_dist.length; k2++) {
				min_dist[k][k2] = Integer.MAX_VALUE;
			}
		}

		min_dist[i][j] = 0;

		while (!q.isEmpty()) {

			Cell cell = q.poll();

			i = cell.getX();
			j = cell.getY();

			int dist = cell.getDist();

			for (int k = 0; k < 4; k++) {
				if (isValid(maze, visited, i + row[k], j + col[k]) && isGate(maze, i, j, i + row[k], j + col[k])) {

					visited[i + row[k]][j + col[k]] = true;
					q.add(maze.getCells()[i + row[k]][j + col[k]]);
					maze.getCells()[i + row[k]][j + col[k]].setDist(dist + 1);
					min_dist[j + col[k]][i + row[k]] = dist + 1;
				}
			}

		}

		koor = new ArrayList<ArrayList<Integer>>();
		koordinatenX = new ArrayList<Integer>();
		koordinatenY = new ArrayList<Integer>();
		distance = new ArrayList<Integer>();
		koordinatenX.add(-8);
		koordinatenY.add(-8);
		distance.add(min_dist[0][0]);
		koor.add(koordinatenX);
		koor.add(koordinatenY);
		koor.add(distance);

		if (min_dist[0][0] != Integer.MAX_VALUE) {
			return koor = calCoordinates(min_dist, koor, maze);
		}
		return null;
	}

	/**calCoordinates sorgt für die Zuweisung von allen Koordinaten des kürzesten Weges
	 * @param min_dist Array das die brecheneten Distanzen aller erreichten Zellen zugewiesen
	 * @param koor Liste, die koordinatenX, koordinatenY und distance vereint
	 * @param maze aus dem die Zellen entnommen werden
	 * @return Liste mit den Listen koordinatenX, koordinatenY und distance 
	 */
	private static ArrayList<ArrayList<Integer>> calCoordinates(int[][] min_dist, ArrayList<ArrayList<Integer>> koor,
			Maze maze) {

		int tempX = 0;
		int tempY = 0;
		int calX = 0;
		int calY = 0;
		int secondX = 0;
		int secondY = 0;
		boolean secondWay = false;

		// for schleife macht nur so viel iterationen wie die länge eines weges ist
		for (int i = 0; i < min_dist[0][0] * 2; i++) {
			int waycount = 0;
			for (int k = 0; k < 4; k++) {

				if (tempX + row[k] >= 0 && tempY + col[k] >= 0 && tempX + row[k] < min_dist.length
						&& tempY + col[k] < min_dist[0].length) {

					// look for second way
					if (min_dist[tempY][tempX] == maze.getCells()[tempX + row[k]][tempY + col[k]].getDist() + 1
							&& isGate(maze, tempX, tempY, tempX + row[k], tempY + col[k]) && waycount == 0) {
						koor.get(0).add(
								maze.getCells()[tempX + row[k]][tempY + col[k]].getX() - (maze.getCells().length - 1));
						koor.get(1).add(maze.getCells()[tempX + row[k]][tempY + col[k]].getY()
								- (maze.getCells()[0].length - 1));
						koor.get(2).add(maze.getCells()[tempX + row[k]][tempY + col[k]].getDist());
						calX = tempX + row[k];
						calY = tempY + col[k];
						waycount++;
					} else if (min_dist[tempY][tempX] == maze.getCells()[tempX + row[k]][tempY + col[k]].getDist() + 1
							&& isGate(maze, tempX, tempY, tempX + row[k], tempY + col[k]) && waycount == 1) {
						koor.get(0).add(
								maze.getCells()[tempX + row[k]][tempY + col[k]].getX() - (maze.getCells().length - 1));
						koor.get(1).add(maze.getCells()[tempX + row[k]][tempY + col[k]].getY()
								- (maze.getCells()[0].length - 1));
						koor.get(2).add(maze.getCells()[tempX + row[k]][tempY + col[k]].getDist());
						secondX = tempX + row[k];
						secondY = tempY + col[k];
						secondWay = true;

					}
					// find cells of the second path
					if (secondX + row[k] >= 0 && secondY + col[k] >= 0 && secondX + row[k] < min_dist.length
							&& secondY + col[k] < min_dist[0].length) {
						if (min_dist[secondY][secondX] == maze.getCells()[secondX + row[k]][secondY + col[k]].getDist()
								+ 1 && isGate(maze, secondX, secondY, secondX + row[k], secondY + col[k])
								&& secondWay) {
							koor.get(0).add(maze.getCells()[secondX + row[k]][secondY + col[k]].getX()
									- (maze.getCells().length - 1));
							koor.get(1).add(maze.getCells()[secondX + row[k]][secondY + col[k]].getY()
									- (maze.getCells()[0].length - 1));
							koor.get(2).add(maze.getCells()[secondX + row[k]][secondY + col[k]].getDist());
							secondX = secondX + row[k];
							secondY = secondY + col[k];

						}

					}

				}
				
			}
			tempX = calX;
			tempY = calY;
		}
		return koor;

	}

	/**isGate schaut, ob zwischen zwei Zellen ein Durchgang besteht.
	 * @param maze aus dem die Zellen entnommen werden
	 * @param row x Wert der ersten Zell
	 * @param col y Wert der ersten Zell
	 * @param row2 x Wert der zweiten Zell
	 * @param col2 x Wert der zweiten Zell
	 * @return den Boolean der true ist, wenn ein durchgang besteht.
	 */
	private static boolean isGate(Maze maze, int row, int col, int row2, int col2) {
		int dir = 0;
		int in_dir = 0;
		if (row - row2 == -1) {
			dir = 1;
			in_dir = 3;
		}
		if (row - row2 == 1) {
			dir = 3;
			in_dir = 1;
		}
		if (col - col2 == 1) {
			dir = 0;
			in_dir = 2;
		}
		if (col - col2 == -1) {
			dir = 2;
			in_dir = 0;
		}

		if (row2 < maze.getCells().length && row2 >= 0 && col2 < maze.getCells()[0].length && col2 >= 0) {
			return maze.getCells()[row][col].getWalls()[dir] == 0
					&& maze.getCells()[row2][col2].getWalls()[in_dir] == 0;
		}

		return false;
	}

	/**Überprüft, ob eine Zelle überprüft werden kann
	 * @param maze aus dem die Zellen entnommen werden
	 * @param visited zeigt schon besichtigte Zellen
	 * @param row x Wert der ersten Zell
	 * @param col y Wert der ersten Zell
	 * @return Boolean der true ist wenn die Zelle angeschaut werden darf
	 */
	private static boolean isValid(Maze maze, boolean[][] visited, int row, int col) {
		return (row >= 0) && (row < maze.getCells().length) && (col >= 0) && (col < maze.getCells()[0].length)
				&& !visited[row][col];
	}
	
	// Getter und Setter
	public int getDis() {
		return dis;
	}

	public void setDis(int dis) {
		this.dis = dis;
	}

	public static int[] getRow() {
		return row;
	}

	public static int[] getCol() {
		return col;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int[][] getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(int[][] min_dist) {
		this.min_dist = min_dist;
	}

	public ArrayList<Integer> getKoordinatenX() {
		return koordinatenX;
	}

	public void setKoordinatenX(ArrayList<Integer> koordinatenX) {
		this.koordinatenX = koordinatenX;
	}

	public ArrayList<Integer> getKoordinatenY() {
		return koordinatenY;
	}

	public void setKoordinatenY(ArrayList<Integer> koordinatenY) {
		this.koordinatenY = koordinatenY;
	}

	public ArrayList<ArrayList<Integer>> getKoor() {
		return koor;
	}

	public void setKoor(ArrayList<ArrayList<Integer>> koor) {
		this.koor = koor;
	}

	public ArrayList<ArrayList<Integer>> getKoor2() {
		return koor2;
	}

	public void setKoor2(ArrayList<ArrayList<Integer>> koor2) {
		this.koor2 = koor2;
	}

	public ArrayList<ArrayList<Integer>> getKoor3() {
		return koor3;
	}

	public void setKoor3(ArrayList<ArrayList<Integer>> koor3) {
		this.koor3 = koor3;
	}

	public ArrayList<ArrayList<Integer>> getKoor4() {
		return koor4;
	}

	public void setKoor4(ArrayList<ArrayList<Integer>> koor4) {
		this.koor4 = koor4;
	}

}
