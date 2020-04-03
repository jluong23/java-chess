package main;

import java.util.ArrayList;

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
		String coordString = getCoordinate();
		if(new ChessCoordinate(coordString).isValid()) {
			char[] coords = this.getCoordinate().toCharArray();
			return coordinateCharactersToIndex(coords);
		}
		else {
			throw new InvalidCoordinateException("Coordinate must be from a1 to h8");
		}
	}
	
	private int[] coordinateCharactersToIndex(char[] coords) {
			int rowIndex = -coords[1] + '8';	
			int colIndex = coords[0] - 'a';
			int[] indexes = {rowIndex, colIndex};
			return indexes;
	}
	
	private static char[] indexToCoordinateCharacters(int[] indexes) {
		char rowValue = (char)('8' - indexes[0]);
		char colValue = (char)(indexes[1] + 'a');
		char[] coordChars = {colValue,rowValue};
		return coordChars;
	}
	
	/**
	 * Turn a pair of indexes into a single coordinate
	 * @param indexes the indexes on the board 
	 * @return coordinate the new coordinate object given indexes
	 * @throws InvalidCoordinateException If the coordinate is invalid and can't be converted
	 */
	public static Coordinate toCoordinate(int[] indexes) throws InvalidCoordinateException{
		String coordString = new String(indexToCoordinateCharacters(indexes));
		Coordinate coord = new ChessCoordinate(coordString);
		if(coord.isValid())return coord;
		else {
			throw new InvalidCoordinateException(coordString);
		}
	}
	
	/**
	 * 
	 * @param coordinateStrings array of strings to convert into coordinate objects
	 * @return newCoords the coordinate objects with coordinate attributes from coordinateStrings
	 * @throws InvalidCoordinateException If the coordinate is invalid and can't be converted
	 */
	public static ArrayList<Coordinate> toCoordinate(String[] coordinateStrings) throws InvalidCoordinateException {
		ArrayList<Coordinate> newCoords = new ArrayList<Coordinate>();
		for (String coordinateString : coordinateStrings) {
			Coordinate coordinate = new ChessCoordinate(coordinateString);
			if(coordinate.isValid()) {
				newCoords.add(coordinate);
			}
			else {
				throw new InvalidCoordinateException(coordinateString);
			}			
		}
		return newCoords;
	
	}

}
