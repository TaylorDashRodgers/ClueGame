package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import expiriment.TestBoard;
import expiriment.TestBoardCell;
import junit.framework.Assert;

class BoardTestsExp {
	TestBoard board;

	@BeforeEach // Run before each test, @BeforeAll would work just as well
	void setUp() {
		board = new TestBoard(4,4);
	}
	

	@Test
	public void testAdjacency1() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertEquals(2, testList.size());	
	}
	@Test
	public void testAdjacency2() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency3() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(0,3)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency4() {
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3,1)));
		Assert.assertTrue(testList.contains(board.getCell(2,0)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency5() {
		TestBoardCell cell = board.getCell(0,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,3)));
		Assert.assertTrue(testList.contains(board.getCell(0,2)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testTargetNormal1() {
		TestBoardCell cell = board.getCell(0,0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,1)));
		Assert.assertTrue(targets.contains(board.getCell(1,2)));
		Assert.assertTrue(targets.contains(board.getCell(0,3)));
		Assert.assertTrue(targets.contains(board.getCell(1,0)));		
	}
	
	
	@Test
	public void testTargetNormal2() {
		TestBoardCell cell = board.getCell(1,1);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		Assert.assertTrue(targets.contains(board.getCell(1,3)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));	
	}
	
	@Test
	public void testTargetsMixed() {
		board.getCell(0,2).setOccupied(true);
		board.getCell(1,2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0,3);
		board.calcTargets(cell,3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));	
	}
	
	@Test
	public void testTargetsRoom() {
		board.getCell(0,2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0,3);
		board.calcTargets(cell,2);
		Set<TestBoardCell> targets = board.getTargets();
		System.out.println(targets.size());
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));	
	}
	
	@Test
	public void testTargetsOccupiedRoom() {
		board.getCell(0,2).setOccupied(true);
		board.getCell(1,2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0,3);
		board.calcTargets(cell,3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));	
	}
	

}
