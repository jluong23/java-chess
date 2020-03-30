package boardgame;

import java.util.ArrayList;
import java.util.List;

import main.ChessCoordinate;

public abstract class Board {
	private Piece[][] board;
	private List<Player> players;
	public Board(List<Player> players2){
		this.players = players2;
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
	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	/**
	 * @return the board
	 */
	public Piece[][] getBoard() {
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
		for (Piece[] row : board) {
			out+="|";
			for (Piece tile : row) {
				if(tile == null) out+="  "; //if nothing on tile, print empty space
				else out+=tile + " ";					
			}
			out+="|\n------------------\n";
		}
		return out;
	}
	
	public abstract void reset();
}
