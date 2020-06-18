package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import boardgame.Board;
import boardgame.Colour;
import boardgame.Coordinate;
import boardgame.Piece;
import boardgame.Player;
import boardgame.exceptions.InvalidColourException;
import boardgame.exceptions.InvalidLayoutException;
import boardgame.exceptions.TooManyPlayersException;
import main.pieces.Bishop;
import main.pieces.King;
import main.pieces.Knight;
import main.pieces.Pawn;
import main.pieces.Queen;
import main.pieces.Rook;

public class ChessBoard extends Board {
	
	private Layout layout;
	private boolean kingRequired;
	
	public ChessBoard(Layout layout, List<Player> players) {
		super(players);
		this.layout = layout;

		
		//set as empty initially to access board methods while changing to given layout
		setBoardArray(new Piece[8][8]);
		setBoardStyle(layout);
		setRowAndColNames();

	}
	/**
	 * @return the kingRequired
	 */
	public boolean isKingRequired() {
		return kingRequired;
	}
	/**
	 * @param kingRequired the kingRequired to set
	 */
	public void setKingRequired(boolean kingRequired) {
		this.kingRequired = kingRequired;
	}
	/**
	 * Set a board given a particular layout from Layout enum and set kingRequired value
	 * STANDARD: King is required
	 * EMPTY: King is not required
	 * @param layout the layout for the chess board
	 */
	public void setBoardStyle(Layout layout) throws InvalidLayoutException {
		if(layout == null) throw new InvalidLayoutException("Null layout detected");
		else {
			switch(layout) {
			case STANDARD:
				setDefaultBoard();
				kingRequired = true;
				break;
			case EMPTY:
				//by default, king required is false.
				kingRequired = false;
				//just break for empty layout enum as the emptyBoard is already assigned
				break;
			default:
				throw new InvalidLayoutException("Layout does not exist");
			}
			
		}
	}
	/**
	 * Sets current board to default board layout
	 * @throws TooManyPlayersException
	 * @throws InvalidColourException
	 */
	private void setDefaultBoard() throws TooManyPlayersException, InvalidColourException{
		if(getPlayers().size() != 2) {
			throw new TooManyPlayersException("There are too many players, must only be 2");
		}else {
			ArrayList<Coordinate> pawnRankCoords, backRankCoords; //indexes for row numbers for ranks
			for (Player player: getPlayers()) {
				
				//select what rank the pieces should be based on colour
				switch (player.getColour()) {
				case BLACK:
					pawnRankCoords = this.getRowCoordinates(1);
					backRankCoords = this.getRowCoordinates(0);
					break;
				case WHITE:
					pawnRankCoords = this.getRowCoordinates(getBoardArray().length-2);
					backRankCoords = this.getRowCoordinates(getBoardArray().length-1);
					break;
				default:
					throw new InvalidColourException("Chess only has white or black colours, please reset");
				}
				
				final int NUM_PIECES_IN_RANK = 8;
				//create the pieces, assigning the player to them
				Piece[] specialPieces = {new Rook(player), new Knight(player), new Bishop(player), new Queen(player),
				new King(player), new Bishop(player), new Knight(player), new Rook(player)};
				
				for (int i = 0; i < NUM_PIECES_IN_RANK; i++) {
					this.setPiece(pawnRankCoords.get(i), new Pawn(player));
					this.setPiece(backRankCoords.get(i), specialPieces[i]);
				}
			}
		}
	}
	
	@Override
	public void reset() {
		setBoardStyle(layout);
	}
	@Override
	public void startGameLoop() {
		Player whitePlayer = this.getPlayer(Colour.WHITE);
		Player blackPlayer = this.getPlayer(Colour.BLACK);
		King whiteKing = (King) whitePlayer.getPieces("King", true).get(0);
		King blackKing = (King) blackPlayer.getPieces("King", true).get(0);
		//white starts first move
		setPlayerTurn(whitePlayer);
		//scanner object to take input from command line
		Scanner reader = new Scanner(System.in);
		//initialise while loop condition variables
		boolean draw = false, whiteWin = false, blackWin = false, quit = false; 
		
		while(!whiteWin && !blackWin && !draw && !quit) {
			//print the board
			System.out.println(this);
			//ask the current player to make a move
			System.out.print(getPlayerTurn().getColour() + "'s turn to move: ");
			boolean validMoveFound = false;
			
			while(!validMoveFound && !quit) {
				String move = reader.next();
				if(move.equalsIgnoreCase("quit")) quit = true;
				else if (makeMove(move)) {
					//if the move is valid, play it and change turn
						validMoveFound = true;
						changeTurn();
					}
				else {
					//move is invalid, ask for another move
					System.out.println("Invalid move. Try again: ");
				}
			}
			//update results of game for loop condition
			whiteWin = blackKing.inCheckmate();
			blackWin = whiteKing.inCheckmate();
			draw = whiteKing.inStalemate() || blackKing.inStalemate();
		}
		
		//end of game has been reached
		if(quit)System.out.println("Exiting the game...");
		else if(draw)System.out.println("DRAW");
		else {
			// the loser is the player that has the current turn, so the winner does not have the current turn
			Player winner = getPlayerTurn().equals(whitePlayer) ? blackPlayer : whitePlayer;
			System.out.println(winner.getColour() + " has won the game!");
		}
		reader.close();
		
	}
	
	@Override
	public boolean makeMove(String notation) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void setRowAndColNames() {		
		List<Character> rowNames = new ArrayList<Character>();
		List<Character> columnNames = new ArrayList<Character>();
		for (int i = 0; i < getBoardArray().length; i++) {
			//for white's perspective
			//from 8 to 1, top left to bottom
			rowNames.add((char) ('8' - i));
			//from a to b, left to right from
			columnNames.add((char)('A' + i));
		}
		setRowNames(rowNames);
		setColumnNames(columnNames);
	}
	
	public static void main(String[] args) {
		//test starting the game
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Player[] playersArray = {p1,p2};
		List<Player> players = Arrays.asList(playersArray);
		ChessBoard b = new ChessBoard(Layout.STANDARD,players);
		b.startGameLoop();

	}

	
	
	
}