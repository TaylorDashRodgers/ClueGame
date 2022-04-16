package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	// Variable declaration
	private JPanel Bot, Top, Top1, Top2, Bot1, Bot2;
	private JLabel label, label2;
	private JButton Top3, nextButton;
	private JTextField guess, guessResult, rolls, whoseTurn;
	private static Board board;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel(Board board)  {
		this.board = board;
		setLayout(new GridLayout(2,0));
		Bot = new JPanel();
		Top = new JPanel();
		Top.setLayout(new GridLayout(1,4));
		Bot.setLayout(new GridLayout(0,2));
		Bot1 = new JPanel();
		Bot1.setLayout(new GridLayout(1,0));
		Bot2 = new JPanel();
		Bot2.setLayout(new GridLayout(1,0));
		Top1 = new JPanel();
		Top1.setLayout(new GridLayout(2,0));
		label = new JLabel();
		label.setText("Whose turn?");
		Top1.add(label);
		label.setHorizontalAlignment(JLabel.CENTER);
		whoseTurn = new JTextField();
		whoseTurn.setEditable(false);
		Top1.add(whoseTurn);
		Top.add(Top1);
		Top2 = new JPanel();
		label2 = new JLabel();
		label2.setText("Roll:");
		label2.setHorizontalAlignment(JLabel.RIGHT);
		Top2.add(label2);
		rolls = new JTextField(5);
		rolls.setEditable(false);
		Top2.add(rolls);
		Top.add(Top2);
		Top3 = new JButton("Make Accusaiton");
		Top.add(Top3);
		
		nextButton = new JButton("NEXT!");
		ButtonListener listener = new ButtonListener();
		nextButton.addActionListener(listener);
		Top.add(nextButton);
		
		guess = new JTextField();
		guess.setEditable(false);
		guessResult = new JTextField();
		guessResult.setEditable(false);
		Bot1.add(guess);
		Bot1.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		Bot.add(Bot1);
		Bot2.add(guessResult);
		Bot2.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		Bot.add(Bot2);
		add(Top);
		add(Bot);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			board.nextTurn();
		}
	}
	
	// Creates our setGuess method.
    public void setGuess(String guesss) {
		guess.setText(guesss);
    }
    
	// Creates our setTurn method.
    public void setTurn(Player player, int roll) {
    	whoseTurn.setText(player.getName());
    	String s = Integer.toString(roll);
    	rolls.setText(s);
    	whoseTurn.setBackground(Color.orange);
    }
    
	// Creates our setGuessResult method.
    public void setGuessResult(String stringo) {
    	guessResult.setText(stringo);
    }
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		 // Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		GameControlPanel panel = new GameControlPanel(board);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
//		 test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange", false), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}
