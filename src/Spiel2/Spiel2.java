package Spiel2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import Spiel2.Spiel2.Level;
import team07.main.LeftPanel;
import team07.main.MyFrame;

public class Spiel2 extends JPanel {
	// 3 Levels and their final attributes
	public enum Level {
		TRAINING, MITTEL, SCHWER;
		public int getConflictsNo() {
			Random rand = new Random();
			switch (this) {
			case TRAINING:
				return rand.nextInt(5);
			case MITTEL:
				int[] arr = {1,2,3,8,9,10};
				int no = rand.nextInt(arr.length);
				return arr[no];
			case SCHWER:
				return (int) Math.floor(Math.random() * (15 - 10 + 1) + 10);
			default:
				return 0;
			}
		}

		public int getNodeNo() {
			switch (this) {
			case TRAINING:
				return 4;
			case MITTEL:
				return 6;
			case SCHWER:
				return 8;
			default:
				return 0;
			}
		}
	}

	private static Game myGame;
	private static PlayArea playArea;
	private static JPanel leftPanel;
	private static ControlArea rightPanel;
	private static int leftWidth;
	private static int leftHeight;
	private static JPanel contentPane;


	/**
	 * @return the playArea
	 */
	public static PlayArea getPlayArea() {
		return playArea;
	}

	/**
	 * @param playArea the playArea to set
	 */
	public static void setPlayArea(PlayArea playArea) {
		Spiel2.playArea = playArea;
	}

	public static JPanel getContentPane() {
		return contentPane;
	}

	public static void setContentPane(JPanel contentPane) {
		Spiel2.contentPane = contentPane;
	}

	/**
	 * @return the leftWidth
	 */
	public static int getLeftWidth() {
		return leftWidth;
	}

	/**
	 * @param leftWidth the leftWidth to set
	 */
	public static void setLeftWidth(int leftWidth) {
		Spiel2.leftWidth = leftWidth;
	}

	/**
	 * @return the leftHeight
	 */
	public static int getLeftHeight() {
		return leftHeight;
	}

	/**
	 * @param leftHeight the leftHeight to set
	 */
	public static void setLeftHeight(int leftHeight) {
		Spiel2.leftHeight = leftHeight;
	}

	/**
	 * @return the leftPanel
	 */
	public static JPanel getLeftPanel() {
		return leftPanel;
	}

	/**
	 * @param leftPanel the leftPanel to set
	 */
	public static void setLeftPanel(JPanel leftPanel) {
		Spiel2.leftPanel = leftPanel;
	}

	/**
	 * @return the myGame
	 */
	public static Game getMyGame() {
		return myGame;
	}

	/**
	 * @param myGame the myGame to set
	 */
	public static void setMyGame(Game myGame) {
		Spiel2.myGame = myGame;
	}

	/**
	 * @return the rightPanel
	 */
	public static ControlArea getRightPanel() {
		return rightPanel;
	}

	/**
	 * @param rightPanel the rightPanel to set
	 */
	public void setRightPanel(ControlArea rightPanel) {
		Spiel2.rightPanel = rightPanel;
	}

	public Spiel2(LeftPanel lPanel2) {
		contentPane = this;
		myGame = new Game(Level.TRAINING);
		LeftPanel lPanel = lPanel2;
		
//		setBackground(Color.BLACK);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		playArea = new PlayArea();
		playArea.requestFocusInWindow();

//		leftPanel.add(playArea);
		c.weightx = 0.8;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		add(leftPanel, c);

		rightPanel = new ControlArea(lPanel);
		c.weightx = 0.2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(rightPanel, c);
		
		setVisible(true);
	

	}
}
