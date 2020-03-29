package boardgame;

@SuppressWarnings("serial")
public class InvalidLayoutException extends RuntimeException {
	private String s;
	
	public InvalidLayoutException(String s) {
		this.s = s;
	}
	
	public String getLayout() {
		return s;
	}
}
