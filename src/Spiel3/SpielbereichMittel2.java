package Spiel3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Spiel3.SpielbereichMittel1.ClickListener;

public class SpielbereichMittel2 extends JPanel implements MouseListener, KeyListener, ComponentListener {
	public static Graph getGraph() {
		return graph;
	}

	public static void setGraph(Graph graph) {
		SpielbereichMittel2.graph = graph;
	}

	private static int cellSize = 25;
	private static int offSetX = cellSize * 2;
	private static int offSetY = cellSize * 2;
	private int x2 = 6;
	private int y2 = 8;
	private int width, height;

	private Point image_corner;
	private Point previousPoint;
	private ImageIcon extrarohr1;
	Image smiley;

	private static Graph graph;

	boolean showimage01 = false;
	boolean showimage02 = false;
	boolean showimage13 = false;
	boolean showimage23 = false;
	boolean showimage24 = false;
	boolean showimage35 = false;
	boolean showimage45 = false;
	boolean showimage46 = false;
	boolean showimage57 = false;
	boolean showimage67 = false;

	String gewicht1;
	String gewicht2;
	String gewicht3;
	String gewicht4;
	String gewicht5;
	String gewicht6;
	String gewicht7;
	String gewicht8;
	String gewicht9;

	int counter01 = 0;
	int counter02 = 0;
	int counter13 = 0;
	int counter23 = 0;
	int counter24 = 0;
	int counter35 = 0;
	int counter46 = 0;
	int counter57 = 0;
	int counter67 = 0;

	boolean showedge01 = false;
	boolean showedge02 = false;
	boolean showedge13 = false;
	boolean showedge23 = false;
	boolean showedge24 = false;
	boolean showedge35 = false;
	boolean showedge45 = false;
	boolean showedge46 = false;
	boolean showedge57 = false;
	boolean showedge67 = false;

	boolean extrarohrmoved = false;

	public static int anzahlkanten2 = 0;
	public static int lösung2;

	public static boolean mouseclick1 = true;
	public static int switchpanel;

	public static ArrayList<ArrayList<Integer>> angebunden;
	public static ArrayList<Integer> angebunden1;
	public static ArrayList<Integer> angebunden2;

	Stack<String> abwählen;

	private Rectangle[][] recs = new Rectangle[18][14];

	public SpielbereichMittel2() {
		addMouseListener(this);
		addComponentListener(this);
		addKeyListener(this);
		setFocusable(true);

		lösung2 = 0;

		cellSize = 25;
		offSetX = cellSize * 2;
		offSetY = cellSize * 2;

		angebunden = new ArrayList<ArrayList<Integer>>();
		angebunden1 = new ArrayList<Integer>();
		angebunden2 = new ArrayList<Integer>();
		angebunden.add(angebunden1);
		angebunden.add(angebunden2);

		abwählen = new Stack<String>();

		int i = offSetX;
		int j = offSetY;
		for (int row = 0; row < 18; row++) {
			for (int col = 0; col < 14; col++) {
				recs[row][col] = new Rectangle(i, offSetY, cellSize, cellSize);
				j += cellSize;
			}
			i = 50;
			j += cellSize;
		}

		graph = new Graph(8, 2);
		width = Spiel3.getPanelWidth();
		height = Spiel3.getPanelHeight();

		ClickListener clicklistener = new ClickListener();
		this.addMouseListener(clicklistener);
		this.addMouseMotionListener(clicklistener);

		image_corner = new Point(recs[1][1].x, recs[1][1].y);

	}

	int a = 1;
	int b = 3;

	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(Graph.getBackground(), 0, 0, getWidth(), getHeight(), this);

		int l = offSetX;
		int k = offSetY;
		for (int row = 0; row < 18; row++) {
			for (int col = 0; col < 14; col++) {
				recs[row][col] = new Rectangle(l, k, cellSize, cellSize);
				l += cellSize;
			}
			l = offSetX;
			k += cellSize;
		}

		g.setColor(Color.LIGHT_GRAY);
		Graphics2D g2d = (Graphics2D) g;
		for (int row = 0; row < 18; row++) {
			for (int col = 0; col < 14; col++) {
				g2d.draw(recs[row][col]);
			}
		}

