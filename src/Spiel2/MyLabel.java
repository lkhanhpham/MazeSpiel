package Spiel2;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * a class extending JLabel that can be dragged and dropped
 * 
 * @author LINHKHANHSPC
 *
 */
public class MyLabel extends JLabel {
	private ImageIcon image;
	private boolean notMoved = true;
	private JComponent parent = null;
	private boolean hasReached = false;
	private String name;
	private JLabel[] tanks;
	private int inTank;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getInTank() {
		return inTank;
	}

	public void setInTank(int inTank) {
		this.inTank = inTank;
	}

	/**
	 * @return the parent
	 */
	public JComponent getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	public boolean hasReached() {
		return hasReached;

	}

	public void setHasReached(boolean hasReached) {
		this.hasReached = hasReached;

	}

	public boolean isNotMoved() {
		return notMoved;
	}

	public void setNotMoved(boolean moved) {
		this.notMoved = moved;
	}

	public MyLabel(ImageIcon image, String name) {
		tanks = PlayArea.getTanks();
		inTank = -1;
		this.image = image;
		this.name = name;
		setIcon(image);

	}

	public static class MyMouseListener extends MouseAdapter {

		private JComponent owner;
		private int startx;
		private int starty;

		/**
		 * @return the startx
		 */
		public int getStartx() {
			return startx;
		}

		/**
		 * @param startx the startx to set
		 */
		public void setStartx(int startx) {
			this.startx = startx;
		}

		/**
		 * @return the starty
		 */
		public int getStarty() {
			return starty;
		}

		/**
		 * @param starty the starty to set
		 */
		public void setStarty(int starty) {
			this.starty = starty;
		}

		// constructor of MouseListener with a JComponent as parameter which the JLabel
		// can be dragged within
		public MyMouseListener(JComponent owner) {
			this.owner = owner;
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void checkValidDrop(MouseEvent e) {
			MyLabel src = (MyLabel) e.getSource();
			JLabel[] tanks = PlayArea.getTanks();
			Point p = src.getLocation();
			if (src.getInTank() > -1) {

				int neux = src.getLocation().x + e.getX();
				int neuy = src.getLocation().y + e.getY();
				src.setLocation(neux, neuy);
				p = src.getLocation();
//				System.out.println("this " + src.getLocation().x + " " + src.getLocation().y);
//				System.out.println("ziel " + rec.x + " " + rec.y);
//				if (p.x > rec.x && p.getX() < rec.x + rec.width && p.y >= rec.y
//						&& p.y < rec.y + rec.height) {
//					src.hasReached = true;
////					this.inTank = tanks.indexOf(tank);
//
//				}
			}
			for (int i = 0; i < PlayArea.getTanks().length; i++) {
				if (tanks[i].isVisible()) {
					Rectangle rec = tanks[i].getBounds();
//					System.out.println("this " + this.getLocation().x + " " + this.getLocation().y);
//					System.out.println("ziel " + rec.x + " " + rec.y);

					if (p.getX() > rec.x && p.getX() < rec.x + rec.width && p.getY() > rec.y
							&& p.getY() < rec.y + rec.height) {
						tanks[i].add(src);
						src.hasReached = true;
						src.inTank = i;
					}
				}
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (!ControlArea.isGamePaused()) {
				MyLabel src = (MyLabel) e.getSource();
				checkValidDrop(e);
				PlayArea pane = (PlayArea) owner;
				if (!src.hasReached) {
					int indexOfSrc = Arrays.asList(PlayArea.getLabels()).indexOf(src);
					src.setLocation(PlayArea.getStartX(), PlayArea.getCorY().get(indexOfSrc));
					src.setInTank(-1);
					src.setNotMoved(true);
				} else {
					// JLabel tank = PlayArea.getTanks()[src.getInTank()];
					src.setNotMoved(false);
				}
				// System.out.println("2: " + src.getX() + " " + src.getY());
				src.revalidate();
				owner.revalidate();
				owner.repaint();
			}

		}

		public void mouseDragged(MouseEvent e) {
			if (!ControlArea.isGamePaused()) {
				// System.out.println("4: " + e.getX() + " " + e.getY());
				setPosition(e);
			}
		}

		// sets the label to the new position of the mouse pointer
		private void setPosition(MouseEvent e) {

			MyLabel src = (MyLabel) e.getSource();
			;
			int neux = src.getLocation().x + e.getX() - startx;
			int neuy = src.getLocation().y + e.getY() - starty;
			if (src.inTank > -1) {
				neux = src.getLocation().x + e.getX();
				neuy = src.getLocation().y + e.getY();
			}
//			System.out.println("neux " + neux + " neuy " + neuy);
			src.setLocation(neux, neuy);

		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			if (!ControlArea.isGamePaused()) {
				MyLabel src = (MyLabel) e.getSource();
				//			System.out.println("1: " + src.getX() + " " + src.getY());
				src.setHasReached(false);
				src.setNotMoved(false);
				startx = e.getX();
				starty = e.getY();
			}

		}

	}

}
