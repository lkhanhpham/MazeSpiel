package Spiel1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import player.Player;
import team07.main.MyPanel;
import team07.main.RightPanel;

public class AlgorithmDisplay extends JPanel
		implements KeyListener, ComponentListener, MouseListener, MouseMotionListener {

	private Knapsack knapSack;
	private int width, height;
	private int cellSize;
	private int offSetX, offSetY;
	private int pointX, pointY, xVal, yVal;
	private int whatDoor;

	private Point point_armor, point_bow, point_sword, point_golden_apple, point_shovel, point_axe, point_pickaxe,
			point_meat, point_flint;
	private Point prevPoint_armor, prevPoint_bow, prevPoint_sword, prevPoint_golden_apple, prevPoint_shovel,
			prevPoint_axe, prevPoint_pickaxe, prevPoint_meat, prevPoint_flint;
	private int resizeX, resizeY;
	private boolean schwer = false, mittel = false, training = false;
	private boolean pause = false, verloren = false, abgabe = false, displayValue = false, doClick = false;

	private ArrayList<Integer> propPosX;
	private ArrayList<Integer> propPosY;
	private ArrayList<Integer> propOrder;
	private ArrayList<Integer> openDoorX1;
	private ArrayList<Integer> openDoorY1;
	private ArrayList<Integer> openDoorX2;
	private ArrayList<Integer> openDoorY2;
	private ArrayList<Integer> doorOrder;
	
	private Graphics2D g2dRaster, g2dRand, g2dV, g2dW, g2dSchranke, g2dValue;

	private boolean isResizing, drawProps, resizePlayer = false, resizeProps = false, addPositions;
	private boolean dragableArmor = false, dragableBow = false, dragableFlint = false, dragableApple = false,
			dragableSword = false, dragableShovel = false, dragableMeat = false, dragableAxe = false,
			dragablePickaxe = false;
	private int drawCounter = 0;

	private boolean vanishArmor = false, vanishBow = false, vanishFlint = false, vanishApple = false,
			vanishMeat = false, vanishShovel = false, vanishPickaxe = false, vanishSword = false, vanishAxe = false;

	private boolean showCurrentValue = false;
	private int currentValue = 0, currentWeight = 0, maximumValue, maximumWeight, maximumWeight_angepasst;

	private Props prop;

	public AlgorithmDisplay(boolean schwer, boolean mittel, boolean training) {
		newKnapsack();

		propPosX = new ArrayList<Integer>();
		propPosY = new ArrayList<Integer>();
		propOrder = new ArrayList<Integer>();
		openDoorX1 = new ArrayList<Integer>();
		openDoorY1 = new ArrayList<Integer>();
		openDoorX2 = new ArrayList<Integer>();
		openDoorY2 = new ArrayList<Integer>();
		doorOrder = new ArrayList<Integer>();

		point_armor = new Point();
		point_bow = new Point();
		point_sword = new Point();
		point_golden_apple = new Point();
		point_shovel = new Point();
		point_axe = new Point();
		point_pickaxe = new Point();
		point_meat = new Point();
		point_flint = new Point();

		prevPoint_armor = new Point();
		prevPoint_bow = new Point();
		prevPoint_sword = new Point();
		prevPoint_golden_apple = new Point();
		prevPoint_shovel = new Point();
		prevPoint_axe = new Point();
		prevPoint_pickaxe = new Point();
		prevPoint_meat = new Point();
		prevPoint_flint = new Point();

		cellSize = 16;
		xVal = 5;
		yVal = 5;
		drawProps = true;
		addPositions = true;

		resizeX = 0;
		resizeY = 0;

		this.schwer = schwer;
		this.mittel = mittel;
		this.training = training;
		
		if (this.training == true) {
			maximumWeight_angepasst = (int) (maximumWeight * 0.5);
		}
		
		if (this.mittel == true) {
			maximumWeight_angepasst = (int) (maximumWeight * 0.7);
		}
		
		if (this.schwer == true) {
			maximumWeight_angepasst = (int) (maximumWeight * 0.9);
		}
		addKeyListener(this);
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void doDrawing(Graphics g) {
		
		setBackground(Color.WHITE);
		
		if (this.hasFocus() != true && pause == false) {
			this.grabFocus();
		}
		
		if (pause == true) {
			removeMouseListener(this);
			removeMouseMotionListener(this);
		}
		
		if (drawCounter < 5) {
			drawCounter++;
		}

		/*
		 * Zeichnet Items
		 */
		if (drawProps == true) {
			prop = new Props(this);

			// Gewicht und Wert von Props

			if (this.training == true) {
				addValue_Training();
				addWeight_Training();
				maximumValue = knapSack.getPerfectValue_training();
				maximumWeight = knapSack.getGesamtGewicht();
			}

			if (this.mittel == true) {
				addValue_Mittel();
				addWeight_Mittel();
				maximumValue = knapSack.getPerfectValue_mittel();
				maximumWeight = knapSack.getGesamtGewicht();
			}

			if (this.schwer == true) {
				addValue_Schwer();
				addWeight_Schwer();
				maximumValue = knapSack.getPerfectValue_schwer();
				maximumWeight = knapSack.getGesamtGewicht();
			}
		}

		width = MyPanel.getLeftPanelWidth();
		height = MyPanel.getLeftPanelHeight();

		if (width < height) { // Passt cellSize an wenn Hoehe des Panels veraendert wird
			cellSize = width / ((xVal + 2) * 2);
		}

		if (width > height) { // Passt cellSize an wenn Breite des Panels veraendert wird
			cellSize = height / ((yVal + 2) * 2);
		}

		if (isResizing == true) {
			offSetX = width / 2 - (cellSize * xVal) + cellSize;
			offSetY = height / 2 - (cellSize * yVal);

			if (addPositions == true) {
				addProps();
				Collections.shuffle(propOrder);
				initOpenDoor();

				if (this.training == true) {
					addPropCords_Training();
				}

				if (this.mittel == true) {
					addPropCords_Mittel();
				}

				if (this.schwer == true) {
					addPropCords_Schwer();
				}

				addPositions = false;
			}

			if (resizeProps == true) {
				resizeProps();

				if (this.training == true) {
					addPropCords_Training();
				}

				if (this.mittel == true) {
					addPropCords_Mittel();
				}

				if (this.schwer == true) {
					addPropCords_Schwer();
				}

				resizeProps = false;
			}

			if (vanishArmor == true) {
				vanishArmor();
			}

			if (vanishBow == true) {
				vanishBow();
			}

			if (vanishFlint == true) {
				vanishFlint();
			}

			if (vanishApple == true) {
				vanishApple();
			}

			if (vanishMeat == true) {
				vanishMeat();
			}

			if (vanishShovel == true) {
				vanishShovel();
			}

			if (vanishPickaxe == true) {
				vanishPickaxe();
			}

			if (vanishSword == true) {
				vanishSword();
			}

			if (vanishAxe == true) {
				vanishAxe();
			}

			openDoor();
			resizePlayer = true;
			prop = new Props(this);
			isResizing = false;
			repaint();
		}

		g2dRaster = (Graphics2D) g;
		g2dRand = (Graphics2D) g;
		g2dV = (Graphics2D) g;
		g2dW = (Graphics2D) g;
		g2dSchranke = (Graphics2D) g;
		g2dValue = (Graphics2D) g;

		// -----Raster---------------------------------------------------------------------------------------------
		g2dRaster.setColor(Color.LIGHT_GRAY); // setzt Farbe vom Hintergrund (Karierter Hintergrund)

		for (int i = 0; i < (cellSize * xVal + cellSize) * 2; i += cellSize * 2) {
			for (int j = 0; j < (cellSize * yVal + cellSize) * 2; j += cellSize * 2) {
				g2dRaster.drawRect(i + offSetX - cellSize, j + offSetY - cellSize, cellSize * 2, cellSize * 2);
			}
		}

		g2dRand.setColor(Color.BLACK);
		g2dRand.setStroke(new BasicStroke(2)); // Line Thickness
		g2dRand.drawRect(offSetX - cellSize, offSetY - cellSize, cellSize * 12, cellSize * 12);

		if (resizePlayer == true) {
			pointX = offSetX + (cellSize * xVal) - cellSize;
			pointY = offSetY + (cellSize * yVal) - cellSize;
			pointX += (resizeX * cellSize * 2);
			pointY += (resizeY * cellSize * 2);
			resizePlayer = false;
		}

		prop.setXProp(pointX);
		prop.setYProp(pointY);

		if (drawCounter > 2) {
			
			g.drawImage(prop.getKnapsack(), prop.getXProp() - cellSize, prop.getYProp() - cellSize, this);

			g2dRaster.setColor(Color.WHITE);
			g2dRaster.setStroke(new BasicStroke(2)); // Line Thickness
			g2dRaster.drawLine(openDoorX1.get(doorOrder.get(0)), openDoorY1.get(doorOrder.get(0)),
					openDoorX2.get(doorOrder.get(0)), openDoorY2.get(doorOrder.get(0)));

			if (training == true) {
				
				g2dSchranke.setColor(Color.BLACK);
				g2dSchranke.setFont(new Font("Arial", Font.PLAIN, 15));
				g2dSchranke.drawString("Maximalgewicht: " + Integer.toString((int) (maximumWeight)), width - cellSize * 4,
						 cellSize - (cellSize / 2));
				
				if (displayValue == true) {
					g2dValue.setFont(new Font("Arial", Font.PLAIN, 15));
					g2dValue.setColor(Color.BLACK);
					g2dValue.drawString("Maximaler erreichbarer Wert: " + Integer.toString(maximumValue), 0,
							cellSize - (cellSize / 2));
				}
				
				g.drawImage(prop.getArmor(), propPosX.get(propOrder.get(0)) + cellSize / 2,
						propPosY.get(propOrder.get(0)) + cellSize / 2, this);
				g.drawImage(prop.getMeat(), propPosX.get(propOrder.get(1)) + cellSize / 2,
						propPosY.get(propOrder.get(1)) + cellSize / 2, this);
				g.drawImage(prop.getBow(), propPosX.get(propOrder.get(2)) + cellSize / 2,
						propPosY.get(propOrder.get(2)) + cellSize / 2, this);
				g.drawImage(prop.getFlint(), propPosX.get(propOrder.get(3)) + cellSize / 2,
						propPosY.get(propOrder.get(3)) + cellSize / 2, this);
				g.drawImage(prop.getGolden_apple(), propPosX.get(propOrder.get(4)) + cellSize / 2,
						propPosY.get(propOrder.get(4)) + cellSize / 2, this);

				g2dV.setColor(Color.RED);
				g2dV.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dV.drawString("W: " + Integer.toString(prop.getWert_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize * 2) - 2);

				g2dW.setColor(Color.BLUE);
				g2dW.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize / 2));
			}

			if (mittel == true) {
				
				g2dSchranke.setColor(Color.BLACK);
				g2dSchranke.setFont(new Font("Arial", Font.PLAIN, 15));
				g2dSchranke.drawString("Maximalgewicht: " + Integer.toString((int) (maximumWeight)), width - cellSize * 4,
						 cellSize - (cellSize / 2));
				
				if (displayValue == true) {
					g2dValue.setFont(new Font("Arial", Font.PLAIN, 15));
					g2dValue.setColor(Color.BLACK);
					g2dValue.drawString("Maximaler erreichbarer Wert: " + Integer.toString(maximumValue), 0,
							cellSize - (cellSize / 2));
				}
				
				g.drawImage(prop.getArmor(), propPosX.get(propOrder.get(0)) + cellSize / 2,
						propPosY.get(propOrder.get(0)) + cellSize / 2, this);
				g.drawImage(prop.getMeat(), propPosX.get(propOrder.get(1)) + cellSize / 2,
						propPosY.get(propOrder.get(1)) + cellSize / 2, this);
				g.drawImage(prop.getBow(), propPosX.get(propOrder.get(2)) + cellSize / 2,
						propPosY.get(propOrder.get(2)) + cellSize / 2, this);
				g.drawImage(prop.getFlint(), propPosX.get(propOrder.get(3)) + cellSize / 2,
						propPosY.get(propOrder.get(3)) + cellSize / 2, this);
				g.drawImage(prop.getGolden_apple(), propPosX.get(propOrder.get(4)) + cellSize / 2,
						propPosY.get(propOrder.get(4)) + cellSize / 2, this);
				g.drawImage(prop.getShovel(), propPosX.get(propOrder.get(5)) + cellSize / 2,
						propPosY.get(propOrder.get(5)) + cellSize / 2, this);
				g.drawImage(prop.getPickaxe(), propPosX.get(propOrder.get(6)) + cellSize / 2,
						propPosY.get(propOrder.get(6)) + cellSize / 2, this);

				g2dV.setColor(Color.RED);
				g2dV.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dV.drawString("W: " + Integer.toString(prop.getWert_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_shovel()), propPosX.get(propOrder.get(5)) + 2,
						propPosY.get(propOrder.get(5)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_pickaxe()), propPosX.get(propOrder.get(6)) + 2,
						propPosY.get(propOrder.get(6)) + (cellSize * 2) - 2);

				g2dW.setColor(Color.BLUE);
				g2dW.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_shovel()), propPosX.get(propOrder.get(5)) + 2,
						propPosY.get(propOrder.get(5)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_pickaxe()), propPosX.get(propOrder.get(6)) + 2,
						propPosY.get(propOrder.get(6)) + (cellSize / 2));
			}

			if (schwer == true) {
				
				g2dSchranke.setColor(Color.BLACK);
				g2dSchranke.setFont(new Font("Arial", Font.PLAIN, 15));
				g2dSchranke.drawString("Maximalgewicht: " + Integer.toString((int) (maximumWeight)), width - cellSize * 4,
						 cellSize - (cellSize / 2));
				
				if (displayValue == true) {
					g2dValue.setFont(new Font("Arial", Font.PLAIN, 15));
					g2dValue.setColor(Color.BLACK);
					g2dValue.drawString("Maximaler erreichbarer Wert: " + Integer.toString(maximumValue), 0,
							cellSize - (cellSize / 2));
				}
				
				g.drawImage(prop.getArmor(), propPosX.get(propOrder.get(0)) + cellSize / 2,
						propPosY.get(propOrder.get(0)) + cellSize / 2, this);
				g.drawImage(prop.getMeat(), propPosX.get(propOrder.get(1)) + cellSize / 2,
						propPosY.get(propOrder.get(1)) + cellSize / 2, this);
				g.drawImage(prop.getBow(), propPosX.get(propOrder.get(2)) + cellSize / 2,
						propPosY.get(propOrder.get(2)) + cellSize / 2, this);
				g.drawImage(prop.getFlint(), propPosX.get(propOrder.get(3)) + cellSize / 2,
						propPosY.get(propOrder.get(3)) + cellSize / 2, this);
				g.drawImage(prop.getGolden_apple(), propPosX.get(propOrder.get(4)) + cellSize / 2,
						propPosY.get(propOrder.get(4)) + cellSize / 2, this);
				g.drawImage(prop.getShovel(), propPosX.get(propOrder.get(5)) + cellSize / 2,
						propPosY.get(propOrder.get(5)) + cellSize / 2, this);
				g.drawImage(prop.getPickaxe(), propPosX.get(propOrder.get(6)) + cellSize / 2,
						propPosY.get(propOrder.get(6)) + cellSize / 2, this);
				g.drawImage(prop.getSword(), propPosX.get(propOrder.get(7)) + cellSize / 2,
						propPosY.get(propOrder.get(7)) + cellSize / 2, this);
				g.drawImage(prop.getAxe(), propPosX.get(propOrder.get(8)) + cellSize / 2,
						propPosY.get(propOrder.get(8)) + cellSize / 2, this);

				g2dV.setColor(Color.RED);
				g2dV.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dV.drawString("W: " + Integer.toString(prop.getWert_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_shovel()), propPosX.get(propOrder.get(5)) + 2,
						propPosY.get(propOrder.get(5)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_pickaxe()), propPosX.get(propOrder.get(6)) + 2,
						propPosY.get(propOrder.get(6)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_sword()), propPosX.get(propOrder.get(7)) + 2,
						propPosY.get(propOrder.get(7)) + (cellSize * 2) - 2);
				g2dV.drawString("W: " + Integer.toString(prop.getWert_axe()), propPosX.get(propOrder.get(8)) + 2,
						propPosY.get(propOrder.get(8)) + (cellSize * 2) - 2);

				g2dW.setColor(Color.BLUE);
				g2dW.setFont(new Font("Arial", Font.PLAIN, 10));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_armor()), propPosX.get(propOrder.get(0)) + 2,
						propPosY.get(propOrder.get(0)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_meat()), propPosX.get(propOrder.get(1)) + 2,
						propPosY.get(propOrder.get(1)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_bow()), propPosX.get(propOrder.get(2)) + 2,
						propPosY.get(propOrder.get(2)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_flint()), propPosX.get(propOrder.get(3)) + 2,
						propPosY.get(propOrder.get(3)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_golden_apple()),
						propPosX.get(propOrder.get(4)) + 2, propPosY.get(propOrder.get(4)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_shovel()), propPosX.get(propOrder.get(5)) + 2,
						propPosY.get(propOrder.get(5)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_pickaxe()), propPosX.get(propOrder.get(6)) + 2,
						propPosY.get(propOrder.get(6)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_sword()), propPosX.get(propOrder.get(7)) + 2,
						propPosY.get(propOrder.get(7)) + (cellSize / 2));
				g2dW.drawString("G: " + Integer.toString(prop.getGewicht_axe()), propPosX.get(propOrder.get(8)) + 2,
						propPosY.get(propOrder.get(8)) + (cellSize / 2));
			}
		}
		g2dRand.setColor(Color.BLACK);
		if (showCurrentValue == true) {
			g2dRand.drawString("Summe der Werte aller Gegenstaende im Koffer: " + Integer.toString(currentValue), offSetX - cellSize,
					offSetY - cellSize - 20);
			g2dRand.drawString("Verbleibender Platz im Koffer: " + Integer.toString(maximumWeight - currentWeight), offSetX - cellSize,
					offSetY - cellSize - 30);
			repaint();
		}
		
		if (pause == true && verloren == false) {
			g2dRand.setFont(new Font("Arial", Font.PLAIN, 25));
			g2dRand.drawString("Spiel ist pausiert", offSetX + cellSize * 3, offSetY - cellSize - (cellSize / 2));
		}
		
		if (prop.getXProp() - offSetX > width || prop.getXProp() + (offSetX / 2) < 0 || prop.getYProp() - (offSetY / 4) > height || prop.getYProp() + (offSetY / 4) < 0) {
			
			if (doClick == false) {
			abgabe = true;
			doClick = true;
			}
		}

		if (currentWeight > maximumWeight || verloren == true) {
			g2dRand.setFont(new Font("Arial", Font.PLAIN, 25));
			g2dRand.setColor(Color.RED);
			g2dRand.drawString("You Lost", offSetX + cellSize * 3, offSetY - cellSize - (cellSize / 2));
				g2dValue.setFont(new Font("Arial", Font.PLAIN, 15));
				g2dValue.setColor(Color.BLACK);
				g2dValue.drawString("Maximaler erreichbarer Wert: " + Integer.toString(maximumValue), 0,
						cellSize - (cellSize / 2));
			verloren = true;
			removeMouseListener(this);
			removeMouseMotionListener(this);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	public void addProps() {
		propOrder.add(0);
		propOrder.add(1);
		propOrder.add(2);
		propOrder.add(3);
		propOrder.add(4);
		propOrder.add(5);
		propOrder.add(6);
		propOrder.add(7);
		propOrder.add(8);
		propOrder.add(9);
		propOrder.add(10);
		propOrder.add(11);
		propOrder.add(12);
		propOrder.add(13);
		propOrder.add(14);
		propOrder.add(15);
		propOrder.add(16);
		propOrder.add(17);
		propOrder.add(18);
		propOrder.add(19);
		propOrder.add(20);
		propOrder.add(21);
		propOrder.add(22);
		propOrder.add(23);
		propOrder.add(24);
		propOrder.add(25);
		propOrder.add(26);
		propOrder.add(27);
		propOrder.add(28);
		propOrder.add(29);
		propOrder.add(30);
		propOrder.add(31);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 3);
		propPosX.add(offSetX + cellSize * 5);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 3);
		propPosX.add(offSetX + cellSize * 5);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 3);
		propPosX.add(offSetX + cellSize * 5);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosX.add(offSetX - cellSize);
		propPosX.add(offSetX + cellSize);
		propPosX.add(offSetX + cellSize * 3);
		propPosX.add(offSetX + cellSize * 5);
		propPosX.add(offSetX + cellSize * 7);
		propPosX.add(offSetX + cellSize * 9);

		propPosY.add(offSetY - cellSize);
		propPosY.add(offSetY - cellSize);
		propPosY.add(offSetY - cellSize);
		propPosY.add(offSetY - cellSize);
		propPosY.add(offSetY - cellSize);
		propPosY.add(offSetY - cellSize);

		propPosY.add(offSetY + cellSize);
		propPosY.add(offSetY + cellSize);
		propPosY.add(offSetY + cellSize);
		propPosY.add(offSetY + cellSize);
		propPosY.add(offSetY + cellSize);
		propPosY.add(offSetY + cellSize);

		propPosY.add(offSetY + cellSize * 3);
		propPosY.add(offSetY + cellSize * 3);
		propPosY.add(offSetY + cellSize * 3);
		propPosY.add(offSetY + cellSize * 3);

		propPosY.add(offSetY + cellSize * 5);
		propPosY.add(offSetY + cellSize * 5);
		propPosY.add(offSetY + cellSize * 5);
		propPosY.add(offSetY + cellSize * 5);

		propPosY.add(offSetY + cellSize * 7);
		propPosY.add(offSetY + cellSize * 7);
		propPosY.add(offSetY + cellSize * 7);
		propPosY.add(offSetY + cellSize * 7);
		propPosY.add(offSetY + cellSize * 7);
		propPosY.add(offSetY + cellSize * 7);

		propPosY.add(offSetY + cellSize * 9);
		propPosY.add(offSetY + cellSize * 9);
		propPosY.add(offSetY + cellSize * 9);
		propPosY.add(offSetY + cellSize * 9);
		propPosY.add(offSetY + cellSize * 9);
		propPosY.add(offSetY + cellSize * 9);
	}

	public void addPropCords_Training() {
		point_armor.x = propPosX.get(propOrder.get(0));
		point_meat.x = propPosX.get(propOrder.get(1));
		point_bow.x = propPosX.get(propOrder.get(2));
		point_flint.x = propPosX.get(propOrder.get(3));
		point_golden_apple.x = propPosX.get(propOrder.get(4));

		point_armor.y = propPosY.get(propOrder.get(0));
		point_meat.y = propPosY.get(propOrder.get(1));
		point_bow.y = propPosY.get(propOrder.get(2));
		point_flint.y = propPosY.get(propOrder.get(3));
		point_golden_apple.y = propPosY.get(propOrder.get(4));
	}

	public void addPropCords_Mittel() {
		point_armor.x = propPosX.get(propOrder.get(0));
		point_meat.x = propPosX.get(propOrder.get(1));
		point_bow.x = propPosX.get(propOrder.get(2));
		point_flint.x = propPosX.get(propOrder.get(3));
		point_golden_apple.x = propPosX.get(propOrder.get(4));
		point_shovel.x = propPosX.get(propOrder.get(5));
		point_pickaxe.x = propPosX.get(propOrder.get(6));

		point_armor.y = propPosY.get(propOrder.get(0));
		point_meat.y = propPosY.get(propOrder.get(1));
		point_bow.y = propPosY.get(propOrder.get(2));
		point_flint.y = propPosY.get(propOrder.get(3));
		point_golden_apple.y = propPosY.get(propOrder.get(4));
		point_shovel.y = propPosY.get(propOrder.get(5));
		point_pickaxe.y = propPosY.get(propOrder.get(6));
	}

	public void addPropCords_Schwer() {
		point_armor.x = propPosX.get(propOrder.get(0));
		point_meat.x = propPosX.get(propOrder.get(1));
		point_bow.x = propPosX.get(propOrder.get(2));
		point_flint.x = propPosX.get(propOrder.get(3));
		point_golden_apple.x = propPosX.get(propOrder.get(4));
		point_shovel.x = propPosX.get(propOrder.get(5));
		point_pickaxe.x = propPosX.get(propOrder.get(6));
		point_sword.x = propPosX.get(propOrder.get(7));
		point_axe.x = propPosX.get(propOrder.get(8));

		point_armor.y = propPosY.get(propOrder.get(0));
		point_meat.y = propPosY.get(propOrder.get(1));
		point_bow.y = propPosY.get(propOrder.get(2));
		point_flint.y = propPosY.get(propOrder.get(3));
		point_golden_apple.y = propPosY.get(propOrder.get(4));
		point_shovel.y = propPosY.get(propOrder.get(5));
		point_pickaxe.y = propPosY.get(propOrder.get(6));
		point_sword.y = propPosY.get(propOrder.get(7));
		point_axe.y = propPosY.get(propOrder.get(8));
	}

	public void resizeProps() {
		propPosX.set(0, offSetX - cellSize);
		propPosX.set(1, offSetX + cellSize);
		propPosX.set(2, offSetX + cellSize * 3);
		propPosX.set(3, offSetX + cellSize * 5);
		propPosX.set(4, offSetX + cellSize * 7);
		propPosX.set(5, offSetX + cellSize * 9);

		propPosX.set(6, offSetX - cellSize);
		propPosX.set(7, offSetX + cellSize);
		propPosX.set(8, offSetX + cellSize * 3);
		propPosX.set(9, offSetX + cellSize * 5);
		propPosX.set(10, offSetX + cellSize * 7);
		propPosX.set(11, offSetX + cellSize * 9);

		propPosX.set(12, offSetX - cellSize);
		propPosX.set(13, offSetX + cellSize);
		propPosX.set(14, offSetX + cellSize * 7);
		propPosX.set(15, offSetX + cellSize * 9);

		propPosX.set(16, offSetX - cellSize);
		propPosX.set(17, offSetX + cellSize);
		propPosX.set(18, offSetX + cellSize * 7);
		propPosX.set(19, offSetX + cellSize * 9);

		propPosX.set(20, offSetX - cellSize);
		propPosX.set(21, offSetX + cellSize);
		propPosX.set(22, offSetX + cellSize * 3);
		propPosX.set(23, offSetX + cellSize * 5);
		propPosX.set(24, offSetX + cellSize * 7);
		propPosX.set(25, offSetX + cellSize * 9);

		propPosX.set(26, offSetX - cellSize);
		propPosX.set(27, offSetX + cellSize);
		propPosX.set(28, offSetX + cellSize * 3);
		propPosX.set(29, offSetX + cellSize * 5);
		propPosX.set(30, offSetX + cellSize * 7);
		propPosX.set(31, offSetX + cellSize * 9);

		propPosY.set(0, offSetY - cellSize);
		propPosY.set(1, offSetY - cellSize);
		propPosY.set(2, offSetY - cellSize);
		propPosY.set(3, offSetY - cellSize);
		propPosY.set(4, offSetY - cellSize);
		propPosY.set(5, offSetY - cellSize);

		propPosY.set(6, offSetY + cellSize);
		propPosY.set(7, offSetY + cellSize);
		propPosY.set(8, offSetY + cellSize);
		propPosY.set(9, offSetY + cellSize);
		propPosY.set(10, offSetY + cellSize);
		propPosY.set(11, offSetY + cellSize);

		propPosY.set(12, offSetY + cellSize * 3);
		propPosY.set(13, offSetY + cellSize * 3);
		propPosY.set(14, offSetY + cellSize * 3);
		propPosY.set(15, offSetY + cellSize * 3);

		propPosY.set(16, offSetY + cellSize * 5);
		propPosY.set(17, offSetY + cellSize * 5);
		propPosY.set(18, offSetY + cellSize * 5);
		propPosY.set(19, offSetY + cellSize * 5);

		propPosY.set(20, offSetY + cellSize * 7);
		propPosY.set(21, offSetY + cellSize * 7);
		propPosY.set(22, offSetY + cellSize * 7);
		propPosY.set(23, offSetY + cellSize * 7);
		propPosY.set(24, offSetY + cellSize * 7);
		propPosY.set(25, offSetY + cellSize * 7);

		propPosY.set(26, offSetY + cellSize * 9);
		propPosY.set(27, offSetY + cellSize * 9);
		propPosY.set(28, offSetY + cellSize * 9);
		propPosY.set(29, offSetY + cellSize * 9);
		propPosY.set(30, offSetY + cellSize * 9);
		propPosY.set(31, offSetY + cellSize * 9);
	}

	public void vanishArmor() {
		propPosX.set(propOrder.get(0), 10000);
		propPosY.set(propOrder.get(0), 10000);
	}

	public void vanishMeat() {
		propPosX.set(propOrder.get(1), 10000);
		propPosY.set(propOrder.get(1), 10000);
	}

	public void vanishBow() {
		propPosX.set(propOrder.get(2), 10000);
		propPosY.set(propOrder.get(2), 10000);
	}

	public void vanishFlint() {
		propPosX.set(propOrder.get(3), 10000);
		propPosY.set(propOrder.get(3), 10000);
	}

	public void vanishApple() {
		propPosX.set(propOrder.get(4), 10000);
		propPosY.set(propOrder.get(4), 10000);
	}

	public void vanishShovel() {
		propPosX.set(propOrder.get(5), 10000);
		propPosY.set(propOrder.get(5), 10000);
	}

	public void vanishPickaxe() {
		propPosX.set(propOrder.get(6), 10000);
		propPosY.set(propOrder.get(6), 10000);
	}

	public void vanishSword() {
		propPosX.set(propOrder.get(7), 10000);
		propPosY.set(propOrder.get(7), 10000);
	}

	public void vanishAxe() {
		propPosX.set(propOrder.get(8), 10000);
		propPosY.set(propOrder.get(8), 10000);
	}

	public void addValue_Training() {
		prop.setGewicht_armor(knapSack.getGewicht_training()[0]);
		prop.setGewicht_bow(knapSack.getGewicht_training()[1]);
		prop.setGewicht_flint(knapSack.getGewicht_training()[2]);
		prop.setGewicht_golden_apple(knapSack.getGewicht_training()[3]);
		prop.setGewicht_meat(knapSack.getGewicht_training()[4]);
	}

	public void addWeight_Training() {
		prop.setWert_armor(knapSack.getWert_training()[0]);
		prop.setWert_bow(knapSack.getWert_training()[1]);
		prop.setWert_flint(knapSack.getWert_training()[2]);
		prop.setWert_golden_apple(knapSack.getWert_training()[3]);
		prop.setWert_meat(knapSack.getWert_training()[4]);
	}

	public void addValue_Mittel() {
		prop.setGewicht_armor(knapSack.getGewicht_mittel()[0]);
		prop.setGewicht_bow(knapSack.getGewicht_mittel()[1]);
		prop.setGewicht_flint(knapSack.getGewicht_mittel()[2]);
		prop.setGewicht_golden_apple(knapSack.getGewicht_mittel()[3]);
		prop.setGewicht_meat(knapSack.getGewicht_mittel()[4]);
		prop.setGewicht_pickaxe(knapSack.getGewicht_mittel()[5]);
		prop.setGewicht_shovel(knapSack.getGewicht_mittel()[6]);
	}

	public void addWeight_Mittel() {
		prop.setWert_armor(knapSack.getWert_mittel()[0]);
		prop.setWert_bow(knapSack.getWert_mittel()[1]);
		prop.setWert_flint(knapSack.getWert_mittel()[2]);
		prop.setWert_golden_apple(knapSack.getWert_mittel()[3]);
		prop.setWert_meat(knapSack.getWert_mittel()[4]);
		prop.setWert_pickaxe(knapSack.getWert_mittel()[5]);
		prop.setWert_shovel(knapSack.getWert_mittel()[6]);
	}

	public void addValue_Schwer() {
		prop.setGewicht_armor(knapSack.getGewicht_schwer()[0]);
		prop.setGewicht_bow(knapSack.getGewicht_schwer()[1]);
		prop.setGewicht_flint(knapSack.getGewicht_schwer()[2]);
		prop.setGewicht_golden_apple(knapSack.getGewicht_schwer()[3]);
		prop.setGewicht_meat(knapSack.getGewicht_schwer()[4]);
		prop.setGewicht_pickaxe(knapSack.getGewicht_schwer()[5]);
		prop.setGewicht_shovel(knapSack.getGewicht_schwer()[6]);
		prop.setGewicht_sword(knapSack.getGewicht_schwer()[7]);
		prop.setGewicht_axe(knapSack.getGewicht_schwer()[8]);
	}

	public void addWeight_Schwer() {
		prop.setWert_armor(knapSack.getWert_schwer()[0]);
		prop.setWert_bow(knapSack.getWert_schwer()[1]);
		prop.setWert_flint(knapSack.getWert_schwer()[2]);
		prop.setWert_golden_apple(knapSack.getWert_schwer()[3]);
		prop.setWert_meat(knapSack.getWert_schwer()[4]);
		prop.setWert_pickaxe(knapSack.getWert_schwer()[5]);
		prop.setWert_shovel(knapSack.getWert_schwer()[6]);
		prop.setWert_sword(knapSack.getWert_schwer()[7]);
		prop.setWert_axe(knapSack.getWert_schwer()[8]);
	}

	public void openDoor() {

		openDoorX1.set(0, offSetX - cellSize - (cellSize / 2));
		openDoorX1.set(1, offSetX + cellSize - (cellSize / 2));
		openDoorX1.set(2, offSetX + cellSize * 3 - (cellSize / 2));
		openDoorX1.set(3, offSetX + cellSize * 5 - (cellSize / 2));
		openDoorX1.set(4, offSetX + cellSize * 7 - (cellSize / 2));
		openDoorX1.set(5, offSetX + cellSize * 9 - (cellSize / 2));

		openDoorY1.set(0, offSetY + cellSize * 11);
		openDoorY1.set(1, offSetY + cellSize * 11);
		openDoorY1.set(2, offSetY + cellSize * 11);
		openDoorY1.set(3, offSetY + cellSize * 11);
		openDoorY1.set(4, offSetY + cellSize * 11);
		openDoorY1.set(5, offSetY + cellSize * 11);

		openDoorX2.set(0, offSetX + cellSize + (cellSize / 2));
		openDoorX2.set(1, offSetX + cellSize * 3 + (cellSize / 2));
		openDoorX2.set(2, offSetX + cellSize * 5 + (cellSize / 2));
		openDoorX2.set(3, offSetX + cellSize * 7 + (cellSize / 2));
		openDoorX2.set(4, offSetX + cellSize * 9 + (cellSize / 2));
		openDoorX2.set(5, offSetX + cellSize * 11 + (cellSize / 2));

//
//		openDoorX2.set(18, offSetX + cellSize);
//		openDoorX2.set(19, offSetX + cellSize * 3);
//		openDoorX2.set(20, offSetX + cellSize * 5);
//		openDoorX2.set(21, offSetX + cellSize * 7);
//		openDoorX2.set(22, offSetX + cellSize * 9);
//		openDoorX2.set(23, offSetX + cellSize * 11);
		// ---------------
//		openDoorY2.set(0, offSetY + cellSize);
//		openDoorY2.set(1, offSetY + cellSize * 3);
//		openDoorY2.set(2, offSetY + cellSize * 5);
//		openDoorY2.set(3, offSetY + cellSize * 7);
//		openDoorY2.set(4, offSetY + cellSize * 9);
//		openDoorY2.set(5, offSetY + cellSize * 11);

		openDoorY2.set(0, offSetY + cellSize * 11);
		openDoorY2.set(1, offSetY + cellSize * 11);
		openDoorY2.set(2, offSetY + cellSize * 11);
		openDoorY2.set(3, offSetY + cellSize * 11);
		openDoorY2.set(4, offSetY + cellSize * 11);
		openDoorY2.set(5, offSetY + cellSize * 11);

//		openDoorY2.set(12, offSetY + cellSize);
//		openDoorY2.set(13, offSetY + cellSize * 3);
//		openDoorY2.set(14, offSetY + cellSize * 5);
//		openDoorY2.set(15, offSetY + cellSize * 7);
//		openDoorY2.set(16, offSetY + cellSize * 9);
//		openDoorY2.set(17, offSetY + cellSize * 11);
//
//		openDoorY2.set(18, offSetY - cellSize);
//		openDoorY2.set(19, offSetY - cellSize);
//		openDoorY2.set(20, offSetY - cellSize);
//		openDoorY2.set(21, offSetY - cellSize);
//		openDoorY2.set(22, offSetY - cellSize);
//		openDoorY2.set(23, offSetY - cellSize);
	}

	public void initOpenDoor() {
//		doorOrder.add(0);
//		doorOrder.add(1);
//		doorOrder.add(2);
//		doorOrder.add(3);
//		doorOrder.add(4);
//		doorOrder.add(5);
		doorOrder.add(0);
		doorOrder.add(1);
		doorOrder.add(2);
		doorOrder.add(3);
		doorOrder.add(4);
		doorOrder.add(5);
//		doorOrder.add(12);
//		doorOrder.add(13);
//		doorOrder.add(14);
//		doorOrder.add(15);
//		doorOrder.add(16);
//		doorOrder.add(17);
//		doorOrder.add(18);
//		doorOrder.add(19);
//		doorOrder.add(20);
//		doorOrder.add(21);
//		doorOrder.add(22);
//		doorOrder.add(23);

		Collections.shuffle(doorOrder);

//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX - cellSize);

		openDoorX1.add(offSetX - cellSize - (cellSize / 2));
		openDoorX1.add(offSetX + cellSize - (cellSize / 2));
		openDoorX1.add(offSetX + cellSize * 3 - (cellSize / 2));
		openDoorX1.add(offSetX + cellSize * 5 - (cellSize / 2));
		openDoorX1.add(offSetX + cellSize * 7 - (cellSize / 2));
		openDoorX1.add(offSetX + cellSize * 9 - (cellSize / 2));

//		openDoorX1.add(offSetX + cellSize * 11);
//		openDoorX1.add(offSetX + cellSize * 11);
//		openDoorX1.add(offSetX + cellSize * 11);
//		openDoorX1.add(offSetX + cellSize * 11);
//		openDoorX1.add(offSetX + cellSize * 11);
//		openDoorX1.add(offSetX + cellSize * 11);

//		openDoorX1.add(offSetX - cellSize);
//		openDoorX1.add(offSetX + cellSize);
//		openDoorX1.add(offSetX + cellSize * 3);
//		openDoorX1.add(offSetX + cellSize * 5);
//		openDoorX1.add(offSetX + cellSize * 7);
//		openDoorX1.add(offSetX + cellSize * 9);
		// -----------------------------------------
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY + cellSize);
//		openDoorY1.add(offSetY + cellSize * 3);
//		openDoorY1.add(offSetY + cellSize * 5);
//		openDoorY1.add(offSetY + cellSize * 7);
//		openDoorY1.add(offSetY + cellSize * 9);

		openDoorY1.add(offSetY + cellSize * 11);
		openDoorY1.add(offSetY + cellSize * 11);
		openDoorY1.add(offSetY + cellSize * 11);
		openDoorY1.add(offSetY + cellSize * 11);
		openDoorY1.add(offSetY + cellSize * 11);
		openDoorY1.add(offSetY + cellSize * 11);

