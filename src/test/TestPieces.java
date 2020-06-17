/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardgame.Action;
import boardgame.Colour;
import boardgame.Coordinate;
import boardgame.Piece;
import boardgame.Player;
import main.Castle;
import main.ChessBoard;
import main.ChessCoordinate;
import main.Layout;
import main.pieces.Bishop;
import main.pieces.King;
import main.pieces.Knight;
import main.pieces.Pawn;
import main.pieces.Queen;
import main.pieces.Rook;
/**
 * @author james
 *
 */
public class TestPieces {
	List<Player> players;
	Player p1,p2;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p1 = new Player(Colour.WHITE);
		p2 = new Player(Colour.BLACK);
		Player[] playersArray = {p1,p2};
		players = Arrays.asList(playersArray);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	
	private void performMovementTest(String[] expectedStrings, Piece piece) {
		Set<Coordinate> expected = ChessCoordinate.toCoordinate(expectedStrings);
		Set<Coordinate> actual = piece.getValidMoves(Action.MOVE_TO);
//		assertEquals(expected, actual);
		assertTrue(expected.equals(actual));
	}
	private void performAttackTest(String[] expectedStrings, Piece piece) {
		Set<Coordinate> expected = ChessCoordinate.toCoordinate(expectedStrings);
		Set<Coordinate> actual = piece.getValidMoves(Action.ATTACK);
//		assertEquals(expected, actual);
		assertTrue(expected.equals(actual));

	}
	
