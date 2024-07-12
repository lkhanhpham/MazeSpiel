package Spiel2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Maze.MazeDisplay;
import Spiel2.ControlArea.ClockListener;
import Spiel2.Spiel2.Level;
import team07.main.LeftPanel;
import team07.main.MyFrame;
import team07.main.MyPanel;
import team07.main.RightPanel;

public class ControlArea extends JPanel implements ActionListener {
	private static JPanel leftPanel;
	private static PlayArea playArea;
	private static JComboBox schwierigkeit;
	private static JButton anleitung, start, end, zurueck, resetHs, abgeben;

	// variables for pausing game
	private static JToggleButton pause;
	private static Timer timer;
	private static boolean gamePaused = false;

	private static JLabel showLevel;
	private static int currentLevel;

	private static final int N = 60;
	private static ClockListener cl;

	private static JTextField tf = new JTextField(8);
	private static JLabel highscore = new JLabel("Highscores");
	private static JTextArea hs = new JTextArea(5, 1);
	private static StringBuilder highscoreString;

	private static JLabel currentScore, zielwert;
	private static int score = 0;
	private static HashMap<String, Integer> best3scores;
	private static int min;
	private static String name_to_remove;
	private static boolean newHs = false;
	private LeftPanel lPanel;

	public ControlArea(LeftPanel lPanel2) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		lPanel = lPanel2;

		leftPanel = Spiel2.getLeftPanel();
		playArea = Spiel2.getPlayArea();

		cl = new ClockListener();
		Color color = new Color(5, 39, 71);

		// zurueck button
		JPanel pane1 = new JPanel();
		pane1.setBackground(color);
		zurueck = new JButton("Zurueck");
		pane1.setLayout(new BorderLayout());
		pane1.add(zurueck, BorderLayout.EAST);

		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		add(pane1, c);

		JPanel pane2 = new JPanel();
		pane2.setBackground(color);

		// anleitung button
		anleitung = new JButton();
		anleitung.setPreferredSize(new Dimension(50, 50));

		URL url = this.getClass().getClassLoader().getResource("resources/anleitung.png");
		ImageIcon img = new ImageIcon(url);
		Image scaledImg = img.getImage().getScaledInstance(50, 50, 1);
		img = new ImageIcon(scaledImg);
		anleitung.setIcon(img);

		Dimension d = new Dimension(50, 30);
		// start button
		start = new JButton("Starten");
		start.setPreferredSize(d);

		// end button
		end = new JButton("Beenden");
		end.setEnabled(false);
		end.setPreferredSize(d);

		// Dropdown Menu to choose Level
		String[] levels = { "TRAINING", "MITTEL", "SCHWER" };
		schwierigkeit = new JComboBox(levels);
		schwierigkeit.setSelectedIndex(0);
		schwierigkeit.setPreferredSize(new Dimension(70, 50));
		schwierigkeit.setMaximumSize(new Dimension(100, 50));

		// shows timer
		tf.setHorizontalAlignment(JTextField.RIGHT);
		tf.setEditable(false);
		tf.setBackground(Color.DARK_GRAY);
		tf.setForeground(Color.white);
//		tf.setPreferredSize(new Dimension(100, 30));

		// pause button
		pause = new JToggleButton("Pausieren");
		pause.setPreferredSize(d);

		timer = new Timer(1000, cl);
		timer.setInitialDelay(0);

		// abgeben button
		abgeben = new JButton("Abgeben");
		abgeben.setEnabled(false);
		abgeben.setPreferredSize(d);
		abgeben.addActionListener(this);

		currentLevel = 1;
		showLevel = new JLabel("Dein Level: " + currentLevel);
		showLevel.setForeground(Color.white);

		// Highscore Board
		highscore.setForeground(Color.white);
		currentScore = new JLabel("Deine Punkte: " + score);
		currentScore.setForeground(Color.white);
		hs.setEditable(false);
		hs.setBackground(Color.white);

		// reset highscore button
		resetHs = new JButton("Reset Highscore");

		// show the zielwert
		zielwert = new JLabel();
		zielwert.setText("Zielwert: " + 0);
		zielwert.setForeground(Color.white);

		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		add(pane2, c);

