package boardgame;

import java.util.ArrayList;
import java.util.HashMap;

import boardgame.exceptions.NoBoardException;

public class Player {

	private Colour colour;
	private int score;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> piecesTaken;

	
	public Player(Colour colour) {
		this.colour = colour;
		this.myPieces = new ArrayList<Piece>();
		this.piecesTaken = new ArrayList<Piece>();
		score = 0;
	}
	
	/**
	 * @return the colour
	 */
	public Colour getColour() {
		return colour;
	}
	/**
	 * @param colour the colour to set
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the myPieces
	 */
	public ArrayList<Piece> getMyPieces() {
		return myPieces;
	}
	/**
	 * @param myPieces the myPieces to set
	 */
	public void setMyPieces(ArrayList<Piece> myPieces) {
		this.myPieces = myPieces;
	}
	/**
	 * @return the piecesTaken
	 */
	public ArrayList<Piece> getPiecesTaken() {
		return piecesTaken;
	}
	/**
	 * @param piecesTaken the piecesTaken to set
	 */
	public void setPiecesTaken(ArrayList<Piece> piecesTaken) {
		this.piecesTaken = piecesTaken;
	}

	/**
	 * Fetches an arrayList of pieces from myPieces by piece name (all occurunces)
	 * @param pieceName - String of piece name requested (by class name)
	 * @return pieces - arraylist of pieces in myPieces, empty arraylist if none found.
	 */
	public ArrayList<Piece> getPieces(String pieceName) {
		ArrayList<Piece> piecesFound = new ArrayList<Piece>();
		for (Piece piece : myPieces) {
			if(piece.getName().equalsIgnoreCase(pieceName)) {
				piecesFound.add(piece);
			}
		}
		return piecesFound;	
	}
	
	/**
	 * Get a hash map of all possible moves for a player of a given action
	 * @param action - The action of a certain move
	 * @return allMoves - A hashmap of piece to Arraylist of coordinates which are the given action a piece can make 
	*/
	public HashMap<Piece, ArrayList<Coordinate>> getAllValidMoves(Action action) throws NoBoardException{
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, ArrayList<Coordinate>> allMoves= new HashMap<Piece, ArrayList<Coordinate>>();
		for (Piece piece : myPieces) {
			allMoves.put(piece, piece.getValidMoves(action));
		}
		return allMoves;
	}
	/**
	 * Get a hash map of all possible moves for a player 
	 * @return allMoves - A hashmap of piece to Arraylist of coordinates a piece can perform to
	*/
	public HashMap<Piece, ArrayList<Coordinate>> getAllValidMoves() throws NoBoardException{
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, ArrayList<Coordinate>> allMoves= new HashMap<Piece, ArrayList<Coordinate>>();
		for (Piece piece : myPieces) {
			allMoves.put(piece, piece.getAllValidMoves());
		}
		return allMoves;
	}
	
	

}
