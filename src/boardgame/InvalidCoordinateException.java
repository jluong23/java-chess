package boardgame;

@SuppressWarnings("serial")
public class InvalidCoordinateException extends RuntimeException {
	private String c;
	
	public InvalidCoordinateException(String c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	public String getCharacter() {
		return c;
	}
}
