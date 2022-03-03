package expiriment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	
	public TestBoard(int x, int y){
		board = new TestBoardCell[x][y];
		for (int i = 0 ; i < x ; i++ ) {
			for(int j = 0; j < y; j++) {
				TestBoardCell temp = new TestBoardCell(i,j);
				board[i][j] = temp;
			}
		}
		for (int i = 0 ; i < x ; i++ ) {
			for(int j = 0; j < y; j++) {
				TestBoardCell temp1 = new TestBoardCell(i+1,j);
				TestBoardCell temp2 = new TestBoardCell(i-1,j);
				TestBoardCell temp3 = new TestBoardCell(i,j);
				TestBoardCell temp4 = new TestBoardCell(i+1,j);
				board[i][j].addAdjacency(temp1);
				board[i][j].addAdjacency(temp2);
				board[i][j].addAdjacency(temp3);
				board[i][j].addAdjacency(temp4);
			}
		}	
	}
	
	public TestBoardCell getCell(int x, int y) {
		return board[x][y];
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
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
