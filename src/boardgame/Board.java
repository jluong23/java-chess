package boardgame;

import java.util.ArrayList;
import java.util.List;

import boardgame.exceptions.InvalidCoordinateException;
import main.ChessCoordinate;

public abstract class Board {
	private Piece[][] board;
	private List<Player> players;
	private List<Piece> pieces;

	public Board(List<Player> players2){
		this.players = players2;
		pieces = new ArrayList<Piece>();
	}
	
	/**
	 * @return the pieces
	 */
	public List<Piece> getPieces() {
		return pieces;
	}

	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
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
		//update piece position
		piece.setPosition(coordinate);
		//set board attribute of piece to this instance and vice versa
		piece.setBoard(this);
		this.pieces.add(piece);
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
	public ArrayList<Coordinate> getRowCoordinates(int row) {
		ArrayList<Coordinate> coords = new ArrayList<>();
		for (int i = 0; i < board[row].length; i++) {
			int[] indexes = {row,i};
			coords.add(ChessCoordinate.toCoordinate(indexes));
		}
		return coords;
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
}
