
package main;

import java.util.ArrayList;
import java.util.Arrays;

import boardgame.*;
import boardgame.exceptions.InvalidAction;
import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.NoPlayerAttributeException;


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
	protected ArrayList<Coordinate> searchTiles(Direction dir, int numTiles, Action action) {
		int numSearched = 0;
		//index and coordinates to check for in direction dir, initially current position
		int[] indexes = this.getPosition().getIndexes();
		Coordinate coordinate = ChessCoordinate.toCoordinate(indexes);
		//initialise array list of tiles that are moveable to
		ArrayList<Coordinate> moveableTiles = new ArrayList<Coordinate>();
		//boolean set to true when other piece is found, blocking movement of this piece
		boolean foundPiece = false;
		while (numSearched < numTiles && !foundPiece) {
			//move in given direction
			indexes[0] += dir.dr;
			indexes[1] += dir.dc;
			try {
				//try update the coordinate with new indexes
				coordinate = ChessCoordinate.toCoordinate(indexes);
			} catch (InvalidCoordinateException e) {
				//coordinate is invalid, must be at the edge of a board so break out of loop
				break;
			}
			Piece obstruction = getBoard().at(coordinate);
			if(obstruction !=null)foundPiece = true;
			//conditions for actions to be met
			switch (action) {
			case ATTACK:
				//piece can only attack if opposite colours
				if(obstruction != null && obstruction.getPlayer().getColour() != this.getPlayer().getColour()) {
					moveableTiles.add(coordinate); 					
				}
				break;
			case MOVE_TO:
				//piece can keep moving if piece has not been found yet
				if(!foundPiece)moveableTiles.add(coordinate); 
				break;
			default:
				throw new InvalidAction(action);
			}
			numSearched++;
			
		}
		return moveableTiles;
	}
	
}
