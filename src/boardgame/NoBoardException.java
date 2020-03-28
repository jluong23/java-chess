package boardgame;

@SuppressWarnings("serial")
public class NoBoardException extends Exception {
	private Piece p;
	
	public NoBoardException(Piece p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}
	
	public Piece getPiece() {
		return p;
	}
}
