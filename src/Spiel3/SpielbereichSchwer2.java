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
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Spiel3.SpielbereichSchwer1.ClickListener;

public class SpielbereichSchwer2 extends JPanel implements ComponentListener, MouseListener, KeyListener {

	private static int cellSize = 25;
	private static int offSetX = cellSize * 2;
	private static int offSetY = cellSize * 2;
	private int x2 = 6;
	private int y2 = 8;
	private int width, height;

	private Point image_corner;
	private Point previousPoint;

	public static Graph graph;

	String gewicht1;
	String gewicht2;
	String gewicht3;
	String gewicht4;
	String gewicht5;
	String gewicht6;
	String gewicht7;
	String gewicht8;
	String gewicht9;
	String gewicht10;
	String gewicht11;
	String gewicht12;
	String gewicht13;

	boolean showimage01 = false;
	boolean showimage02 = false;
	boolean showimage13 = false;
	boolean showimage24 = false;
	boolean showimage35 = false;
	boolean showimage46 = false;
	boolean showimage57 = false;
	boolean showimage68 = false;
	boolean showimage79 = false;
	boolean showimage711 = false;
	boolean showimage89 = false;
	boolean showimage910 = false;
	boolean showimage1011 = false;

	boolean showedge01 = false;
	boolean showedge02 = false;
	boolean showedge13 = false;
	boolean showedge24 = false;
	boolean showedge35 = false;
	boolean showedge46 = false;
	boolean showedge57 = false;
	boolean showedge68 = false;
	boolean showedge79 = false;
	boolean showedge711 = false;
	boolean showedge89 = false;
	boolean showedge910 = false;
	boolean showedge1011 = false;

	boolean extrarohrmoved = false;

	public static int anzahlkanten3 = 0;
	public static int lösung3 = 0;

	public static boolean mouseclick2 = true;

	public static ArrayList<ArrayList<Integer>> angebunden;
	public static ArrayList<Integer> angebunden1;
	public static ArrayList<Integer> angebunden2;

	Stack<String> abwählen;

	private Rectangle[][] recs = new Rectangle[18][14];

