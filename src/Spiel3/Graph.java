package Spiel3;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Graph {
	public int knotenanzahl;
	public int[][] adjazenzmatrix;
	int showgraph;

	public Graph(int x, int showgraph) {
		knotenanzahl = x;
		adjazenzmatrix = new int[knotenanzahl][knotenanzahl];

		for (int i = 0; i < knotenanzahl; i++) {
			for (int j = 0; j < knotenanzahl; j++) {
				adjazenzmatrix[i][j] = 0;
			}
		}

		this.showgraph = showgraph;

		createGraph();

	}

	/**
	 * In der Methode createGraph wird ein Graph in Form einer Adjazenzmatrix
	 * zufällig erstellt
	 * 
	 * @param zeile
	 * @param spalte
	 */
	public void createGraph() {
		int[] zufallszahlen = new int[16];
		for (int i = 0; i < zufallszahlen.length; i++) {
			int c = 1 + (int) (Math.random() * ((20 - 2) + 1));
			for (int j = 0; j <= i; j++) {
				if (c == zufallszahlen[j]) {
					c = 1 + (int) (Math.random() * ((20 - 2) + 1));
					j = -1;
				}
			}
			zufallszahlen[i] = c;
		}
		if (knotenanzahl == 4 && showgraph == 1) {
			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = zufallszahlen[1];
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = 0;
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[2][0] = zufallszahlen[1];
			adjazenzmatrix[2][1] = 0;
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = zufallszahlen[3];
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = zufallszahlen[3];
			adjazenzmatrix[3][3] = 0;

		} else if (knotenanzahl == 4 && showgraph == 2) {
			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = 0;
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = zufallszahlen[1];
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[2][0] = 0;
			adjazenzmatrix[2][1] = zufallszahlen[1];
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = 0;
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = 0;
			adjazenzmatrix[3][3] = 0;
		}

		if (knotenanzahl == 8 && showgraph == 1) {
			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = zufallszahlen[1];
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[0][4] = 0;
			adjazenzmatrix[0][5] = 0;
			adjazenzmatrix[0][6] = 0;
			adjazenzmatrix[0][7] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = 0;
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[1][4] = 0;
			adjazenzmatrix[1][5] = 0;
			adjazenzmatrix[1][6] = 0;
			adjazenzmatrix[1][7] = 0;
			adjazenzmatrix[2][0] = zufallszahlen[1];
			adjazenzmatrix[2][1] = 0;
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = zufallszahlen[3];
			adjazenzmatrix[2][4] = zufallszahlen[4];
			adjazenzmatrix[2][5] = 0;
			adjazenzmatrix[2][6] = 0;
			adjazenzmatrix[2][7] = 0;
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = zufallszahlen[3];
			adjazenzmatrix[3][3] = 0;
			adjazenzmatrix[3][4] = 0;
			adjazenzmatrix[3][5] = zufallszahlen[5];
			adjazenzmatrix[3][6] = 0;
			adjazenzmatrix[3][7] = 0;
			adjazenzmatrix[4][0] = 0;
			adjazenzmatrix[4][1] = 0;
			adjazenzmatrix[4][2] = zufallszahlen[4];
			adjazenzmatrix[4][3] = 0;
			adjazenzmatrix[4][4] = 0;
			adjazenzmatrix[4][5] = zufallszahlen[6];
			adjazenzmatrix[4][6] = zufallszahlen[7];
			adjazenzmatrix[4][7] = 0;
			adjazenzmatrix[5][0] = 0;
			adjazenzmatrix[5][1] = 0;
			adjazenzmatrix[5][2] = 0;
			adjazenzmatrix[5][3] = zufallszahlen[5];
			adjazenzmatrix[5][4] = zufallszahlen[6];
			adjazenzmatrix[5][5] = 0;
			adjazenzmatrix[5][6] = 0;
			adjazenzmatrix[5][7] = zufallszahlen[8];
			adjazenzmatrix[6][0] = 0;
			adjazenzmatrix[6][1] = 0;
			adjazenzmatrix[6][2] = 0;
			adjazenzmatrix[6][3] = 0;
			adjazenzmatrix[6][4] = zufallszahlen[7];
			adjazenzmatrix[6][5] = 0;
			adjazenzmatrix[6][6] = 0;
			adjazenzmatrix[6][7] = zufallszahlen[9];
			adjazenzmatrix[7][0] = 0;
			adjazenzmatrix[7][1] = 0;
			adjazenzmatrix[7][2] = 0;
			adjazenzmatrix[7][3] = 0;
			adjazenzmatrix[7][4] = 0;
			adjazenzmatrix[7][5] = zufallszahlen[8];
			adjazenzmatrix[7][6] = zufallszahlen[9];
			adjazenzmatrix[7][7] = 0;

		} else if (knotenanzahl == 8 && showgraph == 2) {

			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = zufallszahlen[1];
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[0][4] = 0;
			adjazenzmatrix[0][5] = 0;
			adjazenzmatrix[0][6] = 0;
			adjazenzmatrix[0][7] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = 0;
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[1][4] = 0;
			adjazenzmatrix[1][5] = 0;
			adjazenzmatrix[1][6] = 0;
			adjazenzmatrix[1][7] = 0;
			adjazenzmatrix[2][0] = zufallszahlen[1];
			adjazenzmatrix[2][1] = 0;
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = zufallszahlen[3];
			adjazenzmatrix[2][4] = zufallszahlen[4];
			adjazenzmatrix[2][5] = 0;
			adjazenzmatrix[2][6] = 0;
			adjazenzmatrix[2][7] = 0;
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = zufallszahlen[3];
			adjazenzmatrix[3][3] = 0;
			adjazenzmatrix[3][4] = 0;
			adjazenzmatrix[3][5] = zufallszahlen[5];
			adjazenzmatrix[3][6] = 0;
			adjazenzmatrix[3][7] = 0;
			adjazenzmatrix[4][0] = 0;
			adjazenzmatrix[4][1] = 0;
			adjazenzmatrix[4][2] = zufallszahlen[4];
			adjazenzmatrix[4][3] = 0;
			adjazenzmatrix[4][4] = 0;
			adjazenzmatrix[4][5] = 0;
			adjazenzmatrix[4][6] = zufallszahlen[6];
			adjazenzmatrix[4][7] = 0;
			adjazenzmatrix[5][0] = 0;
			adjazenzmatrix[5][1] = 0;
			adjazenzmatrix[5][2] = 0;
			adjazenzmatrix[5][3] = zufallszahlen[5];
			adjazenzmatrix[5][4] = 0;
			adjazenzmatrix[5][5] = 0;
			adjazenzmatrix[5][6] = 0;
			adjazenzmatrix[5][7] = zufallszahlen[7];
			adjazenzmatrix[6][0] = 0;
			adjazenzmatrix[6][1] = 0;
			adjazenzmatrix[6][2] = 0;
			adjazenzmatrix[6][3] = 0;
			adjazenzmatrix[6][4] = zufallszahlen[6];
			adjazenzmatrix[6][5] = 0;
			adjazenzmatrix[6][6] = 0;
			adjazenzmatrix[6][7] = zufallszahlen[8];
			adjazenzmatrix[7][0] = 0;
			adjazenzmatrix[7][1] = 0;
			adjazenzmatrix[7][2] = 0;
			adjazenzmatrix[7][3] = 0;
			adjazenzmatrix[7][4] = 0;
			adjazenzmatrix[7][5] = zufallszahlen[7];
			adjazenzmatrix[7][6] = zufallszahlen[8];
			adjazenzmatrix[7][7] = 0;

		}

		if (knotenanzahl == 12 && showgraph == 1) {
			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = zufallszahlen[1];
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[0][4] = 0;
			adjazenzmatrix[0][5] = 0;
			adjazenzmatrix[0][6] = 0;
			adjazenzmatrix[0][7] = 0;
			adjazenzmatrix[0][8] = 0;
			adjazenzmatrix[0][9] = 0;
			adjazenzmatrix[0][10] = 0;
			adjazenzmatrix[0][11] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = 0;
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[1][4] = 0;
			adjazenzmatrix[1][5] = 0;
			adjazenzmatrix[1][6] = 0;
			adjazenzmatrix[1][7] = 0;
			adjazenzmatrix[1][8] = 0;
			adjazenzmatrix[1][9] = 0;
			adjazenzmatrix[1][10] = 0;
			adjazenzmatrix[1][11] = 0;
			adjazenzmatrix[2][0] = zufallszahlen[1];
			adjazenzmatrix[2][1] = 0;
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = zufallszahlen[3];
			adjazenzmatrix[2][4] = zufallszahlen[4];
			adjazenzmatrix[2][5] = 0;
			adjazenzmatrix[2][6] = 0;
			adjazenzmatrix[2][7] = 0;
			adjazenzmatrix[2][8] = 0;
			adjazenzmatrix[2][9] = 0;
			adjazenzmatrix[2][10] = 0;
			adjazenzmatrix[2][11] = 0;
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = zufallszahlen[3];
			adjazenzmatrix[3][3] = 0;
			adjazenzmatrix[3][4] = 0;
			adjazenzmatrix[3][5] = zufallszahlen[5];
			adjazenzmatrix[3][6] = 0;
			adjazenzmatrix[3][7] = 0;
			adjazenzmatrix[3][8] = 0;
			adjazenzmatrix[3][9] = 0;
			adjazenzmatrix[3][10] = 0;
			adjazenzmatrix[3][11] = 0;
			adjazenzmatrix[4][0] = 0;
			adjazenzmatrix[4][1] = 0;
			adjazenzmatrix[4][2] = zufallszahlen[4];
			adjazenzmatrix[4][3] = 0;
			adjazenzmatrix[4][4] = 0;
			adjazenzmatrix[4][5] = zufallszahlen[6];
			adjazenzmatrix[4][6] = zufallszahlen[7];
			adjazenzmatrix[4][7] = 0;
			adjazenzmatrix[4][8] = 0;
			adjazenzmatrix[4][9] = 0;
			adjazenzmatrix[4][10] = 0;
			adjazenzmatrix[4][11] = 0;
			adjazenzmatrix[5][0] = 0;
			adjazenzmatrix[5][1] = 0;
			adjazenzmatrix[5][2] = 0;
			adjazenzmatrix[5][3] = zufallszahlen[5];
			adjazenzmatrix[5][4] = zufallszahlen[6];
			adjazenzmatrix[5][5] = 0;
			adjazenzmatrix[5][6] = 0;
			adjazenzmatrix[5][7] = zufallszahlen[8];
			adjazenzmatrix[5][8] = 0;
			adjazenzmatrix[5][9] = 0;
			adjazenzmatrix[5][10] = 0;
			adjazenzmatrix[5][11] = 0;
			adjazenzmatrix[6][0] = 0;
			adjazenzmatrix[6][1] = 0;
			adjazenzmatrix[6][2] = 0;
			adjazenzmatrix[6][3] = 0;
			adjazenzmatrix[6][4] = zufallszahlen[7];
			adjazenzmatrix[6][5] = 0;
			adjazenzmatrix[6][6] = 0;
			adjazenzmatrix[6][7] = zufallszahlen[9];
			adjazenzmatrix[6][8] = zufallszahlen[10];
			adjazenzmatrix[6][9] = 0;
			adjazenzmatrix[6][10] = 0;
			adjazenzmatrix[6][11] = 0;
			adjazenzmatrix[7][0] = 0;
			adjazenzmatrix[7][1] = 0;
			adjazenzmatrix[7][2] = 0;
			adjazenzmatrix[7][3] = 0;
			adjazenzmatrix[7][4] = 0;
			adjazenzmatrix[7][5] = zufallszahlen[8];
			adjazenzmatrix[7][6] = zufallszahlen[9];
			adjazenzmatrix[7][7] = 0;
			adjazenzmatrix[7][8] = 0;
			adjazenzmatrix[7][9] = zufallszahlen[11];
			adjazenzmatrix[7][10] = 0;
			adjazenzmatrix[7][11] = 0;
			adjazenzmatrix[8][0] = 0;
			adjazenzmatrix[8][1] = 0;
			adjazenzmatrix[8][2] = 0;
			adjazenzmatrix[8][3] = 0;
			adjazenzmatrix[8][4] = 0;
			adjazenzmatrix[8][5] = 0;
			adjazenzmatrix[8][6] = zufallszahlen[10];
			adjazenzmatrix[8][7] = 0;
			adjazenzmatrix[8][8] = 0;
			adjazenzmatrix[8][9] = zufallszahlen[12];
			adjazenzmatrix[8][10] = zufallszahlen[13];
			adjazenzmatrix[8][11] = 0;
			adjazenzmatrix[9][0] = 0;
			adjazenzmatrix[9][1] = 0;
			adjazenzmatrix[9][2] = 0;
			adjazenzmatrix[9][3] = 0;
			adjazenzmatrix[9][4] = 0;
			adjazenzmatrix[9][5] = 0;
			adjazenzmatrix[9][6] = 0;
			adjazenzmatrix[9][7] = zufallszahlen[11];
			adjazenzmatrix[9][8] = zufallszahlen[12];
			adjazenzmatrix[9][9] = 0;
			adjazenzmatrix[9][10] = 0;
			adjazenzmatrix[9][11] = zufallszahlen[14];
			adjazenzmatrix[10][0] = 0;
			adjazenzmatrix[10][1] = 0;
			adjazenzmatrix[10][2] = 0;
			adjazenzmatrix[10][3] = 0;
			adjazenzmatrix[10][4] = 0;
			adjazenzmatrix[10][5] = 0;
			adjazenzmatrix[10][6] = 0;
			adjazenzmatrix[10][7] = 0;
			adjazenzmatrix[10][8] = zufallszahlen[13];
			adjazenzmatrix[10][9] = 0;
			adjazenzmatrix[10][10] = 0;
			adjazenzmatrix[10][11] = zufallszahlen[15];
			adjazenzmatrix[11][0] = 0;
			adjazenzmatrix[11][1] = 0;
			adjazenzmatrix[11][2] = 0;
			adjazenzmatrix[11][3] = 0;
			adjazenzmatrix[11][4] = 0;
			adjazenzmatrix[11][5] = 0;
			adjazenzmatrix[11][6] = 0;
			adjazenzmatrix[11][7] = 0;
			adjazenzmatrix[11][8] = 0;
			adjazenzmatrix[11][9] = zufallszahlen[14];
			adjazenzmatrix[11][10] = zufallszahlen[15];
			adjazenzmatrix[11][11] = 0;

		} else if (knotenanzahl == 12 && showgraph == 2) {

			adjazenzmatrix[0][0] = 0;
			adjazenzmatrix[0][1] = zufallszahlen[0];
			adjazenzmatrix[0][2] = zufallszahlen[1];
			adjazenzmatrix[0][3] = 0;
			adjazenzmatrix[0][4] = 0;
			adjazenzmatrix[0][5] = 0;
			adjazenzmatrix[0][6] = 0;
			adjazenzmatrix[0][7] = 0;
			adjazenzmatrix[0][8] = 0;
			adjazenzmatrix[0][9] = 0;
			adjazenzmatrix[0][10] = 0;
			adjazenzmatrix[0][11] = 0;
			adjazenzmatrix[1][0] = zufallszahlen[0];
			adjazenzmatrix[1][1] = 0;
			adjazenzmatrix[1][2] = 0;
			adjazenzmatrix[1][3] = zufallszahlen[2];
			adjazenzmatrix[1][4] = 0;
			adjazenzmatrix[1][5] = 0;
			adjazenzmatrix[1][6] = 0;
			adjazenzmatrix[1][7] = 0;
			adjazenzmatrix[1][8] = 0;
			adjazenzmatrix[1][9] = 0;
			adjazenzmatrix[1][10] = 0;
			adjazenzmatrix[1][11] = 0;
			adjazenzmatrix[2][0] = zufallszahlen[1];
			adjazenzmatrix[2][1] = 0;
			adjazenzmatrix[2][2] = 0;
			adjazenzmatrix[2][3] = 0;
			adjazenzmatrix[2][4] = zufallszahlen[3];
			adjazenzmatrix[2][5] = 0;
			adjazenzmatrix[2][6] = 0;
			adjazenzmatrix[2][7] = 0;
			adjazenzmatrix[2][8] = 0;
			adjazenzmatrix[2][9] = 0;
			adjazenzmatrix[2][10] = 0;
			adjazenzmatrix[2][11] = 0;
			adjazenzmatrix[3][0] = 0;
			adjazenzmatrix[3][1] = zufallszahlen[2];
			adjazenzmatrix[3][2] = 0;
			adjazenzmatrix[3][3] = 0;
			adjazenzmatrix[3][4] = 0;
			adjazenzmatrix[3][5] = zufallszahlen[4];
			adjazenzmatrix[3][6] = 0;
			adjazenzmatrix[3][7] = 0;
			adjazenzmatrix[3][8] = 0;
			adjazenzmatrix[3][9] = 0;
			adjazenzmatrix[3][10] = 0;
			adjazenzmatrix[3][11] = 0;
			adjazenzmatrix[4][0] = 0;
			adjazenzmatrix[4][1] = 0;
			adjazenzmatrix[4][2] = zufallszahlen[3];
			adjazenzmatrix[4][3] = 0;
			adjazenzmatrix[4][4] = 0;
			adjazenzmatrix[4][5] = 0;
			adjazenzmatrix[4][6] = zufallszahlen[5];
			adjazenzmatrix[4][7] = 0;
			adjazenzmatrix[4][8] = 0;
			adjazenzmatrix[4][9] = 0;
			adjazenzmatrix[4][10] = 0;
			adjazenzmatrix[4][11] = 0;
			adjazenzmatrix[5][0] = 0;
			adjazenzmatrix[5][1] = 0;
			adjazenzmatrix[5][2] = 0;
			adjazenzmatrix[5][3] = zufallszahlen[4];
			adjazenzmatrix[5][4] = 0;
			adjazenzmatrix[5][5] = 0;
			adjazenzmatrix[5][6] = 0;
			adjazenzmatrix[5][7] = zufallszahlen[6];
			adjazenzmatrix[5][8] = 0;
			adjazenzmatrix[5][9] = 0;
			adjazenzmatrix[5][10] = 0;
			adjazenzmatrix[5][11] = 0;
			adjazenzmatrix[6][0] = 0;
			adjazenzmatrix[6][1] = 0;
			adjazenzmatrix[6][2] = 0;
			adjazenzmatrix[6][3] = 0;
			adjazenzmatrix[6][4] = zufallszahlen[5];
			adjazenzmatrix[6][5] = 0;
			adjazenzmatrix[6][6] = 0;
			adjazenzmatrix[6][7] = 0;
			adjazenzmatrix[6][8] = zufallszahlen[7];
			adjazenzmatrix[6][9] = 0;
			adjazenzmatrix[6][10] = 0;
			adjazenzmatrix[6][11] = 0;
			adjazenzmatrix[7][0] = 0;
			adjazenzmatrix[7][1] = 0;
			adjazenzmatrix[7][2] = 0;
			adjazenzmatrix[7][3] = 0;
			adjazenzmatrix[7][4] = 0;
			adjazenzmatrix[7][5] = zufallszahlen[6];
			adjazenzmatrix[7][6] = 0;
			adjazenzmatrix[7][7] = 0;
			adjazenzmatrix[7][8] = 0;
			adjazenzmatrix[7][9] = zufallszahlen[8];
			adjazenzmatrix[7][10] = 0;
			adjazenzmatrix[7][11] = zufallszahlen[9];
			adjazenzmatrix[8][0] = 0;
			adjazenzmatrix[8][1] = 0;
			adjazenzmatrix[8][2] = 0;
			adjazenzmatrix[8][3] = 0;
			adjazenzmatrix[8][4] = 0;
			adjazenzmatrix[8][5] = 0;
			adjazenzmatrix[8][6] = zufallszahlen[7];
			adjazenzmatrix[8][7] = 0;
			adjazenzmatrix[8][8] = 0;
			adjazenzmatrix[8][9] = zufallszahlen[10];
			adjazenzmatrix[8][10] = 0;
			adjazenzmatrix[8][11] = 0;
			adjazenzmatrix[9][0] = 0;
			adjazenzmatrix[9][1] = 0;
			adjazenzmatrix[9][2] = 0;
			adjazenzmatrix[9][3] = 0;
			adjazenzmatrix[9][4] = 0;
			adjazenzmatrix[9][5] = 0;
			adjazenzmatrix[9][6] = 0;
			adjazenzmatrix[9][7] = zufallszahlen[8];
			adjazenzmatrix[9][8] = zufallszahlen[10];
			adjazenzmatrix[9][9] = 0;
			adjazenzmatrix[9][10] = zufallszahlen[11];
			adjazenzmatrix[9][11] = 0;
			adjazenzmatrix[10][0] = 0;
			adjazenzmatrix[10][1] = 0;
			adjazenzmatrix[10][2] = 0;
			adjazenzmatrix[10][3] = 0;
			adjazenzmatrix[10][4] = 0;
			adjazenzmatrix[10][5] = 0;
			adjazenzmatrix[10][6] = 0;
			adjazenzmatrix[10][7] = 0;
			adjazenzmatrix[10][8] = 0;
			adjazenzmatrix[10][9] = zufallszahlen[11];
			adjazenzmatrix[10][10] = 0;
			adjazenzmatrix[10][11] = zufallszahlen[12];
			adjazenzmatrix[11][0] = 0;
			adjazenzmatrix[11][1] = 0;
			adjazenzmatrix[11][2] = 0;
			adjazenzmatrix[11][3] = 0;
			adjazenzmatrix[11][4] = 0;
			adjazenzmatrix[11][5] = 0;
			adjazenzmatrix[11][6] = 0;
			adjazenzmatrix[11][7] = zufallszahlen[9];
			adjazenzmatrix[11][8] = 0;
			adjazenzmatrix[11][9] = 0;
			adjazenzmatrix[11][10] = zufallszahlen[12];
			adjazenzmatrix[11][11] = 0;

		}

	}

	public static int OptimaleLösung1(Graph g) {
		int visited[] = new int[g.knotenanzahl];
		int min = 999;
		int u = 0;
		int v = 0;
		int total = 0;

		for (int i = 0; i < g.knotenanzahl; i++) {
			visited[i] = 0;
			for (int j = 0; j < g.knotenanzahl; j++) {
				if (g.adjazenzmatrix[i][j] == 0) {
					g.adjazenzmatrix[i][j] = 999;
				}
			}
		}
		visited[0] = 1;

		for (int counter = 0; counter < (g.knotenanzahl - 1); counter++) {
			min = 999;
			for (int i = 0; i < g.knotenanzahl; i++) {
				if (visited[i] == 1)
					for (int j = 0; j < g.knotenanzahl; j++) {
						if (visited[j] == 0) {
							if (min > g.adjazenzmatrix[i][j]) {
								min = g.adjazenzmatrix[i][j];
								u = i;
								v = j;
							}
						}
					}

			}
			visited[v] = 1;
			total = total + min;
			// System.out.println("source"+u+"--->"+v+":"+min);

		}

		return total;
	}

	public static ArrayList<Integer> OptimaleLösung2(Graph g) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int visited[] = new int[g.knotenanzahl];
		int min = 999;
		int u = 0;
		int v = 0;
		int total = 0;

		for (int i = 0; i < g.knotenanzahl; i++) {
			visited[i] = 0;
			for (int j = 0; j < g.knotenanzahl; j++) {
				if (g.adjazenzmatrix[i][j] == 0) {
					g.adjazenzmatrix[i][j] = 999;
				}
			}
		}
		visited[0] = 1;

		for (int counter = 0; counter < (g.knotenanzahl - 1); counter++) {
			min = 999;
			for (int i = 0; i < g.knotenanzahl; i++) {
				if (visited[i] == 1)
					for (int j = 0; j < g.knotenanzahl; j++) {
						if (visited[j] == 0) {
							if (min > g.adjazenzmatrix[i][j]) {
								min = g.adjazenzmatrix[i][j];
								u = i;
								v = j;
							}
						}
					}

			}
			visited[v] = 1;
			total = total + min;
			arr.add(min);
		}

		return arr;
	}

	public static ImageIcon getExtrarohr1() {
		return extrarohr1;
	}

	public static void setExtrarohr1(ImageIcon extrarohr1) {
		Graph.extrarohr1 = extrarohr1;
	}

	private static Image rohrvertikal;
	private static Image rohrhorizontal;
	private static Image extrarohr;
	private static Image lüftungssystem;
	private static Image background;
	private static Image behälter;
	private static ImageIcon extrarohr1;

	public static URL path1 = Graph.class.getResource("/resources/Spiel 3/Rohr3.jpg");
	public static URL path2 = Graph.class.getResource("/resources/Spiel 3/Rohr4.jpg");
	public static URL path3 = Graph.class.getResource("/resources/Spiel 3/Lüftungssystem.png");
	public static URL path4 = Graph.class.getResource("/resources/Spiel 3/weltkarte-umriss.jpg");
	public static URL path5 = Graph.class.getResource("/resources/Spiel 3/Behälter.jpg");

	public static void loadimage() {
		try {
			rohrvertikal = ImageIO.read(path1);
		} catch (IOException e) {

		}

		try {
			rohrvertikal = ImageIO.read(path1);
		} catch (IOException e) {

		}

		try {
			rohrhorizontal = ImageIO.read(path2);
		} catch (IOException e) {

		}

		try {
			extrarohr = ImageIO.read(path2);
			extrarohr = extrarohr.getScaledInstance(SpielbereichMittel1.getCellSize(),
					SpielbereichMittel1.getCellSize(), java.awt.Image.SCALE_REPLICATE);
		} catch (IOException e) {

		}

		extrarohr1 = new ImageIcon(extrarohr);

		try {
			lüftungssystem = ImageIO.read(path3);
		} catch (IOException e) {

		}

		try {
			background = ImageIO.read(path4);
		} catch (IOException e) {

		}

		try {
			behälter = ImageIO.read(path5);
		} catch (IOException e) {

		}

		try {
			rohrvertikal = ImageIO.read(path1);
		} catch (IOException e) {

		}
	}

	public static Image getRohrvertikal() {
		return rohrvertikal;
	}

	public static void setRohrvertikal(Image rohrvertikal) {
		Graph.rohrvertikal = rohrvertikal;
	}

	public static Image getRohrhorizontal() {
		return rohrhorizontal;
	}

	public static void setRohrhorizontal(Image rohrhorizontal) {
		Graph.rohrhorizontal = rohrhorizontal;
	}

	public static Image getExtrarohr() {
		return extrarohr;
	}

	public static void setExtrarohr(Image extrarohr) {
		Graph.extrarohr = extrarohr;
	}

	public static Image getLüftungssystem() {
		return lüftungssystem;
	}

	public static void setLüftungssystem(Image lüftungssystem) {
		Graph.lüftungssystem = lüftungssystem;
	}

	public static Image getBackground() {
		return background;
	}

	public static void setBackground(Image background) {
		Graph.background = background;
	}

	public static Image getBehälter() {
		return behälter;
	}

	public static void setBehälter(Image behälter) {
		Graph.behälter = behälter;
	}

}