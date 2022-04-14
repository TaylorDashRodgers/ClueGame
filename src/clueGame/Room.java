package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public class Room {
	
	
	// Variable Declarations
    private String name;
    private Set<BoardCell> roomCells = new HashSet<BoardCell>();
    private char letter;
    private BoardCell centerCell, labelCell;
 
    
   // Parameterized Constructor
    public Room(String name, char c) {
    	this.letter = c;
    	this.name = name;
    }

    public void draw(int cellWidth, int cellHeight, Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        g2d.setColor(Color.BLACK);
        g2d.drawString(name,(labelCell.getCol()*cellWidth),(labelCell.getRow()*cellHeight));
    }
    
    // Getters
    public char getSymbol() {
    	return letter;
    }
    public BoardCell getCenterCell() {
    	return centerCell;
    }
    public BoardCell getLabelCell() {
    	return labelCell;
    }
    public String getName() {
    	return name;
    }
    
    // Setters
    public void setSymbol(char c) {
    	this.letter = c;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public void setCenterCell(BoardCell center){
        this.centerCell = center;
    }
    public void setLabelCell(BoardCell label){
        this.labelCell = label;
    }
}
