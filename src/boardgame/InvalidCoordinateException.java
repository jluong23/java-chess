package boardgame;

public class InvalidCoordinateException extends Exception {
	private String c;
	
	public InvalidCoordinateException(String c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	public String getCharacter() {
		return c;
	}
}
