package Spiel2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Spiel2.ControlArea.ClockListener;
import Spiel2.MyLabel.MyMouseListener;
import Spiel2.Spiel2.Level;

public class PlayArea extends JPanel implements ComponentListener, ActionListener, KeyListener {
	private Game game;
	private boolean resizing = false;
	private static MyLabel[] labels;
	private static String[] names;
	private static String[] enemies;
	private static Image[] shadows;
	private static JLabel[] tanks;
	private ControlArea controlArea;

	private static int startX, startY;

	private JButton plus;
	private JButton minus;

	// Variables for drawing
	private int cellSize;
	private int gridPadding;
	private int oldCellSize;
	private int oldWidth;
	private int WIDTH, HEIGHT;
	private Image tankImg;
	private ImageIcon tankicon;
	private static JLabel tank;
	private static Rectangle[][] recs;
	private static ArrayList<Integer> corX;
	private static ArrayList<Integer> corY;
	private int counter;
	private Image lorryImg;
	private int lorryPosition;
	private Image plusImg;
	private ImageIcon plusIcon;
	private Image minusImg;
	private ImageIcon minusIcon;
	private Image finishLine;
	private JTextField enterName;
	private Image bg;

//variables for solving
	private Solver solver;
	private static int solution;
	private static StringBuilder str;
	private boolean abgeben = false;
	private boolean correct;
	private int score;

//variable for pausing game
	private boolean paused;
	private String pauseString = "Spiel ist pausiert!";

	public PlayArea() {
		setLayout(null);
		game = Spiel2.getMyGame();
		controlArea = Spiel2.getRightPanel();

		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (int m = 0; m < game.getLevel().getNodeNo(); m++) {
			ints.add(m);
		}

		solver = new Solver(game.getGraph());
//		System.out.println(solver.calOptResult());

		setBackground(new Color(223, 235, 247));

		cellSize = 65;
		gridPadding = 0;
		WIDTH = 395;
		HEIGHT = 315;
		int padding = 90;
		startX = padding;
		startY = 0;

		counter = -1;

		plus = new JButton();
		minus = new JButton();

		plusImg = null;
		plusIcon = null;
		minusImg = null;
		minusIcon = null;

		// loads images into JPanel
		loadImages();

		plus.addActionListener(this);
		plus.setFocusable(false);
		plus.setIcon(plusIcon);

		minus.addActionListener(this);
		minus.setFocusable(false);
		minus.setIcon(minusIcon);

		add(plus);
		add(minus);

		labels = new MyLabel[game.getGraph().getNodeList().size()];

		shadows = new Image[game.getGraph().getNodeList().size()];

		names = new String[game.getGraph().getNodeList().size()];

		enemies = new String[game.getGraph().getNodeList().size()];

		tanks = new JLabel[game.getGraph().getNodeList().size()];

		fill_label();
		corX = new ArrayList<Integer>();
		corY = new ArrayList<Integer>();
		int k = 25;
		for (int i = 0; i < labels.length; i++) {
			corY.add(k);
			k += 25;
		}

		for (int n = 0; n < labels.length; n++) {
			tank = new JLabel();
			tank.setIcon(tankicon);
			tank.setVisible(false);
			tank.setLayout(new FlowLayout());
			tanks[n] = tank;
		}

		recs = new Rectangle[6][6];
		int i = 0;
		int j = (labels.length + 1) * 25;
		for (int row = 0; row < 6; row += 1) {
			for (int col = 0; col < 6; col += 1) {
				Rectangle rec = new Rectangle(i, j, cellSize, cellSize);
				recs[row][col] = rec;
				i += cellSize;
			}
			i = 0;
			j += cellSize;
		}

		lorryPosition = 0;

		int row = 4;
		int col = 0;
		for (int l = 0; l < tanks.length; l++) {
			tank = tanks[l];
			add(tank);
			if (l == 4) {
				col = 2;
				row = 4;
			}
			tank.setBounds(recs[row][col].x, recs[row][col].y, cellSize * 2, cellSize);
			row -= 1;
		}

		str = ControlArea.getHighscoreString();

		if (this.getKeyListeners().length < 1) {
			addKeyListener(this);
		}
		requestFocusInWindow();
		addComponentListener(this);

		setVisible(true);
	}

