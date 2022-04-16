package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{

    private static GameControlPanel control;
    private GameCards cards; 
    private static Board board;
	
	public ClueGame() throws IOException {
		control = GameControlPanel.getInstance();
		control.initialize();
		
		 // Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		
		
		
		setSize(900,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new GameCards(board.getPlayers().get(5));

       

        add(control, BorderLayout.SOUTH);
        add(cards, BorderLayout.EAST);
        add(board, BorderLayout.CENTER);
	}
	
    public static void main(String[] args) throws IOException {
    	Music theme = new Music();
//    	theme.avengersTheme();
    	ClueGame frame = new ClueGame();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
		JOptionPane.showMessageDialog(null, "     You are " + board.getPlayers().get(5).getName() + "\n" + "    Can you find the solution\nbefore the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);  	
    }
    

}
