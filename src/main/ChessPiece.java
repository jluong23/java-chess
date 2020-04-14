
package main;

import java.lang.reflect.InvocationTargetException;
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
			if(action.conditionMet(this, coordinate) && validMove(coordinate))
				moveableTiles.add(coordinate);
			
			numSearched++;
			
		}
		return moveableTiles;
	}
	
	@Override
	public boolean validMove(Coordinate coordinate) throws NoBoardException, InvalidSettingsException {
		if(getBoard() == null) throw new NoBoardException(this);
		else if(((ChessBoard) getBoard()).isKingRequired()){
			King myKing = (King) getPlayer().getPiece("King");
			if(myKing == null) throw new InvalidSettingsException("board.kingRequired",((ChessBoard)getBoard()).isKingRequired());
			else {
				//temp piece is copy of this piece to place at the coordinate
				Piece tempPiece = null;
				try {
					//try make a copy of this piece, sharing the player attribute
					tempPiece = (Piece) Class.forName(this.getClass().getName()).getConstructor(Player.class).newInstance(getPlayer());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				boolean valid = false;
				//the piece that this piece is capturing, can be null if empty space 
				Piece capturedPiece = getBoard().at(coordinate);
				
				//place the temp piece at the coordinate, check if king with same colour is in check
				getBoard().setPiece(coordinate, tempPiece);
				if(!myKing.inCheck())valid = true;
				
				//reset back to original, place captured piece back and remove tempKing from player pieces list
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
