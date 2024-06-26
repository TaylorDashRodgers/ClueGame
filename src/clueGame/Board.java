package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//import clueGame.GameControlPanel.AccusationButtonListener;

public class Board extends JPanel {
	// Declares all of our variables we will be using.
	private BoardCell[][] board;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private int columns, rows, cellWidth, cellHeight;
	private int currentPlayer = 5;
	private int roll;
	private String csvConfig, txtConfig;
	private ArrayList<String> boardCells = new ArrayList<String>();
	private Map<Character,Room> roomsMap = new HashMap<Character,Room>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> weaponCards = new ArrayList<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution solution = new Solution();
	private JComboBox<String> weaponS, personS, weaponA, personA, roomA;
	private JFrame suggestion, accusation;
	//File names for the sprites
	private String[] sprites = {"im","cap","hulk","bp","nat","wanda"};
	//used to track human players turn 
	private boolean moved = false;
	private boolean turnOver = false;


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		cellWidth = getWidth() / columns;
		cellHeight = getHeight() / rows;
		for(BoardCell[] cells: board){
			for(BoardCell cell: cells){
				cell.draw(cellWidth, cellHeight, g,targets, players.get(currentPlayer).isHuman(),moved);
			}
		}
		for(Map.Entry<Character,Room> entry : roomsMap.entrySet()){
			entry.getValue().draw(cellWidth, cellHeight, g);
		}
		int counter = 0;
		for(Player p: players){
			p.draw(cellWidth,cellHeight,g,sprites[counter]);
			counter = counter + 1;
		}
		
	}

	private class MouseClick implements MouseListener {
		// Handles our other mouse events that we don't need at the moment all we are using is mouse clicked.
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mouseClicked(MouseEvent event){
			for(BoardCell target: targets) {
				// Loops through our set of targets and checks if it is a human or not.
				if(players.get(currentPlayer).isHuman()){
					// Once human is confirmed it checks to see if the mouse clicked is on a correct target and if so it moves the character to that new cell.
					if(event.getPoint().getX() > (target.getCol() * cellWidth) &&  event.getPoint().getX() < ((target.getCol() * cellWidth)+ cellWidth) 
							&& event.getPoint().getY() > (target.getRow() *cellHeight) && event.getPoint().getY() < ((target.getRow() *cellHeight)+cellHeight) && !moved){
						players.get(currentPlayer).setColumn(target.getCol());
						players.get(currentPlayer).setRow(target.getRow());
						moved = true;
						repaint();
						// If the player is moved to a room it handles the suggestion for the player to make at this time. 
						if(target.isRoom()){
							UIManager UI=new UIManager();
					    	UI.put("OptionPane.background", Color.white);
					    	UI.put("Panel.background",  Color.white);
							suggestion = new JFrame("Make a Suggestion");
							JPanel suggestionPanel = new JPanel(new GridLayout(4,2));
							suggestion.setSize(300,150);
							suggestion.setLocationRelativeTo(null);
							suggestion.setVisible(true);
							JButton submit = new JButton("Submit");
							SuggestionButtonListener suggestionListener = new SuggestionButtonListener();
							submit.addActionListener(suggestionListener);
							JButton cancel = new JButton("Cancel");
							JLabel roomLabel = new JLabel("Current room");
							JLabel roomS = new JLabel(getRoom(getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn())).getName());
							JLabel weaponLabel = new JLabel("Weapon");
							JLabel personLabel = new JLabel("Person");
							weaponS = new JComboBox<String>();
							personS = new JComboBox<String>();
							for(Player player : players) {
								personS.addItem(player.getName());
							}
							for(Card wep : weaponCards) {
								weaponS.addItem(wep.getCardName());
							}
							suggestionPanel.add(roomLabel);
							suggestionPanel.add(roomS);
							suggestionPanel.add(personLabel);
							suggestionPanel.add(personS);
							suggestionPanel.add(weaponLabel);
							suggestionPanel.add(weaponS);
							suggestionPanel.add(submit);
							suggestionPanel.add(cancel);
							suggestion.add(suggestionPanel);
							turnOver = true;
						}
						else {
							turnOver = true;
						}
					}
				}
			}
		}
	}
	
	private class SuggestionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			getPlayer(personS.getSelectedItem()).setColumn(players.get(currentPlayer).getColumn());
			getPlayer(personS.getSelectedItem()).setRow(players.get(currentPlayer).getRow());
			repaint();
			
			Suggestion s =  new Suggestion();
			s.setRoom(getCard(getRoom(getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn())).getName()));
			s.setPerson(getCard(personS.getSelectedItem()));
			s.setWeapon(getCard(weaponS.getSelectedItem()));
			if(handleSuggestion(s)!= null) {
				players.get(currentPlayer).updateSeen(handleSuggestion(s));
				GameCards.getInstance().addSeen(handleSuggestion(s));
				String suggestionString = getRoom(getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn())).getName() + ", " + personS.getSelectedItem() + ", " + weaponS.getSelectedItem();
				GameControlPanel.getInstance().setGuess(suggestionString);
				GameControlPanel.getInstance().setGuessResult(handleSuggestion(s).getCardName());
			}
			else {
				GameControlPanel.getInstance().setGuessResult("Nothing");
			}
			suggestion.setVisible(false);
		}
	}
	
	public Player getPlayer(Object object) {
		for(Player player : players) {
			if(player.getName() == object) {
				return player;
			}
		}
		return null;
	}

	public BoardCell[][] getBoard(){
		return board;
	}
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
	
	public void nextTurn() {
		// If the turn is not over it will display an error message.
		if(!turnOver) {
			ImageIcon icon = new ImageIcon("data/avengers.png");
    		Image image = icon.getImage();
    		Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
    		icon = new ImageIcon(newimg);
			int result = JOptionPane.showConfirmDialog(null, "Finish your turn!", "Finish!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
		}
		else {
			// Handles all of the cases for moving to the next player including when it is the last player the current player will then be the first player again.
			if(currentPlayer == 5) {
				currentPlayer = 0;
			}
			else {
				currentPlayer = currentPlayer +1;
			}
			// Creates a random number generate so our roll of the dice is random.
			Random rand = new Random();
			roll = rand.nextInt(6)+1;
			calcTargets(getCell(players.get(currentPlayer).getRow(),players.get(currentPlayer).getColumn()),roll);
			GameControlPanel.getInstance().setTurn(players.get(currentPlayer), roll);
			if(players.get(currentPlayer).isHuman()) {
				moved = false;
				repaint();
			}
			else {
				//Selects a random cell from targets unless a room is available 
				BoardCell[] targetsArray = targets.toArray(new BoardCell[targets.size()]);
				int randNum = rand.nextInt(targets.size());
				BoardCell randTarget = targetsArray[randNum];
				players.get(currentPlayer).setColumn(randTarget.getCol());
				players.get(currentPlayer).setRow(randTarget.getRow());
				for(BoardCell target : targets) {
					// Computers prioritize rooms.
					if(target.isRoom()) {
						players.get(currentPlayer).setColumn(target.getCol());
						players.get(currentPlayer).setRow(target.getRow());
					}
				}
				repaint();
				//Handle Accusations and Suggestions from computer
				
				if(getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn()).isRoom()){
					Suggestion s = new Suggestion();
					for(Card card : weaponCards) {
						if(!players.get(currentPlayer).getHand().contains(card)&&!players.get(currentPlayer).getSeen().contains(card)) {
							s.setWeapon(card);
							break;
						}
					}
					for(Player player : players) {
						if(!players.get(currentPlayer).getSeen().contains(getCard(player.getName()))&&!players.get(currentPlayer).getHand().contains(getCard(player.getName()))) {
							s.setPerson(getCard(player.getName()));
							player.setColumn(players.get(currentPlayer).getColumn());
							player.setRow(players.get(currentPlayer).getRow());
							repaint();
							break;
						}
					}
					s.setRoom(getCard(getRoom(getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn())).getName()));

					players.get(currentPlayer).updateSeen(handleSuggestion(s));
					
					String suggestionString = s.getRoom().getCardName() + ", " + s.getPerson().getCardName() + ", " + s.getWeapon().getCardName();
					GameControlPanel.getInstance().setGuess(suggestionString);
					if(handleSuggestion(s)!= null) {
						GameControlPanel.getInstance().setGuessResult(handleSuggestion(s).getCardName());
					}
					else {
						GameControlPanel.getInstance().setGuessResult("Nothing");
					}


				}
			}
			if(players.get(currentPlayer).isHuman()) {
				turnOver = false;
			}
		}
	}

	public void accusation() {
		UIManager UI=new UIManager();
		UI.put("OptionPane.background", Color.white);
		UI.put("Panel.background",  Color.white);
		JFrame accusation = new JFrame("Make an Accusation");
		JPanel accusationPanel = new JPanel(new GridLayout(4,2));
		accusation.setSize(300,150);
		accusation.setLocationRelativeTo(null);
		accusation.setVisible(true);
		JButton submit = new JButton("Submit");
		SubmitButtonListener submitListener = new SubmitButtonListener();
		submit.addActionListener(submitListener);
		JButton cancel = new JButton("Cancel");
		JLabel roomLabel = new JLabel("Room");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel personLabel = new JLabel("Person");
		weaponA = new JComboBox<String>();
		personA = new JComboBox<String>();
		roomA = new JComboBox<String>();
		for(Player player : players) {
			personA.addItem(player.getName());
		}
		for(Card wep : weaponCards) {
			weaponA.addItem(wep.getCardName());
		}
		for(Card room : roomCards) {
			roomA.addItem(room.getCardName());
		}
		accusationPanel.add(roomLabel);
		accusationPanel.add(roomA);
		accusationPanel.add(personLabel);
		accusationPanel.add(personA);
		accusationPanel.add(weaponLabel);
		accusationPanel.add(weaponA);
		accusationPanel.add(submit);
		accusationPanel.add(cancel);
		accusation.add(accusationPanel);
	}

	private class SubmitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Accusation a = new Accusation();
			a.setRoom(getCard(roomA.getSelectedItem()));
			a.setPerson(getCard(personA.getSelectedItem()));
			a.setWeapon(getCard(weaponA.getSelectedItem()));
			if(checkAccusation(a)) {
				ImageIcon icon = new ImageIcon("data/avengers.png");
    			Image image = icon.getImage();
    			Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
    			icon = new ImageIcon(newimg);
				UIManager UI=new UIManager();
    			UI.put("OptionPane.background", new Color(175, 0, 0));
    			UI.put("Panel.background",  new Color(175, 0, 0));
    			UI.put("OptionPane.messageForeground", Color.white);
				int result = JOptionPane.showConfirmDialog(null, "You are a winner!", "Winner", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if(result == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}else{
				ImageIcon icon = new ImageIcon("data/avengers.png");
    			Image image = icon.getImage();
    			Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
    			icon = new ImageIcon(newimg);
				UIManager UI=new UIManager();
    			UI.put("OptionPane.background", new Color(175, 0, 0));
    			UI.put("Panel.background",  new Color(175, 0, 0));
    			UI.put("OptionPane.messageForeground", Color.white);
				int result = JOptionPane.showConfirmDialog(null, "You are a loser!", "Loser", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if(result == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		}
	}
	
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
				if(cellText.charAt(0)!='W' &&  cellText.charAt(0) != 'X') {
					board[row2][col2].setIsRoom(true);
					getRoom(cellText.charAt(0)).addCell(board[row2][col2]);
				}
				if(cellText.charAt(0) == 'X'){
					board[row2][col2].setIsUnused(true);	
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
			// Gets random index to be used for getting solution.
			int randIndex = rand.nextInt(tempDeck.size());
			// Stores our new random card.
			Card randomSolutionCard = tempDeck.get(randIndex);
			// Checks for a person to be added to our solution pile.
			if(tempDeck.size() == tempDeckSize){
				if(randomSolutionCard.getCardType() == CardType.PERSON){
					solution.setPerson(randomSolutionCard);
					// Removes that person card from the rest of the deck.
					tempDeck.remove(randIndex);
				}
			}
			// Once a person has been found for the soultion we check for a random room.
			if(tempDeck.size() == tempDeckSize - 1){
				if(randomSolutionCard.getCardType() == CardType.ROOM){
					solution.setRoom(randomSolutionCard);
					// Removes that room card from the rest of the deck.
					tempDeck.remove(randIndex);
				}
			}
			// Once a rooom has been found for the solution we check for a random weapon.
			if(tempDeck.size() == tempDeckSize - 2){
				if(randomSolutionCard.getCardType() == CardType.WEAPON){
					solution.setWeapon(randomSolutionCard);
					// Removes that weapon card from the rest of the deck.
					tempDeck.remove(randIndex);
				}
			}
		}
		//random for rest of players
		while( tempDeck.size() !=0) {
			// goes through the rest of the deck and deals out to the cards.
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
		deal();
		Random rand = new Random();
		roll = rand.nextInt(6)+1;
		calcTargets(getCell(players.get(currentPlayer).getRow(),players.get(currentPlayer).getColumn()),roll);
		GameControlPanel.getInstance().setTurn(players.get(currentPlayer), roll);
		addMouseListener(new MouseClick());
		
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

	public Card getCard(Object object) {
		for(Card card : deck) {
			if(card.getCardName() == object) {
				return card;
			}
		}
		return null;
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

	public Card handleSuggestion(Suggestion s){
		for(int i = 1; i<6 ; i++) {		
			if(players.get((currentPlayer+i) % 6).disproveSuggestion(s) != null){
				return players.get((currentPlayer+i) % 6).disproveSuggestion(s);
			}
		}
		return null;
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		// Reads in txt
		int playerCounter = 0;
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
				roomCards.add(card);
			}
			if(line[0].equals("Person")) {
				Card card = new Card(line[1], CardType.PERSON);
				deck.add(card);
				if(playerCounter == 0){
					ComputerPlayer player1 = new ComputerPlayer(line[1], 19, 0, "default", false);
					players.add(player1);
				}
				if(playerCounter == 1){
					ComputerPlayer player2 = new ComputerPlayer(line[1], 7, 0, "default", false);
					players.add(player2);
				}
				if(playerCounter == 2){
					ComputerPlayer player3 = new ComputerPlayer(line[1], 0, 7, "default", false);
					players.add(player3);
				}
				if(playerCounter == 3){
					ComputerPlayer player4 = new ComputerPlayer(line[1], 0, 18, "default", false);
					players.add(player4);
				}
				if(playerCounter == 4){
					ComputerPlayer player5 = new ComputerPlayer(line[1], 10, 23, "default", false);
					players.add(player5);
				}
				if(playerCounter == 5){
					HumanPlayer player6 = new HumanPlayer(line[1], "default", 23, 12, true);
					players.add(player6);
				}
				playerCounter += 1;	
			}
			if(line[0].equals("Weapon")) {
				Card card = new Card(line[1], CardType.WEAPON);
				deck.add(card);
				weaponCards.add(card);
			}
			
		}
		inTxt.close();
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i,j).getAdjList();
	}
	
	public boolean checkAccusation(Accusation a) {
		if (a.getRoom().Equals(solution.getRoom()) && a.getWeapon().Equals(solution.getWeapon()) && a.getPerson().Equals(solution.getPerson())) {
			return true;
		}else {
			return false;
		}
	}
	
}