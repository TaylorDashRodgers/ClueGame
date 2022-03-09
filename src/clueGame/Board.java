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
		//System.out.println(ROWS + "/" + COLS);
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

//		for (int i = 0 ; i < ROWS ; i++ ) {
//			for(int j = 0; j < COLS; j++) {
//				System.out.println(board[i][j].getRow()+","+board[i][j].getCol());
//			}
//		}
		FileReader csv2 = new FileReader(csvConfig);
		Scanner inCsv2 = new Scanner(csv2);
		int row2 = 0;
		while(inCsv2.hasNextLine()){
			int col2 = 0;
			line = inCsv2.nextLine().split(",");
			for (String cellText : line){
				//System.out.println(cellText.charAt(0));
				board[row2][col2].setInitial(cellText.charAt(0));
				if(cellText.charAt(0)!='W') {
					board[row2][col2].setIsRoom(true);
				}
				if(cellText.length() != 1) {
					if(cellText.charAt(1)=='*') {
						board[row2][col2].setRoomCenter(true);
					}
					if(cellText.charAt(1)=='#') {
						board[row2][col2].setRoomLabel(true);
					}
					if(cellText.charAt(1)=='<' || cellText.charAt(1)=='^' || cellText.charAt(1)=='>' || cellText.charAt(1)=='v') {
						System.out.println("DOOOR");
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
					else {
						board[row2][col2].setSecretPassage(cellText.charAt(1));
					}
				}
				col2 =+1;
			}
			row2=+1;
		}
		inCsv2.close();
	}

	public void initialize()  {
		try {
			loadSetupConfig();
		}catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadLayoutConfig();
		}catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void setConfigFiles(String csv, String txt){
		this.csvConfig = csv;
		this.txtConfig = txt;
	}




	public BoardCell getCell(int row, int col) {
		return board[row][col];
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