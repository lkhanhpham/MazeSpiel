package Spiel4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import team07.main.MyPanel;

public class Display extends JPanel implements ComponentListener, MouseMotionListener, MouseListener, KeyListener {

	private int columnCount;
	private int rowCount;
	private Rectangle[][] cells;
//	private Rectangle[][] celles;

//	private Point selectedCell;
	private int width;
	private int height;

	private int cellWidth;
	private int cellHeight;

	private int xOffset;
	private int yOffset;

	private BufferedImage imgShip, imgEarth, imgUniverse, imgSun;

	private ArrayList<ArrayList<Integer>> indicies;

	GamePanel gp;

	private String difficulty;
	private int level;

	private Point startPoint;
	private ArrayList<Point> startPoints;
	private ArrayList<Point> earthPoints;

	private Point earthCorner;
	private Point previousPoint;
	private Items currentDragged;
	private Items lastDragged;
	private Point mousPT;
	private int moveX;
	private int moveY;

	private Grid grid;

	private int sunX;
	private int sunY;

	private boolean pause = false;
	private boolean start = false;

	private boolean clickOnShip;
	private int clickedShipNextEarthX;
	private int clickedShipNextEarthY;

	public Display(String difficulty) {
		if (difficulty == null) {
			this.difficulty = "Training";
		} else {
			this.difficulty = difficulty;
		}
//		this.difficulty = "Training";

		level = 1;
		grid = new Grid(this.difficulty);
		this.rowCount = grid.getRowCount();
		this.columnCount = grid.getColumnCount();
//		cells = new Rectangle[rowCount][columnCount];
		this.cells = grid.getCells();

		earthCorner = new Point(0, 0);
		startPoint = new Point();

		sunX = 0;
		sunY = 12;
//
		try {
			imgShip = ImageIO.read(getClass().getResource("/resources/Spiel 4/ship.png"));
			imgEarth = ImageIO.read(getClass().getResource("/resources/Spiel 4/earth.png"));
			imgUniverse = ImageIO.read(getClass().getResource("/resources/Spiel 4/universe3.png"));
			imgSun = ImageIO.read(getClass().getResource("/resources/Spiel 4/sun.png"));
		} catch (IOException ex) {
			System.out.println("file not found");
		}

		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
//
//		indicies = positionIndicies(level);
////		MouseAdapter mouseHandler;
////    mouseHandler = new MouseAdapter() {
////        @Override
////        public void mouseMoved(MouseEvent e) {
////            Point point = e.getPoint();
////
////            int width = getWidth();
////            int height = getHeight();
////
////            int cellWidth = width / columnCount;
////            int cellHeight = height / rowCount;
////
////            selectedCell = null;
////            if (e.getX() >= xOffset && e.getY() >= yOffset) {
////
////                int column = (e.getX() - xOffset) / cellWidth;
////                int row = (e.getY() - yOffset) / cellHeight;
////
////                if (column >= 0 && row >= 0 && column < columnCount && row < rowCount) {
////
////                    selectedCell = new Point(column, row);
////
////                }
////
////            }
////            repaint();
////
////        }
////    };
//		addMouseMotionListener(mouseHandler);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		});

//		if (pause) {
//			removeMouseListener(this);
//			removeMouseMotionListener(this);
//		}

	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	public void doDrawing(Graphics g) {
//		System.out.println("paint");
		Graphics2D g2d = (Graphics2D) g.create();
		
		this.setFocusable(true);
		this.requestFocusInWindow();

		width = Spiel4.getGamePanelWidth();
		height = Spiel4.getGamePanelHeight();

		cellWidth = width / columnCount;
//		int cellHeight = (height - (2 * topOffset)) / rowCount;
		cellHeight = height / rowCount;

		xOffset = (width - (columnCount * cellWidth)) / 2;
		yOffset = (height - (rowCount * cellHeight)) / 2;

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				Rectangle cell = new Rectangle(xOffset + (col * cellWidth), yOffset + (row * cellHeight), cellWidth,
						cellHeight);
				cells[row][col] = cell;
			}
		}

		initializeStartPositions(g);

		g2d.drawImage(imgUniverse, 0, 0, width, height, null);

//		Eaarth 1 startPoint----------------------
//		startPoint.setLocation(cells.get(0).getX(), cells.get(0).getY());
//		startPoint.setLocation(celles[0][0].getX(), celles[0][0].getY());

//		ArrayList<ArrayList<Integer>> positions = findXandY2(celles, indicies);

