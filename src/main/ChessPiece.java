
package main;

import java.util.ArrayList;
import java.util.Arrays;

import boardgame.*;
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
	public ArrayList<Coordinate> getMoveableTiles(Direction dir, int numTiles) {
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
			//piece can move to coordinate if the square is empty 
			Piece obstruction = getBoard().at(coordinate);
			if(obstruction == null)
				moveableTiles.add(coordinate); 
			else {
				//obstruction must be a piece
				foundPiece = true;
				if(obstruction.getPlayer().getColour() != this.getPlayer().getColour()) {
					//if they don't share the same colour, this piece can attack if this piece attacks in same direction as it moves
					if(Arrays.equals(this.getMoveableDirections(), this.getAttackableDirections())){
						moveableTiles.add(coordinate); 						
					}
				}
			}
			numSearched++;
			
		}
		return moveableTiles;
	}
	
}
