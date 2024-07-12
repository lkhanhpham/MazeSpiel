package Spiel4;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Grid {

	private Rectangle[][] cells;
	private Items[][] grid;
	private ArrayList<Items> ships;
	private ArrayList<Items> earths;
	private int columnCount = 13;
	private int rowCount = 13;
	private String difficulty;
	
	private ArrayList<ArrayList<Integer>> startPositions;

	// Lösung
	private int minDistance;
	private int maxRadius;

	public Grid(String difficulty) {
		this.difficulty = difficulty;
		cells = new Rectangle[rowCount][columnCount];
		grid = new Items[rowCount][columnCount];
		ships = new ArrayList<>();
		earths = new ArrayList<>();
		fillGridShips(grid, difficulty, ships);
		fillGridEarth(grid, difficulty, earths);
		centerSelection(this, ships, earths);
		startPositions = startPositions();

//		for (int i = 0; i < earths.size(); i++) {
//			System.out.println(earths.get(i).getId());
//			
//		}
//		
//		for (int i = 0; i < ships.size(); i++) {
//			System.out.println(ships.get(i).getId());
//			
//		}

	}
	
	public boolean allEarthUsed() {
		int colCount = 0;
		for (int i = 0; i < grid[0].length; i++) {
			if (grid[0][i] == null) {
				colCount++;
			}
			
		}
		if (colCount == 13) {
			return true;
		}
		return false;
	}
	
	public boolean isSolutionValid() {
		if (difficulty == "Training") {
//			System.out.println(checkSolution() <= minDistance);
			if (checkSolution() <= 2*minDistance) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (difficulty == "Mittel") {
			System.out.println(1.5*minDistance);
			if (checkSolution() <= 1.5*minDistance) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (difficulty == "Schwer") {
			System.out.println(1.2*minDistance);
			if (checkSolution() <= 1.2*minDistance) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public int distanceToNextEarth(Items item) {
		int distanceToNextEarth = Integer.MAX_VALUE;
		int dist = Integer.MAX_VALUE;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != null && grid[i][j].getType() == "Earth") {
					int distX = Math.abs(grid[i][j].getRow() - item.getRow());
					int distY = Math.abs(grid[i][j].getCol() - item.getCol());
					dist = distX+distY;
					if (dist < distanceToNextEarth) {
						distanceToNextEarth = dist;
					}
				}
			}
		}
		return distanceToNextEarth;
	}
	
	public Items findNextEarth(Items item) {
		int distanceToNextEarth = Integer.MAX_VALUE;
		int dist = Integer.MAX_VALUE;
		Items nextEarth = null;
		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != null && grid[i][j].getType() == "Earth") {
					int distX = Math.abs(grid[i][j].getRow() - item.getRow());
					int distY = Math.abs(grid[i][j].getCol() - item.getCol());
					dist = distX+distY;
					if (dist < distanceToNextEarth) {
						distanceToNextEarth = dist;
						nextEarth = grid[i][j];
					}
				}
			}
		}
		return nextEarth;
	}
	
	public int checkSolution() {
		ArrayList<Integer> distance = new ArrayList<>();
		if (allEarthUsed()) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] != null && grid[i][j].getType() == "Ship") {
						distance.add(distanceToNextEarth(grid[i][j]));
					}
				}
			}
		}
		Collections.sort(distance);
		int maxDistance = distance.get(distance.size()-1);
		// Print distances
		System.out.println(maxDistance);
		return maxDistance;
		
	}
	
	public ArrayList<ArrayList<Integer>> startPositions(){
		ArrayList<ArrayList<Integer>> startPositions = new  ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> startPositionX = new ArrayList<>();
		ArrayList<Integer> startPositionY = new ArrayList<>();
		ArrayList<Integer> startPositionID = new ArrayList<>();
		
		for (int i = 0; i < grid[0].length; i++) {
			if (grid[0][i] != null) {
				startPositionX.add(grid[0][i].getStartPositionRow());
				startPositionY.add(grid[0][i].getStartPositionCol());
				startPositionID.add(grid[0][i].getId());
			}
		}
		startPositions.add(startPositionX);
		startPositions.add(startPositionY);
		startPositions.add(startPositionID);
		return startPositions;
	}

	public void centerSelection(Grid grid, ArrayList<Items> ships, ArrayList<Items> earths) {

//		ArrayList<ArrayList<Integer>> distances = new ArrayList<ArrayList<Integer>>();
//		ArrayList<Integer> shipRow = new ArrayList<>();
//		ArrayList<Integer> shipCol = new ArrayList<>();

		int[][] distances;
		ArrayList<Integer> maxRadius = null;

		if (difficulty == "Training") {
			distances = new int[ships.size()][ships.size()];
			for (int i = 0; i < distances.length; i++) {
				for (int j = 0; j < distances[0].length; j++) {
					int distanceX = Math.abs(ships.get(i).getRow() - ships.get(j).getRow());
					int distanceY = Math.abs(ships.get(i).getCol() - ships.get(j).getCol());
					distances[i][j] = distanceX + distanceY;
				}

			}
			// Print distances
			for (int i = 0; i < distances.length; i++) {
				System.out.println(Arrays.toString(distances[i]));
			}
//			ArrayList<ArrayList<Integer>> distanceAndRadius = new ArrayList<ArrayList<Integer>>();
//			ArrayList<Integer> smallestDistance = new ArrayList<>();
			maxRadius = new ArrayList<>();
			for (int i = 0; i < distances.length - 2; i++) {
				for (int j = i + 1; j < distances.length - 1; j++) {
					for (int k = j + 1; k < distances.length; k++) {
						int[] solutionDistance = new int[distances.length];
						solutionDistance[i] = 0;
						solutionDistance[j] = 0;
						solutionDistance[k] = 0;
						for (int l = 0; l < distances.length; l++) {
							if (l != i && l != j && l != k) {
								int[] values = new int[distances.length-3];
								values[0] = distances[i][l];
								values[1] = distances[j][l];
								values[2] = distances[k][l];
								
								Arrays.sort(values);
								int min = values[0];
								solutionDistance[l] = min;
								
							}
						}
						Arrays.sort(solutionDistance);
						maxRadius.add(solutionDistance[solutionDistance.length - 1]);

					}
				}

			}
		} else if (difficulty == "Mittel") {
			distances = new int[ships.size()][ships.size()];
			for (int i = 0; i < distances.length; i++) {
				for (int j = 0; j < distances[0].length; j++) {
					int distanceX = Math.abs(ships.get(i).getRow() - ships.get(j).getRow());
					int distanceY = Math.abs(ships.get(i).getCol() - ships.get(j).getCol());
					distances[i][j] = distanceX + distanceY;
				}

			}
			// Print distances
			for (int i = 0; i < distances.length; i++) {
				System.out.println(Arrays.toString(distances[i]));
			}
			maxRadius = new ArrayList<>();
			for (int i = 0; i < distances.length - 5; i++) {
				for (int j = i + 1; j < distances.length - 4; j++) {
					for (int k = j + 1; k < distances.length - 3; k++) {
						for (int l = k + 1; l < distances.length - 2; l++) {
							for (int m = l + 1; m < distances.length - 1; m++) {
								for (int n = m + 1; n < distances.length; n++) {
									int[] solutionDistance = new int[distances.length];
									solutionDistance[i] = 0;
									solutionDistance[j] = 0;
									solutionDistance[k] = 0;
									solutionDistance[l] = 0;
									solutionDistance[m] = 0;
									solutionDistance[n] = 0;
									for (int o = 0; o < distances.length; o++) {
										if (o != i && o != j && o != k && o != l && o != m && o != n) {
											int[] values = new int[distances.length-4];
											values[0] = distances[i][o];
											values[1] = distances[j][o];
											values[2] = distances[k][o];
											values[3] = distances[l][o];
											values[4] = distances[m][o];
											values[5] = distances[n][o];

											Arrays.sort(values);
											int min = values[0];
											solutionDistance[o] = min;
										}
									}
									Arrays.sort(solutionDistance);
									maxRadius.add(solutionDistance[solutionDistance.length - 1]);
								}
							}
						}

					}
				}

			}
		} else if (difficulty == "Schwer") {
			distances = new int[ships.size()][ships.size()];
			for (int i = 0; i < distances.length; i++) {
				for (int j = 0; j < distances[0].length; j++) {
					int distanceX = Math.abs(ships.get(i).getRow() - ships.get(j).getRow());
					int distanceY = Math.abs(ships.get(i).getCol() - ships.get(j).getCol());
					distances[i][j] = distanceX + distanceY;
				}

			}
			// Print distances
			for (int i = 0; i < distances.length; i++) {
				System.out.println(Arrays.toString(distances[i]));
			}
			maxRadius = new ArrayList<>();
			for (int i = 0; i < distances.length - 5; i++) {
				for (int j = i + 1; j < distances.length - 4; j++) {
					for (int k = j + 1; k < distances.length - 3; k++) {
						for (int l = k + 1; l < distances.length - 2; l++) {
							for (int m = l + 1; m < distances.length - 1; m++) {
									int[] solutionDistance = new int[distances.length];
									solutionDistance[i] = 0;
									solutionDistance[j] = 0;
									solutionDistance[k] = 0;
									solutionDistance[l] = 0;
									solutionDistance[m] = 0;
									for (int o = 0; o < distances.length; o++) {
										if (o != i && o != j && o != k && o != l && o != m) {
											int[] values = new int[distances.length-20];
											values[0] = distances[i][o];
											values[1] = distances[j][o];
											values[2] = distances[k][o];
											values[3] = distances[l][o];
											values[4] = distances[m][o];

											Arrays.sort(values);
											int min = values[0];
											solutionDistance[o] = min;
										}
									}
									Arrays.sort(solutionDistance);
									maxRadius.add(solutionDistance[solutionDistance.length - 1]);
								}
							}
						}

					}
				}

			}
			
		
		Collections.sort(maxRadius);
		minDistance = maxRadius.get(0);
		System.out.println(minDistance);
		
	}

	public void fillGridEarth(Items[][] grid, String difficulty, ArrayList<Items> earths) {
		ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row = new ArrayList<>();
		ArrayList<Integer> col = new ArrayList<>();

		ArrayList<Integer> earthsIndex = new ArrayList<>();
		earthsIndex.add(0);
		earthsIndex.add(2);
		earthsIndex.add(4);
		earthsIndex.add(6);
		earthsIndex.add(8);
		earthsIndex.add(10);

		if (difficulty == "Training") {
			for (int i = 0; i < 3; i++) {
//				int rowIndex = earthsIndex.get(i);
				int rowIndex = 0;
				int colIndex = earthsIndex.get(i);

				row.add(rowIndex);
				col.add(colIndex);

			}
		} else if (difficulty == "Mittel") {
			for (int i = 0; i < 6; i++) {
//				int rowIndex = earthsIndex.get(i);
				int rowIndex = 0;
				int colIndex = earthsIndex.get(i);

				row.add(rowIndex);
				col.add(colIndex);

			}
		} else if (difficulty == "Schwer") {
			for (int i = 0; i < 5; i++) {
//				int rowIndex = earthsIndex.get(i);
				int rowIndex = 0;
				int colIndex = earthsIndex.get(i);
				row.add(rowIndex);
				col.add(colIndex);

			}
		} else {
			System.out.println("Level Error");
		}

		positions.add(row);
		positions.add(col);

		for (int i = 0; i < positions.get(0).size(); i++) {
			grid[0][positions.get(1).get(i)] = new Items(0, positions.get(1).get(i), "Earth");
			earths.add(grid[0][positions.get(1).get(i)]);
		}

	}

	public void fillGridShips(Items[][] gird, String difficulty, ArrayList<Items> ships) {
		ArrayList<ArrayList<Integer>> indicies = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row = new ArrayList<>();
		ArrayList<Integer> col = new ArrayList<>();
		int indexCol = randomNumberCol();
		int indexRow = randomNumberRow();

		col.add(indexCol);
		row.add(indexRow);

		if (difficulty == "Training") {
			for (int i = 0; i < 5; i++) {
				indexCol = randomNumberCol();
				indexRow = randomNumberRow();
				boolean checked = true;

				for (int j = 0; j < col.size(); j++) {
					if (col.get(j) == indexCol && row.get(j) == indexRow) {
						checked = false;
					}
				}

				if (checked) {
					col.add(indexCol);
					row.add(indexRow);
				} else {
					i--;
				}
			}
		} else if (difficulty == "Mittel") {
			for (int i = 0; i < 9; i++) {
				indexCol = randomNumberCol();
				indexRow = randomNumberRow();
				boolean checked = true;

				for (int j = 0; j < col.size(); j++) {
					if (col.get(j) == indexCol && row.get(j) == indexRow) {
						checked = false;
					}
				}

				if (checked) {
					col.add(indexCol);
					row.add(indexRow);
				} else {
					i--;
				}
			}
		} else if (difficulty == "Schwer") {
			for (int i = 0; i < 24; i++) {
				indexCol = randomNumberCol();
				indexRow = randomNumberRow();
				boolean checked = true;

				for (int j = 0; j < col.size(); j++) {
					if (col.get(j) == indexCol && row.get(j) == indexRow) {
						checked = false;
					}
				}

				if (checked) {
					col.add(indexCol);
					row.add(indexRow);
				} else {
					i--;
				}
			}
		} else {
			System.out.println("Level Error");
//			return null;
		}

		indicies.add(row);
		indicies.add(col);

		for (int i = 0; i < indicies.get(0).size(); i++) {
			grid[indicies.get(0).get(i)][indicies.get(1).get(i)] = new Items(indicies.get(0).get(i),
					indicies.get(1).get(i), "Ship");
			ships.add(grid[indicies.get(0).get(i)][indicies.get(1).get(i)]);
		}

	}

	public int randomNumberCol() {
		Random rand = new Random();
		int max = 12;
		int min = 0;
		int int_random = rand.nextInt(max - min + 1) + min;
		return int_random;
	}

	public int randomNumberRow() {
		Random rand = new Random();
		int max = 12;
		int min = 1;
		int int_random = rand.nextInt(max - min + 1) + min;
		return int_random;
	}


	public Rectangle[][] getCells() {
		return cells;
	}

	public void setCells(Rectangle[][] celles) {
		this.cells = celles;
	}

	public Items[][] getGrid() {
		return grid;
	}

	public void setGrid(Items[][] grid) {
		this.grid = grid;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public ArrayList<Items> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Items> ships) {
		this.ships = ships;
	}

	public ArrayList<ArrayList<Integer>> getStartPositions() {
		return startPositions;
	}

	public void setStartPositions(ArrayList<ArrayList<Integer>> startPositions) {
		this.startPositions = startPositions;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}

	public int getMaxRadius() {
		return maxRadius;
	}

	public void setMaxRadius(int maxRadius) {
		this.maxRadius = maxRadius;
	}

}
