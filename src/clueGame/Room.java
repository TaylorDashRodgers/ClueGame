package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private Set<BoardCell> roomCells = new HashSet<BoardCell>();
    private BoardCell label;
    private BoardCell center;
    
    public BoardCell getLabelCell() {
    	return label;
    }
    
    public BoardCell getCenterCell() {
    	return center;
    }
    
    public String getName() {
    	return name;
    }


}
