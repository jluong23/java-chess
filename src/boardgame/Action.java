package boardgame;

public enum Action {
	ATTACK, MOVE_TO;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
