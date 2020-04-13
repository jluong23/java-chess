
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
			//set foundPiece to true to prevent piece going through another
			if(getBoard().at(coordinate) !=null)foundPiece = true;
			//if the action can be performed, push to moveableTiles
			if(action.conditionMet(this, coordinate))moveableTiles.add(coordinate);
			numSearched++;
			
		}
		return moveableTiles;
	}
	
	@Override
	protected void captureConsequnce(Piece pieceCaptured) {
		// TODO Auto-generated method stub
		this.getPlayer().getPiecesTaken().add(pieceCaptured);
		pieceCaptured.getPlayer().getMyPieces().remove(pieceCaptured);
		pieceCaptured.setPosition(null);
		
	}
	
}
