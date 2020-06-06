package main.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import boardgame.*;
import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.NoBoardException;
import main.ChessCoordinate;
import main.ChessPiece;

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}
	@Override
	//TODO
	public Set<Coordinate> getTotalMoves(Action action) throws NoBoardException{
		if(getBoard() == null)throw new NoBoardException(this); //if the piece does not have a board attribute
		else {
			int sign = 1;
			//knights can move 3 possible tiles
			final int TILES_MOVED = 3;
			//current positions
			int r = getPosition().getIndexes()[0];
			int c = getPosition().getIndexes()[1];
			Set<Coordinate> possibleCoordinates = new HashSet<Coordinate>();
			
			
			for (int i = 0; i < 2; i++) { 
				//i iterations: check both up and down for each change in column dc by flipping sign
				sign*=-1;
				for (int dc = -2; dc <= 2; dc++) {
					if(dc!= 0) { //skip change in column being 0, knight can't move straight up vertically
						int dr = sign * (TILES_MOVED - Math.abs(dc)); 
						try {
							Coordinate coordinate = ChessCoordinate.toCoordinate(new int[] {r+dr,c+dc});
							//if the action can be performed, push to possibleCoordinates
							if(action.conditionMet(this, coordinate))possibleCoordinates.add(coordinate);
						} catch (InvalidCoordinateException e) {
							//do nothing if invalid coordinate found, move to next
						}
					}
				}
			}
			
			return possibleCoordinates;
		}
	}
}
