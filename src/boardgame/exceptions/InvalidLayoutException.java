package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidLayoutException extends RuntimeException {
	private String msg;
	
	public InvalidLayoutException(String msg) {
		this.msg = msg;
	}
	
	public String getLayout() {
		return msg;
	}
}
