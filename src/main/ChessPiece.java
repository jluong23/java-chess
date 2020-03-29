
package main;

import java.util.HashMap;
import java.util.Map;

import boardgame.*;
import boardgame.exceptions.NoPlayerAttributeException;
import boardgame.exceptions.TooManyPlayersException;


public abstract class ChessPiece extends Piece {
	private ChessPieceEnum name;
	
	public ChessPiece(Player player){
		super(player);
		//name attribute is the class name as an enum
		name = ChessPieceEnum.valueOf(getClass().getSimpleName().toUpperCase());
		setChessSymbol();
	}

	
	/**
	 * Fetch and set the symbol attribute for a chess piece, using the static method getSymbol().
	 * Called in the constructor of ChessPiece class
	 * @throws TooManyPlayersException
	 */
	public void setChessSymbol() throws TooManyPlayersException {
		if(getPlayer() == null) {
			throw new NoPlayerAttributeException(this);
		}else {
			Colour colour = getPlayer().getColour();
			char symbol = getSymbol(name,colour);
			this.setSymbol(symbol);			
		}		
	}
	/**
	 * map colour to map of pieces to character
	 * colour and piece are essentially two keys for character which is a coloured piece 
	 * @param piece the piece wanted as ChessPieceEnum class
	 * @param colour the colour wanted as Colour enum class
	 * @return symbol - symbol of the coloured piece
	 */
	public static char getSymbol(ChessPieceEnum piece, Colour colour) {
		
		HashMap<Colour, Map <ChessPieceEnum, Character> > piecesHashMap = new HashMap<>();
		//populate map
		for (Colour c : Colour.values()) {
			HashMap<ChessPieceEnum, Character> symbolsHashMap = new HashMap<>();
			ChessPieceEnum[] pieceNames = ChessPieceEnum.values();
			char[] symbols = ChessPieceEnum.getSymbols(c);
			//for each piece, map to according symbol
			for (int i = 0; i < pieceNames.length; i++) {
				symbolsHashMap.put(pieceNames[i], symbols[i]);
			}
			//map colour to symbolsHashMap in 2d hashmap
			piecesHashMap.put(c,symbolsHashMap);
		}
		return piecesHashMap.get(colour).get(piece);
	}
}
