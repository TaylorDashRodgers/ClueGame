package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCards extends JPanel{
	public JPanel People, Rooms, Weapons, peopleCardsHand, roomCardsHand, weaponCardsHand, peopleCardsSeen, roomCardsSeen, weaponCardsSeen;
	private JLabel inHandPeople, inHandRooms, inHandWeapons, seenPeople, seenRooms, seenWeapons;
    //private JTextField card;
	
	private static Board board;
	

    public GameCards() {
        setLayout(new GridLayout(3,0));
        People = new JPanel();
        People.setLayout(new GridLayout(4,0));
        People.setBorder(new TitledBorder(new EtchedBorder(), "People"));
        inHandPeople = new JLabel("In Hand:");
        inHandPeople.setHorizontalAlignment(JLabel.LEFT);
        People.add(inHandPeople);
        peopleCardsHand = new JPanel();
        People.add(peopleCardsHand);
        seenPeople = new JLabel("Seen:");
        seenPeople.setHorizontalAlignment(JLabel.LEFT);
        People.add(seenPeople);
        peopleCardsSeen = new JPanel();
        People.add(peopleCardsSeen);
        Rooms = new JPanel();
        Rooms.setLayout(new GridLayout(4,0));
        Rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
        inHandRooms = new JLabel("In Hand:");
        inHandRooms.setHorizontalAlignment(JLabel.LEFT);
        Rooms.add(inHandRooms);
        roomCardsHand = new JPanel();
        Rooms.add(roomCardsHand);
        seenRooms = new JLabel("Seen:");
        seenRooms.setHorizontalAlignment(JLabel.LEFT);
        Rooms.add(seenRooms);
        roomCardsSeen = new JPanel();
        Rooms.add(roomCardsSeen);
        Weapons = new JPanel();
        Weapons.setLayout(new GridLayout(4,0));
        Weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
        inHandWeapons = new JLabel("In Hand:");
        inHandWeapons.setHorizontalAlignment(JLabel.LEFT);
        Weapons.add(inHandWeapons);
        weaponCardsHand = new JPanel();
        Weapons.add(weaponCardsHand);
        seenWeapons = new JLabel("Seen:");
        seenWeapons.setHorizontalAlignment(JLabel.LEFT);
        Weapons.add(seenWeapons);
        weaponCardsSeen = new JPanel();
        Weapons.add(weaponCardsSeen);
        add(People);
        add(Rooms);
        add(Weapons);
    }
    
    public void addPersonHand(Card card) {
    	peopleCardsHand.add(new JTextField(card.getCardName()));
    	peopleCardsHand.revalidate();
    }
    
    public void addRoomHand(Card card) {
    	roomCardsHand.add(new JTextField(card.getCardName()));
    	roomCardsHand.revalidate();
    }
    public void addWeaponHand(Card card) {
    	weaponCardsHand.add(new JTextField(card.getCardName()));
    	weaponCardsHand.revalidate();
    }

    public void addPersonSeen(Card card) {
    	peopleCardsSeen.add(new JTextField(card.getCardName()));
    	peopleCardsSeen.revalidate();
    }
    public void addRoomSeen(Card card) {
    	roomCardsSeen.add(new JTextField(card.getCardName()));
    	roomCardsSeen.revalidate();
    }
    public void addWeaponSeen(Card card) {
    	weaponCardsSeen.add(new JTextField(card.getCardName()));
    	weaponCardsSeen.revalidate();
    }
    
	public static void main(String[] args) {
		GameCards panel = new GameCards();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		Player test = board.getPlayers().get(0);
		Player test2 = board.getPlayers().get(1);
		Player test3 = board.getPlayers().get(2);
		for(Card temp : test.getHand() ) {
			if(temp.getCardType() == CardType.PERSON) {
				panel.addPersonHand(temp);
			}
			if(temp.getCardType() == CardType.ROOM) {
				panel.addRoomHand(temp);
			}
			if(temp.getCardType() == CardType.WEAPON) {
				panel.addWeaponHand(temp);
			}
		}
		
		for (Card temp : test2.getHand() ) {
			test.getSeen().add(temp);
		}
		for (Card temp : test3.getHand() ) {
			test.getSeen().add(temp);
		}
		
		for(Card temp : test.getSeen() ) {
			if(temp.getCardType() == CardType.PERSON) {
				panel.addPersonSeen(temp);
			}
			if(temp.getCardType() == CardType.ROOM) {
				panel.addRoomSeen(temp);
			}
			if(temp.getCardType() == CardType.WEAPON) {
				panel.addWeaponSeen(temp);
			}
		}
		
		
	}

}
