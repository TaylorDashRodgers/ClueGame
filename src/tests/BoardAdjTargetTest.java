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
		// Test in middle of walkway with 4 options
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(7, 5)));
		
		// Test in a one wide walkway
		testList = board.getAdjList(9, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(10, 12)));
		assertTrue(testList.contains(board.getCell(8, 12)));

		// Test in middle of walkway with 4 options
		testList = board.getAdjList(18, 14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 13)));
		assertTrue(testList.contains(board.getCell(18, 15)));
		assertTrue(testList.contains(board.getCell(19, 14)));
		assertTrue(testList.contains(board.getCell(17, 14)));

		// Test adjacent to walkways
		testList = board.getAdjList(9, 16);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 16)));
		assertTrue(testList.contains(board.getCell(10, 16)));
		assertTrue(testList.contains(board.getCell(9, 17)));
	
	}

	@Test
	public void testAdjacenciesRooms()
	{
		Set<BoardCell> testList = board.getAdjList(3, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(5, 5)));
		
		testList = board.getAdjList(2, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 1)));
		assertTrue(testList.contains(board.getCell(22, 17)));
		

		testList = board.getAdjList(5, 21);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 8)));
		assertTrue(testList.contains(board.getCell(10, 21)));
		assertTrue(testList.contains(board.getCell(4, 17)));
	}

	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(5, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(3, 5)));
		assertTrue(testList.contains(board.getCell(6, 5)));
		assertTrue(testList.contains(board.getCell(5, 4)));
		
		testList = board.getAdjList(11, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 3)));
		
		// one more room, the kitchen
		testList = board.getAdjList(14, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 21)));
		assertTrue(testList.contains(board.getCell(15, 17)));
		assertTrue(testList.contains(board.getCell(13, 17)));
		
	}
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(10, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 20)));
		assertTrue(targets.contains(board.getCell(11, 21)));	
		assertTrue(targets.contains(board.getCell(10, 22)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 21), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(9, 23)));
		assertTrue(targets.contains(board.getCell(5, 21)));	
		assertTrue(targets.contains(board.getCell(11, 23)));
		assertTrue(targets.contains(board.getCell(10, 22)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 21), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(10, 19)));	
		assertTrue(targets.contains(board.getCell(11, 22)));
		assertTrue(targets.contains(board.getCell(10, 23)));	
	}

	@Test
	public void testTargetsInWalkway() {
		// test a roll of 1
		board.calcTargets(board.getCell(9, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(10, 7)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(9, 7), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(13, 3)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(9, 7), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(5, 7)));
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(8, 6)));	
	}
}
