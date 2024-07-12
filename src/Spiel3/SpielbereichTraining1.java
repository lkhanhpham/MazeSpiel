package Spiel3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop.Action;
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
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SpielbereichTraining1 extends JPanel implements MouseListener, KeyListener, ComponentListener {
	private static int cellSize = 25;
	private static int offSetX = cellSize * 2;
	private static int offSetY = cellSize * 2;
	private int x2 = 6;
	private int y2 = 8;

	private Point image_corner;
	private Point previousPoint;

	private int width, height;

	private static Graph graph;

	private boolean showimage01 = false;
	private boolean showimage02 = false;
	private boolean showimage13 = false;
	private boolean showimage23 = false;

	public static boolean mouseclick = true;

	private String gewicht1;
	private String gewicht2;
	private String gewicht3;
	private String gewicht4;

	public static int lösung1 = 0;

	private int counter01 = 0;
	private int counter02 = 0;
	private int counter13 = 0;
	private int counter23 = 0;

	private boolean showedge01 = false;
	private boolean showedge02 = false;
	private boolean showedge13 = false;
	private boolean showedge23 = false;
	private Rectangle[][] recs = new Rectangle[18][14];

	private boolean extrarohrmoved = false;

	private static int anzahlkanten1 = 0;
	Stack<String> abwählen;
	public static ArrayList<ArrayList<Integer>> angebunden;
	public static ArrayList<Integer> angebunden1;
	public static ArrayList<Integer> angebunden2;

	public SpielbereichTraining1() {
		cellSize = 25;
		offSetX = cellSize * 2;
		offSetY = cellSize * 2;

		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);

		angebunden = new ArrayList<ArrayList<Integer>>();
		angebunden1 = new ArrayList<Integer>();
		angebunden2 = new ArrayList<Integer>();
		angebunden.add(angebunden1);
		angebunden.add(angebunden2);

		abwählen = new Stack<String>();
		graph = new Graph(4, 1);

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

		addComponentListener(this);

		ClickListener clicklistener = new ClickListener();
		this.addMouseListener(clicklistener);
		this.addMouseMotionListener(clicklistener);

		width = Spiel3.getPanelWidth();
		height = Spiel3.getPanelHeight();

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
				g.fillOval(recs[4][1].x, recs[4][1].y, cellSize, cellSize);
				g.drawString("0", recs[4][1].x, recs[4][1].y);
			} else if (i == 1) {
				g.setColor(Color.blue);
				g.fillOval(recs[4][12].x, recs[4][12].y, cellSize, cellSize);
				g.drawString("1", recs[4][12].x, recs[4][12].y);
			} else if (i == 2) {
				g.setColor(Color.blue);
				g.fillOval(recs[16][1].x, recs[16][1].y, cellSize, cellSize);
				g.drawString("2", recs[16][1].x, recs[16][1].y);
			} else if (i == 3) {
				g.setColor(Color.blue);
				g.fillOval(recs[16][12].x, recs[16][12].y, cellSize, cellSize);
				g.drawString("3", recs[16][12].x, recs[16][12].y);
			}

		}

		for (int i = 0; i < graph.knotenanzahl; i++) {
			for (int j = 0; j < graph.knotenanzahl; j++) {
				if (i == 0 && j == 1) {
					g.setColor(Color.black);
					g.drawLine(recs[4][2].x, recs[4][2].y + (cellSize / 2), recs[4][12].x,
							recs[4][12].y + (cellSize / 2));
					gewicht1 = String.valueOf(graph.adjazenzmatrix[0][1]);
					g.drawString(gewicht1, (recs[4][2].x + recs[4][12].x) / 2,
							(recs[4][2].y + (cellSize / 2) + recs[4][12].y + (cellSize / 2)) / 2);
				} else if (i == 0 && j == 2) {
					g.setColor(Color.black);
					g.drawLine(recs[5][1].x + (cellSize / 2), recs[5][1].y, recs[16][1].x + (cellSize / 2),
							recs[16][1].y);
					gewicht2 = String.valueOf(graph.adjazenzmatrix[0][2]);
					g.drawString(gewicht2, (recs[5][1].x + (cellSize / 2) + recs[16][1].x + (cellSize / 2)) / 2,
							(recs[5][1].y + recs[16][1].y) / 2);
				} else if (i == 1 && j == 3) {
					g.setColor(Color.black);
					g.drawLine(recs[5][12].x + (cellSize / 2), recs[5][12].y, recs[16][12].x + (cellSize / 2),
							recs[16][12].y);
					gewicht3 = String.valueOf(graph.adjazenzmatrix[1][3]);
					g.drawString(gewicht3, (recs[5][12].x + (cellSize / 2) + recs[16][12].x + (cellSize / 2)) / 2,
							(recs[5][12].y + recs[16][12].y) / 2);
				} else if (i == 2 && j == 3) {
					g.setColor(Color.black);
					g.drawLine(recs[16][2].x, recs[16][2].y + (cellSize / 2), recs[16][12].x,
							recs[16][12].y + (cellSize / 2));
					gewicht4 = String.valueOf(graph.adjazenzmatrix[2][3]);
					g.drawString(gewicht4, (recs[16][1].x + recs[16][12].x) / 2,
							(recs[16][12].y + (cellSize / 2) + recs[16][12].y + (cellSize / 2)) / 2);
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
			g.drawImage(Graph.getRohrhorizontal(), recs[4][2].x, recs[4][2].y, recs[4][9].x, cellSize, null);
		}
		if (showimage02 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][1].x, recs[5][1].y, cellSize, recs[1][10].x, null);
		}
		if (showimage13 == true) {
			g.drawImage(Graph.getRohrvertikal(), recs[5][12].x, recs[5][12].y, cellSize, recs[12][10].x, null);
		}
		if (showimage23 == true) {
			g.drawImage(Graph.getRohrhorizontal(), recs[16][2].x, recs[16][2].y, recs[16][9].x, cellSize, null);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (mouseclick == true) {
			if (recs[5][1].y < e.getY() && e.getY() < recs[15][1].y && recs[5][1].x < e.getX()
					&& e.getX() < recs[5][2].x) {
				if (counter02 == 0) {
					showimage02 = true;
					repaint();
					lösung1 += Integer.valueOf(gewicht2);
					anzahlkanten1 += 1;
					counter02 = 1;
					showedge02 = true;
					angebunden1.add(0);
					angebunden2.add(2);
					abwählen.push("b");
				} else if (counter02 == 1) {
					if (abwählen.peek() == "b") {
						showimage02 = false;
						repaint();
						lösung1 -= Integer.valueOf(gewicht2);
						anzahlkanten1 -= 1;
						counter02 = 0;
						showedge02 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();

					}
				}
			}

			if (recs[4][2].x < e.getX() && e.getX() < recs[4][12].x && recs[4][2].y < e.getY()
					&& e.getY() < recs[5][2].y) {
				if (counter01 == 0) {
					showimage01 = true;
					repaint();
					lösung1 += Integer.valueOf(gewicht1);
					anzahlkanten1 += 1;
					showedge01 = true;
					counter01 = 1;
					angebunden1.add(0);
					angebunden2.add(1);
					abwählen.push("a");
				} else if (counter01 == 1) {
					if (abwählen.peek() == "a") {
						showimage01 = false;
						lösung1 -= Integer.valueOf(gewicht1);
						anzahlkanten1 -= 1;
						counter01 = 0;
						showedge01 = false;
						angebunden1.remove(angebunden1.size() - 1);
						angebunden2.remove(angebunden2.size() - 1);
						abwählen.pop();

					}
				}
			}

			if (showedge01 == true || showedge23 == true) {
				if (recs[5][12].y < e.getY() && e.getY() < recs[15][12].y && recs[5][12].x < e.getX()
						&& e.getX() < recs[5][13].x) {
					if (counter13 == 0) {
						showimage13 = true;
						repaint();
						lösung1 += Integer.valueOf(gewicht3);
						anzahlkanten1 += 1;
						counter13 = 1;
						showedge13 = true;
						angebunden1.add(1);
						angebunden2.add(3);
						abwählen.push("c");
					} else if (counter13 == 1) {
						if (abwählen.peek() == "c") {
							showimage13 = false;
							repaint();
							lösung1 -= Integer.valueOf(gewicht3);
							anzahlkanten1 -= 1;
							counter13 = 0;
							showedge13 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}

			if (showedge13 == true || showedge02 == true) {

				if (recs[16][2].x < e.getX() && e.getX() < recs[16][12].x && recs[16][2].y < e.getY()
						&& e.getY() < recs[17][2].y) {
					if (counter23 == 0) {
						showimage23 = true;
						repaint();
						lösung1 += Integer.valueOf(gewicht4);
						anzahlkanten1 += 1;
						counter23 = 1;
						showedge23 = true;
						angebunden1.add(2);
						angebunden2.add(3);
						abwählen.push("d");
					} else if (counter23 == 1) {
						if (abwählen.peek() == "d") {
							showimage23 = false;
							repaint();
							lösung1 -= Integer.valueOf(gewicht4);
							anzahlkanten1 -= 1;
							counter23 = 0;
							showedge23 = false;
							angebunden1.remove(angebunden1.size() - 1);
							angebunden2.remove(angebunden2.size() - 1);
							abwählen.pop();
						}
					}
				}
			}
		}

	}

	public static void gameover1() {
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

	public static void win1() {
		JFrame frame = new JFrame("You won");
		JOptionPane.showMessageDialog(frame, "Du hast gewonnen", "You wonr", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	public static void pausierenstart1() {
		Auswahlbereich.timer.stop();
		SpielbereichTraining1.setMouseclick(false);
	}

	public static void pausierenende1() {
		Auswahlbereich.timer.start();
		SpielbereichTraining1.setMouseclick(true);
	}

	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public class ClickListener extends MouseAdapter {

		public void mousePressed(MouseEvent evt) {
			if (mouseclick == true) {
				extrarohrmoved = true;
				previousPoint = evt.getPoint();
			}
		}

		public void mouseDragged(MouseEvent evt) {
			if (mouseclick == true) {
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

				if (recs[4][1].x < image_corner.x && image_corner.x < recs[4][2].x && recs[4][1].y < image_corner.y
						&& image_corner.y < recs[5][1].y) {
					set = true;
					extrarohrmoved = true;
				}

				if (showedge01 == true) {
					if (recs[4][12].x < image_corner.x && image_corner.x < recs[4][13].x
							&& recs[4][12].y < image_corner.y && image_corner.y < recs[5][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}
				if (showedge02 == true) {
					if (recs[16][1].x < image_corner.x && image_corner.x < recs[16][2].x
							&& recs[16][1].y < image_corner.y && image_corner.y < recs[17][1].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge13 == true) {
					if (recs[4][12].x < image_corner.x && image_corner.x < recs[4][13].x
							&& recs[4][12].y < image_corner.y && image_corner.y < recs[5][12].y
							|| recs[16][12].x < image_corner.x && image_corner.x < recs[16][13].x
									&& recs[16][12].y < image_corner.y && image_corner.y < recs[17][12].y) {
						set = true;
						extrarohrmoved = true;
					}
				}

				if (showedge23 == true) {
					if (recs[16][1].x < image_corner.x && image_corner.x < recs[16][2].x
							&& recs[16][1].y < image_corner.y && image_corner.y < recs[17][1].y
							|| recs[16][12].x < image_corner.x && image_corner.x < recs[16][13].x
									&& recs[16][12].y < image_corner.y && image_corner.y < recs[17][12].y) {
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

	// Getters und Setters

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (mouseclick == true) {
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
			if (Auswahlbereich.stateswitchTraining == 0) {
				SpielbereichTraining1.pausierenstart1();
				Auswahlbereich.stateswitchTraining = 1;
			} else if (Auswahlbereich.stateswitchTraining == 1) {
				SpielbereichTraining1.pausierenende1();
				Auswahlbereich.stateswitchTraining = 0;
			}
		}

		if (recs[a][b].x < image_corner.x && image_corner.x < recs[a][b + 1].x && recs[a][b].y < image_corner.y
				&& image_corner.y < recs[a + 1][b].y) {
			boolean alleangebunden = false;
			int b0 = 0;
			int b1 = 0;
			int b2 = 0;
			int b3 = 0;
			if (SpielbereichTraining1.angebunden1.size() < 3 && SpielbereichTraining1.angebunden2.size() < 3) {
				alleangebunden = false;
			} else if (SpielbereichTraining1.angebunden1.size() >= 3 && SpielbereichTraining1.angebunden2.size() >= 3) {
				for (int i = 0; i < SpielbereichTraining1.angebunden.size(); i++) {
					for (int j = 0; j < SpielbereichTraining1.angebunden1.size(); j++) {
						if (SpielbereichTraining1.angebunden.get(i).get(j) == 0) {
							alleangebunden = true;
							b0++;
						}
						if (SpielbereichTraining1.angebunden.get(i).get(j) == 1) {
							alleangebunden = true;
							b1++;
						}
						if (SpielbereichTraining1.angebunden.get(i).get(j) == 2) {
							alleangebunden = true;
							b2++;
						}
						if (SpielbereichTraining1.angebunden.get(i).get(j) == 3) {
							alleangebunden = true;
							b3++;
						}

					}
				}

			}
			if (b0 == 0 || b1 == 0 || b2 == 0 || b3 == 0) {
				alleangebunden = false;
			}

			if (alleangebunden == true && SpielbereichTraining1.getLösung1() <= Auswahlbereich.getZielwert1()) {
				Auswahlbereich.switchpanel1++;
				Auswahlbereich.timer.stop();
				SpielbereichTraining1.win1();
				SpielbereichTraining2 spielbereichTraining2 = new SpielbereichTraining2();
				Spiel3.panel.add(spielbereichTraining2, "2");
				Spiel3.cl.show(Spiel3.panel, "2");
				spielbereichTraining2.requestFocusInWindow();
				spielbereichTraining2.setFocusable(true);
				Auswahlbereich.timerreset();
				Auswahlbereich.timer.start();
				Auswahlbereich.score1 += SpielbereichTraining1.getAnzahlkanten1();
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.score1);
				SpielbereichTraining1.setLösung1(0);
				SpielbereichTraining1.setAnzahlkanten1(0);
				Auswahlbereich.level1++;
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel1());
				Auswahlbereich.setZielwert1(Graph.OptimaleLösung1(spielbereichTraining2.getGraph()) * 2);
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert1());
				spielbereichTraining2 = null;
			} else {
				Auswahlbereich.timer.stop();
				Auswahlbereich.zeit.setText("Zeit:" + " " + Auswahlbereich.getZeitminuten_string0() + ":"
						+ Auswahlbereich.getZeitsekunden_string0());
				Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel0());
				Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert0());
				Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore0());
				Auswahlbereich.switchpanel1 = 0;
				SpielbereichTraining1.setAnzahlkanten1(0);
				SpielbereichTraining1.gameover1();
				Spiel3.cl.show(Spiel3.panel, "0");

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
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

	public Point getImage_corner() {
		return image_corner;
	}

	public void setImage_corner(Point image_corner) {
		this.image_corner = image_corner;
	}

	public Point getPreviousPoint() {
		return previousPoint;
	}

	public void setPreviousPoint(Point previousPoint) {
		this.previousPoint = previousPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static Graph getGraph() {
		return graph;
	}

	public static void setGraph(Graph graph) {
		SpielbereichTraining1.graph = graph;
	}

	public boolean isShowimage01() {
		return showimage01;
	}

	public void setShowimage01(boolean showimage01) {
		this.showimage01 = showimage01;
	}

	public boolean isShowimage02() {
		return showimage02;
	}

	public void setShowimage02(boolean showimage02) {
		this.showimage02 = showimage02;
	}

	public boolean isShowimage13() {
		return showimage13;
	}

	public void setShowimage13(boolean showimage13) {
		this.showimage13 = showimage13;
	}

	public boolean isShowimage23() {
		return showimage23;
	}

	public void setShowimage23(boolean showimage23) {
		this.showimage23 = showimage23;
	}

	public String getGewicht1() {
		return gewicht1;
	}

	public void setGewicht1(String gewicht1) {
		this.gewicht1 = gewicht1;
	}

	public String getGewicht2() {
		return gewicht2;
	}

	public void setGewicht2(String gewicht2) {
		this.gewicht2 = gewicht2;
	}

	public String getGewicht3() {
		return gewicht3;
	}

	public void setGewicht3(String gewicht3) {
		this.gewicht3 = gewicht3;
	}

	public String getGewicht4() {
		return gewicht4;
	}

	public void setGewicht4(String gewicht4) {
		this.gewicht4 = gewicht4;
	}

	public static int getLösung1() {
		return lösung1;
	}

	public static void setLösung1(int lösung1) {
		SpielbereichTraining1.lösung1 = lösung1;
	}

	public int getCounter01() {
		return counter01;
	}

	public void setCounter01(int counter01) {
		this.counter01 = counter01;
	}

	public int getCounter02() {
		return counter02;
	}

	public void setCounter02(int counter02) {
		this.counter02 = counter02;
	}

	public int getCounter13() {
		return counter13;
	}

	public void setCounter13(int counter13) {
		this.counter13 = counter13;
	}

	public int getCounter23() {
		return counter23;
	}

	public void setCounter23(int counter23) {
		this.counter23 = counter23;
	}

	public boolean isShowedge01() {
		return showedge01;
	}

	public void setShowedge01(boolean showedge01) {
		this.showedge01 = showedge01;
	}

	public boolean isShowedge02() {
		return showedge02;
	}

	public void setShowedge02(boolean showedge02) {
		this.showedge02 = showedge02;
	}

	public boolean isShowedge13() {
		return showedge13;
	}

	public void setShowedge13(boolean showedge13) {
		this.showedge13 = showedge13;
	}

	public boolean isShowedge23() {
		return showedge23;
	}

	public void setShowedge23(boolean showedge23) {
		this.showedge23 = showedge23;
	}

	public static int getAnzahlkanten1() {
		return anzahlkanten1;
	}

	public static void setAnzahlkanten1(int anzahlkanten1) {
		SpielbereichTraining1.anzahlkanten1 = anzahlkanten1;
	}

	public static boolean isMouseclick() {
		return mouseclick;
	}

	public static void setMouseclick(boolean mouseclick) {
		SpielbereichTraining1.mouseclick = mouseclick;
	}

}
