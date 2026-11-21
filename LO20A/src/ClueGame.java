/*
 * Class: ClueGame
 * 
 * Details:
 * the main frame of the game that 
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie SengelmanLipps 
 * 			
 * 
 * Start Date: 11/20/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	public ClueGame() {
		Board board = Board.getInstance();

	    board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	    board.initialize();

	    BoardPanel newBoard = new BoardPanel();
	    add(newBoard);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    pack();
	    setLocationRelativeTo(null);
	}
	
	public static void main (String[] arg) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}

}