		JPanel action = new JPanel();
		GroupLayout layout1 = new GroupLayout(action);
		action.setLayout(layout1);
		action.setBackground(color);
		pane2.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		pane2.add(action, c1);

		layout1.setAutoCreateGaps(true);
//		layout1.setAutoCreateContainerGaps(true);

		layout1.setHorizontalGroup(layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(anleitung, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGroup(layout1.createSequentialGroup().addComponent(start, 100, 100, 100).addComponent(end))
				.addGroup(layout1.createSequentialGroup().addComponent(schwierigkeit)
						.addGroup(layout1.createParallelGroup().addComponent(showLevel).addComponent(currentScore)
								.addComponent(zielwert))));

		layout1.setVerticalGroup(layout1.createSequentialGroup()
				.addComponent(
						anleitung, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(20)
				.addGroup(layout1.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(start, 30, 30, 30)
						.addComponent(end, 30, 30, 30))
				.addGroup(layout1.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(schwierigkeit)
						.addGroup(layout1.createSequentialGroup().addComponent(showLevel).addComponent(currentScore)
								.addComponent(zielwert))));

		JPanel pane3 = new JPanel();
		pane3.setBackground(color);
		;
		BoxLayout box = new BoxLayout(pane3, BoxLayout.LINE_AXIS);
		pane3.setLayout(box);
		JPanel action2 = new JPanel();
		GroupLayout layout2 = new GroupLayout(action2);
		action2.setLayout(layout2);
		action2.setBackground(color);

		c1.weightx = 1;
		c1.weighty = 1;
		c1.fill = GridBagConstraints.VERTICAL;
		pane3.add(Box.createHorizontalGlue());
		pane3.add(action2);
		pane3.add(Box.createHorizontalGlue());

		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		add(pane3, c);

		layout2.setAutoCreateGaps(true);
		layout2.setAutoCreateContainerGaps(true);

		layout2.setHorizontalGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(tf, 120, 120, 120).addComponent(pause, 120, 120, 120).addComponent(abgeben, 120, 120, 120)
				.addComponent(highscore).addGap(20)
				.addComponent(hs, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(resetHs));

		layout2.setVerticalGroup(layout2.createSequentialGroup().addComponent(tf, 40, 40, 40)
				.addComponent(pause, 30, 30, 30).addComponent(abgeben, 30, 30, 30).addGap(20).addComponent(highscore)
				.addComponent(hs, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(resetHs));

		start.addActionListener(this);
		end.addActionListener(this);
		schwierigkeit.addActionListener(this);
		zurueck.addActionListener(this);
		resetHs.addActionListener(this);
		anleitung.addActionListener(this);
//		fortfahren.addActionListener(this);

		pause.setEnabled(false);
		pause.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (pause.isSelected()) {
					if (!gamePaused) {
						timer.stop();
						pause.setText("Fortfahren");
						abgeben.setEnabled(false);
						tf.setForeground(Color.red);
						setGamePaused(true);
					}
				} else {
					if (gamePaused) {
						timer.start();
						tf.setForeground(Color.white);
						pause.setText("Pausieren");
						setGamePaused(false);
						abgeben.setEnabled(true);
					}

				}
				playArea.repaint();
				playArea.requestFocusInWindow();
			}
		});

		start.setFocusable(false);
		end.setFocusable(false);
		schwierigkeit.setFocusable(false);
		zurueck.setFocusable(false);
		pause.setFocusable(false);
		anleitung.setFocusable(false);
		setFocusable(false);

		String path = "highscore_spiel2.txt";
		highscoreString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String line = bufferedReader.readLine();
			for (int i = 0; i < 6; i++) {
				highscoreString.append(line + "   ");
				if (i % 2 == 1) {
					highscoreString.append("\n");
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// Exception handling
		} catch (IOException e) {
			// Exception handling
		}

		hs.setText(highscoreString.toString());

	}

	public static void initHighscore() {
		String path = "highscore_spiel2.txt";
		String highscoreString = "-" + "\n" + "0" + "\n";
		String highscoreString1 = new String();
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
			for (int i = 0; i < 3; i++) {
				bufferedWriter.append(highscoreString);
				highscoreString1 += "-   " + "0" + "\n";
			}
			bufferedWriter.close();
			;
		} catch (IOException e) {
			// Exception handling
		}

