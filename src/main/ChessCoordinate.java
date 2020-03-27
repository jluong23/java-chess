package main;

import java.util.Arrays;

import boardgame.Coordinate;
import boardgame.InvalidCoordinateException;

public class ChessCoordinate extends Coordinate{
	private String coordinate;
	
	/**
	 * Constructor for ChessCoordinate class
	 * @param coordinate
	 */
	public ChessCoordinate(String coordinate) {
		super(coordinate);
	}

	@Override
	public boolean isValid() {
		char[] charCoordinates = this.getCoordinate().toCharArray();
		if(charCoordinates.length != 2) return false;
		else {
			char letter = charCoordinates[0];
			char row = charCoordinates[1];			
			//coordinate must be a1 to h8 
			boolean letterInRange = letter >= 'a' && letter <= 'h';
			boolean rowInRange = row >= '1' && row <= '8';
			return letterInRange && rowInRange;
		}
	}
	
	@Override
	public int[] getIndexes() {
		char[] coords = new char[2];
		try {
			coords = this.split();				
		} catch (InvalidCoordinateException e) {
			System.out.println("Invalid coordinate has been handled, must be from a1 to h8");
			e.printStackTrace();
			System.exit(0);
		}
		//case where split is successful, return as indexes
		int rowIndex = -coords[1] + '8';	
		int colIndex = coords[0] - 'a';
		int[] indexes = {rowIndex, colIndex};
		return indexes;
	}
	
	public static void main(String[] args) {
		ChessCoordinate m = new ChessCoordinate("z");
		System.out.println(Arrays.toString(m.getIndexes()));
	}

}
