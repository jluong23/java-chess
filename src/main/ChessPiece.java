
package main;

import java.util.ArrayList;

import boardgame.*;
import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.InvalidSettingsException;
import boardgame.exceptions.NoBoardException;
import boardgame.exceptions.NoPlayerAttributeException;
import main.pieces.King;


public abstract class ChessPiece extends Piece {
	private static int DEFAULT_DISTANCE = 7;

	public ChessPiece(Player player){
		super(player);
		//name attribute is the class name as an enum
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
	public ArrayList<Coordinate> getTotalMoves(Action action) {
		
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
		ArrayList<Coordinate> tilesCovered = new ArrayList<Coordinate>();
		
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
	 * Looks for a piece in a given direction starting from the pieces 
	 * @param dir - the direction to look at
	 * @return piece - the piece found from looking in that direction
	 */
	protected Piece pieceAhead(Direction dir) {
		//index and coordinates to check for in direction dir, initially current position
		int[] indexes = this.getPosition().getIndexes();
		Coordinate coordinate = ChessCoordinate.toCoordinate(indexes);
		boolean foundPiece = false;
		while (!foundPiece) {
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
		}
		if(foundPiece) {
			return getBoard().at(coordinate);
		}else {
			return null;
		}
	}
	public boolean validMove(Coordinate coordinate) throws NoBoardException, InvalidSettingsException {
		if(getBoard() == null) throw new NoBoardException(this);
		else if(((ChessBoard) getBoard()).isKingRequired()){
			King myKing = (King) getPlayer().getPiece("King");
			
			//if myKing is this piece, must be looking for possible king moves
			boolean checkingKingMoves = this.equals(myKing);
			
			if(myKing == null) throw new InvalidSettingsException("board.kingRequired",((ChessBoard)getBoard()).isKingRequired());
			else {
				//temp piece is copy of this piece to place at the coordinate
				Piece tempPiece = this.deepClonePlayerAttribute();
				boolean valid = false;
				//the piece that this piece is capturing, can be null if empty space 
				Piece capturedPiece = getBoard().at(coordinate);

				//store original position, place back after moving the piece
				Coordinate originalPos = getPosition();
				//emulate the move made, move temp piece to the coordinate checked and set previous position to null
				getBoard().setPiece(coordinate, tempPiece);
				getBoard().setPiece(this.getPosition(), null);
				
				//check if king is in check.
				//If looking for moves for king, check if temp piece is in check
				if(checkingKingMoves && !((King)(tempPiece)).inCheck()) {
					valid = true;
				}else if(!checkingKingMoves && !myKing.inCheck()) {
					valid = true;										
				}
				//reset back to original, place captured piece back and remove tempKing from player pieces list
				getBoard().setPiece(originalPos, this);
				getBoard().setPiece(coordinate, capturedPiece);
				this.getPlayer().getMyPieces().remove(tempPiece);

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
		pieceCaptured.getPlayer().getMyPieces().remove(pieceCaptured);
		pieceCaptured.setPosition(null);
		
	}
	
}
