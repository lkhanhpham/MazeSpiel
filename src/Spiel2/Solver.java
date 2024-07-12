package Spiel2;

import java.util.HashMap;

public class Solver {
	// contains the booleans indicating whether a tank is safe, 1 if tank is allowed
	// and 0 otherwise
	private int optResult;
	private Graph graph;


	/*
	 * Pseudocode 0. Wähle einen beliebigen Startknoten. Dieser ist der erste aktive
	 * Knoten. 1. Prüfe alle Nachbarknoten des aktiven Knoten, ob sie gefärbt sind.
	 * Wenn ja, streiche für jeden gefärbten Knoten dessen Farbe aus der Liste der
	 * erlaubten Farben. Füge jeden ungefärbten Knoten der Nachbarschaftsliste NL
	 * hinzu, außer er ist bereits enthalten. 2. Färbe den aktiven Knoten mit der
	 * nächsten Farbe aus der Liste der erlaubten Farben. 3. Entferne den gefärbten
	 * Knoten aus der Liste der Nachbarschaftsknoten. Ist die Nachbarschaftsliste
	 * leer, gib die Färbung zurück. Wenn nicht, setze den ersten Knoten aus der
	 * Nachbarschaftsliste als den nächsten aktiven Knoten und wiederhole dann
	 * Schritt 1.
	 */
	public Solver(Graph graph) {
		this.graph = graph;
		
		HashMap<Integer,Integer> avail_tank = new HashMap<Integer,Integer>();
		for(int i = 0; i < graph.getNodeList().size(); i++) {
			avail_tank.put(i,1);
		}
		solve(0,avail_tank);
		
		for(Node node : graph.getNodeList()) {
//			System.out.println("node: " + node.getName() + " color " + node.getTank());
		}
		optResult = calOptResult();
//		System.out.println(optResult);
	}

	public void solve(int count, HashMap<Integer, Integer> avail_tank) {
		if (count >= graph.getNodeList().size()) {
			return;
		}
		for(int i = 0; i < graph.getNodeList().size(); i++) {
			avail_tank.replace(i,1);
		}
		Node activeNode = graph.getNodeList().get(count);
		for(Node neighbor:activeNode.getAdj_list()) {
			if(neighbor.inTank()) {
				avail_tank.replace(neighbor.getTank(), 0);
			}
		}
		for(Object key: avail_tank.keySet()) {
//			System.out.print(avail_tank.get(key));
		}
//		System.out.println(activeNode.getName());
		for(Object key: avail_tank.keySet()) {
			if((int)avail_tank.get(key) == 1) {
				activeNode.setTank((int)key);
				activeNode.setInTank(true);
//				System.out.println("color "+ key);
				avail_tank.replace((int)key, 0);
				break;
			}
		}
			solve(count+1,avail_tank);
	}

	public int calOptResult() {
		int max = 0;
		for (Node node : graph.getNodeList()) {
			if (node.getTank() > max) {
				max = node.getTank();
			}
		}
		return max + 1;
	}
	

	public int getOptResult() {
		return optResult;
	}

	public void setOptResult(int optResult) {
		this.optResult = optResult;
	}
}