//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY + cellSize);
//		openDoorY1.add(offSetY + cellSize * 3);
//		openDoorY1.add(offSetY + cellSize * 5);
//		openDoorY1.add(offSetY + cellSize * 7);
//		openDoorY1.add(offSetY + cellSize * 9);
//
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY - cellSize);
//		openDoorY1.add(offSetY - cellSize);
		// ---------------
//		openDoorX2.add(offSetX - cellSize);
//		openDoorX2.add(offSetX - cellSize);
//		openDoorX2.add(offSetX - cellSize);
//		openDoorX2.add(offSetX - cellSize);
//		openDoorX2.add(offSetX - cellSize);
//		openDoorX2.add(offSetX - cellSize);

		openDoorX2.add(offSetX + cellSize + (cellSize / 2));
		openDoorX2.add(offSetX + cellSize * 3 + (cellSize / 2));
		openDoorX2.add(offSetX + cellSize * 5 + (cellSize / 2));
		openDoorX2.add(offSetX + cellSize * 7 + (cellSize / 2));
		openDoorX2.add(offSetX + cellSize * 9 + (cellSize / 2));
		openDoorX2.add(offSetX + cellSize * 11 + (cellSize / 2));

//		openDoorX2.add(offSetX + cellSize * 11);
//		openDoorX2.add(offSetX + cellSize * 11);
//		openDoorX2.add(offSetX + cellSize * 11);
//		openDoorX2.add(offSetX + cellSize * 11);
//		openDoorX2.add(offSetX + cellSize * 11);
//		openDoorX2.add(offSetX + cellSize * 11);
//
//		openDoorX2.add(offSetX + cellSize);
//		openDoorX2.add(offSetX + cellSize * 3);
//		openDoorX2.add(offSetX + cellSize * 5);
//		openDoorX2.add(offSetX + cellSize * 7);
//		openDoorX2.add(offSetX + cellSize * 9);
//		openDoorX2.add(offSetX + cellSize * 11);
		// ---------------
