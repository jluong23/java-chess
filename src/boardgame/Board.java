package boardgame;

public abstract class Board {
	private Piece[][] board;
	
	public Board(Piece[][] board){
		this.board = board;
	}
	
	/**
	 * @return the board
	 */
	public Piece[][] getBoard() {
		return board;
	}
	
	public void set(Piece piece, Coordinate coordinate) {
		int r = coordinate.getIndexes()[0];
		int c = coordinate.getIndexes()[1];
		board[r][c] = piece;
	}
	
	public abstract void reset();
}
