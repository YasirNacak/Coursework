import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for General Tree that is extended from Binary Tree
 * This class controls all of the public methods and their possible
 * outcomes
 * @author Yasir
 */
public class GeneralTreeTest {

    /**
     * Creates a general tree and tries to add an element that
     * has a parent in the created tree
     * Expected result is a successful addition
     */
    @Test
    public void addSuccessful() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        boolean isSuccessful = generalTree.add(1, 2);
        Assert.assertTrue(generalTree.root.left.data == 2 && isSuccessful);
    }

    /**
     * Creates a general tree and tries to add an element that
     * hasn't got the given parent in the created tree
     * Expected result is an unsuccessful addition
     */
    @Test
    public void addUnsuccessful() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        boolean isSuccessful = generalTree.add(2, 3);
        Assert.assertTrue(generalTree.root.left == null && !isSuccessful);
    }

    /**
     * Creates a general tree with 7 nodes
     * and tries to traverse this tree level order and
     * compares the result of traversal with expected output
     */
    @Test
    public void levelOrderTraverseTest() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        generalTree.add(1, 2);
        generalTree.add(1, 4);
        generalTree.add(1, 7);

        generalTree.add(2, 3);
        generalTree.add(2, 6);

        generalTree.add(3, 5);

        StringBuilder stringBuilder = new StringBuilder();

        generalTree.levelOrderTraverse(generalTree.root, stringBuilder);
        Assert.assertEquals("1 2 4 7 3 6 5 ", stringBuilder.toString());
    }

    /**
     * Creates a general tree with 7 nodes and
     * tries to search an item in this tree using level order
     * traversal's search
     * Checks for the children of the found node to verify
     * that the search method is working
     */
    @Test
    public void levelOrderSearchTest() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        generalTree.add(1, 2);
        generalTree.add(1, 4);
        generalTree.add(1, 7);

        generalTree.add(2, 3);
        generalTree.add(2, 6);

        generalTree.add(3, 5);

        Assert.assertTrue(generalTree.levelOrderSearch(2).left.data.equals(3) &&
                generalTree.levelOrderSearch(2).right.data.equals(4));
    }

    /**
     * Creates a general tree with 7 nodes
     * and tries to traverse this tree post order and
     * compares the result of traversal with expected output
     */
    @Test
    public void postOrderTraverseTest() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        generalTree.add(1, 2);
        generalTree.add(1, 4);
        generalTree.add(1, 7);

        generalTree.add(2, 3);
        generalTree.add(2, 6);

        generalTree.add(3, 5);

        StringBuilder stringBuilder = new StringBuilder();

        generalTree.postOrderTraverse(generalTree.root, stringBuilder);
        Assert.assertEquals("5 3 6 2 4 7 1 ", stringBuilder.toString());
    }

    /**
     * Creates a general tree with 7 nodes and
     * tries to search an item in this tree using post order
     * traversal's search
     * Checks for the children of the found node to verify
     * that the search method is working
     */
    @Test
    public void postOrderSearchTest() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        generalTree.add(1, 2);
        generalTree.add(1, 4);
        generalTree.add(1, 7);

        generalTree.add(2, 3);
        generalTree.add(2, 6);

        generalTree.add(3, 5);

        Assert.assertTrue(generalTree.postOrderSearch(2).left.data.equals(3) &&
                generalTree.postOrderSearch(2).right.data.equals(4));
    }

    /**
     * Creates a general tree with 7 nodes
     * and tries to traverse this tree pre order and
     * compares the result of traversal with expected output
     */
    @Test
    public void preOrderTraverseTest() {
        GeneralTree<Integer> generalTree = new GeneralTree<>(1);
        generalTree.add(1, 2);
        generalTree.add(1, 4);
        generalTree.add(1, 7);

        generalTree.add(2, 3);
        generalTree.add(2, 6);

        generalTree.add(3, 5);

        StringBuilder stringBuilder = new StringBuilder();

        generalTree.preOrderTraverse(generalTree.root, 0,stringBuilder);
        Assert.assertEquals("1 2 3 5 6 4 7 ", stringBuilder.toString());
    }
}