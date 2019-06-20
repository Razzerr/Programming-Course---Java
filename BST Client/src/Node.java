class Node<Key>{
    Key key;
    Node left, right, parent;

    Node(Key keyIn, Node leftIn, Node rightIn, Node parentIn) {
        key = keyIn;
        left = leftIn;
        right = rightIn;
        parent = parentIn;
    }
}