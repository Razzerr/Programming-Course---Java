public class BinaryTree<Key extends Comparable<Key>> {
    Node root = null;

    class Node {
        Key key;
        Node left, right, parent;

        Node(Key keyIn, Node leftIn, Node rightIn, Node parentIn) {
            key = keyIn;
            left = leftIn;
            right = rightIn;
            parent = parentIn;
        }
    }

    void add(Key val) {
        Node newNode = new Node(val, null, null, null);
        if (root == null) {
            root = newNode;
            return;
        }

        Node parent = null;
        Node current = root;
        int cmp;

        while (true) {
            parent = current;
            cmp = val.compareTo(parent.key);
            if (cmp < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else if (cmp > 0) {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            } else {
                return;
            }
        }
    }

    Node search(Key val) {
        if (root == null) {
            return null;
        }
        int cmp = val.compareTo(root.key);
        if (cmp < 0) {
            return search(root.left, val);
        } else {
            return search(root.right, val);
        }
    }

    Node search(Node x, Key val) {
        if (x == null || val == x.key) {
            return x;
        }
        int cmp = val.compareTo(x.key);
        if (cmp < 0) {
            return search(x.left, val);
        } else {
            return search(x.right, val);
        }
    }

    void delete(Key key)
    {
        root = deleteRec(root, key);
    }


    Node deleteRec(Node root, Key key)
    {

        if (root == null)  return root;

        int cmp = key.compareTo(root.key);
        if (cmp<0)
            root.left = deleteRec(root.left, key);
        else if (cmp>0)
            root.right = deleteRec(root.right, key);


        else
        {
            if (root.left==null && root.right ==null){
                return null;
            }
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minimum(root.right).key;
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    Node minimum(Node v) {
        Node x = v;
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    String tree = "";

    String treeToString(Node x) {
        if (x != null) {
            tree += String.valueOf(x.key);
            if (x.left != null) {
                tree += ",";
                treeToString(x.left);
            }
            if (x.right != null) {
                tree += "/";
                treeToString(x.right);
            }
            if (x != root) {
                tree += "(";
            }
        }
        return tree;
    }
}
