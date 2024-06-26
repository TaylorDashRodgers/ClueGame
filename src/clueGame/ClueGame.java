package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import javax.swing.UIManager;

public class ClueGame extends JFrame{

    private static GameControlPanel control;
    private GameCards cards; 
    private static Board board;
    
//    private static ClueGame theInstance = new ClueGame();
//	// constructor is private to ensure only one can be created
//
//	// this method returns the only Board
//	public static ClueGame getInstance() {
//		return theInstance;
//	}
//	public GameCards getCards() {
//		return cards;
//	}
	
	public ClueGame() throws IOException {
		control = GameControlPanel.getInstance();
		control.initialize();
		
		 // Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		
		
		
		setSize(750,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = GameCards.getInstance();
		cards.initialize();

       

        add(control, BorderLayout.SOUTH);
        add(cards, BorderLayout.EAST);
        add(board, BorderLayout.CENTER);
	}

    public static void main(String[] args) throws IOException {
    	Music theme = new Music();
    	theme.avengersTheme();
    	ClueGame frame = new ClueGame();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
    	ImageIcon icon = new ImageIcon("data/avengers.png");
    	Image image = icon.getImage();
    	Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
    	icon = new ImageIcon(newimg);
    	 UIManager UI=new UIManager();
    	 UI.put("OptionPane.background", new Color(175, 0, 0));
    	 UI.put("Panel.background",  new Color(175, 0, 0));
    	 UI.put("OptionPane.messageForeground", Color.white);
		JOptionPane.showMessageDialog(null, "     You are " + board.getPlayers().get(5).getName() + "\n" + "    Can you find the solution\nbefore the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE, icon);  	
    }
    

}
