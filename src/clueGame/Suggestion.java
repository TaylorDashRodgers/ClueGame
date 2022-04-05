package clueGame;

public class Suggestion {
    	private Card room, person, weapon;
	
	//Getters
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}
	public Card getPerson() {
		return person;
	}
	
	//Setters
	public void setPerson(Card person) {
		this.person = person;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	public void setRoom(Card room) {
		this.room = room;
	}

}
