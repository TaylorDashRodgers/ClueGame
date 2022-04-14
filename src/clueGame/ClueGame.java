package clueGame;

import java.awt.BorderLayout;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

    private GameControlPanel control;
    private GameCards cards; 
    private static Board board;
	
	public ClueGame() {
		setSize(750,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		control = new GameControlPanel();
		cards = new GameCards();

        // Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();

        add(control, BorderLayout.SOUTH);
        add(cards, BorderLayout.EAST);
        add(board, BorderLayout.CENTER);
	}
    
    public static void main(String[] args) {
    	ClueGame frame = new ClueGame();
    	frame.setVisible(true);
    }

}
