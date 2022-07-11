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
    private BST<K, V> tree;

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
        tree = null;
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
        return containsKey(tree, key);
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
        BST<K, V> node = get(tree, key);
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
            return new BST<>(key, value);
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
        tree = put(tree, key, value);
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
        keySet(tree, set);
        return set;
    }

    private int numChild(BST<K, V> root) {
        if (root.left == null && root.right == null) {
            return 0;
        } else if (root.left == null) {
            return 1;
        } else if (root.right == null) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Helping method to find the parent of the node `current'.
     * @return
     */
    private BST<K, V> getParent(BST<K, V> parent, BST<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        if (current.key.equals(key)) {
            return parent;
        }

        if (current.key.compareTo(key) < 0) {
            return getParent(current, current.right, key);
        } else {
            return getParent(current, current.left, key);
        }
    }

    /**
     * Returns the biggest node among all nodes that less than key.
     */
    private BST<K, V> max(BST<K, V> root) {
        if (root == null) {
            throw new RuntimeException("You have to guarantee the there exists a predecessor!");
        }

        // try to find greater one
        if (root.right != null) {
            return max(root.right);
        } else { // return this one
            return root;
        }
    }

    /**
     * Helping method to remove a node based on key.
     * Three cases:
     *      1. No child for this node: remove the reference of its parent (or remove the root).
     *      2. One child for this node: change parent's reference to its child.
     *      3. Two children for this node: replace the node with its predecessor.
     */
    private BST<K, V> remove(BST<K, V> root, K key) {
        BST<K, V> node = get(root, key);
        if (node == null) {
            return null;
        }

        BST<K, V> parent = getParent(null, tree, key);

        switch(numChild(node)) {
            case 0:
                if (node == tree) {
                    tree = null;
                    break;
                }
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                break;
            case 1:
                BST<K, V> child = node.left == null ? node.right : node.left;
                if (node == tree) {
                    tree = child;
                    break;
                }
                if (parent.left == node) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
                break;
            case 2:
                BST<K, V> max = max(node.left);
                remove(tree, max.key);
                max.left = node.left;
                max.right = node.right;
                if (node == tree) {
                    tree = max;
                    break;
                }
                if (parent.left == node) {
                    parent.left = max;
                } else {
                    parent.right = max;
                }
                break;
            default:
                throw new RuntimeException("BST should have 0~2 children!");
        }

        return node;
    }

    @Override
    public V remove(K key) {
        BST<K, V> removedNode = remove(tree, key);
        if (removedNode == null) {
            return null;
        }
        size -= 1;
        return removedNode.value;
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
