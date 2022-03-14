package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell {

	// Variable Declarations 
	private int col, row;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private char Initial, secretPassage;
	private boolean isRoom, isRoomCenter, isLabel, occupied, isDoor;
	private DoorDirection doorDirection;


	// Default and Parameterized constructor 
	public BoardCell(int row, int col){
		this.col = row;
		this.row = col;
	}
	public BoardCell(){
		row = 0;
		col = 0;
	}


	// Setters
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}
	public void setOccupied(boolean bool) {
		this.occupied = bool;
	}
	public void setIsRoom(boolean bool) {
		this.isRoom = bool;
	}
	public void setIsDoorway(boolean bool) {
		isDoor = bool;
	}
	public void setSecretPassage(char s) {
		secretPassage = s;
	}
	public void setRoomLabel (boolean bool) {
		isLabel = bool;
	}
	public void setInitial(char i) {
		Initial = i;
	}
	public void setRoomCenter(boolean bool) {
		isRoomCenter = bool;
	}


	// adds cell to adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}


	// Getters
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public char getInitial() {
		return Initial;
	}


	// Is-ers
	public boolean isOccupied() {
		return occupied;
	}
	public boolean isRoom() {
		return isRoom;
	}
	public boolean isDoorway() {
		return isDoor;
	}	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	public boolean isLabel() {
		return isLabel;
	}
}
