package Spiel1;

import java.util.ArrayList;

public class Knapsack {

	private int gesamtGewicht, perfectValue_schwer, perfectValue_mittel, perfectValue_training;
	private ArrayList<Integer> includedSchwer, includedMittel, includedTraining;
	private int[] gewicht_training, wert_training, gewicht_mittel, wert_mittel, gewicht_schwer, wert_schwer;
	private int size_training, size_mittel, size_schwer, res, w;
	private int[][] grid_schwer, grid_mittel, grid_training;
	private int counter = 0, min, max, r;
	
	
	public Knapsack() {
		
		gewicht_training = new int[5];
		wert_training  = new int[5];
		gewicht_mittel = new int[7];
		wert_mittel = new int[7];
		gewicht_schwer = new int[9];
		wert_schwer = new int[9];
		
		includedSchwer = new ArrayList<Integer>();
		includedMittel = new ArrayList<Integer>();
		includedTraining = new ArrayList<Integer>();
		
		min = 4;
	    max = 50;
	    r = (int)Math.floor(Math.random()*(max-min+1)+min);
	    
		gesamtGewicht = r;
		
		for (int i = 0; i < gewicht_schwer.length; i++) {
			min = 1;
		    max = 6;
			r = (int)Math.floor(Math.random()*(max-min+1)+min);
			gewicht_schwer[i] = r;
			min = 1;
		    max = 70;
		    r = (int)Math.floor(Math.random()*(max-min+1)+min);
		    wert_schwer[i] = r;
		}
		
		for (int i = 0; i < gewicht_mittel.length; i++) {
			gewicht_mittel[i] = gewicht_schwer[i];
			wert_mittel[i] = wert_schwer[i];
		}
		
		for (int i = 0; i < gewicht_training.length; i++) {
			gewicht_training[i] = gewicht_schwer[i];
			wert_training[i] = wert_schwer[i];
		}
		
		size_training = gewicht_training.length;
		size_mittel = gewicht_mittel.length;
		size_schwer = gewicht_schwer.length;
	}
	
	public void perfectValue_schwer() {
		grid_schwer = new int[size_schwer + 1][gesamtGewicht + 1];

		for (int i = 0; i <= size_schwer; i++) {
			for (int j = 0; j <= gesamtGewicht; j++) {

				if (i == 0 || j == 0) {
					grid_schwer[i][j] = 0;
				} else if (gewicht_schwer[i - 1] <= j) {
					grid_schwer[i][j] = Math.max(wert_schwer[i - 1] + grid_schwer[i - 1][j - gewicht_schwer[i - 1]], grid_schwer[i - 1][j]);
				} else {
					grid_schwer[i][j] = grid_schwer[i - 1][j];
				}
			}
		}

		perfectValue_schwer = grid_schwer[size_schwer][gesamtGewicht];
		res = perfectValue_schwer;
		w = gesamtGewicht;
		
		for (int i = size_schwer; i > 0 && res > 0; i--) {
		if (res == grid_schwer[i - 1][w]) {
		continue;
		} else {
		includedSchwer.add(gewicht_schwer[i - 1]);
		res -= wert_schwer[i - 1];
		w -= gewicht_schwer[i - 1];
		}
	}
}

	public void perfectValue_mittel() {
		grid_mittel = new int[size_mittel + 1][gesamtGewicht + 1];

		for (int i = 0; i <= size_mittel; i++) {
			for (int j = 0; j <= gesamtGewicht; j++) {

				if (i == 0 || j == 0) {
					grid_mittel[i][j] = 0;
				} else if (gewicht_mittel[i - 1] <= j) {
					grid_mittel[i][j] = Math.max(wert_mittel[i - 1] + grid_mittel[i - 1][j - gewicht_mittel[i - 1]], grid_mittel[i - 1][j]);
				} else {
					grid_mittel[i][j] = grid_mittel[i - 1][j];
				}
			}
		}

		perfectValue_mittel = grid_mittel[size_mittel][gesamtGewicht];
		res = perfectValue_mittel;
		w = gesamtGewicht;
		
		for (int i = size_mittel; i > 0 && res > 0; i--) {
		if (res == grid_mittel[i - 1][w]) {
		continue;
		} else {
		includedSchwer.add(gewicht_mittel[i - 1]);
		res -= wert_mittel[i - 1];
		w -= gewicht_mittel[i - 1];
		}
	}
}
	
