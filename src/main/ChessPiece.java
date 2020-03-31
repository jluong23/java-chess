
package main;

import java.util.ArrayList;

import boardgame.*;
import boardgame.exceptions.NoPlayerAttributeException;


public abstract class ChessPiece extends Piece {
	private ChessPieceNames name;

	public ChessPiece(Player player){
		super(player);
		//name attribute is the class name as an enum
		name = ChessPieceNames.valueOf(getClass().getSimpleName().toUpperCase());
		setChessSymbol();
		//by default, moveable and attack distance can reach the whole board
		//overridden by pawn and king piece
		int d = getBoard().getBoardArray().length-1;
		setMoveableDistance(d);
		setAttackDistance(d);
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
