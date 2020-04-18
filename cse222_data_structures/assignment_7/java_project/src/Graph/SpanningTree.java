package Graph;

import java.util.ArrayList;

/**
 * Represents the spanning tree for DFS and BFS applications
 * @author Yasir
 */
public class SpanningTree {
    /**
     * private inner class that represents a single element of the tree
     */
    private class Node{
        /**Value of the node*/
        private Vertex value;
        /**Children of the node*/
        private ArrayList<Node> children;
        /**
         * Constructor that takes a node and initializes the node
         * @param v vertex value of node node
         */
        public Node(Vertex v){
            value = v;
            children = new ArrayList<>();
        }

        /**
         * tostring override
         * @return returns the label of the value of the node
         */
        @Override
        public String toString() {
            return value.toString();
        }
    }

    /**Root of the tree*/
    private Node root;

    /**
     * Constructor that takes the value of the root node
     * @param val value set to be as the value of the root node
     */
    public SpanningTree(int val){
        root = new Node(new Vertex(val));
    }

    /**
     * Private method to find a node in the tree
     * used to add new values
     * @param root local root of the tree
     * @param val value to be searched for
     * @return null, if the value does not exist; node of the searched value, otherwise
     */
    private Node find(Node root, int val){
        if(root.value.getLabel() == val){
            return root;
        } else {
            for(Node n : root.children) {
                Node result = find(n, val);
                if(result != null) return result;
            }
        }
        return null;
    }

    /**
     * Adds an element as the child of a given element
     * @param parent parent to be searched for the node
     * @param val value of the added node
     */
    public void addChildren(int parent, int val){
        Node parentNode = find(root, parent);
        if(parentNode != null)
            parentNode.children.add(new Node(new Vertex(val)));
    }

    /**
     * Public print method, calls the recursive one
     * because the recursive requires an inner class to work
     */
    public void print(){
        print(root, 0);
    }

    /**
     * Recursive preorder print method
     * @param localRoot local root of the tree
     * @param depth current depth of the tree, used for placing spaces
     */
    private void print(Node localRoot, int depth){
        for (int i = 0; i < depth; i++) {
            System.out.print(" ");
        }
        System.out.println(localRoot.value);
        for (Node child : localRoot.children)
            print(child, depth + 2);
    }
}