	public void perfectValue_training() {
		grid_training = new int[size_training + 1][gesamtGewicht + 1];

		for (int i = 0; i <= size_training; i++) {
			for (int j = 0; j <= gesamtGewicht; j++) {
				if (i == 0 || j == 0) {
					grid_training[i][j] = 0;
				} else if (gewicht_training[i - 1] <= j) {
					grid_training[i][j] = Math.max(wert_training[i - 1] + grid_training[i - 1][j - gewicht_training[i - 1]], grid_training[i - 1][j]);
				} else {
					grid_training[i][j] = grid_training[i - 1][j];
				}
			}
		}

		perfectValue_training = grid_training[size_training][gesamtGewicht];
		res = perfectValue_training;
		w = gesamtGewicht;
		
		for (int i = size_training; i > 0 && res > 0; i--) {
		if (res == grid_training[i - 1][w]) {
		continue;
		} else {
		includedSchwer.add(gewicht_training[i - 1]);
		res -= wert_training[i - 1];
		w -= gewicht_training[i - 1];
		}
	}
}

	//Getter & Setter

	public int getGesamtGewicht() {
		return gesamtGewicht;
	}

	public void setGesamtGewicht(int gesamtGewicht) {
		this.gesamtGewicht = gesamtGewicht;
	}

	public int getPerfectValue_schwer() {
		return perfectValue_schwer;
	}

	public void setPerfectValue_schwer(int perfectValue_schwer) {
		this.perfectValue_schwer = perfectValue_schwer;
	}

	public int getPerfectValue_mittel() {
		return perfectValue_mittel;
	}

	public void setPerfectValue_mittel(int perfectValue_mittel) {
		this.perfectValue_mittel = perfectValue_mittel;
	}

	public int getPerfectValue_training() {
		return perfectValue_training;
	}

	public void setPerfectValue_training(int perfectValue_training) {
		this.perfectValue_training = perfectValue_training;
	}

	public ArrayList<Integer> getIncludedSchwer() {
		return includedSchwer;
	}

	public void setIncludedSchwer(ArrayList<Integer> includedSchwer) {
		this.includedSchwer = includedSchwer;
	}

	public ArrayList<Integer> getIncludedMittel() {
		return includedMittel;
	}

	public void setIncludedMittel(ArrayList<Integer> includedMittel) {
		this.includedMittel = includedMittel;
	}

	public ArrayList<Integer> getIncludedTraining() {
		return includedTraining;
	}

	public void setIncludedTraining(ArrayList<Integer> includedTraining) {
		this.includedTraining = includedTraining;
	}

	public int[] getGewicht_training() {
		return gewicht_training;
	}

	public void setGewicht_training(int[] gewicht_training) {
		this.gewicht_training = gewicht_training;
	}

	public int[] getWert_training() {
		return wert_training;
	}

	public void setWert_training(int[] wert_training) {
		this.wert_training = wert_training;
	}

	public int[] getGewicht_mittel() {
		return gewicht_mittel;
	}

	public void setGewicht_mittel(int[] gewicht_mittel) {
		this.gewicht_mittel = gewicht_mittel;
	}

	public int[] getWert_mittel() {
		return wert_mittel;
	}

	public void setWert_mittel(int[] wert_mittel) {
		this.wert_mittel = wert_mittel;
	}

	public int[] getGewicht_schwer() {
		return gewicht_schwer;
	}

	public void setGewicht_schwer(int[] gewicht_schwer) {
		this.gewicht_schwer = gewicht_schwer;
	}

	public int[] getWert_schwer() {
		return wert_schwer;
	}

	public void setWert_schwer(int[] wert_schwer) {
		this.wert_schwer = wert_schwer;
	}

	public int getSize_training() {
		return size_training;
	}

	public void setSize_training(int size_training) {
		this.size_training = size_training;
	}

	public int getSize_mittel() {
		return size_mittel;
	}

	public void setSize_mittel(int size_mittel) {
		this.size_mittel = size_mittel;
	}

	public int getSize_schwer() {
		return size_schwer;
	}

	public void setSize_schwer(int size_schwer) {
		this.size_schwer = size_schwer;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int[][] getGrid_schwer() {
		return grid_schwer;
	}

	public void setGrid_schwer(int[][] grid_schwer) {
		this.grid_schwer = grid_schwer;
	}

	public int[][] getGrid_mittel() {
		return grid_mittel;
	}

	public void setGrid_mittel(int[][] grid_mittel) {
		this.grid_mittel = grid_mittel;
	}

	public int[][] getGrid_training() {
		return grid_training;
	}

	public void setGrid_training(int[][] grid_training) {
		this.grid_training = grid_training;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

}