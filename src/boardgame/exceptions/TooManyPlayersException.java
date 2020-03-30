package boardgame.exceptions;

@SuppressWarnings("serial")
public class TooManyPlayersException extends RuntimeException {
	private String s;
	
	public TooManyPlayersException(String s) {
		// TODO Auto-generated constructor stub
		this.s = s;
	}	
	
	public String getPiece() {
		return s;
	}
}
