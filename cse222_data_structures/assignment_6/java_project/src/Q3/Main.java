package Q3;

public class Main {
    public static void main(String[] args){
        System.out.println("==========================================");
        BinarySearchTree<Integer> balancedBST = new BinarySearchTree<>();
        balancedBST.add(10);

        balancedBST.add(8);
        balancedBST.add(6);
        balancedBST.add(9);

        balancedBST.add(12);
        balancedBST.add(11);
        balancedBST.add(14);

        AVLTree<Integer> validAVLTree = new AVLTree<>(balancedBST);
        System.out.println(validAVLTree);

        System.out.println("==========================================");

        BinarySearchTree<Integer> imbalancedBST = new BinarySearchTree<>();

        imbalancedBST.add(40);
        imbalancedBST.add(30);
        imbalancedBST.add(20);
        imbalancedBST.add(10);
        imbalancedBST.add(50);

        AVLTree<Integer> invalidAVLTree = new AVLTree<>(imbalancedBST);
        System.out.println(invalidAVLTree);

        System.out.println("==========================================");

        BinarySearchTree<Integer> BST = new BinarySearchTree<>();

        BST.add(10);
        BST.add(9);
        BST.add(11);
        BST.add(8);
        BST.add(12);

        AVLTree<Integer> avlTree = new AVLTree<>(BST);
        System.out.println(avlTree);

        System.out.println("==========================================");
        avlTree.add(13);
        System.out.println(avlTree);

        System.out.println("==========================================");
        avlTree.delete(10);
        System.out.println(avlTree);

        System.out.println("==========================================");
        avlTree.delete(11);
        System.out.println(avlTree);

        System.out.println("==========================================");
        avlTree.delete(8);
        System.out.println(avlTree);

        System.out.println("==========================================");
    }
}
