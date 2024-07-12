package team07.main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Spiel1.Spiel1;
import Spiel2.Spiel2;
import Spiel3.Spiel3;
import Spiel4.Spiel4;

public class MyFrame extends JFrame {

	private static LeftPanel leftPanel;
	private static RightPanel rightPanel;

	private static int width, height;
	private static JPanel panelCont = new JPanel();
	public static MyPanel card1;
	private Spiel1 spiel1;
	private Spiel2 spiel2;
	private Spiel3 spiel3;
	private Spiel4 spiel4;
	private static CardLayout cl = new CardLayout();

	/**
	 * @return the panelCont
	 */
	public static JPanel getPanelCont() {
		return panelCont;
	}

	/**
	 * @param panelCont the panelCont to set
	 */
	public static void setPanelCont(JPanel panelCont) {
		MyFrame.panelCont = panelCont;
	}

	/**
	 * @return the cl
	 */
	public static CardLayout getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public static void setCl(CardLayout cl) {
		MyFrame.cl = cl;
	}

	public MyFrame() {
		setSize(800, 800);
		setMinimumSize(new Dimension(600, 600));
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		leftPanel = new LeftPanel();
		spiel1 = new Spiel1(leftPanel);
		spiel2 = new Spiel2(leftPanel);
		spiel3 = new Spiel3(leftPanel);
		spiel4 = new Spiel4(leftPanel);

		card1 = new MyPanel(leftPanel);
		requestFocusInWindow(true);

		// Use CardLayout to show different cards in the window
		panelCont.setLayout(cl);

		panelCont.add(card1, "1");
		panelCont.add(spiel1, "2");
		panelCont.add(spiel2, "3");
		panelCont.add(spiel3, "4");
		panelCont.add(spiel4, "5");
		cl.show(panelCont, "1");
		add(panelCont);
		setVisible(true);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		Scanner Spielauswahl;
		int eingabe;
		boolean eingabeBeendet = false;
		String ungueltigeEingabe = "Ungueltige Eingabe! Moegliche Eingaben sind 1 fuer Spiel 1, 2 fuer Spiel 2, 3 fuer Spiel 3 und 4 fuer Spiel 4.";
		{
				while (!eingabeBeendet) {
					try {
						System.out.println("Bitte Spielnummer eingeben");
						Spielauswahl = new Scanner(System.in);
						eingabe = Spielauswahl.nextInt();

						if (eingabe == 1) {
							eingabeBeendet = true;
							cl.show(panelCont, "2");
							System.out.println("Spiel 1 wird gestartet");
						} else if (eingabe == 2) {
							eingabeBeendet = true;
							cl.show(panelCont, "3");
							System.out.println("Spiel 2 wird gestartet");
						} else if (eingabe == 3) {
							eingabeBeendet = true;
							cl.show(panelCont, "4");
							System.out.println("Spiel 3 wird gestartet");
						} else if (eingabe == 4) {
							eingabeBeendet = true;
							cl.show(panelCont, "5");
							System.out.println("Spiel 4 wird gestartet");
						} else {
							System.err.println(ungueltigeEingabe);
						}
					} catch (InputMismatchException e) {
						System.err.println(ungueltigeEingabe);
					}
				}
		}
	}
}
