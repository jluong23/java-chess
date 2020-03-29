package main.pieces;

import java.util.ArrayList;

import boardgame.*;
import main.ChessPiece;

public class Pawn extends ChessPiece {

	public Pawn(Player player) {
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
