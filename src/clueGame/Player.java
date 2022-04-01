package clueGame;

import java.util.ArrayList;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	void updtateHand(Card card) {
		hand.add(card);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	public Player() {
		super();
		this.name = "default";
		this.color = "default";
		this.row = 0;
		this.column = 0;
	}
	
}