	@Test 
	public void testStandardLayout() {
		ChessBoard b = new ChessBoard(Layout.STANDARD,players);
		b.setKingRequired(true);

	}
	
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.Rook} class.
	 */
	@Test
	public void testRook() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Rook rook = new Rook(p1); //white rook
		
		//first test
		b.setPiece(new ChessCoordinate("a1"), rook); //place rook at c4
		//rook should be able to move to (a2..a8) vertically and (b1..h1) horizontally
		String [] expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2","A3","A4","A5","A6","A7","A8"};
		performMovementTest(expectedCoordsString, rook);
		performAttackTest(new String[] {}, rook); //no enemies to attack

		
		//second test
		//now pawn is at a3, rook can only move to a2 vertically but same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p1));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2"};
		performMovementTest(expectedCoordsString, rook);

		//third test
		//now black pawn is at a3, rook should be able to attack it on a3 and move to a2, same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p2));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2"};
		
		performMovementTest(expectedCoordsString, rook);
		performAttackTest(new String[] {"A3"}, rook);
	}
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.King} class.
	 */
	@Test
	public void testKing() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		//test 1 - king at b2
		King king = new King(p1);
		b.setPiece(new ChessCoordinate("b2"), king);
		String[] expectedCoordsString = new String[]{
				"A1","A2","A3","B3","B1","C1","C2","C3"};
		performMovementTest(expectedCoordsString, king);
		
		king.move(new ChessCoordinate("b1"));
		
		expectedCoordsString = new String[]{
				"A1","A2","B2","C1","C2"};
		performMovementTest(expectedCoordsString, king);

	}
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.Bishop} class.
	 */
	@Test
	public void testBishop() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Bishop bishop = new Bishop(p1);

		//first test
		b.setPiece(new ChessCoordinate("c3"), bishop);
		String[] expectedCoordsString = new String[]{
				"A1","B2","D4","E5","F6","G7","H8", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
		}; 
		performMovementTest(expectedCoordsString, bishop);
		//no enemies to attack
		performAttackTest(new String[] {}, bishop);
		
		//second test - black bishop at e5
		b.setPiece(new ChessCoordinate("e5"), new Bishop(p2));
		expectedCoordsString = new String[]{
				"A1","B2","D4","E5", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
		}; 
		performAttackTest(new String[] {"e5"}, bishop); //white bishop can take on e5
		performAttackTest(new String[] {"c3"}, b.at(new ChessCoordinate("e5"))); //black bishop can take on c3
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.Pawn} class.
	 */
	@Test
	public void testPawn() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Pawn pawn = new Pawn(p1);

		b.setPiece(new ChessCoordinate("c2"), pawn);
		String[] expectedCoordsString = new String[]{
				"C3","C4"
		};
		performMovementTest(expectedCoordsString, pawn);
		
		//second test, pawn moves to c4, can only move to c5
		pawn.move(new ChessCoordinate("c4"));
		expectedCoordsString = new String[]{
				"C5"
		};
		performMovementTest(expectedCoordsString, pawn);
		//third test, pawn has no moves, blocked by black pawn
		b.setPiece(new ChessCoordinate("c5"), new Pawn(p2));
		performMovementTest(new String[] {}, pawn);

		//fourth test, new black pawn at b5, white pawn can take
		b.setPiece(new ChessCoordinate("b5"), new Pawn(p2));
		performMovementTest(new String[] {}, pawn);
		performAttackTest(new String[] {"b5"}, pawn);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.Knight} class.
	 */
	@Test
	public void testKnight() {
		//first test
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Knight knight = new Knight(p1);

		b.setPiece(new ChessCoordinate("c4"), knight);
		String[] expectedCoordsString = new String[]{
				"b2","a3", //bottom left
				"a5","b6", //top left
				"d6","e5", //top right
				"e3","d2" //bottom right
				
		};
		performMovementTest(expectedCoordsString, knight);
		
		//second test
		//knight can take pawn on b2, remove b2 from expected moves to expected attack
		b.setPiece(new ChessCoordinate("b2"), new Pawn(p2));
		expectedCoordsString = new String[]{
				"a3", //bottom left
				"a5","b6", //top left
				"d6","e5", //top right
				"e3","d2" //bottom right
		};
		performAttackTest(new String[] {"b2"}, knight);
		performMovementTest(expectedCoordsString, knight);
		
		knight.move(new ChessCoordinate("a3"));
		expectedCoordsString = new String[]{
				"b5","c4", //top right
				"b1","c2" //bottom right
		};		
		performMovementTest(expectedCoordsString, knight);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for the {@link main.pieces.Knight} class.
	 */
	@Test
	public void testQueen() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Queen queen = new Queen(p1);
		b.setPiece(new ChessCoordinate("b2"), queen);
		String[] expectedCoordsString = new String[]{
			"a1","c3","d4","e5","f6","g7","h8", // '/' diagonal
			"a3","c1", // '\' diagonal
			"a2","c2","d2","e2","f2","g2","h2", // horizontal coordinates
			"b1","b3","b4","b5","b6","b7","b8" // vertical coordinates
		};
							
		performMovementTest(expectedCoordsString, queen);
	}
	/**
	 * Test method for {@link main.ChessPiece#captureConsequence()}
	 */
	@Test
	public void testCapturing() {
		//queen at b2 takes pawn at b3
		//check if the conditions are met as stated in junit comment in captureConsequence().

		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Queen queen = new Queen(p1);
		Piece blackPawn = new Pawn(p2);
		b.setPiece(new ChessCoordinate("b2"), queen);
		b.setPiece(new ChessCoordinate("b3"), blackPawn);
		
		//before capture
		//condition 1 - captured list is empty
		assertEquals(Arrays.asList(new Piece[] {}) , p1.getPiecesTaken());		
		//condition 2 - black pawn unit is alive
		assertTrue(blackPawn.isAlive());		
		//condition 3 - blackPawn has a position at b3
		assertEquals(new ChessCoordinate("b3"), blackPawn.getPosition());	
		
		queen.move(new ChessCoordinate("b3"));
		
		//after capture
		//condition 1
		assertEquals(Arrays.asList(new Piece[] {blackPawn}) , p1.getPiecesTaken());		
		//condition 2
		assertFalse(blackPawn.isAlive());
		//condition 3 
		assertEquals(null, blackPawn.getPosition());		
	}
	
	/**
	 * Test method for {@link main.pieces.king#inCheck()}
	 */
	@Test
	public void testCheck() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King whiteKing = new King(p1);
		b.setPiece(new ChessCoordinate("h1"), whiteKing);
		
		Queen whiteQueen = new Queen(p1);
		King blackKing = new King(p2);
		//first test
		//initially not in check, queen is not attacking black king
		b.setPiece(new ChessCoordinate("a1"), whiteQueen);
		b.setPiece(new ChessCoordinate("b8"), blackKing);

		assertFalse(blackKing.inCheck());

		whiteQueen.move(new ChessCoordinate("b2"));
		assertTrue(blackKing.inCheck());
