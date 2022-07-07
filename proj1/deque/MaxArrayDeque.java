package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> _c;

    public MaxArrayDeque(Comparator<T> c) {
        _c = c;
    }

    /**
     * Returns the maximum item of the deque.
     */
    public T max() {
        return max(_c);
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
