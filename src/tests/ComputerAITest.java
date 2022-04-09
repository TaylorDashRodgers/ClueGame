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

		// Declares our weapon cards for use in test.
		vibraniumShieldCard = board.getDeck().get(15);
		repulsorsCard = board.getDeck().get(16);
		mjolnirCard = board.getDeck().get(17);
		handgunCard = board.getDeck().get(18);
		sonicSpearCard = board.getDeck().get(19);
		bowCard = board.getDeck().get(20);

		// Declares our weapon cards for use in test.
		tonyStarkCard = board.getDeck().get(9);
		steveRogersCard = board.getDeck().get(10);
		bruceBannerCard = board.getDeck().get(11);
		tChallaCard = board.getDeck().get(12);
		natashaRomanoffCard = board.getDeck().get(13);
		wandaMaximoffCard = board.getDeck().get(14);

	}
	
	@Test
	public void selectTargets() {
		// Test a roll of 1 with no room.
		board.calcTargets(board.getCell(10, 19), 1);
		ComputerPlayer computerPlayer = new ComputerPlayer("Tony Stark", 19, 10, "Red", false);
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
		ComputerPlayer computerPlayer1 = new ComputerPlayer("Bruce Banner", 21, 10, "Blue", false);
		assertTrue(computerPlayer.selectTarget().equals(board.getCell(5, 21)));
	}
	
	@Test
	public void createSuggestion() {
		ComputerPlayer computerPlayer = new ComputerPlayer("Steve Rogers", 14, 13, "Purple", false);
		// Tests if the room matches the current location.
		assertTrue(computerPlayer.createSuggestion(bathroomCard).getRoom().getCardName().equals(board.getRoom(board.getBoard()[computerPlayer.getRow()][computerPlayer.getColumn()]).getName()));
		
		// Tests if the create suggestion based off the seen list works for weapon.
		computerPlayer.updateSeen(handgunCard);
		computerPlayer.updateSeen(mjolnirCard);
		computerPlayer.updateSeen(handgunCard);
		computerPlayer.updateSeen(bowCard);
		computerPlayer.updateSeen(repulsorsCard);
		assertTrue(computerPlayer.createSuggestion(bathroomCard).getWeapon().getCardName().equals("vibranium shield"));

		// Test if the create suggestion based off the seen list works for person.
		computerPlayer.updateSeen(tonyStarkCard);
		computerPlayer.updateSeen(steveRogersCard);
		computerPlayer.updateSeen(bruceBannerCard);
		computerPlayer.updateSeen(tChallaCard);
		computerPlayer.updateSeen(natashaRomanoffCard);
		assertTrue(computerPlayer.createSuggestion(bathroomCard).getPerson().getCardName().equals("Wanda Maximoff"));
	}

}
