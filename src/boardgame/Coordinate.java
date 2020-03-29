package boardgame;

public abstract class Coordinate {
	private String coordinate;

	public Coordinate(String coordinate) {
		this.coordinate = coordinate.toLowerCase();
	}
	/**
	 * Try split the coordinate into array of two chars.
	 * Throw InvalidCoordinateException if fails, else return
	 * @return coordinateSplit - array of chess coordinate as chars, eg. (a5 => ['a', '5'] ) 
	 * @throws InvalidCoordinateException
	 */
	public char[] split() throws InvalidCoordinateException{
		if(!this.isValid()) throw new InvalidCoordinateException(coordinate);
		else {
			char[] coordinateSplit = coordinate.toCharArray();
			return coordinateSplit;
		}
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
	 */
	public abstract int[] getIndexes();
		
	
	/**
	 * Checks if a coordinate is valid, must be implemented
	 * @param coordinate - Test coordinate as string 
	 * @return boolean - whether result is valid or not
	 */
	public abstract boolean isValid();
	
	@Override
	public String toString() {
		return coordinate;
	}
	
	
}
