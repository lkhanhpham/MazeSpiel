package Spiel1;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;

import team07.main.LeftPanel;
import team07.main.MyFrame;
import team07.main.RightPanel;

public class ActionPanel extends JPanel implements ActionListener {

	private JPanel panel, panelString, panel1, panel2;
	private GamePanel gPanel;
	private String art = "Training";
	private boolean isPause = false, started = false;
	private JTextArea sg;
	private int currentValue, LevelCounter = 1;
	private String aktuellesLevel;
	private String training, mittel, schwer;
	
	private GridBagConstraints c;

	private ScheduledExecutorService exec3;
	private ScheduledExecutorService exec4;
	private ScheduledExecutorService exec5;
	
	private JButton start, beenden, beenden2, pause, pause2, abgabe, abgabe2, zurueck, rhs;

	private Timer timer, timer2, timer3;
	private int seconds, seconds2, seconds3, minutes;
	private JLabel counterLabel;
	private DecimalFormat dFormat;
	private String ddSecond, ddMinute;
	private boolean minute;
	
	private HighscoreHandler handler;
	private int highscorePoints = 0;
	private String newName;
	private JTable j;
	
	public ActionPanel(GamePanel gPanel, LeftPanel lPanel) {
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		
		handler = new HighscoreHandler();
		handler.getData();
		
		counterLabel = new JLabel("");
		counterLabel.setBounds(10, 10, 10, 10);
		dFormat = new DecimalFormat("00");

		this.gPanel = gPanel;

		c = new GridBagConstraints();

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 0.05;
		add(panel, c);

		panelString = new JPanel();
		panelString.setLayout(new GridBagLayout());
		panelString.setBackground(Color.LIGHT_GRAY);
		c.weightx = 0.2;
		c.gridy = 1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		add(panelString, c);

		panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		panel1.setBackground(Color.LIGHT_GRAY);
		c.weightx = 0.2;
		c.gridy = 3;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		add(panel1, c);

		panel2 = new JPanel();
		panel2.setBackground(Color.LIGHT_GRAY);
		panel2.setLayout(new GridBagLayout());
		c.weightx = 0.2;
		c.gridy = 4;
		c.weighty = 0.4;
		c.fill = GridBagConstraints.BOTH;
		add(panel2, c);

		// Button um schwierigkeitsgrad auszuwählen.
		String[] difficulty = { "Training", "Mittel", "Schwer" };
		JComboBox<String> Schwierigkeitsgrad = new JComboBox<>(difficulty);
		Schwierigkeitsgrad.setFocusable(false);
		JButton jButton = new JButton("KEINE FUNKTION");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.PAGE_START;
		panel1.add(Schwierigkeitsgrad, c);

		Schwierigkeitsgrad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				art = (String) Schwierigkeitsgrad.getSelectedItem();

				if (art == "Mittel") {
					gPanel.setTraining(false);
					gPanel.setMittel(true);
					gPanel.setSchwer(false);
				} else if (art == "Schwer") {
					gPanel.setTraining(false);
					gPanel.setMittel(false);
					gPanel.setSchwer(true);
				}
			}
		});

		// Start Knopf
		start = new JButton("Start");

		// Position Start Button
		c.ipady = 0;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(start, c);
		start.setFocusable(true);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timer != null) {
				beenden2.doClick();
				}
				minute = false;
				panelString.removeAll();
				seconds3 = 0;
				highscorePoints = 0;
				gPanel.drawGame();
				started = true;
				pause.setEnabled(true);
				abgabe.setEnabled(true);
				zurueck.setEnabled(false);
				rhs.setEnabled(false);
				Schwierigkeitsgrad.setEnabled(false);
				LevelCounter = 1;
				
				if (art == "Training") {
					gPanel.getKs().setTraining(true);
					gPanel.getKs().setMittel(false);
					gPanel.getKs().setSchwer(false);
				} else if (art == "Mittel") {
					gPanel.getKs().setTraining(false);
					gPanel.getKs().setMittel(true);
					gPanel.getKs().setSchwer(false);
				} else if (art == "Schwer") {
					gPanel.getKs().setTraining(false);
					gPanel.getKs().setMittel(false);
					gPanel.getKs().setSchwer(true);
				}
				
				
				repaint();
				

				if (art == "Training") {
					resetTimer();
					gPanel.setTraining(true);
					gPanel.setMittel(false);
					gPanel.setSchwer(false);

					gPanel.getKs().getKnapSack().perfectValue_training();
					training = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.5));

					sg = new JTextArea(" Gewichtsschranke: " + training + "\n Level: 1\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					repaint();
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);
					
					exec5 = Executors.newSingleThreadScheduledExecutor();
					exec5.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							
							if (gPanel.getKs().getVerloren() == true) {
								beenden2.doClick();
								pause.setEnabled(false);
								abgabe.setEnabled(false);
								zurueck.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
								timer2.stop();
								resetTimer();
								exec3.shutdown();
								exec4.shutdown();
								exec5.shutdown();
								gPanel.getKs().get2D().drawString("You Lost",
										gPanel.getKs().getOffSetX() - gPanel.getKs().getCellSize() * 3,
										gPanel.getKs().getOffSetY() - gPanel.getKs().getCellSize() - (gPanel.getKs().getCellSize() / 2));
								gPanel.getKs().repaint();
//								if (isNewHighscore() && started == true) {
//									delayTimer();
//									timer3.start();
//								}
								repaint();
							}

						}
					}, 0, 1, TimeUnit.SECONDS);

					normalTimer();
					timer.start();

					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);

					exec4 = Executors.newSingleThreadScheduledExecutor();
					exec4.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {

							if (gPanel.getKs().getAbgabe() == true) {
								abgabe2.doClick();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					exec3 = Executors.newSingleThreadScheduledExecutor();
					exec3.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {

							if (gPanel.getKs().getPause() == true) {
								isPause = true;
								start.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							} else if (gPanel.getKs().getPause() == false) {
								isPause = false;
								start.setEnabled(false);
								Schwierigkeitsgrad.hidePopup();
								Schwierigkeitsgrad.setEnabled(false);

								if (gPanel.getKs().getTraining() == true) {
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(0);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getMittel() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(1);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getSchwer() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(2);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								timer.start();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					start.setEnabled(false);
//                }
				}

				if (art == "Mittel") {
					resetCountdown_mittel();
					gPanel.setTraining(false);
					gPanel.setMittel(true);
					gPanel.setSchwer(false);

					gPanel.getKs().getKnapSack().perfectValue_mittel();
					mittel = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.7));

					sg = new JTextArea(" Gewichtsschranke: " + mittel + "\n Level: 1\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					repaint();
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);

					exec5 = Executors.newSingleThreadScheduledExecutor();
					exec5.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {

							if (gPanel.getKs().getVerloren() == true) {
								beenden2.doClick();
								pause.setEnabled(false);
								abgabe.setEnabled(false);
								zurueck.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
								timer2.stop();
								resetCountdown_mittel();
								exec3.shutdown();
								exec4.shutdown();
								exec5.shutdown();
								gPanel.getKs().get2D().drawString("You Lost",
										gPanel.getKs().getOffSetX() - gPanel.getKs().getCellSize() * 3,
										gPanel.getKs().getOffSetY() - gPanel.getKs().getCellSize() - (gPanel.getKs().getCellSize() / 2));
								gPanel.getKs().repaint();
//								if (isNewHighscore() && started == true) {
//									delayTimer();
//									timer3.start();
//								}
								repaint();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					normalCountdown_mittel();
					seconds = 30;
					timer.start();

					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);

					exec4 = Executors.newSingleThreadScheduledExecutor();
					exec4.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							if (gPanel.getKs().getAbgabe() == true) {
								abgabe2.doClick();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					exec3 = Executors.newSingleThreadScheduledExecutor();
					exec3.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {

							if (gPanel.getKs().getPause() == true) {
								isPause = true;
								start.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
//								timer2.stop();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							} else if (gPanel.getKs().getPause() == false) {
								isPause = false;
								start.setEnabled(false);
								Schwierigkeitsgrad.hidePopup();
								Schwierigkeitsgrad.setEnabled(false);

								if (gPanel.getKs().getTraining() == true) {
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(0);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getMittel() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(1);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getSchwer() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(2);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								timer.start();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					start.setEnabled(false);
					repaint();
				}

				if (art == "Schwer") {
					resetCountdown_schwer();
					gPanel.setTraining(false);
					gPanel.setMittel(false);
					gPanel.setSchwer(true);

					gPanel.getKs().getKnapSack().perfectValue_schwer();
					schwer = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.9));

					sg = new JTextArea(" Gewichtsschranke: " + schwer + "\n Level: 1\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					repaint();
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);

					exec5 = Executors.newSingleThreadScheduledExecutor();
					exec5.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							if (gPanel.getKs().getVerloren() == true) {
								beenden2.doClick();
								pause.setEnabled(false);
								abgabe.setEnabled(false);
								zurueck.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
								timer2.stop();
								resetCountdown_schwer();
								exec3.shutdown();
								exec4.shutdown();
								exec5.shutdown();
								gPanel.getKs().get2D().drawString("You Lost",
										gPanel.getKs().getOffSetX() - gPanel.getKs().getCellSize() * 3,
										gPanel.getKs().getOffSetY() - gPanel.getKs().getCellSize() - (gPanel.getKs().getCellSize() / 2));
								gPanel.getKs().repaint();
//								if (isNewHighscore() && started == true) {
//									delayTimer();
//									timer3.start();
//								}
								repaint();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					normalCountdown_schwer();
					seconds = 15;
					timer.start();

					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);

					exec4 = Executors.newSingleThreadScheduledExecutor();
					exec4.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {

							if (gPanel.getKs().getAbgabe() == true) {
								abgabe2.doClick();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					exec3 = Executors.newSingleThreadScheduledExecutor();
					exec3.scheduleAtFixedRate(new Runnable() {
						@Override
						public void run() {
							if (gPanel.getKs().getPause() == true) {
								isPause = true;
								start.setEnabled(true);
								Schwierigkeitsgrad.setEnabled(true);
								timer.stop();
//								timer2.stop();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							} else if (gPanel.getKs().getPause() == false) {
								isPause = false;
								start.setEnabled(false);
								Schwierigkeitsgrad.hidePopup();
								Schwierigkeitsgrad.setEnabled(false);

								if (gPanel.getKs().getTraining() == true) {
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(true);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(false);
										art = "Training";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(0);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getMittel() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									if (art == "Schwer") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(true);
										gPanel.getKs().setSchwer(false);
										art = "Mittel";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(1);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								if (gPanel.getKs().getSchwer() == true) {
									
									if (art == "Training") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									if (art == "Mittel") {
										gPanel.getKs().setTraining(false);
										gPanel.getKs().setMittel(false);
										gPanel.getKs().setSchwer(true);
										art = "Schwer";
									}
									
									Schwierigkeitsgrad.setSelectedIndex(2);
									gPanel.getKs().requestFocusInWindow();
									repaint();
								}

								timer.start();
//								timer2.start();
								pause.setText("Pause");
								gPanel.getKs().repaint();
							}
						}
					}, 0, 1, TimeUnit.SECONDS);

					start.setEnabled(false);
					repaint();
				}
			}

		});

		// Beenden Knopf
		beenden = new JButton("Beenden");
		c.gridx = 1;
		c.gridy = 0;
		panel.add(beenden, c);
		beenden.setFocusable(false);

		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isNewHighscore() && started == true) {
					delayTimer();
					timer3.start();
				}
    			pause.setEnabled(false);
    			beenden.setEnabled(true);
    			abgabe.setEnabled(false);
    			rhs.setEnabled(true);
    			zurueck.setEnabled(true);
    			Schwierigkeitsgrad.setEnabled(true);
    			if (timer != null) {
				timer.stop();
				resetTimer();
    			}
    			if (timer2 != null) {
    				timer2.stop();
    			}
				minutes = 0;
				panel.remove(counterLabel);
				if (exec3 != null || exec4 != null || exec5 != null) {
					exec3.shutdown();
					exec4.shutdown();
					exec5.shutdown();
				}
				gPanel.getKs().setVerloren(true);
				gPanel.getKs().repaint();
				repaint();
			}
		});
		
		beenden2 = new JButton("Beenden");

		beenden2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isNewHighscore() && started == true) {
					delayTimer();
					timer3.start();
				}
    			pause.setEnabled(false);
    			beenden.setEnabled(true);
    			abgabe.setEnabled(false);
    			rhs.setEnabled(true);
    			Schwierigkeitsgrad.setEnabled(true);
    			if (timer != null) {
				timer.stop();
				resetTimer();
    			}
    			if(timer2 != null) {
    				timer2.stop();
    			}
				minutes = 0;
				panel.remove(counterLabel);
				if (exec3 != null || exec4 != null || exec5 != null) {
					exec3.shutdown();
					exec4.shutdown();
					exec5.shutdown();
				}
				gPanel.getKs().setVerloren(true);
				gPanel.getKs().repaint();
				repaint();
			}
		});

		// Abgabe Knopf
		abgabe = new JButton("Loesung abgeben");
		abgabe.setEnabled(false);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(abgabe, c);
		abgabe.setFocusable(false);

		abgabe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				currentValue = gPanel.getKs().getCurrentValue();
				pause.setEnabled(false);
				nextLevelTimer();
				timer2.start();
				

				if (art == "Training") {
					highscorePoints += gPanel.getKs().getCurrentValue();
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.5) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;
						
				}
			}

				if (art == "Mittel") {
					timer.stop();
					highscorePoints += gPanel.getKs().getCurrentValue() * 2;
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.7) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;
					}
				}

				if (art == "Schwer") {
					timer.stop();
					highscorePoints += gPanel.getKs().getCurrentValue() * 3;
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.9) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;
					}
				}
			}
		});
		
		abgabe2 = new JButton("Loesung abgeben");

		abgabe2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				currentValue = gPanel.getKs().getCurrentValue();
				pause.setEnabled(false);
				nextLevelTimer();
				timer2.start();

				if (art == "Training") {
					highscorePoints += gPanel.getKs().getCurrentValue();
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.5) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;

				}
			}

				if (art == "Mittel") {
					highscorePoints += gPanel.getKs().getCurrentValue() * 2;
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.7) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;
					}
				}

				if (art == "Schwer") {
					highscorePoints += gPanel.getKs().getCurrentValue() * 3;
					
					if (currentValue < (int) gPanel.getKs().getMaximumValue() * 0.9) {
						gPanel.getKs().setVerloren(true);
					} else {
						gPanel.getKs().setDisplayValue(true);
						gPanel.getKs().setAbgabe(false);
						seconds2 = 0;
					}
				}
			}
		});

		// Pause Knopf
		pause = new JButton("Pause");
		pause.setEnabled(false);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(pause, c);
		pause.setFocusable(false);
		repaint();

		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPause == false) {
					isPause = true;
					start.setEnabled(true);
					rhs.setEnabled(true);
					gPanel.getKs().setPause(true);
					Schwierigkeitsgrad.setEnabled(true);
					timer.stop();
					pause.setText("Pause");
					gPanel.getKs().repaint();
				} else if (isPause == true) {
					isPause = false;
					start.setEnabled(false);
					rhs.setEnabled(false);
					gPanel.getKs().setPause(false);
					gPanel.getKs().addMouseListener(gPanel.getKs());
					gPanel.getKs().addMouseMotionListener(gPanel.getKs());
					Schwierigkeitsgrad.hidePopup();
					Schwierigkeitsgrad.setEnabled(false);

					if (gPanel.getKs().getTraining() == true) {
						
						if (art == "Mittel") {
							gPanel.getKs().setTraining(true);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(false);
							art = "Training";
						}
						
						if (art == "Schwer") {
							gPanel.getKs().setTraining(true);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(false);
							art = "Training";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(0);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					if (gPanel.getKs().getMittel() == true) {
						
						if (art == "Training") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(true);
							gPanel.getKs().setSchwer(false);
							art = "Mittel";
						}
						
						if (art == "Schwer") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(true);
							gPanel.getKs().setSchwer(false);
							art = "Mittel";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(1);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					if (gPanel.getKs().getSchwer() == true) {
						
						if (art == "Training") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(true);
							art = "Schwer";
						}
						
						if (art == "Mittel") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(true);
							art = "Schwer";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(2);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					timer.start();
					pause.setText("Pause");
					gPanel.getKs().repaint();
				}
			}
		});
		
		pause2 = new JButton("Pause");

		pause2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPause == false) {
					isPause = true;
					start.setEnabled(true);
					rhs.setEnabled(true);
					gPanel.getKs().setPause(true);
					Schwierigkeitsgrad.setEnabled(true);
					timer.stop();
					pause.setText("Pause");
					gPanel.getKs().repaint();
				} else if (isPause == true) {
					start.setEnabled(false);
					rhs.setEnabled(false);
					gPanel.getKs().setPause(false);
					gPanel.getKs().addMouseListener(gPanel.getKs());
					gPanel.getKs().addMouseMotionListener(gPanel.getKs());
					Schwierigkeitsgrad.hidePopup();
					Schwierigkeitsgrad.setEnabled(false);

					if (gPanel.getKs().getTraining() == true) {
						
						if (art == "Mittel") {
							gPanel.getKs().setTraining(true);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(false);
							art = "Training";
						}
						
						if (art == "Schwer") {
							gPanel.getKs().setTraining(true);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(false);
							art = "Training";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(0);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					if (gPanel.getKs().getMittel() == true) {
						
						if (art == "Training") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(true);
							gPanel.getKs().setSchwer(false);
							art = "Mittel";
						}
						
						if (art == "Schwer") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(true);
							gPanel.getKs().setSchwer(false);
							art = "Mittel";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(1);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					if (gPanel.getKs().getSchwer() == true) {
						
						if (art == "Training") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(true);
							art = "Schwer";
						}
						
						if (art == "Mittel") {
							gPanel.getKs().setTraining(false);
							gPanel.getKs().setMittel(false);
							gPanel.getKs().setSchwer(true);
							art = "Schwer";
						}
						
						Schwierigkeitsgrad.setSelectedIndex(2);
						gPanel.getKs().requestFocusInWindow();
						repaint();
					}

					timer.start();
					pause.setText("Pause");
					gPanel.getKs().repaint();
				}
			}
		});

		// Highscore Feld
		JTextArea hs = new JTextArea("Highscore\n" + "Platz:                Name:             Punkte:", 1, 1);
		hs.setForeground(Color.BLACK);
		hs.setBackground(Color.WHITE);
		hs.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		panel1.add(hs, c);
		
		j = new JTable(handler.getDataSet(), handler.getColumnNames()) {
	            public boolean isCellEditable(int row, int column) {
	                    return false;
	            };
	        };
	        
	        j.setFocusable(false);

	   		c.gridx = 0;
			c.gridy = 2;
	        panel1.add(j, c);

		// Reset Highscore Knopf
		rhs = new JButton("Reset Highscore");
		c.gridx = 0;
		c.gridy = 0;
		panel2.add(rhs, c);
		rhs.setFocusable(false);

		rhs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighscore();
				handler.writeFile("1," + handler.getDataSet()[0][1] + "," + handler.getDataSet()[0][2] + ";"
						  + "2," + handler.getDataSet()[1][1] + "," + handler.getDataSet()[1][2] + ";"
						  + "3," + handler.getDataSet()[2][1] + "," + handler.getDataSet()[2][2]);
				setHighscore();
				repaint();
			}
		});

		// Spielanleitung
		JButton anleitung = new JButton("Spielanleitung");
		c.gridx = 0;
		c.gridy = 1;
		panel2.add(anleitung, c);
		anleitung.setFocusable(false);

		anleitung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isPause == false) {
					pause.doClick();
				}
				Spielanleitung();
			}
		});
		// Zurueck Knopf
		zurueck = new JButton("Zurueck");
		c.gridx = 0;
		c.gridy = 2;
		panel2.add(zurueck, c);
		zurueck.setFocusable(false);

		zurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyFrame.getCl().show(MyFrame.getPanelCont(), "1");
				LeftPanel.getDp().requestFocusInWindow();
				lPanel.removeGame();
				lPanel.drawGame();
				RightPanel.getNeustart().doClick();
				RightPanel.setMaze();
				gPanel.removeGame();
				pause.setEnabled(false);
				abgabe.setEnabled(false);
				rhs.setEnabled(true);
				Schwierigkeitsgrad.setEnabled(true);
				if (timer != null) {
					timer.stop();
				}
				resetTimer();
				panel.remove(counterLabel);
				if (exec3 != null || exec4 != null || exec5 != null) {
					exec3.shutdown();
					exec4.shutdown();
					exec5.shutdown();
				}
				panelString.removeAll();
				start.setEnabled(true);
				lPanel.removeGame();
				lPanel.drawGame();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public void Spielanleitung() {
		JFrame frame = new JFrame("Spielanleitung");
		JOptionPane.showMessageDialog(frame, "Spielanleitung fuer Spiel 1 - Knapsack Problem \n" 
		+ "Das Ziel des Spieles ist es, soviele Gegenstaende mit moeglichst viel Wert ''einzupacken''. \n"
		+ "Die Gegenstaende haben also sowohl ein Gewicht als auch einen Wert.\n"
		+ "Dabei darf die zulaessige Gewichtsgrenze fuer den Koffer nicht Ueberschritten werden, gleichzeitig muss aber der Wert der Gegenstaende einen vom Programm zuvor berechneten Zielwert mindestens erreichen. \n"
		+ "Der Spieler kann einen Gegenstand seiner Wahl auswaehlen und ihn auf den Koffer mittels ''Drag and Drop'' verschieben. \n"
		+ "Beachte, dass ein Gegenstand der Bereits eingepackt wurde nicht wieder ausgepackt werden kann. \n"
		+ "Sollte der Spieler mit seiner Auswahl fertig sein, und seine Loesung zur Ueberpruefung abgeben wollen, muss dieser auf der dafuer vorgesehenen Abgabe Kopf druecken. \n"
		+ "Alternativ kann der Spieler mittels der Pfeiltasten und W,A,S,D den Koffer durch eine Oeffnung am unteren Rand des Spielfeldes ins ''nichts verschieben'. \n"
		+ "Ist der Koffer verschwunden, so ist das Level abgeschlossen. \n"
		+ "Wurden die Bedingungen zum erfolgreichen Abschliessen eines Levels erfuellt sein, so erhaelt der Spieler entsprechend Punkte und gelangt in das naechste Level. \n"
		+ "Ein Level gilt als verloren, wenn der Spieler eine Loesung abgibt, welche die zulaessige Gewichtsschranke\n"
		+ "nicht einhaelt, oder bei der die Summe der Gegenstandswerte unter dem Zielwert liegt, oder\n"
		+ "wenn der Spieler auf den Beenden-Button drueckt. \n"
		+ "\n"
		+ "Schwierigkeitsgrade: \n"
		+ "Training: Erreiche mindestens 50% des optimalen Wertes. \n"
		+ "Mittel: Erreiche innerhalb des Zeitlimits von 30 Sekunden mindestens 70% des optimalen Wertes. \n"
		+ "Schwer: Erreiche innerhalb des Zeitlimits von 15 Sekunden mindestens 90% des optimalen Wertes. \n"
		+ "", "Spielanleitung", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}
	
	public void newHighscore() {
		JFrame frame = new JFrame("Neuer Highscore");
		UIManager.put("OptionPane.okButtonText", "Speichern");
		newName = JOptionPane.showInputDialog(frame, "Du hast einen neuen Highscore aufgestellt :)\nWenn du diesen speichern willst, dann gib bitte deinen Namen ein und drueck auf ''Speichern''.\nFalls nicht dann drueck auf ''Abbrechen''.", "Highscore setzen",
				JOptionPane.PLAIN_MESSAGE);

		if (newName == null) {
			
		} else if (newName.isEmpty() == true) {
			newName = "Kein Name";
			writeHighscore();
			handler.writeFile("1," + handler.getDataSet()[0][1] + "," + handler.getDataSet()[0][2] + ";"
									+ "2," + handler.getDataSet()[1][1] + "," + handler.getDataSet()[1][2] + ";"
									+ "3," + handler.getDataSet()[2][1] + "," + handler.getDataSet()[2][2]);
			setHighscore();
			highscorePoints = 0;
			frame.setVisible(false);
			frame.setResizable(false);
		} else {
			writeHighscore();
			handler.writeFile("1," + handler.getDataSet()[0][1] + "," + handler.getDataSet()[0][2] + ";"
									+ "2," + handler.getDataSet()[1][1] + "," + handler.getDataSet()[1][2] + ";"
									+ "3," + handler.getDataSet()[2][1] + "," + handler.getDataSet()[2][2]);
			setHighscore();
			highscorePoints = 0;
			frame.setVisible(false);
			frame.setResizable(false);
		}
	}

	public void resetHighscore() {
		handler.getDataSet()[2][0] = "3";
		handler.getDataSet()[2][1] = "-";
		handler.getDataSet()[2][2] = Integer.toString(0);
		
		handler.getDataSet()[1][0] = "2";
		handler.getDataSet()[1][1] = "-";
		handler.getDataSet()[1][2] = Integer.toString(0);
		
		handler.getDataSet()[0][0] = "1";
		handler.getDataSet()[0][1] = "-";
		handler.getDataSet()[0][2] = Integer.toString(0);
	}

	public boolean isNewHighscore() {
		if (highscorePoints >= Integer.parseInt(handler.getDataSet()[2][2])
			|| (highscorePoints >= Integer.parseInt(handler.getDataSet()[1][2])
			|| (highscorePoints >= Integer.parseInt(handler.getDataSet()[0][2])))) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void writeHighscore() {
		if (highscorePoints >= Integer.parseInt(handler.getDataSet()[0][2])) {
			handler.getDataSet()[2][0] = "3";
			handler.getDataSet()[2][1] = handler.getDataSet()[1][1];
			handler.getDataSet()[2][2] = handler.getDataSet()[1][2];
			
			handler.getDataSet()[1][0] = "2";
			handler.getDataSet()[1][1] = handler.getDataSet()[0][1];
			handler.getDataSet()[1][2] = handler.getDataSet()[0][2];
			
			handler.getDataSet()[0][0] = "1";
			handler.getDataSet()[0][1] = newName;
			handler.getDataSet()[0][2] = Integer.toString(highscorePoints);
		}
		
		else if (highscorePoints >= Integer.parseInt(handler.getDataSet()[1][2])) {
			handler.getDataSet()[2][0] = "3";
			handler.getDataSet()[2][1] = handler.getDataSet()[1][1];
			handler.getDataSet()[2][2] = handler.getDataSet()[1][2];
			
			handler.getDataSet()[1][0] = "2";
			handler.getDataSet()[1][1] = newName;
			handler.getDataSet()[1][2] = Integer.toString(highscorePoints);
		}
		
		else if (highscorePoints >= Integer.parseInt(handler.getDataSet()[2][2])) {
			handler.getDataSet()[2][0] = "3";
			handler.getDataSet()[2][1] = newName;
			handler.getDataSet()[2][2] = Integer.toString(highscorePoints);
		}
	}

	public void setHighscore() {
			j.getModel().getValueAt(0,0);
			j.getModel().getValueAt(0,1);
			j.getModel().getValueAt(0,2);
			j.getModel().getValueAt(1,0);
			j.getModel().getValueAt(1,1);
			j.getModel().getValueAt(1,2);
			j.getModel().getValueAt(2,0);
			j.getModel().getValueAt(2,1);
			j.getModel().getValueAt(2,2);
	}
	
//	public String[][] getHighscores(){
//		for (int row = 0; handler.getSc().hasNextLine() && row < 3; row++) {
//			handler.getStrings()[row] = handler.getSc().nextLine();
//		    for (int i = 0; i < 3 && i < handler.getStrings().length; i++) {
//		    	handler.getHighscores()[row][i] = handler.getStrings()[i];
//		    }
//		}
//		return handler.getHighscores();
//	}

	public void normalTimer() {

		timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				seconds++;
				ddSecond = dFormat.format(seconds);

				if (minute != true) {
					counterLabel.setText(" Timer: 00:" + ddSecond);
				} else {
					counterLabel.setText("Timer: " + ddMinute + ":" + ddSecond);
				}

				if (seconds == 60) {
					minute = true;
					seconds = 0;
					minutes++;
					ddMinute = dFormat.format(minutes);
					counterLabel.setText("Timer: " + ddMinute + ":" + ddSecond);
				}
			}
		});
	}
	
	public void nextLevelTimer() {

		timer2 = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				seconds2++;
				
				if (art == "Training") {
					timer.stop();
					resetTimer();
					minutes = 0;
					minute = false;
					ddMinute = dFormat.format(00);
					}
				
				if (art == "Mittel") {
					timer.stop();
					}
					
					if (art == "Schwer") {
						timer.stop();
					}
				
				if (seconds2 == 4) {
					gPanel.removeGame();
					gPanel.drawGame();
					
					if (art == "Training") {
					gPanel.getKs().getKnapSack().perfectValue_training();
					panelString.removeAll();
					LevelCounter++;
					aktuellesLevel = Integer.toString(LevelCounter);
					mittel = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.5));
//					sg.removeAll();
					sg = new JTextArea(" Gewichtsschranke: " + mittel + "\n Level: " + aktuellesLevel + "\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					
					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);
					repaint();
					}
					
					if (art == "Mittel") {
					resetCountdown_mittel();
					gPanel.getKs().getKnapSack().perfectValue_mittel();
					panelString.removeAll();
					LevelCounter++;
					aktuellesLevel = Integer.toString(LevelCounter);
					mittel = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.7));
