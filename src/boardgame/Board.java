package boardgame;

import java.util.ArrayList;

public abstract class Board {
	private Piece[][] board;
	private ArrayList<Player> players;
	public Board(ArrayList<Player> players){
		this.players = players;
	}
	
	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
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
			out+="|";
		}
		return out;
	}
	
	public abstract void reset();
}
