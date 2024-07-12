package Spiel3;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import team07.main.LeftPanel;
import team07.main.MyFrame;
import team07.main.RightPanel;

public class Spiel3 extends JPanel  {
	private Auswahlbereich auswahlbereich;
	private GridBagConstraints container;
	public static JPanel panel;
	public static CardLayout cl=new CardLayout();
	private static Spielbereich0 spielbereich0=new Spielbereich0();
	private static SpielbereichTraining1 spielbereichTraining1=new SpielbereichTraining1();
	private static SpielbereichTraining2 spielbereichTraining2=new SpielbereichTraining2();
	private static SpielbereichMittel1 spielbereichMittel1=new SpielbereichMittel1();
	private static SpielbereichMittel2 spielbereichMittel2=new SpielbereichMittel2();
	private static SpielbereichSchwer1 spielbereichSchwer1=new SpielbereichSchwer1();
	private static SpielbereichSchwer2 spielbereichSchwer2=new SpielbereichSchwer2();
	public static int width;
	public static int height;
	
	
	
	
	public Spiel3(LeftPanel lPanel2) {
		 setLayout(new GridBagLayout());
		    container = new GridBagConstraints();
			container.fill = GridBagConstraints.BOTH;
			LeftPanel lPanel = lPanel2;
							
		    panel=new JPanel();
		    panel.setLayout(cl);
		    panel.add(spielbereich0,"0");
		    panel.add(spielbereichTraining1,"1");
		    panel.add(spielbereichTraining2,"2");
		    panel.add(spielbereichMittel1,"3");
		    panel.add(spielbereichMittel2,"4");
		    panel.add(spielbereichSchwer1,"5");
		    panel.add(spielbereichSchwer2,"6");
		    cl.show(panel, "0");
			container.gridx = 0;
			container.gridy = 0;
			container.weightx = 0.8;
			container.weighty = 1.0;
			add(panel, container);
			
			auswahlbereich = new Auswahlbereich(lPanel);
			container.gridx = 1;
			container.gridy = 0;
			container.weightx = 0.2;
			container.weighty = 1.0;
			add(auswahlbereich, container);
			
			addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component c = (Component) e.getSource();
					width = panel.getWidth();
					height = panel.getHeight();
					
				}
			});
		}
			
	public static JPanel getPanel() {
		return panel;
	}

	public static int getPanelWidth() {
		return width;
	}


	

	public static void setWidth(int width) {
		Spiel3.width = width;
	}

	public static int getPanelHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Spiel3.height = height;
	}

	public GridBagConstraints getContainer() {
		return container;
	}

	public void setContainer(GridBagConstraints container) {
		this.container = container;
	}

}