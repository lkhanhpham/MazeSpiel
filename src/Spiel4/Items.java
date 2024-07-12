package Spiel4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Items {

	private int row;
	private int col;
	private int startPositionRow;
	private int startPositionCol;
	private int id;
	private static int idCounter;
	private String type;
	// Attributes for Earth
	private int xCoor;
	private int yCoor;

	private boolean onShip = false;

	public Items(int row, int col, String type) {
		this.col = col;
		this.row = row;
		this.startPositionCol = col;
		this.startPositionRow = row;
		idCounter++;
		id = idCounter;
		this.type = type;

	}

	public boolean isOnShip() {
		return onShip;
	}

	public void setOnShip(boolean onShip) {
		this.onShip = onShip;
	}

	public int getStartPositionRow() {
		return startPositionRow;
	}

	public void setStartPositionRow(int startPositionRow) {
		this.startPositionRow = startPositionRow;
	}

	public int getStartPositionCol() {
		return startPositionCol;
	}

	public void setStartPositionCol(int startPositionCol) {
		this.startPositionCol = startPositionCol;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
