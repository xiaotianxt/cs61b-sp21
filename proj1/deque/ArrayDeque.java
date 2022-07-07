package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private static final int INITIAL_SIZE = 8;
    private static final int SCALE_FACTOR = 2;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_SIZE];
        size = 0;
        nextFirst = INITIAL_SIZE / 2;
        nextLast = INITIAL_SIZE / 2 + 1;
    }

    /**
     * Adds an item of type `T' to the front of the deque.
     */
    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        checkSize();
    }

    /**
     * Adds an item of type `T' to the back of the deque.
     */
    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % items.length;
        checkSize();
    }

    /**
     * Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last,
     * separated by a space.
     */
    @Override
    public void printDeque() {
        for (T item : this) {
            System.out.print(item + " ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns `null'.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        nextFirst = first();

        T oldFirst = items[nextFirst];
        items[nextFirst] = null;

        size -= 1;
        checkSize();
        return oldFirst;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns `null'.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        nextLast = last();

        T oldLast = items[nextLast];
        items[nextLast] = null;

        size -= 1;
        checkSize();
        return oldLast;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * If no such item exists, returns `null'.
     */
    @Override
    public T get(int index) {
        return items[(first() + index) % items.length];
    }

    /**
     * Returns an iterator to make sure the Deque is iterable.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int counter = 0;
            int index = first();

            @Override
            public boolean hasNext() {
                return counter != size;
            }

            @Override
            public T next() {
                T item = items[index];
                index = (index + 1) % items.length;
                counter += 1;
                return item;
            }
        };
    }

    /**
     * Returns where or not the parameter `o' is equal to the Deque.
     * `o' is considered equal if it is a Deque and if it contains
     * the same contents in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        if (size != ((Deque<T>) o).size()) {
            return false;
        }
        if (!(o instanceof ArrayDeque)) {
            Deque<T> other = (Deque<T>) o;
            for (int i = 0; i < size; i++) {
                T a = get(i);
                T b = other.get(i);
                if (a == null) {
                    if (b != null) {
                        return false;
                    }
                    continue;
                }
                if (!a.equals(b)) {
                    return false;
                }
            }
            return true;
        }

        ArrayDeque<T> other = (ArrayDeque<T>) o;

        Iterator<T> iterA = iterator();
        Iterator<T> iterB = other.iterator();

        for (T a = iterA.next(), b = iterB.next();
             iterA.hasNext() && iterB.hasNext();
             a = iterA.next(), b = iterB.next()) {
            if (a == null) {
                return b == null;
            }
            if (!a.equals(b)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the deque needs to be resized.
     */
    private void checkSize() {
        if (size == items.length) {
            resize(items.length * SCALE_FACTOR);
        } else if (items.length > 16 && size * 4 < items.length) {
            resize(items.length / SCALE_FACTOR);
        }
    }

    /**
     * Resize the deque to `capacity' length.
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (first() <= last()) {
            System.arraycopy(items, first(), a, 0, size);
        } else {
            System.arraycopy(items, first(), a, 0, items.length - first());
            System.arraycopy(items, 0, a, items.length - first(), last() + 1);
        }

        nextFirst = capacity - 1;
        nextLast = size;

        items = a;
    }

    /**
     * Helping method.
     * Return the index of the very first element.
     * It should not be used if the deque is empty.
     */
    private int first() {
        return (nextFirst + 1) % items.length;
    }

    /**
     * Helping method.
     * Return the index of the very last element.
     * It should not be used if the deque is empty;
     */
    private int last() {
        return (nextLast - 1 + items.length) % items.length;
    }

//  You need to uncomment the following files to make sure the test files run properly.
//    /**
//     * Returns the String representation of the deque.
//     */
//    @Override
//    public String toString() {
//        if (size() == 0) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        Iterator<T> iter = iterator();
//        sb.append(iter.next());
//        while (iter.hasNext()) {
//            sb.append(" ");
//            sb.append(iter.next());
//        }
//
//        return sb.toString();
//    }
}
