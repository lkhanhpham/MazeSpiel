package Maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import player.Player;
import team07.main.MyFrame;
import team07.main.MyPanel;
import team07.main.RightPanel;

public class MazeDisplay extends JPanel implements KeyListener, ComponentListener {

	// Allgemeine Startwerte
	private Maze m1;
	private static int oldCellSize;

	private int offSetX, offSetY;
	private int offSetX2, offSetY2;
	private int pointX, pointY, oldX, oldY, startPointX, startPointY, x, y, x2, y2, x2t, y2t;
	private int width, height;
	private int cellSize;
	private int resizeY, resizeX;

	// Click on game
	private boolean onGame1, onGame2, onGame3, onGame4 = false;

	// Resizing
	private boolean resizePlayer = false;
	private boolean isResizing;

	// Zueruck an Start
	private boolean startPlayer = false;

	// Ein Step zurueck
	private int setBackX, setBackY;
	private boolean mover = false;
	private static boolean drawLine = false;

	// ShotestPath
	private ShortestPath sp;
	private boolean leftPath = false;

	// Weg nachzeichnen
	private ArrayList<Integer> drawPointX, drawPointY, drawOldX;
	private static ArrayList<Integer> drawOldY;
	private static int oldResizeX;
	private static int oldResizeY;
	private static boolean backDone = false, afterMove = false, oneStepBackOrRestartInAction = false;
	private int offTrackCount;
	private boolean backWasUsed = false;
	private boolean backOnTrack = false;
	private boolean backActive = false;
	private boolean disableBack = false;

	private Player p;
	private String color;
	private boolean drawPlayer = true;

	public MazeDisplay(int x, int y) {
		m1 = new Maze(x, y);
		cellSize = 16; // Hardcode um alles zu malen
		offSetX = cellSize * 2;
		offSetY = cellSize * 2;
		pointX = offSetX + ((x - 1) * cellSize) + cellSize / 2;
		pointY = offSetY + ((y - 1) * cellSize) + cellSize / 2;
		oldX = pointX;
		oldY = pointY;
		startPointX = pointX;
		startPointY = pointY;
		offSetX2 = offSetX + x * cellSize;
		offSetY2 = offSetY + y * cellSize;
		x2 = x;
		y2 = y;
		drawPlayer = true;

		// Shortest Path initialisieren
		sp = new ShortestPath(m1, m1.getCells()[m1.getCells().length - 1][m1.getCells()[0].length - 1],
				m1.getCells()[0][0]);

		// Wege nachzeichnen Initialisieren
		drawPointX = new ArrayList<Integer>();
		drawPointY = new ArrayList<Integer>();
		drawOldX = new ArrayList<Integer>();
		drawOldY = new ArrayList<Integer>();
		addKeyListener(this);
		addComponentListener(this);

	}

