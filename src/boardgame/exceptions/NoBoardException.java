package boardgame.exceptions;

import boardgame.Piece;

@SuppressWarnings("serial")
public class NoBoardException extends RuntimeException {
	private Piece p;
	
	public NoBoardException(Piece p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}
	
	public Piece getPiece() {
		return p;
	}
}
