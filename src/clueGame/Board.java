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

	private BoardCell[][] board;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private int COLS;
	private int ROWS;
	private String csvConfig;
	private String txtConfig;
	private ArrayList<String> boardCells = new ArrayList();
	private Map<Character,Room> roomsMap = new HashMap();
	private Map<Integer,Integer> test = new HashMap();
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

		int y = 0;
		while(inCsv.hasNextLine()){
			int x = 0;
			line = inCsv.nextLine().split(",");
			System.out.println("hi");
			for (String cellText : line){
				board[y][x].setInitial(cellText.charAt(0));
				if(cellText.length() != 1) {
					if(cellText.charAt(1)=='*') {
						board[y][x].setRoomCenter(true);
					}
					if(cellText.charAt(1)=='#') {
						board[y][x].setRoomLabel(true);
					}
					if(cellText.charAt(1)=='<' || cellText.charAt(1)=='^' || cellText.charAt(1)=='>' || cellText.charAt(1)=='v') {
						board[y][x].setIsDoorway(true);
						if(cellText.charAt(1)=='<' ) {
							board[y][x].setDoorDirection(DoorDirection.LEFT);
						}
						if( cellText.charAt(1)=='^' ) {
							board[y][x].setDoorDirection(DoorDirection.UP);
						}
						if( cellText.charAt(1)=='>' ) {
							board[y][x].setDoorDirection(DoorDirection.RIGHT);
						}
						if( cellText.charAt(1)=='v') {
							board[y][x].setDoorDirection(DoorDirection.DOWN);
						}
					}
					else {
						board[y][x].setSecretPassage(cellText.charAt(1));
					}
				}
				x =+1;
			}
			y=+1;
		}
		inCsv.close();
	}

	public void initialize() throws FileNotFoundException {
		try {
			loadSetupConfig();
		}catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadLayoutConfig();
		}catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setConfigFiles(String csv, String txt){
		this.csvConfig = csv;
		this.txtConfig = txt;
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
		//read in txt
		FileReader txt = new FileReader(txtConfig);
		Scanner inTxt = new Scanner(txt);
		String[] line;
		while(inTxt.hasNextLine()) {
			String fullLine = inTxt.nextLine();
			// Checks if the line is a comment and skips if it is.
			if(fullLine.charAt(0)=='/'){
				continue;
			}
			// splits the line up into useful chunks.
			line = fullLine.split(",");
			if(line[0] != "Room" || line[0] != "Space"){
				throw new BadConfigFormatException("Not a space or room.");
			}
			roomsMap.put(line[2].charAt(0),new Room(line[1],line[2].charAt(0)));
		}
		inTxt.close();
	}


	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i,j).getAdjList();
	}
}