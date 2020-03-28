package main;

import java.util.ArrayList;

import boardgame.*;

public class King extends ChessPiece {

	public King(Player player) {
		super(player);
	}
	@Override
	public ArrayList<Coordinate> getPossibleMoves() throws NoBoardException{
		if(this.getBoard() == null) {
			throw new NoBoardException(this);
		}
		else {
			ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
			return moves;
		}
	}


}
