/*
 * Class: GameControlPanel
 *
 * Details:
 * GUI layout for player name, die roll result, making accusation, passing the turn, 
 * showing the guess result
 *
 * Authors: Alaa Eddine Boukentar Asseffar
 * 			Sophie Sengelman-Lipps
 *
 * Start Date: 11/12/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	
	//first layer of JPanels
	JPanel playerPanel;
	JPanel guessingPanel;
	//panel holding the player name and color (top right)
	JPanel playerSmallerPanel;
	JLabel whoseTurn;
	JTextField playerName;
	//panel holding die info
	JPanel diePanel;
	JLabel roll;
	JTextField dieRollResult;
	
	//j buttons 
	JButton makeAccus;
	JButton next;
	
	//lower left panel for guessing
	JPanel guess;
	JTextField playerGuessTextField;
	//lower right panel for guess result
	JPanel guessResult;
	JTextField guessResultTextField;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
		setLayout(new GridLayout(2,0));
		
		playerPanel = new JPanel(new GridLayout(1,4));
		guessingPanel = new JPanel (new GridLayout(0,2));
		
		playerSmallerPanel = new JPanel();
		whoseTurn = new JLabel("Whose turn?");
		//player name textfield
		playerName = new JTextField(12);
		playerSmallerPanel.add(whoseTurn);
		playerSmallerPanel.add(playerName);
		
		diePanel = new JPanel();
		roll = new JLabel("Roll");
		//roll result textfield
		dieRollResult = new JTextField(4);
		diePanel.add(roll);
		diePanel.add(dieRollResult);
		//make accusation button
		makeAccus = new JButton("Make Accusation");
		//next button
		next = new JButton("NEXT!");
		//populating the top panel
		playerPanel.add(playerSmallerPanel);
		playerPanel.add(diePanel);
		playerPanel.add(makeAccus);
		playerPanel.add(next);
		
		//lower panel
		guess= new JPanel(new GridLayout(1,0));
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		playerGuessTextField = new JTextField();
		guess.add(playerGuessTextField);
		
		guessResult = new JPanel(new GridLayout(1,0));
		guessResult.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessResultTextField = new JTextField();
		guessResult.add(guessResultTextField);
		
		guessingPanel.add(guess);
		guessingPanel.add(guessResult);
		
		
		
		
		
		
		add(playerPanel);
		add(guessingPanel);
		
		
		
		
	}
	
	private void setTurn(ComputerPlayer cp, int i) {
		
		playerName.setText(cp.getName());
		playerName.setBackground(cp.getColor());
		dieRollResult.setText(String.valueOf(i));
		
	}
	
	private void setGuess(String string) {
		playerGuessTextField.setText(string);
		
	}
	
	private void setGuessResult(String string) {
		guessResultTextField .setText(string);
		
	}
	
	
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		BoardCell cell = new BoardCell(0, 0);
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.ORANGE, cell), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	
}
