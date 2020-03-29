package boardgame;

@SuppressWarnings("serial")
public class NoPlayerException extends RuntimeException {
	private Piece p;
	
	public NoPlayerException(Piece p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}	
	
	public Piece getPiece() {
		return p;
	}
}