	public void fill_label() {

		for (int node = 0; node < game.getGraph().getNodeList().size(); node++) {
			Node currentNode = game.getGraph().getNodeList().get(node);
			Image img = currentNode.getImage(currentNode.getName());
			ImageIcon icon = new ImageIcon(img);
			String name = currentNode.getName();
			JPopupMenu enemyFish = new JPopupMenu();
			MyLabel label = new MyLabel(icon, name);

			JLabel printEnemy = new JLabel();

			printEnemy.setText(currentNode.printNeighborList());
			enemyFish.add(printEnemy);

			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MyLabel fish = (MyLabel) e.getSource();
					enemyFish.show(e.getComponent(), e.getX(), e.getY());
					enemyFish.setFocusable(false);
					requestFocusInWindow();
				}

			});

			Image shadow = Node.getShadow(name);

			String enemie = currentNode.printNeighborList();

			MyMouseListener l = new MyMouseListener(this);
			label.addMouseListener(l);
			label.addMouseMotionListener(l);
			label.setParent(this);
			labels[node] = label;
			shadows[node] = shadow;

			names[node] = name;

			enemies[node] = enemie;
		}

	}

	public void loadImages() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				URL path = this.getClass().getResource("/resources/Spiel 2/plus.png");
				URL path1 = this.getClass().getResource("/resources/Spiel 2/minus.png");
				URL path2 = this.getClass().getResource("/resources/Spiel 2/tank-02.png");
				URL path3 = this.getClass().getResource("/resources/Spiel 2/lorry-02.png");
				URL path4 = this.getClass().getResource("/resources/Spiel 2/line.png");
				URL path5 = this.getClass().getResource("/resources/Spiel 2/oceanbg.png");
				try {
					plusImg = ImageIO.read(path);
					plusImg = plusImg.getScaledInstance(cellSize / 2, cellSize / 2, Image.SCALE_FAST);
					plusIcon = new ImageIcon(plusImg);

					minusImg = ImageIO.read(path1);
					minusImg = minusImg.getScaledInstance(cellSize / 2, cellSize / 2, Image.SCALE_FAST);
					minusIcon = new ImageIcon(minusImg);

					tankImg = ImageIO.read(path2);
					tankImg = tankImg.getScaledInstance(cellSize * 2, cellSize, Image.SCALE_FAST);
					tankicon = new ImageIcon(tankImg);

					lorryImg = ImageIO.read(path3);
					lorryImg = lorryImg.getScaledInstance(cellSize * 4, cellSize, Image.SCALE_SMOOTH);

					finishLine = ImageIO.read(path4);
					finishLine = finishLine.getScaledInstance(cellSize*2, cellSize * 3, Image.SCALE_SMOOTH);
					bg = ImageIO.read(path5);
				} catch (IOException e) {
				}

			}
		});
	}

	public void doDrawing(Graphics g) {

		if (resizing) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(this.getBackground()); // Karierter Hintergrund
			for (int row = 0; row < 6; row += 1) {
				for (int col = 0; col < 6; col += 1) {
					g2d.draw(recs[row][col]);

				}
			}

			// draws the background
			g.drawImage(bg, 0, 0, this.getBounds().width, this.getBounds().height, this);

			// draws the header
			String[] header = { "Fisch", "Name", "Feinde" };
			Color color = new Color(0, 75, 97);
			g.setColor(color);
			g.setFont(new Font("Myriad", Font.BOLD, 12));
			int margin = 20; // margin of the text in a cell

			g.drawString((String) header[0], startX, margin);
			g.drawString(header[1], startX + 100, margin);
			g.drawString(header[2], startX + 200, margin);

//		 draws the images of fish
			for (int i = 0; i < labels.length; i++) {
				add(labels[i]);
				if (labels[i].hasReached()) {
					int tank = labels[i].getInTank();
					tanks[tank].add(labels[i]);
				}
				if (labels[i].isNotMoved()) {
					labels[i].setBounds(startX, corY.get(i), 25, 25);
				}
			}

//		// draws the shadows of fish underneath
			for (int i = 0; i < shadows.length; i++) {
//			add(shadows[i]);
//			shadows[i].setBounds(startX, corY.get(i), 25, 25);
				g.drawImage(shadows[i], startX, corY.get(i), this);
			}
			// draws the names of fish
			g.setFont(new Font("Myriad", Font.PLAIN, 12));
			for (int i = 0; i < shadows.length; i++) {
				g.drawString(names[i], startX + 100, corY.get(i) + margin);
			}
			// draws the enemies of fish
			for (int i = 0; i < enemies.length; i++) {
				g.drawString(enemies[i], startX + 200, corY.get(i) + margin);
			}

			if (ControlArea.isGamePaused()) {
				g.setColor(Color.red);
				g.setFont(new Font("Myriad", Font.BOLD, 20));
				g.drawString(pauseString, recs[1][2].x, recs[1][2].y);
			}
			g.drawImage(lorryImg, recs[5][lorryPosition].x, recs[5][lorryPosition].y, this);
			g.drawImage(finishLine, recs[3][5].x, recs[3][5].y, this);
		} else {
			g.setFont(new Font("Myriad", Font.BOLD, 15));
			g.drawString("Loading game...", recs[1][2].x, recs[1][2].y);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void componentResized(ComponentEvent e) {
//		System.out.println("componentresized");
		resizing = true;
		updateGui();
	}

	public void updateGui() {
		// updates width & height of the grid below
		oldWidth = WIDTH;
		WIDTH = getWidth();
		HEIGHT = getHeight() - 25 * (labels.length + 2);
		int padding = (WIDTH - 300) / 2;
		startX = padding;

//		System.out.println("width " + WIDTH + " " + "height " + HEIGHT);
//		System.out.println(cellSize);
		oldCellSize = cellSize;
		cellSize = Math.min(WIDTH, HEIGHT) / 6;

		int i = 0;
		int j = (labels.length + 1) * 25;
		for (int row = 0; row < 6; row += 1) {
			for (int col = 0; col < 6; col += 1) {
				Rectangle rec = new Rectangle(i, j, cellSize, cellSize);
				recs[row][col] = rec;
				i += cellSize;
			}
			i = 0;
			j += cellSize;
		}

		tankImg = tankImg.getScaledInstance(cellSize * 2, cellSize, Image.SCALE_FAST);
		tankicon = new ImageIcon(tankImg);
		for (JLabel tank : tanks) {
			tank.setIcon(tankicon);
			tank.revalidate();
		}

		int row = 4;
		int col = 0;
		for (int l = 0; l < tanks.length; l++) {
			tank = tanks[l];
			if (l == 4) {
				col = 2;
				row = 4;
			}
			tank.setBounds(recs[row][col].x, recs[row][col].y, cellSize * 2, cellSize);
			row -= 1;
		}
		finishLine = finishLine.getScaledInstance(cellSize*2, cellSize * 3, Image.SCALE_FAST);
		lorryImg = lorryImg.getScaledInstance(cellSize * 4, cellSize, Image.SCALE_FAST);

		plusImg = plusImg.getScaledInstance(cellSize / 2, cellSize / 2, Image.SCALE_FAST);
		plusIcon = new ImageIcon(plusImg);
		plus.setIcon(plusIcon);

		minusImg = minusImg.getScaledInstance(cellSize / 2, cellSize / 2, Image.SCALE_FAST);
		minusIcon = new ImageIcon(minusImg);
		minus.setIcon(minusIcon);

		plus.setBounds(0, recs[5][0].y, cellSize / 2, cellSize / 2);
		minus.setBounds(cellSize / 2 + 10, recs[5][0].y, cellSize / 2, cellSize / 2);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (!ControlArea.isGamePaused()) {
			if (e.getSource() == plus) {
				addTank(1);
			}
			if (e.getSource() == minus) {
				addTank(-1);
			}
		}

	}

	public void addTank(int no) {
		switch (no) {
		case 1:
			if (counter < tanks.length - 1) {
				counter += 1;
			}
			tanks[counter].setVisible(true);
			this.requestFocusInWindow();
			repaint();
			break;
		case -1:
			if (counter > 0 && tanks[counter].getComponentCount() == 0) {
				tanks[counter].setVisible(false);
				counter -= 1;
			}
			this.requestFocusInWindow();
			repaint();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!ControlArea.isGamePaused()) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (lorryPosition < 5) {
					lorryPosition += 1;
				}
				if (lorryPosition == 5) {
					checkValidSol();
					showResult();
				}
				// lorry.setBounds(recs[5][lorryPosition].x, recs[5][lorryPosition].y, cellSize
				// * 4, cellSize);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (lorryPosition<5) {
					if (lorryPosition > 0) {
						lorryPosition -= 1;
					}
				}
				// lorry.setBounds(recs[5][lorryPosition].x, recs[5][lorryPosition].y, cellSize
				// * 4, cellSize);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (!ControlArea.isGamePaused()) {
				ControlArea.getTimer().stop();
				ControlArea.getPause().setText("Fortfahren");
				ControlArea.getPause().setSelected(true);
				ControlArea.getTf().setForeground(Color.red);
				ControlArea.getAbgeben().setEnabled(false);
				controlArea = Spiel2.getRightPanel();
				controlArea.requestFocusInWindow();
				ControlArea.setGamePaused(true);
				return;
			} else {
				ControlArea.getTimer().start();
				ControlArea.getPause().setText("Pausieren");
				ControlArea.getPause().setSelected(false);
				ControlArea.getTf().setForeground(Color.white);
				ControlArea.getAbgeben().setEnabled(false);
				controlArea = Spiel2.getRightPanel();
				controlArea.requestFocusInWindow();
				ControlArea.setGamePaused(false);
			}
		}
		revalidate();
		repaint();
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
		enterName = new JTextField(1);
		if (abgeben) {
			gbc.gridy = 1;
			gbc.weighty = 0;
			frame.add(fortfahren, gbc);
			abgeben();
			if (correct) {
				textPane.setText("Yay! Deine Lösung ist richtig\n" + "Die optimale Anzahl an benötigten Aquarien ist "
						+ solver.getOptResult() + "\nJetzt darfst du Levelup");
			} else {
				textPane.setText(
						"Spiel verloren! Deine Lösung ist falsch.\n" + "Probiere noch mal mit einem neuen Spiel.\n"
								+ "Die optimale Anzahl an benötigten Aquarien ist " + solver.getOptResult());
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
				ControlArea.setCurrentLevel(0);
				ControlArea.setScore(0);
			}
		} else {
			textPane.setText("Oops!\n" + "Deine Lösung ist nicht gültig\n" + "Du hast entweder noch Fische übrig\n "
					+ "oder du hast noch einen leeren Aquarium.");
			gbc.gridy = 1;
			gbc.weighty = 0;
			frame.add(fortfahren, gbc);
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
			ControlArea.setCurrentLevel(0);
			ControlArea.setScore(0);
		}
		

		fortfahren.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ControlArea.isNewHs()) {
					String path = "highscore_spiel2.txt";
					File file = new File(path);
					setHighscore(enterName.getText(), score, file);
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
//				System.out.println("start");
				ControlArea.getSchwierigkeit().setEnabled(false);
				ControlArea.getEnd().setEnabled(true);
				ControlArea.getAbgeben().setEnabled(true);
				frame.dispose();
			};
		});
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public void setHighscore(String name, int score, File file) {
		// first add the name and new highscore at the end of the original file
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
//			str.append("Name: " + name + "\n");
//			str.append(String.valueOf(score));
			bufferedWriter.append(name + "\n");
			bufferedWriter.append(String.valueOf(score));
			bufferedWriter.close();

		} catch (IOException e) {
			// Exception handling
		}

		// creates a temp file to save new highscore, remove the old name and smallest
		// score
		File tempFile = new File(file.getAbsolutePath() + ".tmp");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
			String line = null;
			int count = 0;
			while ((line = br.readLine()) != null) {
				if ((line.startsWith(String.valueOf(ControlArea.getMin()))
						|| line.contains(ControlArea.getName_to_remove())) && count < 2) {
					count += 1;
					continue;
				}
				bufferedWriter.append(line);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			bufferedWriter.close();
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// writes from the tempFile back to the original file
		str = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile))) {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			String line = bufferedReader.readLine();
//			while (line != null) {
//				str.append(line + "\n");
//				line = bufferedReader.readLine();
//			}
			for (int i = 0; i < 6; i++) {
				str.append(line + "   ");
				if (i % 2 == 1) {
					str.append("\n");
				}
				bufferedWriter.append(line);
				bufferedWriter.newLine();
				line = bufferedReader.readLine();
			}
//			bufferedWriter.append(str);
			bufferedWriter.close();
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// Exception handling
		} catch (IOException e) {
			// Exception handling
		}

		ControlArea.setHighscoreString(str);