	private void doDrawing(Graphics g) {

		if (drawPlayer == true || resizePlayer) {
			// creates instance of player
			p = new Player();
			color = new String();
			drawPlayer = false;
		}

		width = MyPanel.getLeftPanelWidth(); // GamePanel Width
		height = MyPanel.getLeftPanelHeight(); // GamePanel Height

		// Panel hoehe und breite werden aus MyFrame uebergeben

		// -----Repsonsiveness---------------------------------------------------------------------------------------------
		oldCellSize = cellSize; // Initialiesiert das Aendern

		if (width < height) { // Passt cellSize an wenn Hoehe des Panels veraendert wird
			cellSize = width / ((x2 + 2) * 2);
		}

		if (width > height) { // Passt cellSize an wenn Breite des Panels veraendert wird
			cellSize = height / ((y2 + 2) * 2);
		}

		if (isResizing == true || oldCellSize != cellSize) { // Falls sich Size geaendert hat, wird der Konstruktor
																// quasi neu
			// gebaut

			offSetX = width / 2 - (cellSize * x2);
			offSetY = height / 2 - (cellSize * y2);
			pointX = offSetX + (m1.getSizeX() - 1) * cellSize + cellSize / 2;
			pointY = offSetY + (m1.getSizeY() - 1) * cellSize + cellSize / 2;
			oldX = pointX;
			oldY = pointY;
			startPointX = pointX;
			startPointY = pointY;
			offSetX2 = offSetX + x2 * cellSize;
			offSetY2 = offSetY + y2 * cellSize;

			resizePlayer = true; // Fuer die Responsiveness des Players verantwortlich
									// (Es wurde was Ver�ndert, also ver�nder auch den Player)
			repaint();
		}

		Graphics2D g2d = (Graphics2D) g;
		Graphics2D g2d2 = (Graphics2D) g;
		Graphics2D g2d3 = (Graphics2D) g;
		Graphics2D g2d4 = (Graphics2D) g;
		Graphics2D g2d5 = (Graphics2D) g;

		// -----Raster---------------------------------------------------------------------------------------------
		g2d2.setColor(Color.LIGHT_GRAY); // setzt Farbe vom Hintergrund (Karierter Hintergrund)

		for (int i = 0; i < (cellSize * x2 + cellSize) * 2; i += cellSize) {
			for (int j = 0; j < (cellSize * y2 + cellSize) * 2; j += cellSize) {
				g2d2.drawRect(i + offSetX - cellSize, j + offSetY - cellSize, cellSize, cellSize);
			}
		}

		// -----Felder für
		// Spiele----------------------------------------------------------------------------------

		if (resizeX == ((m1.getSizeX() - 1) + 1) * -1 && resizeY == (m1.getSizeY() - 1) * -1 && leftPath == false) {
			g2d2.setColor(Color.green);
			g2d2.fillRect(offSetX - cellSize, offSetY, cellSize, cellSize); // LO
			g2d2.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d2.drawString("Spiel 1", offSetX - cellSize, offSetY - (cellSize / 3));
			onGame1 = true;
		} else {
			g2d2.setColor(Color.red);
			g2d2.fillRect(offSetX - cellSize, offSetY, cellSize, cellSize); // LO
			g2d2.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d2.drawString("Spiel 1", offSetX - cellSize, offSetY - (cellSize / 3));
			onGame1 = false;
		}

		if (resizeX == ((m1.getSizeX() - 1) + 2) && resizeY == (m1.getSizeY() - 1) * -1 && leftPath == false) {
			g2d3.setColor(Color.green);
			g2d3.fillRect(m1.getSizeX() * (cellSize * 2) + offSetX, offSetY, cellSize, cellSize); // RO
			g2d3.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d3.drawString("Spiel 2", m1.getSizeX() * (cellSize * 2) + offSetX - (cellSize * 2),
					offSetY - (cellSize / 3));
			onGame2 = true;
		} else {
			g2d3.setColor(Color.red);
			g2d3.fillRect(m1.getSizeX() * (cellSize * 2) + offSetX, offSetY, cellSize, cellSize); // RO
			g2d3.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d3.drawString("Spiel 2", m1.getSizeX() * (cellSize * 2) + offSetX - (cellSize * 2),
					offSetY - (cellSize / 3));
			onGame2 = false;
		}

		if (resizeX == ((m1.getSizeX() - 1) + 1) * -1 && resizeY == (m1.getSizeY() - 1) + 1 && leftPath == false) {
			g2d4.setColor(Color.green);
			g2d4.fillRect(offSetX - cellSize, m1.getSizeY() * (cellSize * 2) + offSetY - cellSize, cellSize, cellSize); // LU
			g2d4.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d4.drawString("Spiel 3", offSetX - cellSize,
					m1.getSizeY() * (cellSize * 2) + offSetY + (cellSize - cellSize / 4));
			onGame3 = true;
		} else {
			g2d4.setColor(Color.red);
			g2d4.fillRect(offSetX - cellSize, m1.getSizeY() * (cellSize * 2) + offSetY - cellSize, cellSize, cellSize); // LU
			g2d4.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d4.drawString("Spiel 3", offSetX - cellSize,
					m1.getSizeY() * (cellSize * 2) + offSetY + (cellSize - cellSize / 4));
			onGame3 = false;
		}

		if (resizeX == ((m1.getSizeX() - 1) + 2) && resizeY == (m1.getSizeY() - 1) + 1 && leftPath == false) {
			g2d5.setColor(Color.green);
			g2d5.fillRect(m1.getSizeX() * (cellSize * 2) + offSetX, m1.getSizeY() * (cellSize * 2) + offSetY - cellSize,
					cellSize, cellSize); // RU
			g2d5.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d5.drawString("Spiel 4", m1.getSizeX() * (cellSize * 2) + offSetX - (cellSize * 2),
					m1.getSizeY() * (cellSize * 2) + offSetY + (cellSize - cellSize / 4));
			onGame4 = true;
		} else {
			g2d5.setColor(Color.red);
			g2d5.fillRect(m1.getSizeX() * (cellSize * 2) + offSetX, m1.getSizeY() * (cellSize * 2) + offSetY - cellSize,
					cellSize, cellSize); // RU
			g2d5.setFont(new Font("Times New Roman", Font.PLAIN, cellSize));
			g2d5.drawString("Spiel 4", m1.getSizeX() * (cellSize * 2) + offSetX - (cellSize * 2),
					m1.getSizeY() * (cellSize * 2) + offSetY + (cellSize - cellSize / 4));
			onGame4 = false;
		}

		// Startfeld
		g2d2.setColor(Color.blue);
		g2d2.fillRect(m1.getSizeX() * cellSize + offSetX - cellSize, m1.getSizeY() * cellSize + offSetY - cellSize,
				cellSize, cellSize); // Kann auf 2x2 Feld gestreckt werden, falls die letzen beiden cellSize * 2
										// gesetzt werden
		g2d2.drawString("Start", m1.getSizeX() * cellSize + offSetX - cellSize,
				m1.getSizeY() * cellSize + offSetY - cellSize);

		// -----Maze
		// Drawing---------------------------------------------------------------------------------------
		g2d.setColor(Color.black); // setzt Farbe vom Maze
		g2d.setStroke(new BasicStroke(2)); // Line Thickness

		// Die For-Schleife Printed das Maze mit der Logic, die zuvor in Maze erstellt
		// wurde
		// - FOR ME - drawline(von x, von y, bis x, bis y)

		for (int i = 0; i < m1.getSizeX(); i++) {
			x = i * cellSize + offSetX;
			for (int j = 0; j < m1.getSizeY(); j++) {
				y = j * cellSize + offSetY;

				// Oben Links

				if (m1.getCells()[i][j].getWalls()[0] == 1) {
					g2d.drawLine(x, y, x + cellSize, y); // Malt alle Oberen linien
				}

				if (m1.getCells()[i][j].getWalls()[1] == 1) {
					g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize); // Malt alle rechten linien
				}

				if (m1.getCells()[i][j].getWalls()[2] == 1) {
					g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize); // Malt alle unteren linien
				}

				if (m1.getCells()[i][j].getWalls()[3] == 1) {
					g2d.drawLine(x, y, x, y + cellSize); // Malt alle linken linien
				}

				// Oben Rechts
				// x -> ( + offSetX2 - offSetX)
				// x -> walls[1] = - cellSize
				// x -> walls[3] = + cellSize

				if (m1.getCells2()[i][j].getWalls()[0] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y, x + cellSize + offSetX2 - offSetX, y); // Malt alle Oberen
																									// linien
				}

