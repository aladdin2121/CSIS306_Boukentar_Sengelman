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

package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, BoardCell location) {
		super(name, color, false, location);
	}
}