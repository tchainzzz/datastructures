/** 
 This class implements a self balancing red black tree. A red black tree is as subclass of a binary search tree
 with the following constraints:

 1. Each node is either red or black.
 2. The root is black.
 3. All leaf nodes are black.
 4. If a node is red, then both its children are black.
 5. Every path from a given node to any of its descendant leaf nodes contains the same number of black nodes.

*/

package binary_tree.red_black_tree;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<? super T>> {

    public static final int DEFAULT_TREE_SIZE = 15;

    private TreeNode<T> root;
    private int size;

    @SafeVarargs
    public RedBlackTree(T ... data) {
        this.root = null;
        this.size = 0;
        for (T dataPoint : data) {
            insert(dataPoint);
        }
    }

    public void insert(T data) {
        TreeNode<T> newNode = new TreeNode<T>(data, true);
        TreeNode<T> current = this.root;
        TreeNode<T> parent = null;
        if (this.root == null) {
            newNode.setColor(false);
            this.root = newNode; 
        } else {
            while (current != null) {
                parent = current;
                if (data.compareTo(current.data) < 0) {
                    current = current.getLeftChild();
                } else if (data.compareTo(current.data) > 0) {
                    current = current.getRightChild();
                } else { //data.compareTo(current.data) == 0
                    current.addNodeHere();
                    break;
                }
            }
            //now parent is the last node we traversed, and curr is a NIL node.
            if (data.compareTo(parent.data) < 0 ){
                parent.setLeftChild(newNode);
            } else if (data.compareTo(parent.data) > 0) {
                parent.setRightChild(newNode);
            } else {
                parent.addNodeHere();
            }
        }
    }

    public void delete() {

    }

    public void find() {

    }

    public static RedBlackTree generateTree() {
        return generateTree(DEFAULT_TREE_SIZE);
    }

    public static RedBlackTree generateTree(int size) {
        throw new RuntimeException("Not yet implemented!");
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    @Override
    public String toString() {
        throw new RuntimeException("Not yet implemented!");
    }

    /**
        This private class stores information about a tree node. Tree node data must be comparable so that 
        data can be inserted in the correct place. Because Java compilers are capable of autoboxing, this
        doesn't affect primitive classes.
     */

    private class TreeNode<T extends Comparable<? super T>> { 
        private final T data;
        private boolean red; //toggle to represent if a node is red or black
        private int nodesAtLocation; //for storing duplicates
        private TreeNode<T> left_child;
        private TreeNode<T> right_child;

        TreeNode(T data, boolean red) {
            this.data = data;
            this.nodesAtLocation = 1; 
            this.red = red; //default is black node
            this.left_child = null;
            this.right_child = null;
        }

        /* GETTERS */

        private T getData() {
            return this.data;
        }

        private boolean getColor() {
            return this.red;
        }

        private TreeNode<T> getLeftChild() {
            return this.left_child;
        }

        private TreeNode<T> getRightChild() {
            return this.right_child;
        }

        /* SETTERS */

        private void setColor(boolean red) {
            this.red = red;
        }

        private void setLeftChild (TreeNode<T> node) {
            this.left_child = node;
        }

        private void setRightChild (TreeNode<T> node) {
            this.right_child = node;
        }

        private void addNodeHere() {
            this.nodesAtLocation++;
        }

    }

}