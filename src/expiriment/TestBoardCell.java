package expiriment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoardCell {
    int row;
    int col;
    private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
    
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
    
    public void setOccupied(boolean b) {
    	this.isRoom = b;
    }
    public boolean getOccupied() {
    	return isRoom;
    }
    
}
