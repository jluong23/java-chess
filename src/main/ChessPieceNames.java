package main;

import java.util.NoSuchElementException;

import boardgame.Colour;
import boardgame.exceptions.InvalidColourException;

public enum ChessPieceNames{
	//each piece as enum contains according unicode symbol as an attribute
	PAWN('♙','♟', ""), 
	KNIGHT('♘','♞',"N"), 
	BISHOP('♗','♝' ,"B"), 
	ROOK('♖','♜', "R"), 
	QUEEN('♕','♛', "Q"), 
	KING('♔','♚', "K");
	public final char whiteSymbol;
	public final char blackSymbol;
	private final String symbol;
	
	private ChessPieceNames(char c, char d, String symbol) {
		this.whiteSymbol = c;
		this.blackSymbol = d;
		this.symbol = symbol;
	}
	
	/**
	 * Gets the icon for a requested piece and colour
	 * @param pieceName the piece requested as a string
	 * @param colour the colour requested
	 * @throws InvalidColourException thrown when colour is not found
	 * @return icon - the icon requested, throws InvalidColourException if colour is not found
	 */
	public static char getIcon(String pieceName, Colour colour) {
		ChessPieceNames pieceEnum = ChessPieceNames.valueOf(pieceName);
		
		switch(colour) {
		case BLACK:
			return pieceEnum.blackSymbol;
		case WHITE:
			return pieceEnum.whiteSymbol;
		default:
			throw new InvalidColourException("Could not find this colour, exiting...");
		}
	}
	
	/**
	 * Return a piece name as a string from a given symbol
	 * @param symbol - the symbol related to the piece
	 * @return the piece name as a string
	 */
	public static String getPieceName(String symbol) {
		for (ChessPieceNames piece : ChessPieceNames.values()) {
			if(piece.symbol.equalsIgnoreCase(symbol)) return piece.toString();
		}
		throw new NoSuchElementException("Could not find symbol " + symbol +  " in ChessPieceNames.getPieceName()");
	}
	
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}

}

