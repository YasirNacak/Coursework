import java.util.Arrays;
import java.util.Vector;

/**
 * Main test class
 * Does running tests for GeneralTree and MultidimensionalSearchTree classes
 * Tries to test most of the outcomes
 * @author Yasir
 */
public class Main {
    public static void main(String[] args){
        System.out.println("--------------------------------------------------------");
        System.out.println("FIRST TREE OF PART 1:");
        System.out.println("--------------------------------------------------------");
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        System.out.println(generalTree);
        generalTree.add(1, 2);
        System.out.println(generalTree);
        generalTree.add(1, 3);
        System.out.println(generalTree);
        generalTree.add(1, 4);
        System.out.println(generalTree);

        generalTree.add(2, 5);
        System.out.println(generalTree);
        generalTree.add(2, 6);
        System.out.println(generalTree);

        generalTree.add(3, 12);
        System.out.println(generalTree);
        generalTree.add(3, 13);
        System.out.println(generalTree);

        generalTree.add(4, 15);
        System.out.println(generalTree);

        generalTree.add(5, 7);
        System.out.println(generalTree);
        generalTree.add(5, 8);
        System.out.println(generalTree);
        generalTree.add(5, 9);
        System.out.println(generalTree);

        generalTree.add(6, 10);
        System.out.println(generalTree);
        generalTree.add(6, 11);
        System.out.println(generalTree);

        generalTree.add(12, 14);
        System.out.println(generalTree);

        generalTree.add(15, 16);
        System.out.println(generalTree);

        generalTree.add(16, 17);
        System.out.println(generalTree);
        generalTree.add(16, 18);
        System.out.println(generalTree);
        System.out.println("--------------------------------------------------------");
        System.out.println("END OF FIRST TREE IN PART 1");
        System.out.println("--------------------------------------------------------");

        System.out.println("--------------------------------------------------------");
        System.out.println("SECOND TREE OF PART 1:");
        System.out.println("--------------------------------------------------------");
        GeneralTree<Integer> generalTree2 = new GeneralTree<>(1);
        System.out.println(generalTree2);
        generalTree2.add(1, 2);
        System.out.println(generalTree2);
        generalTree2.add(1, 4);
        System.out.println(generalTree2);
        generalTree2.add(1, 7);
        System.out.println(generalTree2);

        generalTree2.add(2, 3);
        System.out.println(generalTree2);
        generalTree2.add(2, 6);
        System.out.println(generalTree2);

        generalTree2.add(3, 5);
        System.out.println(generalTree2);
        System.out.println("--------------------------------------------------------");
        System.out.println("END OF THE MAIN TEST FOR PART 1");
        System.out.println("--------------------------------------------------------");

        System.out.println("\n\n");

        System.out.println("--------------------------------------------------------");
        System.out.println("TEST OF PART 2");
        System.out.println("--------------------------------------------------------");

        Integer[] rootArray = {40, 45};
        MultidimensionalSearchTree mdSearchTree = new MultidimensionalSearchTree(2, new Vector<Integer>(Arrays.asList(rootArray)));

        Integer[] elementB = {15,70};
        mdSearchTree.add(new Vector<Integer>(Arrays.asList(elementB)));
        System.out.println(mdSearchTree);

        Integer[] elementC = {70,10};
        mdSearchTree.add(new Vector<Integer>(Arrays.asList(elementC)));
        System.out.println(mdSearchTree);

        Integer[] elementD = {69,50};
        mdSearchTree.add(new Vector<Integer>(Arrays.asList(elementD)));
        System.out.println(mdSearchTree);

        Integer[] elementE = {66,85};
        mdSearchTree.add(new Vector<Integer>(Arrays.asList(elementE)));
        System.out.println(mdSearchTree);

        Integer[] elementF = {85,90};
        mdSearchTree.add(new Vector<Integer>(Arrays.asList(elementF)));
        System.out.println(mdSearchTree);

        System.out.println(mdSearchTree);
        System.out.println("--------------------------------------------------------");
        System.out.println("END OF THE MAIN TEST FOR PART 2");
        System.out.println("--------------------------------------------------------");
    }
}
