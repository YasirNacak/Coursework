import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Test Class of the SwitchableLinkedList class.
 * Switchable linked list is a modification of
 * the classic linked list found in the java collections.
 * This class adds enable/disable capabilities to the
 * standard linked list.
 */
public class SwitchableLinkedListTest {

    /**
     * Tests disabling an element based on an index that is in the list.
     */
    @Test
    public void disableIndexBasedSuccess() {
        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");
        switchableLinkedList.add("C");
        switchableLinkedList.add("D");
        switchableLinkedList.add("E");

        switchableLinkedList.disable(0);

        Assert.assertTrue(switchableLinkedList.get(0).equals("B") && switchableLinkedList.get(3).equals("E"));
    }

    /**
     * Tests disabling an element based on an index that is NOT in the list.
     */
    @Test
    public void disableIndexBasedInvalidIndex(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");

        switchableLinkedList.disable(6);

        assertEquals("ERROR: Invalid Index For Removal.\n", outContent.toString());
    }

    /**
     * Tests disabling an element using the element itself. The element is in the list.
     */
    @Test
    public void disableElementBasedSuccess() {
        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");
        switchableLinkedList.add("C");
        switchableLinkedList.add("D");
        switchableLinkedList.add("E");

        switchableLinkedList.disable("C");

        Assert.assertTrue(switchableLinkedList.get(2).equals("D") && switchableLinkedList.get(3).equals("E"));
    }

    /**
     * Tests disabling an element using the element itself. The element is NOT in the list.
     */
    @Test
    public void disableElementBasedNonexistentElement(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");

        switchableLinkedList.disable("C");

        assertEquals("ERROR: Item Not Found.\n", outContent.toString());
    }

    /**
     * Tests enabling an element that is disabled.
     */
    @Test
    public void enable() {
        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");
        switchableLinkedList.add("C");
        switchableLinkedList.add("D");
        switchableLinkedList.add("E");

        switchableLinkedList.disable("C");
        switchableLinkedList.enable("C");

        Assert.assertTrue(switchableLinkedList.get(2).equals("C"));
    }

    /**
     * Tries to list the disabled elements where there is at least 1 disabled.
     */
    @Test
    public void listDisabledWithDisabledElements() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");
        switchableLinkedList.add("C");
        switchableLinkedList.add("D");
        switchableLinkedList.add("E");

        switchableLinkedList.disable("A");

        switchableLinkedList.listDisabled();

        Assert.assertEquals("Disabled Item(s):\nA\n", outContent.toString());
    }

    /**
     * Tries to list the disabled elements where there are no disabled elements.
     */
    @Test
    public void listDisabledWithoutAnyDisabled() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");
        switchableLinkedList.add("C");
        switchableLinkedList.add("D");
        switchableLinkedList.add("E");

        switchableLinkedList.listDisabled();

        Assert.assertEquals("ERROR: No Items Are Disabled.\n", outContent.toString());
    }

    /**
     * Tries to print all enabled elements in the list.
     */
    @Test
    public void printElements() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SwitchableLinkedList<String> switchableLinkedList = new SwitchableLinkedList<String>();
        switchableLinkedList.add("A");
        switchableLinkedList.add("B");

        switchableLinkedList.printElements();

        Assert.assertEquals("Printing Elements In Order:\nA B \n", outContent.toString());
    }
}