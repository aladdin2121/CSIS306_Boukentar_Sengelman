
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



import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	Board board;

	public BoardPanel() {

		board = Board.getInstance();

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(650, 650));
		
		addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent e) {

	            int rows = board.getNumRows();
	            int columns = board.getNumColumns();

	            if (rows <= 0 || columns <= 0) {
	                return;
	            }

	            int cellSize = getCellSize();
	            if (cellSize <= 0) {
	                return;
	            }

	            int x = e.getX() - getXOffset(cellSize);
	            int y = e.getY() - getYOffset(cellSize);

	            // Ignore clicks outside the grid.
	            if (x < 0 || y < 0 || x >= columns * cellSize || y >= rows * cellSize) {
	                return;
	            }

	            board.handleBoardClick(y / cellSize, x / cellSize);
	        }
	    });
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
		int cellSize = getCellSize();

		if (cellSize <= 0) {
		    g2.dispose();
		    return;
		}

		int xOffset = getXOffset(cellSize);
		int yOffset = getYOffset(cellSize);

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
	private int getCellSize() {
	    return Math.min(getWidth() / board.getNumColumns(), getHeight() / board.getNumRows());
	}

	private int getXOffset(int cellSize) {
	    return (getWidth() - board.getNumColumns() * cellSize) / 2;
	}

	private int getYOffset(int cellSize) {
	    return (getHeight() - board.getNumRows() * cellSize) / 2;
	}

}
