package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private Set<BoardCell> roomCells = new HashSet<BoardCell>();
    private String symbol;
    private BoardCell centerCell;
    private BoardCell labelCell;
    
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
    	return centerCell;
    }
    public void setCenterCell(BoardCell center){
        this.centerCell = center;
    }
    public BoardCell getLabelCell() {
    	return labelCell;
    }
    public void setLabelCell(BoardCell label){
        this.centerCell = label;
    }
    
    public String getName() {
    	return name;
    }


}