//		openDoorY2.add(offSetY + cellSize);
//		openDoorY2.add(offSetY + cellSize * 3);
//		openDoorY2.add(offSetY + cellSize * 5);
//		openDoorY2.add(offSetY + cellSize * 7);
//		openDoorY2.add(offSetY + cellSize * 9);
//		openDoorY2.add(offSetY + cellSize * 11);

		openDoorY2.add(offSetY + cellSize * 11);
		openDoorY2.add(offSetY + cellSize * 11);
		openDoorY2.add(offSetY + cellSize * 11);
		openDoorY2.add(offSetY + cellSize * 11);
		openDoorY2.add(offSetY + cellSize * 11);
		openDoorY2.add(offSetY + cellSize * 11);

//		openDoorY2.add(offSetY + cellSize);
//		openDoorY2.add(offSetY + cellSize * 3);
//		openDoorY2.add(offSetY + cellSize * 5);
//		openDoorY2.add(offSetY + cellSize * 7);
//		openDoorY2.add(offSetY + cellSize * 9);
//		openDoorY2.add(offSetY + cellSize * 11);
//
//		openDoorY2.add(offSetY - cellSize);
//		openDoorY2.add(offSetY - cellSize);
//		openDoorY2.add(offSetY - cellSize);
//		openDoorY2.add(offSetY - cellSize);
//		openDoorY2.add(offSetY - cellSize);
//		openDoorY2.add(offSetY - cellSize);
	}

	public void newKnapsack() {
		knapSack = new Knapsack();
	}
		
	public void componentResized(ComponentEvent e) {
		isResizing = true;
		resizeProps = true;
		repaint();
	}
	
	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

