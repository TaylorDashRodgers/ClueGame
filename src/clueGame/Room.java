package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private Set<BoardCell> roomCells = new HashSet<BoardCell>();
    private String symbol;
    private BoardCell center;
    
    public String getSymbol() {
    	return symbol;
    }
    public void setSymbol(String symbol) {
    	this.symbol = symbol;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public BoardCell getCenterCell() {
    	return center;
    }
    
    public String getName() {
    	return name;
    }


}
