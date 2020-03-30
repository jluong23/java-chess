package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidColourException extends RuntimeException {
	private String s;
	
	public InvalidColourException(String s) {
		// TODO Auto-generated constructor stub
		this.s = s;
	}	
	
	public String getPiece() {
		return s;
	}
}
