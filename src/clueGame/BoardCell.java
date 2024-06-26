package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;


public class BoardCell {

	// Variable Declarations 
	private int col, row;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private char initial, secretPassage;
	private boolean isRoom, isRoomCenter, isLabel, isOccupied, isDoor, isUnused;
	private DoorDirection doorDirection;


	// Default and Parameterized constructor 
	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
		secretPassage = '0';
	}
	public BoardCell(){
		row = 0;
		col = 0;
		secretPassage = '0';
	}
	
	
	public void draw(int cellWidth, int cellHeight, Graphics g, Set<BoardCell> targets,boolean human, boolean moved) {
		if(isRoom){
			g.setColor(Color.BLUE);
			g.fillRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
		}
		else if(isDoor){
			g.setColor(Color.DARK_GRAY);
			if(doorDirection == DoorDirection.UP){
				g.fillRect((col*cellWidth),(row*cellHeight), cellWidth, 5);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((col*cellWidth),(row*cellHeight)+5, cellWidth, cellHeight-5);
				g.setColor(Color.BLACK);
				g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
			}
			if(doorDirection == DoorDirection.LEFT){
				g.fillRect((col*cellWidth),(row*cellHeight), 5, cellHeight);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((col*cellWidth)+5,(row*cellHeight), cellWidth-5, cellHeight);
				g.setColor(Color.BLACK);
				g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
			}
			if(doorDirection == DoorDirection.RIGHT){
				g.fillRect((col*cellWidth)+cellWidth-5,(row*cellHeight), 5, cellHeight);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((col*cellWidth),(row*cellHeight), cellWidth-5, cellHeight);
				g.setColor(Color.BLACK);
				g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
			}
			if(doorDirection == DoorDirection.DOWN){
				g.fillRect((col*cellWidth),(row*cellHeight)+cellHeight-5, cellWidth, 5);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight-5);
				g.setColor(Color.BLACK);
				g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
			}
		}
		else if (isUnused){
			g.setColor(Color.BLACK);
			g.fillRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
		}
		else{
			if(human&&!moved) {
				for(BoardCell target : targets) {
//					if(target.isRoom) {
//						for(BoardCell room : Board.getInstance().getRoom(target).getCells()) {
//							g.setColor(Color.cyan);
//							g.fillRect((room.getCol()*cellWidth),(room.getRow()*cellHeight), cellWidth, cellHeight);
//						}
//					}
					g.setColor(Color.cyan);
					g.fillRect((target.getCol()*cellWidth),(target.getRow()*cellHeight), cellWidth, cellHeight);
					g.setColor(Color.BLACK);
					g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
				}
			}
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
				g.setColor(Color.BLACK);
				g.drawRect((col*cellWidth),(row*cellHeight), cellWidth, cellHeight);
		}
			
	}


	// Setters
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}
	public void setOccupied(boolean bool) {
		this.isOccupied = bool;
	}
	public void setIsRoom(boolean bool) {
		this.isRoom = bool;
	}
	public void setIsUnused(boolean bool) {
		this.isUnused = bool;
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
		initial = i;
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
		return initial;
	}


	// Is-ers
	public boolean isOccupied() {
		return isOccupied;
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
	public boolean isUnused(){
		return isUnused;
	}
}
