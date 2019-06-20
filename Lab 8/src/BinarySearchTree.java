public class BinarySearchTree<Key extends Comparable<Key>> {
    private Node root;             // root

    private class Node {
        private Key key;           // Data
        private Node left, right;  // left and right subtrees

        public Node(Key key, int size) {
            this.key = key;
            this.left = left;
            this.right = right;

        }
    }

    public BinarySearchTree() {
    }

    public Node contains(Key key) {
        return Node(root, key);
    }

    private Node contains(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return contains(x.left, key);
        else if (cmp > 0) return contains(x.right, key);
        else return x;
    }

    private Node search(Key v) {
        if (root == null) return null;
        if (v < tree.get(0).key) {
            return searchF(tree.get(0).left, v);
        } else {
            return searchF(tree.get(0).right, v);
        }
    }













    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calledput() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


}