		hs.setText(highscoreString1);
	}

	// checks whether a new high score was achieved, if there is spot left on
	// Hsboard, replace "-"
	// with the new highscore, else replace the smallest score

	public static void newHs_reached() {
		String path = "highscore_spiel2.txt";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Integer> best = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();
		String lineOfText = scanner.nextLine();
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0) {
				names.add(lineOfText); // adds the names to a list
			} else {
				best.add(Integer.parseInt(lineOfText)); // adds highscores to a list
			}
			if (scanner.hasNext()) {
				lineOfText = scanner.nextLine();
			}
		}
		scanner.close();
		min = best.get(0);
		for (int j = 0; j < best.size(); j++) {
			if (best.get(j) < min) {
				min = best.get(j);
			}
		}
		name_to_remove = names.get(best.indexOf(min));
//		System.out.println("min ist: " + min);
		if (score >= min) {
			newHs = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					start.setEnabled(false);
					leftPanel.add(playArea);
					Spiel2.setPlayArea(playArea);
					zielwert.setText("Zielwert: " + playArea.getSolver().getOptResult());
					leftPanel.revalidate();
					pause.setEnabled(true);
					Level level = Spiel2.getMyGame().getLevel();

					timer = new Timer(1000, cl);
					timer.start();

					playArea.requestFocusInWindow();
					System.out.println("start");
					schwierigkeit.setEnabled(false);
					end.setEnabled(true);
					abgeben.setEnabled(true);
					zurueck.setEnabled(false);
				}
			});
		}
		if (e.getSource() == end) {
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					playArea.setVisible(false);
					leftPanel.remove(0);
					leftPanel.revalidate();
					zielwert.setText("Zielwert: " + 0);
					end.setEnabled(false);
					Spiel2.setMyGame(new Game(Spiel2.getMyGame().getLevel()));
					playArea = new PlayArea();
					Spiel2.setPlayArea(playArea);
					start.setEnabled(true);
					schwierigkeit.setEnabled(true);
					pause.setText("Pausieren");
					pause.setEnabled(false);
					abgeben.setEnabled(false);
					zurueck.setEnabled(true);
//					fortfahren.setEnabled(false);
					timer.stop();
					cl.resetTimer();
					tf.setForeground(Color.white);
					tf.setText("00:00:00");
				}
			});
		}

