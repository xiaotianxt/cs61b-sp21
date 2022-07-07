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
    /* LinkedList needs to be equal to itself */
    public void testEqualToItself() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertTrue(lld.equals(lld));
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.equals(ad));
    }
}
