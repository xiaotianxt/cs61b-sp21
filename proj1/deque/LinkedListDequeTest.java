package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    /**
     * Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     * <p>
     * && is the "and" operation.
     */
    @Test
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    /**
     * Adds an item, then removes an item, and ensures that dll is empty afterwards.
     */
    @Test
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

        assertEquals("string", s);
        assertEquals(3.14159, d, 1e-5);
        assertTrue(b);
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());
    }

    @Test
    /* Test if get works fine. */
    public void getTest() {
        LinkedListDeque<String> lld = new LinkedListDeque<>();

        String a = "first";
        String b = "middle";
        String c = "last";

        lld.addFirst(b);
        lld.addFirst(a);
        lld.addLast(c);
        lld.printDeque();

        assertEquals(lld.get(0), a);
        assertEquals(lld.get(1), b);
        assertEquals(lld.get(2), c);

        assertNull(lld.get(3));

        lld.removeFirst();
        lld.printDeque();
        assertEquals(lld.get(0), b);
        assertEquals(lld.get(1), c);
        assertNull(lld.get(2));

        lld.removeLast();
        lld.printDeque();
        assertEquals(lld.get(0), b);
        assertNull(lld.get(1));

        lld.removeLast();
        lld.printDeque();
        assertNull(lld.get(0));
        assertNull(lld.get(1));
        assertNull(lld.get(2));
        assertNull(lld.get(-1));
    }


    @Test
    /* Recursive get test. */
    public void recursiveGetTest() {
        LinkedListDeque<String> lld = new LinkedListDeque<>();

        String a = "first";
        String b = "middle";
        String c = "last";

        lld.addFirst(b);
        lld.addFirst(a);
        lld.addLast(c);

        assertEquals(lld.getRecursive(0), a);
        assertEquals(lld.getRecursive(1), b);
        assertEquals(lld.getRecursive(2), c);

        assertNull(lld.getRecursive(3));

        lld.removeFirst();
        assertEquals(lld.getRecursive(0), b);
        assertEquals(lld.getRecursive(1), c);
        assertNull(lld.getRecursive(2));

        lld.removeLast();
        assertEquals(lld.getRecursive(0), b);
        assertNull(lld.getRecursive(1));

        lld.removeLast();
        assertNull(lld.getRecursive(0));
        assertNull(lld.getRecursive(1));
        assertNull(lld.getRecursive(2));
        assertNull(lld.getRecursive(-1));
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    /* Test iterator. */
    public void testIterator() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 1; i <= 10; i++) {
            lld.addLast(i);
        }

        int temp = 1;
        for (int i : lld) {
            assertEquals(i, temp++);
        }
    }
}
