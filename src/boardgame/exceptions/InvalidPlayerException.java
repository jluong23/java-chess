package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidPlayerException extends RuntimeException {
	private String c;
	
	public InvalidPlayerException(String c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	@Override
	public String toString() {
		return c;
	}
}
