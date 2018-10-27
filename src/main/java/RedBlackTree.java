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
 * This class can contain any type that extends Comparable<T>. In other words, the enclosed type must be a linear order
 * (trichotomous and transitive).
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
        T data = newNode.getData();
        TreeNode<T> current = this.root;
        TreeNode<T> parent = null;
        if (this.root == null) {
            newNode.setColor(false);
            this.root = newNode;
        } else {
            System.out.println("Searching");
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
        System.out.println("Rebalancing");
        if (newest == null) return;
        if (newest.equals(this.root)) return;
        if (!newest.getParent().getColor()) return; // if parent is black, we're done
        boolean ommerIsRed = newest.getOmmer() == null ? false : newest.getOmmer().getColor();
        if (!ommerIsRed) {
            if (newest.getGrandparent() != null) {
                TreeNode<T> grandparentLeftChild = newest.getGrandparent().getLeftChild();
                TreeNode<T> grandparentRightChild = newest.getGrandparent().getRightChild();
                if (grandparentLeftChild != null) { // converts left->right case to
                    // left->left
                    if (newest.equals(grandparentLeftChild.getRightChild())) rotateLeft(newest.getParent());
                } else if (grandparentRightChild != null) { // converts right->left->case
                    // to right->right
                    if (newest.equals(grandparentRightChild.getLeftChild())) rotateRight(newest.getParent());
                }
            } else {
                if (newest.equals(newest.getParent().getRightChild())) {
                    rotateLeft(newest.getParent());
                } else if (newest.equals(newest.getParent().getLeftChild())) {
                    rotateRight(newest.getParent());
                }
            }

            
            if (newest.equals(newest.getParent().getLeftChild())) {
                rotateLeft(newest.getGrandparent());
            } else {
                rotateRight(newest.getGrandparent());
            }
        } else {
             // recolor to stay with red-black property
             newest.getParent().setColor(false);
             if (newest.getOmmer() != null) newest.getOmmer().setColor(false);
             // recurse upward to maintain red-black properties
             rebalance(newest.getGrandparent());
        }

        newest.getParent().setColor(false);
        if (newest.getGrandparent() != null) newest.getGrandparent().setColor(true);
        // all balanced! whew!
    }

    /**
     * This rotates the pivot node AROUND its child to the left. Pivot will become a
     * RIGHT child of its LEFT child.
     */
    public void rotateLeft(TreeNode<T> pivot) {
        System.out.println("Rotating right");
        rotate(pivot, true);
    }

    /**
     * This rotates the pivot node AROUND its child to the right. Pivot will become
     * a LEFT child of its RIGHT child.
     */
    public void rotateRight(TreeNode<T> pivot) {
        System.out.println("Rotating right");
        rotate(pivot, false);
    }

    private void rotate(TreeNode<T> pivot, boolean left) {
        if (pivot == null) return;
        TreeNode<T> newParent = null;
        TreeNode<T> parent = pivot.getParent();
        if (left) {
            newParent = pivot.getLeftChild();
        } else {
            newParent = pivot.getRightChild();
        }
        
        assert (newParent != null); 
        // rotate nodes
        if (left) {
            pivot.setLeftChild(newParent.getRightChild());
            newParent.setRightChild(pivot);
        } else {
            pivot.setRightChild(newParent.getLeftChild());
            newParent.setLeftChild(pivot);
        }
        pivot.setParent(newParent);

        //swap colors
        boolean temp = pivot.getColor();
        pivot.setColor(newParent.getColor());
        newParent.setColor(temp);

        // reattach children
        if (pivot.getRightChild() != null) {
            pivot.getRightChild().setParent(pivot);
        }
        if (pivot.getLeftChild() != null) {
            pivot.getLeftChild().setParent(pivot);
        }

        // reconnect to parent
        if (parent != null) {
            if (pivot.equals(parent.getLeftChild())) {
                parent.setLeftChild(newParent);
            } else if (pivot.equals(parent.getRightChild())) {
                parent.setRightChild(newParent);
            }
        }
        newParent.setParent(parent);
        if (pivot.equals(this.root)) this.root = newParent;
    }

    public void delete(TreeNode<T> node) {
        this.delete(node.getData());
    }

    public void delete(T key) {
        TreeNode<T> trash = this.find(key);
        if (trash == null) return; //no key to delete
        if (trash.isLeaf()) { //0 children
            this.replaceNode(trash, null);
        } else if (trash.getRightChild() != null && trash.getLeftChild() != null) { //2 children
            //TODO!
        } else { //1 child
            if (trash.getRightChild() == null) {
                this.replaceNode(trash, trash.getLeftChild());
            } else { //leftChild is null
                this.replaceNode(trash, trash.getRightChild()); 
            }
        }
    }

    private void replaceNode(TreeNode<T> node, TreeNode<T> replaceWith) {
        replaceWith.setParent(node.getParent());
        if (node.equals(node.getParent().getLeftChild())) {
            node.getParent().setLeftChild(replaceWith);
        } else {
            node.getParent().setRightChild(replaceWith);
        }
    }

    public TreeNode<T> find(T key) {
        TreeNode<T> currNode = this.getRoot();
        T currData = currNode.getData();
        while (currData.compareTo(key) != 0) {
            if (currData.compareTo(key) < 0) {
                currNode = currNode.getLeftChild();
            } else if (currData.compareTo(key) > 0) {
                currNode = currNode.getRightChild();
            } else {
                return currNode;
            }
        }
        return null;
    }

    public boolean contains(T key) {
        return this.find(key) != null;
    }

    public boolean contains(TreeNode<T> node) {
        T data = node.getData();
        return this.contains(data);
    }

    public static RedBlackTree<Integer> generateIntegerTree() {
        return generateIntegerTree(DEFAULT_TREE_SIZE);
    }

    public static RedBlackTree<Integer> generateIntegerTree(int size) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < size; i++) {
            System.out.println("Creating and inserting node with data " + i);
            tree.emplace(i);
        }
        return tree;
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> joinTrees(RedBlackTree<T> lhs, T partition,
            RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> splitTree(RedBlackTree<T> tree, T partition) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> union(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> intersect(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> difference(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> symmetricDifference(RedBlackTree<T> lhs, RedBlackTree<T> rhs) {
        throw new RuntimeException("Not yet implemented!");
    }

    /*
     * Pretty-prints the tree in level order using multiple queues.
     */
    @Override
    public String toString() {
        //weakly typed implementation
        ArrayList<Queue<String>> levelArray = new ArrayList<>(); 
        this.toStringHelper(levelArray, this.root, 0);
        StringBuilder sb = new StringBuilder();
        int height = levelArray.size();
        int width = (int) Math.pow(2, height) - 1;
        for (int i = 0; i < height; i++) {
            int firstIndex = width / (int) Math.pow(2, (i + 1));
            int nodesAtLevel = levelArray.get(i).size();
            int padding = (width - 2 * firstIndex - nodesAtLevel) / Math.max(1, (nodesAtLevel - 1));
            for (int j = 0; j < firstIndex; j++) sb.append(' ');
            Queue<String> nodeQueue = levelArray.get(i);
            while (!nodeQueue.isEmpty()) {
                String data = nodeQueue.remove();
                sb.append(data);
                for (int j = 0; j < padding; j++) sb.append(' ');
            }
            sb.append('\n');
            if (i == height - 1) continue;
            for (int j = 0; j < firstIndex - 1; j++) sb.append(' ');
            for (int j = 0; j < levelArray.get(i+1).size(); j++) {
                sb.append(j % 2 == 0 ? '/' : "\\");
                for (int k = 0; k < padding; k++) sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private void toStringHelper(ArrayList<Queue<String>> arr, TreeNode<T> curr, int depth) {
        if (curr != null) {
            if (arr.size() <= depth) {
                arr.add(new LinkedList<>());
            }
            arr.get(depth).add(curr.toString());
            this.toStringHelper(arr, curr.getLeftChild(), depth + 1);
            this.toStringHelper(arr, curr.getRightChild(), depth + 1);
        }
    }

    public int getHeight() {
        return getHeightHelper(this.root);
    }

    private int getHeightHelper(TreeNode<T> node) {
        if (node == null) return 0;
        return Math.max(getHeightHelper(node.getLeftChild()), getHeightHelper(node.getRightChild())) + 1;
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

        public boolean isLeaf() {
            return this.left_child == null && this.right_child == null;
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
            .append(": ")
            .append(this.red ? "R" : "B");
            return sb.toString();
        }

    }

}