				if (m1.getCells2()[i][j].getWalls()[1] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y, x + cellSize + offSetX2 - offSetX - cellSize, y + cellSize); // Malt
																															// alle
																															// rechten
																															// linien
				}

				if (m1.getCells2()[i][j].getWalls()[2] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y + cellSize, x + cellSize + offSetX2 - offSetX, y + cellSize); // Malt
																															// alle
																															// unteren
																															// linien
				}

				if (m1.getCells2()[i][j].getWalls()[3] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX + cellSize, y, x + offSetX2 - offSetX + cellSize, y + cellSize); // Malt
																															// alle
																															// linken
																															// linien
				}

				// Unten Links
				// y -> ( + offSetY2 - offSetY)
				// y -> walls[0] = + cellSize
				// y -> walls[2] = - cellSize

				if (m1.getCells3()[i][j].getWalls()[0] == 1) {
					g2d.drawLine(x, y + offSetY2 - offSetY + cellSize, x + cellSize, y + offSetY2 - offSetY + cellSize); // Malt
																															// alle
																															// Oberen
																															// linien
				}

				if (m1.getCells3()[i][j].getWalls()[1] == 1) {
					g2d.drawLine(x + cellSize, y + offSetY2 - offSetY, x + cellSize, y + cellSize + offSetY2 - offSetY); // Malt
																															// alle
																															// rechten
																															// linien
				}

				if (m1.getCells3()[i][j].getWalls()[2] == 1) {
					g2d.drawLine(x, y + offSetY2 - offSetY, x + cellSize, y + offSetY2 - offSetY); // Malt alle unteren
																									// linien
				}

				if (m1.getCells3()[i][j].getWalls()[3] == 1) {
					g2d.drawLine(x, y + offSetY2 - offSetY, x, y + cellSize + offSetY2 - offSetY); // Malt alle linken
																									// linien
				}

				// Unten Rechts
				// x | y -> ( + offSetX2 - offSetX | + offSetY2 - offSetY)
				// y -> walls[0] = + cellSize
				// x -> walls[1] = - cellSize
				// y -> walls[2] = - cellSize
				// x -> walls[3] = + cellSize

				if (m1.getCells4()[i][j].getWalls()[0] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y + offSetY2 - offSetY + cellSize,
							x + cellSize + offSetX2 - offSetX, y + offSetY2 + cellSize - offSetY); // Malt alle Oberen
																									// linien
				}

				if (m1.getCells4()[i][j].getWalls()[1] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y + offSetY2 - offSetY, x + offSetX2 - offSetX,
							y + cellSize + offSetY2 - offSetY); // Malt alle rechten linien
				}

				if (m1.getCells4()[i][j].getWalls()[2] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX, y + offSetY2 - offSetY, x + cellSize + offSetX2 - offSetX,
							y + offSetY2 - offSetY); // Malt alle unteren linien
				}

				if (m1.getCells4()[i][j].getWalls()[3] == 1) {
					g2d.drawLine(x + offSetX2 - offSetX + cellSize, y + offSetY2 - offSetY,
							x + offSetX2 - offSetX + cellSize, y + cellSize + offSetY2 - offSetY); // Malt alle linken
																									// linien
				}

			}
		}

		// -Colision mit
		// Waenden--------------------------------------------------------------------------------------

		x = (oldX - offSetX - cellSize / 2) / cellSize; // x ist ArrayWise (0-getX)
		y = (oldY - offSetY - cellSize / 2) / cellSize; // y ist ArrayWise (0-getY)
		x2t = (oldX - offSetX - cellSize / 2) / cellSize - x2; // Fuer Zellenverschiebung neue Variable
		y2t = (oldY - offSetY - cellSize / 2) / cellSize - y2; // Fuer Zellenverschiebung neue Variable

		// LO
		if (x >= 0 && x < m1.getSizeX() && y >= 0 && y < m1.getSizeY()) {
			// Links
			if (oldX > pointX && m1.getCells()[x][y].getWalls()[3] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX += 1;

				// wenn möglich
				// oldResizeX = resizeX;

				// Rechts
			} else if (oldX < pointX && m1.getCells()[x][y].getWalls()[1] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX -= 1;
				// wenn möglich
				// oldResizeX = resizeX;

				// Oben
			} else if (oldY > pointY && m1.getCells()[x][y].getWalls()[0] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY += 1;
				// wenn möglich
				// oldResizeY = resizeY;

				// Unten
			} else if (oldY < pointY && m1.getCells()[x][y].getWalls()[2] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY -= 1;
				// wenn möglich
				// oldResizeY = resizeY;
			}
		}

		// RO
		if (x >= m1.getSizeX() && x < m1.getSizeX() * 2 && y >= 0 && y < m1.getSizeY()) {
			// Links
			if (oldX > pointX && m1.getCells2()[x2t][y].getWalls()[1] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX += 1;

				// Rechts
			} else if (oldX < pointX && m1.getCells2()[x2t][y].getWalls()[3] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX -= 1;

				// Oben
			} else if (oldY > pointY && m1.getCells2()[x2t][y].getWalls()[0] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY += 1;

				// Unten
			} else if (oldY < pointY && m1.getCells2()[x2t][y].getWalls()[2] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY -= 1;
			}
		}

		// LU
		if (x >= 0 && x < m1.getSizeX() && y >= m1.getSizeY() && y < m1.getSizeY() * 2) {
			// Links
			if (oldX > pointX && m1.getCells3()[x][y2t].getWalls()[3] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX += 1;

				// Rechts
			} else if (oldX < pointX && m1.getCells3()[x][y2t].getWalls()[1] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX -= 1;

				// Oben
			} else if (oldY > pointY && m1.getCells3()[x][y2t].getWalls()[2] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY += 1;

				// Unten
			} else if (oldY < pointY && m1.getCells3()[x][y2t].getWalls()[0] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY -= 1;
			}
		}

		// RU
		if (x >= m1.getSizeX() && x < m1.getSizeX() * 2 && y >= m1.getSizeY() && y < m1.getSizeY() * 2) {
			// Links
			if (oldX > pointX && m1.getCells4()[x2t][y2t].getWalls()[1] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX += 1;

				// Rechts
			} else if (oldX < pointX && m1.getCells4()[x2t][y2t].getWalls()[3] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeX -= 1;

				// Oben
			} else if (oldY > pointY && m1.getCells4()[x2t][y2t].getWalls()[2] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY += 1;

				// Unten
			} else if (oldY < pointY && m1.getCells4()[x2t][y2t].getWalls()[0] == 1) {
				pointX = oldX;
				pointY = oldY;
				resizeY -= 1;
			}
		}

		// Achtet ob sich der Player bewegt oder ob er auf der Stelle steht
		if (resizeX != oldResizeX || resizeY != oldResizeY) {
			mover = false;
		} else {
			mover = true;
		}

		// Sollte sich der Spieler bewegen werden die setBack Koordinaten so gesetzt,
		// dass die alten sizeX/Y Werte uebergeben werden
		if (mover == false) {
			setBackX = oldResizeX;
			setBackY = oldResizeY;
			backActive = false;
			disableBack = false;
		}
		if (mover == true && backActive == true) {
			disableBack = true;
			RightPanel.getEinszurueck().setEnabled(false);
		}

		// -Responsiveness von Player wird
		// angepasst-----------------------------------------------------------------
		if (resizePlayer == true) {
			pointY += cellSize * resizeY;
			pointX += cellSize * resizeX;
			resizePlayer = false;
		}

		// -Player wird an Start zurueck
		// geschickt--------------------------------------------------------------------
		if (startPlayer == true) {
			// Spieler auf Start zuruecksetzen
			pointX = offSetX + (m1.getSizeX() - 1) * cellSize + cellSize / 2;
			pointY = offSetY + (m1.getSizeY() - 1) * cellSize + cellSize / 2;

			// Alte wege loeschen
			drawOldX.clear();
			drawOldY.clear();
			drawPointX.clear();
			drawPointY.clear();

			startPlayer = false;
		}
		// -Weg
		// nachzeichnen-------------------------------------------------------------------------------------------------------------------------------------
		if (oneStepBackOrRestartInAction == false) { // Damit falls zurueckgesetzt wird oder back gesetzt wird keine
														// linie gezeichnet wird
			if (drawLine == true) { // Falls eine Linie gezeichnet werden soll
				if (mover == false) { // Falls sich der Player ueberhaupt bewegt hat
					drawOldX.add(oldResizeX);
					drawOldY.add(oldResizeY);
					drawPointX.add(resizeX);
					drawPointY.add(resizeY);
					drawLine = false;
				}
			}
		}

		if (oneStepBackOrRestartInAction == true) {
			oneStepBackOrRestartInAction = false;
		}

		if (drawPointX.size() > 0) {
			for (int i = 0; i < drawPointX.size(); i++) {
				g.setColor(Color.BLUE);
				g.drawLine((drawOldX.get(i) * cellSize + getStartPointX()),
						(drawOldY.get(i) * cellSize + getStartPointY()) + cellSize / 8,
						(drawPointX.get(i) * cellSize + getStartPointX()),
						(drawPointY.get(i) * cellSize + getStartPointY()) + cellSize / 8);
			}
		}

		// Player Size (x,x,zahl,zahl) - AKTUELL RESPONSIVE
		// --------------------------------------------------------

		// set initial position of player
		p.setX(pointX);
		p.setY(pointY);
		p.setResizex(resizeX);
		p.setResizey(resizeY);

		for (int i = 0; i < sp.getKoor().get(0).size(); i++) {
			boolean stop = false;

			for (int j = 0; j < sp.getKoor().get(0).size(); j++) {
				if ( // bewegen auf dem kürzesten Weg
				sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY
						&& sp.getKoor().get(0).get(j) == oldResizeX && sp.getKoor().get(1).get(j) == oldResizeY
						&& sp.getKoor().get(2).get(i) == sp.getKoor().get(2).get(j) + 1
						|| sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY
								&& sp.getKoor().get(0).get(j) == oldResizeX && sp.getKoor().get(1).get(j) == oldResizeY
								&& sp.getKoor().get(2).get(i) == sp.getKoor().get(2).get(j) - 1 && backWasUsed
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
								&& sp.getKoor2().get(0).get(j) == oldResizeX
								&& sp.getKoor2().get(1).get(j) == oldResizeY
								&& sp.getKoor2().get(2).get(i) == sp.getKoor2().get(2).get(j) + 1
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
								&& sp.getKoor2().get(0).get(j) == oldResizeX
								&& sp.getKoor2().get(1).get(j) == oldResizeY
								&& sp.getKoor2().get(2).get(i) == sp.getKoor2().get(2).get(j) - 1 && backWasUsed
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
								&& sp.getKoor3().get(0).get(j) == oldResizeX
								&& sp.getKoor3().get(1).get(j) == oldResizeY
								&& sp.getKoor3().get(2).get(i) == sp.getKoor3().get(2).get(j) + 1
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
								&& sp.getKoor3().get(0).get(j) == oldResizeX
								&& sp.getKoor3().get(1).get(j) == oldResizeY
								&& sp.getKoor3().get(2).get(i) == sp.getKoor3().get(2).get(j) - 1 && backWasUsed
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
								&& sp.getKoor4().get(0).get(j) == oldResizeX
								&& sp.getKoor4().get(1).get(j) == oldResizeY
								&& sp.getKoor4().get(2).get(i) == sp.getKoor4().get(2).get(j) + 1
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
								&& sp.getKoor4().get(0).get(j) == oldResizeX
								&& sp.getKoor4().get(1).get(j) == oldResizeY
								&& sp.getKoor4().get(2).get(i) == sp.getKoor4().get(2).get(j) - 1 && backWasUsed
						|| sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY && backOnTrack
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
								&& backOnTrack
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
								&& backOnTrack
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
								&& backOnTrack
						// Sonderfälle für die Startfelder
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
								&& sp.getKoor().get(0).get(j) == oldResizeX && sp.getKoor().get(1).get(j) == oldResizeY
								&& sp.getKoor2().get(2).get(i) == sp.getKoor().get(2).get(j)
						|| sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY
								&& sp.getKoor2().get(0).get(j) == oldResizeX
								&& sp.getKoor2().get(1).get(j) == oldResizeY
								&& sp.getKoor().get(2).get(i) == sp.getKoor2().get(2).get(j)
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
								&& sp.getKoor().get(0).get(j) == oldResizeX && sp.getKoor().get(1).get(j) == oldResizeY
								&& sp.getKoor3().get(2).get(i) == sp.getKoor().get(2).get(j)
						|| sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY
								&& sp.getKoor3().get(0).get(j) == oldResizeX
								&& sp.getKoor3().get(1).get(j) == oldResizeY
								&& sp.getKoor().get(2).get(i) == sp.getKoor3().get(2).get(j)
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
								&& sp.getKoor3().get(0).get(j) == oldResizeX
								&& sp.getKoor3().get(1).get(j) == oldResizeY
								&& sp.getKoor4().get(2).get(i) == sp.getKoor3().get(2).get(j)
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
								&& sp.getKoor4().get(0).get(j) == oldResizeX
								&& sp.getKoor4().get(1).get(j) == oldResizeY
								&& sp.getKoor4().get(2).get(i) == sp.getKoor3().get(2).get(j)
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
								&& sp.getKoor2().get(0).get(j) == oldResizeX
								&& sp.getKoor2().get(1).get(j) == oldResizeY
								&& sp.getKoor4().get(2).get(i) == sp.getKoor2().get(2).get(j)
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
								&& sp.getKoor4().get(0).get(j) == oldResizeX
								&& sp.getKoor4().get(1).get(j) == oldResizeY
								&& sp.getKoor2().get(2).get(i) == sp.getKoor4().get(2).get(j)
						// Sonderfälle für die Zeilfelder
						|| resizeX == -(x2 - 1) && resizeY == -(y2 - 1) || resizeX == x2 && resizeY == -(y2 - 1)
						|| resizeX == -(x2 - 1) && resizeY == y2 || resizeX == x2 && resizeY == y2
						|| resizeX == -(x2) && resizeY == -(y2 - 1) || resizeX == (x2 + 1) && resizeY == -(y2 - 1)
						|| resizeX == -(x2) && resizeY == y2 || resizeX == (x2 + 1) && resizeY == y2
						|| resizeX == oldResizeX && resizeY == oldResizeY) {
					if (leftPath == true) {
						g.setColor(Color.RED); // Player Color
						color = "red";
					}
					if (leftPath == false) {
						offTrackCount = 0;
						g.setColor(Color.GREEN); // Player Color
						color = "yellow";
					}
					g.drawImage(Player.getPlayer(color), p.getX() - cellSize / 4,
							p.getY() + cellSize / 8 - cellSize / 4, this);
					// g.fillRect(pointX - cellSize / 4, pointY + cellSize / 8 - cellSize / 4,
					// cellSize / 2, cellSize / 2);
					backOnTrack = false;
					stop = true;
					break;

				} else {

					if (i == sp.getKoor().get(0).size() - 1 && j == sp.getKoor().get(0).size() - 1 && !mover) {
						// Sollte keine Koordinate stimmen, laeuft die Schleife durch und der Player
						// wird rot
						leftPath = true;
						offTrackCount++;

						g.setColor(Color.RED);
						color = "red";// Player Color
						g.drawImage(Player.getPlayer(color), p.getX() - cellSize / 4,
								p.getY() + cellSize / 8 - cellSize / 4, this);
					}
				}
			}
			if (stop) {
				break;
			}
			// Ueberprueft alle XY-Koordinaten des kuerzesten Weges + Sonderfaelle

		}

	}

	// -Paint von Maze + Collision +
	// Player--------------------------------------------------------------------------
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	public void keyTyped(KeyEvent e) {
	}

	// -Pfeiltasten +
	// WASD-----------------------------------------------------------------------------------------
	public void keyPressed(KeyEvent e) {
		oldX = pointX; // Fuer Collision wichtig, damit man zurueckgesetzt wird, falls man gegen eine
						// Wand laeuft
		oldY = pointY; // siehe Oben

		isResizing = false;

		if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {

			pointY = pointY + cellSize;
			oldResizeY = resizeY;
			oldResizeX = resizeX;
			resizeY = ((pointY - getStartPointY()) / cellSize);
			drawLine = true;
			backDone = true;

			// Damit man sich wenn man in einem Spielfeld ist nicht mehr bewegen kann
			// LO
			if (resizeX == -x2 && resizeY == -(y2 - 2)) {
				pointY -= cellSize;
				resizeY -= 1;
			}
			// RO
			if (resizeX == x2 + 1 && resizeY == -(y2 - 2)) {
				pointY -= cellSize;
				resizeY -= 1;
			}
			// LU
			if (resizeX == -x2 && resizeY == y2 + 1) {
				pointY -= cellSize;
				resizeY -= 1;
			}
			// RU
			if (resizeX == x2 + 1 && resizeY == y2 + 1) {
				pointY -= cellSize;
				resizeY -= 1;
			}
			backWasUsed = false;
			RightPanel.getEinszurueck().setEnabled(true);
		}

		if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
			pointY = pointY - cellSize;
			oldResizeY = resizeY;
			oldResizeX = resizeX;
			resizeY = ((pointY - getStartPointY()) / cellSize);
			drawLine = true;
			backDone = true;

			// LO
			if (resizeX == -x2 && resizeY == -y2) {
				pointY += cellSize;
				resizeY += 1;
			}
			// RO
			if (resizeX == x2 + 1 && resizeY == -y2) {
				pointY += cellSize;
				resizeY += 1;
			}
			// LU
			if (resizeX == -x2 && resizeY == y2 - 1) {
				pointY += cellSize;
				resizeY += 1;
			}
			// RU
			if (resizeX == x2 + 1 && resizeY == y2 - 1) {
				pointY += cellSize;
				resizeY += 1;
			}
			backWasUsed = false;
			RightPanel.getEinszurueck().setEnabled(true);
		}

		if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {
			pointX = pointX - cellSize;
			oldResizeY = resizeY;
			oldResizeX = resizeX;
			resizeX = ((pointX - getStartPointX()) / cellSize);
			drawLine = true;
			backDone = true;

			// LO
			if (resizeX == -(x2 + 1) && resizeY == -(y2 - 1)) {
				pointX += cellSize;
				resizeX += 1;
			}
			// LU
			if (resizeX == -(x2 + 1) && resizeY == y2) {
				pointX += cellSize;
				resizeX += 1;
			}
			backWasUsed = false;
			RightPanel.getEinszurueck().setEnabled(true);
		}

		if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
			pointX = pointX + cellSize;
			oldResizeY = resizeY;
			oldResizeX = resizeX;
			resizeX = ((pointX - getStartPointX()) / cellSize);
			drawLine = true;
			backDone = true;

			// RO
			if (resizeX == x2 + 2 && resizeY == -(y2 - 1)) {
				pointX -= cellSize;
				resizeX -= 1;
			}
			// RU
			if (resizeX == x2 + 2 && resizeY == y2) {
				pointX -= cellSize;
				resizeX -= 1;
			}
			backWasUsed = false;
			RightPanel.getEinszurueck().setEnabled(true);
		}

		if (e.getKeyCode() == e.VK_R) { // Player Reset
			oneStepBackOrRestartInAction = true;
			resizeX = 0;
			resizeY = 0;
			oldResizeX = 0;
			oldResizeY = 0;

			startPlayer = true;
			leftPath = false;
			afterMove = false;
			backWasUsed = false;
		}

		if (e.getKeyCode() == e.VK_B && disableBack == false) { // Player Backset
			oneStepBackOrRestartInAction = true;
			pointX = setBackX * cellSize + getStartPointX();
			pointY = setBackY * cellSize + getStartPointY();

			resizeX = ((pointX - getStartPointX()) / cellSize);
			resizeY = ((pointY - getStartPointY()) / cellSize);

			if (backDone == true) {
				drawOldX.remove(drawOldX.size() - 1);
				drawOldY.remove(drawOldY.size() - 1);
				drawPointX.remove(drawPointX.size() - 1);
				drawPointY.remove(drawPointY.size() - 1);
			}

			for (int i = 0; i < sp.getKoor().get(0).size(); i++) {
				if ((sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY
						|| sp.getKoor2().get(0).get(i) == resizeX && sp.getKoor2().get(1).get(i) == resizeY
						|| sp.getKoor3().get(0).get(i) == resizeX && sp.getKoor3().get(1).get(i) == resizeY
						|| sp.getKoor4().get(0).get(i) == resizeX && sp.getKoor4().get(1).get(i) == resizeY
						|| sp.getKoor().get(0).get(i) == resizeX && sp.getKoor().get(1).get(i) == resizeY

						|| resizeX == -(x2 - 1) && resizeY == -(y2 - 1) || resizeX == x2 && resizeY == -(y2 - 1)
						|| resizeX == -(x2 - 1) && resizeY == y2 || resizeX == x2 && resizeY == y2
						|| resizeX == -(x2) && resizeY == -(y2 - 1) || resizeX == (x2 + 1) && resizeY == -(y2 - 1)
						|| resizeX == -(x2) && resizeY == y2 || resizeX == (x2 + 1) && resizeY == y2)
						&& offTrackCount < 2) {
					leftPath = false;
					backOnTrack = true;
					break;
				} else {
					if (i == sp.getKoor().get(0).size() - 1) { // Checkt, dass nicht einfach wieder auf dem weg
																// backSet benutzt wird
						afterMove = true;
					}
				}

			}

			if (afterMove == true) {
				leftPath = true;
			}

			backDone = false;
			mover = false;
			backWasUsed = true;
			backActive = true;

		}

		// -TESTING TOOLS----------------------------------------------------------

		if (e.getKeyCode() == e.VK_I) { // INFOS
			System.out.println("Infos:");
			System.out.println("pointX (Player Location) : " + pointX);
			System.out.println("pointY (Player Location) : " + pointY);
			System.out.println("oldX : " + oldX);
			System.out.println("oldY : " + oldY);
			System.out.println("sizeX : " + m1.getSizeX());
			System.out.println("sizeY : " + m1.getSizeY());
			System.out.println("cellSize : " + cellSize);
			System.out.println("startX : " + getStartPointX());
			System.out.println("startY : " + getStartPointY());
			System.out.println("posX : " + resizeX);
			System.out.println("posY : " + resizeY);
			System.out.println("oldPosX : " + oldResizeX);
			System.out.println("oldPosY : " + oldResizeY + "\n");
		}

		if (e.getKeyCode() == e.VK_C) { // Koordinaten des Kürzessten Weges
			System.out.println("Coordinates LO");
			for (int j = 0; j < sp.getKoor().get(0).size(); j++) {
				System.out.println("X-Koordinate " + sp.getKoor().get(0).get(j));
				System.out.println("Y-Koordinate " + sp.getKoor().get(1).get(j));
				System.out.println("Distance " + sp.getKoor().get(2).get(j));
			}

			System.out.println("\n");
			System.out.println("Coordinates RO");

			for (int j = 0; j < sp.getKoor2().get(0).size(); j++) {
				System.out.println("X-Koordinate " + sp.getKoor2().get(0).get(j));
				System.out.println("Y-Koordinate " + sp.getKoor2().get(1).get(j));
				System.out.println("Distance " + sp.getKoor2().get(2).get(j));
			}

			System.out.println("\n");
			System.out.println("Coordinates LU");

			for (int j = 0; j < sp.getKoor3().get(0).size(); j++) {
				System.out.println("X-Koordinate " + sp.getKoor3().get(0).get(j));
				System.out.println("Y-Koordinate " + sp.getKoor3().get(1).get(j));
				System.out.println("Distance " + sp.getKoor3().get(2).get(j));
			}

			System.out.println("\n");
			System.out.println("Coordinates RU");

			for (int j = 0; j < sp.getKoor().get(0).size(); j++) {
				System.out.println("X-Koordinate " + sp.getKoor3().get(0).get(j));
				System.out.println("Y-Koordinate " + sp.getKoor3().get(1).get(j));
				System.out.println("Distance " + sp.getKoor3().get(2).get(j));
			}
		}

		if (e.getKeyCode() == e.VK_ENTER) {
			if (onGame1 || onGame3 || onGame4) {
				MyFrame.getCl().show(MyFrame.getPanelCont(), "2");
			}

			if (onGame2) {
				MyFrame.getCl().show(MyFrame.getPanelCont(), "3");
			}

			if (onGame3) {
				MyFrame.getCl().show(MyFrame.getPanelCont(), "4");
			}

			if (onGame4) {
				MyFrame.getCl().show(MyFrame.getPanelCont(), "5");
			}
		}

		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		isResizing = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// Getter und Setter

	public Maze getM1() {
		return m1;
	}

	public void setM1(Maze m1) {
		this.m1 = m1;
	}

	public int getOffSetX() {
		return offSetX;
	}

	public void setOffSetX(int offSetX) {
		this.offSetX = offSetX;
	}

	public int getOffSetY() {
		return offSetY;
	}

	public void setOffSetY(int offSetY) {
		this.offSetY = offSetY;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	public int getOldX() {
		return oldX;
	}

	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public int getStartPointX() {
		return startPointX;
	}

	public void setStartPointX(int startPointX) {
		this.startPointX = startPointX;
	}

	public int getStartPointY() {
		return startPointY;
	}

	public void setStartPointY(int startPointY) {
		this.startPointY = startPointY;
	}

	public int getXMazeDisplay() {
		return x;
	}

	public void setXMazeDisplay(int x) {
		this.x = x;
	}

	public int getYMazeDisplay() {
		return y;
	}

	public void setYMazeDisplay(int y) {
		this.y = y;
	}

	public int getOffSetX2() {
		return offSetX2;
	}

	public void setOffSetX2(int offSetX2) {
		this.offSetX2 = offSetX2;
	}

	public int getOffSetY2() {
		return offSetY2;
	}

	public void setOffSetY2(int offSetY2) {
		this.offSetY2 = offSetY2;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX2t() {
		return x2t;
	}

	public void setX2t(int x2t) {
		this.x2t = x2t;
	}

	public int getY2t() {
		return y2t;
	}

	public void setY2t(int y2t) {
		this.y2t = y2t;
	}

	public int getMazeWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getMazeHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getResizeY() {
		return resizeY;
	}

	public void setResizeY(int resizeY) {
		this.resizeY = resizeY;
	}

	public int getResizeX() {
		return resizeX;
	}

	public void setResizeX(int resizeX) {
		this.resizeX = resizeX;
	}

	public boolean isResizePlayer() {
		return resizePlayer;
	}

	public void setResizePlayer(boolean resizePlayer) {
		this.resizePlayer = resizePlayer;
	}

	public boolean isStartPlayer() {
		return startPlayer;
	}

	public void setStartPlayer(boolean startPlayer) {
		this.startPlayer = startPlayer;
	}

	public int getSetBackX() {
		return setBackX;
	}

	public void setSetBackX(int setBackX) {
		this.setBackX = setBackX;
	}

	public int getSetBackY() {
		return setBackY;
	}

	public void setSetBackY(int setBackY) {
		this.setBackY = setBackY;
	}

	public boolean isMover() {
		return mover;
	}

	public void setMover(boolean mover) {
		this.mover = mover;
	}

	public boolean isDrawLine() {
		return drawLine;
	}

	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}

	public ShortestPath getSp() {
		return sp;
	}

	public void setSp(ShortestPath sp) {
		this.sp = sp;
	}

	public boolean isLeftPath() {
		return leftPath;
	}

	public void setLeftPath(boolean leftPath) {
		this.leftPath = leftPath;
	}

	public ArrayList<Integer> getDrawPointX() {
		return drawPointX;
	}

	public void setDrawPointX(ArrayList<Integer> drawPointX) {
		this.drawPointX = drawPointX;
	}

	public ArrayList<Integer> getDrawPointY() {
		return drawPointY;
	}

	public void setDrawPointY(ArrayList<Integer> drawPointY) {
		this.drawPointY = drawPointY;
	}

	public ArrayList<Integer> getDrawOldX() {
		return drawOldX;
	}

	public void setDrawOldX(ArrayList<Integer> drawOldX) {
		this.drawOldX = drawOldX;
	}

	public ArrayList<Integer> getDrawOldY() {
		return drawOldY;
	}

	public void setDrawOldY(ArrayList<Integer> drawOldY) {
		this.drawOldY = drawOldY;
	}

	public int getOldResizeX() {
		return oldResizeX;
	}

	public void setOldResizeX(int oldResizeX) {
		this.oldResizeX = oldResizeX;
	}

	public int getOldResizeY() {
		return oldResizeY;
	}

	public void setOldResizeY(int oldResizeY) {
		MazeDisplay.oldResizeY = oldResizeY;
	}

	public boolean isBackDone() {
		return backDone;
	}

	public void setBackDone(boolean backDone) {
		this.backDone = backDone;
	}

	public boolean isAfterMove() {
		return afterMove;
	}

	public void setAfterMove(boolean afterMove) {
		this.afterMove = afterMove;
	}

	public boolean isOneStepBackOrRestartInAction() {
		return oneStepBackOrRestartInAction;
	}

	public void setOneStepBackOrRestartInAction(boolean oneStepBackOrRestartInAction) {
		this.oneStepBackOrRestartInAction = oneStepBackOrRestartInAction;
	}

	public boolean isOnGame1() {
		return onGame1;
	}

	public void setOnGame1(boolean onGame1) {
		this.onGame1 = onGame1;
	}

	public boolean isOnGame2() {
		return onGame2;
	}

	public void setOnGame2(boolean onGame2) {
		this.onGame2 = onGame2;
	}

	public boolean isOnGame3() {
		return onGame3;
	}

	public void setOnGame3(boolean onGame3) {
		this.onGame3 = onGame3;
	}

	public boolean isOnGame4() {
		return onGame4;
	}

	public void setOnGame4(boolean onGame4) {
		this.onGame4 = onGame4;
	}

	public boolean isBackWasUsed() {
		return backWasUsed;
	}

	public void setBackWasUsed(boolean backWasUsed) {
		this.backWasUsed = backWasUsed;
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public int getOffTrackCount() {
		return offTrackCount;
	}

	public void setOffTrackCount(int offTrackCount) {
		this.offTrackCount = offTrackCount;
	}

	public boolean isBackOnTrack() {
		return backOnTrack;
	}

	public void setBackOnTrack(boolean backOnTrack) {
		this.backOnTrack = backOnTrack;
	}

	// /**
	// * @return the m1
	// */
	// public Maze getM1() {
	// return m1;
	// }
	//
	// /**
	// * @param m1 the m1 to set
	// */
	// public void setM1(Maze m1) {
	// this.m1 = m1;
	// }
	//
	// /**
	// * @return the offSetX
	// */
	// public static int getOffSetX() {
	// return offSetX;
	// }
	//
	// /**
	// * @param offSetX the offSetX to set
	// */
	// public static void setOffSetX(int offSetX) {
	// MazeDisplay.offSetX = offSetX;
	// }
	//
	// /**
	// * @return the offSetY
	// */
	// public static int getOffSetY() {
	// return offSetY;
	// }
	//
	// /**
	// * @param offSetY the offSetY to set
	// */
	// public static void setOffSetY(int offSetY) {
	// MazeDisplay.offSetY = offSetY;
	// }
	//
	// /**
	// * @return the offSetX2
	// */
	// public static int getOffSetX2() {
	// return offSetX2;
	// }
	//
	// /**
	// * @param offSetX2 the offSetX2 to set
	// */
	// public static void setOffSetX2(int offSetX2) {
	// MazeDisplay.offSetX2 = offSetX2;
	// }
	//
	// /**
	// * @return the offSetY2
	// */
	// public static int getOffSetY2() {
	// return offSetY2;
	// }
	//
	// /**
	// * @param offSetY2 the offSetY2 to set
	// */
	// public static void setOffSetY2(int offSetY2) {
	// MazeDisplay.offSetY2 = offSetY2;
	// }
	//
	// /**
	// * @return the pointX
	// */
	// public static int getPointX() {
	// return pointX;
	// }
	//
	// /**
	// * @param pointX the pointX to set
	// */
	// public static void setPointX(int pointX) {
	// MazeDisplay.pointX = pointX;
	// }
	//
	// /**
	// * @return the pointY
	// */
	// public static int getPointY() {
	// return pointY;
	// }
	//
	// /**
	// * @param pointY the pointY to set
	// */
	// public static void setPointY(int pointY) {
	// MazeDisplay.pointY = pointY;
	// }
	//
	// /**
	// * @return the oldX
	// */
	// public static int getOldX() {
	// return oldX;
	// }
	//
	// /**
	// * @param oldX the oldX to set
	// */
	// public static void setOldX(int oldX) {
	// MazeDisplay.oldX = oldX;
	// }
	//
	// /**
	// * @return the oldY
	// */
	// public static int getOldY() {
	// return oldY;
	// }
	//
	// /**
	// * @param oldY the oldY to set
	// */
	// public static void setOldY(int oldY) {
	// MazeDisplay.oldY = oldY;
	// }
	//
	// /**
	// * @return the startPointX
	// */
	// public static int getStartPointX() {
	// return startPointX;
	// }
	//
	// /**
	// * @param startPointX the startPointX to set
	// */
	// public static void setStartPointX(int startPointX) {
	// MazeDisplay.startPointX = startPointX;
	// }
	//
	// /**
	// * @return the startPointY
	// */
	// public static int getStartPointY() {
	// return startPointY;
	// }
	//
	// /**
	// * @param startPointY the startPointY to set
	// */
	// public static void setStartPointY(int startPointY) {
	// MazeDisplay.startPointY = startPointY;
	// }
	//
	// /**
	// * @return the x
	// */
	// public int getX() {
	// return x;
	// }
	//
	// /**
	// * @param x the x to set
	// */
	// public static void setX(int x) {
	// MazeDisplay.x = x;
	// }
	//
	// /**
	// * @return the y
	// */
	// public int getY() {
	// return y;
	// }
	//
	// /**
	// * @param y the y to set
	// */
	// public static void setY(int y) {
	// MazeDisplay.y = y;
	// }
	//
	// /**
	// * @return the x2
	// */
	// public static int getX2() {
	// return x2;
	// }
	//
	// /**
	// * @param x2 the x2 to set
	// */
	// public static void setX2(int x2) {
	// MazeDisplay.x2 = x2;
	// }
	//
	// /**
	// * @return the y2
	// */
	// public static int getY2() {
	// return y2;
	// }
	//
	// /**
	// * @param y2 the y2 to set
	// */
	// public static void setY2(int y2) {
	// MazeDisplay.y2 = y2;
	// }
	//
	// /**
	// * @return the x2t
	// */
	// public static int getX2t() {
	// return x2t;
	// }
	//
	// /**
	// * @param x2t the x2t to set
	// */
	// public static void setX2t(int x2t) {
	// MazeDisplay.x2t = x2t;
	// }
	//
	// /**
	// * @return the y2t
	// */
	// public static int getY2t() {
	// return y2t;
	// }
	//
	// /**
	// * @param y2t the y2t to set
	// */
	// public static void setY2t(int y2t) {
	// MazeDisplay.y2t = y2t;
	// }
	//
	// /**
	// * @return the width
	// */
	// public int getWidth() {
	// return width;
	// }
	//
	// /**
	// * @param width the width to set
	// */
	// public static void setWidth(int width) {
	// MazeDisplay.width = width;
	// }
	//
	// /**
	// * @return the height
	// */
	// public int getHeight() {
	// return height;
	// }
	//
	// /**
	// * @param height the height to set
	// */
	// public static void setHeight(int height) {
	// MazeDisplay.height = height;
	// }
	//
	// /**
	// * @return the cellSize
	// */
	// public static int getCellSize() {
	// return cellSize;
	// }
	//
	// /**
	// * @param cellSize the cellSize to set
	// */
	// public static void setCellSize(int cellSize) {
	// MazeDisplay.cellSize = cellSize;
	// }
	//
	// /**
	// * @return the oldCellSize
	// */
	// public static int getOldCellSize() {
	// return oldCellSize;
	// }
	//
	// /**
	// * @param oldCellSize the oldCellSize to set
	// */
	// public static void setOldCellSize(int oldCellSize) {
	// MazeDisplay.oldCellSize = oldCellSize;
	// }
	//
	// /**
	// * @return the resizeY
	// */
	// public static int getResizeY() {
	// return resizeY;
	// }
	//
	// /**
	// * @param resizeY the resizeY to set
	// */
	// public static void setResizeY(int resizeY) {
	// MazeDisplay.resizeY = resizeY;
	// }
	//
	// /**
	// * @return the resizeX
	// */
	// public static int getResizeX() {
	// return resizeX;
	// }
	//
	// /**
	// * @param resizeX the resizeX to set
	// */
	// public static void setResizeX(int resizeX) {
	// MazeDisplay.resizeX = resizeX;
	// }
	//
	// /**
	// * @return the resizePlayer
	// */
	// public boolean isResizePlayer() {
	// return resizePlayer;
	// }
	//
	// /**
	// * @param resizePlayer the resizePlayer to set
	// */
	// public void setResizePlayer(boolean resizePlayer) {
	// this.resizePlayer = resizePlayer;
	// }
	//
	// /**
	// * @return the startPlayer
	// */
	// public boolean isStartPlayer() {
	// return startPlayer;
	// }
	//
	// /**
	// * @param startPlayer the startPlayer to set
	// */
	// public void setStartPlayer(boolean startPlayer) {
	// this.startPlayer = startPlayer;
	// }
	//
	// /**
	// * @return the setBackX
	// */
	// public int getSetBackX() {
	// return setBackX;
	// }
	//
	// /**
	// * @param setBackX the setBackX to set
	// */
	// public void setSetBackX(int setBackX) {
	// this.setBackX = setBackX;
	// }
	//
	// /**
	// * @return the setBackY
	// */
	// public int getSetBackY() {
	// return setBackY;
	// }
	//
	// /**
	// * @param setBackY the setBackY to set
	// */
	// public void setSetBackY(int setBackY) {
	// this.setBackY = setBackY;
	// }
	//
	// /**
	// * @return the mover
	// */
	// public boolean isMover() {
	// return mover;
	// }
	//
	// /**
	// * @param mover the mover to set
	// */
	// public void setMover(boolean mover) {
	// this.mover = mover;
	// }
	//
	// /**
	// * @return the drawLine
	// */
	// public static boolean isDrawLine() {
	// return drawLine;
	// }
	//
	// /**
	// * @param drawLine the drawLine to set
	// */
	// public void setDrawLine(boolean drawLine) {
	// MazeDisplay.drawLine = drawLine;
	// }
	//
	// /**
	// * @return the sp
	// */
	// public ShortestPath getSp() {
	// return sp;
	// }
	//
	// /**
	// * @param sp the sp to set
	// */
	// public void setSp(ShortestPath sp) {
	// this.sp = sp;
	// }
	//
	// /**
	// * @return the leftPath
	// */
	// public boolean isLeftPath() {
	// return leftPath;
	// }
	//
	// /**
	// * @param leftPath the leftPath to set
	// */
	// public void setLeftPath(boolean leftPath) {
	// this.leftPath = leftPath;
	// }
	//
	// /**
	// * @return the drawPointX
	// */
	// public ArrayList<Integer> getDrawPointX() {
	// return drawPointX;
	// }
	//
	// /**
	// * @param drawPointX the drawPointX to set
	// */
	// public void setDrawPointX(ArrayList<Integer> drawPointX) {
	// this.drawPointX = drawPointX;
	// }
	//
	// /**
	// * @return the drawPointY
	// */
	// public ArrayList<Integer> getDrawPointY() {
	// return drawPointY;
	// }
	//
	// /**
	// * @param drawPointY the drawPointY to set
	// */
	// public void setDrawPointY(ArrayList<Integer> drawPointY) {
	// this.drawPointY = drawPointY;
	// }
	//
	// /**
	// * @return the drawOldX
	// */
	// public ArrayList<Integer> getDrawOldX() {
	// return drawOldX;
	// }
	//
	// /**
	// * @param drawOldX the drawOldX to set
	// */
	// public void setDrawOldX(ArrayList<Integer> drawOldX) {
	// this.drawOldX = drawOldX;
	// }
	//
	// /**
	// * @return the drawOldY
	// */
	// public ArrayList<Integer> getDrawOldY() {
	// return MazeDisplay.drawOldY;
	// }
	//
	// /**
	// * @param drawOldY the drawOldY to set
	// */
	// public void setDrawOldY(ArrayList<Integer> drawOldY) {
	// MazeDisplay.drawOldY = drawOldY;
	// }
	//
	// /**
	// * @return the oldResizeX
	// */
	// public static int getOldResizeX() {
	// return MazeDisplay.oldResizeX;
	// }
	//
	// /**
	// * @param oldResizeX the oldResizeX to set
	// */
	// public static void setOldResizeX(int oldResizeX) {
	// MazeDisplay.oldResizeX = oldResizeX;
	// }
	//
	// /**
	// * @return the oldResizeY
	// */
	// public static int getOldResizeY() {
	// return MazeDisplay.oldResizeY;
	// }
	//
	// /**
	// * @param oldResizeY the oldResizeY to set
	// */
	// public static void setOldResizeY(int oldResizeY) {
	// MazeDisplay.oldResizeY = oldResizeY;
	// }
	//
	// /**
	// * @return the backDone
	// */
	// public static boolean isBackDone() {
	// return MazeDisplay.backDone;
	// }
	//
	// /**
	// * @param backDone the backDone to set
	// */
	// public static void setBackDone(boolean backDone) {
	// MazeDisplay.backDone = backDone;
	// }
	//
	// /**
	// * @return the afterMove
	// */
	// public static boolean isAfterMove() {
	// return MazeDisplay.afterMove;
	// }
	//
	// /**
	// * @param afterMove the afterMove to set
	// */
	// public static void setAfterMove(boolean afterMove) {
	// MazeDisplay.afterMove = afterMove;
	// }
	//
	// /**
	// * @return the oneStepBackOrRestartInAction
	// */
	// public static boolean isOneStepBackOrRestartInAction() {
	// return MazeDisplay.oneStepBackOrRestartInAction;
	// }
	//
	// /**
	// * @param oneStepBackOrRestartInAction the oneStepBackOrRestartInAction to set
	// */
	// public static void setOneStepBackOrRestartInAction(boolean
	// oneStepBackOrRestartInAction) {
	// MazeDisplay.oneStepBackOrRestartInAction = oneStepBackOrRestartInAction;
	// }
	//
	//
}