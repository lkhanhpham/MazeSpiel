package Spiel1;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Maze.MazeDisplay;
import team07.main.MyPanel;

public class Props {
	
    private Image knapsack;
    private Image armor;
    private Image axe;
    private Image bow;
    private Image flint;
    private Image golden_apple;
    private Image meat;
    private Image pickaxe;
    private Image shovel;
    private Image sword;
    
    private int x, y;
    
    private int gewicht_armor, wert_armor;
    private int gewicht_axe, wert_axe;
    private int gewicht_bow, wert_bow;
    private int gewicht_flint, wert_flint;
    private int gewicht_golden_apple, wert_golden_apple;
    private int gewicht_meat, wert_meat;
    private int gewicht_pickaxe, wert_pickaxe;
    private int gewicht_shovel, wert_shovel;
    private int gewicht_sword, wert_sword;
    
    private AlgorithmDisplay dp;
    
    public Props(AlgorithmDisplay dp) {
    	
    	this.dp = dp;
        URL path1 = this.getClass().getResource("/resources/Spiel 1/knapsack.png");
        URL path2 = this.getClass().getResource("/resources/Spiel 1/armor.png");
        URL path3 = this.getClass().getResource("/resources/Spiel 1/bow.png");
        URL path4 = this.getClass().getResource("/resources/Spiel 1/flint.png");
        URL path5 = this.getClass().getResource("/resources/Spiel 1/golden_apple.png");
        URL path6 = this.getClass().getResource("/resources/Spiel 1/meat.png");
        URL path7 = this.getClass().getResource("/resources/Spiel 1/pickaxe.png");
        URL path8 = this.getClass().getResource("/resources/Spiel 1/shovel.png");
        URL path9 = this.getClass().getResource("/resources/Spiel 1/sword.png");
        URL path10 = this.getClass().getResource("/resources/Spiel 1/axe.png");
        
        try {
            knapsack = ImageIO.read(path1); 
            knapsack = knapsack.getScaledInstance(dp.getCellSize() * 2, dp.getCellSize() * 2, java.awt.Image.SCALE_REPLICATE);

            armor = ImageIO.read(path2); 
            armor = armor.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            bow = ImageIO.read(path3); 
            bow = bow.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            flint = ImageIO.read(path4); 
            flint = flint.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            golden_apple = ImageIO.read(path5); 
            golden_apple = golden_apple.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            meat = ImageIO.read(path6); 
            meat = meat.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            pickaxe = ImageIO.read(path7); 
            pickaxe = pickaxe.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            shovel = ImageIO.read(path8); 
            shovel = shovel.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            sword = ImageIO.read(path9); 
            sword = sword.getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            axe = ImageIO.read(path10); 
            axe = axe .getScaledInstance(dp.getCellSize(), dp.getCellSize(), java.awt.Image.SCALE_REPLICATE);
            
            } catch (IOException e) {
                }
    		}

	public Image getKnapsack() {
		return knapsack;
	}

	public void setKnapsack(Image knapsack) {
		this.knapsack = knapsack;
	}

	public int getXProp() {
		return x;
	}

	public void setXProp(int x) {
		this.x = x;
	}

	public int getYProp() {
		return y;
	}

	public void setYProp(int y) {
		this.y = y;
	}

	public AlgorithmDisplay getDp() {
		return dp;
	}

	public void setDp(AlgorithmDisplay dp) {
		this.dp = dp;
	}

	public Image getArmor() {
		return armor;
	}

	public void setArmor(Image armor) {
		this.armor = armor;
	}

	public Image getAxe() {
		return axe;
	}

	public void setAxe(Image axe) {
		this.axe = axe;
	}

	public Image getBow() {
		return bow;
	}

	public void setBow(Image bow) {
		this.bow = bow;
	}

	public Image getFlint() {
		return flint;
	}

	public void setFlint(Image flint) {
		this.flint = flint;
	}

	public Image getGolden_apple() {
		return golden_apple;
	}

