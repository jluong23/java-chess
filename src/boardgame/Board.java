package boardgame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;

import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.InvalidPlayerException;
import boardgame.exceptions.NoPlayerAttributeException;
import boardgame.exceptions.TooManyPlayersException;
import main.ChessCoordinate;
import main.pieces.King;

public abstract class Board {
	private Piece[][] board;
	private List<Player> players;

	public Board(List<Player> players2){
		this.players = players2;
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
	
	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < board.length; i++) {
			out+= (board.length - i) + " |";
			for (Piece tile : board[i]) {
				if(tile == null) out+="  "; //if nothing on tile, print empty space
				else out+=tile + " ";					
			}
			out+="|\n  ------------------\n";
		}
		return out;
	}
	
	public abstract void reset();
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
			HashMap<Piece, ArrayList<Coordinate>> playerSquaresAttacked = player.getTotalMoves();
			for (ArrayList<Coordinate> tileCoveredList : playerSquaresAttacked.values()) {
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
}
