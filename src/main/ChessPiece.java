
package main;

import java.util.HashMap;
import java.util.Map;

import boardgame.*;


public abstract class ChessPiece extends Piece {
	private ChessPieceEnum name;
	
	public ChessPiece(Player player){
		super(player);
		//name attribute is the class name as an enum
		name = ChessPieceEnum.valueOf(getClass().getSimpleName().toUpperCase());
		try {
			setChessSymbol();
		} catch (NoPlayerException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Fetch and set the symbol attribute for a chess piece
	 * @throws NoPlayerException
	 */
	public void setChessSymbol() throws NoPlayerException {
		if(getPlayer() == null) {
			throw new NoPlayerException(this);
		}else {
			Colour colour = getPlayer().getColour();
			char symbol = getSymbol(name,colour);
			this.setSymbol(symbol);			
		}		
	}
	/**
	 * map colour to map of pieces to character
	 * colour and piece are essentially two keys for character which is a coloured piece 
	 * @param piece
	 * @param colour
	 * @return
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
