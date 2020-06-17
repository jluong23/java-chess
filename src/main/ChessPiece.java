
package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import boardgame.Action;
import boardgame.Colour;
import boardgame.Coordinate;
import boardgame.Direction;
import boardgame.Piece;
import boardgame.Player;
import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.InvalidSettingsException;
import boardgame.exceptions.NoBoardException;
import boardgame.exceptions.NoPlayerAttributeException;
import main.pieces.King;


public abstract class ChessPiece extends Piece {
	private static int DEFAULT_DISTANCE = 7;

	public ChessPiece(Player player){
		super(player);
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
			char symbol = ChessPieceNames.getSymbol(getName(),colour);
			this.setSymbol(symbol);			
		}		
	}
	
	@Override
	public Set<Coordinate> getTotalMoves(Action action) {
		//get which list of directions to check for given an action
		Direction[] directions = null;
		int numTiles = 0;
		switch(action) {
		case ATTACK:
			directions = getAttackableDirections();
			numTiles = getAttackDistance();
			break;
		case MOVE_TO:
			directions = getMoveableDirections();
			numTiles = getMoveableDistance();
			break;
		}
		
		//initialise array list of tiles that are moveable to
		Set<Coordinate> tilesCovered = new HashSet<Coordinate>();
		
		for (Direction dir : directions) {
			ArrayList<Coordinate> tilesInDir = new ArrayList<Coordinate>();

			int numSearched = 0;
			//index and coordinates to check for in direction dir, initially current position
			int[] indexes = this.getPosition().getIndexes();
			Coordinate coordinate = ChessCoordinate.toCoordinate(indexes);
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
				
				//if the action is possible, make the move. does not include checks
				if(action.conditionMet(this, coordinate))tilesInDir.add(coordinate);
				numSearched++;
			}
			tilesCovered.addAll(tilesInDir);
		}
		return tilesCovered;
	}
	
	/**
	 * Return all of the adjacent coordinates in a given direction next to a piece.
	 * This method will go through obstructions until the end of the board is reached.
	 * For chess, this is used for checking if a king can castle.
	 * @param dir - the direction to look at
	 * @return tiles - arrayList of coordinates to pieces found from piece looking in that direction
	 */
	protected ArrayList<Coordinate> getAdjacentTiles(Direction dir) {
		ArrayList<Coordinate> tiles = new ArrayList<Coordinate>();
		//index and coordinates to check for in direction dir, initially current position
		int[] indexes = this.getPosition().getIndexes();
		Coordinate coordinate = ChessCoordinate.toCoordinate(indexes);
		while (coordinate.isValid()) {
			//move in given direction
			indexes[0] += dir.dr;
			indexes[1] += dir.dc;
			try {
				//try update the coordinate with new indexes
				coordinate = ChessCoordinate.toCoordinate(indexes);
			} catch (InvalidCoordinateException e) {
				//coordinate is invalid, must be at the edge of a board so return tiles
				return tiles;
			}
			tiles.add(coordinate);
		}
		//shouldn't be ran since it will always go towards the edge of the board, calling the return
		// in the catch statement. 
		return null;
		
	}
	@Override
	public boolean validMove(Coordinate coordinate) throws NoBoardException, InvalidSettingsException {
		if(getBoard() == null) throw new NoBoardException(this);
		else if(((ChessBoard) getBoard()).isKingRequired()){
			if(getPlayer().getPieces("King", true).size() == 0) {
				//king not found, throw an error when kingRequired is on, throw an error
				throw new InvalidSettingsException("board.kingRequired",((ChessBoard)getBoard()).isKingRequired());				
			}
			else {
				//king has been found
				King myKing = (King) getPlayer().getPieces("King", true).get(0);
				//if myKing is this piece, must be looking for possible king moves
				boolean checkingKingMoves = this.equals(myKing);
				
				boolean valid = false;

				makePseudoMove(coordinate);
				//check if king is in check after pseudo move is played
				//If looking for moves for king, check if this king is in check
				if(checkingKingMoves && !((King)(this)).inCheck()) {
					valid = true;
				}else if(!checkingKingMoves && !myKing.inCheck()) {
					//this piece is not a king, check if it puts their king in check
					valid = true;										
				}
				popPseudoMove();
				
				return valid;	
			}
		}
		
		//king required is off, return true
		return true;
	}
	
	@Override
	protected void captureConsequnce(Piece pieceCaptured) {
		// TODO Auto-generated method stub
		this.getPlayer().getPiecesTaken().add(pieceCaptured);
		pieceCaptured.setAlive(false);
		pieceCaptured.setPosition(null);
		
	}

	
}
