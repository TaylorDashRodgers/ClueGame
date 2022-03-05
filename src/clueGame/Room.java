package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private Set<BoardCell> roomCells = new HashSet<BoardCell>();
    
    public String getName() {
    	return name;
    }


}
