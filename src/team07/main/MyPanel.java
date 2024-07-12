package team07.main;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class MyPanel extends JPanel{
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	private static int width;
	private static int height;
	
	public MyPanel(LeftPanel lPanel) {
        setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
			
		leftPanel = lPanel;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.8;
		gbc.weighty = 1.0;
		add(leftPanel, gbc);
			
		rightPanel = new RightPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 1.0;
		add(rightPanel, gbc);
		
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Component c = (Component) e.getSource();
				width = leftPanel.getWidth();
				height = leftPanel.getHeight();
				
			}
		});
	}


	/**
	 * @return the width
	 */
	public static int getLeftPanelWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public static void setLeftPanelWidth(int width) {
		MyPanel.width = width;
	}


	/**
	 * @return the height
	 */
	public static int getLeftPanelHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public static void setLeftPanelHeight(int height) {
		MyPanel.height = height;
	}

}