
/*
 * Class: GameControlPanel
 *
 * Details:
 * GUI layout for player name, die roll result, making accusation, passing the turn,
 * showing the guess result
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelman-Lipps
 *
 * Start Date: 11/12/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	// First layer of JPanels
	JPanel playerPanel;
	JPanel guessingPanel;

	// Panel holding the player name and color
	JPanel playerSmallerPanel;
	JLabel whoseTurn;
	JTextField playerName;

	// Panel holding die information
	JPanel diePanel;
	JLabel roll;
	JTextField dieRollResult;

	// JButtons
	JButton makeAccus;
	JButton next;

	// Lower left panel for guessing
	JPanel guess;
	JTextField playerGuessTextField;

	// Lower right panel for guess result
	JPanel guessResult;
	JTextField guessResultTextField;

	/**
	 * Constructor for the panel, it does 90% of the work.
	 */
	public GameControlPanel(Board board) {

		setLayout(new GridLayout(2, 1));

		playerPanel = new JPanel(new GridLayout(1, 4));
		guessingPanel = new JPanel(new GridLayout(1, 2));

		playerSmallerPanel = new JPanel(new GridLayout(2, 1));
		whoseTurn = new JLabel("Whose turn?");

		// Player name textfield
		playerName = new JTextField(12);
		playerName.setEditable(false);
		playerName.setOpaque(true);

		playerSmallerPanel.add(whoseTurn);
		playerSmallerPanel.add(playerName);

		// Panel holding die information
		diePanel = new JPanel();
		roll = new JLabel("Roll");

		// Roll result textfield
		dieRollResult = new JTextField(4);
		dieRollResult.setEditable(false);

		diePanel.add(roll);
		diePanel.add(dieRollResult);

		// Make accusation button
		makeAccus = new JButton("Make Accusation");

		// Next button
		next = new JButton("NEXT!");
		next.addActionListener(e -> board.nextPlayer());

		// Populate the top panel
		playerPanel.add(playerSmallerPanel);
		playerPanel.add(diePanel);
		playerPanel.add(makeAccus);
		playerPanel.add(next);

		// Lower panel
		guess = new JPanel(new GridLayout(1, 0));
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		playerGuessTextField = new JTextField();
		playerGuessTextField.setEditable(false);

		guess.add(playerGuessTextField);

		guessResult = new JPanel(new GridLayout(1, 0));
		guessResult.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		guessResultTextField = new JTextField();
		guessResultTextField.setEditable(false);

		guessResult.add(guessResultTextField);

		guessingPanel.add(guess);
		guessingPanel.add(guessResult);

		add(playerPanel);
		add(guessingPanel);
	}

	// Update the current player's name, color, and die roll.
	public void setTurn(Player player, int rollResult) {

		playerName.setText(player.getName());
		playerName.setBackground(player.getColor());
		dieRollResult.setText(String.valueOf(rollResult));
	}

	// Update the guess textfield.
	public void setGuess(String string) {
		playerGuessTextField.setText(string);
	}

	// Update the guess result textfield.
	public void setGuessResult(String string) {
		guessResultTextField.setText(string);
	}

	/**
	 * Main to test the panel.
	 *
	 * @param args
	 */
	/*
	public static void main(String[] args) {

		GameControlPanel panel = new GameControlPanel();

		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Test filling in the data.
		BoardCell cell = new BoardCell(0, 0);

		panel.setTurn(new ComputerPlayer("Col. Mustard", Color.ORANGE, cell), 5);

		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}
	*/

}
