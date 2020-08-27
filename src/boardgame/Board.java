package boardgame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.InvalidPlayerException;
import boardgame.exceptions.TooManyPlayersException;
import main.ChessCoordinate;

public abstract class Board {
	private Piece[][] board;
	private List<Player> players;
	private Player playerTurn;
	private int moveCounter;
	private List<Coordinate> moves;
	private List<String> rowNames;
	private List<String> columnNames;


	public Board(List<Player> players2){
		this.players = players2;
	}
	
	/**
	 * @return the playerTurn
	 */
	public Player getPlayerTurn() {
		return playerTurn;
	}

	/**
	 * @param playerTurn the playerTurn to set
	 */
	public void setPlayerTurn(Player playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	/**
	 * Get all of the pieces on this board. The pieces checked are the player's pieces in the players list
	 * @return The pieces on this board
	 */
	public List<Piece> getPieces() {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (Player player : players) {
			pieces.addAll(player.getMyPieces());
		}
		return pieces;
	}
	
	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Returns the first occurrence of a player with a given colour
	 * @param colour - the colour the player should have
	 * @return the player object or null if no player has the colour parameter
	 */
	public Player getPlayer(Colour colour) {
		for (Player player : getPlayers()) {
			if(player.getColour().equals(colour)) return player;
		}
		return null;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	/**
	 * @param board the board to set
	 */
	public void setBoardArray(Piece[][] board) {
		this.board = board;
	}

	/**
	 * @return the board
	 */
	public Piece[][] getBoardArray() {
		return board;
	}
	/**
	 * Set piece at coordinate in board array
	 * @param coordinate - new position for piece
	 * @param piece - piece to place at coordinate
	 */
	public void setPiece(Coordinate coordinate, Piece piece) {
		int r = coordinate.getIndexes()[0];
		int c = coordinate.getIndexes()[1];
		board[r][c] = piece;
		if (piece!=null) {
			piece.setAlive(true);
			//update piece position
			piece.setPosition(coordinate);
			//set board attribute of piece to this instance and vice versa
			piece.setBoard(this);
		}
	}

	/**
	 * Return what is on the board at a given coordinate
	 * @param coordinate the coordinate to check 
	 * @return result -  the object at the coordinate on the board, null if empty
	 */
	public Piece at(Coordinate coordinate) {
		if(!coordinate.isValid()) {
			throw new InvalidCoordinateException("Tried to use invalid coordinate, exiting...");
		}else {
			int[] indexes = coordinate.getIndexes();
			return board[indexes[0]][indexes[1]];
		}
		
	}
	
	/**
	 * Return all coordinates at a given row
	 * @param row the row of coordinates requested
	 * @return the coordinates fetched as an ArrayList
	 */
	protected ArrayList<Coordinate> getRowCoordinates(int row) {
		ArrayList<Coordinate> coords = new ArrayList<>();
		for (int i = 0; i < board[row].length; i++) {
			int[] indexes = {row,i};
			coords.add(ChessCoordinate.toCoordinate(indexes));
		}
		return coords;
	}
	/**
	 * Returns all of the pieces on a given row index
	 * @param rowIndex - the row index to search on
	 * @return pieces - the pieces on the board at the given row index
	 */
	public ArrayList<Piece> getPiecesOnRow(int rowIndex) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (Coordinate coordinate: getRowCoordinates(rowIndex)) {
			Piece piece = this.at(coordinate);
			if(piece != null)pieces.add(piece);
		}
		return pieces;
	}
	
	/**
	 * Get the first occurunce of a piece on the board by row
	 * @param pieceName - the piece to get by name
	 * @param row - the row to get from using row names list attribute
	 * @return The piece on the row, null if there is no piece
	 */
	public Piece getPieceOnRow(String pieceName, String row) {
		int rowIndex = getRowNames().indexOf(row);
		for (Piece piece : board[rowIndex]) {
			if(piece.getName().equalsIgnoreCase(pieceName)) return piece;
		}
		return null;
	}
	
	/**
	 * Get the first occurunce of a piece on the board by column
	 * 
	 * @param pieceName - the piece to get by name
	 * @param col - th ecolumn to get from using column names list attribute
	 * @return The piece on the column, null if there is no piece
	 */
	public Piece getPieceOnCol(String pieceName, String col) {
		int colIndex = getColumnNames().indexOf(col);
		for (Piece[] row : board) {
			Piece piece = row[colIndex];
			if(piece.getName().equalsIgnoreCase(pieceName)) return piece;
			
		}
		return null;
	}
	
	
	/**
	 * Return result if list of pieces are on the same column
	 * @param pieces
	 * @return
	 */
	public boolean shareColumn(List<Piece> pieces) {
		//true until counter example is found
		boolean shared = true;
		String firstColumn = pieces.get(0).getPosition().getCoordinate().split("")[0];
		for (int i = 0; i < pieces.size(); i++) {
			String column = pieces.get(i).getPosition().getCoordinate().split("")[0];
			//columns should be equal between all pieces
			if(firstColumn != column) shared = false;
		}
		return shared;
	}
	
	/**
	 * @return the moveCounter
	 */
	public int getMoveCounter() {
		return moveCounter;
	}

	/**
	 * @param moveCounter the moveCounter to set
	 */
	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	/**
	 * @return the moves
	 */
	public List<Coordinate> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(List<Coordinate> moves) {
		this.moves = moves;
	}
	
	/**
	 * @return the columnNames
	 */
	public List<String> getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames2 the columnNames to set
	 */
	public void setColumnNames(List<String> columnNames2) {
		this.columnNames = columnNames2;
	}

	/**
	 * @return the rowNames
	 */
	public List<String> getRowNames() {
		return rowNames;
	}

	/**
	 * @param rowNames2 the rowNames to set
	 */
	public void setRowNames(List<String> rowNames2) {
		this.rowNames = rowNames2;
	}

	@Override
	public String toString() {
		String out = "";
		final String SPACE = "  ";
		for (int i = 0; i < board.length; i++) {
			//write down the row value 
			out+= getRowNames().get(i) + " |";
			for (int j = 0; j < board[i].length; j++) {
				Piece tile = board[i][j];
				if(tile == null) out+=SPACE; //if nothing on tile, print empty space
				else out+=tile.getFirstLetterNameWithColourPrefix();				
				out += "|";
			}
			
			out+="\n  -------------------------\n";
			
		}
		//write down the column names
		out+=SPACE + SPACE;
		for (String col : getColumnNames()) {
			out+= col + " ";
		}
		return out;
	}
	
	/**
	 * Change the turn of the game by changing playerTurn attribute.
	 * By default, functions by taking the next player in the players attribute, wrapping around
	 * to the beginning if on the last player.
	 */
	protected void changeTurn() {
		int currentIndex = players.indexOf(playerTurn);
		if(currentIndex == players.size() - 1) setPlayerTurn(players.get(0));
		else setPlayerTurn(players.get(currentIndex+1));
		
	}
	
	/**
	 * Starts the game
	 */
	public abstract void startGameLoop();
	
	/**
	 * Should reset the board to it's layout attribute and reset piece lists (captured and owned) to original for all players
	 */
	public abstract void reset();
	
	/**
	 * Make a move given the move notation
	 * @param notation - the move notation
	 * @return boolean result whether the move was successfully played. Should return false for invalid notation
	 */
	public abstract boolean makeMove(String notation);
	
	
	
	/**
	 * Return if a coordinate is attacked by a player
	 * @param coordinate - the coordinate to check if attacked
	 * @param player - the player to check who is attacking the coordinate
	 * @return isAttacked - whether the coordinate is attacked by the player
	 */
	public boolean squareAttacked(Coordinate coordinate, Player player) {
		if(coordinate == null)throw new InvalidCoordinateException("Null coordinate in squareAttacked()");
		else if(player == null) throw new InvalidPlayerException("Null player in squareAttacked()");
		else {
			//get attacked squares by player. If contains the coordinate parameter, square is attacked, return true
			HashMap<Piece, Set<Coordinate>> playerSquaresAttacked = player.getTotalMoves();
			for (Set<Coordinate> tileCoveredList : playerSquaresAttacked.values()) {
				if(tileCoveredList.contains(coordinate)) return true;
			}
			//exhausted all player attacks, coordinate is not attacked, return false
			return false;
		}
		
	}

	/**
	 * Given there are only two players on the board, getOtherPlayer() will return the other player
	 * given one of the players
	 * @param player - the known player on the board
	 * @return otherPlayer - the other player on the board
	 */
	public Player getOtherPlayer(Player knownPlayer) {
		if(players.size() != 2)throw new TooManyPlayersException("Need two players for getOtherPlayer()");
		else {
			for (Player player : players) {
				if(player != knownPlayer) return player;
			}			
		}
		return null; //returns null if other player can't be found, shouldn't happen
	}

	/**
	 * This method should populate rowNames and columnNames using the public setters for these attributes. 
	 * This is required to print the board with correct axes.
	 */
	public abstract void setRowAndColNames();


}
