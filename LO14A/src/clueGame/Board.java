/*
 * Class: Board
 * 
 * Details:
 * The board creates a grid of type BoardCell and populates it from a .csv file
 * it calculates the adjacency of each cell
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie Sengelmann-Lipps
 * 
 * Start Date: 10/17/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */
package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;

	private String layoutConfigFile;
	private String setupConfigFile;

	private Map<Character, Room> roomMap;
	private Set<Character> roomInitials;

	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	private static final Board theInstance = new Board();

	private Board() {
		super();

		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.roomMap = new HashMap<>();
		this.roomInitials = new HashSet<>();
	}

	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String csv, String txt) {
		this.layoutConfigFile = csv;
		this.setupConfigFile = txt;
	}

	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			System.err.println("Unable to initialize board: " + e.getMessage());
		}
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {

		this.roomMap.clear();
		this.roomInitials.clear();

		try (Scanner scan = new Scanner(new File("data", setupConfigFile))) {

			int lineNumber = 0;

			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				lineNumber++;

				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}

				String[] tokens = line.split(",", -1);

				if (tokens.length != 3) {
					throw new BadConfigFormatException("Invalid setup entry on line " + lineNumber);
				}

				String type = tokens[0].trim();
				String name = tokens[1].trim();
				String symbol = tokens[2].trim();

				if (name.isEmpty() || symbol.length() != 1) {
					throw new BadConfigFormatException("Invalid setup entry on line " + lineNumber);
				}

				if (!type.equalsIgnoreCase("room") && !type.equalsIgnoreCase("space")) {
					throw new BadConfigFormatException("Unknown setup entry type on line " + lineNumber);
				}

				char initial = symbol.charAt(0);

				if (roomMap.containsKey(initial)) {
					throw new BadConfigFormatException("Duplicate setup symbol: " + initial);
				}

				roomMap.put(initial, new Room(name));

				if (type.equalsIgnoreCase("room")) {
					roomInitials.add(initial);
				}
			}
		}

		if (roomMap.isEmpty()) {
			throw new BadConfigFormatException("The setup file contains no entries.");
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

		List<String[]> layout = new ArrayList<>();

		try (Scanner scan = new Scanner(new File("data", layoutConfigFile))) {

			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();

				if (line.isEmpty()) {
					continue;
				}

				String[] tokens = line.split(",", -1);

				for (int col = 0; col < tokens.length; col++) {
					tokens[col] = tokens[col].trim();
				}

				layout.add(tokens);
			}
		}

		if (layout.isEmpty()) {
			throw new BadConfigFormatException("The layout file is empty.");
		}

		numRows = layout.size();
		numColumns = layout.get(0).length;

		if (numColumns == 0) {
			throw new BadConfigFormatException("The layout file has no columns.");
		}

		// Validate every row and cell before creating the grid.
		for (int row = 0; row < numRows; row++) {
			String[] tokens = layout.get(row);

			if (tokens.length != numColumns) {
				throw new BadConfigFormatException("Incorrect number of columns in row " + row);
			}

			for (int col = 0; col < numColumns; col++) {
				validateCellToken(tokens[col], row, col);
			}
		}

		targets.clear();
		visited.clear();

		for (Room room : roomMap.values()) {
			room.setCenterCell(null);
			room.setLabelCell(null);
		}

		grid = new BoardCell[numRows][numColumns];

		// Create and initialize every cell.
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				String token = layout.get(row)[col];
				char initial = token.charAt(0);

				BoardCell cell = new BoardCell(row, col);

				cell.setInitial(initial);
				cell.setRoom(roomInitials.contains(initial));
				cell.setEmpty(initial == 'X');
				cell.setIsDoorWay(false);

				if (token.length() == 2) {
					char marker = token.charAt(1);

					if (isDirectionMarker(marker)) {
						cell.setDirection(getDirection(marker));
						cell.setIsDoorWay(true);
					} else if (marker == '*') {
						cell.setRoomCenter(true);
						roomMap.get(initial).setCenterCell(cell);
					} else if (marker == '#') {
						cell.setLabel(true);
						roomMap.get(initial).setLabelCell(cell);
					} else {
						cell.setSecretPassage(marker);
					}
				}

				grid[row][col] = cell;
			}
		}

		calcAdjacencies();
	}

	private void validateCellToken(String token, int row, int col) throws BadConfigFormatException {

		if (token.isEmpty() || token.length() > 2) {
			throw new BadConfigFormatException("Invalid cell at row " + row + ", column " + col);
		}

		char initial = token.charAt(0);

		if (!roomMap.containsKey(initial)) {
			throw new BadConfigFormatException(
					"Unknown room or space symbol '" + initial + "' at row " + row + ", column " + col);
		}

		if (token.length() == 1) {
			return;
		}

		char marker = token.charAt(1);

		if (isDirectionMarker(marker)) {
			return;
		}

		if ((marker == '*' || marker == '#') && roomInitials.contains(initial)) {
			return;
		}

		// A second room initial represents a secret passage.
		if (roomInitials.contains(initial) && roomInitials.contains(marker)) {
			return;
		}

		throw new BadConfigFormatException("Invalid cell marker '" + marker + "' at row " + row + ", column " + col);
	}

	private boolean isDirectionMarker(char marker) {
		return marker == '^' || marker == 'v' || marker == '<' || marker == '>';
	}

	private DoorDirection getDirection(char marker) {
		switch (marker) {
		case '^':
			return DoorDirection.UP;
		case 'v':
			return DoorDirection.DOWN;
		case '<':
			return DoorDirection.LEFT;
		case '>':
			return DoorDirection.RIGHT;
		default:
			return DoorDirection.NONE;
		}
	}

	private void calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				BoardCell cell = grid[row][col];
				//if the cell is X then it is skipped
				
				if (cell.isEmpty()) {
				    continue;
				}
				// if it is a room cell and not a secret passage we skip. Room center is
				// calculated with doorway and secret passage

				if (cell.isRoom() && !cell.isSecretPass()) {
					continue;
				}
				// if it is a secret pass then we find the room center of the corresponding
				// room and we update the current room center adjacent list and the
				// other room center adjacent list
				if (cell.isSecretPass()) {

					BoardCell current = roomMap.get(cell.getInitial()).getCenterCell();
					BoardCell destination = roomMap.get(cell.getSecretPassage()).getCenterCell();

					current.addAdj(destination);
					destination.addAdj(current);

				}
				// if it is a doorway, then the adjacent cell is the room center of the room
				// and walkways
				else if (cell.isDoorway()) {
					BoardCell roomCell;
					if (cell.getDoorDirection() == DoorDirection.UP) {
						roomCell = grid[row - 1][col];

					} else if (cell.getDoorDirection() == DoorDirection.DOWN) {
						roomCell = grid[row + 1][col];

					} else if (cell.getDoorDirection() == DoorDirection.LEFT) {
						roomCell = grid[row][col - 1];

					} else {
						roomCell = grid[row][col + 1];

					}

					if (roomInitials.contains(roomCell.getInitial())) {
						BoardCell roomCenter = roomMap.get(roomCell.getInitial()).getCenterCell();

						if (roomCenter != null) {
							cell.addAdj(roomCenter);
							roomCenter.addAdj(cell);
							walkWayAdjCalc(row, col, cell);
						}
					}

				}

				// case of walkways cells
				else if (cell.getInitial() == 'W') {
					walkWayAdjCalc(row, col, cell);
				}
			}
		}
	}

	private void walkWayAdjCalc(int row, int col, BoardCell cell) {
		if (row > 0 && grid[row - 1][col].getInitial() == 'W') {
			cell.addAdj(grid[row - 1][col]);
		}

		if (row < numRows - 1 && grid[row + 1][col].getInitial() == 'W') {
			cell.addAdj(grid[row + 1][col]);
		}

		if (col > 0 && grid[row][col - 1].getInitial() == 'W') {
			cell.addAdj(grid[row][col - 1]);
		}

		if (col < numColumns - 1 && grid[row][col + 1].getInitial() == 'W') {
			cell.addAdj(grid[row][col + 1]);
		}
	}

	public void calcTargets(BoardCell start, int length) {
		targets.clear();
		visited.clear();

		if (start == null || length < 0) {
			return;
		}

		visited.add(start);
		findTargets(start, length);
		visited.clear();
	}

	private void findTargets(BoardCell current, int stepsRemaining) {

		if (stepsRemaining == 0) {
			targets.add(current);
			return;
		}

		for (BoardCell next : current.getAdjList()) {

			if (visited.contains(next) || (next.getOccupied() && !next.isRoom())) {
				continue;
			}

			visited.add(next);

			if (next.isRoom() || stepsRemaining == 1) {
				targets.add(next);
			} else {
				findTargets(next, stepsRemaining - 1);
			}

			visited.remove(next);
		}
	}

	public Room getRoom(char initial) {
		return this.roomMap.get(initial);
	}

	public Room getRoom(BoardCell cell) {
		if (cell == null) {
			return null;
		}

		return this.roomMap.get(cell.getInitial());
	}

	public BoardCell getCell(int row, int col) {
		return this.grid[row][col];
	}

	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	public int getNumRows() {
		return this.numRows;
	}

	public int getNumColumns() {
		return this.numColumns;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return this.grid[row][col].getAdjList();
	}

}