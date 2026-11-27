
/*
 * Class: ClueGame
 *
 * Details:
 * the main frame of the game
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie SengelmanLipps
 *
 * Start Date: 11/20/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class ClueGame extends JFrame {

	public ClueGame() {

		Board board = Board.getInstance();

		setTitle("Clue Game");
		setLayout(new BorderLayout());

		// Add the board panel.
		BoardPanel newBoard = new BoardPanel();
		board.setBoardPanel(newBoard); 
		add(newBoard, BorderLayout.CENTER);

		// Add the known cards panel.
		KnownCardsPanel knownCards = new KnownCardsPanel(board.getHumanPlayer().getHand(),
				Collections.<Card, Color>emptyMap());

		add(new JScrollPane(knownCards), BorderLayout.EAST);

		// Add the game control panel.
		GameControlPanel controlPanel = new GameControlPanel(board);
		
		board.setControlPanel(controlPanel);
		//controlPanel.setTurn(board.getHumanPlayer(), board.getRoll());

		add(controlPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(1100, 700);
		setLocationRelativeTo(null);
	}

	public static void main(String[] arg) {

		Board board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		ClueGame game = new ClueGame();
		
		game.setVisible(true);
		JOptionPane.showMessageDialog(game, "You are "+board.getHumanPlayer().getName()+", \n"+
									" can you find the solution the computer players?", 
									"Welcome to clue",JOptionPane.INFORMATION_MESSAGE);
		
		board.startGame();
	}

}
