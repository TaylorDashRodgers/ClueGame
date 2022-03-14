package clueGame;

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
