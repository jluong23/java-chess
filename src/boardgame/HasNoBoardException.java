package boardgame;

@SuppressWarnings("serial")
public class HasNoBoardException extends Exception {
	private Piece p;
	
	public HasNoBoardException(Piece p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}
	
	public Piece getPiece() {
		return p;
	}
}
