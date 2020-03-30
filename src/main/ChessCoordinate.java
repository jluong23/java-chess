package main;

import boardgame.Coordinate;
import boardgame.exceptions.InvalidCoordinateException;

public class ChessCoordinate extends Coordinate{	
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
	/**
	 * 
	 * @param indexes the indexes on the board 
	 * @return coordinate the new coordinate object given indexes
	 * @throws InvalidCoordinateException if the coordinate is invalid and can't be converted
	 */
	public static Coordinate toCoordinate(int[] indexes) throws InvalidCoordinateException{
		char rowValue = (char)('8' - indexes[0]);
		char colValue = (char)(indexes[1] + 'a');
		char[] coordChars = {colValue,rowValue};
		String coordString = new String(coordChars);
		ChessCoordinate coord = new ChessCoordinate(coordString);
		if(coord.isValid())return coord;
		else {
			throw new InvalidCoordinateException(coordString);
		}
	}
	
	@Override
	/**
	 * Returns whether two chess coordinates are the same
	 */
	public boolean equals(Object obj) {
		if(this.getClass() == obj.getClass()) {
			return ((ChessCoordinate) obj).getCoordinate() ==this.getCoordinate();
		}else {
			return false;
		}
	}
		

}
