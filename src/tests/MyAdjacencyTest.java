/**. 
 * MyAdjacencyTest class that does the JUnit the,
 * adjacencies and targets testing based on the,
 * board object.
 * 
 * @author Mehmet Yilmaz
 * @author Ruidi Huang
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;

import clueGame.Board;
import clueGame.BoardCell;
import java.util.Set;

import clueGame.Board;

class MyAdjacencyTest { 
	private static Board board; // initializing a static Board object for all the tests
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance(); // only set instance variable for Board
		board.setConfigFiles("OurBoardLayout.csv", "OurRooms.txt"); // set the file names for setConfigFiles()
		board.initialize(); // load both config files for tests
	}
	
	// Adjacency tests for locations with only walkways as adjacent location(s)
	@Test
	public void testAdjacentLocationsWalways(){
		// test walkway at point (7,4)
		Set<BoardCell> testPoint = board.getAdjList(7,4);
		assertTrue(testPoint.contains(board.getCellAt(6, 4)));
		assertTrue(testPoint.contains(board.getCellAt(8, 4)));
		assertTrue(testPoint.contains(board.getCellAt(7, 5)));
		assertTrue(testPoint.contains(board.getCellAt(7, 3)));
		assertEquals(4, testPoint.size());
		
		// test walkway at point (8,22)
		testPoint = board.getAdjList(8, 22);
		assertTrue(testPoint.contains(board.getCellAt(8, 21)));
		assertEquals(1, testPoint.size());
		
		// test walkway at point (14,15)
		testPoint = board.getAdjList(14,15);
		assertTrue(testPoint.contains(board.getCellAt(14, 16)));
		assertTrue(testPoint.contains(board.getCellAt(14, 14)));
		assertTrue(testPoint.contains(board.getCellAt(13, 15)));
		assertTrue(testPoint.contains(board.getCellAt(15, 15)));
		assertEquals(4, testPoint.size());
	}
	
	// Adjacency tests for locations within rooms. The adjacency list should be empty
	@Test
	public void testAdjacentLocationsInRooms(){
		// test cell (2,2) in room M
		Set<BoardCell> testPoint = board.getAdjList(2,2);
		assertEquals(0, testPoint.size());
		
		// test cell (18, 11) in room D
		testPoint = board.getAdjList(18,11);
		assertEquals(0, testPoint.size());
		
		// test cell (1,  13) in room B
		testPoint = board.getAdjList(1,13);
		assertEquals(0, testPoint.size());
	}
	
	// Adjacency tests for locations at each edge of the board (UP and DOWN edges)
	@Test
	public void testAdjacentAtEdges(){
		// test adjacency list for the UP edge of the board
		Set<BoardCell> testPoint = board.getAdjList(0, 4);
		assertTrue(testPoint.contains(board.getCellAt(0, 5)));
		assertTrue(testPoint.contains(board.getCellAt(0, 4)));
		assertTrue(testPoint.contains(board.getCellAt(4, 1)));
		assertEquals(3, testPoint.size());
		
		// test adjacency list for the DOWN edge of the board
		testPoint = board.getAdjList(21, 20);
		assertTrue(testPoint.contains(board.getCellAt(21, 19)));
		assertTrue(testPoint.contains(board.getCellAt(21, 21)));
		assertTrue(testPoint.contains(board.getCellAt(20, 20)));
		assertEquals(4, testPoint.size());
	}
	
	// Adjacency tests for locations beside room() cell that is not a doorway
	@Test
	public void testBesideRoomNotDoorWay(){
		// testBesideRoomNotDoorWay test for point (9, 15)
		Set<BoardCell> testPoint = board.getAdjList(9, 15);
		assertTrue(testPoint.contains(board.getCellAt(10, 15)));
		assertTrue(testPoint.contains(board.getCellAt(9, 14)));
		assertTrue(testPoint.contains(board.getCellAt(8, 15)));
		assertEquals(3, testPoint.size());
		
		// testBesideRoomNotDoorWay test for point (21, 7)
		testPoint = board.getAdjList(21, 7);
		assertTrue(testPoint.contains(board.getCellAt(20, 7)));
		assertTrue(testPoint.contains(board.getCellAt(21, 8)));
		assertEquals(2, testPoint.size());
	}
	
	// Adjacency tests for locations that are adjacent to a doorway with needed directions
	@Test
	public void testLocationsAdjacenetToDoorway(){
		// test cell (5, 4)
		Set<BoardCell> testPoint = board.getAdjList(5, 4);
		assertTrue(testPoint.contains(board.getCellAt(6, 4)));
		assertTrue(testPoint.contains(board.getCellAt(5, 5)));
		assertTrue(testPoint.contains(board.getCellAt(4, 4)));
		assertTrue(!testPoint.contains(board.getCellAt(5, 3))); // not contain (5,3)
		assertEquals(3, testPoint.size());
		
		// test cell (2, 8)
		testPoint = board.getAdjList(2, 8);
		assertTrue(testPoint.contains(board.getCellAt(2, 9)));
		assertTrue(testPoint.contains(board.getCellAt(2, 7)));
		assertTrue(testPoint.contains(board.getCellAt(1, 8)));
		assertTrue(testPoint.contains(board.getCellAt(3, 8)));
		assertEquals(4, testPoint.size());
	}
	
	// Adjacency tests for locations that are doorways. The value of the adjacent cell should be 1.
	@Test
	public void LocationsAreDoorways(){
		Set<BoardCell> testPoint;

		// test UP door at cell (12, 4)
		testPoint = board.getAdjList(12, 4);
		assertTrue(testPoint.contains(board.getCellAt(12,4)));
		assertEquals(1, testPoint.size());
		
		// test RIGHT door at cell (11, 16)
		testPoint = board.getAdjList(11, 16);
		assertTrue(testPoint.contains(board.getCellAt(11,16)));
		assertEquals(1, testPoint.size());
		
		// test DOWN door at cell (14, 10)
		testPoint = board.getAdjList(14, 10);
		assertTrue(testPoint.contains(board.getCellAt(14,10)));
		assertEquals(1, testPoint.size());
		
		// test LEFT door at cell (4, 11)
		testPoint = board.getAdjList(4, 11);
		assertTrue(testPoint.contains(board.getCellAt(4,11)));
		assertEquals(1, testPoint.size());
	}
	
	// Target test along the walkway at cell (19, 0) with dice rolls of 1, 2, and 3
	@Test
	public void TargetWalkWays_19_0(){
		for(int i =1; i < 4; i++) {
			board.calcTargets(19, 0, i);
			Set<BoardCell> targets= board.getTargets();
			switch(i) {
			case 1:
				assertEquals(1, targets.size());
				assertTrue(targets.contains(board.getCellAt(18, 0)));
				break;
			case 2:
				assertEquals(2, targets.size());
				assertTrue(targets.contains(board.getCellAt(18, 0)));
				assertTrue(targets.contains(board.getCellAt(18, 1)));
				break;
			case 3:
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(18, 0)));
				assertTrue(targets.contains(board.getCellAt(18, 1)));
				assertTrue(targets.contains(board.getCellAt(18, 2)));
				break;
			}
		}
	}
	
	// Target test along the walkway at cell (21, 18) with dice rolls of 1, 2, and 3
	@Test
	public void TargetWalkWays_21_18(){
		for(int i =1; i < 4; i++) {
			board.calcTargets(21, 18, i);
			Set<BoardCell> targets= board.getTargets();
			switch(i) {
			case 1:
				assertEquals(2, targets.size());
				assertTrue(targets.contains(board.getCellAt(20, 18)));
				assertTrue(targets.contains(board.getCellAt(21, 17)));
				break;
			case 2:
				assertEquals(1, targets.size());
				assertTrue(targets.contains(board.getCellAt(20, 17)));
				break;
			case 3:
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(20, 18)));
				assertTrue(targets.contains(board.getCellAt(21, 17)));
				assertTrue(targets.contains(board.getCellAt(19, 17)));
				break;
			}
		}
	}
	
	// Target test along the walkway at cell (2, 16) with dice rolls of 1, 2, and 3
	@Test
	public void TargetWalkWays_2_16(){
		for(int i =1; i < 4; i++) {
			board.calcTargets(2, 16, i);
			Set<BoardCell> targets= board.getTargets();
			switch(i) {
			case 1:
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(1, 16)));
				assertTrue(targets.contains(board.getCellAt(3, 16)));
				assertTrue(targets.contains(board.getCellAt(2, 17)));
				break;
			case 2:
				assertEquals(5, targets.size());
				assertTrue(targets.contains(board.getCellAt(2, 18)));
				assertTrue(targets.contains(board.getCellAt(0, 16)));
				assertTrue(targets.contains(board.getCellAt(4, 16)));
				assertTrue(targets.contains(board.getCellAt(1, 17)));
				assertTrue(targets.contains(board.getCellAt(3, 17)));
				break;
			case 3:
				assertEquals(8, targets.size());
				assertTrue(targets.contains(board.getCellAt(0, 17)));
				assertTrue(targets.contains(board.getCellAt(1, 16)));
				assertTrue(targets.contains(board.getCellAt(3, 18)));
				assertTrue(targets.contains(board.getCellAt(2, 17)));
				assertTrue(targets.contains(board.getCellAt(5, 16)));
				assertTrue(targets.contains(board.getCellAt(3, 16)));
				assertTrue(targets.contains(board.getCellAt(4, 15)));
				assertTrue(targets.contains(board.getCellAt(4, 17)));
				break;
			}
		}
	}
	
	// Target test along the walkway at cell (8, 6) with dice rolls of 1, 2, and 3
	@Test
	public void TargetWalkWays_8_6(){
		for(int i =1; i < 4; i++) {
			board.calcTargets(2, 16, i);
			Set<BoardCell> targets= board.getTargets();
			switch(i) {
			case 1:
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(7, 6)));
				assertTrue(targets.contains(board.getCellAt(8, 5)));
				assertTrue(targets.contains(board.getCellAt(9, 6)));
				break;
			case 2:
				assertEquals(5, targets.size());
				assertTrue(targets.contains(board.getCellAt(6, 6)));
				assertTrue(targets.contains(board.getCellAt(8, 4)));
				assertTrue(targets.contains(board.getCellAt(10, 6)));
				assertTrue(targets.contains(board.getCellAt(7, 5)));
				assertTrue(targets.contains(board.getCellAt(9, 5)));
				break;
			case 3:
				assertEquals(11, targets.size());
				assertTrue(targets.contains(board.getCellAt(5, 6)));
				assertTrue(targets.contains(board.getCellAt(7, 6)));
				assertTrue(targets.contains(board.getCellAt(6, 7)));
				assertTrue(targets.contains(board.getCellAt(6, 5)));
				assertTrue(targets.contains(board.getCellAt(7, 4)));
				assertTrue(targets.contains(board.getCellAt(8, 3)));
				assertTrue(targets.contains(board.getCellAt(8, 5)));
				assertTrue(targets.contains(board.getCellAt(11, 6)));
				assertTrue(targets.contains(board.getCellAt(9, 6)));
				assertTrue(targets.contains(board.getCellAt(10, 5)));
				assertTrue(targets.contains(board.getCellAt(10, 6)));
				break;
			}
		}
	}

	// Target tests for targets that allow the user to enter into a room
	@Test
	public void TargetsEnterRoom(){
		board.calcTargets(15, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// into the room
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		// going up
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		// going down
		assertTrue(targets.contains(board.getCellAt(13, 5)));
		// going forward then left
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		// going forward then right
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		// going forward then back in all possible directions
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
	}
	
	// Target tests for targets that allow the user to enter into a room, shortcut verison
	@Test
	public void TargetsEnterRoomShortcut(){
		board.calcTargets(6, 10, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// into the room
		assertTrue(targets.contains(board.getCellAt(4, 11)));
		// other directions:
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(5, 10)));
		assertTrue(targets.contains(board.getCellAt(5, 12)));
		assertTrue(targets.contains(board.getCellAt(6, 13)));
		
	}
	
	// Targets test for targets calculated when leaving a room
	@Test
	public void testRoomExit() {
		// test cell point (11, 16) with a dice roll of 1
		board.calcTargets(11, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		// there should only be 1 target
		assertEquals(1, targets.size());
		// there should be a target cell that is outside the room
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		
		// test cell point (12, 3) with a dice roll of 2
		board.calcTargets(12, 3, 2);
		targets= board.getTargets();
		// there should only be 1 target
		assertEquals(1, targets.size());
		// there should be a target cell that is outside the room
		assertTrue(targets.contains(board.getCellAt(12, 5)));
	}
}
