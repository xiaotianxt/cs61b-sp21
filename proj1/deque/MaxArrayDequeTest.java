package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MaxArrayDequeTest {
    private final Comparator<Integer> comparator = Comparator.comparingInt(o -> o);

    @Test
    /* Test max method. */
    public void testMax() {
        MaxArrayDeque<Integer> md = new MaxArrayDeque<>(comparator);
        assertNull(md.max());
        md.addLast(0);
        md.addLast(1);
        assertEquals(1, (int) md.max());
    }

    @Test
    /* Test max with argument. */
    public void testMaxArgument() {
        // This comparator thinks even number is always larger than odd number.
        MaxArrayDeque<Integer> md = new MaxArrayDeque<>(comparator);
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 % 2 == 0) {
                    if (o2 % 2 == 0) {
                        return o1 - o2;
                    } else {
                        return 1;
                    }
                } else {
                    if (o2 % 2 == 0) {
                        return -1;
                    } else {
                        return o1 - o2;
                    }
                }
            }
        };

        md.addLast(2);
        md.addLast(1);
        md.addLast(3);
        md.addLast(5);
        md.addLast(7);
        assertEquals(2, (int) md.max(c));
        md.removeFirst();
        assertEquals(7, (int) md.max(c));
    }
}
