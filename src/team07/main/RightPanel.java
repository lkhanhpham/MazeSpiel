package team07.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Maze.MazeDisplay;

public class RightPanel extends JPanel implements ActionListener {
	private JButton anleitung;
	private static JButton neustart;
	private static JButton einszurueck;
	private JButton spiel1;
	private JButton spiel2;
	private JButton spiel3;
	private JButton spiel4;
	private static MazeDisplay maze;

	/**
	 * Die Komponenten werden dem Panel hinzugefügt und mittels GridLayout
	 * angeordnet
	 */

	public RightPanel() {	
	setBackground(Color.DARK_GRAY);
	setMaximumSize(new Dimension(200,800));
	setLayout(new GridBagLayout());
	
	maze = LeftPanel.getDp();

	//creates upper panel
	JPanel panel = new JPanel();
	panel.setBackground(Color.ORANGE);

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 0.2;
	add(panel,c);

	//creates Anleitung button
	anleitung = new JButton();
	
	URL url = this.getClass().getClassLoader().getResource("resources/anleitung.png");
	ImageIcon img = new ImageIcon(url);
	Image scaledImg = img.getImage().getScaledInstance(50, 50, 1);
	img = new ImageIcon(scaledImg);
	anleitung.setIcon(img);
	anleitung.setPreferredSize(new Dimension(50,50));

	panel.setLayout(new GridBagLayout());
	GridBagConstraints center = new GridBagConstraints();
	panel.add(anleitung, center);

	//creates middle panel 
	JPanel panel1 = new JPanel();
	panel1.setBackground(Color.ORANGE);
	c.gridy = 1;
	c.weighty = 0.4;
	c.fill = GridBagConstraints.BOTH;
	add(panel1,c);


	//creates lower panel
	JPanel panel2 = new JPanel();
	panel2.setBackground(Color.CYAN);
	c.weightx = 1;
	c.gridy = 2;;
	c.weighty = 0.4;
	c.fill = GridBagConstraints.BOTH;
	add(panel2,c);


	//creates two buttons which will be added to panel1

	neustart = new JButton();
	neustart.setText("Zum Startfeld");
	neustart.addActionListener(this);

	
	einszurueck = new JButton();
	einszurueck.setText("Eins zurueck");
	einszurueck.addActionListener(this);
	einszurueck.setEnabled(false);
	
	anleitung.setFocusable(false);
	neustart.setFocusable(false);
	einszurueck.setFocusable(false);

	anleitung.addActionListener(this);


	panel1.setLayout(new GridBagLayout());
	GridBagConstraints c1 = new GridBagConstraints();


	JPanel panel3 = new JPanel();
	panel3.setOpaque(false);
	panel1.add(panel3, c1);
	
	GroupLayout layout = new GroupLayout(panel3);

	panel3.setLayout(layout);
   
	panel1.add(panel3); 
	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);
	
