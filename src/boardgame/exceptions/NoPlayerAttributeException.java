package boardgame.exceptions;

import boardgame.Piece;

@SuppressWarnings("serial")
public class NoPlayerAttributeException extends RuntimeException {
	private Piece p;
	
	public NoPlayerAttributeException(Piece p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}	
	
	public Piece getPiece() {
		return p;
	}
}
