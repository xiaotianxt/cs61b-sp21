package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();

        int[] items = {4, 5, 6};

        for (int i : items) {
            a.addLast(i);
            b.addLast(i);
        }

        for (int i = items.length - 1; i >= 0; i--) {
            int item = items[i];
            int itemA = a.removeLast();
            int itemB = b.removeLast();
            assertEquals(item, itemA, itemB);
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
                assertEquals(size, M.size());
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                int last = L.getLast();
                int mLast = M.getLast();
                System.out.println("getLast(): " + last);
                assertEquals(last, mLast);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                int last = L.removeLast();
                int mLast = M.removeLast();
                System.out.println("removeLast(): " + last);
                assertEquals(last, mLast);
            }
        }
    }
}
