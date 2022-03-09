package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell {
	
	int col;
    int row;
    private Set<BoardCell> adjList = new HashSet<BoardCell>();
    boolean isDoor;
    private char Initial;
    private char secretPassage;
    boolean occupied;
    boolean isRoom;
    boolean isLabel;
    DoorDirection doorDirection;
	
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	
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
    public boolean isOccupied() {
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
    
   public void setIsDoorway(boolean bool) {
	   isDoor = bool;
   }
   public boolean isDoorway() {
	   return isDoor;
   }
   
   public void setRoomCenter(boolean bool) {
	   isRoom = bool;
   }
   public boolean isRoomCenter() {
	   return isRoom;
   }
   
   public void setRoomLabel (boolean bool) {
	   isLabel = bool;
   }
   public boolean isLabel() {
	   return isLabel;
   }
   
 
   
   public void setSecretPassage(char s) {
	   secretPassage = s;
   }
   public char getSecretPassage() {
	   return secretPassage;
   }
   
   public void setInitial(char i) {
	   Initial = i;
   }
   public char getInitial() {
	   return Initial;
   }

}
