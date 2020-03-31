package main;

import java.util.ArrayList;
import java.util.List;

import boardgame.*;
import boardgame.exceptions.*;
import main.pieces.*;

public class ChessBoard extends Board {
	
	private Layout layout;
	private static Piece[][] emptyBoard = new Piece[8][8];
	
	
	public ChessBoard(Layout layout, List<Player> players) {
		super(players);
		this.layout = layout;
		//set to emptyBoard initially to access board methods when changing to given layout
		setBoardArray(emptyBoard);
		setBoardStyle(layout);
	}
	/**
	 * Set a board given a particular layout from Layout enum
	 * @param layout the layout for the chess board
	 */
	public void setBoardStyle(Layout layout) throws InvalidLayoutException {
		if(layout == null) throw new InvalidLayoutException("Null layout detected");
		else {
			switch(layout) {
			case STANDARD:
				setDefaultBoard();
				break;
			default:
				throw new InvalidLayoutException("Layout does not exist");
			}						
		}
	}
	/**
	 * Sets current board to default board layout
	 * @throws TooManyPlayersException
	 * @throws InvalidColourException
	 */
	private void setDefaultBoard() throws TooManyPlayersException, InvalidColourException{
		if(getPlayers().size() != 2) {
			throw new TooManyPlayersException("There are too many players, must only be 2");
		}else {
			ArrayList<Coordinate> pawnRankCoords, backRankCoords; //indexes for row numbers for ranks
			for (Player player: getPlayers()) {
				
				//select what rank the pieces should be based on colour
				switch (player.getColour()) {
				case BLACK:
					pawnRankCoords = this.getRowCoordinates(1);
					backRankCoords = this.getRowCoordinates(0);
					break;
				case WHITE:
					pawnRankCoords = this.getRowCoordinates(getBoardArray().length-2);
					backRankCoords = this.getRowCoordinates(getBoardArray().length-1);
					break;
				default:
					throw new InvalidColourException("Chess only has white or black colours, please reset");
				}
				
				final int NUM_PIECES_IN_RANK = 8;
				//create the pieces, assigning the player to them
				ArrayList<Piece> pawns = new Pawn(player).clone(NUM_PIECES_IN_RANK);
				Piece[] specialPieces = {new Rook(player), new Knight(player), new Bishop(player), new Queen(player),
				new King(player), new Bishop(player), new Knight(player), new Rook(player)};
				
				for (int i = 0; i < NUM_PIECES_IN_RANK; i++) {
					this.setPiece(pawnRankCoords.get(i), pawns.get(i));
					this.setPiece(backRankCoords.get(i), specialPieces[i]);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String out = super.toString();
		//include letters of chess board
		out+="   a b c d e f g h";
		return out;
	}
	@Override
	public void reset() {
		setBoardStyle(layout);
	}
}