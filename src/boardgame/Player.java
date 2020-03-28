package boardgame;

import java.util.ArrayList;

public class Player {

	private Colour colour;
	private int score;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> piecesTaken;

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
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

}
