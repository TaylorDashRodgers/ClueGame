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
	private Map<String,String> rooms = new HashMap();
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
       	
     
       	public void initialize() {

       		FileReader csv = null;
			try {
				csv = new FileReader(csvConfig);
			} catch (FileNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			}
       		Scanner inCsv = new Scanner(csv);
       		String[] line = null;
       		int row = 0;
       		int col = 0 ;
       		while(inCsv.hasNextLine()){
       			line = inCsv.nextLine().split(",");
       			row =+ 1;
       		}
       		//initialize board
       		COLS = line.length;
       		ROWS = row;

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

			int y = 0;
			while(inCsv.hasNextLine()){
				int x = 0;
				line = inCsv.nextLine().split(",");
				for (String cellText : line){
					board[x][y].setInitial(cellText.charAt(0));
					if(cellText.length() != 1) {
						if(cellText.charAt(1)=='*') {
							board[x][y].setRoomCenter(true);
						}
						if(cellText.charAt(1)=='#') {
							board[x][y].setRoomLabel(true);
						}
						if(cellText.charAt(1)=='<' || cellText.charAt(1)=='^' || cellText.charAt(1)=='>' || cellText.charAt(1)=='v') {
							board[x][y].setIsDoorway(true);
						}
					}
				}
			}
       }

	   public void setConfigFiles(String csv, String txt){
		   this.csvConfig = csv;
		   System.out.println(csv);
		   this.txtConfig = txt;
		   System.out.println(txt);
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

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		//read in txt
	   	FileReader txt = null;
		txt = new FileReader(txtConfig);
		Scanner inTxt = new Scanner(txt);
		String[] line;
		while(inTxt.hasNextLine()) {	
		line = inTxt.nextLine().split(",");
		rooms.put(line[2],line[1]);
		}
		Set<String> keySet = rooms.keySet();
		System.out.println();
		for (String key : keySet) {
			System.out.println(key + " , " + rooms.get(key));
		}
	}
}
