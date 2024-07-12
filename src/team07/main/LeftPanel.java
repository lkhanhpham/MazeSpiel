package team07.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import Maze.MazeDisplay;
import Spiel1.AlgorithmDisplay;

public class LeftPanel extends JPanel {
	
	private static MazeDisplay dp;

	public LeftPanel() {
		setLayout(new GridLayout());
		dp = new MazeDisplay(9, 9); //Erstellt ein x*y Feld
		add(dp); //Fuegt es dem Panel zu
		addKeyListener(dp);
		dp.setFocusable(true);
	}
	
	public void removeGame() {
		if (dp != null) {
		this.remove(dp);
		}
		repaint();
	}
	
	public void drawGame() {
		if (dp != null) {
		this.remove(dp);
		}
		this.dp = new MazeDisplay(9, 9);
		this.add(dp);
		this.addKeyListener(dp);
		this.dp.setFocusable(true);
		this.requestFocusInWindow();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * @return the dp
	 */
	public static MazeDisplay getDp() {
		return dp;
	}

	/**
	 * @param dp the dp to set
	 */
	public static void setDp(MazeDisplay dp) {
		LeftPanel.dp = dp;
	}
}