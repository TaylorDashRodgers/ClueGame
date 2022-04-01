package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board {
	// Declares all of our variables we will be using.
	private BoardCell[][] board;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private int columns, rows;
	private String csvConfig, txtConfig;
	private ArrayList<String> boardCells = new ArrayList<String>();
	private Map<Character,Room> roomsMap = new HashMap<Character,Room>();
	//I think we need these not sure
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution solution = new Solution();
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
		columns = line.length;
		rows = row;
		inCsv.close();
		board = new BoardCell[rows][columns];
		for (int i = 0 ; i < rows ; i++ ) {
			for(int j = 0; j < columns; j++) {
				BoardCell temp = new BoardCell(i,j);
				board[i][j] = temp;
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
					 else if (cellText.charAt(1)!='#' && cellText.charAt(1)!='*'){
						board[row2][col2].setSecretPassage(cellText.charAt(1));
					}
				}
				col2 = col2 + 1;
			}
			row2 = row2 + 1;
		}
		inCsv2.close();
		
		for (int i = 0 ; i < rows ; i++ ) {
			for(int j = 0; j < columns; j++) {
				
				// if secret passage, add Centercell linked with secret passage to room center's adj list
				if(board[i][j].getSecretPassage()!= '0'){
					getRoom(board[i][j].getInitial()).getCenterCell().addAdjacency(getRoom(board[i][j].getSecretPassage()).getCenterCell());
				} 
				// if currently a walkway only add moves that are also walkways
				if(board[i][j].getInitial()== 'W'){
					if((i-1)>=0 && board[i-1][j].getInitial()== 'W') {
						board[i][j].addAdjacency(board[i-1][j]);
					}
					if((j-1)>=0 && board[i][j-1].getInitial()== 'W') {
						board[i][j].addAdjacency(board[i][j-1]);
					}
					if(j<(columns-1) && board[i][j+1].getInitial()== 'W') {
						board[i][j].addAdjacency(board[i][j+1]);
					}
					if(i<(rows-1) && board[i+1][j].getInitial()== 'W') {
						board[i][j].addAdjacency(board[i+1][j]);
					}
				}

				if(board[i][j].isDoorway()) {
					// if door is facing left
					if(board[i][j].getDoorDirection() == DoorDirection.LEFT) {
						if((i-1)>=0 && board[i-1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i-1][j]);
						}
						
						board[i][j].addAdjacency(getRoom(board[i][j-1].getInitial()).getCenterCell());
						getRoom(board[i][j-1].getInitial()).getCenterCell().addAdjacency(board[i][j]);

						if(j<(columns-1) && board[i][j+1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j+1]);
						}
						if(i<(rows-1) && board[i+1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i+1][j]);
						}
					}

					if(board[i][j].getDoorDirection() == DoorDirection.UP) {
						
						board[i][j].addAdjacency(getRoom(board[i-1][j].getInitial()).getCenterCell());
						getRoom(board[i-1][j].getInitial()).getCenterCell().addAdjacency(board[i][j]);

						if((j-1)>=0 && board[i][j-1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j-1]);
						}
						if(j<(columns-1) && board[i][j+1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j+1]);
						}
						if(i<(rows-1) && board[i+1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i+1][j]);
						}
					}

					if(board[i][j].getDoorDirection() == DoorDirection.RIGHT) {

						if((i-1)>=0 && board[i-1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i-1][j]);
						}

						if((j-1)>=0 && board[i][j-1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j-1]);
						}

						board[i][j].addAdjacency(getRoom(board[i][j+1].getInitial()).getCenterCell());
						getRoom(board[i][j+1].getInitial()).getCenterCell().addAdjacency(board[i][j]);

						if(i<(rows-1) && board[i+1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i+1][j]);
						}
					}

					if(board[i][j].getDoorDirection() == DoorDirection.DOWN) {

						if((i-1)>=0 && board[i-1][j].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i-1][j]);
						}

						if((j-1)>=0 && board[i][j-1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j-1]);
						}

						if(j<(columns-1) && board[i][j+1].getInitial()== 'W') {
							board[i][j].addAdjacency(board[i][j+1]);
						}

						board[i][j].addAdjacency(getRoom(board[i+1][j].getInitial()).getCenterCell());
						getRoom(board[i+1][j].getInitial()).getCenterCell().addAdjacency(board[i][j]);
					}
				}
			}
		}
	}
	
	public void deal() {
		//random three for solution
		ArrayList<Card> tempDeck = new ArrayList<Card>(deck);
		int tempDeckSize = tempDeck.size();
		Random rand = new Random();
		while(tempDeck.size() != tempDeckSize - 3){
			int randIndex = rand.nextInt(tempDeck.size());
			Card randomSolutionCard = tempDeck.get(randIndex);
			if(tempDeck.size() == tempDeckSize){
				if(randomSolutionCard.getCardType() == CardType.PERSON){
					solution.setPerson(randomSolutionCard);
					tempDeck.remove(randIndex);
				}
			}
			if(tempDeck.size() == tempDeckSize - 1){
				if(randomSolutionCard.getCardType() == CardType.ROOM){
					solution.setRoom(randomSolutionCard);
					tempDeck.remove(randIndex);
				}
			}
			if(tempDeck.size() == tempDeckSize - 2){
				if(randomSolutionCard.getCardType() == CardType.WEAPON){
					solution.setWeapon(randomSolutionCard);
					tempDeck.remove(randIndex);
				}
			}
		}
		//random for rest of players
		while( tempDeck.size() !=0) {
			for(int i = 0; i< players.size(); i++) {
				if(tempDeck.size()== 0) {
					break;
				}
				int randIndex = rand.nextInt(tempDeck.size());
				players.get(i).updtateHand(tempDeck.get(randIndex));
				tempDeck.remove(randIndex);
			}
		}
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Solution getSolution() {
		return solution;
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
		
		ComputerPlayer player1 = new ComputerPlayer();
		players.add(player1);
		ComputerPlayer player2 = new ComputerPlayer();
		players.add(player2);
		ComputerPlayer player3 = new ComputerPlayer("default", "default", 5, 20, false);
		players.add(player3);
		ComputerPlayer player4 = new ComputerPlayer();
		players.add(player4);
		ComputerPlayer player5 = new ComputerPlayer();
		players.add(player5);
		HumanPlayer player6 = new HumanPlayer("default", "default", 0, 0, true);
		players.add(player6);
		
		deal();
		
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

	public void calcTargets(BoardCell startCell,int pathLength){
		visited.clear();
		targets.clear();
		calcTarget(startCell,pathLength);
	}
	// Recursively looks at adjList, path length, and visited list to determine the possible cells to move to
	private void calcTarget(BoardCell startCell,int pathLength) {
		visited.add(startCell);
		for(BoardCell cell : startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					if(!cell.isOccupied()|| cell.isRoom()) {
						targets.add(cell);
					}	
				}
				else if(cell.isRoom()){
					targets.add(cell);
				}
				else {
					if(!cell.isOccupied()) {
						calcTarget(cell,pathLength-1);
					}
				}
				visited.remove(cell);
			}
		}
	}

	public Room getRoom(char c) {
		return roomsMap.get(c);
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumColumns() {
		return columns;
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
			line = fullLine.split(", ");
			if(!line[0].equals("Room") && !line[0].equals("Space") && !line[0].equals("Person") && !line[0].equals("Weapon")){
				throw new BadConfigFormatException("Not a space or room.");
			}
			
			if(line[0].equals("Room")) {
				roomsMap.put(line[2].charAt(0),new Room(line[1],line[2].charAt(0)));
				Card card = new Card(line[1],CardType.ROOM);
				deck.add(card);
			}
			if(line[0].equals("Person")) {
				Card card = new Card(line[1], CardType.PERSON);
				deck.add(card);
			}
			if(line[0].equals("Weapon")) {
				Card card = new Card(line[1], CardType.WEAPON);
				deck.add(card);
			}
			
		}
		inTxt.close();
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i,j).getAdjList();
	}
}