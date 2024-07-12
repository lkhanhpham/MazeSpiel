package Spiel1;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import team07.main.LeftPanel;
import team07.main.MyFrame;

public class Spiel1 extends JPanel {
	private GamePanel gPanel;
	private ActionPanel aPanel;
	private LeftPanel lPanel;
	
	public Spiel1(LeftPanel lPanel) {
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		this.lPanel = lPanel;
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gPanel = new GamePanel();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.99;
		gbc.weighty = 0.8;
		add(gPanel,gbc);
		
		aPanel = new ActionPanel(gPanel, this.lPanel);
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.01;
		gbc.weighty = 0.2;
		add(aPanel,gbc);
	}

	public GamePanel getgPanel() {
		return gPanel;
	}

	public void setgPanel(GamePanel gPanel) {
		this.gPanel = gPanel;
	}
}