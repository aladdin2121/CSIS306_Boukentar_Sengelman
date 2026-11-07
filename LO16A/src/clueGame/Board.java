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
 * Sources: googled how to parse in colors
 */
package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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
	//set deck and different Player objects to public for tests
	public Set<Card> deck;
	
	public Player person;
	public Player com1;
	public Player com2;
	public Player com3;
	public Player com4;
	public Player com5;
	
	public Solution solution;

	private static final Board theInstance = new Board();

	private Board() {
		super();

		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.roomMap = new HashMap<>();
		this.roomInitials = new HashSet<>();
		this.deck = new HashSet<>();
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
			//setting up starting location requires the grid to be already setup, and setting up the grid requires 
			//rooms to be already setup. So, we setup starting location of each player after both configs are setup
			BoardCell cell = grid[person.getStartingRow()][person.getStartingCol()];
			person.setLocation(cell);
			BoardCell cell1 = grid[com1.getStartingRow()][com1.getStartingCol()];
			com1.setLocation(cell1);
			BoardCell cell2 = grid[com2.getStartingRow()][com2.getStartingCol()];
			com2.setLocation(cell2);
			BoardCell cell3 = grid[com3.getStartingRow()][com3.getStartingCol()];
			com3.setLocation(cell3);
			BoardCell cell4 = grid[com4.getStartingRow()][com4.getStartingCol()];
			com4.setLocation(cell4);
			BoardCell cell5 = grid[com5.getStartingRow()][com5.getStartingCol()];
			com5.setLocation(cell5);
			
			List<Card> list = new ArrayList<>(deck);
	        
	        // Pick a random index
			
			Card randomElement1;
			Card randomElement2;
			Card randomElement3;
			while(true) {
				int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
				randomElement1 = list.get(randomIndex);
				if (randomElement1.getType()==CardType.ROOM)
					randomIndex = ThreadLocalRandom.current().nextInt(list.size());
					randomElement2 = list.get(randomIndex);
					if (randomElement2.getType()==CardType.PERSON) {
						randomIndex = ThreadLocalRandom.current().nextInt(list.size());
						randomElement3 = list.get(randomIndex);
						if (randomElement3.getType()==CardType.WEAPON) {
							break;
						}
					}
			}
			this.solution = Solution.getInstance();
			solution.setSolution(randomElement1, randomElement2, randomElement3);
	        deck.remove(randomElement1);
	        deck.remove(randomElement2);
	        deck.remove(randomElement3);
			
			
			
		} catch (FileNotFoundException | BadConfigFormatException e) {
			System.err.println("Unable to initialize board: " + e.getMessage());
		}
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {

		this.roomMap.clear();
		this.roomInitials.clear();
		this.deck.clear();

		try (Scanner scan = new Scanner(new File("data", setupConfigFile))) {

			int lineNumber = 0;

			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				lineNumber++;

				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}

				String[] tokens = line.split(",", -1);
				
				//setting up rooms and spaces

				if (tokens.length == 3) {
					System.out.println("executing inside if statement when length is 3");
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
						Card card = new Card (CardType.ROOM, name);
						deck.add(card);
						
						
						
					}
				}
				//setting up weapons
				if (tokens.length == 2) {
					System.out.println("executing inside if statement when length is 2");
					String type = tokens[0].trim();
					String name = tokens[1].trim();
					if (!type.equalsIgnoreCase("weapon")) {
						throw new BadConfigFormatException("Unknown setup entry type on line "+ lineNumber);
					}
					Card card = new Card (CardType.WEAPON, name);
					System.out.println(card);
					deck.add(card);
				}
				//setting up Players and player card in the deck
				if (tokens.length == 6) {
					System.out.println("executing inside if statement when length is 6");
					String personToken = tokens[0].trim();
					String name = tokens[1].trim();
					Color color = getColor(tokens[2]);
					String player = tokens[3].trim();
					int row = 0;
					int col = 0;
					try {
						row = Integer.parseInt(tokens[4].trim());
						col = Integer.parseInt(tokens[5].trim());
					}
					catch(NumberFormatException e) {
						System.out.println("token is not an int on line: "+ lineNumber);
					}
					
					if (!personToken.equalsIgnoreCase("Person")) {
						throw new BadConfigFormatException("Unknown setup entry type on line "+ lineNumber);
					}
					Card card = new Card (CardType.PERSON, name);
					deck.add(card);
					
					switch(player) {
						case "Human":
							this.person = new Player(name, color, true, row, col); System.out.println(this.person); break;
						case "Com1":
							this.com1 = new Player(name, color, false, row, col); System.out.println(this.com1); break;
						case "Com2":
							this.com2 = new Player(name, color, false, row, col); System.out.println(this.com2);break;
						case "Com3":
							this.com3 = new Player(name, color, false, row, col); System.out.println(this.com3);break;
						case "Com4":
							this.com4 = new Player(name, color, false, row, col); System.out.println(this.com4);break;
						case "Com5":
							this.com5 = new Player(name, color, false, row, col); System.out.println(this.com5);break;
					}
					
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
	private Color getColor(String colorName) {
	    switch (colorName.trim().toLowerCase()) {
	        case "pink":
	            return Color.PINK;
	        case "red":
	            return Color.RED;
	        case "yellow":
	            return Color.YELLOW;
	        case "white":
	            return Color.WHITE;
	        case "green":
	            return Color.GREEN;
	        case "blue":
	            return Color.BLUE;
	        default:
	            throw new IllegalArgumentException("Unknown color: " + colorName);
	    }
	}

}
