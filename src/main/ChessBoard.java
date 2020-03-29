package main;

import java.util.ArrayList;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Player;
import boardgame.exceptions.InvalidLayoutException;
import boardgame.exceptions.TooManyPlayersException;

public class ChessBoard extends Board {
	
	private Layout layout;
	private Piece[][] emptyBoard = new Piece[8][8];
	
	public ChessBoard(Layout layout, ArrayList<Player> players) {
		super(players);
		this.layout = layout;
		//set to emptyBoard initially to access board methods when changing to given layout
		setBoard(emptyBoard);
		setBoardStyle(layout);
	}
	/**
	 * Create a board given a particular layout from Layout enum
	 * @param layout the layout for the chess board
	 * @return board 2d array of the board with the given layout
	 */
	public void setBoardStyle(Layout layout) throws InvalidLayoutException {
		if(layout == null) throw new InvalidLayoutException("Null layout detected");
		else {
			switch(layout) {
			case STANDARD:
				setBoard(createDefaultBoard());
				break;
			default:
				throw new InvalidLayoutException("Layout does not exist");
			}						
		}
	}
	
	/** 
	 * TODO Should return layout of chess board as 2d array of pieces
	 * @return
	 */
	private Piece[][] createDefaultBoard() throws TooManyPlayersException{
		if(getPlayers().size() != 2) {
			throw new TooManyPlayersException("There are too many players, must only be 2");
		}else {
			
		}
	}

	@Override
	public void reset() {
		setBoardStyle(layout);
	}
}