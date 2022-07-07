package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T i) {
            item = i;
        }
    }

    private final Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type `T' to the front of the deque.
     */
    @Override
    public void addFirst(T item) {
        Node<T> oldFirst = sentinel.next;
        Node<T> newFirst = new Node<>(item);

        sentinel.next = newFirst;
        newFirst.next = oldFirst;

        newFirst.prev = sentinel;
        oldFirst.prev = newFirst;

        size += 1;
    }

    /**
     * Adds an item of type `T' to the back of the deque.
     */
    @Override
    public void addLast(T item) {
        Node<T> oldLast = sentinel.prev;
        Node<T> newLast = new Node<>(item);

        sentinel.prev = newLast;
        newLast.prev = oldLast;

        oldLast.next = newLast;
        newLast.next = sentinel;

        size += 1;
    }

    /**
     * Returns `true' if deque is empty, `false' otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
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
        Node<T> node = sentinel.next;

        // print all items, except the last one.
        while (node != sentinel.prev) {
            System.out.print(node.item + " ");
            node = node.next;
        }

        // print the last item, or print empty line.
        if (node != sentinel) {
            System.out.println(node.item);
        } else {
            System.out.println();
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns `null'.
     */
    @Override
    public T removeFirst() {
        Node<T> oldFirst = sentinel.next;
        Node<T> newFirst = oldFirst.next;

        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        size = size == 0 ? 0 : size - 1;

        return oldFirst.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns `null'.
     */
    @Override
    public T removeLast() {
        Node<T> oldLast = sentinel.prev;
        Node<T> newLast = oldLast.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        size = size == 0 ? 0 : size - 1;
        return oldLast.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * If no such item exists, returns `null'.
     */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }

        Node<T> node = sentinel.next;
        while (index > 0) {
            index--;
            node = node.next;
        }

        return node.item;
    }

    /**
     * Gets the item at the given index.
     * Same as `get', but uses recursion.
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return getRecursive(sentinel.next, index);
        }
    }

    /**
     * Helping method for `getRecursive'.
     */
    private T getRecursive(Node<T> cur, int k) {
        if (k == 0) {
            return cur.item;
        } else {
            return getRecursive(cur.next, k - 1);
        }
    }

    /**
     * Returns an iterator to make sure the Deque is iterable.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> cur = sentinel;

            @Override
            public boolean hasNext() {
                return cur.next != sentinel;
            }

            @Override
            public T next() {
                cur = cur.next;
                return cur.item;
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
        if (o.getClass() != this.getClass()) {
            return false;
        }

        LinkedListDeque<T> other = (LinkedListDeque<T>) o;

        if (this.size != other.size) {
            return false;
        }

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

    @Override
    public String toString() {
        if (size == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Node<T> item = sentinel;
        for (int i = 0; i < size - 1; i++) {
            item = item.next;
            sb.append(item.item);
            sb.append(' ');
        }
        sb.append(item.next.item);
        return sb.toString();
    }
}
