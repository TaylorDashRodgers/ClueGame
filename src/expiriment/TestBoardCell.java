package expiriment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoardCell {
    int row;
    int col;
    private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
    
    boolean occupied;
    boolean isRoom;
	
	
    public TestBoardCell(int row, int col){
    	this.row = row;
    	this.col = col;

    }
    
    public void addAdjacency(TestBoardCell cell) {
    	adjList.add(cell);
    }
    public Set<TestBoardCell> getAdjList(){
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
    
}
