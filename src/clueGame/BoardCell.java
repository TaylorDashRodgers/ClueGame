package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell {
	
	int col;
    int row;
    private Set<BoardCell> adjList = new HashSet<BoardCell>();
    
    boolean occupied;
    boolean isRoom;
	
	
    public BoardCell(int row, int col){
    	this.col = row;
    	this.row = col;

    }
    
    public void addAdjacency(BoardCell cell) {
    	adjList.add(cell);
    }
    public Set<BoardCell> getAdjList(){
    	return adjList;
    }
    
    public void setOccupied(boolean bool) {
    	this.occupied = bool;
    }
    public boolean getOccupied() {
    	return occupied;
    }
    public void setIsRoom(boolean bool) {
    	this.isRoom = bool;
    }
    public boolean getIsRoom() {
    	return isRoom;
    }
    public int getCol() {
    	return col;
    }
    public int getRow() {
    	return row;
    }

}
