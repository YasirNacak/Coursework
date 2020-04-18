import javafx.util.Pair;

import java.util.*;

/**
 * Binary tree representation of the general class
 * Extends the binary tree structure that is in the book
 * @author Yasir
 */
public class GeneralTree<E> extends BinaryTree<E> {
    private ArrayList<Pair<Node, Integer>> nodeLevels;

    /**
     * Constructor
     * @param rootItem starting item of the list
     */
    GeneralTree(E rootItem){
        this.root = new Node(rootItem);
        nodeLevels = new ArrayList<>();
    }

    /**
     * Adds an element to the list based on the
     * given parent element. If the parent does
     * not exist in the tree, returns false
     * otherwise returns true
     * @param parentItem already existing parent item
     * @param childItem the item wanted to be added
     * @return true if the adding is successful, false otherwise
     */
    public boolean add(E parentItem, E childItem){
        if(this.root == null){
            System.out.println("unsuccessful");
            return false;
        }
        Node startingNode = this.postOrderSearch(parentItem);
        if(startingNode != null) {
            if (startingNode.left == null) {
                startingNode.left = new Node(childItem);
            } else {
                Node tempNode = startingNode.left;
                while(tempNode.right != null){
                    tempNode = tempNode.right;
                }
                tempNode.right = new Node(childItem);
            }
            return true;
        }
        return false;
    }

    /**
     * Helper method for level order search
     * calculates the level of each node in the tree and
     * adds it to the private array of the class
     * @param startingNode first element that starts the search
     * @param depth level of depth for the node group
     */
    private void levelOrderHelper(Node startingNode, int depth){
        nodeLevels.add(new Pair<>(startingNode, depth));
        if(startingNode.left != null)
            levelOrderHelper(startingNode.left, depth+1);

        while (startingNode != null){
            nodeLevels.add(new Pair<>(startingNode, depth));
            if(startingNode.left != null)
                levelOrderHelper(startingNode.left, depth+1);
            startingNode = startingNode.right;
        }
    }

    /**
     * Level order traversing method
     * @param startingNode first element that starts the traversing
     * @param sb the resulting string of the traversal
     */
    public void levelOrderTraverse(Node startingNode, StringBuilder sb){
        levelOrderHelper(startingNode, 0);
        Collections.sort(nodeLevels, new Comparator<Pair<Node, Integer>>() {
            @Override
            public int compare(Pair<Node, Integer> o1, Pair<Node, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        ArrayList<Node> levelOrder = new ArrayList();
        for (int i=0; i<nodeLevels.size(); i++){
            if(!levelOrder.contains(nodeLevels.get(i).getKey())) {
                levelOrder.add(nodeLevels.get(i).getKey());
                sb.append(nodeLevels.get(i).getKey() + " ");
            }
        }
    }

    /**
     * Level order search
     * Utilizes the helper level order methods
     * @param element element to be searched in the array
     * @return null if the element does not exist, the node otherwise
     */
    public Node levelOrderSearch(E element){
        StringBuilder stringBuilder = new StringBuilder();
        levelOrderTraverse(root, stringBuilder);
        if(stringBuilder.toString().contains(element.toString()))
            return quickSearch(element);
        else return null;
    }

    /**
     * Post order traverse method
     * @param startingNode first element that starts the traversing
     * @param sb the resulting string of the traversal
     */
    public void postOrderTraverse(Node startingNode, StringBuilder sb){
        if(startingNode.left != null)
            postOrderTraverse(startingNode.left, sb);
        sb.append(startingNode + " ");
        if(startingNode.right != null)
            postOrderTraverse(startingNode.right, sb);
    }

    /**
     * Post order search method
     * @param element element to be searched in the array
     * @return null if the element does not exist, the node otherwise
     */
    public Node postOrderSearch(E element){
        StringBuilder stringBuilder = new StringBuilder();
        postOrderTraverse(root, stringBuilder);
        if(stringBuilder.toString().contains(element.toString()))
            return quickSearch(element);
        else return null;
    }

    /**
     * Pre order traverse method override
     * Being used in the toString
     * @param startingNode first element that starts the traversing
     * @param depth The depth
     * @param sb The string buffer to save the output
     */
    @Override
    public void preOrderTraverse(Node startingNode, int depth, StringBuilder sb){
        sb.append(startingNode + " ");
        if(startingNode.left != null)
            preOrderTraverse(startingNode.left, depth + 1, sb);
        if(startingNode.right != null)
            preOrderTraverse(startingNode.right, depth + 1, sb);
    }

    /**
     * Iterative quick search method
     * @param element element to be searched in the list
     * @return null if the element does not exist, the node otherwise
     */
    private Node quickSearch(E element){
        Queue<Node> nodeQueue = new LinkedList<>();
        if(this.root != null) {
            nodeQueue.add(root);
            while (!nodeQueue.isEmpty()) {
                Node tempNode = nodeQueue.remove();
                if (tempNode.left != null) nodeQueue.add(tempNode.left);
                if (tempNode.right != null) nodeQueue.add(tempNode.right);
                if (tempNode.data == element) return tempNode;
            }
        }
        return null;
    }

    /**
     * Override of the toString method
     * @return result of the post order traversal on this tree
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.preOrderTraverse(this.root, 1, stringBuilder);
        return stringBuilder.toString();
    }
}
