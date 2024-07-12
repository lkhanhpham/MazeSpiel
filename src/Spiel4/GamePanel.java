package Spiel4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private Display dp;

	public GamePanel(String difficulty) {
		setLayout(new GridLayout());
		
		initGame(difficulty);
		
		
	}
	
	public void initGame(String diffculty) {
		dp = new Display(diffculty);
		add(dp); // Fuegt es dem Panel zu
//		addKeyListener(dp);
		dp.setFocusable(true);
		dp.requestFocusInWindow();
		
	}

	public Display getDp() {
		return dp;
	}

	public void setDp(Display dp) {
		this.dp = dp;
	}

}
