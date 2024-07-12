package Spiel2;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Node is a class that represents the fish 
 * @author LINHKHANHSPC
 *
 */
public class Node{ 
    private String name; 
    private int tank;
    //define adjacency list 
    private ArrayList<Node> adj_list;
    private boolean inTank;
    private Image fish;
    private HashMap<String, Integer> availNeighbor;
    /**
	 * @return the availNeighbor
	 */
	public HashMap<String, Integer> getAvailNeighbor() {
		return availNeighbor;
	}

	/**
	 * @param availNeighbor the availNeighbor to set
	 */
	public void setAvailNeighbor(HashMap<String, Integer> availNeighbor) {
		this.availNeighbor = availNeighbor;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tank
	 */
	public int getTank() {
		return tank;
	}

	/**
	 * @param tank the tank to set
	 */
	public void setTank(int tank) {
		this.tank = tank;
	}

	/**
	 * @return the adj_list
	 */
	public ArrayList<Node> getAdj_list() {
		return adj_list;
	}

	/**
	 * @param adj_list the adj_list to set
	 */
	public void setAdj_list(ArrayList<Node> adj_list) {
		this.adj_list = adj_list;
	}

	/**
	 * @return the isInTank
	 */
	public boolean inTank() {
		return inTank;
	}

	/**
	 * @param inTank to set 
	 */
	public void setInTank(boolean inTank) {
		this.inTank = inTank;
	}

	/**
	 * @return the fish
	 */
	public Image getFish() {
		return fish;
	}

	/**
	 * @param fish the fish to set
	 */
	public void setFish(Image fish) {
		this.fish = fish;
	}

	public Node(String name){
        this.name = name;
        adj_list = new ArrayList<Node>();
        availNeighbor = new HashMap<String, Integer>();
    	for(int i = 0; i < Graph.getNodes().size(); i++) {
    		if(Graph.getNodes().get(i)==name) {
    			availNeighbor.put(Graph.getNodes().get(i), 0);
    		}
    		else {
    			availNeighbor.put(Graph.getNodes().get(i), 1);
    		}
    	}
    }

    public void addNeighbor(Node k){
        adj_list.add(k);
        k.adj_list.add(this);
    }

    public String printNeighborList(){
        String s = "";
        for(Node node : adj_list){
            s= s.concat(node.name.charAt(0) + ", ");
          }
        return s;
    }
    public Image getImage(String name) {
    	String s = new String();
    	switch (name) {
    		case "Alaska":
    			s = "/resources/Spiel 2/fish-03.png";
    			break;
    		case "Moorish":
    			s = "/resources/Spiel 2/fish-04.png";
    			break;
    		case "Seahorse":
    			s = "/resources/Spiel 2/fish-05.png";
    			break;
    		case "Bluefish":
    			s = "/resources/Spiel 2/fish-06.png";
    			break;
    		case "Catfish":
    			s = "/resources/Spiel 2/fish-07.png";
    			break;
    		case "Rabbitfish":
    			s = "/resources/Spiel 2/fish-08.png";
    			break;
    		case "Goldfish":
    			s = "/resources/Spiel 2/fish-09.png";
    			break;
    		case "Horn Shark":
    			s = "/resources/Spiel 2/fish-10.png";
    			break;
    		case "Tang":
    			s = "/resources/Spiel 2/fish-11.png";
    			break;
    		case "Dragonet":
    			s = "/resources/Spiel 2/fish-12.png";
    			break;
    	}

    	URL path = this.getClass().getResource(s);
    	try {
            fish = ImageIO.read(path); 
            fish = fish.getScaledInstance(25, 25, Image.SCALE_FAST);
            } catch (IOException e) {
                }
 
    	return fish;
    }
    public static Image getShadow(String name) {
    	String s = new String();
    	switch (name) {
    		case "Alaska":
    			s = "/resources/Spiel 2/fish shadow-03.png";
    			break;
    		case "Moorish":
    			s = "/resources/Spiel 2/fish shadow-04.png";
    			break;
    		case "Seahorse":
    			s = "/resources/Spiel 2/fish shadow-05.png";
    			break;
    		case "Bluefish":
    			s = "/resources/Spiel 2/fish shadow-06.png";
    			break;
    		case "Catfish":
    			s = "/resources/Spiel 2/fish shadow-07.png";
    			break;
    		case "Rabbitfish":
    			s = "/resources/Spiel 2/fish shadow-08.png";
    			break;
    		case "Goldfish":
    			s = "/resources/Spiel 2/fish shadow-09.png";
    			break;
    		case "Horn Shark":
    			s = "/resources/Spiel 2/fish shadow-10.png";
    			break;
    		case "Tang":
    			s = "/resources/Spiel 2/fish shadow-11.png";
    			break;
    		case "Dragonet":
    			s = "/resources/Spiel 2/fish shadow-12.png";
    			break;
    	}

    	URL path = Node.class.getResource(s);
    	Image fishshadow = null;
    	try {
            fishshadow = ImageIO.read(path); 
            fishshadow = fishshadow.getScaledInstance(25, 25, Image.SCALE_FAST);
            } catch (IOException e) {
                }

    	return fishshadow;
    }
    
}