//		//third test: king can not move to b7 since it was still be in check
		String[] expectedCoordsString = new String[]{
				"a8","c8", //left and down left
				"a7","c7" //right and down right
			};
		performMovementTest(expectedCoordsString, blackKing);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getMoves(Action)} for units that are pinned to their king.
	 */
	@Test
	public void testPinnedPiece() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		Queen whiteQueen = new Queen(p1);
		King whiteKing = new King(p1);
		King blackKing = new King(p2);
		Pawn pinnedPawn = new Pawn(p2);
		//first test - pinned pawn has no moves
		b.setPiece(new ChessCoordinate("a1"), whiteQueen);
		b.setPiece(new ChessCoordinate("c3"), pinnedPawn);
		b.setPiece(new ChessCoordinate("h8"), blackKing);
		b.setPiece(new ChessCoordinate("a2"), whiteKing);
		System.out.println(b);
		performMovementTest(new String[] {}, pinnedPawn);
	}
	
	/**
	 * Test method for {@link main.pieces.King#castle(side)} on the kingside for white
	 */
	
	@Test
	public void testCastleKingSide() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		//test 1 - white king castling king side puts king on g1 and rook on f1
		King whiteKing = new King(p1);
		Rook whiteKingsRook = new Rook(p1);
		
		b.setPiece(new ChessCoordinate("e1"), whiteKing);
		b.setPiece(new ChessCoordinate("h1"), whiteKingsRook);
		whiteKing.move(new ChessCoordinate(Castle.KING_SIDE.getCode()));
		ChessCoordinate kingLocation = new ChessCoordinate("g1");
		ChessCoordinate rookLocation = new ChessCoordinate("f1");

		assertEquals(b.at(kingLocation), whiteKing);
		//ensure kings position is updated
		assertEquals(whiteKing.getPosition(), kingLocation);
		
		assertEquals(b.at(rookLocation), whiteKingsRook);
		//ensure the rooks position is updated
		assertEquals(whiteKingsRook.getPosition(), rookLocation);
	}	
	
	/**
	 * Test method for {@link main.pieces.King#castle(side)} on the kingside for black
	 */
	@Test
	public void testCastleKingSideBlack() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King blackKing = new King(p2);
		Rook blackKingsRook = new Rook(p2);
		
		b.setPiece(new ChessCoordinate("e8"), blackKing);
		b.setPiece(new ChessCoordinate("h8"), blackKingsRook);
		blackKing.move(new ChessCoordinate(Castle.KING_SIDE.getCode()));

		
		ChessCoordinate kingLocation = new ChessCoordinate("g8");
		ChessCoordinate rookLocation = new ChessCoordinate("f8");

		assertEquals(b.at(kingLocation), blackKing);
		//ensure kings position is updated
		assertEquals(blackKing.getPosition(), kingLocation);
		
		assertEquals(b.at(rookLocation), blackKingsRook);
		//ensure the rooks position is updated
		assertEquals(blackKingsRook.getPosition(), rookLocation);	
		
	}
	
	/**
	 * Test method for {@link main.pieces.King#castle(side)} on the queen side for white
	 */
	
	@Test
	public void testCastleQueenSide() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		//test 1 - white king castling queen side puts king on c1 and rook on d1
		King whiteKing = new King(p1);
		Rook whiteQueensRook = new Rook(p1);
		
		b.setPiece(new ChessCoordinate("e1"), whiteKing);
		b.setPiece(new ChessCoordinate("a1"), whiteQueensRook);
		whiteKing.move(new ChessCoordinate(Castle.QUEEN_SIDE.getCode()));
		ChessCoordinate kingLocation = new ChessCoordinate("c1");
		ChessCoordinate rookLocation = new ChessCoordinate("d1");

		assertEquals(b.at(kingLocation), whiteKing);
		//ensure kings position is updated
		assertEquals(whiteKing.getPosition(), kingLocation);
		
		assertEquals(b.at(rookLocation), whiteQueensRook);
		//ensure the rooks position is updated
		assertEquals(whiteQueensRook.getPosition(), rookLocation);
	}
	
	/**
	 * Test method for {@link main.pieces.King#castle(side)} on the queen side for black
	 */
	@Test
	public void testCastleQueenSideBlack() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King blackKing = new King(p2);
		Rook blackQueensRook = new Rook(p2);
		
		b.setPiece(new ChessCoordinate("e8"), blackKing);
		b.setPiece(new ChessCoordinate("a8"), blackQueensRook);
		blackKing.move(new ChessCoordinate(Castle.QUEEN_SIDE.getCode()));
		
		ChessCoordinate kingLocation = new ChessCoordinate("c8");
		ChessCoordinate rookLocation = new ChessCoordinate("d8");

		assertEquals(b.at(kingLocation), blackKing);
		//ensure kings position is updated
		assertEquals(blackKing.getPosition(), kingLocation);
		
		assertEquals(b.at(rookLocation), blackQueensRook);
		//ensure the rooks position is updated
		assertEquals(blackQueensRook.getPosition(), rookLocation);	
	}
	
	/**
	 * Test method for {@link main.pieces.King#canCastle(side)} 
	 */
	@Test
	public void testCanCastle() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King whiteKing = new King(p1);
		King blackKing = new King(p2);
		Rook whiteKingsRook = new Rook(p1);
		Rook whiteQueensRook = new Rook(p1);
		
		//test 1 - white king can castle in both directions
		b.setPiece(new ChessCoordinate("e1"), whiteKing);
		b.setPiece(new ChessCoordinate("b8"), blackKing);
		b.setPiece(new ChessCoordinate("h1"), whiteKingsRook);
		b.setPiece(new ChessCoordinate("a1"), whiteQueensRook);

		assertTrue(whiteKing.canCastle(Castle.KING_SIDE));
		assertTrue(whiteKing.canCastle(Castle.QUEEN_SIDE));

		//test 2 - black queen at f8, white king can't castle kingside as f1 is attacked
		//but can still castle queenside
		Queen blackQueen = new Queen(p2);
		b.setPiece(new ChessCoordinate("f8"), blackQueen);
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertTrue(whiteKing.canCastle(Castle.QUEEN_SIDE));
		blackQueen.move(new ChessCoordinate("g8"));
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertTrue(whiteKing.canCastle(Castle.QUEEN_SIDE));
		
		//test 3 - place white knight on b1, white king can not castle queen side or kingside as knight blocks
		Knight whiteKnight = new Knight(p1);
		b.setPiece(new ChessCoordinate("b1"), whiteKnight);
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertFalse(whiteKing.canCastle(Castle.QUEEN_SIDE));
		
		whiteKnight.move(new ChessCoordinate("c3"));
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertTrue(whiteKing.canCastle(Castle.QUEEN_SIDE));
		
		
		blackQueen.move(new ChessCoordinate("e6"));
		
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertFalse(whiteKing.canCastle(Castle.QUEEN_SIDE));

		whiteKnight.move(new ChessCoordinate("e2"));
		assertTrue(whiteKing.canCastle(Castle.KING_SIDE));
		assertTrue(whiteKing.canCastle(Castle.QUEEN_SIDE));

		blackQueen.move(new ChessCoordinate("e5"));
		whiteQueensRook.move(new ChessCoordinate("c1"));
		assertTrue(whiteKing.canCastle(Castle.KING_SIDE));
		assertFalse(whiteKing.canCastle(Castle.QUEEN_SIDE));

		blackQueen.move(new ChessCoordinate("e4"));
		whiteKing.move(new ChessCoordinate("d1"));
		assertFalse(whiteKing.canCastle(Castle.KING_SIDE));
		assertFalse(whiteKing.canCastle(Castle.QUEEN_SIDE));
	}
	
	/**
	 * Test method for {@link main.pieces.King#inStalemate()} 
	 */
	@Test
	public void testStalemate() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King whiteKing = new King(p1);
		King blackKing = new King(p2);
		Queen whiteQueen = new Queen(p1);
		//black king is in stalemate on a1, has no moves
		b.setPiece(new ChessCoordinate("A1"), blackKing);
		b.setPiece(new ChessCoordinate("A3"), whiteKing);
		b.setPiece(new ChessCoordinate("C2"), whiteQueen);
		assertTrue(blackKing.inStalemate());
		
		whiteQueen.move(new ChessCoordinate("h2"));
		
		assertFalse(blackKing.inStalemate());

	}
	
	/**
	 * Test method for {@link main.pieces.King#inCheckmate()} 
	 */
	@Test
	public void testCheckmate() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		b.setKingRequired(true);
		King whiteKing = new King(p1);
		King blackKing = new King(p2);
		Rook whiteRook = new Rook(p1);

		//mate with rook against black king
		b.setPiece(new ChessCoordinate("A1"), blackKing);
		b.setPiece(new ChessCoordinate("A3"), whiteKing);
		b.setPiece(new ChessCoordinate("H1"), whiteRook);
		assertTrue(blackKing.inCheckmate());
		
		whiteRook.move(new ChessCoordinate("h2"));
		assertFalse(blackKing.inCheckmate());
	}
}
