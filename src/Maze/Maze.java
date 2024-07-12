package Maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**Maze sorgt für die Initialisierung und Generierung eines Mazes auf Basis von Arrays mit Hilfe der Tiefensuche
 * @author Team 7
 *
 */
public class Maze {

	private int sizeX, sizeY, r1, x, y, totalCells, visitedCells;
	private Cell[][] cells, cells2, cells3, cells4;
	private Cell currentCell;
	private Stack<Cell> cellStack;
	private ArrayList<Vertex> neighborCellList;
	private Vertex tmpV;
	private static final int[] row = { -1, 0, 0, 1 };
	private static final int[] col = { 0, -1, 1, 0 };
	private ShortestPath sp;

	/**Konstruktor, der das Maze generiert
	 * @param x Breite des Maze
	 * @param y Höhe des Maze
	 */
	public Maze(int x, int y) {
		sizeX = x;
		sizeY = y;
		cells = new Cell[sizeX][sizeY];
		cells2 = new Cell[sizeX][sizeY];
		cells3 = new Cell[sizeX][sizeY];
		cells4 = new Cell[sizeX][sizeY];
		initializeCells();
		generateMaze();
		sp = new ShortestPath(this, cells[cells.length - 1][cells[0].length - 1], cells[0][0]);
		findSecondPath(this);
	}
	
	/**Findet auf dem Weg zum Ziel bestimmte Muster, die problemlos aufgebrochen werden können und bricht eine Wand auf
	 * @param maze
	 */
	private void findSecondPath(Maze maze) {
		for (int j = 0; j < sp.getKoor().get(0).size(); j++) {
			int spX = sp.getKoor().get(0).get(j) + (maze.getCells().length - 1);
			int spY = sp.getKoor().get(1).get(j) + (maze.getCells().length - 1);


			for (int i = 0; i < 4; i++) {
				if (i == 0 && this.cells[spX][spY].getWalls()[i] == 1 && spY != 0 && spX != 0 && spX != this.cells.length-1) {
					if ((this.cells[spX - 1][spY].getWalls()[i] == 1 && this.cells[spX + 1][spY].getWalls()[i] == 1)
							|| (this.cells[spX][spY].getWalls()[3] == 1
									&& this.cells[spX + 1][spY - 1].getWalls()[3] == 1)
							|| (this.cells[spX][spY].getWalls()[1] == 1
									&& this.cells[spX - 1][spY - 1].getWalls()[1] == 1)) {
//						System.out.println(this.cells[spX][spY].getWalls()[0]);
//						System.out.println(this.cells[spX][spY-1].getWalls()[2]);
						this.cells[spX][spY].getWalls()[0] = 0;
						this.cells[spX][spY-1].getWalls()[2] = 0;
//						System.out.println(i);
//						System.out.println(this.cells[spX][spY].getWalls()[0]);
//						System.out.println(this.cells[spX][spY-1].getWalls()[2]);
						return;
					}
				}
				if (i == 1 && this.cells[spX][spY].getWalls()[i] == 1 && spY != 0 && spY != this.cells[0].length-1 && spX != this.cells.length-1) {
					if ((this.cells[spX ][spY-1].getWalls()[i] == 1 && this.cells[spX][spY+1].getWalls()[i] == 1)
							|| (this.cells[spX][spY].getWalls()[0] == 1
									&& this.cells[spX + 1][spY + 1].getWalls()[0] == 1)
							|| (this.cells[spX][spY].getWalls()[2] == 1
									&& this.cells[spX + 1][spY - 1].getWalls()[2] == 1)) {
						this.cells[spX][spY].getWalls()[1] = 0;
						this.cells[spX+1][spY].getWalls()[3] = 0;
						return;
					}
				}
				if (i == 2 && this.cells[spX][spY].getWalls()[i] == 1 && spY != this.cells[0].length-1 && spX != 0 && spX != this.cells.length-1) {
					if ((this.cells[spX - 1][spY].getWalls()[i] == 1 && this.cells[spX + 1][spY].getWalls()[i] == 1)
							|| (this.cells[spX][spY].getWalls()[3] == 1
									&& this.cells[spX + 1][spY + 1].getWalls()[3] == 1)
							|| (this.cells[spX][spY].getWalls()[1] == 1
									&& this.cells[spX - 1][spY + 1].getWalls()[1] == 1)) {
						this.cells[spX][spY].getWalls()[2] = 0;
						this.cells[spX][spY+1].getWalls()[0] = 0;
						return;
					}
				}
				if (i == 3 && this.cells[spX][spY].getWalls()[i] == 1 && spY != 0 && spY != this.cells[0].length-1 && spX != 0) {
					if ((this.cells[spX ][spY-1].getWalls()[i] == 1 && this.cells[spX][spY+1].getWalls()[i] == 1)
							|| (this.cells[spX][spY].getWalls()[0] == 1
									&& this.cells[spX - 1][spY + 1].getWalls()[0] == 1)
							|| (this.cells[spX][spY].getWalls()[2] == 1
									&& this.cells[spX + 1][spY + 1].getWalls()[3] == 1)) {
						this.cells[spX][spY].getWalls()[3] = 0;
						this.cells[spX-1][spY].getWalls()[1] = 0;
						return;
					}
				}
			}
		}

	}