//		System.out.println(height);

//		if (doorOrder.get(0) >= 0 && doorOrder.get(0) <= 5) {
			whatDoor = 1;
//		} else if (doorOrder.get(0) >= 6 && doorOrder.get(0) <= 11) {
//			whatDoor = 1;
//		} else if (doorOrder.get(0) >= 12 && doorOrder.get(0) <= 17) {
//			whatDoor = 1;
//		} else if (doorOrder.get(0) >= 18 && doorOrder.get(0) <= 23) {
//			whatDoor = 1;
//		}
		
		if (pause == false && verloren == false) {
		
		if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
			pointY = pointY + cellSize * 2;
			prop.setXProp(pointX);
			prop.setYProp(pointY);
			resizeY += 1;
			showCurrentValue = false;

			// Collision
			if ((pointX == offSetX && pointY == offSetY + cellSize * 12)
					|| (pointX == offSetX + cellSize * 2 && pointY == offSetY + cellSize * 12)
					|| (pointX == offSetX + cellSize * 4 && pointY == offSetY + cellSize * 12)
					|| (pointX == offSetX + cellSize * 6 && pointY == offSetY + cellSize * 12)
					|| (pointX == offSetX + cellSize * 8 && pointY == offSetY + cellSize * 12)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 12)

					|| (pointX == offSetX && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 2 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 4 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 6 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 8 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY)) {
				pointY -= cellSize * 2;
			}

			if (whatDoor == 1) {
				if ((prop.getXProp() - cellSize * 2 == openDoorX1.get(doorOrder.get(0)) - (cellSize / 2)
						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
					pointY += cellSize * 2;
				}
			}

//			if (whatDoor == 1) {
//				if ((prop.getXProp() - cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointY += cellSize * 2;
//				}
//			}
			repaint();
		}

		if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
			pointY = pointY - cellSize * 2;
			prop.setXProp(pointX);
			prop.setYProp(pointY);
			resizeY -= 1;
			showCurrentValue = false;

			// Collision

			if ((pointX == offSetX && pointY == offSetY - cellSize * 2)
					|| (pointX == offSetX + cellSize * 2 && pointY == offSetY - cellSize * 2)
					|| (pointX == offSetX + cellSize * 4 && pointY == offSetY - cellSize * 2)
					|| (pointX == offSetX + cellSize * 6 && pointY == offSetY - cellSize * 2)
					|| (pointX == offSetX + cellSize * 8 && pointY == offSetY - cellSize * 2)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY - cellSize * 2)

					|| (pointX == offSetX && pointY == offSetY + cellSize * 10)
					|| (pointX == offSetX + cellSize * 2 && pointY == offSetY + cellSize * 10)
					|| (pointX == offSetX + cellSize * 4 && pointY == offSetY + cellSize * 10)
					|| (pointX == offSetX + cellSize * 6 && pointY == offSetY + cellSize * 10)
					|| (pointX == offSetX + cellSize * 8 && pointY == offSetY + cellSize * 10)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 10)) {
				pointY += cellSize * 2;
			}

