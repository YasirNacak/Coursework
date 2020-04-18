package Q3;

/*<listing chapter="9" section="2">*/

import java.util.ArrayList;

/**
 * Self-balancing binary search tree using the algorithm defined
 * by Adelson-Velskii and Landis.
 * @author Koffman and Wolfgang
 */
public class AVLTree<E extends Comparable<E>>
        extends BinarySearchTreeWithRotate<E> {

    // Insert nested class AVLNode<E> here.
    /*<listing chapter="9" number="2">*/
    /** Class to represent an AVL Node. It extends the
     * BinaryTree.Node by adding the balance field.
     */
    private static class AVLNode<E> extends Node<E> {
        /** balance is right subtree height - left subtree height */
        private int balance;

        // Methods
        /**
         * Construct a node with the given item as the data field.
         * @param item The data field
         */
        AVLNode(E item) {
            super(item);
            balance = 0;
        }
    }
    /*</listing>*/
    // Data Fields
    /** Flag to indicate that height of tree has increased. */
    private boolean increase;

    /** flag to indicate that height of the tree has decreased */
    private boolean decrease;

    /** required list of elements for the constructor */
    private ArrayList<E> elementsFromBST;

    /**
     * finds height of a node recursively
     * @param node the node that needs to find height of
     * @return height of the given node
     */
    private int nodeHeight(Node node){
        int leftHeight = 0, rightHeight = 0;
        if(node.left != null) leftHeight = nodeHeight(node.left);
        if(node.right != null) rightHeight = nodeHeight(node.right);
        return 1 + Integer.max(leftHeight, rightHeight);
    }

    /**
     * determines the addition order when the constructor is called
     * @param bst bst parameter in constructor
     */
    public void getLevelOrderFromBST(BinarySearchTree bst) {
        int h = nodeHeight(bst.root);
        for (int i=1; i<=h; i++) traverseLevel(bst.root, i);
    }

    /**
     * recursive level order traversal method
     * @param root root of the current subtree
     * @param level level of the given root
     */
    private void traverseLevel (Node root,  int level) {
        if (root == null) return;
        if (level == 1) elementsFromBST.add((E)root.data);
        else if (level > 1)
        {
            traverseLevel(root.left, level-1);
            traverseLevel(root.right, level-1);
        }
    }

    /**
     * Constructor that takes a binary search tree.
     * Checks if the given BST is an AVL tree or not.
     * Throws and exception if the parameter is not an AVL tree.
     * @param bst the Binary Search Tree that needs to be checked
     */
    public AVLTree(BinarySearchTree bst){
        elementsFromBST = new ArrayList<E>();
        int rightHeight = 0, leftHeight = 0;
        if(bst.root.right != null) rightHeight = nodeHeight(bst.root.right);
        if(bst.root.left != null) leftHeight = nodeHeight(bst.root.left);
        System.out.println("abs diff of heights: " + Math.abs(rightHeight-leftHeight));
        if(Math.abs(rightHeight-leftHeight) <= 1) {
            getLevelOrderFromBST(bst);
            for(E i : elementsFromBST) add(i);
        }
        else System.out.println("This Binary Search Tree is not an AVL Tree. Can't create tree!");
    }

    // Methods
    /**
     * add starter method.
     * @pre the item to insert implements the Comparable interface.
     * @param item The item being inserted.
     * @return true if the object is inserted; false
     *         if the object already exists in the tree
     * @throws ClassCastException if item is not Comparable
     */
    @Override
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method. Inserts the given object into the tree.
     * @post addReturn is set true if the item is inserted,
     *       false if the item is already in the tree.
     * @param localRoot The local root of the subtree
     * @param item The object to be inserted
     * @return The new local root of the subtree with the item
     *         inserted
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        if (item.compareTo(localRoot.data) == 0) {
            // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item < data
            localRoot.left = add((AVLNode<E>) localRoot.left, item);

            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < -1) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot; // Rebalance not needed.
        } else { // item > data
            // Insert solution to programming exercise 2, section 2, chapter 9 here
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if(increase == true){
                incrementBalance(localRoot);
                return localRoot.balance > 1 ? rebalanceRight(localRoot) : localRoot;
            }
            return localRoot;
        }
    }

    /*<listing chapter="9" number="3">*/
    /**
     * Method to rebalance left.
     * @pre methodRoot is the root of an AVL subtree that is
     *      critically left-heavy.
     * @post Balance is restored.
     * @param methodRoot Root of the AVL subtree
     *        that needs rebalancing
     * @return a new methodRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> methodRoot) {
        System.out.println("METHOD : REBALANCE LEFT");
        AVLNode<E> leftChild = (AVLNode<E>) methodRoot.left;
        if (leftChild.balance > 0) {
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            if (leftRightChild.balance < 0) {
                leftChild.balance = -1;
                leftRightChild.balance = 0;
                methodRoot.balance = 0;
            } else if (leftRightChild.balance > 0) {
                leftChild.balance = 0;
                leftRightChild.balance = 0;
                methodRoot.balance = 1;
            } else {
                leftChild.balance = 0;
                methodRoot.balance = 0;
            }
            methodRoot.left = rotateLeft(leftChild);
        } else {
            leftChild.balance = 0;
            methodRoot.balance = 0;
        }
        return (AVLNode<E>) rotateRight(methodRoot);
    }
    /*</listing>*/

    /**
     * method that rebalances right
     * slightly modified version of the rebalanceLeft
     * @param methodRoot root of the subtree that needs rebalance
     * @return new root of the subtree
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> methodRoot) {
        System.out.println("METHOD : REBALANCE RIGHT");
        AVLNode<E> rightChild = (AVLNode<E>) methodRoot.right;
        if (rightChild.balance < 0) {
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            if (rightLeftChild.balance > 0) {
                rightChild.balance = 0;
                rightLeftChild.balance = 0;
                methodRoot.balance = -1;
            } else if (rightLeftChild.balance < 0) {
                rightChild.balance = 1;
                rightLeftChild.balance = 0;
                methodRoot.balance = 0;
            } else {
                rightChild.balance = 0;
                rightLeftChild.balance = 0;
                methodRoot.balance = 0;
            }
            increase = false;
            decrease = true;
            methodRoot.right = rotateRight(rightChild);
            return (AVLNode<E>) rotateLeft(methodRoot);
        } else {
            increase = false;
            decrease = true;
            rightChild.balance = 0;
            methodRoot.balance = 0;
            return (AVLNode<E>) rotateLeft(methodRoot);
        }
    }


    /**
     * Method to decrement the balance field and to reset the value of
     * increase.
     * @pre The balance field was correct prior to an insertion [or
     *      removal,] and an item is either been added to the left[
     *      or removed from the right].
     * @post The balance is decremented and the increase flags is set
     *       to false if the overall height of this subtree has not
     *       changed.
     * @param node The AVL node whose balance is to be incremented
     */
    private void decrementBalance(AVLNode<E> node) {
        System.out.println("METHOD : DECREMENT BALANCE");
        // Decrement the balance.
        node.balance--;
        if (node.balance == 0) {
            // If now balanced, overall height has not increased.
            increase = false;
        }
    }

    /**
     * increases the balance of the given node and changes the value of
     * increase and decrease depending on the end result of the increment.
     * @param node node that needs its balanced to increased
     */
    private void incrementBalance(AVLNode<E> node){
        System.out.println("METHOD : INCREMENT BALANCE");
        node.balance++;
        if(node.balance > 0){ increase = true; decrease = false; }
        else {increase = false; decrease = true; }
    }

    /**
     * recursive method that finds the child of a node with biggest value
     * @param node
     * @return
     */
    private E findChildWithBiggestValue(AVLNode<E> node) {
        System.out.println("METHOD : FIND CHILD WITH BIGGEST VALUE");
        E result;
        if (node.right.right != null) {
            result = findChildWithBiggestValue((AVLNode<E>) node.right);
            if (((AVLNode<E>) node.right).balance < -1) node.right = rebalanceLeft((AVLNode<E>)node.right);
            if (decrease) decrementBalance(node);
            return result;
        }
        result = node.right.data;
        node.right = node.right.left;
        decrementBalance(node);
        return result;
    }

    /**
     * recursive deletion from the tree
     * @param item item to be deleted
     * @param methodRoot root of the tree that changes with each call of this method
     * @return root of the new tree with item removed from it
     */
    private AVLNode<E> delete(E item, AVLNode<E> methodRoot){
        System.out.println("METHOD : RECURSIVE DELETE");
        if (item.compareTo(methodRoot.data) == 0) {
            deleteReturn = methodRoot.data;
            if (methodRoot.right == null) {
                decrease = true;
                return (AVLNode<E>) methodRoot.left;
            } else if (methodRoot.left == null) {
                decrease = true;
                return (AVLNode<E>) methodRoot.right;
            } else {
                if (methodRoot.left.right != null) {
                    methodRoot.data = findChildWithBiggestValue((AVLNode<E>) methodRoot.left);
                    if (((AVLNode<E>) methodRoot.left).balance < -1) methodRoot.left = rebalanceLeft((AVLNode<E>) methodRoot.left);
                    if (decrease) incrementBalance(methodRoot);
                    return methodRoot;
                } else {
                    methodRoot.data = methodRoot.left.data;
                    methodRoot.left = methodRoot.left.left;
                    incrementBalance(methodRoot);
                    return methodRoot;
                }
            }
        } else if (item.compareTo(methodRoot.data) < 0) {
            methodRoot.left = delete(item, (AVLNode<E>) methodRoot.left);
            if (decrease) {
                incrementBalance(methodRoot);
                return methodRoot.balance > 1 ? rebalanceRightLeft(methodRoot) : methodRoot;
            } else {
                return methodRoot;
            }
        } else if (item.compareTo(methodRoot.data) > 0) {
            methodRoot.right = delete(item, (AVLNode<E>) methodRoot.right);
            if (decrease) {
                decrementBalance(methodRoot);
                return methodRoot.balance < -1 ? rebalanceLeftRight(methodRoot) : methodRoot;
            } else {
                return methodRoot;
            }
        }
        return null;
    }

    /**
     * delete method that can be called by the user
     * does not contain private inner class information
     * @param item item to be deleted
     * @return removed item or null
     */
    public E delete(E item){
        System.out.println("METHOD : STARTER DELETE");
        decrease = false;
        root = delete(item,(AVLNode<E>) root);

        // inherited return value for the public deletion.
        // comes from the BinarySearchTree
        return deleteReturn;
    }

    /**
     * Special case of rebalance right where right subtree is balanced
     * this case can result when there is a deletion from the left subtree
     * and an imbalance occurs.
     * @param methodRoot
     * @return
     */
    private AVLNode<E> rebalanceLeftRight(AVLNode<E> methodRoot) {
        System.out.println("METHOD : REBALANCE LEFT RIGHT");
        AVLNode<E> leftChild = (AVLNode<E>)methodRoot.left;
        if (leftChild.balance > 0) {
            AVLNode<E> leftRightChild = (AVLNode<E>)leftChild.right;
            if (leftRightChild.balance < 0) {
                leftChild.balance = -1;
                leftRightChild.balance = 0;
                methodRoot.balance = 0;
            } else if (leftRightChild.balance > 0) {
                leftChild.balance = 0;
                leftRightChild.balance = 0;
                methodRoot.balance = 1;
            } else {
                leftChild.balance = 0;
                methodRoot.balance = 0;
            }
            increase = false;
            decrease = true;
            methodRoot.left = rotateLeft(leftChild);
            return (AVLNode<E>)rotateRight(methodRoot);
        }
        if (leftChild.balance < 0) {
            leftChild.balance = 0;
            methodRoot.balance = 0;
            increase = false;
            decrease = true;
        } else {
            leftChild.balance = 1;
            methodRoot.balance = -1;
        }
        return (AVLNode<E>)rotateRight(methodRoot);
    }

    /**
     * Special case of rebalance right where left subtree is balanced
     * this case can result when there is a deletion from the right subtree
     * and an imbalance occurs.
     * @param methodRoot
     * @return
     */
    private AVLNode<E> rebalanceRightLeft(AVLNode<E> methodRoot) {
        System.out.println("METHOD : REBALANCE RIGHT LEFT");
        AVLNode<E> rightChild = (AVLNode<E>) methodRoot.right;
        if (rightChild.balance < 0) {
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            if (rightLeftChild.balance > 0) {
                rightChild.balance = 1;
                rightLeftChild.balance = 0;
                methodRoot.balance = 0;
            } else if (rightLeftChild.balance < 0) {
                rightChild.balance = 0;
                rightLeftChild.balance = 0;
                methodRoot.balance = -1;
            } else {
                rightChild.balance = 0;
                methodRoot.balance = 0;
            }
            increase = false;
            decrease = true;
            methodRoot.right = rotateRight(rightChild);
            return (AVLNode<E>) rotateLeft(methodRoot);
        }
        if (rightChild.balance > 0) {
            rightChild.balance = 0;
            methodRoot.balance = 0;
            increase = false;
            decrease = true;
        } else {
            rightChild.balance = -1;
            methodRoot.balance = 1;
        }
        return (AVLNode<E>) rotateLeft(methodRoot);
    }


}
