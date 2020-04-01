
package main;

import java.util.ArrayList;

import boardgame.*;
import boardgame.exceptions.NoPlayerAttributeException;
import main.pieces.Rook;


public abstract class ChessPiece extends Piece {
	private ChessPieceNames name;
	
	private static int DEFAULT_DISTANCE = 7;

	public ChessPiece(Player player){
		super(player);
		//name attribute is the class name as an enum
		name = ChessPieceNames.valueOf(getClass().getSimpleName().toUpperCase());
		setChessSymbol();
		//by default, moveable and attack distance can reach the whole board
		//reset by pawn and king piece
		setMoveableDistance(DEFAULT_DISTANCE);
		setAttackDistance(DEFAULT_DISTANCE);
	}

	/**
	 * Fetch and set the symbol attribute for this instance.
	 * @throws NoPlayerAttributeException When no players are in the game, don't know what colour symbol to fetch, exit.
	 */
	public void setChessSymbol() throws NoPlayerAttributeException {
		if(getPlayer() == null) {
			throw new NoPlayerAttributeException(this);
		}else {
			Colour colour = getPlayer().getColour();
			char symbol = this.name.getSymbol(name,colour);
			this.setSymbol(symbol);			
		}		
	}
	
	@Override
	//TODO	
	public ArrayList<Coordinate> getMoveableTiles(Direction dir, int numTiles) {
		int numSearched = 0;
		//index and coordinates to check for in direction dir, initially current position
		int[] indexes = this.getPosition().getIndexes();
		Coordinate coordinate = ChessCoordinate.toCoordinate(indexes);
		
		while (numSearched <= numTiles && coordinate.isValid()) {
			//move in given direction
			indexes[0] += dir.dr;
			indexes[1] += dir.dc;
			System.out.println(coordinate);
			numSearched++;
		}
		return null;
	}
	
}
