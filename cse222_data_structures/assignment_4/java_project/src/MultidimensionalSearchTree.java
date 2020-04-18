import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Multidimensional search tree class
 * All nodes in this class has n elements stored in them
 * and adding depends on the current depth of the class
 * @author Yasir
 */
public class MultidimensionalSearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E>{
    /**
     * Extension of the node in the binary tree class
     * Adds the dimension factor to the node so it
     * can be used in the multidimensional tree
     */
    protected static class MultidimensionalNode<E extends Comparable<E>> extends Node<E>{
        Vector<E> nodeData;

        /**
         * Constructor
         * @param item first item in the node
         * @param nodeData a vector containing all of the data
         */
        MultidimensionalNode(E item, Vector<E> nodeData){
            this.data = item;
            this.nodeData = nodeData;
        }

        /**
         * toString override
         * Calls the toString of the vector class
         * @return string representation of the vector in this node
         */
        @Override
        public String toString() {
            return nodeData.toString();
        }
    }

    /**
     * The number of elements in one node in the tree
     */
    private int dimension;

    /**
     * List of all the elements that are added to the array
     * in the order of addition
     */
    private Vector<Vector<E>> addedElements;

    /**
     * Single Parameter constructor
     * Calls the 2 parameter constructor
     * @param item first item in the tree
     */
    MultidimensionalSearchTree(Vector<E> item){
        this(3, item);
    }

    /**
     * Two parameter constructor
     * Initializes the tree with given amount of dimensions
     * @param dimension
     * @param item
     */
    MultidimensionalSearchTree(int dimension, Vector<E> item){
        root = new MultidimensionalNode(item.get(0), item);
        this.dimension = dimension;
        addedElements = new Vector<>();
        addedElements.add(item);
    }

    /**
     * Adds an element to the tree at the appropriate position
     * @param item item that is going to be added
     * @return since the tree is always available for addition, returns true
     */
    @Override
    public boolean add(Vector<E> item) {
        MultidimensionalNode itemNode = new MultidimensionalNode(item.get(0), item);
        add((MultidimensionalNode) root, itemNode, 0);
        addedElements.add(item);
        return true;
    }

    /**
     * Helper method of the public add method
     * Works recursively and finds the correct spot for the
     * item that is needed to be added to the tree
     * @param currentNode node that needs to be controlled
     * @param item item that is going to be added
     * @param depth how deep is the current node this helps with dimension factor
     */
    private void add(MultidimensionalNode currentNode, MultidimensionalNode item, int depth){
        if(((E)currentNode.nodeData.get(depth%dimension)).compareTo((E)item.nodeData.get(depth%dimension)) > 0){
            if(currentNode.left != null) {
                currentNode = (MultidimensionalNode) currentNode.left;
                add(currentNode, item, depth + 1);
            } else {
                currentNode.left = item;
            }
        } else {
            if(currentNode.right != null) {
                currentNode = (MultidimensionalNode) currentNode.right;
                add(currentNode, item, depth + 1);
            } else {
                currentNode.right = item;
            }
        }
    }

    /**
     * Checks if a given element is in the tree
     * @param target item to be searched in the tree
     * @return true if the item is in, false otherwise
     */
    @Override
    public boolean contains(Vector<E> target) {
        Queue<MultidimensionalNode> nodeQueue = new LinkedList<>();
        if(this.root != null) {
            nodeQueue.add((MultidimensionalNode) root);
            while (!nodeQueue.isEmpty()) {
                MultidimensionalNode tempNode = nodeQueue.remove();
                if (tempNode.left != null) nodeQueue.add((MultidimensionalNode)tempNode.left);
                if (tempNode.right != null) nodeQueue.add((MultidimensionalNode)tempNode.right);
                boolean areEqual = false;
                int timesEqual = 0;
                for(int i=0; i<target.size(); i++)
                    if(target.get(i).equals(tempNode.nodeData.get(i)))
                        timesEqual++;
                if(target.size() == timesEqual) areEqual = true;
                if (areEqual) return true;
            }
        }
        return false;
    }

    /**
     * Returns the value of the item in the list
     * @param target item to be searched
     * @return the value of the target if it is in the tree, null otherwise
     */
    @Override
    public Vector<E> find(Vector<E> target) {
        if(contains(target))
            return target;
        else return null;
    }

    /**
     * removes an element from the tree and returns the value of the deleted node
     * @param target item to be deleted
     * @return contents of the deleted item
     */
    @Override
    public Vector<E> delete(Vector<E> target) {
        remove(target);
        return target;
    }

    /**
     * Removes an item from the tree if the tree contains it
     * @param target item to be removed from the tree
     * @return true if the item is in the tree, false otherwise
     */
    @Override
    public boolean remove(Vector<E> target) {
        if(!contains(target)) {
            return false;
        } else {
            addedElements.remove(target);
            this.root = new MultidimensionalNode<E>(addedElements.get(0).get(0), addedElements.get(0));
            Vector<Vector<E>> previouslyAddedElements = new Vector<>();
            for (int i=0; i<addedElements.size(); i++)
                previouslyAddedElements.add(addedElements.get(i));
            addedElements.clear();
            for(int i=1; i<previouslyAddedElements.size(); i++)
                add(previouslyAddedElements.get(i));
            return true;
        }
    }

    /**
     * Recursively constructs the string representation of the tree
     * @param currentNode which node should the string builder needs to check
     * @param sb the string builder that is going to hold the values of the tree eventually
     */
    private void constructString(MultidimensionalNode currentNode, StringBuilder sb){
        sb.append("node: " + currentNode + "\n");
        sb.append("left child: ");
        if(currentNode.left != null) {
            sb.append(currentNode.left + "\n");
        } else {
            sb.append("null" + "\n");
        }
        sb.append("right child: ");
        if(currentNode.right != null){
            sb.append(currentNode.right + "\n");
        } else {
            sb.append("null" + "\n");
        }
        sb.append("------------------------\n");
        if(currentNode.left != null)
            constructString((MultidimensionalNode) currentNode.left, sb);
        if(currentNode.right != null)
            constructString((MultidimensionalNode)currentNode.right, sb);
    }

    @Override
    public String toString() {
        if(root == null){
            return "empty tree";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            constructString((MultidimensionalNode)root, stringBuilder);
            stringBuilder.append("/////////////////////////////////////////////");
            return stringBuilder.toString();
        }
    }
}
