package Spiel2;

import Spiel2.Spiel2.Level;

public class Game {
	private Graph graph;
	private Level level;

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
		
	}
	
	
	public Game(Level level){
		graph = new Graph(level);
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	
}
