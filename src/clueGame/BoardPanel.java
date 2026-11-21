
/*
 * Class: BoardPanel
 *
 * Details:
 * it creates the board graphics
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelman-Lipps
 *
 * Start Date: 11/20/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */

package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	Board board;

	public BoardPanel() {

		board = Board.getInstance();

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(650, 650));
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int rows = board.getNumRows();
		int columns = board.getNumColumns();

		if (rows <= 0 || columns <= 0) {
			g2.dispose();
			return;
		}

		// Calculate cell size based on the panel dimensions.
		int cellSize = Math.min(getWidth() / columns, getHeight() / rows);

		if (cellSize <= 0) {
			g2.dispose();
			return;
		}

		// Center the board in the panel.
		int boardWidth = columns * cellSize;
		int boardHeight = rows * cellSize;

		int xOffset = (getWidth() - boardWidth) / 2;
		int yOffset = (getHeight() - boardHeight) / 2;

		// Draw each board cell.
		for (int row = 0; row < rows; row++) {

			for (int col = 0; col < columns; col++) {

				BoardCell cell = board.getCell(row, col);

				int x = xOffset + col * cellSize;
				int y = yOffset + row * cellSize;

				cell.draw(g2, x, y, cellSize);
			}
		}

		// Draw room names after all cells have been drawn.
		drawRoomNames(g2, cellSize, xOffset, yOffset);

		// Draw the players after the cells and room names.
		for (Player player : board.getPlayers()) {

			if (player.getLocation() != null) {
				player.draw(g2, cellSize, xOffset, yOffset);
			}
		}

		g2.dispose();
	}

	// Draw room names at the cells marked for room labels.
	private void drawRoomNames(Graphics2D g, int cellSize, int xOffset, int yOffset) {

		for (int row = 0; row < board.getNumRows(); row++) {

			for (int col = 0; col < board.getNumColumns(); col++) {

				BoardCell cell = board.getCell(row, col);

				if (cell.isLabel()) {

					Room room = board.getRoom(cell.getInitial());

					if (room == null) {
						continue;
					}

					String roomName = room.getName();

					int x = xOffset + col * cellSize;
					int y = yOffset + row * cellSize;

					// Adjust font size based on cell size.
					int fontSize = Math.max(8, cellSize / 2);

					g.setFont(new Font("Arial", Font.BOLD, fontSize));

					g.setColor(Color.BLUE);

					FontMetrics metrics = g.getFontMetrics();

					int textX = x + (cellSize - metrics.stringWidth(roomName)) / 2;
					int textY = y + (cellSize + metrics.getAscent()) / 2;

					g.drawString(roomName, textX, textY);
				}
			}
		}
	}

}
