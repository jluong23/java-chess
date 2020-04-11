package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidAction extends RuntimeException {
	private boardgame.Action action;
		
	public InvalidAction(boardgame.Action action2) {
		action = action2;
	}	
	
	@Override
	public String toString() {
		return action + " is not available for this game";
	}

}