//			if (whatDoor == 1) {
//				if ((prop.getXProp() - cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() + cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointY -= cellSize * 2;
//				}
//			}
//
			if (whatDoor == 1) {
				if ((prop.getXProp() - cellSize == openDoorX1.get(doorOrder.get(0)) + (cellSize / 2)
						&& prop.getYProp() + cellSize == openDoorY1.get(doorOrder.get(0)))) {
					pointY -= cellSize * 2;
				}
			}

			repaint();
		}

		if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {
			pointX = pointX - cellSize * 2;
			prop.setXProp(pointX);
			prop.setYProp(pointY);
			resizeX -= 1;
			showCurrentValue = false;

			// Collision

			if ((pointX == offSetX - cellSize * 2 && pointY == offSetY)
					|| (pointX == offSetX - cellSize * 2 && pointY == offSetY + cellSize * 2)
					|| (pointX == offSetX - cellSize * 2 && pointY == offSetY + cellSize * 4)
					|| (pointX == offSetX - cellSize * 2 && pointY == offSetY + cellSize * 6)
					|| (pointX == offSetX - cellSize * 2 && pointY == offSetY + cellSize * 8)
					|| (pointX == offSetX - cellSize * 2 && pointY == offSetY + cellSize * 10)

					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 2)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 4)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 6)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 8)
					|| (pointX == offSetX + cellSize * 10 && pointY == offSetY + cellSize * 10)) {
				pointX += cellSize * 2;
			}

