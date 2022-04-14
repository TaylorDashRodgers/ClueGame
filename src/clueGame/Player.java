package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

abstract public class Player {
	private String name, color;
	private int row, column;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private boolean isHuman;
	protected ArrayList<Card> seen = new ArrayList<Card>(); 

	public void draw(int cellWidth, int cellHeight, Graphics g){
		g.setColor(Color.black);
		g.fillOval(column*cellWidth,row*cellHeight, cellWidth, cellHeight);
	}
	//Getters
	public boolean isHuman() {
		return isHuman;
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
	public String getName() {
		return name;
	}
	public String getColor() {
		return color;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	//Setters
	public void setColumn(int column) {
		this.column = column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	//Constructors
	public Player(String name, String color, int row, int column, boolean isHuman) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		this.isHuman = isHuman;
	}
	public Player() {
		super();
		this.name = "default";
		this.color = "default";
		this.row = 0;
		this.column = 0;
		this.isHuman = false;
	}

	//Methods
	public Card disproveSuggestion(Suggestion s) {
		for(Card card : hand){
			if(s.getPerson().Equals(card) || s.getRoom().Equals(card) || s.getWeapon().Equals(card)){
				return card;
			}
		}
		return null;
	}

	public void updtateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card card) {
		seen.add(card);
	}

	public ArrayList<Card> getSeen(){
		return seen;
	}
	
}
