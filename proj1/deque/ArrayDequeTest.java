package deque;

import org.junit.Test;
import sun.tools.tree.ArrayExpression;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testAddFirstWithoutResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(7);
        deque.addFirst(6);
        deque.addFirst(5);
        deque.addFirst(4);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals("1 2 3 4 5 6 7", deque.toString());
    }

    @Test
    public void testAddLastWithoutResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        assertEquals("1 2 3 4 5 6 7", deque.toString());
    }

    @Test
    public void testRemoveFirstWithoutResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 2; i <= 8; i++) {
            deque.addLast(i);
        }

        assertEquals("2 3 4 5 6 7 8", deque.toString());

        int ret = deque.removeFirst();
        assertEquals("3 4 5 6 7 8", deque.toString());
        assertEquals(ret, 2);

        ret = deque.removeFirst();
        assertEquals("4 5 6 7 8", deque.toString());
        assertEquals(ret, 3);

        ret = deque.removeFirst();
        assertEquals("5 6 7 8", deque.toString());
        assertEquals(ret, 4);

        ret = deque.removeFirst();
        assertEquals("6 7 8", deque.toString());
        assertEquals(ret, 5);

        ret = deque.removeFirst();
        assertEquals("7 8", deque.toString());
        assertEquals(ret, 6);

        ret = deque.removeFirst();
        assertEquals("8", deque.toString());
        assertEquals(ret, 7);


        ret = deque.removeFirst();
        assertEquals("", deque.toString());
        assertEquals(ret, 8);
    }


    @Test
    public void testRemoveLastWithoutResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i < 8; i++) {
            deque.addLast(i);
        }
        assertEquals("1 2 3 4 5 6 7", deque.toString());

        int ret = deque.removeLast();
        assertEquals("1 2 3 4 5 6", deque.toString());
        assertEquals(ret, 7);

        ret = deque.removeLast();
        assertEquals("1 2 3 4 5", deque.toString());
        assertEquals(ret, 6);

        ret = deque.removeLast();
        assertEquals("1 2 3 4", deque.toString());
        assertEquals(ret, 5);

        ret = deque.removeLast();
        assertEquals("1 2 3", deque.toString());
        assertEquals(ret, 4);

        ret = deque.removeLast();
        assertEquals("1 2", deque.toString());
        assertEquals(ret, 3);

        ret = deque.removeLast();
        assertEquals("1", deque.toString());
        assertEquals(ret, 2);


        ret = deque.removeLast();
        assertEquals("", deque.toString());
        assertEquals(ret, 1);
    }

    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 9; i >= 0; i--) {
            ad.addFirst(i);
        }

        assertEquals("0 1 2 3 4 5 6 7 8 9", ad.toString());
        assertEquals(10, ad.size());
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
            sb.append(i).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        assertEquals(sb.toString(), ad.toString());
        assertEquals(100, ad.size());
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            ad.addLast(i % 10);
            sb.append(i % 10).append(' ');
        }

        System.out.println(ad);

        for (int i = 0; i < 16; i++) {
            int first = ad.removeFirst();
            assertEquals(i % 10, first);
        }
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            ad.addLast(i % 10);
            sb.append(i % 10).append(' ');
        }

        System.out.println(ad);

        for (int i = 15; i >= 0; i--) {
            int last = ad.removeLast();
            assertEquals(i % 10, last);
        }
    }


    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        Deque<Integer> lld1 = new ArrayDeque<>();
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
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

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

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
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

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
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
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

}
