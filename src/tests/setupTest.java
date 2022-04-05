package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

class setupTest {
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
	}
		
	@Test
	public void playerTest(){
		assertTrue(board.getPlayers().size() == 6);
		assertTrue(board.getPlayers().get(1).getRow() == 0 && board.getPlayers().get(1).getColumn() == 0);
		assertTrue(board.getPlayers().get(2).getRow() == 5 && board.getPlayers().get(2).getColumn() == 20);
		assertTrue(board.getPlayers().get(5).isHuman());
		assertFalse(board.getPlayers().get(4).isHuman());
	}
	
	@Test
	public void deckTest(){
		assertTrue(board.getDeck().size() == 21);
		assertTrue(board.getDeck().get(0).getCardType() == CardType.ROOM);
		assertTrue(board.getDeck().get(9).getCardType() == CardType.PERSON);
		assertTrue(board.getDeck().get(15).getCardType() == CardType.WEAPON);
		
	}

	@Test
	public void dealTest(){
		assertTrue(board.getSolution().getPerson() != null);
		assertTrue(board.getSolution().getWeapon() != null);
		assertTrue(board.getSolution().getRoom() != null);
		assertTrue(board.getPlayers().get(0).getHand().size() == 3);
		assertTrue(board.getPlayers().get(1).getHand().size() == 3);
		assertTrue(board.getPlayers().get(5).getHand().size() == 3);
		assertTrue(board.getPlayers().get(3).getHand().size() == 3);
	}
	
}
