package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCards extends JPanel{
    // Variable declaration
	public JPanel People, Rooms, Weapons, peopleCardsHand, roomCardsHand, weaponCardsHand, peopleCardsSeen, roomCardsSeen, weaponCardsSeen;
	private JLabel inHandPeople, inHandRooms, inHandWeapons, seenPeople, seenRooms, seenWeapons;
	
	private static Board board;
	
  private static GameCards theInstance = new GameCards();
	// constructor is private to ensure only one can be created

	// this method returns the only Board
	public static GameCards getInstance() {
		return theInstance;
	}

	
	

    public void initialize() {
    	Player player = board.getInstance().getPlayers().get(5);
    	setBorder(new TitledBorder(new EtchedBorder(), "Known Cards:"));
        setLayout(new GridLayout(3,0));
        People = new JPanel();
        People.setLayout(new GridLayout(4,0));
        People.setBorder(new TitledBorder(new EtchedBorder(), "People"));
        inHandPeople = new JLabel("In Hand:");
        inHandPeople.setHorizontalAlignment(JLabel.LEFT);
        People.add(inHandPeople);
        peopleCardsHand = new JPanel();
        peopleCardsHand.setLayout(new GridLayout(0,1));
        People.add(peopleCardsHand);
        seenPeople = new JLabel("Seen:");
        seenPeople.setLayout(new GridLayout(0,1));
        seenPeople.setHorizontalAlignment(JLabel.LEFT);
        People.add(seenPeople);
        peopleCardsSeen = new JPanel();
		peopleCardsSeen.setLayout(new GridLayout(0,1));
        People.add(peopleCardsSeen);
        Rooms = new JPanel();
        Rooms.setLayout(new GridLayout(4,0));
        Rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
        inHandRooms = new JLabel("In Hand:");
        inHandRooms.setHorizontalAlignment(JLabel.LEFT);
        Rooms.add(inHandRooms);
        roomCardsHand = new JPanel();
        roomCardsHand.setLayout(new GridLayout(0,1));
        Rooms.add(roomCardsHand);
        seenRooms = new JLabel("Seen:");
        seenRooms.setHorizontalAlignment(JLabel.LEFT);
        Rooms.add(seenRooms);
        roomCardsSeen = new JPanel();
        roomCardsSeen.setLayout(new GridLayout(0,1));
        Rooms.add(roomCardsSeen);
        Weapons = new JPanel();
        Weapons.setLayout(new GridLayout(4,0));
        Weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
        inHandWeapons = new JLabel("In Hand:");
        inHandWeapons.setHorizontalAlignment(JLabel.LEFT);
        Weapons.add(inHandWeapons);
        weaponCardsHand = new JPanel();
        weaponCardsHand.setLayout(new GridLayout(0,1));
        Weapons.add(weaponCardsHand);
        seenWeapons = new JLabel("Seen:");
        seenWeapons.setHorizontalAlignment(JLabel.LEFT);
        Weapons.add(seenWeapons);
        weaponCardsSeen = new JPanel();
        weaponCardsSeen.setLayout(new GridLayout(0,1));
        Weapons.add(weaponCardsSeen);
        add(People);
        add(Rooms);
        add(Weapons);        
        for(Card temp : player.getHand() ) {
			if(temp.getCardType() == CardType.PERSON) {
				JTextField tempText = new JTextField(temp.getCardName());
				tempText.setEditable(false);
				peopleCardsHand.add(tempText);
			}
			if(temp.getCardType() == CardType.ROOM) {
				JTextField tempText = new JTextField(temp.getCardName());
				tempText.setEditable(false);
				roomCardsHand.add(tempText);
			}
			if(temp.getCardType() == CardType.WEAPON) {
				JTextField tempText = new JTextField(temp.getCardName());
				tempText.setEditable(false);
				weaponCardsHand.add(tempText);
			}
		}
    }
    
    // Creates the add to the hand methods.
    public void addPersonHand(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		peopleCardsHand.add(tempText);
    	peopleCardsHand.revalidate();
    }
    
    public void addRoomHand(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		roomCardsHand.add(tempText);
    	roomCardsHand.revalidate();
    }
    public void addWeaponHand(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		weaponCardsHand.add(tempText);
    	weaponCardsHand.revalidate();
    }

    // Creates the add to the seen methods.
    public void addPersonSeen(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		peopleCardsSeen.add(tempText);
    	peopleCardsSeen.revalidate();
    }
    public void addRoomSeen(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		roomCardsSeen.add(tempText);
    	roomCardsSeen.revalidate();
    }
    public void addWeaponSeen(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		weaponCardsSeen.add(tempText);
    	weaponCardsSeen.revalidate();
    }
    
    public void addSeen(Card card) {
    	JTextField tempText = new JTextField(card.getCardName());
		tempText.setEditable(false);
		if(card.getCardType()== CardType.PERSON) {
			peopleCardsSeen.add(tempText);
	    	peopleCardsSeen.revalidate();
		}
		if(card.getCardType()== CardType.WEAPON) {
			weaponCardsSeen.add(tempText);
	    	weaponCardsSeen.revalidate();
		}
		if(card.getCardType()== CardType.ROOM) {
			roomCardsSeen.add(tempText);
	    	roomCardsSeen.revalidate();
		}
    }
    
    
	public static void main(String[] args) {
		

		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		GameCards panel = new GameCards(board.getPlayers().get(0));  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(160, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		Player test = board.getPlayers().get(0);
		Player test2 = board.getPlayers().get(1);
		Player test3 = board.getPlayers().get(2);

		// Grabs cards from two other players to test the seen funcitonality
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
