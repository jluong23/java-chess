package board;

public abstract class Piece {
	
	private String symbol;
	private Colour colour;
	
	public Piece(Colour colour) {
		this.colour = colour;
	}
	
	public abstract Coordinate[] getPossibleMoves();
	
	
}
