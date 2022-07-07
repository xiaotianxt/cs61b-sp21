package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> c_;

    public MaxArrayDeque(Comparator<T> c) {
        c_ = c;
    }

    /**
     * Returns the maximum item of the deque.
     */
    public T max() {
        return max(c_);
    }

    /**
     * Returns the maximum item of the deque with given comparator.
     */
    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }

        T max = this.get(0);
        for (T item : this) {
            if (c.compare(item, max) > 0) {
                max = item;
            }
        }

        return max;
    }
}
