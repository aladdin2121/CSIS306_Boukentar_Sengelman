/*
 * Class: HumanPlayer
 *
 * Details:
 * Represents the human player.
 *
 * Authors: Alaa Eddine Boukentar Asseffar
 * 			Sophie Sengelman-Lipps
 *
 * Start Date: 11/06/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */


import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color, BoardCell location) {
		super(name, color, true, location);
	}
}