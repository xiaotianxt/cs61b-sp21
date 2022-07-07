package deque;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;

public class EqualTest {
    @Test
    public void testArrayDeque() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
            ad2.addLast(i);
        }

        assertEquals(ad1, ad2);
    }

    @Test
    public void testLinkedListDeque() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
            lld2.addLast(i);
        }

        assertEquals(lld1, lld2);
    }

    @Test
    /* Deque needs to be equal to itself */
    public void testEqualToItself() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertTrue(lld.equals(lld));
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.equals(ad));
    }

    private void testNotEqual(Deque<Integer> d1, Deque<Integer> d2) {
        assertEquals(d1, d2);

        d1.addFirst(0);
        assertNotEquals(d1, d2);

        d2.addLast(0);
        assertEquals(d1, d2);
    }

    @Test
    /* Test not equal */
    public void testNotEqual() {
        testNotEqual(new ArrayDeque<>(), new ArrayDeque<>());
        testNotEqual(new LinkedListDeque<>(), new LinkedListDeque<>());
    }

    @Test
    /* Test different deque */
    public void testDifferentDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertTrue(ad.equals(lld));
        ad.addFirst(0);
        lld.addFirst(0);
        assertTrue(ad.equals(lld));
        ad.removeFirst();
        assertFalse(ad.equals(lld));
    }
}