//		if (e.getSource() == pause) {
//			timer.stop();
//		}

		if (e.getSource() == schwierigkeit) {

			JComboBox cb = (JComboBox) e.getSource();
			String level = (String) cb.getSelectedItem();
			end.setEnabled(false);

			leftPanel.removeAll();
			switch (level) {
			case "TRAINING":
				Spiel2.setMyGame(new Game(Spiel2.Level.TRAINING));
				break;
			case "MITTEL":
				Spiel2.setMyGame(new Game(Spiel2.Level.MITTEL));
				break;
			case "SCHWER":
				Spiel2.setMyGame(new Game(Spiel2.Level.SCHWER));
				break;
			}
			playArea = new PlayArea();
			Spiel2.setPlayArea(playArea);
			playArea.requestFocusInWindow();
//			leftPanel.revalidate();
//			MyFrame.getSpiel2().revalidate();

		}
		if (e.getSource() == zurueck) {

			MyFrame.getCl().show(MyFrame.getPanelCont(), "1");
			LeftPanel.getDp().requestFocusInWindow();
			lPanel.removeGame();
			lPanel.drawGame();
			RightPanel.getNeustart().doClick();
			RightPanel.setMaze();
		}
		if (e.getSource() == abgeben) {
			playArea = Spiel2.getPlayArea();
			playArea.checkValidSol();
			playArea.showResult();
			playArea.requestFocusInWindow();
		}
		if (e.getSource() == resetHs) {
			initHighscore();
		}
		if (e.getSource() == anleitung) {
			Spielanleitung();
		}

	}

	public void Spielanleitung() {
		if (!gamePaused) {
			timer.stop();
			pause.setText("Fortfahren");
			abgeben.setEnabled(false);
			tf.setForeground(Color.red);
			setGamePaused(true);
		}
		JFrame frame = new JFrame("Spielanleitung");

		JOptionPane.showMessageDialog(frame, "Die Regeln sind wie folgend:\n"
				+ "Es gibt Fischarten, die sich nicht miteinander vertragen, und diese Konflikte der Fischarten\r\n"
				+ "sind in der Tabelle ganz oben im Spielbereich dargestellt.\n"
				+ "Du musst die Fische aus der Tabelle so auf Aquarien aufteilen,\r\n"
				+ "dass es in keinem Aquarium zu Konflikten zwischen zwei Fischen kommt. Zudem gibt es eine\r\n"
				+ "Schranke, die vorgibt, wie viele Aquarien dabei maximal verwendet werden dürfen. Bei den\r\n"
				+ "Schwierigkeitsstufen mittel und schwer wird jedes Level unter Zeitdruck gespielt.\n"
				+ "TRAINING: du darfst ein Aquarium mehr als die Schranke benutzen\n"
				+ "MITTEL + SCHWER: du darfst nur genau wie viele Aquarien benutzen wie die Schranke angibt.\n"
				+ "Die Fischen zu verteilen ist erledigt durch Drag und Drop.\n"
				+ "Um deine Lösung abzugeben, du kannst entweder den Wagen bis zur Finish Linie\n"
				+ "oder auf Abgeben anklicken.", "Spielanleitung", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	/**
	 * @return the score
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public static void setScore(int score) {
		ControlArea.score = score;
	}

	/**
	 * @return the cl
	 */
	public static ClockListener getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public static void setCl(ClockListener cl) {
		ControlArea.cl = cl;
	}

	/**
	 * @return the schwierigkeit
	 */
	public static JComboBox getSchwierigkeit() {
		return schwierigkeit;
	}

	/**
	 * @param schwierigkeit the schwierigkeit to set
	 */
	public static void setSchwierigkeit(JComboBox schwierigkeit) {
		ControlArea.schwierigkeit = schwierigkeit;
	}

	/**
	 * @return the anleitung
	 */
	public static JButton getAnleitung() {
		return anleitung;
	}

	/**
	 * @param anleitung the anleitung to set
	 */
	public static void setAnleitung(JButton anleitung) {
		ControlArea.anleitung = anleitung;
	}

	/**
	 * @return the start
	 */
	public static JButton getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public static void setStart(JButton start) {
		ControlArea.start = start;
	}

	/**
	 * @return the end
	 */
	public static JButton getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public static void setEnd(JButton end) {
		ControlArea.end = end;
	}

	/**
	 * @return the zurueck
	 */
	public static JButton getZurueck() {
		return zurueck;
	}

	/**
	 * @param zurueck the zurueck to set
	 */
	public static void setZurueck(JButton zurueck) {
		ControlArea.zurueck = zurueck;
	}

	/**
	 * @return the resetHs
	 */
	public static JButton getResetHs() {
		return resetHs;
	}

	/**
	 * @param resetHs the resetHs to set
	 */
	public static void setResetHs(JButton resetHs) {
		ControlArea.resetHs = resetHs;
	}

	/**
	 * @return the abgeben
	 */
	public static JButton getAbgeben() {
		return abgeben;
	}

	/**
	 * @param abgeben the abgeben to set
	 */
	public static void setAbgeben(JButton abgeben) {
		ControlArea.abgeben = abgeben;
	}

	/**
	 * @return the pause
	 */
	public static JToggleButton getPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public static void setPause(JToggleButton pause) {
		ControlArea.pause = pause;
	}

	/**
	 * @return the timer
	 */
	public static Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public static void setTimer(Timer timer) {
		ControlArea.timer = timer;
	}

	/**
	 * @return the showLevel
	 */
	public static JLabel getShowLevel() {
		return showLevel;
	}

	/**
	 * @param showLevel the showLevel to set
	 */
	public static void setShowLevel(JLabel showLevel) {
		ControlArea.showLevel = showLevel;
	}

	/**
	 * @return the currentLevel
	 */
	public static int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel the currentLevel to set
	 */
	public static void setCurrentLevel(int currentLevel) {
		ControlArea.currentLevel = currentLevel;
	}

	/**
	 * @return the hs
	 */
	public static JTextArea getHs() {
		return hs;
	}

	/**
	 * @param hs the hs to set
	 */
	public static void setHs(JTextArea hs) {
		ControlArea.hs = hs;
	}

	/**
	 * @return the tf
	 */
	public static JTextField getTf() {
		return tf;
	}

	/**
	 * @param tf the tf to set
	 */
	public static void setTf(JTextField tf) {
		ControlArea.tf = tf;
	}

	/**
	 * @return the currentScore
	 */
	public static JLabel getCurrentScore() {
		return currentScore;
	}

	/**
	 * @param currentScore the currentScore to set
	 */
	public static void setCurrentScore(JLabel currentScore) {
		ControlArea.currentScore = currentScore;
	}

	/**
	 * @return the best3scores
	 */
	public static HashMap<String, Integer> getBest3scores() {
		return best3scores;
	}

	/**
	 * @param best3scores the best3scores to set
	 */
	public static void setBest3scores(HashMap<String, Integer> best3scores) {
		ControlArea.best3scores = best3scores;
	}

	public static boolean isNewHs() {
		return newHs;
	}

	public static void setNewHs(boolean newHs) {
		ControlArea.newHs = newHs;
	}

	/**
	 * @return the min
	 */
	public static int getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public static void setMin(int min) {
		ControlArea.min = min;
	}

	/**
	 * @return the highscoreString
	 */
	public static StringBuilder getHighscoreString() {
		return highscoreString;
	}

	/**
	 * @param highscoreString the highscoreString to set
	 */
	public static void setHighscoreString(StringBuilder highscoreString) {
		ControlArea.highscoreString = highscoreString;
	}

	public static String getName_to_remove() {
		return name_to_remove;
	}

	public static void setName_to_remove(String name_to_remove) {
		ControlArea.name_to_remove = name_to_remove;
	}

	/**
	 * @return the gamePaused
	 */
	public static boolean isGamePaused() {
		return gamePaused;
	}

	/**
	 * @param gamePaused the gamePaused to set
	 */
	public static void setGamePaused(boolean gamePaused) {
		ControlArea.gamePaused = gamePaused;
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
		ControlArea.leftPanel = leftPanel;
	}

	/**
	 * @return the zielwert
	 */
	public static JLabel getZielwert() {
		return zielwert;
	}

	/**
	 * @param zielwert the zielwert to set
	 */
	public static void setZielwert(JLabel zielwert) {
		ControlArea.zielwert = zielwert;
	}

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
		ControlArea.playArea = playArea;
	}

	public class ClockListener implements ActionListener {

		private int hours;
		private int minutes;
		private int seconds;
		private String hour;
		private String minute;
		private String second;
		private Level level;
		private int countdownMittel = 90;
		private int countdownSchwer = 120;

		@Override
		public void actionPerformed(ActionEvent e) {
			level = Spiel2.getMyGame().getLevel();
			if (level == Spiel2.Level.TRAINING) {
				NumberFormat formatter = new DecimalFormat("00");
				if (seconds == N) {
					seconds = 00;
					minutes++;
				}

				if (minutes == N) {
					minutes = 00;
					hours++;
				}
				hour = formatter.format(hours);
				minute = formatter.format(minutes);
				second = formatter.format(seconds);
				seconds++;
				tf.setText(String.valueOf(hour + ":" + minute + ":" + second));
			}
			if (level == Spiel2.Level.MITTEL) {
				minutes = countdownMittel / 60;
				seconds = countdownMittel % 60;

				NumberFormat formatter = new DecimalFormat("00");
				hour = formatter.format(hours);
				minute = formatter.format(minutes);
				second = formatter.format(seconds);
				tf.setText(hour + ":" + minute + ":" + second);
				countdownMittel--;
				if (countdownMittel < 15) {
					tf.setForeground(Color.red);
				}
				if (countdownMittel < 1) {
					tf.setText("Time Over!");
					showResult();
					timer.stop();
				}
			}
			if (level == Spiel2.Level.SCHWER) {
				minutes = countdownSchwer / 60;
				seconds = countdownSchwer % 60;

				NumberFormat formatter = new DecimalFormat("00");
				hour = formatter.format(hours);
				minute = formatter.format(minutes);
				second = formatter.format(seconds);
				tf.setText(hour + ":" + minute + ":" + second);
				countdownSchwer--;
				if (countdownSchwer < 15) {
					tf.setForeground(Color.red);
				}
				if (countdownSchwer < 1) {
					tf.setText("Time Over!");
					showResult();
					timer.stop();
				}
			}

		}

		public void resetTimer() {
			minutes = 0;
			seconds = 0;
			countdownMittel = 90;
			countdownSchwer = 120;
		}

		public void showResult() {
			JFrame frame = new JFrame("Dein Ergebnis");
			frame.setLayout(new GridBagLayout());
			frame.setPreferredSize(new Dimension(400, 300));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1;
			gbc.weighty = 0.5;
			gbc.fill = GridBagConstraints.BOTH;
			JPanel pane = new JPanel();
			JTextPane textPane = new JTextPane();
			textPane.setFont(new Font("Myriad", Font.BOLD, 13));
			textPane.setEditable(false);
			StyledDocument doc = textPane.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);

			frame.add(pane, gbc);
			pane.add(textPane);
			textPane.setBackground(pane.getBackground());

			JButton fortfahren = new JButton("Fortfahren");
			JLabel label = new JLabel("Du hast neue Highscore erreicht. Gebe deinen Namen hier.");
			JTextField enterName = new JTextField(1);
			gbc.gridy = 1;
			gbc.weighty = 0;
			frame.add(fortfahren, gbc);

			textPane.setText("Spiel verloren!\n" + "Die Zeit ist um.\n" + "Probiere noch mal mit einem neuen Spiel.\n"
					+ "Die optimale Anzahl an benötigten Aquarien ist " + playArea.getSolver().getOptResult());

			ControlArea.newHs_reached();
			if (ControlArea.isNewHs()) {
				gbc.gridy = 2;
				gbc.weighty = 0.5;
				frame.add(label, gbc);
				gbc.gridy = 3;
				gbc.weighty = 0.5;
				frame.add(enterName, gbc);
				score = ControlArea.getScore();
			}
			fortfahren.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (ControlArea.isNewHs()) {
						String path = "highscore_spiel2.txt";
						File file = new File(path);
						playArea.setHighscore(enterName.getText(), score, file);
						ControlArea.setNewHs(false);
					}
					ControlArea.getTimer().stop();

					ControlArea.getShowLevel().setText("Dein Level: " + ControlArea.getCurrentLevel());
					ControlArea.getCurrentScore().setText("Deine Punkte: " + ControlArea.getScore());

					ControlArea x = Spiel2.getRightPanel();
					ControlArea.getStart().setEnabled(false);
					Level level = Spiel2.getMyGame().getLevel();
					Spiel2.setMyGame(new Game(level));
					PlayArea playArea = new PlayArea();
					Spiel2.getLeftPanel().removeAll();
					Spiel2.getLeftPanel().add(playArea);
					Spiel2.setPlayArea(playArea);
					Spiel2.getLeftPanel().revalidate();
					ControlArea.setPlayArea(playArea);
					ControlArea.getZielwert().setText("Zielwert: " + playArea.getSolver().getOptResult());
					ControlArea.getPause().setEnabled(true);
					ClockListener cl = x.new ClockListener();
					ControlArea.setTimer(new Timer(1000, cl));
					ControlArea.getTimer().start();

					playArea.requestFocusInWindow();
//					System.out.println("start");
					ControlArea.getSchwierigkeit().setEnabled(false);
					ControlArea.getEnd().setEnabled(true);
					ControlArea.getAbgeben().setEnabled(true);
					frame.dispose();
				};
			});
			ControlArea.setCurrentLevel(0);
			ControlArea.setScore(0);
			frame.setLocationRelativeTo(null);
			frame.pack();
			frame.setVisible(true);
		}

	}
}