	//Uses GroupLayout to manage buttons layout 
		 layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			   .addComponent(neustart)
			   .addComponent(einszurueck)
	
		 );
		 layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addComponent(neustart)
			.addComponent(einszurueck)

		 );

	spiel1 = new JButton("Spiel 1");
	//add functionality to button
	spiel1.addActionListener(this);

	spiel2 = new JButton("Spiel 2");

	spiel2.addActionListener(this);

	spiel3 = new JButton("Spiel 3");
	spiel3.addActionListener(this);

	spiel4 = new JButton("Spiel 4");

	spiel4.addActionListener(this);

	JLabel devMode = new JLabel();
	devMode.setText("<html><center><font color=#00617D><b>Entwicklermodus</font></b><br>"
	+ "<font color=#7A7A7A>Zum Spielen direkt starten</font>");
	devMode.setFont(new Font("Verdana", Font.PLAIN, 14));

	JPanel panel5 = new JPanel();
	panel5.setBackground(Color.CYAN);
	GroupLayout layout1 = new GroupLayout(panel5);
	panel5.setLayout(layout1);
	layout1.setAutoCreateGaps(true);
	

	layout1.setHorizontalGroup(
		layout1.createParallelGroup(GroupLayout.Alignment.CENTER)
		.addComponent(devMode)
		.addComponent(spiel1)
		.addComponent(spiel2)
		.addComponent(spiel3)
		.addComponent(spiel4)
	 );
	 layout1.setVerticalGroup(
		layout1.createSequentialGroup()
		.addComponent(devMode)
		.addGap(20)
		.addComponent(spiel1)
		.addComponent(spiel2)
		.addComponent(spiel3)
		.addComponent(spiel4)
		   
	 );
	panel2.add(panel5, c1);
		
	}

	// Die Methode gibt einen JFrame mit der Beschreibung und Ablauf des Spiels
	// wieder
	public void Spielanleitung() {
		JFrame frame = new JFrame("Spielanleitung");

		JOptionPane.showMessageDialog(frame, "Aufbau des Spiels:\n"
				+ "Es befindet sich ein Labyrinth im Spielbereich, wobei die Größe des Labyrinths frei gewählt werden kann. \n "
				+ "In unserem Fall besteht das Labyrinth aus einem Labyrinth, welches 3x permutiert und angehängt wurde. \n"
				+ "In der Mitte befindet sich ein grün eingefärbter Spieler, welcher sich mit den Pfeiltasten \n"
				+ "und den Tasten W,A,S und D bewegen kann. \n"
				+ "Außerdem gibt es 4 Ausgänge, die der Spieler über mehrere Wege erreichen kann. \n"
				+ "Ziel des Spiels:\n"
				+ "Der Spieler hat das Ziel einen der 4 Ausgänge zu erreichen um die Spiele aus MS2 öffnen zu können. \n"
				+ "Ablauf des Spiels:\n"
				+ "In der Mitte des Labyrinths befindet sich der Spieler.\n"
				+ "Dieser kann sich ein Weg zu den 4 Ausgängen bahnen. Dabei bleibt der Spieler grün, wenn er sich auf einen \n"
				+ "kürzesten Weg zu den 4 Ausgängen befindet. Andernfalls wird dieser rot.\n"
				+ "Mit dem Button Neustart kann wird der Spieler auf die Startposition gesetzt und grün eingefärbt. \n"
				+ "Mit dem Button Eins Zurück wird der Spieler auf die zuletzt besuchte Position gesetzt."
				+ "", "Spielanleitung",
				JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
		frame.setResizable(false);
	}

	// Wenn der Button Anleitung gedrückt wird, wird die Methode Spielanleitung
	// ausgeführt
	@Override
	public void actionPerformed(ActionEvent e) {
		
		maze = LeftPanel.getDp();
		
		if (e.getSource() == this.anleitung) {
			Spielanleitung();
		}
		
		if (e.getSource() == this.neustart) {
			//maze.setOneStepBackOrRestartInAction(true);
			maze.setResizeX(0);
			maze.setResizeY(0);
			maze.setOldResizeX(0);
			maze.setOldResizeY(0);
			maze.setStartPlayer(true);
			maze.setLeftPath(false);
			//maze.setAfterMove(false);
			maze.repaint();
			einszurueck.setEnabled(false);

		}
		
		if (e.getSource() == this.einszurueck) {
			maze.setOneStepBackOrRestartInAction(true);
			
			maze.setPointX(maze.getSetBackX()*maze.getCellSize()+maze.getStartPointX());
			maze.setPointY(maze.getSetBackY()*maze.getCellSize()+maze.getStartPointY());  
			
			maze.setResizeX((maze.getPointX()-maze.getStartPointX()) /maze.getCellSize());
			maze.setResizeY((maze.getPointY()-maze.getStartPointY()) /maze.getCellSize());
			
			if(maze.isBackDone()==true) {
				maze.getDrawOldX().remove(maze.getDrawOldX().size()-1);
				maze.getDrawOldY().remove(maze.getDrawOldY().size()-1);
				maze.getDrawPointX().remove(maze.getDrawPointX().size()-1);
				maze.getDrawPointY().remove(maze.getDrawPointY().size()-1);
			}

			
			for(int i=0;i<maze.getSp().getKoor().get(0).size();i++) {
			if((maze.getSp().getKoor().get(0).get(i) == maze.getResizeX() && maze.getSp().getKoor().get(1).get(i) == maze.getResizeY()
			 || maze.getSp().getKoor2().get(0).get(i) == maze.getResizeX() && maze.getSp().getKoor2().get(1).get(i) == maze.getResizeY()
			 || maze.getSp().getKoor3().get(0).get(i) == maze.getResizeX() && maze.getSp().getKoor3().get(1).get(i) == maze.getResizeY()
			 || maze.getSp().getKoor4().get(0).get(i) == maze.getResizeX() && maze.getSp().getKoor4().get(1).get(i) == maze.getResizeY()
			 || maze.getResizeX()==-(maze.getX2()-1) && maze.getResizeY()==-(maze.getY2()-1) || maze.getResizeX()==maze.getX2() && maze.getResizeY()==-(maze.getY2()-1)
			 || maze.getResizeX()==-(maze.getX2()-1) && maze.getResizeY()==maze.getY2() || maze.getResizeX()==maze.getX2() && maze.getResizeY()==maze.getY2()
			 || maze.getResizeX()==-(maze.getX2()) && maze.getResizeY()==-(maze.getY2()-1) || maze.getResizeX()==(maze.getX2()+1) && maze.getResizeY()==-(maze.getY2()-1)
			 || maze.getResizeX()==-(maze.getX2()) && maze.getResizeY()==maze.getY2() || maze.getResizeX()==(maze.getX2()+1) && maze.getResizeY()==maze.getY2()) && maze.getOffTrackCount() < 2){
				maze.setLeftPath(false);
				maze.setBackOnTrack(true);
				break;
			 } else {
				 if(i==maze.getSp().getKoor().get(0).size()-1) {// Checkt, dass nicht einfach wieder auf dem weg
					 maze.setLeftPath(true);                    // backSet benutzt wirdmaze.setAfterMove(true);
				 }
			 }
			}
			
			
			maze.setBackDone(false);
			maze.setMover(false);
			//maze.setBackWasUsed(true);
			maze.repaint();
			einszurueck.setEnabled(false);
		}
		
		if (e.getSource() == this.spiel1) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "2");
		}
		if (e.getSource() == this.spiel2) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "3");
		}
		if (e.getSource() == this.spiel3) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "4");
		}
		if (e.getSource() == this.spiel4) {
			MyFrame.getCl().show(MyFrame.getPanelCont(), "5");
		}
	}
		
		

	// Getters und Setters
	public JButton getAnleitung() {
		return anleitung;
	}

	public void setAnleitung(JButton anleitung) {
		this.anleitung = anleitung;
	}

	public static JButton getNeustart() {
		return neustart;
	}

	public static void setNeustart(JButton neustart2) {
		neustart = neustart2;
	}

	public static JButton getEinszurueck() {
		return einszurueck;
	}

	public static void setEinszurueck(JButton einszurueck) {
		RightPanel.einszurueck = einszurueck;
	}

	public JButton getSpiel1() {
		return spiel1;
	}

	public void setSpiel1(JButton spiel1) {
		this.spiel1 = spiel1;
	}

	public JButton getSpiel2() {
		return spiel2;
	}

	public void setSpiel2(JButton spiel2) {
		this.spiel2 = spiel2;
	}

	public JButton getSpiel3() {
		return spiel3;
	}

	public void setSpiel3(JButton spiel3) {
		this.spiel3 = spiel3;
	}

	public JButton getSpiel4() {
		return spiel4;
	}

	public void setSpiel4(JButton spiel4) {
		this.spiel4 = spiel4;
	}
	
	public static void setMaze() {
		maze = LeftPanel.getDp();
	}
}
