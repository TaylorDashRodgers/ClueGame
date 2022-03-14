package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	// Declares all of our variables we will be using.
	private BoardCell[][] board;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private int COLS, ROWS;
	private String csvConfig, txtConfig;
	private ArrayList<String> boardCells = new ArrayList<String>();
	private Map<Character,Room> roomsMap = new HashMap<Character,Room>();
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
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		// Reads in the file for the first time to get the cols and rows sizes.
		FileReader csv = new FileReader(csvConfig);
		Scanner inCsv = new Scanner(csv);
		String[] line = null;
		int row = 0;
		int col = 0 ;
		while(inCsv.hasNextLine()){
			line = inCsv.nextLine().split(",");
			row = row + 1;
		}

		//initialize board
		COLS = line.length;
		ROWS = row;
		inCsv.close();
		board = new BoardCell[ROWS][COLS];
		for (int i = 0 ; i < ROWS ; i++ ) {
			for(int j = 0; j < COLS; j++) {
				BoardCell temp = new BoardCell(i,j);
				board[i][j] = temp;
			}
		}

		for (int i = 0 ; i < ROWS ; i++ ) {
			for(int j = 0; j < COLS; j++) { 
				if((i-1)>=0) {
					board[i][j].addAdjacency(board[i-1][j]);
				}
				if((j-1)>=0) {
					board[i][j].addAdjacency(board[i][j-1]);
				}
				if(j<(COLS-1)) {
					board[i][j].addAdjacency(board[i][j+1]);
				}
				if(i<(ROWS-1)) {
					board[i][j].addAdjacency(board[i+1][j]);
				}	

			}
		}

		// Reads through the file the second time now that we have rows and cols.
		FileReader csv2 = new FileReader(csvConfig);
		Scanner inCsv2 = new Scanner(csv2);
		int row2 = 0;
		while(inCsv2.hasNextLine()){
			int col2 = 0;
			// Reads in individual lines of csv and splits them by commas.
			line = inCsv2.nextLine().split(",");
			for (String cellText : line){
				board[row2][col2].setInitial(cellText.charAt(0));
				// Checks for non walkways and assigns them as rooms.
				if(cellText.charAt(0)!='W') {
					board[row2][col2].setIsRoom(true);
				}
				// Further check of all cells that are special meaning more than one char long.
				if(cellText.length() != 1) {
					// Check for room center.
					if(cellText.charAt(1)=='*') {
						board[row2][col2].setRoomCenter(true);
						roomsMap.get(cellText.charAt(0)).setCenterCell(board[row2][col2]);
					}
					// Check for room label.
					if(cellText.charAt(1)=='#') {
						board[row2][col2].setRoomLabel(true);
						roomsMap.get(cellText.charAt(0)).setLabelCell(board[row2][col2]);
					}
					// Check for door and door direction.
					if(cellText.charAt(1)=='<' || cellText.charAt(1)=='^' || cellText.charAt(1)=='>' || cellText.charAt(1)=='v') {
						board[row2][col2].setIsDoorway(true);
						if(cellText.charAt(1)=='<' ) {
							board[row2][col2].setDoorDirection(DoorDirection.LEFT);
						}
						if( cellText.charAt(1)=='^' ) {
							board[row2][col2].setDoorDirection(DoorDirection.UP);
						}
						if( cellText.charAt(1)=='>' ) {
							board[row2][col2].setDoorDirection(DoorDirection.RIGHT);
						}
						if( cellText.charAt(1)=='v') {
							board[row2][col2].setDoorDirection(DoorDirection.DOWN);
						}
					}
					// Last check meaning it is a secret passage.
					else {
						board[row2][col2].setSecretPassage(cellText.charAt(1));
					}
				}
				col2 = col2 + 1;
			}
			row2 = row2 + 1;
		}
		inCsv2.close();
	}

	public void initialize()  {
		try {
			loadSetupConfig();
		}catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			loadLayoutConfig();
		}catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Sets our Config Files.
	public void setConfigFiles(String csv, String txt){
		this.csvConfig = "data/" + csv;
		this.txtConfig = "data/" + txt;
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	// Recursively looks at adjList, path length, and visited list to determine the possible cells to move to
	public void calcTargets(BoardCell startCell,int pathLength) {
		visited.add(startCell);
		for(BoardCell cell : startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1 || cell.isRoom()) {
					if(!cell.isOccupied()) {
						targets.add(cell);
					}	
				}
				else {
					if(!cell.isOccupied()) {
						calcTargets(cell,pathLength-1);
					}
				}
			}
			visited.remove(cell);
		}
		visited.remove(startCell);
	}

	public Room getRoom(char c) {
		return roomsMap.get(c);
	}

	public int getNumRows() {
		return ROWS;
	}

	public int getNumColumns() {
		return COLS;
	}

	public Room getRoom(BoardCell cell) {
		return roomsMap.get(cell.getInitial());
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		// Reads in txt
		FileReader txt = new FileReader(txtConfig);
		Scanner inTxt = new Scanner(txt);
		String[] line;
		while(inTxt.hasNextLine()) {
			String fullLine = inTxt.nextLine();
			// Checks if the line is a comment and skips if it is.
			if(fullLine.charAt(0)=='/'){
				continue;
			}
			// Splits the line up into useful chunks.
			line = fullLine.split(",");
			if(!line[0].equals("Room") && !line[0].equals("Space")){
				throw new BadConfigFormatException("Not a space or room.");
			}
			roomsMap.put(line[2].charAt(1),new Room(line[1].substring(1),line[2].charAt(1)));
		}
		inTxt.close();
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i,j).getAdjList();
	}
}