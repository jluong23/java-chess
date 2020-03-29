package main;

import java.util.ArrayList;

import boardgame.Board;
import boardgame.InvalidLayoutException;
import boardgame.Piece;
import boardgame.Player;

public class ChessBoard extends Board {
	
	private Layout layout;
	
	public ChessBoard(Layout layout, ArrayList<Player> players) {
		
		super(create(layout), players);
		this.layout = layout;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create a board given a particular layout from Layout enum
	 * @param layout the layout for the chess board
	 * @return board 2d array of the board with the given layout
	 */
	public static Piece[][] create(Layout layout) throws InvalidLayoutException {
		if(layout == null) throw new InvalidLayoutException("Null layout detected");
		switch(layout) {
		case DEFAULT:
			return createDefaultBoard();
		default:
			throw new InvalidLayoutException("Layout does not exist");
		}			
	}
	
	/** 
	 * TODO Should return layout of chess board as 2d array of pieces
	 * @return
	 */
	private static Piece[][] createDefaultBoard() {
		
		return null;
	}

	@Override
	public void reset() {
		create(layout);
	}
}