//		System.out.println(str.toString());
		// updates the highscore board
		ControlArea.getHs().setText(str.toString());
	}

	public void checkValidSol() {
		abgeben = true;
		for (JLabel tank : tanks) {
			if (tank.isVisible()) {
//				System.out.println(tank.getComponentCount());
				if (tank.getComponentCount() == 0) {
					abgeben = false;
					break;
				}
			}
		}
		for (MyLabel fish : labels) {
			if (fish.getInTank() < 0) {
				abgeben = false;
				break;
			}
		}
	}

	public void abgeben() {
		ControlArea.getTimer().stop();
		correct = checkSolution();
//		System.out.println(correct);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static int calSolution() {
		int max = 0;
		for (MyLabel fish : labels) {
//			System.out.println((String) fish.getName() + fish.getInTank());
			if (fish.getInTank() > max) {
				max = fish.getInTank();
			}
		}
		return max + 1;
	}

	public boolean checkSolution() {
		boolean correct = true;
		if (game.getLevel() == Spiel2.Level.TRAINING) {
			if (!(PlayArea.calSolution() <= solver.getOptResult() + 1)) {
				correct = false;
			} else {
				for (int fish = 0; fish < labels.length; fish++) {
					Node activeNode = game.getGraph().getNodeList().get(fish);
//					System.out.println("active: " + activeNode.getName());
					for (int enemy = 0; enemy < activeNode.getAdj_list().size(); enemy++) {
						Node enemyNode = activeNode.getAdj_list().get(enemy);
//						System.out.println("enemy: " + enemyNode.getName());
						int index = game.getGraph().getNodeList().indexOf(enemyNode);
						MyLabel e = labels[index];
						if (e.getInTank() == labels[fish].getInTank()) {
//							System.out.println(labels[fish].getName() + " has conflict with this enemy: " + e.getName());
							correct = false;
						}
					}

				}
			}
			if (correct) {
				ControlArea.setCurrentLevel(ControlArea.getCurrentLevel() + 1);
				ControlArea.setScore(ControlArea.getScore() + 4);
			}
		}
		if (game.getLevel() == Spiel2.Level.MITTEL || game.getLevel() == Spiel2.Level.SCHWER) {
			if (!(PlayArea.calSolution() == solver.getOptResult())) {
				correct = false;
			} else {
				for (int fish = 0; fish < labels.length; fish++) {
					Node activeNode = game.getGraph().getNodeList().get(fish);
					for (int enemy = 0; enemy < activeNode.getAdj_list().size(); enemy++) {
						Node enemyNode = activeNode.getAdj_list().get(enemy);
						int index = game.getGraph().getNodeList().indexOf(enemyNode);
						MyLabel e = labels[index];
						if (e.getInTank() == labels[fish].getInTank()) {
							correct = false;
						}
					}
				}
			}
			if (correct) {
				ControlArea.setCurrentLevel(ControlArea.getCurrentLevel() + 1);
				if (game.getLevel() == Spiel2.Level.MITTEL) {
					ControlArea.setScore(ControlArea.getScore() + 6);
				} else {
					ControlArea.setScore(ControlArea.getScore() + 8);
				}
			}

		}

		return correct;

	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the labels
	 */
	public static MyLabel[] getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public static void setLabels(MyLabel[] labels) {
		PlayArea.labels = labels;
	}

	/**
	 * @return the names
	 */
	public static String[] getNames() {
		return names;
	}

	/**
	 * @param names the names to set
	 */
	public static void setNames(String[] names) {
		PlayArea.names = names;
	}

	/**
	 * @return the enemies
	 */
	public static String[] getEnemies() {
		return enemies;
	}

	/**
	 * @param enemies the enemies to set
	 */
	public static void setEnemies(String[] enemies) {
		PlayArea.enemies = enemies;
	}

	/**
	 * @return the shadows
	 */
	public static Image[] getShadows() {
		return shadows;
	}

	/**
	 * @param shadows the shadows to set
	 */
	public static void setShadows(Image[] shadows) {
		PlayArea.shadows = shadows;
	}

	/**
	 * @return the tanks
	 */
	public static JLabel[] getTanks() {
		return tanks;
	}

	/**
	 * @param tanks the tanks to set
	 */
	public static void setTanks(JLabel[] tanks) {
		PlayArea.tanks = tanks;
	}

	/**
	 * @return the startX
	 */
	public static int getStartX() {
		return startX;
	}

	/**
	 * @param startX the startX to set
	 */
	public static void setStartX(int startX) {
		PlayArea.startX = startX;
	}

	/**
	 * @return the startY
	 */
	public static int getStartY() {
		return startY;
	}

	/**
	 * @param startY the startY to set
	 */
	public static void setStartY(int startY) {
		PlayArea.startY = startY;
	}

	/**
	 * @return the plus
	 */
	public JButton getPlus() {
		return plus;
	}

	/**
	 * @param plus the plus to set
	 */
	public void setPlus(JButton plus) {
		this.plus = plus;
	}

	/**
	 * @return the minus
	 */
	public JButton getMinus() {
		return minus;
	}

	/**
	 * @param minus the minus to set
	 */
	public void setMinus(JButton minus) {
		this.minus = minus;
	}

	/**
	 * @return the cellSize
	 */
	public int getCellSize() {
		return cellSize;
	}

	/**
	 * @param cellSize the cellSize to set
	 */
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	/**
	 * @return the gridPadding
	 */
	public int getGridPadding() {
		return gridPadding;
	}

	/**
	 * @param gridPadding the gridPadding to set
	 */
	public void setGridPadding(int gridPadding) {
		this.gridPadding = gridPadding;
	}

	/**
	 * @return the oldCellSize
	 */
	public int getOldCellSize() {
		return oldCellSize;
	}

	/**
	 * @param oldCellSize the oldCellSize to set
	 */
	public void setOldCellSize(int oldCellSize) {
		this.oldCellSize = oldCellSize;
	}

	/**
	 * @return the oldWidth
	 */
	public int getOldWidth() {
		return oldWidth;
	}

	/**
	 * @param oldWidth the oldWidth to set
	 */
	public void setOldWidth(int oldWidth) {
		this.oldWidth = oldWidth;
	}

	/**
	 * @return the tankImg
	 */
	public Image getTankImg() {
		return tankImg;
	}

	/**
	 * @param tankImg the tankImg to set
	 */
	public void setTankImg(Image tankImg) {
		this.tankImg = tankImg;
	}

	/**
	 * @return the tankicon
	 */
	public ImageIcon getTankicon() {
		return tankicon;
	}

	/**
	 * @param tankicon the tankicon to set
	 */
	public void setTankicon(ImageIcon tankicon) {
		this.tankicon = tankicon;
	}

	/**
	 * @return the recs
	 */
	public static Rectangle[][] getRecs() {
		return recs;
	}

	/**
	 * @param recs the recs to set
	 */
	public static void setRecs(Rectangle[][] recs) {
		PlayArea.recs = recs;
	}

	/**
	 * @return the corX
	 */
	public static ArrayList<Integer> getCorX() {
		return corX;
	}

	/**
	 * @param corX the corX to set
	 */
	public static void setCorX(ArrayList<Integer> corX) {
		PlayArea.corX = corX;
	}

	/**
	 * @return the corY
	 */
	public static ArrayList<Integer> getCorY() {
		return corY;
	}

	/**
	 * @param corY the corY to set
	 */
	public static void setCorY(ArrayList<Integer> corY) {
		PlayArea.corY = corY;
	}

	/**
	 * @return the tank
	 */
	public static JLabel getTank() {
		return tank;
	}

	/**
	 * @param tank the tank to set
	 */
	public static void setTank(JLabel tank) {
		PlayArea.tank = tank;
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return the lorryImg
	 */
	public Image getLorryImg() {
		return lorryImg;
	}

	/**
	 * @param lorryImg the lorryImg to set
	 */
	public void setLorryImg(Image lorryImg) {
		this.lorryImg = lorryImg;
	}

	/**
	 * @return the lorryPosition
	 */
	public int getLorryPosition() {
		return lorryPosition;
	}

	/**
	 * @param lorryPosition the lorryPosition to set
	 */
	public void setLorryPosition(int lorryPosition) {
		this.lorryPosition = lorryPosition;
	}

	/**
	 * @return the enterName
	 */
	public JTextField getEnterName() {
		return enterName;
	}

	/**
	 * @param enterName the enterName to set
	 */
	public void setEnterName(JTextField enterName) {
		this.enterName = enterName;
	}

	/**
	 * @return the solver
	 */
	public Solver getSolver() {
		return solver;
	}

	/**
	 * @param solver the solver to set
	 */
	public void setSolver(Solver solver) {
		this.solver = solver;
	}

}