//			if (whatDoor == 1) {
//				if ((prop.getXProp() + cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointX -= cellSize * 2;
//				}
//			}
//
//			if (whatDoor == 1) {
//				if ((prop.getXProp() + cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointX -= cellSize * 2;
//				}
//			}

			repaint();
		}

		if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
			pointX = pointX + cellSize * 2;
			prop.setXProp(pointX);
			prop.setYProp(pointY);
			resizeX += 1;
			showCurrentValue = false;

			// Collision

			if ((pointX == offSetX + cellSize * 12 && pointY == offSetY)
					|| (pointX == offSetX + cellSize * 12 && pointY == offSetY + cellSize * 2)
					|| (pointX == offSetX + cellSize * 12 && pointY == offSetY + cellSize * 4)
					|| (pointX == offSetX + cellSize * 12 && pointY == offSetY + cellSize * 6)
					|| (pointX == offSetX + cellSize * 12 && pointY == offSetY + cellSize * 8)
					|| (pointX == offSetX + cellSize * 12 && pointY == offSetY + cellSize * 10)

					|| (pointX == offSetX && pointY == offSetY)
					|| (pointX == offSetX && pointY == offSetY + cellSize * 2)
					|| (pointX == offSetX && pointY == offSetY + cellSize * 4)
					|| (pointX == offSetX && pointY == offSetY + cellSize * 6)
					|| (pointX == offSetX && pointY == offSetY + cellSize * 8)
					|| (pointX == offSetX && pointY == offSetY + cellSize * 10)) {
				pointX -= cellSize * 2;
			}

