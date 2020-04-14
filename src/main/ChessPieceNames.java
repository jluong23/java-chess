package main;

import boardgame.Colour;
import boardgame.exceptions.InvalidColourException;

public enum ChessPieceNames{
	//each piece as enum contains according unicode symbol as an attribute
	PAWN('♙','♟'), 
	KNIGHT('♘','♞'), 
	BISHOP('♗','♝'), 
	ROOK('♖','♜'), 
	QUEEN('♕','♛'), 
	KING('♔','♚');
	public final char whiteSymbol;
	public final char blackSymbol;
	
	private ChessPieceNames(char c, char d) {
		this.whiteSymbol = c;
		this.blackSymbol = d;
	}
	
	/**
	 * Gets the symbol for a requested piece and colour
	 * @param piece the piece requested as a string
	 * @param colour the colour requested
	 * @throws InvalidColourException thrown when colour is not found
	 * @return symbol - the symbol requested, throws InvalidColourException if colour is not found
	 */
	public static char getSymbol(String piece, Colour colour) {
		ChessPieceNames pieceEnum = ChessPieceNames.valueOf(piece);
		
		switch(colour) {
		case BLACK:
			return pieceEnum.blackSymbol;
		case WHITE:
			return pieceEnum.whiteSymbol;
		default:
			throw new InvalidColourException("Could not find this colour, exiting...");
		}
	}
	
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}

}

