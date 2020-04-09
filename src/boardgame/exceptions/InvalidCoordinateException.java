package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidCoordinateException extends RuntimeException {
	private String c;
	
	public InvalidCoordinateException(String c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	@Override
	public String toString() {
		return c + " is an invalid coordinate";
	}
}
