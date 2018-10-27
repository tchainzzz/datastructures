
import org.junit.*;
import static org.junit.Assert.*;

public class RedBlackTreeTest {
	
	@Test
	public void nullTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		assertNotNull(tree);
	}

	@Test
	public void singleNodeTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.emplace(1);
		assertEquals(1, (int) tree.getRoot().getData());
		System.out.println("singleNodeTest() result: " + (int) tree.getRoot().getData());
	}

	@Test
	public void twoNodeTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.emplace(1);
		tree.emplace(2);
		assertEquals(2, (int) tree.getRoot().getRightChild().getData());
		System.out.println("twoNodeTest() result: " + (int) tree.getRoot().getRightChild().getData());

	}

	@Test
	public void parentSanityCheck() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.emplace(1);
		tree.emplace(2);
		assertEquals(tree.getRoot(), tree.getRoot().getRightChild().getParent());
	}

	@Test
	public void printTreeTest() {
		for (int i = 0; i < 15; i++) {
			System.out.println("Tree size: " + i);
			System.out.println();
			RedBlackTree<Integer> tree = RedBlackTree.generateIntegerTree(i);
			assertNotNull(tree);
			System.out.println(tree.toString());
			System.out.println("---------------------------------------------------------------");
		}
		
	}

	@Test
	public void massiveTreeTest() {
		int size = (int) Math.pow(2, 27);
		RedBlackTree<Integer> tree = RedBlackTree.generateIntegerTree(size - 1);
		assertNotNull(tree);
		assertTrue(tree.getHeight() <= (2 * (Math.log(size)/Math.log(2))));
		System.out.println("Actual tree height: " + tree.getHeight());
		System.out.println("Nodes: " + tree.getSize());
	}

	@Test
	public void deletionTest() {
		RedBlackTree<Integer> tree = RedBlackTree.generateIntegerTree(7);
		assertNotNull(tree);
		tree.delete(99999); //should delete nothing
		tree.delete(6);
		tree.delete(1);
		System.out.println(tree.toString());
	}


}