	public void setGolden_apple(Image golden_apple) {
		this.golden_apple = golden_apple;
	}

	public Image getMeat() {
		return meat;
	}

	public void setMeat(Image meat) {
		this.meat = meat;
	}

	public Image getPickaxe() {
		return pickaxe;
	}

	public void setPickaxe(Image pickaxe) {
		this.pickaxe = pickaxe;
	}

	public Image getShovel() {
		return shovel;
	}

	public void setShovel(Image shovel) {
		this.shovel = shovel;
	}

	public Image getSword() {
		return sword;
	}

	public void setSword(Image sword) {
		this.sword = sword;
	}

	public int getGewicht_armor() {
		return gewicht_armor;
	}

	public void setGewicht_armor(int gewicht_armor) {
		this.gewicht_armor = gewicht_armor;
	}

	public int getWert_armor() {
		return wert_armor;
	}

	public void setWert_armor(int wert_armor) {
		this.wert_armor = wert_armor;
	}

	public int getGewicht_axe() {
		return gewicht_axe;
	}

	public void setGewicht_axe(int gewicht_axe) {
		this.gewicht_axe = gewicht_axe;
	}

	public int getWert_axe() {
		return wert_axe;
	}

	public void setWert_axe(int wert_axe) {
		this.wert_axe = wert_axe;
	}

	public int getGewicht_bow() {
		return gewicht_bow;
	}

	public void setGewicht_bow(int gewicht_bow) {
		this.gewicht_bow = gewicht_bow;
	}

	public int getWert_bow() {
		return wert_bow;
	}

	public void setWert_bow(int wert_bow) {
		this.wert_bow = wert_bow;
	}

	public int getGewicht_flint() {
		return gewicht_flint;
	}

	public void setGewicht_flint(int gewicht_flint) {
		this.gewicht_flint = gewicht_flint;
	}

	public int getWert_flint() {
		return wert_flint;
	}

	public void setWert_flint(int wert_flint) {
		this.wert_flint = wert_flint;
	}

	public int getGewicht_golden_apple() {
		return gewicht_golden_apple;
	}

	public void setGewicht_golden_apple(int gewicht_golden_apple) {
		this.gewicht_golden_apple = gewicht_golden_apple;
	}

	public int getWert_golden_apple() {
		return wert_golden_apple;
	}

	public void setWert_golden_apple(int wert_golden_apple) {
		this.wert_golden_apple = wert_golden_apple;
	}

	public int getGewicht_meat() {
		return gewicht_meat;
	}

	public void setGewicht_meat(int gewicht_meat) {
		this.gewicht_meat = gewicht_meat;
	}

	public int getWert_meat() {
		return wert_meat;
	}

	public void setWert_meat(int wert_meat) {
		this.wert_meat = wert_meat;
	}

	public int getGewicht_pickaxe() {
		return gewicht_pickaxe;
	}

	public void setGewicht_pickaxe(int gewicht_pickaxe) {
		this.gewicht_pickaxe = gewicht_pickaxe;
	}

	public int getWert_pickaxe() {
		return wert_pickaxe;
	}

	public void setWert_pickaxe(int wert_pickaxe) {
		this.wert_pickaxe = wert_pickaxe;
	}

	public int getGewicht_shovel() {
		return gewicht_shovel;
	}

	public void setGewicht_shovel(int gewicht_shovel) {
		this.gewicht_shovel = gewicht_shovel;
	}

	public int getWert_shovel() {
		return wert_shovel;
	}

	public void setWert_shovel(int wert_shovel) {
		this.wert_shovel = wert_shovel;
	}

	public int getGewicht_sword() {
		return gewicht_sword;
	}

	public void setGewicht_sword(int gewicht_sword) {
		this.gewicht_sword = gewicht_sword;
	}

	public int getWert_sword() {
		return wert_sword;
	}

	public void setWert_sword(int wert_sword) {
		this.wert_sword = wert_sword;
	}
	
	
}