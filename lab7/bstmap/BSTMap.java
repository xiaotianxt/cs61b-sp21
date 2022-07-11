package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private static class BST<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public BST<K, V> left;
        public BST<K, V> right;
        public BST(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * The root of BST.
     */
    private BST<K, V> root;

    /**
     * The size of BST.
     */
    private int size;

    public BSTMap() {
        size = 0;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Helping method for containsKey(K key).
     * Returns `true' if the BSTMap contains `key'.
     */
    private boolean containsKey(BST<K, V> root, K key) {
        if (root == null) {
            return false;
        }
        if (root.key.equals(key)) {
            return true;
        }
        if (root.key.compareTo(key) < 0) {
            return containsKey(root.right, key);
        } else {
            return containsKey(root.left, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    /**
     * Helping method for get(K key).
     * Returns the `value' if this contains `key',
     * else return `null'.
     */
    private BST<K, V> get(BST<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root;
        }
        if (root.key.compareTo(key) < 0) {
            return get(root.right, key);
        } else {
            return get(root.left, key);
        }
    }

    @Override
    public V get(K key) {
        BST<K, V> node = get(root, key);
        if (node != null) {
            return node.value;
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Helping method for put(K key, V value).
     * Returns a new Node if it's time to create,
     * else return root itself.
     */
    private BST<K, V> put(BST<K, V> root, K key, V value) {
        if (root == null) {
            size += 1;
            return new BST<K, V>(key, value);
        }
        if (root.key.equals(key)) {
            root.value = value;
            return root;
        }
        if (root.key.compareTo(key) < 0) {
            root.right = put(root.right, key, value);
        } else {
            root.left = put(root.left, key, value);
        }
        return root;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private void keySet(BST<K, V> root, Set<K> set) {
        if (root == null) {
            return;
        }
        set.add(root.key);
        keySet(root.left, set);
        keySet(root.right, set);
    }
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySet(root, set);
        return set;
    }

    private V remove(BST<K, V> root, K key) {
        BST<K, V> node = get(root, key);
        if (node == null) {
            return null;
        }
        assert node.key.equals(key);
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(root, key);
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
