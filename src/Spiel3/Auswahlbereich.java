package Spiel3;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import team07.main.LeftPanel;
import team07.main.MyFrame;

public class Auswahlbereich extends JPanel implements ActionListener, KeyListener {
	public static int getScore0() {
		return score0;
	}

	public static void setScore0(int score0) {
		Auswahlbereich.score0 = score0;
	}

	public static int getLevel0() {
		return level0;
	}

	public static void setLevel0(int level0) {
		Auswahlbereich.level0 = level0;
	}

	public static int getZielwert0() {
		return zielwert0;
	}

	public static void setZielwert0(int zielwert0) {
		Auswahlbereich.zielwert0 = zielwert0;
	}

	/**
	 * Es werden 14 Panels für den Auswahlbereich erzeugt
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	private JPanel panel8;
	private JPanel panel9;
	private JPanel panel10;
	private JPanel panel11;
	private JPanel panel12;
	private JPanel panel13;
	private JPanel panel14;

	/**
	 * Es werden alle benötigten Komponenten für den Auswahlbereich erzeugt und
	 * jeweils einem Panel hinzugefügt
	 */
	private static JButton zurueck;
	private static JLabel auswahlbereich;
	private static JButton start;
	private static JButton beenden;
	private static JButton pausieren;
	private static JButton anleitung;
	private static JButton zurücksetzen;
	private static JButton abgabe;
	private static JComboBox<String> schwierigkeitsgrad;
	public static JLabel score;
	private static JLabel highscore;
	public static JLabel highscore1;
	public static JLabel highscore2;
	public static JLabel highscore3;
	public static JLabel level;
	public static JLabel zeit;
	public static JLabel zielwert;

	String[] a = { "Training", "mittel", "schwer" };
	String name1;
	String name2;
	String name3;

	/**
	 * Für jeden Schwierigkeitsgrad gibt es Variablen für Level, Score und Timer
	 */
	private static int score0 = 0;
	private static int level0 = 0;

	public static int score1 = 0;
	public static int level1 = 0;

	public static int score2 = 0;
	public static int level2 = 0;

	public static int score3 = 0;
	public static int level3 = 0;

	public static int switchpanel1 = 0;
	public static int switchpanel2 = 0;
	public static int switchpanel3 = 0;

	private static int zeitminuten0 = 0;
	private static int zeitsekunden0 = 0;
	private static String zeitsekunden_string0 = String.format("%02d", zeitsekunden0);
	private static String zeitminuten_string0 = String.format("%02d", zeitminuten0);

	private static boolean minuten = false;
	private static int abgelaufenezeit = 0;
	private static int zeitminuten = 0;
	private static int zeitsekunden = 0;
	private static String zeitsekunden_string = String.format("%02d", zeitsekunden);
	private static String zeitminuten_string = String.format("%02d", zeitminuten);
	public static Timer timer = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			zeitsekunden++;
			zeitsekunden_string = String.format("%02d", zeitsekunden);
			zeitminuten_string = String.format("%02d", zeitminuten);
			if (minuten != true) {
				zeit.setText("Zeit: 00:" + zeitsekunden_string);
			} else {
				zeit.setText("Zeit: " + zeitminuten_string + ":" + zeitsekunden);
			}

