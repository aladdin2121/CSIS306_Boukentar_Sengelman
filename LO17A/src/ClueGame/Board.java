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
import java.awt.Color;
import java.util.Collection;
import java.util.Collections;

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

	private List<Player> players;
	private Set<Card> deck;
	private Solution answer;

	private static final Board theInstance = new Board();

	private Board() {
		super();

		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.roomMap = new HashMap<>();
		this.roomInitials = new HashSet<>();

		this.players = new ArrayList<>();
		this.deck = new HashSet<>();
		this.answer = Solution.getInstance();
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
			this.players.clear();
			this.deck.clear();

			loadSetupConfig();
			loadLayoutConfig();
			loadPlayers();
			dealCards();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			System.err.println("Unable to initialize board: " + e.getMessage());
		}
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {

		this.roomMap.clear();
		this.roomInitials.clear();
		this.deck.clear();

		try (Scanner scan = new Scanner(new File("data", this.setupConfigFile))) {

			int lineNumber = 0;

			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				lineNumber++;

				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}

				String[] tokens = line.split(",", -1);

				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}

				String type = tokens[0];

				if (type.equalsIgnoreCase("room") || type.equalsIgnoreCase("space")) {

					if (tokens.length != 3) {
						throw new BadConfigFormatException("Invalid setup entry on line " + lineNumber);
					}

					String name = tokens[1];
					String symbol = tokens[2];

					if (name.isEmpty() || symbol.length() != 1) {
						throw new BadConfigFormatException("Invalid setup entry on line " + lineNumber);
					}

					char initial = symbol.charAt(0);

					if (this.roomMap.containsKey(initial)) {
						throw new BadConfigFormatException("Duplicate setup symbol: " + initial);
					}

					this.roomMap.put(initial, new Room(name));

					if (type.equalsIgnoreCase("room")) {
						this.roomInitials.add(initial);
						this.deck.add(new Card(CardType.ROOM, name));
					}

				} else if (type.equalsIgnoreCase("person")) {

					if (tokens.length != 5 && tokens.length != 6) {
						throw new BadConfigFormatException("Invalid person entry on line " + lineNumber);
					}

					if (tokens[1].isEmpty() || tokens[2].isEmpty()) {
						throw new BadConfigFormatException("Invalid person entry on line " + lineNumber);
					}

					this.deck.add(new Card(CardType.PERSON, tokens[1]));

				} else if (type.equalsIgnoreCase("weapon")) {

					if (tokens.length != 2 || tokens[1].isEmpty()) {
						throw new BadConfigFormatException("Invalid weapon entry on line " + lineNumber);
					}

					this.deck.add(new Card(CardType.WEAPON, tokens[1]));

				} else {
					throw new BadConfigFormatException("Unknown setup entry type on line " + lineNumber);
				}
			}
		}

		if (this.roomMap.isEmpty()) {
			throw new BadConfigFormatException("The setup file contains no entries.");
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

		List<String[]> layout = new ArrayList<>();

		try (Scanner scan = new Scanner(new File("data", this.layoutConfigFile))) {

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

		this.numRows = layout.size();
		this.numColumns = layout.get(0).length;

		if (this.numColumns == 0) {
			throw new BadConfigFormatException("The layout file has no columns.");
		}

		// Validate every row and cell before creating the grid.
		for (int row = 0; row < this.numRows; row++) {
			String[] tokens = layout.get(row);

			if (tokens.length != this.numColumns) {
				throw new BadConfigFormatException("Incorrect number of columns in row " + row);
			}

			for (int col = 0; col < this.numColumns; col++) {
				validateCellToken(tokens[col], row, col);
			}
		}

		this.targets.clear();
		this.visited.clear();

		for (Room room : this.roomMap.values()) {
			room.setCenterCell(null);
			room.setLabelCell(null);
		}

		this.grid = new BoardCell[this.numRows][this.numColumns];

		// Create and initialize every cell.
		for (int row = 0; row < this.numRows; row++) {
			for (int col = 0; col < this.numColumns; col++) {

				String token = layout.get(row)[col];
				char initial = token.charAt(0);

				BoardCell cell = new BoardCell(row, col);

				cell.setInitial(initial);
				cell.setRoom(this.roomInitials.contains(initial));
				cell.setEmpty(initial == 'X');
				cell.setIsDoorWay(false);

				if (token.length() == 2) {
					char marker = token.charAt(1);

					if (this.isDirectionMarker(marker)) {
						cell.setDirection(this.getDirection(marker));
						cell.setIsDoorWay(true);
					} else if (marker == '*') {
						cell.setRoomCenter(true);
						this.roomMap.get(initial).setCenterCell(cell);
					} else if (marker == '#') {
						cell.setLabel(true);
						this.roomMap.get(initial).setLabelCell(cell);
					} else {
						cell.setSecretPassage(marker);
					}
				}

				this.grid[row][col] = cell;
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

	private void loadPlayers() throws FileNotFoundException, BadConfigFormatException {

		try (Scanner scan = new Scanner(new File("data", this.setupConfigFile))) {

			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();

				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}

				String[] tokens = line.split(",", -1);

				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}

				if (!tokens[0].equalsIgnoreCase("person")) {
					continue;
				}

				String name = tokens[1];
				Color color = getColor(tokens[2]);

				boolean human;
				int row;
				int col;

				if (tokens.length == 6) {
					if (tokens[3].equalsIgnoreCase("human")) {
						human = true;
					} else if (tokens[3].equalsIgnoreCase("com") || tokens[3].equalsIgnoreCase("computer")) {
						human = false;
					} else {
						throw new BadConfigFormatException("Invalid player type for " + name);
					}

					row = Integer.parseInt(tokens[4]);
					col = Integer.parseInt(tokens[5]);

				} else if (tokens.length == 5) {
					// If no player type is provided, the first player is human.
					human = this.players.isEmpty();

					row = Integer.parseInt(tokens[3]);
					col = Integer.parseInt(tokens[4]);

				} else {
					throw new BadConfigFormatException("Invalid person entry for " + name);
				}

				if (row < 0 || row >= this.numRows || col < 0 || col >= this.numColumns) {
					throw new BadConfigFormatException("Invalid starting position for " + name);
				}

				BoardCell location = this.grid[row][col];

				if (human) {
					this.players.add(new HumanPlayer(name, color, location));
				} else {
					this.players.add(new ComputerPlayer(name, color, location));
				}
			}
		}

		if (!this.players.isEmpty()) {
			int humanCount = 0;

			for (Player player : this.players) {
				if (player.isHuman()) {
					humanCount++;
				}
			}

			if (this.players.size() != 6 || humanCount != 1) {
				throw new BadConfigFormatException(
						"The setup file must contain six players and exactly one human player.");
			}
		}
	}

	private Color getColor(String color) throws BadConfigFormatException {
		switch (color.toLowerCase()) {
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
			throw new BadConfigFormatException("Invalid player color: " + color);
		}
	}

	private void dealCards() throws BadConfigFormatException {

		// Older setup files used for the board tests do not contain players or weapons.
		if (this.players.isEmpty()) {
			return;
		}

		int roomCount = 0;
		int personCount = 0;
		int weaponCount = 0;

		for (Card card : this.deck) {
			if (card.getType() == CardType.ROOM) {
				roomCount++;
			} else if (card.getType() == CardType.PERSON) {
				personCount++;
			} else if (card.getType() == CardType.WEAPON) {
				weaponCount++;
			}
		}

		if (roomCount != 9 || personCount != 6 || weaponCount != 6) {
			throw new BadConfigFormatException("The deck must contain nine rooms, six people, and six weapons.");
		}

		List<Card> cards = new ArrayList<>(this.deck);
		Collections.shuffle(cards);

		Card room = null;
		Card person = null;
		Card weapon = null;

		for (Card card : cards) {
			if (card.getType() == CardType.ROOM && room == null) {
				room = card;
			} else if (card.getType() == CardType.PERSON && person == null) {
				person = card;
			} else if (card.getType() == CardType.WEAPON && weapon == null) {
				weapon = card;
			}
		}

		if (room == null || person == null || weapon == null) {
			throw new BadConfigFormatException("Unable to select the solution cards.");
		}

		this.answer.setSolution(room, person, weapon);

		cards.remove(room);
		cards.remove(person);
		cards.remove(weapon);

		int playerIndex = 0;

		for (Card card : cards) {
			this.players.get(playerIndex).updateHand(card);
			playerIndex++;

			if (playerIndex == this.players.size()) {
				playerIndex = 0;
			}
		}
	}

	private void calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				BoardCell cell = grid[row][col];
				// if the cell is X then it is skipped

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

	
	public boolean checkAccusation(Solution accusation) {
		boolean correct = accusation.getRoom().equals(answer.getRoom())
				&& accusation.getPerson().equals(answer.getPerson())
				&& accusation.getWeapon().equals(answer.getWeapon());

		return correct;
	}

	
	public Card handleSuggestion(int suggestingPlayerIndex, Solution suggestion) {
		List<Player> players = new ArrayList<>(this.players);

	    int numberOfPlayers = players.size();

	    for (int i = 1; i < numberOfPlayers; i++) {

	        int currentIndex = (suggestingPlayerIndex + i) % numberOfPlayers;

	        Player currentPlayer = players.get(currentIndex);

	        Card disprovingCard = currentPlayer.disproveSuggestion(suggestion);

	        if (disprovingCard != null) {
	            return disprovingCard;
	        }
	    }

	    return null;
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

	public Collection<Player> getPlayers() {
		return players;
	}

	public Collection<Card> getDeck() {
		return deck;
	}

	public Solution getAnswer() {
		return answer;
	}
}
