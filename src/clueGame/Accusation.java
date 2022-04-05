package clueGame;

public class Accusation {
	
	private Card room, weapon, person;
	
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
	public void setRoom(Card room) {
		this.room = room;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	public void setPerson(Card person) {
		this.person = person;
	}

}
