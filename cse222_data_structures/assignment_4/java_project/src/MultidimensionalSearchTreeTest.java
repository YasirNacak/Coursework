import org.junit.Assert;
import org.junit.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.Arrays;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Test class for the Multidimensional Search Tree class that
 * extends Binary Tree and implements the Search Tree interface
 * Test the possible cases of the methods in this class.
 * @author Yasir
 */
public class MultidimensionalSearchTreeTest {
    /**
     * All of the variables below are sample
     * elements for our test tree
     */
    /**/
    private Integer[] rootArray = {40, 45};
    private Integer[] elementB = {15,70};
    private Integer[] elementC = {70,10};
    private Integer[] elementD = {69,50};
    private Integer[] elementE = {66,85};
    private Integer[] elementF = {85,90};

    /**
     * Tries to add a 2D integer to the list and controls
     * if the 2D integer is added correctly
     */
    @Test
    public void addTest() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));

        Assert.assertTrue((multidimensionalSearchTree.root.right == null) &&
                multidimensionalSearchTree.root.left.data.equals(elementB[0]));
    }

    /**
     * Adds 3 elements to the tree and controls if the contains method
     * returns true for all of the added variables
     */
    @Test
    public void containsTestSuccessful() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementC)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementD)));
        Assert.assertTrue(multidimensionalSearchTree.contains(new Vector<>(Arrays.asList(elementB))) &&
                multidimensionalSearchTree.contains(new Vector<>(Arrays.asList(elementC))) &&
                multidimensionalSearchTree.contains(new Vector<>(Arrays.asList(elementD))));
    }

    /**
     * Adds 3 elements to the tree and controls if the contains method
     * returns false for another element that is not added
     */
    @Test
    public void containsTestUnsuccessful() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementC)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementD)));
        Assert.assertFalse(multidimensionalSearchTree.contains(new Vector<>(Arrays.asList(elementE))));
    }

    /**
     * Adds 2 elements to the tree and checks if the find method is working
     * by comparing the return value of the find method and the elements of
     * the added value
     */
    @Test
    public void findTest() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementC)));

        Assert.assertEquals(multidimensionalSearchTree.find(new Vector<>(Arrays.asList(elementB))),
                new Vector<>(Arrays.asList(elementB)));
    }

    /**
     * Adds an element to the tree and deletes it afterwards
     * Compares the return value of the delete method with the actual
     * value of the added element
     */
    @Test
    public void deleteTest() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));

        Assert.assertEquals(multidimensionalSearchTree.delete(new Vector<>(Arrays.asList(elementB))),
                new Vector<>(Arrays.asList(elementB)));
    }

    /**
     * Constructs a tree and and removes an element from it
     * Then uses contains method to control if the removed item is
     * still in the list or not
     */
    @Test
    public void removeTest() {
        MultidimensionalSearchTree<Integer> multidimensionalSearchTree;
        multidimensionalSearchTree = new MultidimensionalSearchTree<>(2, new Vector<>(Arrays.asList(rootArray)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementB)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementC)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementD)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementE)));
        multidimensionalSearchTree.add(new Vector<>(Arrays.asList(elementF)));

        multidimensionalSearchTree.remove(new Vector<>(Arrays.asList(elementD)));

        Assert.assertFalse(multidimensionalSearchTree.contains(new Vector<>(Arrays.asList(elementD))));
    }
}