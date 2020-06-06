package boardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	 * @param alive - if the piece is alive or not
	 * @return pieces - arraylist of pieces in myPieces, empty arraylist if none found.
	 */
	public ArrayList<Piece> getPieces(String pieceName, boolean alive) {
		ArrayList<Piece> piecesFound = new ArrayList<Piece>();
		for (Piece piece : myPieces) {
			if(piece.getName().equalsIgnoreCase(pieceName) && piece.isAlive() == alive) {
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
	public HashMap<Piece, Set<Coordinate>> getAllValidMoves(Action action) throws NoBoardException{
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, Set<Coordinate>> allMoves= new HashMap<Piece, Set<Coordinate>>();
		for (Piece piece : myPieces) {
			allMoves.put(piece, piece.getValidMoves(action));
		}
		return allMoves;
	}
	/**
	 * Get a hash map of all possible valid moves for a player 
	 * @return allMoves - A hashmap of piece to Arraylist of coordinates a piece can perform to
	*/
	public HashMap<Piece, Set<Coordinate>> getAllValidMoves() throws NoBoardException{
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, Set<Coordinate>> allMoves= new HashMap<Piece, Set<Coordinate>>();
		for (Piece piece : myPieces) {
			allMoves.put(piece, piece.getAllValidMoves());
		}
		return allMoves;
	}
	/**
	 * Get a hashmap of all possible moves for a player (not necessarily valid moves) of a certain action.
	 * For chess, used to see what enemy the squares are covering.
	 * @param action - The action of a certain move
	 * @return allMoves - A hashmap of piece to Arraylist of coordinates which are the given action a piece makes
	 */
	public HashMap<Piece, Set<Coordinate>> getTotalMoves(Action action) {
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, Set<Coordinate>> totalMoves= new HashMap<Piece, Set<Coordinate>>();
		for (Piece piece : myPieces) {
			totalMoves.put(piece, piece.getTotalMoves(action));
		}
		return totalMoves;
	}
	/**
	 * Get a hashmap of all possible moves for a player (not necessarily valid moves)
	 * For chess, used to see what enemy the squares are covering.
	 * @param action - The action of a certain move
	 */
	public HashMap<Piece, Set<Coordinate>> getTotalMoves() {
		//player's pieces mapped to all the moves of a given action the piece can make
		HashMap<Piece, Set<Coordinate>> totalMoves= new HashMap<Piece, Set<Coordinate>>();
		for (Piece piece : myPieces) {
			totalMoves.put(piece, piece.getAllTotalMoves());
		}
		return totalMoves;
	}
	
	
	
	

}
