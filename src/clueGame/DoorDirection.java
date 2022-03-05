package clueGame;

public enum DoorDirection {
	UP("^"), LEFT("<"), RIGHT(">"), DOWN("v");
	private String value;
	
	DoorDirection (String aValue){
		value = aValue;
	}
	
	public String toString() {
		return value;
	}
	
}

