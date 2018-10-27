
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
		RedBlackTree<Integer> tree = RedBlackTree.generateIntegerTree(7);
		assertNotNull(tree);
		System.out.println(tree.toString());
	}


}