//			if (whatDoor == 1) {
//				if ((prop.getXProp() - cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointX += cellSize * 2;
//				}
//			}
//
//			if (whatDoor == 1) {
//				if ((prop.getXProp() - cellSize == openDoorX1.get(doorOrder.get(0))
//						&& prop.getYProp() - cellSize == openDoorY1.get(doorOrder.get(0)))) {
//					pointX += cellSize * 2;
//				}
//			}
		}

			repaint();
		}

		if (e.getKeyCode() == e.VK_I) {
			System.out.println("PointX: " + pointX);
			System.out.println("PointY: " + pointY);
			System.out.println("OffsetX: " + offSetX);
			System.out.println("OffsetY: " + offSetY);
			System.out.println("CellSize: " + cellSize);
			System.out.println("Perfect Value: " + maximumValue);
			System.out.println("Maximum Weight: " + maximumWeight);
			System.out.println("Current Value: " + currentValue);
			System.out.println("Current Weight: " + currentWeight);
		}
		if (e.getKeyCode() == e.VK_P) {
			if (pause == true) {
				pause = false;
				addMouseListener(this);
				addMouseMotionListener(this);
			} else if (pause == false) {
				pause = true;
			}
		}
		
		if (e.getKeyCode() == e.VK_V) {
			verloren = true;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public Knapsack getKnapSack() {
		return knapSack;
	}

	public void setKnapSack(Knapsack knapSack) {
		this.knapSack = knapSack;
	}

	public int getWidthAD() {
		return width;
	}

	public void setWidthAD(int width) {
		this.width = width;
	}

	public int getHeightAD() {
		return height;
	}

	public void setHeightAD(int height) {
		this.height = height;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
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

	public int getxVal() {
		return xVal;
	}

	public void setxVal(int xVal) {
		this.xVal = xVal;
	}

	public int getyVal() {
		return yVal;
	}

	public void setyVal(int yVal) {
		this.yVal = yVal;
	}

	public boolean isResizing() {
		return isResizing;
	}

	public void setResizing(boolean isResizing) {
		this.isResizing = isResizing;
	}

	public void mouseClicked(MouseEvent e) {
//		System.out.println(e.getPoint());
		if (e.getPoint().x > prop.getXProp() - cellSize && e.getPoint().x < prop.getXProp() + cellSize
				&& e.getPoint().y > prop.getYProp() - cellSize && e.getPoint().y < prop.getYProp() + cellSize) {

			if (showCurrentValue == false) {
				showCurrentValue = true;
			} else if (showCurrentValue == true) {
				showCurrentValue = false;
			}
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {

		// Training
		if (this.training == true) {

			dragableArmor = false;
			dragableBow = false;
			dragableFlint = false;
			dragableApple = false;
			dragableMeat = false;

			if (e.getPoint().x > propPosX.get(propOrder.get(0))
					&& e.getPoint().x < propPosX.get(propOrder.get(0)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(0))
					&& e.getPoint().y < propPosY.get(propOrder.get(0)) + cellSize * 2) {
				prevPoint_armor = e.getPoint();
				dragableArmor = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(1))
					&& e.getPoint().x < propPosX.get(propOrder.get(1)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(1))
					&& e.getPoint().y < propPosY.get(propOrder.get(1)) + cellSize * 2) {
				prevPoint_meat = e.getPoint();
				dragableMeat = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}

			}

			if (e.getPoint().x > propPosX.get(propOrder.get(2))
					&& e.getPoint().x < propPosX.get(propOrder.get(2)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(2))
					&& e.getPoint().y < propPosY.get(propOrder.get(2)) + cellSize * 2) {
				prevPoint_bow = e.getPoint();
				dragableBow = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(3))
					&& e.getPoint().x < propPosX.get(propOrder.get(3)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(3))
					&& e.getPoint().y < propPosY.get(propOrder.get(3)) + cellSize * 2) {
				prevPoint_flint = e.getPoint();
				dragableFlint = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(4))
					&& e.getPoint().x < propPosX.get(propOrder.get(4)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(4))
					&& e.getPoint().y < propPosY.get(propOrder.get(4)) + cellSize * 2) {
				prevPoint_golden_apple = e.getPoint();
				dragableApple = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}
		}

		// //Mittel

		if (this.mittel == true) {

			dragableArmor = false;
			dragableBow = false;
			dragableFlint = false;
			dragableApple = false;
			dragableMeat = false;
			dragableShovel = false;
			dragablePickaxe = false;

			if (e.getPoint().x > propPosX.get(propOrder.get(0))
					&& e.getPoint().x < propPosX.get(propOrder.get(0)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(0))
					&& e.getPoint().y < propPosY.get(propOrder.get(0)) + cellSize * 2) {
				prevPoint_armor = e.getPoint();
				dragableArmor = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(1))
					&& e.getPoint().x < propPosX.get(propOrder.get(1)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(1))
					&& e.getPoint().y < propPosY.get(propOrder.get(1)) + cellSize * 2) {
				prevPoint_meat = e.getPoint();
				dragableMeat = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(2))
					&& e.getPoint().x < propPosX.get(propOrder.get(2)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(2))
					&& e.getPoint().y < propPosY.get(propOrder.get(2)) + cellSize * 2) {
				prevPoint_bow = e.getPoint();
				dragableBow = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(3))
					&& e.getPoint().x < propPosX.get(propOrder.get(3)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(3))
					&& e.getPoint().y < propPosY.get(propOrder.get(3)) + cellSize * 2) {
				prevPoint_flint = e.getPoint();
				dragableFlint = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(4))
					&& e.getPoint().x < propPosX.get(propOrder.get(4)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(4))
					&& e.getPoint().y < propPosY.get(propOrder.get(4)) + cellSize * 2) {
				prevPoint_golden_apple = e.getPoint();
				dragableApple = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(5))
					&& e.getPoint().x < propPosX.get(propOrder.get(5)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(5))
					&& e.getPoint().y < propPosY.get(propOrder.get(5)) + cellSize * 2) {
				prevPoint_shovel = e.getPoint();
				dragableShovel = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(6))
					&& e.getPoint().x < propPosX.get(propOrder.get(6)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(6))
					&& e.getPoint().y < propPosY.get(propOrder.get(6)) + cellSize * 2) {
				prevPoint_pickaxe = e.getPoint();
				dragablePickaxe = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}
		}

		// Schwer

		if (this.schwer == true) {

			dragableArmor = false;
			dragableBow = false;
			dragableFlint = false;
			dragableApple = false;
			dragableMeat = false;
			dragableShovel = false;
			dragablePickaxe = false;
			dragableSword = false;
			dragableAxe = false;

			if (e.getPoint().x > propPosX.get(propOrder.get(0))
					&& e.getPoint().x < propPosX.get(propOrder.get(0)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(0))
					&& e.getPoint().y < propPosY.get(propOrder.get(0)) + cellSize * 2) {
				prevPoint_armor = e.getPoint();
				dragableArmor = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(1))
					&& e.getPoint().x < propPosX.get(propOrder.get(1)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(1))
					&& e.getPoint().y < propPosY.get(propOrder.get(1)) + cellSize * 2) {
				prevPoint_meat = e.getPoint();
				dragableMeat = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}

			}

			if (e.getPoint().x > propPosX.get(propOrder.get(2))
					&& e.getPoint().x < propPosX.get(propOrder.get(2)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(2))
					&& e.getPoint().y < propPosY.get(propOrder.get(2)) + cellSize * 2) {
				prevPoint_bow = e.getPoint();
				dragableBow = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(3))
					&& e.getPoint().x < propPosX.get(propOrder.get(3)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(3))
					&& e.getPoint().y < propPosY.get(propOrder.get(3)) + cellSize * 2) {
				prevPoint_flint = e.getPoint();
				dragableFlint = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(4))
					&& e.getPoint().x < propPosX.get(propOrder.get(4)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(4))
					&& e.getPoint().y < propPosY.get(propOrder.get(4)) + cellSize * 2) {
				prevPoint_golden_apple = e.getPoint();
				dragableApple = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(5))
					&& e.getPoint().x < propPosX.get(propOrder.get(5)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(5))
					&& e.getPoint().y < propPosY.get(propOrder.get(5)) + cellSize * 2) {
				prevPoint_shovel = e.getPoint();
				dragableShovel = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(6))
					&& e.getPoint().x < propPosX.get(propOrder.get(6)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(6))
					&& e.getPoint().y < propPosY.get(propOrder.get(6)) + cellSize * 2) {
				prevPoint_pickaxe = e.getPoint();
				dragablePickaxe = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(7))
					&& e.getPoint().x < propPosX.get(propOrder.get(7)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(7))
					&& e.getPoint().y < propPosY.get(propOrder.get(7)) + cellSize * 2) {
				prevPoint_sword = e.getPoint();
				dragableSword = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}

			if (e.getPoint().x > propPosX.get(propOrder.get(8))
					&& e.getPoint().x < propPosX.get(propOrder.get(8)) + cellSize * 2
					&& e.getPoint().y > propPosY.get(propOrder.get(8))
					&& e.getPoint().y < propPosY.get(propOrder.get(8)) + cellSize * 2) {
				prevPoint_axe = e.getPoint();
				dragableAxe = true;

				if (showCurrentValue == true) {
					showCurrentValue = false;
				}
			}
		}
	}

	public void mouseDragged(MouseEvent e) {

		if (this.training == true) {

			if (dragableArmor) {
				Point currentpoint = e.getPoint();

				point_armor.translate((int) (currentpoint.getX() - prevPoint_armor.getX()),
						(int) (currentpoint.getY() - prevPoint_armor.getY()));

				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(0), point_armor.x);
				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(0), point_armor.y);
				}
				
				prevPoint_armor = currentpoint;
				repaint();
			}

			if (dragableMeat) {
				Point currentpoint = e.getPoint();

				point_meat.translate((int) (currentpoint.getX() - prevPoint_meat.getX()),
						(int) (currentpoint.getY() - prevPoint_meat.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(1), point_meat.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(1), point_meat.y);

				}

				prevPoint_meat = currentpoint;
				repaint();
			}

			if (dragableBow) {
				Point currentpoint = e.getPoint();

				point_bow.translate((int) (currentpoint.getX() - prevPoint_bow.getX()),
						(int) (currentpoint.getY() - prevPoint_bow.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(2), point_bow.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(2), point_bow.y);

				}

				prevPoint_bow = currentpoint;
				repaint();
			}

			if (dragableFlint) {
				Point currentpoint = e.getPoint();

				point_flint.translate((int) (currentpoint.getX() - prevPoint_flint.getX()),
						(int) (currentpoint.getY() - prevPoint_flint.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(3), point_flint.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(3), point_flint.y);

				}
				
				prevPoint_flint = currentpoint;
				repaint();
			}

			if (dragableApple) {
				Point currentpoint = e.getPoint();

				point_golden_apple.translate((int) (currentpoint.getX() - prevPoint_golden_apple.getX()),
						(int) (currentpoint.getY() - prevPoint_golden_apple.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(4), point_golden_apple.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(4), point_golden_apple.y);

				}
				
				prevPoint_golden_apple = currentpoint;
				repaint();
			}
		}

		// Mittel

		if (this.mittel == true) {

			if (dragableArmor) {
				Point currentpoint = e.getPoint();

				point_armor.translate((int) (currentpoint.getX() - prevPoint_armor.getX()),
						(int) (currentpoint.getY() - prevPoint_armor.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(0), point_armor.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(0), point_armor.y);

				}
				
				prevPoint_armor = currentpoint;
				repaint();
			}

			if (dragableMeat) {
				Point currentpoint = e.getPoint();

				point_meat.translate((int) (currentpoint.getX() - prevPoint_meat.getX()),
						(int) (currentpoint.getY() - prevPoint_meat.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(1), point_meat.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(1), point_meat.y);

				}

				prevPoint_meat = currentpoint;
				repaint();
			}

			if (dragableBow) {
				Point currentpoint = e.getPoint();

				point_bow.translate((int) (currentpoint.getX() - prevPoint_bow.getX()),
						(int) (currentpoint.getY() - prevPoint_bow.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(2), point_bow.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(2), point_bow.y);

				}

				prevPoint_bow = currentpoint;
				repaint();
			}

			if (dragableFlint) {
				Point currentpoint = e.getPoint();

				point_flint.translate((int) (currentpoint.getX() - prevPoint_flint.getX()),
						(int) (currentpoint.getY() - prevPoint_flint.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(3), point_flint.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(3), point_flint.y);

				}
				
				prevPoint_flint = currentpoint;
				repaint();
			}

			if (dragableApple) {
				Point currentpoint = e.getPoint();

				point_golden_apple.translate((int) (currentpoint.getX() - prevPoint_golden_apple.getX()),
						(int) (currentpoint.getY() - prevPoint_golden_apple.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(4), point_golden_apple.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(4), point_golden_apple.y);

				}
				
				prevPoint_golden_apple = currentpoint;
				repaint();
			}

			if (dragableShovel) {
				Point currentpoint = e.getPoint();

				point_shovel.translate((int) (currentpoint.getX() - prevPoint_shovel.getX()),
						(int) (currentpoint.getY() - prevPoint_shovel.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(5), point_shovel.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(5), point_shovel.y);

				}
				
				prevPoint_shovel = currentpoint;
				repaint();
			}

			if (dragablePickaxe) {
				Point currentpoint = e.getPoint();

				point_pickaxe.translate((int) (currentpoint.getX() - prevPoint_pickaxe.getX()),
						(int) (currentpoint.getY() - prevPoint_pickaxe.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(6), point_pickaxe.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(6), point_pickaxe.y);

				}
				
				prevPoint_pickaxe = currentpoint;
				repaint();
			}
		}

		// Schwer
		if (this.schwer == true) {

			if (dragableArmor) {
				Point currentpoint = e.getPoint();

				point_armor.translate((int) (currentpoint.getX() - prevPoint_armor.getX()),
						(int) (currentpoint.getY() - prevPoint_armor.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(0), point_armor.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(0), point_armor.y);

				}
				
				prevPoint_armor = currentpoint;
				repaint();
			}

			if (dragableMeat) {
				Point currentpoint = e.getPoint();

				point_meat.translate((int) (currentpoint.getX() - prevPoint_meat.getX()),
						(int) (currentpoint.getY() - prevPoint_meat.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(1), point_meat.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(1), point_meat.y);

				}

				prevPoint_meat = currentpoint;
				repaint();
			}

			if (dragableBow) {
				Point currentpoint = e.getPoint();

				point_bow.translate((int) (currentpoint.getX() - prevPoint_bow.getX()),
						(int) (currentpoint.getY() - prevPoint_bow.getY()));

				
				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(2), point_bow.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(2), point_bow.y);

				}

				prevPoint_bow = currentpoint;
				repaint();
			}

			if (dragableFlint) {
				Point currentpoint = e.getPoint();

				point_flint.translate((int) (currentpoint.getX() - prevPoint_flint.getX()),
						(int) (currentpoint.getY() - prevPoint_flint.getY()));

				
				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(3), point_flint.x);
				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(3), point_flint.y);
				}
				
				prevPoint_flint = currentpoint;
				repaint();
			}

			if (dragableApple) {
				Point currentpoint = e.getPoint();

				point_golden_apple.translate((int) (currentpoint.getX() - prevPoint_golden_apple.getX()),
						(int) (currentpoint.getY() - prevPoint_golden_apple.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(4), point_golden_apple.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(4), point_golden_apple.y);

				}
				
				prevPoint_golden_apple = currentpoint;
				repaint();
			}

			if (dragableShovel) {
				Point currentpoint = e.getPoint();

				point_shovel.translate((int) (currentpoint.getX() - prevPoint_shovel.getX()),
						(int) (currentpoint.getY() - prevPoint_shovel.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(5), point_shovel.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(5), point_shovel.y);

				}
				
				prevPoint_shovel = currentpoint;
				repaint();
			}

			if (dragablePickaxe) {
				Point currentpoint = e.getPoint();

				point_pickaxe.translate((int) (currentpoint.getX() - prevPoint_pickaxe.getX()),
						(int) (currentpoint.getY() - prevPoint_pickaxe.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(6), point_pickaxe.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(6), point_pickaxe.y);

				}
				
				prevPoint_pickaxe = currentpoint;
				repaint();
			}

			if (dragableSword) {
				Point currentpoint = e.getPoint();

				point_sword.translate((int) (currentpoint.getX() - prevPoint_sword.getX()),
						(int) (currentpoint.getY() - prevPoint_sword.getY()));

				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(7), point_sword.x);

				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(7), point_sword.y);

				}
				
				prevPoint_sword = currentpoint;
				repaint();
			}

			if (dragableAxe) {
				Point currentpoint = e.getPoint();

				point_axe.translate((int) (currentpoint.getX() - prevPoint_axe.getX()),
						(int) (currentpoint.getY() - prevPoint_axe.getY()));
				
				if (e.getPoint().x - cellSize < offSetX + cellSize * 10 && e.getPoint().x + cellSize > offSetX) {
					propPosX.set(propOrder.get(8), point_axe.x);
				}
				
				if (e.getPoint().y - cellSize < offSetY + cellSize * 10 && e.getPoint().y + cellSize > offSetY) {
					propPosY.set(propOrder.get(8), point_axe.y);
				}
				
				prevPoint_axe = currentpoint;
				repaint();
			}

		}

	}

	public void mouseReleased(MouseEvent e) {
		
		if (e.getPoint().x > prop.getXProp() - cellSize && e.getPoint().x < prop.getXProp() + cellSize
				&& e.getPoint().y > prop.getYProp() - cellSize && e.getPoint().y < prop.getYProp() + cellSize) {

			if (dragableArmor) {
				propPosX.set(propOrder.get(0), 10000);
				propPosY.set(propOrder.get(0), 10000);
				vanishArmor = true;
				currentValue += prop.getWert_armor();
				currentWeight += prop.getGewicht_armor();
				repaint();
			}

			if (dragableMeat) {
				propPosX.set(propOrder.get(1), 10000);
				propPosY.set(propOrder.get(1), 10000);
				vanishMeat = true;
				currentValue += prop.getWert_meat();
				currentWeight += prop.getGewicht_meat();
				repaint();
			}

			if (dragableBow) {
				propPosX.set(propOrder.get(2), 10000);
				propPosY.set(propOrder.get(2), 10000);
				vanishBow = true;
				currentValue += prop.getWert_bow();
				currentWeight += prop.getGewicht_bow();
				repaint();
			}

			if (dragableFlint) {
				propPosX.set(propOrder.get(3), 10000);
				propPosY.set(propOrder.get(3), 10000);
				currentValue += prop.getWert_flint();
				currentWeight += prop.getGewicht_flint();
				vanishFlint = true;
				repaint();
			}

			if (dragableApple) {
				propPosX.set(propOrder.get(4), 10000);
				propPosY.set(propOrder.get(4), 10000);
				vanishApple = true;
				currentValue += prop.getWert_golden_apple();
				currentWeight += prop.getGewicht_golden_apple();
				repaint();
			}

			if (dragableShovel) {
				propPosX.set(propOrder.get(5), 10000);
				propPosY.set(propOrder.get(5), 10000);
				vanishShovel = true;
				currentValue += prop.getWert_shovel();
				currentWeight += prop.getGewicht_shovel();
				repaint();
			}

			if (dragablePickaxe) {
				propPosX.set(propOrder.get(6), 10000);
				propPosY.set(propOrder.get(6), 10000);
				vanishPickaxe = true;
				currentValue += prop.getWert_pickaxe();
				currentWeight += prop.getGewicht_pickaxe();
				repaint();
			}

			if (dragableSword) {
				propPosX.set(propOrder.get(7), 10000);
				propPosY.set(propOrder.get(7), 10000);
				vanishSword = true;
				currentValue += prop.getWert_sword();
				currentWeight += prop.getGewicht_sword();
				repaint();
			}

			if (dragableAxe) {
				propPosX.set(propOrder.get(8), 10000);
				propPosY.set(propOrder.get(8), 10000);
				vanishAxe = true;
				currentValue += prop.getWert_axe();
				currentWeight += prop.getGewicht_axe();
				repaint();
			}
		} else {
			resizeProps();
			
			if (training) {
				addPropCords_Training();
			} else if (mittel) {
				addPropCords_Mittel();
			} else if (schwer) {
				addPropCords_Schwer();
			}
			
			if (vanishArmor == true) {
				vanishArmor();
			}
			if (vanishBow == true) {
				vanishBow();
			}
			if (vanishFlint == true) {
				vanishFlint();
			}
			if (vanishApple == true) {
				vanishApple();
			}
			if (vanishMeat == true) {
				vanishMeat();
			}
			if (vanishShovel == true) {
				vanishShovel();
			}
			if (vanishPickaxe == true) {
				vanishPickaxe();
			}
			if (vanishSword == true) {
				vanishSword();
			}
			if (vanishAxe == true) {
				vanishAxe();
			}
			repaint();
		}
	}
	
	public int getCurrentValue() {
		return currentValue;
	}
	
	public int getCurrentWeight() {
		return currentWeight;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean getVerloren() {
		return verloren;
	}
	
	public void setVerloren(boolean verloren) {
		this.verloren = verloren;
	}
	
	public boolean getAbgabe() {
		return abgabe;
	}
	
	public Graphics2D get2D() {
		return g2dRand;
	}
	
	public boolean getTraining() {
		return training;
	}
	
	public boolean getMittel() {
		return mittel;
	}
	
	public boolean getSchwer() {
		return schwer;
	}
	
	public void setAbgabe(boolean abgabe) {
		this.abgabe = abgabe;
	}
	
	public int getMaximumValue() {
		return maximumValue;
	}
	
	public void setDisplayValue(boolean value) {
		this.displayValue = value;
	}
	
	public void setDoClick(boolean click) {
		this.doClick = click;
	}
	
	public void setTraining(boolean peter) {
		training = peter;
	}
	
	public void setMittel(boolean peter) {
		mittel = peter;
	}
	
	public void setSchwer(boolean peter) {
		schwer = peter;
	}
	
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

	}
}