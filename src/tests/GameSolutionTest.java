package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Accusation;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Suggestion;

class GameSolutionTest {

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
		tonyStarkCard = new Card("Tony Stark", CardType.PERSON);
		vibraniumShieldCard = new Card("vibranium shield", CardType.WEAPON);
		bathroomCard = new Card("Bathroom", CardType.ROOM);
		steveRogersCard = new Card("Steve Rogers", CardType.PERSON);
		repulsorsCard = new Card("repulsors", CardType.WEAPON);
		gymCard = new Card("Gym", CardType.ROOM);
		board.getSolution().setPerson(tonyStarkCard);
		board.getSolution().setWeapon(vibraniumShieldCard);
		board.getSolution().setRoom(bathroomCard);	
	}

	@Test
	public void checkAccusation(){
		Accusation acuse = new Accusation();
		acuse.setPerson(tonyStarkCard);
		acuse.setRoom(bathroomCard);
		acuse.setWeapon(vibraniumShieldCard);
		assertTrue(board.checkAccusation(acuse));
		acuse.setWeapon(repulsorsCard);
		assertFalse(board.checkAccusation(acuse));
	}

	@Test
	public void disproveSuggestion(){
		Suggestion suggest = new Suggestion();
		suggest.setPerson(tonyStarkCard);
		suggest.setRoom(bathroomCard);
		suggest.setWeapon(repulsorsCard);
		board.getPlayers().get(0).getHand().clear();
		board.getPlayers().get(0).getHand().add(0, repulsorsCard);
		assertTrue(board.getPlayers().get(0).disproveSuggestion(suggest) != null);
		board.getPlayers().get(0).getHand().clear();
		board.getPlayers().get(0).getHand().add(0, vibraniumShieldCard);
		board.getPlayers().get(0).getHand().add(1, steveRogersCard);
		board.getPlayers().get(0).getHand().add(2, gymCard);
		assertFalse(board.getPlayers().get(0).disproveSuggestion(suggest) != null);
	}

	@Test
	public void handleSuggestion(){
		Suggestion suggest = new Suggestion();
		suggest.setPerson(tonyStarkCard);
		suggest.setRoom(bathroomCard);
		suggest.setWeapon(repulsorsCard);
		board.getPlayers().get(0).getHand().add(0, repulsorsCard);
		assertTrue(board.handleSuggestion(suggest) != null);
		board.getPlayers().get(0).getHand().clear();
		board.getPlayers().get(4).getHand().clear();
		board.getPlayers().get(4).getHand().add(0, repulsorsCard);
		assertTrue(board.handleSuggestion(suggest) != null);
		}
		

}