			if (zeitsekunden == 60) {
				minuten = true;
				zeitsekunden = 0;
				zeitminuten++;
				zeitminuten_string = String.format("%02d", zeitminuten);
				zeit.setText("Zeit: " + zeitminuten_string + ":" + zeitsekunden_string);
			}

		}

	});

	private static int zeitminuten1 = 0;
	private static int zeitsekunden1 = 60;
	private static String zeitminuten1_string;
	private static String zeitsekunden1_string;
	public static Timer timer1 = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			zeitsekunden1--;
			zeitsekunden1_string = String.format("%02d", zeitsekunden1);
			zeitminuten1_string = String.format("%02d", zeitminuten1);
			zeit.setText("Zeit:" + " " + zeitminuten1_string + ":" + zeitsekunden1_string);

			if (zeitsekunden1 == 0) {
				timer1.stop();
				zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
				level.setText("Level:" + " " + level0);
				zielwert.setText("Zielwert:" + " " + zielwert0);
				score.setText("Score:" + " " + score0);
				Auswahlbereich.switchpanel2 = 0;
				SpielbereichMittel1.lösung2 = 0;
				SpielbereichMittel1.anzahlkanten2 = 0;
				SpielbereichMittel2.lösung2 = 0;
				SpielbereichMittel2.anzahlkanten2 = 0;
				SpielbereichMittel1.gameover2();
				Spiel3.cl.show(Spiel3.panel, "0");
			}

		}

	});

	private static int zeitminuten2 = 0;
	private static int zeitsekunden2 = 30;
	private static String zeitminuten2_string;
	private static String zeitsekunden2_string;
	public static Timer timer2 = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			zeitsekunden2--;
			zeitsekunden2_string = String.format("%02d", zeitsekunden2);
			zeitminuten2_string = String.format("%02d", zeitminuten2);
			zeit.setText("Zeit:" + " " + zeitminuten2_string + ":" + zeitsekunden2_string);

			if (zeitsekunden2 == 0) {
				timer2.stop();
				zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
				level.setText("Level:" + " " + level0);
				zielwert.setText("Zielwert:" + " " + zielwert0);
				score.setText("Score:" + " " + score0);
				Auswahlbereich.switchpanel3 = 0;
				SpielbereichSchwer1.lösung3 = 0;
				SpielbereichSchwer1.anzahlkanten3 = 0;
				SpielbereichSchwer2.lösung3 = 0;
				SpielbereichSchwer2.anzahlkanten3 = 0;
				SpielbereichSchwer1.gameover3();
				Spiel3.cl.show(Spiel3.panel, "0");
			}

		}

	});

	private static int zielwert0 = 0;
	private static int zielwert1 = 0;
	private static int zielwert2 = 0;
	private static int zielwert3 = 0;
	public static int stateswitchTraining = 0;
	public static int stateswitchMittel = 0;
	public static int stateswitchSchwer = 0;

	public static StringBuilder highscoreString1;
	public static StringBuilder highscoreString2;
	public static StringBuilder highscoreString3;

	private LeftPanel lPanel;

	/**
	 * In dem Konstruktor werden alle Komponenten erzeugt und einem Panel
	 * hinzugefügt
	 */
	public Auswahlbereich(LeftPanel lPanel2) {
		setLayout(new GridLayout(14, 1));
		panel1 = createPanel();
		panel2 = createPanel();
		panel3 = createPanel();
		panel4 = createPanel();
		panel5 = createPanel();
		panel6 = createPanel();
		panel7 = createPanel();
		panel8 = createPanel();
		panel9 = createPanel();
		panel10 = createPanel();
		panel11 = createPanel();
		panel12 = createPanel();
		panel13 = createPanel();
		panel14 = createPanel();
		lPanel = lPanel2;

		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		add(panel6);
		add(panel7);
		add(panel8);
		add(panel9);
		add(panel10);
		add(panel11);
		add(panel12);
		add(panel13);
		add(panel14);

		zurueck = new JButton("Zurück");
		auswahlbereich = new JLabel("Auswahlbereich");
		anleitung = new JButton("Anleitung");
		start = new JButton("Start");
		beenden = new JButton("Beenden");
		pausieren = new JButton("Pause");
		zurücksetzen = new JButton("Zurücksetzen");
		abgabe = new JButton("Lösungsabgabe");
		schwierigkeitsgrad = new JComboBox<String>(a);
		score = new JLabel("Score:" + " " + score0);
		highscore = new JLabel("Highscore");
		highscore1 = new JLabel("1)");
		highscore2 = new JLabel("2)");
		highscore3 = new JLabel("3)");
		level = new JLabel("Level:" + " " + level0);
		zeit = new JLabel("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
		zielwert = new JLabel("Zielwert:" + " " + zielwert0);

		zurueck.setFocusable(false);
		anleitung.setFocusable(false);
		start.setFocusable(false);
		beenden.setFocusable(false);
		pausieren.setFocusable(false);
		zurücksetzen.setFocusable(false);
		abgabe.setFocusable(false);
		schwierigkeitsgrad.setFocusable(false);

		schwierigkeitsgrad.setSelectedItem("Training");

		zurueck.addActionListener(this);
		anleitung.addActionListener(this);
		start.addActionListener(this);
		beenden.addActionListener(this);
		pausieren.addActionListener(this);
		zurücksetzen.addActionListener(this);
		abgabe.addActionListener(this);
		schwierigkeitsgrad.addActionListener(this);

		this.addKeyListener(this);
		setFocusable(true);

		panel1.add(zurueck);
		panel1.add(start);
		panel2.add(anleitung);
		panel3.add(beenden);
		panel4.add(pausieren);
		panel4.add(schwierigkeitsgrad);
		panel5.add(zurücksetzen);
		panel6.add(abgabe);
		panel7.add(highscore);
		panel8.add(highscore1);
		panel9.add(highscore2);
		panel10.add(highscore3);
		panel11.add(score);
		panel12.add(level);
		panel13.add(zeit);
		panel14.add(zielwert);

		String path = "Highscore_Spiel3.txt";

		highscoreString1 = new StringBuilder();
		highscoreString2 = new StringBuilder();
		highscoreString3 = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String line = bufferedReader.readLine();
			for (int i = 0; i < 6; i++) {
				if (i == 0 || i == 1) {
					highscoreString1.append(line + "   ");
					line = bufferedReader.readLine();
				}
				if (i == 2 || i == 3) {
					highscoreString2.append(line + "   ");
					line = bufferedReader.readLine();
				}
				if (i == 4 || i == 5) {
					highscoreString3.append(line + "   ");
					line = bufferedReader.readLine();
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		highscore1.setText(highscoreString1.toString());
		highscore2.setText(highscoreString2.toString());
		highscore3.setText(highscoreString3.toString());

	}

	public static JFrame frame;
	public static JLabel label;
	public static JTextField textfield;
	public static JButton speichern;

	public static void highscore() {

		frame = new JFrame("Neuer Highscore");
		frame.setLayout(new GridLayout(3, 1));
		label = new JLabel("Neuer Highscore");
		textfield = new JTextField();
		speichern = new JButton("Speichern");
		frame.add(label);
		frame.add(textfield);
		frame.add(speichern);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				savehighscore();
				frame.dispose();

			}

		});
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public static String removename;
	public static int min;

	public static boolean findMinScore() {
		String path = "Highscore_Spiel3.txt";
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> punkte = new ArrayList<Integer>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String line = bufferedReader.readLine();
			for (int i = 0; i < 6; i++) {
				if (i % 2 == 0) {
					highscoreString1.append(line + "   ");
					name.add(line);
				}
				if (i % 2 == 1) {
					highscoreString2.append(line + "   ");
					punkte.add(Integer.parseInt(line));
				}
				line = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		min = punkte.get(0);
		for (int j = 0; j < punkte.size(); j++) {
			if (punkte.get(j) < min) {
				min = punkte.get(j);
			}
		}
		removename = name.get(punkte.indexOf(min));

		String combo6 = (String) schwierigkeitsgrad.getSelectedItem();
		int score = 0;
		if (combo6 == "Training") {
			score = score1;
		}
		if (combo6 == "mittel") {
			score = score2;
		}
		if (combo6 == "schwer") {
			score = score3;
		}
		if (score >= min) {
			return true;
		}

		return false;

	}

	public static void savehighscore() {
		String combo6 = (String) schwierigkeitsgrad.getSelectedItem();
		int score = 0;
		if (combo6 == "Training") {
			score = score1;
		}
		if (combo6 == "mittel") {
			score = score2;
		}
		if (combo6 == "schwer") {
			score = score3;
		}
		String path = "Highscore_Spiel3.txt";
		File newFile = new File(path + ".tmp");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
			String line = bufferedReader.readLine();
			int count = 0;
			for (int i = 0; i < 6; i++) {
				System.out.println(line);
				if (i % 2 == 0 && count < 2) {
					if (line.contains(removename)) {
						count += 1;
						line = bufferedReader.readLine();
						continue;
					}
				}
				if (i % 2 == 1) {
					int lineint = Integer.parseInt(line);
					if (lineint == min && count < 2) {
						count += 1;
						line = bufferedReader.readLine();
						continue;
					}
				}
				bufferedWriter.append(line);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				line = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
			String line = bufferedReader.readLine();
			while (line != null) {
				bufferedWriter.append(line);
				bufferedWriter.newLine();
				line = bufferedReader.readLine();
			}
			bufferedWriter.append(textfield.getText() + "\n");

			bufferedWriter.append(String.valueOf(score));
			bufferedWriter.close();
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		highscoreString1 = new StringBuilder();
		highscoreString2 = new StringBuilder();
		highscoreString3 = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			String line = bufferedReader.readLine();
			for (int i = 0; i < 6; i++) {
				if (i == 0 || i == 1) {
					highscoreString1.append(line + "   ");
					line = bufferedReader.readLine();
				}
				if (i == 2 || i == 3) {
					highscoreString2.append(line + "   ");
					line = bufferedReader.readLine();
				}
				if (i == 4 || i == 5) {
					highscoreString3.append(line + "   ");
					line = bufferedReader.readLine();
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		highscore1.setText(highscoreString1.toString());
		highscore2.setText(highscoreString2.toString());
		highscore3.setText(highscoreString3.toString());

	}

	private static JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		return panel;
	}

	public void Spielanleitung() {
		JFrame frame = new JFrame("Spielanleitung");
		JOptionPane.showMessageDialog(frame, "Hier kommt die Spielanleitung rein", "Spielanleitung",
				JOptionPane.QUESTION_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	/**
	 * In der actionPerformed Methode werden den Buttons ihre Funktionalität
	 * zugewiesen
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.zurueck) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "1");
			LeftPanel.getDp().requestFocusInWindow();
			lPanel.removeGame();
			lPanel.drawGame();
		}

		if (e.getSource() == this.anleitung) {
			String combo1 = (String) schwierigkeitsgrad.getSelectedItem();
			if (combo1 == "Training") {
				SpielbereichTraining1.pausierenstart1();
				SpielbereichTraining2.pausierenstart1();
				Spielanleitung();
				SpielbereichTraining1.pausierenende1();
				SpielbereichTraining2.pausierenende1();
			}

			if (combo1 == "mittel") {
				SpielbereichMittel1.pausierenstart2();
				SpielbereichMittel2.pausierenstart2();
				Spielanleitung();
				SpielbereichMittel1.pausierenende2();
				SpielbereichMittel2.pausierenende2();
			}

			if (combo1 == "schwer") {
				SpielbereichSchwer1.pausierenstart3();
				SpielbereichSchwer2.pausierenstart3();
				Spielanleitung();
				SpielbereichSchwer1.pausierenende3();
				SpielbereichSchwer2.pausierenende3();
			}
		}

		if (e.getSource() == this.start) {
			String combo2 = (String) schwierigkeitsgrad.getSelectedItem();
			if (combo2 == "Training") {
				SpielbereichTraining1.mouseclick=true;
				SpielbereichTraining2.mouseclick=true;
				//pausieren.setText("Pause");
				SpielbereichTraining1 spielbereichTraining1 = new SpielbereichTraining1();
				Spiel3.panel.add(spielbereichTraining1, "1");
				Spiel3.cl.show(Spiel3.panel, "1");
				spielbereichTraining1.requestFocusInWindow();
				spielbereichTraining1.setFocusable(true);
				SpielbereichTraining1.lösung1=0;
				score1 = 0;
				timerreset();
				timer.start();
				level1 = +1;
				level.setText("Level:" + " " + level1);
				zielwert1 = Graph.OptimaleLösung1(spielbereichTraining1.getGraph()) * 2;
				zielwert.setText("Zielwert:" + " " + zielwert1);
				spielbereichTraining1=null;

			}

			if (combo2 == "mittel") {
				SpielbereichMittel1 spielbereichMittel1 = new SpielbereichMittel1();
				Spiel3.panel.add(spielbereichMittel1, "3");
				Spiel3.cl.show(Spiel3.panel, "3");
				spielbereichMittel1.requestFocusInWindow();
				spielbereichMittel1.setFocusable(true);
				SpielbereichMittel1.lösung2 = 0;
				score2 = 0;
				timer1reset();
				timer1.start();
				level2 = +1;
				level.setText("Level:" + " " + level2);
				// zielwert2=(int)
				// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich1.getGraph())*1.5);
				zielwert2 = Graph.OptimaleLösung1(spielbereichMittel1.getGraph());
				System.out.println(Graph.OptimaleLösung2(spielbereichMittel1.getGraph()));
				zielwert.setText("Zielwert:" + " " + zielwert2);
				spielbereichMittel1 = null;
			}

			if (combo2 == "schwer") {
				SpielbereichSchwer1 spielbereichSchwer1 = new SpielbereichSchwer1();
				Spiel3.panel.add(spielbereichSchwer1, "5");
				Spiel3.cl.show(Spiel3.panel, "5");
				spielbereichSchwer1.requestFocusInWindow();
				spielbereichSchwer1.setFocusable(true);
				SpielbereichSchwer1.lösung3=0;
				score3 = 0;
				timer2reset();
				timer2.start();
				level3 = +1;
				level.setText("Level:" + " " + level3);
				zielwert3 = Graph.OptimaleLösung1(spielbereichSchwer1.graph);
				zielwert.setText("Zielwert:" + " " + zielwert3);
				spielbereichSchwer1=null;
			}

		}

		if (e.getSource() == this.pausieren) {
			String combo3 = (String) schwierigkeitsgrad.getSelectedItem();
			if (combo3 == "Training") {
				if (stateswitchTraining == 0) {
					SpielbereichTraining1.pausierenstart1();
					stateswitchTraining = 1;
					//pausieren.setText("Weiter");
				} else if (stateswitchTraining == 1) {
					SpielbereichTraining1.pausierenende1();
					stateswitchTraining = 0;
					//pausieren.setText("Pause");
				}
			}

			if (combo3 == "mittel") {
				if (stateswitchMittel == 0) {
					SpielbereichMittel1.pausierenstart2();
					SpielbereichMittel2.pausierenstart2();
					pausieren.setText("Fortfahren");
					stateswitchMittel = 1;
				} else if (stateswitchMittel == 1) {
					SpielbereichMittel1.pausierenende2();
					SpielbereichMittel2.pausierenende2();
					stateswitchMittel = 0;
					pausieren.setText("Pause");
				}

			}

			if (combo3 == "schwer") {
				if(stateswitchSchwer == 0) {
					SpielbereichSchwer1.pausierenstart3();
					SpielbereichSchwer2.pausierenstart3();
					stateswitchSchwer=1;
				} else if(stateswitchSchwer == 1) {
					SpielbereichSchwer1.pausierenende3();
					SpielbereichSchwer2.pausierenende3();
					stateswitchSchwer=0;
				}

			}
		}

		if (e.getSource() == this.abgabe) {
			String combo4 = (String) schwierigkeitsgrad.getSelectedItem();
			if (combo4 == "Training") {
				if (switchpanel1 % 2 == 0) {
					boolean alleangebunden = false;
					int b0 = 0;
					int b1 = 0;
					int b2 = 0;
					int b3 = 0;
					if (SpielbereichTraining1.angebunden1.size() < 3 && SpielbereichTraining1.angebunden2.size() < 3) {
						alleangebunden = false;
					} else if (SpielbereichTraining1.angebunden1.size() >= 3
							&& SpielbereichTraining1.angebunden2.size() >= 3) {
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

				} else if (switchpanel1 % 2 == 1) {
					boolean alleangebunden = false;
					int b0 = 0;
					int b1 = 0;
					int b2 = 0;
					int b3 = 0;
					if (SpielbereichTraining2.angebunden1.size() < 3 && SpielbereichTraining2.angebunden2.size() < 3) {
						alleangebunden = false;
					} else if (SpielbereichTraining2.angebunden1.size() >= 3
							&& SpielbereichTraining2.angebunden2.size() >= 3) {
						for (int i = 0; i < SpielbereichTraining2.angebunden.size(); i++) {
							for (int j = 0; j < SpielbereichTraining2.angebunden1.size(); j++) {
								if (SpielbereichTraining2.angebunden.get(i).get(j) == 0) {
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

					if (alleangebunden == true && SpielbereichTraining2.getLösung1() <= Auswahlbereich.getZielwert1()) {
						Auswahlbereich.switchpanel1++;
						Auswahlbereich.timer.stop();
						SpielbereichTraining2.win1();
						SpielbereichTraining1 spielbereichTraining1 = new SpielbereichTraining1();
						Spiel3.panel.add(spielbereichTraining1, "1");
						Spiel3.cl.show(Spiel3.panel, "1");
						spielbereichTraining1.requestFocusInWindow();
						spielbereichTraining1.setFocusable(true);
						Auswahlbereich.timerreset();
						Auswahlbereich.timer.start();
						Auswahlbereich.score1 += SpielbereichTraining2.getAnzahlkanten1();
						Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.score1);
						SpielbereichTraining2.setLösung1(0);
						SpielbereichTraining2.setAnzahlkanten1(0);
						Auswahlbereich.level1++;
						Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel1());
						Auswahlbereich.setZielwert1(Graph.OptimaleLösung1(spielbereichTraining1.getGraph()) * 2);
						Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert1());
						spielbereichTraining1 = null;
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

			if (combo4 == "mittel") {
				if (switchpanel2 % 2 == 0) {
					boolean alleangebunden = false;
					int b0 = 0;
					int b1 = 0;
					int b2 = 0;
					int b3 = 0;
					int b4 = 0;
					int b5 = 0;
					int b6 = 0;
					int b7 = 0;
					if (SpielbereichMittel1.angebunden1.size() < 7 && SpielbereichMittel1.angebunden2.size() < 7) {
						alleangebunden = false;
					} else if (SpielbereichMittel1.angebunden1.size() >= 7
							&& SpielbereichMittel1.angebunden2.size() >= 7) {
						for (int i = 0; i < SpielbereichMittel1.angebunden.size(); i++) {
							for (int j = 0; j < SpielbereichMittel1.angebunden1.size(); j++) {
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 0) {
									alleangebunden = true;
									b0++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 1) {
									alleangebunden = true;
									b1++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 2) {
									alleangebunden = true;
									b2++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 3) {
									alleangebunden = true;
									b3++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 4) {
									alleangebunden = true;
									b4++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 5) {
									alleangebunden = true;
									b5++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 6) {
									alleangebunden = true;
									b6++;
								}
								if (SpielbereichMittel1.angebunden.get(i).get(j) == 7) {
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
					System.out.println(SpielbereichMittel1.lösung2);
					System.out.println(zielwert2);
					if (alleangebunden == true && SpielbereichMittel1.lösung2 <= Auswahlbereich.zielwert2) {
						alleangebunden = false;
						switchpanel2++;
						timer1.stop();
						SpielbereichMittel1.win2();
						SpielbereichMittel2 spielbereichMittel2 = new SpielbereichMittel2();
						Spiel3.panel.add(spielbereichMittel2, "4");
						Spiel3.cl.show(Spiel3.panel, "4");
						spielbereichMittel2.requestFocusInWindow();
						spielbereichMittel2.setFocusable(true);
						timer1reset();
						timer1.start();
						Auswahlbereich.score2 += (SpielbereichMittel1.anzahlkanten2 * 2);
						Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.score2);
						SpielbereichMittel1.anzahlkanten2 = 0;
						Auswahlbereich.level2 += 1;
						Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.level2);
						// Auswahlbereich.zielwert2=(int)
						// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich11.getGraph())*1.5);
						Auswahlbereich.zielwert2 = (Graph.OptimaleLösung1(SpielbereichMittel2.getGraph()));
						System.out.println(Graph.OptimaleLösung2(spielbereichMittel2.getGraph()));
						Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.zielwert2);
						spielbereichMittel2 = null;
					} else {
						timer1.stop();
						zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
						level.setText("Level:" + " " + level0);
						zielwert.setText("Zielwert:" + " " + zielwert0);
						score.setText("Score:" + " " + score0);
						SpielbereichMittel1.anzahlkanten2 = 0;
						Auswahlbereich.switchpanel2 = 0;
						SpielbereichMittel1.gameover2();
						Spiel3.cl.show(Spiel3.panel, "0");
					}
				} else if (switchpanel2 % 2 == 1) {
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
					} else if (SpielbereichMittel2.angebunden1.size() >= 7
							&& SpielbereichMittel2.angebunden2.size() >= 7) {
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
					System.out.println(zielwert2);
					if (alleangebunden == true && SpielbereichMittel2.lösung2 <= Auswahlbereich.zielwert2) {
						alleangebunden = false;
						switchpanel2++;
						timer1.stop();
						SpielbereichMittel2.win2();
						SpielbereichMittel1 spielbereichMittel1 = new SpielbereichMittel1();
						Spiel3.panel.add(spielbereichMittel1, "2");
						Spiel3.cl.show(Spiel3.panel, "2");
						spielbereichMittel1.requestFocusInWindow();
						spielbereichMittel1.setFocusable(true);
						timer1reset();
						timer1.start();
						Auswahlbereich.score2 += (SpielbereichMittel2.anzahlkanten2 * 2);
						Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.score2);
						SpielbereichMittel2.anzahlkanten2 = 0;
						Auswahlbereich.level2 += 1;
						Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.level2);
						// Auswahlbereich.zielwert2=(int)
						// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich1.getGraph())*1.5);
						Auswahlbereich.zielwert2 = (Graph.OptimaleLösung1(spielbereichMittel1.getGraph()));
						System.out.println(Graph.OptimaleLösung2(spielbereichMittel1.getGraph()));
						Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.zielwert2);
						spielbereichMittel1 = null;
					} else {
						timer1.stop();
						zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
						level.setText("Level:" + " " + level0);
						zielwert.setText("Zielwert:" + " " + zielwert0);
						score.setText("Score:" + " " + score0);
						SpielbereichMittel2.anzahlkanten2 = 0;
						Auswahlbereich.switchpanel2 = 0;
						SpielbereichMittel2.gameover2();
						Spiel3.cl.show(Spiel3.panel, "0");
					}
				}
			}

			if (combo4 == "schwer") {
				if(switchpanel3%2==0) {
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
					if (SpielbereichSchwer1.angebunden1.size() < 11 && SpielbereichSchwer1.angebunden2.size() < 11) {
						alleangebunden = false;
					} else if (SpielbereichSchwer1.angebunden1.size() >= 11 && SpielbereichSchwer1.angebunden2.size() >= 11) {
						for (int i = 0; i < SpielbereichSchwer1.angebunden.size(); i++) {
							for (int j = 0; j < SpielbereichSchwer1.angebunden1.size(); j++) {
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 0) {
									alleangebunden = true;
									b0++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 1) {
									alleangebunden = true;
									b1++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 2) {
									alleangebunden = true;
									b2++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 3) {
									alleangebunden = true;
									b3++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 4) {
									alleangebunden = true;
									b4++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 5) {
									alleangebunden = true;
									b5++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 6) {
									alleangebunden = true;
									b6++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 7) {
									alleangebunden = true;
									b7++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 8) {
									alleangebunden = true;
									b8++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 9) {
									alleangebunden = true;
									b9++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 10) {
									alleangebunden = true;
									b10++;
								}
								if (SpielbereichSchwer1.angebunden.get(i).get(j) == 11) {
									alleangebunden = true;
									b11++;
								}
							}
						}

					}
					if (b0 == 0 || b1 == 0 || b2 == 0 || b3 == 0 || b4 == 0 || b5 == 0 || b6 == 0 || b7 == 0 || b8 == 0 || b9 == 0 || b10 == 0 || b11 ==0) {
						alleangebunden = false;
					}
					System.out.println(alleangebunden);
					System.out.println(SpielbereichSchwer1.lösung3);
					System.out.println(Auswahlbereich.getZielwert3());
					if (alleangebunden == true && SpielbereichSchwer1.lösung3 <= Auswahlbereich.getZielwert3()) {
						Auswahlbereich.switchpanel3++;
						Auswahlbereich.timer2.stop();
						SpielbereichSchwer1.win3();
						SpielbereichSchwer2 spielbereichSchwer2 = new SpielbereichSchwer2();
						Spiel3.panel.add(spielbereichSchwer2, "6");
						Spiel3.cl.show(Spiel3.panel, "6");
						spielbereichSchwer2.requestFocusInWindow();
						spielbereichSchwer2.setFocusable(true);
						Auswahlbereich.timer2reset();
						Auswahlbereich.timer2.start();
						Auswahlbereich.score3 += (SpielbereichSchwer1.anzahlkanten3 * 3);
						Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore3());
						SpielbereichSchwer1.lösung3 = 0;
						SpielbereichSchwer1.anzahlkanten3 = 0;
						Auswahlbereich.level3++;
						Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.level3);
						// Auswahlbereich.setZielwert2((int)
						// Math.ceil(OptimaleLösung.OptimaleLösung1(spielbereich11.getGraph())*1.5));
						Auswahlbereich.setZielwert3(Graph.OptimaleLösung1(spielbereichSchwer2.graph));
						System.out.println(Graph.OptimaleLösung2(spielbereichSchwer2.graph));
						Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert3());
						spielbereichSchwer2 = null;

					} else {
						Auswahlbereich.timer2.stop();
						Auswahlbereich.zeit.setText("Zeit:" + " " + Auswahlbereich.getZeitminuten_string0() + ":"
								+ Auswahlbereich.getZeitsekunden_string0());
						Auswahlbereich.level.setText("Level:" + " " + Auswahlbereich.getLevel0());
						Auswahlbereich.zielwert.setText("Zielwert:" + " " + Auswahlbereich.getZielwert0());
						Auswahlbereich.score.setText("Score:" + " " + Auswahlbereich.getScore0());
						Auswahlbereich.switchpanel3 = 0;
						SpielbereichSchwer1.anzahlkanten3 = 0;
						SpielbereichSchwer1.gameover3();
						Spiel3.cl.show(Spiel3.panel, "0");
					}
				} else if(switchpanel3%2==1) {
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

			}

		

		if (e.getSource() == this.beenden) {
			String combo5 = (String) schwierigkeitsgrad.getSelectedItem();
			if (combo5 == "Training") {
				timer.stop();
				SpielbereichTraining1.gameover1();
				SpielbereichTraining1.setAnzahlkanten1(0);
				Auswahlbereich.switchpanel1 = 0;
				zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
				level.setText("Level:" + " " + level0);
				zielwert.setText("Zielwert:" + " " + zielwert0);
				score.setText("Score:" + " " + score0);
				Spiel3.cl.show(Spiel3.panel, "0");
			}
			if (combo5 == "mittel") {
				timer1.stop();
				SpielbereichMittel1.gameover2();
				SpielbereichMittel1.anzahlkanten2 = 0;
				SpielbereichMittel2.anzahlkanten2 = 0;
				Auswahlbereich.switchpanel2 = 0;
				zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
				level.setText("Level:" + " " + level0);
				zielwert.setText("Zielwert:" + " " + zielwert0);
				score.setText("Score:" + " " + score0);
				Spiel3.cl.show(Spiel3.panel, "0");
			}
			if (combo5 == "schwer") {
				timer2.stop();
				SpielbereichSchwer1.gameover3();
				SpielbereichSchwer1.anzahlkanten3 = 0;
				SpielbereichSchwer2.anzahlkanten3 = 0;
				Auswahlbereich.switchpanel3 = 0;
				zeit.setText("Zeit:" + " " + zeitminuten_string0 + ":" + zeitsekunden_string0);
				level.setText("Level:" + " " + level0);
				zielwert.setText("Zielwert:" + " " + zielwert0);
				score.setText("Score:" + " " + score0);
				Spiel3.cl.show(Spiel3.panel, "0");
			}

		}

		if (e.getSource() == this.zurücksetzen) {
			String path = "Highscore_Spiel3.txt";

			String neuHs = "-" + "\n" + "0" + "\n";
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
				for (int i = 0; i < 3; i++) {
					bufferedWriter.append(neuHs);
				}
				bufferedWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			highscore1.setText("-   0");
			highscore2.setText("-   0");
			highscore3.setText("-   0");

		}
	}

	public static void timerreset() {
		timer.stop();
		zeitsekunden = 0;
		zeitminuten = 0;
		zeitsekunden_string = String.format("%02d", zeitsekunden);
		zeitminuten_string = String.format("%02d", zeitminuten);
		zeit.setText("Zeit:" + " " + zeitminuten_string + ":" + zeitsekunden_string);
		timer.start();
	}

	public static void timer1reset() {
		timer1.stop();
		zeitsekunden1 = 60;
		zeitminuten1 = 0;
		zeitsekunden1_string = String.format("%02d", zeitsekunden1);
		zeitminuten1_string = String.format("%02d", zeitminuten1);
		zeit.setText("Zeit:" + " " + zeitminuten1_string + ":" + zeitsekunden1_string);
		// timer1.start();
	}

	public static void timer2reset() {
		timer2.stop();
		zeitsekunden2 = 30;
		zeitminuten2 = 0;
		zeitsekunden2_string = String.format("%02d", zeitsekunden2);
		zeitminuten2_string = String.format("%02d", zeitsekunden2);
		zeit.setText("Zeit:" + " " + zeitminuten2_string + ":" + zeitsekunden2_string);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_P) {
			System.out.println("Hallo");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// Getters und Setters
	public JPanel getPanel1() {
		return panel1;
	}

	public void setPanel1(JPanel panel1) {
		this.panel1 = panel1;
	}

	public JPanel getPanel2() {
		return panel2;
	}

	public void setPanel2(JPanel panel2) {
		this.panel2 = panel2;
	}

	public JPanel getPanel3() {
		return panel3;
	}

	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}

	public JPanel getPanel4() {
		return panel4;
	}

	public void setPanel4(JPanel panel4) {
		this.panel4 = panel4;
	}

	public JPanel getPanel5() {
		return panel5;
	}

	public void setPanel5(JPanel panel5) {
		this.panel5 = panel5;
	}

	public JPanel getPanel6() {
		return panel6;
	}

	public void setPanel6(JPanel panel6) {
		this.panel6 = panel6;
	}

	public JPanel getPanel7() {
		return panel7;
	}

	public void setPanel7(JPanel panel7) {
		this.panel7 = panel7;
	}

	public JPanel getPanel8() {
		return panel8;
	}

	public void setPanel8(JPanel panel8) {
		this.panel8 = panel8;
	}

	public JPanel getPanel9() {
		return panel9;
	}

	public void setPanel9(JPanel panel9) {
		this.panel9 = panel9;
	}

	public JPanel getPanel10() {
		return panel10;
	}

	public void setPanel10(JPanel panel10) {
		this.panel10 = panel10;
	}

	public JPanel getPanel11() {
		return panel11;
	}

	public void setPanel11(JPanel panel11) {
		this.panel11 = panel11;
	}

	public JPanel getPanel13() {
		return panel13;
	}

	public void setPanel13(JPanel panel13) {
		this.panel13 = panel13;
	}

	public JPanel getPanel14() {
		return panel14;
	}

	public void setPanel14(JPanel panel14) {
		this.panel14 = panel14;
	}

	public static JButton getZurueck() {
		return zurueck;
	}

	public static void setZurueck(JButton zurueck) {
		Auswahlbereich.zurueck = zurueck;
	}

	public static JLabel getAuswahlbereich() {
		return auswahlbereich;
	}

	public static void setAuswahlbereich(JLabel auswahlbereich) {
		Auswahlbereich.auswahlbereich = auswahlbereich;
	}

	public static JButton getStart() {
		return start;
	}

	public static void setStart(JButton start) {
		Auswahlbereich.start = start;
	}

	public static JButton getBeenden() {
		return beenden;
	}

	public static void setBeenden(JButton beenden) {
		Auswahlbereich.beenden = beenden;
	}

	public static JButton getPausieren() {
		return pausieren;
	}

	public static void setPausieren(JButton pausieren) {
		Auswahlbereich.pausieren = pausieren;
	}

	public static JButton getAnleitung() {
		return anleitung;
	}

	public static void setAnleitung(JButton anleitung) {
		Auswahlbereich.anleitung = anleitung;
	}

	public static JButton getZurücksetzen() {
		return zurücksetzen;
	}

	public static void setZurücksetzen(JButton zurücksetzen) {
		Auswahlbereich.zurücksetzen = zurücksetzen;
	}

	public static JButton getAbgabe() {
		return abgabe;
	}

	public static void setAbgabe(JButton abgabe) {
		Auswahlbereich.abgabe = abgabe;
	}

	public static JComboBox<String> getSchwierigkeitsgrad() {
		return schwierigkeitsgrad;
	}

	public static void setSchwierigkeitsgrad(JComboBox<String> schwierigkeitsgrad) {
		Auswahlbereich.schwierigkeitsgrad = schwierigkeitsgrad;
	}

	public static JLabel getScore() {
		return score;
	}

	public static void setScore(JLabel score) {
		Auswahlbereich.score = score;
	}

	public static JLabel getHighscore() {
		return highscore;
	}

	public static void setHighscore(JLabel highscore) {
		Auswahlbereich.highscore = highscore;
	}

	public static JLabel getLevel() {
		return level;
	}

	public static void setLevel(JLabel level) {
		Auswahlbereich.level = level;
	}

	public static JLabel getZeit() {
		return zeit;
	}

	public static void setZeit(JLabel zeit) {
		Auswahlbereich.zeit = zeit;
	}

	public static JLabel getZielwert() {
		return zielwert;
	}

	public static void setZielwert(JLabel zielwert) {
		Auswahlbereich.zielwert = zielwert;
	}

	public String[] getA() {
		return a;
	}

	public void setA(String[] a) {
		this.a = a;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public static int getScore1() {
		return score1;
	}

	public static void setScore1(int score1) {
		Auswahlbereich.score1 = score1;
	}

	public static int getLevel1() {
		return level1;
	}

	public static void setLevel1(int level1) {
		Auswahlbereich.level1 = level1;
	}

	public static int getScore2() {
		return score2;
	}

	public static void setScore2(int score2) {
		Auswahlbereich.score2 = score2;
	}

	public static int getLevel2() {
		return level2;
	}

	public static void setLevel2(int level2) {
		Auswahlbereich.level2 = level2;
	}

	public static int getScore3() {
		return score3;
	}

	public static void setScore3(int score3) {
		Auswahlbereich.score3 = score3;
	}

	public static int getLevel3() {
		return level3;
	}

	public static void setLevel3(int level3) {
		Auswahlbereich.level3 = level3;
	}

	public static int getZeitminuten0() {
		return zeitminuten0;
	}

	public static void setZeitminuten0(int zeitminuten0) {
		Auswahlbereich.zeitminuten0 = zeitminuten0;
	}

	public static int getZeitsekunden0() {
		return zeitsekunden0;
	}

	public static void setZeitsekunden0(int zeitsekunden0) {
		Auswahlbereich.zeitsekunden0 = zeitsekunden0;
	}

	public static String getZeitsekunden_string0() {
		return zeitsekunden_string0;
	}

	public static void setZeitsekunden_string0(String zeitsekunden_string0) {
		Auswahlbereich.zeitsekunden_string0 = zeitsekunden_string0;
	}

	public static String getZeitminuten_string0() {
		return zeitminuten_string0;
	}

	public static void setZeitminuten_string0(String zeitminuten_string0) {
		Auswahlbereich.zeitminuten_string0 = zeitminuten_string0;
	}

	public static int getAbgelaufenezeit() {
		return abgelaufenezeit;
	}

	public static void setAbgelaufenezeit(int abgelaufenezeit) {
		Auswahlbereich.abgelaufenezeit = abgelaufenezeit;
	}

	public static int getZeitminuten() {
		return zeitminuten;
	}

	public static void setZeitminuten(int zeitminuten) {
		Auswahlbereich.zeitminuten = zeitminuten;
	}

	public static int getZeitsekunden() {
		return zeitsekunden;
	}

	public static void setZeitsekunden(int zeitsekunden) {
		Auswahlbereich.zeitsekunden = zeitsekunden;
	}

	public static String getZeitsekunden_string() {
		return zeitsekunden_string;
	}

	public static void setZeitsekunden_string(String zeitsekunden_string) {
		Auswahlbereich.zeitsekunden_string = zeitsekunden_string;
	}

	public static String getZeitminuten_string() {
		return zeitminuten_string;
	}

	public static void setZeitminuten_string(String zeitminuten_string) {
		Auswahlbereich.zeitminuten_string = zeitminuten_string;
	}

	public static Timer getTimer() {
		return timer;
	}

	public static void setTimer(Timer timer) {
		Auswahlbereich.timer = timer;
	}

	public static int getZeitminuten1() {
		return zeitminuten1;
	}

	public static void setZeitminuten1(int zeitminuten1) {
		Auswahlbereich.zeitminuten1 = zeitminuten1;
	}

	public static int getZeitsekunden1() {
		return zeitsekunden1;
	}

	public static void setZeitsekunden1(int zeitsekunden1) {
		Auswahlbereich.zeitsekunden1 = zeitsekunden1;
	}

	public static String getZeitminuten1_string() {
		return zeitminuten1_string;
	}

	public static void setZeitminuten1_string(String zeitminuten1_string) {
		Auswahlbereich.zeitminuten1_string = zeitminuten1_string;
	}

	public static String getZeitsekunden1_string() {
		return zeitsekunden1_string;
	}

	public static void setZeitsekunden1_string(String zeitsekunden1_string) {
		Auswahlbereich.zeitsekunden1_string = zeitsekunden1_string;
	}

	public static Timer getTimer1() {
		return timer1;
	}

	public static void setTimer1(Timer timer1) {
		Auswahlbereich.timer1 = timer1;
	}

	public static int getZeitminuten2() {
		return zeitminuten2;
	}

	public static void setZeitminuten2(int zeitminuten2) {
		Auswahlbereich.zeitminuten2 = zeitminuten2;
	}

	public static int getZeitsekunden2() {
		return zeitsekunden2;
	}

	public static void setZeitsekunden2(int zeitsekunden2) {
		Auswahlbereich.zeitsekunden2 = zeitsekunden2;
	}

	public static String getZeitminuten2_string() {
		return zeitminuten2_string;
	}

	public static void setZeitminuten2_string(String zeitminuten2_string) {
		Auswahlbereich.zeitminuten2_string = zeitminuten2_string;
	}

	public static String getZeitsekunden2_string() {
		return zeitsekunden2_string;
	}

	public static void setZeitsekunden2_string(String zeitsekunden2_string) {
		Auswahlbereich.zeitsekunden2_string = zeitsekunden2_string;
	}

	public static Timer getTimer2() {
		return timer2;
	}

	public static void setTimer2(Timer timer2) {
		Auswahlbereich.timer2 = timer2;
	}

	public static int getZielwert1() {
		return zielwert1;
	}

	public static void setZielwert1(int zielwert1) {
		Auswahlbereich.zielwert1 = zielwert1;
	}

	public static int getZielwert2() {
		return zielwert2;
	}

	public static void setZielwert2(int zielwert2) {
		Auswahlbereich.zielwert2 = zielwert2;
	}

	public static int getZielwert3() {
		return zielwert3;
	}

	public static void setZielwert3(int zielwert3) {
		Auswahlbereich.zielwert3 = zielwert3;
	}

	public static int getStateswitchTraining() {
		return stateswitchTraining;
	}

	public static void setStateswitchTraining(int stateswitchTraining) {
		Auswahlbereich.stateswitchTraining = stateswitchTraining;
	}

	public static int getStateswitchMittel() {
		return stateswitchMittel;
	}

	public static void setStateswitchMittel(int stateswitchMittel) {
		Auswahlbereich.stateswitchMittel = stateswitchMittel;
	}

	public static int getStateswitchSchwer() {
		return stateswitchSchwer;
	}

	public static void setStateswitchSchwer(int stateswitchSchwer) {
		Auswahlbereich.stateswitchSchwer = stateswitchSchwer;
	}

}
