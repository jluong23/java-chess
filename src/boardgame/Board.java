package boardgame;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public String toString() {
		String out = "";
		for (Piece[] row : board) {
			out+="|";
			for (Piece tile : row) {
				out+=tile;
			}
			out+="|\n";
		}
		return out;
	}
	
	public abstract void reset();
}
