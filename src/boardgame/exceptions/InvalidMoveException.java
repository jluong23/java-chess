package boardgame.exceptions;

import boardgame.Coordinate;
import boardgame.Piece;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception {
	private Piece piece;
	private Coordinate coordinate;
	
	public InvalidMoveException(Piece p, Coordinate c) {
		piece = p;
		coordinate = c;
	}	
	
	@Override
	public String toString() {
		return piece + "at " + piece.getPosition() + " can not move to " + coordinate;
	}
}
