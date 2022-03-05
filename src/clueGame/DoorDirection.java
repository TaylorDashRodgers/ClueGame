package clueGame;

public enum DoorDirection {
	UP("W^"), LEFT("W<"), RIGHT("W>"), DOWN("Wv");
	private String value;
	
	DoorDirection (String aValue){
		value = aValue;
	}
	
	public String toString() {
		return value;
	}
	
}