		printShips(g);
//		if (selectedCell != null) {
//
//			int index = selectedCell.x + (selectedCell.y * columnCount);
//			Rectangle cell = cells.get(index);
//			g2d.setColor(Color.BLUE);
//			g2d.fill(cell);
//
//		}

		g2d.setColor(Color.black);
		for (int i = 0; i < grid.getGrid()[0].length; i++) {
			g2d.fillRect((int) cells[0][i].getX(), (int) cells[0][i].getY(), cellWidth, cellHeight);
		}
		
		if (clickOnShip) {
			paintClickedShip(g2d);
		}

		printEarths(g);

		g2d.setColor(Color.GRAY);
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				g2d.draw(cells[i][j]);
			}
		}

		printSun(g);

		printLastEarth(g);

		if (pause) {

			removeMouseListener(this);
			removeMouseMotionListener(this);
			g2d.setColor(Color.white);
			g2d.setFont(new Font("Times New Roman", Font.PLAIN, width / 5));
			g2d.drawString("PAUSE", width / 5, height / 2);
		} else {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		g2d.dispose();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (start) {
			doDrawing(g);
		}
		
	}

	public void paintClickedShip(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(clickedShipNextEarthX, clickedShipNextEarthY, cellWidth, cellHeight);
		
	}

	public void printSun(Graphics g2d) {

		g2d.drawImage(imgSun, (int) cells[sunX][sunY].getX(), (int) cells[sunX][sunY].getY(), cellWidth, cellHeight,
				null);

	}

	public void printLastEarth(Graphics g2d) {
		if (currentDragged != null) {
			g2d.drawImage(imgEarth, moveX - (cellWidth / 2), moveY - (cellHeight / 2), cellWidth, cellHeight, null);
		}

	}

	public void printShips(Graphics g2d) {
		for (int j = 0; j < grid.getGrid().length; j++) {
			for (int i = 0; i < grid.getGrid()[0].length; i++) {
				if (grid.getGrid()[j][i] != null && grid.getGrid()[j][i].getType() == "Ship") {
					g2d.setColor(Color.white);
//					g2d.fillRect((int) cells[j][i].getX(), (int) cells[j][i].getY(), cellWidth, cellHeight);
					g2d.drawImage(imgShip, (int) cells[j][i].getX(), (int) cells[j][i].getY(), cellWidth, cellHeight,
							null);
				}

			}

		}
	}

	public void printEarths(Graphics g2d) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < grid.getGrid()[0].length; j++) {
				if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Earth" && i > 0) {
					g2d.setColor(new Color(171, 219, 227));
					g2d.fillRect((int) cells[i][j].getX(), (int) cells[i][j].getY(), cellWidth, cellHeight);
					g2d.drawImage(imgEarth, (int) cells[i][j].getX(), (int) cells[i][j].getY(), cellWidth, cellHeight,
							null);
				} else if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Earth") {
					g2d.drawImage(imgEarth, (int) cells[i][j].getX(), (int) cells[i][j].getY(), cellWidth, cellHeight,
							null);
				}

			}
		}

	}

	public void initializeStartPositions(Graphics g) {
		startPoints = new ArrayList<>();
		for (int i = 0; i < grid.getStartPositions().get(0).size(); i++) {
			Point startPoint = new Point(
					(int) cells[grid.getStartPositions().get(0).get(i)][grid.getStartPositions().get(1).get(i)].getX(),
					(int) cells[grid.getStartPositions().get(0).get(i)][grid.getStartPositions().get(1).get(i)].getY());
			startPoints.add(startPoint);
		}

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

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

	@Override
	public void mouseClicked(MouseEvent e) {
		clickOnShip = false;
		Point currentClicked = e.getPoint();
		Items nextEarth = null;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < grid.getGrid()[0].length; j++) {
				if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Ship") {
					if (currentClicked.x >= cells[i][j].getX() && currentClicked.x < cells[i][j].getX() + cellWidth
							&& currentClicked.y >= cells[i][j].getY()
							&& currentClicked.y < cells[i][j].getY() + cellHeight) {
						nextEarth = grid.findNextEarth(grid.getGrid()[i][j]);
						clickedShipNextEarthX = nextEarth.getRow();
						clickedShipNextEarthY = nextEarth.getCol();
						clickOnShip = true;
					}
				}
			}
		}
		repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		previousPoint = e.getPoint();
