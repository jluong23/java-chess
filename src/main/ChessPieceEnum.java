package main;
import boardgame.Colour;

public enum ChessPieceEnum {
	//these should be in the same order 
	PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;
	private static final char[] WHITE_SYMBOLS = {'♙','♘','♗','♖','♕', '♔'};
	private static final char[] BLACK_SYMBOLS = {'♟','♞','♝','♜','♛', '♚'};
	
	public static char[] getSymbols(Colour colour) {
		switch(colour) {
		case BLACK:
			return BLACK_SYMBOLS;
		case WHITE:
			return WHITE_SYMBOLS;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}

}

