package Q1;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random randomize = new Random();

        // setting the minimum number as 1 and maximum as 5000
        int startingNumber = 1 + randomize.nextInt(5000);

        System.out.println("starting number of our tree = " + startingNumber);

        RedBlackTree<Integer> tree = new RedBlackTree<>(startingNumber);

        // adding the next 21 elements to the red-black tree
        // this results in a tree width the height of 6 and node number of 22
        // and this is the worst case of a red-black tree
        for (int i = 0; i < 21; i++) {
            tree.insert(startingNumber + 1 + i);
        }

        // node visitor lambda definition to print out the values of the current node
        NodeVisitor v = (node, depth) -> {
            if (node.val() != null) {
                String s = node.color() == 0 ? "red" : "black";
                System.out.println("(value: " + node.val().toString() + ", color: " + s + ", depth: " + depth + ")");
            }
        };

        // inorder traversal to show the nodes of the tree
        tree.inorder(v, 0);
    }
}