//		System.out.println("pressed");

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < grid.getGrid()[0].length; j++) {
				if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Earth") {
					if (previousPoint.x >= cells[i][j].getX() && previousPoint.x < cells[i][j].getX() + cellWidth
							&& previousPoint.y >= cells[i][j].getY()
							&& previousPoint.y < cells[i][j].getY() + cellHeight) {
						Items currentItems = grid.getGrid()[i][j];
						if (currentDragged == null) {
							currentDragged = currentItems;
							grid.getGrid()[i][j] = null;
						}
					}
				}

			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point currentPoint = e.getPoint();
//		System.out.println(currentPoint.x);
//		System.out.println(currentPoint.y);
//		boolean onShip = false;
//		int a = 0;
//		int b = 0;

		if (currentDragged != null) {
			gridloop: for (int i = 0; i < grid.getGrid().length; i++) {
				for (int j = 0; j < grid.getGrid()[0].length; j++) {
					if (currentPoint.getX() >= cells[i][j].getX()
							&& currentPoint.getX() < cells[i][j].getX() + cellWidth
							&& currentPoint.getY() >= cells[i][j].getY()
							&& currentPoint.y < cells[i][j].getY() + cellHeight) {
						if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Ship") {
//							System.out.println("done");
							if (currentDragged.isOnShip()) {
								grid.getGrid()[currentDragged.getRow()][currentDragged.getCol()] = new Items(
										currentDragged.getRow(), currentDragged.getCol(), "Ship");
							}
							grid.getGrid()[i][j] = null;
							grid.getGrid()[i][j] = currentDragged;
							grid.getGrid()[i][j].setRow(i);
							grid.getGrid()[i][j].setCol(j);
							grid.getGrid()[i][j].setOnShip(true);
							currentDragged = null;
//							System.out.println(grid.getGrid()[i][j].isOnShip());
							break gridloop;
//							moveX = (int) cells[i][j].getX();
//							moveY = (int) cells[i][j].getY();

						} else {
							for (int k = 0; k < startPoints.size(); k++) {
								int x = grid.getStartPositions().get(0).get(k);
								int y = grid.getStartPositions().get(1).get(k);
								int currentDraggedID = grid.getStartPositions().get(2).get(k);
								if (grid.getGrid()[x][y] == null && currentDraggedID == currentDragged.getId()) {
//									System.out.println("reset");
									int a = currentDragged.getRow();
									int b = currentDragged.getCol();
									grid.getGrid()[a][b] = new Items(a, b, "Ship");
//									System.out.println(grid.getGrid()[i][j].getType());
//									a = i;
//									b = j;
									moveX = (int) cells[x][y].getX();
									moveY = (int) cells[x][y].getY();
									grid.getGrid()[x][y] = currentDragged;

									currentDragged.setOnShip(false);

								}
							}
						}
					} else {

					}

				}
			}
		}
		currentDragged = null;
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point currentPoint = e.getPoint();

		if (currentDragged != null) {
			lastDragged = currentDragged;
			int dx = (int) currentPoint.getX() - previousPoint.x;
			int dy = (int) currentPoint.getY() - previousPoint.y;
			moveX = (int) currentPoint.getX() + dx;
			moveY = (int) currentPoint.getY() + dy;
		}

//		for (int i = 0; i < cells.length; i++) {
//			for (int j = 0; j < grid.getGrid()[0].length; j++) {
//				if (grid.getGrid()[i][j] != null && grid.getGrid()[i][j].getType() == "Earth") {
//					if (currentPoint.x >= cells[i][j].getX() && currentPoint.x < cells[i][j].getX()+cellWidth && currentPoint.y >= cells[i][j].getY() && currentPoint.y < cells[i][j].getY()+cellHeight) {
//						earthCorner.translate((int) (currentPoint.getX() - previousPoint.getX()),
//								(int) (currentPoint.getY() - previousPoint.getY()));
//					}
//				}
//
//			}
//		}
		previousPoint = currentPoint;
		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getSunX() {
		return sunX;
	}

	public void setSunX(int sunX) {
		this.sunX = sunX;
	}

	public int getSunY() {
		return sunY;
	}

	public void setSunY(int sunY) {
		this.sunY = sunY;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
			if (sunX > 0) {
				sunX--;
			}
		}
		if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {
			if (sunY > 0) {
				sunY--;
			}
			
		}
		if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
			if (sunY < cells.length-1) {
				sunY++;
			}
		}
		if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
			if (sunX < cells.length-1) {
				sunX++;
			}
		}
		repaint();
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
	

}