//					sg.removeAll();
					sg = new JTextArea(" Gewichtsschranke: " + mittel + "\n Level: " + aktuellesLevel + "\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					
					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);
					repaint();
					}
					
					if (art == "Schwer") {
					resetCountdown_schwer();
					gPanel.getKs().getKnapSack().perfectValue_schwer();
					panelString.removeAll();
					LevelCounter++;
					aktuellesLevel = Integer.toString(LevelCounter);
					mittel = Integer.toString((int)(gPanel.getKs().getKnapSack().getGesamtGewicht() * 0.9));
//					sg.removeAll();
					sg = new JTextArea(" Gewichtsschranke: " + mittel + "\n Level: " + aktuellesLevel + "\n Punkte: " + highscorePoints, 1, 1);
					sg.setForeground(Color.BLACK);
					sg.setBackground(Color.LIGHT_GRAY);
					sg.setEditable(false);
					
					c.gridx = 0;
					c.gridy = 0;
					panelString.add(counterLabel, c);
					c.gridx = 0;
					c.gridy = 6;
					panelString.add(sg, c);
					repaint();
					}
					
					gPanel.getKs().setDoClick(false);
					gPanel.getKs().setDisplayValue(false);
					pause.setEnabled(true);
					timer2.stop();
					repaint();
				}
			}
		});
	}
	
	public void delayTimer() {

		timer3 = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				seconds3++;
				if (art == "Mittel") {
					timer.stop();
					}
					
					if (art == "Schwer") {
						timer.stop();
					}
				
				if (seconds3 == 1) {
						newHighscore();
//						handler.writeFile("1," + handler.getDataSet()[0][1] + "," + handler.getDataSet()[0][2] + ";"
//								  + "2," + handler.getDataSet()[1][1] + "," + handler.getDataSet()[1][2] + ";"
//								  + "3," + handler.getDataSet()[2][1] + "," + handler.getDataSet()[2][2]);
//						setHighscore();
						repaint();
						started = false;
						start.setEnabled(true);
						timer3.stop();
				}
			}
		});
	}

	public void normalCountdown_mittel() {

		timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				seconds--;
				ddSecond = dFormat.format(seconds);

				counterLabel.setText(" Timer: 00:" + ddSecond);

				if (seconds == 0) {
					timer.stop();
					gPanel.getKs().setVerloren(true);
				}
			}
		});
	}

	public void normalCountdown_schwer() {

		timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				seconds--;
				ddSecond = dFormat.format(seconds);

				counterLabel.setText(" Timer: 00:" + ddSecond);

				if (seconds == 0) {
					timer.stop();
					gPanel.getKs().setVerloren(true);
				}
			}
		});
	}

	public void resetTimer() {
		seconds = 0;
		minutes = 0;
	}

	public void resetCountdown_mittel() {
		seconds = 30;
	}

	public void resetCountdown_schwer() {
		seconds = 15;
	}

	public void setPause(boolean pause) {
		isPause = pause;
	}

	public boolean getPause() {
		return isPause;
	}

	public void focus() {
		requestFocusInWindow();
	}
}
