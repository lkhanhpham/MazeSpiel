package Spiel4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import team07.main.LeftPanel;
import team07.main.MyFrame;

public class SelectPanel extends JPanel{
	private JPanel topPanel, middlePanel, bottomPanel;
	private JButton start, anleitung, pause, beenden, zurueck, hsReset, lösungAbgeben;
	private JLabel scoreBoard, timerBoard;
	
	private JComboBox<String> dropdown;
	
//	private String[] levels = {"Training", "Mittel", "Schwer"};
	

	public SelectPanel() {
		setBackground(Color.white);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
//		
//		
//		topPanel = new JPanel();
//		topPanel.setBackground(Color.LIGHT_GRAY);
//		topPanel.setLayout(new GridBagLayout());
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.weightx = 1.0;
//		gbc.weighty = 0.25;
//		add(topPanel, gbc);
//		topPanel.setPreferredSize(new Dimension(0, 0));
//		
//		middlePanel = new JPanel();
//		middlePanel.setBackground(Color.blue);
//		middlePanel.setLayout(new GridBagLayout());
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.weightx = 1.0;
//		gbc.weighty = 0.25;
//		add(middlePanel, gbc);
//		middlePanel.setPreferredSize(new Dimension(0, 0));
//		
//		bottomPanel = new JPanel();
//		bottomPanel.setBackground(Color.gray);
//		bottomPanel.setLayout(new GridBagLayout());
//		gbc.gridx = 0;
//		gbc.gridy = 2;
//		gbc.weightx = 1.0;
//		gbc.weighty = 0.5;
//		add(bottomPanel, gbc);
//		bottomPanel.setPreferredSize(new Dimension(0, 0));
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		start = new JButton("Start");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(start, gbc);
		
		pause = new JButton("Pause");
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(pause, gbc);
//		pause.setEnabled(false);
		
		anleitung = new JButton("Anleitung");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(anleitung, gbc);
		
		beenden = new JButton("Beenden");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(beenden, gbc);
		beenden.setEnabled(false);
		
		String[] levels = {"Training", "Mittel", "Schwer"};
		dropdown = new JComboBox<>(levels);
		dropdown.setSelectedIndex(0);
		gbc.gridx = 0;
		gbc.gridy = 4;
//		gbc.gridwidth = 2;
		add(dropdown, gbc);
		
		zurueck = new JButton("Zurueck");
//		gbc.weightx = 1.0;
//		gbc.weighty = 0.3;
		gbc.gridx = 0;
		gbc.gridy = 5;
//		gbc.gridwidth = 1;
		add(zurueck, gbc);
		zurueck.setFocusable(false);
		
		hsReset = new JButton("HS Reset");
		gbc.gridx = 0;
		gbc.gridy = 6;
//		gbc.gridwidth = 1;
		add(hsReset, gbc);
		
		timerBoard = new JLabel("Timer:  ");
		gbc.gridx = 0;
		gbc.gridy = 7;
//		gbc.gridwidth = 2;
		add(timerBoard, gbc);
		
		scoreBoard = new JLabel("Score: \n Highscore: ");
		gbc.gridx = 0;
		gbc.gridy = 8;
//		gbc.gridwidth = 2;
		add(scoreBoard, gbc);
		
		lösungAbgeben = new JButton("Lösung abgeben");
		gbc.gridx = 0;
		gbc.gridy = 9;
//		gbc.gridwidth = 2;
		add(lösungAbgeben, gbc);
		setPreferredSize(new Dimension(0,0));
		
		lösungAbgeben.setEnabled(false);
		
		
		
	}

	public JButton getStart() {
		return start;
	}

	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getAnleitung() {
		return anleitung;
	}

	public void setAnleitung(JButton anleitung) {
		this.anleitung = anleitung;
	}

	public JButton getPause() {
		return pause;
	}

	public void setPause(JButton pause) {
		this.pause = pause;
	}

	public JButton getBeenden() {
		return beenden;
	}

	public void setBeenden(JButton beenden) {
		this.beenden = beenden;
	}

	public JButton getHsReset() {
		return hsReset;
	}

	public void setHsReset(JButton hsReset) {
		this.hsReset = hsReset;
	}

	public JComboBox<String> getDropdown() {
		return dropdown;
	}

	public void setDropdown(JComboBox<String> dropdown) {
		this.dropdown = dropdown;
	}

	public JButton getLösungAbgeben() {
		return lösungAbgeben;
	}

	public void setLösungAbgeben(JButton lösungAbgeben) {
		this.lösungAbgeben = lösungAbgeben;
	}

	public JButton getZurueck() {
		return zurueck;
	}

	public void setZurueck(JButton zurueck) {
		this.zurueck = zurueck;
	}
	
}
