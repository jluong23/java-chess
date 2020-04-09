package boardgame;

import boardgame.exceptions.InvalidCoordinateException;
import main.ChessCoordinate;

public abstract class Coordinate implements java.lang.Comparable<Coordinate>{
	private String coordinate;

	/**
	 * Create new coordinate object
	 * @param coordinate the coordinate, converted to lower case
	 */
	public Coordinate(String coordinate) {
		this.coordinate = coordinate.toLowerCase();
	}
	/**
	 * @return the coordinate
	 */
	public String getCoordinate() {
		return coordinate;
	}

	/**
	 * Map coordinate string to indexes on 2d array board, must be implemented
	 * @return indexes - indexes of coordinate in board array, row and column indexes respectively in array
	 * @throws InvalidCoordinateException 
	 */
	public abstract int[] getIndexes() throws InvalidCoordinateException;
	
	
	/**
	 * Checks if a coordinate is valid, must be implemented.
	 * Returns true by default.
	 * @param coordinate - Test coordinate as string 
	 * @return boolean - whether result is valid or not
	 */
	public abstract boolean isValid();
		
	@Override
	public String toString() {
		return coordinate;
	}
	
	@Override
	public int compareTo(Coordinate o) {
		// TODO Auto-generated method stub
		return getCoordinate().compareTo(o.getCoordinate());
	}
	
	@Override
	/**
	 * Returns whether two chess coordinates are the same
	 */
	public boolean equals(Object obj) {
		if(this.getClass() == obj.getClass()) {
			return ((Coordinate) obj).getCoordinate().contentEquals(this.getCoordinate());
		}else {
			return false;
		}
	}
	
	
	
	
}
