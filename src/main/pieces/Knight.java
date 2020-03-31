package main.pieces;

import java.util.ArrayList;

import boardgame.*;
import boardgame.exceptions.NoBoardException;
import main.ChessPiece;

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}
	
	@Override
	//TODO
	public ArrayList<Coordinate> getPossibleMoves() throws NoBoardException{
		//knights need to override getPossibleMoves() as they move differently to other pieces
		return null;
	}
	
	@Override
	//TODO
	public ArrayList<Coordinate> getSquaresAttacking() {
		//knights need to override getSquaresAttacking() as they move differently to other pieces
		return null;
	}


}
