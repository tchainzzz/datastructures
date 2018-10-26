/** 
 * Trenton Chang - Oct. 20 2018
 * 
 * This class implements a self balancing red black tree. A red black tree is as subclass of a binary search tree
 * with the following constraints:
 *
 * 1. Each node is either red or black.
 * 2. The root is black.
 * 3. All leaf nodes are black.
 * 4. If a node is red, then both its children are black.
 * 5. Every path from a given node to any of its descendant leaf nodes contains the same number of black nodes.
 *
 */


public class RedBlackTree<T extends Comparable<? super T>> {

    public static final int DEFAULT_TREE_SIZE = 15;

    private TreeNode<T> root;
    private int size;

    /**
     * Constructor for class RedBlackTree.
     */
    @SafeVarargs
    public RedBlackTree(T... data) {
        this.root = null;
        this.size = 0;
        for (T dataPoint : data) {
            emplace(dataPoint);
        }
    }

    /**
     * Inserts and constructs a new TreeNode into the tree.
     */
    public void emplace(T data) {
        insert(new TreeNode<T>(data, true));
    }

    /**
     * Inserts a new TreeNode into the tree.
     */
    public void insert(TreeNode<T> newNode) {
        TreeNode<T> current = this.root;
        T data = newNode.getData();
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
                } else { // data.compareTo(current.data) == 0
                    current.addNodeHere();
                    break;
                }
            }
            // now parent is the last node we traversed, and curr is a NIL node.
            if (data.compareTo(parent.data) < 0) {
                parent.setLeftChild(newNode);
                newNode.setParent(parent);
                rebalance(newNode);
            } else if (data.compareTo(parent.data) > 0) {
                parent.setRightChild(newNode);
                newNode.setParent(parent);
                rebalance(newNode);
            } else {
                parent.addNodeHere();
            }
        }
    }

    private void rebalance(TreeNode<T> newest) {
        if (!newest.getParent().getColor())
            return; // if parent is black, we're done
        if (newest.getOmmer().getColor()) { // ommer is red
            // recolor to stay with red-black property
            newest.getParent().setColor(false);
            newest.getOmmer().setColor(false);
            // recurse upward to maintain red-black properties
            rebalance(newest.getGrandparent());
        } else { // ommer is black
            if (newest == newest.getGrandparent().getLeftChild().getRightChild()) { // converts left->right case to
                                                                                    // left->left
                rotateLeft(newest.getParent());
            } else if (newest == newest.getGrandparent().getRightChild().getLeftChild()) { // converts right->left->case
                                                                                           // to right->right
                rotateRight(newest.getParent());
            }
            if (newest == newest.getParent().getLeftChild()) {
                rotateRight(newest.getGrandparent());
            } else {
                rotateLeft(newest.getGrandparent());
            }
        }
        newest.getParent().setColor(false);
        newest.getGrandparent().setColor(true);
        // all balanced! whew!
    }

    /**
     * This rotates the pivot node AROUND its child to the left. Pivot will become a
     * RIGHT child of its LEFT child.
     */
    public void rotateLeft(TreeNode<T> pivot) {
        rotate(pivot, true);
    }

    /**
     * This rotates the pivot node AROUND its child to the right. Pivot will become
     * a LEFT child of its RIGHT child.
     */
    public void rotateRight(TreeNode<T> pivot) {
        rotate(pivot, false);
    }

    private void rotate(TreeNode<T> pivot, boolean left) {
        TreeNode<T> newParent = null;
        TreeNode<T> parent = pivot.getParent();
        try {
            if (left) {
                newParent = pivot.getRightChild();
            } else {
                newParent = pivot.getLeftChild();
            }
        } catch (NullPointerException e) {
            // you can't rotate around a null node, so left and right child of the pivot
            // must exist!
            return;
        }
        assert (newParent != null);
        // rotate nodes
        if (left) {
            pivot.setRightChild(newParent.getLeftChild());
            newParent.setLeftChild(pivot);
        } else {
            pivot.setLeftChild(newParent.getRightChild());
            newParent.setRightChild(pivot);
        }
        pivot.setParent(newParent);

        // reattach children
        if (pivot.getRightChild() != null) {
            pivot.getRightChild().setParent(pivot);
        }
        if (pivot.getLeftChild() != null) {
            pivot.getLeftChild().setParent(pivot);
        }

        // reconnect to parent
        if (parent != null) {
            if (pivot == parent.getLeftChild()) {
                parent.setLeftChild(newParent);
            } else if (pivot == parent.getRightChild()) {
                parent.setRightChild(newParent);
            }
        }
        newParent.setParent(parent);
    }

    public void delete(T key) {

    }

    public void find(T key) {

    }

    public static RedBlackTree generateIntegerTree() {
        return generateIntegerTree(DEFAULT_TREE_SIZE);
    }

    public static RedBlackTree generateIntegerTree(int size) {
        throw new RuntimeException("Not yet implemented!");
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }

    public static <T extends Comparable<? super T>> void joinTrees(RedBlackTree<T> lhs, T partition,
            RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> void splitTree(RedBlackTree<T> tree, T partition) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> void union(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> void intersect(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> void difference(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> void symmetricDifference(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    @Override
    public String toString() {
        throw new RuntimeException("Not yet implemented!");
    }

    /**
     * This private class stores information about a tree node. Tree node data must
     * be comparable so that data can be inserted in the correct place. Because Java
     * compilers are capable of autoboxing, this doesn't affect primitive classes.
     */

    public class TreeNode<T extends Comparable<? super T>> {
        private final T data;
        private boolean red; // toggle to represent if a node is red or black
        private int nodesAtLocation; // for storing duplicates
        private TreeNode<T> left_child;
        private TreeNode<T> right_child;
        private TreeNode<T> parent;

        TreeNode(T data, boolean red) {
            this.data = data;
            this.nodesAtLocation = 1;
            this.red = red; // default is black node
            this.parent = null;
            this.left_child = null;
            this.right_child = null;
        }

        public TreeNode<T> getGrandparent() {
            try {
                return this.parent.parent;
            } catch (NullPointerException e) {
                return null;
            }
        }

        public TreeNode<T> getSibling() {
            if (this.parent != null) {
                if (this == this.parent.left_child) {
                    return this.parent.right_child;
                } else if (this == this.parent.right_child) {
                    return this.parent.left_child;
                } else {
                    return null;
                }
            }
            return null;
        }

        public TreeNode<T> getOmmer() {
            try {
                return this.parent.getSibling();
            } catch (NullPointerException e) {
                return null;
            }
        }

        /* GETTERS */

        public T getData() {
            return this.data;
        }

        public boolean getColor() {
            return this.red;
        }

        public TreeNode<T> getParent() {
            return this.parent;
        }

        public TreeNode<T> getLeftChild() {
            return this.left_child;
        }

        public TreeNode<T> getRightChild() {
            return this.right_child;
        }

        /* SETTERS */

        private void setColor(boolean red) {
            this.red = red;
        }

        private void setParent(TreeNode<T> node) {
            this.parent = node;
        }

        private void setLeftChild(TreeNode<T> node) {
            this.left_child = node;
        }

        private void setRightChild(TreeNode<T> node) {
            this.right_child = node;
        }

        private void addNodeHere() {
            this.nodesAtLocation++;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.data.toString())
            .append(" - ")
            .append(this.red ? "R" : "B");
            return sb.toString();
        }

    }

}