	/**
	 * Definiert das Array für die viel Felder des Maze
	 */
	//"Zeichnet nur oben Links weil sizeX und sizeY immernoch so gewaehlt wurden und if abfrage dafuer sorgt, dass es auch nur da zeichnet"
	public void initializeCells() {
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				cells[i][j] = new Cell();
				cells[i][j].setX(i);
				cells[i][j].setY(j);

				cells2[i][j] = new Cell();
				cells2[i][j].setX(i);
				cells2[i][j].setY(j);

				cells3[i][j] = new Cell();
				cells3[i][j].setX(i);
				cells3[i][j].setY(j);

				cells4[i][j] = new Cell();
				cells4[i][j].setX(i);
				cells4[i][j].setY(j);
			}
		}
	}

	// DFS Algo fuer das Oeffnen der Felder im Maze
	/**
	 * Generiert das Maze indem entsprechende Wände geöffnet werden aufgrund des Depth First Search Algorithmus
	 */
	public void generateMaze() {

		/*
		 * -ALGO IN PSEUDOCODE- create a CellStack (LIFO) to hold a list of cell
		 * locations set TotalCells = number of cells in grid choose a cell random and
		 * call it currentcell (siehe x und y) set visitedCells = 1
		 * 
		 * while visitedCells < totalCells find all neighbors of currentCell with all
		 * wall intact if one or more found choose one at random knock down the wall
		 * between it and CurrentCell push CurrentCell location on the CellStack make
		 * the newe cell Currentcell add 1 to visitedCellls else pop the most recent
		 * cell entry off the CellStack make it currentcell
		 */

		Random rand = new Random(); // Für Random Zahl zuständig

		x = rand.nextInt(sizeX); // gibt eine Random Zahl zwischen 0 und sizeX aus
		y = rand.nextInt(sizeY); // gibt eine Random Zahl zwischen 0 und sizeY aus

		cellStack = new Stack<Cell>(); // erstellt einen Stack für die Cells
		totalCells = sizeX * sizeY; // definiert die Gesamtanzahl der Stacks
		visitedCells = 1; // Nach defi vom Algo ist die Startzelle automatisch besucht und wird daher auf
							// 1 gesetzt
		currentCell = cells[x][y]; // sorgt mit x und y dass irgendwo Random angefangen wird

		neighborCellList = new ArrayList<Vertex>(); // Um zu wissen ob es nachbarn gibt

		tmpV = new Vertex();

		while (visitedCells < totalCells) {
			neighborCellList.clear(); // ArrayList wird zu beginn gelöscht

			tmpV = new Vertex();
			if (y - 1 >= 0 && cells[x][y - 1].checkWalls() == true) {
				tmpV.setX1(x); // x Koordinate von CurrentCell
				tmpV.setY1(y); // y Koordinate von CurrentCell
				tmpV.setX2(x); // x Koordinate von NextCell
				tmpV.setY2(y - 1); // y Koordinate von NextCell
				tmpV.setWall1(0); // Markiert die Wand, die von CurrentCell aus gelöscht werden muss
				tmpV.setWall2(2); // Markiert die Wand, die von NextCell aus gelöscht werden muss
				neighborCellList.add(tmpV); // NextCell samt Parametern
			}

			tmpV = new Vertex();
			if (y + 1 < sizeY && cells[x][y + 1].checkWalls() == true) {
				tmpV.setX1(x); // x Koordinate von CurrentCell
				tmpV.setY1(y); // y Koordinate von CurrentCell
				tmpV.setX2(x); // x Koordinate von NextCell
				tmpV.setY2(y + 1);
				tmpV.setWall1(2); // Markiert die Wand, die von CurrentCell aus gelöscht werden muss
				tmpV.setWall2(0); // Markiert die Wand, die von NextCell aus gelöscht werden muss
				neighborCellList.add(tmpV); // NextCell samt Parametern
			}

			tmpV = new Vertex();
			if (x - 1 >= 0 && cells[x - 1][y].checkWalls() == true) {
				tmpV.setX1(x); // x Koordinate von CurrentCell
				tmpV.setY1(y); // y Koordinate von CurrentCell
				tmpV.setX2(x - 1); // x Koordinate von NextCell
				tmpV.setY2(y); // x Koordinate von NextCell
				tmpV.setWall1(3); // Markiert die Wand, die von CurrentCell aus gelöscht werden muss
				tmpV.setWall2(1); // Markiert die Wand, die von NextCell aus gelöscht werden muss
				neighborCellList.add(tmpV); // NextCell samt Parametern
			}

			tmpV = new Vertex();
			if (x + 1 < sizeX && cells[x + 1][y].checkWalls() == true) {
				tmpV.setX1(x); // x Koordinate von CurrentCell
				tmpV.setY1(y); // y Koordinate von CurrentCell
				tmpV.setX2(x + 1); // x Koordinate von NextCell
				tmpV.setY2(y); // x Koordinate von NextCell
				tmpV.setWall1(1); // Markiert die Wand, die von CurrentCell aus gelöscht werden muss
				tmpV.setWall2(3); // Markiert die Wand, die von NextCell aus gelöscht werden muss
				neighborCellList.add(tmpV); // NextCell samt Parametern
			}

			if (neighborCellList.size() >= 1) { // Überprüft, ob es unendeckte Nachbarn gibt
				r1 = rand.nextInt(neighborCellList.size()); // Wählt einen random Nachbarn aus
				tmpV = neighborCellList.get(r1); // setzt diesen auf tmpV

				cells[tmpV.getX1()][tmpV.getY1()].getWalls()[tmpV.getWall1()] = 0; // löscht Wall von CurrentCell
				cells[tmpV.getX2()][tmpV.getY2()].getWalls()[tmpV.getWall2()] = 0; // löscht Wall von NextCell

				cellStack.push(currentCell); // pusht das currentCell auf den Stack

				currentCell = cells[tmpV.getX2()][tmpV.getY2()]; // setzt CurrentCell auf die gefundene Cell

				x = currentCell.getX(); // erhält die x Koordinate von NextCell (jetzt CurrentCell)
				y = currentCell.getY(); // erhält die y Koordinate von NextCell (jetzt CurrentCell)

				visitedCells++; // erhoeht den Counter um keine Endlosschleife in der While zu haben

			} else {
				currentCell = cellStack.pop(); // haut die letzte Zelle im cellStack raus, falls der Fall eintritt, dass
												// es keine unentdeckten NextCells gibt
												// ausserdem wird mit pop() die vorherige Zelle des Stacks wieder
												// ausgewaehlt
				x = currentCell.getX(); // erhaelt die x Koordinate von PreviousCell (jetzt CurrentCell)
				y = currentCell.getY(); // erhaelt die y Koordinate von PreviousCell (jetzt CurrentCell)
			}
		}

		// walls 0 oben, walls 1 rechts, walls 2 unten, walls 3 links

		// Enterance + 2x2 Feld in der Mitte
		cells[0][0].getWalls()[3] = 0;
		cells[sizeX - 1][sizeY - 1].getWalls()[1] = 0;
		cells[sizeX - 1][sizeY - 1].getWalls()[2] = 0;

		// -Duplizieren der MazeLogik in richter Reihenfolge fuer
		// RO/LU/RU---------------------------
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				cells2[sizeX - 1 - i][j] = cells[i][j];
				cells2[sizeX - 1 - i][j].setWalls(cells[i][j].getWalls());
			}
		}

		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				cells3[i][sizeY - 1 - j] = cells[i][j];
				cells3[i][sizeY - 1 - j].setWalls(cells[i][j].getWalls());
			}
		}

		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				cells4[sizeX - 1 - i][sizeY - 1 - j] = cells[i][j];
				cells4[sizeX - 1 - i][sizeY - 1 - j].setWalls(cells[i][j].getWalls());
			}
		}
	}

	// Getter und Setter

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public Cell[][] getCells2() {
		return cells2;
	}

	public Cell[][] getCells3() {
		return cells3;
	}

	public Cell[][] getCells4() {
		return cells4;
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
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

	public int getTotalCells() {
		return totalCells;
	}

	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}

	public int getVisitedCells() {
		return visitedCells;
	}

	public void setVisitedCells(int visitedCells) {
		this.visitedCells = visitedCells;
	}

	public Cell getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
	}

	public Stack<Cell> getCellStack() {
		return cellStack;
	}

	public void setCellStack(Stack<Cell> cellStack) {
		this.cellStack = cellStack;
	}

	public ArrayList<Vertex> getNeighborCellList() {
		return neighborCellList;
	}

	public void setNeighborCellList(ArrayList<Vertex> neighborCellList) {
		this.neighborCellList = neighborCellList;
	}

	public Vertex getTmpV() {
		return tmpV;
	}

	public void setTmpV(Vertex tmpV) {
		this.tmpV = tmpV;
	}

	public static int[] getRow() {
		return row;
	}

	public static int[] getCol() {
		return col;
	}

	public void setCells2(Cell[][] cells2) {
		this.cells2 = cells2;
	}

	public void setCells3(Cell[][] cells3) {
		this.cells3 = cells3;
	}

	public void setCells4(Cell[][] cells4) {
		this.cells4 = cells4;
	}
	
	
}