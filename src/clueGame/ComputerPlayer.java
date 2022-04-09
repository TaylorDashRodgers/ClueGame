package clueGame;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, int row, int column, String color, boolean isHuman) {
		super(name, color, row, column, isHuman);
		// TODO Auto-generated constructor stub
	}
	public ComputerPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Methods
	public Suggestion createSuggestion(Card room){
		Suggestion temp = new Suggestion();
		temp.setRoom(room);
		for(Card card : Board.getInstance().getDeck()){
			if(!seen.contains(card)){
				//System.out.println(card.getCardName());
				if(card.getCardType() == CardType.PERSON && temp.getPerson() == null){
					temp.setPerson(card);
				}
				else if(card.getCardType() == CardType.WEAPON && temp.getWeapon() == null){
					temp.setWeapon(card);
				}
				else if(temp.getPerson() != null && temp.getWeapon() != null){
					break;
				}
			}
		}
		return temp;
	}

	public BoardCell selectTarget() {
		Set<BoardCell> targets = Board.getInstance().getTargets();
		for (BoardCell cell : targets) {
			if (cell.isRoomCenter()) {
				return cell;
			}
		}
		BoardCell[] arrayOfSet = targets.toArray(new BoardCell[targets.size()]);
		Random rand = new Random();
		int randNum = rand.nextInt(targets.size());
		return arrayOfSet[randNum];
	}
}
