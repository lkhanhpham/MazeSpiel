//package Spiel4;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//
//import team07.main.LeftPanel;
//import team07.main.MyFrame;
//
//public class Spiel4 extends JPanel {
//	private JButton zurueck;
//	public Spiel4() {
//		setBackground(Color.GRAY);
//		zurueck=new JButton("Zurueck");
//		add(zurueck);
//		zurueck.setFocusable(false);
//		zurueck.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				MyFrame.getCl().show(MyFrame.getPanelCont(), "1");
//				LeftPanel.getDp().requestFocusInWindow();
//			}
//			
//	});
//	}
//
//}

package Spiel4;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import team07.main.LeftPanel;
import team07.main.MyFrame;
import team07.main.MyPanel;
import team07.main.RightPanel;

public class Spiel4 extends JPanel implements ActionListener, FocusListener {
	private LeftPanel lPanel;
	
	private JButton zurueck;
	private GamePanel gamePanel;
	private SelectPanel selectPanel;
	private String difficulty;

	private static int width;
	private static int height;
	
	public Spiel4(LeftPanel lPanel2) {
//		addKeyListener(this);
//		this.lPanel = lPanel;
		initGame();
		lPanel = lPanel2;
	}

	public void initGame() {
		setBackground(Color.white);
		setLayout(new GridBagLayout());
		difficulty = "Training";
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		gamePanel = new GamePanel(difficulty);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		gbc.weighty = 1.0;
		add(gamePanel, gbc);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();

		selectPanel = new SelectPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		gbc.weighty = 1.0;
		add(selectPanel, gbc);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Component c = (Component) e.getSource();
				width = gamePanel.getWidth();
				height = gamePanel.getHeight();

			}
		});

		selectPanel.getBeenden().addActionListener(this);
		selectPanel.getPause().addActionListener(this);
		selectPanel.getStart().addActionListener(this);
		selectPanel.getLösungAbgeben().addActionListener(this);
		selectPanel.getAnleitung().addActionListener(this);
		selectPanel.getZurueck().addActionListener(this);
	
		setFocusable(true);
//		requestFocus();
		requestFocusInWindow();

	}

	public JButton getZurueck() {
		return zurueck;
	}

	public void setZurueck(JButton zurueck) {
		this.zurueck = zurueck;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public SelectPanel getSelectPanel() {
		return selectPanel;
	}

	public void setSelectPanel(SelectPanel selectPanel) {
		this.selectPanel = selectPanel;
	}

	/**
	 * @return the width
	 */
	public static int getGamePanelWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public static void setGamePanelWidth(int width) {
		Spiel4.width = width;
	}

	/**
	 * @return the height
	 */
	public static int getGamePanelHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public static void setGamePanelHeight(int height) {
		Spiel4.height = height;
	}

	public void Spielanleitung() {
		JFrame frame = new JFrame("Spielanleitung");

		JOptionPane.showMessageDialog(frame,
				"Auf dem Weg durch die Galaxy bist du auf der Erkundungsmission. Um für die Sicherheit deiner \r\n"
				+ "Flotte zu sorgen, musst du einen Hub-Planet in bei einem deiner Schiffe platzieren, damit bei einem\r\n"
				+ " Angriff alle Schiffe in kürzester Zeit in einen sicheren Hafen gelangen können.\r\n"
				+ "Ziehe mit der Maus die Planeten am oberen Rand die Planeten auf die Schiffe, sodass die Abstände in \r\n"
				+ "Kästchen gemessen (Manhattan-Distance) zwischen den Schiffen und den Hub-Planeten so klein wie \r\n"
				+ "möglich sind.\r\n"
				+ "Klicke auf „Start“ um deine Mission zu erfüllen. Mit „Pause“ kannst du dein Spiel pausieren, um \r\n"
				+ "Ablenkungen aus dem Weg zu schaffen. Mit dem Button „Lösung abgeben“ sollst du deine \r\n"
				+ "Planetenaufteilung dem Sicherheitsrat übermitteln. Durch die Pfeiltasten (und W,A,S,D) kannst du \r\n"
				+ "die Sonne zu einem deiner gesetzten Planeten schieben, um ebenfalls die Lösung dem Rat zu \r\n"
				+ "übermitteln.\r\n"
				+ "Bei guter Aufteilung der Planeten erhältst du die Zustimmung des Rates und sollst im nächsten Level \r\n"
				+ "deine Fähigkeiten erneut unter Beweis stellen. Mit „Beenden“ gibst du dein Spiel ab und bekommst \r\n"
				+ "vom Rat deinen Score gutgeschrieben. Mit dem Dropdownmenü kannst du die Schwierigkeit des \r\n"
				+ "Spiels anpassen. Mit dem „Zurueck“ Button kannst du auf das Spiel verlassen und zum Labyrinth \r\n"
				+ "zurückgehen. Der Butten „HS Reset“ setzt alle Highscores zurück. Der Timer zählt bei entsprechender\r\n"
				+ " Schwierigkeit runter, damit du zeigen kannst, dass du auch unter Zeitdruck die Probleme der Mission\r\n"
				+ " lösen kannst.\r\n"
				+ "",
				"Spielanleitung", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectPanel.getPause()) {
			System.out.println(3);
//			gamePanel.getDp().setPause(true);
			if (gamePanel.getDp().isPause() == true) {
				gamePanel.getDp().setPause(false);
				
			}
			else {
				gamePanel.getDp().setPause(true);
			}
			repaint();
			
		}
		if (e.getSource() == selectPanel.getZurueck()) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "1");
			LeftPanel.getDp().requestFocusInWindow();
			lPanel.removeGame();
			lPanel.drawGame();
			RightPanel.getNeustart().doClick();
			RightPanel.setMaze();
		}
		if (e.getSource() == selectPanel.getAnleitung()) {
			Spielanleitung();
		}
		if (e.getSource() == selectPanel.getLösungAbgeben()) {
			System.out.println(gamePanel.getDp().getGrid().isSolutionValid());
		}
		if (e.getSource() == selectPanel.getBeenden()) {
			System.out.println(gamePanel.getDp().getGrid().isSolutionValid());
		}

		if (e.getSource() == selectPanel.getDropdown()) {
			difficulty = (String) selectPanel.getDropdown().getSelectedItem();
			gamePanel.getDp().setLevel(1);
		}
		if (e.getSource() == selectPanel.getStart()) {
//			remove(selectPanel);
//			remove(gamePanel);
//
//			initGame();
//			add(gamePanel);
//			add(selectPanel);
			gamePanel.getDp().setStart(true);
			selectPanel.getLösungAbgeben().setEnabled(true);
		}
//		revalidate();
		repaint();

	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}