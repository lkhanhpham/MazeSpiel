package Spiel1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private boolean schwer = false, mittel = false, training = true;
	private AlgorithmDisplay ks = new AlgorithmDisplay(schwer, mittel, training);
	
	public GamePanel() {
		setBackground(Color.WHITE); 
		setLayout(new BorderLayout());
	}
	
	public void drawGame() {
		if (ks != null) {
		this.remove(ks);
		}
		this.ks = new AlgorithmDisplay(schwer, mittel, training);
		this.add(ks, BorderLayout.CENTER);
		this.addKeyListener(ks);
		this.ks.setFocusable(true);
		this.requestFocusInWindow();
		this.revalidate();
		this.repaint();
	}
	
	public void removeGame() {
		if (ks != null) {
		this.remove(ks);
		}
		repaint();
	}

	public AlgorithmDisplay getKs() {
		return ks;
	}

	public void setKs(AlgorithmDisplay ks) {
		this.ks = ks;
	}

	public boolean isSchwer() {
		return schwer;
	}

	public void setSchwer(boolean schwer) {
		this.schwer = schwer;
	}

	public boolean isMittel() {
		return mittel;
	}

	public void setMittel(boolean mittel) {
		this.mittel = mittel;
	}

	public boolean isTraining() {
		return training;
	}

	public void setTraining(boolean training) {
		this.training = training;
	}
	
	
	
}