		for (int i = 0; i < graph.knotenanzahl; i++) {
			if (i == 0) {
				g.setColor(Color.red);
				g.fillOval(recs[16][1].x, recs[16][1].y, cellSize, cellSize);
				g.drawString("0", recs[16][1].x, recs[16][1].y);
			} else if (i == 1) {
				g.setColor(Color.blue);
				g.fillOval(recs[16][12].x, recs[16][12].y, cellSize, cellSize);
				g.drawString("1", recs[16][12].x, recs[16][12].y);
			} else if (i == 2) {
				g.setColor(Color.blue);
				g.fillOval(recs[12][1].x, recs[12][1].y, cellSize, cellSize);
				g.drawString("2", recs[12][1].x, recs[12][1].y);
			} else if (i == 3) {
				g.setColor(Color.blue);
				g.fillOval(recs[12][12].x, recs[12][12].y, cellSize, cellSize);
				g.drawString("3", recs[12][12].x, recs[12][12].y);
			} else if (i == 4) {
				g.setColor(Color.blue);
				g.fillOval(recs[8][1].x, recs[8][1].y, cellSize, cellSize);
				g.drawString("4", recs[8][1].x, recs[8][1].y);
			} else if (i == 5) {
				g.setColor(Color.blue);
				g.fillOval(recs[8][12].x, recs[8][12].y, cellSize, cellSize);
				g.drawString("5", recs[8][12].x, recs[8][12].y);
			} else if (i == 6) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][1].x, recs[4][1].y, cellSize, cellSize);
				g.drawString("6", recs[4][1].x, recs[4][1].y);
			} else if (i == 7) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][12].x, recs[4][12].y, cellSize, cellSize);
				g.drawString("7", recs[4][12].x, recs[4][12].y);
			}

		}
		for (int i = 0; i < graph.knotenanzahl; i++) {
			for (int j = 0; j < graph.knotenanzahl; j++) {
				if (i == 0 && j == 1) {
					g.setColor(Color.black);
					g.drawLine(recs[16][2].x, recs[16][2].y + (cellSize / 2), recs[16][12].x,
							recs[16][12].y + (cellSize / 2));
					gewicht1 = String.valueOf(graph.adjazenzmatrix[0][1]);
					g.drawString(gewicht1, (recs[16][2].x + recs[16][12].x) / 2,
							(recs[16][2].y + (cellSize / 2) + recs[16][12].y + (cellSize / 2)) / 2);
				} else if (i == 0 && j == 2) {
					g.setColor(Color.black);
					g.drawLine(recs[13][1].x + (cellSize / 2), recs[13][1].y, recs[16][1].x + (cellSize / 2),
							recs[16][1].y);
					gewicht2 = String.valueOf(graph.adjazenzmatrix[0][2]);
					g.drawString(gewicht2, (recs[13][1].x + (cellSize / 2) + recs[16][1].x + (cellSize / 2)) / 2,
							(recs[13][1].y + recs[16][1].y) / 2);
				} else if (i == 1 && j == 3) {
					g.setColor(Color.black);
					g.drawLine(recs[13][12].x + (cellSize / 2), recs[13][12].y, recs[16][12].x + (cellSize / 2),
							recs[16][12].y);
					gewicht3 = String.valueOf(graph.adjazenzmatrix[1][3]);
					g.drawString(gewicht3, (recs[13][12].x + (cellSize / 2) + recs[16][12].x + (cellSize / 2)) / 2,
							(recs[13][12].y + recs[16][12].y) / 2);
				} else if (i == 2 && j == 3) {
					g.setColor(Color.black);
					g.drawLine(recs[12][2].x, recs[12][2].y + (cellSize / 2), recs[12][12].x,
							recs[12][12].y + (cellSize / 2));
					gewicht4 = String.valueOf(graph.adjazenzmatrix[2][3]);
					g.drawString(gewicht4, (recs[12][2].x + recs[12][12].x) / 2,
							(recs[12][2].y + (cellSize / 2) + recs[12][12].y + (cellSize / 2)) / 2);
				} else if (i == 2 && j == 4) {
					g.setColor(Color.black);
					g.drawLine(recs[9][1].x + (cellSize / 2), recs[9][1].y, recs[12][1].x + (cellSize / 2),
							recs[12][1].y);
					gewicht5 = String.valueOf(graph.adjazenzmatrix[2][4]);
					g.drawString(gewicht5, (recs[9][1].x + (cellSize / 2) + recs[12][1].x + (cellSize / 2)) / 2,
							(recs[9][1].y + recs[12][1].y) / 2);
				} else if (i == 3 && j == 5) {
					g.setColor(Color.black);
					g.drawLine(recs[9][12].x + (cellSize / 2), recs[9][12].y, recs[12][12].x + (cellSize / 2),
							recs[12][12].y);
					gewicht6 = String.valueOf(graph.adjazenzmatrix[3][5]);
					g.drawString(gewicht6, (recs[9][12].x + (cellSize / 2) + recs[12][12].x + (cellSize / 2)) / 2,
							(recs[9][12].y + recs[12][12].y) / 2);
				} else if (i == 4 && j == 6) {
					g.setColor(Color.black);
					g.drawLine(recs[5][1].x + (cellSize / 2), recs[5][1].y, recs[8][1].x + (cellSize / 2),
							recs[8][1].y);
					gewicht7 = String.valueOf(graph.adjazenzmatrix[4][6]);
					g.drawString(gewicht7, (recs[5][1].x + (cellSize / 2) + recs[8][1].x + (cellSize / 2)) / 2,
							(recs[5][1].y + recs[8][1].y) / 2);
				} else if (i == 5 && j == 7) {
					g.setColor(Color.black);
					g.drawLine(recs[5][12].x + (cellSize / 2), recs[5][12].y, recs[8][12].x + (cellSize / 2),
							recs[8][12].y);
					gewicht8 = String.valueOf(graph.adjazenzmatrix[5][7]);
					g.drawString(gewicht8, (recs[5][12].x + (cellSize / 2) + recs[8][12].x + (cellSize / 2)) / 2,
							(recs[5][12].y + recs[8][12].y) / 2);
				} else if (i == 6 && j == 7) {
					g.setColor(Color.black);
					g.drawLine(recs[4][2].x, recs[4][2].y + (cellSize / 2), recs[4][12].x,
							recs[4][12].y + (cellSize / 2));
					gewicht9 = String.valueOf(graph.adjazenzmatrix[6][7]);
					g.drawString(gewicht9, (recs[4][2].x + recs[4][12].x) / 2,
							(recs[4][2].y + (cellSize / 2) + recs[4][12].y + (cellSize / 2)) / 2);
				}

			}
		}

		if (extrarohrmoved == false) {
			image_corner = new Point(recs[1][1].x, recs[1][1].y);
		}
		Image newextrarohr = Graph.getExtrarohr1().getImage();
		newextrarohr = newextrarohr.getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_REPLICATE);
		Graph.setExtrarohr1(new ImageIcon(newextrarohr));
		Graph.getExtrarohr1().paintIcon(this, g, image_corner.x, image_corner.y);

		g.drawImage(Graph.getLüftungssystem(), recs[a][b].x, recs[a][b].y, cellSize, cellSize, null);

		if (showimage01 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[16][2].x, recs[16][2].y, recs[16][9].x, cellSize, null);
		}
		if (showimage02 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[13][1].x, recs[13][1].y, cellSize, recs[13][2].x, null);
		}
		if (showimage13 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[13][12].x, recs[13][12].y, cellSize, recs[13][2].x, null);
		}
		if (showimage23 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[12][2].x, recs[12][2].y, recs[12][9].x, cellSize, null);
		}
		if (showimage24 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[9][1].x, recs[9][1].y, cellSize, recs[9][2].x, null);
		}
		if (showimage35 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[9][12].x, recs[9][12].y, cellSize, recs[9][2].x, null);
		}
		if (showimage46 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][1].x, recs[5][1].y, cellSize, recs[5][2].x, null);
		}
		if (showimage57 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][12].x, recs[5][12].y, cellSize, recs[5][2].x, null);
		}
		if (showimage67 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[4][2].x, recs[4][2].y, recs[4][9].x, cellSize, null);
		}

	}

	boolean showbonus = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (mouseclick1 == true) {
			if (recs[16][2].x < e.getX() && e.getX() < recs[16][12].x && recs[16][2].y < e.getY()
					&& e.getY() < recs[17][2].y) {
				if (counter01 == 0) {
					showimage01 = true;
					repaint();
					lösung2 += Integer.valueOf(gewicht1);
					anzahlkanten2 += 1;
					counter01 = 1;
					showedge01 = true;
					abwählen.push("a");
					angebunden1.add(0);
					angebunden2.add(1);
				} else if (counter01 == 1) {
					if (abwählen.peek() == "a") {
						showimage01 = false;
						repaint();
						lösung2 -= Integer.valueOf(gewicht1);
						anzahlkanten2 -= 1;
						counter01 = 0;
						showedge01 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}

			if (recs[13][1].y < e.getY() && e.getY() < recs[16][1].y && recs[13][1].x < e.getX()
					&& e.getX() < recs[13][2].x) {
				if (counter02 == 0) {
					showimage02 = true;
					repaint();
					lösung2 += Integer.valueOf(gewicht2);
					anzahlkanten2 += 1;
					counter02 = 1;
					showedge02 = true;
					abwählen.push("b");
					angebunden1.add(0);
					angebunden2.add(2);
				} else if (counter02 == 1) {
					if (abwählen.peek() == "b") {
						showimage02 = false;
						repaint();
						lösung2 -= Integer.valueOf(gewicht2);
						anzahlkanten2 -= 1;
						counter02 = 0;
						showedge02 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}

			if (showedge01 == true || showedge23 == true || showedge35 == true) {
				if (recs[13][12].y < e.getY() && e.getY() < recs[16][12].y && recs[13][12].x < e.getX()
						&& e.getX() < recs[13][13].x) {
					if (counter13 == 0) {
						showimage13 = true;
						repaint();
						lösung2 += Integer.valueOf(gewicht3);
						anzahlkanten2 += 1;
						counter13 = 1;
						showedge13 = true;
						abwählen.push("c");
						angebunden1.add(1);
						angebunden2.add(3);
					} else if (counter13 == 1) {
						if (abwählen.peek() == "c") {
							showimage13 = false;
							repaint();
							lösung2 -= Integer.valueOf(gewicht3);
							anzahlkanten2 -= 1;
							counter13 = 0;
							showedge13 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge02 == true || showedge13 == true || showedge24 == true || showedge35 == true) {
				if (recs[12][2].x < e.getX() && e.getX() < recs[12][12].x && recs[12][2].y < e.getY()
						&& e.getY() < recs[13][2].y) {
					if (counter23 == 0) {
						showimage23 = true;
						repaint();
						lösung2 += Integer.valueOf(gewicht4);
						anzahlkanten2 += 1;
						counter23 = 1;
						showedge23 = true;
						abwählen.push("d");
						angebunden1.add(2);
						angebunden2.add(3);
					} else if (counter23 == 1) {
						if (abwählen.peek() == "d") {
							showimage23 = false;
							repaint();
							lösung2 -= Integer.valueOf(gewicht4);
							anzahlkanten2 -= 1;
							counter23 = 0;
							showedge23 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge02 == true || showedge23 == true || showedge46 == true) {
				if (recs[9][1].y < e.getY() && e.getY() < recs[12][1].y && recs[9][1].x < e.getX()
						&& e.getX() < recs[9][2].x) {
					if (counter24 == 0) {
						showimage24 = true;
						repaint();
						lösung2 += Integer.valueOf(gewicht5);
						anzahlkanten2 += 1;
						counter24 = 1;
						showedge24 = true;
						abwählen.push("e");
						angebunden1.add(2);
						angebunden2.add(4);
					} else if (counter24 == 1) {
						if (abwählen.peek() == "e") {
							showimage24 = false;
							repaint();
							lösung2 -= Integer.valueOf(gewicht5);
							anzahlkanten2 -= 1;
							counter24 = 0;
							showedge24 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}
			if (showedge23 == true || showedge13 == true || showedge57 == true) {
				if (recs[9][12].y < e.getY() && e.getY() < recs[12][12].y && recs[9][12].x < e.getX()
						&& e.getX() < recs[9][13].x) {
					if (counter35 == 0) {
						showimage35 = true;
						repaint();
						lösung2 += Integer.valueOf(gewicht6);
						anzahlkanten2 += 1;
						counter35 = 1;
						showedge35 = true;
						abwählen.push("f");
						angebunden1.add(3);
						angebunden2.add(5);
					} else if (counter35 == 1) {
						if (abwählen.peek() == "f") {
							showimage35 = false;
							repaint();
							lösung2 -= Integer.valueOf(gewicht6);
							anzahlkanten2 -= 1;
							counter35 = 0;
							showedge35 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

		}

		if (showedge24 == true || showedge67 == true) {
			if (recs[5][1].y < e.getY() && e.getY() < recs[8][1].y && recs[5][1].x < e.getX()
					&& e.getX() < recs[5][2].x) {
				if (counter46 == 0) {
					showimage46 = true;
					repaint();
					lösung2 += Integer.valueOf(gewicht7);
					anzahlkanten2 += 1;
					counter46 = 1;
					showedge46 = true;
					abwählen.push("g");
					angebunden1.add(4);
					angebunden2.add(6);
				} else if (counter46 == 1) {
					if (abwählen.peek() == "g") {
						showimage46 = false;
						repaint();
						lösung2 -= Integer.valueOf(gewicht7);
						anzahlkanten2 -= 1;
						counter46 = 0;
						showedge46 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}
		}

		if (showedge35 == true || showedge67 == true) {
			if (recs[5][12].y < e.getY() && e.getY() < recs[8][12].y && recs[5][12].x < e.getX()
					&& e.getX() < recs[5][13].x) {
				if (counter57 == 0) {
					showimage57 = true;
					repaint();
					lösung2 += Integer.valueOf(gewicht8);
					anzahlkanten2 += 1;
					counter57 = 1;
					showedge57 = true;
					abwählen.push("h");
					angebunden1.add(5);
					angebunden2.add(7);
				} else if (counter57 == 1) {
					if (abwählen.peek() == "h") {
						showimage57 = false;
						repaint();
						lösung2 -= Integer.valueOf(gewicht8);
						anzahlkanten2 -= 1;
						counter57 = 0;
						showedge57 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}
		}

		if (showedge46 == true || showedge57 == true) {
			if (recs[4][2].x < e.getX() && e.getX() < recs[4][12].x && recs[4][2].y < e.getY()
					&& e.getY() < recs[5][2].y) {
				if (counter67 == 0) {
					showimage67 = true;
					repaint();
					lösung2 += Integer.valueOf(gewicht9);
					anzahlkanten2 += 1;
					counter67 = 1;
					showedge67 = true;
					abwählen.push("i");
					angebunden1.add(6);
					angebunden2.add(7);
				} else if (counter67 == 1) {
					if (abwählen.peek() == "i") {
						showimage67 = false;
						repaint();
						lösung2 -= Integer.valueOf(gewicht9);
						anzahlkanten2 -= 1;
						counter67 = 0;
						showedge67 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void pausierenstart2() {
		Auswahlbereich.timer1.stop();
		SpielbereichMittel2.mouseclick1 = false;
	}

	public static void pausierenende2() {
		Auswahlbereich.timer1.start();
		SpielbereichMittel2.mouseclick1 = true;
	}

	public static void gameover2() {
		JFrame frame = new JFrame("Game over");
		frame.setLayout(new GridLayout(3, 1));
		JLabel label = new JLabel("Du hast verloren");
		JButton weiter = new JButton("Weiter");
		frame.add(label);
		frame.add(weiter);
		frame.setSize(300, 300);
		weiter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean neuHs = Auswahlbereich.findMinScore();
				if (neuHs) {
					Auswahlbereich.highscore();
					frame.dispose();
				} else {
					frame.dispose();
				}

			}

		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	public static void win2() {
		JFrame frame = new JFrame("You won");
		JOptionPane.showMessageDialog(frame, "Du hast gewonnen", "You won", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (mouseclick1 == true) {
			if (e.getKeyCode() == e.VK_LEFT) {
				if (b > 0) {
					b -= 1;
					repaint();
				}
			}

			if (e.getKeyCode() == e.VK_RIGHT) {
				if (b < 13) {
					b += 1;
					repaint();
				}
			}

			if (e.getKeyCode() == e.VK_DOWN) {
				if (a < 17) {
					a += 1;
					repaint();
				}
			}

			if (e.getKeyCode() == e.VK_UP) {
				if (a > 0) {
					a -= 1;
					repaint();
				}
			}
		}

		if (e.getKeyCode() == e.VK_P) {
			if (Auswahlbereich.stateswitchMittel == 0) {
				SpielbereichMittel2.pausierenstart2();
				Auswahlbereich.stateswitchMittel = 1;
			} else if (Auswahlbereich.stateswitchMittel == 1) {
				SpielbereichMittel2.pausierenende2();
				Auswahlbereich.stateswitchMittel = 0;
			}
		}

		if (recs[a][b].x < image_corner.x && image_corner.x < recs[a][b + 1].x && recs[a][b].y < image_corner.y
				&& image_corner.y < recs[a + 1][b].y) {
			boolean alleangebunden = false;
			int b0 = 0;
			int b1 = 0;
			int b2 = 0;
			int b3 = 0;
			int b4 = 0;
			int b5 = 0;
			int b6 = 0;
			int b7 = 0;
			if (SpielbereichMittel2.angebunden1.size() < 7 && SpielbereichMittel2.angebunden2.size() < 7) {
				alleangebunden = false;
			} else if (SpielbereichMittel2.angebunden1.size() >= 7 && SpielbereichMittel2.angebunden2.size() >= 7) {
				for (int i = 0; i < SpielbereichMittel2.angebunden.size(); i++) {
					for (int j = 0; j < SpielbereichMittel2.angebunden1.size(); j++) {
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 0) {
							alleangebunden = true;
							b0++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 1) {
							alleangebunden = true;
							b1++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 2) {
							alleangebunden = true;
							b2++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 3) {
							alleangebunden = true;
							b3++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 4) {
							alleangebunden = true;
							b4++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 5) {
							alleangebunden = true;
							b5++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 6) {
							alleangebunden = true;
							b6++;
						}
						if (SpielbereichMittel2.angebunden.get(i).get(j) == 7) {
							alleangebunden = true;
							b7++;
						}
					}
				}

			}
			if (b0 == 0 || b1 == 0 || b2 == 0 || b3 == 0 || b4 == 0 || b5 == 0 || b6 == 0 || b7 == 0) {
				alleangebunden = false;
			}
			System.out.println(alleangebunden);
			System.out.println(SpielbereichMittel2.lösung2);
			System.out.println(Auswahlbereich.getZielwert2());
			if (alleangebunden == true && SpielbereichMittel2.lösung2 <= Auswahlbereich.getZielwert2()) {
				Auswahlbereich.timer1.stop();
				SpielbereichMittel2.win2();
				Auswahlbereich.switchpanel2++;
				SpielbereichMittel1 spielbereichMittel1 = new SpielbereichMittel1();
				Spiel3.panel.add(spielbereichMittel1, "2");
				Spiel3.cl.show(Spiel3.panel, "2");
				spielbereichMittel1.requestFocusInWindow();
				spielbereichMittel1.setFocusable(true);
				Auswahlbereich.timer1reset();
				Auswahlbereich.timer1.start();
				Auswahlbereich.score2 += (SpielbereichMittel2.anzahlkanten2 * 2);
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore2());
				SpielbereichMittel1.lösung2 = 0;
				SpielbereichMittel1.anzahlkanten2 = 0;
				Auswahlbereich.level2++;
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.level2);
				// Auswahlbereich.setZielwert2((int)
				// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich1.getGraph())*1.5));
				Auswahlbereich.setZielwert2(Graph.OptimaleLösung1(spielbereichMittel1.getGraph()));
				System.out.println(Graph.OptimaleLösung2(spielbereichMittel1.getGraph()));
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert2());
				spielbereichMittel1 = null;
			} else {
				Auswahlbereich.timer1.stop();
				Auswahlbereich.zeit.setText("Zeit:" + " " + Auswahlbereich.getZeitminuten_string0() + ":"
						+ Auswahlbereich.getZeitsekunden_string0());
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel0());
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert0());
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore0());
				SpielbereichMittel2.anzahlkanten2 = 0;
				SpielbereichMittel2.gameover2();
				Auswahlbereich.switchpanel2 = 0;
				Spiel3.cl.show(Spiel3.panel, "0");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public class ClickListener extends MouseAdapter {

		public void mousePressed(MouseEvent evt) {
			if (mouseclick1 == true) {
				extrarohrmoved = true;
				previousPoint = evt.getPoint();
			}
		}

		public void mouseDragged(MouseEvent evt) {
			if (mouseclick1 == true) {
				Point currentPoint = evt.getPoint();

				image_corner.translate((int) (currentPoint.getX() - previousPoint.getX()),
						(int) (currentPoint.getY() - previousPoint.getY()));

				previousPoint = currentPoint;
				repaint();
			}
		}

		public void mouseReleased(MouseEvent evt) {
			Point currentPoint = evt.getPoint();
			boolean set = false;
			for (int i = 0; i < 7; i++) {

				if (recs[16][1].x < image_corner.x && image_corner.x < recs[16][2].x && recs[16][1].y < image_corner.y
						&& image_corner.y < recs[17][1].y) {
					set = true;
					extrarohrmoved = true;
				}

				if (showedge01 == true) {
					if (recs[16][12].x < image_corner.x && image_corner.x < recs[16][13].x
							&& recs[16][12].y < image_corner.y && image_corner.y < recs[17][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}
				if (showedge02 == true) {
					if (recs[12][1].x < image_corner.x && image_corner.x < recs[12][2].x
							&& recs[12][1].y < image_corner.y && image_corner.y < recs[13][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge13 == true) {
					if (recs[16][12].x < image_corner.x && image_corner.x < recs[16][13].x
							&& recs[16][12].y < image_corner.y && image_corner.y < recs[17][12].y
							|| recs[12][12].x < image_corner.x && image_corner.x < recs[12][13].x
									&& recs[12][12].y < image_corner.y && image_corner.y < recs[13][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge23 == true) {
					if (recs[12][1].x < image_corner.x && image_corner.x < recs[12][2].x
							&& recs[12][1].y < image_corner.y && image_corner.y < recs[13][1].y
							|| recs[12][12].x < image_corner.x && image_corner.x < recs[12][13].x
									&& recs[12][12].y < image_corner.y && image_corner.y < recs[13][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge24 == true) {
					if (recs[12][1].x < image_corner.x && image_corner.x < recs[12][2].x
							&& recs[12][1].y < image_corner.y && image_corner.y < recs[13][1].y
							|| recs[8][1].x < image_corner.x && image_corner.x < recs[8][2].x
									&& recs[8][1].y < image_corner.y && image_corner.y < recs[9][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge35 == true) {
					if (recs[12][12].x < image_corner.x && image_corner.x < recs[12][13].x
							&& recs[12][12].y < image_corner.y && image_corner.y < recs[13][12].y
							|| recs[8][12].x < image_corner.x && image_corner.x < recs[8][13].x
									&& recs[8][12].y < image_corner.y && image_corner.y < recs[9][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge46 == true) {
					if (recs[8][1].x < image_corner.x && image_corner.x < recs[8][2].x && recs[8][1].y < image_corner.y
							&& image_corner.y < recs[9][1].y
							|| recs[4][1].x < image_corner.x && image_corner.x < recs[4][2].x
									&& recs[4][1].y < image_corner.y && image_corner.y < recs[5][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge57 == true) {
					if (recs[8][12].x < image_corner.x && image_corner.x < recs[8][13].x
							&& recs[8][12].y < image_corner.y && image_corner.y < recs[9][12].y
							|| recs[4][12].x < image_corner.x && image_corner.x < recs[4][13].x
									&& recs[4][12].y < image_corner.y && image_corner.y < recs[5][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge67 == true) {
					if (recs[4][1].x < image_corner.x && image_corner.x < recs[4][2].x && recs[4][1].y < image_corner.y
							&& image_corner.y < recs[5][1].y
							|| recs[4][12].x < image_corner.x && image_corner.x < recs[4][13].x
									&& recs[4][12].y < image_corner.y && image_corner.y < recs[5][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

			}
			if (!set) {
				image_corner.move(recs[2][2].x, recs[2][2].y);
				extrarohrmoved = false;
				repaint();
			}

		}

	}

	@Override
	public void componentResized(ComponentEvent e) {
		width = Spiel3.width;
		height = Spiel3.height;
		if (width < height) {
			cellSize = width / ((x2 + 2) * 2);
			offSetX = width / 2 - (cellSize * (x2 + 1));
			offSetY = height / 2 - (cellSize * (y2 + 1));
		}

		if (width > height) { // Passt cellSize an wenn Breite des Panels veraendert wird
			cellSize = height / ((y2 + 2) * 2);
			offSetX = width / 2 - (cellSize * (x2 + 1));
			offSetY = height / 2 - (cellSize * (y2 + 1));
		}
		repaint();

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

}
