package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Suggestion;

class ComputerAITest {

	private static Board board;
	private static Card tonyStarkCard, steveRogersCard, bruceBannerCard, tChallaCard, natashaRomanoffCard, wandaMaximoffCard, kitchenCard, bedroomCard, theatreCard, hotTubCard,
	gymCard, barCard, closetCard, officeCard, bathroomCard, vibraniumShieldCard, repulsorsCard, mjolnirCard, handgunCard, sonicSpearCard, bowCard;

	@BeforeAll
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// Set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
		bathroomCard = new Card("Bathroom", CardType.ROOM);
	}
	
	@Test
	public void selectTargets() {
		// Test a roll of 1 with no room.
		board.calcTargets(board.getCell(10, 19), 1);
		ComputerPlayer computerPlayer = new ComputerPlayer("Tony Stark", "Red", 10, 19, false);
		int counter = 0;
		// loops through checking to make sure it is random by not being true everytime.
		for(int i = 0; i < 10; i++) {
			if(computerPlayer.selectTarget().equals(board.getCell(11, 19))) {
				counter += 1;
			}
		}
		assertTrue(counter != 10);
		
		// Tests if there is a room it will be selected.
		board.calcTargets(board.getCell(10, 21), 1);
		ComputerPlayer computerPlayer1 = new ComputerPlayer("Bruce Banner", "Blue", 10, 21, false);
		assertTrue(computerPlayer.selectTarget().equals(board.getCell(5, 21)));
	}
	
	@Test
	public void createSuggestion() {
		Suggestion suggestion = new Suggestion();
		suggestion.setRoom(bathroomCard);
		ComputerPlayer computerPlayer = new ComputerPlayer("Steve Rogers", "Purple", 13, 14, false);
		// Tests if the room 
		assertTrue(computerPlayer.getRow() == 13);
		assertTrue(computerPlayer.getColumn() == 14);
	}

}
