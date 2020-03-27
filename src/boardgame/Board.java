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
	/**
	 * Set piece at coordinate in board array
	 * @param coordinate - new position for piece
	 * @param piece - piece to place at coordinate
	 */
	public void set(Coordinate coordinate, Piece piece) {
		int r = coordinate.getIndexes()[0];
		int c = coordinate.getIndexes()[1];
		board[r][c] = piece;
		//update piece position
		piece.setPosition(coordinate);
	}
	
	public void print() {
		for (Piece[] row : board) {
			System.out.print("|");
			for (Piece tile : row) {
				System.out.print(tile);
			}
			System.out.println("|");
		}
	}
	
	public abstract void reset();
}
