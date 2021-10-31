package dataStructures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Player;
import tree.BinarySearchTree;
import tree.TreeException;

public class BSTTest {

	@Test
	public void testInsert() throws TreeException{
		BinarySearchTree<Integer, Double> tree = new BinarySearchTree<>();
		Player player1 = new Player("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		Player player2 = new Player("Carlos", 27, "Guam", 203, 842, 111, 76, 302);
		Player player3 = new Player("Juan", 35, "Arg", 217, 247, 150, 58, 152);
		Player player4 = new Player("Link", 37, "Mex", 313, 507, 230, 88, 162);

		tree.insert(player1.getKey(), player1.getPoints());
		tree.insert(player2.getKey(), player2.getPoints());
		tree.insert(player3.getKey(), player3.getPoints());
		tree.insert(player4.getKey(), player4.getPoints());

		assertFalse(tree.isEmpty());

		assertEquals(player1.getKey(), tree.getRootNode().key());
		assertEquals(player2.getKey(), tree.getRootNode().left().key());
		assertEquals(player3.getKey(), tree.getRootNode().right().key());
		assertEquals(player4.getKey(), tree.getRootNode().right().right().key());
	}

	@Test
	public void testContainsKey() throws TreeException{
		BinarySearchTree<Integer, Double> tree = new BinarySearchTree<>();
		Player player1 = new Player("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		Player player2 = new Player("Carlos", 27, "Guam", 203, 842, 111, 76, 302);
		Player player3 = new Player("Juan", 35, "Arg", 217, 247, 150, 58, 152);
		Player player4 = new Player("Link", 37, "Mex", 313, 507, 230, 88, 162);

		tree.insert(player1.getKey(), player1.getPoints());
		tree.insert(player2.getKey(), player2.getPoints());
		tree.insert(player3.getKey(), player3.getPoints());
		tree.insert(player4.getKey(), player4.getPoints());

		assertFalse(tree.isEmpty());

		assertTrue(tree.containsKey(player1.getKey()));
		assertTrue(tree.containsKey(player2.getKey()));
		assertTrue(tree.containsKey(player3.getKey()));
		assertTrue(tree.containsKey(player4.getKey()));
	
	}
	
	@Test
	public void testRemove() throws TreeException{
		BinarySearchTree<Integer, Double> tree = new BinarySearchTree<>();
		Player player1 = new Player("Lanita", 25, "Guam", 213.0, 847, 100, 78, 352);
		Player player2 = new Player("Carlos", 27, "Guam", 203, 842, 111, 76, 302);
		Player player3 = new Player("Juan", 35, "Arg", 217, 247, 150, 58, 152);
		Player player4 = new Player("Link", 37, "Mex", 313, 507, 230, 88, 162);

		tree.insert(player1.getKey(), player1.getPoints());
		tree.insert(player2.getKey(), player2.getPoints());
		tree.insert(player3.getKey(), player3.getPoints());
		tree.insert(player4.getKey(), player4.getPoints());

		assertFalse(tree.isEmpty());
		assertEquals(3, tree.height());
		
		tree.remove(player4.getKey());
		assertEquals(2, tree.height());
	
	}

}
