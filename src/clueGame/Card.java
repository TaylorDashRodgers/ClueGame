package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	//TO DO
	boolean equals(Card card) {
		return true;
	}

	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return type;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Card(String cardName,CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
}
