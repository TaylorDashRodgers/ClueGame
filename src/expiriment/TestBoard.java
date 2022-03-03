package expiriment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	public Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	
	public TestBoard(int x, int y){
		board = new TestBoardCell[x][y];
		for (int i = 0 ; i < x ; i++ ) {
			for(int j = 0; j < y; j++) {
				TestBoardCell temp = new TestBoardCell(x,y);
				board[x][y] = temp;
			}
		}
		for (int i = 0 ; i < x ; i++ ) {
			for(int j = 0; j < y; j++) {
				board[x][y].addAdjacency(board[x+1][y]);
				board[x][y].addAdjacency(board[x-1][y]);
				board[x][y].addAdjacency(board[x][y+1]);
				board[x][y].addAdjacency(board[x][y-1]);
				adjMtx.put(board[x][y], board[x][y].getAdjList());
			}
		}	
	}
	
	public void calcTargets(TestBoardCell startCell,int pathLength) {
		ArrayList <TestBoardCell> visited = new ArrayList<TestBoardCell>();
		for(TestBoardCell cell : startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					targets.add(cell);
				}
				else {
					calcTargets(cell,pathLength-1);
				}
				visited.remove(cell);
			}	
		}
	}
}
