package board;

import java.util.Arrays;

import exceptions.InvalidCoordinateException;

public class Coordinate {
	private String coordinate;
	
	
	public Coordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	private String[] split() throws InvalidCoordinateException{
		//coordinate must be a1 to h8 
		String[] coordinateSplit = coordinate.split("(?<=[a-hA-H])(?=[1-8])");
		if(coordinateSplit.length < 2 ) throw new InvalidCoordinateException(coordinate);
		else { return coordinateSplit;}
	}
	
	/**
	 * Map coordinate string (a1) to coordinate on 2d array board
	 * from white's perspective [7,7]
	 * @return
	 */
	public void getIndexes(){
		try {
			String[] coords = this.split();				
			System.out.println(Arrays.toString(coords));
		} catch (InvalidCoordinateException e) {
			System.out.println("Invalid coordinate has been handled, must be from a1 to h8");
		}
		
	}
	
	public static void main(String[] args) {
		Coordinate m = new Coordinate("a9");
		m.getIndexes();
	}
}
