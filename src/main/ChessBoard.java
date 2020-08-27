package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import boardgame.Action;
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
	private void setBoardStyle(Layout layout) throws InvalidLayoutException {
		if(layout == null) throw new InvalidLayoutException("Null layout detected");
		else {
			switch(layout) {
			case STANDARD:
				kingRequired = true;
				setDefaultBoard();
				break;
			case EMPTY:
				kingRequired = false;
				//just break for empty layout enum as the emptyBoard is already assigned
				break;
			case TEST_TWO_KNIGHTS:
				kingRequired = true;
				setTestTwoKnightsBoard();
				break;
			default:
				throw new InvalidLayoutException("Layout does not exist");
			}
			this.layout = layout;

		}
	}
	private void setTestTwoKnightsBoard() {
		if(getPlayers().size() != 2) {
			throw new TooManyPlayersException("There are too many players, must only be 2");
		}else {
			setPiece(new ChessCoordinate("A1"), new Knight(getPlayer(Colour.WHITE)));
			setPiece(new ChessCoordinate("C1"), new Knight(getPlayer(Colour.WHITE)));
			setPiece(new ChessCoordinate("B1"), new King(getPlayer(Colour.WHITE)));
			
			setPiece(new ChessCoordinate("A8"), new Knight(getPlayer(Colour.BLACK)));
			setPiece(new ChessCoordinate("C8"), new Knight(getPlayer(Colour.BLACK)));
			setPiece(new ChessCoordinate("B8"), new King(getPlayer(Colour.BLACK)));
			setPiece(new ChessCoordinate("B3"), new Pawn(getPlayer(Colour.BLACK)));


		}
	}
	/**
	 * Sets current board to default board layout
	 * @throws TooManyPlayersException
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
		
		while(!(whiteWin || blackWin || draw || quit)) {
			//print the board
			System.out.println(this);
			//ask the current player to make a move
			System.out.print(getPlayerTurn().getColour() + "'s turn to move: ");
			boolean validMoveFound = false;
			
			while(!(validMoveFound || quit)) {
				String move = reader.next();
				if(move.equalsIgnoreCase("quit")) quit = true;
				else if (makeMove(move)) {
					//if the move is valid, play it and change turn
						validMoveFound = true;
						changeTurn();
					}
				else {
					//move is invalid, ask for another move
					System.out.print("Invalid move. Try again: ");
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
		
		//castle the king if its a castling move
		if(Castle.isCastlingMove(new ChessCoordinate(notation))) {
			King playerKing = (King) getPlayerTurn().getPieces("King", true).get(0);
			//return the result of trying to castle
			return playerKing.move(new ChessCoordinate(notation));
			
		}else {
			//not a castling move, standard move
				
			String[] notationList = notation.split("");
			//find the piece name
			String pieceName = ChessPieceNames.getPieceName(notationList[0]);		
			
			if(pieceName == null && getColumnNames().contains(notationList[0].toLowerCase())) {
				//if first character is a column and returned null from above
				//must be a pawn move like e4 or axb2
				pieceName = ChessPieceNames.PAWN.toString();
			}else if(pieceName == null){
				//not a column and null so invalid move
				return false;
			}
				
			
//			//capturing move if there is a capture (x)
			boolean isCaptureMove = Arrays.asList(notationList).contains("x");
			
			//final coordinate
			String coordinateString = notationList[notationList.length-2] + notationList[notationList.length-1];
			ChessCoordinate coordinate = new ChessCoordinate(coordinateString);
			if(coordinate.isValid()) {
				//extract players alive pieces of piece name
				List<Piece> piecesOfType = getPlayerTurn().getPieces(pieceName, true);
				//list of pieces that can move to the same square
				List<Piece> piecesCanMoveToSquare = new ArrayList<Piece>();
				for (Piece piece : piecesOfType) {
					
					//use valid moves of attacking if capturing move, else use move to
					Set<Coordinate> pieceMoves = isCaptureMove 
							? piece.getValidMoves(Action.ATTACK) : piece.getValidMoves(Action.MOVE_TO);
					if(pieceMoves.contains(coordinate))piecesCanMoveToSquare.add(piece);
				}	
				
				if (piecesCanMoveToSquare.size() == 1) {
					//only one piece can go to that square, so take piece at 0th index as the list only has one element
					piecesCanMoveToSquare.get(0).move(coordinate);
					return true;
				}
				
				else if (piecesCanMoveToSquare.size() > 1) {
					
					if(notation.length() <= 3) {
						System.out.println("Invalid move, specify which piece to move to " + coordinateString);
						return false;
					}
					//multiple pieces of the same type can move to that square, check which one to use
					//if pawn, the identifier to use is the 0th index of notationList, eg axb4. use pawn in column a.
					//for other pieces, column to use is the 1st index of notationList. eg Nab4.
					
					//variable called identifier as it can be a row or column value.
					String identifier = (pieceName.equalsIgnoreCase(ChessPieceNames.PAWN.toString()) ?
							notationList[0] : notationList[1]).toLowerCase();
					//identifier should be a row value if pieces share the same column
					boolean isRowIdentifier = shareColumn(piecesOfType);
					
					if(isRowIdentifier && getRowNames().contains(identifier)) {
						//locate piece by row and move that piece to coordinate
						for (Piece piece : piecesCanMoveToSquare) {
							//look at the row number of piece coordinate, see if it matches the identifier 
							if(Character.toString(piece.getPosition().toString().charAt(1)).equalsIgnoreCase(identifier)) {
								piece.move(coordinate);
								return true;
							}
						}
						System.out.println("There is no " + pieceName + " on row " + identifier);
						return false;
					}else if(getColumnNames().contains(identifier)) {
						for (Piece piece : piecesCanMoveToSquare) {
							//look at the column letter of piece coordinate, see if it matches the identifier 
							if(Character.toString(piece.getPosition().toString().charAt(0)).equalsIgnoreCase(identifier)) {
								piece.move(coordinate);
								return true;
							}
						}
						System.out.println("There is no " + pieceName + " on column " + identifier);
						return false;
					}
					//invalid identifier
					else return false;
				}
					
				else {
					return false;
				}
			}
			//invalid coordinate, move can not be played
			else return false;
		}
		
	}
	@Override
	public void setRowAndColNames() {		
		List<String> rowNames = new ArrayList<>();
		List<String> columnNames = new ArrayList<>();
		for (int i = 0; i < getBoardArray().length; i++) {
			//for white's perspective
			//from 8 to 1, top left to bottom
			rowNames.add(Character.toString('8'- i));
			//from a to b, left to right from
			columnNames.add(Character.toString('a'+ i));
		}
		setRowNames(rowNames);
		setColumnNames(columnNames);
	}
}