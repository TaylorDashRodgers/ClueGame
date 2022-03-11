package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import junit.framework.Assert;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
    private static Board board;

    @BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

    // Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(12, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 14)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(18, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(18, 3)));
		assertTrue(testList.contains(board.getCell(17, 4)));
		assertTrue(testList.contains(board.getCell(18, 5)));

		// Test adjacent to walkways
		testList = board.getAdjList(8, 16);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(8, 15)));
		assertTrue(testList.contains(board.getCell(8, 17)));
		assertTrue(testList.contains(board.getCell(7, 16)));
		assertTrue(testList.contains(board.getCell(9, 16)));

		// Test next to closet
		testList = board.getAdjList(9,12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(8, 12)));
		assertTrue(testList.contains(board.getCell(10, 12)));
	
	}

	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(4, 6)));
		assertTrue(testList.contains(board.getCell(20, 19)));
		
		// now test the ballroom (note not marked since multiple test here)
		testList = board.getAdjList(20, 11);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(16, 9)));
		
		// one more room, the kitchen
		testList = board.getAdjList(20, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 18)));
		assertTrue(testList.contains(board.getCell(2, 2)));
	}

	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(10, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(15, 21)));
		assertTrue(testList.contains(board.getCell(11, 2)));

		testList = board.getAdjList(19, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 2)));
		assertTrue(testList.contains(board.getCell(18, 5)));
		assertTrue(testList.contains(board.getCell(19, 6)));
		
		testList = board.getAdjList(19, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 7)));
		assertTrue(testList.contains(board.getCell(19, 6)));
		assertTrue(testList.contains(board.getCell(20, 7)));
		assertTrue(testList.contains(board.getCell(20, 11)));
	}
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
	}

	@Test
	public void testTargetsInWalkway() {
		// test a roll of 1
		board.calcTargets(board.getCell(9, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(9, 1)));
		assertTrue(targets.contains(board.getCell(9, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(9, 2), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 2)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(9, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 2)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(9, 6)));	
	}
}