	public SpielbereichSchwer2() {
		addMouseListener(this);
		addKeyListener(this);
		addComponentListener(this);
		setFocusable(true);
		graph = new Graph(12, 2);

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

		width = Spiel3.getPanelWidth();
		height = Spiel3.getPanelHeight();

		ClickListener clicklistener = new ClickListener();
		this.addMouseListener(clicklistener);
		this.addMouseMotionListener(clicklistener);

		image_corner = new Point(recs[1][1].x, recs[1][1].y);

		Graph.loadimage();
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
				g.fillOval(recs[16][4].x, recs[16][4].y, cellSize, cellSize);
				g.drawString("1", recs[16][4].x, recs[16][4].y);
			} else if (i == 2) {
				g.setColor(Color.blue);
				g.fillOval(recs[13][1].x, recs[13][1].y, cellSize, cellSize);
				g.drawString("2", recs[13][1].x, recs[13][1].y);
			} else if (i == 3) {
				g.setColor(Color.blue);
				g.fillOval(recs[13][4].x, recs[13][4].y, cellSize, cellSize);
				g.drawString("3", recs[13][4].x, recs[13][4].y);
			} else if (i == 4) {
				g.setColor(Color.blue);
				g.fillOval(recs[10][1].x, recs[10][1].y, cellSize, cellSize);
				g.drawString("4", recs[10][1].x, recs[10][1].y);
			} else if (i == 5) {
				g.setColor(Color.blue);
				g.fillOval(recs[10][4].x, recs[10][4].y, cellSize, cellSize);
				g.drawString("5", recs[10][4].x, recs[10][4].y);
			} else if (i == 6) {
				g.setColor(Color.blue);
				g.fillOval(recs[7][1].x, recs[7][1].y, cellSize, cellSize);
				g.drawString("6", recs[7][1].x, recs[7][1].y);
			} else if (i == 7) {
				g.setColor(Color.blue);
				g.fillOval(recs[7][4].x, recs[7][4].y, cellSize, cellSize);
				g.drawString("7", recs[7][4].x, recs[7][4].y);
			} else if (i == 8) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][1].x, recs[4][1].y, cellSize, cellSize);
				g.drawString("8", recs[4][1].x, recs[4][1].y);
			} else if (i == 9) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][4].x, recs[4][4].y, cellSize, cellSize);
				g.drawString("9", recs[4][4].x, recs[4][4].y);
			} else if (i == 10) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][7].x, recs[4][7].y, cellSize, cellSize);
				g.drawString("10", recs[4][7].x, recs[4][7].y);
			} else if (i == 11) {
				g.setColor(Color.blue);
				g.fillOval(recs[7][7].x, recs[7][7].y, cellSize, cellSize);
				g.drawString("11", recs[7][7].x, recs[7][7].y);
			}

		}
		for (int i = 0; i < graph.knotenanzahl; i++) {
			for (int j = 0; j < graph.knotenanzahl; j++) {
				if (i == 0 && j == 1) {
					g.setColor(Color.black);
					g.drawLine(recs[16][2].x, recs[16][2].y + (cellSize / 2), recs[16][4].x,
							recs[16][4].y + (cellSize / 2));
					gewicht1 = String.valueOf(graph.adjazenzmatrix[0][1]);
					g.drawString(gewicht1, (recs[16][2].x + recs[16][4].x) / 2,
							(recs[16][2].y + (cellSize / 2) + recs[16][4].y + (cellSize / 2)) / 2);
				} else if (i == 0 && j == 2) {
					g.setColor(Color.black);
					g.drawLine(recs[14][1].x + (cellSize / 2), recs[14][1].y, recs[16][1].x + (cellSize / 2),
							recs[16][1].y);
					gewicht2 = String.valueOf(graph.adjazenzmatrix[0][2]);
					g.drawString(gewicht2, (recs[14][1].x + (cellSize / 2) + recs[16][1].x + (cellSize / 2)) / 2,
							(recs[14][1].y + recs[16][1].y) / 2);
				} else if (i == 1 && j == 3) {
					g.setColor(Color.black);
					g.drawLine(recs[14][4].x + (cellSize / 2), recs[14][4].y, recs[16][4].x + (cellSize / 2),
							recs[16][4].y);
					gewicht3 = String.valueOf(graph.adjazenzmatrix[1][3]);
					g.drawString(gewicht3, (recs[14][4].x + (cellSize / 2) + recs[16][4].x + (cellSize / 2)) / 2,
							(recs[14][4].y + recs[16][4].y) / 2);
				} else if (i == 2 && j == 4) {
					g.setColor(Color.black);
					g.drawLine(recs[11][1].x + (cellSize / 2), recs[11][1].y, recs[13][1].x + (cellSize / 2),
							recs[13][1].y);
					gewicht4 = String.valueOf(graph.adjazenzmatrix[2][4]);
					g.drawString(gewicht4, (recs[11][1].x + (cellSize / 2) + recs[13][1].x + (cellSize / 2)) / 2,
							(recs[11][1].y + recs[13][1].y) / 2);
				} else if (i == 3 && j == 5) {
					g.setColor(Color.black);
					g.drawLine(recs[11][4].x + (cellSize / 2), recs[11][4].y, recs[13][4].x + (cellSize / 2),
							recs[13][4].y);
					gewicht5 = String.valueOf(graph.adjazenzmatrix[3][5]);
					g.drawString(gewicht5, (recs[11][4].x + (cellSize / 2) + recs[13][4].x + (cellSize / 2)) / 2,
							(recs[11][4].y + recs[13][4].y) / 2);
				} else if (i == 4 && j == 6) {
					g.setColor(Color.black);
					g.drawLine(recs[8][1].x + (cellSize / 2), recs[8][1].y, recs[10][1].x + (cellSize / 2),
							recs[10][1].y);
					gewicht6 = String.valueOf(graph.adjazenzmatrix[4][6]);
					g.drawString(gewicht6, (recs[8][1].x + (cellSize / 2) + recs[10][1].x + (cellSize / 2)) / 2,
							(recs[8][1].y + recs[10][1].y) / 2);
				} else if (i == 5 && j == 7) {
					g.setColor(Color.black);
					g.drawLine(recs[8][4].x + (cellSize / 2), recs[8][4].y, recs[10][4].x + (cellSize / 2),
							recs[10][4].y);
					gewicht7 = String.valueOf(graph.adjazenzmatrix[5][7]);
					g.drawString(gewicht7, (recs[8][4].x + (cellSize / 2) + recs[10][4].x + (cellSize / 2)) / 2,
							(recs[8][4].y + recs[10][4].y) / 2);
				} else if (i == 6 && j == 8) {
					g.setColor(Color.black);
					g.drawLine(recs[5][1].x + (cellSize / 2), recs[5][1].y, recs[7][1].x + (cellSize / 2),
							recs[7][1].y);
					gewicht8 = String.valueOf(graph.adjazenzmatrix[6][8]);
					g.drawString(gewicht8, (recs[5][1].x + (cellSize / 2) + recs[7][1].x + (cellSize / 2)) / 2,
							(recs[5][1].y + recs[7][1].y) / 2);
				} else if (i == 7 && j == 9) {
					g.setColor(Color.black);
					g.drawLine(recs[5][4].x + (cellSize / 2), recs[5][4].y, recs[7][4].x + (cellSize / 2),
							recs[7][4].y);
					gewicht9 = String.valueOf(graph.adjazenzmatrix[7][9]);
					g.drawString(gewicht9, (recs[5][4].x + (cellSize / 2) + recs[7][4].x + (cellSize / 2)) / 2,
							(recs[5][4].y + recs[7][4].y) / 2);
				} else if (i == 7 && j == 11) {
					g.setColor(Color.black);
					g.drawLine(recs[7][5].x, recs[7][5].y + (cellSize / 2), recs[7][7].x,
							recs[7][7].y + (cellSize / 2));
					gewicht10 = String.valueOf(graph.adjazenzmatrix[7][11]);
					g.drawString(gewicht10, (recs[7][5].x + recs[7][7].x) / 2,
							(recs[7][5].y + (cellSize / 2) + recs[7][7].y + (cellSize / 2)) / 2);
				} else if (i == 8 && j == 9) {
					g.setColor(Color.black);
					g.drawLine(recs[4][2].x, recs[4][2].y + (cellSize / 2), recs[4][4].x,
							recs[4][4].y + (cellSize / 2));
					gewicht11 = String.valueOf(graph.adjazenzmatrix[8][9]);
					g.drawString(gewicht11, (recs[4][2].x + recs[4][4].x) / 2,
							(recs[4][2].y + (cellSize / 2) + recs[4][4].y + (cellSize / 2)) / 2);
				} else if (i == 9 && j == 10) {
					g.setColor(Color.black);
					g.drawLine(recs[4][5].x, recs[4][5].y + (cellSize / 2), recs[4][7].x,
							recs[4][7].y + (cellSize / 2));
					gewicht12 = String.valueOf(graph.adjazenzmatrix[9][10]);
					g.drawString(gewicht12, (recs[4][5].x + recs[4][7].x) / 2,
							(recs[4][5].y + (cellSize / 2) + recs[4][7].y + (cellSize / 2)) / 2);
				} else if (i == 10 && j == 11) {
					g.setColor(Color.black);
					g.drawLine(recs[5][7].x + (cellSize / 2), recs[5][7].y, recs[7][7].x + (cellSize / 2),
							recs[7][7].y);
					gewicht13 = String.valueOf(graph.adjazenzmatrix[10][11]);
					g.drawString(gewicht13, (recs[5][7].x + (cellSize / 2) + recs[7][7].x + (cellSize / 2)) / 2,
							(recs[5][7].y + recs[7][7].y) / 2);
				}

			}
		}
		if (extrarohrmoved == false) {
			image_corner = new Point(recs[1][1].x, recs[1][1].y);
		}

		g.drawImage(Graph.getLüftungssystem(), recs[a][b].x, recs[a][b].y, cellSize, cellSize, null);

		Image newextrarohr = Graph.getExtrarohr1().getImage();
		newextrarohr = newextrarohr.getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_REPLICATE);
		Graph.setExtrarohr1(new ImageIcon(newextrarohr));
		Graph.getExtrarohr1().paintIcon(this, g, image_corner.x, image_corner.y);

		if (showimage01 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[16][2].x, recs[16][2].y, recs[16][1].x, cellSize, null);
		}
		if (showimage02 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[14][1].x, recs[14][1].y, cellSize, recs[16][1].x, null);
		}
		if (showimage13 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[14][4].x, recs[14][4].y, cellSize, recs[14][1].x, null);
		}
		if (showimage24 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[11][1].x, recs[11][1].y, cellSize, recs[11][1].x, null);
		}
		if (showimage35 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[11][4].x, recs[11][4].y, cellSize, recs[11][1].x, null);
		}
		if (showimage46 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[8][1].x, recs[8][1].y, cellSize, recs[8][1].x, null);
		}
		if (showimage57 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[8][4].x, recs[8][4].y, cellSize, recs[8][1].x, null);
		}
		if (showimage68 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][1].x, recs[5][1].y, cellSize, recs[5][1].x, null);
		}
		if (showimage79 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][4].x, recs[5][4].y, cellSize, recs[5][1].x, null);
		}
		if (showimage711 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[7][5].x, recs[7][5].y, recs[7][1].x, cellSize, null);
		}
		if (showimage89 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[4][2].x, recs[4][2].y, recs[4][1].x, cellSize, null);
		}
		if (showimage910 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[4][5].x, recs[4][5].y, recs[4][1].x, cellSize, null);
		}
		if (showimage1011 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][7].x, recs[5][7].y, cellSize, recs[5][1].x, null);
		}
	}

	int counter01 = 0;
	int counter02 = 0;
	int counter13 = 0;
	int counter24 = 0;
	int counter35 = 0;
	int counter46 = 0;
	int counter57 = 0;
	int counter68 = 0;
	int counter79 = 0;
	int counter711 = 0;
	int counter89 = 0;
	int counter910 = 0;
	int counter1011 = 0;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (mouseclick2 == true) {
			if (recs[16][2].x < e.getX() && e.getX() < recs[16][4].x && recs[16][2].y < e.getY()
					&& e.getY() < recs[17][2].y) {
				if (counter01 == 0) {
					showimage01 = true;
					repaint();
					lösung3 += Integer.valueOf(gewicht1);
					anzahlkanten3 += 1;
					counter01 = 1;
					showedge01 = true;
					abwählen.push("a");
					angebunden1.add(0);
					angebunden2.add(1);
				} else if (counter01 == 1) {
					if (abwählen.peek() == "a") {
						showimage01 = false;
						repaint();
						lösung3 -= Integer.valueOf(gewicht1);
						anzahlkanten3 -= 1;
						counter01 = 0;
						showedge01 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}

			if (recs[14][1].y < e.getY() && e.getY() < recs[16][1].y && recs[14][1].x < e.getX()
					&& e.getX() < recs[14][2].x) {
				if (counter02 == 0) {
					showimage02 = true;
					repaint();
					lösung3 += Integer.valueOf(gewicht2);
					anzahlkanten3 += 1;
					counter02 = 1;
					showedge02 = true;
					abwählen.push("b");
					angebunden1.add(0);
					angebunden2.add(2);
				} else if (counter02 == 1) {
					if (abwählen.peek() == "b") {
						showimage02 = false;
						repaint();
						lösung3 -= Integer.valueOf(gewicht2);
						anzahlkanten3 -= 1;
						counter02 = 0;
						showedge02 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();
					}
				}
			}

			if (showedge01 == true || showedge35 == true) {
				if (recs[14][4].y < e.getY() && e.getY() < recs[16][4].y && recs[14][4].x < e.getX()
						&& e.getX() < recs[14][5].x) {
					if (counter13 == 0) {
						showimage13 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht3);
						anzahlkanten3 += 1;
						counter13 = 1;
						showedge13 = true;
						abwählen.push("c");
						angebunden1.add(1);
						angebunden2.add(3);
					} else if (counter13 == 1) {
						if (abwählen.peek() == "c") {
							showimage13 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht3);
							anzahlkanten3 -= 1;
							counter13 = 0;
							showedge13 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge02 == true || showedge46 == true) {
				if (recs[11][1].y < e.getY() && e.getY() < recs[13][1].y && recs[11][1].x < e.getX()
						&& e.getX() < recs[11][2].x) {
					if (counter24 == 0) {
						showimage24 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht4);
						anzahlkanten3 += 1;
						counter24 = 1;
						showedge24 = true;
						abwählen.push("d");
						angebunden1.add(2);
						angebunden2.add(4);
					} else if (counter24 == 1) {
						if (abwählen.peek() == "d") {
							showimage24 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht4);
							anzahlkanten3 -= 1;
							counter24 = 0;
							showedge24 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}
			if (showedge13 == true || showedge57 == true) {
				if (recs[11][4].y < e.getY() && e.getY() < recs[13][4].y && recs[11][4].x < e.getX()
						&& e.getX() < recs[11][5].x) {
					if (counter35 == 0) {
						showimage35 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht5);
						anzahlkanten3 += 1;
						counter35 = 1;
						showedge35 = true;
						abwählen.push("e");
						angebunden1.add(3);
						angebunden2.add(5);
					} else if (counter35 == 1) {
						if (abwählen.peek() == "e") {
							showimage35 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht5);
							anzahlkanten3 -= 1;
							counter35 = 0;
							showedge35 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge24 == true || showedge68 == true) {
				if (recs[8][1].y < e.getY() && e.getY() < recs[10][1].y && recs[8][1].x < e.getX()
						&& e.getX() < recs[8][2].x) {
					if (counter46 == 0) {
						showimage46 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht6);
						anzahlkanten3 += 1;
						counter46 = 1;
						showedge46 = true;
						abwählen.push("f");
						angebunden1.add(4);
						angebunden2.add(6);
					} else if (counter46 == 1) {
						if (abwählen.peek() == "f") {
							showimage46 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht6);
							anzahlkanten3 -= 1;
							counter46 = 0;
							showedge46 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge35 == true || showedge79 == true || showedge711 == true) {
				if (recs[8][4].y < e.getY() && e.getY() < recs[10][4].y && recs[8][4].x < e.getX()
						&& e.getX() < recs[8][5].x) {
					if (counter57 == 0) {
						showimage57 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht7);
						anzahlkanten3 += 1;
						counter57 = 1;
						showedge57 = true;
						abwählen.push("g");
						angebunden1.add(5);
						angebunden2.add(7);
					} else if (counter57 == 1) {
						if (abwählen.peek() == "g") {
							showimage57 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht7);
							anzahlkanten3 -= 1;
							counter57 = 0;
							showedge57 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge46 == true || showedge89 == true) {
				if (recs[5][1].y < e.getY() && e.getY() < recs[7][1].y && recs[5][1].x < e.getX()
						&& e.getX() < recs[5][2].x) {
					if (counter68 == 0) {
						showimage68 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht8);
						anzahlkanten3 += 1;
						counter68 = 1;
						showedge68 = true;
						abwählen.push("h");
						angebunden1.add(6);
						angebunden2.add(8);
					} else if (counter57 == 1) {
						if (abwählen.peek() == "h") {
							showimage68 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht8);
							anzahlkanten3 -= 1;
							counter68 = 0;
							showedge68 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge57 == true || showedge89 == true || showedge910 == true || showedge711 == true) {
				if (recs[5][4].y < e.getY() && e.getY() < recs[7][4].y && recs[5][4].x < e.getX()
						&& e.getX() < recs[5][5].x) {
					if (counter79 == 0) {
						showimage79 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht9);
						anzahlkanten3 += 1;
						counter79 = 1;
						showedge79 = true;
						abwählen.push("i");
						angebunden1.add(7);
						angebunden2.add(9);
					} else if (counter79 == 1) {
						if (abwählen.peek() == "i") {
							showimage79 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht9);
							anzahlkanten3 -= 1;
							counter79 = 0;
							showedge79 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge57 == true || showedge79 == true || showedge1011 == true) {
				if (recs[7][5].x < e.getX() && e.getX() < recs[7][7].x && recs[7][5].y < e.getY()
						&& e.getY() < recs[8][5].y) {
					if (counter711 == 0) {
						showimage711 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht10);
						anzahlkanten3 += 1;
						counter711 = 1;
						showedge711 = true;
						abwählen.push("j");
						angebunden1.add(7);
						angebunden2.add(11);
					} else if (counter711 == 1) {
						if (abwählen.peek() == "j") {
							showimage711 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht10);
							anzahlkanten3 -= 1;
							counter711 = 0;
							showedge711 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge68 == true || showedge79 == true || showedge910 == true) {
				if (recs[4][2].x < e.getX() && e.getX() < recs[4][4].x && recs[4][2].y < e.getY()
						&& e.getY() < recs[5][2].y) {
					if (counter89 == 0) {
						showimage89 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht11);
						anzahlkanten3 += 1;
						counter89 = 1;
						showedge89 = true;
						abwählen.push("k");
						angebunden1.add(8);
						angebunden2.add(9);
					} else if (counter89 == 1) {
						if (abwählen.peek() == "k") {
							showimage89 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht11);
							anzahlkanten3 -= 1;
							counter89 = 0;
							showedge89 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge89 == true || showedge79 == true || showedge1011 == true) {
				if (recs[7][5].x < e.getX() && e.getX() < recs[7][7].x && recs[7][5].y < e.getY()
						&& e.getY() < recs[8][5].y) {
					if (counter910 == 0) {
						showimage910 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht12);
						anzahlkanten3 += 1;
						counter910 = 1;
						showedge910 = true;
						abwählen.push("l");
						angebunden1.add(9);
						angebunden2.add(10);
					} else if (counter910 == 1) {
						if (abwählen.peek() == "l") {
							showimage910 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht12);
							anzahlkanten3 -= 1;
							counter910 = 0;
							showedge910 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge711 == true || showedge910 == true) {
				if (recs[5][7].y < e.getY() && e.getY() < recs[7][7].y && recs[5][7].x < e.getX()
						&& e.getX() < recs[5][8].x) {
					if (counter1011 == 0) {
						showimage1011 = true;
						repaint();
						lösung3 += Integer.valueOf(gewicht13);
						anzahlkanten3 += 1;
						counter1011 = 1;
						showedge1011 = true;
						abwählen.push("m");
						angebunden1.add(10);
						angebunden2.add(11);
					} else if (counter1011 == 1) {
						if (abwählen.peek() == "m") {
							showimage1011 = false;
							repaint();
							lösung3 -= Integer.valueOf(gewicht13);
							anzahlkanten3 -= 1;
							counter1011 = 0;
							showedge1011 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

		}

	}

	public static void pausierenstart3() {
		Auswahlbereich.timer1.stop();
		SpielbereichMittel1.mouseclick1 = false;
	}

	public static void pausierenende3() {
		Auswahlbereich.timer1.start();
		SpielbereichMittel1.mouseclick1 = true;
	}

	public static void gameover3() {
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

	public static void win3() {
		JFrame frame = new JFrame("You won");
		JOptionPane.showMessageDialog(frame, "Du hast gewonnen", "You wonr", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
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

	public class ClickListener extends MouseAdapter {

		public void mousePressed(MouseEvent evt) {
			if (mouseclick2 == true) {
				previousPoint = evt.getPoint();
				if (recs[1][1].x < previousPoint.x && previousPoint.x < recs[1][2].x && recs[1][1].y < previousPoint.y
						&& previousPoint.y < recs[2][1].y) {
					extrarohrmoved = true;
				}
			}
		}

		public void mouseDragged(MouseEvent evt) {
			if (mouseclick2 == true) {
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
					if (recs[16][4].x < image_corner.x && image_corner.x < recs[16][5].x
							&& recs[16][4].y < image_corner.y && image_corner.y < recs[17][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}
				if (showedge02 == true) {
					if (recs[13][1].x < image_corner.x && image_corner.x < recs[13][2].x
							&& recs[13][1].y < image_corner.y && image_corner.y < recs[14][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge13 == true) {
					if (recs[16][4].x < image_corner.x && image_corner.x < recs[16][5].x
							&& recs[16][4].y < image_corner.y && image_corner.y < recs[17][4].y
							|| recs[13][4].x < image_corner.x && image_corner.x < recs[13][5].x
									&& recs[13][4].y < image_corner.y && image_corner.y < recs[14][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge24 == true) {
					if (recs[13][1].x < image_corner.x && image_corner.x < recs[13][2].x
							&& recs[13][1].y < image_corner.y && image_corner.y < recs[14][1].y
							|| recs[10][1].x < image_corner.x && image_corner.x < recs[10][2].x
									&& recs[10][1].y < image_corner.y && image_corner.y < recs[11][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge35 == true) {
					if (recs[13][4].x < image_corner.x && image_corner.x < recs[13][5].x
							&& recs[13][4].y < image_corner.y && image_corner.y < recs[14][4].y
							|| recs[10][4].x < image_corner.x && image_corner.x < recs[10][5].x
									&& recs[10][4].y < image_corner.y && image_corner.y < recs[11][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge46 == true) {
					if (recs[10][1].x < image_corner.x && image_corner.x < recs[10][2].x
							&& recs[10][1].y < image_corner.y && image_corner.y < recs[11][1].y
							|| recs[7][1].x < image_corner.x && image_corner.x < recs[7][2].x
									&& recs[7][1].y < image_corner.y && image_corner.y < recs[8][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge57 == true) {
					if (recs[10][4].x < image_corner.x && image_corner.x < recs[10][5].x
							&& recs[10][4].y < image_corner.y && image_corner.y < recs[11][4].y
							|| recs[7][4].x < image_corner.x && image_corner.x < recs[7][5].x
									&& recs[7][4].y < image_corner.y && image_corner.y < recs[8][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge68 == true) {
					if (recs[7][1].x < image_corner.x && image_corner.x < recs[7][2].x && recs[7][1].y < image_corner.y
							&& image_corner.y < recs[8][1].y
							|| recs[4][1].x < image_corner.x && image_corner.x < recs[4][2].x
									&& recs[4][1].y < image_corner.y && image_corner.y < recs[5][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge79 == true) {
					if (recs[7][4].x < image_corner.x && image_corner.x < recs[7][5].x
							&& recs[7][4].y < image_corner.y && image_corner.y < recs[8][4].y
							|| recs[4][4].x < image_corner.x && image_corner.x < recs[4][5].x
									&& recs[4][4].y < image_corner.y && image_corner.y < recs[5][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}
				
				if (showedge711 == true) {
					if (recs[7][4].x < image_corner.x && image_corner.x < recs[7][5].x
							&& recs[7][4].y < image_corner.y && image_corner.y < recs[8][4].y
							|| recs[7][7].x < image_corner.x && image_corner.x < recs[7][8].x
									&& recs[7][7].y < image_corner.y && image_corner.y < recs[8][7].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge89 == true) {
					if (recs[4][1].x < image_corner.x && image_corner.x < recs[4][2].x
							&& recs[4][1].y < image_corner.y && image_corner.y < recs[5][1].y
							|| recs[4][4].x < image_corner.x && image_corner.x < recs[4][5].x
									&& recs[4][4].y < image_corner.y && image_corner.y < recs[5][4].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge910 == true) {
					if (recs[4][4].x < image_corner.x && image_corner.x < recs[4][5].x
							&& recs[4][4].y < image_corner.y && image_corner.y < recs[5][4].y
							|| recs[4][7].x < image_corner.x && image_corner.x < recs[4][8].x
									&& recs[4][7].y < image_corner.y && image_corner.y < recs[5][7].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge1011 == true) {
					if (recs[4][7].x < image_corner.x && image_corner.x < recs[4][8].x
							&& recs[4][7].y < image_corner.y && image_corner.y < recs[5][7].y
							|| recs[7][7].x < image_corner.x && image_corner.x < recs[7][8].x
									&& recs[7][7].y < image_corner.y && image_corner.y < recs[8][7].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

			}
			if (!set) {
				image_corner.move(recs[1][1].x, recs[1][1].y);
				extrarohrmoved = false;
				repaint();
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (mouseclick2 == true) {
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
				SpielbereichMittel1.pausierenstart2();
				Auswahlbereich.stateswitchMittel = 1;
			} else if (Auswahlbereich.stateswitchMittel == 1) {
				SpielbereichMittel1.pausierenende2();
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
			int b8 = 0;
			int b9 = 0;
			int b10 = 0;
			int b11 = 0;
			if (SpielbereichSchwer2.angebunden1.size() < 11 && SpielbereichSchwer2.angebunden2.size() < 11) {
				alleangebunden = false;
			} else if (SpielbereichSchwer2.angebunden1.size() >= 11 && SpielbereichSchwer2.angebunden2.size() >= 11) {
				for (int i = 0; i < SpielbereichSchwer2.angebunden.size(); i++) {
					for (int j = 0; j < SpielbereichSchwer2.angebunden1.size(); j++) {
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 0) {
							alleangebunden = true;
							b0++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 1) {
							alleangebunden = true;
							b1++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 2) {
							alleangebunden = true;
							b2++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 3) {
							alleangebunden = true;
							b3++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 4) {
							alleangebunden = true;
							b4++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 5) {
							alleangebunden = true;
							b5++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 6) {
							alleangebunden = true;
							b6++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 7) {
							alleangebunden = true;
							b7++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 8) {
							alleangebunden = true;
							b8++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 9) {
							alleangebunden = true;
							b9++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 10) {
							alleangebunden = true;
							b10++;
						}
						if (SpielbereichSchwer2.angebunden.get(i).get(j) == 11) {
							alleangebunden = true;
							b11++;
						}
					}
				}

			}
			if (b0 == 0 || b1 == 0 || b2 == 0 || b3 == 0 || b4 == 0 || b5 == 0 || b6 == 0 || b7 == 0 || b8 == 0 || b9 == 0 || b10 == 0 || b11 == 0) {
				alleangebunden = false;
			}
			System.out.println(alleangebunden);
			System.out.println(SpielbereichSchwer2.lösung3);
			System.out.println(Auswahlbereich.getZielwert3());
			if (alleangebunden == true && SpielbereichSchwer2.lösung3 <= Auswahlbereich.getZielwert3()) {
				Auswahlbereich.timer2.stop();
				SpielbereichSchwer2.win3();
				Auswahlbereich.switchpanel3++;
				SpielbereichSchwer1 spielbereichSchwer1 = new SpielbereichSchwer1();
				Spiel3.panel.add(spielbereichSchwer1, "5");
				Spiel3.cl.show(Spiel3.panel, "5");
				spielbereichSchwer1.requestFocusInWindow();
				spielbereichSchwer1.setFocusable(true);
				Auswahlbereich.timer2reset();
				Auswahlbereich.timer2.start();
				Auswahlbereich.score3 += (SpielbereichSchwer2.anzahlkanten3 * 3);
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore2());
				SpielbereichSchwer1.lösung3 = 0;
				SpielbereichSchwer1.anzahlkanten3 = 0;
				Auswahlbereich.level3++;
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.level3);
				// Auswahlbereich.setZielwert2((int)
				// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich1.getGraph())*1.5));
				Auswahlbereich.setZielwert3(Graph.OptimaleLösung1(spielbereichSchwer1.graph));
				System.out.println(Graph.OptimaleLösung2(spielbereichSchwer1.graph));
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert3());
				spielbereichSchwer1 = null;
			} else {
				Auswahlbereich.timer2.stop();
				Auswahlbereich.zeit.setText("Zeit:" + " " + Auswahlbereich.getZeitminuten_string0() + ":"
						+ Auswahlbereich.getZeitsekunden_string0());
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel0());
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert0());
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore0());
				SpielbereichSchwer2.anzahlkanten3 = 0;
				SpielbereichSchwer2.gameover3();
				Auswahlbereich.switchpanel3 = 0;
				Spiel3.cl.show(Spiel3.panel, "0");
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
