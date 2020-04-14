package boardgame;

import boardgame.exceptions.InvalidAction;

public enum Action {
	ATTACK, MOVE_TO;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	/**
	 * Returns whether an action can be met for a given piece at a target coordinate
	 * @param piece - the piece wanting to perform the action
	 * @param coordinate - the target coordinate the piece wants to perform the action on
	 * @return result - whether the piece can perform the action
	 * @throws InvalidAction - If the action is invalid
	 */
	public boolean conditionMet(Piece piece, Coordinate coordinate) {
		Piece obstruction = piece.getBoard().at(coordinate);
		switch (this) {
		case ATTACK:
			//piece can only attack if opposite colours
			return obstruction != null && obstruction.getPlayer().getColour() != piece.getPlayer().getColour();
		case MOVE_TO:
			return obstruction == null;
		default:
			throw new InvalidAction(this);
		}

	}
}
