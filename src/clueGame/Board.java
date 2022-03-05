package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

	private BoardCell[][] board;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
    private final static int COLS = 24;
    private final static int ROWS = 24;

	       /*
       * variable and methods used for singleton pattern
       */
       private static Board theInstance = new Board();
       // constructor is private to ensure only one can be created
      
       // this method returns the only Board
       public static Board getInstance() {
              return theInstance;
       }
       /*
        * initialize the board (since we are using singleton pattern)
        */
       public void initialize()
       {
       }
	
	private Board(){
		board = new BoardCell[COLS][ROWS];
		for (int i = 0 ; i < COLS ; i++ ) {
			for(int j = 0; j < ROWS; j++) {
				BoardCell temp = new BoardCell(i,j);
				board[i][j] = temp;
			}
		}
		for (int i = 0 ; i < COLS ; i++ ) {
			for(int j = 0; j < ROWS; j++) {
				
				if((i-1)>=0) {
					board[i][j].addAdjacency(board[i-1][j]);
				}
				if((j-1)>=0) {
					board[i][j].addAdjacency(board[i][j-1]);
				}
				if(j<(ROWS-1)) {
					board[i][j].addAdjacency(board[i][j+1]);
				}
				if(i<(COLS-1)) {
					board[i][j].addAdjacency(board[i+1][j]);
				}	
				
			}
		}	
	}


	
	public BoardCell getCell(int x, int y) {
		return board[x][y];
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public void calcTargets(BoardCell startCell,int pathLength) {
		visited.add(startCell);
		for(BoardCell cell : startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1 || cell.isRoom) {
					if(!cell.occupied) {
						targets.add(cell);
					}	
				}
				else {
					if(!cell.occupied) {
						calcTargets(cell,pathLength-1);
					}
				}
			}
			visited.remove(cell);
		}
		visited.remove(startCell);

	}
	public Room getRoom(char c) {
		Room temp = new Room();
		return temp;
	}
	
	public int getNumRows() {
		return ROWS;
	}
	
	public int getNumColumns() {
		return COLS;
	}
	
	public Room getRoom(BoardCell cell) {
		Room temp = new Room();
		return temp;
	}
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	
	
}
