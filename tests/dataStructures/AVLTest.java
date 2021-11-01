package dataStructures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Player;
import tree.BalancedBinaryTree;
import tree.BinarySearchTree;
import tree.TreeException;

public class AVLTest {

	public tree.node.TreeNode<Integer,Double> setupScenary1(){
		Player player1 = new Player("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		Player player2 = new Player("Charles", 29, "Arg", 107, 500, 254, 70, 213);
		Player player3 = new Player("Aiden", 36, "Col", 205, 330, 120, 88, 103);

		tree.node.TreeNode<Integer,Double> root = new tree.node.TreeNode<>(player1.getKey(), player1.getPoints());
		tree.node.TreeNode<Integer,Double> rightSon = new tree.node.TreeNode<>(player2.getKey(), player2.getPoints());
		tree.node.TreeNode<Integer,Double> rightSon2 = new tree.node.TreeNode<>(player3.getKey(), player3.getPoints());

		root.setRight(rightSon);
		rightSon.setParent(root);
		rightSon.setRight(rightSon2);

		return root;
	}

	@Test
	public void testInsert() throws TreeException{
		setupScenary1();
		BalancedBinaryTree<Integer, Double> tree = new BalancedBinaryTree<>();
		Player player1 = new Player("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		Player player2 = new Player("Charles", 29, "Arg", 107, 500, 254, 70, 213);
		Player player3 = new Player("Aiden", 36, "Col", 205, 330, 120, 88, 103);
		Player player4 = new Player("Lanita", 25, "Guam", 95, 847, 100, 78, 352);
		Player player5 = new Player("Carlos", 27, "Guam", 203, 842, 111, 76, 302);

		tree.insert(player1.getKey(), player1.getPoints());
		tree.insert(player2.getKey(), player2.getPoints());
		tree.insert(player3.getKey(), player3.getPoints());

		assertEquals(0,tree.balanceFactor(tree.getRootNode()));

		tree.insert(player4.getKey(), player4.getPoints());
		assertEquals(-1,tree.balanceFactor(tree.getRootNode()));

		tree.insert(player5.getKey(), player5.getPoints());
		assertEquals(0,tree.balanceFactor(tree.getRootNode()));

		assertEquals(3,tree.height());
	}

	@Test
	public void testRemove() throws TreeException{
		setupScenary1();
		BalancedBinaryTree<Integer, Double> tree = new BalancedBinaryTree<>();
		Player player1 = new Player("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		Player player2 = new Player("Charles", 29, "Arg", 207, 500, 254, 70, 213);
		Player player3 = new Player("Aiden", 36, "Col", 205, 330, 120, 88, 103);
		Player player4 = new Player("Lanita", 25, "Guam", 219, 847, 100, 78, 352);

		tree.insert(player1.getKey(), player1.getPoints());
		tree.insert(player2.getKey(), player2.getPoints());
		tree.insert(player3.getKey(), player3.getPoints());
		tree.insert(player4.getKey(), player4.getPoints());
		assertEquals(3,tree.height());
		
		tree.remove(player4.getKey());
		assertEquals(2,tree.height());
	}

}
