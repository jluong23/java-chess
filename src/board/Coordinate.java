package board;

import java.util.Arrays;

import exceptions.InvalidCoordinateException;

public class Coordinate {
	private String coordinate;

	public Coordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	/**
	 * Try split the coordinate into array of two chars.
	 * Throw InvalidCoordinateException if fails, else return.
	 * @return coordinateSplit - array of chess coordinate as chars, eg. (a5 => ['a', '5'] ) 
	 * @throws InvalidCoordinateException
	 */
	private char[] split() throws InvalidCoordinateException{
		if(!Coordinate.isValid(coordinate)) throw new InvalidCoordinateException(coordinate);
		else {
			char[] coordinateSplit = coordinate.toCharArray();
			return coordinateSplit;
		}
	}
	/**
	 * Map coordinate string (a1) to coordinate on 2d array board
	 * from white's perspective [7,7]
	 * @return indexes - indexes of coordinate in board array, row and column indexes respectively in array
	 */
	public int[] getIndexes(){
		char[] coords = new char[2];
		try {
			coords = this.split();				
		} catch (InvalidCoordinateException e) {
			System.out.println("Invalid coordinate has been handled, must be from a1 to h8");
			e.printStackTrace();
			System.exit(0);
		}
		
		//case where split is successful, return as indexes
		//subtract by starting values, returning indexes (0..7)
		int rowIndex = coords[1] - '1';	
		int colIndex = coords[0] - 'a';
		int[] indexes = {rowIndex, colIndex};
		return indexes;
		
	}
	/**
	 * Checks if a chess coordinate is valid
	 * @param coordinate - Test coordinate as string 
	 * @return boolean - whether result is valid or not
	 */
	public static boolean isValid(String coordinate) {
		char[] charCoordinates = new char[2];
		charCoordinates = coordinate.toCharArray();
		char letter = Character.toLowerCase(charCoordinates[0]);
		char row = charCoordinates[1];
		
		//coordinate must be a1 to h8 
		boolean letterInRange = letter >= 'a' && letter <= 'h';
		boolean rowInRange = row >= '1' && row <= '8';
		return charCoordinates.length == 2 && letterInRange && rowInRange;
	}
	
	public static void main(String[] args) {
		Coordinate m = new Coordinate("h7");
		System.out.println(Arrays.toString(m.getIndexes()));
	}
}
